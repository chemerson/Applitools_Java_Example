package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ApplitoolsTestUFGCodedRegions {

    public static void main(String[] args) {
        // Initialize the Runner for your test.
        EyesRunner runner = new VisualGridRunner(5);

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));  //Put your API key here
        ChromeOptions cOptions = new ChromeOptions();
       // cOptions.setCapability("acceptInsecureCerts", true);
        cOptions.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(cOptions);

        Configuration config = new Configuration();

        config
                .addBrowser(1024, 768, BrowserType.CHROME)
                .addBrowser(1024, 768, BrowserType.CHROME_ONE_VERSION_BACK)
                .addBrowser(1024, 768, BrowserType.FIREFOX)
                .addBrowser(1024, 768, BrowserType.FIREFOX_ONE_VERSION_BACK)
                .addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_Pro_Max, ScreenOrientation.PORTRAIT))
                .addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_X, ScreenOrientation.PORTRAIT))
                .addBrowser(new IosDeviceInfo(IosDeviceName.iPad_Pro_3, ScreenOrientation.PORTRAIT))

                .setLayoutBreakpoints(true)

                .setBranchName("Child Branch 2")
                .setParentBranchName("Parent Branch 1")
                .setAppName("Applitools Demo App")
                .setTestName("Applitools Demo CR")
                .setViewportSize(new RectangleSize(1024, 768));

        eyes.setConfiguration(config);

        try {

            eyes.open(driver);

            driver.get("http://applitoolsjenkins.eastus.cloudapp.azure.com:5000/demo.html?version=1");

            eyes.check("Login", Target
                    .window()
                    .fully()
                    .layout(By.id("username"))
                    .content(By.cssSelector("#container > div > div.logo-w"))
            );

            eyes.check("Main Form Only", Target
                    .region(By.cssSelector("#main-form"))
            );

            driver.findElement(By.cssSelector("#log-in")).click();

            eyes.check("Empty PW", Target.window().fully().layout());

            // End the test.
            eyes.closeAsync();

            // Close the browser.
            driver.quit();

            // Wait and collect all test results
            TestResultsSummary allTestResults = runner.getAllTestResults(false);

            // Print results
            System.out.println(allTestResults);

        } catch (Exception e) {
            eyes.abortIfNotClosed();
            driver.quit();
            e.printStackTrace();
            System.exit(0);
        }
        System.exit(0);
    }

}
