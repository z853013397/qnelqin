package com.qunl.qunlerih.apis;

public class API {
	public static final String pathBase = "http://hunter.001do.com:8088/api";// 桌子的信息
//	public static final String pathBase = "http://192.168.1.233:8088/api";// 桌子的信息
	public static final String path = pathBase + "/table/0/0";// 桌子的信息

	public static final String checkLogin = pathBase + "/account/";// 这个是登录的请求
	
	public static final String createTable = pathBase + "/table/";// 桌子的信息
	public static final String attendTable = pathBase + "/table/";// 加入桌子的信息
	
	
	//http://hunter.001do.com:8088/api
	public static final String sendMessage = "http://www.qunl.com/API/sendSMS.php";
	
}
