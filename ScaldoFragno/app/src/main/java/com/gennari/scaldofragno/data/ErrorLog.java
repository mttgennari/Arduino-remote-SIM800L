package com.gennari.scaldofragno.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ErrorLog {
    private String data;
    private int errCode;
    private String errDesc;

    public int getErrCode() {
        return errCode;
    }

    public String getData() {
        long d = Long.parseLong(data);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(d*1000);
        return sdf.format(c.getTime());
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
}
