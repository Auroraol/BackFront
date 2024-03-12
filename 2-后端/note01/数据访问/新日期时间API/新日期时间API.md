# 时间 :crossed_swords:

	n天:    24L * 60 * 60 * 1000L * n
	n小时:  60L * 60 * 1000L * n
	n分钟: 60 * 1000L * n
	n秒钟: 1000L * n

**数据库数据**

![image-20231227104205950](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/image-20231227104205950.png)

**entity**

jdk8推出LocalDate和LocalTime来获取单纯的日期和时间

```java
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * 
 * @TableName timetest
 */
@TableName(value ="timetest")
@Data
public class Timetest implements Serializable {
    /**
     * 
     */
    @TableField(value = "Date")
    private LocalDate date;

    /**
     * 
     */
    @TableField(value = "Time")
    private LocalTime time;

    /**
     * 
     */
    @TableField(value = "DateTime")
    private LocalDateTime datetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
```

**Controller**

```java
package com.example.demo.controller;

import com.example.demo.entity.Timetest;
import com.example.demo.service.TimetestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-12-27 10:48
 */

@RestController
public class TimeTestController {

	@Autowired
	TimetestService timetestService;

	@GetMapping("/time")
	List<Timetest> getTime(){
		return timetestService.getTime();
	}

	@GetMapping("/date")
	List<Timetest> getDate(){
		return timetestService.getDate();
	}

	@GetMapping("/dateTime")
	List<Timetest> getDateTime(){
		return timetestService.getDatetime();
	}

}
```

运行结果

```json
[
    {
        "date": "2023-12-27",
        "time": null,
        "datetime": null
    }
]
```

```json
[
    {
        "date": null,
        "time": "10:38:49",
        "datetime": null
    }
]
```

```json
[
    {
        "date": null,
        "time": null,
        "datetime": "2023-12-27T10:38:51"
    }
]
```

**解决方法** 

```java
@TableField(value = "DateTime")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //原始数据为2023-12-27T10:38:51 格式化为2023-12-27 10:38:51
private LocalDateTime datetime;
```

```json
[
    {
        "date": null,
        "time": null,
        "datetime": "2023-12-27 10:38:51"
    }
]
```

原因

![image-20231227113947054](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/image-20231227113947054.png)

# 常用的日期时间API简

介绍下java8API比较常用的日期时间API，按**java.time** 包的类顺序：

- Clock：时钟
- Instant：瞬间时间。
- LocalDate：本地日期。只有表示年月日
- LocalDateTime：本地日期时间，LocalDate+LocalTime
- LocalTime：本地时间，只有表示时分秒
- OffsetDateTime：有时间偏移量的日期时间（不包含基于ZoneRegion的时间偏移量）
- OffsetTime：有时间偏移量的时间
- ZonedDateTime：有时间偏移量的日期时间（包含基于ZoneRegion的时间偏移量）

博主把这些类都点开看了，都是属于不可变类。而且官方也说了，**java.time包** 下的类都是线程安全的。

# Clock

## Clock类说明

```java
public abstract class Clock {
...
}
```

**Clock** 是抽象类，内部提供了四个内部类，这是它的内部实现类

![image-2021081496702](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/4dc57a83e19c762a799d17ee2b4247f1.png)

- **FixedClock** ：始终返回相同瞬间的时钟，通常使用于测试。
- **OffsetClock** ：偏移时钟，时间偏移量的单位是Duration。
- **SystemClock** ：系统默认本地时钟。
- **TickClock** ：偏移时钟，时间偏移量的单位是纳秒。

**Clock** 提供了下面这几个常用的方法（这几个方法在实现类里都有对应的实现）：

```
// 获取时钟的当前Instant对象。
public abstract Instant instant()// 获取时钟的当前毫秒数值
public long millis()// 获取用于创建时钟的时区。
public abstract ZoneId	getZone()// 返回具有指定时区的当前时钟的新实例
public abstract Clock withZone(ZoneId zone)
```

## FixedClock

### Clock.fixed

```java
public static Clock fixed(Instant fixedInstant, ZoneId zone)
```

需要传递`instant`和`zone`，并将返回具有固定瞬间的时钟。

```java
		Instant instant = Instant.now();Clock fixedClock = Clock.fixed(instant, ZoneId.of("Asia/Shanghai"));Clock fixedClock1 = Clock.fixed(instant, ZoneId.of("GMT"));System.out.println("中国时区的Clock："+fixedClock);System.out.println("GMT时区的Clock:"+fixedClock1);
```

![image-20210814195855581](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/50c15d6a6aaab82bb28a2e51299f6728.png)

由运行结果可知，返回的结果是有带对应时区的。

验证获取的时钟会不会改变：

```java
		Clock clock = Clock.systemDefaultZone();Clock fixedClock = Clock.fixed(clock.instant(), ZoneId.of("Asia/Shanghai"));System.out.println(fixedClock.instant());try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}System.out.println(fixedClock.instant());
```

