package org.shelan.model;


import java.util.Date;

/**
 * Access Log model
 */
public class AccessLog {
    private Date timestamp;
    private String username;
    private boolean loggingSuccess;

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggingSuccess() {
        return loggingSuccess;
    }

    public void setLoggingSuccess(boolean loggingSuccess) {
        this.loggingSuccess = loggingSuccess;
    }
}
