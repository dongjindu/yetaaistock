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

import java.net.URI;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.openqa.grid.common.SeleniumProtocol.WebDriver;
import org.openqa.selenium.WebDriver;
import yetaai.stock.tools.YetaaiWebDriver;

/**
 *
 * @author Yetaai
 */
public class UrlStatusCheckerTest {

    public static UrlStatusChecker urlChecker;

    public UrlStatusCheckerTest() throws Exception{
        statusCode404FromString();
    }

    public void statusCode404FromString() throws Exception {
        urlChecker.setURIToCheck("webServerURL" + ":" + "webServerPort" + "/doesNotExist.html");
        urlChecker.setHTTPRequestMethod(RequestMethod.GET);
        assert (urlChecker.getHTTPStatusCode() == 404);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        WebDriver driver = new YetaaiWebDriver().driver;
        urlChecker = new UrlStatusChecker(driver);
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
     * Test of setURIToCheck method, of class UrlStatusChecker.
     */
    @Test
    public void testSetURIToCheck_String() throws Exception {
        System.out.println("setURIToCheck");
        String linkToCheck = "";
        UrlStatusChecker instance = null;
        instance.setURIToCheck(linkToCheck);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setURIToCheck method, of class UrlStatusChecker.
     */
    @Test
    public void testSetURIToCheck_URI() throws Exception {
        System.out.println("setURIToCheck");
        URI linkToCheck = null;
        UrlStatusChecker instance = null;
        instance.setURIToCheck(linkToCheck);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setURIToCheck method, of class UrlStatusChecker.
     */
    @Test
    public void testSetURIToCheck_URL() throws Exception {
        System.out.println("setURIToCheck");
        URL linkToCheck = null;
        UrlStatusChecker instance = null;
        instance.setURIToCheck(linkToCheck);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHTTPRequestMethod method, of class UrlStatusChecker.
     */
    @Test
    public void testSetHTTPRequestMethod() {
        System.out.println("setHTTPRequestMethod");
        RequestMethod requestMethod = null;
        UrlStatusChecker instance = null;
        instance.setHTTPRequestMethod(requestMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of followRedirects method, of class UrlStatusChecker.
     */
    @Test
    public void testFollowRedirects() {
        System.out.println("followRedirects");
        Boolean value = null;
        UrlStatusChecker instance = null;
        instance.followRedirects(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHTTPStatusCode method, of class UrlStatusChecker.
     */
    @Test
    public void testGetHTTPStatusCode() throws Exception {
        System.out.println("getHTTPStatusCode");
        UrlStatusChecker instance = null;
        int expResult = 0;
        int result = instance.getHTTPStatusCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mimicWebDriverCookieState method, of class UrlStatusChecker.
     */
    @Test
    public void testMimicWebDriverCookieState() {
        System.out.println("mimicWebDriverCookieState");
        boolean value = false;
        UrlStatusChecker instance = null;
        instance.mimicWebDriverCookieState(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