![image-202108149044323](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/e70e39a973517a80b9157d2cddb6866f.png)

**Clock.fixed** 创建一个固定的时钟，**clock** 对象将始终提供与指定相同的时刻。。如图所示，强制睡眠1秒，但是时刻没变。

### Clock.fixed 跟 Offset 方法更配

由上面可知**Clock.fixed** 得到一个固定的时钟，那要添加时间或者减去时间就要用到**Offset 方法**

示例代码如下

```java
		Clock clock = Clock.systemDefaultZone();Clock fixedClock = Clock.fixed(clock.instant(), ZoneId.of("Asia/Shanghai"));System.out.println(fixedClock.instant());Clock clockAdd = Clock.offset(clock, Duration.ofMinutes(20));Clock clockSub = Clock.offset(clock, Duration.ofMinutes(-10));System.out.println("原先的: " + clock.instant());System.out.println("加了20分钟: " + clockAdd.instant());System.out.println("减了10分钟: " + clockSub.instant());
```

![image-202108141995813](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/bcca0e1526d8cd6503af1377f99ae608.png)

## OffsetClock

**OffsetClock** 是偏移时钟，时间偏移量的单位是Duration。

```java
//Clockpublic static Clock offset(Clock baseClock, Duration offsetDuration) {Objects.requireNonNull(baseClock, "baseClock");Objects.requireNonNull(offsetDuration, "offsetDuration");if (offsetDuration.equals(Duration.ZERO)) {return baseClock;}return new OffsetClock(baseClock, offsetDuration);}
```

由源码可知，使用**Clock.offset方法** 返回的是OffsetClock实例对象

```java
		Clock clock = Clock.systemDefaultZone();Clock fixedClock = Clock.fixed(clock.instant(), ZoneId.of("Asia/Shanghai"));System.out.println(fixedClock.instant());Clock clockAdd = Clock.offset(clock, Duration.ofMinutes(20));System.out.println("原先的: " + clock.instant());System.out.println("加了20分钟: " + clockAdd.instant());
```

![image-20210814944060](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/96e20c1d4e0a579c0458302442698fa0.png)

## SystemClock

**SystemClock** 是系统默认的本地时钟。

```java
			Clock clock = Clock.systemDefaultZone();System.out.println(clock.millis());Clock utc = Clock.systemUTC();System.out.println(utc.millis());System.out.println(System.currentTimeMillis());
```

![image-20210814904947](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/8b0d8e5bb2fb216a0cea2a79b2fbcb7e.png)

居然完全一样。这就要看下源码了

### Clock.systemDefaultZone()

用的是系统默认的时区**ZoneId.systemDefault()**

```
    public static Clock systemDefaultZone() {return new SystemClock(ZoneId.systemDefault());}
```

![image-2021081495878](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/d0924a2c22e3a9c4a97e6f08bc04100f.png)

最终调用的也是System.currentTimeMillis()

### Clock.systemUTC()

用的是UTC时区**ZoneOffset.UTC**

```java
     public static Clock systemUTC() {return new SystemClock(ZoneOffset.UTC);}
```

![image-2021081495878](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/d0924a2c22e3a9c4a97e6f08bc04100f.png)

最终调用的也是System.currentTimeMillis()

### 结论

Clock.systemDefaultZone() 和Clock.systemUTC()获取的millis()时间戳是一样的，就是对应时区的差别。

## TickClock

**TickClock** 是偏移时钟，时间偏移量的最小单位是纳秒。

如图所示，Clock主要提供下面三个方法

```java
//构造的时钟的计时单位是自定义的偏移量单位
public static Clock tick(Clock baseClock, Duration tickDuration); 
//构造的时钟的计时单位是分 
public static Clock tickMinutes(ZoneId zone);
//构造的时钟的计时单位是秒
public static Clock tickSeconds(ZoneId zone) ;
```

![image-202108149595](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/a690516acfca40b1a8a0fe3b576b2516.png)

实战：

```java
		Clock tickClock = Clock.tick(Clock.systemDefaultZone(),Duration.ofHours(1L));		Clock tickMinutes = Clock.tickMinutes(ZoneId.of("Asia/Shanghai"));		Clock tickSeconds = Clock.tickSeconds(ZoneId.of("Asia/Shanghai"));		LocalDateTime tickClockLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(tickClock.millis()),ZoneId.of("Asia/Shanghai"));		LocalDateTime tickMinutesLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(tickMinutes.millis()),ZoneId.of("Asia/Shanghai"));		LocalDateTime tickSecondsLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(tickSeconds.millis()),ZoneId.of("Asia/Shanghai"));		System.out.println("tickClock  :"+tickClock.millis() +" 转为date时间："+tickClockLocalDateTime);	System.out.println("tickMinutes:"+tickMinutes.millis() +" 转为date时间："+tickMinutesLocalDateTime);	System.out.println("tickSeconds:"+tickSeconds.millis() +" 转为date时间："+tickSecondsLocalDateTime);
```

