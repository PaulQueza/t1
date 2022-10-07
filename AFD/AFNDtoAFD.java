package AFD;

import java.util.ArrayList;

public class AFNDtoAFD {
    elaborar_tabla_e tabla_epsilon;
    elaborar_tabla_transiciones tabla_transiciones;
    elaborar_tabla_final tabla_final;
    datosAFD datos;
    public AFNDtoAFD(ArrayList<Integer> todosEstados,ArrayList<String> alfabeto, int estadoFinal){
        tabla_epsilon = new elaborar_tabla_e(todosEstados);
        tabla_transiciones = new elaborar_tabla_transiciones(alfabeto,todosEstados);
        tabla_final = new elaborar_tabla_final(alfabeto,estadoFinal);
        datos = new datosAFD(alfabeto);

        procesoContruccion();
    }

    public void procesoContruccion(){
        // Aqui se imprimen todos los datos
        datos.imprimirEstados();
        datos.imprimirAlfabeto();
        datos.imprimirAFD();
        datos.imprimirSyF();
    }
    public ArrayList<Integer> getEstadosFinales(){
        return datos.getEstadosFinales();
    }
}
