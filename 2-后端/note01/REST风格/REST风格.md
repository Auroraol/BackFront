## 引言

**Relational State Transfer** (REST) 是一种软件架构风格。 REST 定义了 Web 应用程序架构的行为方式。 它是一种基于资源的架构，其中 REST 服务器托管的所有内容（文件、图像或数据库表中的行）都是一种资源，具有多种表示形式。

REST 推荐特定的架构约束

- 统一接口
- 无状态性
- 客户端-服务器
- 可缓存性
- 分层系统
- 点播代码

REST 约束有以下优点 

- 可扩展性
- 简单性
- 可修改性
- 可靠性
- 便携性
- 可见度

REST 使用 HTTP 动词或方法对资源进行操作。 POST、GET、PUT 和 DELETE 方法分别执行 CREATE、READ、UPDATE 和 DELETE 操作。

## 一、URL 设计:crossed_swords:

### 1.1 动词 + 谓语

RESTful 的核心思想就是，客户端发出的数据操作指令都是“动词 + 谓语”的结构。比如，`GET /articles`这个命令，`GET`是动词，`/articles`是谓语。

动词通常就是五种 HTTP 方法，对应 CRUD 操作。根据 HTTP 规范，动词一律大写。

> - GET：读取（Read）
> - POST：新建（Create）
> - PUT：更新（Update）
> - PATCH：更新（Update），通常是部分更新
> - DELETE：删除（Delete）

### 1.2 动词的覆盖

有些客户端只能使用`GET`和`POST`这两种方法。服务器必须接受`POST`模拟其他三个方法（`PUT`、`PATCH`、`DELETE`）。

这时，客户端发出的 HTTP 请求，要加上`X-HTTP-Method-Override`属性，告诉服务器应该使用哪一个动词，覆盖`POST`方法。

```
POST /api/Person/4 HTTP/1.1  
X-HTTP-Method-Override: PUT
```

上面代码中，`X-HTTP-Method-Override`指定本次请求的方法是`PUT`，而不是`POST`。

### 1.3 谓语必须是名词

谓语就是 API 的 URL，是 HTTP 动词作用的对象。它应该是名词，不能是动词。

比如，`/articles`这个 URL 就是正确的，而下面的 URL 不是名词，所以都是错误的。

> - /getAllCars
> - /createNewCar
> - /deleteAllRedCars

推荐使用 使用kebab-case（短横线小写隔开形式）

### 1.4  对URL使用kebab-case（短横线小写隔开形式)

例如，如果你想要获得订单列表

不应该：

```
/systemOrders
/system_orders
```

应该：

```
/system-orders
```

### 1.5 指向集合使用复数名称

常见的操作是读取一个集合，比如`GET /articles`（读取所有文章），这里明显应该是复数。

为了统一起见，建议都使用复数 URL，比如`GET /articles/2`要好于`GET /article/2`。

例如，如果你想获得系统的所有用户

不应该：

```
GET /user
GET /User
```

应该：

```
GET /users
```

### 1.6  参数使用camelCase（驼峰形式）

例如，如果你想从一个特定的商店购买产品。

不应该：

```
/system-orders/{order_id}
/system-orders/{OrderId}
```

应该：

```
/system-orders/{orderId}
```

### 1.7 URL以集合开始，以标识符结束

如果要保持概念的单一性和一致性。

不应该：

```
GET /shops/:shopId/category/:categoryId/price
```

应该：

```
GET /shops/:shopId/
GET /category/:categoryId
```

###  1.8 让动词远离你的资源URL:crossed_swords:

不要在URL中使用动词来表达你的意图。相反，使用适当的HTTP方法来描述操作。

不应该：

```
POST /updateuser/{userId}
GET /getusers
```

应该：

```
PUT /user/{userId}
```

### 1.9 对非资源URL使用动词

如果你有一个端点，它只返回一个操作。在这种情况下，你可以使用动词。

例如，如果你想要向用户重新发送警报。

应该：

```
POST /alarm/245743/resend
```

