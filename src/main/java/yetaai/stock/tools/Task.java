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
import java.util.GregorianCalendar;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yetaai
 */
public class Task {
    private String taskdate;
    private int taskid;
    private String market;
    private String symbol;
    private String comment;
    private short status;
    private int batchno;
    private long createdtime;
    private long begintime;
    private long endtime;
    public final static short CREATED = 5;
    public final static short SCHEDULED = 10;
    public final static short RUNNING = 50;
    public final static short FAILED = 55;
    public final static short SUCCESSFUL = 60;
    
    public void saveTaskToDB() {
        try {
            Connection conn = DBConnPool.getAConnection();
            PreparedStatement ps = conn.prepareStatement("insert into task (taskdate, taskid, market, symbol," +
                    "comment, status, batchno, createdtime, begintime, endtime) "
                    + "values(?, ?, ?, ?,     ?, ?, ?,   ?, ?, ?)");
            ps.setString(1, this.getTaskdate());
            ps.setInt(2, this.getTaskid());
            ps.setString(3, this.getMarket());
            ps.setString(4, this.getSymbol());
            ps.setString(5, this.getComment());
            ps.setShort(6, this.getStatus());
            ps.setInt(7, this.getBatchno());
            ps.setLong(8, this.getCreatedtime());
            ps.setLong(9, this.getBegintime());
            ps.setLong(10, this.getEndtime());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, this.getClass().getName() + ": SQL Exception saving Task to DB");
        }
    }
    public void syncTaskFromDB() {
        try {
            Connection conn = DBConnPool.getAConnection();
            PreparedStatement ps = conn.prepareStatement("select taskdate, taskid, market, symbol," +
                    "comment, status, batchno, createdtime, begintime, endtime from task where taskdate = ? and taskid = ?");
            ps.setString(1, this.getTaskdate());
            ps.setInt(2, this.getTaskid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                this.setMarket(rs.getString("market"));
                this.setSymbol(rs.getString("symbol"));
                this.setComment(rs.getString("comment"));
                this.setStatus(rs.getShort("status"));
                this.setBatchno(rs.getInt("batchno"));
                this.setCreatedtime(rs.getLong("createdtime"));
                this.setBegintime(rs.getLong("begintime"));
                this.setEndtime(rs.getLong("endtime"));
            }
        } catch (SQLException sqle) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, this.getClass().getName() + ": SQL Exception getting task from DB");
        }
    }
    public String getStatusText() {
        switch (this.getStatus()) {
            case Task.CREATED : return "Created";
            case Task.SCHEDULED : return "Scheduled";
            case Task.RUNNING: return "Running";
            case Task.FAILED: return "Failed";
            case Task.SUCCESSFUL: return "Successful";
            default: return "UNKNOWN";
        }
    }

    /**
     * @return the taskdate
     */
    public String getTaskdate() {
        return taskdate;
    }

    /**
     * @return the taskid
     */
    public int getTaskid() {
        return taskid;
    }

    /**
     * @return the market
     */
    public String getMarket() {
        return market;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(String market) {
        this.market = market;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the status
     */
    public short getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(short status) {
        this.status = status;
    }

    /**
     * @return the batchno
     */
    public int getBatchno() {
        return batchno;
    }

    /**
     * @param batchno the batchno to set
     */
    public void setBatchno(int batchno) {
        this.batchno = batchno;
    }

    /**
     * @return the createdtime
     */
    public long getCreatedtime() {
        return createdtime;
    }

    /**
     * @param createdtime the createdtime to set
     */
    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }

    /**
     * @return the begintime
     */
    public long getBegintime() {
        return begintime;
    }

    /**
     * @param begintime the begintime to set
     */
    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    /**
     * @return the endtime
     */
    public long getEndtime() {
        return endtime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    /**
     * @param taskdate the taskdate to set
     */
    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }

    /**
     * @param taskid the taskid to set
     */
    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }
}
