package uz.pdp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.attach.AgentInitializationException;
import uz.pdp.model.Contact;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactServise {

    private static List<Contact> contactList=new ArrayList<>();
      static   Contact contact=new Contact();

      static{
          readFromFile();

      }

    public static void main(String[] args) {

        while (true){


            System.out.println("""
                    1.Add
                    2.Show
                    3.Edit
                    4.Delete
                    5.search
                    0.Exit
                   """);

            String option= getInputAsString("Choose option : ");

            if(option.equals("0")) return;

            switch (option){
                case "1" -> addContact();
                case "2" -> showContact();
                case "3" -> editContact();
                case "4" -> deleteContact();
                case "5" -> searchContact();
            }

        }

    }

    private static void addContact() {
        String name=getInputAsString("Enter name : ");
        String phone;

        do {
            phone=getInputAsString("Enter phone number : ");
        }while (!phone.matches("\\+998[0-9]{9}"));

        Contact add=null;

        for (Contact contact : contactList) {
            if(contact.getPhone().equals(phone)){
                add=contact;
                break;
            }
        }

        if (add==null){
            contact.setPhone(phone);
            contact.setFullName(name);
            contactList.add(contact);

            writeToFile();
            System.out.println("OK");
        }else {
            System.out.println("No");
        }








    }

    private static void writeToFile() {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();

        Path path = Path.of("src/main/resources", "contacts.json");

        try {
            Files.writeString(path,gson.toJson(contactList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void readFromFile() {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();

        Path path = Path.of("src/main/resources", "contacts.json");

      if (!Files.exists(path)) return;

        try {
            String json=Files.readString(path);

            List<Contact> list = gson.fromJson(json, new TypeToken<>() {});

            ContactServise.contactList.addAll(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void showContact() {

        if (contactList.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("amount of contact : "+ contactList.size());
        System.out.println();
        int i=0;
        for (Contact contact1 : contactList) {
            System.out.println(++i+" - "+contact1);
        }
        System.out.println();
    }

    private static void editContact() {
        if (contactList.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("amount of contact : "+ contactList.size());
        System.out.println();
        int i=0;
        for (Contact contact1 : contactList) {
            System.out.println(++i+" - "+contact1);
        }
        System.out.println();
        String edit;
        do {
         edit=getInputAsString("enter number to edit : ");

        }while (!edit.matches("\\+998[0-9]{9}"));

        Contact e=null;
        for (Contact contact1 : contactList) {
            if(contact1.getPhone().equals(edit)){
                e=contact1;
                break;

            }


        }


        if(e!=null){
            String newNum;
            String newName=getInputAsString("Enter new name : ");
            do {
                newNum=getInputAsString("Enter new number : ");
            }while (!newNum.matches("\\+998[0-9]{9}"));

            for (Contact contact1 : contactList) {
                if(!contact1.getPhone().equals(newNum)){
                    e.setFullName(newName);
                    e.setPhone(newNum);

                    writeToFile();

                    System.out.println("success");
                    return;

                }else {
                    System.out.println("already exists");
                    return;
                }
            }

        }else {

        System.out.println("Not found");
        }




    }

    private static void deleteContact() {

        if (contactList.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("amount of contact : "+ contactList.size());
        System.out.println();
        int i=0;
        for (Contact contact1 : contactList) {
            System.out.println(++i+" - "+contact1);
        }
        System.out.println();

        String delete;

        do {
            delete=getInputAsString("enter number to delete : ");
        }while (!delete.matches("\\+998[0-9]{9}"));

        for (Contact contact1 : contactList) {
            if (contact1.getPhone().equals(delete)){
                contactList.remove(contact1);
                System.out.println("Contact has deleted!");
                writeToFile();
                return;
            }
        }
        System.out.println("Not found!");

    }

    private static void searchContact() {
        if (contactList.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("amount of contact : "+ contactList.size());
        System.out.println();
        int i=0;
        for (Contact contact1 : contactList) {
            System.out.println(++i+" - "+contact1);
        }
        System.out.println();

        String search=getInputAsString("enter number or name to search contact : ");

        for (Contact contact1 : contactList) {
            if(contact1.getPhone().contains(search)||contact1.getFullName().contains(search)){
                System.out.println(contact1);
                return;
            }
        }
        System.out.println("Contact is not found!");
    }

    static Scanner scanner=new Scanner(System.in);
    private static String getInputAsString(String s) {

        System.out.print(s);

        return scanner.nextLine();
    }
}
