package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.ProxySettings;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ApplitoolsClassicTest {

    public static void main(String[] args) {
        // Initialize the Runner for your test.
        EyesRunner runner = new ClassicRunner();

        // Initialize the eyes SDK
        Eyes eyes = new Eyes(runner);

        // Change the APPLITOOLS_API_KEY API key with yours
        eyes.setApiKey("8pVdTA8n6joSM18VHrggfo9OHmhaFjepyAbkdrZ1NcU110");  //Put your API key here
        // or pull from the environment variable
        // eyes.setApiKey(System.getenv(""));
        // eyes.setProxy(new ProxySettings("http://127.0.0.1:8888"));

        ChromeOptions cOptions = new ChromeOptions();
        cOptions.setCapability("acceptInsecureCerts", true);
        ChromeDriver driver = new ChromeDriver(cOptions);

        try {

            eyes.open(driver, "Demo App", "Demo Test", new RectangleSize(800, 600));

            driver.get("https://time.gov");

            eyes.check("Home Page", Target.window().fully().layout());

            // End the test.
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

}
