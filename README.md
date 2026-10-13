Problema das P-Medianas — GRASP
Sobre o projeto

Este projeto implementa uma abordagem heurística baseada no algoritmo GRASP (Greedy Randomized Adaptive Search Procedure) para o problema das p-medianas.

O objetivo é selecionar instalações de modo a minimizar a soma das distâncias entre cada cliente e a instalação aberta mais próxima.

Funcionamento

O algoritmo é composto pelas seguintes etapas:

Construção aleatorizada: seleciona instalações candidatas e constrói uma solução inicial.
Cálculo do custo: soma as menores distâncias entre os clientes e as instalações abertas.
Busca local: tenta melhorar a solução substituindo instalações abertas por instalações fechadas.
GRASP: repete o processo e mantém a melhor solução encontrada.
Instância utilizada
Número de clientes: 3
Número de instalações candidatas: 3
Número de instalações selecionadas (𝑝): 3
Número de iterações: 30
Tamanho da amostra: 3
Semente aleatória: 42
Relatório:
O relatório apresenta os cálculos dos custos das soluções, a análise das frequências das instalações e dos pares e a utilização dessas informações na construção de novas soluções.

Consultar o relatório em LaTeX
Tecnologias
Linguagem C
Linguagem Java
LaTeX
Overleaf
Carlos Vinicius Fialho Rabelo
