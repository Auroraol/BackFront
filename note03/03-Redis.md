

# 一、引言

## 1.1 数据库压力过大

由于用户量增大，请求数量也随之增大，数据压力过大

## 1.2 数据不同步

多台服务器之间，数据不同步

## 1.3 传统锁失效

多台服务器之间的锁，已经不存在互斥性了。

# 二、Redis介绍

## 2.1 NoSQL介绍

- Redis就是一款NoSQL。
- NoSQL -> 非关系型数据库 -> Not Only SQL。
- Key-Value：Redis
- 文档型：ElasticSearch，Solr，Mongodb。 大数据  处理数据分析
- 面向列：Hbase，Cassandra图形化：Neo4j
- 除了关系型数据库都是非关系型数据库。mysql  oracle  sysbase db2 postgrel  sqlserver    sql操作
- NoSQL只是一种概念，泛指非关系型数据库，和关系型数据库做一个区分。

## 2.2 Redis介绍

+ Redis是key-value数据库（NoSQL一种),  mysql是关系数据库
+ Redis数据操作主要在内存, 而mysql主要存储在磁盘
+ Redis在某一些场景使用中要明显优于mysql, 比如计数器、排行榜等方面 

- 有一位意大利人，在开发一款LLOOGG的统计页面（大屏），因为MySQL的性能不好，自己研发了一款非关系型数据库，并命名为Redis。Salvatore。
- Redis（Remote Dictionary Server）即远程字典服务，Redis是由C语言去编写，Redis是一款基于Key-Value的NoSQL，而且Redis是基于内存存储数据的，Redis还提供了多种持久化机制，性能可以达到110000/s读取数据以及81000/s写入数据，Redis还提供了主从，哨兵以及集群的搭建方式，可以更方便的横向扩展以及垂直扩展。

## 2.3 主流功能与应用

### 2.3.1 分布式缓存

<img src="03-Redis.assets/2.Redis获取数据简图.jpg" style="zoom:67%;" />

**与传统数据库关系(MySQL)**

+ Redis是key-value数据库(NoSQL一种)，MySQL是关系型数据库
+ Redis数据操作主要在内存，而MySQL主要存储在磁盘
+ Redis在某一些场景使用中要明显优于MySQL，比如计数器、排行榜等方面
+ Redis通常用于一些特定场景，需要与MySQL一起配合使用
+ 两者并不是相互替换和竞争的关系，而是共用和配合使用

### 2.3.2 内存存储和持久化（RDB和AOF）

Redis支持异步将内存中的数据写到硬盘上，同时不影响继续服务

### 2.3.3  高可用架构搭配

单机、主从、哨兵、集群

### 2.3.4 缓存穿透、击穿、雪崩

### 2.3.5 分布式锁

### 2.3.6 队列

Redis提供list和Set操作，这使得Redis能作为一个很好的消息队列平台来使用。我们常通过Redis的队列功能做购买限制。比如到了节假日或者推广期间，进行一些活动，对用户购买行为进行限制，限制今天只能购买几次商品或者一段时间内只能购买一次。也比较适合使用。

### 2.3.7 排行榜+点赞

在互联网应用中，有各种各样的排行榜，如电商网站的月度销量排行榜、社交APP的礼物排行榜、小程序的投票排行榜等等。Redis提供的zset数据类型能够快速实现这些复杂的排行榜。	比如小说网站对小说进行排名，根据排名，将排名靠前的小说推荐给用户。

## 2.4 总体功能概述

![image-20231007143801928](03-Redis.assets/image-20231007143801928.png)

 **优势**

+ 性能极高-Redis读的速度是110000次/秒，写的速度是81000次/秒
+ Redis数据类型丰富，不仅仅支持简单的Key-Value类型的数据，同时还提供list，set，zset，hash等数据结构的存储
+ Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用
+ Redis支持数据的备份，即master-slave模式的数据备份

## 2.5 总结

![](03-Redis.assets/4.小总结.jpg)







# 三、Redis安装

**官网地址:** 

英文：https://redis.io/

中文：http://www.redis.cn/	https://www.redis.com.cn/documentation.html

安装包：https://redis.io/download/，选择redis7.0版本即可

Redis源码地址：https://github.com/redis/redis

Redis在线测试地址(不用下载也能玩)：https://try.redis.io/

Redis命令参考：http://doc.redisfans.com/

**Redis迭代演化和Redis7新特性浅谈:**

<img src="03-Redis.assets/image-20231007143944910.png" alt="image-20231007143944910" style="zoom:67%;" />



```xml
5.0版本是直接升级到6.0版本，对于这个激进的升级，Redis之父antirez表现得很有信心和兴奋，所以第一时间发文来阐述6.0的一些重大功能"Redis 6.0.0 GA is out!":
```

$\textcolor{red}{随后Redis再接再厉，直接王炸Redis7.0---2023年爆款}$

```xml
2022年4月27日Redis正式发布了7.0更新
(其实早在2022年1月31日，Redis已经预发布了7.0rc-1，近过社区的考验后，确认没有重大Bug才会正式发布)
```

Redis版本迭代推演介绍：

​	几个里程碑式的重要版本:如上图：redis历史版本回顾

​	命名规则：Redis从发布至今，已经有十余年的时光了，一直遵循着自己的命名规则:

1. 版本号第二位如果是奇数，则为非稳定版本，如2.7、2.9、3.1
2. $\textcolor{red}{版本号第二位如果是偶数，则为稳定版，如2.6、2.8、3.2}$
3. 当前奇数版本就是下一个稳定版本的开发版，如2.9版本就是3.0版本的开发版本
4. 我们可以通过redis.io官网来下载自己感兴趣的版本进行源码阅读
5. 历史发布版本的源码：https://download.redis.io/releases/

**总体概述**

大体和之前的redis版本保持一致和稳定，主要是自身底层性能和**资源利用率上的优化和提高**，如果生产上系统稳定，**不用着急升级到最新redis7版本**，如果从零开始新系统，直接上redis7.0-GA版。

