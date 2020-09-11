package com.strathmore.projectds.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strathmore.projectds.converters.DateTimeConverter;
import com.strathmore.projectds.toy.Toy;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ServerProtocol {
    public static final int END = 0;
    public static final int WAITING = 1;

    private static final int CODE = 2;
    private static final int NAME = 3;

    private static final int DESCRIPTION = 4;
    private static final int PRICE = 5;
    private static final int DATE = 6;
    private static final int BATCH = 7;

    private static final int COMPANY = 8;
    private static final int STREET = 9;
    private static final int ZIP = 10;
    private static final int COUNTRY = 11;
    private static final int ALL = 12;

    private int state = WAITING;

    public String getOutput(String input) {
        String comment;
        switch (state) {
            case WAITING:
                state = CODE;
                return "Welcome to My Server. Enter your Toy Code: ";
            case CODE:
                state = NAME;
                return "Enter your Toy Name: ";
            case NAME:
                state = DESCRIPTION;
                return "Thank You " + input + ". Now enter your Toy Description: ";
            case DESCRIPTION:
                state = PRICE;
                return "Enter your Toy Price: ";
            case PRICE:
                state = DATE;
                comment = "That's Quite Pricey :(. ";
                if (Integer.parseInt(input) < 2000) {
                    comment = "That's An Affordable Price :). ";
                }
                return comment + "Enter your Toy Manufacturing Date: ";
            case DATE:
                state = BATCH;
                comment = "That Toy Must be Vintage! ";
                if (DateTime.parse(input, DateTimeConverter.FORMAT).isAfter(DateTime.parse("2020-01-01", DateTimeFormat.forPattern("yyyy-MM-dd")))) {
                    comment = "What a Recent Toy :). ";
                }
                return comment + "Enter your Toy Batch Number: ";
            case BATCH:
                state = COMPANY;
                return "Enter your Toy Company Name: ";
            case COMPANY:
                state = STREET;
                return "Enter your Toy Street Address: ";
            case STREET:
                state = ZIP;
                return "Enter your Toy ZIP Code: ";
            case ZIP:
                state = COUNTRY;
                return "Enter your Toy Country: ";
            case COUNTRY:
                state = ALL;
                comment = "";
                if (input.equalsIgnoreCase("Kenya")) {
                    comment = "Hey, I come from there too :). ";
                }
                return comment + "Enter all your toy Details: ";
            case ALL:
                state = END;
                Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();
                Toy toy = gson.fromJson(input, Toy.class);
                return "Thank You! You Have Input the Following Details: " + toy.toString() + ". Server Is Requesting a Unique Thank You Message: ";
            default:
                state = WAITING;
                return String.valueOf(END);
        }
    }
}
