public class Celula {
    int bombasProximas;
    boolean bomba;
    boolean bandeira;
    boolean foiAberto;

    public static void criarCelulas(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                campo[i][j] = new Celula();
            }
        }
    }

    public static void limparBombas(Celula[][] campoMinado) {
        for (int i = 0; i < campoMinado.length; i++) {
            for (int j = 0; j < campoMinado[0].length; j++) {
                campoMinado[i][j].bomba = false;
            }
        }
    }

    public static void definirBombas(Celula[][] campoMinado, int qtdBombas) {
        for(int i = 0; i < qtdBombas; i++) {
            while(true) {
                int X = (int)(Math.random() * campoMinado.length);
                int Y = (int)(Math.random() * campoMinado[0].length);
                if(campoMinado[X][Y].bomba == false) {
                    campoMinado[X][Y].bomba = true;
                    break;
                }
            }
        }
    }

    public static void definirBombasProximas(Celula[][] campo) {
        int deslocamentoI;
        int deslocamentoJ;
        int bombasAoRedor;
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                bombasAoRedor = 0;
                if(campo[i][j].bomba) {
                    continue;
                }
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        deslocamentoI = i + x;
                        deslocamentoJ = y + j;
                        if ((deslocamentoI >= 0 && deslocamentoI < campo.length) &&
                            (deslocamentoJ >= 0 && deslocamentoJ < campo[0].length)) {
                            if (campo[deslocamentoI][deslocamentoJ].bomba) {
                                bombasAoRedor++;
                            }
                        }
                    }
                    
                }
                campo[i][j].bombasProximas = bombasAoRedor;
            }
        }
    }
}
