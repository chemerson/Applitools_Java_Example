package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class iOS {


    public static void main(String[] args) throws MalformedURLException {
        // Initialize the Runner for your test.
        EyesRunner runner = new ClassicRunner();

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        //eyes.setLogHandler(new StdoutLogHandler(true));

        // Change the APPLITOOLS_API_KEY API key with yours
        // eyes.setApiKey("");  //Put your API key here
        // or pull from the environment variable
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));


        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "13.3");
        caps.setCapability("deviceName", "CME iPhone X");
        caps.setCapability("udid","20c0ba037f8bc7fd37bfd285014b01958237ae0e");
       // caps.setCapability("browserName", "Safari");
        caps.setCapability("xcodeOrgId", "27VJ2SNPHP");
        caps.setCapability("xcodeSigningId", "iPhone Developer");
        caps.setCapability("bundleId", "com.gramgames.mergemagic");


        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        AppiumDriver driver = new AppiumDriver(url, caps);

        try {

            eyes.open(driver, "Demo App", "Demo iOS Test");

            //driver.get("https://applitools.com/helloworld");

            driver.launchApp();

            eyes.check("Home Page", Target.window());

            // End the test.
            eyes.close(false);

            // Close the browser.
            driver.quit();

            // Wait and collect all test results
            TestResultsSummary allTestResults = runner.getAllTestResults();

            // Print results
            System.out.println(allTestResults);

        } catch (Exception e) {
            eyes.abortIfNotClosed();
            driver.quit();
            e.printStackTrace();
        }

    }


}
