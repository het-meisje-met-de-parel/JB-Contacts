package contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Contacts {

    private final List<Contact> contacts = new ArrayList<>();

    public int count() {
        return contacts.size();
    }

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public void delete(Contact contact) {
        contacts.remove(contact);
    }

    public String getContactsPreview() {
        return getContactsPreview(contacts);
    }

    public String getContactsPreview (List <Contact> contacts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contacts.size(); i++) {
            String preview = contacts.get(i).getPreview();
            sb.append(i + 1).append(". ").append(preview).append("\n");
        }
        return sb.toString();
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public List<Contact> filterContacts (String query) {
        return contacts.stream().filter(cont -> {
            return cont.getPreview().toLowerCase().contains(query)
                || cont.getValue(Field.NUMBER.field).contains(query);
        }).collect(Collectors.toList());
    }

}
