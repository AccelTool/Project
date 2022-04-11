package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class InterfaceGraphique extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Fenetre2 fn = new Fenetre2(primaryStage,"AccelTool");
        primaryStage.setScene(fn.getScene());
        primaryStage.show();


    }
}