偏移量的单位支持：天，时，分，秒，豪秒，纳秒

![image-20210814909314](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/1fc70f6481544b175b6989c416564294.png)

![image-2021081495696](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/c207d56ba2b9312ba8703525aea1b16f.png)

# Instant

## Instant类说明

```java
public final class Instantimplements Temporal, TemporalAdjuster, Comparable<Instant>, Serializable {...}
```

Instant表示瞬间时间。也是不可变类且是线程安全的。其实**Java.time** 这个包是线程安全的。

**Instant**是java 8新增的特性，里面有两个核心的字段

```java
	...		private final long seconds;       private final int nanos;	...
```

一个是单位为秒的时间戳，另一个是单位为纳秒的时间戳。

是不是跟**System.currentTimeMillis()**返回的long时间戳很像，System.currentTimeMillis()返回的是毫秒级，Instant多了更精确的纳秒级时间戳。

## Instant常用的用法

```java
 		Instant now = Instant.now();System.out.println("now:"+now);System.out.println(now.getEpochSecond()); // 秒System.out.println(now.toEpochMilli()); // 毫秒
```

![image-20210720905353](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/f9530dae1dfe480dec644bf7c292d89f.png)

### Instant是没有时区的，但是Instant加上时区后，可以转化为ZonedDateTime

```java
		Instant ins = Instant.now();ZonedDateTime zdt = ins.atZone(ZoneId.systemDefault());System.out.println(zdt);
```

![image-202107205211996](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/3fab27e4ccded2c5c85c1ad3d7b7fda3.png)

### long型时间戳转Instant

要注意long型时间戳的时间单位选择Instant对应的方法转化

```java
//1626796436 为秒级时间戳
Instant ins = Instant.ofEpochSecond(1626796436);
ZonedDateTime zdt = ins.atZone(ZoneId.systemDefault());
System.out.println("秒级时间戳转化："+zdt);
//1626796436111l 为秒级时间戳
Instant ins1 = Instant.ofEpochMilli(1626796436111l);
ZonedDateTime zdt1 = ins1.atZone(ZoneId.systemDefault());
System.out.println("毫秒级时间戳转化："+zdt1);
```

## Instant的坑

Instant.now()获取的时间与北京时间相差8个时区，这是一个细节，要避坑。

看源码，用的是UTC时间。

```java
public static Instant now() {     return Clock.systemUTC().instant();  }
```

解决方案：

```java
Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
System.out.println("now:"+now);
```

![image-202107234326190](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/52a978b797d8b9409b70f239b92642b0.png)

# LocalDate

## LocalDate类说明

LocalDate表示本地日期。只有表示年月日。相当于：yyyy-MM-dd。

## LocalDate常用的用法

### 获取当前日期

```java
		LocalDate localDate1 = LocalDate.now();		LocalDate localDate2 = LocalDate.now(ZoneId.of("Asia/Shanghai"));		LocalDate localDate3 = LocalDate.now(Clock.systemUTC());System.out.println("now         :"+localDate1);	System.out.println("now by zone :"+localDate2);System.out.println("now by Clock:"+localDate3);
```

![image-2021081496781](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/6803e4fe90f265f84db734d4e60aa874.png)

### 获取localDate对象

```java
		LocalDate localDate1 = LocalDate.of(2021, 8, 14);LocalDate localDate2 = LocalDate.parse("2021-08-14");System.out.println(localDate1);		System.out.println(localDate2);
```

![image-2021081497325](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/832fd97d61a612f6dc24d3a461c0ade2.png)

### 获取指定日期的年月日

```
LocalDate localDate1 = LocalDate.of(2021, 8, 14);		
// 当前日期年份：2021		
System.out.println(localDate1.getYear());		
// 当前日期月份对象：AUGUST		
System.out.println(localDate1.getMonth());		
// 当前日期月份：8		
System.out.println(localDate1.getMonthValue());		
// 该日期是当前周的第几天:6		
System.out.println(localDate1.getDayOfWeek().getValue());		
// 该日期是当前月的第几天:14		
System.out.println(localDate1.getDayOfMonth());		
// 该日期是当前年的第几天:226		
System.out.println(localDate1.getDayOfYear());
```

![image-2021081498430](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/743c702d98fd06c85ee1ae08584bab9e.png)

### 修改年月日

```java
		LocalDate localDate1 = LocalDate.of(2021, 8, 14);// 修改该日期的年份：2022-08-14System.out.println(localDate1.withYear(2022));// 修改该日期的月份：2021-12-14System.out.println(localDate1.withMonth(12));// 修改该日期在当月的天数：2021-08-01System.out.println(localDate1.withDayOfMonth(1));
```

![image-20210814935404](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/c1da7ffda1dbaa3bc79ecf2967028bc1.png)

### 比较日期

