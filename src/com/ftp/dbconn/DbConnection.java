package com.ftp.dbconn;


import java.sql.Connection ;
import java.sql.DriverManager ;
public class DbConnection {
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver" ; 
	private static final String DBURL = "jdbc:mysql://localhost:3306/mldn" ;
	private static final String DBUSER = "root" ;
	private static final String DBPASSWORD = "123456";
	public  Connection conn ;
	public DbConnection() throws Exception {
		Class.forName(DBDRIVER) ;
		this.conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD) ;
		if (conn != null) {

            System.out.println("你已连接到数据库：" + conn.getCatalog()); 
	}
	}
	public Connection getConnection(){
		return this.conn ;
	}
	public void close() throws Exception {
		if(this.conn != null){
			try{
				this.conn.close() ;
			}catch(Exception e){
				throw e ;
			}
		}
	}
}