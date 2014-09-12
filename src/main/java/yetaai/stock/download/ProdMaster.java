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
package yetaai.stock.download;

import WebFileDownloader.FileDownloader;
import static com.steadystate.css.parser.SACParserCSS1Constants.URL;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.regex.Pattern;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import yetaai.stock.tools.CArrays;
import yetaai.stock.tools.SeleniumWaiter;
import yetaai.stock.tools.YetaaiWebDriver;
import yetaai.stock.tools.DBConnPool;

/**
 *
 * @author Yetaai
 */
public class ProdMaster {

    //Down Jones 30 Industrial Average, http://money.cnn.com/data/dow30/
    //S&P 500 
    //Nasdaq Composite
    //S&P / TSX Composite
    //Most active http://www.nasdaq.com/markets/most-active.aspx
    //Deloitte 500 fastest growing companies
    //Mutual funds index
    //Owners informations
    //Major holders
    //All companies http://www.nasdaq.com/screening/company-list.aspx
    //http://www.nasdaq.com/screening/companies-by-name.aspx?letter=A
    //http://www.nasdaq.com/screening/companies-by-name.aspx?letter=A&page=13
    //http://www.nasdaq.com/screening/companies-by-name.aspx?letter=A&exchange=NYSE&render=download
    public static void masterByNasdaq() {
        Connection conn = DBConnPool.getAConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("select taskid, taskname, taskurl, batchno from downloadingtasks order by batchno asc, taskid asc");
            ResultSet rs = ps.executeQuery();
            //rs.beforeFirst();
            
            while (rs.next()) {
                //                     taskid                  taskname             taskurl                   batchno
//                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
                
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
            }
        }
    }

    public static void masterByNasdaqold() throws Exception {
        final String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        final WebDriver driver1 = new YetaaiWebDriver().driver;
        final String nasdaqMaster = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=NASDAQ&render=download";
        driver1.get(nasdaqMaster);
        final FileDownloader downloader1 = new FileDownloader(driver1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloader1.downloadFileDirect(nasdaqMaster, "d" + date + "product001nasdaq" + ".csv");
                } catch (IOException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (NullPointerException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                }
                System.out.println("Product master in Nasdaq downloaded");
            }
        }).start();
//        SeleniumWaiter swaiter = new SeleniumWaiter(driver1);
        ////input[starts-with(@id, 'activation:') and contains(@id, ':voId:1')]
//        WebElement downloadLink = swaiter.waitForMe(By.xpath("//div[@id='resultsDisplay']/a[contains(@href, 'companies-by-name')]"),5);
//        WebElement downloadLink = swaiter.waitForMe(By.cssSelector("#resultsDisplay > a:nth-child(3)"),20);
//        System.out.println(driver1.getPageSource());
        //downloader.localDownloadPath("e:\\webscrap\\temp");
//        String downloadedFileAbsoluteLocation = downloader1.downloadFile(downloadLink, "nyse.csv");
        final String nyseMaster = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=NYSE&render=download";
        final WebDriver driver2 = new YetaaiWebDriver().driver;
        driver2.get(nyseMaster);
        final FileDownloader downloader2 = new FileDownloader(driver2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloader2.downloadFileDirect(nyseMaster, "d" + date + "product002nyse" + ".csv");
                } catch (IOException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (NullPointerException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                }
                System.out.println("Product master in NYSE downloaded");
            }
        }).start();
        final String amexMaster = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=AMEX&render=download";
        final WebDriver driver3 = new YetaaiWebDriver().driver;
        driver3.get(amexMaster);
        final FileDownloader downloader3 = new FileDownloader(driver3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloader3.downloadFileDirect(amexMaster, "d" + date + "product003amex" + ".csv");
                } catch (IOException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (NullPointerException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ProdMaster.class.getName()).log(Level.ERROR, null, ex);
                }
                System.out.println("Product master in AMEX downloaded");
            }
        }).start();
    }

    public static void main(String[] arguments) throws Exception {
        masterByNasdaqold();
    }

    @SuppressWarnings("empty-statement")
    public static void masterByNasdaqDownload() {
        String baseUrl0 = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=A&exchange=NYSE&render=download";
        WebDriver driver = new YetaaiWebDriver().driver;
        driver.get(baseUrl0);
        SeleniumWaiter swaiter = new SeleniumWaiter(driver);
        long starttime = System.currentTimeMillis();
        while (System.currentTimeMillis() - starttime < 1000 * 10) {
        };
        System.out.println(driver.getPageSource());
    }

    /**
     * @throws Exception
     */
    public static void secondPage() throws Exception {
        WebDriver driver;
        String baseUrl;
        StringBuffer verificationErrors = new StringBuffer();
        DesiredCapabilities dCaps;
        dCaps = new DesiredCapabilities();
        dCaps.setJavascriptEnabled(true);
        dCaps.setCapability("takesScreenshot", false);
//        driver1 = new RemoteWebDriver(new URL("http://localhost:8888/wd/hub"), dCaps);//PhantomJSDriver(dCaps);
        driver = new RemoteWebDriver(new URL("http://localhost:8888/"), dCaps);//PhantomJSDriver(dCaps);

        baseUrl = "https://finance.yahoo.com/";
//        driver1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        SeleniumWaiter swait = new SeleniumWaiter(driver);
//        swait.waitForMe(By.xpath("//*@id='txtQuotes'"), 10);
        WebElement we = swait.waitForMe(By.xpath("//*[@id='txtQuotes']"), 30);
        we.click();
        we.sendKeys("GE");
        //Search the action button and click then wait until the data is ready on page.Then next page etc. can be clicked thereafter.
        Long initime = System.currentTimeMillis();
        //while(System.currentTimeMillis() - initime < 30 * 1000) {
        //}
        System.out.println(driver.getPageSource());
    }
}
