package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Wrapper;
import java.util.List;

/**
 * @ClassName DishMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/16 14:57
 * @Version 1.0
 **/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 分页查询菜品及菜品分类信息 （一对一查询）
     * @param page
     * @param wrapper
     * @return
     * 自定义SQL + 分页 （一对一查询）
     *     说明:
     *     1、Page参数必须放在第一位，mybatis-plus分页插件帮我们自动拼接
     *     2、wrapper封装的sql ，通过${ew.customSqlSegment}帮我们注入
     */
    @Select("select dish.*,category.name as category_name from dish \n" +
            "left join category on dish.category_id = category.id ${ew.customSqlSegment}")
    Page<DishDto> page(Page page ,@Param(Constants.WRAPPER) QueryWrapper wrapper);

    /**
     * 查询菜品及菜品口味信息（一对多查询）
     * @param wrapper
     * @return
     */
    List<DishDto> getWithFlavor(@Param(Constants.WRAPPER) QueryWrapper wrapper);


    //---------------------------- mybatis-plus自定义方法 测试 ---------------------------------

    /*
    需求1: 按照name查询菜品信息，并按照update_time降序排序
     */
    /*@Select("select * from dish where name like concat('%',#{name},'%')")
    List<Dish> findAll(String name);*/

    /*
    需求2: 按照name查询菜品信息，并按照update_time降序排序
    思考: mybatisplus是如何传参的？
    要求: 通过QueryWrapper对象传递查询参数
     */
   /* @Select("select * from dish ${ew.customSqlSegment}")
    List<Dish> findAllByWrapper(@Param(Constants.WRAPPER) Wrapper wrapper);*/

    /*
    需求3: 同时查询出菜品的名称 - 多表查询
    原理：底层从Wrapper中取出参数，拼接出sql，sql需要使用 ${ew.customSqlSegment}固定表达式来接受
     */
    /*@Select("select dish.*,category.name as category_name from dish \n" +
            "left join category on dish.category_id = category.id ${ew.customSqlSegment}")
    List<DishDto> findAllWithCategory(@Param(Constants.WRAPPER) QueryWrapper wrapper);*/


}
