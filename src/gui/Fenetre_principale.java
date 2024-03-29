package gui;

import database.Database;

import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.jfr.DataAmount;
import main.Application;


import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

public class Fenetre_principale {

    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;

    private Database db;

    public double coeff_frict = 999.99;

    private ArrayList<CanvasLineChart> charts = new ArrayList<>();

    private double t = 0.0;
    private int id = 1;



    Application application;

    public Fenetre_principale(Stage stage, String name, Database db){

        this.db = db;
        application = new Application(db);

        width = 1200;
        height = 600;
        stage.initStyle(StageStyle.UNDECORATED); //Undecorated <=> borderless
        stage.setResizable(false);
        stage.setTitle(name);
        String url_logo = getClass().getResource("/resources/images/logo_acceltool.png").toExternalForm();
        Image im_logo = new Image(url_logo);
        stage.getIcons().add(im_logo);

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(25,50,50,50));
        vBox.setAlignment(Pos.BOTTOM_CENTER);

        // anchorPane correspond au "root"
        AnchorPane anchorPane = new AnchorPane();
        drag(anchorPane, stage);
        scene = new Scene(anchorPane, width, height);

        anchorPane.getChildren().add(vBox);
        anchorPane.setBottomAnchor(vBox, 0.0);
        anchorPane.setStyle("-fx-background-color: linear-gradient(to bottom, #616E7A, #B8D0E6, #8091A0)");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox .setSpacing(150);
        //hBox.setStyle("-fx-background-color: #f00");

        VBox vbIndicator = new VBox();
        vbIndicator.setAlignment(Pos.CENTER);
        vbIndicator.setSpacing(70);

        Label lCoefFrict = new Label("Friction Coefficient :\n" + coeff_frict);
        lCoefFrict.setPrefSize(220,70);
        lCoefFrict.setStyle("-fx-text-fill: #354654; -fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: #fff; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.5, 0.0, 0.0); -fx-text-alignment: center");
        lCoefFrict.setAlignment(Pos.CENTER);

