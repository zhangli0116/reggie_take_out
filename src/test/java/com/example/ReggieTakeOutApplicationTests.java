package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dto.DishDto;
import com.example.mapper.DishMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReggieTakeOutApplicationTests {

    @Autowired
    private DishMapper dishMapper;

    @Test
    void contextLoads() {
    }

    /*
    使用wrapper自定义SQL
     */
    @Test
    void test2(){
        //查询参数
        String name = "鸡";
        //封装查询对象
        QueryWrapper<DishDto> qw = new QueryWrapper<>();
        qw.like("dish.name",name);
        qw.lambda().orderByDesc(DishDto::getUpdateTime);
        //
        //List<DishDto> list = dishMapper.findAllWithCategory(qw);
        //System.out.println(list);
    }

}
