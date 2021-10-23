package com.example.dto;


import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    /**
     * 菜品口味
     */
    private List<DishFlavor> flavors = new ArrayList<>();

    /**
     * 分类名称
     */
    private String categoryName;

    private Integer copies;

}