|                                    |                                                              |
| ---------------------------------- | ------------------------------------------------------------ |
| 多AOF文件支持                      | 7.0 版本中一个比较大的变化就是 aof 文件由一个变成了多个，主要分为两种类型:基本文件(base files)、增量文件(incr files)，请注意这些文件名称是复数形式说明每一类文件不仅仅只有一个。在此之外还引入了一个清单文件(manifest) 用于跟踪文件以及文件的创建和应用顺序(恢复) |
| config命令增强                     | 对于Config Set 和Get命令，支持在一次调用过程中传递多个配置参数。例如，现在我们可以在执行一次Config Set命今中更改多个参数: config set maxmemory 10000001 maxmemory-clients 50% port 6399 |
| 限制客户端内存使用<Client-eviction | 一旦 Redis 连接较多，再加上每个连接的内存占用都比较大的时候， Redis总连接内存占用可能会达到maxmemory的上限，可以增加允许限制所有客户端的总内存使用量配置项，redis.config 中对应的配置项<br />//两种配置形式:指定内存大小、基于 maxmemory 的百分比。<br />maxmemory-client 1g<br />maxmemory-client 10% |
| listpack紧凑列表调整               | listpack 是用来替代 ziplist 的新数据结构，在 7.0 版本已经没有 ziplist 的配置了 (6.0版本仅部分数据类型作为过渡阶段在使用）listpack已经替换了ziplist类似hash-max-ziplist-entries 的配置 |
| 访问安全性增强ACLV2                | 在redis.conf配置文件中protected-mode默认为yes，只有当你希望你的客户端在没有授权的情况下可以连接到Redis server的时候可以将protect-mode设置为no |
| redis function                     | Redis函数，一种新的通过服务端脚本扩展Redis的方式，函数与数据本身一起存储。简言之，redis自己要去抢夺Lua脚本的饭碗 |
| RDB保存时间调整                    | 将持久化文件RDB的保存规则发生了改变，尤其是时间记录频度变化  |
| 命令新增和变动                     | Zset (有序集合)增加 ZMPOP、BZMPOP、ZINTERCARD 等命令<br />Set (集合)增加 SINTERCARD 命令<br />LIST(列表)增加 LMPOP、BLMPOP ，从提供的键名列表中的第一个非空列表键中弹出一个或多个元素。 |
| 性能资源利用率、安全等改进         | 自身底层部分优化改动，Redis核心在许多方面进行了重构和改进主动碎片整理V2:增强版主动碎片整理，配合Jemalloc版本更新，更快更智能，延时更低<br />HyperLogLog改进:在Redis5.0中，HyperLogLog算法得到改进，优化了计数统计时的内存使用效率，7更加优秀更好的内存统计报告<br />如果不是为了API向后兼容，我们将不再使用slave一词......（政治正确） |

## 3.1 安装Redis7

### window安装

一般不采用, 下载地址：https://github.com/zkteco-home/redis-windows

### linux安装

#### 安装

安装redis之前需要具备c++环境

<img src="03-Redis.assets/image-20231007144522525.png" alt="image-20231007144522525" style="zoom: 60%;" />

**1.下载获取redis-7.0.0.tar.gz后将它放入的Linux目录/opt**

![image-20231007151306050](03-Redis.assets/image-20231007151306050.png)

2./opt目录下解压redis，tar -zxvf redis-7.0.0.tar.gz

3.进入目录，cd redis-7.0.0

4.在redis-7.0.0目录下执行make命令

![](03-Redis.assets/2.make命令.png)

5.查看默认安装目录：/usr/local/bin，Linux下的/usr/local类似我们Windows系统的C:\Program Files，安装完成后，去/usr/local/bin下查看

```
redis-benchmark:性能测试工具，服务启动后运行该命令，看看自己电脑性能如何
redis-check-aof:修复有问题的AOF文件，RDB和AOF后续学习
redis-check-dump:修复有问题的dump.rdb文件
```

#### 配置

![image-20231007152724071](03-Redis.assets/image-20231007152724071.png)

<font color=red>不改原版配置</font>

![image-20231007153023103](03-Redis.assets/image-20231007153023103.png)

**修改配置**

![image-20231007153225512](03-Redis.assets/image-20231007153225512.png)

![image-20231007152807672](03-Redis.assets/image-20231007152807672.png)

<img src="03-Redis.assets/image-20231007154157049.png" alt="image-20231007154157049" style="zoom: 67%;" />

#### 启动Redis

```shell
redis-server /myredis/redis.conf 
```

![image-20231007154355732](03-Redis.assets/image-20231007154355732.png)

注意:   /myredis/redis.conf 是自己写的配置文件,一般不修改源配置文件

#### 使用redis-cli连接Redis

```
redis-cli -a 741106 -p 6379
```

![image-20231007154912636](03-Redis.assets/image-20231007154912636.png)

注意:  

+ 741106表示密码
+  6379表示端口号, 默认就是6379

#### 关闭&退出

```shell
单实例关闭: redis-cli -a 密码 shutdown
多实例关闭(指定端口关闭):redis-cli -p 6379 shutdown
```

```
退出: quit
```

**中文支持**

```
redis-cli -a 741106 -p 6379 --raw
```

### Docke安装

####  安装

##### 1、Docker Hub中Redis稳定的版本

**Docker Hub官方网址：
https://hub.docker.com/**

![image-20231007145720897](03-Redis.assets/image-20231007145720897.png)

##### 2、创建两个目录，挂载为容器的卷

conf为配置文件目录，data为数据目录

```shell
mkdir -p /mydata/redis/conf
mkdir -p /mydata/redis/data
```

##### 3、创建并运行容器

```shell
docker run --name redis -p 6379:6379 -v /mydata/redis/conf:/usr/local/etc/redis -v /mydata/redis/data:/data -d redis redis-server /usr/local/etc/redis/redis.conf
```

##### 4、运行 redis-cli 测试连接 redis-server

如图，说明Redis容器配置正确

```shell
docker exec -it 13c redis-cli
```

![image-20231007150447719](03-Redis.assets/image-20231007150447719.png)

或者使用 Docker-Compose安装

```yaml
version: '3.1' 
services:
  redis:
    image: daocloud.io/library/redis:7.0.0
    restart: always
    container_name: redis
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 6379:6379
```

### 使用redis-cli连接Redis

进去Redis容器的内部

docker exec -it 容器id bash

在容器内部，使用redis-cli连接

![image-20231007155120089](03-Redis.assets/image-20231007155120089.png)

## 3.3 使用图形化界面连接Redis

下载地址：https://github.com/lework/RedisDesktopManager-Windows/releases/download/2019.5/redis-desktop-manager-2019.5.zip傻瓜式安装

<img src="03-Redis.assets/1637478537209-d063f916-27be-4d52-89f0-e81c7141b6a4.png" alt="img" style="zoom:67%;" />

##  3.4 卸载Redis步骤

**停止redis-server服务**

<img src="03-Redis.assets/5.停止redis-server服务.png" style="zoom:67%;" />

**删除/usr/local/bin目录下与redis相关的文件**

ls -l /usr/local/bin/redis-*

rm -rf /usr/local/bin/redis-*

<img src="03-Redis.assets/6.删除redis文件.png" style="zoom:67%;" />



# 四、Redis常用命令【重点】

## 4.1 Redis存储数据的结构

**这里说的数据类型是value的数据类型，key的类型都是字符串**

<img src="03-Redis.assets/2.10大数据类型图示.png" style="zoom:67%;" />

### 十大数据类型

#### 1.redis字符串（String）

String是redis最基本的数据类型，一个key对应一个value。

string类型是<font color='red'>二进制安全的</font>，意思是redis的string可以包含任何数据，比如jpg图片或者序列化的对象。

string类型是Redis最基本的数据类型，一个redis中字符串value**最多可以是512M**

#### 2.redis列表（List）

Redis列表是最简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的$\textcolor{blue}{头部（左边）或者尾部（右边）}$，它的底层实际是个$\textcolor{red}{双端链表}$，最多可以包含2^32-1个元素（4294967295，每个列表超过40亿个元素）

#### 3.redis哈希表（Hash）

Redis Hash是一个string类型的field（字段）和value（值）的映射表，Hash特别适合用户存储对象。

Redis中每个Hash可以存储2^32-1个键值对（40多亿）

#### 4.redis集合（Set）

Redis的Set是string类型的$\textcolor{red}{无序集合}$。集合成员是唯一的，这就意味着集合中不能出现重复的数据，集合对象的编码可以是intset或者Hashtable。

Redis中Set集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

集合中最大的成员数为2^32-1（4294967295，每个集合可存储40多亿个成员）

#### 5.redis有序集合（ZSet）

zset(sorted set：有序集合)

Redis zset和Set一样也是string类型元素的集合，且不允许重复的成员。

$\textcolor{red}{不同的是每个元素都会关联一个double类型的分数}$，redis正是通过分数来为集合中的成员进行从小到大的排序。

$\textcolor{red}{zset的成员是唯一的，但是分数（score）却可以重复。}$

$\textcolor{red}{zset集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。集合中最大的成员数是2^.32-1}$

#### 6.redis地理空间（GEO）

Redis GEO主要用于存储地理位置信息，并对存储的信息进行操作，包括：

添加地理位置的坐标。

获取地理位置的坐标。

计算两个位置之间的距离。

根据用户给定的经纬度坐标来获取指定范围内的地址位置集合。

#### 7.redis基数统计（HyperLogLog）

HyperLogLog是用来做$\textcolor{red}{基数统计}$的算法，HyperLogLog的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需要的空间总是固定且是很小的。

在Redis里面，每个HyperLogLog键只需要花费12KB内存，就可以计算接近2^64个不同元素的基数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。

但是，因为HyperLogLog只会根据输入元素来计算基数，而不会存储输入元素本身，所以HyperLogLog不能像集合那样，返回输入的各个元素。

#### 8.redis位图（bitmap）

![](03-Redis.assets/3.redis位图.jpg)

由0和1状态表现的二进制位的bit数组

#### 9.redis位域（bitfield）

通过bitfield命令可以一次性操作多个$\textcolor{red}{比特位域(指的是连续的多个比特位)}$，它会执行一系列操作并返回一个响应数组，这个数组中的元素对应参数列表中的相应的执行结果。

说白了就是通过bitfield命令我们可以一次性对多个比特位域进行操作。

#### 10.redis流（Stream）

Redis Stream是Redis5.0版本新增加的数据结构。

Redis Stream主要用于消息队列（MQ，Message Queue），Redis本身就是一个Redis发布订阅（pub/sub）来实现消息队列的功能，但它有个缺点就是消息无法持久化，如果出现网络断开、Redis宕机等，消息就会被丢弃。

简单来说发布订阅（pub/sub）可以分发消息，但无法记录历史消息。

而Redis Stream提供了消息的持久化和主备复制功能，可以让任何客户端访问任何时刻的数据，并且能记住每一个客户端的访问位置，还能保证消息不丢失。

### 总结

<font color= red>redis常见数据类型操作命令</font>

官网英文： https://redis.io/commands/

中文：http://www.redis.cn/commands.html

<font color=blue>命令不区分大小写，而key是区分大小写的</font>

**常用的5种数据结构：**

- key-string：一个key对应一个值。最常用的，一般用于存储一个值。
- key-hash：一个key对应一个Map。存储一个对象数据的。
- key-list：一个key对应一个列表 数组。使用list结构实现栈和队列结构。  分布式锁   红锁
- key-set：一个key对应一个集合。无序集合  不重复。交集，差集和并集的操作。
- key-zset：一个key对应一个有序的集合。不重复  给一个分值。排行榜，积分存储等操作。

**另外三种数据结构：**

- HyperLogLog：计算近似值的。
- GEO：地理位置 x   y。
- BIT：一般存储的也是一个字符串，存储的是一个byte[]。

![img](03-Redis.assets/1637478589651-428c3a1f-29f3-43a6-a03b-a355ec513426.png)

## 4.2 string常用命令

### 总结

![img](03-Redis.assets/1638157441094-2b79ff16-13d4-4e1e-8156-baa9ec55e96d.png)

```bash
#1.  添加值
set key value （*）

#2. 取值
get key （*）

#3. 批量操作
mset key value [key value...] （*）
mget key [key...] （*）

#4. 自增命令（自增1）
incr key   key必须是integer

#5. 自减命令（自减1）
decr key

#6. 自增或自减指定数量  key必须是integer
incrby key increment
decrby key increment

#7. 设置值的同时，指定生存时间（每次向Redis中添加数据时，尽量都设置上生存时间，秒）重点
setex key second value （**）

#8. 设置值，如果当前key不存在的话（如果这个key存在，什么事都不做，如果这个key不存在，和set命令一样）重点
setnx key value （**）

#9. 在key对应的value后，追加内容
append key value

#10. 查看value字符串的长度
strlen key
```

### 详细

#### 1.set key value

![](03-Redis.assets/14.string参数.jpg)

**返回值：**

设置成功则返回OK，返回nil为未执行Set命令，如不满足NX，XX条件等。

若使用GET参数，则返回该键原来的值，或在键不存在时nil。

<img src="03-Redis.assets/image-20231007212111632.png" alt="image-20231007212111632" style="zoom:80%;" />

<img src="03-Redis.assets/image-20231007212056965.png" alt="image-20231007212056965" style="zoom: 80%;" />

<img src="03-Redis.assets/image-20231007211934531.png" alt="image-20231007211934531" style="zoom:81%;" />

如何获得设置指定的key过期的Unix时间，单位为秒

```java
System.out.println(Long.toString(System.currentTimeMillis()/1000L));
```

<img src="03-Redis.assets/image-20231007212414116.png" alt="image-20231007212414116" style="zoom:80%;" />

<img src="03-Redis.assets/image-20231007212404475.png" alt="image-20231007212404475" style="zoom:80%;" />

#### 2.同时设置/获取多个键值

mset/mget/msetnx

```shell
mset key value [key value...]
mget key [key ...]
msetnx key value [key value...]
```

![](03-Redis.assets/17.string多值操作.jpg)

#### 3.获取指定区间范围内的值

getrange/setrange

![image-20231007213649332](03-Redis.assets/image-20231007213649332.png)

#### 4.数值增减

$\textcolor{red}{一定要是数据才能进行加减}$

递增数字：INCR key

增加指定的整数：INCRBY key increment

递减数值：DECR key

减少指定的整数：DECRBY key decrement

![](03-Redis.assets/19.string类型自增自减.jpg)

#### 5.获取字符串长度和内容追加

获取字符串长度：strlen key

字符串内容追加：append key value

![](03-Redis.assets/20字符串长度获取和内容追加.jpg)

#### 6.分布式锁

setnx key value

setex(set with expire) 键秒值/setnx(set if not exist)

![](03-Redis.assets/21.分布式锁.jpg)

#### 7.getset(先get再set)

getset：先get然后立即set

![image-20231007214820009](03-Redis.assets/image-20231007214820009.png)

<img src="03-Redis.assets/image-20231007214806686.png" alt="image-20231007214806686" style="zoom:93%;" />

### 应用

![image-20231007223258344](03-Redis.assets/image-20231007223258344.png)

![image-20231007223307998](03-Redis.assets/image-20231007223307998.png)

## 4.3 hash常用命令

### 总结

![img](03-Redis.assets/1638175403658-7a18957a-2aa7-421f-9628-83115e892f46.png)

![img](03-Redis.assets/1638175305264-83c53134-9d7a-4039-8a54-08f2ab540336.png)

```bash
#1. 存储数据
hset key field value    gg:{id:val,id:val}（*）

#2. 获取数据
hget key field （*）

#3. 批量操作
hmset key field value [field value ...] （*）
hmget key field [field ...] （*）

#4. 自增（指定自增的值）
hincrby key field increment

#5. 设置值（如果key-field不存在，那么就正常添加，如果存在，什么事都不做）
hsetnx key field value （*）

#6. 检查field是否存在
hexists key field 

#7. 删除key对应的field，可以删除多个
hdel key field [field ...] （*）

#8. 获取当前hash结构中的全部field和value
hgetall key （*）

#9. 获取当前hash结构中的全部field（*）
hkeys key

#10. 获取当前hash结构中的全部value（*）
hvals key

#11. 获取当前hash结构中field的数量（*）
hlen key
```

### 详情

KV模式不变，但V是一个键值对  Map<String, Map<Object, Object>>

#### 1.hset/hget/hmset/hmget/hgetall/hdel

<img src="03-Redis.assets/image-20231007223917084.png" alt="image-20231007223917084" style="zoom:80%;" />

#### 2.hlen

获取某个key内的全部数量

![](03-Redis.assets/35.Hash-hlen.jpg)

#### 3.hexists key 在key里面的某个值的key

![](03-Redis.assets/36.hash-hexists.jpg)

#### 4.hkeys/hvals

hkeys key 查询出所有key对应的子key值

hvals key 查询出所有key对应的子key的value值

![](03-Redis.assets/37.hash-hkeys.jpg)

#### 5.hincrby/hincrbyfloat

![image-20231007224101420](03-Redis.assets/image-20231007224101420.png)

#### 6.hsetnx

不存在赋值，存在了无效

![](03-Redis.assets/39.hash-hsetnx.jpg)

### 应用

![image-20231007223110903](03-Redis.assets/image-20231007223110903.png)

## 4.4 list常用命令

### 总结

![img](03-Redis.assets/1638178486834-fbe6e361-905d-4886-98b4-7042e982dc22.png)

```bash
#1. 存储数据（从左侧插入数据，从右侧插入数据）
lpush key value [value ...] （*）
rpush key value [value ...] （*）

#2. 存储数据（） 像数组的左边或者右边添加值
lpushx key value （*）
rpushx key value （*）

#3. 修改数据（在存储数据时，指定好你的索引位置,覆盖之前索引位置的数据，index超出整个列表的长度，也会失败）
lset key index value （*）

#4. 弹栈方式获取数据（左侧弹出数据，从右侧弹出数据） 删除
lpop key（*）
rpop key（*）

#5. 获取指定索引范围的数据（start从0开始，stop输入-1，代表最后一个，-2代表倒数第二个）
lrange key start stop（*）

#6. 获取指定索引位置的数据  -1  到取值 arr[3]
lindex key index  跟正整数就从左向右取 跟负数就是从右向左取  （*） get(i)

#7. 获取整个列表的长度 arr.length
llen key （*）

#8. 删除列表中的数据（他是删除当前列表中的count个value值，count > 0从左侧向右侧删除，count < 0从右侧向左侧删除，count == 0删除列表中全部的value）
lrem key count value（*）

#9. 保留列表中的数据（保留你指定索引范围内的数据，超过整个索引范围被移除掉）
ltrim key start stop

#10. 将一个列表中最后的一个数据，插入到另外一个列表的头部位置
rpoplpush list1 list2
```

### 详细

**单key多value**

简单说明：$\textcolor{red}{一个双端链表的结构}$，容量是2的32次方减1个元素大概40多亿，主要功能有push/pop等，一般用在栈、队列、消息队列等场景。left、right都可以插入添加；

如果键不存在，创建新的链表；

如果键已存在，新增内容；

如果值全移除，对应的键也就消失了

$\textcolor{green}{它的底层实际上就是个双向链表，对两端的作性能很高，通过索引下标的操作中间的节点性能会较差}$

![](03-Redis.assets/24.redis-List结构.jpg)

####  1.lpush/rpush/lrange

 注：**没有rrange**只能用lrange从头开始

![](03-Redis.assets/25.List类型遍历.jpg)

#### 2.lpop/rpop

![](03-Redis.assets/26.list弹出值命令.jpg)

#### 3.lindex，按照索引下标获得元素（从上到下）

![](03-Redis.assets/27.List-lindex.jpg)

#### 4.llen

==获取List列表中元素的个数==

![](03-Redis.assets/28.List-llen.jpg)

#### 5.lrem key 数量N 给定值v1

==删除N个值等于v1的元素==

从left往right删除2个值等于v1的元素，返回的值为实际删除的数量

LREM list3 0 值，表示删除全部给定的值，$\textcolor{red}{零个就是全部值}$

![](03-Redis.assets/29.List-lrem.jpg)

#### 6.ltrim key 开始index 结束index

==截取指定范围的值后在赋值给key==

![](03-Redis.assets/30.List-ltrim.jpg)

#### 7.rpoplpush 源列表  目的列表

==移除列表的最后一个元素，并将该元素添加到另一个列表并返回==

![](03-Redis.assets/31.list-rpoplpush.jpg)

#### 8.lset key index value

==让指定数组集合的小标位置值替换成新值==

<img src="03-Redis.assets/32.list-lset.jpg" style="zoom:140%;" />

#### 9.linsert key before/after

==已有值前/后插入的新值==

<img src="03-Redis.assets/33.list-linsert.jpg" style="zoom:80%;" />

### 应用

![image-20231007223146485](03-Redis.assets/image-20231007223146485.png)

## 4.5 set常用命令   无序不重复集合

### 总结

![img](03-Redis.assets/1638179162586-36a1bf45-2aba-432a-9ec1-9efa12b45997.png)

```bash
#1. 存储数据
sadd key member [member ...]

#2. 获取数据（获取全部数据）
smembers key

#3. 随机获取一个数据（获取的同时，移除数据，count默认为1，代表弹出数据的数量）
spop key [count]  取出随机的值，并移除

#4. 交集（取多个set集合交集）
sinter set1 set2 ...

#5. 并集（获取全部集合中的数据）
sunion set1 set2 ...

#6. 差集（获取多个集合中不一样的数据）  数据放在前面的为数据基点（筛选样板）
sdiff set1 set2 ...

# 7. 删除数据
srem key member [member ...]

# 8. 查看当前的set集合中是否包含这个值
sismember key member
```

### 详细

单值多value，且无重复,  自动去重

#### 1.SADD key member [member ...]

添加元素，可以多次向同一个key中设置不同值，不会覆盖之前的值

![image-20231007221506685](03-Redis.assets/image-20231007221506685.png)

#### 2.SMEMBERS key

遍历集合中的所有元素

![image-20231007221711759](03-Redis.assets/image-20231007221711759.png)

#### 3.SISMEMBER key member

判断元素是否在集合中

![](03-Redis.assets/40.set命令.png)



#### 4.SREM key member [member ...]

删除元素

![](03-Redis.assets/41.set-srem.png)

#### 5.scard

获取集合里面的元素个数

![](03-Redis.assets/42.set-scard.png)

#### 6.SRANDMEMBER key [数字]

从集合中随机$\textcolor{red}{展现设置的数字个数}$元素，元素不删除

![](03-Redis.assets/43.set-srandmember.png)

#### 7.SPOP key [数字]

从集合中==随机==$\textcolor{red}{弹出}$一个元素，出一个删除一个

![](03-Redis.assets/44.set-spop.png)

#### 8. smove key1 key2

将key1里已存在的某个值赋给key2, 并删除 key1的那个值

<img src="03-Redis.assets/image-20231007222038850.png" alt="image-20231007222038850" style="zoom:80%;" />

#### 9.集合运算-集合的差集运算A-B

属于A但是不属于B的元素构成的集合

SDIFF key [key ...]，可以计算多个元素的差集

![](03-Redis.assets/46.set-sdiff.png)

#### 10.集合运算-集合的并集运算A∪B

属于A或者属于B的元素构成的集合

SUNION key [key ...]

![](03-Redis.assets/47.set-sunion.png)



#### 11.集合运算-集合的交集运算A∩B

属于A同时也属于B的共同拥有的元素构成的集合

#####  SINTER key [key ...]

![](03-Redis.assets/48.set-sinter.png)



#####  SINTERCARD numkeys key 【key ...】【LIMIT limit】

注意: 

+  numkeys 的具体值由输入的key个数决定

+ SINTERCARD 为redis7新命令，它不返回结果集，而是返回结果的基数。返回由所有给定集合的交集产生的集合的基数(事物个数的数)

![](03-Redis.assets/49.set-sintercadr.png)

![](03-Redis.assets/50.set-sintercard limit.png)

### 应用

![image-20231007222914877](03-Redis.assets/image-20231007222914877.png)

## 4.6 zset的常用命令

### 总结

![img](03-Redis.assets/1638179832631-60871923-8187-47e9-8830-a998a0a97e32.png)

![img](03-Redis.assets/1638180544636-b20e5821-8c19-4d0f-b0db-043c6bd3f8cd.png)

```bash
#1. 添加数据(score必须是数值（可重复）。member不允许重复的。)
zadd key score member [score member ...]

#2. 修改member的分数（如果member是存在于key中的，正常增加分数，如果memeber不存在，这个命令就相当于zadd）
zincrby key increment member

#3. 查看指定的member的分数
zscore key member

#4. 获取zset中数据的数量 获取数组长度
zcard key

#5. 根据score的范围查询member数量  包含边界值  sql 语句里面between
zcount key min max

#6. 删除zset中的成员
zrem key member [member...]

#7. 根据分数从小到大排序，获取指定范围内（index 下标）的数据（withscores如果添加这个参数，那么会返回member对应的分数）
zrange key start stop [withscores]

#8. 根据分数从大到小排序，获取指定范围内（index 下标）的数据（withscores如果添加这个参数，那么会返回member对应的分数） 降序排序
zrevrange key start stop [withscores]

#9. 根据分数的返回去获取member(withscores代表同时返回score，添加limit，就和MySQL中一样，如果不希望等于min或者max的值被查询出来可以采用 ‘(分数’ 相当于 < 但是不等于的方式，最大值和最小值使用+inf和-inf来标识)
zrangebyscore key min max [withscores] [limit offset count]
```



### 详细

在set基础上，每个val值前加一个score分数值。之前set是k1 v1 v2 v3，现在zset是 k1 score1 v1 score2 v2

#### 1.ZADD key score member [score member ...]

添加元素

![image-20231007225125658](03-Redis.assets/image-20231007225125658.png)

#### 2.ZRANGE key start stop [WITHSCORES]

**按照元素分数从小到大的顺序**返回索引从start到stop之间的所有元素

![](03-Redis.assets/51.zset-zrange.png)

#### 3.zrevrange key start stop [WITHSCORES]

**按照元素分数从大到小的顺序**返回索引从start到stop之间的所有元素

![](03-Redis.assets/52.zset-zrevrange.png)

#### 4.ZRANGEBYSCORE key min max 【WITHSCORES】【LIMIT offset count】

获取指定分数范围的元素，可以在min和max前面加个(，表示不包含

limit作用是返回限制，limit开始下标步，一共多少步

![](03-Redis.assets/53.zset-zrangebyscore.png)

#### 5.ZSCORE key member

获取元素的分数

![image-20231007225507671](03-Redis.assets/image-20231007225507671.png)

#### 6.ZCARD key

获取集合中元素的数量

![image-20231007225631705](03-Redis.assets/image-20231007225631705.png)

#### 7. zrem key member [member ...]

某个score对应的value值，作用是删除元素

![](03-Redis.assets/55.zset-zrem.png)

#### 8.ZINCRBY key increment member

增加某个元素的分数

![image-20231007225805186](03-Redis.assets/image-20231007225805186.png)

#### 9.ZCOUNT key min max

获得指定分数内的元素个数

![](03-Redis.assets/57.zset-zcount.png)

#### 10.ZMPOP numkeys key [key ...] MIN|MAX [COUNT count]

从键名列表中的**第一个非空排序集中弹出最大/小一个或多个元素**

![image-20231007225919370](03-Redis.assets/image-20231007225919370.png)

#### 11.zrank key member [withscore]

作用是通过子value获得**下标值**

![image-20231007230233285](03-Redis.assets/image-20231007230233285.png)

#### 12.zrevrank key member [withscore]

作用是通过子value逆序获得**下标值**

![image-20231007230254082](03-Redis.assets/image-20231007230254082.png)

### 应用

![image-20231007230318439](03-Redis.assets/image-20231007230318439.png)

## 4.7 key常用命令(*****)

### 总结

```bash
#1. 查看Redis中的全部的key（pattern：* ，xxx*，*xxx） 这里采用通配符
keys pattern

#2. 查看某一个key是否存在（1 - key存在，0 - key不存在） 数组
exists key [key ...]  

#3. 删除key
del key [key ...]

#4. 设置key的生存时间，单位为秒，单位为毫秒,设置还能活多久
expire key second
pexpire key milliseconds

#5. 设置key的生存时间，单位为秒，单位为毫秒,设置能活到什么时间点
https://developer.aliyun.com/skills/timestamp.html
expireat key timestamp
pexpireat key milliseconds  最多能活多久 不代表能活到那么久 也不代表不能活那么久

#6. 查看key的剩余生存时间,单位为秒，单位为毫秒（-2 - 当前key不存在，-1 - 当前key没有设置生存时间，具体剩余的生存时间）
ttl key
pttl key

#7. 移除key的生存时间（1 - 移除成功，0 - key不存在生存时间，key不存在）
persist key

#8. 选择操作的库 Redis默认拥有16个数据库，初始默认使用0号库
select 0~15

#9. 移动key到另外一个库中
move key db
```

### 详细

#### 1.keys *

查看当前库所有的key

<img src="03-Redis.assets/4.查看当前库所有key.jpg" style="zoom: 80%;" />

#### 2.exists key

判断某个key是否存在

![](03-Redis.assets/5.判断key是否存在.jpg)

#### 3.type key

查看你的key是什么类型

![](03-Redis.assets/6.判断key类型.jpg)

#### 4.del key

删除指定的key数据

![](03-Redis.assets/7.删除key.jpg)

#### 5. unlink key

非阻塞删除，仅仅将keys从keyspace元数据中删除，真正的删除会在后续异步中操作。

del key 是原子的删除，只有删除成功了才会返回删除结果，如果是删除大key用del会将后面的操作都阻塞，而unlink key 不会阻塞，它会在后台异步删除数据。

#### 6.ttl key

查看还有多少秒过期，-1表示永不过期，-2表示已过期

#### 7.expire key 秒钟

为给定的key设置过期时间

![](03-Redis.assets/8.设置过期时间.jpg)

#### 8.move key dbindex[0-15]

将当前数据库的key移动到给定的数据库DB当中

![](03-Redis.assets/9.redis数据移动.jpg)

#### 9.select dbindex

Redis默认拥有16个数据库，初始默认使用0号库，切换数据库【0-15】，默认为0

#### 10.dbsize

查看当前数据库key的数量

#### 11.flushdb

清空当前库

#### 12.flushall

通杀全部库

<img src="03-Redis.assets/10.清空数据库.jpg" style="zoom: 80%;" />



## 4.8 库的常用命令

```bash
#1. 清空当前所在的数据库
flushdb             (~) 了解

#2. 清空全部数据库
flushall            (~) 了解

#3. 查看当前数据库中有多少个key
dbsize

#4. 查看最后一次操作的时间
lastsave

#5. 实时监控Redis服务接收到的命令
monitor
```

## 4.9 帮助命令

help @类型

```
help @string
help @list
help @hash
help @hyperloglog
```

<img src="03-Redis.assets/12.help命令.jpg" style="zoom: 80%;" />



## 4.10 位图(bitmap)常用命令

由0和1状态表现的二进制位的bit数组

### 介绍

![](03-Redis.assets/60.zset-bitarrays.jpg)

说明：$\textcolor{red}{用String类型作为底层数据结构实现的一种统计二值状态的数据类型}$

$\textcolor{red}{位图本质是数组}$，它是基于String数据类型的按位的操作。该数组由多个二进制位组成，每个二进制位都对应一个偏移量（我们称之为一个索引）。

Bitmap支持的最大位数是2^32位，它可以极大的节约存储空间，使用512M内存就可以存储多达42.9亿的字节信息(2^32=4294967296)

### 应用

**用于状态统计，Y、N类似AtomicBoolean:**

1. 用户是否登陆过Y、N，比如软件的每日签到功能
2. 电影、广告是否被点击播放过
3. 钉钉打卡上下班，签到统计\

### 基本命令

![](03-Redis.assets/61.bitmap基本命令.jpg)

#### 1.setbit key offset value

setbit 键偏移位 只能零或者1

**Bitmap的偏移量从零开始计算的**

![](03-Redis.assets/62.Bitmap-setbit示意图.jpg)



![](03-Redis.assets/63.Bitmap-setbit.jpg)

#### 2.getbit key offset

获取键偏移位的值

![](03-Redis.assets/64.Bitmap-getbit.jpg)

#### 3.strlen key

统计字节数占用多少

![](03-Redis.assets/65.Bitmap-strlen.jpg)

不是字符串长度而是占据几个字节，超过8位后自己按照8位一组**一byte**再扩容

#### 4.bitcount key [start end [byte|bit]]

全部键里面包含有1的有多少个

![](03-Redis.assets/66.Bitmap-bitcount.jpg)

#### 5.bitop operation(AND|OR|XOR|NOT) destkey key [key ...]

对不同的二进制存储数据进行位运算（AND、OR、NOT、

案例：连续2天都签到的用户数量

假如某个网站或者系统，它的用户有1000W，我们可以使用redis的HASH结构和bitmap结构做个用户id和位置的映射

![](03-Redis.assets/67.Bitmap-bitop1.jpg)



![image-20231007233108016](03-Redis.assets/image-20231007233108016.png)

![image-20231007233200936](03-Redis.assets/image-20231007233200936.png)

![image-20231007233246304](03-Redis.assets/image-20231007233246304.png)

## 4.11 基数统计(HyperLogLog)常用命令

### 介绍

> 去重脱水后的真实数据

去重复统计功能的基数估计算法-就是HyperLogLog

```tex
Redis在2.8.9版本添加了HyperLogLog 结构。
Redis HyperLogLog是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的。
在Redis里面，每个 HyperLogLog键只需要花费12KB内存，就可以计算接近2^64个不同元素的基数。这和计算基数时，元素越多耗费
内存就越多的集合形成鲜明对比。
但是，因为HyperLogLog只会根据输入元素来计算基数，而不会储存输入元素本身，所以HyperLogLog不能像集合那样，返回输入的各个元素。
```

**基数**：是一种数据集，去重复后的真实个数

```tex
(全集)={2,4,6,8,77,39,4,8,10}
去掉重复的内容
基数={2,4,6,8,77,39,10} = 7
```

基数统计：用于统计一个集合中不重复的元素个数，就是对集合去重复后剩余元素的计算。

### 应用

用户搜索网站关键词的数量

统计用户每天搜索不同词条个数

统计某个网站的UV、统计某个文章的UV

UV:  Unique Visitor，独立访客，一般理解为客户端IP，**需要去重考虑**

### 基本命令

![](03-Redis.assets/69.HyperLogLog常用命令.jpg)



![](03-Redis.assets/70.HyperLogLog命令演示.jpg)





## 4.12 地理空间(GEO)常用命令

### 介绍

移动互联网时代LBS应用越来越多，交友软件中附近的小姐姐、外卖软件中附近的美食店铺、高德地图附近的核酸检查点等等，那这种附近各种形形色色的XXX地址位置选择是实现:

+ 地球上的地理位置是使用二维的经纬度表示，经度范围(-180,180]，纬度范围(-90，90]，只要我们确定一个点的经纬度就可以取得他在地球的位置。
+ 例如滴滴打车，最直观的操作就是实时记录更新各个车的位置，然后当我们要找车时，在数据库中查找距离我们(坐标x0,y0)附近r公里范围内部的车辆

使用如下SQL即可:

```sql
select taxi from position where x0-r< X < x0 + r and y0-r< y < y0+r
```

SQL带来的问题:

1. 查询性能问题，如果并发高，数据量大这种查询是要搞垮数据库的
2. 这个查询的是一个矩形访问，而不是以我为中心r公里为半径的圆形访问。
3. 精准度的问题，我们知道地球不是平面坐标系，而是一个圆球，这种矩形计算在长距离计算时会有很大误差

### 原理

![](03-Redis.assets/71.GEO原理.jpg)

redis在3.2版本以后增加了地址位置的处理

### 基本命令

#### 1.GEOADD key longitude latitude member [longitude latitude member]

多个经度(longitude)、纬度(latitude)、位置名称(member)添加到指定的key中

出现中文乱码使用如下命令：

```shell
redis-cli --raw
```

**geo类型实际上是zset，可以使用zset相关的命令对其进行遍历**

```shell
GEOADD city 116.403963 39.915119 "天安门" 116.403414 39.924091 "故宫" 116.024067 40.362639 "长城"
```

![image-20231008112222111](03-Redis.assets/image-20231008112222111.png)

#### 2.GEOPOS key member [member]

从键里面返回所有指定名称(member )元素的位置（经度和纬度），不存在返回nil

```shell
GEOPOS city 天安门 故宫 长城
```

![](03-Redis.assets/73.GEO-geopos.png)

#### 3.GEODIST key member1 member2 [M|KM|FT|MI]

返回两个给定位置之间的距离

```
m-米
km-千米
ft-英寸
mi-英里
```

![](03-Redis.assets/75.GEO-GEODIST.png)

#### 4.GEORADIUS key longitude latitude radius M|KM|FT|MI \[WITHCOORD] \[WITHDIST] \[WITHHASH] [COUNT count [ANY]

以给定的经纬度为中心，返回与中心的距离不超过给定最大距离的所有元素位置

```
WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
WITHCOORD: 将位置元素的经度和维度也一并返回。
WITHHASH:以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 这个选项主要用于底层应用或者调试，实际中的作用并不大
COUNT 限定返回的记录数。
```

![image-20231008112515175](03-Redis.assets/image-20231008112515175.png)

#### 5.GEORADIUSBYMEMBER

跟GEORADIUS类似

![](03-Redis.assets/77.GEO-georadiusbymember.png)

#### 6.GEOHASH

返回一个或多个位置元素的GEOhash表示

geohash 算法生成的base32编码值，3维变2维变1维

![](03-Redis.assets/74.GEO-GEOhash.png)

## 4.13 流(Stream)常用命令

### 介绍

>  Stream流就是Redis版的MQ消息中间件+阻塞队列

Redis5.0 之前的痛点，Redis消息队列的2种方案：

1. List实现消息队列，List实现方式其实就是点对点的模式

   ![](03-Redis.assets/78.List实现消息队列.png)

2. Pub/Sub

![](03-Redis.assets/79.pub、sub.png)

Redis5.0版本新增了一个更强大的数据结构---Stream

### 应用

实现消息队列，它支持消息的持久化、支持自动生成全局唯一ID、支持ack确认消息的模式、支持消费组模式等，让消息队列更加的稳定和可靠

### 底层结构和原理说明

![](03-Redis.assets/80.stream结构.png)

$\textcolor{blue}{一个消息链表，将所有加入的消息都串起来，每个消息都有一个唯一的ID和对应的内容}$

![image-20231008113328868](03-Redis.assets/image-20231008113328868.png)

### 基本命令理论简介

##### 队列相关指令

![](03-Redis.assets/82.队列相关指令.png)

##### 消费组相关指令

![](03-Redis.assets/83.消费组相关指令.png)

XINFO GROUPS    打印消费组的详细信息

XINFO STREAM     打印stream的详细信息

##### 四个特殊符号

| 符号     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| - +      | 最小和最大可能出现的Id                                       |
| $    | $ | 表示只消费新的消息，当前流中最大的Id，可用于将要到来的信息   |
| >        | 用于XREADGROUP命令，表示迄今还没有发送给组中使用者的信息，会更新消费者组的最后Id |
| *        | 用于XADD命令，让系统自动生成Id                               |

$\textcolor{red}{基本命令代码实操}$

Redis流实例演示

### 队列相关命令

#### 1.XADD

添加消息到队列末尾，消息ID必须要比上一个ID大，默认用星号表示自动生成ID；

*用于XADD命令中，让系统自动生成ID；

XADD用于向Stream队列中添加消息，如果指定的Stream队列不存在，则该命令执行时会新建一个Stream队列

![](03-Redis.assets/84.Stream-XADD.png)

生成的消息ID，有两部分组成，毫秒时间戳-该毫秒内产生的第一条消息

*表示服务器自动生成MessageID(类似MySQL里面主键auto_increment)，后面顺序跟着一堆业务key/value

| 信息条目指的是序列号，在相同的毫秒下序列号从0开始递增，序列号是64位长度，理论上在同一毫秒内生成的数据量无法到达这个级别，因此不用担心序列号会不够用。milisecondsTime指的是Redis节点服务器的本地时间，如果存在当前的毫秒时间截比以前已经存在的数据的时间戳小的话(本地时间钟后跳)，那么系统将会采用以前相同的毫秒创建新的ID，也即redis 在增加信息条目时会检查当前 id 与上一条目的 id，自动纠正错误的情况，一定要保证后面的 id 比前面大，.个流中信息条目的ID必须是单调增的，这是流的基础。 |
| ------------------------------------------------------------ |
| 客户端显示传入规则:<br />Redis对于ID有强制要求，格式必须是**时间戳-自增Id**这样的方式，且后续ID不能小于前一个ID |
| Stream的消息内容，也就是图中的Messaget它的结构类似Hash结构，以kev-value的形式存在 |

#### 2.XRANGE key start end [COUNT count]

用于获取消息列表（可以指定范围），忽略删除的消息

```
start 表示开始值，-代表最小值
end 表示结束值，+代表最大值
count 表示最多获取多少个值
```

![](03-Redis.assets/85.Stream-XRANGE.png)

#### 3.XREVRANGE key end start [COUNT count]

根据ID降序输出

![](03-Redis.assets/86.Stream-XREVRANGE.png)

#### 4.XDEL key id [id ...]

![](03-Redis.assets/87.Stream-XDEL.png)

#### 5.XLEN key

![](03-Redis.assets/88.Stream-XLEN.png)

#### 6.XTRIM key MAXLEN|MINID

用于对Stream的长度进行截取，如超长会进行截取

```
MAXLEN 允许的最大长度，对流进行修剪限制长度
MINID 允许的最小id，从某个id值开始比该id值小的将会被抛弃
```

![](03-Redis.assets/89.Stream-xtrim.png)

#### 7.XREAD \[COUNT count] [BLOCK milliseconds] STREAMS key [key ...] id [id ...]

**用于获取消息(阻塞/非阻塞)**

只会返回大于指定ID的消息，COUNT最多读取多少条消息；BLOCK是否以阻塞的方式读取消息，默认不阻塞，如果milliseconds设置为0，表示永远阻塞, 可以读取多个key

**非阻塞**

- $表特殊ID，表示以当前Stream已经存储的最大的ID作为最后一个ID，当前Stream中不存在大于当前最大ID的消息，因此此时返回nil

- 0-0代表从最小的ID开始获取Stream中的消息，当不指定count，将会返回Stream中的所有消息，注意也可以使用0 (00/000也都是可以的)

  **![image-20231008141608898](03-Redis.assets/image-20231008141608898.png)**

**阻塞**

![](03-Redis.assets/91.Stream-xread阻塞.png)

$\textcolor{red}{总结（类似Java里面的阻塞队列）}$

Stream的基础方法，使用XADD存入消息和XREAD循环阻塞读取消息的方式可以实现简易版的消息队列

![](03-Redis.assets/92.Stream-XREAD总结.png)



---

### 消费组相关指令

#### 1.XGROUP CREATE key group id|$

用于创建消费组

```shell
xgroup create mystream group $     #$表示从Stream尾部开始消费
xgroup create mystream groupB 0    #0表示从Stream头部开始消费
```

创建消费组的时候必须指定ID，ID为0表示从头开始消费，为$表示只消费新消息

![image-20231008142119662](03-Redis.assets/image-20231008142119662.png)

#### 2.XREADGROUP GROUP group  \[COUNT count] [BLOCK milliseconds] STREAMS key id

">"，表示从第一条尚未被消费的消息开始读取

例子

![image-20231008142434417](03-Redis.assets/image-20231008142434417.png)

消费组groupA内的消费者consumer1从mystream消息队列中读取所有消息

但是，**不同消费组**的消费者可以消费同一条消息

![](03-Redis.assets/95.不同组消费组.png)

$\textcolor{red}{消费组的目的:}$

让组内的多个消费者共同分担读取消息，所以，我们通常会让每个消费者读取部分消息，从而实现消息读取负载在多个消费者间是均衡分部的

![image-20231008142657976](03-Redis.assets/image-20231008142657976.png)

##### 重点问题

基于 Stream 实现的消息队列，如何保证消费者在发生故障或宕机再次重启后，仍然可以读取未处理完的消息?

Streams 会自动使用内部队列(也称为 PENDING List)留存消费组里每个消费者读取的消息保底措施，直到消费者使用 XACK命令通知 Streams"消息已经处理完成”。
消费确认增加了消息的可靠性，一般在业务处理完成之后，需要执行 XACK 命令确认消息已经被消费完成

![](03-Redis.assets/97.消息ack.png)

#### 3.XPENDING

**查询每个消费组内所有消费组$\textcolor{red}{[已读取、但尚未确认]}$的消息**

例子1

![image-20231008142939110](03-Redis.assets/image-20231008142939110.png)

例子2

![image-20231008143349150](03-Redis.assets/image-20231008143349150.png)

查看某个消费组具体读取了那些数据

![](03-Redis.assets/99.Stream-XPENDING.png)

#### 4.XACK key group id [id...]

向消息队列确认消息处理已完成

![](03-Redis.assets/100.Stream-XACK.jpg)

#### 5. XINFO 用于打印Stream\Consumer\Group的详细信息

![](03-Redis.assets/101.Stream-XINFO.jpg)











## 4.14  位域(bitfield)常用命令

### 介绍

将一个redis字符串看作是**一个由二进制位组成的数组**并能对变长位宽和任意没有字节对齐的指定整型位域进行寻址和修改

![](03-Redis.assets/103.bitfield基本语法.jpg)

### 应用

位域修改、溢出控制

![image-20231008143835569](03-Redis.assets/image-20231008143835569.png)

### 基本命令

Ascii码表：https://ascii.org.cn

#### 1.BITFIELD key [GET type offset]

![image-20231008143940863](03-Redis.assets/image-20231008143940863.png)

#### 2.BITFIELD key set type offstet value

![image-20231008143950560](03-Redis.assets/image-20231008143950560.png)

#### 3.BITFIELD key [INCRBY type offset increment]

![](03-Redis.assets/106.bitfield-incrby.jpg)

如果偏移量后面的值发生溢出（大于127），redis对此也有对应的溢出控制，默认情况下，INCRBY使用WRAP参数

#### 4.溢出控制 OVERFLOW [WRAP|SAT|FAIL]

WRAP:使用回绕(wrap around)方法处理有符号整数和无符号整数溢出情况

![](03-Redis.assets/107.溢出策略warp.jpg)

SAT:使用饱和计算(saturation arithmetic)方法处理溢出，下溢计算的结果为最小的整数值，而上溢计算的结果为最大的整数值

![](03-Redis.assets/108.溢出策略sat.jpg)

fail:命令将拒绝执行那些会导致上溢或者下溢情况出现的计算，并向用户返回空值表示计算未被执行

![](03-Redis.assets/109.溢出策略fail.jpg)

# 五、Java连接Redis【重点】

------

## 5.1 Jedis连接Redis

### 5.1.1 创建Maven工程

idea创建

### 5.1.2 导入需要的依赖

```xml
<dependencies>
    <!--    1、 Jedis-->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.9.0</version>
    </dependency>
    <!--    2、 Junit测试-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <!--    3、 Lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.16.20</version>
    </dependency>
</dependencies>
```

### 5.1.3 测试

```java
public class Demo1 {

    @Test
    public void set(){
        //1. 连接Redis
        Jedis jedis = new Jedis("192.168.199.109",6379);
        //2. 操作Redis - 因为Redis的命令是什么，Jedis的方法就是什么
        jedis.set("name","李四");
        //3. 释放资源
        jedis.close();
    }

    @Test
    public void get(){
        //1. 连接Redis
        Jedis jedis = new Jedis("192.168.199.109",6379);
        //2. 操作Redis - 因为Redis的命令是什么，Jedis的方法就是什么
        String value = jedis.get("name");
        System.out.println(value);
        //3. 释放资源
        jedis.close();
    }
}
```

## 5.2 Jedis存储一个对象到Redis以byte[]的形式

### 5.2.1 准备一个User实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Integer id;

    private String name;

    private Date birthday;

}
```

### 5.2.2 导入spring-context依赖

```xml
<!-- 4. 导入spring-context -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>4.3.18.RELEASE</version>
</dependency>
```

### 5.2.3 创建Demo测试类，编写内容

```java
public class Demo2 {

    // 存储对象 - 以byte[]形式存储在Redis中
    @Test
    public void setByteArray(){
        //1. 连接Redis服务
        Jedis jedis = new Jedis("192.168.199.109",6379);
        //------------------------------------------------
        //2.1 准备key(String)-value(User)
        String key = "user";
        User value = new User(1,"张三",new Date());
        //2.2 将key和value转换为byte[]
        byte[] byteKey = SerializationUtils.serialize(key);
        byte[] byteValue = SerializationUtils.serialize(value);
        //2.3 将key和value存储到Redis
        jedis.set(byteKey,byteValue);
        //------------------------------------------------
        //3. 释放资源
        jedis.close();
    }

    // 获取对象 - 以byte[]形式在Redis中获取
    @Test
    public void getByteArray(){
        //1. 连接Redis服务
        Jedis jedis = new Jedis("192.168.199.109",6379);
        //------------------------------------------------
        //2.1 准备key
        String key = "user";
        //2.2 将key转换为byte[]
        byte[] byteKey = SerializationUtils.serialize(key);
        //2.3 jedis去Redis中获取value
        byte[] value = jedis.get(byteKey);
        //2.4 将value反序列化为User对象
        User user = (User) SerializationUtils.deserialize(value);
        //2.5 输出
        System.out.println("user:" + user);
        //------------------------------------------------
        //3. 释放资源
        jedis.close();
    }

}
```

## 5.3 Jedis存储一个对象到Redis以String的形式

### 5.3.1 导入依赖

```xml
<!-- 导入fastJSON -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>
```

### 5.3.2 测试

```java
public class Demo3 {

    // 存储对象 - 以String形式存储
    @Test
    public void setString(){
        //1. 连接Redis
        Jedis jedis = new Jedis("192.168.199.109",6379);
        //2.1 准备key(String)-value(User)
        String stringKey = "stringUser";
        User value = new User(2,"李四",new Date());
        //2.2 使用fastJSON将value转化为json字符串
        String stringValue = JSON.toJSONString(value);
        //2.3 存储到Redis中
        jedis.set(stringKey,stringValue);
        //3. 释放资源
        jedis.close();
    }


    // 获取对象 - 以String形式获取
    @Test
    public void getString(){
        //1. 连接Redis
        Jedis jedis = new Jedis("192.168.199.109",6379);

        //2.1 准备一个key
        String key = "stringUser";
        //2.2 去Redis中查询value
        String value = jedis.get(key);
        //2.3 将value反序列化为User
        User user = JSON.parseObject(value, User.class);
        //2.4 输出
        System.out.println("user:" + user);

        //3. 释放资源
        jedis.close();
    }
}
```

## 5.4 Jedis连接池的操作

```java
@Test
public void pool2(){
    //1. 创建连接池配置信息
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    poolConfig.setMaxTotal(100);  // 连接池中最大的活跃数
    poolConfig.setMaxIdle(10);   // 最大空闲数
    poolConfig.setMinIdle(5);   // 最小空闲数
    poolConfig.setMaxWaitMillis(3000);  // 当连接池空了之后,多久没获取到Jedis对象,就超时

    //2. 创建连接池
    JedisPool pool = new JedisPool(poolConfig,"192.168.199.109",6379);

    //3. 通过连接池获取jedis对象
    Jedis jedis = pool.getResource();
	
    //4. 操作	
    String value = jedis.get("stringUser");
    System.out.println("user:" + value);

    //5. 释放资源
    jedis.close();
}
```

## 5.5 Redis的管道操作

因为在操作Redis的时候，执行一个命令需要先发送请求到Redis服务器，这个过程需要经历网络的延迟，Redis还需要给客户端一个响应。

如果我需要一次性执行很多个命令，上述的方式效率很低，可以通过Redis的管道，先将命令放到客户端的一个Pipeline中，之后一次性的将全部命令都发送到Redis服务，Redis服务一次性的将全部的返回结果响应给客户端。



```java
//  Redis管道的操作
@Test
public void pipeline(){
    //1. 创建连接池
    JedisPool pool = new JedisPool("192.168.199.109",6379);
    long l = System.currentTimeMillis();

    /*//2. 获取一个连接对象
    Jedis jedis = pool.getResource();

    //3. 执行incr - 100000次
    for (int i = 0; i < 100000; i++) {
        jedis.incr("pp");
    }

    //4. 释放资源
    jedis.close();*/

    //================================
    //2. 获取一个连接对象
    Jedis jedis = pool.getResource();
    //3. 创建管道
    Pipeline pipelined = jedis.pipelined();
    //3. 执行incr - 100000次放到管道中
    for (int i = 0; i < 100000; i++) {
        pipelined.incr("qq");
    }
    //4. 执行命令
    pipelined.syncAndReturnAll();
    //5. 释放资源
    jedis.close();

    System.out.println(System.currentTimeMillis() - l);
}
```

# 六、Redis其他配置及集群【重点】

修改yml文件，以方便后期修改Redis配置信息

```yaml
version: '3.1'
services:
  redis:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 6379:6379
    volumes:
      - ./conf/:/usr/local/redis/
    command: ["redis-server","/usr/local/redis/redis.conf"]
```

## 6.1 Redis的AUTH 

![img](03-Redis.assets/1638257016127-078e7efe-7dca-4995-aba4-4788e1480e5b.png)

方式一：通过修改Redis的配置文件，实现Redis的密码校验

\# redis.conf
requirepass 密码





三种客户端的连接方式

- redis-cli：在输入正常命令之前，先输入auth 密码即可。（auth  +密码）
- 图形化界面：在连接Redis的信息中添加上验证的密码。
- Jedis客户端：

- - jedis.auth(password);

- 使用JedisPool的方式

// 使用当前有参构造设置密码

```yaml
public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,int timeout, final String password)
```





## 6.2 Redis的事务（了解）  redis是没有事务（*）

Redis的事务：一次性事务操作，该成功的成功，该失败的失败。（一套命令集合，正确命令该成功成功，不正确命令不成功）

先开启事务，执行一些列的命令，但是命令不会立即执行，会被放在一个队列中，如果你执行事务，那么这个队列中的命令全部执行，如果取消了事务，一个队列中的命令全部作废。

- 开启事务：multi
- 输入要执行的命令：被放入到一个队列中
- 执行事务：exec
- 取消事务：discard

Redis的事务向发挥功能，需要配置watch监听机制

在开启事务之前，先通过watch命令去监听一个或多个key，在开启事务之后，如果有其他客户端修改了我监听的key，事务会自动取消。

如果执行了事务，或者取消了事务，watch监听自动消除，一般不需要手动执行unwatch。



![img](03-Redis.assets/1638257604153-de483725-ab0f-43f5-9379-231142946051.png)



![img](03-Redis.assets/1638257862758-076fece0-4883-415c-920a-a39546882938.png)



## 6.3 Redis持久化机制（*****）

### 6.3.1 RDB

RDB是Redis默认的持久化机制

- RDB持久化文件，速度比较快，而且存储的是一个二进制的文件，传输起来很方便。
- RDB持久化的时机：save 900 1：在900秒内，有1个key改变了，就执行RDB持久化。save 300 10：在300秒内，有10个key改变了，就执行RDB持久化。save 60 10000：在60秒内，有10000个key改变了，就执行RDB持久化。
- RDB无法保证数据的绝对安全。
- dbfilename dump.rdb  （如果采用docker安装redis，持久化的时候我们的文件保存在我们的docker容器里面）
- rdbcompression yes  开启rdb持久化机制

```bash
save 900 1
save 300 10
save 60 10000
```

配置完成生成的文件路径容器的/data目录下面



优点：

持久化数据快，存储方便，数据安全（二进制）

缺点是

持久化策略可能会存在数据丢失，不能保证我们的数据安全

### 6.3.2 AOF

AOF持久化机制默认是关闭的，Redis官方推荐同时开启RDB和AOF持久化，更安全，避免数据丢失。

- AOF持久化的速度，相对RDB较慢的，存储的是一个文本文件，到了后期文件会比较大，传输困难。
- AOF持久化时机。appendfsync always：每执行一个写操作，立即持久化到AOF文件中，性能比较低。appendfsync everysec：每秒执行一次持久化。appendfsync no：会根据你的操作系统不同，环境的不同，在一定时间内执行一次持久化。
- AOF相对RDB更安全，推荐同时开启AOF和RDB。

```bash
appendonly yes
appendfilename redis.aof
appendfsync everysec

