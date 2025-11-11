public class imprimir {
    
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
        MaisCores.setCombinacoes(196, 17);
        System.out.print("+----------------------------+");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("|  __  __ _____ _   _ _   _  |");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("| |  \\/  | ____| \\ | | | | | |");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("| | |\\/| |  _| |  \\| | | | | |");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("| | |  | | |___| |\\  | |_| | |");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("| |_|  |_|_____|_| \\_|\\___/  |");
        MaisCores.resetColor();
        System.out.println();
        MaisCores.setCombinacoes(196, 17);
        System.out.print("+----------------------------+");
        MaisCores.resetColor();
        System.out.println();
    }

    private static void imprimirOpcoesMenu() {
        MaisCores.setCombinacoes(196, 17);
        System.out.print("|  >JOGAR<      Digite 1     |");
        MaisCores.resetColor();
        System.out.println();

        MaisCores.setCombinacoes(196, 17);
        System.out.print("|                            |");
        MaisCores.resetColor();
        System.out.println();

        MaisCores.setCombinacoes(196, 17);
        System.out.print("|  >OPÇÕES<     Digite 2     |");
        MaisCores.resetColor();
        System.out.println();

        MaisCores.setCombinacoes(196, 17);
        System.out.printf("|                            |");
        MaisCores.resetColor();
        System.out.println();

        MaisCores.setCombinacoes(196, 17);
        System.out.print("|  >INSTRUÇÕES< Digite 3     |");
        MaisCores.resetColor();
        System.out.println();
    }

    public static void menuInicial() {

        imprimirMENU();
        imprimirOpcoesMenu();

        MaisCores.setCombinacoes(196, 17);
        System.out.printf("|============================|");
        MaisCores.resetColor();
        System.out.println();
    }
}
