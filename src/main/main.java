package main;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!"); // Display the string.

        ApplicationB application=new ApplicationB();
        application.execute();
    }

    public static void moyenne(float[] coef) {
        double total = 0;
        for (int i = 0; i < coef.length; i++) {
            total = total + coef[i];
        }
        double moy = total / coef.length;

        System.out.format("La moyenne est: %.2f", moy);
    }
}