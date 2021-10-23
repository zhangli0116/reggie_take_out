package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Contents;
import com.example.common.R;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.SMSUtils;
import com.example.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description 用户登录
 * @Author zhang
 * @Date 2021/10/18 16:19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        //生成4位验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        //使用阿里短信SDK发送短信
        //SMSUtils.sendSMS(phone,code);
        //将验证码保存到session中
        session.setAttribute(phone,code);
        return R.success("发送短信成功,验证码为: " + code);
    }

    @PostMapping("/login")
    public R<User> loginApi(@RequestBody Map<String,String> map, HttpSession session){
        //获取登录界面的 手机号和验证码
        String phone = map.get("phone");
        String code = map.get("code");
        //进行验证码的比对
        Object sessionCode = session.getAttribute(phone);
        if(sessionCode!=null && sessionCode.equals(code)){
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone,phone);
            User user = userService.getOne(lqw);
            if(user == null){
                user = new User();
                user.setPhone(phone); //唯一不可重复
                user.setName("hm_" + phone);
                user.setStatus(1);
                //添加用户到数据库
                userService.save(user);
            }
            //
            session.setAttribute(Contents.USER_INFO,user);
            return R.success(user);

        }
        return R.error("登录失败,请重新输入验证码");
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/loginout")
    public R<String> loginout(HttpSession httpSession){
        //移除
        httpSession.removeAttribute(Contents.USER_INFO);
        return R.success("退出成功");
    }
}
