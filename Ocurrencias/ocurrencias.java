package Ocurrencias;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ocurrencias {
    ArrayList<String> lineas_ocurrencias; int estadoActual;
    ArrayList<Integer> estadosFinales;
    // Array con las posiciones de la linea y la posicion de la letra en donde encontro el estado final
    ArrayList<String> ocurrencias;
    // Array con las posiciones de las lineas en donde se encontro un estado final
    ArrayList<String> posicion_lineas_ocurrencias;
    File AFD = new File("./Archivos/AFD.txt");
    public ocurrencias(ArrayList<String> lineasOcurrencias, ArrayList<Integer> estadosFinales){
        this.lineas_ocurrencias=lineasOcurrencias;
        this.estadosFinales = estadosFinales;
        ocurrencias = new ArrayList<String>();
        posicion_lineas_ocurrencias= new ArrayList<String>();
        estadoActual=0;
        recorrerLineas();
        imprimirOcurrencias();
    }
    public void recorrerLineas(){
        for(int i = 0; i<lineas_ocurrencias.size(); i++){
            recorrerEstados(lineas_ocurrencias.get(i), i+1);
        }
    }
    public void recorrerEstados(String linea, int posicionLinea){
        char[] lineaSeparada = linea.toCharArray();
        for(int i = 0; i<lineaSeparada.length; i++){
            if(lineaSeparada[i] != '-'){
                consumirLetra(""+lineaSeparada[i]);
                // Si el estado actual es final entonces se agrega la posicion de la linea en el arreglo ocurrencias
                if(revisarEstadoFinal(estadoActual)){
                    String ingresarOcurrencia = posicionLinea+"-"+(i+1);
                    ocurrencias.add(ingresarOcurrencia);
                    posicion_lineas_ocurrencias.add(""+posicionLinea);
                    posicion_lineas_ocurrencias = eliminarPosicionesRepetidas(posicion_lineas_ocurrencias);
                }
            }
        }
    }
    public void consumirLetra(String letra){
        String line="", simbolo=""; int posicion=0, estado1=0, estado2=0;
        try {
            Scanner input = new Scanner(AFD);
            while (input.hasNext()) {
                line = input.next();
                if (posicion == 0) {
                    // Estado 1 del afd
                    estado1 = Integer.parseInt(line);
                    posicion++;
                } else if (posicion == 1) {
                     // Simbolo del afd
                     simbolo = line;
                    posicion++;
                }else if(posicion == 2){
                    // Estado 2 del afd
                    estado2 = Integer.parseInt(line);
                    if(estado1==estadoActual && simbolo.equals(letra)){
                        estadoActual=estado2;
                        break;
                    }
                    posicion=0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public boolean revisarEstadoFinal(int estado){
        // Revisa si el estado es estado Final
        for(int i = 0; i<estadosFinales.size(); i++){
            if(estado==estadosFinales.get(i)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> eliminarPosicionesRepetidas(ArrayList<String> al) {
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < al.size(); i++) {
            if (!newList.contains(al.get(i))) {
                newList.add(al.get(i));
            }
        }
        return newList;
    }
    public void imprimirOcurrencias(){
        char[] lineaSeparada;
        System.out.println("");
        System.out.println("Ocurrencias:");
        for(int i = 0; i<posicion_lineas_ocurrencias.size(); i++){
            System.out.print("Linea "+posicion_lineas_ocurrencias.get(i)+": ");
            for(int j = 0; j<ocurrencias.size(); j++){
                lineaSeparada = ocurrencias.get(j).toCharArray();
                if(posicion_lineas_ocurrencias.get(i).equals(""+lineaSeparada[0])){
                    System.out.print(lineaSeparada[2]+" ");
                }
            }
            System.out.println("");
        }
    }
}