        Button bIndicator = new Button();
        bIndicator.setStyle("-fx-background-color: transparent");
        imageIndicator(bIndicator);
        bIndicator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageIndicator(bIndicator);
            }
        });

        Button bInitialise = new Button("Initialize database");
        bInitialise.setStyle("-fx-text-fill: #fff; -fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: #354654; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.5, 0.0, 0.0); -fx-cursor: hand");
        bInitialise.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    application.initializeDB(db);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //add a button to creat tables
        Button bCreateTables = new Button("Create tables");
        bCreateTables.setStyle("-fx-text-fill: #fff; -fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: #354654; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.5, 0.0, 0.0); -fx-cursor: hand");
        bCreateTables.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                application.createTables(db);
            }
        });



        vbIndicator.getChildren().addAll(lCoefFrict, bIndicator);

        VBox vbAffichage = new VBox();
        vbAffichage.setSpacing(25);

        Pane pCanvas = new Pane();

        Canvas canvas = new Canvas(700, 200);
        GraphicsContext g = canvas.getGraphicsContext2D();

        clear(g);

        pCanvas.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 200px 700px, #cce0ff, #eee); -fx-background-radius: 10px; -fx-opacity: 0.5");
        pCanvas.setPadding(new Insets(0));

        pCanvas.getChildren().add(canvas);


        charts.add(new CanvasLineChart(g, Color.rgb(0, 0, 255)));
        charts.add(new CanvasLineChart(g, Color.rgb(255, 0, 0)));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                t += 0.016;

                if (t > 1){
                    clear(g);
                    try {
                        boolean bool = application.startSimulation(db, "inv_one_line.txt","out_one_line.txt", charts, id);
                        id++;

                        coeff_frict = charts.get(0).getLast();
                        lCoefFrict.setText("Friction Coefficient :\n" + coeff_frict);
                        imageIndicator(bIndicator);

                        if (bool){
                            this.stop();
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    t = 0.0;
                }

            }
        };

        Pane pBlanc = new Pane();
        pBlanc.setPrefSize(700,100);
        pBlanc.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 200px 700px, #cce0ff, #eee); -fx-background-radius: 10px; -fx-opacity: 0.5");
        pBlanc.setPadding(new Insets(100,200,0,50));

        HBox hbLabel = new HBox();
        hbLabel.setPadding(new Insets(40, 0, 0,150));

        Label lLegCoef = new Label("Friction Coefficient");
        Label lLegRolling = new Label("Rolling Torque");

        lLegCoef.setStyle("-fx-text-fill: #00f; -fx-font-size: 1.5em; -fx-font-weight: bold;");
        lLegRolling.setStyle("-fx-text-fill: #f00; -fx-font-size: 1.5em; -fx-font-weight: bold;");


        hbLabel.getChildren().addAll(lLegCoef, lLegRolling);

        hbLabel.setSpacing(100);

        pBlanc.getChildren().add(hbLabel);

        Button bUpdate = new Button("Update Database");
        bUpdate.setStyle("-fx-text-fill: #fff; -fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: #354654; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.5, 0.0, 0.0); -fx-cursor: hand");

        HBox hbButtonDB = new HBox();
        hbButtonDB.setSpacing(15);

        hbButtonDB.getChildren().addAll(bUpdate, bInitialise, bCreateTables);

        vbAffichage.getChildren().addAll(pCanvas, pBlanc, hbButtonDB);

        hBox.getChildren().addAll(vbAffichage, vbIndicator);


        Button bStart = new Button("Start Simulation");
        bStart.setStyle("-fx-text-fill: #fff; -fx-font-size: 3.9em; -fx-font-weight: bold; -fx-background-color: #354654; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.5, 0.0, 0.0); -fx-cursor: hand");
        bStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clear(g);
                //get the data from the database db using db.recupereEntree()
                id = 1;
                timer.start();

            }
        });

        vBox.getChildren().addAll(hBox, bStart);

        Button bClose = new Button("x");
        bClose.setStyle("-fx-text-fill: #fff; -fx-font-size: 2.3em; -fx-background-color: transparent; -fx-cursor: hand");
        bClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }     });
        bClose.setTranslateX(width-50);
        bClose.setTranslateY(0);
        anchorPane.getChildren().add(bClose);

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

    private void imageIndicator(Button bIndicator){

        if (coeff_frict < 0.14){
            String url_rouge = getClass().getResource("/resources/images/indicateur_rouge.png").toExternalForm();
            Image iIndic_rouge = new Image(url_rouge);
            ImageView iVIndic_rouge = new ImageView();
            iVIndic_rouge.setImage(iIndic_rouge);
            bIndicator.setGraphic(iVIndic_rouge);
        } else if (coeff_frict < 0.16) {
            String url_jaune = getClass().getResource("/resources/images/indicateur_jaune.png").toExternalForm();
            Image iIndic_jaune = new Image(url_jaune);
            ImageView iVIndic_jaune = new ImageView();
            iVIndic_jaune.setImage(iIndic_jaune);
            bIndicator.setGraphic(iVIndic_jaune);
        } else {
            String url_vert = getClass().getResource("/resources/images/indicateur_vert.png").toExternalForm();
            Image iIndic_vert = new Image(url_vert);
            ImageView iVIndic_vert = new ImageView();
            iVIndic_vert.setImage(iIndic_vert);
            bIndicator.setGraphic(iVIndic_vert);
        }

        bIndicator.setPrefSize(50,50);

    }

    private void clear(GraphicsContext g){
        g.clearRect(0, 0, 700, 200);
        g.setStroke(Color.BLACK);

        g.strokeLine(14, 10, 14, 188);
        g.strokeLine(13, 10, 13, 188);
        g.strokeLine(12, 10, 12, 188);

        g.strokeLine(12, 186, 690, 186);
        g.strokeLine(12, 187, 690, 187);
        g.strokeLine(12, 188, 690, 188);

        g.setFill(Color.RED);
        g.setFont(new Font("Arial Black", 15));
        g.fillText("100 000", 15,15);

        g.setFill(Color.BLUE);
        g.setFont(new Font("Arial Black", 15));
        g.fillText("1", 0,15);

        g.setFill(Color.BLACK);
        g.setFont(new Font("Arial Black", 15));
        g.fillText("0", 0,200);
        g.fillText("t", 685,200);
    }

}

