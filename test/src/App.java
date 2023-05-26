import java.math.BigDecimal;

public class App {
    public static void main(String[] args) throws Exception {
        String msg = "Paula";

        BigDecimal e, n, d ,p, q, z;

        p = new BigDecimal(15649);
        q = new BigDecimal(20479);
        d = new BigDecimal(335539);

        //Calculo do valor de n -> n = p * q
        n = p.multiply(q);

        // Calculo do valor de z -> z = (p - 1) * (q - 1)
        z = p.add(BigDecimal.valueOf(-1)).multiply(q.add(BigDecimal.valueOf(-1)));

        //Calculo do numero e -> e = (z + 1) / d
        e = z.add(BigDecimal.valueOf(1)).divide(d);

        String resultado = "";
        String result;

        //Cifra cada letra da mensagem
        for (int i = 0; i < msg.length(); i++) {
            result = new BigDecimal(msg.charAt(i)).pow(e.intValue()).remainder(n).toString();

            //resultado = (letra ^ e) % n
            resultado += result.length() + result;

            System.out.println(result.length());
        }

        System.out.println(resultado);
        System.out.println(Long.parseLong(resultado));

        // System.out.println(new BigDecimal('A').pow(e.intValue()).remainder(n).toString());
        // System.out.println(new BigDecimal('a').pow(e.intValue()).remainder(n).toString());
        // System.out.println(new BigDecimal('Z').pow(e.intValue()).remainder(n).toString());

        // System.out.println(Double.toHexString(Math.pow((double) 'A', e)));
        // System.out.println(Math.pow((double) 'a', e));
        // System.out.println(Math.pow((double) 'Z', e));

        // for (int i = 0; i < resultado.length(); i++) {
        //     System.out.print((byte) resultado.charAt(i));
        // }
       

        // for (int i = 0; i < resultado.length(); i++)
    }
}
