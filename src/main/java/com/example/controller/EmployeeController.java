package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Contents;
import com.example.common.R;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName EmployeeController
 * @Description
 * @Author zhang
 * @Date 2021/10/13 14:20
 * @Version 1.0
 **/

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 用户登录验证
     * @param employee
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public R<Employee> login(@RequestBody Employee employee , HttpSession session){

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        String passwordToMd5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //2、根据页面提交的用户名username和密码password查询数据库
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,username);
        // 因为username添加了唯一性约束，所以使用getOne
        Employee user = employeeService.getOne(lqw);

        //3、如果没有查询到则返回登录失败结果
        if(user == null){
            return R.error("登录失败");
        }
        if(!passwordToMd5.equals(user.getPassword())){
            return R.error("登录失败");
        }
        //4、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(user.getStatus() == 0){
            return R.error("账号已被禁用");
        }
        //5、登录成功，将员工id存入Session并返回登录成功结果
        session.setAttribute(Contents.EMPLOYEE_INFO,user);
        return R.success(user);
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute(Contents.EMPLOYEE_INFO);
        return R.success("移除成功");
    }

    /**
     * 添加员工信息
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee){
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
        //添加员工信息
        employeeService.save(employee);
        return R.success("保存成功");
    }

    /**
     * 分页查询数据
     * @param page 当前页
     * @param pageSize 每页条数
     * @param name 模糊匹配姓名
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //设置分页查询条件
        Page<Employee> pageInfo = new Page<>(page,pageSize);
        //设置模糊查询条件
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        // name like %name%
        lqw.like(StringUtils.isNotBlank(name),Employee::getName,name);
        //分页查询
        employeeService.page(pageInfo, lqw);
        //返回封装结果集
        return R.success(pageInfo);
    }

    /**
     * 根据员工id更新信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpSession session){
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据员工id查询所有
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> queryEmployeeById(@PathVariable("id") Long id){
        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("没有此员工信息");

    }

}
