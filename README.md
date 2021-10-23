day01  
* 1、登录功能  
* 2、登出功能  
* 3、验证登录功能 使用HandlerInterceptor或Filter  


day02
* 1、添加员工信息  Employee
* 2、添加全局异常处理器，用于处理添加重复的name异常数据   
* 3、分页查询员工信息  
* 4、修改员工信息  
* 5、添加消息转换器，修改long类型数据的json序列化规则  
，返回json数据时，将Long类型的数据，改为String类型，解决前端js精度问题

day03
* 1、mybatis-plus自动填充 ,处理save和update公共添加数据部分
* 2、ThreadLocal保持当前线程的值 => 获得当前的用户id
Thread -> ThreadLocalMap -> (ThreadLocal,value)

ThreadLocalMap是Thread的成员变量，ThreadLocalMap的key是ThreadLocal

* 3、菜品或套餐类型的添加/删除/修改/分页查询  Category Dish Setmeal

day04
* 1、单图片提交上传，从服务端获取图片
* 2、添加菜品信息
  * 1、菜品分类信息查询
  * 2、图片信息的上传及显示
  * 3、保存菜品和菜品口味信息（多表保存）
* 3、分页（多表查询）
  * 使用Wrapper自定义SQL + Page => 自定义Mapper
  * DTO的使用
* 4、编辑
  * 数据回显
  * 修改数据（对于菜品口味集合的修改 =》 采用先删除再插入的策略）

day05
* 1、添加套餐及套餐菜品关联信息
* 2、分页（查询套餐及套餐分类信息）
   套餐分类 -> 套餐 —> 套餐对应的菜品关系
* 3、阿里SMS服务基本使用
* 4、用户短信验证码登录验证功能

day06
* 1、显示模块
  * 1、主页左边栏的展示 http://localhost:8080/category/list
  * 2、菜品详情（一对多查询，展示菜品及菜品口味）http://localhost:8080/dish/list?categoryId=1397844263642378242&status=1 
  * 3、套餐详情 http://localhost:8080/setmeal/list?categoryId=1413386191767674881&status=1
  * 4、显示购物车信息
* 2、购物车模块
  * 1、添加购物车（新增或自增）
  * 2、清空购物车
  * 3、修改购物车 （未做）
  * 4、显示购物车信息
* 3、地址模块
  * 1、添加地址
  * 2、修改地址
  * 3、设置默认地址
  * 4、查询用户所有地址
* 4、订单模块
  * 1、显示默认地址
  * 2、显示购物车信息
  * 3、下单 （新增订单、订单明细）

day07
* 1、前后台功能完善