```
		LocalDate localDate1 = LocalDate.of(2021, 8, 14);// 比较指定日期和参数日期，返回正数，那么指定日期时间较晚（数字较大）:13int i = localDate1.compareTo(LocalDate.of(2021, 8, 1));System.out.println(i);// 比较指定日期是否比参数日期早（true为早）:trueSystem.out.println(localDate1.isBefore(LocalDate.of(2021,8,31)));// 比较指定日期是否比参数日期晚（true为晚）:falseSystem.out.println(localDate1.isAfter(LocalDate.of(2021,8,31)));// 比较两个日期是否相等:trueSystem.out.println(localDate1.isEqual(LocalDate.of(2021, 8, 14)));
```

![image-202108149597](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/f462f2f401c28d3766700f7a0943f4a7.png)

### LocalDate 和String相互转化、Date和LocalDate相互转化

#### LocalDate 和String相互转化

```java
		LocalDate localDate1 = LocalDate.of(2021, 8, 14);// LocalDate 转 StringDateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");String dateString = localDate1.format(dateTimeFormatter);System.out.println("LocalDate 转 String:"+dateString);// String 转 LocalDateString str = "2021-08-14";DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");LocalDate date = LocalDate.parse(str, fmt);System.out.println("String 转 LocalDate:"+date);
```

![image-2021081499979](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/94f13e96916afe58d8b5ac50188c50a9.png)

#### Date和LocalDate相互转化

```java
	// Date 转 LocalDateDate now = new Date();// 先将Date转换为ZonedDateTimeInstant instant = now.toInstant();ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Shanghai"));LocalDate localDate = zonedDateTime.toLocalDate();// Sat Aug 14 23:16:28 CST 2021System.out.println(now);// 2021-08-14System.out.println(localDate);// LocalDate 转 DateLocalDate now1 = LocalDate.now();ZonedDateTime dateTime = now1.atStartOfDay(ZoneId.of("Asia/Shanghai"));Date date1 = Date.from(dateTime.toInstant());System.out.println(date1);
```

![image-2021081492237](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/19c57533eb4945e7d6a71a647e6c260e.png)

# LocalDateTime

## LocalDateTime类说明

表示当前日期时间，相当于：yyyy-MM-ddTHH:mm:ss

## LocalDateTime常用的用法

### 获取当前日期和时间

```java
		LocalDate d = LocalDate.now(); // 当前日期LocalTime t = LocalTime.now(); // 当前时间LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间System.out.println(d); // 严格按照ISO 8601格式打印System.out.println(t); // 严格按照ISO 8601格式打印System.out.println(dt); // 严格按照ISO 8601格式打印
```

![image-20210714857780](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/88a2ca1f2ab378e9fb78c35b6c19578c.png)

由运行结果可行，本地日期时间通过now()获取到的总是以当前默认时区返回的

### 获取指定日期和时间

```java
		LocalDate d2 = LocalDate.of(2021, 07, 14); // 2021-07-14, 注意07=07月LocalTime t2 = LocalTime.of(13, 14, 20); // 13:14:20LocalDateTime dt2 = LocalDateTime.of(2021, 07, 14, 13, 14, 20);LocalDateTime dt3 = LocalDateTime.of(d2, t2);System.out.println("指定日期时间："+dt2);System.out.println("指定日期时间："+dt3);
```

![image-20210714803165](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/e4815e85482b966bf64df64ee74af08d.png)

### 日期时间的加减法及修改

```java
		LocalDateTime currentTime = LocalDateTime.now(); // 当前日期和时间System.out.println("------------------时间的加减法及修改-----------------------");//3.LocalDateTime的加减法包含了LocalDate和LocalTime的所有加减,上面说过,这里就只做简单介绍System.out.println("3.当前时间：" + currentTime);System.out.println("3.当前时间加5年：" + currentTime.plusYears(5));System.out.println("3.当前时间加2个月：" + currentTime.plusMonths(2));System.out.println("3.当前时间减2天：" + currentTime.minusDays(2));System.out.println("3.当前时间减5个小时：" + currentTime.minusHours(5));System.out.println("3.当前时间加5分钟：" + currentTime.plusMinutes(5));System.out.println("3.当前时间加20秒：" + currentTime.plusSeconds(20));//还可以灵活运用比如：向后加一年，向前减一天，向后加2个小时，向前减5分钟，可以进行连写System.out.println("3.同时修改(向后加一年，向前减一天，向后加2个小时，向前减5分钟)：" + currentTime.plusYears(1).minusDays(1).plusHours(2).minusMinutes(5));System.out.println("3.修改年为2025年：" + currentTime.withYear(2025));System.out.println("3.修改月为12月：" + currentTime.withMonth(12));System.out.println("3.修改日为27日：" + currentTime.withDayOfMonth(27));System.out.println("3.修改小时为12：" + currentTime.withHour(12));System.out.println("3.修改分钟为12：" + currentTime.withMinute(12));System.out.println("3.修改秒为12：" + currentTime.withSecond(12));
```

![image-20210714941902](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/391472f879266d60a5d313b8a0796da9.png)

