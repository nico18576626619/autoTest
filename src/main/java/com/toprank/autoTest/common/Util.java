package com.toprank.autoTest.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;

public class Util {
	public static void main(String[] args) throws Exception {
//		String path = "E:\\appium\\ykLogin1.xlsx";
//		File file = getFile(path);
//		String sheet = "baidu";
//		List<String[]> list = getDataFormxlsx(file, sheet);
//		printData(list);
		System.out.println(getScreenshotPath()+"\\"+createPath());
		// System.out.println(getPropertyValue("appPackage"));

	}

	public static void SetElement(AndroidDriver driver, List<String[]> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			String[] dl = dataList.get(i);
			String caseName = dl[0];
			String byFlag = dl[1];
			String byFlagVal = dl[2];
			String actionFlag = dl[3];
			String testData = dl[4];
			WebElement element = null;
			switch (byFlag) {
			case "id":
				element = driver.findElement(By.id(byFlagVal));
				break;
			case "name":
				element = driver.findElement(By.name(byFlagVal));
				break;
			case "xpath":
				element = driver.findElement(By.xpath(byFlagVal));
				break;
			case "linkText":
				element = driver.findElement(By.linkText(byFlagVal));
				break;
			case "className":
				element = driver.findElement(By.className(byFlagVal));
				break;
			case "swipe":
				swipe(driver, actionFlag, testData);
				break;
			case "checkTextByid":
				element = driver.findElement(By.id(byFlagVal));
				String text = element.getText();
				Assert.assertEquals(text.matches(".*" + testData + ".*"), true, "找到不到对应Text:" + testData);
				break;
			case "checkToastBytext":
				try {
					final WebDriverWait wait = new WebDriverWait(driver, 2);
					Assert.assertNotNull(wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + testData + "')]"))));
					System.out.println("找到了toast");
				} catch (Exception e) {
					Assert.fail("找不到toast文本:" + testData);
				}
				break;
			case "checkImage":
				if(testData==null){
					Assert.fail("没有预期对比图片");
				}else{
					if(byFlagVal==null){
						checkImage(driver, testData);
					}else{
						checkImage(driver, testData,byFlagVal);
					}
				}
				break;
			}

			if (element != null) {
				switch (actionFlag) {
				case "click":
					element.click();
					break;
				case "input":
					element.clear();
					element.sendKeys(testData);
				case "None":
					break;
				}
			}

		}
	}

	private static void checkImage(AndroidDriver driver, String testData, String byFlagVal) {
		// TODO Auto-generated method stub
		String[] zb=byFlagVal.split(",");
		System.out.println(zb[0]+zb[1]+zb[2]+zb[3]);
		File f1=new File(Util.getExpectImagePath()+"\\"+testData);
		BufferedImage temp1=Utils.getImageFromFile(f1);
		BufferedImage img1=getSubImg(temp1,Integer.parseInt(zb[0]),Integer.parseInt(zb[1]),Integer.parseInt(zb[2]),Integer.parseInt(zb[3]));
		
		File tmp=driver.getScreenshotAs(OutputType.FILE);
		BufferedImage temp2=Utils.getImageFromFile(tmp);
		BufferedImage img2=getSubImg(temp2,Integer.parseInt(zb[0]),Integer.parseInt(zb[1]),Integer.parseInt(zb[2]),Integer.parseInt(zb[3]));
		
		Boolean same=Utils.sameAs(img1,img2,0.9);
		
		File f2=new File(getScreenshotPath()+"\\"+testData);
		
		try {
			ImageIO.write(img2,"PNG",f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Assert.assertTrue(same,"图片和预期不符....");
		
	}

	public static void checkImage(AndroidDriver driver, String testData) {
		File f1=new File(Util.getExpectImagePath()+"\\"+testData);
		BufferedImage img1=Utils.getImageFromFile(f1);
		
		File tmp=driver.getScreenshotAs(OutputType.FILE);
		BufferedImage img2=Utils.getImageFromFile(tmp);
		
		Boolean same=Utils.sameAs(img1,img2,0.9);
		
		File f2=new File(getScreenshotPath()+"\\"+testData);
		
		try {
			FileUtils.copyFile(tmp,f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Assert.assertTrue(same,"图片和预期不符....");
	}

	/**
	 * 生成图片名
	 * @return
	 */
	private static String createPath() {
		Date cTime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MMdd-HHmmss");
		String time=sdf.format(cTime);
		return "image"+time+".png";
	}
	
	
	/**
	 *  获取工程目录
	 * @return
	 */
	private static  String getProjectPath(){
		File f=new File("");
		String path=f.getAbsolutePath();
		return path;
	}
	
	private static String getScreenshotPath(){
		String path=getProjectPath()+"\\Image\\Screenshot";
		return path;
	}
	
	private static String getExpectImagePath(){
		String path=getProjectPath()+"\\Image\\ExpectImage";
		return path;
	}
	
	

	/**
	 * 滑动
	 * 
	 * @param driver
	 *            driver
	 * @param actionFlag
	 *            滑动方向
	 * @param times
	 *            滑动次数
	 */
	public static void swipe(AndroidDriver driver, String actionFlag, String times) {
		int cs = Integer.parseInt(times);
		swipe(driver, actionFlag, cs);
	}

	/**
	 * ۬֯滑动
	 * 
	 * @param driver
	 *            driver
	 * @param actionFlag
	 *         	  滑动方向UP/DOWN/LEFT/RIGHT
	 * @param times
	 *            滑动次数
	 */
	public static void swipe(AndroidDriver driver, String actionFlag, int times) {
		// 滑动时间
		int during = 1000;
		// 间隔滑动时间
		int sleepTime = 1000;
		switch (actionFlag) {
		case "UP":
			while (times >= 0) {
				swipeToUp(driver, 1000);
				sleep(sleepTime);
				times--;
			}
			break;

		case "DOWN":
			while (times >= 0) {
				swipeToDown(driver, 1000);
				sleep(sleepTime);
				times--;
			}
			break;

		case "LEFT":
			while (times == 0) {
				swipeToLeft(driver, 1000);
				sleep(sleepTime);
				times--;
			}
			break;

		case "RIGHT":
			while (times == 0) {
				swipeToRight(driver, 1000);
				sleep(sleepTime);
				times--;
			}
			break;
		}
	}

	// 测试方法，打印数据
	public static void printData(List<String[]> list) {
		Iterator<String[]> it = list.iterator();
		while (it.hasNext()) {
			String[] tmp = it.next();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i] == "") {
					System.out.println("--");
				} else {
					System.out.print(tmp[i] + "\t");
				}
			}
			System.out.println();
		}
	}

	/**
	 * @param path
	 *            文件路径
	 * @return 文件对象
	 */
	public static File getFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.err.println(path + "文件不存在！！");
			return null;
		} else {
			return file;
		}
	}

	/**
	 * @param file
	 *            文件对象
	 * @return 字节流
	 */
	public static InputStream getInputStream(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * @param inputstram
	 * @return
	 */
	public static Properties getPropertyFrom(InputStream inputstram) {
		Properties p = new Properties();
		try {
			p.load(inputstram);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		String val = null;
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\config.properties";
		Properties prop = getPropertyFrom(getInputStream(getFile(path)));
		val = prop.getProperty(key);
		return val;
	}

	/**
	 * @param testdata
	 *            测试用例文件对象
	 * @param sheetname
	 *            用例sheet
	 * @return List<String[]>
	 * @throws Exception
	 */
	public static List<String[]> getDataFormxlsx(File testdata, String sheetname) throws Exception {
		return getDataFormxlsx(testdata, sheetname, "start", "end");
	}

	/**
	 * @param testdata
	 *            测试用例文件对象
	 * @param sheetname
	 *            用例sheet
	 * @param startFlag
	 *            用例文件读取起始标记
	 * @param endFlag
	 *            用例文件读取起始标记
	 * @return List<String[]>
	 * @throws Exception
	 */
	public static List<String[]> getDataFormxlsx(File testdata, String sheetname, String startFlag, String endFlag)
			throws Exception {
		List<String[]> dataList = new ArrayList<String[]>();
		Workbook wb = new XSSFWorkbook(new FileInputStream(testdata));
		XSSFSheet sheet = (XSSFSheet) wb.getSheet(sheetname);
		int rowNum = sheet.getPhysicalNumberOfRows();
		int allColNum = sheet.getRow(0).getPhysicalNumberOfCells();
		int colStart = 0;
		int colEnd = 0;
		for (int i = 0; i < allColNum; i++) {
			XSSFCell tmp = sheet.getRow(0).getCell(i);
			String colTitle = tmp.getStringCellValue();
			if (colTitle.equals(startFlag))
				colStart = tmp.getColumnIndex() + 1;
			if (colTitle.equals(endFlag))
				colEnd = tmp.getColumnIndex() - 1;
		}
		for (int i = 1; i < rowNum; i++) {
			int colNum = colEnd - colStart + 1;
			String[] ccs = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				XSSFCell cc = sheet.getRow(i).getCell(j + colStart);
				if (cc != null) {
					String cv = cc.getStringCellValue();
					ccs[j] = cv;
				} else {
					ccs[j] = "";
				}
			}
			dataList.add(ccs);
		}
		return dataList;
	}

	/**
	 * This Method for swipe up
	 * 
	 * @param driver
	 * @param during
	 */
	public static void swipeToUp(AndroidDriver driver, int during) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, during);
		// wait for page loading
	}

	/**
	 * This Method for swipe down
	 * 
	 * @param driver
	 * @param during
	 */
	public static void swipeToDown(AndroidDriver driver, int during) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, during);
		// wait for page loading
	}

	/**
	 * This Method for swipe Left
	 * 
	 * @param driver
	 * @param during
	 */
	public static void swipeToLeft(AndroidDriver driver, int during) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, during);
		// wait for page loading
	}

	/**
	 * This Method for swipe Right
	 * 
	 * @param driver
	 * @param during
	 */
	public static void swipeToRight(AndroidDriver driver, int during) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, during);
		// wait for page loading
	}

	/**
	 * 线程等待
	 * 
	 * @param times
	 */
	public static void sleep(int times) {
		try {
			Thread.sleep(times);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *比较两张图片
	 * @param myImage 
	 * @param otherImage 
	 * @param percent 相似度0-1.0
	 * @return
	 */
	public static boolean sameAs(BufferedImage myImage, BufferedImage otherImage, double percent) {
		// BufferedImage otherImage = other.getBufferedImage();
		// BufferedImage myImage = getBufferedImage();
		if (otherImage.getWidth() != myImage.getWidth()) {
			return false;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			return false;
		}
		int[] otherPixel = new int[1];
		int[] myPixel = new int[1];
		int width = myImage.getWidth();
		int height = myImage.getHeight();
		int numDiffPixels = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}
		double numberPixels = height * width;
		double diffPercent = numDiffPixels / numberPixels;
		return percent <= 1.0D - diffPercent;
	}

	/**
	 *  截取图片部分
	 * @param image
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static BufferedImage getSubImg(BufferedImage image, int x1, int y1, int x2, int y2) {
		int x=x1;
		int y=y1;
		int w=x2-x1;
		int h=y2-y1;
		return image.getSubimage(x, y, w, h);
	}
	
	public static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	/**
	 * 获取BufferedImage
	 * @param f
	 * @return
	 */
	public static BufferedImage getImageFromFile(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// if failed, then copy it to local path for later check:TBD
			// FileUtils.copyFile(f, new File(p1));
			e.printStackTrace();
			System.exit(1);
		}
		return img;
	}

}
