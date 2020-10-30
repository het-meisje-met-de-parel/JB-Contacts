package contacts;

public enum Field {

    NUMBER ("number", "Number"),
    NAME ("name", "Name"),
    ORG_NAME ("name", "Organization name"),
    SURNAME ("surname", "Surname"),
    BIRTH ("birth", "Birth date"),
    GENDER ("gender", "Gender"),
    ADDRESS ("address", "Address");

    public final String field, text;

    Field (String field, String text) {
        this.field = field; this.text = text;
    }

}
