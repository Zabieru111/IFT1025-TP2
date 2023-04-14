package client;

import erreur.Erreur;
import javafx.scene.layout.BorderPane;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le client dans l'interaction server-client.
 */
public class Client {
    List<Erreur> erreurs = new ArrayList<>();
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public List<Course> courseList = new ArrayList<>();


    /**
     * Retourne une liste de cours à la classe appelante.
     * Envoie la ligne entré en paramètre au serveur et récupère la liste de cours
     * que le serveur envoit. Cette classe retourne à la classe appelante toujours même si la liste de cours est nulle.
     *
     * @param line string qui représente la commande est envoyé au serveur.
     * @throws IOException erreur qui peut survenir s'il y a un problème entre la connexion avec le serveur
     * @throws ClassNotFoundException erreur qui peut survenir si le client n'arrive pas lire se que le serveur envoit
     * @return liste de cours de la session choisie.
     */
    public List<Course> Charger(String line){
        try{
            clientSocket = new Socket("127.0.0.1", 1337);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos.writeObject(line);
            List<Course> courseFiltered = (ArrayList)ois.readObject();
            oos.close();
            ois.close();
            addCourse(courseFiltered);
            return courseFiltered;
        }catch (IOException e){

        }catch (ClassNotFoundException e){

        }
        return null;
    }

    /**
     * sauvegarde les cours que le serveur envoit pour que l'utilisateur puisse les choisir.
     * Lorsqu'un cours est envoyé par le serveur, cette classe sauvegarde celui-ci dans une liste
     * permettant à l'utilisateur de le selectionner.
     *
     * @param filteredlist liste des cours que l'utilisateur à vu au moins une fois.
     */
    public void addCourse(List <Course> filteredlist){
        for (int i=0;i<filteredlist.size();i++){
            if (courseList.size()!=0) {
                if (courseList.indexOf(filteredlist.get(i)) == -1) {
                    courseList.add(filteredlist.get(i));
                }
            }
            else{
                courseList.add(filteredlist.get(i));
            }
        }
    }

    /**
     * Envoit au serveur les données d'inscription de l'utilisateur.
     * Crée une connexion avec le serveur et envoit un formulaire à celui-ci.
     * Si la connexion est établie, retourne un message de confirmation à l'utilisateur. Sinon, retourne un message
     * d'erreur à celui-ci.
     *
     * @param line information de l'inscription de l'utilisateur.
     * @throws IOException erreur qui peut survenir s'il y a un problème entre la connexion avec le serveur.
     * @return un message de confirmation ou un message d'erreur que la classe appelante affichera.
     */
    public String inscrire(String line){
        erreurs.clear();
        String[] information = line.split(" ");
        String command = information[0];
        String prenom = information[1];
        String nom = information[2];
        String email = information[3];
        String matricule = information[4];
        String courseName = information[5];
        validate(prenom,nom,email,matricule,courseName);
        Course course = verifierCours(courseName);
        if (erreurs.size()==0) {
            RegistrationForm registrationForm = new RegistrationForm(prenom, nom, email, matricule, course);
            try {
                clientSocket = new Socket("127.0.0.1", 1337);
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(command);
                oos.writeObject(registrationForm);
                oos.close();
                return  "Félicitations! Inscription réussie de "+prenom+" au cours "+courseName+".";
            }catch (IOException e){
                return "Erreur avec la connexion du server";
            }
        }
        return "Erreur";
    }

    /**
     * Retourne un cours si celui-ci est dans la liste de l'utilisateur.
     * verifie dans la liste de cours du client si le code du cours entré en paramètre est valide
     * si oui retourne le cours,
     * si non retourne null.
     *
     * @param courseName code du cours à vérifier
     * @return le cours qui à été sauvegarder dans la liste de cours du client
     */
    public Course verifierCours(String courseName){
        for (int i =0;i<courseList.size();i++){
            if (courseList.get(i).getCode().equals(courseName)){
                return courseList.get(i);
            }
        }
        return null;
    }

    /**
     * Vérifie les informations d'inscriptions de l'utilisateur et ajoute une erreur si celle-ci est non valide.
     * Vérifie la validité des différentes informations que l'utilisateur a entré et ajoute une erreur à la liste
     * d'erreur si l'information est non-valide.
     *
     * @param prenom prenom entré par l'utilisateur
     * @param nom nom entré par l'utilisateur
     * @param email email entré par l'utilisateur
     * @param matricule matricule entré par l'utilisateur
     * @param courseName code du cours entré par l'utilisateur
     */
    public void validate(String prenom,String nom, String email,String matricule,String courseName){
        if (prenom.matches("[A-z]+")==false){
            erreurs.add(new Erreur("Le prenom n'est pas valide","prenomField"));
        }
        if (nom.matches("[A-z]+")==false){
            erreurs.add(new Erreur("Le nom n'est pas valide","nomField"));
        }
        if (email.matches("[A-z]+@[A-z]+\\.[A-z]+")==false){
            erreurs.add(new Erreur("Le email n'est pas valide","emailField"));
        }
        if (matricule.matches("[0-9]{6}")==false){
            erreurs.add(new Erreur("Le matricule n'est pas valide","matriculeField"));
        }
        if (courseName ==null){
            erreurs.add(new Erreur("Aucun cours n'a été selectionné","courseField"));
        }
    }

    /**
     * Retourne la liste d'erreur du client
     *
     * @return retourne la liste d'erreur rencontré par le client
     */
    public List<Erreur> getErreurs() {
        return erreurs;
    }
}

