package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.OrdersDto;
import com.example.entity.Orders;
import com.example.entity.ShoppingCart;
import com.example.service.OrderDetailService;
import com.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName OrdersController
 * @Description 订单管理
 * @Author zhang
 * @Date 2021/10/20 16:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    /**
     * 下单支付 （支付功能没做）
     * http://localhost:8080/order/submit
     * @param orders
     * @return
     */
    @PostMapping
    @RequestMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 后台 - 分页查询
     * @param page 页数
     * @param pageSize 每页条数
     * @param number 订单号
     * @param beginTime 下单开始日期
     * @param endTime 下单结束日期
     * @return
     */
    @GetMapping
    @RequestMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, Integer number,
                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime){
        //1、分页对象
        Page iPage = new Page(page,pageSize);
        //2、查询条件
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(number!=null,Orders::getNumber,number);
        lqw.between(beginTime!=null&& endTime!=null ,Orders::getOrderTime,beginTime,endTime);
        lqw.orderByDesc(Orders::getOrderTime);
        //3、分页查询
        ordersService.page(iPage,lqw);
        return R.success(iPage);
    }

    /**
     * http://localhost:8080/order
     * 后台 - 更新订单数据 （修改订单状态）
     * 2:待派送
     * 3:已派送
     * 4:派送成功
     * 5:已取消 （未做）
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        ordersService.updateById(orders);
        return R.success("状态修改成功");
    }

    /**
     * 分页查询订单数据 和 订单详情数据
     * url: http://localhost:8080/order/userPage?page=1&pageSize=1
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    @RequestMapping("/userPage")
    public R<Page> userPage(Integer page,Integer pageSize){
        //1、分页对象
        Page iPage = new Page(page,pageSize);
        //2、查询对象
        QueryWrapper<OrdersDto> qw = new QueryWrapper<>();
        //按下单时间倒序
        qw.orderByDesc("orders.order_time");
        //3、查询
        ordersService.page(iPage,qw);
        return R.success(iPage);
    }

}
