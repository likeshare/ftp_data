package com.ftp;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Date;

public class IsertDB {
	private static final String user = "root";

    private static final String pwd = "123456";

    private static final String url = "org.gjt.mm.mysql.Driver" ;

    private static final String driver = "jdbc:mysql://192.168.1.204:3306/record" ;

	public static Connection getCon() {

        Connection con = null;

        try {

            Class.forName(driver).newInstance();

            con = DriverManager.getConnection(url, user, pwd);

            if (con != null) {

                System.out.println("�������ӵ����ݿ⣺" + con.getCatalog());

            }

        } catch (Exception e) {

            System.out.println("�������ݿ�ʧ�ܣ�");

            e.printStackTrace();

        }

        return con;

    } 
	public boolean insertDb(String useridSting,String productid,String actionSting,String startdate,String enddate){
		Connection con = null;

        Statement stm = null; 
		boolean flag = false;
		String sql = "insert into ftp_smssubscribeuser(userid,ProductId,action,startdate,enddate) values("+useridSting+","+productid+","+actionSting+","+startdate+","+enddate+")";
	   
	    try {
	    	con = getCon();
	    	stm =con.createStatement();
	    	int i=stm.executeUpdate(sql);
	    	if(i>0){
	    		flag=true;
	    		System.out.println(flag+"�������ݳɹ�");
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(stm, con);
		}
	    
     
		return flag;
		
      
	}
	//�ر��������

    public void close( Statement stm, Connection con) {

        
        if (stm != null)

            try {

                stm.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

        if (con != null)

            try {

                con.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

    }

} 


