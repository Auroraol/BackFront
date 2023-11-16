

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

	//非递归实现
	private static void method_2() {

		//获取模拟数据库的数据
		List<Person> dataList = getData(false);

		//将List转化为Map，其中id为key，对象为value
		Map<Integer, Person> personMap = dataList.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

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
				List<Person> children = parentPerson.getChildren();

				//孩子节点为空，则初始化孩子节点，并将当前节点插入到父节点的孩子节点中
				if (Objects.isNull(children) || children.isEmpty()) {

					//初始化孩子节点
					List<Person> tempList = new ArrayList<>();
					tempList.add(person);

					//添加到孩子节点
					parentPerson.setChildren(tempList);
				} else {
					//孩子节点不为空，直接把当前节点插入到父级节点的子节点中
					children.add(person);
				}
			}
		}

		System.out.println(list);
		System.out.println("最后构建的菜单树为：*************************");
		System.out.println(JSON.toJSONString(list));
	}


	public static void main(String[] args) {
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

//		method_2();
	}
}
