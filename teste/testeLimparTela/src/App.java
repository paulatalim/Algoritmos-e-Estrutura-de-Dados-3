
public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("Olaaaaa");

        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
        else
		    Runtime.getRuntime().exec("read -p 'Pressione qualquer tecla para continuar. . .'");


        //Limpa a tela no windows, no linux e no MacOS
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
		    Runtime.getRuntime().exec("clear");

        //trava a tela
        //System.in.read();

        System.out.println("foiii");
    }
}
