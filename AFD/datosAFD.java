package AFD;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class datosAFD {
    ArrayList<String> alfabeto;
    ArrayList<Integer> todos_estados;
    File AFD=new File("./Archivos/AFD.txt");
    File TABLA_FINAL =new File("./Archivos/tabla_final.txt");
    ArrayList<Integer> f;
    int s;

    public datosAFD(ArrayList<String> alfabeto){
        todos_estados = new ArrayList<Integer>();
        f = new ArrayList<Integer>();
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
                    // Primer estado (SI LLEGAMOS AL FINAL DEL TXT ENCONTRAMOS EN EL PRIMER ESTADO EL ULTIMO ESTADO DEL AUTOMATA)
                    estadoFinal=Integer.parseInt(line);
                    posicion++;
                }else if(posicion==1){
                    // Simbolo
                    posicion++;
                }else{
                    // Segundo estado     
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
        int posicion=0,estado=0;String line="";
        // La cantidad de columnas de el txt, la suma del alfabeto mas la columna
        // q(Numero de estado ) y e(Conjunto del estado)
        int cantidad_de_columnas = alfabeto.size() + 2;
        try {
            Scanner input = new Scanner(TABLA_FINAL);
            // Nos saltamos el encabezado
            input.nextLine();
            while (input.hasNext()) {
                line = input.next();
                if (posicion == 0) {
                    // Columna q
                    estado = Integer.parseInt(line);
                    posicion++;
                } else if (posicion == 1) {
                    // Columna estados
                    posicion++;
                } else if (posicion < cantidad_de_columnas) {
                    // Columna de el caracter del alafabeto
                    posicion++;
                }else{
                    // Columna donde se menciona si es un estado final o no
                    if(line.equals("FINAL")){
                        this.f.add(estado);
                    }
                    posicion = 0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.s=0;
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
        System.out.print("F={");
        for(int i = 0; i<f.size(); i++){
            if(i+1==f.size()){
                System.out.print(f.get(i));
            }else{
                System.out.print(f.get(i)+",");
            }
        }
        System.out.println("}");
    }   
}
