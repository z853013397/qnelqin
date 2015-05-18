package com.qunl.qunlerih.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Zhaosha
 *搜索牌桌的返回信息
 */
public class MyFriendInfoBean {
	public String result;
	public List<TableInfo> record;
	public static class TableInfo implements Serializable {
		public String tid;
		public String tname;
		public String anonymous;
		public String uid;
		public String ownerid;
		public String date;
	}
}
