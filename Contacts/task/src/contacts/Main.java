package contacts;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String DUMP_FILENAME = "contacts.db";

    public static void main(String[] args) {
        Contacts contacts = new Contacts();
        if (args.length > 0) {
            DUMP_FILENAME = args[0];

            System.out.printf("Open %s\n", DUMP_FILENAME);
            contacts = restoreContacts(DUMP_FILENAME);
        }

        Scanner scanner = new Scanner(System.in);
        menu(scanner, contacts);
    }

    private static void menu(Scanner scanner, Contacts contacts) {
        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            String action = scanner.nextLine();

            if ("count".equals(action)) {
                System.out.printf("The Phone Book has %d records.\n", contacts.count ());
            } else if ("add".equals(action)) {
                add(scanner, contacts);
            } else if ("list".equals(action)) {
                list(scanner, contacts);
            } else if ("search".equals(action)) {
                search(scanner, contacts);
            } else if (action.equals("exit")) {
                dumpContacts(contacts, DUMP_FILENAME);
                break;
            }

            System.out.println();
        }
    }

    private static void add(Scanner scanner, Contacts contacts) {
        System.out.println("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        Contact c = null;

        if (type.equals("person")) {
            System.out.println("Enter the name: ");
            String name = scanner.nextLine();
            System.out.println("Enter the surname: ");
            String surname = scanner.nextLine();
            System.out.println("Enter the birth date: ");
            String birthDate = scanner.nextLine();
            if (birthDate.isBlank()) {
                System.out.println("Bad birth date!");
                birthDate = "[no data]";
            }
            System.out.println("Enter the gender (M, F): ");
            String gender = scanner.nextLine();
            if (gender.isBlank()) {
                System.out.println("Bad gender!");
                gender = "[no data]";
            }
            System.out.println("Enter the number: ");
            String number = scanner.nextLine();
            c = new Man(name, surname, number, birthDate, gender);
        } else if (type.equals("organization")) {
            System.out.println("Enter the name: ");
            String name = scanner.nextLine();
            System.out.println("Enter the address: ");
            String address = scanner.nextLine();
            System.out.println("Enter the number: ");
            String number = scanner.nextLine();
            c = new Organization(name, number, address);
        } else {
            return;
        }

        contacts.add(c);
        if (!c.hasNumber()) {
            System.out.println("Wrong number format!");
        }
        System.out.println("The record added.");
    }

    private static void list(Scanner scanner, Contacts contacts) {
        System.out.println(contacts.getContactsPreview());

        while (true) {
            System.out.println("[list] Enter action ([number], back):");
            String action = scanner.nextLine();
            if ("back".equals(action)) {
                break;
            }

            int record = Integer.parseInt(action) - 1;
            if (!record(scanner, contacts, contacts.getContact(record))) {
                break;
            }
        }
    }

    private static boolean record(Scanner scanner, Contacts contacts, Contact contact) {
        System.out.println(contact);
        System.out.println();

        while (true) {
            System.out.println("[record] Enter action (edit, delete, menu):");
            String action = scanner.nextLine();

            if ("menu".equals(action)) {
                return false;
            } else if ("delete".equals(action)) {
                contacts.delete(contact);
                System.out.println("The record removed!");
                return false;
            } else if ("edit".equals(action)) {
                edit(scanner, contacts, contact);
            }
        }
    }

    private static void edit(Scanner scanner, Contacts contacts, Contact contact) {
        String fields = String.join(", ", contact.getObjectFields());
        System.out.printf("Select a field (%s)\n", fields);

        String field = scanner.nextLine();
        System.out.printf("Enter %s\n", field);
        contact.setValue(field, scanner.nextLine());
        System.out.println("Saved");
        System.out.println(contact);
    }

    private static void search(Scanner scanner, Contacts contacts) {
        while (true) {
            System.out.println("Enter the search query: ");
            String query = scanner.nextLine();

            List<Contact> found = contacts.filterContacts(query);
            System.out.printf("Found %d results:\n", found.size());
            System.out.println(contacts.getContactsPreview(found));

            System.out.println("[search] Enter action ([number], back, again):");
            String action = scanner.nextLine();

            if ("back".equals(action)) {
                break;
            } else if ("again".equals(action)) {
                continue;
            }

            int record = Integer.parseInt(action) - 1;
            if (!record(scanner, contacts, found.get(record))) {
                break;
            }
        }
    }

    private static Contacts restoreContacts(String filename) {
        try (
            FileInputStream fis = new FileInputStream(new File(filename));
            ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (Contacts) ois.readObject();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    private static void dumpContacts(Contacts contacts, String filename) {
        try (
            FileOutputStream fos = new FileOutputStream(new File(filename));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(contacts);
            oos.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
