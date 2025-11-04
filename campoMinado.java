import java.util.Scanner;
import java.util.random.*;

public class campoMinado {

    public static void limparMatrizBool(boolean[][] matrizBool) {
        for (int i = 0; i < matrizBool.length; i++) {
            for (int j = 0; j < matrizBool[0].length; j++) {
                matrizBool[i][j] = false;
            }
        }
    }

    public static void definirCadrados(char[][] cadrados) {
        for (int i = 0; i < cadrados[0].length; i++) {
            if(i % 5 == 0) {
                //cadrados[0][i] = '*\n';
            }else {
                cadrados[0][i] = '*';
            }
        }
        for (int i = 1; i <= cadrados.length; i++) {
            for (int j = 0; j < cadrados[0].length; j++) {
                
            }
        }
    }

    public static void imprimirCampoMinado(char[][] cadrados) {

    }

    public static void definirBombas(boolean[][] matrizBool, int qtdBombas) {
        for(int i = 0; i < qtdBombas; i++) {
            while(true) {
                int X = (int)(Math.random()) * matrizBool.length;
                int Y = (int)(Math.random()) * matrizBool[0].length;
                if(matrizBool[X][Y] == false) {
                    matrizBool[X][Y] = true;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        boolean[][] bombas = new boolean[9][9];
        int qtdBombas;
        char[][] cadrados = new char[10][15];
    }
}