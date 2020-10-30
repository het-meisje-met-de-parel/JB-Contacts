package contacts;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Contact {

    protected LocalDateTime created = LocalDateTime.now();
    protected LocalDateTime edited = LocalDateTime.now();

    protected Map<String, String> field2value = new LinkedHashMap<>();
    protected final List <Field> fields;

    public Contact(String number, List<Field> fields) {
        this.fields = fields;

        setValue(Field.NUMBER.field, number);
        for (Field field : fields) {
            setValue(field.field, "[no data]");
        }

        edited = created;
    }

    public void setValue(String field, String value) {
        if ("number".equals(field)) {
            if (checkNumber(value)) {
                field2value.put(field, value);
            } else {
                field2value.put(field, "[no number]");
            }
        } else {
            field2value.put(field, value);
        }
        edited = LocalDateTime.now();
    }

    public String getValue(String field) {
        return field2value.get(field);
    }

    public List<String> getObjectFields() {
        return List.copyOf(field2value.keySet());
    }

    public abstract String getPreview();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            String value = field2value.get(field.field);
            sb.append(field.text).append(": ").append(value).append("\n");
        }

        String number = getValue(Field.NUMBER.field);
        sb.append(Field.NUMBER.text).append(": ").append(number).append("\n");
        sb.append("Time created: ").append(created).append("\n");
        sb.append("Time last edit: ").append(edited).append("\n");
        return sb.toString();
    }

    public boolean hasNumber() {
        String number = getValue("number");
        if (!checkNumber(number)) {
            return false;
        }
        if (number.equals("[no number]")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNumber(String number) {
        String[] groups;
        if (!(number.contains(" ") || number.contains("-"))) {
            if (!number.matches("[0-9a-zA-Z()+]+")) {
                System.err.println("!!!!!");
                return false;
            }
            if (number.contains("+") && number.charAt(0) != '+') {
                System.err.println("!!!!!");
                return false;
            }
            return true;
        } else {
            groups = number.split("[ -]");
            if (groups[0].contains("+") && groups[0].charAt(0) != '+') {
                System.err.println("1 !!!!!");
                return false;
            }
            if ((groups[0].contains("(") && groups[0].contains(")"))
                    && (groups[1].contains("(") && groups[1].contains(")"))) {
                System.err.println("2 !!!!!");
                return false;
            }
            for (int i = 0; i < groups.length; i++) {
                if (groups[i].length() < 2 && i != 0) {
                    System.err.println("3 !!!!!");
                    return false;
                }
                if (!groups[i].matches("[0-9a-zA-Z()+]+")) {
                    System.err.println("4 !!!!! " + groups[i]);
                    return false;
                }
                if (!groups[i].matches("(\\([0-9a-zA-Z+]+\\)|[0-9a-zA-Z+]+)")) {
                    System.err.println("43 !!!!! " + groups[i]);
                    return false;
                }
                if (groups[i].contains("+") && i != 0) {
                    System.err.println("5 !!!!!");
                    return false;
                }
                if (groups[i].contains("(") && groups[i].contains(")")
                        && (i != 0 && i != 1)) {
                    System.err.println("6 !!!!!");
                    return false;
                }
                if ((groups[i].contains("(") && !groups[i].contains(")"))
                || (!groups[i].contains("(") && groups[i].contains(")"))) {
                    System.err.println("7 !!!!!");
                    return false;
                }
            }
            return true;
        }
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

}
