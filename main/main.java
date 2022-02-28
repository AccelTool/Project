package main;

public class main {
    public static void moyenne(float[] v) {
        double total = 0;
        for (int i = 0; i < v.length; i++) {
            total = total + v[i];
        }
        double moy = total / v.length;

        System.out.format("La moyenne est: %.2f", moy);
    }
    }
