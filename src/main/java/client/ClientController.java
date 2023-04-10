package client;

import erreur.Erreur;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import server.models.Course;

import java.util.List;

public class ClientController {
    private Client client;

    public ClientController() {
        this.client = new Client();
    }
    public List<Course> charger(String saison){
        String line = "CHARGER "+saison;
        List<Course> temp = client.Charger(line);
        return temp;
    }
    public String inscrire(TextField prenom, TextField nom, TextField email, TextField matricule, TableView course) {
        if (course.getSelectionModel().getSelectedItem()!=null) {
            Course temp = (Course) course.getSelectionModel().getSelectedItem();
            String line = "INSCRIRE " + prenom.getText() + " " + nom.getText() + " " + email.getText() + " " + matricule.getText() + " " +temp.getCode();
            String message = client.inscrire(line);
            if (message.equals("Erreur")) {
                message = "";
                for (Erreur erreur : client.getErreurs()) {
                }
            }
            return message;
        }
        else{
            return "Aucun cours selectionné";
        }
    }
}
