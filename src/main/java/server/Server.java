package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructeur pour la classe Server.
     *
     * @param port le port sur lequel le serveur écoute.
     * @throws IOException erreur qui peut survenir s'il y a un problème entre la connexion avec le serveur.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute les EventHandlers.
     *
     * @param h eventhandler qui est ajouté.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Passe la commande et ses arguments à tous les eventHandlers.
     *
     * @param cmd partie commande de line.
     * @param arg arguments de line.
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Notifie la connection et la déconnection du client, et appelle les méthodes listen() et disconnect() pour
     * réagir à ce que le client envoie, notamment filtrer les cours et inscrire les données de l'utilisateur.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Appelle les méthodes qui vont décoder line.
     *
     * @throws IOException erreur qui peut survenir s'il y a un problème entre la connexion avec le serveur.
     * @throws ClassNotFoundException erreur qui peut survenir si le client n'arrive pas lire se que le serveur envoit.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Découpe line pour avoir la partie commande et les arguments.
     *
     * @param line
     * @return
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Déconnecte le client du serveur.
     *
     * @throws IOException erreur qui peut survenir s'il y a un problème entre la connexion avec le serveur.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Exécute handleRegistration() ou handleLoadCourses(arg) en fonction de la commande.
     *
     * @param cmd partie commande de line.
     * @param arg arguments de line.
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur.erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        // TODO: implémenter cette méthode
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/cours.txt"));
            List<Course> courseList = new ArrayList<>();

            String s;
            while ((s = reader.readLine()) != null) {
                String[] courses = s.split("\t");
                Course course = new Course(courses[1], courses[0], courses[2]);
                courseList.add(course);
            }
            List<Course> courseFiltered = new ArrayList<>();
            for (int i = 0; i < courseList.size(); i++) {
                if (courseList.get(i).getSession().equals(arg)) {
                    courseFiltered.add(courseList.get(i));
                }
            }
            objectOutputStream.writeObject(courseFiltered);

        }
        catch (IOException e) {
            System.out.println("Il y a une erreur.erreur!");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur.erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode
        try {
            String line = "";
            RegistrationForm registrationForm = (RegistrationForm) objectInputStream.readObject();
            BufferedReader reader = new BufferedReader(new FileReader("data/inscription.txt"));
            String s;
            while ((s = reader.readLine())!=null){
                line +=s+"\n";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/inscription.txt"));
            writer.append(line);
            line = registrationForm.getCourse().getSession() + "\t" + registrationForm.getCourse().getCode() +
                    "\t" + registrationForm.getMatricule() + "\t" + registrationForm.getPrenom() + "\t" +
                    registrationForm.getNom() + "\t" + registrationForm.getEmail();

            writer.append(line);

            writer.close();
        }
        catch (IOException e) {
            System.out.println("Il y a une erreur.erreur");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Il y a une erreur.erreur");
        }

    }
}