请记住，这些不是我们的CRUD操作。相反，它们被认为是在我们的系统中执行特定工作的函数。

### 2.0 JSON属性使用camelCase(驼峰形式)

如果你正在构建一个请求体或响应体为JSON的系统，那么属性名应该使用驼峰大小写。

不应该：

```
{
   user_name: "Mohammad Faisal"
   user_id: "1"
}
```

应该：

```
{
   userName: "Mohammad Faisal"
   userId: "1"
}
```

### 2.1 避免多级 URL:crossed_swords:

常见的情况是，资源需要多级分类，因此很容易写出多级的 URL。更好的做法是，除了第一级，其他级别都用查询字符串表达。

**例子:  获取某个作者的某一类文章**

```
GET /authors/12/categories/2
```

优化

```
GET /authors/12?categories=2
```

```java
@RestController
public class AuthorController {

    //GET /authors/12?categories=2
    @GetMapping("/authors/{authorId}")
    public String getAuthorArticles(
            @PathVariable Long authorId,
            @RequestParam(name = "categories") Long categoryId
    ) {
        return "Author ID: " + authorId + ", Category ID: " + categoryId;
    }
}
```

继续优化

```java
@RestController
@RequestMapping("/authors")
public class AuthorController {

    //GET /authors/12?categories=2
    @GetMapping("/{authorId}")
    public String getAuthorArticles(
            @PathVariable Long authorId,
            @RequestParam(name = "categories") Long categoryId
    ) {
        return "Author ID: " + authorId + ", Category ID: " + categoryId;
    }
}
```

**例子:  查询已发布的文章**

```
GET /articles/published=false
```

优化

```
GET /articles?published=false
```

```java
@RestController
public class ArticleController {

    //GET /articles?published=false
    @GetMapping("/articles")
    public String getPublishedArticles(
            @RequestParam(name = "published", required = false) boolean published
    ) {
        if (published) {
            return "Retrieving published articles";
        } else {
            return "Retrieving all articles";
        }
    }
}

```

继续优化

```java
@RestController
@RequestMapping("/articles")
public class AuthorController {

    //GET /articles?published=false
    @GetMapping("")
    public String getPublishedArticles(
            @RequestParam(name = "published", required = false) boolean published
    ) {
        if (published) {
            return "Retrieving published articles";
        } else {
            return "Retrieving all articles";
        }
    }
}
```

### 2.2 REST使用:crossed_swords:

------

#### 开发风格

是一种开发风格，遵从此风格开发软件，符合REST风格，则RESTFUL。

两个核心要求： 

- 每个资源都有唯一的标识(URL)
- 不同的行为，使用对应的http-method

**看东西(GET),放东西(POST),改东西(PUT), 丢东西(DELETE)**

一般都不直接用 put 和 delete，存在安全漏洞，常用的就是post来替代实现 put、delete 的方法。

| 请求方式 | 标识                                 | 意图                                               |
| -------- | ------------------------------------ | -------------------------------------------------- |
| GET      | http://localhost:8989/users          | <font color=red>查询所有</font>用户                |
| GET      | http://localhost:8989/users/1        | <font color=red>查询</font>用户1                   |
| GET      | http://localhost:8989/users/1/orders | <font color=red>查询</font>用户1的所有订单         |
| POST     | http://localhost:8989/users          | 在所有用户中<font color=red>增加一个</font>        |
| POST     | http://localhost:8989/users/1/orders | 在用户1的所有订单中<font color=red>增加</font>一个 |
| PUT      | http://localhost:8989/users          | 在所有用户中<font color=red>修改一个</font>        |
| DELETE   | http://localhost:8989/users/1        | <font color=red>删除</font>用户1                   |

####  使用

<img src="REST%E9%A3%8E%E6%A0%BC.assets/image-20231019223559120.png" alt="image-20231019223559120" style="zoom:67%;" />

#####  定义Rest风格的 Controller