# appendfsync always
# appendfsync no
```

![img](03-Redis.assets/1638263409284-0414a38e-592e-41b7-841a-8af596574201.png)

### 6.3.3 注意事项

同时开启RDB和AOF的注意事项：

如果同时开启了AOF和RDB持久化，那么在Redis宕机重启之后，需要加载一个持久化文件，优先选择AOF文件。

如果先开启了RDB，再次开启AOF，如果RDB执行了持久化，那么RDB文件中的内容会被AOF覆盖掉。



## 6.4 Redis的主从架构



1、解决我们的日常一个单机故障，而衍生出来 主从架构

2、高并发所有请求都会发送我们单台redis，如果我redis承受就会宕机或者阻塞

3、组从：主机一般来说的我们master（读写都可以），slave（只读）

![img](03-Redis.assets/1637479460989-1be7c015-af23-4141-b22a-064df8f9a7ec.png)



指定yml文件

```yaml
  version: "3.1"
  services:
    redis1:
      image: daocloud.io/library/redis:5.0.7
      restart: always
      container_name: redis1
      environment:
        - TZ=Asia/Shanghai
      ports:
        - 8001:6379
      volumes:
        - ./conf/redis1.conf:/usr/local/redis/redis.conf
      command: ["redis-server","/usr/local/redis/redis.conf"]
    redis2:
      image: daocloud.io/library/redis:5.0.7
      restart: always
      container_name: redis2
      environment:
        - TZ=Asia/Shanghai
      ports:
        - 8002:6379
      volumes:
        - ./conf/redis2.conf:/usr/local/redis/redis.conf
      links:
        - redis1:master
      command: ["redis-server","/usr/local/redis/redis.conf"]
    redis3:
      image: daocloud.io/library/redis:5.0.7
      restart: always
      container_name: redis3
      environment:
        - TZ=Asia/Shanghai
      ports:
        - 8003:6379
      volumes:
        - ./conf/redis3.conf:/usr/local/redis/redis.conf
      links:
        - redis1:master
      command: ["redis-server","/usr/local/redis/redis.conf"]
# redis2和redis3从节点配置
replicaof master 6379
```

命令：通过 info replication 查看当前redis信息



![img](03-Redis.assets/1638265595088-110a9261-5854-46e5-8f36-240bcdfac31a.png)

## 6.5 哨兵（*****）

哨兵可以帮助我们解决主从架构中的单点故障问题



![img](03-Redis.assets/1637479534967-8072a76f-92c4-4a53-a9c9-4a6e03b6fcd5.png)



修改了以下docker-compose.yml，为了可以在容器内部使用哨兵的配置

```yaml
version: "3.1"
services:
  redis1:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis1
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 8001:6379
    volumes:
      - ./conf/redis1.conf:/usr/local/redis/redis.conf
      - ./conf/sentinel1.conf:/data/sentinel.conf        # 添加的内容
    command: ["redis-server","/usr/local/redis/redis.conf"]
  redis2:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis2
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 8002:6379
    volumes:
      - ./conf/redis2.conf:/usr/local/redis/redis.conf
      - ./conf/sentinel2.conf:/data/sentinel.conf        # 添加的内容
    links:
      - redis1:master
    command: ["redis-server","/usr/local/redis/redis.conf"]
  redis3:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis3
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 8003:6379
    volumes:
      - ./conf/redis3.conf:/usr/local/redis/redis.conf
      - ./conf/sentinel3.conf:/data/sentinel.conf        # 添加的内容 
    links:
      - redis1:master
    command: ["redis-server","/usr/local/redis/redis.conf"]
```

准备哨兵的配置文件，并且在容器内部手动启动哨兵即可

```yaml
# 哨兵需要后台启动
daemonize yes
# 指定Master节点的ip和端口（主）
sentinel monitor master localhost 6379 2
# 指定Master节点的ip和端口（从）
sentinel monitor master master 6379 2
# 哨兵每隔多久监听一次redis架构
sentinel down-after-milliseconds master 10000
```

在Redis容器内部启动sentinel即可

```yaml
redis-sentinel sentinel.conf
```

## 6.6 Redis的集群



Redis集群在保证主从加哨兵的基本功能之外，还能够提升Redis存储数据的能力。



![img](03-Redis.assets/1637479648353-34895afa-c956-4382-a615-32012bd97ec5.png)



准备yml文件

```yaml
# docker-compose.yml
version: "3.1"
services:
  redis1:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis1
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7001:7001
      - 17001:17001
    volumes:
      - ./conf/redis1.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]
  redis2:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis2
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7002:7002
      - 17002:17002
    volumes:
      - ./conf/redis2.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]  
  redis3:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis3
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7003:7003
      - 17003:17003
    volumes:
      - ./conf/redis3.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]  
  redis4:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis4
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7004:7004
      - 17004:17004
    volumes:
      - ./conf/redis4.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]  
  redis5:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis5
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7005:7005
      - 17005:17005
    volumes:
      - ./conf/redis5.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]  
  redis6:
    image: daocloud.io/library/redis:5.0.7
    restart: always
    container_name: redis6
    environment:
      - TZ=Asia/Shanghai
    ports:
      - 7006:7006
      - 17006:17006
    volumes:
      - ./conf/redis6.conf:/usr/local/redis/redis.conf
    command: ["redis-server","/usr/local/redis/redis.conf"]  
