package gui;


import javafx.scene.control.Button;

public class Bouton {
    Button button;

    public Bouton(double hauteur, double largeur, String text){
        button = new Button(text);
        button.setPrefSize(largeur, hauteur);
        button.setStyle("-fx-background-color: #50687d; -fx-color: #fff");
    }

    public Button getButton() {
        return button;
    }

    public void closeButton(){
        button.setStyle("");
    }

    public void reduceButton(){
        button.setStyle("");
    }

    public void loginButton(){
        button.setStyle("");
    }

    public void createButton(){
        button.setStyle("");
    }

}


