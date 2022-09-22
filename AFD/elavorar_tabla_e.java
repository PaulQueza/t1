package AFD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class elavorar_tabla_e {
    ArrayList<Integer> listaestados= new ArrayList<Integer>();
    ArrayList<Integer> todosEstados= new ArrayList<Integer>();
    File TH=new File("./Archivos/TH.txt");
    File TABLA_E=new File("./Archivos/tabla_e.txt"); FileWriter fw;

    public elavorar_tabla_e(ArrayList<Integer> todosEstados){
        try{
            fw = new FileWriter(TABLA_E);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
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
            System.out.println("Escribir en la tabla ->");
            leerListaEstados(listaestados);
            escribirTablaE(todosEstados.get(posicionEstados), getListaEstados(listaestados), TABLA_E);
            listaestados=new ArrayList<Integer>();
            posicionEstados++;
        }
    }
    public void escribirTablaE(int estado,String conjunto, File archivo){
        // Lo usamos para escribir la tabla en el txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println(estado+" "+conjunto);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
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
    public String getListaEstados(ArrayList<Integer> listaestados){
        String generarListaEstados="";
        // Lee la lista de estados
        for(int i=0; i<listaestados.size(); i++){
            if(i==0){
                generarListaEstados=""+listaestados.get(i);
            }else{
                generarListaEstados=generarListaEstados+","+listaestados.get(i);
            }
        }
        if(generarListaEstados.equals("")){
            generarListaEstados="NULL";
        }
        return generarListaEstados;
    }
    public void leerListaEstados(ArrayList<Integer> listaestados){
        // Lee la lista de estados
        for(int i=0; i<listaestados.size(); i++){
                System.out.println(" "+listaestados.get(i)+" ");
        }
    }
}