### LocalDateTime和Date相互转化

#### Date转LocalDateTime

```java
		System.out.println("------------------方法一：分步写-----------------------");//实例化一个时间对象Date date = new Date();//返回表示时间轴上同一点的瞬间作为日期对象Instant instant = date.toInstant();//获取系统默认时区ZoneId zoneId = ZoneId.systemDefault();//根据时区获取带时区的日期和时间ZonedDateTime zonedDateTime = instant.atZone(zoneId);//转化为LocalDateTimeLocalDateTime localDateTime = zonedDateTime.toLocalDateTime();System.out.println("方法一：原Date = " + date);System.out.println("方法一：转化后的LocalDateTime = " + localDateTime);System.out.println("------------------方法二：一步到位（推荐使用）-----------------------");//实例化一个时间对象Date todayDate = new Date();//Instant.ofEpochMilli(long l)使用1970-01-01T00:00:00Z的纪元中的毫秒来获取Instant的实例LocalDateTime ldt = Instant.ofEpochMilli(todayDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();System.out.println("方法二：原Date = " + todayDate);System.out.println("方法二：转化后的LocalDateTime = " + ldt);
```

![image-20210714210839339](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/fef7dbb7e4ae8717d415490471be8959.png)

#### LocalDateTime转Date

```java
		System.out.println("------------------方法一：分步写-----------------------");//获取LocalDateTime对象，当前时间LocalDateTime localDateTime = LocalDateTime.now();//获取系统默认时区ZoneId zoneId = ZoneId.systemDefault();//根据时区获取带时区的日期和时间ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);//返回表示时间轴上同一点的瞬间作为日期对象Instant instant = zonedDateTime.toInstant();//转化为DateDate date = Date.from(instant);System.out.println("方法一：原LocalDateTime = " + localDateTime);System.out.println("方法一：转化后的Date = " + date);System.out.println("------------------方法二：一步到位（推荐使用）-----------------------");//实例化一个LocalDateTime对象LocalDateTime now = LocalDateTime.now();//转化为dateDate dateResult = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());System.out.println("方法二：原LocalDateTime = " + now);System.out.println("方法二：转化后的Date = " + dateResult);
```

![image-20210714211035080](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/878a923c61fa76d95853b0c45e3f1d0a.png)

# LocalTime

## LocalTime类说明

LocalTime：本地时间，只有表示时分秒

## LocalTime常用的用法

### 获取当前时间

```java
		LocalTime localTime1 = LocalTime.now();LocalTime localTime2 = LocalTime.now(ZoneId.of("Asia/Shanghai"));LocalTime localTime3 = LocalTime.now(Clock.systemDefaultZone());System.out.println("now         :"+localTime1);System.out.println("now by zone :"+localTime2);System.out.println("now by Clock:"+localTime3);
```

![image-2021081498171](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/03a4f2ff0c10c2dce14fc2746663f79c.png)

### 获取LocalTime对象

```java
		LocalTime localTime1 = LocalTime.of(23, 26, 30);LocalTime localTime2 = LocalTime.of(23, 26);System.out.println(localTime1);System.out.println(localTime2);
```

![image-2021081494673](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/3d508a6436f660ce5a94f9e9d2af79f6.png)

### 获取指定日期的时分秒

```java
		LocalTime localTime1 = LocalTime.of(23, 26, 30);//当前时间的时：23System.out.println(localTime1.getHour());//当前时间的分：26System.out.println(localTime1.getMinute());//当前时间的秒：30System.out.println(localTime1.getSecond());
```

![image-2021081492055](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/12c935f86916a357e3e499294b8fc5d1.png)

### 修改时分秒

```java
		LocalTime localTime1 = LocalTime.of(23, 26, 30);//修改时间的时：00:26:30System.out.println(localTime1.withHour(0));//修改时间的分：23:30:30System.out.println(localTime1.withMinute(30));//修改时间的秒：23:26:59System.out.println(localTime1.withSecond(59));
```

![image-202108149774](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/f9dfe1e34385a897f35162465c302b72.png)

### 比较时间

```java
		LocalTime localTime1 = LocalTime.of(23, 26, 30);LocalTime localTime2 = LocalTime.of(23, 26, 32);// 两个时间进行比较 大返回1，小就返回-1，一样就返回0:-1System.out.println(localTime1.compareTo(localTime2));// 比较指定时间是否比参数时间早（true为早）:trueSystem.out.println(localTime1.isBefore(localTime2));// 比较指定时间是否比参数时间晚（true为晚）:falseSystem.out.println(localTime1.isAfter(localTime2));// 比较两个时间是否相等:trueSystem.out.println(localTime1.equals(LocalTime.of(23, 26, 30)));
```

![image-2021081498214](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/2878d385ad0388945721f475b4f3f81b.png)

# OffsetDateTime

## OffsetDateTime类说明

OffsetDateTime：有时间偏移量的日期时间（不包含基于ZoneRegion的时间偏移量）

