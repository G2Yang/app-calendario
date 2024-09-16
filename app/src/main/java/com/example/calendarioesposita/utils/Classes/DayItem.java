package com.example.calendarioesposita.utils.Classes;

public class DayItem {
    private String day;
    private boolean isCurrentMonth;

    public DayItem(String day, boolean isCurrentMonth) {
        this.day = day;
        this.isCurrentMonth = isCurrentMonth;
    }

    public String getDay() {
        return day;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }
}