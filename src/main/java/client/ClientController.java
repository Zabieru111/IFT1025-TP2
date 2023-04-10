package client;

import erreur.Erreur;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import server.models.Course;

import java.util.List;

public class ClientController {
    private Client client;

    private ClientFX clientFX;


    public ClientController() {
        this.client = new Client();
    }
    public List<Course> charger(String saison){
        String line = "CHARGER "+saison;
        List<Course> temp = client.Charger(line);
        return temp;
    }
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