```java
public final class OffsetDateTimeimplements Temporal, TemporalAdjuster, Comparable<OffsetDateTime>, Serializable {//The minimum supported {@code OffsetDateTime}, '-999999999-01-01T00:00:00+18:00' public static final OffsetDateTime MIN = LocalDateTime.MIN.atOffset(ZoneOffset.MAX);// The maximum supported {@code OffsetDateTime}, '+999999999-12-31T23:59:59.999999999-18:00'.public static final OffsetDateTime MAX = LocalDateTime.MAX.atOffset(ZoneOffset.MIN);...}
```

上面的**MIN** 和**MAX** 是公有静态变量。

## OffsetDateTime常用的用法

### 获取当前日期时间

```java
		OffsetDateTime offsetDateTime1 = OffsetDateTime.now();OffsetDateTime offsetDateTime2 = OffsetDateTime.now(ZoneId.of("Asia/Shanghai"));OffsetDateTime offsetDateTime3 = OffsetDateTime.now(Clock.systemUTC());System.out.println("now         :"+offsetDateTime1);System.out.println("now by zone :"+offsetDateTime2);System.out.println("now by Clock:"+offsetDateTime3);
```

![image-2021082196097](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/541b46282750c47b2525e73c7fbdb6d9.png)

### 获取OffsetDateTime对象

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);OffsetDateTime offsetDateTime1 = OffsetDateTime.of(localDateTime1, ZoneOffset.ofHours(8));OffsetDateTime offsetDateTime2 = OffsetDateTime. of(2021, 8, 15, 13, 14, 20,0, ZoneOffset.ofHours(8));Instant now = Instant.now();OffsetDateTime offsetDateTime3 = OffsetDateTime.ofInstant(now, ZoneId.of("Asia/Shanghai"));System.out.println(offsetDateTime1);System.out.println(offsetDateTime2);System.out.println(offsetDateTime3);
```

![image-20210821900413](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/3742f4132bdd5a1a2d7719daf88c0955.png)

### 获取指定日期的年月日时分秒

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);OffsetDateTime offsetDateTime1 = OffsetDateTime.of(localDateTime1, ZoneOffset.ofHours(8));//当前时间的年：2021System.out.println(offsetDateTime1.getYear());//当前时间的月：8System.out.println(offsetDateTime1.getMonthValue());//当前时间的日：15System.out.println(offsetDateTime1.getDayOfMonth());//当前时间的时：13System.out.println(offsetDateTime1.getHour());//当前时间的分：14System.out.println(offsetDateTime1.getMinute());//当前时间的秒：20System.out.println(offsetDateTime1.getSecond());
```

![image-2021082193542](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/f3793af31e2f1bce1abbf7f5e2e98476.png)

### 修改年月日时分秒

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);OffsetDateTime offsetDateTime1 = OffsetDateTime.of(localDateTime1, ZoneOffset.ofHours(8));//修改时间的年：2022-08-15T13:14:20+08:00System.out.println(offsetDateTime1.withYear(2022));//修改时间的月：2021-09-15T13:14:20+08:00System.out.println(offsetDateTime1.withMonth(9));//修改时间的日：2021-08-30T13:14:20+08:00System.out.println(offsetDateTime1.withDayOfMonth(30));//修改时间的时：2021-08-15T00:14:20+08:00System.out.println(offsetDateTime1.withHour(0));//修改时间的分：2021-08-15T13:30:20+08:00System.out.println(offsetDateTime1.withMinute(30));//修改时间的秒：2021-08-15T13:14:59+08:00System.out.println(offsetDateTime1.withSecond(59));
```

![image-2021082194524](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/9650c14aaacf305ef7862697917e80dd.png)

### 比较日期时间

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);OffsetDateTime offsetDateTime1 = OffsetDateTime.of(localDateTime1, ZoneOffset.ofHours(8));OffsetDateTime offsetDateTime3 = OffsetDateTime.of(localDateTime1, ZoneOffset.ofHours(8));LocalDateTime localDateTime2 = LocalDateTime.of(2021, 8, 15, 13, 14, 30);OffsetDateTime offsetDateTime2 = OffsetDateTime.of(localDateTime2, ZoneOffset.ofHours(8));// 两个时间进行比较 大返回1，小就返回-1，一样就返回0:-1System.out.println(offsetDateTime1.compareTo(offsetDateTime2));// 比较指定时间是否比参数时间早（true为早）:trueSystem.out.println(offsetDateTime1.isBefore(offsetDateTime2));// 比较指定时间是否比参数时间晚（true为晚）:falseSystem.out.println(offsetDateTime1.isAfter(offsetDateTime2));// 比较两个时间是否相等:trueSystem.out.println(offsetDateTime1.equals(offsetDateTime3));
```

![image-20210821944542](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/c7c55af2e662620c69ce419c422de04c.png)

### 字符串转化为OffsetDateTime对象

