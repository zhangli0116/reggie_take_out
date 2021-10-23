package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.common.BaseContext;
import com.example.common.CustomException;
import com.example.common.R;
import com.example.entity.ShoppingCart;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName ShoppingCartController
 * @Description 购物车管理
 * @Author zhang
 * @Date 2021/10/20 15:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车信息，如果已存在相同品类则数量自增+1
     * @param shoppingCart
     * @return 需要返回购物车数据，在前端页面回显
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        //1、获得当前用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //2、查询当前用户是否存在购物车信息
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        //select * from shopping_cart where user_id = ? and (dish_id = ? or setmeal_id = ? )
        lqw.eq(ShoppingCart::getUserId,currentId)
                .and(shoppingCartLambdaQueryWrapper -> shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId)
                        .or().eq(ShoppingCart::getSetmealId,setmealId));
        //获得该用户
        ShoppingCart one = shoppingCartService.getOne(lqw);


        //3、购物车中是否存在数据
        //存在数据 数量自增 +1
        if(one!=null){
            Integer number = one.getNumber();
            one.setNumber(number + 1);
            shoppingCartService.updateById(one);
            return R.success(one);
        }

        //4、不存在数据 新增
        //
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartService.save(shoppingCart);
        return R.success(shoppingCart);

    }

    /**
     * 减少购物车中某个菜品/套餐的数量 。如果为0直接删除
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        //1、获得当前用的id
        Long currentId = BaseContext.getCurrentId();

        //2、查看当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        //设置查询条件
        lqw.eq(ShoppingCart::getUserId,currentId)
                .and(shoppingCartLambdaQueryWrapper -> shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId)
                        .or().eq(ShoppingCart::getSetmealId,setmealId));

        //获得该用户的数据
        ShoppingCart one = shoppingCartService.getOne(lqw);
        //是否存在数据
        if(one!=null){
            Integer number = one.getNumber();
            if(number == 1){
                //删除数据
                shoppingCartService.removeById(one);
                return R.success(null);
            }else{
                one.setNumber(number - 1);
                shoppingCartService.updateById(one);
                return R.success(shoppingCart);
            }
        }else{
            throw new CustomException("购物车中无此菜品");
        }

    }

    /**
     * 获得对应用户的购物车数据并按添加时间倒序展示
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        //1、设置查询条件
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        lqw.eq(ShoppingCart::getUserId,currentId);
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        //2、查询 select * from shopping_cart where user_id = ? order by create_time
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    /**
     * 清空用户的购物车信息
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //1、设置查询条件
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        lqw.eq(ShoppingCart::getUserId,currentId);
        //2、按条件删除用户购物车信息
        shoppingCartService.remove(lqw);
        return R.success("已清空用户的购物车");
    }

}
