package com.jcifs.demo1;

import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.ftp.FtpDemo01;

public class Cifs {

	public static void main(String[] args) {
		String host ="122.141.250.65";
		String user= "ftpuser";
		String password ="ftpuser";
		String remoteFile = "/VAC/SubscribeInfoall/52969/*.req";
		String localPath ="c:\\52969";
		FTPClient client = new FTPClient();
		try {
			client.setRemoteHost(host);
			client.connect();
			client.login(user,password);
			client.setType(FTPTransferType.BINARY);
			client.get(localPath, remoteFile);
			System.out.println("****************œ¬‘ÿÕÍ±œ***************");
			client.quit();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
