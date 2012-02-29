package com.ftp;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class TTask extends TimerTask {
	public void run() {
		System.out.println("开始读取FTP server数据\n");
        
		FTPService listFtpfiles = new FTPService();
		listFtpfiles.FTPLogin();
		System.out.println("\n 读取数据结束");
		System.out.println("\n" + new Date());

	}
}