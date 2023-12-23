# 1、背景

  ①项目开发中，比如一本书，具有多级目录；一个一级分类下面还有二级分类，二级下面有三级…。此时需要后端返回一颗完整的无限层级的菜单树。

​        ② 爬虫语雀时想要自动识别到分组创建文件夹

# 2、思路

构建树的一般思想都是使用Map之类的映射处理或者使用递归之类的处理，Map有个好处是直观明。但是递归对数据保护的比较好。

- 找出所有的顶级父级，组装成List。
- 递归找出每一个顶级父级的孩子节点。

# 3、实现

## 3.1、递归实现

<img src="Java%E9%80%92%E5%BD%92%E3%80%81%E9%9D%9E%E9%80%92%E5%BD%92%E6%9E%84%E5%BB%BA%E5%85%B7%E6%9C%89%E6%97%A0%E7%BA%BF%E5%B1%82%E7%BA%A7%E7%88%B6%E5%AD%90%E5%85%B3%E7%B3%BB%E7%9A%84%E7%9B%AE%E5%BD%95%E3%80%81%E8%8F%9C%E5%8D%95.assets/image-20231116232420423.png" alt="image-20231116232420423" style="zoom:67%;" />

```java
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 说明：构建无限层级树
 *
 */
public class Test {

	public static void main(String[] args) {

		//模仿数据库中查询出来的数据
		List<Person> dataIsSort = getData(true);
		List<Person> dataNoSort = getData(false);

		//获取顶级父级节点
		List<Person> parentListIsSort = getParentList(dataIsSort);
		List<Person> parentListNoSort = getParentList(dataNoSort);

		//排序
		for (Person parent : parentListIsSort) {
			parent.setChildren(getChildrenList(dataIsSort, parent));
		}

		//不排
		for (Person parent : parentListNoSort) {
			parent.setChildren(getChildrenList(dataNoSort, parent));
		}

		System.out.println("排序后的JSON:************************************************************************");
		System.out.println(JSON.toJSONString(parentListIsSort));
		System.out.println("不排序后的JSON:************************************************************************");
		System.out.println(JSON.toJSONString(parentListNoSort));
	}

	/**
	 * 获取所有的顶级父级节点
	 *
	 * @param allPersonList
	 * 		所有节点
	 * @return 返回所有的顶级父节点
	 */
	private static List<Person> getParentList(final List<Person> allPersonList) {
		//顶级父级节点的parent为0
		return allPersonList.stream().filter(p -> Objects.equals(p.getParentId(), 0)).collect(Collectors.toList());
	}

	/**
	 * 获取顶级父节点的孩子节点
	 *
	 * @param allPersonList
	 * 		所有的数据
	 * @param parent
	 * 		顶级父
	 * @return 孩子节点
	 */
	private static List<Person> getChildrenList(final List<Person> allPersonList, final Person parent) {

		//遍历所有的节点，获取当前父节点的所以的孩子节点
		List<Person> childrenList = allPersonList.stream().filter(p -> Objects.equals(p.getParentId(), parent.getId())).collect(Collectors.toList());

		//递归处理：找到孩子节点的孩子节点，直至其没有孩子节点
		for (Person child : childrenList) {

			//当前孩子节点child作为父节点继续遍历
			child.setChildren(getChildrenList(allPersonList, child));
		}

		//返回孩子节点，此时孩子节点的孩子节点都已经填充满了
		return childrenList;
	}

	/**
	 * 构造带有父子关系的数据
	 *
	 * @param isSort
	 * 		是否排序，模仿需要指定顺序的情况，此处指定为从小到大
	 * @return 初始化数据
	 */
	private static List<Person> getData(boolean isSort) {

		Person p1 = new Person(1, 0, "顶级父1");
		Person p3 = new Person(3, 1, "子1_3");
		Person p4 = new Person(4, 1, "子1_4");

		Person p21 = new Person(21, 3, "子1_3_21");
		Person p22 = new Person(22, 4, "子1_4_22");
		Person p23 = new Person(23, 4, "子1_4_23");
		Person p24 = new Person(24, 23, "子1_4_23_24");

		Person p5 = new Person(5, 1, "子1_5");
		Person p6 = new Person(6, 1, "子1_6");
		Person p7 = new Person(7, 1, "子1_7");

		Person p2 = new Person(2, 0, "顶级父2");
		Person p8 = new Person(8, 2, "子2_8");

		Person p14 = new Person(14, 8, "子2_8_14");
		Person p15 = new Person(15, 8, "子2_8_15");
		Person p17 = new Person(17, 8, "子2_8_17");

		Person p18 = new Person(18, 14, "子2_8_14_18");
		Person p19 = new Person(19, 14, "子2_8_14_19");
		Person p20 = new Person(20, 15, "子2_8_15_20");

		Person p9 = new Person(9, 2, "子2_9");
		Person p10 = new Person(10, 2, "子2_10");
		Person p11 = new Person(11, 2, "子2_11");
		Person p12 = new Person(12, 2, "子2_12");
		Person p13 = new Person(13, 2, "子2_13");

		Person p16 = new Person(16, 0, "顶级父16");

		List<Person> list = new ArrayList<Person>();

		//打乱排序
		list.add(p15);
		list.add(p14);
		list.add(p8);

		list.add(p23);

		list.add(p11);
		list.add(p12);
		list.add(p5);
		list.add(p13);
		list.add(p18);
		list.add(p19);

		list.add(p24);

		list.add(p10);
		list.add(p17);

		list.add(p7);
		list.add(p9);
		list.add(p21);
		list.add(p4);
		list.add(p16);
		list.add(p6);

		list.add(p20);
		list.add(p22);
		list.add(p1);
		list.add(p2);
		list.add(p3);

		if (isSort) {
			return list.stream().distinct().sorted(Comparator.comparingInt(Person::getId)).collect(Collectors.toList());
		}

		return list.stream().distinct().collect(Collectors.toList());
	}
}

class Person {

	/**
	 * 主id，不可能为空
	 */
	private Integer id;

	/**
	 * 父id，当其为0时表示为顶级父
	 */
	private Integer parentId;

	private String name;

	/**
	 * 孩子节点
	 */
	private List<Person> children;

	public Person(Integer id, Integer parentId, String name) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

}

```

