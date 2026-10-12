#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <float.h>

#define MAX 1000

static int numerosClientes;
static int numeroLocais;
static int p;
static double distancia[MAX][MAX];

typedef struct {
    int locais[MAX];
    int tamanho;
} Lista;

int getNumerosClientes() {
    return numerosClientes;
}

void setNumerosClientes(int valor) {
    numerosClientes = valor;
}

int getNumeroLocais() {
    return numeroLocais;
}

void setNumeroLocais(int valor) {
    numeroLocais = valor;
}

int getP() {
    return p;
}

void setP(int valor) {
    p = valor;
}

void setDistancia(double matriz[MAX][MAX]) {
    for (int i = 0; i < numerosClientes; i++) {
        for (int j = 0; j < numeroLocais; j++) {
            distancia[i][j] = matriz[i][j];
        }
    }
}

bool contem(Lista lista, int valor) {
    for (int i = 0; i < lista.tamanho; i++) {
        if (lista.locais[i] == valor) {
            return true;
        }
    }
    return false;
}

double funcaoCusto(Lista locaisAbertos) {
    double total = 0;

    for (int i = 0; i < numerosClientes; i++) {
        double menor = DBL_MAX;

        for (int k = 0; k < locaisAbertos.tamanho; k++) {
            int local = locaisAbertos.locais[k];

            if (distancia[i][local] < menor) {
                menor = distancia[i][local];
            }
        }

        total += menor;
    }

    return total;
}

void embaralhar(int vetor[], int tamanho) {
    for (int i = tamanho - 1; i > 0; i--) {
        int j = rand() % (i + 1);

        int temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }
}

Lista funcaoConstruirAleatoria(int tamAmostra) {
    Lista locaisAbertos = {.tamanho = 0};

    for (int i = 0; i < p; i++) {
        int locaisFechados[MAX];
        int tamanhoFechados = 0;

        double melhorCusto = DBL_MAX;
        int melhorLocal = -1;

        for (int j = 0; j < numeroLocais; j++) {
            if (!contem(locaisAbertos, j)) {
                locaisFechados[tamanhoFechados++] = j;
            }
        }

        embaralhar(locaisFechados, tamanhoFechados);

        int tamanho = tamAmostra < tamanhoFechados
                      ? tamAmostra
                      : tamanhoFechados;

        for (int k = 0; k < tamanho; k++) {
            Lista temp = locaisAbertos;
            int candidato = locaisFechados[k];

            temp.locais[temp.tamanho++] = candidato;

            double custo = funcaoCusto(temp);

            if (custo < melhorCusto) {
                melhorCusto = custo;
                melhorLocal = candidato;
            }
        }

        if (melhorLocal != -1) {
            locaisAbertos.locais[locaisAbertos.tamanho++] = melhorLocal;
        }
    }

    return locaisAbertos;
}

Lista buscaLocalNova(Lista solucaoInicial) {
    double custoAtual = funcaoCusto(solucaoInicial);
    bool estado = true;

    while (estado) {
        estado = false;

        for (int i = 0; i < solucaoInicial.tamanho; i++) {
            int localI = solucaoInicial.locais[i];

            for (int j = 0; j < numeroLocais; j++) {
                if (contem(solucaoInicial, j)) {
                    continue;
                }

                Lista listaAtual = solucaoInicial;

                for (int k = 0; k < listaAtual.tamanho; k++) {
                    if (listaAtual.locais[k] == localI) {
                        for (int m = k; m < listaAtual.tamanho - 1; m++) {
                            listaAtual.locais[m] = listaAtual.locais[m + 1];
                        }

                        listaAtual.tamanho--;
                        break;
                    }
                }

                listaAtual.locais[listaAtual.tamanho++] = j;

                double custo = funcaoCusto(listaAtual);

                if (custo < custoAtual) {
                    custoAtual = custo;
                    estado = true;
                    solucaoInicial = listaAtual;
                    goto sairDoBreakDescobri;
                }
            }
        }

        sairDoBreakDescobri:
        ;
    }

    return solucaoInicial;
}

Lista grasp(int iteracoes, int tamAmostra) {
    Lista melhorSolucao = {.tamanho = 0};
    double melhorCusto = DBL_MAX;

    for (int i = 0; i < iteracoes; i++) {
        Lista aleatoria = funcaoConstruirAleatoria(tamAmostra);

        Lista solucaoMelhorada = buscaLocalNova(aleatoria);
        double custoAtual = funcaoCusto(solucaoMelhorada);

        if (custoAtual < melhorCusto) {
            melhorCusto = custoAtual;
            melhorSolucao = solucaoMelhorada;
        }
    }

    return melhorSolucao;
}

void imprimirLista(Lista lista) {
    printf("[");

    for (int i = 0; i < lista.tamanho; i++) {
        printf("%d", lista.locais[i]);

        if (i < lista.tamanho - 1) {
            printf(", ");
        }
    }

    printf("]");
}

int main() {
    double solucoes[MAX][MAX] = {
        {0, 7, 12},
        {1, 7, 12},
        {1, 4, 9}
    };

    setP(3);
    setNumerosClientes(3);
    setNumeroLocais(3);
    setDistancia(solucoes);

    srand(42);

    printf("Função Gulosa: ");
    Lista gulosa = funcaoConstruirAleatoria(3);
    imprimirLista(gulosa);
    printf("\n");

    printf("Grasp: ");
    Lista resultado = grasp(30, 3);
    imprimirLista(resultado);
    printf("\n");

    return 0;
}
