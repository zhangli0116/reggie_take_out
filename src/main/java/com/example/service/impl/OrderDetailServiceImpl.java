package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.OrderDetail;
import com.example.mapper.OrderDetailMapper;
import com.example.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderDetailServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/20 16:43
 * @Version 1.0
 **/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