## 3.2、[非递归](https://so.csdn.net/so/search?q=非递归&spm=1001.2101.3001.7020)实现

<img src="Java%E9%80%92%E5%BD%92%E3%80%81%E9%9D%9E%E9%80%92%E5%BD%92%E6%9E%84%E5%BB%BA%E5%85%B7%E6%9C%89%E6%97%A0%E7%BA%BF%E5%B1%82%E7%BA%A7%E7%88%B6%E5%AD%90%E5%85%B3%E7%B3%BB%E7%9A%84%E7%9B%AE%E5%BD%95%E3%80%81%E8%8F%9C%E5%8D%95.assets/image-20231117115302397.png" alt="image-20231117115302397" style="zoom:67%;" />

```java
	//非递归实现
	private static void method_2() {

		//获取模拟数据库的数据
//		List<Person> dataList = getData(false);  //存放数据: Person p9 = new Person(9, 2, "子2_9");
		List<Person> dataList = getData( true);  //存放数据: Person p9 = new Person(9, 2, "子2_9");

		//将List转化为Map，其中id为key，对象为value  比如:
		Map<Integer, Person> personMap = dataList.stream().collect(Collectors.toMap(Person::getId, person -> person));
		//System.out.println(personMap);
		/*
		 * {1=Test$Person@7ba4f24f, 2=Test$Person@3b9a45b3, 3=Test$Person@7699a589,
		 * 4=Test$Person@58372a00, 5=Test$Person@4dd8dc3, 6=Test$Person@6d03e736,
		 * 7=Test$Person@568db2f2, 8=Test$Person@378bf509, 9=Test$Person@5fd0d5ae,
		 * 10=Test$Person@2d98a335, 11=Test$Person@16b98e56, 12=Test$Person@7ef20235,
		 * 13=Test$Person@27d6c5e0, 14=Test$Person@4f3f5b24, 15=Test$Person@15aeb7ab,
		 * 16=Test$Person@7b23ec81, 17=Test$Person@6acbcfc0, 18=Test$Person@5f184fc6,
		 * 19=Test$Person@3feba861, 20=Test$Person@5b480cf9, 21=Test$Person@6f496d9f,
		 * 22=Test$Person@723279cf, 23=Test$Person@10f87f48, 24=Test$Person@b4c966a}
		 *
		 */

		//重新构建List
		List<Person> list = new ArrayList<>();

		//原List遍历
		for (Person person : dataList) {

			//获取当前对象的父级id
			Integer parentId = person.getParentId();

			//获取该父级id，对应的Person对象
			Person parentPerson = personMap.get(parentId);

			//父级对象为空，表明parentPerson为顶级父级对象，直接将其放入list：本循环运行完后，list的大小其实就是顶级父级的个数
			if (parentPerson == null) {
				list.add(person);
			} else {

				//获取父级对象的子节点
				List<Person> children = parentPerson.getChildren();  //属性
				/*
				// java中默认是用引用. 相当于children修改同时会改变parentPerson.getChildren()这个属性
				   这个方式很少见, 一般情况下会,写一些操作属性的方法
				   比如这里 可以在类中定义一个方法
				   public void addChildren(person person) {
				   		this.children.add(person);
				   }
				*/

				//孩子节点为空，则初始化孩子节点，并将当前节点插入到父节点的孩子节点中
				if (Objects.isNull(children) || children.isEmpty()) {

					//初始化孩子节点
					List<Person> tempList = new ArrayList<>();
					tempList.add(person);

					//添加到孩子节点
					parentPerson.setChildren(tempList);
				} else {
					//孩子节点不为空，直接把当前节点插入到父级节点的子节点中
					children.add(person);  //这个方式并不好理解
				}
			}
		}

		System.out.println(list);
		System.out.println("最后构建的菜单树为：*************************");
		System.out.println(JSON.toJSONString(list));
	}
```

