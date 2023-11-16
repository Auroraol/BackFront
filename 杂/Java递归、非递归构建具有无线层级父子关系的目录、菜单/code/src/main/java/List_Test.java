import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: LFJ
 * @Date: 2023-11-16 15:43
 */

//一、本案例对象
class City {
	private  String name;
	private int  total;
	private int  num;

	public City(String name, int total) {
		this.name = name;
		this.total = total;

	}

	public City(String name, int total, int num) {
		this.name = name;
		this.total = total;
		this.num = num;
	}

	public String getCity() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}

//// 获取list中所有对象的某个属性
//      List<String> list= cities.stream().map(City::getCity).collect(Collectors.toList());
//		List<String> list= cities.stream().map(o->o.getCity()).collect(Collectors.toList());
//
// 分组
//		Map<String, List<City>> map =  cities.stream().collect(Collectors.groupingBy(City :: getCity));
//		Map<String, List<City>> map =  cities.stream().filter(v -> StringUtils.isNotEmpty(v.getCity())).collect(Collectors.groupingBy(City :: getCity));


//二、去重
class ListUtil {

//	public static List<String> list = Arrays.asList("1","2","2","3","3","4","4","5","6","7","8");
	public static List<String> list = new ArrayList<String>(Arrays.asList("1","2","2","3","3","4","4","5","6","7","8"));
	public static List<City> cities = null;
	static {
		cities = new ArrayList<City>(){
			{
				add(new City("上海",11));
				add(new City("武汉",22));
				add(new City("武汉",55));
				add(new City("上海",33));
				add(new City("北京",33));
				add(new City("深圳",43));
			}
		};
	}

	public static void main(String[] args) {
		System.out.println(ListUtil.distinctElements(list));
		System.out.println(ListUtil.getNoDuplicateElements(list));
		System.out.println(ListUtil.getDuplicateElements(list));
		System.out.println(ListUtil.getDuplicateElementsForObject(cities));
		System.out.println(ListUtil.getNoDuplicateElementsForObject(cities));
		System.out.println(ListUtil.getElementsAfterDuplicate(cities));
		System.out.println(ListUtil.getDuplicateObject(cities));
		System.out.println(ListUtil.getNoDuplicateObject(cities));
		System.out.println(ListUtil.distinctObject(cities));
	}

	/**-----------基础数据类型--------------*/
	// 方法1
	//去重后的集合 [1, 2, 3, 4, 5, 6, 7, 8]
	public static <T> List<T> distinctElements(List<T> list) {
		return list.stream().distinct().collect(Collectors.toList());
	}

