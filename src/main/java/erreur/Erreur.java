package erreur;

public class Erreur {
    String message;
    String field;

    public Erreur(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
