package cn.itcast.hotel.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * mysql对应的es文档实体类
 */
@Data
@NoArgsConstructor
public class HotelDoc {
	private Long id;
	private String name;
	private String address;
	private Integer price;
	private Integer score;
	private String brand;
	private String city;
	private String starName;
	private String business;
	private String location;
	private String pic;
	private Object distance;
	private Boolean isAD;
	private List<String> suggestion;    //用于自动补全

	public HotelDoc(Hotel hotel) {
		this.id = hotel.getId();
		this.name = hotel.getName();
		this.address = hotel.getAddress();
		this.price = hotel.getPrice();
		this.score = hotel.getScore();
		this.brand = hotel.getBrand();
		this.city = hotel.getCity();
		this.starName = hotel.getStarName();
		this.business = hotel.getBusiness();
		this.location = hotel.getLatitude() + ", " + hotel.getLongitude();
		this.pic = hotel.getPic();
		// 组装suggestion  //将brand、business等信息放到里面。如果出现brand、business相关信息就可以自动补全
		if (this.business.contains("/")) {
			// business有多个值，需要切割
			String[] arr = this.business.split("/");
			// 添加元素
			// 使用ArrayList类创建List对象
			this.suggestion = new LinkedList<>();
			this.suggestion.add(this.brand);
			Collections.addAll(this.suggestion, arr);
		} else {
			// 创建不可变List对象
			this.suggestion = Arrays.asList(this.brand, this.business);
		}
	}
}