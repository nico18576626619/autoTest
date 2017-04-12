package com.toprank.autoTest.testCase;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.toprank.autoTest.common.Utils;

public class test {
	public static void main(String[] args) throws IOException {
//		saveAs();
		File f=new File("");
		String p=f.getAbsolutePath();
		String p1=f.getCanonicalPath();
		System.out.println(p);
		System.out.println(p1);		
	}
	public static void saveAs() {
		String p1="d:/1";
		String p2="d:/2";
		File f1=new File(p1);
		File f2=new File(p2);
		
		BufferedImage img1=Utils.getImageFromFile(f1);
		
		BufferedImage img2=Utils.getImageFromFile(f2);
		Boolean same=Utils.sameAs(img1,img2,0.9);
		System.out.println(same);
	}
	
}
