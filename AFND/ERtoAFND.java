package AFND;

import java.util.ArrayList;

public class ERtoAFND{
    String er; datosAFND datos; elavorar_transiciones transiciones;
    public ERtoAFND(String er){
        this.er=er;
        datos = new datosAFND(er);
        transiciones = new elavorar_transiciones(er);
        procesoContruccion();
    }
    public void procesoContruccion(){
        datos.imprimirEstados();
        datos.imprimirAlfabeto();
        datos.imprimirSyF();
    }
    public ArrayList<String> getAlfabeto(){return datos.getAlfabeto();}
    public ArrayList<Integer> getTodosEstados(){return datos.getTodosEstados();}
    public int getF(){return datos.getF();}
    public int getS(){return datos.getS();}
}
