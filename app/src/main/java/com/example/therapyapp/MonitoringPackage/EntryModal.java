package com.example.therapyapp.MonitoringPackage;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
public class EntryModal {
        private int mood;
        private int concen;
        private int determ;
        private int balanc;
        private int self;
        private Date date;
        private int id;
        public int getMood() { return mood; }
        public void setMood(int mood) { this.mood = mood; }
        public int getconcen() { return concen; }
        public void setconcen(int concen) { this.concen = concen; }
        public int getDeterm() { return determ; }
        public void setDeterm(int determ) { this.determ = determ; }
        public int getBalanc() { return balanc; }
        public void setBalanc(int balanc) { this.balanc = balanc; }
        public int getSelf() { return self; }
        public void setSelf(int self) { this.self = self; }
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public EntryModal(int id, int mood, int concen, int determ, int balanc, int self, String date)
        {
                this.id = id;
                this.mood = mood;
                this.concen = concen;
                this.determ = determ;
                this.balanc = balanc;
                this.self = self;
                try {
                        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
        }
}
