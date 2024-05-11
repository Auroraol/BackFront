

# ObjectUtils

适用于任何数据类型

 判断空:  ObjectUtils.isEmpty(a)

相等:  ObjectUtils.nullSafeEquals(a, b);



## 一、来源

Apache [Commons-Lang3](https://so.csdn.net/so/search?q=Commons-Lang3&spm=1001.2101.3001.7020)

## 二、SpringBoot集成

```java
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>
12345
```

## 三、常用方法总结

**1、isEmpty(Object obj)**
检查对象是否为空。如果对象为null或者为空字符串、空数组、空Collection、空Map或者空[Iterator](https://so.csdn.net/so/search?q=Iterator&spm=1001.2101.3001.7020)，则返回true；否则返回false。

```java
String str = "";
boolean empty = ObjectUtils.isEmpty(str); // true
12
```

**2、isNotEmpty(Object obj)**
检查对象是否非空。如果对象不为null且不为空字符串、空数组、空Collection、空Map或者空Iterator，则返回true；否则返回false。

```java
String str = "Hello";
boolean notEmpty = ObjectUtils.isNotEmpty(str); // true
12
```

**3、equals(Object object1, Object object2)**
比较两个对象是否相等，可以处理null值，避免了NullPointerException的出现。

```java
String str1 = "Hello";
String str2 = "Hello";
boolean equals = ObjectUtils.equals(str1, str2); // true
123
```

**4、hashCode(Object obj)**
计算对象的[哈希码](https://so.csdn.net/so/search?q=哈希码&spm=1001.2101.3001.7020)，可以处理null值。

```java
String str = "Hello";
int hashCode = ObjectUtils.hashCode(str); // 69609650
12
```

**5、toString(Object obj)**
将对象转换为字符串。如果对象为空，则返回字符串"null"。

```java
int num = 123;
String str = ObjectUtils.toString(num); // "123"
12
```

**6、defaultIfNull(T object, T defaultValue)**
如果对象为空，则返回默认值。

```java
String str = null;
String defaultStr = "default";
String result = ObjectUtils.defaultIfNull(str, defaultStr); // "default"
123
```

**7、allNotNull(Object… objects)**
检查多个对象是否都不为空。如果所有对象都不为空，则返回true；否则返回false。

```java
String str1 = "Hello";
String str2 = "World";
boolean allNotNull = ObjectUtils.allNotNull(str1, str2); // true
123
```

**8、anyNotNull(Object… objects)**
检查多个对象中是否至少有一个不为空。如果至少有一个对象不为空，则返回true；否则返回false。

```java
String str1 = "Hello";
String str2 = null;
boolean anyNotNull = ObjectUtils.anyNotNull(str1, str2); // true
123
```

**9、compare(Comparable c1, Comparable c2)**
比较两个可比较的对象的大小。可以处理null值。如果c1小于c2，则返回负数；如果c1等于c2，则返回0；如果c1大于c2，则返回正数。

```java
Integer num1 = 123;
Integer num2 = 456;
int result = ObjectUtils.compare(num1, num2); // -1
123
```

**10、min(Comparable… values)**
返回一组可比较对象中的最小值，可以处理null值。

```java
Integer num1 = 123;
Integer num2 = 456;
Integer min = ObjectUtils.min(num1, num2); // 123
123
```

**11、max(Comparable… values)**
返回一组可比较对象中的最大值，可以处理null值。

```java
Integer num1 = 123;
Integer num2 = 456;
Integer max = ObjectUtils.max(num1, num2); // 456
123
```

**12、clone(Object obj)**
克隆一个对象。如果对象实现了Cloneable接口，则调用其clone()方法进行克隆；否则返回null。

```java
Person person = new Person("John", 30);
Person clone = ObjectUtils.clone(person); // 返回person的克隆对象
12
```