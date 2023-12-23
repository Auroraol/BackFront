package com.bank.model;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 19:00
 */
public class Bankcard {

	private int accountid;   //账户id
	private double  cardid;   //id
	private double cardnumber;    //银行卡卡号
	private double balance;   //金额
	private int status;    //状态
	private String cardpassword;   //密码

	public Bankcard(int accountid, double cardid, double cardnumber, double balance, int status, String cardpassword) {
		this.accountid = accountid;
		this.cardid = cardid;
		this.cardnumber = cardnumber;
		this.balance = balance;
		this.status = status;
		this.cardpassword = cardpassword;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public double getCardid() {
		return cardid;
	}

	public void setCardid(double cardid) {
		this.cardid = cardid;
	}

	public double getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(double cardnumber) {
		this.cardnumber = cardnumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCardpassword() {
		return cardpassword;
	}

	public void setCardpassword(String cardpassword) {
		this.cardpassword = cardpassword;
	}


	@Override
	public String toString() {
		return "Bankcard{" +
				"accountid=" + accountid +
				", cardid=" + cardid +
				", cardnumber=" + cardnumber +
				", balance=" + balance +
				", status=" + status +
				", cardpassword='" + cardpassword + '\'' +
				'}';
	}
}