# redis.conf
# 指定redis的端口号
port 7001
# 开启Redis集群
cluster-enabled yes
# 集群信息的文件
cluster-config-file nodes-7001.conf
# 集群的对外ip地址
cluster-announce-ip 101.42.253.234
# 集群的对外port
cluster-announce-port 7001
# 集群的总线端口
cluster-announce-bus-port 17001


port 7001
cluster-enabled yes
cluster-config-file nodes-7001.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7001
cluster-announce-bus-port 17001


port 7002
cluster-enabled yes
cluster-config-file nodes-7002.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7002
cluster-announce-bus-port 17002

port 7003
cluster-enabled yes
cluster-config-file nodes-7003.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7003
cluster-announce-bus-port 17003

port 7004
cluster-enabled yes
cluster-config-file nodes-7004.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7004
cluster-announce-bus-port 17004

port 7005
cluster-enabled yes
cluster-config-file nodes-7005.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7005
cluster-announce-bus-port 17005

port 7006
cluster-enabled yes
cluster-config-file nodes-7006.conf
cluster-announce-ip 43.138.66.98
cluster-announce-port 7006
cluster-announce-bus-port 17006
```

启动了6个Redis的节点。

随便跳转到一个容器内部，使用redis-cli管理集群

redis-cli --cluster create  表示链接我们的集群redis （redis-cli --cluster create [ip:port ip:port]）

--cluster-replicas 1  1表示一个master下面有几个备机

```yaml
redis-cli --cluster create 101.42.253.234:7001 101.42.253.234:7002 101.42.253.234:7003 101.42.253.234:7004 101.42.253.234:7005 101.42.253.234:7006 --cluster-replicas 1
```

## redis-cli -h  ip  -p port -c【需要加上这个-c  可以让我们的集群自动切换】

 

 

## 6.7 Java连接Redis集群



使用JedisCluster对象连接Redis集群



```java
@Test
public void test(){
    // 创建Set<HostAndPort> nodes
    Set<HostAndPort> nodes = new HashSet<>();
    nodes.add(new HostAndPort("192.168.199.109",7001));
    nodes.add(new HostAndPort("192.168.199.109",7002));
    nodes.add(new HostAndPort("192.168.199.109",7003));
    nodes.add(new HostAndPort("192.168.199.109",7004));
    nodes.add(new HostAndPort("192.168.199.109",7005));
    nodes.add(new HostAndPort("192.168.199.109",7006));

    // 创建JedisCluster对象
    JedisCluster jedisCluster = new JedisCluster(nodes);

    // 操作
    String value = jedisCluster.get("b");
    System.out.println(value);
}
```

# 七、Redis常见问题【重点】

## 7.1 key的生存时间到了，Redis会立即删除吗？

不会立即删除。

- 定期删除：Redis每隔一段时间就去会去查看Redis设置了过期时间的key，会再100ms的间隔中默认查看3个key。
- 惰性删除：如果当你去查询一个已经过了生存时间的key时，Redis会先查看当前key的生存时间，是否已经到了，直接删除当前key，并且给用户返回一个空值。

## 7.2 Redis的淘汰机制

在Redis内存已经满的时候，添加了一个新的数据，执行淘汰机制。8种策略

- volatile-lru：在内存不足时，Redis会再设置过了生存时间的key中干掉一个最近最少使用的key。
- allkeys-lru：在内存不足时，Redis会再全部的key中干掉一个最近最少使用的key。



- volatile-lfu：在内存不足时，Redis会再设置过了生存时间的key中干掉一个最近最少频次使用的key。
- allkeys-lfu：在内存不足时，Redis会再全部的key中干掉一个最近最少频次使用的key。



- volatile-random：在内存不足时，Redis会再设置过了生存时间的key中随机干掉一个。
- allkeys-random：在内存不足时，Redis会再全部的key中随机干掉一个。
- 
- volatile-ttl：在内存不足时，Redis会再设置过了生存时间的key中干掉一个剩余生存时间最少的key。
- noeviction：（默认）在内存不足时，直接报错。



指定淘汰机制的方式：maxmemory-policy 具体策略，设置Redis的最大内存：maxmemory 字节大小  k8s



## 7.3 缓存的常问题  

### 7.3.1 缓存穿透问题

缓存穿透

![img](03-Redis.assets/1637479872848-6da37af8-4a58-493e-a6f4-fa33e592942b.png)



### 7.3.2 缓存击穿问题

缓存击穿



![img](03-Redis.assets/1637479907071-93d1a270-95eb-485b-82b0-5c2fc3864d67.png)



### 7.3.4缓存倾斜问题

缓存倾斜

![image.png](03-Redis.assets/1637479934494-f5ad07d8-5699-4374-b11a-2e3f0247fb66.png)



# 八、Redis持久化

## 介绍

官网地址：https://redis.io/docs/manual/persistence/

<img src="03-Redis.assets/1.Redis持久化.jpg" style="zoom:67%;" />

![](03-Redis.assets/2.Redis持久化图示.jpg)

主要两种方式:

+ **RDB**(Redis DataBase)

+ **AOF**(Append Only File)

## RDB(Redis DataBase)

RDB(Redis 数据库)：RDB持久化以指定的时间间隔执行数据集的时间点快照

### 介绍

+ 在指定的时间间隔，执行数据集的时间点快照

+ 实现类似照片记录效果的方式，就是把某一时刻的数据和状态以文件的形式写到磁盘上，也就是快照。这样一来即使故障宕机，快照文件也不会丢失，数据的可靠性也就得到了保证

+ 这个快照文件就称为RDB文件(dump.rdb)

### 作用

+ 在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是snapshot内存快照，它恢复时再将硬盘快照文件直接读回到内存里

+ Redis的数据都在内存中，保存备份时它执行的是全量快照，也就是说，把内存中的所有数据都记录到磁盘中，一锅端

+ RDB保存的是dump.rdb文件

### RDB案例

#### 需求说明

![](03-Redis.assets/3.需求说明.jpg)

$\textcolor{red}{配置文件}$(6 VS 7)

Redis6.0.16及以下

![](03-Redis.assets/4.RDB6.0.16及以下配置1.jpg)

![](03-Redis.assets/5.RDB6.0.16及以下配置2.jpg.jpg)

Redis6.2以及Redis-7.0.0

![](03-Redis.assets/6.RDB7配置.jpg)

#### 操作步骤

![](03-Redis.assets/7.RDB触发方式.jpg)

##### 自动触发

Redis7版本，按照redis.conf里配置的 save \<seconds> \<changes>

**本次案例5秒2次修改**

![](03-Redis.assets/8.5s内修改2次.png)

**修改dump文件保存路径**

```shell
lfj@lfj-virtual-machine:/myredis$ ls
redis.conf
```

![](03-Redis.assets/9.dump文件路径.png)

```shell
root@lfj-virtual-machine:/myredis# redis-server /myredis/redis.conf 
root@lfj-virtual-machine:/myredis# redis-cli -a 741106 -p 6379
127.0.0.1:6379> CONFIG get dir
```

**修改dump文件名称**

![](03-Redis.assets/10.RDB修改备份文件名.png)

<font color = red>注意: 改完配置, 重新启动Redis</font>

```shell
shutdown
quite
```

**触发备份**

第一种情况，5秒内保存2次

![](03-Redis.assets/11.触发备份1.png)

第二种情况，两次保存间隔超过5秒

![](03-Redis.assets/12.触发备份2.png)
  注：RDB 持久化是 Redis 的一种持久化机制，它会在 Redis 数据发生修改时对内存中的数据进行快照，然后保存到磁盘，以保证数据的持久性。通常情况下，RDB 保存快照的时间间隔由配置文件中的参数 save 决定，格式为 save \<seconds> \<changes>，表示在 \<seconds> 秒内，如果数据有 \<changes> 次修改，则会进行一次快照。

在题目描述的情况下，RDB 设置了每 5 秒进行一次快照，但是如果在 5 秒内修改次数超过了 2 次，也会进行快照。这是因为在 Redis 中，保存快照并不是在规定的时间到达后才进行，而是在修改数据时和时间间隔条件的双重限制下才进行的。

如果限制只按时间间隔来进行保存快照，则会出现两个问题：

如果时间间隔太大，那么 Redis 持久化的数据可能会丢失，并且故障恢复时的数据可能会受到影响。

如果时间间隔太小，那么数据的保存成本就会过高，并可能导致 Redis 运行效率下降。

因此，Redis 引入了按时间和数据修改次数双重限制的快照保存机制，以在灵活性和效率之间取得平衡。如果在 5 秒内修改的次数超过 2 次，则说明数据的变化较快，在此情况下保存快照并不会带来明显的性能问题。因此，Redis 将其纳入保存快照的范围，以保证数据的安全和一致性

**如何恢复**

将备份文件(dump.rdb)移动到 Redis 安装目录并启动服务即可

备份成功后故意用flushdb清空redis，看看是否可以恢复数据

- 执行flushall/flushdb命令也会产生dump.rdb文件，但里面是空的，无意义

==物理恢复，一定要将服务产生的RDB文件备份一份，然后分机隔离，避免生产上物理损坏后备份文件也挂了。==

##### 手动触发

使用save或者bgsave命令

$\textcolor{red}{redis提供了两个命令来生成RDB文件，分别是save和bgsave}$

![](03-Redis.assets/13.RDB手动保存.png)

**save**：在主程序中执行会**阻塞**当前redis服务器，直到持久化工作完成执行save命令期间，Redis不能处理其他命令，**线上禁止使用**

**bgsave(默认)**：

![image-20231009164939223](03-Redis.assets/image-20231009164939223.png)

- redis会在后台异步进行快照操作，**不阻塞**快照同时还可以相应客户端请求，该触发方式会fork一个子进程由子进程复制持久化过程
- Redis会使用bgsave对当前内存中的所有数据做快照，这个操作是子进程在后台完成的，这就允许主进程同时可以修改数据。
- LASTSAVE    可以通过lastsave命令获取最后一次成功执行快照的时间


![](03-Redis.assets/15.lastsave命令.png)

### RDB优劣

#### 优势

官网说明：

<img src="03-Redis.assets/16.RDB优势官网说明.jpg" style="zoom:67%;" />

- RDB是Redis 数据的一个非常紧凑的单文件时间点表示。RDB文件非常适合备份。例如，您可能希望在最近的24小时内每小时归档一次RDB文件，并在30天内每天保存一个RDB快照。这使您可以在发生灾难时轻松恢复不同版本的数据集。
- RDB非常适合灾难恢复，它是一个可以传输到远程数据中心或Amazon S3(可能已加密）的压缩文件。
- RDB最大限度地提高了Redis 的性能，因为Redis 父进程为了持久化而需要做的唯一工作就是派生一个将完成所有其余工作的子进程。父进程永远不会执行磁盘I/О或类似操作。
- 与AOF 相比，RDB允许使用大数据集更快地重启。
- 在副本上，RDB支持重启和故障转移后的部分重新同步。

总结：

- 适合大规模的数据恢复
- 按照业务定时备份
- 对数据完整性和一致性要求不高
- RDB文件在内存中的加载速度要比AOF快很多

#### 劣势

官网说明：

<img src="03-Redis.assets/17.RDB劣势官网说明.jpg" style="zoom:67%;" />

- 如果您需要在Redis停止工作时（例如断电后）将数据丢失的可能性降到最低，那么RDB并不好。您可以配置生成RDB的不同保存点（例如，在对数据集至少5分钟和100次写入之后，您可以有多个保存点)。但是，您通常会每五分钟或更长时间创建一次RDB快照，因此，如果Redis由于任何原因在没有正确关闭的情况下停止工作，您应该准备好丢失最新分钟的数据。
- RDB需要经常fork()以便使用子进程在磁盘上持久化。如果数据集很大，fork()可能会很耗时，并且如果数据集很大并且CPU性能不是很好，可能会导致Redis停止为客户端服务几毫秒甚至一秒钟。AOF也需要fork()但频率较低，您可以调整要重写日志的频率，而不需要对持久性进行任何权衡。

小总结：

- 在一定间隔时间做一次备份，所以如果redis意外down掉的话，就会丢失从当前至最近一次快照期间的数据，**快照之间的数据会丢失**
- 内存数据的全量同步，如果数据量太大会导致IO严重影响服务器性能
- RDB依赖于主进程的fork，在更大的数据集中，这可能会导致服务请求的瞬间延迟。fork的时候内存中的数据被克隆了一份，大致2倍的膨胀性，需要考虑

模拟数据丢失：

![](03-Redis.assets/18.RDB模拟数据丢失.jpg)

### RDB快照文件.md

#### 检查修复dump.rdb文件

进入到redis安装目录(/usr/local/bin)，执行redis-check-rdb命令 redis-check-rdb ./redisconfig/dump.rdb

<img src="03-Redis.assets/image-20231009170418205.png" alt="image-20231009170418205" style="zoom:67%;" />

####   哪些情况会触发RDB快照

1. 配置文件中默认的快照配置
2. 手动bgsave命令
3. 执行flushdb/fulshall命令也会产生dump.rdb文件，但是也会将命令记录到dump.rdb文件中，恢复后依旧是空，无意义
4. 执行shutdown且没有设置开启AOF持久化
5. 主从复制时，主节点自动触发

#### 如何禁用快照(RDB)

1. 方法1: 动态所有停止RDB保存规则的方法：redis-cli config set value ""
2. 方法2: 手动修改配置文件

![](03-Redis.assets/19.RDB快照禁用.jpg)

### RDB优化配置项详解

配置文件SNAPSHOTTING模块

- save \<seconds> \<changes>：配置快照保存条件

- dir：配置快照保存目录地址

- dbfilename：配置快照的文件名

- stop-writes-on-bgsave-error：

  ![](03-Redis.assets/20.stop-writes-on-bgsave-error.jpg)

  默认yes，如果配置成no，表示不在乎数据不一致或者有其他的手段发现和控制这种不一致，那么在快照写入失败时，也能确保redis继续接受新的请求

- rdbcompression：

  ![](03-Redis.assets/21.rdbcompression.jpg)

  默认yes，对于存储到磁盘中的快照，可以设置是否进行压缩存储。如果是的话，Redis会采用LZF算法进行压缩。如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能

- rdbchecksum：

  ![](03-Redis.assets/22.rdbchecksum.jpg)

  默认yes，在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能

- rdb-del-sync-files：

![](03-Redis.assets/23.rdb-del-sync-files.jpg)

在没有持久化的情况下删除复制中使用的RDB文件。默认情况下no，此选项是禁用的。

### 总结

![](03-Redis.assets/24.RDB小总结.jpg)





## AOF(Append Only File)

### 介绍

<img src="03-Redis.assets/1.Redis持久化-16967476611551.jpg" style="zoom:67%;" />

$\textcolor{red}{以日志的形式来记录每个写操作}$，将Redis执行过的所有写指令记录下来(读操作不记录)，只许追加文件但是不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

默认情况下，redis是没有开启AOF的。开启AOF功能需要设置配置：appendonly yes

### 作用

<img src="03-Redis.assets/25.AOF能干嘛.jpg" style="zoom: 80%;" />

AOF保存的是appendonly.aof文件

### AOF持久化工作流程

![](03-Redis.assets/26.AOF持久化工作流程.jpg)

1.Client作为命令的来源，会有多个源头以及源源不断的请求命令。

2.在这些命令到达Redis Server 以后并不是直接写入AOF文件，会将其这些命令先放入AOF缓存中进行保存。这里的AOF缓冲区实际上是内存中的一片区域，存在的目的是当这些命令达到一定量以后再写入磁盘，避免频繁的磁盘IO操作。

3.AOF缓冲会根据AOF缓冲区**同步文件的三种写回策略**将命令写入磁盘上的AOF文件。

4.随着写入AOF内容的增加为避免文件膨胀，会根据规则进行命令的合并(**又称AOF重写**)，从而起到AOF文件压缩的目的。

5.当Redis Server服务器重启的时候会队AOF文件载入数据。

### AOF缓冲区三种写回策略

![](03-Redis.assets/27.三种写回策略.jpg)

**ALways**：同步写回，每个写命令执行完立刻同步地将日志写会磁盘

**everysec**：每秒写回，每个写命令执行完，只是先把日志写到AOF文件的内存缓冲区，每隔1秒把缓冲区中的内容写入到磁盘

**no**：操作系统控制的写回，每个写命令执行完，只是先把日志写到AOF文件的内存缓冲区，由操作系统决定何时将缓冲区内容写回磁盘

 **总结**

<img src="03-Redis.assets/28.AOF三种写回策略.jpg" style="zoom:80%;" />

### AOF案例

#### 配置文件说明 (6 VS 7)

##### 如何开启aof

<img src="03-Redis.assets/29.开启AOF.jpg" style="zoom:67%;" />

##### 使用默认写回策略

![](03-Redis.assets/30.AOF默认保存策略.jpg)

##### aof文件-保存路径

- redis6及以前

  AOF保存文件的位置和RDB保存文件的位置一样，都是通过redis.conf配置文件的dir配置

![](03-Redis.assets/31.AOF配置文件路径(Redis6及以前).jpg)

- redis7最新

![](03-Redis.assets/32.AOF配置文件路径(Redis7).jpg)

**一句话：**

![](03-Redis.assets/33.Redis新老版本区别.jpg)

##### aof文件-保存名称

###### redis6及以前 ，有且仅有一个


![](03-Redis.assets/34.AOF文件名称(Redis6及以前).jpg)

###### Redis7 Multi Part AOF的设计


从1个文件到3个文件

![](03-Redis.assets/35.AOF文件名称(Redis7).jpg)

**MP-AOF实现**
**方案概述**
顾名思义，MP-AOF就是将原来的单个AOF文件拆分成多个AOF文件。在MP-AOF中，我们将AOF分为三种类型,
分别为:

- **BASE: 表示基础AOF**，它一般由子进程通过重写产生，该文件最多只有一个。


- **INCR:表示增量AOF**，它一般会在AOFRW开始执行时被创建，该文件可能存在多个。


- **HISTORY**:表示历史AOF，它由BASE和INCR AOF变化而来，每次AOFRW成功完成时，本次AOFRW之前对应的BASE和INCR AOF都将变为HISTORY，HISTORY类型的AOF会被Redis自动删除。

为了管理这些AOF文件，我们引入了一个manifest (清单)文件来跟踪、管理这些AOF。同时，为了便于AOF备份和拷贝，我们将所有的AOF文件和manifest文件放入一个单独的文件目录中，目录名由appenddirname配置(Redis 7.0新增配置项)决定。

Redis7.0config 中对应的配置项

![](03-Redis.assets/36.redis7AOF配置项.jpg)

<font color = red>注意: 改完配置, 重新启动Redis</font>

```shell
shutdown
quite
```

##### 正常恢复

1. 修改默认的appendonly no，改为yes
2. 写操作继续，生成aof文件到指定目录（然后将appendonly文件备份，使用flushdb+shutdown服务器来模拟redis宕机数据丢失，删除生成的新aof文件，将备份文件恢复）
   <img src="03-Redis.assets/image-20231009210103560.png" alt="image-20231009210103560" style="zoom: 80%;" />
3. 恢复：重启redis然后重新加载，结果OK，将数据重新写入到了redis

##### 异常恢复

1. 故意胡乱改动正常的AOF文件，模拟网络闪断文件写入不完整等其他异常情况

   appendonly.aof.1.incr.aof文件

   <img src="03-Redis.assets/image-20231009210252074.png" alt="image-20231009210252074" style="zoom:67%;" />

2. 重启Redis之后就会进行AOF文件的载入
   ![](03-Redis.assets/39.aof异常服务启动失败.jpg)
   
3. 异常修复命令：redis-check-aof --fix进行修复
   ![image-20231009210400635](03-Redis.assets/image-20231009210400635.png)
   
4. 启动后OK





### AOF优劣

#### 优势

更好的保护数据不丢失、性能高、可做紧急恢复

<img src="03-Redis.assets/41.AOF优势.png" style="zoom:67%;" />

- 使用AOF Redis 更加持久: 您可以有不同的fsync 策略: 根本不fsync、每秒 fsync、每次查询时fsync。使用每秒fsync的默认策略，写入性能仍然很棒。fsync 是使用后台线程执行的，当没有fsync正在进行时，主线程将努力执行写入，因此您只能丢失一秒钟的写入。
- AOF 日志是一个仅附加日志，因此不会出现寻道问题，也不会在断电时出现损坏问题。即使由于某种原因(磁盘已满或其他原因) 日志以写一半的命令结尾，redis-check-aof 工具也能够轻松修复它。
- 当AOF 变得太大时，Redis 能够在后台自动重写AOF。重写是完全安全的，因为当 Redis继续附加到旧文件时，会使用创建当前数据集所需的最少操作集生成一个全新的文件，一旦第二个文件准备就绪，Redis 就会切换两者并开始附加到新的那一个。
- AOF以易于理解和解析的格式依次包含所有操作的日志。您甚至可以轻松导出AOF文件。例如，即使您不小心使用孩FLUSHALL命令刷新了所有内容，只要在此期间没有执行日志重写，您仍然可以通过停止服务器、删除最新命令并重新启动 Redis 来保存您的数据集。

#### 劣势

相同数据集的数据而言AOF文件要远大于RDB文件，恢复速度慢于RDB

AOF运行效率要慢于RDB，每秒同步策略效率较好，不同步效率和RDB相同

<img src="03-Redis.assets/42.AOF劣势.png" style="zoom:67%;" />

- AOF文件通常比相同数据集的等效 RDB 文件大。
- 根据确切的 fsync策略，AOF可能比 RDB 慢。一般来说，将fsync 设置为每秒性能仍然非常高，并且在禁用 fsync的情况下，即使在高负载下它也应该与 RDB 一样快。即使在巨大的写入负载的情况下，RDB仍然能够提供关于最大延迟的更多保证。

### AOF重写机制

#### 介绍

由于AOF持久化是Redis不断将写命令记录到 AOF 文件中，随着Redis不断的进行，AOF 的文件会越来越大,文件越大，占用服务器内存越大以及 AOF 恢复要求时间越长。
为了解决这个问题，**Redis新增了重写机制**，当AOF文件的大小超过所设定的峰值时，Redis就会**自动**启动AOF文件的内容压缩.只保留可以恢复数据的最小指令集或者可以**手动使用命令 bgrewriteaof 来重新**。

<img src="03-Redis.assets/43.AOF重写.png" style="zoom:67%;" />

一句话：启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集。

#### 触发机制

- **官网默认配置**

![](03-Redis.assets/44.AOF重写官网默认配置.png)

- **自动触发**

满足配置文件中的选项后，Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时

- **手动触发**

客户端向服务器发送bgrewriteaof命令

#### 案例

**需求说明：**

$\textcolor{red}{启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集。}$
$\textcolor{blue}{举个例子:}$ 比如有个key
开始你 set k1 v1
然后改成 set k1 v2
最后改成 set k1 v3
如果不重写，那么这3条语句都在aof文件中，内容占空间不说启动的时候都要执行一遍，共计3条命令但是，我们实际效果只需要set k1 v3这一条，所以，
开启重写后，只需要保存set k1 3就可以了只需要保留最后一次修改值，相当于给aof文件瘦身减肥，性能更好。
AOF重写不仅降低了文件的占用空间，同时更小的AOF也可以更快地被Redis加载。

**需求验证：**

$\textcolor{green}{启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集。}$

**步骤：**

- 前期配置准备：

  1. 开启aof，appendonly yes，设置aof持久化开启

  2. 重写峰值修改为1k

     ![](03-Redis.assets/45.aof重写峰值修改.jpg)

  3. 关闭混合，设置为no

     ![](03-Redis.assets/46.aof-rdb混合关闭.jpg)

  4. 删除执勤啊的全部aof和rdb，清除干扰项


- 自动触发案例01

  1. 完成上述正确配置，重启redis服务器，执行 set k1 v1 查看aof文件是否正常

     ![](03-Redis.assets/47.aof文件.jpg)

  2. 查看aof三大 配置文件

     appendonly.aof.1.base.aof；appendonly.aof.1.incr.aof；appendonly.aof.manifest

  3. k1不停的更新值

     ![](03-Redis.assets/48.aof重写后文件.jpg)

  4. 重写触发(合并相同的)

<img src="03-Redis.assets/49.aof重写后的base文件.jpg" style="zoom:67%;" />

- 手动触发案例02

  客户端向服务器发送bgrewriteaof命令

![](03-Redis.assets/50.aof重写手动触发.jpg)

结论

**也就是说AOF文件重写并不是对原文件进行重新整理，而是直接读取服务器现有的键值对，然后用一条命令去代替之前记录这个键值对的多条命令，生成一个新的文件后去替换原来的AOF文件。**

AOF文件重写触发机制:通过 redis.conf配置文件中的 auto-aof-rewrite-percentage:默认值为100，以及auto-aof-rewrite-min-size: 64mb配置，也就是说默认Redis会记录上次重写时的AOF大小，**默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时触发。**

#### 重写原理

1. 在重写开始前，redis会创建一个“重写子进程”，这个子进程会读取现有的AOF文件，并将其包含的指令进行分析压缩并写入到一个临时文件中。
2. 与此同时，主进程会将新接收到的写指令一边累积到内存缓冲区中，一边继续写入到原有的AOF文件中，这样做是保证原有的AOF文件的可用性，避免在重写过程中出现意外。
3. 当“重写子进程”完成重写工作后，它会给父进程发一个信号，父进程收到信号后就会将内存中缓存的写指令追加到新AOF文件中
4. 当追加结束后，redis就会用新AOF文件来代替旧AOF文件，之后再有新的写指令，就都会追加到新的AOF文件中
5. 重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似

### AOF 优化配置项详解

配置文件 APPEND ONLY MODE模块

![](03-Redis.assets/51. APPEND ONLY MODE模块.jpg)

###  总结

![](03-Redis.assets/52.AOF小总结.jpg)

## RDB-AOF混合持久化

### 官网建议

<img src="03-Redis.assets/53.混合持久化官网建议.jpg" style="zoom:80%;" />

### RDB VS AOF

**问题：**

可否共存？

如果共存听谁的？

**Redis配置文档解答：RDB和AOF共存时会优先加载AOF文件**

![](03-Redis.assets/54.优先加载AOF.jpg)

**$\textcolor{red}{数据恢复顺序和加载流程}$**

![](03-Redis.assets/55.混合持久化加载顺序.jpg)

### 选择

- RDB持久化方式能够在指定的时间间隔对你的数据进行快照存储。
- AOF持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以redis协议追加保存每次写的操作到文件末尾。

### 同时开启两种持久化方式

- 在这种情况下，$\textcolor{red}{当redis重启的时候会优先载入AOF文件来恢复原始的数据}$，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整。
- RDB的数据不实时，同时使用两者时服务器重启也只会找AOF文件。但是作者也不建议只使用AOF方式备份，因为RDB更适合用于备份数据库（AOF在不断的变化不好备份），留着RDB作为一个万一的手段。

### 推荐方式

RDB+AOF混合方式

1开启混合方式设置
$\textcolor{red}{设置aof-use-rdb-preamble的值为yes， yes表示开启，设置为no表示禁用}$
2 RDB+AOF的混合方式--------->结论:RDB镜像做全量持久化，AOF做增量持久化
先使用RDB进行快照存储，然后使用AOF持久化记录所有的写操作，当重写策略满足或手动触发重写的时候，将最新的数据存储为新的RDB记录。这样的话，重启服务的时候会从RDB和AOF两部分恢复数据，既保证了数据完整性，又提高了恢复数据的性能。简单来说:混合持久化方式产生的文件一部分是RDB格式，一部分是AOF格式。----》AOF包括了RDB头部+AOF混写

![](03-Redis.assets/56.混合持久化.jpg)

## 纯缓存模式

### 同时关闭RDB+AOF，专心做缓存

1. save ""  -- 禁用RDB

   禁用RDB持久化模式下，我们仍然可以使用命令save、bgsave生成RDB文件

2. appendonly no  -- 禁用AOF

   禁用AOF持久化模式下，我们仍然可以使用命令bgrewriteaof生成AOF文件

# 九、redis事务

## 介绍

官网：https://redis.io/docs/manual/transactions/

可以一次执行多个命令，本质是一组命令的集合，一个事务中的所有命令都会序列化，$\textcolor{red}{按顺序地串行化执行而不会被其他命令插入，不许加塞}$

一个队列中，一次性、顺序性、排他性的执行一系列命令

==Redis不提供事务回滚的功能，开发者必须在事务执行出错后，自行恢复数据库状态==

**Redis事务 VS 数据库事务**

| Redis事务            | 数据库事务                                                   |
| -------------------- | ------------------------------------------------------------ |
| 1.单独的隔离操作     | Redis的事务仅仅是保证事务里的操作会被连续独占的执行，redis命令执行是单线程架构，在执行完事务内所有指令前是不可能再去同时执行其他客户端的请求的 |
| 2.没有隔离级别的概念 | 因为事务提交前任何指令都不会被实际执行，也就不存在”事务内的查询要看到事务里的更新，在事务外查询不能看到”这种问题了 |
| 3.不保证原子性       | Redis的事务**不保证原子性**，也就是不保证所有指令同时成功或同时失败，只有决定是否开始执行全部指令的能力，没有执行到一半进行回滚的能力 |
| 4.排它性             | Redis会保证一个事务内的命令依次执行，而不会被其它命令插入    |

## 常用命令

官网 ：https://redis.io/docs/manual/transactions/

<img src="03-Redis.assets/image-20231009152503731.png" alt="image-20231009152503731" style="zoom:67%;" />

**Redis 事务命令**

![image-20231009152543478](03-Redis.assets/image-20231009152543478.png)

### case1：正常执行 MULTI EXEC

![](03-Redis.assets/3.Redis事务正常执行.jpg)

### case2：放弃事务 MULTI DISCARD

![image-20231009215854065](03-Redis.assets/image-20231009215854065.png)

### case3：全体连坐

官网说明：

![image-20231009220110481](03-Redis.assets/image-20231009220110481.png)

![image-20231009220100673](03-Redis.assets/image-20231009220100673.png)

一个语法出错，全体连坐。如果任何一个命令语法有错，Redis会直接返回错误，所有的命令都不会执行

### case4：冤头债主

**官网说明：**

![](03-Redis.assets/7.Redis事务冤头债主.jpg)

**补充：**

![](03-Redis.assets/8.Redis不提供回滚功能.jpg)

![](03-Redis.assets/9.Redis冤头债主.jpg)

$\textcolor{red}{注意和传统数据库事务的区别，不一定要么全部成功要么全部失败}$

### case5：watch监控

- **Redis使用Watch来提供乐观锁定，类似于CAS(Check-and-Set)**

  1. 悲观锁：悲观锁(Pessimistic Lock)，顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁

  2. 乐观锁：乐观锁(Optimistic Lock)，顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，$\textcolor{red}{所以不会上锁}$，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据。

     $\textcolor{red}{乐观锁策略：提交版本必须大于记录当前版本才能执行更新}$

  3. CAS

     <img src="03-Redis.assets/10.Redis的CAS.jpg" style="zoom:67%;" />

- **watch key [key ...]**

  1. 初始化k1和balance两个key，先监控在开启multi，保证两个key变动在同一个事务内

     ![](03-Redis.assets/11.Redis-watch.jpg)

  2. 有加塞篡改：watch命令是一种乐观锁的实现，Redis在修改的时候会检测数据是否被更改，如果被更改了，则执行失败

     ![image-20231009221931423](03-Redis.assets/image-20231009221931423.png)

     <font color=red>注意: 图中3和4不管哪个先执行，最终的结果都是整个事务执行失败</font>>

- **unwatch**  放弃监控

  ![](03-Redis.assets/14.unwatch.jpg)

- **小结**

  一旦执行了exec之前加的监控锁都会被取消掉

  当客户端连接丢失的时候(比如退出链接)，所有东西都会被取消监视

## 总结

$\textcolor{red}{开启}$：以multi开始一个事务

$\textcolor{red}{入队}$：将多个命令入队到事务中，接到这些命令并不会立即执行，而是放到等待执行的事务队列里面

$\textcolor{red}{执行}$：有exec命令触发事务

# 十、Redis管道

 **面试题：如何优化频繁命令往返造成的性能瓶颈？**

Redis是一种基于**客户端-服务端模型**以及请求/响应协议的TCP服务。一个请求会遵循以下步骤:
1客户端向服务端发送命令分四步(发送命令→命令排队→命令执行-返回结果)，并监听Socket返回，通常以阻塞模式等待服务端响应。
2服务端处理命令，并将结果返回给客户端。
上述两步称为: Round Trip Time(简称RTT,数据包往返于两端的时间)。

<img src="03-Redis.assets/1.Redis客户端与服务端交互模型.jpg" style="zoom:67%;" />

如果同时需要执行大量的命令，那么就要等待上一条命令应答后再执行，这中间不仅仅多了RTT (Round Time Trip) ，而且还频繁调用系统IO， 发送网络请求，同时需要redis调用多次read()和write()系统方法， 系统方法会将数据从用户态转移到内核态，这样就会对进程上下文有比较大的影响了，性能不太好，0(π_ π)0。这时候Redis管道就出现了。

## 介绍

**上述问题的解决思路**

管道(pipeline)可以一次性发送多条命令给服务端，**服务端依次处理完毕后，通过一 条响应一次性将结果返回，通过减少客户端与redis的通信次数来实现降低往返延时时间**。pipeline实现的原理是队列，先进先出特性就保证数据的顺序性。

<img src="03-Redis.assets/2.Redis pipeline交互模型.jpg" style="zoom:67%;" />

 官网：https://redis.io/docs/manual/pipelining/

 定义：pipeline是为了解决RTT往返时，仅仅是将命令打包一次性发送，对整个Redis的执行不造成其他任何影响

## 使用

**批处理命令变种优化措施**，类似Redis的原生批命令(mget和mset)

![image-20231009224455815](03-Redis.assets/image-20231009224455815.png)

## 总结

- pipeline与原生批量命令对比
  1. 原生批量命令是原子性(例如：mset、mget)，$\textcolor{red}{pipeline是非原子性的}$
  2. 原生批量命令一次只能执行一种命令，pipeline支持批量执行不同命令
  3. 原生批量命令是服务端实现，而pipeline需要服务端与客户端共同完成


- 管道与事务对比
  1. 事务具有原子性，管道不具有原子性
  2. 管道一次性将多条命令发送到服务器，事务是一条一条的发，事务只有在接收到exec命令后才会执行，管道不会
  3. 执行事务时会阻塞其他命令的执行，而执行管道中的命令时不会
- 使用pipeline注意事项
  1. pipeline缓冲的指令只是会依次执行，不保证原子性，如果执行中指令发生异常，将会继续执行后续的指令
  2. **使用pipeline组装的命令个数不能太多**，不然数量过大客户端阻塞的时间可能过久，同时服务端此时也被迫回复一个队列答复，占用很多内存

# 十一、Redis发布订阅(了解)

## 介绍

**定义：**是一种消息通信模式：发送者(PUBLISH)发送消息，订阅者(SUBSCRIBE)接收消息，可以实现进程间的消息传递

**官网：**https://redis.io/docs/manual/pubsub/

Redis可以实现消息中间件MQ的功能，通过发布订阅实现消息的引导和分流。但是目前不推荐使用该功能，专业的事情交给专业的中间件处理，redis就做好分布式缓存功能

## 作用

Redis客户端可以订阅任意数量的频道，类似我们微信关注多个公众号

![](03-Redis.assets/1.Redis订阅.jpg)

当有新消息通过publish命令发送给频道channel1时，订阅客户端都会收到消息

![](03-Redis.assets/2.Redis发布.jpg)

## 总结

<img src="03-Redis.assets/3.发布订阅小总结.jpg" style="zoom:67%;" />

## 常用命令

![](03-Redis.assets/4.发布订阅常用命令.jpg)

- SUBSCRIBE channel [channel ...]

  订阅给定的一个或多个频道的信息

  $\textcolor{red}{推荐先执行订阅然后在发布，订阅成功之前发布的消息是收不到的}$

  订阅的客户端每次可以收到一个3个参数的消息

  1. 消息种类
  2. 始发频道的名称
  3. 实际的消息内容

![](03-Redis.assets/5.消息接收参数.jpg)

- PUBLISH channel message

  发布消息到指定的频道

- PSUBSCRIBE pattern [pattern ...]

  按照模式批量订阅，订阅一个或多个符合给定模式(支持*号？号之类的)的频道

- PUBSUB subcommand [argument [argument ...]]

  查看订阅与发布系统

  PUBSUB CHANNELS

  ​	由活跃频道组成的列表

  ![](03-Redis.assets/6.PUBSUB CHANNELS.jpg)

  PUBSUB NUMSUB [channel [channel ...]]

  ​	某个频道有几个订阅者

  ![](03-Redis.assets/7.PUBSUB NUMSUB.jpg)

  PUBSUB NUMPAT

  ​	只统计使用PSUBSCRIBE命令执行的返回客户端订阅的唯一$\textcolor{red}{模式的数量}$

  ![](03-Redis.assets/9.模式订阅.jpg)


- UNSUBSCRIBE [channel [channel ...]]

  退订给定的频道

- PUNSUBSCRIBE [pattern [pattern ...]]

  退订所有给定模式的频道

## 案例

1. 开启3个客户端，演示客户端A、B订阅消息，客户端C发布消息

   ![](03-Redis.assets/8.订阅演示.jpg)

2. 演示批量订阅和发布

   ![](03-Redis.assets/10.批量订阅和发布.jpg)

3. 取消订阅

![](03-Redis.assets/11.取消订阅.jpg)

## 总结

<font color = 'red'> **可以实现消息中间件MQ的功能，通过发布订阅实现消息的引导和分流。但是不推荐使用该功能，专业的事情交给专业的中间件处理，redis就做好分布式缓存功能**</font>

PUB/SUB缺点

1. 发布的消息在Redis系统中不能持久化，因此，必须先执行订阅，在等待消息发布。如果先发布了消息，那么该消息由于没有订阅者，消息将被直接丢弃
2. 消息只管发送，对于发布者而言消息是即发即失，不管接受，也没有ACK机制，无法保证消息的消费成功
3. 以上的缺点导致Redis的Pub/Sub模式就像个小玩具，在生产环境中几乎无用武之地，为此Redis5.0版本新增了Stream数据结构，不但支持多播，还支持数据持久化，相比Pub/Sub更加的强大



# 十二、Redis复制(replica)

## 介绍

官网地址：https://redis.io/docs/management/replication/

<img src="03-Redis.assets/1.数据复制.jpg" style="zoom:67%;" />

<font color=red>主从复制，master以写为主，slave以读为主，当master数据变化的时候，自动将新的数据异步同步到其他的slave数据库</font>

## 作用

- 读写分离
- 容灾恢复
- 数据备份
- 水平扩容支撑高并发

## 使用

$\textcolor{red}{原则: 配从(库)不配主(库)}$

权限:  master如果配置了requirepass参数，需要密码登录 ，那么slave就要配置masterauth来设置校验密码，否则的话master会拒绝slave的访问请求

​                     <img src="03-Redis.assets/2.从机配置主机密码.jpg" style="zoom: 80%;" />

  基本操作命令:

+ **info replication** ：可以查看复制结点的主从关系和配置信息
+ **replicaof 主库IP 主库端口** ：一般写入进Redis.conf配置文件内，重启后依然生效

+ **slaveof 主库IP 主库端口** ：

  + 每次与master断开之后，都需要重新连接，除非你配置进了redis.conf文件；

  + 在运行期间修改slave节点的信息，如果该数据库已经是某个主数据库的从数据库，那么会停止和原主数据库的同步关系 $\textcolor{red}{转而和新的主数据库同步，重新拜码头}$

+  **slaveof no one** ：使当前数据库停止与其他数据库的同步，$\textcolor{red}{转成主数据库，自立为王}$

## 案例-配置文件

### 架构说明

一个Master两个Slave，三台虚拟机，每台都安装redis

<img src="03-Redis.assets/3.主从架构.jpg" style="zoom:80%;" />

拷贝多个redis.conf文件

redis6379.conf、redis6380.conf、redis6381.conf

### 口诀

三台虚拟机需要能相互ping通且需要注意防火墙配置

三大命令：

1. 主从复制

   replicaof 主库IP 主库端口，配从(库)不配主(库)

2. 改换门庭

   slaveof 新主库IP 新主库端口

3. 自立为王

   slaveof no one

### 修改配置文件

redis6379.conf(主机)为例，步骤如下：

1. 开启daemonize yes

   ![](03-Redis.assets/4.配置daemonize .jpg)

2. 注释掉bind 127.0.0.1

3. protected-mode no

   ![](03-Redis.assets/5.配置protected-mode.jpg)

4. 指定端口

5. 指定当前工作目录，dir

   ![](03-Redis.assets/6.配置dir.jpg)

6. pid文件名字，pidfile

   ![](03-Redis.assets/7.配置pidfile.jpg)

7. log文件名字，logfile

   **如果日志文件和启动文件同级，这里可以配置为./6379.log，否则这里一定要写绝对路径，是个巨坑！！！**

   ![](03-Redis.assets/8.配置logfile.jpg)

8. 设置密码 requiredpass 

   ![](03-Redis.assets/9.配置requiredpass .jpg)

9. dump.rdb名字

   ![](03-Redis.assets/10配置dump.rdb名字.jpg)

10. aof文件，appendfilename

    ![](03-Redis.assets/11.配置appendfilename.jpg)

11. **从机需要:**(建议主从都写)

    **从机访问主机的通行密码masterauth**，必须配置

    ![](03-Redis.assets/12.从机配置主机密码.jpg)
    
    **从机需要配置，主机不用**

### 一主二仆

#### 方案1：配置文件固定写死主从关系

- 配置文件执行：replicaof 主库IP 主库端口

- 配从(库)不配(主)库：配置从机

  ![image-20231010083145645](03-Redis.assets/image-20231010083145645.png)


- 先master后两台slave依次启动

   ![](03-Redis.assets/image-20231010083820088.png)

- 主从关系查看

  主机的日志

<img src="03-Redis.assets/image-20231010084009939.png" alt="image-20231010084009939" style="zoom:80%;" />

​	从机的日志

<img src="03-Redis.assets/17.从机日志.jpg" style="zoom:80%;" />

+ 命令查看主从关系	

命令：info replication命令查看 

![](03-Redis.assets/18.命令查看主从关系.jpg)

#### 方案2：命令操作手动主从关系指令

1. 从机停机去掉配置文件中的配置项，3台目前都是主机状态，各不从属

   <img src="03-Redis.assets/22.去掉从机配置.png" style="zoom:80%;" />

2. 3台都是master

   ![](03-Redis.assets/23.3台master.png)

3. 预设的从机上执行命令

   salveof 主库IP 主库端口

   ![](03-Redis.assets/24.slaveof效果.png)

#### <font color = red>主从问题</font>

**配置 VS 命令的区别:**

+ 配置，持久稳定永久生效；

+ 命令，当成生效

**用命令指定:**

1. Q：用命令使用的话，2台从机重启后，关系还在吗？

   A：不会存在了

**写入配置文件固定写死:**

1. Q：从机可以执行写命令吗？ 

   A：**不可以，从机只能读**

   ![](03-Redis.assets/19.从机只能读.png)

2. Q：从机切入点问题？

   Q：$\textcolor{blue}{slave是从头开始复制还是从切入点开始复制?}$
   A：master启动，写到k3
   slave1跟着master同时启动，跟着写到k3
   slave2写到k3后才启动，那之前的是否也可以复制?
   $\textcolor{blue}{Y，首次一锅端，后续跟随，master写，slave跟}$

3. Q：主机shutdown后，从机会上位吗？

   A：**从机不动，原地待命，从机数据可以正常使用，等待主机重启归来**

   ![](03-Redis.assets/20.主机挂掉从机不上位.png)

4. Q：主机shutdown后，重启后主从关系还在吗？从机还能否顺利复制？

   A：主从关系依然存在，从机依旧是从机，可以顺利复制

   ![](03-Redis.assets/21.主机重启.png)

5. Q：某台从机down后，master继续，从机重启后它能跟上大部队吗？

   A：可以，类似于从机切入点问题

### 薪火相传

- 上一个slave可以是下一个slave的master，slave同样可以接收其他slaves的连接和同步请求，那么该slave作为了链条中下一个的master,可以有效减轻主master的写压力
- 中途变更转向:会清除之前的数据，重新建立主从关系并拷贝最新的
- slaveof 新主库IP 新主库端口

### 反客为主

slaveof no one  使当前数据库停止与其他数据库的同步关系

## 复制原理和工作流程

### slave启动，同步初请

- slave启动成功链接到master后会发送一个sync命令
- slave首次全新连接master，**一次完全同步(全量复制)将被自动执行**，slave自身原有数据会被master数据覆盖清除

### 首次连接，全量复制

- master节点收到sync命令后会开始在后台保存快照(即RDB持久化，主从复制时会触发RDB)，同时收集所有接收到的用于修改数据集的命令并缓存起来，master节点执行RDB持久化完后，master将RDB快照文件和所有缓存的命令发送到所有slave，以完成一次完全同步
- 而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中，从而完成复制初始化

### 心跳持续，保持通信

- repl-ping-replica-period 10

  ![image-20231010090053818](03-Redis.assets/image-20231010090053818.png)

### 进入平稳，增量复制

- master继续将新的所有收集到的修改命令自动依次传送给slave，完成同步

### 从机下线，重连续传

- master会检查backlog里面的offset，master和slave都会保存一个复制的offset还有一个masterId，offset是保存在backlog中的。$\textcolor{red}{master只会把已经缓存的offset后面的数据复制给slave，类似断点续传}$

## 复制的缺点

- 复制延时，信号衰减

  由于所有的写操作都是先在Master上操作，然后同步更新到Slave上，所以从Master同步到Slave机器有一定的延迟，当系统很繁忙的时候，延迟问题会更加严重，Slave机器数量的增加也会使这个问题更加严重。

  <img src="03-Redis.assets/26.主从同步延迟.png" style="zoom:80%;" />

- master挂了

  默认情况下，不会在slave节点中自动选一个master

  那每次都要人工干预？ –> <font color = red>无人值守变成刚需, 所以引出后续的技术</font>

# 十三、Redis哨兵

## 作用

吹哨人巡查监控后台master主机是否故障，如果故障了根据$\textcolor{red}{投票数}$自动将某一个从库转换为新主库，继续对外服务

作用：**俗称无人值守运维**

<img src="03-Redis.assets/image-20231010090947292.png" alt="image-20231010090947292" style="zoom:80%;" />

官网理论：https://redis.io/docs/management/sentinel/

<img src="03-Redis.assets/2.哨兵能干嘛.png" style="zoom:67%;" />

**主从监控**：监控主从redis库运行是否正常

**消息通知**：哨兵可以将故障转移的结果发送给客户端

**故障转移**：如果master异常，则会进行主从切换，将其中一个slave作为新master

**配置中心**：客户端通过连接哨兵来获得当前Redis服务的主节点地址

## 配置参数说明

### Redis Sentinel架构

- 3个哨兵：自动监控和维护集群，不存放数据，只是吹哨人
- 1主2从：用于数据读取和存放

<img src="03-Redis.assets/image-20231010091324192.png" alt="image-20231010091324192" style="zoom: 67%;" />

### 操作步骤

1. /myredis目录下拷贝sentinel.conf文件按，名字绝对不能错

2. 先看看/opt目录下默认的sentinel.conf文件的内容

   <img src="03-Redis.assets/4.sentinel.conf目录地址.png" style="zoom:67%;" />

3. 重要参数项说明

   - bind：服务监听地址，用于客户端连接，默认本机地址

   - daemonize：是否以后台daemon方式运行

   - protected-model：安全保护模式

   - port：端口

   - logfile：日志文件路径

   - pidfile：pid文件路径

   - dir：工作目录

   - ```
     sentinel monitor <master-name> <ip> <redis-port> <quorum>
     ```

     设置要监控的master服务器

     quorum表示最少有几个哨兵认可客观下线，同意故障迁移的法定票数

     ![](03-Redis.assets/5.quorum票数解释.png)
     
     网络是不可靠的有时候一个sentinel会因为网络堵塞而误以为master redis已经死掉，在sentinel集群环境下需要多个sentinel互相沟通来确认某个master是否真的死掉了，quorum这个参数是进行客观下线的一个依据，意思是至少有quorum个sentinel认为这个master有故障，才会对这个master进行下线以及故障转移。因为有的时候，某个sentinel节点可能因为自身网络原因，导致无法连接master，而此时master并没有出现故障，所以，这就需要多个sentinel都一致认为改master有问题，才可以进行下一步操作，这就保证了公平性和高可用。


   - ```
     sentinel auth-pass <master-name> <password>
     ```

     master设置了密码，连接master服务的密码

   - 其他

     | sentinel down-after-milliseconds <master-name>  <milliseconds> | 指定多少毫秒之后，主节点没有应答哨兵，此时哨兵主观上认为主节点下线 |
     | ------------------------------------------------------------ | ------------------------------------------------------------ |
     | sentinel parallel-syncs <master-name> <nums>                 | 表示允许并行同步的slave个数，当Master挂了后，哨兵会选出新的Master，此时，剩余的slave会向新的master发起同步数据 |
     | sentinel failover-timeout <master-name> <milliseconds>       | 故障转移的超时时间，进行故障转移时，如果超过设置的毫秒，表示故障转移失败 |
     | sentinel notification-script <master-name> <script-path>     | 配置当某一事件发生时所需要执行的脚本                         |
     | sentinel client-reconfig-script <master-name> <script-path>  | 客户端重新配置主节点参数脚本                                 |

## sentinel通用配置及主从配置

**sentinel26379.conf、sentinel26380.conf、sentinel26381.conf**

```properties
bind 0.0.0.0
daemonize yes
protected-mode no
port 26379  #
logfile "/myredis/sentinel26379.log"      #
pidfile /var/run/redis-sentinel26379.pid  #
dir /myredis                              #自定义存放目录
sentinel monitor mymaster 192.168.111.169 6379 2 #
sentinel auth-pass mymaster 111111               #
```

<img src="03-Redis.assets/6.sentinel配置.png" style="zoom:67%;" />

**最终配置**

![](03-Redis.assets/7.sentinel集群配置.png)

## master主机配置文件

理论上sentinel配置文件应该部署在不同的服务器上，做成集群，但是本次演示将其放到一台机器上

![](03-Redis.assets/8.sentinel部署.png)

### 先启动一主二从3个redis实例，测试正常的主从复制

- 架构说明

  ![image-20231010092354863](03-Redis.assets/image-20231010092354863.png)


- 主机6379配置修改

  ![](03-Redis.assets/10.主机配置master访问密码.png)

  6379后续可能会变成从机，需要设置访问新主机的密码，所以**此处会设置masterauth**，**不然后续可能会报错 master_link_status:down**

- 3台不同的虚拟机实例，启动三台真是机器实例并连接

  ```shell
  redis-server redis6379.conf
  redis-server redis6380.conf
  redis-server redis6381.conf
  
  redis-cli -a 123456 -p 6379
  redis-cli -a 123456 -p 6380
  redis-cli -a 123456 -p 6381
  ```

### 再启动3个哨兵监控后再测试一次主从复制

  sentinel的两种启动方式

  <img src="03-Redis.assets/image-20231010092649534.png" alt="image-20231010092649534" style="zoom: 80%;" />

  ```shell
  redis-server sentinel26379.conf --sentinel
  redis-server sentinel26380.conf --sentinel
  redis-server sentinel26381.conf --sentinel
  ```

![](03-Redis.assets/12.sentinel启动结果查询.png)

启动后查看日志文件, 我们会发现sentinel配置文件会自动在配置文件中加上部分配置

<img src="03-Redis.assets/13.sentinel文件重写.png" style="zoom: 80%;" />

## 主节点异常情况

### 原有的master挂了

- 我们自己手动关闭6379服务器，模拟master挂了

- 问题思考

  1. 两台从机数据是否OK
  2. 是否会从剩下的2台机器上选出新的master
  3. 之前down机的master机器重启回来，谁将会是新老大？会不会双master冲突

- 答案

  1. 两台从机数据OK

     <img src="03-Redis.assets/image-20231010094415198.png" alt="image-20231010094415198" style="zoom:67%;" />

     错误警告: error: broken pipe

     ![image-20231010094224701](03-Redis.assets/image-20231010094224701.png)

     ![image-20231010094205427](03-Redis.assets/image-20231010094205427.png)

  2. 会投票选出新的master主机

     日志文件

     ![](03-Redis.assets/15.sentinel选举.png)
  
  3. 谁是master，限本次案例
  
     本案例中6381被选举为新的master，上位成功
  
     **重启6379之后，它会从原来的master降级为slave, 会进行薪火相传**
  
     6380还是slave，只不过是换了一个新老大6381(从跟随6379变成跟随6381)

### 挂掉后master配置文件的自动变化

老master的redis6379.conf文件

![](03-Redis.assets/16.旧master配置文件重写.jpg)

新master的redis6381.conf文件

![](03-Redis.assets/17.slave升master配置文件重写.jpg)

**结论**

$\textcolor{red}{文件的内容，在运行期间会被sentinel动态进行更改}$

```
Master-Slave切换后，master_redis.conf、slave_redis.conf、sentinel.conf的内容都会发生改变，即master_redis.conf中会多一行slaveof的配置，而升级为master的主机会去掉原来的slaveof配置，sentinel.conf的监控目标会随之调换}
```

### 其他备注

生产上都是不同机房不同服务器，很少出现3个哨兵全部挂掉的情况

可以同时监控多个master，一行一个

<img src="03-Redis.assets/18.多master监控.jpg" style="zoom:67%;" />

## 哨兵运行流程和选举原理

当一个主从配置中master失效后，sentinel可以选举出一个新的master用于自动接替原master的工作，主从配置中的其他redis服务器自动指向新的master同步数据，一般建议sentinel采取奇数台，防止某一台sentinel无法连接到master导致误切换

### 运行流程，故障切换

![image-20231010103004712](03-Redis.assets/image-20231010103004712.png)

- 三个哨兵监控一主二从，正常运行中

  <img src="03-Redis.assets/image-20231010102718950.png" alt="image-20231010102718950" style="zoom: 67%;" />

- SDown主观下线(Subjectively Down)

  1. SDOWN（主观不可用）是**单个sentinel自己主观上**检测到的关于master的状态，从sentinel的角度来看，如果发送了PING心跳后，在一定时间内没有收到合法的回复，就达到了SDOWN的条件。

  2. sentinel配置文件中的down-after-milliseconds设置了判断主观下线的时间长度

  3. 说明

     ![](03-Redis.assets/19.主观下线说明.jpg)

- ODown客观下线(Objectively Down)

  1. ODOWN需要一定数量的sentinel，$\textcolor{red}{多个哨兵达成一致意见}$才能认为一个master客观上已经宕机

  2. 说明

     ![](03-Redis.assets/20.ODown客观下线说明.jpg)

     $\textcolor{red}{\large quorum这个参数是进行客观下线的一个依据，法定人数/法定票数}$
     意思是至少有quorum个sentinel认为这个master有故障才会对这个master进行下线以及故障转移。因为有的时候，某个sentinel节点可能因为自身网络原因导致无法连接master，而此时master并没有出现故障，所以这就需要多个sentinel都一致认为该master有问题，才可以进行下一步操作，这就保证了公平性和高可用。

- 选举出领导者哨兵(哨兵中选出兵王)

  ![](03-Redis.assets/21.主哨兵解释.jpg)

  1. 当主节点被判断客观下线后，各个哨兵节点会进行协商，先选举出一个$\textcolor{red}{\large 领导者哨兵节点（兵王）}$并由该领导者也即被选举出的兵王进行failover（故障转移）。

     哨兵日志文件解读分析

     ![](03-Redis.assets/22.哨兵兵王选举.jpg)

  2. 哨兵领导者，兵王如何选出来的？-> **Raft算法**

     ![](03-Redis.assets/23.Raft算法.jpg)

     监视该主节点的所有哨兵都有可能被选为领导者，选举使用的算法是Raft算法;Raft算法的基本思路是先到先得:即在一轮选举中，哨兵A向B发送成为领导者的申请、如果B没有同意过其他哨兵，则会同意A成为领导者。


- 由兵王开始推动故障切换流程并选出新的master

  1. 新主登基

     - $\textcolor{red}{\large 某个Slave被选中成为新Master}$

     - 选出新master的规则，剩余Slave节点健康前提下，会按下图规则进行选举

       <img src="03-Redis.assets/image-20231010102411939.png" alt="image-20231010102411939" style="zoom:67%;" />

       + redis.conf文件中，优先级slave-priority或者replica-priority最高的从节点(数字越小优先级越高)

       ![](03-Redis.assets/25.从节点升级为主节点默认优先级.jpg)

       + 复制偏移位置offset最大的从节点(也就是在master还没有宕机时，复制到数据比其他Slave要多)
       +  最小Run ID的从节点，字典顺序，ASCII码

  2. 群臣俯首

     - $\textcolor{red}{\large 一朝天子一朝臣，换个码头重新拜}$
     - 执行slaveof no one命令让选出来的从节点成为新的主节点，并通过slaveof命令让其他节点成为其从节点
     - sentinel leader会对选举出的新master执行slaveof on one操作，将其提升为master节点
     - sentinel leader向其他slave发送命令，让剩余的slave成为新的master节点的slave

  3. 旧主拜服( 如果老master回来也得认怂，会被降级为slave)

     - 老master重新上线后，会将它设置为新选出的master的从节点
     - sentinel leader会让原来的master降级为slave并恢复正常工

总结

上述的failover操作均由sentinel自己独自完成，完全不需要人工干预

<img src="03-Redis.assets/26.选举新master总结.jpg" style="zoom: 80%;" />

## 哨兵使用建议

1. 哨兵节点的数量应为多个，哨兵本身应该集群，保证高可用
2. 哨兵节点的数量应该是奇数
3. 各个哨兵节点的配置应一致
4. 如果哨兵节点部署在Docker等容器里面，尤其要注意端口的正确映射
5. 哨兵集群+主从复制，并不能保证数据零丢失，$\textcolor{red}{\large所以需要使用集群}$

# 十四、Redis集群(cluster)

## 介绍

定义:  <font color=red>由于数据量过大，单个Master复制集难以承担</font>，因此需要对多个复制集进行集群，形成水平扩展每个复制集只负责存储整个数据集的一部分，这就是Redis的集群，其作用是提供在多个Redis节点间共享数据的程序集。

官网：https://redis.io/docs/reference/cluster-spec/

**Redis集群是一个提供在多个Redis节点间共享数据的程序集，Redis集群可以支持多个master**

<img src="03-Redis.assets/2.redis集群图.jpg" style="zoom:80%;" />

## 作用

- Redis集群支持多个master，每个master又可以挂载多个slave
  1. 读写分离
  2. 支持数据的高可用
  3. 支持海量数据的读写存储操作
- 由于Cluster自带Sentinel的故障转移机制，内置了高可用的支持，$\textcolor{red}{\large 无需再去使用哨兵功能}$
- 客户端与Redis的节点连接，不再需要连接集群中所有的节点，只需要任意连接集群中的一个可用节点即可
- $\textcolor{red}{\large 槽位slot}$负责分配到各个物理服务节点，由对应的集群来负责维护节点、插槽和数据之间的关系

## 集群算法-分片-槽位slot

### 官网出处

![](03-Redis.assets/3.槽位官网说明.jpg)

翻译说明：![](03-Redis.assets/4.官网翻译.jpg)

### redis集群的槽位slot

Redis集群的数据分片

Redis集群没有使用一致性hash 而是引入了哈希槽的概念。

Redis集群有16384个哈希槽每个key通过CRC16校验后对16384取模来决定放置哪个槽，集群的每个节点负责一部分hash槽，举个例子，比如当前集群有3个节点，那么：

<img src="03-Redis.assets/5.槽位示例.jpg" style="zoom:80%;" />

### redis集群的分片

| 分片是什么            | 使用Redis集群时我们会将存储的数据分散到多台redis机器上，这称为分片。简言之，集群中的每个Redis实例都被认为是整个数据的一个分片。 |
| --------------------- | ------------------------------------------------------------ |
| 如何找到给定key的分片 | 为了找到给定key的分片，我们对key进行CRC16(key)算法处理并通过对总分片数量取模。然后，$\textcolor{red}{\large使用确定性哈希函数}$，这意味着给定的key$\textcolor{red}{\large将多次始终映射到同一个分片}$，我们可以推断将来读取特定key的位置。 |

 ### 分片和槽位的优势

$\textcolor{blue}{\large 最大优势，方便扩缩容和数据分派查找}$

这种结构很容易添加或者删除节点，比如如果我想添加个节点D，我需要从节点A，B，C中得部分槽位到D上。如果我想一出节点A，需要将A中的槽移动到B和C节点上，然后将没有任何槽的节点从集群中移除即可。由于一个结点将哈希槽移动到另一个节点不会停止服务，所以无论添加删除或者改变某个节点的哈希槽的数量都不会造成集群不可用的状态。<img src="03-Redis.assets/5.槽位示例.jpg" style="zoom: 67%;" />

## slot槽位映射方案

### 哈希取余分区(小厂)

<img src="03-Redis.assets/6.哈希取余分区.jpg" alt="6.哈希取余分区.jpg" style="zoom:67%;" />

2亿条记录就是2亿个k,v，我们单机不行必须要分布式多机，假设有3台机器构成一个集群，用户每次读写操作都是根据公式：hash(key) % N个机器台数，计算出哈希值，用来决定数据映射到哪一个节点上。

$\textcolor{blue}{\large 优点}$：简单粗暴，直接有效，只需要预估好数据规划好节点，例如3台、8台、10台，就能保证一段时间的数据 支撑。使用Hash算法让固定的一部分请求落到同一台服务器上，这样每台服务器固定处理一部分请求 (并维护这些请求的信息)， 起到负载均衡+分而治之的作用。

$\textcolor{blue}{\large 缺点}$：原来规划好的节点，进行扩容或者缩容就比较麻烦了额，不管扩缩，每次数据变动导致节点有变动，映射关系需要重新进行计算，在服务器个数固定不变时没有问题，如果需要弹性扩容或故障停机的情况下，原来的取模公式就会发生变化: Hash(key)/3会 变成Hash(key) /?。此时地址经过取余运算的结果将发生很大变化，根据公式获取的服务器也会变得不可控。

**某个redis机器宕机了，由于台数数量变化，会导致hash取余全部数据重新洗牌。**

### 一致性哈希算法分区(中厂)

   - 是什么？

     一致性Hash算法背景是在1997年由麻省理工学院提出的，设计目标是**为了解决分布式缓存数据变动和映射问题**，某个机器宕机了，分母数量改变了，自然取余数不行了 

   - 能干嘛？

     提出一致性Hash解决方案。目的是当服务器个数发生变动时，尽量减少影响客户端到服务器的映射关系

   - 3大步骤

     $\textcolor{blue}{\large 算法构建一致性哈希环}$ 

     一致性哈希算法必然有个hash函数并按照算法产生hash值，这个算法的所有可能哈希值会构成一个全量集，这个集合可以成为一个hash空间[0,2^32-1]，这个是一个线性空间，但是在算法中，我们通过适当的逻辑控制将它首尾相连(O= 2^32),这样让它逻辑上形成了一个环形空间。
     它也是按照使用取模的方法，**前面笔记介绍的节点取模法是对节点（服务器）的数量进行取模。而一致性Hash算法是对2^32取模，简单来说，一致性Hash算法将整个哈希值空间组织成一个虚拟的圆环**，如假设某哈希函数H的值空间为0-2^32-1(即哈希值是一个32位无符号整形），整个哈希环如下图:整个空间**按顺时针方向组织**，圆环的正上方的点代表0，O点右侧的第一个点代表1，以此类推，2、3、4、……直到2^32-1，也就是说0点左侧的第一个点代表2^32-1，0和2个32-1在零点中方向重合，我们把这个由2^32个点组成的圆环称为Hash环。

     ![](03-Redis.assets/7.Hash环.jpg)

     $\textcolor{blue}{\large 服务器IP节点映射}$ 

     将集群中各个IP节点映射到环上的某一个位置。
     将各个服务器使用Hash进行一个哈希，具体可以选择服务器的IP或主机名作为关键字进行哈希，这样每台机器就能确定其在哈希环上的位置。假如4个节点NodeA、B、C、D，经过IP地址的**哈希函数**计算(hash(ip))，使用IP地址哈希后在环空间的位置如下:

     ![](03-Redis.assets/8.对节点取Hash值.jpg)

     $\textcolor{blue}{\large key落到服务器的落键规则}$ 

     当我们需要存储一个kv键值对时，首先计算key的hash值，hash(key)，将这个key使用相同的函数Hash计算出哈希值并确定此数据在环上的位置，**从此位置沿环顺时针“行走”**，第一台遇到的服务器就是其应该定位到的服务器，并将该键值对存储在该节点上。
     如我们有Object A、 Object B、 Object C. object D四个数据对象，经过哈希计算后，在环空间上的位置如下:根据一致性Hash算法，数据A会被定为到Node A上，B被定为到Node B上，C被定为到Node C上，D被定为到Node D上。

     ![](03-Redis.assets/9.key的落键规则.jpg)

   - 优点

     $\textcolor{green}{\large 一致性哈希算法的容错性}$ ：假设Node C宕机，可以看到此时对象A、B、D不会受到影响。一般的，在一致性Hash算法中，如果一台服务器不可用，则受影响的数据仅仅是此服务器到其环空间中前一台服务悉**〈即沿着逆时针方向行走遇到的第一台服务器）之间数据**，其它不会受到影响。简单说，就是C挂了，受到影响的只是B、C之间的数据**且这些数据会转移到D进行存储**。

     ![](03-Redis.assets/10.一致性哈希算法容错性.jpg)

     $\textcolor{green}{\large 一致性哈希算法的扩展性}$ 

     数据量增加了，需要增加一台节点NodeX，X的位置在A和B之间，那收到影响的也就是A到X之间的数据，重新把A到X的数据录入到X上即可，不会导致hash取余全部数据重新洗牌。

     ![](03-Redis.assets/11.一致性哈希算法扩展性.jpg)

   - 缺点

     $\textcolor{green}{\large 一致性哈希算法的数据倾斜问题}$ 

     一致性Hash算法在服务**节点太少时**，容易因为节点分布不均匀而造成**数据倾斜**（被缓存的对象大部分集中缓存在某一台服务器上)问题，例如系统中只有两台服务器:

     ![](03-Redis.assets/12.一致性哈希算法缺点.jpg)

   - 小总结

     为了在节点数目发生改变时尽可能少的迁移数据

     将所有的存储节点排列在收尾相接的Hash环上，每个key在计算Hash后会顺时针找到临近的存储节点存放。而当有节点加入或退出时仅影响该节点在Hash环上顺时针相邻的后续节点。

     $\textcolor{green}{\large 优点}$ ：加入和删除节点只影响哈希环中顺时针方向的相邻的节点，对其他节点无影响。

     $\textcolor{green}{\large 缺点}$ ：数据的分布和节点的位置有关，因为这些节点不是均匀的分布在哈希环上的，所以数据在进行存储时达不到均匀分布的效果。

### $\textcolor{red}{\large 哈希槽分区}$(大厂)

- 是什么？ HASH_SLOT = CRC16(key) mod 16384

  1. 为什么出现

     哈希槽实质就是一个数组，数组[0, 2^14 - 1]形成hash slot空间

  2. 能干什么

     解决均匀分配的问题，在<font color=red>数据和节点之间又加入了一层，把这层称为哈希槽(slot)</font>，用于管理数据和节点之间的关系}$，现在就相当于节点上放的是槽，槽里面放的是数据。![](03-Redis.assets/13.哈希槽.jpg)

     槽解决的是粒度问题，相当于把粒度变大了，这样便于数据移动。哈希解决的是映射问题，使用key的哈希值来计算所在的槽，便于数据分配

  3. 多少个hash

     一个集群只能有16384个槽，编号0-16383(0-2^14-1)。这些槽会分配给集群中的所有主节点，分配策略没有要求。

     集群会记录节点和槽的对应关系，解决了节点和槽的关系后，接下来就需要对key求哈希值，然后对16384取模，余数是几key就落入对应的槽里。HASH_SLOT = CRC16(key) mod 16384。以槽为单位移动数据，因为槽的数目是固定的，处理起来比较容易，这样数据移动问题就解决了。

- 哈希槽计算

  Redis集群中内置了16384个哈希槽，redis 会根据节点数量大致均等的将哈希槽映射到不同的节点。当需要在Redis集群中放置一个key-valuel时，redis先对key使用crc16算法算出一个结果然后用结果对16384求余数[ CRC16(key) % 16384]，这样每个key都会对应一个编号在0-16383之间的哈希槽，也就是映射到某个节点上。如下代码，key之A、B在Node2， key之C落在Node3上

  ![](03-Redis.assets/14.hash槽计算.jpg)

$\textcolor{red}{\large 经典面试题：为什么Redis集群的最大槽数是16384个？}$

Redis集群并没有使用一致性hash而是引入了哈希槽的概念。Redis 集群有16384个哈希糟，每个key通过CRC16校验后对16384取模来决定放置哪个槽，集群的每个节点负责一部分hash槽。但为什么哈希槽的数量是16384 (2^14）个呢？

CRC16算法产生的hash值有16bit，该算法可以产生2^16=65536个值。
换句话说值是分布在0～65535之间，有更大的65536不用为什么只用16384就够?

作者在做mod运算的时候，为什么不mod65536，而选择mod16384? $\textcolor{blue}{\large HASH\_SLOT = CRC16(key) mod 65536为什么没启用？}$

作者回答：https://github.com/redis/redis/issues/2576

![](03-Redis.assets/15.Redis集群最大槽位为什么是16384.jpg)

说明1：

正常的心跳数据包带有节点的完整配置，可以用幂等方式用旧的节点替换旧节点，以便更新旧的配置。
这意味着它们包含原始节点的插槽配置，该节点使用2k的空间和16k的插槽，但是会使用8k的空间（使用65k的插槽）。同时，由于其他设计折衷，Redis集群不太可能扩展到1000个以上的主节点。
因此16k处于正确的范围内，以确保每个主机具有足够的插槽，最多可容纳1000个矩阵，但数量足够少，可以轻松地将插槽配置作为原始位图传播。请注意，在小型群集中，位图将难以压缩，因为当N较小时，位图将设置的slot / N位占设置位的很大百分比。

说明2：

$\textcolor{blue}{\large (1)如果槽位为65536，发送心跳信息的消息头达8k，发送的心跳包过于庞大。}$
在消息头中最占空间的是myslots[CLUSTER_SLOTS/8]。当槽位为65536时，这块的大小是:65536÷8÷1024=8kb

在消息头中最占空间的是myslots[CLUSTER_SLOTS/8]。当槽位为16384时，这块的大小是:16384∶8∶1024=2kb

因为每秒钟，redis节点需要发送一定数量的ping消息作为心跳包，如果槽位为65536，这个ping消息的消息头太大了，浪费带宽。
$\textcolor{blue}{\large (2)redis的集群主节点数量基本不可能超过1000个。}$
集群节点越多，心跳包的消息体内携带的数据越多。如果节点过1000个，也会导致网络拥堵。因此redis作者不建议redis cluster节点数量超过1000个。那么，对于节点数在1000以内的redis cluster集群，16384个槽位够用了。没有必要拓展到65536个。
$\textcolor{blue}{\large (3)槽位越小，节点少的情况下，压缩比高，容易传输}$
Redis主节点的配置信息中它所负责的哈希槽是通过一张bitmap的形式来保存的，在传输过程中会对bitmap进行压缩，但是如果bitmap的填充率slots /N很高的话(N表示节点数)， bitmap的压缩率就很低。如果节点数很少，而哈希槽数量很多的话，bitmap的压缩率就很低。

计算结论

Redis集群中内置了16384个哈希槽，redis会根据节点数量大致均等的将哈希槽映射到不同的节点。当需要在Redis集群中放置-一个key-value时， redis先对key使用crc16算法算出一个结果然后用结果对16384求余数[ CRC16(key) % 16384]， 这样每个key都会对应一个编号在0-16383之间的哈希槽，也就是映射到某个节点上。如下代码，key之A、B在Node2， key之C落在Node3上

![](03-Redis.assets/14.hash槽计算.jpg)


### Redis集群不保证强一致性

redis集群$\textcolor{blue}{\large 不保证强一致性}$，这意味着在特定的条件下，Redis集群可能会丢掉一些被系统收到的写入请求命令

last failover wins 最后一次故障转移获胜

![](03-Redis.assets/16.Redis集群不保证强一致性.jpg)

## 3主3从Redis集群配置

![image-20231010111821518](03-Redis.assets/image-20231010111821518.png)

![image-20231010111459357](03-Redis.assets/image-20231010111459357.png)

### 找3台真实虚拟机，各自新建

mkdir -p /myredis/cluster

### 新建6个独立的Redis实例服务

IP： 192.168.0.100 + 端口6381/6382

```shell
vim /myredis/cluster/redisCluster6381.conf
```

```conf
bind 0.0.0.0
daemonize yes
protected-mode no
port 6381
logfile "/myredis/cluster/cluster6381.log"
pidfile /myredis/cluster6381.pid
dir /myredis/cluster
dbfilename dump6381.rdb
appendonly yes
appendfilename "appendonly6381.aof"
requirepass 123456
masterauth 123456

