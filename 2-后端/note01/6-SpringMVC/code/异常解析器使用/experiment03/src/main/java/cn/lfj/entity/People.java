package cn.lfj.entity;

/**
 * @Author: LFJ
 * @Date: 2023-10-17 22:18
 */

public class People {
	private Integer count;
	private String peoplename;
	private Student student;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPeoplename() {
		return peoplename;
	}

	public void setPeoplename(String peoplename) {
		this.peoplename = peoplename;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}

