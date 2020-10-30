package contacts;

import java.util.List;

public class Organization extends Contact{

    public Organization(String name, String number, String address) {
        super(number, List.of(Field.ORG_NAME, Field.ADDRESS));
        setValue(Field.ORG_NAME.field, name);
        setValue(Field.ADDRESS.field, address);

        edited = created;
    }

    @Override
    public String getPreview() {
        return getValue(Field.ORG_NAME.field);
    }

}
