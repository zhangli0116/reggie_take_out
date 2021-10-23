package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.SetmealDto;
import com.example.entity.Dish;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SetmealController
 * @Description 套餐管理
 * @Author zhang
 * @Date 2021/10/18 11:12
 * @Version 1.0
 **/
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 获得套餐分页数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //分页对象
        Page<Setmeal> iPage = new Page<>(page,pageSize);
        //查询对象
        QueryWrapper<Setmeal> qw = new QueryWrapper<>();
        //设置查询条件
        qw.like(StringUtils.isNotBlank(name),"setmeal.name",name);
        // 多表分页查询
        setmealService.pageWithCategory(iPage,qw);
        return R.success(iPage);
    }

    /** xxxx?ids=1,2,3
     * 更加id 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIds(@RequestParam("ids") List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 获得对应套餐集合
     * 查询条件 : 套餐分类 和 可售状态为 1
     * http://localhost:8080/setmeal/list?categoryId=1450039704190812162&status=1
     * @return
     */
    @GetMapping
    @RequestMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        //1、设置查询条件
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(Setmeal::getStatus,setmeal.getStatus());
        //2、按条件查询套餐信息
        List<Setmeal> list = setmealService.list(lqw);
        return R.success(list);
    }

    /**
     * 根据套餐id 获取套餐及套餐菜品信息
     * @param id
     * @return
     */
    @GetMapping
    @RequestMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getWithDish(id);
        return R.success(setmealDto);
    }

    /**
     * 根据id查询套餐对应的菜品详情
     * @return
     */
    @GetMapping
    @RequestMapping("/dish/{setmealId}")
    public R<List<SetmealDish>> getDish(@PathVariable Long setmealId){
        //1、
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,setmealId);
        //2、
        List<SetmealDish> list = setmealDishService.list(lqw);
        return R.success(list);
    }

    /**
     * 修改套餐及套餐菜品信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> saveWithDish(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("保存套餐及套餐菜品成功");
    }

    /**
     * 批量起售/停售
     * http://localhost:8080/setmeal/status/0?ids=1415580119015145474,1450040880579837954
     * 0:停售 1:起售
     * @param code
     * @param ids
     * @return
     */
    @PostMapping
    @RequestMapping("/status/{code}")
    public R<String> status(@PathVariable Integer code,@RequestParam List<Long> ids){
        //1、设置更改条件
        LambdaUpdateWrapper<Setmeal> luw = new LambdaUpdateWrapper<>();
        luw.set(Setmeal::getStatus,code);
        luw.in(Setmeal::getId,ids);
        //2、更改状态
        setmealService.update(luw);
        return R.success("修改成功");
    }
}
