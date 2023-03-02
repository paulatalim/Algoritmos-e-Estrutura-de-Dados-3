import java.util.Date;
import java.text.SimpleDateFormat;

public class App {
    public static void main(String[] args) throws Exception {
        //marca a data de hj
        Date hoje = new Date();

        long horario = System.currentTimeMillis();

        //transforma a data de millisgundos para a data atual
        Date d2 = new Date(horario);

        //System.out.println(hoje);
        System.out.println(d2);


        //Formata a data
        SimpleDateFormat formatar_data = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        String data_formatada = formatar_data.format(d2);
        System.out.println(data_formatada);
    }
}
