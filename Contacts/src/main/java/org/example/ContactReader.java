package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ContactReader {
    private final Scanner scanner;
    private LinkedList<String> dataItems;

    public ContactReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public ResponseCode readContact() {
        System.out.println("Input with space separator: <Last Name> <First Name> <Patronymic> <date of birth> <phone number> <gender> : ");

        String data = this.scanner.useDelimiter("\n").next();

        this.dataItems = new LinkedList<>(List.of(data.split(" ")));
        if (this.dataItems.size() == 6)
            return ResponseCode.GOOD_LEN;


        return ResponseCode.BAD_LEN;
    }

    public Contact parseContact() throws IllegalArgumentException {
        var gender = getGender();
        var phone = getPhone();
        var birthDay = getBirthDay();

        var surname = getSurname();
        var name = getName();
        var midName = getMidName();

        return new Contact(surname, name, midName, gender).withBirthDay(birthDay).withPhone(phone);
    }

    private Date getBirthDay() throws IllegalArgumentException {
        int i = 0;
        while (i < this.dataItems.size()) {
            String value = this.dataItems.get(i);
            if (value.contains(".")) {
                var values = value.split("\\.");
                if (values.length != 3)
                    throw new IllegalArgumentException("Birthday must be in format dd.MM.yyyy");

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    var date = sdf.parse(value);
                    this.dataItems.remove(i);
                    return date;
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Birthday must be in format dd.MM.yyyy");
                }
            }
            i++;
        }

        throw new IllegalArgumentException("Birthday must be in format dd.MM.yyyy");
    }

    private long getPhone() throws IllegalArgumentException {
        int i = 0;
        while (i < this.dataItems.size()) {
            String value = this.dataItems.get(i);
            if (value.matches("\\d+")) {
                this.dataItems.remove(i);
                return Long.parseLong(value);
            }
            i++;
        }

        throw new IllegalArgumentException("Phone number must be in number format");
    }

    private Gender getGender() throws IllegalArgumentException {
        int i = 0;
        while (i < this.dataItems.size()) {
            String value = this.dataItems.get(i);
            if (value.equalsIgnoreCase("M") || value.equalsIgnoreCase("F")) {
                this.dataItems.remove(i);
                return Gender.valueOf(value.toUpperCase());
            }
            i++;
        }

        throw new IllegalArgumentException("Gender code must be M or F");
    }

    private String getSurname() throws IllegalArgumentException {
        if (!this.dataItems.isEmpty()) {
            var surname = dataItems.get(0);
            this.dataItems.remove(0);
            return surname;
        }

        throw new IllegalArgumentException("Surname");
    }

    private String getMidName() throws IllegalArgumentException {
        if (!this.dataItems.isEmpty()) {
            var middleName = dataItems.get(0);
            this.dataItems.remove(0);
            return middleName;
        }

        throw new IllegalArgumentException("MiddleName");
    }

    private String getName() throws IllegalArgumentException {
        if (!this.dataItems.isEmpty()) {
            var name = dataItems.get(0);
            this.dataItems.remove(0);
            return name;
        }

        throw new IllegalArgumentException("Name");
    }
}