```java
@RestController
public class RestController {
    
    // GET /users
    @GetMapping("/users")
    public List<User> queryAllUsers(){
        System.out.println("get");
        List<User> users = ....
        return users;
    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user){
        System.out.println("Post user :"+user);
        return "{status:1}";
    }
    
    @PutMapping("/users")
    public String updateUser(@RequestBody User user){
        System.out.println("Put user" user:"+user);
        return "{status:1}";
    }

    // GET /users/1                  
    @GetMapping("/users/{id}")
    public String queryOneUser(@PathVariable Integer id){//@PathVariable 接收路径中的值
        System.out.println("Get user id:"+id);
        return "{status:1}";
    }

    @DeleteMapping("/users/{id}")
    public String deleteOneUser(@PathVariable Integer id){//@PathVariable 接收路径中的值
        System.out.println("delete user id:"+id);
        return "{status:1}";
    }
                           
    // GET /users/1/orders
    @GetMapping("/{userId}/orders")
    public String getUserOrders(@PathVariable Long userId) {
        return "Orders for user " + userId;
    }
}
```

#####  Ajax请求

```html
<script>    
	function putUser(){ // 发送更新请求 （增加请求发送方式也是如此）
        var xhr = new XMLHttpRequest();
    	//定义 put，delete,get,post方式 即可，不用定义_method
        xhr.open("put","${pageContext.request.contextPath}/users");
    	// 设置请求头
        xhr.setRequestHeader("content-type","application/json")；
        // 设置请求参数
        var user = {id:1，NAME:"shine"，city:"bj"，"birth":"2020/12/12"，"salary":100.5};
        xhr.send(JSON.stringify(user));
        xhr.onreadystatechange=function(){
            if(xhr.readyState==4 && xhr.status==200){
                var ret = xhr.responseText;
                // 解析json，并输出
                console.log(JSON.parse(ret));
            }
        }
    	/*$.ajax({
            url:'${pageContext.request.contextPath}/rest04/users',
            type:'put',
            contentType:"application/json",//声明请求参数类型为 json
            data:JSON.stringify(user),// 转换js对象成json
            success:function(ret){
                console.log(JSON.parse(ret));
            }
        });*/
    }

	function delUser(){  // 发送删除请求
        var xhr = new XMLHttpRequest();
        //定义 put，delete,get,post方式 即可，不用定义_method
        xhr.open("delete","${pageContext.request.contextPath}/rest04/users/1");
        xhr.send();
        xhr.onreadystatechange=function(){
            if(xhr.readyState==4 && xhr.status==200){
                var ret = xhr.responseText;
                console.log(JSON.parse(ret));
            }
        }
    }
</script>
```

## 二、状态码

### 2.1 状态码必须精确

客户端的每一次请求，服务器都必须给出回应。回应包括状态码和数据两部分。

状态码就是一个三位数，分成五个类别。

> - `1xx`：相息
> - `2xx`：操作成功
> - `3xx`：重定向
> - `4xx`：客户端错误
> - `5xx`：服务器错误

这五大类总共包含[100多种](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)状态码，覆盖了绝大部分可能遇到的情况。每一种状态码都有标准的（或者约定的）解释，客户端只需查看状态码，就可以判断出发生了什么情况，所以服务器应该返回尽可能精确的状态码。

API 不需要`1xx`状态码，下面介绍其他四类状态码的精确含义。

### 2.2 2xx 状态码

`200`状态码表示操作成功，但是不同的方法可以返回更精确的状态码。

> - GET: 200 OK
> - POST: 201 Created
> - PUT: 200 OK
> - PATCH: 200 OK
> - DELETE: 204 No Content

上面代码中，`POST`返回`201`状态码，表示生成了新的资源；`DELETE`返回`204`状态码，表示资源已经不存在。

此外，`202 Accepted`状态码表示服务器已经收到请求，但还未进行处理，会在未来再处理，通常用于异步操作。下面是一个例子。

```json
HTTP/1.1 202 Accepted

{
  "task": {
    "href": "/api/company/job-management/jobs/2130040",
    "id": "2130040"
  }
}
```

