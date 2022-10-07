import java.util.ArrayList;
import java.util.Scanner;
import AFD.AFNDtoAFD;
import AFND.ERtoAFND;
import ER.desglosador;
import Ocurrencias.ocurrencias;

public class principal{
    public static void main(String[] args){
        ArrayList<String> listaParaOcurrencias = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        String er = in.nextLine();

        String lineaOcurrencia = in.nextLine();
        listaParaOcurrencias.add(lineaOcurrencia);
        while(tieneSiguiente(lineaOcurrencia)){
            lineaOcurrencia = in.nextLine();
            listaParaOcurrencias.add(lineaOcurrencia);
        }
        desglosador desglosar = new desglosador(er);
        ERtoAFND ertoafnd = new ERtoAFND(desglosar.getErDesglosado());
        AFNDtoAFD afndtoafd = new AFNDtoAFD(ertoafnd.getTodosEstados(), ertoafnd.getAlfabeto(), ertoafnd.getF());
        ocurrencias ocurrencia = new ocurrencias(listaParaOcurrencias, afndtoafd.getEstadosFinales());
        in.close();
    }
    public static boolean tieneSiguiente(String lineOcurrencia){
        char[] revisarSiguiente = lineOcurrencia.toCharArray();
        if(revisarSiguiente[revisarSiguiente.length-1]=='-'){
            return true;
        }else{
            return false;
        }
    }
}
