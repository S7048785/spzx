package com.atguigu.manager.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.manager.mapper.SysMenuMapper;
import com.atguigu.manager.mapper.SysRoleUserMapper;
import com.atguigu.manager.mapper.SysUserMapper;
import com.atguigu.manager.service.ISysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUserRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import spzx.common.exception.GuiguException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public LoginVo login(LoginDto loginDto) {
		// 获取验证码和key
		String captcha = loginDto.getCaptcha();
		String codeKey = loginDto.getCodeKey();
		// 查询redis
		String code = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
		// 校验
		if (!StrUtil.equalsIgnoreCase(captcha, code)) {
			throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
		}
		if (StrUtil.isBlank(code)) {
			throw new GuiguException(202,"验证码过期");
		}
		// 删除验证码缓存
		redisTemplate.delete("user:login:validatecode:" + codeKey);

		SysUser sysUser = getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, loginDto.getUserName()));
		if (sysUser == null) {
			throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
		}
		// 验证密码是否正确
		String password = loginDto.getPassword();
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!md5Password.equals(sysUser.getPassword())) {
			throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);

		}
		// 生成令牌，保存数据到Redis中
		String token = UUID.randomUUID().toString(true);
		redisTemplate.opsForValue().set("user:login:" + token, JSONUtil.toJsonStr(sysUser), 30, TimeUnit.MINUTES);

		// 构建响应结果对象
		LoginVo loginVo = new LoginVo() ;
		loginVo.setToken(token);
		loginVo.setRefresh_token("");

		// 返回
		return loginVo;
	}

	@Override
	public SysUser getUserInfo(String token) {
		String userJson = redisTemplate.opsForValue().get("user:login:" + token);
		SysUser user = JSONUtil.toBean(userJson, SysUser.class);
		return user;
	}

	@Override
	public void logout(String token) {
		redisTemplate.delete("user:login:" + token);
	}

	public String getIpAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 上面两个获取Ip的逻辑都可以
		return request.getRemoteAddr();
	}

	@Override
	public Page<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
		Page<SysUser> pageInfo = Page.of(pageNum, pageSize);
		if(sysUserDto.getCreateTimeBegin() == null || sysUserDto.getCreateTimeEnd() == null) {
			sysUserDto.setCreateTimeBegin("0");
			sysUserDto.setCreateTimeEnd("0");
		}
		page(
				pageInfo,
				Wrappers.lambdaQuery(SysUser.class)
						.like(StrUtil.isNotBlank(sysUserDto.getKeyword()), SysUser::getUsername, sysUserDto.getKeyword())
						.gt(!"0".equals(sysUserDto.getCreateTimeBegin()), SysUser::getCreateTime, Long.valueOf(sysUserDto.getCreateTimeBegin()))
						.lt(!"0".equals(sysUserDto.getCreateTimeEnd()), SysUser::getCreateTime, Long.valueOf(sysUserDto.getCreateTimeEnd()))
		);
		return pageInfo;
	}

	@Transactional
	@Override
	public void doAssign(AssginRoleDto assginRoleDto) {
		// 删除之前的所有的用户角色关系
		sysRoleUserMapper.delete(Wrappers.lambdaQuery(SysUserRole.class).in(SysUserRole::getUserId, assginRoleDto.getUserId()));

		// 分配新的角色数据
		List<Long> roleIdList = assginRoleDto.getRoleIdList();
		sysRoleUserMapper.insert(CollUtil.map(roleIdList, roleId -> {
			long timestamp = System.currentTimeMillis();
			java.util.Date date = new java.util.Date(timestamp);
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = sdf.format(date);

			return SysUserRole.builder()
					.userId(assginRoleDto.getUserId())
					.roleId(roleId)
					.createTime(formattedDate)
					.updateTime(formattedDate)
					.isDeleted(0)
					.build();
				}, true)

		);
	}


}