	//方法2 lambda表达式  对象数据类型, 推荐使用这个
	//lambda表达式 去除集合所有重复的值  [1, 5, 6, 7, 8]
	public static <T> List<T> getNoDuplicateElements(List<T> list) {
		// 获得元素出现频率的 Map，键为元素，值为元素出现的次数  得到{1=1, 2=2, 3=2, 4=2, 5=1, 6=1, 7=1, 8=1}
		Map<T, Long> map = list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));  //  // xx -> xx 就表示直接用list中的数据, 这里就是 1 2 3...
		//
		System.out.println("getDuplicateElements2: "+map);
		return map.entrySet().stream() // Set<Entry>转换为Stream<Entry>  //map.entrySet()得到每个键值对
				.filter(entry -> entry.getValue() == 1) // 过滤出元素出现次数等于 1 的 entry //及只出现一次的
				.map(entry -> entry.getKey()) // 获得 entry 的键 //及重复元素）对应的 Stream
				.collect(Collectors.toList()); // 转化为 List
	}

	//lambda表达式 查找出重复的集合 [2, 3, 4]
	public static <T> List<T> getDuplicateElements(List<T> list) {
		return list.stream().collect(Collectors.collectingAndThen(Collectors
				.groupingBy(p -> p, Collectors.counting()), map->{
			map.values().removeIf(size -> size ==1); // >1 查找不重复的集合；== 1 查找重复的集合

			List<T> tempList = new ArrayList<>(map.keySet());
			return tempList;
		}));
	/*
	list.stream(): 将列表转换为流，以便进行流式操作。
	Collectors.groupingBy(p -> p, Collectors.counting()): 对元素进行分组并计数。

	Collectors.collectingAndThen(...): 这是一个收集器，它接受两个参数：第一个是主要的收集器（在这里是 groupingBy），第二个是转换函数，将主要收集器的结果进行最终的转换。

	map -> { ... }: 这是转换函数，接受 groupingBy 的结果，即 Map<T, Long>，其中键是元素，值是元素出现的次数。

	map.values().removeIf(size -> size == 1): 这一行代码移除了出现次数为1的元素。map.values() 返回了所有的计数，
											然后使用 removeIf 方法移除出现次数为1的元素。

	List<T> tempList = new ArrayList<>(map.keySet()): 最后，将修改后的键集合转换为一个新的列表。
	 */
	}

	// 方法3
	//利用set集合
	public static <T> Set<T> getDuplicateElements2(List<T> list) {
		Set<T> set = new HashSet<>();
		Set<T> exist = new HashSet<>();
		for (T s : list) {
			if (set.contains(s)) {
				exist.add(s);
			} else {
				set.add(s);
			}
		}
		return exist;
	}

	//利用LinkedHashSet集合  [1, 2, 3, 4, 5, 6, 7, 8]   保存顺序  //简单数据类型, 推荐使用这个
	public static <T> Set<T> getDuplicateElements3(List<T> list) {
		Set<T> exist = new LinkedHashSet<>(list);
		return exist;
	}

	/**
	 * -----------对象--------------
	 */

	//原属重复的 属性集合   [上海, 武汉]
	public static List<Object> getDuplicateElementsForObject(List<City> list) {
		Map<Object, Long> map = list.stream().collect(Collectors.groupingBy(p -> p.getCity(),Collectors.counting()));
		return map.entrySet().stream()
				.filter(entry -> entry.getValue() > 1) // >1 查找重复的集合；== 查找不重复的集合
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}

	//原属未重复的 属性集合   [深圳, 北京]
	public static List<String> getNoDuplicateElementsForObject(List<City> list){
		Map<String,List<City>> map = list.stream().collect(Collectors.groupingBy(City::getCity));
		return map.entrySet().stream().filter(entry -> entry.getValue().size() == 1)
				.map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
				.collect(Collectors.toList()); // 转化为 List

	}

	//原属去重后的 属性集合(重复的保留一个) [上海, 武汉, 北京, 深圳]
	public static List<String> getElementsAfterDuplicate(List<City> list) {
		return list.stream().map(o->o.getCity()).distinct().collect(Collectors.toList());
	}

	//对象中某个原属重复的 对象集合
	// [[City(city=上海, total=11), City(city=上海, total=33)], [City(city=武汉, total=22), City(city=武汉, total=55)]]
	public static List<List<City>> getDuplicateObject(List<City> list) {
		return list.stream().collect(Collectors.groupingBy(City::getCity)).entrySet().stream()
				.filter(entry -> entry.getValue().size() > 1) // >1 查找重复的集合；== 查找不重复的集合
				.map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	//对象中某个原属未重复 对象集合
	//[[City(city=深圳, total=43)], [City(city=北京, total=33)]]
	public static List<City> getNoDuplicateObject(List<City> list) {
		List<City> cities = new ArrayList<>();
		list.stream().collect(Collectors.groupingBy(City::getCity)).entrySet().stream()
				.filter(entry -> entry.getValue().size() ==1) //>1 查找重复的集合；== 查找不重复的集合;
				.map(entry -> entry.getValue())
				.forEach(p -> cities.addAll(p));
		return cities;
	}


	//根据对象的某个原属去重后的(重复的保留一个) 对象集合
	//[City(city=上海, total=11), City(city=武汉, total=22), City(city=北京, total=33), City(city=深圳, total=43)]
	public static List<City> distinctObject(List<City> list) {
		return list.stream().filter(distinctByKey(City::getCity)).collect(Collectors.toList());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
	}
}

