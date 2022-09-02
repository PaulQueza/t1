package AFND;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class datosAFND{
    String er;
    ArrayList<String> alfabeto;
    ArrayList<Integer> todos_estados;
    int s,f;
    File TH=new File("t1/Archivos/TH.txt");

    public datosAFND(String er){
        this.er=er;
        alfabeto= new ArrayList<String>();
        todos_estados= new ArrayList<Integer>();
        s=0;f=0;
        obtenerAlfabeto();
        obtenerEstados();
        obtenerSyF();
    }
    public void obtenerAlfabeto(){
        /* Para el alfabeto, guardamos todos los caracteres no "especiales" 
        para luego utilizar "HashSet" que nos eliminara los repetidos si es que llegan a haber */
        char[] er_char = er.toCharArray();
        for(int i=0; i<er_char.length;i++){
            if(er_char[i]!='|' && er_char[i]!='*' && er_char[i]!='.' && er_char[i]!='_' && er_char[i]!='~' && er_char[i]!='(' && er_char[i]!=')'){
                alfabeto.add(""+er_char[i]);
            }
        }
        HashSet<String> set = new HashSet<>(alfabeto);
        alfabeto.clear();
        alfabeto.addAll(set);
    }
    public void obtenerEstados(){
        String line="";int posicion=0,estadoFinal=0;
        try {
            Scanner input = new Scanner(TH);
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
                    // Primer estado
                    posicion++;
                }else if(posicion==1){
                    // Simbolo
                    posicion++;
                }else{
                    // Segundo estado
                    estadoFinal=Integer.parseInt(line);
                    posicion=0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for(int i=0;i<estadoFinal;i++){
            System.out.println("Estado agregado --->"+i);
            todos_estados.add(i);
        }
    }
    public void obtenerSyF(){
        int primero=0,posicion=0,estadoFinal=0, estadoInicial=0;String line="";
        try {
            Scanner input = new Scanner(TH);
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
                    if(primero==0){
                        estadoInicial=Integer.parseInt(line);
                    }
                    // Primer estado de la linea
                    posicion++;
                }else if(posicion==1){
                    // Simbolo
                    posicion++;
                }else if(posicion==2){
                    // Ultimo estado de la linea
                    estadoFinal=Integer.parseInt(line);
                    posicion=0;
                }
                primero++;
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.s=estadoInicial;
        this.f=estadoFinal;
    }
    public void imprimirAlfabeto(){
        // Simplemente recorremos nuestra lista mostrando nuestro alfabeto
        System.out.print("Sigma={");
        for(int i = 0; i<alfabeto.size();i++){ 
            System.out.print(alfabeto.get(i));
            if(i<alfabeto.size()-1){
                System.out.print(",");
            }
        }
        System.out.print("}");
        System.out.println(" ");
    }
    public void imprimirEstados(){
        // Simplemente recorremos nuestra lista mostrando todos los estados
        System.out.print("K={");
        for(int i = 0; i<todos_estados.size();i++){ 
            System.out.print("q"+todos_estados.get(i));
            if(i<todos_estados.size()-1){
                System.out.print(",");
            }
        }
        System.out.print("}");
        System.out.println(" ");
    }
    public void imprimirSyF(){
        // Simplemente mostramos el estado inicial y final
        System.out.println("S={"+s+"}");
        System.out.println("F={"+f+"}");
    }
    public ArrayList<String> getAlfabeto(){return alfabeto;}
    public ArrayList<Integer> getTodosEstados(){return todos_estados;}
    public int getF(){return f;}
    public int getS(){return s;}
}
