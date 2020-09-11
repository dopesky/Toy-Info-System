package com.strathmore.projectds.toy;

import com.strathmore.projectds.converters.DateTimeConverter;
import org.joda.time.DateTime;

public class Toy {
    public String code = null;
    public String name = null;
    public String description = null;
    public Integer price = null;
    public DateTime date = null;
    public String batch = null;
    public String company = null;
    public String street = null;
    public String zip = null;
    public String country = null;

    public Toy() {
    }

    @Override
    public String toString() {
        String code = "Code: " + this.code;
        String name = "Name: " + this.name;
        String description = "Description: " + this.description;
        String price = "Price: " + this.price.toString();
        String date = "Date: " + this.date.toString(DateTimeConverter.FORMAT);
        String batch = "Batch: " + this.batch;
        String company = "Company Name: " + this.company;
        String street = "Street Address: " + this.street;
        String zip = "Zip Code: " + this.zip;
        String country = "Country: " + this.country;
        return code + ", " + name + ", " + description + ", " + price + ", " + date + ", " + batch + ", " + company + ", " + street + ", " + zip + ", " + country;
    }
}
