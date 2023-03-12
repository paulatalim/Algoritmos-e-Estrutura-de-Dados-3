#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

/*******************
 * FUNCIONALIDADES *
********************/
/*
Descricao: esse procedimento prepara o
programa para sua tela ser apagada
*/
void reiniciar_programa () {
    printf ("\n\nPressione 'enter' para continuar");
    getchar ();
    system ("cls");
}

/*
Descricao: esse procedimento exibe os
elementos de uma matriz de reais
Parametros: dois valores inteiros
(dimensoes da matriz), uma matriz de reais
*/
void mostrar_matriz (int M, int N, float max [M][N]) {
    printf ("Numeros na Matriz:\n");
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            printf ("%.02f ", max[i][j]);
        }
        printf ("\n");
    }

    //Quebra de linha para deixar a saida mais organizada
    printf ("\n");
}

/***************
 * EXERCICIO 1 *
****************/
/*
Descricao: esse procedimento define
as dimensoes para uma matriz

Parametros: dois ponteiros de valores
inteiros (as dimensoes da matriz)
*/
void dimensao_matriz (int *M, int *N) {
    bool passou_primeiro_laco = false;
    //Valida a resposta
    do {
        //Titulo da pagina
        printf ("* MODELANDO A MATRIZ *\n\n\n");

        //Informa em qual etapa do processo o usuario esta
        printf ("* Etapa 1: Definindo as dimensoes da matriz *\n\n");

        //Quando a resposta do usuario ser invalida
        if (passou_primeiro_laco) {
            printf ("Resposta invalida, tente novamente\n");
        }

        //Informando o tamnho da matriz
        printf ("Informe as dimensoes da matriz: ");
        scanf ("%d %d", &*M, &*N);
        fflush (stdin);

        //Quando o primeiro laco passar o booleano fica verdadeiro
        passou_primeiro_laco = true;

        system ("cls");
    } while (*N == 1 && *M == 1);
}

/*
Descricao: esse procedimento preenche uma matriz,
na qual pode ser preenchida com o numeros informados
pelo usuario ou com numeros aleatorios, tal decisao de
preenchimento tambem eh informada pelo usuario

Parametros: dois valores inteiros (dimensoes da matriz) e
uma matriz que armazena valores reais (matriz a ser preenchida)
*/
void preenchimento_de_matriz (int M, int N, float max[M][N]) {
    int opcao, i, j;

    //Onde vai ser gerado os numeros aleatorios
    srand (time(NULL));

    //Valida resposta do usuario
    do {
        system ("cls");

        //Titulo da pagina
        printf ("* MODELANDO A MATRIZ *\n\n");

        //Informa as informacoes ja dadas
        printf ("Informacoes cadastradas da matriz:\n"
                "Dimensoes: %d x %d\n\n\n", M, N);

        //Informa em qual etapa do processo o usuario esta
        printf ("* Etapa 2: Selecao da forma de preenchimento da matriz *\n\n");

        //Escolha do preenchimento da matriz
        printf ("Escolha como deseja preencher a matriz, digitando: \n\n"
                "[0] - informando os numeros\n"
                "[1] - com numeros aleatorios\n\n"
                "Sua opcao: ");
        scanf ("%d", &opcao);
        fflush (stdin);

    } while (opcao < 0 || opcao > 1);

    system ("cls");

    //Titulo da pagina
    printf ("* MODELANDO A MATRIZ *\n\n");

    //Preenchimento da matriz
    if (opcao == 0) {

        //Informa as informacoes ja dadas
        printf ("Informacoes cadastradas da matriz:\n"
                "Dimensoes: %d x %d\n"
                "Opcao de preenchimento: com numeros do teclado\n\n\n", M, N);

        //Informa em qual etapa do processo o usuario esta
        printf ("* Etapa 3: Preenchendo a matriz *\n");

        //Preenchimento da matriz pelo usuario
        printf ("\nInsira um numero para a posicao\n");
        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                printf ("%d %d: ", i, j);
                scanf ("%f", &max[i][j]);
                fflush (stdin);
            }
        }

        system ("cls");

        //Titulo da pagina
        printf ("* MODELANDO A MATRIZ *\n\n");

        //Informa as informacoes ja dadas
        printf ("Informacoes cadastradas da matriz:\n"
                "Dimensoes: %d x %d\n"
                "Opcao de preenchimento: com numeros do teclado\n", M, N);

    } else {
        //Informa as informacoes ja dadas
        printf ("Informacoes cadastradas da matriz:\n"
                "Dimensoes: %d x %d\n"
                "Opcao de preenchimento: com numeros aleatorios\n", M, N);

        //Preenchimento da matriz com numeros randomicos
        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                max[i][j] = rand() % 10;
            }
        }
    }

    //Mostrar numeros da matriz
    mostrar_matriz (M, N, max);
}

