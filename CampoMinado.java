import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.IOException;

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

    public static int obtemTeclaPressionada() {
        return Crt.INSTANCE._getch();
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void salvarDados(String nome, int pontuacao) throws IOException {
        File Rank = new File("Rank.txt");
        FileWriter escritor = new FileWriter(Rank);
        
        escritor.write(pontuacao + "" + nome + "\n");
        escritor.close();
    }

    public static void carregarDados(int[] top20num, String[] top20nome) throws IOException{
        File Rank = new File("Rank.txt");
        Scanner leitor = new Scanner(Rank);

        while(leitor.hasNext()) {
            int pontAtual = leitor.nextInt();
            String nomeAtual = leitor.nextLine().trim();
            if (pontAtual > top20num[19]) {
                top20num[19] = pontAtual;
                Arrays.sort(top20num);
                top20nome[acharPos(top20num, pontAtual)] = nomeAtual;
            }
        }
        leitor.close();
    }

    public static int acharPos(int[] top20, int pontAtual) {
        int pos = 0;
        for (int i = 0; i < top20.length; i++) {
            if(top20[i] == pontAtual) {
                pos = i;
            }
        }
        return pos;
    }

    public static int lerInt() {
        return TECLADO.nextInt();
    }

    public static String lerTexto() {
        return TECLADO.nextLine();
    }

    public static int verificarX(int x, Celula[][] campo) {
        if (x > campo.length - 1) {
            x = campo.length - 1;
        } else if (x < 0) {
            x = 0;
        }
        return x;
    }

    public static int verificarY(int y, Celula[][] campo) {
        if (y >= campo[0].length - 1) {
            y = campo[0].length - 1;
        } else if (y < 0) {
            y = 0;
        }
        return y;
    }

    public static int definirPrimeriroX(Celula[][] campo) {
        int x = campo.length / 2;
        int y = campo[0].length / 2;
        while(true) {
            if(campo[x][y].bomba == false) {
                break;
            }else {
                x++;
            }
        }
        return x;
    }

    public static boolean verificarSeGanhou(Celula[][] campo, int qtdBombas) {
        int bombasAchadas = 0;
        for(int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if (campo[i][j].bomba == true) {
                    if (campo[i][j].bandeira == true) {
                        bombasAchadas++;
                    }
                }
                if(bombasAchadas >= qtdBombas) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verificarSePerdeu(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if (campo[i][j].bomba == true && campo[i][j].foiAberto == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void perdeu(String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
        int opcaoSelec = 1;
        limparConsole();
        Imprimir.menuPerdeu(opcaoSelec);
        while(true) {
            if (pressionouTecla()) {
                int tecla = obtemTeclaPressionada();

                switch (tecla) {
                    case 'w':
                    case 'W':
                        opcaoSelec--;
                        opcaoSelec = verificarOpSelecFim(opcaoSelec);
                        limparConsole();
                        Imprimir.menuPerdeu(opcaoSelec);
                        break;
                    case 's':
                    case 'S':
                        opcaoSelec++;
                        opcaoSelec = verificarOpSelecFim(opcaoSelec);
                        limparConsole();
                        Imprimir.menuPerdeu(opcaoSelec);
                        break;
                    case '\n':
                    case '\r':
                        redirecionarPraFuncoesFinais(opcaoSelec, nome, top20num, top20nome, pontuacao);
                    default:
                        continue;
                }
            }
        }
    }
    public static void ganhou(String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException{
        int opcaoSelec = 1;
        limparConsole();
        Imprimir.menuGanhou(opcaoSelec);
        while(true) {
            if (pressionouTecla()) {
                int tecla = obtemTeclaPressionada();

                switch (tecla) {
                    case 'w':
                    case 'W':
                        opcaoSelec--;
                        opcaoSelec = verificarOpSelecFim(opcaoSelec);
                        limparConsole();
                        Imprimir.menuGanhou(opcaoSelec);
                        break;
                    case 's':
                    case 'S':
                        opcaoSelec++;
                        opcaoSelec = verificarOpSelecFim(opcaoSelec);
                        limparConsole();
                        Imprimir.menuGanhou(opcaoSelec);
                        break;
                    case '\n':
                    case '\r':
                        redirecionarPraFuncoesFinais(opcaoSelec, nome, top20num, top20nome, pontuacao);
                    default:
                        continue;
                }
            }
        }
    }

    public static boolean verificarSeEstaNoTop20(String[] top20, String nome) {
        for (int i = 0; i < top20.length; i++) {
            if(top20[i] == nome) {
                return true;
            }
        }
        return false;
    }

    public static void pontuacao(String nome, int pontuacao, int[] top20Pont, String[] top20Nome) throws IOException {
        salvarDados(nome, pontuacao);
        carregarDados(top20Pont, top20Nome);
        int pos = acharPos(top20Pont, pontuacao);
        int num;

        System.out.printf("Rank Top 20:\n\n");
        if(verificarSeEstaNoTop20(top20Nome, nome) == true) {
            System.out.printf("Você está na posição %d!!\n\n", pos);
        }else {
            System.out.printf("Você não esta no top 20 :(\nSua pontuação é %d\n\n", pontuacao);
        }
        for (int i = 0; i < top20Pont.length; i++) {
            System.out.printf("%d)Nome: %S\nPontuação: %d\n\n", i+1, top20Nome[i], top20Pont[i]);
        }
        
        while(true) {
            System.out.println("Digite 0 para voltar ao menu inicial.");
            num = lerInt();
            if(num == 0) {
                inicio(1, nome, top20Pont, top20Nome, pontuacao);
            }
        }

    }

    public static void redirecionarPraFuncoesFinais(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
        if(opcaoSelec == 1) {
            novoJogo(nome, top20num, top20nome, pontuacao);
        }if(opcaoSelec == 2) {
            pontuacao(nome, pontuacao, top20num, top20nome);
        }if(opcaoSelec == 3) {
            inicio(opcaoSelec, nome, top20num, top20nome, pontuacao);
        }
    }

    public static void novoJogo(String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
        Celula[][] campo = new Celula[15][15];
        int qtdBombas = 70;
        int dificuldade = (campo.length * campo[0].length) + (qtdBombas * 10);
        long tempoInicial = System.currentTimeMillis();
        long fim;
        long tempoTotal;

        Celula.criarCelulas(campo);
        Celula.limparBombas(campo);
        Celula.definirBombas(campo, qtdBombas);
        Celula.definirBombasProximas(campo);
        Celula.corrigirNumBombasProximas(campo);
        Celula.definirBombasProximas(campo);
        qtdBombas = Celula.corrigirQtdBombas(campo);

        int xCelulaAtual = definirPrimeriroX(campo);
        int yCelulaAtual = (campo[0].length) / 2;

        Celula.abrirCelulasVizinhas(xCelulaAtual, yCelulaAtual, campo);

        limparConsole();
        Imprimir.campoMinado(campo);

        while (true) {
            if(verificarSePerdeu(campo) == true) {
                perdeu(nome, top20num, top20nome, pontuacao);
                fim = System.currentTimeMillis();
                tempoTotal = fim - tempoInicial;
                pontuacao = (int)((dificuldade * 10000)/ tempoTotal);
            }
            if(verificarSeGanhou(campo, qtdBombas) == true) {
                ganhou(nome, top20num, top20nome, pontuacao);
                fim = System.currentTimeMillis();
                tempoTotal = fim - tempoInicial;
                pontuacao = (int)((dificuldade * 10000)/ tempoTotal);
            }
            if (pressionouTecla()) {
                int ch = obtemTeclaPressionada();

                switch (ch) {
                    case 'w':
                    case 'W':
                        xCelulaAtual -= 1;
                        xCelulaAtual = verificarX(xCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 's':
                    case 'S':
                        xCelulaAtual += 1;
                        xCelulaAtual = verificarX(xCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'a':
                    case 'A':
                        yCelulaAtual -= 1;
                        yCelulaAtual = verificarY(yCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'd':
                    case 'D':
                        yCelulaAtual += 1;
                        yCelulaAtual = verificarY(yCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'c':
                    case 'C':
                        campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                        campo[xCelulaAtual][yCelulaAtual].foiAberto = true;
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        break;
                    case 'b':
                    case 'B':
                        if (campo[xCelulaAtual][yCelulaAtual].foiAberto == false) {
                            if(campo[xCelulaAtual][yCelulaAtual].bandeira == true) {
                                campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                            }else {
                                campo[xCelulaAtual][yCelulaAtual].bandeira = true;
                            }
                        }
                        limparConsole();
                        Imprimir.campoMinado(campo);
                        break;
                    default:
                        continue;
                }
            }
        }
    }

    public static void opcoes() {

    }

    public static void instrucoes() {

    }

    public static int verificarOpSelecInicio(int opcaoSelec) {
        if(opcaoSelec < 1) {
            opcaoSelec = 1;
        }else if(opcaoSelec > 4) {
            opcaoSelec = 4;
        }
        return opcaoSelec;
    }

    public static int verificarOpSelecFim(int opcaoSelec) {
        if(opcaoSelec < 1) {
            opcaoSelec = 1;
        }else if(opcaoSelec > 3) {
            opcaoSelec = 3;
        }
        return opcaoSelec;
    }

    public static void redirecionarPraFuncoesIniciais(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
        if(opcaoSelec == 1) {
            novoJogo(nome, top20num, top20nome, pontuacao);
        }if(opcaoSelec == 2) {
            opcoes();
        }if(opcaoSelec == 3) {
            instrucoes();
        }if(opcaoSelec == 4) {
            System.exit(0);
        }
    }

    public static void inicio(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
        limparConsole();
        Imprimir.menuInicial(opcaoSelec);
        while(true) {
            if (pressionouTecla()) {
                int tecla = obtemTeclaPressionada();

                switch (tecla) {
                    case 'w':
                    case 'W':
                        opcaoSelec--;
                        opcaoSelec = verificarOpSelecInicio(opcaoSelec);
                        limparConsole();
                        Imprimir.menuInicial(opcaoSelec);
                        break;
                    case 's':
                    case 'S':
                        opcaoSelec++;
                        opcaoSelec = verificarOpSelecInicio(opcaoSelec);
                        limparConsole();
                        Imprimir.menuInicial(opcaoSelec);
                        break;
                    case '\n':
                    case '\r':
                        redirecionarPraFuncoesIniciais(opcaoSelec,nome, top20num, top20nome, pontuacao);
                    default:
                        continue;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String nome;
        int pontuacao = 0;
        String[] nomeTop20 = new String[20];
        int[] pontuacaoTop20 = new int[20];
        int opcaoSelec = 1;

        Imprimir.setCor256(196);
        System.out.println("Para jogar, digite seu nome:");
        Imprimir.resetColor();
        nome = lerTexto();
        inicio(opcaoSelec, nome, pontuacaoTop20, nomeTop20, pontuacao);
    }
}