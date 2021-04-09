package utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class page {
    private static void loadPage(RemoteWebDriver driver) throws InterruptedException {
        Actions builder = new Actions(driver);
        Action seriesOfActions = builder
                .sendKeys(Keys.PAGE_DOWN)
                .build();
        Integer i = 0;
        Long height = (Long) driver.executeScript("return document.body.scrollHeight;");

        //Some pages are > 15000 so for now do some pages if this happens
        if(height >= 15000){
            Thread.sleep(500);
            for (i = 0; i < 23; i++) {
                seriesOfActions.perform();
                Thread.sleep(500);
            }
        } else {
            for (i = 0; i < height / 800 && height < 15000; i++) {
                seriesOfActions.perform();
                Thread.sleep(500);
                height = (Long) driver.executeScript("return document.body.scrollHeight;");
            }
        }
    }
}
