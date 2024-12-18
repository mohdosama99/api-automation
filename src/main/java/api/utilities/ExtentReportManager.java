package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		System.out.println("onStart called");
		String timeStamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_a").format(new Date());

		repName = "Test-report_" + timeStamp + ".html";
		String currentDir = System.getProperty("user.dir");
		//sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
		sparkReporter = new ExtentSparkReporter(currentDir+"/reports/"+repName);
		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
		sparkReporter.config().setReportName("Pet Store API");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Pest Store API");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("userName", System.getProperty("user.name"));
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test Passed");
		System.out.println("onTestSuccess called for: " + result.getName());

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		if (result.getThrowable() != null) {
		    test.log(Status.SKIP, result.getThrowable().getMessage());
		} else {
		    test.log(Status.SKIP, "Test Skipped without exception.");
		}
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
		System.out.println("onFinish called");
	}

}
