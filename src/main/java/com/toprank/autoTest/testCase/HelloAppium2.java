package com.toprank.autoTest.testCase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.rmi.CORBA.Util;

import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toprank.autoTest.common.Utils;

import io.appium.java_client.android.AndroidDriver;

public class HelloAppium2 {
	AndroidDriver driver;
	@BeforeClass
	//初始化
	public void setUp(){
		driver=Init.driver;
		System.out.println(driver);
	}
	
	@Test
	public void runCase(){
		String path="E:\\appium\\ykLogin.xlsx";
		File file=Utils.getFile(path);
		String sheet="baidu";
		List<String[]> list = null;
		try {
			list = Utils.getDataFormxlsx(file,sheet);
			Utils.printData(list);
			Utils.SetElement(driver, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@AfterClass
	public void tearDown(){
		System.out.println("-----------end---------------");
	}
	
}
