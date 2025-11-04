import java.util.Scanner;
import java.util.random.*;

public class RafaelKrassota_CampoMinado {

    public static void limparMatrizBool(boolean[][] matrizBool) {
        for (int i = 0; i < matrizBool.length; i++) {
            for (int j = 0; j < matrizBool[0].length; j++) {
                matrizBool[i][j] = false;
            }
        }
    }

    //public static void imprimirCampoMinado(int[][] campoMinado)

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
        boolean[][] bombas = new boolean[8][8];
        int qtdBombas;

    }
}