package erreur;

/**
 * Classe représentant une erreur de validation
 */
public class Erreur {
    String message;
    String field;

    /**
     * Constructeur d'une erreur
     *
     * @param message message d'erreur
     * @param field catégorie de l'erreur
     */
    public Erreur(String message, String field) {
        this.message = message;
        this.field = field;
    }

    /**
     * @return le message de l'erreur.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return la catégorie de l'erreur.
     */
    public String getField() {
        return field;
    }
}
