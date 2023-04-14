package client;

import server.models.Course;
import java.util.List;
import java.util.Scanner;


/**
 * Classe qui contrôle l'interaction en ligne de commande
 */
public class Client_Simple {

    /**
     * Commence l'interface graphique dans la ligne de commande.
     * Appelle les méthodes qui vont afficher les différentes options que l'utilisateur va pouvoir choisir.
     *
     * @param args argument de la ligne de commande
     */
    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        List<Course> courseList =  initialiserMenu(scanner, client);
        afficherCours(courseList);
        optionMenu(scanner, client);
        System.out.println("Fin de la connexion avec le serveur");
    }
    private static void afficherCours(List<Course> courseList){
        if (courseList!=null){
            System.out.println("Les cours offerts de la session d'"+courseList.get(0).getSession()+" sont:");
        for (int i=0;i<courseList.size();i++) {
            System.out.println((i + 1) + ". " + courseList.get(i).getCode() + "\t" + courseList.get(i).getName());
        }
        }
    }
    private static List<Course> initialiserMenu(Scanner scanner, Client client){
        System.out.println("***Bienvennue au portail d'inscription de cours de l'UDEM***");
        String line = choixSession(scanner);
        List<Course> courseList = client.Charger(line);
        return  courseList;
    }

    /**
     * Affiche le menu d'option à l'utilisateur.
     * Affiche différentes options comme voir les cours d'une session, s'inscrire à un cours ou quitter le programme
     * à l'utilisateur. Cette méthodes à besoin que l'utilisateur entre un choix valide.
     *
     * @param scanner laisse à l'utilisateur faire des choix
     * @param client objet représentant le client
     */
    public static void optionMenu(Scanner scanner,Client client){
        int choice =0;
        String line ="";
        do{
            do {
                System.out.println("> Choix:");
                System.out.println("1. Consulter les cours offerts pour une autre session");
                System.out.println("2. Inscription a un cours");
                System.out.println("3. Fermer l'espace client");
                System.out.print("> Choix: ");
                String temp = scanner.nextLine();
                if (temp.length() == 1 && Character.isDigit(temp.charAt(0))) {
                    choice = Integer.parseInt(temp);
                }
            }while(choice<1 || choice>3);
            switch (choice){
                case 1 : line = choixSession(scanner);
                    List<Course> courseList = client.Charger(line);
                    afficherCours(courseList);
                break;
                case 2 : line = inscription(scanner);
                    System.out.println(client.inscrire(line));
                break;
                case 3 : line = "";
            }

        }while(line.equals("")==false);
    }


    /**
     * Menu qui laisse l'utilisateur choisir une session.
     * Laisse à l'utilisateur choisir une session et retourne un string qui représentera la commande à
     * envoyer au serveur. Cette méthode à besoin que l'utilisateur entre un choix valide.
     *
     * @param scanner laisse à l'utilisateur faire des choix
     * @return un string qui représente la commande à envoyer au serveur
     */
    public static String choixSession(Scanner scanner){
        int choice = 0;
        String line = "CHARGER";
        do {
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Ete");
            System.out.print("> Choix: ");
            String temp = scanner.nextLine();
            if (temp.length()==1 && Character.isDigit(temp.charAt(0))){
                choice = Integer.parseInt(temp);
            }
        }while(choice<1 || choice>3);
        switch(choice){
            case 1 : line+=" Automne";break;
            case 2 : line+=" Hiver";break;
            case 3 : line+=" Ete";break;
            default : break;
        }
        return line;
    }

    /**
     * Laisse à l'utilisateur entré les informations d'inscriptions.
     * L'utilisateur entre les informations pour son inscription à un cours. Ceux-ci sont renvoyé
     * à la méthode appelante.
     *
     * @param scanner laisse à l'utilisateur faire des choix
     * @return unstring contenant toutes les informations d'inscriptions
     */
    public static String inscription(Scanner scanner){
        String line = "INSCRIRE ";
        System.out.print("Veuillez saisir votre prenom: ");
        line +=scanner.nextLine()+" ";
        System.out.print("Veuillez saisir votre nom: ");
        line+= scanner.nextLine()+" ";
        System.out.print("Veuillez saisir votre email: ");
        line+= scanner.nextLine()+" ";
        System.out.print("Veuillez saisir votre matricule: ");
        line+= scanner.nextLine()+" ";
        System.out.print("Veuillez saisir le code du cours: ");
        line+= scanner.nextLine();
        return line;
    }
}
