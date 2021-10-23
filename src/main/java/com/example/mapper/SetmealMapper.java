package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName SetmealMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/16 14:57
 * @Version 1.0
 **/
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    /**
     * 分页查询套餐及套餐分类名称 （一对一查询）
     * @param iPage
     * @param qw
     * @return
     */
    @Select("select setmeal.*,category.name as category_name \n" +
            "from setmeal left join category \n" +
            "on setmeal.category_id = category.id ${ew.customSqlSegment}")
    Page<SetmealDto> pageWithCategory(Page iPage, @Param(Constants.WRAPPER) QueryWrapper qw);

    /**
     * 根据id查询套餐及套餐菜品（一对多查询） 自己写sql
     * select * from setmeal left join setmeal_dish on setmeal.id = setmeal_dish.setmeal_id where setmeal.id = 1415580119015145474
     * @param qw
     * @return
     */
    /*SetmealDto getWithDish(@Param(Constants.WRAPPER) QueryWrapper qw);*/
}
