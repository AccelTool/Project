package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Fenetre {

    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private double width;
    private double height;

    public Fenetre(Stage stage, String name){


        width = 1200;
        height = 600;
        stage.initStyle(StageStyle.UNDECORATED); //Undecorated <=> borderless
        stage.setResizable(false);
        stage.setTitle(name);
        String url_logo = getClass().getResource("/resources/images/logo_acceltool.png").toExternalForm();
        Image im_logo = new Image(url_logo);
        stage.getIcons().add(im_logo);

        Image rim_logo = new Image(url_logo, 500, 500, false, false);

        //L'image du background (bg)
        Canvas cv = new Canvas(width, height);
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSTEELBLUE);
        gc.fillRect(0,0,width,height);
        gc.drawImage(rim_logo,50,50);

        // anchorPane correspond au "root"
        AnchorPane anchorPane = new AnchorPane(cv);
        scene = new Scene(anchorPane, width, height);


        //Un Pane pour déplacer la fenêtre
        Pane drag = new Pane(cv);
        anchorPane.getChildren().add(drag);
        anchorPane.setTopAnchor(drag,0.0);
        drag.toFront();

        GridPane gridPane = new GridPane();

        Bouton btn = new Bouton(50,100);
        anchorPane.getChildren().add(btn.getButton());
        btn.getButton().toFront();
        anchorPane.setRightAnchor(btn.getButton(), 50.0);


        // Les deux MouseEvent suivants permettent de déplacer la fenêtre car elle est borderless
        drag.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        drag.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public Scene getScene() {
        return scene;
    }


}
