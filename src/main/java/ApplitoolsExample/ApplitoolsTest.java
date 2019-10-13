package ApplitoolsExample;

import com.applitools.eyes.*;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ApplitoolsTest {

    public static void main(String[] args) {
        // Initialize the Runner for your test.
        EyesRunner runner = new ClassicRunner();

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        // Change the APPLITOOLS_API_KEY API key with yours
        eyes.setApiKey("<YOUR KEY>");  //Put your API key here

        ChromeOptions cOptions = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(cOptions);

        try {

            eyes.open(driver, "Demo App", "Demo Test", new RectangleSize(800, 600));

            driver.get("https://applitools.com/helloworld");

            eyes.check("Home Page", Target.window().layout());

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
