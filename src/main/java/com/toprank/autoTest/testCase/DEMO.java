package com.toprank.autoTest.testCase;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.toprank.autoTest.common.Utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

public class DEMO {
	public static void main(String[] args) throws IOException {
		AndroidDriver driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();          
		capabilities.setCapability("deviceName","MX4pro");  
	    capabilities.setCapability("platformName","Android");     
		capabilities.setCapability("platformVersion", "5.1.1");          
		capabilities.setCapability("udid","760BCL8228SR");                
		capabilities.setCapability("appPackage", "com.alexkaer.ekuhotel");  
	    capabilities.setCapability("appActivity", "com.alexkaer.yikuhouse.StartActivity"); 
	    capabilities.setCapability("sessionOverride", true);     
	    capabilities.setCapability("unicodeKeyboard", true);    
	    capabilities.setCapability("resetKeyboard", false);  
//	    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,AutomationName.APPIUM);
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
//		WebElement m = driver.findElement(By.id("com.alexkaer.ekuhotel:id/tv_location"));
//		System.out.println(m.getText());
		
//		
//		String toast="定位中....";
//		//toast 寻找测试
//		try {
//		final WebDriverWait wait = new WebDriverWait(driver,2);
//		Assert.assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"+ toast + "')]"))));
//		System.out.println("找到了toast");
//		} catch (Exception e) {
//		throw new AssertionError("找不到toast:"+toast);
//		}
		
		WebElement me = driver.findElement(By.id("com.alexkaer.ekuhotel:id/main_recommond_tv"));
		me.getLocation();
		
		String p1="d:/1";
		String p2="d:/2";
		File f2=new File(p2);
		File f1=driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f1,new File(p1));
		BufferedImage img1=Utils.getImageFromFile(f1);
		f2=driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f2,new File(p2));
		BufferedImage img2=Utils.getImageFromFile(f2);
		Boolean same=Utils.sameAs(img1,img2,0.9);
		System.out.println(same);
		BufferedImage subImg1=Utils.getSubImage(img1,6,39,474,38);
		BufferedImage subImg2=Utils.getSubImage(img1,6,39,474,38);
		same=Utils.sameAs(subImg1,subImg2,1);
		File f3=new File("d:/sub-1.png");
		ImageIO.write(subImg1,"PNG",f3);
		File f4=new File("d:/sub-2.png");
		ImageIO.write(subImg1,"PNG",f4);
		
		System.out.println(same);
		

	}

}
