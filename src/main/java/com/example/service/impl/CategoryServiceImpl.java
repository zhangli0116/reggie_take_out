package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomException;
import com.example.entity.Category;
import com.example.entity.Dish;
import com.example.entity.Setmeal;
import com.example.mapper.CategoryMapper;
import com.example.service.CategoryService;
import com.example.service.DishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/16 11:40
 * @Version 1.0
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public boolean remove(Long id) {
        //判断当前分类下是否还存在菜品
        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(lqw1);
        if(count1 > 0){
            //抛出自定义异常
            throw new CustomException("当前分类下还存在菜品");
        }

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(lqw2);
        if(count2 > 0){
            //抛出自定义异常
            throw new CustomException("当前分类下还存在套餐");
        }


        //当分类下不存在菜品或套餐时，进行删除操作
        //如果存在@TableLogic则进行逻辑删除
        return super.removeById(id);

    }
}
