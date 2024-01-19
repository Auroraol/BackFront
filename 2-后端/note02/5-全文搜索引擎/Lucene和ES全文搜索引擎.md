### 关于Lucene和ES全文[搜索引擎](https://so.csdn.net/so/search?q=搜索引擎&spm=1001.2101.3001.7020)的介绍和使用

#### 一. 什么是全文检索

狭义的理解主要针对文本数据的搜索。数据可分为“**结构化**”数据(关系数据库表形式管理的数据)，半[结构化数据](https://so.csdn.net/so/search?q=结构化数据&spm=1001.2101.3001.7020)(XML文档、JSON文档)，和非结构化数据(WORD、PDF)，通常而言在结构化的数据中搜索性能是比较高的，全文搜索的目的就是把非结构化的数据变成有结构化的数据进行搜索，从而提高搜索效率。

**`全文搜索引擎 : 就是把没有结构的数据，转换为有结构的数据，来加快对文本的快速搜索，通常而言，有结构的数据的查询是很快的，比如： 有序数组 ， 红黑树`**

#### 二. Lucene概述

##### 1. 什么是Lucene

Lucene是apache下的一个开源的全文检索引擎工具包(一堆jar包)。它为软件开发人员提供一个简单易用的工具包（类库），以方便的在小型目标系统中实现全文检索的功能。Lucene适用于中小型项目 ，ES适用于中大型项目（它底层是基于lucene实现的）

##### 2. Lucene索引原理

任何技术都有一些核心，Lucene也有核心，而它的核心分为：`索引创建，索引搜索`。

##### 3. 索引的创建

将现实世界中所有的结构化和非结构化数据提取信息，创建索引的过程。那么索引里面究竟存的什么，以及如何创建索引呢？在这通过下面的例子来解答这个问题。首先构造三个不同的句子，有长有短：

![image-20221010185109347](Lucene%E5%92%8CES%E5%85%A8%E6%96%87%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E.assets/601beab99b0a1b8f1e2bd9ffa9c9c130.png)

在①处分别为3个句子加上编号，然后进行**分词**，把被一个单词分解出来与编号对应放在②处；在搜索的过程总，对于搜索的过程中大写和小写指的都是同一个单词，在这就没有区分的必要，按规则统一变为小写放在③处；要加快搜索速度，就必须保证这些单词的排列时有一定规则，这里按照字母顺序排列后放在④处；最后再简化索引，合并相同的单词，就得到如下结果：**`倒排索引文档`**

![image-20221010185352535](Lucene%E5%92%8CES%E5%85%A8%E6%96%87%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E.assets/619bab88fbf903d5d6a904bb6a7dd04c.png)

通常在数据库中我们都是根据文档找到内容，而这里是通过词，能够快速找到包含他的文档，这就是文档倒排链表。以上就是lucene索引结构中最核心的部分。我们注意到关键字是按字符顺序排列的（lucene没有使用B树结构），因此lucene可以用二元搜索算法快速定位关键词。

##### 4. 索引的搜索

就是得到用户的查询请求，搜索创建的索引，然后返回结果的过程。

![image-20221010190015258](Lucene%E5%92%8CES%E5%85%A8%E6%96%87%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E.assets/2a05ef6ccf6ee380c72b0a5f07da2c74.png)

搜索java world两个关键词，符合java的有1,2两个文档，符合world的有1,3两个文档，在搜索引擎中直接这样排列两个词他们之间是OR的关系，出现其中一个都可以被找到，所以这里3个都会出来。全文检索中是有相关性排序的，那么结果在是怎么排列的呢？hello java world中包含两个关键字排在第一，另两个都包含一个关键字，得到结果，hello lucene world排在第二，java在最长的句子中占的权重最低排在结果集的第三。从这里可以看出相关度排序还是有一定规则的。

#### 三. ElasticSearch相关概念

##### 1. 为什么要使用ElasticSearch

虽然全文搜索领域，Lucene可以被认为是迄今为止最先进、性能最好的、功能最全的搜索引擎库。但是，Lucene只是一个库。想要使用它，你必须使用Java来作为开发语言并将其直接集成到你的应用中，更糟糕的是，Lucene的配置及使用非常复杂，你需要深入了解检索的相关知识来理解它是如何工作的。

##### 2. 什么是ElasticSearch

ES是一个分布式的全文搜索引擎，为了解决原生Lucene使用的不足，优化Lucene的调用方式，并实现了高可用的分布式集群的搜索方案，ES的索引库管理支持依然是基于Apache Lucene™的开源搜索引擎。

ES也使用Java开发并使用Lucene作为其核心来实现所有索引和搜索的功能，但是它的目的是通过简单的 RESTful API来隐Lucene的复杂性，从而让全文搜索变得简单。

案例：添加索引

```json
PUT /mall/goods/1
{
  "id":1,
  "name":"哑铃"
}
```

获取

```json
GET /mall/goods/1

```

#### 四. ES下载和安装

##### 1.ElasticSearch安装

ES服务只依赖于JDK，推荐使用JDK1.8+

下载地址：https://www.elastic.co/downloads/elasticsearch

安装后解压即可，双击安装目录 bin/elasticsearch.bat即可启动

使用浏览器访问：http://localhost:9200

![image-20221010190909766](Lucene%E5%92%8CES%E5%85%A8%E6%96%87%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E.assets/71b0fc47366c3af1b2a96f3c89578b12.png)

看到上图信息，恭喜你，你的ES集群已经启动并且正常运行！

##### 2.Kibana安装

es的图形化管理界面

下载地址：https://www.elastic.co/downloads/kibana

解压即可安装 ， 执行bin\kibana.bat 即可启动Kibana

浏览器访问 http://localhost:5601 Kibana默认地址

Kibana组件详细说明：https://www.cnblogs.com/hunttown/p/6768864.html

#### 五. es与mysql的比较

| ElastciSearch全文搜索 | Mysql关系型数据库 |
| --------------------- | ----------------- |
| 索引库(index)         | 数据库(database)  |
| 文档类型(Type)        | 数据表(Table)     |
| 文档(Document)        | 一行数据(Row)     |
| 字段(field)           | 一个列(column)    |
| 文档ID                | 主键ID            |
| 查询(Query DSL)       | 查询(SQL)         |
| GET http://…          | SELECT * FROM …   |
| PUT http://           | UPDATE table set… |

#### 六. 查询索引库

查询所有索引库

```http
GET _cat/indices?v
1
```

查看指定索引库

```http
GET _cat/indices/aigou
1
```

删除索引库

```http
DELETE 名字
1
```

修改索引库

```
删除再添加
1
```

添加索引

```http
PUT crm/user/11	
{
	"id":11,
	"username":"zs",
	"age":18,
	"name":"zs",
	"sex":1,
	"join_date": 1584092062348
}
123456789
```

解释：添加id为11的用户 ， 索引库为 crm，类型为 User

##### 获取文档

- 获取指定文档

```
GET 索引库/类型/文档ID 
1
```

指定返回的列

```http
GET /crm/user/123?_source=fullName,email
1
```

只要内容不要元数据

```http
GET mall/goods/1/_source
1
```

删除文档

```
DELETE {index}/{type}/{id}
1
```

查询所有

```
GET _search
1
```

查询指定索引库

```
GET crm/_search
1
```

查询指定类型

```
GET crm/user/_search
1
```

查询指定文档

```
GET crm/user/11
1
```

##### 分页查询

```
&size=2&from=2
1
```

- size: 每页条数
- form:从多少条数据开始查

**条件查询+分页+排序**

```http
GET crm/user/_search?q=age:17&size=2&from=2&sort=id:desc&_source=id,username
1
```

#### 七. DSL查询与DSL过滤

##### 什么是DSL查询

对于简单查询，使用查询字符串比较好，但是对于复杂查询，由于条件多，逻辑嵌套复杂，查询字符串不易组织与表达，且容易出错，因此推荐复杂查询通过DSL使用JSON内容格式的请求体代替。

DSL查询是由ES提供丰富且`灵活的查询语言叫做DSL查询(Query DSL),它允许你构建更加复杂、强大的查询`。DSL(Domain Specific Language特定领域语言)以JSON请求体的形式出现。DSL有两部分组成：DSL查询和DSL过滤。

##### 一个常用的相对完整的DSL查询

```json
GET /crm/user/_search
{
	"query": {
   		"match_all": {}
	},
	"from": 20, 
	"size": 10,
	"_source": ["username", "age", "id"],
	"sort": [{"join_date": "desc"},{"age": "asc"}]
}
12345678910
```

上面查询 ， `match_all` 表示 查询所有数据，查询返回fullName，age和email几个列，按照加入日期和年龄进行排序

##### DSL查询案例

```json
GET /crm/user/_search
{
	"query" : {
   		"match" : {
   			"username" : "Hello Java"
		}
	}
}
12345678
```

match如同：where username = hello or username = java

term如同：where username = “Hello Java”

##### DSL过滤

DSL过滤语句和DSL查询语句非常相似，但是它们的使用目的却不同：DSL过滤文档的方式更像是对于我的条件"有"或者"没有"（等于 ；不等于），而DSL查询语句则像是"有多像"(模糊查询)。

##### DSL查询+过滤语法

```json
GET /crm/user/_search
{
	"query": { 
		"bool": { 		//booleanQuery 组合
			"must": [{	//与(must) 或(should) 非(must not) 
				"match": { //match : 匹配，吧查询的内容分词后去查询
					"username": "zs"
				},
			}],
			"filter": {
				"term": {
					"name": "zs ls"
				}
			}
		}
	},
	"from": 20,
	"size": 10,
	"_source": ["name", "age", "username"],
	"sort": [{
		"join_date": "desc"
	}, {
		"age": "asc"
	}]
}
12345678910111213141516171819202122232425
```

解释：

- query : 查询，所有的查询条件在query里面
- bool : 组合搜索bool可以组合多个查询条件为一个查询对象，这里包含了 DSL查询和DSL过滤的条件
- must : 必须匹配 ：与(must) 或(should) 非(must_not)
- match：分词匹配查询，会对查询条件分词 ， multi_match :多字段匹配
- filter： 过滤条件
- term：词元查询，不会对查询条件分词
- from，size :分页
- _source ：查询结果中需要哪些列
- sort：排序

##### 综合案例

名称(name)中有 “zs” 的用户 ，性别sex是男生(1),年龄（age）在 18- 20之间,按照年龄（age）倒排序，查询第 1 页，每页10 条 ，查询结果中只需要 ：id,name,username,age

```json
GET /aigou/product/_search 
{
	"query":{
		"bool": {
			"must": [{
				"match": {
					"name": "zs"
				}
			}],
			
			"filter": [
				{
                    "range":{	//范围查询
                        "age":{
                            "gte":18,
                            "lte":20
                        }
                    }
				},
				{
					"term": {	//词元查询
						"sex": 1
					}
				}
			]
		}
	},
	"from": 1,
	"size": 10,
	"_source": ["id", "name", "age","username"],
	"sort": [{
		"age": "desc"
	}]
}
12345678910111213141516171819202122232425262728293031323334
```

#### 八. 分词器安装和使用

##### 1.什么是分词

在全文检索理论中，文档的查询是通过关键字查询文档索引来进行匹配，因此将文本拆分为有意义的单词，对于搜索结果的准确性至关重要，因此，在建立索引的过程中和分析搜索语句的过程中都需要对文本串分词。ES的倒排索引是分词的结果。

##### 2.下载ES的IK分词器

插件源码地址：https://github.com/medcl/elasticsearch-analysis-ik

###### 解压elasticsearch-analysis-ik-6.8.0.zip文件

并将解压后的内容放置于ES根目录/plugins/ik

#### 九文档类型映射

ES的文档映射(mapping)机制用于进行字段类型确认，将每个字段匹配为一种确定的数据类型。就如果Mysql创建表时候指定的每个column列的类型。 为了方便字段的检索，我们会指定存储在ES中的字段是否进行分词，但是有些字段类型可以分词，有些字段类型不可以分词，所以对于字段的类型需要我们自己去指定。

需要注意的是，我们在Mysql建表过程是：

Mysql创建数据库 -> 创建表(指定字段类型) -> crud数据 而在ES中也是一样，

ES创建索引库 -> 文档类型映射 -> crud文档

查看索引类型的映射配置：GET {indexName}/_mapping/{typeName}

```json
GET mall/_mapping/goods

{
  "mall" : {
    "mappings" : {
      "goods" : {
        "properties" : {
          "name" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          }
        }
      }
    }
  }
}
123456789101112131415161718192021
```

基本字段类型

| 字符串 | text(分词) ； | keyword(不分词) ； | StringField(不分词文本)； | TextFiled(要分词文本) |       |
| ------ | ------------- | ------------------ | ------------------------- | --------------------- | ----- |
| 数字   | long          | integer            | short                     | double                | float |
| 日期   | date          |                    |                           |                       |       |
| 逻辑   | boolean       |                    |                           |                       |       |

创建新的索引库

```
put aigou
1
```

单类型创建映射

```json
put aigou/goods/_mapping
{
	"goods": {
        "properties": {
            "id": {
                "type": "long"
            },
            "name": {
                "type": "text",
                "analyzer": "ik_smart",
                "search_analyzer": "ik_smart"
            }
        }
    }
}
123456789101112131415
```

解释：给aigou索引库中的是goods类型创建映射 ，id指定为long类型 ， name指定为text类型(要分词)，analyzer分词使用ik，查询分词器也使用ik

多类型创建映射

```json
PUT aigou
{
  "mappings": {
    "user": {
      "properties": {
        "id": {
          "type": "integer"
        },
        "info": {
          "type": "text",
          "analyzer": "ik_smart",
          "search_analyzer": "ik_smart"
        }
      }
    },
    "dept": {
      "properties": {
        "id": {
          "type": "integer"
        },
        ....更多字段映射配置
      }
    }
  }
}
12345678910111213141516171819202122232425
```

#### 十. JavaApi操作ES

官方文档API：https://www.elastic.co/guide/en/elasticsearch/client/java-api/index.html

##### 导入依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.elasticsearch.client</groupId>
        <artifactId>transport</artifactId>
        <version>6.8.6</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
</dependencies>
123456789101112
```

##### 连接ElasticSearch

编写工具

```java
public class ESClientUtil {

    public static TransportClient getClient(){
        TransportClient client = null;
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

}
12345678910111213141516
```

##### 文档CRUD

1. 添加文档

   ```java
   @Test
   public void testAdd() {
     //获取客户端对象
     TransportClient client = ESClientUtil.getClient();
   
     //创建索引
     IndexRequestBuilder indexRequestBuilder = client.prepareIndex("shopping", "user", "1");
     Map<String,Object> data = new HashMap<>();
     data.put("id",1);
     data.put("username","zs");
     data.put("age",11);
     //获取结果
     IndexResponse indexResponse = indexRequestBuilder.setSource(data).get();
   
     System.out.println(indexResponse);
     client.close();
   }
   1234567891011121314151617
   ```

2. 获取文档

   ```java
    @Test
       public void get() {
           final TransportClient client = ESClientUtil.getClient();
           final GetResponse response = client.prepareGet("shopping", "user", "2").get();
           final Map<String, Object> source = response.getSource();
           source.forEach((key, val) -> {
               System.out.println(key + ":" + val);
           });
           System.out.println(response);
                   client.close();
       }
   1234567891011
   ```

3. 更新文档

   ```java
   @Test
       public void testUpdate(){
           //获取客户端对象
           TransportClient client = ESClientUtil.getClient();
   
           //修改索引
           UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate("shopping", "user", "1");
           Map<String,Object> data = new HashMap<>();
           data.put("id",1);
           data.put("username","zs");
           data.put("age",11);
           //获取结果设置修改内容
           UpdateResponse updateResponse = updateRequestBuilder.setDoc(data).get();
   
           System.out.println(updateResponse);
           client.close();
       }
   1234567891011121314151617
   ```

4. 删除文档

   ```java
    @Test
       public void testDelete(){
           //获取客户端对象
           TransportClient client = ESClientUtil.getClient();
   
           DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete("shopping", "user", "1");
           DeleteResponse deleteResponse = deleteRequestBuilder.get();
   
           System.out.println(deleteResponse);
           client.close();
       }
   1234567891011
   ```

5. 批量操作

   ```java
    @Test
       public void testBuilkAdd(){
           //获取客户端对象
           TransportClient client = ESClientUtil.getClient();
   
           BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
   
           Map<String,Object> data1 = new HashMap<>();
           data1.put("id",11);
           data1.put("username","zs");
           data1.put("age",11);
   
           bulkRequestBuilder.add(client.prepareIndex("shopping", "user", "11").setSource(data1));
   
           Map<String,Object> data2 = new HashMap<>();
           data2.put("id",22);
           data2.put("username","zs");
           data2.put("age",11);
   
           bulkRequestBuilder.add(client.prepareIndex("shopping", "user", "11").setSource(data2));
   
           BulkResponse bulkItemResponses = bulkRequestBuilder.get();
           Iterator<BulkItemResponse> iterator = bulkItemResponses.iterator();
           while(iterator.hasNext()){
               BulkItemResponse next = iterator.next();
               System.out.println(next.getResponse());
           }
           client.close();
       }
   1234567891011121314151617181920212223242526272829
   ```

6. 查询

   ```java
   @Test
       public void testSearch(){
           //获取客户端对象
           TransportClient client = ESClientUtil.getClient();
   
   
           SearchRequestBuilder searchRequestBuilder = client.prepareSearch("shopping");
           searchRequestBuilder.setTypes("user");
           searchRequestBuilder.setFrom(0);
           searchRequestBuilder.setSize(10);
           searchRequestBuilder.addSort("age", SortOrder.ASC);
   
           //查询条件
           BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
           List<QueryBuilder> must = boolQueryBuilder.must();
           must.add(QueryBuilders.matchQuery("username" , "zs"));
   
           List<QueryBuilder> filter = boolQueryBuilder.filter();
           filter.add(QueryBuilders.rangeQuery("age").lte(20).gte(10));
           filter.add(QueryBuilders.termQuery("id",11));
   
           searchRequestBuilder.setQuery(boolQueryBuilder);
   
           SearchResponse searchResponse = searchRequestBuilder.get();
   
           SearchHits hits = searchResponse.getHits();
   
           System.out.println("条数："+hits.getTotalHits());
           for (SearchHit hit : hits.getHits()) {
               System.out.println(hit.getSourceAsMap());
           }
           client.close();
       }
   123456789101112131415161718192021222324252627282930313233
   ```

(“username” , “zs”));

```
       List<QueryBuilder> filter = boolQueryBuilder.filter();
       filter.add(QueryBuilders.rangeQuery("age").lte(20).gte(10));
       filter.add(QueryBuilders.termQuery("id",11));

       searchRequestBuilder.setQuery(boolQueryBuilder);

       SearchResponse searchResponse = searchRequestBuilder.get();

       SearchHits hits = searchResponse.getHits();

       System.out.println("条数："+hits.getTotalHits());
       for (SearchHit hit : hits.getHits()) {
           System.out.println(hit.getSourceAsMap());
       }
       client.close();
   }
12345678910111213141516

```