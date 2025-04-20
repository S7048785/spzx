package com.atguigu.manager.service;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IOrderInfoService extends IService<OrderInfo> {
	OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
