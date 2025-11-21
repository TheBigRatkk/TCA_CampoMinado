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
            System.out.print(">Ver ranking<");
            setCor256(196);
            System.out.print("                |");
            resetColor();
            System.out.println();
        }else {
            setCombinacoes(196, 17);
            System.out.print("|               >Ver ranking<                |");
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
}
/*
+--------------------------------------------+
|  _____          _   _ _    _  ____  _    _ |
| / ____|   /\   | \ | | |  | |/ __ \| |  | ||
|| |  __   /  \  |  \| | |__| | |  | | |  | ||
|| | |_ | / /\ \ | . ` |  __  | |  | | |  | ||
|| |__| |/ ____ \| |\  | |  | | |__| | |__| ||
| \_____/_/    \_\_| \_|_|  |_|\____/ \____/ |
+--------------------------------------------+
|                                            |
|               >Ir de novo<                 |
|                                            |
|              >Ver pontuação<               |
|                                            |
|                  >Sair<                    |
|                                            |
+--------------------------------------------+
*/

/* +-------------------------------------------+
   | _____  ______ _____  _____  ______ _    _ |
   ||  __ \|  ____|  __ \|  __ \|  ____| |  | ||
   || |__) | |__  | |__) | |  | | |__  | |  | ||
   ||  ___/|  __| |  _  /| |  | |  __| | |  | ||
   || |    | |____| | \ \| |__| | |____| |__| ||
   ||_|    |______|_|  \_\_____/|______|\____/ |
   +-------------------------------------------+                                       
                                            



*/