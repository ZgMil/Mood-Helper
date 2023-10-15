package com.example.therapyapp.InfoPackage;

public class InfoModal {
    private String text;
    private String name;
    private int tag;
    private int id;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getTag() { return tag; }
    public void setTag(int tag) { this.tag = tag; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public InfoModal( String name, String text, int tag)
    {
        this.name = name;
        this.text = text;
        this.tag = tag;
    }
}
