package AFND;
import java.util.ArrayList;

public class ERtoAFND{
    String er; datosAFND datos; elaborar_transiciones transiciones;
    public ERtoAFND(String er){
        this.er=er;
        datos = new datosAFND(er);
<<<<<<< Updated upstream
        transiciones = new elaborar_transiciones(er,datos.getAlfabeto());
=======
        datos.obtenerAlfabeto();
        transiciones = new elaborar_transiciones(er,datos.getAlfabeto());
        datos.obtenerEstados();
        datos.obtenerSyF();
>>>>>>> Stashed changes
        procesoContruccion();
    }
    public void procesoContruccion(){
        datos.imprimirEstados();
        datos.imprimirAlfabeto();
        datos.imprimirAFND();
        datos.imprimirSyF();
    }
    public ArrayList<String> getAlfabeto(){return datos.getAlfabeto();}
    public ArrayList<Integer> getTodosEstados(){return datos.getTodosEstados();}
    public int getF(){return datos.getF();}
    public int getS(){return datos.getS();}
}