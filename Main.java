import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws IOException {

        salvarDados(15, 3200, "Jogador01");
        carregarDados();

    }
    
    /**
     * Salva os dados do jogador em um arquivo de texto
     * 
     * Recebe o nivel, qtd de moedas, e o nome do jogador,
     * 
     * Usa File() pra pegar o nome do arquivo
     * 
     * Usa FileWriter(arquivo) pra escrever no arquivo
     * a variavel do tipo FileWriter tem o metodo write() pra escrever no arquivo
     * Após escrever no arquivo, usa o close() pra fechar o arquivo
     * Se o close() nao for usado, o arquivo de save fica corrompido
     * 
     */
    public static void salvarDados(int nivel, int moedas, String nome) throws IOException {

        File arquivo = new File("save.txt");
        
        FileWriter escritor = new FileWriter(arquivo);

        escritor.write("nivel " + nivel + "\n");
        escritor.write("moedas " + moedas + "\n");
        escritor.write("nome " + nome + "\n");
        
        escritor.close();
            
        System.out.println("Dados salvos com sucesso!");
            
    }
    
    /**
     * cria variaveis para os dados que vai receber:
     * nivel, moedas e nome
     * 
     * Usa File() pra pegar o nome do arquivo
     * 
     * Usa Scanner(arquivo) pra ler o arquivo
     * 
     * while (leitor.hasNext()) que le até nao ter mais dados (EOF)
     * 
     * se o proximo dado for nivel -> coloca na variavel nivel
     * se o proximo dado for moedas -> coloca na variavel moedas
     * se o proximo dado for nome -> coloca na variavel nome
     * 
     * para isso foi usado um switch case pra identificar o tipo de dado
     * e colocar na variavel correspondente
     * 
     * Usa leitor.close() pra fechar o arquivo
     * se o close() nao for usado, o arquivo de save fica corrompido
     * 
     */
    public static void carregarDados() throws IOException {

        int nivel = 0;
        int moedas = 0;
        String nome = null;

        File arquivo = new File("save.txt");

        // Usando scanner pra ler o arquivo, new Scanner(arquivo);
        Scanner leitor = new Scanner(arquivo);

        // o hasNext() que le até nao ter mais dados (EOF)
        while (leitor.hasNext()) {

            String tipo = leitor.next();

            switch (tipo) {

                case "nivel":
                    nivel = leitor.nextInt();
                    break;

                case "moedas":
                    moedas = leitor.nextInt();
                    break;

                case "nome":
                    nome = leitor.next();
                    break;

            }
            
        }

        leitor.close();
            
        System.out.println("\nDados carregados:");
        System.out.println("Nível: " + nivel);
        System.out.println("Moedas: " + moedas);
        System.out.println("Nome: " + nome);

    }

}
