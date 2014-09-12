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
package yetaai.stock.tools;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Yetaai
 */
public class YetaaiWebDriver {
    public WebDriver driver;
    public YetaaiWebDriver(String sUrl) {
        StringBuilder verificationErrors = new StringBuilder();
        DesiredCapabilities dCaps = new DesiredCapabilities();
        //if ( sUrl == null || sUrl.length() < 5) sUrl = DriverUrl;
        dCaps.setJavascriptEnabled(true);
        dCaps.setCapability("takesScreenshot", false);
        try {
            //        driver = new RemoteWebDriver(new URL("http://localhost:8888/wd/hub"), dCaps);//PhantomJSDriver(dCaps);
            driver = new RemoteWebDriver(new URL(sUrl), dCaps);//PhantomJSDriver(dCaps);
        } catch (MalformedURLException ex) {
            Logger.getLogger(YetaaiWebDriver.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    public YetaaiWebDriver() {
        String DriverUrl = "http://localhost:8888/";
        StringBuilder verificationErrors = new StringBuilder();
        DesiredCapabilities dCaps = new DesiredCapabilities();
        //if ( sUrl == null || sUrl.length() < 5) sUrl = DriverUrl;
        dCaps.setJavascriptEnabled(true);
        dCaps.setCapability("takesScreenshot", false);
        try {
            //        driver = new RemoteWebDriver(new URL("http://localhost:8888/wd/hub"), dCaps);//PhantomJSDriver(dCaps);
            driver = new RemoteWebDriver(new URL(DriverUrl), dCaps);//PhantomJSDriver(dCaps);
        } catch (MalformedURLException ex) {
            Logger.getLogger(YetaaiWebDriver.class.getName()).log(Level.ERROR, null, ex);
        }
    }
}
