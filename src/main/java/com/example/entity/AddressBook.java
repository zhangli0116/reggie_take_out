package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地址簿
 */
@Data
public class AddressBook implements Serializable {


    private static final long serialVersionUID = 1894754613241646450L;
    //在序列化成json时，将long类型数据序列化成String类型，解决前端js在使用long类型数据精度丢失问题。
    //@JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    //用户id
    //@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    //收货人
    private String consignee;


    //手机号
    private String phone;


    //性别 0 女 1 男
    private String sex;


    //省级区划编号
    private String provinceCode;


    //省级名称
    private String provinceName;


    //市级区划编号
    private String cityCode;


    //市级名称
    private String cityName;


    //区级区划编号
    private String districtCode;


    //区级名称
    private String districtName;


    //详细地址
    private String detail;


    //标签
    private String label;

    //是否默认 0 否 1是
    private Integer isDefault;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;
}
