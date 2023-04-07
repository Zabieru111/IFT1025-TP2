package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public List<Course> courseList = new ArrayList<>();
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
    public String inscrire(String line){
        String[] information = line.split(" ");
        String command = information[0];
        String prenom = information[1];
        String nom = information[2];
        String email = information[3];
        String matricule = information[4];
        String courseName = information[5];
        Course course = verifierCours(courseName);
        if (course!=null) {
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
        return "Le code du cours "+courseName+" n'existe pas dans vos cours listés";
    }
    public Course verifierCours(String courseName){
        for (int i =0;i<courseList.size();i++){
            if (courseList.get(i).getCode().equals(courseName)){
                return courseList.get(i);
            }
        }
        return null;
    }
}

