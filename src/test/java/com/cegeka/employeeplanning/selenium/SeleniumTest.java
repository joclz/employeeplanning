package com.cegeka.employeeplanning.selenium;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest {
    public static final int SPEED_FACTOR = 1;

    public static final int TIMEOUT_VERY_SHORT = 100 / SPEED_FACTOR;
    public static final int TIMEOUT_SHORT = 250 / SPEED_FACTOR;
    public static final int TIMEOUT_SHORT_WITHOUT_FAKTOR = 250;
    public static final int TIMEOUT_MIDDLE = 500 / SPEED_FACTOR;
    public static final int TIMEOUT_LONG = 1500 / SPEED_FACTOR;

    public static final String MITARBEITER_NACHNAME = "Aal";
    public static final String MITARBEITER_VORNAME = "Selenium-Test";
    public static final String MITARBEITER_STUNDENSATZ_NEU = "88.8";

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver-v0.29.0-win64/geckodriver.exe");
        driver = new FirefoxDriver();
        /* Maximize browser window */
        driver.manage().window().maximize();
        /* Go to URL which you want to navigate */
        String baseUrl = "http://localhost:4200/employeeplanning#/";
        driver.get(baseUrl);
        /* Set  timeout  for 500 milliseconds so that the page may load properly within that time */
        sleep(TIMEOUT_MIDDLE);

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminPass");
        driver.findElement(By.id("submitLogin")).click();
    }

    @Test
    public void test_001_Login() {
        /* Logout */
        driver.findElement(By.id("menuUser")).click();
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("logoutUser")).click();
        sleep(TIMEOUT_VERY_SHORT);

        /* Login */
        driver.findElement(By.id("username")).sendKeys("a");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("d");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("m");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("i");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("username")).sendKeys("n");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("password")).sendKeys("adminX");
        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("submitLogin")).click();
        sleep(TIMEOUT_MIDDLE);

        /* Bei NICHT erfolgreichem Login, ist das Element noch vorhanden */
        List<WebElement> username = driver.findElements(By.id("username"));
        Assert.assertEquals(username.size(), 1);

        driver.findElement(By.id("password")).clear();
        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("password")).sendKeys("adminPass");
        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("submitLogin")).click();
        sleep(TIMEOUT_MIDDLE);

        /* Bei erfolgreichem Login, darf das Element nicht mehr vorhanden sein */
        username = driver.findElements(By.id("username"));
        Assert.assertEquals(username.size(), 0);
    }

    @Test
    public void test_010_AddMitarbeiter() {
        /* Mitarbeiter Hinzufügen
           -> FindElement by id, name, xpath  */
        driver.findElement(By.id("menuMitarbeiter")).click();
        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("addMitarbeiter")).click();
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterName")).sendKeys(MITARBEITER_NACHNAME);
        // oder auch über xpath. Macht aber meines Erachtens auch nur dann Sinn wenn eine id vergeben worden ist.
        //driver.findElement(By.xpath("//*[@id='mitarbeiterName']")).sendKeys(MITARBEITER_NACHNAME);
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.name("mitarbeiterVorname")).sendKeys(MITARBEITER_VORNAME);
        sleep(TIMEOUT_VERY_SHORT);

        driver.findElement(By.id("mitarbeiterStundensatz")).sendKeys("a");
        sleep(TIMEOUT_VERY_SHORT);

        driver.findElement(By.id("mitarbeiterStatus")).sendKeys("Angestellt");
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterUnit")).sendKeys("Factory Nürnberg");
        sleep(TIMEOUT_SHORT_WITHOUT_FAKTOR);

        /* Check Fehlermeldung Stundensatz */
        WebElement mitarbeiterStundensatzError = driver.findElement(By.id("mitarbeiterStundensatzError"));
        Assert.assertEquals(mitarbeiterStundensatzError.getText(), "Stundensatz ist ein Pflichtfeld");
        driver.findElement(By.id("mitarbeiterStundensatz")).clear();
        driver.findElement(By.id("mitarbeiterStundensatz")).sendKeys("75.0");

        driver.findElement(By.id("mitarbeiterHinzufuegen")).click();
        sleep(TIMEOUT_SHORT);

        /* Check, ob Error-Msg bz. Erfolgs-Msg angezeigt wird */
        List<WebElement> error = driver.findElements(By.cssSelector("mat-error"));
        Assert.assertEquals(error.size(), 0);

        List<WebElement> success = driver.findElements(By.cssSelector("mat-success"));
        Assert.assertEquals(success.size(), 1);
    }

    @Test
    public void test_020_FindAllMitarbeiterSelectTheAddedOneAndEdit() {
        /* Auflisten Mitarbeiter */
        listMitarbeiterAndFilterAal();

        /* Sortierung nach Name */
        driver.findElement(By.id("sortHeaderName")).click();

        WebElement tableMitarbeiter = driver.findElement(By.className("tableMitarbeiter"));
        /* Nur zum Test */
        List<WebElement> tableRows = tableMitarbeiter.findElements(By.tagName("tr"));
        String filteredMitarbeiter = tableRows.get(1).getText();
        Assert.assertNotEquals(filteredMitarbeiter, "Keine Daten passend zu Filter: " + MITARBEITER_NACHNAME + " gefunden");

        /* Zugriff per X-Path auf das Element in der 1. Zeile, 7. Spalte, 2. Button */
        WebElement editierButton = tableMitarbeiter.findElement(By.xpath("//div//table/tbody/tr[1]/td[7]//button[2]"));
        editierButton.click();
        driver.findElement(By.id("mitarbeiterStundensatz")).clear();
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("mitarbeiterStundensatz")).sendKeys(MITARBEITER_STUNDENSATZ_NEU);
        sleep(TIMEOUT_VERY_SHORT);
        driver.findElement(By.id("submitButton")).click();
        sleep(TIMEOUT_VERY_SHORT);
    }

    @Test
    public void test_021_FindAllNewMitarbeiterWithWeiterButton() {
        listMitarbeiter();

        WebElement paginator = driver.findElement(By.cssSelector("mat-paginator"));
        WebElement weiterButton = paginator.findElement(By.xpath("//div//div//div[2]//button[2]"));
        int counterElements = 0;
        boolean weiter = true;

        WebElement tableMitarbeiter = driver.findElement(By.className("tableMitarbeiter"));
        while (weiter) {
            List<WebElement> tableRows = tableMitarbeiter.findElements(By.tagName("tr"));
            for (WebElement tableRow : tableRows) {
                if (tableRow.getText().contains(MITARBEITER_NACHNAME) && tableRow.getText().contains(MITARBEITER_VORNAME)) {
                    counterElements++;
                }
            }
            if (weiterButton.isEnabled()) {
                weiterButton.click();
                sleep(TIMEOUT_SHORT);
            } else {
                weiter = false;
            }
        }

        Assert.assertTrue(counterElements > 0);
    }

    @Test
    public void test_030_CheckStundensatzChangeOfEditedMitarbeiter() {
        listMitarbeiterAndFilterAal();

        WebElement tableMitarbeiter = driver.findElement(By.className("tableMitarbeiter"));

        /* Zugriff per X-Path auf das Element (Stundensatz) in der 1. Zeile, 5. Spalte */
        WebElement stundensatzMitarbeiter = tableMitarbeiter.findElement(By.xpath("//div//table/tbody/tr[1]/td[5]"));
        Assert.assertEquals(stundensatzMitarbeiter.getText(), MITARBEITER_STUNDENSATZ_NEU);
    }

    @Test
    public void test_040_CheckDeleteMitarbeiter() {
        listMitarbeiterAndFilterAal();

        WebElement tableMitarbeiter = driver.findElement(By.className("tableMitarbeiter"));

        /* Zugriff per X-Path auf das Element in der 1. Zeile, 7. Spalte, 1. Button */
        WebElement deleteButton = tableMitarbeiter.findElement(By.xpath("//div//table/tbody/tr[1]/td[7]//button[1]"));
        deleteButton.click();

        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("Ja")).click();
        sleep(TIMEOUT_SHORT);

        List<WebElement> success = driver.findElements(By.cssSelector("mat-success"));
        Assert.assertEquals(success.size(), 1);
    }

    private void listMitarbeiterAndFilterAal() {
        listMitarbeiter();
        driver.findElement(By.id("filterMitarbeiter")).sendKeys(MITARBEITER_NACHNAME);
        sleep(TIMEOUT_VERY_SHORT);
    }

    private void listMitarbeiter() {
        driver.findElement(By.id("menuMitarbeiter")).click();
        sleep(TIMEOUT_SHORT);
        driver.findElement(By.id("listMitarbeiter")).click();
        sleep(TIMEOUT_VERY_SHORT);
    }

    @After
    public void tearDown() {
        sleep(TIMEOUT_LONG);
        driver.quit();
        //System.setProperty("webdriver.firefox.marionette", "false");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wait(WebDriver driver, long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }
}