运行结果

```
排序后的JSON:************************************************************************
[{"children":[{"children":[{"children":[],"id":21,"name":"子1_3_21","parentId":3}],"id":3,"name":"子1_3","parentId":1},{"children":[{"children":[],"id":22,"name":"子1_4_22","parentId":4},{"children":[{"children":[],"id":24,"name":"子1_4_23_24","parentId":23}],"id":23,"name":"子1_4_23","parentId":4}],"id":4,"name":"子1_4","parentId":1},{"children":[],"id":5,"name":"子1_5","parentId":1},{"children":[],"id":6,"name":"子1_6","parentId":1},{"children":[],"id":7,"name":"子1_7","parentId":1}],"id":1,"name":"顶级父1","parentId":0},{"children":[{"children":[{"children":[{"children":[],"id":18,"name":"子2_8_14_18","parentId":14},{"children":[],"id":19,"name":"子2_8_14_19","parentId":14}],"id":14,"name":"子2_8_14","parentId":8},{"children":[{"children":[],"id":20,"name":"子2_8_15_20","parentId":15}],"id":15,"name":"子2_8_15","parentId":8},{"children":[],"id":17,"name":"子2_8_17","parentId":8}],"id":8,"name":"子2_8","parentId":2},{"children":[],"id":9,"name":"子2_9","parentId":2},{"children":[],"id":10,"name":"子2_10","parentId":2},{"children":[],"id":11,"name":"子2_11","parentId":2},{"children":[],"id":12,"name":"子2_12","parentId":2},{"children":[],"id":13,"name":"子2_13","parentId":2}],"id":2,"name":"顶级父2","parentId":0},{"children":[],"id":16,"name":"顶级父16","parentId":0}]
不排序后的JSON:************************************************************************
[{"children":[],"id":16,"name":"顶级父16","parentId":0},{"children":[{"children":[],"id":5,"name":"子1_5","parentId":1},{"children":[],"id":7,"name":"子1_7","parentId":1},{"children":[{"children":[{"children":[],"id":24,"name":"子1_4_23_24","parentId":23}],"id":23,"name":"子1_4_23","parentId":4},{"children":[],"id":22,"name":"子1_4_22","parentId":4}],"id":4,"name":"子1_4","parentId":1},{"children":[],"id":6,"name":"子1_6","parentId":1},{"children":[{"children":[],"id":21,"name":"子1_3_21","parentId":3}],"id":3,"name":"子1_3","parentId":1}],"id":1,"name":"顶级父1","parentId":0},{"children":[{"children":[{"children":[{"children":[],"id":20,"name":"子2_8_15_20","parentId":15}],"id":15,"name":"子2_8_15","parentId":8},{"children":[{"children":[],"id":18,"name":"子2_8_14_18","parentId":14},{"children":[],"id":19,"name":"子2_8_14_19","parentId":14}],"id":14,"name":"子2_8_14","parentId":8},{"children":[],"id":17,"name":"子2_8_17","parentId":8}],"id":8,"name":"子2_8","parentId":2},{"children":[],"id":11,"name":"子2_11","parentId":2},{"children":[],"id":12,"name":"子2_12","parentId":2},{"children":[],"id":13,"name":"子2_13","parentId":2},{"children":[],"id":10,"name":"子2_10","parentId":2},{"children":[],"id":9,"name":"子2_9","parentId":2}],"id":2,"name":"顶级父2","parentId":0}]

```

