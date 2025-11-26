public class Celula {
    int bombasProximas;
    boolean bomba;
    boolean bandeira;
    boolean foiAberto;
    boolean estaSelecionado;

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

    public static void abrirCampo(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if(campo[i][j].bombasProximas == 0 && campo[i][j].bomba == false) {
                    campo[i][j].foiAberto = true;
                }
            }
        }
    }

    public static void definirBombasProximas(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                int bombasAoRedor = 0;

                if (campo[i][j].bomba) {
                    campo[i][j].bombasProximas = 0;
                    continue;
                }

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) {
                            continue;
                        }

                        int ni = i + dx;
                        int nj = j + dy;

                        if (ni >= 0 && ni < campo.length && nj >= 0 && nj < campo[0].length) {
                            if (campo[ni][nj].bomba) {
                                bombasAoRedor++;
                            }
                        }
                    }
                }
                campo[i][j].bombasProximas = bombasAoRedor;
            }
        }
    }

    public static void abrirCelulasAdjacentes(Celula[][] campo) {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {

                if (campo[i][j].bombasProximas == 0 && campo[i][j].bomba == false) {

                    for(int dx = -1; dx <= 1; dx++) {
                        for(int dy = -1; dy <= 1; dy++) {

                            if (dx == 0 & dy == 0) {
                                continue;
                            }

                            int ni = i + dx;
                            int nj = j + dy;

                            if (ni >= 0 && ni < campo.length && nj >= 0 && nj < campo[0].length) {
                                if (campo[ni][nj].bomba == false) {
                                    campo[ni][nj].foiAberto = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}