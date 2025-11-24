public class Imprimir {

    public static void resetColor() {
        System.out.print("\u001b[0m");
    }

    public static void setCorFundo256(int cor) {
        if (cor < 0 || cor > 255) {
            resetColor();
            return;
        }
        System.out.print("\u001b[48;5;" + cor + "m");
    }

    public static void setCor256(int cor) {
        if (cor < 0 || cor > 255) {
            resetColor();
            return;
        }
        System.out.print("\u001b[38;5;" + cor + "m");
    }

    public static void setCombinacoes(int corTexto, int corFundo) {
        setCor256(corTexto);
        setCorFundo256(corFundo);
    }

    private static void imprimirMENU() {
        setCombinacoes(196, 17);
        System.out.print("+----------------------------+");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("|  __  __ _____ _   _ _   _  |");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("| |  \\/  | ____| \\ | | | | | |");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("| | |\\/| |  _| |  \\| | | | | |");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("| | |  | | |___| |\\  | |_| | |");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("| |_|  |_|_____|_| \\_|\\___/  |");
        resetColor();
        System.out.println();
        setCombinacoes(196, 17);
        System.out.print("+----------------------------+");
        resetColor();
        System.out.println();
    }

    private static void imprimirOpcoesMenu(int opSelec) {
        if(opSelec == 1) {
            setCombinacoes(196, 17);
            System.out.print("|         ");
            setCor256(7);
            System.out.print(">JOGAR<");
            setCor256(196);
            System.out.print("            |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|         >JOGAR<            |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                            |");
        resetColor();
        System.out.println();

        if(opSelec == 2) {
            setCombinacoes(196, 17);
            System.out.print("|         ");
            setCor256(7);
            System.out.print(">OPÇÕES<");
            setCor256(196);
            System.out.print("           |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|         >OPÇÕES<           |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                            |");
        resetColor();
        System.out.println();

        if(opSelec == 3) {
            setCombinacoes(196, 17);
            System.out.print("|       ");
            setCor256(7);
            System.out.print(">INSTRUÇÕES<");
            setCor256(196);
            System.out.print("         |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|       >INSTRUÇÕES<         |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                            |");
        resetColor();
        System.out.println();

        if(opSelec == 4) {
            setCombinacoes(196, 17);
            System.out.print("|          ");
            setCor256(7);
            System.out.print(">Sair<");
            setCor256(196);
            System.out.print("            |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|          >Sair<            |");
            resetColor();
            System.out.println();
        }
    }

    public static void menuInicial(int opSelec) {

        imprimirMENU();
        imprimirOpcoesMenu(opSelec);

        setCombinacoes(196, 17);
        System.out.printf("|============================|");
        resetColor();
        System.out.println();
    }

    private static void imprimirTopoCampoMinado(Celula[][] campo) {
        int numUnderLines = (campo[0].length * 4) - 1;
        System.out.print(" ");
        for (int i = 0; i < numUnderLines; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    private static void imprimirFinalCampoMinado(Celula[][] campo) {
        int numTracos = (campo[0].length * 4) - 1;
        System.out.print(" ");
        for (int i = 0; i < numTracos; i++) {
            System.out.print("¨");
        }
        System.out.println();
    }

    private static void verificarCorNumero(int bombasProximas) {
        switch (bombasProximas) {
            case 1:
                setCor256(39);
                break;
            case 2:
                setCor256(82);
                break;
            case 3:
                setCor256(196);
                break;
            case 4:
                setCor256(63);
                break;
            case 5:
                setCor256(167);
                break;
            case 6:
                setCor256(153);
                break;
            case 7:
                setCor256(165);
                break;
            case 8:
                setCor256(247);
                break;
            default:
                break;
        }
    }

    private static void verificarSelecNum(Celula[][] campo, int i, int j) {
        if (campo[i][j].estaSelecionado == true) {
            setCor256(7);
        } else {
            verificarCorNumero(campo[i][j].bombasProximas);
        }
    }

    private static void verificarSelecBandeira(Celula[][] campo, int i, int j) {
        if (campo[i][j].estaSelecionado == true) {
            System.out.print("| ");
            setCor256(166);
            System.out.print("P ");
            setCor256(106);
        } else {
            System.out.print("| ");
            setCor256(1);
            System.out.print("P ");
            setCor256(106);
        }
    }

    private static void verificarSelec(Celula[][] campo, int i, int j) {
        if (campo[i][j].estaSelecionado == true) {
            System.out.print("| ");
            setCor256(7);
            System.out.print("* ");
            setCor256(106);
        } else {
            setCor256(106);
            System.out.print("| * ");
        }
    }

    public static void campoMinado(Celula[][] campo) {
        setCor256(106);
        imprimirTopoCampoMinado(campo);
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                setCor256(106);
                if (campo[i][j].foiAberto == true) {
                    System.out.print("| ");
                    verificarSelecNum(campo, i, j);
                    System.out.printf("%d ", campo[i][j].bombasProximas);
                    setCor256(106);
                } else if (campo[i][j].bandeira == true) {
                    verificarSelecBandeira(campo, i, j);
                } else {
                    verificarSelec(campo, i, j);
                }
            }
            System.out.print("|");
            resetColor();
            System.out.println();
        }
        resetColor();
        setCor256(106);
        imprimirFinalCampoMinado(campo);
    }

    public static void fimDeJogo(Celula[][] campo) {
        setCor256(106);
        imprimirTopoCampoMinado(campo);
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                setCor256(106);
                if(campo[i][j].bomba == true) {
                    System.out.print("|");
                    setCombinacoes(i, j);
                    System.out.print(" O ");
                    resetColor();
                }else {
                    System.out.printf("| %d ", campo[i][j].bombasProximas);
                }
            }
            System.out.print("|");
            resetColor();
            System.out.println();
        }
        resetColor();
        setCor256(106);
        imprimirFinalCampoMinado(campo);
    }

    private static void imprimirGANHOU() {
        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  _____          _   _ _    _  ____  _    _ |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| / ____|   /\\   | \\ | | |  | |/ __ \\| |  | ||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|| |  __   /  \\  |  \\| | |__| | |  | | |  | ||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|| | |_ | / /\\ \\ | . ` |  __  | |  | | |  | ||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|| |__| |/ ____ \\| |\\  | |  | | |__| | |__| ||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| \\_____/_/    \\_\\_| \\_|_|  |_|\\____/ \\____/ |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
        System.out.println();
    }

    private static void imprimirPERDEU() {
        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| _____  ______ _____  _____  ______ _    _  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("||  __ \\|  ____|  __ \\|  __ \\|  ____| |  | | |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|| |__) | |__  | |__) | |  | | |__  | |  | | |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("||  ___/|  __| |  _  /| |  | |  __| | |  | | |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|| |    | |____| | \\ \\| |__| | |____| |__| | |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("||_|    |______|_|  \\_\\_____/|______|\\____/  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
        System.out.println();
    }

    private static void imprimirOPCOES() {
        setCombinacoes(196, 17);
        System.out.print("+-------------------------------------------+");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|   ____  _____   _____ ____  ______  _____ |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  / __ \\|  __ \\ / ____/ __ \\|  ____|/ ____||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| | |  | | |__) | |   | |  | | |__  | (___  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| | |  | |  ___/| |   | |  | |  __|  \\___ \\ |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| | |__| | |    | |___| |__| | |____ ____) ||");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  \\____/|_|     \\_____\\____/|______|_____/ |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|                 )_)                       |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("+-------------------------------------------+");
        resetColor();
        System.out.println();
    }

    private static void imprimirOpcoesMenuFinal(int opSelc) {
        setCombinacoes(196, 17);
        System.out.print("|                                            |");
        resetColor();
        System.out.println();

        if(opSelc == 1) {
            setCombinacoes(196, 17);
            System.out.print("|               ");
            setCor256(7);
            System.out.print(">Ir de novo<");
            setCor256(196);
            System.out.print("                 |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|               >Ir de novo<                 |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                                            |");
        resetColor();
        System.out.println();

        if(opSelc == 2) {
            setCombinacoes(196, 17);
            System.out.print("|               ");
            setCor256(7);
            System.out.print(">Ver pontuação<");
            setCor256(196);
            System.out.print("              |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|               >Ver pontuação<              |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                                            |");
        resetColor();
        System.out.println();

        if(opSelc == 3) {
            setCombinacoes(196, 17);
            System.out.print("|                  ");
            setCor256(7);
            System.out.print(">Sair<");
            setCor256(196);
            System.out.print("                    |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|                  >Sair<                    |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                                            |");
        resetColor();
        System.out.println();
        
    }

    public static void menuPerdeu(int opSelc) {
        imprimirPERDEU();
        imprimirOpcoesMenuFinal(opSelc);

        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
    }

    public static void menuGanhou(int opSelc) {
        imprimirGANHOU();
        imprimirOpcoesMenuFinal(opSelc);

        setCombinacoes(196, 17);
        System.out.print("+--------------------------------------------+");
        resetColor();
    }

    private static void imprimirOpcoesMenusOpcao(int opSelec, int opTam, int opBombas) {
        setCombinacoes(196, 17);
        System.out.print("|                                           |");
        resetColor();
        System.out.println();

        if(opSelec == 1) {
            if(opTam == 1) {
                setCombinacoes(196, 17);
                System.out.print("|             Tamanho: ");
                setCor256(7);
                System.out.print("<9x9>");
                setCor256(196);
                System.out.print("                |");
                resetColor();
                System.out.println();
            }if(opTam == 2) {
                setCombinacoes(196, 17);
                System.out.print("|            Tamanho: ");
                setCor256(7);
                System.out.print("<12x12>");
                setCor256(196);
                System.out.print("               |");
                resetColor();
                System.out.println();
            }if(opTam == 3) {
                setCombinacoes(196, 17);
                System.out.print("|            Tamanho: ");
                setCor256(7);
                System.out.print("<15x15>");
                setCor256(196);
                System.out.print("               |");
                resetColor();
                System.out.println();
            }
        }else {
            if(opTam == 1) {
                setCombinacoes(196, 17);
                System.out.print("|             Tamanho: <9x9>                |");
                resetColor();
                System.out.println();
            }if(opTam == 2) {
                setCombinacoes(196, 17);
                System.out.print("|             Tamanho: <12x12>              |");
                resetColor();
                System.out.println();
            }if(opTam == 3) {
                setCombinacoes(196, 17);
                System.out.print("|             Tamanho: <15x15>              |");
                resetColor();
                System.out.println();
            }
        }

        setCombinacoes(196, 17);
        System.out.print("|                                           |");
        resetColor();
        System.out.println();

        if(opSelec == 2) {
            if(opBombas >= 100) {
                setCombinacoes(196, 17);
                System.out.print("|         Número de bombas: ");
                setCor256(7);
                System.out.printf("<%d>", opBombas);
                setCor256(196);
                System.out.print("           |");
                resetColor();
                System.out.println();
            }else {
                setCombinacoes(196, 17);
                System.out.print("|         Número de bombas: ");
                setCor256(7);
                System.out.printf("<%d>", opBombas);
                setCor256(196);
                System.out.print("            |");
                resetColor();
                System.out.println();
            }
        }else {
            if(opBombas >= 100) {
                setCombinacoes(196, 17);
                System.out.printf("|         Número de bombas: <%d>           |", opBombas);
                resetColor();
                System.out.println();
            }else {
                setCombinacoes(196, 17);
                System.out.printf("|         Número de bombas: <%d>            |", opBombas);
                resetColor();
                System.out.println();
            }
        }

        if(opTam == 1) {
            setCombinacoes(196, 17);
            System.out.print("|          Recomendado: 10 à 20             |");
            resetColor();
            System.out.println();
        }if(opTam == 2) {
            setCombinacoes(196, 17);
            System.out.print("|          Recomendado: 20 à 40             |");
            resetColor();
            System.out.println();
        }if(opTam == 3) {
            setCombinacoes(196, 17);
            System.out.print("|          Recomendado: 40 à 70             |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                                           |");
        resetColor();
        System.out.println();

        if(opSelec == 3) {
            setCombinacoes(196, 17);
            System.out.print("|                  ");
            setCor256(7);
            System.out.print("<Sair>");
            setCor256(196);
            System.out.print("                   |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|                 <Sair>                    |");
            resetColor();
            System.out.println();
        }

        setCombinacoes(196, 17);
        System.out.print("|                                           |");
        resetColor();
        System.out.println();
    }

    public static void menuOpçoes(int opcaoSelec, int opTam, int opBombas) {
        imprimirOPCOES();
        imprimirOpcoesMenusOpcao(opcaoSelec, opTam, opBombas);

        setCombinacoes(196, 17);
        System.out.print("+-------------------------------------------+");
        resetColor();
        System.out.println();
    }

    public static void menuInstruções() {
        setCombinacoes(196, 17);
        System.out.print("+------------------------------------------------------------------------+");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  _____ _   _  _____ _______ _____  _    _  _____ ____  ______  _____   |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| |_   _| \\ | |/ ____|__   __|  __ \\| |  | |/ ____/ __ \\|  ____|/ ____|  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|   | | |  \\| | (___    | |  | |__) | |  | | |   | |  | | |__  | (___    |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|   | | | . ` |\\___ \\   | |  |  _  /| |  | | |   | |  | |  __|  \\___ \\   |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  _| |_| |\\  |____) |  | |  | | \\ \\| |__| | |___| |__| | |____ ____) |  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("| |_____|_| \\_|_____/   |_|  |_|  \\_\\\\____/ \\_____\\____/|______|_____/   |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|                                             )_)                        |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("+------------------------------------------------------------------------+");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|Use W, A, S, D para se mover nos menus e no jogo. Quando estiver jogando|");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|aperte \"C\" para cavar uma célula e \"B\" para marca-lá como bomba, se você|");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|cavar um local onde estiver uma bomba é fim de jogo, se conseguir marcar|");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|todas as células com bombas, você ganha. A cada vez que você acerta onde|");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|uma bomba esta, você ganha pontos, sua pontuação final é baseada nisso e|");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|no tempo demorado. Você pode ajustar o tamanho do campo e a quantidade  |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|de bombas em opções. Se conseguir uma pontuação boa pode entrar no rank |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|dos top 20 melhores jogadores. Para voltar aperte enter.                |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("+------------------------------------------------------------------------+");
        resetColor();
        System.out.println();
    }
 
    public static void pontuacao(int pontuacao) {
        setCombinacoes(196, 17);
        System.out.printf("Sua pontuação é: %d", pontuacao );
        resetColor();
        System.out.println();
        System.out.println("Pressione enter para volta ao início.");
    }
}