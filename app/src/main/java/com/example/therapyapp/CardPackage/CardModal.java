package com.example.therapyapp.CardPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardModal {
    private String groups;
    private String text;
    private int id;
    public String getGroup() { return groups; }
    public void setGroup(String groups) { this.groups = groups; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public CardModal(int id, String groups, String text)
    {
        this.id = id;
        this.text = text;
        this.groups = groups;
    }
}
