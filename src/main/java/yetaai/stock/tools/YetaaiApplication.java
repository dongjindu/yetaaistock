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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yetaai
 */
public class YetaaiApplication {

    private static String nFirstTime = "FirstTime";
    private static GregorianCalendar vStartThisTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    private static HashMap<String, String> prophm = new HashMap();
    private static final SimpleDateFormat utctimeformat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final TimeZone utctimezone = TimeZone.getTimeZone("UTC");

    static {
        utctimeformat.setTimeZone(utctimezone);
        String vStartThisTimes;
        System.out.println(vStartThisTime.toString());
        vStartThisTimes = utctimeformat.format(vStartThisTime.getTime());
        Connection conn = DBConnPool.getAConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("select pname, pvalue from properties where pname = ?");
            ps.setString(1, nFirstTime);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                firstInitialize();
            }
            loadProps();
        } catch (SQLException sqle) {
            Logger.getLogger(YetaaiApplication.class.getName()).log(Level.ERROR, YetaaiApplication.class.getName() + ": SQL Exception");
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(YetaaiApplication.class.getName()).log(Level.ERROR, null, ex);
            }
        }
    }

    private static void firstInitialize() {
        Connection conn = DBConnPool.getAConnection();
        try {
            String vStartThisTimes = utctimeformat.format(vStartThisTime.getTime());
            PreparedStatement ps = conn.prepareStatement("insert into properties (pname, pvalue) values(?, ?)");
            ps.setString(1, nFirstTime);
            ps.setString(2, vStartThisTimes);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(YetaaiApplication.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    /**
     * Clear prophm and relaod all from database
     */
    private static void loadProps() {
        Connection conn = DBConnPool.getAConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("select pname, pvalue from properties");
            ResultSet rs = ps.executeQuery();
            prophm.clear();
            while (rs.next()) {
                prophm.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            Logger.getLogger(YetaaiApplication.class.getName()).log(Level.ERROR, YetaaiApplication.class.getName() + ": SQL exception");
        }
    }

    public static void flushProps() {
        Connection conn = DBConnPool.getAConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("replace into properties (pname, pvalue) value(?, ?)");
            for (Map.Entry<String, String> entry : prophm.entrySet()) {
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(YetaaiApplication.class.getName()).log(Level.ERROR, YetaaiApplication.class.getName() + ": SQL Exception");
        }
    }
    public static HashMap<String, String> getProps() {
        return YetaaiApplication.prophm;
    }
    public static SimpleDateFormat getUTCtimeformat() {
        return YetaaiApplication.utctimeformat;
    }
    public static TimeZone getUTCtimezone() {
        return YetaaiApplication.utctimezone;
    }
}
