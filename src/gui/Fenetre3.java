package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
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
        width = stage.getWidth();
        height = stage.getHeight();
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
        anchorPane.setStyle("-fx-background-radius: 10;");
        anchorPane.setStyle("-fx-background-insets: 0,1,2,3,0;");

        stage.setScene(scene);
        stage.show();
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