# 开启集群
cluster-enabled yes
cluster-config-file nodes-6381.conf  #集群的配置文件
cluster-node-timeout 5000
```

```shell
vim /myredis/cluster/redisCluster6382.conf
```

```
bind 0.0.0.0
daemonize yes
protected-mode no
port 6382
logfile "/myredis/cluster/cluster6381.log"
pidfile /myredis/cluster6381.pid
dir /myredis/cluster
dbfilename dump6381.rdb
appendonly yes
appendfilename "appendonly6382.aof"
requirepass 123456
masterauth 123456

# 开启集群
cluster-enabled yes
cluster-config-file nodes-6382.conf  #集群的配置文件
cluster-node-timeout 5000
```

IP：192.168.0.100 + 端口6383/6384

```
vim /myredis/cluster/redisCluster6383.conf
vim /myredis/cluster/redisCluster6384.conf
```

IP：192.168.0.100 + 端口6385/6386

```
vim /myredis/cluster/redisCluster6385.conf
vim /myredis/cluster/redisCluster6386.conf
```

启动6台主机实例

```shell
redis-server /myredis/cluster/redisCluster6381.conf

...

redis-server /myredis/cluster/redisCluster6386.conf
```

### 通过redis-cli 命令为6台机器构建集群关系

**构建主从关系命令**

```shell
// 一定要注意，此处要修改自己的IP为真实IP
redis-cli -a 123456 --cluster create --cluster-replicas 1 192.168.111.175:6381 192.168.111.175:6382 192:168.111.172:6383 192.168.111.172:6384 192.168.111.174:6385 192.168.111.174:6386
```

注:  

+ --cluster- replicas 1 表示为每个master创建一一个slave节点
+ 第一个位置192.168.111.175:6381表示集群的领路人

![](03-Redis.assets/17.启动3主3从.jpg)

 <img src="03-Redis.assets/image-20231010112626242.png" alt="image-20231010112626242" style="zoom:67%;" />

**一切OK的话，3主3从搞定**

### 6381作为切入点，查看并检验集群状态

**连接进6381作为切入点，$\textcolor{red}{\large 查看节点状态}$**

<img src="03-Redis.assets/19.集群节点状态.jpg" style="zoom:67%;" />

**cluster nodes**

<img src="03-Redis.assets/20.集群节点状态查看.jpg" style="zoom:67%;" />

**CLUSTER INFO**

<img src="03-Redis.assets/image-20231010112725444.png" alt="image-20231010112725444" style="zoom: 67%;" />

## 3主3从redis集群读写

### 问题 

#### 对6381新增连个key，看看效果如何

![](03-Redis.assets/22.集群环境对6381新增两个key.jpg)

#### 为什么报错

![](03-Redis.assets/23.为什么报错.jpg)

#### 如何解决

防止路由失效加参数-c并新增两个key：

```shell
redis-cli -a 123456 -p 6381 -c
```

<img src="03-Redis.assets/24.集群重定向.jpg" style="zoom:67%;" />

### 集群启动

命令:

```shell
redis-cli -a 123456 -p 6381 -c
```

#### 服务加上-c后查看集群信息

信息无变化

![](03-Redis.assets/25.查看集群信息.jpg)

#### 查看某个key该属于对应的槽位值 cluster keyslot 键名称

![](03-Redis.assets/26.cluster keyslot 键名称.jpg)

## 主从容错切换迁移案例

### 容错切换迁移

- 主6381和从机切换，先停止主机6381

  6381主机停了，对应的真实从机上位

  6381作为1号主机分配的从机以实际情况为准，具体是几号机器就是几号机器


- 再次查看集群信息，本次6381主6384从

  ![](03-Redis.assets/28.集群主从查看.png)

- 停止主机6381，再次查看集群信息

  ![](03-Redis.assets/27.从机上位.png)

  6384成功上位

- 随后，6381原来的主机回来了，是否会上位？

  恢复前：![](03-Redis.assets/29.集群主节点恢复前.png)

  恢复后：![](03-Redis.assets/30.集群主节点恢复后.png)

  $\textcolor{red}{\large 从机6381不会上位, 以从机的形式回归}$, 使用cluster failover命令

### 集群不保证数据一致性100%OK，是会有数据丢失的情况

Redis集群不保证强一致性这意味着在特定的条件下，Redis集群可能会丢掉一些被系统收到的写入请求命令

![](03-Redis.assets/16.Redis集群不保证强一致性-16968372161634.jpg)

### 节点从属调整该

上面6381宕机后，6381机6384主从对调了，和原始设计图不一样了,该如何调换主从位置呢

重新登录6381机器 

```
常用命令：cluster failover
```

![](03-Redis.assets/31.6391上位命令.png)

## 主从扩容案例

### 新建6387、6388两个服务实例配置文件+新建后启动

IP：192.168.11.174+端口6387/端口6388

```shell
vim /myredis/cluster/redisCluster6387.conf
```

![](03-Redis.assets/32.6387配置文件.png)

```shell
vim /myredis/cluster/redisCluster6388.conf
```

### 启动87/88两个新的节点实例，此时他们自己都是master

```shell
redis-server /myredis/cluster/redisCluster6387.conf
redis-server /myredis/cluster/redisCluster6388.conf
```

![image-20231010114924527](03-Redis.assets/image-20231010114924527.png)

### 将新增的6387节点(空槽位)作为master节点加入集群领路人

==一般情况下使用一主一从的形式加入==

将新增的6387作为master节点加入原有集群

```shell
redis-cli -a 密码 --cluster 新建节点所在的IP地址:6387 原来集群节点里面的领路人的IP地址:6381
```

+ 6387就是将要作为master新增节点
+ 6381 就是原来集群节点里面的领路人，相当于6387拜拜6381的码头从而找到组织加入集群redis-cli -a 123456 --cluster add-node 192.168.111.174:6387 192.168.111.175:6381

![](03-Redis.assets/33.新节点加入集群master.png)

### 检查集群情况第一次

```shell
redis-cli --cluster check 领路人端口所在的真实ip地址：领路人端口
```

例如：redis-cli -a 123456 --cluster check 192.168.111.175:6381

![](03-Redis.assets/34.加入后集群情况.png)

### 领路人重新分派槽位( reshard)

重新分派槽号

```shell
命令:redis-cli -a 密码 --cluster reshard IP地址:端口号
```

redis-cli -a 123456 --cluster reshard 192.168.111.175:6381

 ![image-20231010120600101](03-Redis.assets/image-20231010120600101.png)

![image-20231010120649389](03-Redis.assets/image-20231010120649389.png)

### 检查集群情况第二次

```shell
redis-cli --cluster check 领路人端口所在的真实ip地址：领路人端口
```

redis-cli --cluster check 192.168.111.175:6381

![](03-Redis.assets/37.集群情况查看.png)



**槽位分派说明**

为什么6387是3个新的区间，以前的还是连续？  

+ **重新分配成本太高，所以前3家各自匀出来一部分**，从6381/6383/6385三个旧节点分别匀出1367个坑位给信节点6387

<img src="03-Redis.assets/38.槽号分配说明.png" style="zoom:80%;" />

### 为新增主节点6387分配从节点6388

命令：

```shell
redis-cli -a 密码 --cluster add-node ip:新slave端口 ip:新master端口 --cluster-slave --cluster-master-id 新主机节ID
```

redis-cli -a 111111 --cluster add-node 192.168.111.174:6388 192.168.111.174:6387 --cluster-slave
--cluster-master-id 4feb6a7ee0ed2b39f86474cf4189ab2a554a40f-------这个是6387的编号，按照自己实际情况

![](03-Redis.assets/38.为主机分配从节点.png)

### 检查集群情况第三次

redis-cli --cluster check 真实IP地址：6381

redis-cli --cluster check 192.168.111.175:6381

![](03-Redis.assets/40.集群情况第三次查看.png)



## 主从缩容案例

### 案例6388和6387下线

6387和6388:   集群中的一个主机和它的从机

**1.获取从节点ID**

- 检查集群情况第一次，先获得从节点6388的节点ID

  redis-cli -a 123456 --cluster check 192.168.111.174:6388

![](03-Redis.assets/41.获取缩容结点.png)

- 从集群中将4号结点6388删除

  redis-cli -a 123456 --cluster del-node 192.168.111.174:6388 218e7b8b4f81be54ff173e4776b4f4faaf7c13da

  ![](03-Redis.assets/42.删除从节点.png)

- 将6387的槽号清空，重新分配，本例将清出来的槽号都给6381(清出来的槽号都给领路人)

  redis-cli -a 123456 --cluster reshard 192.168.111.175:6381

  ![](03-Redis.assets/43.查询节点ID.png)

  ![](03-Redis.assets/44.删除节点槽位分配.png)

- 检查集群情况第二次

  redis-cli -a 123456 --cluster check 192.168.111.175:6381

  4096个槽位都指给6381，它变成了8192个槽位，相当于全部都给6381了，不然要输入三次 Source node

  ![](03-Redis.assets/45.集群缩容第二次检查.png)

- 将6387删除

  redis-cli -a 123456 --cluster del-node 192.168.111.174:6387 307a5f6617a6eeb4949f3cb9124ed04c6962c348

  ![](03-Redis.assets/46.从节点删除.png)

- 检查集群情况第三次 6387/6388被彻底删除

  redis-cli -a 123456 --cluster check 192.168.111.174:6381

  ![](03-Redis.assets/47.集群缩容彻底删除.png)

## 集群常用操作命令和CRC16算法分析

### 不在同一个slot槽位下的多键操作支持不好，通识占位符登场

![](03-Redis.assets/48.集群取值失败.jpg)

不在同一个slot槽位下的键值无法使用mset、mget等多键操作

可以通过{}来定义同一个组的概念，使key中{}内相同内容的键值对放到一个slot槽位去，对照下图类似k1k2k3都映射为x，自然槽位一样

![](03-Redis.assets/49.集群通配符获取.jpg)

### Redis集群有16384个哈希槽，每个key通过CRC16校验后对16384取模来决定放置哪个槽。集群的每个节点负责一部分hash槽。

**CRC16源码浅谈**

cluster.c源码分析一下

![](03-Redis.assets/50.crc16源码浅谈.jpg)

### 常用命令

- 集群是否完整才能对外提供服务

  ![](03-Redis.assets/51.集群是否完整才能对外提供服务..jpg)

  | 默认YES，现在集群架构是3主3从的redis cluster由3个master平分16384个slot，每个master的小集群负责1/3的slot，对应一部分数据。cluster-require-full-coverage:默认值yes，即需要集群完整性，方可对外提供服务通常情况，如果这3个小集群中，任何一个(1主1从）挂了，你这个集群对外可提供的数据只有2/3了，整个集群是不完整的， redis默认在这种情况下，是不会对外提供服务的。 |
  | ------------------------------------------------------------ |
  | 如果你的诉求是，集群不完整的话也需要对外提供服务，需要将该参数设置为no，这样的话你挂了的那个小集群是不行了，但是其他的小集群仍然可以对外提供服务。 |

  cluster-require-full-coverage

- CLUSTER COUNTKEYSINSLOT 槽位数字编号

  CLUSTER COUNTKEYSINSLOT 12706

  返回结果：

  ​	1 该槽位被占用

  ​	0 该槽位没有被占用

- CLUSTER KEYSLOT 键名称

  CLUSTER KEYSLOT k1

  返回对应key的槽位数据，key不存在则返回0



# 十五、  SpringBoot集成Redis

### 总概述

jedis-lettuce-RedisTemplate三者的联系

## 本地Java连接Redis常见问题，小白注意

1. bind配置请注释掉
2. 保护模式设置为no
3. Linux系统的防火墙设置
4. Redis服务器的IP地址和密码是否正确
5. 忘记写访问redis的服务端口号和auth密码

![image-20231010193546859](03-Redis.assets/image-20231010193546859.png)

## 方法1  集成Jedis

是什么：Jedis Client是Redis官网推荐的一个面向Java客户端，库文件实现了对各类API进行封装调用

步骤：

1. 建module

2. 改pom

   ```pom
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.7.11</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       <groupId>com.luojia</groupId>
       <artifactId>redis7_study</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>redis7_study</name>
       <description>Demo project for Spring Boot</description>
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
           <maven.compiler.source>1.8</maven.compiler.source>
           <maven.compiler.target>1.8</maven.compiler.target>
           <junit.version>4.12</junit.version>
           <log4j.version>1.2.17</log4j.version>
           <lombok.version>1.16.18</lombok.version>
       </properties>
   
       <dependencies>
           <!--SpringBoot 通用依赖模块-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <!-- jedis -->
           <dependency>
               <groupId>redis.clients</groupId>
               <artifactId>jedis</artifactId>
               <version>4.3.1</version>
           </dependency>
           <!-- 通用基础配置 -->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>${junit.version}</version>
           </dependency>
           <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>${log4j.version}</version>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>${lombok.version}</version>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

