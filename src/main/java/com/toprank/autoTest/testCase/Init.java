package com.toprank.autoTest.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import com.toprank.autoTest.common.Mobile;
import com.toprank.autoTest.common.Utils;

import io.appium.java_client.android.AndroidDriver;

public class Init {
	public static AndroidDriver  driver=null;
	@BeforeTest
	void setUp(){
		if(Mobile.isConnect(Utils.getPropertyValue("udid"))){
			System.out.println("-----------begin------------");
			DesiredCapabilities capabilities = new DesiredCapabilities();          
			capabilities.setCapability("deviceName",Utils.getPropertyValue("deviceName"));  
	        capabilities.setCapability("platformName",Utils.getPropertyValue("platformName"));     
			capabilities.setCapability("platformVersion",Utils.getPropertyValue("platformVersion"));          
			capabilities.setCapability("udid",Utils.getPropertyValue("udid"));                
			capabilities.setCapability("appPackage", Utils.getPropertyValue("appPackage"));  
		    capabilities.setCapability("appActivity", Utils.getPropertyValue("appActivity"));     
	        capabilities.setCapability("unicodeKeyboard", Utils.getPropertyValue("unicodeKeyboard"));    
	        capabilities.setCapability("resetKeyboard", Utils.getPropertyValue("resetKeyboard"));  
			try {
				driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
			System.out.println("初始化完毕！！");
		}
	}
}
