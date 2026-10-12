package heuristica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PMedianasGRASP {
    private static int numerosClientes;
    private static int numeroLocais;
    private static int p;
    private static double[][] distancia;

    public static int getNumerosClientes() {
        return numerosClientes;
    }

    public static void setNumerosClientes(int numerosClientes) {
        PMedianasGRASP.numerosClientes = numerosClientes;
    }

    public static int getNumeroLocais() {
        return numeroLocais;
    }

    public static void setNumeroLocais(int numeroLocais) {
        PMedianasGRASP.numeroLocais = numeroLocais;
    }

    public static int getP() {
        return p;
    }

    public static void setP(int p) {
        PMedianasGRASP.p = p;
    }

    public static double[][] getDistancia() {
        return distancia;
    }

    public static void setDistancia(double[][] distancia) {
        PMedianasGRASP.distancia = distancia;
    }

    public static double funcaoCusto(List<Integer> locaisAbertos) {
        double total = 0;
        for (int i = 0; i < numerosClientes; i++) {
            double menor = Double.MAX_VALUE;
            for (int k : locaisAbertos) {
                if (distancia[i][k] < menor) {
                    menor = distancia[i][k];
                }
            }
            total += menor;
        }
        return total;
    }

    public static List<Integer> funcaoConstruirAleatoria(int tamAmostra, Random random) {
        List<Integer> locaisAbertos = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            List<Integer> locaisFechados = new ArrayList<>();
            double melhorCusto = Double.MAX_VALUE;
            int melhorLocal = -1;
            for (int j = 0; j < numeroLocais; j++) {
                if (!locaisAbertos.contains(j)) {
                    locaisFechados.add(j);
                }
            }
            Collections.shuffle(locaisFechados, random);
            int tamanho = Math.min(tamAmostra, locaisFechados.size());//Vou assumir que tamAmostra vai ser <= 1
            for (int k = 0; k < tamanho; k++) {
                List<Integer> temp = new ArrayList<>(locaisAbertos);
                Integer canditado = locaisFechados.get(k);
                temp.add(canditado);
                double custo = funcaoCusto(temp);
                if (custo < melhorCusto) {
                    melhorCusto = custo;
                    melhorLocal = canditado;
                }
            }
            locaisAbertos.add(melhorLocal);
        }
        return locaisAbertos;
    }

    public static List<Integer> buscaLocalNova(List<Integer> solucaoInicial) {
        double custoAtual = funcaoCusto(solucaoInicial);
        boolean estado = true;

        while (estado) {
            estado = false;
            sairDoBreakDescobri:
            for (int i = 0; i < solucaoInicial.size(); i++) {
                Integer localI = solucaoInicial.get(i);
                for (int j = 0; j < numeroLocais; j++) {
                    if (solucaoInicial.contains(j)) {
                        continue;
                    }
                    List<Integer> listaAtual = new ArrayList<>(solucaoInicial);
                    listaAtual.remove(localI);
                    listaAtual.add(j);
                    double custo = funcaoCusto(listaAtual);
                    if (custo < custoAtual) {
                        custoAtual = custo;
                        estado = true;
                        solucaoInicial = listaAtual;
                        break sairDoBreakDescobri;
                    }
                }
            }
        }
        return solucaoInicial;
    }

    public static List<Integer> grasp(int iteracoes, int tamAmostra, Random random) {
        List<Integer> melhorSolucao = null;
        double melhorCusto = Double.MAX_VALUE;

        for (int i = 0; i < iteracoes; i++) {
            List<Integer> aleatoria = funcaoConstruirAleatoria(tamAmostra, random);

            List<Integer> solucaoMelhorada = buscaLocalNova(aleatoria);
            double custoAtual = funcaoCusto(solucaoMelhorada);

            if (custoAtual < melhorCusto) {
                melhorCusto = custoAtual;
                melhorSolucao = solucaoMelhorada;
            }
        }
        return melhorSolucao;
    }
}
