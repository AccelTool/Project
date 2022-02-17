package main;

public class main {
    public static void moyenne(float[] coef) {
        double total = 0;
        for (int i = 0; i < coef.length; i++) {
            total = total + coef[i];
        }
        double moy = total / coef.length;

        System.out.format("La moyenne est: %.2f", moy);
    }
    }
