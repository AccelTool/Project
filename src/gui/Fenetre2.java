package gui;

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


public class Fenetre2 {

    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;

    public Fenetre2(Stage stage, String name){


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
        AnchorPane anchorPane = new AnchorPane(hBox);
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

        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();

        tf1.setStyle("-fx-background-color: #fff")
        tf2.setStyle("-fx-background-color: #fff");
        tf3.setStyle("-fx-background-color: #fff");

        vbConnect.getChildren().add(tf1);
        vbConnect.getChildren().add(tf2);
        vbConnect.getChildren().add(tf3);

        /*
        Bouton btnConnect = new Bouton(50,200,"Connect");
        Button button = new Button("connect");
        button.setPrefSize(200,50);
        button.setStyle("-fx-text-fill: #fff; -fx-background-color: #6d44b8; -fx-font-weight: bold; -fx-cursor: pointer");
        //button.setStyle("-fx-translate-y: 50px; -fx-animated: true");
        button.toFront();
        Button create = new Button("create");

        create.setPrefSize(200,50);
        create.setStyle("-fx-text-fill: #fff; -fx-background-color: #6d44b8; -fx-font-weight: bold; -fx-cursor: pointer");
        //button.setStyle("-fx-translate-y: 50px; -fx-animated: true");
        create.toFront();



        TranslateTransition translate = new TranslateTransition();
        translate.setNode(button);
        translate.setDuration(Duration.seconds(1));
        translate.setByY(50);
        translate.play();

        vBox.getChildren().add(button);
        vBox.setSpacing(50);

        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-background-color: #eee; -fx-background-radius: 60%/10%");

        vBox1.getChildren().add(create);

        //vBox.getChildren().add(textField);
        //vBox.getChildren().add(create);

        TextField textField = new TextField("jlshv");

        Label label = new Label("skdmhuv");

        vBox.getChildren().add(vBox1);
        vBox1.getChildren().add(label);
        vBox1.getChildren().add(textField);

        vBox1.setPadding(new Insets(0,0,0,0));

        vBox1.setPrefSize(100,360);
        vBox1.setPadding(new Insets(50,50,50,50));

        hBox.setSpacing(250);
        //vBox.setPadding(new Insets(50,50,50,50));

        vBox.setStyle("-fx-background-radius: 20px; -fx-background-color: #50687D; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 50, 0.5, 0.0, 0.0)");

        hBox.getChildren().add(vBox);
        //hBox.getChildren().add(btnConnect.getButton());
        hBox.setAlignment(Pos.CENTER);
        btnConnect.getButton().toFront();

*/


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
