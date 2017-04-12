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

public class HelloAppium {
	AndroidDriver driver;
	@BeforeClass
	//初始化
	public void setUp(){
		System.out.println("-----------begin------------");
		DesiredCapabilities capabilities = new DesiredCapabilities();          
		capabilities.setCapability("deviceName","MX4pro");  
        capabilities.setCapability("platformName","Android");     
		capabilities.setCapability("platformVersion", "5.1.1");          
		capabilities.setCapability("udid","760BCL8228SR");                
		capabilities.setCapability("appPackage", "com.alexkaer.ekuhotel");  
	    capabilities.setCapability("appActivity", "com.alexkaer.yikuhouse.StartActivity"); 
        capabilities.setCapability("sessionOverride", true);     
        capabilities.setCapability("unicodeKeyboard", true);    
        capabilities.setCapability("resetKeyboard", true);  
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
	}
	
	@Test
	public void runCase(){
		String path="E:\\appium\\ykLogin.xlsx";
		File file=Utils.getFile(path);
		String sheet="baidu";
		List<String[]> list = null;
		try {
			list = Utils.getDataFormxlsx(file,sheet);
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
