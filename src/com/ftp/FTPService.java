package com.ftp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.commons.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.jmx.LoggerDynamicMBean;

import com.ftp.config.ReadProperties;
import java.util.Properties;

public class FTPService {
	/**
	 * ȡ��ĳ��Ŀ¼�µ������ļ��б�
	 * 
	 */
	/**
	 * @param path
	 * @return function:��ȡָ��Ŀ¼�µ��ļ���
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// ���ָ��Ŀ¼�������ļ���
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * @param fileName
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 *             function:�����ļ�
	 */
	public boolean unloadFile(String fileName, String sourceFile) {
		boolean flag = false;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			flag = ftpClient.retrieveFile(sourceFile, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ����һ���ļ���
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		String result = "";
		InputStream ins = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);

			// byte []b = new byte[ins.available()];
			// ins.read(b);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			String inLine = "";
			while ((inLine = reader.readLine()) != null) {
				result += (inLine + System.getProperty("line.separator"));
				inLine = reader.readLine();
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}

			// ��������һ��getReply()�ѽ�������226���ѵ�. �������ǿ��Խ���������null����
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param fileName
	 * @return function:�ӷ������϶�ȡָ�����ļ�
	 * @throws ParseException
	 * @throws IOException
	 */
	/**
	 * public List readFile() throws ParseException{
	 * 
	 * 
	 * InputStream ins = null; try { //�ӷ������϶�ȡָ�����ļ� ins =
	 * ftpClient.retrieveFileStream
	 * ("SubscribeInfo000521012011072703140900045.r");
	 * 
	 * BufferedReader reader=new BufferedReader(new
	 * InputStreamReader(ins,"UTF-8"));
	 * 
	 * String inLine = "";reader.readLine();
	 * 
	 * while (inLIne=(reader.readLine())!= null) {
	 * 
	 * String beanStr = inLine+System.getProperty("line.separator");
	 * 
	 * 
	 * System.out.print(inLine);
	 * 
	 * } reader.close(); if(ins != null){ ins.close(); } //
	 * ��������һ��getReply()�ѽ�������226���ѵ�. �������ǿ��Խ���������null���� ftpClient.getReply();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	private FTPClient ftpClient = new FTPClient();
	private OutputStream output = null;
	// private String hostName = "122.141.250.65";
	// private String userName = "ftpuser";
	// private String password = "ftpuser";
	// private String remoteDir = "/VAC/SubscribeInfo/52969/";
	int lineNumber = 0;
	IsertDB insertdb = new IsertDB();

	public void FTPLogin() {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger(FTPService.class);
		Properties pr = ReadProperties.getProperties();
		String remoteDir = pr.getProperty("ROOTPATH");
		String ftp_hostName = pr.getProperty("ftp_hostname");
		String ftp_username = pr.getProperty("ftp_username");
		String ftp_password = pr.getProperty("ftp_password");
		// DB�˺�����
		String db_username = pr.getProperty("DB_USE");
		String db_password = pr.getProperty("DB_PASSWORD");
		String db_driver = pr.getProperty("DB_DRIVER");
		String db_url = pr.getProperty("DB_URL");
		System.out.println("ftp�ĸ�Ŀ¼��:" + remoteDir);
		logger.info("ftp�ĸ�Ŀ¼" + remoteDir);
		try {
			ftpClient.connect(ftp_hostName);

			System.out.println("���ӵ�" + ftp_hostName + "FTPClient��ʼ��½.....");
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.login(ftp_username, ftp_password);
			int reply = ftpClient.getReplyCode();
			System.out.println(reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				throw new IOException("failed to connect to the FTP Server:"
						+ ftp_hostName);
			}
			System.out.println("��½�ɹ�");
			FTPFile[] remoteFiles = ftpClient.listFiles(remoteDir);

			if (remoteFiles != null || remoteFiles.length != 0) {
				for (int i = 0; i < remoteFiles.length; i++) {
					String name = remoteFiles[i].getName();
					long lenth = remoteFiles[i].getSize();
					String readableLength = FileUtils
							.byteCountToDisplaySize(lenth);
					// System.out.println(name + ":\t\t" + readableLength);
					logger.info(name + ":\t\t" + readableLength);
					InputStream ins = ftpClient.retrieveFileStream(remoteDir
							+ name);
					BufferedReader rd = new BufferedReader(
							new InputStreamReader(ins, "UTF-8"));
					String inline = "";

					while ((inline = rd.readLine()) != null) {
						lineNumber = lineNumber + 1;

						int columnNumber = inline.length();
						System.out.println("This is line " + lineNumber + " , "
								+ columnNumber + " columns.");

						String inlinString[] = inline.split("\t");
						if (lineNumber == 1) {
							System.out
									.println("**************�ļ�ͷ***********************");
							System.out.println(inline);
							logger.info("��ȡͷ�ļ�����" + inline);
							System.out
									.println("**************�ļ�ͷ***********************");
							System.out
									.println("**************�ļ��忪ʼ***********************");
						}

						if (lineNumber > 1) {

							System.out
									.print(inlinString[2].substring(2) + "\t");
							System.out.print(inlinString[5] + "\t");
							System.out.print(inlinString[6] + "\t");
							System.out.print(inlinString[11] + "\t");
							System.out.print(inlinString[12] + "\t\n");

							// insertdb.insertDb(inlinString[2],
							// inlinString[5],inlinString[6], inlinString[11],
							// inlinString[12]);
							logger.info(inlinString[2].substring(2) + "\t");
							logger.info(inlinString[5] + "\t");
							logger.info(inlinString[6] + "\t");
							logger.info(inlinString[11] + "\t");
							logger.info(inlinString[12] + "\t\n");
							String sql = "insert into ftp_smssubscribeuser_52969(userid,ProductId,action,startdate,enddate) values("
									+ inlinString[2].substring(2)
									+ ","
									+ inlinString[5]
									+ ","
									+ inlinString[6]
									+ ","
									+ inlinString[11]
									+ ","
									+ inlinString[12] + ")";
							try {
								Class.forName(db_driver).newInstance();

								Connection conDB = DriverManager.getConnection(
										db_url, db_username, db_password);
								Statement stDB = conDB.createStatement(1005,
										1008);
								stDB.executeUpdate(sql);
								System.out
										.println("\n****************���ɹ�******************");
								try {
									stDB.close();
									conDB.close();
								} catch (Exception e5) {
									System.out.println("error at e5:  " + e5);
								}
							} catch (Exception e1) {
								System.out.println("con mysql error:  " + e1);

								e1.printStackTrace();
							}

						}

					}

					/**
					 * InputStream inStream =new
					 * FTPClient().retrieveFileStream(name); BufferedReader
					 * reader = new BufferedReader(new
					 * InputStreamReader(inStream, "UTF-8")); String inLine =
					 * reader.readLine(); System.out.println(inLine); //
					 */
				}

			}

		} catch (SocketException e1) {
			// // TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} finally {
			// ʹ��IO���ر���
			IOUtils.closeQuietly(output);
			try {
				ftpClient.disconnect();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}

	}

	/**
	 * public static void main(String[] args) { FTPService listFtpfiles = new
	 * FTPService(); listFtpfiles.FTPLogin();
	 * 
	 * }
	 */
}
