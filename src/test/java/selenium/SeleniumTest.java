package selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumTest {
    public static final int TIMEOUT_VERY_SHORT = 100;
    public static final int TIMEOUT_SHORT = 250;
    public static final int TIMEOUT_MIDDLE = 500;
    public static final int TIMEOUT_LONG = 1000;

    private WebDriver driver;
    private String baseUrl = "http://localhost:4200/employeeplanning#/";

    @Before
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", "D:\\Programme\\geckodriver-v0.29.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        //Maximize browser window
        driver.manage().window().maximize();
        //Go to URL which you want to navigate
        driver.get(baseUrl);
        //Set  timeout  for 500 milliseconds so that the page may load properly within that time
        sleep(driver, TIMEOUT_MIDDLE);

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminPass");
        driver.findElement(By.id("submitLogin")).click();
    }

    @Test
    public void testLogin() {
        /** Logout */
        driver.findElement(By.id("menuUser")).click();
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("logoutUser")).click();
        sleep(driver, TIMEOUT_VERY_SHORT);

        /** Login */
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
    }

    @Test
    public void testAddMitarbeiter() {
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

        driver.findElement(By.id("mitarbeiterStatus")).sendKeys("Angestellt");
        sleep(driver, TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterUnit")).sendKeys("Factory Nürnberg");
        sleep(driver, TIMEOUT_SHORT);

        /** Check Fehlermeldung Stundensatz */
        WebElement mitarbeiterStundensatzError = driver.findElement(By.id("mitarbeiterStundensatzError"));
        Assert.assertEquals(mitarbeiterStundensatzError.getText(), "Stundensatz ist ein Pflichtfeld");
        driver.findElement(By.id("mitarbeiterStundensatz")).clear();
        driver.findElement(By.id("mitarbeiterStundensatz")).sendKeys("75.0");

        driver.findElement(By.id("mitarbeiterHinzufuegen")).click();
        sleep(driver, TIMEOUT_SHORT);
    }

    @Test
    public void testMyStuff() {

        /** Auflisten Mitarbeiter
         */
        driver.findElement(By.id("menuMitarbeiter")).click();
        sleep(driver, TIMEOUT_SHORT);
        driver.findElement(By.id("listMitarbeiter")).click();
    }

    @After
    public void tearDown() {
        sleep(driver, 1500);
        driver.close();
        //System.setProperty("webdriver.firefox.marionette", "false");
    }

    private void sleep(WebDriver driver, long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.manage().timeouts().setScriptTimeout(millis, TimeUnit.MILLISECONDS);
    }
}