//三、排序
class ListUtil_sort {

	public static List<Integer> list = Arrays.asList(10,1,6,4,8,7,9,3,2,5);
	public static List<City> cities = null;
	public static List<City> cities2 = null;
	static {
		cities = new ArrayList<City>(){
			{
				add(new City("上海",11));
				add(new City("武汉",55));
				add(new City("南京",33));
				add(new City("深圳",33));
			}
		};

		cities2 = new ArrayList<City>(){
			{
				add(new City("上海",11,11));
				add(new City("武汉",55,22));
				add(new City("南京",33,55));
				add(new City("深圳",33,44));
			}
		};

	}
	public static void main(String[] args) {
		System.out.println(sort(list));
		System.out.println(reversed(list));
		System.out.println(sortForObject(cities));
		System.out.println(reversedForObject(cities));
		System.out.println(sortForObject2(cities2));
	}

	//list排序 升序
	public static <T> List<T> sort(List<T> list){
		return list.stream().sorted().collect(Collectors.toList());
	}

	//list排序 降序
	public static List<Integer> reversed(List<Integer> list){
		return list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}

	//根据对象某个属性排序  正序
	public static List<City> sortForObject(List<City> list){
		return list.stream().sorted(Comparator.comparing(City::getTotal)).collect(Collectors.toList());
	}

	//根据对象某个属性排序  倒序
	public static List<City> reversedForObject(List<City> list){
		return list.stream().sorted(Comparator.comparing(City::getTotal).reversed()).collect(Collectors.toList());
	}

	//根据对象两个属性排序  正序
	public static List<City> sortForObject2(List<City> list){
		return list.stream().sorted(Comparator.comparing(City::getTotal).thenComparing(City::getNum)).collect(Collectors.toList());
	}
}

//四、过滤、求和、最值, 批量修改list<object>中的某一个属性
class ListUtil_sum {

	public static List<Integer> list = Arrays.asList(10,1,6,4,8,7,9,3,2,5);
	public static List<String> strList = Arrays.asList("10","1","6","4");
	public static List<City> cities = null;
	public static Map<String,Integer> cityMap = null;
	static {
		cities = new ArrayList<City>(){
			{
				add(new City("上海",11));
				add(new City("武汉",55));
				add(new City("武汉",45));
				add(new City("深圳",33));
			}
		};
		cityMap = new HashMap<>();
		cityMap.put("武汉",55);
		cityMap.put("上海",11);
	}
	public static void main(String[] args) {
		//System.out.println(calculation(list));
		//calculation2(cities);
		//joinStringValueByList(cities);
		//joinStringValueByList2(strList);
		System.out.println(filter(cities));
	}


	//基础数据类型求各自值
	///IntSummaryStatistics{count=4, sum=132, min=11, average=33.000000, max=55}
	public static IntSummaryStatistics calculation(List<Integer> list){
		IntSummaryStatistics stat = list.stream().collect(Collectors.summarizingInt(p -> p));
		System.out.println("max:"+stat.getMax());
		System.out.println("min:"+stat.getMin());
		System.out.println("sum:"+stat.getSum());
		System.out.println("count:"+stat.getCount());
		System.out.println("average:"+stat.getAverage());
		Integer max = list.stream().reduce(Integer::max).get();//得到最大值
		Integer min = list.stream().reduce(Integer::min).get();//得到最小值
		System.out.println("max:"+max+"；min:"+min);
		return stat;
	}

	//对象某个属性求各自值
	public static void calculation2(List<City> list){
		System.out.println("sum="+ list.stream().mapToInt(City::getTotal).sum());
		System.out.println("max="+ list.stream().mapToInt(City::getTotal).max().getAsInt());
		System.out.println("min="+ list.stream().mapToInt(City::getTotal).min().getAsInt());
		System.out.println("ave="+ list.stream().mapToInt(City::getTotal).average().getAsDouble());
	}

