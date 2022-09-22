package AFD;

import java.util.ArrayList;

public class AFNDtoAFD {
    elavorar_tabla_e tabla_epsilon;
    elavorar_tabla_transiciones tabla_transiciones;
    elavorar_tabla_final tabla_final;
    datosAFD datos;
    public AFNDtoAFD(ArrayList<Integer> todosEstados,ArrayList<String> alfabeto){
        tabla_epsilon = new elavorar_tabla_e(todosEstados);
        tabla_transiciones = new elavorar_tabla_transiciones(alfabeto,todosEstados);
        tabla_final = new elavorar_tabla_final(alfabeto);
        datos = new datosAFD(alfabeto);

        procesoContruccion();
    }

    public void procesoContruccion(){
        // Aqui se imprimen todos los datos
        datos.imprimirEstados();
        datos.imprimirAlfabeto();
        datos.imprimirSyF();
    }
    
}
