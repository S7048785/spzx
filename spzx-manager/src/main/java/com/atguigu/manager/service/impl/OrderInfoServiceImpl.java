package com.atguigu.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.manager.mapper.OrderInfoMapper;
import com.atguigu.manager.mapper.OrderStatisticsMapper;
import com.atguigu.manager.service.IOrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
	private final OrderStatisticsMapper orderStatisticsMapper;

	public OrderInfoServiceImpl(OrderStatisticsMapper orderStatisticsMapper) {
		this.orderStatisticsMapper = orderStatisticsMapper;
	}

	@Override
	public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
		// 查询统计结果数据
		List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(Wrappers.lambdaQuery(OrderStatistics.class).ge(OrderStatistics::getOrderDate, orderStatisticsDto.getCreateTimeBegin()).le(OrderStatistics::getOrderDate, orderStatisticsDto.getCreateTimeEnd()).orderByAsc(OrderStatistics::getOrderDate));

		//日期列表
		List<String> dateList = orderStatisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).collect(Collectors.toList());

		//统计金额列表
		List<BigDecimal> amountList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());

		// 创建OrderStatisticsVo对象封装响应结果数据
		OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo() ;
		orderStatisticsVo.setDateList(dateList);
		orderStatisticsVo.setAmountList(amountList);

		// 返回数据
		return orderStatisticsVo;
	}
}
