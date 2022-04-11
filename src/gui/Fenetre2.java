package gui;

import database.Database;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class Fenetre2 {

    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;
    private Database db = new Database();

    public Fenetre2(Stage stage, String name){

        // Création de la fenêtre
        width = 1200;
        height = 600;
        stage.initStyle(StageStyle.UNDECORATED); //Undecorated <=> borderless
        stage.setResizable(false);
        stage.setTitle(name);
        String url_logo = getClass().getResource("/resources/images/logo_acceltool.png").toExternalForm();
        Image im_logo = new Image(url_logo);
        stage.getIcons().add(im_logo);

        Image rim_logo = new Image(url_logo, 500, 500, false, false);
        ImageView iVlogo = new ImageView();
        iVlogo.setImage(rim_logo);

        HBox hBox = new HBox();
        hBox.getChildren().add(iVlogo);
        hBox.setSpacing(250);
        hBox.setPadding(new Insets(50,50,70,50));
        hBox.setAlignment(Pos.CENTER);
        //hBox.setMouseTransparent(true);

        // anchorPane correspond au "root"
        AnchorPane anchorPane = new AnchorPane();
        drag(anchorPane, stage);
        scene = new Scene(anchorPane, width, height);

        anchorPane.getChildren().add(new Pane(hBox));
        anchorPane.setBottomAnchor(hBox, 0.0);
        anchorPane.setStyle("-fx-background: linear-gradient(to bottom, #616E7A, #B8D0E6, #8091A0)");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: linear-gradient(to bottom, #354654, #50687D, #3F5263); -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 50, 0.5, 0.0, 0.0)");
        hBox.setMargin(vBox, new Insets(90,0,100,0));
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().add(vBox);

        VBox vbCreate = new VBox();
        vbCreate.setAlignment(Pos.CENTER);
        vbCreate.setPadding(new Insets(0,50,0,50));
        vbCreate.setPrefHeight(5);
        vbCreate.setStyle("-fx-background-color: #eee; -fx-background-radius: 60%/30%; -fx-translate-y: 22px");

        VBox vbConnect = new VBox();
        vbConnect.setAlignment(Pos.CENTER);
        vbConnect.setPadding(new Insets(0,50,0,50));
        vbConnect.setSpacing(20);
        vBox.setMargin(vbConnect, new Insets(0,0,0,0));

        Pane pBlanc = new Pane();
        pBlanc.setStyle("-fx-background-color: #eee; -fx-background-radius: 0 0 10px 10px");
        pBlanc.setPadding(new Insets(30,50,0,50));


        vBox.getChildren().addAll(vbConnect, vbCreate, pBlanc);

        Button bConnect = new Button("Connect");
        bConnect.setStyle("-fx-text-fill: #fff; -fx-font-size: 3.9em; -fx-font-weight: bold; -fx-background-color: transparent; -fx-cursor: hand");
        vbConnect.getChildren().add(bConnect);
        vbConnect.setMargin(bConnect, new Insets(10,0,0,0));
        //vbConnect.setStyle("-fx-background-color: red");

        Button bCreate = new Button("Create");
        bCreate.setStyle("-fx-text-fill: #50687D; -fx-font-size: 2.3em; -fx-font-weight: bold; -fx-background-color: transparent; -fx-cursor: hand");
        vbCreate.getChildren().add(bCreate);
        //when pressing the create button, it will create the database and the tables, then open a new window
        bCreate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    db.createTcpServer("8082");
                    System.out.println("Serveur créé");
                    db.openDBConnection();
                    System.out.println("Connexion à la base de données établie");
                    db.createDB();
                    System.out.println("Base de données initialisée");

                    Stage stage2 = new Stage();
                    stage2.setTitle("AccelTool");
                    stage2.setScene(new Fenetre3(stage2, "AccelTool").getScene());
                    stage2.show();
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de la création de la base de données");
                }
            }
        });

        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();

        tf1.setPromptText("URL");
        tf2.setPromptText("User name");
        tf3.setPromptText("Password");

        tf1.setStyle("-fx-background-color: #fff");
        tf2.setStyle("-fx-background-color: #fff");
        tf3.setStyle("-fx-background-color: #fff");

        vbConnect.getChildren().add(tf1);
        vbConnect.getChildren().add(tf2);
        vbConnect.getChildren().add(tf3);


        bConnect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String url = "jdbc:h2:tcp://" + tf1.getText() + "/~/database";
                String user = tf2.getText();
                String password = tf3.getText();

                System.out.println("Cette fonction n'a pas encore été implémentée pour l'instant");
                System.out.println("Vous pouvez vous connecter en local avec le bouton \"Create\"");
                Stage popUp = new Stage();
                popUp.setTitle("Attention");
                popUp.setScene(new Scene(new Label(" Cette fonctionnalité n'est pas encore implémentée,\n vous pouvez vous connecter en local avec le bouton \"Create\"")));
                popUp.setWidth(350);
                popUp.setHeight(100);
                popUp.getIcons().add(new Image("https://www.pngfind.com/pngs/m/47-478974_computer-icons-warning-sign-windows-10-download-warning.png"));
                popUp.show();

                /* WORK IN PROGRESS | NOT WORKING | La connexion n'est pas possible avec une ip (autre que localhost)
                   Nous avons essayé avec différents formats d'url, mais aucune solution n'a été trouvée
                try {
                    System.out.println("Connexion à la base de données à l'adresse "+url);
                    db.openDBConnection(url, user, password);
                    System.out.println("Connexion réussie");
                    Stage stage2 = new Stage();
                    stage2.setTitle("AccelTool");
                    stage2.setScene(new Fenetre3(stage2, "AccelTool").getScene());
                    stage2.show();
                    stage.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Connexion échouée");
                }
                */
            }
        });

        //add a button to close the window
        Button bClose = new Button("x");
        bClose.setStyle("-fx-text-fill: #fff; -fx-font-size: 2.3em; -fx-background-color: transparent; -fx-cursor: hand");
        bClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
                //check if database is open
                if (db.isOpen()) {
                    db.closeDBConnection();
                    db.closeServer();
                }
                if (db.isRunning()) {
                    db.closeServer();
                }
            }});
        bClose.setTranslateX(width-50);
        bClose.setTranslateY(0);
        anchorPane.getChildren().add(bClose);

        //add a button to minimize the window
        Button bMinimize = new Button("-");
        bMinimize.setStyle("-fx-text-fill: #fff; -fx-font-size: 2.3em; -fx-font-weight: bold; -fx-background-color: transparent; -fx-cursor: hand");
        bMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setIconified(true);
            }
        });
        bMinimize.setTranslateX(width-100);
        bMinimize.setTranslateY(0);
        anchorPane.getChildren().add(bMinimize);







    }

    public Scene getScene() {
        return scene;
    }

    private void drag(AnchorPane anchorPane, Stage stage){
        // Les deux MouseEvent suivants permettent de déplacer la fenêtre car elle est borderless
        anchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }



}