3. 写YML

   ```yaml
   server.port=7777
   spring.application.name=redis7_study
   ```

4. 主启动

5. 业务类

## 方法2  集成letter

1. 是什么

   Lettuce是一个Redis的Java驱动包，Lettuce翻译为生菜，就是吃的那种生成，所以它的logo如下：

![](03-Redis.assets/1.Redis驱动之Lettuce.jpg)

2. lettuce VS Jedis

   ![](03-Redis.assets/2.lettuce VS Jedis.jpg)

## 方法3  RedisTemplate-推荐使用

### 连接单机

pom

```xml
        <!-- SpringBoot 与Redis整合依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
        <!-- swagger2 接口文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

写YML

```yml
server.port=7777

spring.application.name=redis7_study

# ===========================logging===========================
logging.level.root=info
logging.1evel.com.luojia.redis7_study.redis7=info
1ogging.pattern.console=%d{yyyy-MM-dd HH:m:ss.SSS} [%thread] %-5level %1ogger- %msg%n

1ogging.file.name=F:\workspace\数据结构和算法\Learning-in-practice\Redis\redis7-study
1ogging.pattern.fi1e=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n

# ===========================swagge===========================
spring.swagger2.enabled=true
#在springboot2.6.X结合swagger2.9.X会提示documentationPluginsBootstrapper空指针异常，
#原因是在springboot2.6.X中将SpringMVC默认路径匹配策略MAntPathMatcher更改为athPatternParser,
#导致出错，解决办法是matching-strategy 切换回之前ant_path_matcher
spring.mvc.pathmatch.matching-strategy=ant_path_matcher

