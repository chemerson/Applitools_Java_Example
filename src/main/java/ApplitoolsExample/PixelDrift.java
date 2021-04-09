package ApplitoolsExample;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.locators.OcrRegion;
import com.applitools.eyes.locators.TextRegion;
import com.applitools.eyes.locators.TextRegionSettings;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class PixelDrift {

    public static void main(String[] args) {
        // Initialize the Runner for your test.
        EyesRunner runner = new ClassicRunner();

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));  //Put your API key here

        // eyes.setProxy(new ProxySettings("http://127.0.0.1:8888"));

        ChromeOptions cOptions = new ChromeOptions();
        //cOptions.setCapability("acceptInsecureCerts", true);
        //cOptions.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(cOptions);

        BatchInfo batchInfo = new BatchInfo("Compare Match Types");
        batchInfo.addProperty("Test Type", "Match Levels");

        Configuration config = new Configuration();
        config
            .setBranchName("Child Branch 2")
            .setParentBranchName("Parent Branch 1")
            .setAppName("Applitools Demo App")
            .setTestName("Match Types")
            .setBatch(batchInfo)
            .setStitchMode(StitchMode.CSS)
            .setViewportSize(new RectangleSize(1440, 820))
        ;

        eyes.setConfiguration(config);

        try {

            eyes.open(driver);

            for (int i=1;i<4;i++) {

                driver.get("https://www.troweprice.com/personal-investing/accounts/index.html");
                eyesCheck(eyes, i);
                driver.get("https://www.troweprice.com/personal-investing/home.html");
                eyesCheck(eyes, i);
                driver.get("https://www.troweprice.com/personal-investing/tools/fund-research");
                eyesCheck(eyes, i);
                driver.get("https://www.troweprice.com/personal-investing/tools/fund-research/daily-prices");
                eyesCheck(eyes, i);

            }

            eyes.close(false);

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
        }

    }

    private static void eyesCheck(Eyes eyes, int matchLevel)  {
        switch (matchLevel) {
            case 1:
                eyes.check("Layout", Target.window().fully().layout());
                break;
            case 2:
                eyes.check("Strict", Target.window().fully().strict().ignoreDisplacements());
                break;
            case 3:
                eyes.check("Exact", Target.window().fully().exact());
                break;
            default:
                throw new IllegalArgumentException("Invalid match level " + matchLevel);
        }
    }


}
