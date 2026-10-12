package heuristica;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        double[][] solucoes = {
                {0, 7, 12},
                {1, 7, 12},
                {1, 4, 9}
        };

        PMedianas.setP(3);
        PMedianas.setNumerosClientes(3);
        PMedianas.setNumeroLocais(3);
        PMedianas.setDistancia(solucoes);
        Random random = new Random(42);

        System.out.println("Função Gulosa: " + PMedianasGRASP.funcaoConstruirAleatoria(3, random));
        System.out.println("Grasp: " + PMedianasGRASP.grasp(30, 3, random));
    }
}
