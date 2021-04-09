package ApplitoolsExample;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.locators.TextRegion;
import com.applitools.eyes.locators.TextRegionSettings;
import com.applitools.eyes.locators.VisualLocator;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;


public class ApplitoolsOCR {

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
            .setBranchName("OCR")
            .setParentBranchName("Parent Branch 1")
            .setAppName("Applitools OCR")
            .setTestName("Applitools OCR")
            .setViewportSize(new RectangleSize(1440, 820));

        eyes.setConfiguration(config);

        try {

            eyes.open(driver);

            driver.get("https://www.geico.com/");

            eyes.check("Initial Sign In Page", Target.window().strict());

            System.out.println(extractText(eyes, "W"));

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

    public static String extractText(Eyes eyes, String search) {

        try {
            Map<String, List<TextRegion>> textRegions = eyes.extractTextRegions(new TextRegionSettings(search));
            List<TextRegion> regions = textRegions.get(search);

            TextRegion region = regions.get(0);
            int rSize = regions.size();
            String out = "";
            for(int i = 0 ; i<rSize ; i++){
                region = regions.get(i);
                out = out + "Match " + (i+1) + ": " + region.getText() + "\n";
            }

            return out ;

        } catch (Exception e) {
            return "extractText: '" + search + "' not found";
        }

    }

}