### 2.3 3xx 状态码

API 用不到`301`状态码（永久重定向）和`302`状态码（暂时重定向，`307`也是这个含义），因为它们可以由应用级别返回，浏览器会直接跳转，API 级别可以不考虑这两种情况。

API 用到的`3xx`状态码，主要是`303 See Other`，表示参考另一个 URL。它与`302`和`307`的含义一样，也是“暂时重定向”，区别在于`302`和`307`用于`GET`请求，而`303`用于`POST`、`PUT`和`DELETE`请求。收到`303`以后，浏览器不会自动跳转，而会让用户自己决定下一步怎么办。下面是一个例子。

```json
HTTP/1.1 303 See Other
Location: /api/orders/12345
```

### 2.4 4xx 状态码

`4xx`状态码表示客户端错误

主要有下面几种:

+ `400 Bad Request`：服务器不理解客户端的请求，未做任何处理。

+ `401 Unauthorized`：用户未提供身份验证凭据，或者没有通过身份验证。

+ `403 Forbidden`：用户通过了身份验证，但是不具有访问资源所需的权限。

+ `404 Not Found`：所请求的资源不存在，或不可用。

+ `405 Method Not Allowed`：用户已经通过身份验证，但是所用的 HTTP 方法不在他的权限之内。

+ `410 Gone`：所请求的资源已从这个地址转移，不再可用。

+ `415 Unsupported Media Type`：客户端要求的返回格式不支持。比如，API 只能返回 JSON 格式，但是客户端要求返回 XML 格式。

+ `422 Unprocessable Entity` ：客户端上传的附件无法处理，导致请求失败。

+ `429 Too Many Requests`：客户端的请求次数超过限额。

### 2.5 5xx 状态码

`5xx`状态码表示服务端错误

一般来说，API 不会向用户透露服务器的详细信息，所以只要两个状态码就够了:

+ `500 Internal Server Error`：客户端请求有效，服务器处理时发生了意外。

+ `503 Service Unavailable`：服务器无法处理请求，一般用于网站维护状态。

## 三、服务器回应

### 3.1 不要返回纯本文

API 返回的数据格式，不应该是纯文本，而应该是一个 JSON 对象，因为这样才能返回标准的结构化数据。所以，服务器回应的 HTTP 头的`Content-Type`属性要设为`application/json`。

客户端请求时，也要明确告诉服务器，可以接受 JSON 格式，即请求的 HTTP 头的`ACCEPT`属性也要设成`application/json`。

```json
GET /orders/2 HTTP/1.1 
Accept: application/json
```

### 3.2 发生错误时，不要返回 200 状态码

有一种不恰当的做法是，即使发生错误，也返回`200`状态码，把错误信息放在数据体里面。

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "status": "failure",
  "data": {
    "error": "Expected at least two items in list."
  }
}
```

上面代码中，解析数据体以后，才能得知操作失败。

这做法实际上取消了状态码，这是完全不可取的。正确的做法是，状态码反映发生的错误，具体的错误信息放在数据体里面返回。

```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "error": "Invalid payoad.",
  "detail": {
     "surname": "This field is required."
  }
}
```

### 3.3 提供链接

API 的使用者未必知道，URL 是怎么设计的。一个解决方法就是，在回应中，给出相关链接，便于下一步操作。这样的话，用户只要记住一个 URL，就可以发现其他的 URL。这种方法叫做 HATEOAS。

举例来说，GitHub 的 API 都在 api.github.com 这个域名。访问它，就可以得到其他 URL。

```json
{
  ...
  "feeds_url": "https://api.github.com/feeds",
  "followers_url": "https://api.github.com/user/followers",
  "following_url": "https://api.github.com/user/following{/target}",
  "gists_url": "https://api.github.com/gists{/gist_id}",
  "hub_url": "https://api.github.com/hub",
  ...
}
```

上面的回应中，挑一个 URL 访问，又可以得到别的 URL。对于用户来说，不需要记住 URL 设计，只要从 api.github.com 一步步查找就可以了。

![image-20240102165820030](REST%E9%A3%8E%E6%A0%BC.assets/image-20240102165820030.png)

HATEOAS 的格式没有统一规定，上面例子中，GitHub 将它们与其他属性放在一起。更好的做法应该是，将相关链接与其他属性分开。

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "status": "In progress",
   "links": {[
    { "rel":"cancel", "method": "delete", "href":"/api/status/12345" } ,
    { "rel":"edit", "method": "put", "href":"/api/status/12345" }
  ]}
}
```

