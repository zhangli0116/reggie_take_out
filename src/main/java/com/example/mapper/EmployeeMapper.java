package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName EmployeeMapper
 * @Description
 * @Author zhang
 * @Date 2021/10/13 14:18
 * @Version 1.0
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
