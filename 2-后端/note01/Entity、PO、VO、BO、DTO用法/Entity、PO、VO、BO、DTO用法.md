# 1. DTO（数据传输对象）

DTO是一种用于在不同层之间传递数据的对象，其主要目的是为了解耦。

在Java中，DTO通常用于表示业务逻辑层与持久层之间的数据传递。

DTO通常包含业务对象的一部分或全部属性，并提供简单的getter和setter方法。

```java
@Data
public class UserDTO {
    private String username;
    private String email;
}
```

# 2. VO（值对象）

VO是一种概念上的对象，其主要目的是用于表示领域模型中的值。

在Java中，VO通常用于封装页面展示或特定业务逻辑需要的数据。

VO通常包含与业务领域紧密相关的属性，并且可以包含一些计算得到的值。

```java
@Data
public class UserProfilevo {
    private String username;
    private String bio;
    private String avatarUrl;
}
```

在实际项目中的使用
在实际项目中，通常会使用DTO进行数据传输，而VO用于更好地表达业务概念和页面展示。以下是一个简单的控制器示例：

在这个例子中， getUserDetails方法返回用户的DTO，而getUserProfile方法返回用户的VO。这种分层的方式有助于提高代码的可维护性和灵活性。

```java
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public ResponseEntity<UserDTO> getUserDetails() {
    	UserDTO userDTO = userService.getUserDetails();
    	return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfileVO> getUserProfile() {
        UserProfileVO userProfileVO = userService.getUserProfile();
        return new ResponseEntity<>(userProfilevo, HttpStatus.OK);
    }
}
```



如果你的数据需要在不同层之间频繁传输,用于数据库的持久化或服务之间的通信,选择使用DTO。

如果你的数据主要用于在前端页面上展示,用于表达业务领域概念,选择使用VO。



## Entity、PO、VO、BO、DTO用法

DTO主要用于不同层之间的数据传输，而VO主要用于前端页面的展示。

### 一、Entity

和数据表一一对应，一个Entity对应一张表, entity里的每一个字段，与数据库相对应

### 二、PO

Persistent Object持久化对象，跟数据库导入记录数据一一对应的映射关系。
一个P0对应一个表或多个表联合查询, Entity是一个特殊的PO

### 三、VO

View Object对应页面显示的数据对象，可以和表对应，也可以不对应。控制层与视图层进行传输交换。

+ 如果需要在前端页面上显示部分数据，建议使用 VO 来封装数据。

+ VO主要用于表示业务领域中的值或对象。

### 四、BO

Business object业务对象、一个复杂的业务，往往包含多个小业务

