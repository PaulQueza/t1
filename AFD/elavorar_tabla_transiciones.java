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

public class elavorar_tabla_transiciones {
    ArrayList<String> alfabeto;
    ArrayList<Integer> todosEstados;
    ArrayList<Integer> listaestados;
    File TH=new File("t1/Archivos/TH.txt");
    File TABLA_TRANSICIONES =new File("t1/Archivos/tabla_transiciones.txt"); FileWriter fw;
    public elavorar_tabla_transiciones(ArrayList<String> alfabeto,ArrayList<Integer> todosEstados){
        try{
            fw = new FileWriter(TABLA_TRANSICIONES);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        listaestados = new ArrayList<Integer>();
        this.alfabeto=alfabeto;
        this.todosEstados=todosEstados;
        tabla_transiciones();
    }
    public void tabla_transiciones(){
        String encabezado=""; String dato;
        // Agregamos el encabesado de la tabla
        for(int h=0;h<alfabeto.size();h++){
            if(h==0){
                encabezado=""+alfabeto.get(h);
            }else{
                encabezado+=" "+alfabeto.get(h);
            }
        }
        System.out.println("ENCABEZADO ----->"+encabezado);
        escribirTablaEncabezado(encabezado, TABLA_TRANSICIONES);
        // Agregamos las transiciones para cada dato
        for(int i=0; i<todosEstados.size();i++){
            escribirTablaTransiciones(""+todosEstados.get(i), TABLA_TRANSICIONES);
            for(int j=0; j<alfabeto.size();j++){
                System.out.println("Estado revisar -->"+todosEstados.get(i)+", Simbolo revisar-->"+alfabeto.get(j));
                leerTransiciones(todosEstados.get(i), alfabeto.get(j), listaestados);
                System.out.print("{"+todosEstados.get(i)+"} U {");
                dato=getListaEstados(listaestados);
                System.out.println("} ");
                if(listaestados.size()>0){
                    escribirTablaTransiciones(dato, TABLA_TRANSICIONES);
                }else{
                    escribirTablaTransiciones("NULL", TABLA_TRANSICIONES);
                }
                
                listaestados=new ArrayList<Integer>();
            }
            espacioTabla(TABLA_TRANSICIONES);
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
    public void escribirTablaTransiciones(String dato, File archivo){
        // Lo usamos para escribir la tabla en el txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.print(dato+" ");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
    public void escribirTablaEncabezado(String alfabeto, File archivo){
        // Lo usamos para escribir la tabla en el txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("q "+alfabeto);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
    public void espacioTabla(File archivo){
        // Lo usamos para escribir la tabla en el txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
    public String getListaEstados(ArrayList<Integer> listaestados){
        String getListaEstados="";
        // Lee la lista de estados
        for(int i=0; i<listaestados.size(); i++){
            if(i==0){
                getListaEstados=""+listaestados.get(i);
            }
            else{
                getListaEstados+=","+listaestados.get(i);
            }
            System.out.print(" "+listaestados.get(i)+" ");
        }
        return getListaEstados;
    }
}
