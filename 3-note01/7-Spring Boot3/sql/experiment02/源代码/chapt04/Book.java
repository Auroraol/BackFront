package com.itheima.pojo;

import java.io.Serializable;

/**
 * ͼ��־û���
 */
public class Book implements Serializable {
    private Integer id;                    //����
    private String bookName;              //ͼ������
    private double price;                 //�۸�
    private String author;                //����

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id + ", bookName='" + bookName +
                ", price=" + price + ", author='" + author + '}';
    }
}