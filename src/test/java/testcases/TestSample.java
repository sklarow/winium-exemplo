package testcases;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestSample {

    static DesktopOptions options;
    static WiniumDriverService service;
    static WiniumDriver driver;

    @BeforeTest
    public static void setupEnvironment() {
        options = new DesktopOptions();
        options.setApplicationPath("C:\\Windows\\System32\\calc.exe");

        File driverPath = new File("C:\\env\\winiumdriver\\Winium.Desktop.Driver.exe");
        service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true).withSilent(true).buildDesktopService();
        try {
            service.start();
        } catch (IOException e) {
            System.out.println("Exception while starting WINIUM service");
            e.printStackTrace();
        }
        driver = new WiniumDriver(service,options);

    }

    @Test
    public void multiplyTest() {

        driver.findElement(By.name("Eight")).click();
        driver.findElement(By.name("Multiply by")).click();
        driver.findElement(By.name("Nine")).click();
        driver.findElement(By.id("equalButton")).click();

        String results = driver.findElement(By.id("CalculatorResults")).getAttribute("Name");



        Assert.assertEquals(results, "Display is 72");

    }

    @Test
    public void sumTest() {

        driver.findElement(By.name("Eight")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Two")).click();
        driver.findElement(By.id("equalButton")).click();

        String results = driver.findElement(By.id("CalculatorResults")).getAttribute("Name");

        Assert.assertEquals(results, "Display is 10");

    }

    @Test
    public void subtractTest(){

        Random rn = new Random();
        int firstNumber = rn.nextInt(9) + 1;
        int secondNumber = rn.nextInt(9) + 1;

        int expectedSubtraction = firstNumber - secondNumber;

        String firstLocator = "num" + firstNumber + "Button";
        String secondLocator = "num" + secondNumber + "Button";

        driver.findElement(By.id(firstLocator)).click();
        driver.findElement(By.name("Minus")).click();
        driver.findElement(By.id(secondLocator)).click();
        driver.findElement(By.id("equalButton")).click();

        String results = driver.findElement(By.id("CalculatorResults")).getAttribute("Name");
        String expectedResults = "Display is " + expectedSubtraction;

        Assert.assertEquals(results, expectedResults);

    }

    @AfterTest
    public void tearDown(){

        driver.findElementById("Close").click();
        service.stop();
    }
}