## 四、参考链接

[RESTful API  (hellowac.github.io)](https://hellowac.github.io/fastapi-best-practices-zh-cn/http/restapibsetp/#四参考链接)

# 五、 使用API设计工具

有许多好的API设计工具用于编写好的文档，例如：

- **API蓝图**：https://apiblueprint.org/
- **Swagger**：https://swagger.io/

![图片](REST%E9%A3%8E%E6%A0%BC.assets/640.png)

拥有良好而详细的文档可以为API使用者带来良好的用户体验。

# 六、URL设计补充

##  使用简单序数作为版本

始终对API使用版本控制，并将其向左移动，使其具有最大的作用域。版本号应该是v1，v2等等。

应该：http://api.domain.com/v1/shops/3/products

始终在API中使用版本控制，因为如果API被外部实体使用，更改端点可能会破坏它们的功能。

## 在你的响应体中包括总资源数

如果API返回一个对象列表，则响应中总是包含资源的总数。你可以为此使用total属性。

不应该：

```
{
  users: [ 
     ...
  ]
}
```

应该：

```
{
  users: [ 
     ...
  ],
  total: 34
}
```

## 接受limit和offset参数

在GET操作中始终接受limit和offset参数。

应该：

```
GET /shops?offset=5&limit=5
```

这是因为它对于前端的分页是必要的。

## 获取字段查询参数

返回的数据量也应该考虑在内。添加一个fields参数，只公开API中必需的字段。

例子：

只返回商店的名称，地址和联系方式。

```
GET /shops?fields=id,name,address,contact
```

在某些情况下，它还有助于减少响应大小。

## 不要在URL中通过认证令牌

这是一种非常糟糕的做法，因为url经常被记录，而身份验证令牌也会被不必要地记录。

不应该：

```
GET /shops/123?token=some_kind_of_authenticaiton_token
```

相反，通过头部传递它们：

```
Authorization: Bearer xxxxxx, Extra yyyyy
```

此外，授权令牌应该是短暂有效期的。

## 验证内容类型

服务器不应该假定内容类型。

例如，如果你接受**application/x-www-form-urlencoded**，那么攻击者可以创建一个表单并触发一个简单的POST请求。

因此，始终验证内容类型，如果你想使用默认的内容类型，请使用：

```
content-type: application/json
```

## 对CRUD函数使用HTTP方法

HTTP方法用于解释CRUD功能。

**GET**：检索资源的表示形式。看

**POST**：创建新的资源和子资源。放

**PUT**：更新现有资源。改

**PATCH**：更新现有资源，它只更新提供的字段，而不更新其他字段。 更

**DELETE**：删除已存在的资源。删

##  在嵌套资源的URL中使用关系

以下是一些实际例子：

- **GET /shops/2/products**：从shop 2获取所有产品的列表。
- **GET /shops/2/products/31**：获取产品31的详细信息，产品31属于shop 2。
- **DELETE /shops/2/products/31**：应该删除产品31，它属于商店2。
- **PUT /shops/2/products/31**：应该更新产品31的信息，只在resource-URL上使用PUT，而不是集合。
- **POST /shops**：应该创建一个新的商店，并返回创建的新商店的详细信息。在集合url上使用POST。

# 七、黄金法则

如果您对API格式的决定有疑问，这些黄金规则可以帮助我们做出正确的决定。

- 扁平比嵌套好。
- 简单胜于复杂。
- 字符串比数字好。
- 一致性比定制更好。
