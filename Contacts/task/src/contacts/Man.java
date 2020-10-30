package contacts;

import java.time.LocalDateTime;
import java.util.List;

public class Man  extends Contact{

    public Man(String name, String surname, String number,
               String birthDate, String gender) {
        super(number, List.of(
            Field.NAME, Field.SURNAME, Field.BIRTH, Field.GENDER
        ));

        setValue(Field.NAME.field, name);
        setValue(Field.SURNAME.field, surname);
        setValue(Field.BIRTH.field, birthDate);
        setValue(Field.GENDER.field, gender);

        edited = created;
    }

    @Override
    public String getPreview() {
        String name = getValue(Field.NAME.field);
        String surname = getValue(Field.SURNAME.field);
        return String.format("%s %s", name, surname);
    }

}
