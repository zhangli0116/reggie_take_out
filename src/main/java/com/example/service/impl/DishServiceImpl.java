package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomException;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import com.example.mapper.DishMapper;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DishServiceImpl
 * @Description
 * @Author zhang
 * @Date 2021/10/16 16:14
 * @Version 1.0
 **/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 分页查询菜品数据
     * @param queryWrapper
     * @return
     */
    @Override
    public Page<DishDto> page(Page page, QueryWrapper<DishDto> queryWrapper) {
        return dishMapper.page(page,queryWrapper);
    }

    /**
     * 查询菜品及菜品口味信息
     * @param wrapper
     * @return
     */
    @Override
    public List<DishDto> getWithFlavor(QueryWrapper wrapper) {
        return dishMapper.getWithFlavor(wrapper);
    }

    /**
     * 根据id批量删除已停售的菜品 和 菜品相关的口味信息
     * @param ids
     */
    @Override
    public void removeWithFlavor(List<Long> ids) {
        //1、判断当前菜品是否停售
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getStatus,1);
        lqw.in(Dish::getId,ids);
        int count = this.count(lqw);
        if(count > 0){
            throw new CustomException("当前存在未停售的菜品");
        }
        //2、删除菜品信息
        this.removeByIds(ids);
        //3、删除菜品口味信息
        LambdaQueryWrapper<DishFlavor> dlqw = new LambdaQueryWrapper<>();
        dlqw.in(DishFlavor::getDishId,ids);
        dishFlavorService.remove(dlqw);

    }


    /**
     * 添加菜品同时添加菜品口味 -- 需要在接口添加事务
     * @param dishDto
     */
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //1、添加菜品

        //添加菜品后,雪花算法生成的id自动注入到实体类中
        this.save(dishDto);

        //2、添加菜品关联的菜品口味
        //获得口味集合
        List<DishFlavor> flavors = dishDto.getFlavors();
        //获得生成的菜品id
        Long dishId = dishDto.getId();
        flavors = flavors.stream().map((flavor) ->{
            flavor.setDishId(dishId);
            return flavor;
        }).collect(Collectors.toList());

        //批量添加
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 修改菜品信息 和 对应的菜品口味信息
     * @param dishDto
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品信息 ？
        this.updateById(dishDto);
        // 删除菜品口味信息

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);

        // 添加菜品口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        //菜品口味中封装菜品id （因为前端传来的数据并没有进行封装）
        flavors = flavors.stream().map( flavor ->{
            flavor.setDishId(dishDto.getId());
            return flavor;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 通过id获得菜品信息 和 菜品口味信息
     * @param id  菜品id
     * @return
     */
    @Override
    public DishDto getWithFlavor(Long id){
        //1、获得菜品信息
        Dish dish = this.getById(id);
        //创建表现层对象
        DishDto dishDto = new DishDto();
        //复制 dish -> dishDto
        BeanUtils.copyProperties(dish,dishDto);

        //2、获得菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(lqw);

        //3.将菜品口味封装到菜品中
        dishDto.setFlavors(list);
        return dishDto;
    }




}
