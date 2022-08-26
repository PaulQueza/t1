package AFD;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class elavorar_tabla_transiciones {
    ArrayList<String> alfabeto;
    ArrayList<Integer> todosEstados;
    ArrayList<Integer> listaestados;
    File TH;
    public elavorar_tabla_transiciones(ArrayList<String> alfabeto,ArrayList<Integer> todosEstados){
        TH = new File("Archivos/TH.txt");
        listaestados = new ArrayList<Integer>();
        this.alfabeto=alfabeto;
        this.todosEstados=todosEstados;
        tabla_transiciones();
    }
    public void tabla_transiciones(){
        for(int i=0; i<todosEstados.size();i++){
            for(int j=0; j<alfabeto.size();j++){
                System.out.println("Estado revisar -->"+todosEstados.get(i)+", Simbolo revisar-->"+alfabeto.get(j));
                leerTransiciones(todosEstados.get(i), alfabeto.get(j), listaestados);
                System.out.print("{"+todosEstados.get(i)+"} U {");
                leerListaEstados(listaestados);
                System.out.println("} ");
            }
        }
    }
    public ArrayList<Integer> leerTransiciones(int estadoBuscar, String simboloBuscar,ArrayList<Integer> listaestados){
        // Encuentra todos los epsilon de un estado
        String simbolo="",line="";int estado1=0,estado2=0,posicion=0;
        try {
            Scanner input = new Scanner(TH);
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
                    // Primer estado
                    estado1=Integer.parseInt(line);
                    posicion++;
                }else if(posicion==1){
                    // Simbolo
                    simbolo=line;
                    posicion++;
                }else{
                    // Segundo estado
                    estado2=Integer.parseInt(line);
                    if(estado1==estadoBuscar && simbolo.equals(simboloBuscar)){
                        System.out.println("Se encontro una transicion = {q"+estado1+", "+simbolo+", q"+estado2+"}");
                        listaestados.add(estado2);
                    }
                    posicion=0;
                }
                
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        HashSet<Integer> set = new HashSet<>(listaestados);
        listaestados.clear();
        listaestados.addAll(set);
        Collections.sort(listaestados);
        return listaestados;
    }
    public void leerListaEstados(ArrayList<Integer> listaestados){
        // Lee la lista de estados
        for(int i=0; i<listaestados.size(); i++){
            System.out.print(" "+listaestados.get(i)+" ");
        }
    }
}