```java
				String str = "2021-08-15T10:15:30+08:00";OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(str);OffsetDateTime offsetDateTime2 = OffsetDateTime.parse(str,DateTimeFormatter.ISO_OFFSET_DATE_TIME);System.out.println(offsetDateTime1);System.out.println(offsetDateTime2);
```

![image-2021082196169](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/ac32f804c85d9ad45f9422fb7fa52e54.png)

# OffsetTime

## OffsetTime类说明

OffsetTime：有时间偏移量的时间

```java
public final class OffsetTimeimplements Temporal, TemporalAdjuster, Comparable<OffsetTime>, Serializable {//The minimum supported {@code OffsetTime}, '00:00:00+18:00'.public static final OffsetTime MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);//The maximum supported {@code OffsetTime}, '23:59:59.999999999-18:00'.public static final OffsetTime MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);...
}
```

上面的**MIN** 和**MAX** 是公有静态变量。

## OffsetTime常用的用法

### 获取当前时间

```java
		OffsetTime offsetTime1 = OffsetTime.now();OffsetTime offsetTime2 = OffsetTime.now(ZoneId.of("Asia/Shanghai"));OffsetTime offsetTime3 = OffsetTime.now(Clock.systemUTC());System.out.println("now         :"+offsetTime1);System.out.println("now by zone :"+offsetTime2);System.out.println("now by Clock:"+offsetTime3);
```

![image-2021088203](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/4b7062f66c1cbab98f41f73346f3d013.png)

### 获取OffsetTime对象

```java
		LocalTime localTime1 = LocalTime.of(13, 14, 20);OffsetTime offsetTime1 = OffsetTime.of(localTime1, ZoneOffset.ofHours(8));OffsetTime offsetTime2 = OffsetTime. of(13, 14, 20,0, ZoneOffset.ofHours(8));Instant now = Instant.now();OffsetTime offsetTime3 = OffsetTime.ofInstant(now, ZoneId.of("Asia/Shanghai"));System.out.println(offsetTime1);System.out.println(offsetTime2);System.out.println(offsetTime3);
```

![image-20210895380](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/bc70fed36cdb50d842c66da9f774e5e9.png)

### 获取指定时间的时分秒

```java
		LocalTime localTime1 = LocalTime.of( 13, 14, 20);OffsetTime offsetTime1 = OffsetTime.of(localTime1, ZoneOffset.ofHours(8));//当前时间的时：13System.out.println(offsetTime1.getHour());//当前时间的分：14System.out.println(offsetTime1.getMinute());//当前时间的秒：20System.out.println(offsetTime1.getSecond());
```

![image-202108802988](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/1336d659b6e9bc6f143ef2ad38b7f0e9.png)

### 修改时分秒

```java
		LocalTime localTime1 = LocalTime.of( 13, 14, 20);OffsetTime offsetTime1 = OffsetTime.of(localTime1, ZoneOffset.ofHours(8));//修改时间的时：00:14:20+08:00System.out.println(offsetTime1.withHour(0));//修改时间的分：13:30:20+08:00System.out.println(offsetTime1.withMinute(30));//修改时间的秒：13:14:59+08:00System.out.println(offsetTime1.withSecond(59));
```

![image-202108945483](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/0b2cfba06da58dedab9439095db18f1e.png)

### 比较时间

```java
		LocalTime localTime1 = LocalTime.of( 13, 14, 20);OffsetTime offsetTime1 = OffsetTime.of(localTime1, ZoneOffset.ofHours(8));OffsetTime offsetTime3 = OffsetTime.of(localTime1, ZoneOffset.ofHours(8));LocalTime localTime2 = LocalTime.of(13, 14, 30);OffsetTime offsetTime2 = OffsetTime.of(localTime2, ZoneOffset.ofHours(8));// 两个时间进行比较 大返回1，小就返回-1，一样就返回0:-1System.out.println(offsetTime1.compareTo(offsetTime2));// 比较指定时间是否比参数时间早（true为早）:trueSystem.out.println(offsetTime1.isBefore(offsetTime2));// 比较指定时间是否比参数时间晚（true为晚）:falseSystem.out.println(offsetTime1.isAfter(offsetTime2));// 比较两个时间是否相等:trueSystem.out.println(offsetTime1.equals(offsetTime3));
```

![image-2021089109890](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/f2cd159153c302b6fd6f061c7b4f0cba.png)

# ZonedDateTime

## ZonedDateTime类说明

表示一个带时区的日期和时间，ZonedDateTime可以理解为LocalDateTime+ZoneId

从源码可以看出来,ZonedDateTime类中定义了LocalDateTime和ZoneId两个变量。

且ZonedDateTime类也是不可变类且是线程安全的。

```java
public final class ZonedDateTimeimplements Temporal, ChronoZonedDateTime<LocalDate>, Serializable {/*** Serialization version.*/private static final long serialVersionUID = -6260982410461394882L;/*** The local date-time.*/private final LocalDateTime dateTime;/*** The time-zone.*/private final ZoneId zone;...
}
```

