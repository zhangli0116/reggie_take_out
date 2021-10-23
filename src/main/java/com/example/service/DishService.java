package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName DishService
 * @Description
 * @Author zhang
 * @Date 2021/10/16 16:13
 * @Version 1.0
 **/
public interface DishService extends IService<Dish> {

    @Transactional
    void saveWithFlavor(DishDto dishDto);

    @Transactional
    DishDto getWithFlavor(Long id);

    @Transactional
    void updateWithFlavor(DishDto dishDto);

    /**
     * 分页查询菜品信息
     * @param page
     * @param queryWrapper
     * @return
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    Page<DishDto> page(Page page, QueryWrapper<DishDto> queryWrapper);

    /**
     * 查询菜品及菜品口味信息
     * @param wrapper
     * @return
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    List<DishDto> getWithFlavor(QueryWrapper wrapper);

    /**
     * 根据id批量删除已停售的菜品 和 菜品口味信息
     * @param ids
     */
    @Transactional
    void removeWithFlavor(List<Long> ids);
}
