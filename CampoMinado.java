import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class CampoMinado {

    public static Scanner TECLADO = new Scanner(System.in);

    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);
        
        int _kbhit();
        int _getch();
    }

    public static boolean pressionouTecla() {
        return Crt.INSTANCE._kbhit() != 0;
    } 

    public static int obtemTeclaPressionada(){
        return Crt.INSTANCE._getch();
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int lerInt() {
        return TECLADO.nextInt();
    }

    public static int verificarX(int x, Celula[][] campo) {
        if(x > campo.length) {
            x--;
        }else if(x < 0) {
            x++;
        }
        return x;
    }

    public static int verificarY(int y, Celula[][] campo) {
        if(y > campo[0].length) {
            y--;
        }else if(y < 0) {
            y++;
        }
        return y;
    }

    public static void novoJogo(int qtdBombas, Celula[][] campo) {
        Celula.criarCelulas(campo);
        Celula.limparBombas(campo);
        Celula.definirBombas(campo, qtdBombas);;
        Celula.definirBombasProximas(campo);
        int xCelulaAtual = (campo.length + 1) / 2;
        int yCelulaAtual = (campo[0].length + 1) / 2;
        while(true) {
            if (pressionouTecla()) {
                int ch = obtemTeclaPressionada();

                switch (ch) {
                    case 'w':
                    case 'W':
                        yCelulaAtual += 1;
                        yCelulaAtual = verificarY(yCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        break;
                
                    default:
                        break;
                }
            }
        }
    }

    public static void opcoes() {

    }

    public static void instrucoes() {

    }

    public static void main(String[] args) {
        Celula[][] campoMinado = new Celula[25][15];
        int qtdBombas = 12;
        int opcao = 0;

        do {
            Imprimir.menuInicial();
            opcao = lerInt();
            switch(opcao) {
                case 1:
                    novoJogo(qtdBombas, campoMinado);
                    break;
                case 2:
                    opcoes();
                    break;
                case 3:
                    instrucoes();
                    break;
                default:
                    continue;
            }
        }while(!(opcao == 1 ||
               opcao == 2 ||
               opcao == 3));
        
    }
}

/*  Celula.criarCelulas(campoMinado);
    Celula.limparBombas(campoMinado);
    Celula.definirBombas(campoMinado, qtdBombas);;
    Celula.definirBombasProximas(campoMinado);
    Celula.imprimirCampoMinado(campoMinado); */

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

 /* +----------------------------+
    |  __  __ _____ _   _ _   _  |
    | |  \/  | ____| \ | | | | | |
    | | |\/| |  _| |  \| | | | | |
    | | |  | | |___| |\  | |_| | |
    | |_|  |_|_____|_| \_|\___/  |
    +----------------------------+
    |  >JOGAR<      Digite 1     |
    |                            |
    |  >OPÇÕES<     Digite 2     |
    |                            |
    |  >INSTRUÇÕES< Digite 3     |
    |============================|
  *    
  */