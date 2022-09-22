package Main;

import java.util.Scanner;
import AFD.AFNDtoAFD;
import AFND.ERtoAFND;
import ER.desglosador_er;
import ER.desglosador;

public class principal{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String er = in.nextLine();
        /* 
        System.out.println("------------------------FUNCION ROBAO------------------------");
        desglosador_er desglosarROBAO = new desglosador_er(er);
        */
        desglosador desglosar = new desglosador(er);
        System.out.println("er --->"+desglosar.getErDesglosado());
        System.out.println("------------------------AFND------------------------");
        ERtoAFND ertoafnd = new ERtoAFND(desglosar.getErDesglosado());
        System.out.println("------------------------AFD------------------------");
        AFNDtoAFD afndtoafd = new AFNDtoAFD(ertoafnd.getTodosEstados(), ertoafnd.getAlfabeto());
        in.close();
    }
}
