package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.common.CustomException;
import com.example.dto.OrdersDto;
import com.example.entity.*;
import com.example.mapper.OrdersMapper;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName OrdersServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/20 16:42
 * @Version 1.0
 **/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();
        
        //获得当前用户信息
        User user = userService.getById(userId);

        //获得当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(lqw);
        if(shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，请重新添加");
        }

        //通过id获得地址信息
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if(addressBook == null){
            throw new CustomException("请设置默认的地址信息");
        }


        //生成订单id
        long orderId = IdWorker.getId();

        //创建一个原子类用来计数
        AtomicInteger amount = new AtomicInteger(0);

        //组装订单明细数据
        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //组装订单数据
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        //插入订单数据
        this.save(orders);
        //批量插入订单明细数据
        orderDetailService.saveBatch(orderDetails);
        //清空当前用户的购物车
        shoppingCartService.remove(lqw);
    }

    /**
     * 分页查询订单及订单详情数据
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public Page<OrdersDto> page(Page page, QueryWrapper<OrdersDto> queryWrapper) {
        return ordersMapper.page(page,queryWrapper);
    }
}