# ===========================redis单机===========================
spring.redis.database=0
#修改为自己真实IP
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=123456
spring.redis.lettuce.pool.max-active=8
spring.redis.1ettuce.pool.max-wait=-1ms
spring.redis.1ettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
```

主启动

业务类

- 配置类(解决RedisTemplate使用的是JDK序列化方式（默认）惹的祸)

  ```java
  package com.luojia.redis7_study.config;
  
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
  import org.springframework.data.redis.core.RedisTemplate;
  import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
  import org.springframework.data.redis.serializer.StringRedisSerializer;
  
  @Configuration
  public class RedisConfig {
  
      /**
       * *redis序列化的工具定置类，下面这个请一定开启配置
       * *127.0.0.1:6379> keys *
       * *1) “ord:102” 序列化过
       * *2)“\xaclxedlxeelx05tixeelaord:102” 野生，没有序列化过
       * *this.redisTemplate.opsForValue(); //提供了操作string类型的所有方法
       * *this.redisTemplate.opsForList();// 提供了操作List类型的所有方法
       * *this.redisTemplate.opsForset(); //提供了操作set类型的所有方法
       * *this.redisTemplate.opsForHash(); //提供了操作hash类型的所有方认
       * *this.redisTemplate.opsForZSet(); //提供了操作zset类型的所有方法
       * param LettuceConnectionFactory
       * return
       */
      @Bean
      public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
          //pom导入对应的依-->导包
          RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
          redisTemplate.setConnectionFactory(lettuceConnectionFactory);
          // 设置key序列化方式string
          redisTemplate.setKeySerializer(new StringRedisSerializer());
          
          //	// 源代码private RedisSerializer<String> stringSerializer = RedisSerializer.string();
          // 设置value的序列化方式json，使用GenericJackson2JsonRedisSerializer替换默认序列化
          redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
  
          redisTemplate.setHashKeySerializer(new StringRedisSerializer());
          redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
          redisTemplate.afterPropertiesSet();
          return redisTemplate;
      }
  }
  ```

  ```java
  package com.luojia.redis7_study.config;
  
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import springfox.documentation.builders.ApiInfoBuilder;
  import springfox.documentation.builders.PathSelectors;
  import springfox.documentation.builders.RequestHandlerSelectors;
  import springfox.documentation.service.ApiInfo;
  import springfox.documentation.spi.DocumentationType;
  import springfox.documentation.spring.web.plugins.Docket;
  import springfox.documentation.swagger2.annotations.EnableSwagger2;
  
  import java.time.LocalDate;
  import java.time.format.DateTimeFormatter;
  
  @Configuration
  @EnableSwagger2
  public class SwaggerConfig {
  
      @Value("${spring.swagger2.enabled}")
      private Boolean enabled;
  
      @Bean
      public Docket createRestApi() {
          return new Docket (DocumentationType.SWAGGER_2)
                  .apiInfo(apiInfo())
                  .enable(enabled)
                  .select()
                  .apis(RequestHandlerSelectors.basePackage("com.luojia.redis7_study.config")) //你自己的package
                  .paths (PathSelectors.any())
                  .build();
  
      }
  
      public ApiInfo apiInfo() {
          return new ApiInfoBuilder()
                  .title("springboot利用swagger2构建api接口文档 "+"\t"
                          + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()))
                  .description( "springboot+redis整合" )
                  .version("1.0")
                  .termsOfServiceUrl("https://github.com/Romantic-Lei")
                  .build();
      }
  
  }
  ```

- **service**

  ```java
  package com.luojia.redis7_study.service;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.data.redis.core.RedisTemplate;
  import org.springframework.stereotype.Service;
  
  import java.util.UUID;
  import java.util.concurrent.ThreadLocalRandom;
  
  @Service
  @Slf4j
  public class OrderService {
  
      @Autowired
      private RedisTemplate redisTemplate;
  
      public static final String ORDER_KEY="ord:";
  
      public void addOrder() {
          int keyId = ThreadLocalRandom.current().nextInt(1000) + 1;
          String serialNo = UUID.randomUUID().toString();
          String key = ORDER_KEY+keyId;
          String value = "JD" + serialNo;
          
          redisTemplate.opsForValue().set(key, value);  // redisTemplate.opsForValue()表示String类型
          log.info("***key:{}", key);
          log.info("***value:{}", value);
  
      }
  
      public String getOrderById(Integer keyId) {
          return (String)redisTemplate.opsForValue().get(ORDER_KEY+keyId); // 取
      }
  }
  ```

- **controller**

  ```java
  package com.luojia.redis7_study.controller;
  
  import com.luojia.redis7_study.service.OrderService;
  import io.swagger.annotations.Api;
  import io.swagger.annotations.ApiOperation;
  import io.swagger.models.auth.In;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @Slf4j
  @Api(tags="订单接口")
  public class OrderController {
  
      @Autowired
      private OrderService orderService;
  
      @ApiOperation("新增订单")
      @PostMapping("/order/add")
      public void addOrder() {
          orderService.addOrder();
      }
  
      @ApiOperation("根据keyId查询订单")
      @GetMapping("/order/query")
      public String queryOrder(Integer keyId) {
          return orderService.getOrderById(keyId);
      }
  }
  ```

测试：

项目启动，连接swagger：http://localhost:7777/swagger-ui.html

$\textcolor{red}{\large 序列化问题}$： 因为java和redis数据类型不同

![](03-Redis.assets/3.序列化问题.jpg)

为什么会这样？

![](03-Redis.assets/4.RedisTemplate 序列化.png)

RedisTemplate使用的是JDK序列化方式（默认）惹的祸

![](03-Redis.assets/5.jdk序列化方式.png)

解决:  用RedisTemplate子类或者用配置类

### RedisTemplate连接集群

- 启动Redis集群6台实例

- 第一次改写YML

  ```yaml
  # ===========================redis集群===========================
  spring.redis.password=123456
  # 获取失败 最大重定向次数
  spring.redis.cluster.max-redirects=3
  spring.redis.lettuce.pool.max-active=8
  spring.redis.1ettuce.pool.max-wait=-1ms
  spring.redis.1ettuce.pool.max-idle=8
  spring.redis.lettuce.pool.min-idle=0
  spring.redis.cluster.nodes=192.168.111.175:6381,192.168.111.175:6382,192.168.111.176:6383,192.168.111.176:6384
  ```


- 直接通过微服务访问Redis集群

  一切正常 （http://localhost:7777/swagger-ui.html）

- $\textcolor{red}{\large 问题来了}$

  1. 人为模拟，master-6381机器意外宕机，手动shutdown

  2. 先对redis集群用命令的方式，手动验证各种读写命令，看看6384是否上位

  3. Redis Cluster集群能自动感知并自动完成主备切换，对应的slave6384会被选举为新的master节点

  4. 通过redis客户端连接6384可以正常进行读写操作

  5. $\textcolor{green}{\large 微服务客户端再次读写访问试试}$

     - 故障现象

       SpringBoot客户端没有动态感知RedisCluster的最新集群信息

       金典故障 

       【故障演练】 Redis Cluster集群部署采用了3主3从拓扑结构，数据读写访问master节点，slave节点负责备份。$\textcolor{red}{\large 当master宕机主从切换成功，redis手动OK，but 2个经典故障}$

       ![](03-Redis.assets/6.Java连接Redis经典故障.png)

     - 导致原因
       SpringBoot 2.X版本，Redis默认的连接池采用Lettuce，当Redis集群节点发生变化后，Letture默认是不会刷新节点拓扑

     - 解决方案

       1. 排除lettuce采用Jedis（不推荐）

          ![](03-Redis.assets/7.将Lettuce二方包仲裁掉.png)

       2. 重写连接工厂实例（极度不推荐）

       3. 刷新节点集群拓扑动态感应

          ![](03-Redis.assets/8.刷新节点集群拓扑动态感应官网说明.png)

          解决方法：

          - 调用 RedisClusterClient.reloadPartitions
          - 后台基于时间间隔的周期刷新
          - 后台基于持续的 **断开** 和 **移动**、**重定向** 的自适应更新

  

  

## Redis之RedisTemplate的序列化方式深入解读

使用Spring提供的Spring Data Redis操作redis必然要使用Spring提供的模板类[RedisTemplate](https://so.csdn.net/so/search?q=RedisTemplate&spm=1001.2101.3001.7020)，使用RedisTemplate离不开Redis的序列化方式

### RedisTemplate

![image-20231011084232442](03-Redis.assets/image-20231011084232442.png)

4个序列化相关的属性 ，主要是用于KEY和VALUE的序列化，比如说经常会将POJO对象存储到Redis中，一般情况下会使用JSON方式序列化成字符串存储到Redis中 。

Spring提供的Redis数据结构的操作类

- ValueOperations 类，提供 Redis String API 操作
- ListOperations 类，提供 Redis List API 操作
- SetOperations 类，提供 Redis Set API 操作
- ZSetOperations 类，提供 Redis ZSet(Sorted Set) API 操作
- GeoOperations 类，提供 Redis Geo API 操作
- HyperLogLogOperations 类，提供 Redis HyperLogLog API 操作

比如操作Redis String可以通过opsForValue方法返回ValueOperations来进行操作

### StringRedisTemplate

RedisTemplate支持泛型，StringRedisTemplate K/V 均为String类型, 是RedisTemplate的子类。

<img src="03-Redis.assets/image-20231011084931312.png" alt="image-20231011084931312" style="zoom: 60%;" />

org.springframework.data.redis.core.StringRedisTemplate 继承RedisTemplate类，使用org.springframework.data.redis.serializer.StringRedisSerializer字符串序列化方式

<img src="03-Redis.assets/image-20231011085021961.png" alt="image-20231011085021961" style="zoom: 60%;" />

### RedisSerializer序列化接口

**RedisSerializer**接口是Redis序列化]接口，用于Redis KEY和VALUE的序列化。

<img src="03-Redis.assets/image-20231011085227404.png" alt="image-20231011085227404" style="zoom:67%;" />

RedisSerializer接口的实现类如下：

<img src="03-Redis.assets/image-20231011085325690.png" alt="image-20231011085325690" style="zoom:50%;" />

**默认Redis提供了11中的序列化方式，主要分为：**

- JDK序列化方式（默认）
- String序列化方式
- JSON序列化方式
- XML序列化方式

#### JDK序列化方式（默认）

org.springframework.data.redis.serializer.JdkSerializationRedisSerializer，默认不配置的情况RedisTemplate采用的是该数据序列化方式：

<img src="03-Redis.assets/image-20231011085505083.png" alt="image-20231011085505083" style="zoom:67%;" />

Spring Boot自动化配置RedisTemplate Bean对象时，就会设置默认的序列化方式。绝大多数情况下，并不推荐使用
JdkSerializationRedisSerializer进行序列化。

key跟value的值都是16进制字符串，可以看到key跟value实际上保存的都是以byte[]字节数组的格式存储：

![image-20231011085806542](03-Redis.assets/image-20231011085806542.png)

#### String序列化方式

org.springframework.data.redis.serializer.StringRedisSerializer，字符串和二进制数组都直接转换：

<img src="03-Redis.assets/image-20231011085915081.png" alt="image-20231011085915081" style="zoom:67%;" />

默认的话，**StringRedisTemplate**的key和value采用的就是这种序列化方案

#### JSON序列话方式

##### GenericJackson2JsonRedisSerializer

org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer 使用Jackson 实现JSON的序列化方式，

Generic单词翻译过来表示：通用的意思，可以看出，是支持所有

 **RedisConfig配置**

通过配置方式选择对应Redis数据的序列化方式，配置如下：

```java
package com.example.jedisserializefrombytestojson.config;
 
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
 
