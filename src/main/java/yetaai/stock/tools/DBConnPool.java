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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yetaai
 */
public class DBConnPool {

    private static DataSource dataSource;
    private static String DRIVER_NAME;
    private static String URL;
    private static String UNAME;
    private static String PWD;

    static {
        BasicConfigurator.configure();
        ResourceBundle config;
        FileInputStream fis;
        try {
            fis = new FileInputStream(DBConnPool.class.getClassLoader().getResource("DBProperties.properties").getPath());
            config = new PropertyResourceBundle(fis); //ResourceBundle.getBundle("DBProperties.properties");
            fis.close();
            DRIVER_NAME = config.getString("DRIVER_NAME"); //config.getString("com.mysql.jdbc.Driver");
            URL = config.getString("URL"); //config.getString("jdbc:mysql://localhost:3306/invest001");
            UNAME = config.getString("UNAME"); //config.getString("root");
            PWD = config.getString("PWD"); //config.getString("elttil");
            dataSource = setupDataSource();
        } catch (IOException ex) {
            Logger.getLogger(DBConnPool.class.getName()).log(Level.ERROR, null, ex);
        } finally {
        }
    }

    public static Connection getAConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnPool.class.getName()).log(Level.ERROR, null, ex);
        }
        return null;
    }

    private static DataSource setupDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(URL);
        cpds.setUser(UNAME);
        cpds.setPassword(PWD);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(10);
        return cpds;
    }
}
