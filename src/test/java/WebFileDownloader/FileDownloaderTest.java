/*
 * Copyright (c) 2014, Yetaai
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package WebFileDownloader;

import static WebFileDownloader.UrlStatusCheckerTest.urlChecker;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yetaai.stock.tools.SeleniumWaiter;
import yetaai.stock.tools.YetaaiWebDriver;

/**
 *
 * @author Yetaai
 */
public class FileDownloaderTest {

    WebDriver driver = new YetaaiWebDriver().driver;

    @Test
    public void downloadAFile() throws Exception {
        WebDriver driver = new YetaaiWebDriver().driver;
        driver.get("http://www.nasdaq.com/screening/companies-by-name.aspx?letter=A&exchange=NYSE");
        SeleniumWaiter swaiter = new SeleniumWaiter(driver);
        ////input[starts-with(@id, 'activation:') and contains(@id, ':voId:1')]
        WebElement downloadLink = swaiter.waitForMe(By.xpath("//a[starts-with(@href, 'companies-by-name')]"),5);
//        WebElement downloadLink = swaiter.waitForMe(By.cssSelector("#resultsDisplay > a:nth-child(3)"),20);
//        System.out.println(driver.getPageSource());

        FileDownloader downloader = new FileDownloader(driver);
        
        String downloadedFileAbsoluteLocation = downloader.downloadFile(downloadLink, "nyse.csv");

        assert (new File(downloadedFileAbsoluteLocation).exists());
        assert (downloader.getHTTPStatusOfLastDownloadAttempt() == 200);
    }

    @Test
    public void downloadAnImage() throws Exception {
        FileDownloader downloadTestFile = new FileDownloader(driver);
        driver.get("http://www.localhost.com//downloadTest.html");
        WebElement image = driver.findElement(By.id("ebselenImage"));
        String downloadedImageAbsoluteLocation = downloadTestFile.downloadImage(image, "imagefilename");

        assert (new File(downloadedImageAbsoluteLocation).exists());
        assert (downloadTestFile.getHTTPStatusOfLastDownloadAttempt() == 200);
    }

    public FileDownloaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of followRedirectsWhenDownloading method, of class FileDownloader.
     */
    @Test
    public void testFollowRedirectsWhenDownloading() {
        System.out.println("followRedirectsWhenDownloading");
        boolean value = false;
        FileDownloader instance = null;
        instance.followRedirectsWhenDownloading(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of localDownloadPath method, of class FileDownloader.
     */
    @Test
    public void testLocalDownloadPath_0args() {
        System.out.println("localDownloadPath");
        FileDownloader instance = null;
        String expResult = "";
        String result = instance.localDownloadPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of localDownloadPath method, of class FileDownloader.
     */
    @Test
    public void testLocalDownloadPath_String() {
        System.out.println("localDownloadPath");
        String filePath = "";
        FileDownloader instance = null;
        instance.localDownloadPath(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downloadFile method, of class FileDownloader.
     */
    @Test
    public void testDownloadFile() throws Exception {
        System.out.println("downloadFile");
        WebElement element = null;
        FileDownloader instance = null;
        String expResult = "";
        String result = instance.downloadFile(element, "nyse.csv");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downloadImage method, of class FileDownloader.
     * @throws java.lang.Exception
     */
    @Test
    public void testDownloadImage() throws Exception {
        System.out.println("downloadImage");
        WebElement element = null;
        FileDownloader instance = null;
        String expResult = "";
        String result = instance.downloadImage(element, "nyse.csv");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHTTPStatusOfLastDownloadAttempt method, of class
     * FileDownloader.
     */
    @Test
    public void testGetHTTPStatusOfLastDownloadAttempt() {
        System.out.println("getHTTPStatusOfLastDownloadAttempt");
        FileDownloader instance = null;
        int expResult = 0;
        int result = instance.getHTTPStatusOfLastDownloadAttempt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mimicWebDriverCookieState method, of class FileDownloader.
     */
    @Test
    public void testMimicWebDriverCookieState() {
        System.out.println("mimicWebDriverCookieState");
        boolean value = false;
        FileDownloader instance = null;
        instance.mimicWebDriverCookieState(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
