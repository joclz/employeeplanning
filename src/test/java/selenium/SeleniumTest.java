package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver", "D:\\Programme\\geckodriver-v0.29.0-win64\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        //Maximize browser window
        driver.manage().window().maximize();
        //Go to URL which you want to navigate
        driver.get("http://localhost:4200/employeeplanning#/");
        //Set  timeout  for 500 milliseconds so that the page may load properly within that time
        timeout(driver, 500);

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminPass");
        timeout(driver, 250);
        driver.findElement(By.id("submitLogin")).click();

        driver.findElement(By.id("menuMitarbeiter")).click();
        timeout(driver, 250);
        driver.findElement(By.id("listeMitarbeiter")).click();
    }

    private static void timeout(WebDriver driver, long millis) {
        driver.manage().timeouts().implicitlyWait(millis, TimeUnit.MILLISECONDS);
    }
}
