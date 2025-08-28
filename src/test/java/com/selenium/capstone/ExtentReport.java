package com.selenium.capstone;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {
  
	public static ExtentReports report() {

		ExtentSparkReporter reporter = new ExtentSparkReporter("ExtentReport.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
	    extent.setSystemInfo("Project", "Telecom Domian Automation");
	    extent.setSystemInfo("Tester", "Devendra");
		
	    return extent;
	}
	
}
