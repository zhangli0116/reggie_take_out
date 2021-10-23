package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.entity.Category;
import com.example.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CatagoryController
 * @Description
 * @Author zhang
 * @Date 2021/10/16 11:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品或套餐分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功");
    }

    /**
     * 分页查询数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> iPage = new Page<>(page,pageSize);
        //
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //按sort升序排序
        lqw.orderByAsc(Category::getSort);
        categoryService.page(iPage,lqw);
        return R.success(iPage);
    }

    /**
     * 通过id删除菜品数据
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        categoryService.remove(id);
        return R.success("删除菜品成功");
    }

    /**
     * 修改菜品/套餐分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 根据type 查询菜品或套餐分类
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //封装查询对象
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //添加条件
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序条件
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        //list查询
        List<Category> list = categoryService.list(lqw);
        //响应
        return R.success(list);
    }
}
