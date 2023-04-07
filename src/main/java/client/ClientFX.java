package client;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClientFX extends Application {
    public static void main(String[] args) {
        ClientFX.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene = new Scene(root, 600, 400);

//        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
//                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//                + "-fx-border-radius: 5;" + "-fx-border-color: white;");

        VBox courseInterface = new VBox();
        VBox inscriptionInterface = new VBox();
        Separator separator = new Separator();


        Text coursTitle = new Text("Liste des cours");
        coursTitle.setFont(Font.font("serif", 25));
        Text inscriptionTitle = new Text("Formulaire d'inscription");
        inscriptionTitle.setFont(Font.font("serif", 25));


        separator.setOrientation(Orientation.VERTICAL);

        courseInterface.setMinWidth(300);
        inscriptionInterface.setMinWidth(300);

        courseInterface.getChildren().add(coursTitle);
        courseInterface.setAlignment(Pos.TOP_CENTER);

        inscriptionInterface.getChildren().add(inscriptionTitle);
        inscriptionInterface.setAlignment(Pos.TOP_CENTER);



        HBox prenomBox = new HBox();
        HBox nomBox = new HBox();
        HBox emailBox = new HBox();
        HBox matriculeBox = new HBox();

        TextField prenomField = new TextField();
        TextField nomField = new TextField();
        TextField emailField = new TextField();
        TextField matriculeField = new TextField();

        Text prenomText = new Text("Prénom\t\t");
        Text nomText = new Text("Nom\t\t");
        Text emailText = new Text("Email\t\t");
        Text matriculeText = new Text("Matricule\t\t");

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




//        courseInterface.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
//                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//                + "-fx-border-radius: 5;" + "-fx-border-color: white;");
//        inscriptionInterface.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
//                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//                + "-fx-border-radius: 5;" + "-fx-border-color: white;");

        root.setBackground(new Background((new BackgroundFill(Color.rgb(248,247,225), CornerRadii.EMPTY, Insets.EMPTY))));

        root.getChildren().addAll(courseInterface, separator, inscriptionInterface);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
