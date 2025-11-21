import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.IOException;

public class CampoMinado {

    public static Scanner TECLADO = new Scanner(System.in);
        public static int configTam = 2;
        public static int configBombas = 40;

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

        public static void salvarDados(String nome, int pontuacao) {
            File Rank = new File("Rank.txt");
            try (FileWriter escritor = new FileWriter(Rank, true)) { // append
                escritor.write(pontuacao + " " + nome + System.lineSeparator());
            } catch (IOException e) {
                System.err.println("Erro ao salvar ranking: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static void carregarDados(int[] top20num, String[] top20nome) {
            File Rank = new File("Rank.txt");
            if (!Rank.exists()) {
                return;
            }
            try (Scanner leitor = new Scanner(Rank)) {
                while (leitor.hasNext()) {
                    if (!leitor.hasNextInt()) {
                        leitor.nextLine();
                        continue;
                    }
                    int pontAtual = leitor.nextInt();
                    String nomeAtual = leitor.nextLine().trim();
                    if (pontAtual > top20num[top20num.length - 1]) {
                        top20num[top20num.length - 1] = pontAtual;
                        Arrays.sort(top20num);
                        top20nome[acharPos(top20num, pontAtual)] = nomeAtual;
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao carregar ranking: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static int acharPos(int[] top20, int pontAtual) {
            int pos = 0;
            for (int i = 0; i < top20.length; i++) {
                if (top20[i] == pontAtual) {
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
            while (true) {
                if (campo[x][y].bomba == false) {
                    break;
                } else {
                    x++;
                }
            }
            return x;
        }

        public static boolean verificarSeGanhou(Celula[][] campo, int qtdBombas) {
            int bombasAchadas = 0;
            for (int i = 0; i < campo.length; i++) {
                for (int j = 0; j < campo[0].length; j++) {
                    if (campo[i][j].bomba == true) {
                        if (campo[i][j].bandeira == true) {
                            bombasAchadas++;
                        }
                    }
                    if (bombasAchadas >= qtdBombas) {
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

        public static void perdeu(String nome, int[] top20num, String[] top20nome, int pontuacao, Celula[][] campo) throws IOException, InterruptedException {
            Imprimir.fimDeJogo(campo);
            TimeUnit.SECONDS.sleep(3);
            int opcaoSelec = 1;
            limparConsole();
            Imprimir.menuPerdeu(opcaoSelec);
            while (true) {
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

        public static void ganhou(String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
            int opcaoSelec = 1;
            limparConsole();
            Imprimir.menuGanhou(opcaoSelec);
            while (true) {
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
                if (top20[i] == nome) {
                    return true;
                }
            }
            return false;
        }

        public static void pontuacao(String nome, int pontuacao, int[] top20Pont, String[] top20Nome) {
            salvarDados(nome, pontuacao);
            carregarDados(top20Pont, top20Nome);
            int pos = acharPos(top20Pont, pontuacao);
            int num;

            System.out.printf("\nRank Top 20:\n\n");
            if (verificarSeEstaNoTop20(top20Nome, nome) == true) {
                System.out.printf("Você está na posição %d!!\n\n", pos);
            } else {
                System.out.printf("Você não esta no top 20 :(?\nSua pontuação é %d\n\n", pontuacao);
            }
            for (int i = 0; i < top20Pont.length; i++) {
                System.out.printf("%d)Nome: %S\nPontuação: %d\n\n", i + 1, top20Nome[i], top20Pont[i]);
            }

            while (true) {
                System.out.println("Digite 0 para voltar ao menu inicial.");
                num = lerInt();
                if (num == 0) {
                    try {
                        inicio(1, nome, top20Pont, top20Nome, pontuacao);
                    } catch (IOException e) {
                        System.err.println("Erro ao voltar ao menu inicial: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

        }

        public static void redirecionarPraFuncoesFinais(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
            if (opcaoSelec == 1) {
                novoJogo(nome, top20num, top20nome, pontuacao);
            }
            if (opcaoSelec == 2) {
                pontuacao(nome, pontuacao, top20num, top20nome);
            }
            if (opcaoSelec == 3) {
                inicio(opcaoSelec, nome, top20num, top20nome, pontuacao);
            }
        }

        public static void novoJogo(String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
            int size = 12;
            if (configTam == 1) size = 9;
            else if (configTam == 2) size = 12;
            else if (configTam == 3) size = 15;

            Celula[][] campo = new Celula[size][size];
            int qtdBombas = configBombas;
            int acertos = 0;
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
                if (verificarSePerdeu(campo) == true) {
                    fim = System.currentTimeMillis();
                    tempoTotal = fim - tempoInicial;
                    pontuacao = (int) ((acertos * 10000) / tempoTotal);
                    try {
                        perdeu(nome, top20num, top20nome, pontuacao, campo);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("Jogo interrompido: " + ie.getMessage());
                    }
                }
                if (verificarSeGanhou(campo, qtdBombas) == true) {
                    fim = System.currentTimeMillis();
                    tempoTotal = fim - tempoInicial;
                    pontuacao = (int) ((acertos * 10000) / tempoTotal);
                    ganhou(nome, top20num, top20nome, pontuacao);
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
                            if (campo[xCelulaAtual][yCelulaAtual].bomba == false) {
                                acertos += 10;
                            }
                            limparConsole();
                            Imprimir.campoMinado(campo);
                            break;
                        case 'b':
                        case 'B':
                            if (campo[xCelulaAtual][yCelulaAtual].foiAberto == false) {
                                if (campo[xCelulaAtual][yCelulaAtual].bandeira == true) {
                                    campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                                } else {
                                    campo[xCelulaAtual][yCelulaAtual].bandeira = true;
                                    acertos += 15;
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

        public static int verificarOpBombas(int bombas) {
            if (bombas > 100) {
                bombas = 100;
            }
            if (bombas < 10) {
                bombas = 10;
            }
            return bombas;
        }

        public static void opcoes(String nome, int[] top20num, String[] top20nome, int pontuacao) {
            int opcaoSelec = 1;
            int opTam = configTam;
            int opBombas = configBombas;

            limparConsole();
            Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);

            try {
                while (true) {
                    if (!pressionouTecla()) {
                        continue;
                    }
                    int ch = obtemTeclaPressionada();

                    switch (ch) {
                        case 'w':
                        case 'W':
                            opcaoSelec--;
                            opcaoSelec = verificarOpSelecFim(opcaoSelec);
                            limparConsole();
                            Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            break;

                        case 's':
                        case 'S':
                            opcaoSelec++;
                            opcaoSelec = verificarOpSelecFim(opcaoSelec);
                            limparConsole();
                            Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            break;

                        case 'a':
                        case 'A':
                            if (opcaoSelec == 1) {
                                opTam--;
                                opTam = verificarOpSelecFim(opTam);
                                configTam = opTam;
                                limparConsole();
                                Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            } else if (opcaoSelec == 2) {
                                opBombas--;
                                opBombas = verificarOpBombas(opBombas);
                                configBombas = opBombas;
                                limparConsole();
                                Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            }
                            break;

                        case 'd':
                        case 'D':
                            if (opcaoSelec == 1) {
                                opTam++;
                                opTam = verificarOpSelecFim(opTam);
                                configTam = opTam;
                                limparConsole();
                                Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            } else if (opcaoSelec == 2) {
                                opBombas++;
                                opBombas = verificarOpBombas(opBombas);
                                configBombas = opBombas;
                                limparConsole();
                                Imprimir.menuOpçoes(opcaoSelec, opTam, opBombas);
                            }
                            break;

                        case '\n':
                        case '\r':
                            if (opcaoSelec == 3) {
                                inicio(1, nome, top20num, top20nome, pontuacao);
                                return;
                            }
                            break;

                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Algo deu ruim :( " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static void instrucoes(String nome, int[] top20num, String[] top20nome, int pontuacao) {
            try {
                limparConsole();
                Imprimir.menuInstruções();

                while(true) {
                    if(pressionouTecla()) {
                        int ch = obtemTeclaPressionada();

                        if(ch == '\n' || ch == '\r') {
                            inicio(1, nome, top20num, top20nome, pontuacao);
                        }else {
                            limparConsole();
                            Imprimir.menuInstruções();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao voltar ao menu inicial: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static int verificarOpSelecInicio(int opcaoSelec) {
            if (opcaoSelec < 1) {
                opcaoSelec = 1;
            } else if (opcaoSelec > 4) {
                opcaoSelec = 4;
            }
            return opcaoSelec;
        }

        public static int verificarOpSelecFim(int opcaoSelec) {
            if (opcaoSelec < 1) {
                opcaoSelec = 1;
            } else if (opcaoSelec > 3) {
                opcaoSelec = 3;
            }
            return opcaoSelec;
        }

        public static void redirecionarPraFuncoesIniciais(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
            if (opcaoSelec == 1) {
                novoJogo(nome, top20num, top20nome, pontuacao);
            }
            if (opcaoSelec == 2) {
                opcoes(nome, top20num, top20nome, pontuacao);
            }
            if (opcaoSelec == 3) {
                instrucoes(nome, top20num, top20nome, pontuacao);
            }
            if (opcaoSelec == 4) {
                System.exit(0);
            }
        }

        public static void inicio(int opcaoSelec, String nome, int[] top20num, String[] top20nome, int pontuacao) throws IOException {
            limparConsole();
            Imprimir.menuInicial(opcaoSelec);
            while (true) {
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
                            redirecionarPraFuncoesIniciais(opcaoSelec, nome, top20num, top20nome, pontuacao);
                            break;
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