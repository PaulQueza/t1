package Main;

import java.util.Scanner;
import AFD.AFNDtoAFD;
import AFND.ERtoAFND;
import ER.desglosador_er;

public class principal{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String er = in.nextLine();
        desglosador_er desglosar = new desglosador_er(er);
        System.out.println("------------------------AFND------------------------");
        ERtoAFND ertoafnd = new ERtoAFND(desglosar.getErDesglosado());
        System.out.println("------------------------AFD------------------------");
        AFNDtoAFD afndtoafd = new AFNDtoAFD(ertoafnd.getTodosEstados(), ertoafnd.getAlfabeto());

        in.close();
    }
}
