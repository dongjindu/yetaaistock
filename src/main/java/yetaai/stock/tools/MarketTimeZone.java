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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yetaai
 */
public class MarketTimeZone {
    /**
     * Buffer all time zones of all market
     */
    private String market;
    private TimeZone timezone;
    private TimeZone dsttimezone;
    private TimeZone dbtimezone;
    private GregorianCalendar validfrom = new GregorianCalendar();
    private GregorianCalendar validto = new GregorianCalendar();
    private short dststartmonth;
    private short dststartday;
    private short dstendmonth;
    private short dstendday;
    private String opentime;
    private String closetime;
    private static ArrayList<MarketTimeZone> al;

    static {
        refreshTimeZone();
    }

    public static void refreshTimeZone() {
        Connection conn = DBConnPool.getAConnection();
        try {
            //create table marketworktime(market varchar(20), validfrom char(8), timezone varchar(30),
            //        validto char(8), dststart char(4), dstend char(4), opentime char(14), closetime char(14),
            //           primary key (market, validfrom));
            PreparedStatement ps = conn.prepareStatement("select market, validfrom, timezone,  validto, dststart, dstend"
                    + ", opentime, closetime from marketworktime order by market, validfrom asc");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int i = rs.getRow();
                MarketTimeZone mtz = new MarketTimeZone();
                mtz.market = rs.getString("market");
                mtz.dbtimezone = TimeZone.getTimeZone(rs.getString("timezone"));
                int irawoffset = mtz.dbtimezone.getRawOffset();
                mtz.timezone = Application.getUTCtimezone();
                mtz.timezone.setRawOffset(irawoffset);
                mtz.dsttimezone = Application.getUTCtimezone();
                mtz.dsttimezone.setRawOffset(irawoffset + mtz.dbtimezone.getDSTSavings());
                mtz.validfrom.setTime(Application.getUTCtimeformat().parse(rs.getString("validfrom")));
                mtz.validto.setTime(Application.getUTCtimeformat().parse(rs.getString("validto")));
                mtz.dststartday = Short.valueOf(rs.getString("dststart").substring(2, 4));
                mtz.dststartmonth = Short.valueOf(rs.getString("dststart").substring(0,2));
                mtz.dstendday = Short.valueOf(rs.getString("dstend").substring(2, 4));
                mtz.dstendmonth = Short.valueOf(rs.getString("dstend").substring(0,2));
                mtz.opentime = rs.getString("opentime");
                mtz.opentime = rs.getString("closetime");
                al.add(mtz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarketTimeZone.class.getName()).log(Level.ERROR, MarketTimeZone.class.getName() + ": SQL Exception");
        } catch (ParseException ex) {
            Logger.getLogger(MarketTimeZone.class.getName()).log(Level.ERROR, MarketTimeZone.class.getName() + ": Date from database is not parsed correctely");
        }
    }
    public static ArrayList<MarketTimeZone> getAllTimeZone() {
        return al;
    }
    public static TimeZone getMarketTimeZone(String m, GregorianCalendar g) {
        for (MarketTimeZone mtz:al) {
            if (m.equals(mtz.market)) {
                if (g.compareTo(mtz.validfrom) >= 0 && g.compareTo(mtz.validto) < 0 ) {
                    SimpleDateFormat format = Application.getUTCtimeformat();
                    format.setTimeZone(mtz.dbtimezone);
                    int istart = mtz.dststartmonth * 100 + mtz.dststartday;
                    int iend = mtz.dstendmonth * 100 + mtz.dstendday;
                    int ig = Short.valueOf(format.format(g).substring(4,6)) * 100
                            + Short.valueOf(format.format(g).substring(6,8));
                    if (istart < iend && ig >= istart && ig < iend) {
                        return mtz.dsttimezone;
                    } else if (istart < iend && ( ig < istart || ig >= iend )) {
                        return mtz.timezone;
                    } else if (istart > iend && ( ig >=istart || ig < iend)) {
                        return mtz.dsttimezone; //Normally southern globe
                    } else if (istart > iend && ( ig < istart && ig >= iend)) {
                        return mtz.timezone;
                    } else {
                        Logger.getLogger(MarketTimeZone.class.getName()).log(Level.ERROR, "Check daylight saving start and end day for " + mtz.market);
                    }
                }
            }
        }
        return null;
    }
}
