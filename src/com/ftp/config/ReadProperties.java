package com.ftp.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ReadProperties {
       public static Properties getProperties(){
    	   return getProperties("/ftp.properties");
    	   
       }

	public  static Properties getProperties(String path) {
		// TODO Auto-generated method stub
		InputStream in = ReadProperties.class.getResourceAsStream(path);
		Properties prop = new Properties();
		if(in!=null){
			try {
				prop.load(in);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("加载指定类下文件出错"+e.getMessage());
			}
			
		}
		return prop;
	}
       
}