	// 批量修改list<object>中的某一个属性
	//对象某个属性 等于特定值的累加
	public static void calculation11(List<City> list){
		Map<String, IntSummaryStatistics> intSummaryStatistics = list.stream().
				collect(Collectors.groupingBy(i -> i.getCity(), Collectors.summarizingInt(City::getTotal)));
		System.out.println("-4-->" + intSummaryStatistics);
		System.out.println("-5-->" + intSummaryStatistics.get("武汉").getSum());
	}

	//功能描述 姓名以逗号拼接   上海,武汉,武汉,深圳
	public static String joinStringValueByList(List<City> list){
		return list.stream().map(City::getCity).collect(Collectors.joining(","));
	}

	//功能描述 姓名以逗号拼接  10,1,6,4
	public static String joinStringValueByList2(List<String> list){
		//方式一
		return String.join(",", list);
		//方式二
//		System.out.println(list.stream().collect(Collectors.joining(",")));
	}

	//功能描述 过滤
	public static List<City> filter(List<City> list){
		return list.stream().filter(a -> a.getTotal()>44).collect(Collectors.toList());  //过滤掉list中 对象.getTotal()>44的对象
	}

}

//五、List对象转换
class ObjectSwitch{
	public static List<City> cities = null;
	public static Map<String,Integer> cityMap = null;
	static {
		cities = new ArrayList<City>(){
			{
				add(new City("上海",11));
				add(new City("武汉",55));
				add(new City("武汉",45));
				add(new City("深圳",33));
			}
		};
		cityMap = new HashMap<>();
		cityMap.put("武汉",55);
		cityMap.put("上海",11);
	}

	public static void main(String[] args) {
		arr2list();
		listToMap(cities);
		mapToList(cityMap);
//		stringToList("上海、武汉");
	}

