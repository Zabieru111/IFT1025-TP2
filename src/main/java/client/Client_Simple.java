package client;

import server.models.Course;
import java.util.List;
import java.util.Scanner;

public class Client_Simple {
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
