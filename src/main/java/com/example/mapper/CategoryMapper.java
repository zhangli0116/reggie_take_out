package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CategoryMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/16 11:39
 * @Version 1.0
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
