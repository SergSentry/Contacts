package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact {
    private final String name;
    private final String surname;
    private final String middleName;
    private final Gender gender;
    private Date birthDay;
    private long phoneNumber;

    public Contact(String surname, String name, String middleName, Gender gender){
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public Contact withBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public Contact withPhone(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        Character div = ' ';
        StringBuilder sb = new StringBuilder();

        sb.append(surname).append(div);
        sb.append(name).append(div);
        sb.append(middleName).append(div);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sb.append(sdf.format(birthDay)).append(div);

        sb.append(phoneNumber).append(div);
        sb.append(gender.toString().toLowerCase());
        return sb.toString();
    }
}