	// 功能描述 数组转集合
	public static void arr2list() {
		// 默认 List<Integer> list = new ArrayList<>() 只能声明, 不能同时初始化
		int [] arr = {1,2,3,4,5,6,7,8};
		// 方法1
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		System.out.println(list);

		// 方法2
		List list2 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));  //asList()中必须是具体的值
		System.out.println(list2);

		// 方法3
		//基本类型
		List list3 =  Arrays.stream(arr).boxed().collect(Collectors.toList());

		// Integer
		Integer [] myArray = { 1, 2, 3 };
		List myList = Arrays.stream(myArray).collect(Collectors.toList());
		System.out.println(list3);
	}

	//功能描述 List转map
	public static void listToMap(List<City> list) {
		//用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
		Map<String, City> map = list.stream().collect(Collectors.toMap(City::getCity, city -> city, (k1, k2) -> k1));
		map.forEach((k, v) -> System.out.println("k=" + k + ",v=" + v));
		/*
		list.stream(): 将输入的列表转换为流，以便进行流式操作。
		Collectors.toMap(City::getCity, city -> city, (k1, k2) -> k1):
			使用toMap收集器，将流中的对象转换为Map。这个收集器接受三个参数：
				City::getCity: 这是键的提取器，使用每个City对象的getCity()方法作为键。
				city -> city: 这是值的提取器，直接将每个City对象作为值。  // xx -> xx 就表示直接用list中的数据, 这里就是 City对象
				(k1, k2) -> k1: 可选参数, 这是一个合并函数，当遇到具有相同键的元素时，它定义了如何合并这些元素。在这里，它简单地选择保留第一个出现的元素。
		最终结果是一个Map<String, City>，其中键是城市名，而值是具有相应城市名的City对象。
		如果列表中有重复的城市名，合并函数确保只有第一个出现的对象被保留在最终的Map中。
		* */
		/*
		k=上海,v=City@723279cf
		k=武汉,v=City@10f87f48
		k=深圳,v=City@b4c966a
		*/

		//
		Map<String, String> map2 = list.stream().collect(Collectors.toMap(City::getCity, City::getName, (k1, k2) -> k1));
		map2.forEach((k,v)->System.out.println("k="+k+",v="+v));
		/*
		k=上海,v=上海
		k=武汉,v=武汉
		k=深圳,v=深圳
		*/
		//
		//转换成map的时候，可能出现key一样的情况，如果不指定一个覆盖规则，上面的代码是会报错的。转成map的时候，最好使用下面的方式
		Map<Integer, City> map3 = list.stream().collect(Collectors.toMap(City::getTotal, Function.identity(), (k1,k2)->k1));
		map3.forEach((k, v) -> System.out.println("k=" + k + ", v=" + v));
		/*
		k=33, v=City@b4c966a
		k=55, v=City@10f87f48
		k=11, v=City@723279cf
		k=45, v=City@5b6f7412
		*/

	}

	//功能描述 map转list
	public static void mapToList(Map<String,Integer> map){
		List<City> list = map.entrySet().stream().map(entry -> new City(entry.getKey(),entry.getValue())).collect(Collectors.toList());
		/*
		 * map.entrySet().stream(): 这一部分首先将map的键值对转换为一个Stream<Entry>，其中每个Entry表示map中的一对键值。
		 * .map(entry -> new City(entry.getKey(), entry.getValue())): 使用map操作将每个Entry映射为一个新的City对象。这里假设 City 类有一个接受两个参数的构造函数，分别是城市名和城市对象。
		 * .collect(Collectors.toList()): 最后，使用collect操作将流中的元素收集到一个列表中。这个列表包含了通过映射操作创建的所有City对象。
		 * 最终结果是一个City对象的列表
		 */
		map.keySet().stream().collect(Collectors.toList());  // 把map的每一个key转成list
		map.values().stream().collect(Collectors.toList());  // 把map的每一个value转成list

		System.out.println(list);  //[City@30dae81, City@1b2c6ec2]
		list.forEach(bean -> System.out.println(bean.getCity() + "," + bean.getTotal()));
		/*
		上海,11
		武汉,55
		* */
	}

	//功能描述 字符串转list
	public static void stringToList(String str){
		//不需要处理
		//<String> list = Arrays.asList(str.split(","));
		//需要处理
		List<String> list = Arrays.asList(str.split(",")).stream().map(string -> String.valueOf(string)).collect(Collectors.toList());
		list.forEach(string -> System.out.println(string));
	}
}

//六、遍历
class each_Test{
	// 遍历Collection集合
	public static <T> void eachCollection(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Java");
		list.add("World");
		list.add("笔记");

		// 高级for遍历
		for (String value : list) {
			System.out.println(value);
		}

		// 使用for循环遍历List
		for (int i = 0; i < list.size(); i++){
			String s = (String) list.get(i);
			System.out.println(s);
		}

	}

	// 遍历map集合
	public static void eachMap(){
		Map<String, Integer> cityMap = new HashMap<>();
		cityMap.put("武汉",55);
		cityMap.put("上海",11);

		//方法1
		cityMap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));

		//方法2
		//获取Map中的所有key与value的对应关系
		Set<Map.Entry<String,Integer>> entrySet = cityMap.entrySet();
		//遍历Set集合
		Iterator<Map.Entry<String,Integer>> it =entrySet.iterator();
		while(it.hasNext()){
			//得到每一对对应关系
			Map.Entry<String,Integer> entry = it.next();
			//通过每一对对应关系获取对应的key
			String key = entry.getKey();
			//通过每一对对应关系获取对应的value
			Integer value = entry.getValue();
			System.out.println(key+"="+value);
		}

		//方法3
		Set<String> keySet = cityMap.keySet();  //获取Map中的所有key
		//遍历存放所有key的Set集合
		Iterator<String> it2 =keySet.iterator();
		while(it2.hasNext()){
			//得到每一个key
			String key = it2.next();
			//通过key获取对应的value
			Integer value = cityMap.get(key);
			System.out.println(key+"="+value);
		}
	}

	public static void main(String[] args) {
		eachCollection();
		eachMap();
	}
}

public class List_Test {
	public static void main(String[] args) {

	}
}
