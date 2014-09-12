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

/**
 *
 * @author Yetaai
 */
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.net.ssl.SSLException;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FileDownloader {

    private static final Logger LOG = Logger.getLogger(FileDownloader.class);
    private WebDriver driver;
    private String localDownloadPath = "E:\\webscrap\\temp";// System.getProperty("java.io.tmpdir");
    private boolean followRedirects = true;
    private boolean mimicWebDriverCookieState = true;
    private int httpStatusOfLastDownloadAttempt = 0;

    public FileDownloader(WebDriver driverObject) {
        this.driver = driverObject;
    }

    /**
     * Specify if the FileDownloader class should follow redirects when trying
     * to download a file
     *
     * @param value
     */
    public void followRedirectsWhenDownloading(boolean value) {
        this.followRedirects = value;
    }

    /**
     * Get the current location that files will be downloaded to.
     *
     * @return The filepath that the file will be downloaded to.
     */
    public String localDownloadPath() {
        return this.localDownloadPath;
    }

    /**
     * Set the path that files will be downloaded to.
     *
     * @param filePath The filepath that the file will be downloaded to.
     */
    public void localDownloadPath(String filePath) {
        this.localDownloadPath = filePath;
    }

    /**
     * Download the file specified in the href attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadFile(WebElement element, String fileName) throws Exception {
        return downloader(element, "href", fileName);
    }

    /**
     * Download the image specified in the src attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadImage(WebElement element, String fileName) throws Exception {
        return downloader(element, "src", fileName);
    }

    /**
     * Gets the HTTP status code of the last download file attempt
     *
     * @return
     */
    public int getHTTPStatusOfLastDownloadAttempt() {
        return this.httpStatusOfLastDownloadAttempt;
    }

    /**
     * Mimic the cookie state of WebDriver (Defaults to true) This will enable
     * you to access files that are only available when logged in. If set to
     * false the connection will be made as an anonymouse user
     *
     * @param value
     */
    public void mimicWebDriverCookieState(boolean value) {
        this.mimicWebDriverCookieState = value;
    }

    /**
     * Load in all the cookies WebDriver currently knows about so that we can
     * mimic the browser cookie state
     *
     * @param seleniumCookieSet
     * @return
     */
    private BasicCookieStore mimicCookieState(Set<Cookie> seleniumCookieSet) {
        BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
        for (Cookie seleniumCookie : seleniumCookieSet) {
            BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            duplicateCookie.setDomain(seleniumCookie.getDomain());
            duplicateCookie.setSecure(seleniumCookie.isSecure());
            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
            duplicateCookie.setPath(seleniumCookie.getPath());
            mimicWebDriverCookieStore.addCookie(duplicateCookie);
        }

        return mimicWebDriverCookieStore;
    }

    /**
     * Perform the file/image download.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    private String downloader(WebElement element, String attribute, String fileName) throws IOException, NullPointerException, URISyntaxException {
        String fileToDownloadLocation = element.getAttribute(attribute);
        System.out.println("fielToDownloadLocation: " + fileToDownloadLocation);
        if (fileToDownloadLocation.trim().equals("")) {
            throw new NullPointerException("The element you have specified does not link to anything!");
        }
        return this.downloadFileDirect(fileToDownloadLocation, fileName);
    }

    /**
     * Perform the file/image download.
     *
     * @param fileToDownloadURL
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public String downloadFileDirect(String fileToDownloadURL, String fileName) throws IOException, NullPointerException, URISyntaxException {
        System.out.println("fielToDownloadLocation: " + fileToDownloadURL);
        if (fileToDownloadURL.trim().equals("")) {
            throw new NullPointerException("The element you have specified does not link to anything!");
        }

        URL fileToDownload = new URL(fileToDownloadURL);

        File downloadedFile = new File(this.localDownloadPath + java.io.File.separator + fileName);//fileToDownload.getFile().replaceFirst("/|\\\\", ""));
        if (downloadedFile.canWrite() == false) {
            downloadedFile.setWritable(true);
        }

        //HttpClient client = new DefaultHttpClient();
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(
                    IOException exception,
                    int executionCount,
                    HttpContext context) {
                if (executionCount >= 5) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient client = HttpClients.custom().setRetryHandler(myRetryHandler).build();
        BasicHttpContext localContext = new BasicHttpContext();

        LOG.info("Mimic WebDriver cookie state: " + this.mimicWebDriverCookieState);
        if (this.mimicWebDriverCookieState) {
            localContext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
        }

        HttpGet httpget = new HttpGet(fileToDownload.toURI());
        HttpParams params = httpget.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 300000);
        HttpConnectionParams.setSoTimeout(params, 300000);
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followRedirects);
        httpget.setParams(params);

        LOG.info("Sending GET request for: " + httpget.getURI());
        HttpResponse response = client.execute(httpget, localContext);
        this.httpStatusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();
        LOG.info("HTTP GET request status: " + this.httpStatusOfLastDownloadAttempt);
        LOG.info("Downloading file: " + downloadedFile.getName());
        FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
        response.getEntity().getContent().close();

        String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
        LOG.info("File downloaded to '" + downloadedFileAbsolutePath + "'");

        return downloadedFileAbsolutePath;
    }

}
