package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ShoppingCartMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/20 15:45
 * @Version 1.0
 **/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
