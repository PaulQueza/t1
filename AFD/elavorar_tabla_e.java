package AFD;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class elavorar_tabla_e {
    ArrayList<Integer> listaestados= new ArrayList<Integer>();
    ArrayList<Integer> todosEstados= new ArrayList<Integer>();
    File TH=new File("Archivos/TH.txt");
    public elavorar_tabla_e(ArrayList<Integer> todosEstados){
        this.todosEstados=todosEstados;
        tabla_clausura_e();
    }
    public void tabla_clausura_e(){
        /*  estadosbuscados = posicion para todos los estados encontrados con epsilon
        *   posicionEstados = posicion de los estados del AFND
        */
        int estadosbuscados=0,posicionEstados=0;
        while(todosEstados.size()>posicionEstados){
            todosEstados.get(posicionEstados);
            System.out.println("Estado Revisar --> "+todosEstados.get(posicionEstados));
            listaestados=leerEpsilon(todosEstados.get(posicionEstados), listaestados);
            System.out.println("ListaEstado.Size("+listaestados.size()+") > estadosbuscados("+estadosbuscados+")");
            while(listaestados.size()>(estadosbuscados+1)){
                estadosbuscados++;
                System.out.println("ESTADO REVISAR DENTRO --> "+listaestados.get(estadosbuscados));
                listaestados=leerEpsilon(listaestados.get(estadosbuscados), listaestados);
                System.out.println("ListaEstado.Size("+listaestados.size()+") > estadosbuscados("+estadosbuscados+")");
            }
            System.out.println(" ");
            // -----
            System.out.print("{"+todosEstados.get(posicionEstados)+"} U {");
            leerListaEstados(listaestados);
            System.out.print("}");
            System.out.println(" "); 
            System.out.println(" ");
            listaestados=new ArrayList<Integer>();
            posicionEstados++;
        }
    }
    public void leerArchivo(File archivo){
        String line="", simbolo="";int estado1=0,estado2=0,posicion=0;
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
                    // Ultimo estado
                    estado2=Integer.parseInt(line);
                    posicion=0;
                }    
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList<Integer> leerEpsilon(int estadoBuscar,ArrayList<Integer> listaestados){
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
                    if(estado1==estadoBuscar && simbolo.equals("_")){
                        System.out.println("Se encontro un epsilon = {q"+estado1+", "+simbolo+", q"+estado2+"}");
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
