package com.strathmore.projectds.toy;

import org.joda.time.DateTime;

public class Toy {
    String code = null;
    String name = null;
    String description = null;
    Integer price = null;
    DateTime date = null;
    String batch = null;
    String company = null;
    String street = null;
    String zip = null;
    String country = null;

    public Toy(String code, String name, String description, int price, DateTime date, String batch, String company, String street, String zip, String country) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.batch = batch;
        this.company = company;
        this.street = street;
        this.zip = zip;
        this.country = country;
    }
}
