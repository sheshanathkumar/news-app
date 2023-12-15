package com.sk.news.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NewsUtil {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Timestamp convertDateToTimestamp(String date){
        return Timestamp.valueOf(date);
    }

}
