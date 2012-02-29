package com.ftp;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;
import com.ftp.config.ReadProperties;
import java.util.Timer;
import org.apache.log4j.*;
import java.util.Date;



public class MyTask {
	static Logger logger =Logger.getLogger(MyTask.class);
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//定时启动
		
		Properties prop = (new ReadProperties()).getProperties();
		long f=Long.valueOf(prop.getProperty("firsttime"));
		Long p= Long.valueOf(prop.getProperty("period"));
		Timer t = new Timer();
		@SuppressWarnings("deprecation")
		Date date =new Date(2011,11,15,50,00,00);
		TTask mytask=new TTask();
		
		t.schedule(mytask,f,p);
		System.out.println("************程序"+f+"后启动,每"+p+"重复执行一次***************");
		
	}

}