/***************
 * EXERCICIO 2 *
 ***************/
/*
Descricao: Esse procedimento recebe
os indices de uma linha e uma coluna
de uma matriz e, a partir disso, calcula
e imprime a soma dos numeros dessa linha
e dessa coluna

Parametros: dois valores inteiros (o indice da
linha e o indice da coluna) e uma matriz
*/
void soma_linha_coluna (int linha, int coluna, int M, int N, float max[M][N]) {
    float soma = 0;

    //Soma dos numeros da linha
    for (int i = 0; i < N; i++) {
        soma += max[linha][i];
    }

    //Soma dos elementos da coluna
    for (int i = 0; i < M; i++) {
        soma += max[i][coluna];
    }

    //Exibe resultado da soma total dos valores da coluna e da linha
    printf ("Soma: %.02f", soma);
}

/***************
 * EXERCICIO 3 *
 ***************/
/*
Descricao: esse procedimento faz uma matriz
de reais, preenchendo-a com numeros informados
pelo usuario, e a compara com uma outra matriz
de reais, verificando se elas sao identicas ou nao

Parametros: dois valores inteiros (dimensoes da duas
matrizes a ser comparadas), uma matriz de reais
(matriz a ser comparada)
*/
void comparar_matrizes (int M, int N, float max[M][N]) {
    int i, j;
    float max2[M][N];
    bool ehIdentica = true;

    //Mostra a primeira matriz
    mostrar_matriz (M, N, max);

    //Instrucoes do usuario
    printf ("\nInstrucao: a seguir voce devera preencher uma\n"
            "segunda matriz de mesma dimensao da primeira\n\n");

    //Preenchimento da matriz
    printf ("Insira um numero para a posicao\n");
    for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            printf ("%d %d: ", i, j);
            scanf ("%f", &max2[i][j]);
            fflush (stdin);

            //Comparacao com a primeira matriz
            if (max[i][j] != max2[i][j]) {
                ehIdentica = false;
            }
        }
    }

    reiniciar_programa ();

    //Titulo da pagina
    printf ("* EXERCICIO 3 *\n\n");

    //Cabecalho da tabela
    printf ("Primeira matriz:\t"
            "Segunda matriz:\n");

    //Exibe as matrizes estudadas
    for (int i = 0; i < M; i++) {
        //Exibe a primeira matriz
        for (int j = 0; j < N; j++) {
            printf ("%.02f ", max[i][j]);
        }
        printf ("\t\t");

        //Exibe a segunda matriz
        for (int j = 0; j < N; j++) {
            printf ("%.02f ", max2[i][j]);
        }
        printf ("\n");
    }

    //Exibe se a segunda matriz eh identica ou nao a primeira matriz
    if (ehIdentica) {
        printf ("\nConclusao: As matrizes sao identicas");
    } else {
        printf ("\nConclusao: As matrizes nao sao identicas");
    }
}

/***************
 * EXERCICIO 6 *
 ***************/
 /*
Descricao: essa funcao calcula a media
de valores de uma funcao e retorna quantos
numeros na matriz estao acima dessa media

Parametros: dois valores inteiros (dimensoes
da matriz), matriz de reais (matriz a ser
verificada)

Retorno: um valor inteiro (quantidade de numeros
acima do valor medio da matriz)
*/
int valores_acima_da_media (int M, int N, float max[M][N]) {
    int cont, i, j;
    float media;

    media = 0;
    cont = 0;

    /*Calculo da soma de todos os numeros dentro
    da matriz para depois calcular sua media*/
    for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            media += max[i][j];
        }
    }

    //Calculo da media
    media /= (M * N);

    //Verifica quantos valoes da matriz estao acima da media
     for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            if (max[i][j] > media) {
                cont++;
            }
        }
    }

    //Retorno
    return cont;
}

