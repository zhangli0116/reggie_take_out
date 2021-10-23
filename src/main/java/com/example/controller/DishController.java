package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import com.example.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DishController
 * @Description
 * @Author zhang
 * @Date 2021/10/17 11:43
 * @Version 1.0
 **/
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * 添加菜品 和 菜品对应口味
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    /**
     * 分页查询菜品信息
     * @param page 当前页
     * @param pageSize 每页大小
     * @param name 菜品名称
     * @return
     */
    @GetMapping("/page")
    public R<Page> list(int page,int pageSize,String name){
        //创建查询条件
        QueryWrapper<DishDto> qw = new QueryWrapper<>();
        // dish.name like %xxx%
        qw.like(StringUtils.isNotBlank(name),"dish.name",name);
        qw.orderByDesc("dish.update_time");
        //设置分页查询条件
        Page<DishDto> iPage = new Page<>(page,pageSize);
        //进行分页查询
        dishService.page(iPage,qw);
        return R.success(iPage);
    }

    /**
     * 根据id获得菜品 和 菜品口味
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        DishDto dishDto = dishService.getWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 更新菜品 和 菜品对应的口味
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("更新菜品及对应的口味成功");
    }

    /**
     * 后台套餐管理模块/添加菜品
     * 功能 ： 根据条件(category_id 和 status)查询查询菜品数据
     * @param
     * @return
     */
    /*@GetMapping("/list")
    public R<List<Dish>> listByCategoryId(Dish dish){
        //
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //添加分类id条件
        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        //添加排序条件
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        //设置起售条件（1:启售 0:停售）
        lqw.eq(Dish::getStatus,1);
        //
        List<Dish> list = dishService.list(lqw);
        return R.success(list);
    }*/

    /**
     * 增强 /dish/list
     * 根据条件(category_id 和 status)查询查询菜品及菜品口味数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getWithFlavor(Dish dish){
        //1、设置查询条件
        QueryWrapper<DishDto> lqw = new QueryWrapper<>();
        //添加分类id条件
        lqw.eq(dish.getCategoryId()!=null,"dish.category_id",dish.getCategoryId());
        //添加排序条件 （按更新时间倒序和sort升序排列）
        lqw.orderByAsc("dish.sort").orderByDesc("dish.update_time");
        //设置起售条件（1:启售 0:停售）
        lqw.eq("dish.status",1);
        //2、获得菜品及口味信息
        List<DishDto> list = dishService.getWithFlavor(lqw);
        return R.success(list);
    }

    /**
     *  url: http://localhost:8080/dish?ids=1397850392090947585
     *  http://localhost:8080/dish?ids=1397849739276890114,1397850140982161409
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.removeWithFlavor(ids);
        return R.success("删除成功");
    }

    /**
     * 修改菜品的状态
     * http://localhost:8080/dish/status/0?ids=1451016583043477506,1451016442609790978
     * @return
     */
    @RequestMapping("/status/{code}")
    @PostMapping
    public R<String> status(@PathVariable("code") Integer status,@RequestParam("ids") List<Long> ids){
        //1、更新条件设置
        LambdaUpdateWrapper<Dish> luw = new LambdaUpdateWrapper<>();
        luw.in(Dish::getId,ids);
        luw.set(Dish::getStatus,status);
        //2、执行更新
        dishService.update(luw);
        return R.success("修改状态成功");
    }

}
