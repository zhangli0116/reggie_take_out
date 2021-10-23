package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName SetmealService
 * @Description
 * @Author zhang
 * @Date 2021/10/16 16:19
 * @Version 1.0
 **/
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐及关联菜品
     * @param setmealDto
     */
    @Transactional
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 分页查询套餐
     * @param iPage
     * @param qw
     * @return
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    Page<SetmealDto> pageWithCategory(Page iPage, QueryWrapper qw);

    /**
     * 删除套餐及关联菜品
     * @param ids
     */
    @Transactional
    void removeWithDish(List<Long> ids);


    /**
     * 获得套餐及套餐菜品信息
     * @param id
     * @return
     */
    SetmealDto getWithDish(Long id);

    /**
     * 修改套餐及套餐菜品信息
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}
