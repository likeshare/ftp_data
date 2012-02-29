package com.ftp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
public class testProperties {

	
	public static void main(String[] args) throws Exception{
		Properties pro = new Properties();
		InputStream fis = testProperties.class.getResourceAsStream("/ftp.properties"); 
	//		FileInputStream fis = 
	//		      new FileInputStream("src/ftp.properties");
			pro.load(fis);
			
			System.out.println(" Ù–‘ «:"+pro.getProperty("ROOTPATH")
					);
	}

}
