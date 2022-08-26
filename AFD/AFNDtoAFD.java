package AFD;

import java.util.ArrayList;

public class AFNDtoAFD {

    public AFNDtoAFD(ArrayList<Integer> todosEstados,ArrayList<String> alfabeto){
        elavorar_tabla_e tabla_epsilon = new elavorar_tabla_e(todosEstados);
        elavorar_tabla_transiciones tabla_transiciones = new elavorar_tabla_transiciones(alfabeto,todosEstados);
        procesoContruccion();
    }
    public void procesoContruccion(){
        // Aqui se imprimen todos los datos
    }
    
}
