package com.qunl.qunlerih.dao;

public class TableListItem {
	
	  public String uid; //id of qunle
      public String tname; // name of table
      public String ownerid; //table owner uid
      public String tid; //id of table
      public String anonymous; //anonymous attend 0:false, 1:true
      
      public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getTname() {
			return tname;
		}

		public void setTname(String tname) {
			this.tname = tname;
		}

		public String getOwnerid() {
			return ownerid;
		}

		public void setOwnerid(String ownerid) {
			this.ownerid = ownerid;
		}

		public String getTid() {
			return tid;
		}

		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getAnonymous() {
			return anonymous;
		}

		public void setAnonymous(String anonymous) {
			this.anonymous = anonymous;
		}
		

}
