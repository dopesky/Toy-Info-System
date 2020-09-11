package com.strathmore.projectds.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strathmore.projectds.converters.DateTimeConverter;
import com.strathmore.projectds.toy.Toy;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.swing.*;

public class ClientProtocol {
    public static final int THANKS = 1;
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

    public int state = CODE;

    private final Toy toy;

    public ClientProtocol() {
        toy = new Toy();
    }

    public String setOutput(JTextField textField, JLabel errorLabel, JButton button) {
        String input = textField.getText();
        errorLabel.setText("");
        switch (state) {
            case CODE:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Code.");
                    return null;
                }
                state = NAME;
                toy.code = input;
                return input;
            case NAME:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Name.");
                    return null;
                }
                state = DESCRIPTION;
                toy.name = input;
                return input;
            case DESCRIPTION:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Description.");
                    return null;
                }
                state = PRICE;
                toy.description = input;
                return input;
            case PRICE:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Price.");
                    return null;
                }
                try {
                    toy.price = Integer.parseInt(input);
                    if (toy.price < 1) {
                        errorLabel.setText("Enter A Price Greater than 0.");
                        return null;
                    }
                    state = DATE;
                    return input;
                } catch (NumberFormatException nfe) {
                    errorLabel.setText("Enter an Integer Value For Price.");
                    return null;
                }
            case DATE:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Date.");
                    return null;
                }
                try {
                    toy.date = DateTime.parse(input, DateTimeFormat.forPattern("dd/MM/yyyy")).withZoneRetainFields(DateTimeConverter.ZONE);
                } catch (Exception ex) {
                    errorLabel.setText("Enter Valid Date in the Format dd/mm/yyyy.");
                    return null;
                }
                state = BATCH;
                return toy.date.toString(DateTimeConverter.FORMAT);
            case BATCH:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Batch Number.");
                    return null;
                }
                toy.batch = input;
                state = COMPANY;
                return input;
            case COMPANY:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Company Name.");
                    return null;
                }
                toy.company = input;
                state = STREET;
                return input;
            case STREET:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Street Address.");
                    return null;
                }
                toy.street = input;
                state = ZIP;
                return input;
            case ZIP:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid ZIP Code.");
                    return null;
                }
                toy.zip = input;
                state = COUNTRY;
                return input;
            case COUNTRY:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Valid Country Name.");
                    return null;
                }
                toy.country = input;
                state = ALL;
                textField.setEditable(false);
                textField.setEnabled(false);
                button.setText("SEND ALL AS JSON");
                return input;
            case ALL:
                textField.setEditable(true);
                textField.setEnabled(true);
                button.setText("SEND");
                state = THANKS;
                Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();
                return gson.toJson(toy);
            case THANKS:
                if (input.trim().length() < 1) {
                    errorLabel.setText("Enter a Thank You Message.");
                    return null;
                }
                state = CODE;
                return input;
            default:
                state = CODE;
                return null;
        }
    }
}