/***************
 * EXERCICIO 7 *
 ***************/
 /*
Descricao: essa funcao verifica se a
primeira linha de uma matriz de reais
eh igual a sua diagonal principal.

Parametro: dois valores inteiros (dimensoes
da matriz) uma matriz de reais (matriz a
ser verificada)

Retorno: true (se a primeira linha da
matriz for igual à sua diagonal principal)
ou false (caso a primeira linha da matriz
for diferente de sua diagonal principal)
*/
bool comparar_primeiraLinha_diagonal (int M, int N, float max[M][N]) {
    bool ehIdentico = false;

    //Verifica se a matriz tem diagonal principal
    if (M == N) {
        //Verifica se os elementos da primeira linha eh igual a diagonal
        for (int i = 0; i < M; i++) {
            if (max[0][i] == max [i][i]) {
                ehIdentico = true;
            }
        }
    }

    return ehIdentico;
}

/***************
 * EXERCICIO 10 *
 ***************/
/*
Descricao: essa funcao calcula a soma
dos numeros de uma coluna de uma matriz
de reais
Parametros: tres valores inteiros
(dimensoes da matriz e a coluna na qual
se deseja fazer a soma), uma matriz de
reais
Retorno: um valor real (soma dos numeros
da coluna)
*/
float soma_coluna (int numC, int M, int N, float max[M][N]) {
    float soma = 0;

    for (int i = 0; i < M; i++) {
        soma += max [i][numC];
    }

    return soma;
}

/****************
 * EXERCICIO 13 *
 ***************/
/*
Descricao: esse procedimento cria uma matriz,
a multiplica por uma outra matriz e exibe os
resultados dessa multiplicacao

Parametros: dois valores inteiros (dimensao da
matriz), uma matriz de reais (a matriz que sera
multiplicada)
*/
void multiplicar_matriz (int M, int N, float max[M][N]) {
    int M1, N1, i, j, k;
    float soma = 0;

    //Instrucoes do usuario
    printf ("Instrucao: a seguir voce devera modelar uma segunda\n"
            "matriz para multiplica-la com a primeira matriz");

    reiniciar_programa ();

    //Define a dimensao da matriz
    dimensao_matriz (&M1, &N1);

    //Declacao da matriz
    float max2 [M1][N1];

    //Titulo da pagina
    printf ("* MODELANDO A MATRIZ *\n\n\n");

    printf ("* Etapa2: Preenchendo a matriz *\n\n");

    //Preenchimento da matriz
    printf ("\nInsira um numero para a posicao:\n");
    for (i = 0; i < M1; i++) {
        for (j = 0; j < N1; j++) {
            printf ("%d %d: ", i, j);
            scanf ("%f", &max2[i][j]);
            fflush (stdin);
        }
    }

    reiniciar_programa ();

    //Titulo da pagina
    printf ("* EXERCICIO 13 *\n\n");

    //Informa o que esta sendo exibido
    printf ("Primeira matriz:\n");

    //Exibe a primeira matriz
    mostrar_matriz (M, N, max);

    //Informa o que esta sendo exibido
    printf ("Segunda matriz:\n");

    //Exibe a segunda matriz
    mostrar_matriz (M1, N1, max2);

    //Verifica se a multiplicacao das matrizes eh possivel
    if (N == M1) {
        //Informa o que esta sendo exibido
        printf ("Resultado da multiplicacao:\n");

        //Calculo da multiplicacao das matrizes
        for (i = 0; i < M; i++) {
            for (k = 0; k < N1; k++) {
                for (j = 0; j < N; j ++) {
                    soma += (max[i][j] * max2[j][k]);
                }
                //Exibe o resultado da multiplicacao
                printf ("%.02f ", soma);
                soma = 0;
            }
            printf ("\n");
        }
    } else {
        //Quando a multiplicacao nao eh possivel
        printf ("Resultados: nao eh possivel calcular a multiplicacao dessas matrizes");
    }
}

/***************
 * EXERCICIO 14 *
 ***************/
/*
Descricao: essa funcao calcula e soma a
distancia de um ponto P com pontos de uma matriz

Parametros: tres valores inteiros (coordenadas x
e y do ponto P e a quantidade de pontos na matriz)
e uma matriz de reais

Retorno: um valor float (soma das distancias
entre os pontos)
*/
float distancia (float Px, float Py, int m, float M[m][2]) {
    float soma = 0;

    //Calculo das distancias
    for (int i = 0; i < m; i ++) {
        soma += abs(Px -  M[i][0]) + abs(Py - M[i][1]);
    }

    return soma;
}

