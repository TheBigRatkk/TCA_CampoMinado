import com.sun.jna.Library;
import com.sun.jna.Native;

public class ControleTeclasJogoComJNA {

    // Define uma interface para mapear as funções nativas do C
    // ** Crie uma pasta lib no seu projeto e copie o arquino jna-5.17.0.jar para ela
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);
        
        // Se uma tecla foi pressionada, ela retorna um valor diferente de zero.
        int _kbhit();

        // Se _kbhit() detectar uma tecla, chamamos _getch() para ler a tecla. 
        // O _getch() lê a tecla diretamente, sem esperar pelo ENTER. 
        // Ele retorna o código ASCII da tecla pressionada.
        int _getch();
    }

    public static boolean pressionouTecla(){
        return Crt.INSTANCE._kbhit() != 0;
    }

    // Ele retorna o código ASCII da tecla pressionada
    public static int obtemTeclaPressionada(){
        return Crt.INSTANCE._getch();

    }
     
    // Métodos auxiliares para a interface do console
    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void desenharTela(int x, int y) {
        StringBuilder tela = new StringBuilder();
        int largura = 40;
        int altura = 10;
        
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (i == y && j == x) {
                    tela.append('H'); 
                } else {
                    tela.append('.');
                }
            }
            tela.append('\n');
        }
        System.out.println(tela.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Use A, D, W, S para mover o personagem. Pressione ESC para sair.");
        
        int posX = 10;
        int posY = 5;
        
        while (true) {
            // Lógica para detectar o movimento          
           
            if ( pressionouTecla()) {
                int ch = obtemTeclaPressionada();
                switch (ch) {
                    case 'a':
                        posX--;
                        break;
                    case 'd':
                        posX++;
                        break;
                    case 'w':
                        posY--;
                        break;
                    case 's':
                        posY++;
                        break;
                    case 27: // Código ASCII para a tecla ESC
                        return; // Sai do loop e termina o programa
                }
            }

            // Lógica do jogo (desenhar a tela)
            limparConsole();
            desenharTela(posX, posY);

            // Controla a velocidade do jogo
            Thread.sleep(500); // espera 100 milissegundos -> 0,1 segundos
        }
    }
   
}