## ZonedDateTime常用的用法

### 获取当前日期时间

```java
		// 默认时区获取当前时间ZonedDateTime zonedDateTime = ZonedDateTime.now();// 用指定时区获取当前时间,Asia/Shanghai为上海时区ZonedDateTime zonedDateTime1 = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));//withZoneSameInstant为转换时区，参数为ZoneIdZonedDateTime zonedDateTime2 = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));System.out.println(zonedDateTime);System.out.println(zonedDateTime1);System.out.println(zonedDateTime2);
```

![image-202107205246938](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/6111942ba504d9a2879f1868d189c31e.png)

```java
		ZonedDateTime zonedDateTime1 = ZonedDateTime.now();ZonedDateTime zonedDateTime2 = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));ZonedDateTime zonedDateTime3 = ZonedDateTime.now(Clock.systemUTC());System.out.println("now         :"+zonedDateTime1);System.out.println("now by zone :"+zonedDateTime2);System.out.println("now by Clock:"+zonedDateTime3);
```

![image-202108957912](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/12382c7d91b39915420485e123d581e3.png)

### 获取ZonedDateTime对象

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneOffset.ofHours(8));ZonedDateTime zonedDateTime2 = ZonedDateTime. of(2021, 8, 15, 13, 14, 20,0, ZoneOffset.ofHours(8));Instant now = Instant.now();ZonedDateTime zonedDateTime3 = ZonedDateTime.ofInstant(now, ZoneId.of("Asia/Shanghai"));System.out.println(zonedDateTime1);System.out.println(zonedDateTime2);System.out.println(zonedDateTime3);
```

![image-2021088020148](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/d592ce784dcc58679ef2d6fef02154f1.png)

### 获取指定日期的年月日时分秒

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneOffset.ofHours(8));//当前时间的年：2021System.out.println(zonedDateTime1.getYear());//当前时间的月：8System.out.println(zonedDateTime1.getMonthValue());//当前时间的日：15System.out.println(zonedDateTime1.getDayOfMonth());//当前时间的时：13System.out.println(zonedDateTime1.getHour());//当前时间的分：14System.out.println(zonedDateTime1.getMinute());//当前时间的秒：20System.out.println(zonedDateTime1.getSecond());
```

![image-202108219231845](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/edf03e4f5afa74fc61c0df0d13867faa.png)

### 修改年月日时分秒

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneOffset.ofHours(8));//修改时间的年：2022-08-15T13:14:20+08:00System.out.println(zonedDateTime1.withYear(2022));//修改时间的月：2021-09-15T13:14:20+08:00System.out.println(zonedDateTime1.withMonth(9));//修改时间的日：2021-08-30T13:14:20+08:00System.out.println(zonedDateTime1.withDayOfMonth(30));//修改时间的时：2021-08-15T00:14:20+08:00System.out.println(zonedDateTime1.withHour(0));//修改时间的分：2021-08-15T13:30:20+08:00System.out.println(zonedDateTime1.withMinute(30));//修改时间的秒：2021-08-15T13:14:59+08:00System.out.println(zonedDateTime1.withSecond(59));
```

![image-20210821998](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/61088d6227242128630b8252610a109c.png)

### 比较日期时间

```java
		LocalDateTime localDateTime1 = LocalDateTime.of(2021, 8, 15, 13, 14, 20);ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneOffset.ofHours(8));ZonedDateTime zonedDateTime3 = ZonedDateTime.of(localDateTime1, ZoneOffset.ofHours(8));LocalDateTime localDateTime2 = LocalDateTime.of(2021, 8, 15, 13, 14, 30);ZonedDateTime zonedDateTime2 = ZonedDateTime.of(localDateTime2, ZoneOffset.ofHours(8));// 两个时间进行比较 大返回1，小就返回-1，一样就返回0:-1System.out.println(zonedDateTime1.compareTo(zonedDateTime2));// 比较指定时间是否比参数时间早（true为早）:trueSystem.out.println(zonedDateTime1.isBefore(zonedDateTime2));// 比较指定时间是否比参数时间晚（true为晚）:falseSystem.out.println(zonedDateTime1.isAfter(zonedDateTime2));// 比较两个时间是否相等:trueSystem.out.println(zonedDateTime1.equals(zonedDateTime3));
```

![image-20210821907094](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/a8be0c0980057ae5c238f27ae0373b11.png)

### LocalDateTime+ZoneId变ZonedDateTime

```java
		LocalDateTime localDateTime = LocalDateTime.now();ZonedDateTime zonedDateTime1 = localDateTime.atZone(ZoneId.systemDefault());ZonedDateTime zonedDateTime2 = localDateTime.atZone(ZoneId.of("America/New_York"));System.out.println(zonedDateTime1);System.out.println(zonedDateTime2);
```

![image-2021072094003](%E6%96%B0%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4API.assets/077df40143b68751ddb43cefcdf81299.png)

上面的例子说明了，LocalDateTime是可以转成ZonedDateTime的。



