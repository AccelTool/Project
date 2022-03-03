package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class InterfaceGraphique extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Fenetre fn = new Fenetre(primaryStage,"AccelTool");
        primaryStage.setScene(fn.getScene());
        primaryStage.show();
    }
}
