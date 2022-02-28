package main.java;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Display the string.
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
