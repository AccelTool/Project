package gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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

        //L'image du background (bg)
        Canvas cv = new Canvas(800, 500);
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(Color.LIGHTSTEELBLUE);
        gc.fillRect(0,0,800,500);

        // anchorPane correspond au "root"
        AnchorPane anchorPane = new AnchorPane(cv);
        scene = new Scene(anchorPane, 800, 500);

    }

    public Scene getScene() {
        return scene;
    }


}
