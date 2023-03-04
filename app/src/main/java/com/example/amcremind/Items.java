package com.example.amcremind;

public class Items {
    private String totalno;
    private String itemname;
    private String renewal;
    private String itemprice;
    private String id;
    private String location;
    public Items() {

    }
    public Items(String id, String itemname, String renewal, String itemprice, String location, String tot) {
        this.id = id;
        this.itemname = itemname;
        this.renewal = renewal;
        this.itemprice = itemprice;
        this.location = location;
        this.totalno = tot;
    }

    public String getTotalno() {
        return totalno;
    }


    public String getid() {
        return id;
    }

    public String getItemname() {
        return itemname;
    }

    public String getRenewal() {
        return renewal;
    }

    public String getItemprice() {
        return itemprice;
    }

    public String getLocation() {
        return location;

    }
}
