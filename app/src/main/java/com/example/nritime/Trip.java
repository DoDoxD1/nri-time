package com.example.nritime;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Trip {

    String startDateStr, endDateStr;

    boolean isActive = false;

    LocalDate startDate;
    LocalDate endDate;

    long days;

    public Trip(String startDateStr, String endDateStr) {
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;

        if(endDateStr.equals("Active")){
            isActive = true;
            SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
            endDateStr = df.format(Calendar.getInstance().getTime());
        }
        startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd MM yyyy"));
        endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd MM yyyy"));

        days = ChronoUnit.DAYS.between(startDate,endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getDays(){
        return days;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }
}
