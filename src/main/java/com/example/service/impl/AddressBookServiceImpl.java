package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.AddressBook;
import com.example.mapper.AddressBookMapper;
import com.example.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @Author zhouxiangyang
 * @Date 2021/10/13 18:16
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
