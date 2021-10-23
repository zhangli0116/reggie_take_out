package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DishDto;
import com.example.entity.Category;

/**
 * @ClassName CategoryService
 * @Description
 * @Author zhang
 * @Date 2021/10/16 11:40
 * @Version 1.0
 **/
public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除分类
     * @param id
     */
    boolean remove(Long id);

}
