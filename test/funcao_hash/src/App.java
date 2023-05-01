
public class App {
    public static void main(String[] args) throws Exception {
        int chave = 31;
        int profundidade = 2;
        int result1 = (int) Math.pow(2, profundidade);
        int result = chave % (int) Math.pow(2, profundidade);

        int qnt_buckets_cria;

        int profundidade_diretorio = 4;

        if (profundidade_diretorio != 1) {
            qnt_buckets_cria = (int) Math.pow(2, profundidade_diretorio) / 2;
        } else {
            qnt_buckets_cria = 2;
        }


        System.out.println(qnt_buckets_cria);
    }
}
