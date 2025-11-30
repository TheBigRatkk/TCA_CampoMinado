import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.File;
import java.io.IOException;

public class CampoMinado {

    //Variaveis globais
    //Scanner
    public static Scanner TECLADO = new Scanner(System.in);
    //Tamanho do campo(9x9, 12x12, 15x15) padrao: 2(12x12)
    public static int configTam = 2;
    //Numero de bombas(10 - 100) padrao: 30
    public static int configBombas = 30;
    //Pontuacao do jogador
    public static int jogadorPontuacao = 0;

    //Configura JNA
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();

        int _getch();
    }

    //Retorna se pressionou alguma tecla
    public static boolean pressionouTecla() {
        return Crt.INSTANCE._kbhit() != 0;
    }

    //obtem a tecla pressionada
    public static int obtemTeclaPressionada() {
        return Crt.INSTANCE._getch();
    }

    //Limpa o console
    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void tocarSom(String caminhoArquivoSom){
              
        try {
            //Abre o arquivo de som
            File soundFile = new File(caminhoArquivoSom);
            if (!soundFile.exists()) {
                System.out.println("Arquivo de som não encontrado: " + caminhoArquivoSom);
                return;
            }

            //Cria um AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            //Obtem as informacoes do formato de audio
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            //Cria o Clip e carrega o audio
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            //comeca
            audioClip.start();

            // Mantendo o programa ativo enquanto o som toca
            /*Adiciona um ouvinte ao audio, que monitora os evntos de execucao do audio
              Se o evento detectado for do tipo STOP, fecha o clip e tenta fechar o stream
              se uma execao for detectada exibe uma mensagem e o erro.
             */          
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioClip.close();
                    try { 
                        audioStream.close();
                    } catch(IOException e) {
                        System.err.println("Não foi possível parar o áudio.");
                        e.printStackTrace();
                    }
                }
            });
        //Se execoes forem detectadas exibe a mensagem de erro e o erro de cada execao
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

    //Metodo de ler numero inteiro
    public static int lerInt() {
        return TECLADO.nextInt();
    }

    //Verifica se o x é maior que o limite da matriz, caso sim x recebe o limite
    public static int verificarX(int x, Celula[][] campo) {
        if (x > campo.length - 1) {
            x = campo.length - 1;
        } else if (x < 0) {
            x = 0;
        }
        return x;
    }

    //Verifica se o y é maior que o limite da matriz, caso sim y recebe o limite
    public static int verificarY(int y, Celula[][] campo) {
        if (y >= campo[0].length - 1) {
            y = campo[0].length - 1;
        } else if (y < 0) {
            y = 0;
        }
        return y;
    }

    /*Metodo que verifica se o jogador ganhou
      Percorre a matriz em busca das bombas que foram encontradas,
      ou seja, se a mesma celula for uma bomba e estiver com uma bandeira
      se o numero de bombas encontradas for maior ou igual que
      a quantidade de bombas, retorna verdadeiro, se sair do loop
      retorna falso*/
    public static boolean verificarSeGanhou(Celula[][] campo, int qtdBombas) {
        int bombasAchadas = 0;
        int abertos = 0;
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if (campo[i][j].bomba == true && campo[i][j].bandeira == true) {
                    bombasAchadas++;
                }if (campo[i][j].bomba == false && campo[i][j].foiAberto == true) {
                    abertos++;
                }if (bombasAchadas >= qtdBombas && abertos >= (campo.length * campo[0].length) - qtdBombas) {
                    return true;
                }
            }
        }
        return false;
    }

    /*Metodo que verifica se o jogador perdeu
      percorre a matriz verificando se a posicao atual
      é uma bomba e se foi aberto, se sim retorna verdadeiro,
      se sair do loop, retorna falso*/
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

    //Metodo que organiza as funcoes quando game over
    public static void perdeu(Celula[][] campo) {
        int opcaoSelec = 1;//Variavel para controlar o uso das cores junto com JNA e para redirecionamento
        limparConsole();
        Imprimir.fimDeJogo(campo);//Imprime "animacao" de game over
        tocarSom("sons\\boooooooooooo.wav");
        tocarSom("sons\\bomba.wav");
        try {
            Thread.sleep(3000);//Paraliza o progrma por 3 segundos
        } catch (InterruptedException e) {
            System.err.println("Não foi possível paralizar o programa");
            e.printStackTrace();
        }
        limparConsole();
        Imprimir.menuPerdeu(opcaoSelec);//imprime o menu de game over
        while (true) {
                if (pressionouTecla()) {
                    int tecla = obtemTeclaPressionada();

                    //switch case na tecla pressionada
                    switch (tecla) {
                        case 'w'://Caso 'w' desincrementa 1 da opcao atual
                        case 'W':
                            opcaoSelec--;
                            opcaoSelec = verificarOpSelecFim(opcaoSelec);//Verifica a opcao selcionada para não passar do limite(1-3)
                            limparConsole();
                            Imprimir.menuPerdeu(opcaoSelec);
                            break;
                        case 's'://Caso 's' incrementa 1 da opcao atual
                        case 'S':
                            opcaoSelec++;
                            opcaoSelec = verificarOpSelecFim(opcaoSelec);
                            limparConsole();
                            Imprimir.menuPerdeu(opcaoSelec);
                            break;
                        case '\n'://Caso enter redireciona para a opcao selecionada
                        case '\r':
                            redirecionarPraFuncoesFinais(opcaoSelec);
                        default:
                            continue;
                    }
            }
        }
    }

    //Metodo que organiza as funcoes quando ganha
    //Quase a mesma coise do metodo perdeu()
    public static void ganhou() {
        int opcaoSelec = 1;
        limparConsole();
        Imprimir.menuGanhou(opcaoSelec);
        tocarSom("sons\\Aplusos.wav");
        tocarSom("sons\\makita.wav");
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

    //Metodo que mostra a pontuação do jogador
    public static void mostrarPontuacao() {
        limparConsole();
        Imprimir.pontuacao(jogadorPontuacao);
        while(true) {
            if (pressionouTecla()) {
                int ch = obtemTeclaPressionada();
                if (ch == '\n' || ch == '\r') {
                    inicio();
                }
                limparConsole();
                Imprimir.pontuacao(jogadorPontuacao);
            }
        }
    }

    //Metodo para processar o movimento com JNA e redirecionar para os metodos escolhidos
    public static void redirecionarPraFuncoesFinais(int opcaoSelec){
        if (opcaoSelec == 1) {
            novoJogo();
        }
        if (opcaoSelec == 2) {
            mostrarPontuacao();
        }
        if (opcaoSelec == 3) {
            inicio();
        }
    }

    //Metodo para verifica o numero de bandeiras para não passar dos limites
    public static int verificarNumBandeiras(int totalBandeiras, int bandeiras) {
        if (bandeiras > totalBandeiras) {
            bandeiras = totalBandeiras;
        }else if(bandeiras < 0){
            bandeiras = 0;
        }
        return bandeiras;
    }

    public static int obterTamnhoCampo(int config) {
        if (configTam == 1) {
            return 9;
        }else if (configTam == 2) {
            return 12;
        }else if (configTam == 3) {
            return 15;
        }
        return 12;
    }

    //Metodo que inicia um novo jogo
    public static void novoJogo() {
        //Obtem o tamanho do campo de acordo com o escolhido no menu de opções
        int tamanho = obterTamnhoCampo(configTam);

        Celula[][] campo = new Celula[tamanho][tamanho];//Cria a matriz
        int qtdBombas = configBombas;//Configura a quantidade de bombas
        int bandeiras = qtdBombas;//Configura a quantidade de bandeiras
        //Variaveis para obter a pontuação
        int acertos = 0;
        long tempoInicial = System.currentTimeMillis();//obtem o horário atual
        long fim;
        long tempoTotal;

        Celula.criarCelulas(campo);//cria as células do campo minado em si
        Celula.limparBombas(campo);//Define todas as celulas como bomba == false
        Celula.definirBombas(campo, qtdBombas);//Usa random para determinar onde terá bombas
        Celula.definirBombasProximas(campo);//Baseado nas células bomba == true, determina os numeros que serão vistos
        //Define as células (bomba == false && bombasProximas == 0) como foiAberto == true
        Celula.abrirCampo(campo);
        //Define as células adjacentes as (bomba == false && bombasProximas == 0) como foiAberto == true
        Celula.abrirCelulasAdjacentes(campo);

        //Define a posição inicial na matriz
        int xCelulaAtual = campo.length / 2;
        int yCelulaAtual = (campo[0].length) / 2;

        limparConsole();
        Imprimir.campoMinado(campo, bandeiras);

        while (true) {
            if (verificarSePerdeu(campo) == true) {
                fim = System.currentTimeMillis();
                tempoTotal = fim - tempoInicial;
                int modficador = (int) (Math.random() * 10);
                jogadorPontuacao = (int) ((acertos * modficador * 100000) / tempoTotal);
                perdeu(campo);
            }
            if (verificarSeGanhou(campo, qtdBombas) == true) {
                fim = System.currentTimeMillis();
                tempoTotal = fim - tempoInicial;
                jogadorPontuacao = (int) ((acertos * 100000) / tempoTotal);
                ganhou();
            }if (pressionouTecla()) {
                int ch = obtemTeclaPressionada();

                switch (ch) {
                    case 'w':
                    case 'W':
                        xCelulaAtual -= 1;
                        xCelulaAtual = verificarX(xCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 's':
                    case 'S':
                        xCelulaAtual += 1;
                        xCelulaAtual = verificarX(xCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'a':
                    case 'A':
                        yCelulaAtual -= 1;
                        yCelulaAtual = verificarY(yCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'd':
                    case 'D':
                        yCelulaAtual += 1;
                        yCelulaAtual = verificarY(yCelulaAtual, campo);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = true;
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
                        campo[xCelulaAtual][yCelulaAtual].estaSelecionado = false;
                        break;
                    case 'c':
                    case 'C':
                        campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                        campo[xCelulaAtual][yCelulaAtual].foiAberto = true;
                        tocarSom("sons\\SHOVEL.wav");
                        if (campo[xCelulaAtual][yCelulaAtual].bomba == false) {
                            acertos += 10;
                        }
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
                        break;
                    case 'b':
                    case 'B':
                        if (campo[xCelulaAtual][yCelulaAtual].foiAberto == false) {
                            if (campo[xCelulaAtual][yCelulaAtual].bandeira == true) {
                                campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                                bandeiras++;
                                bandeiras = verificarNumBandeiras(qtdBombas, bandeiras);
                            } else {
                                campo[xCelulaAtual][yCelulaAtual].bandeira = true;
                                bandeiras--;
                                if(bandeiras <= 0) {
                                    campo[xCelulaAtual][yCelulaAtual].bandeira = false;
                                }
                                bandeiras = verificarNumBandeiras(qtdBombas, bandeiras);
                                if(campo[xCelulaAtual][yCelulaAtual].bomba == true) {
                                    acertos += 15;
                                }
                            }
                        }
                        tocarSom("sons\\bandeira.wav");
                        limparConsole();
                        Imprimir.campoMinado(campo, bandeiras);
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

        while (true) {
            if (pressionouTecla()) {
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
                            inicio();
                            return;
                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public static void instrucoes() {
        limparConsole();
        Imprimir.menuInstruções();

        while(true) {
            if(pressionouTecla()) {
                int ch = obtemTeclaPressionada();

                if(ch == '\n' || ch == '\r') {
                    inicio();
                }else {
                    limparConsole();
                    Imprimir.menuInstruções();
                }
            }
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

    public static void redirecionarPraFuncoesIniciais(int opcaoSelec) {
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

    public static void inicio() {
        int opcaoSelec = 1;
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
        inicio();
    }
}