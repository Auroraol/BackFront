package com.itheima.pojo;

public class Orders {
	private Integer id;    //����id
	private String number;//�������	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", number=" + number + "]";
	}

}
