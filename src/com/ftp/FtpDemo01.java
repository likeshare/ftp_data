package com.ftp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;




public class FtpDemo01 {
    public FtpClient ftpClient;
    /**
     * connectserver ����ftp������
     * @author seal
     * @throws java.io.IOException
     * @param path �ļ���,�մ���Ŀ¼
     * @param password ����
     * @param user ��½�û�
     * @param server ��������ַ
     * 
    */
    public void  connectServer(String server,String user,String password,String path) throws IOException{
    	ftpClient = new FtpClient();
    	ftpClient.openServer(server);
    	ftpClient.login(user, password);
    	if (path.length()!=0) {
    		
			ftpClient.binary();
			ftpClient.sendServer("PORT");
		}
		System.out.println("��½�ɹ�");
	}
    /** 
     * download ��ftp�����ļ������� 
     *  
     * @throws java.lang.Exception 
     * @return 
     * @param newfilename �������ɵ��ļ��� 
     * @param filename �������ϵ��ļ��� 
     */  
    public long down(String filename,String newfilename)throws Exception{
    	long result =0;
    	TelnetInputStream is=null;
    	FileOutputStream os=null;
        try {
			is =ftpClient.get(filename);
			File outFile = new File(newfilename);
			os = new FileOutputStream(outFile);
			byte[] bytes = new byte[1024];
			int c;
			while((c=is.read(bytes))!=-1){
				os.write(bytes, 0, c);
				result=result+c;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			if(is!=null){
				
					is.close();
				
				}
			if(os!=null){
				os.close();
				
			}
			}
		
    return result;
			

			
    }
    /**
     * ȡ������Ŀ¼�����е��ļ��б�
     *
     * 
     */
    public List getFileList(String path){
    	List list =new ArrayList();
    	try {
			DataInputStream dis = new DataInputStream(ftpClient.nameList(path));
			String filename= "";
			while((filename =dis.readLine())!=null){
				list.add(filename);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return list;
    }
    /** 
     * closeServer �Ͽ���ftp������������ 
     *  
     * @throws java.io.IOException 
     */  
    public void closeServer() throws IOException {  
        try {  
            if (ftpClient != null) {  
                ftpClient.closeServer();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	public static void main(String[] args) {
		FtpDemo01 ftp  =new FtpDemo01();
		try {
			ftp.connectServer("122.141.250.65","ftpuser","ftpuser", "/VAC/SubscribeInfo/52101");
			List list = ftp.getFileList("/");
			for (int i = 0; i < list.size(); i++) {  
             String filename = (String) list.get(i);  
              System.out.println(filename);  
              
              }  
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
