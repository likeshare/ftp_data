package com.ftp;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.FingerClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;

public class TestFtp {
	 FTPClient ftpClient = new FTPClient();
	 OutputStream output = null;
	 String hostName = "122.141.250.65";
	 String userName = "ftpuser";
	 String password = "ftpuser";
	 String remoteDir = "/VAC/SubscribeInfo/52101";
	
	/** 
     * 取得某个目录下的所有文件列表 
     *  
     */  
    public List<String> getFileList(String path) throws IOException {  
        // listFiles return contains directory and file, it's FTPFile instance  
        // listNames() contains directory, so using following to filer directory.  
        //String[] fileNameArr = ftpClient.listNames(path);  
        
    	FTPFile[] ftpFiles= ftpClient.listFiles(path);  
          
        List<String> retList = new ArrayList<String>();  
        if (ftpFiles == null || ftpFiles.length == 0) {  
            return retList;  
        }  
        for (FTPFile ftpFile : ftpFiles) {  
            if (ftpFile.isFile()) {  
                retList.add(ftpFile.getName());  
            }  
        }  
        return retList;  
    }
		
	public static void main(String[] args) {
		 
	     
		 
		
	}

}
