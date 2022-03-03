package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Fenetre {

    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    public Fenetre(Stage stage, String name){

        stage.initStyle(StageStyle.UNDECORATED); //Undecorated <=> borderless
        stage.setResizable(false);
        stage.setTitle(name);
        stage.getIcons().add(new Image(getClass().getResource("/resources/images/logo_acceltool.png").toExternalForm()));

        //L'image du background (bg)
        Canvas cv = new Canvas(800, 500);
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSTEELBLUE);
        gc.fillRect(0,0,800,500);

        // anchorPane correspond au "root"
        AnchorPane anchorPane = new AnchorPane(cv);
        scene = new Scene(anchorPane, 800, 500);


        //Un Pane pour déplacer la fenêtre
        Pane drag = new Pane(cv);
        anchorPane.getChildren().add(drag);
        anchorPane.setTopAnchor(drag,0.0);
        drag.toFront();

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
