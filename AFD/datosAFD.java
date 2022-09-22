package AFD;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class datosAFD {
    ArrayList<String> alfabeto;
    ArrayList<Integer> todos_estados;
    File AFD=new File("./Archivos/AFD.txt");
    int s,f;
    public datosAFD(ArrayList<String> alfabeto){
        todos_estados = new ArrayList<Integer>();
        this.alfabeto=alfabeto;
        obtenerEstados();
        obtenerSyF();
    }
    public void obtenerEstados(){
        String line="";int posicion=0,estadoFinal=0;
        try {
            Scanner input = new Scanner(AFD);
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
        for(int i=0;i<=estadoFinal;i++){
            System.out.println("Estado agregado --->"+i);
            todos_estados.add(i);
        }
    }
    public void obtenerSyF(){
        int primero=0,posicion=0,estadoFinal=0, estadoInicial=0;String line="";
        try {
            Scanner input = new Scanner(AFD);
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
}
