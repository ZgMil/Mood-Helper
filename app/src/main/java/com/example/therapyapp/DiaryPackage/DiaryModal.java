package com.example.therapyapp.DiaryPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryModal {
    private String text;
    private Date date;
    private int id;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public DiaryModal(int id,String text, String date)
    {
        this.text = text;
        this.id = id;
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd hh:mm:s").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