/**
 * Redis配置
 */
      
@Configuration
public class RedisConfig {
 
    //GenericJackson2JsonRedisSerializer
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
 
        //String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用GenericJackson2JsonRedisSerializer 替换默认序列化(默认采用的是JDK序列化)
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
 
        //key序列化方式采用String类型
        template.setKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackson类型
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        
      	//hash的key序列化方式也是采用String类型
        template.setHashKeySerializer(stringRedisSerializer);
        //hash的value也是采用jackson类型
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
 
    Jackson2JsonRedisSerializer
    //@Bean
    //@ConditionalOnMissingBean(name = "redisTemplate")
    //public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    //    RedisTemplate<String, Object> template = new RedisTemplate<>();
    //    template.setConnectionFactory(factory);
    //
    //    //String的序列化方式
    //    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    //    // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
    //    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    //
    //    //key序列化方式采用String类型
    //    template.setKeySerializer(stringRedisSerializer);
    //    //value序列化方式采用jackson类型
    //    template.setValueSerializer(jackson2JsonRedisSerializer);
    //    //hash的key序列化方式也是采用String类型
    //    template.setHashKeySerializer(stringRedisSerializer);
    //    //hash的value也是采用jackson类型
    //    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    //    template.afterPropertiesSet();
    //    return template;
    //}
    //
    FastJsonRedisSerializer
    //@Bean("redisTemplate")
    //public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
    //    RedisTemplate<String, Object> template = new RedisTemplate<>();
    //    template.setConnectionFactory(factory);
    //
    //    //String序列化方式
    //    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    //    // 使用FastJsonRedisSerializer替换默认序列化(默认采用的是JDK序列化)
    //    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    //
    //    //key序列化方式采用String类型
    //    template.setKeySerializer(stringRedisSerializer);
    //    //value序列化方式采用jackson类型
    //    template.setValueSerializer(fastJsonRedisSerializer);
    //    //hash的key序列化方式也是采用String类型
    //    template.setHashKeySerializer(stringRedisSerializer);
    //    //hash的value也是采用jackson类型
    //    template.setHashValueSerializer(fastJsonRedisSerializer);
    //    template.afterPropertiesSet();
    //    return template;
    //}
 
}
```

运行以下测试类：

```java
@Test
void redisTemplateSerializeTest() {
    
    // key
    String redisTemplateStringKey = "redisTemplateStringKey";
    String redisTemplateUserObjectKey = "redisTemplateUserObjectKey";
    String redisTemplateUserArrayObjectKey = "redisTemplateUserArrayObjectKey";
    String redisTemplateJSONObjectKey = "redisTemplateJSONObjectKey";
    String redisTemplateJSONArrayKey = "redisTemplateJSONArrayKey";
 
    // value
    //序列化String类型和反序列化String类型
    redisTemplate.opsForValue().set(redisTemplateStringKey, "austin");
    String austin = (String) redisTemplate.opsForValue().get(redisTemplateStringKey);
    System.out.println("stringGet: " + austin);
 
    //序列化Object对象类型和反序列化Object对象类型 (User对象)
    User user = new User("123", "austin", 25);
    redisTemplate.opsForValue().set(redisTemplateUserObjectKey, user);
    User userGet = (User) redisTemplate.opsForValue().get(redisTemplateUserObjectKey);
    System.out.println("userGet: " + userGet);
 
    //序列化Object对象数组类型和反序列化Object对象数组类型 (User[]对象数组)
    User user1 = new User("1", "austin1", 25);
    User user2 = new User("2", "austin2", 25);
    User[] userArray = new User[]{user1, user2};
    redisTemplate.opsForValue().set(redisTemplateUserArrayObjectKey, userArray);
    User[] userArrayGet = (User[]) redisTemplate.opsForValue().get(redisTemplateUserArrayObjectKey);
    System.out.println("userArrayGet: " + userArrayGet);
 
    //序列化JSONObject对象类型和反序列化JSONObject对象类型
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", "123");
    jsonObject.put("name", "austin");
    jsonObject.put("age", 25);
    redisTemplate.opsForValue().set(redisTemplateJSONObjectKey, jsonObject);
    JSONObject jsonObjectGet = (JSONObject) redisTemplate.opsForValue().get(redisTemplateJSONObjectKey);
    System.out.println("jsonObjectGet: " + jsonObjectGet);
 
    //序列化JSONArray对象类型和反序列化JSONArray对象类型
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("id", "1");
    jsonObject1.put("name", "austin1");
    jsonObject1.put("age", 25);
    JSONObject jsonObject2 = new JSONObject();
    jsonObject2.put("id", "1");
    jsonObject2.put("name", "austin2");
    jsonObject2.put("age", 25);
    jsonArray.add(jsonObject1);
    jsonArray.add(jsonObject2);
    redisTemplate.opsForValue().set(redisTemplateJSONArrayKey, jsonArray);
    JSONArray jsonArrayGet = (JSONArray) redisTemplate.opsForValue().get(redisTemplateJSONArrayKey);
    System.out.println("jsonArrayGet: " + jsonArrayGet);
}
```

观察redis数据的存储格式：

<img src="03-Redis.assets/image-20231011090825531.png" alt="image-20231011090825531" style="zoom:67%;" />

运行结果:

**key- value** ：

- 字符串类型

```vbnet
Key: redisTemplateStringKey
Value: "austin"
```

- 对象类型

```cobol
Key: redisTemplateUserObjectKey
Value:
{
    "@class": "com.example.jedisserializefrombytestojson.User",
    "id": "123",
    "name": "austin",
    "age": 25
}
```

- 对象数组类型

```cobol
Key: redisTemplateUserArrayObjectKey
Value: 
[
    "[Lcom.example.jedisserializefrombytestojson.User;",
    [
        {
            "@class": "com.example.jedisserializefrombytestojson.User",
            "id": "1",
            "name": "austin1",
            "age": 25
        },
        {
            "@class": "com.example.jedisserializefrombytestojson.User",
            "id": "2",
            "name": "austin2",
            "age": 25
        }
    ]
]
```

- JSONObject类型

```cobol
Key: redisTemplateJSONObjectKey
Value:
{
    "@class": "com.alibaba.fastjson.JSONObject",
    "name": "austin",
    "id": "123",
    "age": 25
}
```

- JSONArray类型

```cobol
Key: redisTemplateJSONArrayKey
Value: 
[
    "com.alibaba.fastjson.JSONArray",
    [
        {
            "@class": "com.alibaba.fastjson.JSONObject",
            "name": "austin1",
            "id": "1",
            "age": 25
        },
        {
            "@class": "com.alibaba.fastjson.JSONObject",
            "name": "austin2",
            "id": "1",
            "age": 25
        }
    ]
]
```

运行redisTemplateSerializeTest测试类，结果发现该方式序列化和反序列化都没有问题，是通用性序列化方式：

<img src="03-Redis.assets/image-20231011091103207.png" alt="image-20231011091103207" style="zoom:67%;" />

补充:  在将一个对象序列化成一个字符串，怎么保证字符串反序列化成对象的类型:   Jackson通过 Default Typing，会在字符串多冗余一个类型，这样反序列化就知道具体的类型了。**使用GenericJackson2JsonRedisSerializer序列化方式，String类型、对象、对象数组、JSONObject、JSONArray序列化和反序列化都没有问题，value值序列化后多了@class属性，反序列化的对象的类型就可以从这里获取到。@class属性完美解决了反序列化后的对象类型，所以实际项目中，目前很多采用 GenericJackson2JsonRedisSerializer序列化方式。**

##### jackson2JsonRedisSerializer

org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

```java
	@Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
    
        //String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    
        //key序列化方式采用String类型
        template.setKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackson类型
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的key序列化方式也是采用String类型
        template.setHashKeySerializer(stringRedisSerializer);
        //hash的value也是采用jackson类型
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
```

观察redis数据的存储格式：

<img src="03-Redis.assets/image-20231011091611715.png" alt="image-20231011091611715" style="zoom:67%;" />

运行结果

**key- value**:

- 字符串类型

```vbnet
Key: redisTemplateStringKey
Value: "austin"
```

- 对象类型

```cobol
Key: redisTemplateUserObjectKey
Value:
{
    "id": "123",
    "name": "austin",
    "age": 25
}
```

与GenericJackson2JsonRedisSerializer序列化方式结果不同的是，value没有@class属性。

- 对象数组类型

```cobol
Key: redisTemplateUserArrayObjectKey
Value: 
[
    {
        "id": "1",
        "name": "austin1",
        "age": 25
    },
    {
        "id": "2",
        "name": "austin2",
        "age": 25
    }
]
```

与GenericJackson2JsonRedisSerializer序列化方式结果不同的是，value没有"@class":"com.example.jedisserializefrombytestojson.User" 对象类型属性。

- JSONObject类型

```cobol
Key: redisTemplateJSONObjectKey
Value:
{
    "name": "austin",
    "id": "123",
    "age": 25
}
```

与GenericJackson2JsonRedisSerializer序列化方式结果不同的是，value没有"@class":"com.alibaba.fastjson.JSONObject"属性。

- JSONArray类型

```cobol
Key: redisTemplateJSONArrayKey
Value: 
[
    {
        "name": "austin1",
        "id": "1",
        "age": 25
    },
    {
        "name": "austin2",
        "id": "1",
        "age": 25
    }
]
```

与GenericJackson2JsonRedisSerializer序列化方式结果不同的是，value没有 "com.alibaba.fastjson.JSONArray" 对象类型属性。

--------
**Jackson2JsonRedisSerializer与GenericJackson2JsonRedisSerializer序列化结果不同的是:**

+ 前者并没有@class或者@type类型属性，这种序列化方式可能会导致获取redis数据反序列化成POJO对象时候出错，导致反序列化失败，所以一般也很少使用该方式。

比如在对象强制转换的情况，会报错：

![image-20231011091918762](03-Redis.assets/image-20231011091918762.png)

报错信息很明显，不能直接将JSONObject对象强制转换成User对象，不能通过方式获取转换：

```java
//该方式强转会报错
User userGet = (User) redisTemplate.opsForValue().get(redisTemplateUserObjectKey);
```

而正确的方式应该是：

通过jackson中的com.fastxml.jackson的ObjectMapper对象进行转换

```java
Object userObject = redisTemplate.opsForValue().get(redisTemplateUserObjectKey);
ObjectMapper objectMapper = new ObjectMapper();
User userGet = objectMapper.convertValue(userObject, User.class);
System.out.println("userGet: " + userGet);
```

<img src="03-Redis.assets/image-20231011092009633.png" alt="image-20231011092009633" style="zoom: 50%;" />

或者使用fastjson2(推荐)

```java
// 从redis查询token
Object obj = redisTemplate.opsForValue().get(token);  //  <!-- redis -->
// 反序列化 json对象反序列化成user对象
XUser user = JSON.parseObject(JSON.toJSONString(obj), XUser.class);
```

#### 总结

+ 采用**GenericJackson2JsonRedisSerializer**序列化方式对于String、对象、对象数组、JSONObject、JSONArray的序列化反序列化操作都正常，对象强转是没有任何问题

+ 采用**Jackson2JsonRedisSerializer**序列化方式在对象强制时，也就是使用 User userGet = (User) redisTemplate.opsForValue().get(redisTemplateUserObjectKey);方式获取对象，会操作对象转换失败，建议的解决方式是默认都采用 com.fastxml.jackson的ObjectMapper对象进行转换，也就是：

  ```
  ObjectMapper objectMapper = new ObjectMapper();
  objectMapper.convertValue(Object fromValue, Class<T> toValueType);
  ```

  该方式支持将任意类型的Object对象转换为相应的实体对象。

















