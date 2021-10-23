package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/18 16:20
 * @Version 1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
