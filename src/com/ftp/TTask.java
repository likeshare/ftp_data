package com.ftp;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class TTask extends TimerTask {
	public void run() {
		System.out.println("��ʼ��ȡFTP server����\n");
        
		FTPService listFtpfiles = new FTPService();
		listFtpfiles.FTPLogin();
		System.out.println("\n ��ȡ���ݽ���");
		System.out.println("\n" + new Date());

	}
}