排序后的结果如下

```json
[
    {
        "children": [
            {
                "children": [
                    {
                        "children": [],
                        "id": 21,
                        "name": "子1_3_21",
                        "parentId": 3
                    }
                ],
                "id": 3,
                "name": "子1_3",
                "parentId": 1
            },
            {
                "children": [
                    {
                        "children": [],
                        "id": 22,
                        "name": "子1_4_22",
                        "parentId": 4
                    },
                    {
                        "children": [
                            {
                                "children": [],
                                "id": 24,
                                "name": "子1_4_23_24",
                                "parentId": 23
                            }
                        ],
                        "id": 23,
                        "name": "子1_4_23",
                        "parentId": 4
                    }
                ],
                "id": 4,
                "name": "子1_4",
                "parentId": 1
            },
            {
                "children": [],
                "id": 5,
                "name": "子1_5",
                "parentId": 1
            },
            {
                "children": [],
                "id": 6,
                "name": "子1_6",
                "parentId": 1
            },
            {
                "children": [],
                "id": 7,
                "name": "子1_7",
                "parentId": 1
            }
        ],
        "id": 1,
        "name": "顶级父1",
        "parentId": 0
    },
    {
        "children": [
            {
                "children": [
                    {
                        "children": [
                            {
                                "children": [],
                                "id": 18,
                                "name": "子2_8_14_18",
                                "parentId": 14
                            },
                            {
                                "children": [],
                                "id": 19,
                                "name": "子2_8_14_19",
                                "parentId": 14
                            }
                        ],
                        "id": 14,
                        "name": "子2_8_14",
                        "parentId": 8
                    },
                    {
                        "children": [
                            {
                                "children": [],
                                "id": 20,
                                "name": "子2_8_15_20",
                                "parentId": 15
                            }
                        ],
                        "id": 15,
                        "name": "子2_8_15",
                        "parentId": 8
                    },
                    {
                        "children": [],
                        "id": 17,
                        "name": "子2_8_17",
                        "parentId": 8
                    }
                ],
                "id": 8,
                "name": "子2_8",
                "parentId": 2
            },
            {
                "children": [],
                "id": 9,
                "name": "子2_9",
                "parentId": 2
            },
            {
                "children": [],
                "id": 10,
                "name": "子2_10",
                "parentId": 2
            },
            {
                "children": [],
                "id": 11,
                "name": "子2_11",
                "parentId": 2
            },
            {
                "children": [],
                "id": 12,
                "name": "子2_12",
                "parentId": 2
            },
            {
                "children": [],
                "id": 13,
                "name": "子2_13",
                "parentId": 2
            }
        ],
        "id": 2,
        "name": "顶级父2",
        "parentId": 0
    },
    {
        "children": [],
        "id": 16,
        "name": "顶级父16",
        "parentId": 0
    }
]
```

![image-20231116231626322](Java%E9%80%92%E5%BD%92%E3%80%81%E9%9D%9E%E9%80%92%E5%BD%92%E6%9E%84%E5%BB%BA%E5%85%B7%E6%9C%89%E6%97%A0%E7%BA%BF%E5%B1%82%E7%BA%A7%E7%88%B6%E5%AD%90%E5%85%B3%E7%B3%BB%E7%9A%84%E7%9B%AE%E5%BD%95%E3%80%81%E8%8F%9C%E5%8D%95.assets/image-20231116231626322.png)



# 4. 综合代码

