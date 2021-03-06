package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class ApplitoolsClassicTest {

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

        Configuration config = new Configuration();
        config
            .setBranchName("Child Branch 2")
            .setParentBranchName("Parent Branch 1")
            .setAppName("Applitools Demo App")
            .setTestName("Applitools Demo")
            .setViewportSize(new RectangleSize(1440, 820));

        eyes.setConfiguration(config);

        try {

            eyes.open(driver);

            driver.get("http://wikipedia.com");
            eyes.check("New Step", Target.window().fully().strict());
            
            driver.get("http://applitoolsjenkins.eastus.cloudapp.azure.com:5000/demo.html?version=1");
          //  driver.get("http://applitoolsjenkins.eastus.cloudapp.azure.com:5000/demo.html?version=1&changelogo=true");

            eyes.check("Initial Sign In Page", Target.window().fully().strict());

            driver.findElement(By.cssSelector("#log-in")).click();

            eyes.check("No Username and Password error", Target.window().fully().strict());



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
