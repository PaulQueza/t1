import java.util.Scanner;
import AFD.AFNDtoAFD;
import AFND.ERtoAFND;
import ER.desglosador;

public class principal{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String er = in.nextLine();
        desglosador desglosar = new desglosador(er);
        ERtoAFND ertoafnd = new ERtoAFND(desglosar.getErDesglosado());
<<<<<<< Updated upstream:Main/principal.java
        System.out.println("------------------------AFD------------------------");
=======
>>>>>>> Stashed changes:principal.java
        AFNDtoAFD afndtoafd = new AFNDtoAFD(ertoafnd.getTodosEstados(), ertoafnd.getAlfabeto(), ertoafnd.getF());
        in.close();
    }
}