package heuristica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static List<Integer> paraAsPosicoes(List<Integer> indices, int[] posicao) {
        List<Integer> resultado = new ArrayList<>();
        for (int index : indices) {
            resultado.add(posicao[index]);
        }
        return resultado;
    }
    public static void main(String[] args) {
        int[] posicoes = {0, 1, 4, 7, 9, 12};
        int n = posicoes.length;
        double[][] distancia = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distancia[i][j] = Math.abs(posicoes[i] - posicoes[j]);
            }
        }

        PMedianasGRASP.setP(3);
        PMedianasGRASP.setNumerosClientes(n);
        PMedianasGRASP.setNumeroLocais(n);
        PMedianasGRASP.setDistancia(distancia);
        Random random = new Random(42);

        List<Integer> construcao = PMedianasGRASP.funcaoConstruirAleatoria(3, random);

        System.out.println("Construir Aleatória: " + paraAsPosicoes(construcao, posicoes));
        System.out.println("Custo: " + PMedianasGRASP.funcaoCusto(construcao));

        List<Integer> grasp = PMedianasGRASP.grasp(30, 3, random);
        System.out.println("GRASP: " + paraAsPosicoes(grasp, posicoes));
        System.out.println("Custo: " + PMedianasGRASP.funcaoCusto(grasp));
    }
}
