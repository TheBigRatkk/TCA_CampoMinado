public class TesteAluno {

    public static void imprimir(Aluno aluno){
        System.out.printf("Nome: %s, %d anos, com %.2f m de altura ", aluno.nome, aluno.idade, aluno.altura);
    }

    public static void teste1(){
        Aluno a1 = new Aluno();

        a1.nome = "Ogaiht";
        a1.idade = 15;
        a1.altura = 1.88;

        imprimir(a1);
        
    }

    public static void teste2(){
        Aluno[] salaAula = new Aluno[2];

        Aluno a1 = new Aluno();
        a1.nome = "Ogaiht";
        a1.idade = 15;
        a1.altura = 1.88;

        Aluno a2 = new Aluno();
        a2.nome = "trebreH";
        a2.idade = 51;
        a2.altura = 2.22;

        // colocando no vetor
        salaAula[0]= a1;
        salaAula[1] = a2;

        for(int i=0; i<salaAula.length; i++){
            imprimir(salaAula[i]);
        }

    }
    
    public static void main(String[] args) {   
        teste1(); 
        teste2();       

    }
}