```java


import com.alibaba.fastjson2.JSON;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: LFJ
 * @Date: 2023-11-16 11:54
 */
public class Test {
	static class Person {

		/**
		 * 主id，不可能为空
		 */
		private Integer id;

		/**
		 * 父id，当其为0时表示为顶级父
		 */
		private Integer parentId;

		private String name;

		/**
		 * 孩子节点
		 */
		private List<Person> children;

		public Person(Integer id, Integer parentId, String name) {
			this.id = id;
			this.parentId = parentId;
			this.name = name;
		}

		public Integer getParentId() {
			return parentId;
		}

		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		// 设置孩子
		public List<Person> getChildren() {
			return children;
		}

		public void setChildren(List<Person> children) {
			this.children = children;
		}

	}

	/**
	 * 构造带有父子关系的数据  模拟数据
	 *
	 * @param isSort
	 * 		是否排序，模仿需要指定顺序的情况，此处指定为从小到大
	 * @return 初始化数据
	 */
	private static List<Person> getData(boolean isSort) {

		Person p1 = new Person(1, 0, "顶级父1");
		Person p3 = new Person(3, 1, "子1_3");
		Person p4 = new Person(4, 1, "子1_4");

		Person p21 = new Person(21, 3, "子1_3_21");
		Person p22 = new Person(22, 4, "子1_4_22");
		Person p23 = new Person(23, 4, "子1_4_23");
		Person p24 = new Person(24, 23, "子1_4_23_24");

		Person p5 = new Person(5, 1, "子1_5");
		Person p6 = new Person(6, 1, "子1_6");
		Person p7 = new Person(7, 1, "子1_7");

		Person p2 = new Person(2, 0, "顶级父2");
		Person p8 = new Person(8, 2, "子2_8");

		Person p14 = new Person(14, 8, "子2_8_14");
		Person p15 = new Person(15, 8, "子2_8_15");
		Person p17 = new Person(17, 8, "子2_8_17");

		Person p18 = new Person(18, 14, "子2_8_14_18");
		Person p19 = new Person(19, 14, "子2_8_14_19");
		Person p20 = new Person(20, 15, "子2_8_15_20");

		Person p9 = new Person(9, 2, "子2_9");
		Person p10 = new Person(10, 2, "子2_10");
		Person p11 = new Person(11, 2, "子2_11");
		Person p12 = new Person(12, 2, "子2_12");
		Person p13 = new Person(13, 2, "子2_13");

		Person p16 = new Person(16, 0, "顶级父16");

		List<Person> list = new ArrayList<Person>();

		//打乱排序
		list.add(p15);
		list.add(p14);
		list.add(p8);

		list.add(p23);

		list.add(p11);
		list.add(p12);
		list.add(p5);
		list.add(p13);
		list.add(p18);
		list.add(p19);

		list.add(p24);

		list.add(p10);
		list.add(p17);

		list.add(p7);
		list.add(p9);
		list.add(p21);
		list.add(p4);
		list.add(p16);
		list.add(p6);

		list.add(p20);
		list.add(p22);
		list.add(p1);
		list.add(p2);
		list.add(p3);

		if (isSort) {
			// 通过Person::getId 升序
			return list.stream().distinct().sorted(Comparator.comparingInt(Person::getId)).collect(Collectors.toList());
		}
		// 通过
//		System.out.println(list.stream().distinct().collect(Collectors.toList()));
//		return list.stream().distinct().collect(Collectors.toList());
		return list;
	}

	/**
	 * 获取所有的顶级父级节点
	 *
	 * @param allPersonList
	 * 		所有节点
	 * @return 返回所有的顶级父节点
	 */
	private static List<Person> getParentList(final List<Person> allPersonList) {
		//顶级父级节点的parent为0, 通过过滤拿到
		return allPersonList.stream().filter(p -> Objects.equals(p.getParentId(), 0)).collect(Collectors.toList());
	}

	/**
	 * 获取顶级父节点的孩子节点
	 *
	 * @param allPersonList
	 * 		所有的数据
	 * @param parent
	 * 		顶级父
	 * @return 孩子节点
	 */
	private static List<Person> getChildrenList(final List<Person> allPersonList, final Person parent) {

		//遍历所有的节点，获取当前父节点的所以的孩子节点
		List<Person> childrenList = allPersonList.stream().filter(p -> Objects.equals(p.getParentId(), parent.getId())).collect(Collectors.toList());

		//递归处理：找到孩子节点的孩子节点，直至其没有孩子节点
		for (Person child : childrenList) {

			//当前孩子节点child作为父节点继续遍历
			child.setChildren(getChildrenList(allPersonList, child));
		}

		//返回孩子节点，此时孩子节点的孩子节点都已经填充满了
		return childrenList;
	}

	// 递归
	private static void method_1() {
		//模仿数据库中查询出来的数据
		List<Person> dataIsSort = getData(true);
		List<Person> dataNoSort = getData(false);

		//获取顶级父级节点
		List<Person> parentListIsSort = getParentList(dataIsSort);
		List<Person> parentListNoSort = getParentList(dataNoSort);

		//排序
		for (Person parent : parentListIsSort) {
			//得到子级
			parent.setChildren(getChildrenList(dataIsSort, parent));
		}

		//不排
		for (Person parent : parentListNoSort) {
			parent.setChildren(getChildrenList(dataNoSort, parent));
		}

		System.out.println("排序后的JSON:************************************************************************");
		System.out.println(JSON.toJSONString(parentListIsSort));  //把List<Person>数组解析成json
		System.out.println("不排序后的JSON:************************************************************************");
		System.out.println(JSON.toJSONString(parentListNoSort));

	}

	//非递归实现
	private static void method_2() {

		//获取模拟数据库的数据
//		List<Person> dataList = getData(false);  //存放数据: Person p9 = new Person(9, 2, "子2_9");
		List<Person> dataList = getData( true);  //存放数据: Person p9 = new Person(9, 2, "子2_9");

		//将List转化为Map，其中id为key，对象为value  比如:
		Map<Integer, Person> personMap = dataList.stream().collect(Collectors.toMap(Person::getId, person -> person));
		//System.out.println(personMap);
		/*
		 * {1=Test$Person@7ba4f24f, 2=Test$Person@3b9a45b3, 3=Test$Person@7699a589,
		 * 4=Test$Person@58372a00, 5=Test$Person@4dd8dc3, 6=Test$Person@6d03e736,
		 * 7=Test$Person@568db2f2, 8=Test$Person@378bf509, 9=Test$Person@5fd0d5ae,
		 * 10=Test$Person@2d98a335, 11=Test$Person@16b98e56, 12=Test$Person@7ef20235,
		 * 13=Test$Person@27d6c5e0, 14=Test$Person@4f3f5b24, 15=Test$Person@15aeb7ab,
		 * 16=Test$Person@7b23ec81, 17=Test$Person@6acbcfc0, 18=Test$Person@5f184fc6,
		 * 19=Test$Person@3feba861, 20=Test$Person@5b480cf9, 21=Test$Person@6f496d9f,
		 * 22=Test$Person@723279cf, 23=Test$Person@10f87f48, 24=Test$Person@b4c966a}
		 *
		 */

		//重新构建List
		List<Person> list = new ArrayList<>();

		//原List遍历
		for (Person person : dataList) {

			//获取当前对象的父级id
			Integer parentId = person.getParentId();

			//获取该父级id，对应的Person对象
			Person parentPerson = personMap.get(parentId);

			//父级对象为空，表明parentPerson为顶级父级对象，直接将其放入list：本循环运行完后，list的大小其实就是顶级父级的个数
			if (parentPerson == null) {
				list.add(person);
			} else {

				//获取父级对象的子节点
				List<Person> children = parentPerson.getChildren();  //属性
				/*
				// java中默认是用引用. 相当于children修改同时会改变parentPerson.getChildren()这个属性
				   这个方式很少见, 一般情况下会,写一些操作属性的方法
				   比如这里 可以在类中定义一个方法
				   public void addChildren(person person) {
				   		this.children.add(person);
				   }
				*/

				//孩子节点为空，则初始化孩子节点，并将当前节点插入到父节点的孩子节点中
				if (Objects.isNull(children) || children.isEmpty()) {

					//初始化孩子节点
					List<Person> tempList = new ArrayList<>();
					tempList.add(person);

					//添加到孩子节点
					parentPerson.setChildren(tempList);
				} else {
					//孩子节点不为空，直接把当前节点插入到父级节点的子节点中
					children.add(person);  //这个方式并不好理解
				}
			}
		}

		System.out.println(list);
		System.out.println("最后构建的菜单树为：*************************");
		System.out.println(JSON.toJSONString(list));
	}

	// 非递归
	public static void main(String[] args) {
		//method_1();
		method_2();
	}
}
```

