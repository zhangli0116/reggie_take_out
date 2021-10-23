package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.OrdersDto;
import com.example.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName OrdersMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/20 16:40
 * @Version 1.0
 **/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 分页查询订单及订单详情数据
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<OrdersDto> page(Page page,@Param(Constants.WRAPPER) QueryWrapper<OrdersDto> queryWrapper);
}
