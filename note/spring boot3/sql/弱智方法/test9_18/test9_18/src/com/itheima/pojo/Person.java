package com.itheima.pojo;

public class Person {
		// TODO Auto-generated method stub
		private Integer id;          //����id
	    private String name;         //����
	    private Integer age;         //����
	    private String sex;          //�Ա�
	    private IdCard card;        //��Ա������֤��

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
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public IdCard getCard() {
			return card;
		}
		public void setCard(IdCard card) {
			this.card = card;
		}
		@Override
		public String toString() {
			return "Person [id=" + id + ", name=" + name + ", "
					+ "age=" + age + ", sex=" + sex + ", card=" + card + "]";
		}

}
