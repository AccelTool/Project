package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Fenetre3 {


    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;



    public Fenetre3(Stage stage, String name) {
        //create a window similar to Fenetre2
        //but with a different scene
        //and a different title
        width = 1200;
        height = 600;

        AnchorPane anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, width, height);
        stage.initStyle(StageStyle.UNDECORATED); //Undecorated <=> borderless
        stage.setResizable(false);
        stage.setTitle(name);
        String url_logo = getClass().getResource("/resources/images/logo_acceltool.png").toExternalForm();
        Image im_logo = new Image(url_logo);
        stage.getIcons().add(im_logo);
        drag(anchorPane, stage);

        anchorPane.setStyle("-fx-background: linear-gradient(to bottom, #616E7A, #B8D0E6, #8091A0)");


        //add a button to close the window
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
