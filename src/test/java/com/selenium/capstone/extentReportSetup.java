package com.selenium.capstone;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class extentReportSetup {
	
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@BeforeSuite
	public void beforeSuite() {
		extent = ExtentReport.report();
	}
	
	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}

}
