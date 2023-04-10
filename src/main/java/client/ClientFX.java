package client;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;

import java.util.ArrayList;
import java.util.List;

public class ClientFX extends Application {
    public static void main(String[] args) {
        ClientFX.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        HBox root = new HBox();
        Scene scene = new Scene(root, 600, 450);

        VBox courseInterface = new VBox();
        VBox inscriptionInterface = new VBox();
        Separator separator = new Separator();


        Text courseTitle = new Text("Liste des cours");
        courseTitle.setFont(Font.font("serif", 25));
        courseInterface.setMinWidth(300);

        Text inscriptionTitle = new Text("Formulaire d'inscription");
        inscriptionTitle.setFont(Font.font("serif", 25));
        inscriptionInterface.setMinWidth(300);

        separator.setOrientation(Orientation.VERTICAL);

        courseInterface.getChildren().add(courseTitle);
        courseInterface.setMargin(courseTitle, new Insets(10));
        courseInterface.setAlignment(Pos.TOP_CENTER);

        inscriptionInterface.getChildren().add(inscriptionTitle);
        inscriptionInterface.setMargin(inscriptionTitle, new Insets(10));
        inscriptionInterface.setAlignment(Pos.TOP_CENTER);

        // Inscription Interface

        HBox prenomBox = new HBox();
        HBox nomBox = new HBox();
        HBox emailBox = new HBox();
        HBox matriculeBox = new HBox();

        Text prenomText = new Text("Prénom\t\t");
        Text nomText = new Text("Nom\t\t");
        Text emailText = new Text("Email\t\t");
        Text matriculeText = new Text("Matricule\t\t");

        TextField prenomField = new TextField();
        TextField nomField = new TextField();
        TextField emailField = new TextField();
        TextField matriculeField = new TextField();

        prenomBox.getChildren().addAll(prenomText, prenomField);
        prenomBox.setAlignment(Pos.CENTER);
        nomBox.getChildren().addAll(nomText, nomField);
        nomBox.setAlignment(Pos.CENTER);
        emailBox.getChildren().addAll(emailText, emailField);
        emailBox.setAlignment(Pos.CENTER);
        matriculeBox.getChildren().addAll(matriculeText, matriculeField);
        matriculeBox.setAlignment(Pos.CENTER);

        Button submit = new Button("Envoyer");

        submit.setMinWidth(80);

        inscriptionInterface.getChildren().addAll(prenomBox, nomBox, emailBox, matriculeBox, submit);
        inscriptionInterface.setSpacing(10);

        // Course Interface

        TableView table = new TableView<Course>();

        TableColumn codeLabel = new TableColumn<Course, String>("Code");
        codeLabel.setCellValueFactory(new PropertyValueFactory<Course, String>("Code"));

        TableColumn courseLabel = new TableColumn<Course, String>("Cours");
        courseLabel.setCellValueFactory(new PropertyValueFactory<Course, String>("Name"));

        codeLabel.setPrefWidth(100);
        courseLabel.setPrefWidth(180);

        table.getColumns().addAll(codeLabel, courseLabel);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Course> courseFiltered = new ArrayList<>();

        courseFiltered.add(new Course("Programmation2", "IFT1025", "Hiver"));
        courseFiltered.add(new Course("Programmation1", "IFT1015", "Automne"));
        courseFiltered.add(new Course("Genie_Logiciel", "IFT2255", "Hiver"));
        courseFiltered.add(new Course("Architecture_des_ordinateurs", "IFT1227", "Automne"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnddddddddddddddddddddddddddddddddddddddddddes", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));
        courseFiltered.add(new Course("Base_de_donnees", "IFT2256", "Ete"));

        for (Course cours : courseFiltered) {
            table.getItems().add(cours);
        }


        table.setPrefHeight(30*11);
        table.setPrefWidth(120);

        Separator separator2 = new Separator();

//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(table);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        courseInterface.getChildren().addAll(table, separator2);
        courseInterface.setMargin(table, new Insets(0, 10, 10, 10));


        HBox buttonGroup = new HBox();

        ChoiceBox sessionsMenu = new ChoiceBox<String>();

        sessionsMenu.getItems().addAll("Hiver", "Ete", "Automne");

        sessionsMenu.setValue("Hiver");

        Button chargerButton = new Button("Charger");


        buttonGroup.getChildren().addAll(sessionsMenu, chargerButton);
        buttonGroup.setMargin(sessionsMenu, new Insets(20));
        buttonGroup.setMargin(chargerButton, new Insets(20));
        buttonGroup.setAlignment(Pos.CENTER);

        courseInterface.getChildren().add(buttonGroup);

        root.setBackground(new Background((new BackgroundFill(Color.rgb(248,247,225),
                CornerRadii.EMPTY, Insets.EMPTY))));

        root.getChildren().addAll(courseInterface, separator, inscriptionInterface);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
