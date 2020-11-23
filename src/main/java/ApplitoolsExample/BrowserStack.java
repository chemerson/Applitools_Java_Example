package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.params;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStack {


    public static final String USERNAME = "<YOUR BROWSERSTACK USERNAME HERE>";
    public static final String AUTOMATE_KEY = "<AUTOMATE KEY FROM BROWSERSTACK>";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";


    public static void main(String[] args) throws MalformedURLException {
        // Initialize the Runner for your test.
        EyesRunner runner = new ClassicRunner();

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        // Change the APPLITOOLS_API_KEY API key with yours
        // eyes.setApiKey("");  //Put your API key here
        // or pull from the environment variable
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));


        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "80.0 beta");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("resolution", "1280x1024");
        caps.setCapability("name", "Bstack-[Java] Sample Test");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

        try {

            eyes.open(driver, "Demo App", "Demo Test", new RectangleSize(1200, 800));

            driver.get("https://applitools.com/helloworld");

            eyes.check("Home Page", Target.window().fully());

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
