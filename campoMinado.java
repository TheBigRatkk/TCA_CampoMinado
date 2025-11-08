import java.util.Scanner;

public class campoMinado {

    public static void criarCelulas(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                campo[i][j] = new Celula();
            }
        }
    }

    public static void limparBombas(Celula[][] campoMinado) {
        for (int i = 0; i < campoMinado.length; i++) {
            for (int j = 0; j < campoMinado[0].length; j++) {
                campoMinado[i][j].bomba = false;
            }
        }
    }

    public static void imprimirCampoMinado(Celula[][] campo) { 
        for (int i = 0; i < 33; i++) {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if(campo[i][j].foiAberto == false) {
                    System.out.print("| * ");
                }else {
                    System.out.printf("| %d ", campo[i][j].bombasProximas);
                }
            }
            System.out.println("|");
        }
    }

    public static void definirBombas(Celula[][] campoMinado, int qtdBombas) {
        for(int i = 0; i < qtdBombas; i++) {
            while(true) {
                int X = (int)(Math.random() * campoMinado.length);
                int Y = (int)(Math.random() * campoMinado[0].length);
                if(campoMinado[X][Y].bomba == false) {
                    campoMinado[X][Y].bomba = true;
                    break;
                }
            }
        }
    }

    public static void definirBombasProximas(Celula[][] campo) {
        int deslocamentoI;
        int deslocamentoJ;
        int bombasAoRedor;
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if(campo[i][j].bomba) {
                    bombasAoRedor = 0;
                    continue;
                }
                bombasAoRedor = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        deslocamentoI = i + x;
                        deslocamentoJ = y + j;
                        if ((deslocamentoI >= 0 && deslocamentoI < campo.length) &&
                            (deslocamentoJ >= 0 && deslocamentoJ < campo[0].length)) {
                            if (campo[deslocamentoI][deslocamentoJ].bomba) {
                                bombasAoRedor++;
                            }
                        }
                    }
                    
                }
                campo[i][j].bombasProximas = bombasAoRedor;
            }
        }
    }

    public static void main(String[] args) {
        Celula[][] campoMinado = new Celula[12][8];
        int qtdBombas = 12;

        criarCelulas(campoMinado);
        limparBombas(campoMinado);
        definirBombas(campoMinado, qtdBombas);
        definirBombasProximas(campoMinado);

        for (int i = 0; i < campoMinado.length; i++) {
            for (int j = 0; j < campoMinado[0].length; j++) {
                System.out.printf("%b\t", campoMinado[i][j].bomba);
            }
            System.out.println();
        }
        for (int i = 0; i < campoMinado.length; i++) {
            for (int j = 0; j < campoMinado[0].length; j++) {
                System.out.printf("%d\t", campoMinado[i][j].bombasProximas);
            }
            System.out.println();
        }
        imprimirCampoMinado(campoMinado);
    }
}

/*_______________________________________________________________
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | 79 bombas
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |
 -----------------------------------------------------------------
 */

 /*  _______________________________________________
    | * | * | * | * | * | * | * | * | * | * | * | * |38 bombas
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    | * | * | * | * | * | * | * | * | * | * | * | * | 
    ------------------------------------------------- */

/*  _______________________________
 * | * | * | * | * | * | * | * | * | 12 bombas
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * | 
 * | * | * | * | * | * | * | * | * |
 * | * | * | * | * | * | * | * | * | 
 * ---------------------------------
 *     
 */