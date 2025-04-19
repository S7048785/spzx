package com.atguigu.manager.service;

import com.atguigu.spzx.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {

    // 获取验证码图片
    public ValidateCodeVo generateValidateCode();

}