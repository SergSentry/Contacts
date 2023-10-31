package org.example;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class ContactBook {
    private static final String BLOCK_DIVIDER = "---------------------------------------------------------------------";

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var contactBook = new ContactBook();

        while (true) {
            var id = contactBook.showMenu(scanner, "1-ADD CONTACT, 0-EXIT", 0, 1);
            if (id == 0) {
                scanner.close();
                break;
            }

            if (id == 1) {
                ContactReader reader = new ContactReader(scanner);
                ResponseCode code = reader.readContact();
                if (code == ResponseCode.BAD_LEN) {
                    System.out.println("Check the input data. You enter little or a lot of data.");
                    continue;
                }

                try {
                    Contact contact = reader.parseContact();
                    ContactWriter writer = new ContactWriter(Paths.get("").toFile());
                    writer.write(contact);
                } catch (IllegalArgumentException ie) {
                    System.out.println(ie.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public int showMenu(Scanner in, String menuCaptions, int min, int max) {
        System.out.println(BLOCK_DIVIDER);
        System.out.print(menuCaptions);
        int id = -1;

        do {
            System.out.format("\nchoose (%d-%d): ", min, max);
            while (!in.hasNextInt()) {
                in.next();
                System.out.format("\nchoose (%d-%d): ", min, max);
            }

            id = in.nextInt();
        } while (id < min && id > max);

        return id;
    }
}