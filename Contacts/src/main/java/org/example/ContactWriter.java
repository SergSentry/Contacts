package org.example;

import java.io.*;
import java.nio.file.Path;

public class ContactWriter {
    private final String root;

    public ContactWriter(File root) {
        this.root = root.getAbsolutePath();
    }

    public void write(Contact contact) throws IOException {
        File file = Path.of(this.root, contact.getSurname()).toFile();
        if (file.exists())
            appendContact(file, contact);
        else
            newContact(file, contact);
    }

    private void newContact(File file, Contact contact) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file);
             PrintWriter printer = new PrintWriter(new OutputStreamWriter(out))) {
            printer.println(contact);
        }
    }

    private void appendContact(File file, Contact contact) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file, true);
             PrintWriter printer = new PrintWriter(new OutputStreamWriter(out))) {
            printer.println(contact);
        }
    }
}
