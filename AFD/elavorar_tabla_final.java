package AFD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class elavorar_tabla_final {
    ArrayList<String> alfabeto= new ArrayList<String>();
    File TH=new File("./Archivos/TH.txt");
    File TABLA_E = new File("./Archivos/tabla_e.txt");
    File TABLA_TRANSICIONES = new File("./Archivos/tabla_transiciones.txt");
    File TABLA_FINAL=new File("./Archivos/tabla_final.txt"); FileWriter fw;

    public elavorar_tabla_final(ArrayList<String> alfabeto){
        this.alfabeto=alfabeto;
        try{
            fw = new FileWriter(TABLA_FINAL);  
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        tabla_final();
    }

    public void tabla_final(){
        String encabezado="";
        // Agregamos el encabesado de la tabla
        for(int h=0;h<alfabeto.size();h++){
            if(h==0){
                encabezado=""+alfabeto.get(h);
            }else{
                encabezado+=" "+alfabeto.get(h);
            }
        }
        escribirTablaEncabezado(encabezado, TABLA_FINAL);
        // Hacemos la tabla
        String conjunto=obtenerPrimerEstadoTabla(TABLA_E);
        escribirTablaFinal(0, conjunto, TABLA_FINAL);
        buscarConjuntos(conjunto, alfabeto, TABLA_TRANSICIONES);
        
    }

    public String obtenerPrimerEstadoTabla(File archivo){
        // Encuentra todos los epsilon de un estado
        String primerEstado="",conjunto1="", conjunto2="",line="";int posicion=0;
        try {
            Scanner input = new Scanner(archivo);
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
                    // Primer conjunto
                    conjunto1=line;
                }else if(posicion==1){
                    // Segundo conjunto
                    conjunto2=line;
                }
                posicion++;
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(conjunto2.equals("NULL")){
            System.out.println("Primer estado de la tabla-------------> {"+conjunto1+"}");
            primerEstado=conjunto1;
        }else{
            System.out.println("Primer estado de la tabla-------------> {"+conjunto1+","+conjunto2+"}");
            primerEstado=conjunto1+","+conjunto2;
        }
        return primerEstado;
        
    }
    public void buscarConjuntos(String estadosBuscar, ArrayList<String> alfabeto, File archivo){
        // Encuentra todos los epsilon de un estado
        String primerEstado="",conjunto1="", conjunto2="",line="";int posicion=0;
        String[] estadosSeparados = estadosBuscar.split(",");
        for(int i=0; i<estadosSeparados.length; i++){
            // Buscamos las transiciones del alfabeto para cada estado
            for(int j=0; j<alfabeto.size(); j++){
                // Buscamos letra por letra del alfabeto
                System.out.println("Estado Buscar ---> "+estadosSeparados[i]+", Letra a buscar ---> "+alfabeto.get(j)+", Posicion texto buscar ---> "+j);
                try {
                    Scanner input = new Scanner(archivo);
                    // Nos saltamos el encabezado
                    input.nextLine();
                    while (input.hasNext()) {
                        // Buscamos el estado en la tabla
                        line = input.next();
                        System.out.println("Input.line ---> "+line+" == "+estadosSeparados[i]);
                        if(line.equals(estadosSeparados[i])){
                            // Si encotramos el estado, vemos las transiciones para la letra en especifico
                            System.out.println("ENTRO EPICOOOO");
                            while(posicion<=j){
                                line = input.next();
                                System.out.println("Avanzando en la poscion del texto... --> "+line);
                                posicion++;
                            }
                            /* Si hay un NULL quiere decir que no hay transicion, de caso contrario quiere decir que
                            *  si hay y hay que guardarlo  
                            */
                            if(line.equals("NULL")){
                                System.out.println("Se encontro un NULL, no se agrega nada");
                            }else{
                                System.out.println("Se encontro un conjunto ---> "+line);
                            }
                            posicion=0;
                            break;
                        }else{
                            input.nextLine();
                        }
                    }
                    input.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void escribirTablaFinal(int estado,String conjunto, File archivo){
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

    public void escribirTablaEncabezado(String alfabeto, File archivo){
        // Lo usamos para escribir la tabla en el txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("q e "+alfabeto);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
}