例如，一个订单信息BO，可能包含，1.订单基础信息（购买人，时间，状态等基础信息） 2.订单支付信息 3.订单[优惠券信息 4.订单收货信息 5.订单售后信息 6.订单退款信息等。

把一个个订单信息对应一个个PO，组装到一起是BO.

### 五、DTO

DTO 是一种数据传输对象

+ 用于将数据库中的数据转换为前端需要的格式

- 用于服务之间数据传输对象，仅仅包括调用方想要的数据对象
- DTO主要用于在不同应用程序层之间传输数据，例如，在微服务架构中，DTO可能用于将数据从后端服务传递到前端应用，或者从不同的微服务传递到共享的数据层。DTO通常不包含业务逻辑，而是专注于数据传输和封装，使其更加简洁和清晰。

**DTO 和 VO 的使用场景**

1. 数据传输量大小
   如果需要传输的数据量比较大，建议使用 DTO 来封装数据。因为 DTO 可以只包含必要的字段，避免不必要的数据传输，提高程序的性能和效率。
2. [前后端数据交互](https://so.csdn.net/so/search?q=前后端数据交互&spm=1001.2101.3001.7020)
   如果需要进行前后端数据交互，建议使用 DTO 来封装数据。因为 DTO 可以将数据从数据库中查询出来，并将其转换为前端需要的格式，方便前后端之间的数据交互。
3. 数据安全
   如果需要保护一些敏感数据，建议使用 DTO 来封装数据。因为 DTO 可以封装一些敏感数据，增加数据的[安全性](https://so.csdn.net/so/search?q=安全性&spm=1001.2101.3001.7020)。
4. 前端页面显示
   如果需要在前端页面上显示部分数据，建议使用 VO 来封装数据。因为 VO 可以根据具体的需求来封装不同的数据属性，方便前端页面的显示和交互。
5. 代码复杂度和维护难度
   如果需要减少代码的复杂度和维护难度，建议使用 DTO 和 VO 来封装数据。因为 DTO 和 VO 可以将数据封装成一个独立的对象，方便代码的开发和维护。

下面是各对象应用：

![image-20240119130343054](Entity%E3%80%81PO%E3%80%81VO%E3%80%81BO%E3%80%81DTO%E7%94%A8%E6%B3%95.assets/image-20240119130343054.png)





entity包表示对数据库中所有表的映射，是根据数据库表字段设计出来的实体（要求表名与类名相同，字段名与成员变量名相同）

vo包表示前端页面传过来的如表单等数据的字段，比如当前端填写了一个表单，当前端传过来的数据较多时，我们可以创建一个`vo`实体类，将前端传来的数据字段名作为成员变量名，这样我们就可以使用`@RequestBody`注解快速获取参数内容，而不需要使用Request对象来一个个获取，方便开发。

dto包表示的是 `vo`和`entity`的一个中间转换对象，是vo或entity对象中属性的一个子对象。当前端传来`vo`数据，我们提取`vo`中的数据到`dto`中，再将`dto`的数据处理后全部移动到entity中进行数据的保存。




假如前端表单有数据 如下：

```applescript
id,username,age
```

而entity数据实体中的数据没有age：

```applescript
id,userId,username,createTime
```

这时，要创建一个`dto`类作为`vo`与`entity`的中间转换的对象(其实`dto`也可以看作在`controller`、`service`层的传输对象)，这里创建的dto如下：

```applescript
id、userId、username、createTime
```

我们要做的就是将vo对象做一定的处理后，然后创建出`dto`对象，在`controller`、`service`进行传输，最终再将service中的所有数据`copy`给`entity`，由`dao`层将`entity`数据存到[数据库](https://link.csdn.net/?target=https%3A%2F%2Fauth.huaweicloud.com%2Fauthui%2Fsaml%2Flogin%3FxAccountType%3Dcsdndev_IDP%26isFirstLogin%3Dfalse%26service%3Dhttps%3A%2F%2Factivity.huaweicloud.com%2Fdbs_Promotion%2Findex.html%3Futm_source%3Dhwc-csdn%26utm_medium%3Dshare-op%26utm_campaign%3D%26utm_content%3D%26utm_term%3D%26utm_adplace%3DAdPlace070851)中。





## 一个用于dto到entity的快速复制工具：

```java
package com.swpu.utils;


import java.util.ArrayList;
import java.util.List;


public class BeanCopyUtil {
    /**
     * 根据现有对象的属性创建目标对象，并赋值
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
    /**
     * 拷贝集合
     * @param source
     * @param target
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtil.copyObject(obj, target));
            }
        }
        return list;
    }
}
```

使用

```java
AuthenticationToken authenticationToken = authenticationService.use(username, password);

AccessTokenDTO accessTokenDTO = BeanCopyUtil.copyObject(authenticationToken, AccessTokenDTO.class);  //
```







通常DTO、VO类与Entity类有比较明显的关联，比如前者具有后者的全部属性或部分属性（和附加属性），自然而然的就会想到继承关系。

\* 大部分人认为不可以继承，这样做是不合理的。
\* 一部分人认为在某些情况下继承是可以的，比如能够保证Entity类相对稳定且DTO、VO的特性完全来自Entity。
\* 小部分人认为随意，按照自己的编程习惯即可。

我个人的结论：可以，但不推荐。

1. 这样做并没有任何语法错误；但细分为DTO、VO与Entity的目的之一就是为了解耦，在不同层使用不同的类，而继承会增加代码耦合度。
2. 若DTO、VO类具有Entity的全部属性，通过继承可以减少代码重复，提高效率；但若DTO、VO类只具有Entity的部分属性，没有必要继承，强行继承会使子类增加冗余的属性和方法。
3. 继承有副作用：修改父类，子类实现也会发生变化。Entity、DTO、VO类一般只负责记录和传递数据，其只有类内属性的Get/Set方法，不具有任何业务逻辑方法，我们通常不会对Get/Set方法做修改，此时继承的副作用较小；但如果后续数据库表中增加了字段，那么Entity类的属性与方法也要相应的增加，可能导致子类DTO、VO中出现冗余属性、方法污染。

我通常是先根据数据表写出对应的Entity类，然后在根据需求写接口时就要开始设计DTO和VO类了。

+ DTO类具有Entity类的部分属性：由于已经完成Entity类的编写，使Entity类继承DTO类时，需要将属性剪切过去再继承，如果使用了Lombok则还要再加上`@EqualsAndHashCode(callSuper = true)`和`@ToString(callSuper = true)`这两个注解。显然直接将对应的属性CV过去要快得多，且此时Entity类内有什么属性一目了然，不需要再点进父类翻找属性。
+ DTO类具有Entity类的全部属性：通过组合（与委托）的方式来完成。比如`Entity`类中有属性`name`与方法`getName()`，则可以在DTO类内组合一个Entity类型的属性`entity`，可以通过`entity.getName()`的委托方式来实现DTO类的`getName()`方法，也可以只提供`getEntity()`方法，在需要用到`Entity`类中的属性时，通过此方法链式调用获取。
+  DTO类具有Entity类的部分属性，并且具有其他属性：此时已无法通过继承关系来实现，如果强行继承必然会造成属性与方法的冗余，仍采用组合（与委托）方式即可。

虽然继承可以实现代码复用，但若仅仅是为了代码复用而强行继承，或强行抽象出父类，可能会适得其反，影响代码的可读性；应该在组合优于继承的前提下合理使用继承。总之，是否选择继承要综合公司/小组内的规范要求、个人的编程习惯和想法来说，以上观点仅供参考。





`Entity`：实体，与数据库的每一行数据打交道的，它的属性对应数据库每个字段
例如`Person`实体

```java
class Person {
	private Long idCard;
	private String name;
	private Date birthday;
	......
}
```

对应数据库的`id`，`name`，`birthday`等等字段，在CRUD中都会频繁用到



`VO(View Object)`：视图类对象，使用 VO 来封装`Entity`实体信息的部分属性，用于在前端页面上显示

```java
class UserVO {
	private String username;
	private String password;
}
```



`DTO(Data Transform Object)`：数据传输对象，用于调用接口或接口返回时传输，属性往往是`Entity`或`VO`的属性的一个子集

我们需要在前后端之间传输用户信息的全部属性，可以使用 DTO 来封装数据

由于 DTO 只包含数据属性，不包含任何业务逻辑，因此可以避免数据的重复查询和传输，提高程序的性能和效率：

例如学生有学号，姓名，性别，年龄，身高，体重等等属性

```java
// entity实体
class Student {
	private Long id;
	private String name;
	private String sex;
	private Integer age;
	private Integer height;
	private Float weight;
}
```

现在有个业务要根据身高和体重计算学生的健康指数，那么现在传一个`Entity`，除身高、体重外有许多用不上的属性，因此就用DTO，传其中两个属性就行

```java
class StudentDTO {
	private Integer height;
	private Float weight;
}
```

- mapper（dao）层：数据传输对象，一般是把数据库表封装成对象，表的各个字段就是该对象的各个变量。
- service层：相对具体的业务逻辑服务层。
- controller层：主要负责接受前台的数据和请求，并且在底层处理完之后把结果返回回去，一般不能写业务逻辑在这一层，因为第一造成了不可复用，第二以后的维护困难，第三这一层没有上层，如果给用户返回了奇怪的错误信息会不好看。

- **manager层：负责将Dao层中的数据库操作组合复用，主要是一些缓存方案，中间件的处理，以及对第三方平台封装的层。**
- **biz层： 包含service层，service层注重基础业务的处理，biz层是复杂应用层的业务层。**

## VO/PO/DTO

**错误示例**

![image-20240323225829174](Entity%E3%80%81PO%E3%80%81VO%E3%80%81BO%E3%80%81DTO%E7%94%A8%E6%B3%95.assets/image-20240323225829174.png)

**由此诞生PO、VO、BO、 DTO**

| 名称   | 详情                                                         |
| ------ | ------------------------------------------------------------ |
| Entity | 数据库实体类， 和数据表一一对应，一个Entity对应一张表，不可修改，直接从数据库查出来的对象 |
| PO     | 永久对象： Entity实体类对应着数据库的每个列，称为PO          |
| VO     | 视图类对象，使用 VO 来封装Entity实体信息的部分属性，用于和前端交互，如接收前端参数，返回结果 |
| DTO    | 数据传输对象，用于调用接口或接口返回时传输，作为service/manager层修改/查询数据库时的入参参数 |

### 例子1

#### Entity的使用

Entity的作用：对应数据库的表结构，不可修改，直接从数据库查出来的对象

```java
@Data
public class Order{
    // 订单id
    private Long id;
    // 订单号
    private String uniqueOrderNo;
    // 购买人手机
    private String phone;
}
```

```java
@Data
public class OrderItem {
    // 订单明细id
    private Long id;
    // 订单id
    private Long orderId;
    // 商品id
    private Long productId;
    // 购买数量
    private Integer quantity;
}
```

#### VO的使用

##### 新增时

```java
@Data
public class OrderAddVo {
    // 订单号
    private String uniqueOrderNo;
    // 购买人手机
    private String phone;
    // 订单明细
    private List<OrderItemAddVo> items;
}

@Data
public class OrderItemAddVo {
    // 商品id
    private Long productId;
    // 购买数量
    private Integer quantity;
}
```

保存接口

```java
 	@PostMapping(value =  "/add")
    public ResponseEnvelope<Long> add(@RequestBody OrderAddVo addVo){
    	// 保存到数据库
        Long orderId = save(addVo);
        return new ResponseEnvelope<>(orderId);
    }
```

------

##### 修改时

```java
@Data
public class OrderEditVo {
    // 订单id
    private Long id;
    // 购买人手机
    private String phone;
    // 订单明细
    private List<OrderItemEditVo> items;
    
    
}

@Data
public class OrderItemEditVo {
    // 订单明细id
    private Long id;
    // 商品id
    private Long productId;
    // 购买数量
    private Integer quantity;
}
```

修改接口

```java
   @PostMapping(value =  "/edit")
    public ResponseEnvelope<Long> edit(OrderEditVo editVo){
        Long orderId = edit(editVo);
        return new ResponseEnvelope<>(orderId);
    }
```

------

##### 查询时

查询时VO的作用：

1. 将数据库多个表的`数据组装`成一个VO返回给前端
2. `数据脱敏`，不直接返回数据库的表接口，需要的字段才在VO中指定。
3. 不返回数据库所有的字段，只返回前端需要的字段。

查询接口订单明细接口

```java
@Data
public class OrderDetailVo {
	// 订单id
	private String id;
	// 订单号
	private String uniqueOrderNo;
	// 订单明细
	private List<OrderDetailItemVo> items;
}

@Data
public class OrderDetailItemVo {
    // 订单明细id
    private Long id;
    // 订单id
    private Long orderId;
    // 商品
    private String productName;
    // 商品id
    private Long productId;
    // 购买数量
    private Integer quantity;
}
```

查询接口

```java
	@GetMapping(value ="/get")
    public ResponseEnvelope<OrderDetailVo> get(Long id){
        List<OrderDetailItemVo> itemsVos = new ArrayList<>();

        // 查询数据库订单
        Order order = getOrder(id);
        
        // 查询数据库订单明细
        List<OrderItem> items = getOrderItem(id);
        items.forEach(item -> {
            OrderDetailItemV v = new OrderDetailItemV();
            BeanUtils.copyProperties(item, v);
            // 查询商品名称
            v.setProductName(getProductName(item.getProductId));
            itemsVos.add(v);
        });
        
        OrderDetailVo detailVo = new OrderDetailVo();
        // 复制属性
        BeanUtils.copyProperties(order, detailVo);
        detailVo.setItems(itemsVos);
        return new ResponseEnvelope<>(orderId);
    }

```

个人理解这样可以不需要sql层面进行多表查询

#### DTO的使用

```
为什么要使用DTO呢？
```

DTO的作用：
对于不同数据格式但是行为一致的，提供统一的方法入口

假设我们有个需要要求同步第三方的订单过来，第三方数据格式如下 。

一个订单对应一个ThirdPartyOrder

```java
@Data
public class ThirdPartyOrder {
    private Order order;
    private List<OrderItem> items;
    private OrderPay orderPay;
}

private static class Order{
    // 订单号
    private String uniqueOrderNo;
    // 购买人手机
    private String phone;
}

private static class OrderItem{
    // 商品id
    private String productId;
    // 数量
    private Integer quantity;
}

private static class OrderPay{
    // 支付类型
    private String payType;
    // 支付流水号
    private String panNo;
}
```

第三方订单，原来的add接口的区别在于数据结构不一致，并且多出支付信息OrderPay orderPay。
这样的数据结构会导致我们没有办法使用save(OrderAddVo addVo)方法，同样的保存逻辑只是数据结构有一点点差异。
所以这个时候要用到`DTO`，定义一个save(OrderAddDto addDto)，这个方法就是通用的保存订单的方法。

1. 前端调用save(OrderAddDto addDto)方法时候需要转换实体，OrderAddVo-》OrderAddDto
2. 第三方订单调用save(OrderAddDto addDto)方法时候需要转换实体，ThirdPartyOrder -》OrderAddDto



```java
@PostMapping(value = "/add")
public ResponseEnvelope<Long> add(@RequestBody OrderAddVo addVo){
}
 
@PostMapping(value = "/addThirdParty")
public ResponseEnvelope<Long> addThirdParty(@RequestBody ThirdPartyOrder addVo){
    
    // 保存到数据库
    Long orderId = save(addTdo);
    return new ResponseEnvelope<>(orderId);
}
```

只需要在调用save(OrderAddDto addTdo)之前将要保存的不同Vo实体类都转换成-OrderAddDto

### 例子2

#### 保存

##### 定义VO、DTO、PO对象：

PO对象

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_account")
public class UserAccount implements Serializable {
    //该实体类为用户账号表对应PO对象
    @TableId
    private int SerialNum;
    private int Account;
    private String Username;
    private String Password;
    private String Roles;
    private String status;
    private String registerDate;
}
```

DTO对象

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    //该实体类为业务处理DTO对象
    private int Account;
    private String Username;
    private String Password;
    private String Roles;
}
```

VO对象

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    //该实体类为封装好的前端传输页面VO对象
    @NotBlank(message = "用户名不能为空")
    private String Username;
    @NotBlank(message = "密码不能为空")
    private String Password;
}
```

##### 控制层

```java
    //用户注册
    @PostMapping("/register")
    public CommonResult<Object> userRegister(@RequestBody @Valid UserInfo user){
        int result = userService.userRegister(user);
        if(result!=0){
            return CommonResult.success(result);
        }else {
            return CommonResult.fail(result);
        }
    }
```

##### 业务处理

```java
    @Override
    public int userRegister(UserInfo user) {
        if(accountMapper.selectCount(new QueryWrapper<UserAccount>().eq("username",user.getUsername()))!=0){
            log.info("用户名已存在");
            return 0;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDTO,user);
        //加密密码
        Md5Hash md5Hash = new Md5Hash(userDTO.getPassword(), userDTO.getUsername(),2);
        userDTO.setPassword(md5Hash.toString());
        //模拟生成账号
        userDTO.setAccount(12345678);
        //赋予权限
        userDTO.setRoles("user");
        return accountMapper.insert(userDTO);
    }
```

####  查询

##### 定义一个VO用于展示用户的信息

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    //该VO用于展示用户信息，去除了用户密码等敏感信息
    private int serialNum;
    private int account;
    private String username;
}
```

##### 控制层

```java
    @GetMapping("/get/all")
    public CommonResult<Object> getAllAccount() throws InvocationTargetException, IllegalAccessException {
        //查询所有数据
        List<UserAccount> userAccountList = userService.getAllUser();
        List<UserVo> resultList = new ArrayList<>();
        //封装为VO进行展示
        for(UserAccount u:userAccountList){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,u);
            resultList.add(userVo);
        }
        //根据序列号进行排序，后注册的放在最前面
        resultList.sort(new Comparator<UserVo>() {
            @Override
            public int compare(UserVo o1, UserVo o2) {
                return o2.getSerialNum() - o1.getSerialNum();
            }
        });
        return new CommonResult<>(200,"账号列表如下:",resultList,resultList.size());
    }
```

##### 业务层

```java
    public List<UserAccount> getAllUser() {
        return accountMapper.selectList();
    }
```

# vo

 @[Accessors](https://so.csdn.net/so/search?q=Accessors&spm=1001.2101.3001.7020)(chain = true)

作用：在VO实体类]加注解@Accessors(chain = true)之后，在service层写业务逻辑实例化vo对象的相关赋值动作就可通过链式写法来编程，简洁高效。如下例子：

####  VO实例：

```java
@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private String sex;
}
```

####  Service层调用：

```java
 
//实例化对象后，通过链式写法设置多个属性值
Person user=new User();
user.setId(1).setName("user").setSex("m");
```