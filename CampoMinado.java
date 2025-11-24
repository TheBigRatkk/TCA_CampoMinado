import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.management.ListenerNotFoundException;
import javax.sound.sampled.*;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.File;
import java.io.IOException;

public class CampoMinado {

    public static Scanner TECLADO = new Scanner(System.in);
    public static int configTam = 2;
    public static int configBombas = 40;
    public static int jogadorPontuacao = 0;

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

    public static void tocarSom(String caminhoArquivoSom){
              
        try {
            // Abrindo o arquivo de som
            File soundFile = new File(caminhoArquivoSom);
            if (!soundFile.exists()) {
                System.out.println("Arquivo de som não encontrado: " + caminhoArquivoSom);
                return;
            }

            // Criando um AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Obtendo as informações do formato de áudio
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Criando o Clip e carregando o áudio
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            audioClip.start();

            // Mantendo o programa ativo enquanto o som toca            
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioClip.close();
                    try { 
                    audioStream.close();
                    } catch(IOException e) {

                    }
                }
            });
        } catch (UnsupportedAudioFileException e) {
            System.err.println("O formato de áudio não é suportado.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Linha de áudio não disponível.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de áudio.");
            e.printStackTrace();
        }
    }

    public static int lerInt() {
        return TECLADO.nextInt();
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

    public static void perdeu(Celula[][] campo) throws IOException, InterruptedException {
        limparConsole();
        Imprimir.fimDeJogo(campo);
        tocarSom("soms\\boooooooooooo.wav");
        tocarSom("soms\\bomba.wav");
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
                        redirecionarPraFuncoesFinais(opcaoSelec);
                    default:
                        continue;
                }
            }
        }
    }

    public static void ganhou() throws IOException {
        int opcaoSelec = 1;
        limparConsole();
        Imprimir.menuGanhou(opcaoSelec);
        tocarSom("soms\\Aplusos.wav");
        tocarSom("soms\\makita.wav");
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
                        redirecionarPraFuncoesFinais(opcaoSelec);
                    default:
                        continue;
                }
            }
        }
    }

    public static void mostrarPontuacao() throws IOException {
        limparConsole();
        Imprimir.pontuacao(jogadorPontuacao);
        while(true) {
            if (pressionouTecla()) {
                int ch = obtemTeclaPressionada();
                if (ch == '\n' || ch == '\r') {
                    inicio(1);
                }
                limparConsole();
                Imprimir.pontuacao(jogadorPontuacao);
            }
        }
    }

    public static void redirecionarPraFuncoesFinais(int opcaoSelec) throws IOException {
        if (opcaoSelec == 1) {
            novoJogo();
        }
        if (opcaoSelec == 2) {
            mostrarPontuacao();
        }
        if (opcaoSelec == 3) {
            inicio(1);
        }
    }

    public static void novoJogo() throws IOException {
        int tamanho = 12;
        if (configTam == 1) {
            tamanho = 9;
        }else if (configTam == 2) {
            tamanho = 12;
        }else if (configTam == 3) {
            tamanho = 15;
        }

        Celula[][] campo = new Celula[tamanho][tamanho];
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
                int modficador = (int) (Math.random() * 10);
                jogadorPontuacao = (int) ((acertos * modficador * 10000) / tempoTotal);
                try {
                    perdeu(campo);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.err.println("Jogo interrompido: " + ie.getMessage());
                }
            }
            if (verificarSeGanhou(campo, qtdBombas) == true) {
                fim = System.currentTimeMillis();
                tempoTotal = fim - tempoInicial;
                jogadorPontuacao = (int) ((acertos * 10000) / tempoTotal);
                ganhou();
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
                        tocarSom("soms\\SHOVEL.wav");
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
                        tocarSom("soms\\bandeira.wav");
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

    public static void opcoes() {
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
                            inicio(1);
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

    public static void instrucoes() {
        try {
            limparConsole();
            Imprimir.menuInstruções();

            while(true) {
                if(pressionouTecla()) {
                    int ch = obtemTeclaPressionada();

                    if(ch == '\n' || ch == '\r') {
                        inicio(1);
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

    public static void redirecionarPraFuncoesIniciais(int opcaoSelec) throws IOException {
        if (opcaoSelec == 1) {
            novoJogo();
        }
        if (opcaoSelec == 2) {
            opcoes();
        }
        if (opcaoSelec == 3) {
            instrucoes();
        }
        if (opcaoSelec == 4) {
            System.exit(0);
        }
    }

    public static void inicio(int opcaoSelec) throws IOException {
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
                        redirecionarPraFuncoesIniciais(opcaoSelec);
                        break;
                    default:
                        continue;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int opcaoSelec = 1;
        Imprimir.setCor256(196);
        System.out.println("Para jogar, digite seu nome:");
        Imprimir.resetColor();
        jogadorPontuacao = 0;

        inicio(opcaoSelec);
    }
}