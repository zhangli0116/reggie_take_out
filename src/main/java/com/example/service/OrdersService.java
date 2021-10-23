package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.OrdersDto;
import com.example.entity.Orders;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName OrdersService
 * @Description
 * @Author zhang
 * @Date 2021/10/20 16:41
 * @Version 1.0
 **/
public interface OrdersService extends IService<Orders> {
    /**
     * 下单
     * @param orders
     */
    @Transactional
    void submit(Orders orders);

    /**
     * 分页查询订单及订单详情数据
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<OrdersDto> page(Page page , QueryWrapper<OrdersDto> queryWrapper);
}