/*
Descricao: Esse procedimento, apartir das coordendas
de um ponto P e a quantidade de pontos, constroi uma
matriz de reais com essa quantidade de pontos, sendo
cada ponto informado pelo usuario, posterioriormente,
e depois se calcula a soma das distancias entre os
pontos

Parametros: dois valores reais (coordenadas do ponto
P) e um valor inteiro (quantidade de pontos que matriz
ira armezenar)
*/
void soma_distancias_pontos_matriz (float Px, float Py, int m) {
    //Declaracao da matriz
    float max2 [m][2];

    //Instrucao para o usuario
    printf ("Informe os pontos no padrao (x, y):\n");

    //Preenchendo a matriz
    for (int i = 0; i < m; i++) {
        printf ("Ponto %d: ", i+1);
        scanf ("%f, %f", &max2[i][0], &max2[i][1]);
        fflush (stdin);
    }

    //Exibe o resultado da soma das distancias
    printf ("\nA soma das distancias ate o ponto P: %.02f", distancia(Px, Py, m, max2));
}

/**************
 * EXECUTAVEL *
 **************/
int main() {
    int opcao, M, N, linha, coluna, i, j, cont, m;
    float k, Px, Py;
    bool ehIdentico;

    //Titulo da pagina
    printf ("* LISTA 8 *\n\n");

    //Instrucoes do usuario
    printf ("Instrucao: antes de iniciar os exrcicios, modele a matriz que\n"
            "sera utilizada neles, apos esse processo, voce tambem podera\n"
            "remodelar a matriz novamente, caso desejar");

    reiniciar_programa ();

    //Modelagem da matriz
    do {
        //Titulo da pagina
        printf ("* MODELANDO A MATRIZ *\n\n");

        //Instrucoes do usuario
        printf ("A seguir voce ira modelar uma matriz de reias, \n"
                "o que eh um processo dividido em 3 etapas:\n\n"
                " - Etapa 1: Definir as dimensoes da matriz\n"
                " - Etapa 2: Selecionar a forma de preenchimento da matriz\n"
                " - Etapa 3: Preencher a matriz");

        reiniciar_programa ();
        //Define as dimensoes da matriz
        dimensao_matriz (&M, &N);

        //Declaracao da matriz
        float max[M][N];

        //Preenchimento da matriz
        preenchimento_de_matriz (M, N, max);

        reiniciar_programa ();

        //Repete o programa
        do {
            //Validação da resposta do usuário
            do {
                //Titulo da pagina
                printf ("*  APRENDENDO MATRIZ *\n\n");

                //Menu das opções
                printf ("Digite para executar:\n\n"
                        "[1]  - Remodelar a matriz\n"
                        "[2]  - Exercicio 2\n"
                        "[3]  - Exercicio 3\n"
                        "[4]  - Exercicio 4\n"
                        "[6]  - Exercicio 6\n"
                        "[7]  - Exercicio 7\n"
                        "[10] - Exercicio 10\n"
                        "[12] - Exercicio 12\n"
                        "[13] - Exercicio 13\n"
                        "[14] - Exercicio 14\n"
                        "[0] - Sair\n\n"
                        "Sua opcao: ");
                scanf ("%d", &opcao);
                fflush (stdin);
                system ("cls");

            } while (opcao < 0 || opcao > 14 || opcao == 5 || opcao == 8 || opcao == 9 || opcao == 11);

            //Execução dos exercicios
            switch (opcao) {
                //Exercicio 2
                case 2:
                    //Titulo da pagina
                    printf ("EXERCICIO 2\n\n");

                    //Mostrar numeros da matriz
                    mostrar_matriz (M, N, max);

                    //Valida a resposta
                    do {
                        //Entrada do indices da linha da matriz
                        printf ("Informe o indice da linha desejada: ");
                        scanf ("%d", &linha);
                        fflush (stdin);
                    } while (linha > M || linha < 0);

                    //Valida a resposta
                    do {
                        //Entrada do indices da coluna da matriz
                        printf ("Informe o indice da coluna desejada: ");
                        scanf ("%d", &coluna);
                        fflush (stdin);
                    } while (coluna > N || coluna < 0);


                    //Chamada do procedimento
                    soma_linha_coluna (linha, coluna, M, N, max);
                    break;

                //Exercicio 3
                case 3:
                    //Titulo da pagina
                    printf ("* EXERCICIO 3 *\n\n");

                    //Chamada do procedimento
                    comparar_matrizes (M, N, max);
                    break;

                //Exercicio 4
                case 4:
                    //Titulo da pagina
                    printf ("* EXERCICIO 4 *\n\n");

                    //Mostra a matriz que esta sendo utilizada no exercicio
                    mostrar_matriz (M, N, max);

                    //Entrada do numero k
                    printf ("informe um numero: ");
                    scanf ("%f", &k);
                    fflush (stdin);

                    //Passa por todos os numeros da matriz
                    for (i = 0; i < M; i ++) {
                        for (j = 0; j < N; j++) {

                            //Verifica se o numero da matriz eh igual ao numero k
                            if (max[i][j] == k) {

                                //Conta a quantidade de vezes que k apareceu dentro da matriz
                                cont++;
                            }
                        }
                    }

                    //Exibe a quantida de vezes que k apareceu dentro da matriz
                    printf ("O numero %.02f aparece %d vezes na matriz", k, cont);
                    break;

                //Exercicio 6
                case 6:
                    //Titulo da pagina
                    printf ("* EXERCICIO 6 *\n\n");

                    //Mostra a matriz que esta sendo utilizada no exercicio
                    mostrar_matriz (M, N, max);

                    //Exibe a quantidade de numeros que estao acima da media dos numeros da matriz
                    printf ("Quantidade de numeros acima da media: %d", valores_acima_da_media (M, N, max));
                    break;

                //Exercicio 7
                case 7:
                    //Titulo da pagina
                    printf ("* EXERCICIO 7 *\n\n");

                    //Mostra a matriz que esta sendo utilizada no exercicio
                    mostrar_matriz (M, N, max);

                    //Verifica se a primeira linha e a diagonal sao identicas
                    ehIdentico = comparar_primeiraLinha_diagonal (M, N, max);

                    //Exibe o resultado da comparacao
                    if (ehIdentico) {
                        printf ("A primeira linha eh identica a diagonal principal da matriz");
                    } else {
                        printf ("A primeira linha e a diagonal principal da matriz sao diferentes");
                    }
                    break;

                //Exercicio 10
                case 10:
                    //Titulo da pagina
                    printf ("* EXERCICIO 10 *\n\n");

                    //Mostra a matriz que esta sendo utilizada no exercicio
                    mostrar_matriz (M, N, max);

                    //Entrada do indice da coluna
                    printf ("Informe o indice da coluna: ");
                    scanf ("%d", &coluna);
                    fflush (stdin);

                    //Exibe o resultado da soma dos elementos da coluna
                    printf ("Soma da coluna %d da matriz: %.02f", coluna, soma_coluna (coluna, M, N, max));
                    break;

                //Exercicio 12
                case 12:
                    //Titulo da pagina
                    printf ("* EXERCICIO 12 *\n\n");

                    //Mostra a matriz que esta sendo utilizada no exercicio
                    mostrar_matriz (M, N, max);

                    //Informa o usuario o que esta sendo mostrado
                    printf ("A transposta da matriz\n");

                    //Constroi a transposta da matriz
                    for (j = 0; j < N; j++) {
                        for (i = 0; i < M; i ++) {
                            //Exibe a transposta da matriz
                            printf ("%.02f ", max[i][j]);
                        }
                        printf ("\n");
                    }
                    break;

                //Exercicio 13
                case 13:
                    //Titulo da pagina
                    printf ("* EXERCICIO 13 *\n\n");

                    //Chamada do procedimento para calcular a multiplicacao das matrizes
                    multiplicar_matriz (M, N, max);

                    break;

                //Exercicio 14
                case 14:
                    //Titulo da pagina
                    printf ("* EXERCICIO 14 *\n\n");

                    //Informa a coordenada do ponto P
                    printf ("Informe o ponto P no padrao (x, y): ");
                    scanf ("%f, %f", &Px, &Py);
                    fflush (stdin);

                    //Informa quantos pontos a matriz tera
                    printf ("Informe quantos pontos cartesianos deseja inserir: ");
                    scanf ("%d", &m);
                    fflush (stdin);

                    //Soma das distancias do ponto P com os pontos das matriz
                    soma_distancias_pontos_matriz (Px, Py, m);

                    break;


                //Sair
                case 0:
                    printf ("Obrigada!!! Volte sempre");
                    break;
            }

            if (opcao != 1) {
                reiniciar_programa ();
            }

        } while (opcao != 0 && opcao != 1);
    } while (opcao == 1);

  return 0;
}
