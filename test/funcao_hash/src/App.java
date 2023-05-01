
public class App {
    public static void main(String[] args) throws Exception {
        int chave = 31;
        int profundidade = 2;
        int result1 = (int) Math.pow(2, profundidade);
        int result = chave % (int) Math.pow(2, profundidade);

        System.out.println(result);
    }
}
