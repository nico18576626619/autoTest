package com.toprank.autoTest.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.testng.Assert;

public class Mobile{
	public static void main(String[] args) {
		isConnect("760BCL8228SR");
	}
	
	/**
	 * 返回手机连接情况
	 * @param uuid  设备唯一标识
	 * @return
	 */
	public static boolean isConnect(String uuid){
		String cmd="adb devices";
		try {
			Process p = Runtime.getRuntime().exec("adb devices");
			InputStream r = p.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(r,"gbk"));
			String line=null;
			
			while((line=br.readLine())!=null){
				if(line.contains(uuid)){
					System.err.println("手机连接成功！！");
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		{
		System.err.println("手机未连接成功,请检查手机连接情况！！");
		Assert.fail("手机未连接成功,请检查手机连接情况！！");
		}
		return false;		
	}
}
