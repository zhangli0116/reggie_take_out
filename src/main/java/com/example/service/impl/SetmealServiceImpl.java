package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomException;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import com.example.mapper.SetmealMapper;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SetmealServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/16 16:19
 * @Version 1.0
 **/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //  1、新增套餐数据
        this.save(setmealDto);

        // 2、新增套餐和菜品关联数据
        //获得套餐id
        Long setmealId = setmealDto.getId();
        // 在套餐菜品关联中添加套餐id
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map( setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            return setmealDish;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public Page<SetmealDto> pageWithCategory(Page iPage, QueryWrapper qw) {
        Page<SetmealDto> withCategory = setmealMapper.pageWithCategory(iPage, qw);
        return withCategory;
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //判断当前套餐是否停售
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getStatus,1);
        int count = this.count(lqw);
        if(count > 0){
            throw new CustomException("当前套餐还处于启售状态");
        }

        //删除套餐菜品关联关系
        LambdaQueryWrapper<SetmealDish> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lqw2);

        //删除套餐
        this.removeByIds(ids);

    }

    /**
     * 获得套餐及套餐菜品
     * @param id
     * @return
     */
    @Override
    public SetmealDto getWithDish(Long id) {
        //1、获得套餐信息
        Setmeal setmeal = this.getById(id);
        //2、创建表现层对象
        SetmealDto setmealDto = new SetmealDto();
        //复制信息
        BeanUtils.copyProperties(setmeal,setmealDto);

        //3、获得套餐对应的菜品信息
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(lqw);
        //封装对象
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    /**
     * 修改套餐及套餐菜品信息
     * @param setmealDto
     */
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //1、修改套餐信息
        this.updateById(setmealDto);

        //2、修改套餐对应的菜品信息
        //2.1 先删除
        Long setmealId = setmealDto.getId();

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(lqw);
        //2.2 再插入
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //插入套餐的id
        setmealDishes = setmealDishes.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            return setmealDish;
        }).collect(Collectors.toList());
        //批量插入数据
        setmealDishService.saveBatch(setmealDishes);



    }


}
