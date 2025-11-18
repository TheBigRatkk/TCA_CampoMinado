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
        setCor256(106);
        imprimirTopoCampoMinado(campo);
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                setCor256(106);
                if(campo[i][j].foiAberto == false) {
                    if(campo[i][j].estaSelecionado == true) {
                        System.out.print("| ");
                        setCor256(7);
                        System.out.print("* ");
                        setCor256(106);
                    }else {
                        System.out.print("| * ");
                    }
                }else if(campo[i][j].foiAberto == true) {
                    System.out.printf("| %d ", campo[i][j].bombasProximas);
                }else if(campo[i][j].bandeira == true) {
                    System.out.print("| P ");
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
}
