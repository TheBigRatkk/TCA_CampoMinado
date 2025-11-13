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

    private static void imprimirOpcoesMenu() {
        setCombinacoes(196, 17);
        System.out.print("|  >JOGAR<      Digite 1     |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|                            |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  >OPÇÕES<     Digite 2     |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.printf("|                            |");
        resetColor();
        System.out.println();

        setCombinacoes(196, 17);
        System.out.print("|  >INSTRUÇÕES< Digite 3     |");
        resetColor();
        System.out.println();
    }

    public static void menuInicial() {

        imprimirMENU();
        imprimirOpcoesMenu();

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

    public static void campoMinado(Celula[][] campo) {
        setCor256(28);
        imprimirTopoCampoMinado(campo);
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                setCombinacoes(46, 28);
                if(campo[i][j].foiAberto == false) {
                    System.out.print("| * ");
                }else {
                    System.out.printf("| %d ", campo[i][j].bombasProximas);
                }
            }
            System.out.print("|");
            resetColor();
            System.out.println();
        }
        resetColor();
        setCor256(28);
        imprimirFinalCampoMinado(campo);
    }
}
