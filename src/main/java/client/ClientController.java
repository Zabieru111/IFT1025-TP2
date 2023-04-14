package client;

import erreur.Erreur;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import server.models.Course;

import java.util.List;

/**
 * Classe qui crée un lien entre la logique et la vue pour le model MVC
 */
public class ClientController {
    private Client client;

    private ClientFX clientFX;


    /**
     * Constructeur de la classe.
     * Celui-ci initialise un nouveau client.
     */
    public ClientController() {
        this.client = new Client();
    }

    /**
     * Envoit les informations nécessaire pour charger des cours.
     * Envoit les informations à la classe client et retourne la liste de cours à la vue
     * pour que ceux-ci se font afficher. Cette classe retourne toujours à la classe appelante, même si
     * la liste de cours est nulle.
     *
     * @param saison saison choisi par l'utilisateur
     * @return liste de cours que le serveur renvoit
     */
    public List<Course> charger(String saison){
        String line = "CHARGER "+saison;
        List<Course> temp = client.Charger(line);
        return temp;
    }

    /**
     * Envoit les différentes informations entré par l'utilisateur pour l'inscription.
     * Envoit les informations pour que celles-ci soit valider. Une erreur est retournée si l'une d'elles est non valide.
     * Si toutes sont valides, un message de confirmation est retourné.
     *
     * @param prenom prénom entré par l'utilisateur
     * @param nom nom entré par l'utilisateur
     * @param email email entré par l'utilisateur
     * @param matricule matricule entré par l'utilisateur
     * @param course cours entré par l'utilisateur
     * @param clientFX vue que l'utilisateur voit
     * @return une message de confirmation ou un message d'erreur
     */
    public String inscrire(TextField prenom, TextField nom, TextField email, TextField matricule, TableView course, ClientFX clientFX) {
        this.clientFX = clientFX;
        clientFX.resetField(prenom);
        clientFX.resetField(nom);
        clientFX.resetField(email);
        clientFX.resetField(matricule);
        clientFX.resetField(course);

        if (course.getSelectionModel().getSelectedItem()!=null) {
            Course temp = (Course) course.getSelectionModel().getSelectedItem();
            String line = "INSCRIRE " + prenom.getText() + " " + nom.getText() + " " + email.getText() + " " + matricule.getText() + " " +temp.getCode();
            String message = client.inscrire(line);
            if (message.equals("Erreur")) {
                message = "";
                for (Erreur erreur : client.getErreurs()) {
                    message += erreur.getMessage() + "\n";
                    if (erreur.getField().equals("prenomField")){
                        clientFX.changeField(prenom);
                    }
                    if (erreur.getField().equals("nomField")){
                        clientFX.changeField(nom);
                    }
                    if (erreur.getField().equals("emailField")){
                        clientFX.changeField(email);
                    }
                    if (erreur.getField().equals("matriculeField")){
                        clientFX.changeField(matricule);
                    }
                }
            }
            return message;
        }
        else{
            clientFX.changeField(course);
            return "Aucun cours selectionné";
        }
    }
}
