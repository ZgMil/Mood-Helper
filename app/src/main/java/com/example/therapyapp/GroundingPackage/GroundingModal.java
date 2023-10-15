package com.example.therapyapp.GroundingPackage;

public class GroundingModal {
    private int address;
    private String text;
    private String name;
    private int id;
    public int getAddress() { return address; }
    public void setAddress(int address) { this.address = address; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public GroundingModal( String name, int address, String text)
    {
        this.name = name;
        this.text = text;
        this.address = address;
    }
}
