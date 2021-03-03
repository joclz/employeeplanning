package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
    public static final int TIMEOUT_VERY_SHORT = 100;
    public static final int TIMEOUT_SHORT = 250;
    public static final int TIMEOUT_MIDDLE = 500;
    public static final int TIMEOUT_LONG = 1000;

    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver", "D:\\Programme\\geckodriver-v0.29.0-win64\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        //Maximize browser window
        driver.manage().window().maximize();
        //Go to URL which you want to navigate
        driver.get("http://localhost:4200/employeeplanning#/");
        //Set  timeout  for 500 milliseconds so that the page may load properly within that time
        sleep(driver, TIMEOUT_LONG);

        /** LOGIN
         */
        driver.findElement(By.id("username")).sendKeys("a");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("d");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("m");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("i");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("n");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("password")).sendKeys("adminPass");
        sleep(driver, TIMEOUT_SHORT);
        driver.findElement(By.id("submitLogin")).click();
        sleep(driver, TIMEOUT_MIDDLE);

        /** Mitarbeiter Hinzufügen
         *  -> FindElement by id, name, xpath  */
        driver.findElement(By.id("menuMitarbeiter")).click();
        sleep(driver, TIMEOUT_SHORT);
        driver.findElement(By.id("addMitarbeiter")).click();
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterName")).sendKeys("Aal");
        // oder auch über xpath. Macht aber meines Erachtens auch nur dann Sinn wenn eine id vergeben worden ist.
        //driver.findElement(By.xpath("//*[@id='mitarbeiterName']")).sendKeys("Aal");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.name("mitarbeiterVorname")).sendKeys("Norbert");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterStundensatz")).sendKeys("a");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterStatus")).sendKeys("a");
        sleep(driver, TIMEOUT_LONG);

        /** Auflisten Mitarbeiter
         */
        driver.findElement(By.id("menuMitarbeiter")).click();
        sleep(driver, TIMEOUT_SHORT);
        driver.findElement(By.id("listMitarbeiter")).click();

        System.setProperty("webdriver.firefox.marionette", "false");
    }

    private static void sleep(WebDriver driver, long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.manage().timeouts().setScriptTimeout(millis, TimeUnit.MILLISECONDS);
    }
}
