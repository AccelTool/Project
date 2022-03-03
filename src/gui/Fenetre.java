package gui;

import javafx.scene.Scene;
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



    }
}
