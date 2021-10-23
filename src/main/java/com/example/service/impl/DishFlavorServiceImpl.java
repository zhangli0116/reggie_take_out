package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.DishFlavor;
import com.example.mapper.DishFlavorMapper;
import com.example.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @ClassName DishFlavorServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/17 9:21
 * @Version 1.0
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService{
}
