package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author zhouxiangyang
 * @Date 2021/10/13 18:16
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
