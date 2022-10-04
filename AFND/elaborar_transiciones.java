package AFND;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class elaborar_transiciones{
    String er; 
    boolean E1_usado=false,E2_usado=false, AUX1_usado=false, AUX2_usado=false,AUX3_usado=false;
    ArrayList<String> automata = new ArrayList<String>();FileWriter fw;
    File E1 = new File("./Archivos/E1.txt");
    File E2 = new File("./Archivos/E2.txt");
    File TH = new File("./Archivos/TH.txt");
    File AUX1 = new File("./Archivos/AYUDA1.txt");
    File AUX2 = new File("./Archivos/AYUDA2.txt");
    File AUX3 = new File("./Archivos/AYUDA3.txt");
    public elaborar_transiciones(String er, ArrayList<String> alfabeto){
        // Abrimos y borramos todo lo que tenga los archivos .txt
        try{
            fw = new FileWriter(E1);
            fw = new FileWriter(E2);
            fw = new FileWriter(TH);
            fw = new FileWriter(AUX1);
            fw = new FileWriter(AUX2);
            fw = new FileWriter(AUX3);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        // Agregamos el rizo
        for(int i=0; i<alfabeto.size(); i++){
            escribirAutomata(0, 0, alfabeto.get(i), TH);
        }
        this.er=er;
        recorrerER();
    }
    public void recorrerER(){
        char[] er_recorrer = er.toCharArray();
        System.out.println(er);
        for(int i = 0; i<er_recorrer.length; i++){
            System.out.println("DATO: "+er_recorrer[i]);
            if(er_recorrer[i]=='*'){
                if(i+1==er_recorrer.length){
                    System.out.println("KLEENE- UNIR TODO");
                    clausura(false, true);
                }else if(er_recorrer[i-1]=='.' || er_recorrer[i-1]=='|' || er_recorrer[i-1]=='*'){
                    System.out.println("KLEENE NO SIMPLE");
                    clausura(false, false);
                }else{
                    System.out.println("KLEENE SIMPLE");
                    clausura(true, false);
                }
            }else if(er_recorrer[i]=='|'){
                if(i+1==er_recorrer.length){
                    union(false, true);
                    System.out.println("UNIR- UNIR TODO");
                }else if(er_recorrer[i-1]=='.' || er_recorrer[i-1]=='|'){
                    System.out.println("-Unir NO Simple-");
                    union(false, false);
                }else{
                    System.out.println("-UNIR SIMPLE-");
                    union(true, false);
                }
            }else if(er_recorrer[i]=='.'){
                if(i+1==er_recorrer.length){
                    System.out.println("CONCATENAR- UNIR TODO");
                    concatenacion(false, true);
                }else if(er_recorrer[i-1]=='.' || er_recorrer[i-1]=='|'){
                    System.out.println("-CONCATENAR NO SIMPLE-");
                    concatenacion(false, false);
                }else{
                    System.out.println("-CONCATENAR SIMPLE-");
                    concatenacion(true, false);
                }
                
            }else{
                transicion_basica(""+er_recorrer[i]);
            }
            // Debug
            System.out.println("|AUX1|="+AUX1_usado+" |AUX2|="+AUX2_usado+" |E1|="+E1_usado+" |E2|="+E2_usado);
            System.out.println("--------- AUX1 ---------");
            sololeer(AUX1);
            System.out.println("--------- AUX2 ---------");
            sololeer(AUX2);
            System.out.println("--------- E1 ---------");
            sololeer(E1);
            System.out.println("--------- E2 ---------");
            sololeer(E2);
            System.out.println("--------- TH ---------");
            sololeer(TH);
        }
    }
    public int retroceder(int i, char[] er_recorrer){
        int j=i;
        while(true){
            if(er_recorrer[j]=='.' || er_recorrer[j]=='|' || er_recorrer[j]=='*'){
                return j+1;
            }else if(j==0){
                return j;
            }
            j--;
        }
    } 
    public void concatenacion(boolean simple, boolean fin){
        /* Normal: Concatenacion entre 2 automatas basicos(2 simbolos)
         * !Normal: Concatenacion entre 2 automatas         */
        File archivo; int ultimoestado=0;
        if(fin){
            System.out.println("UNIR-- EN CONSTRUCCION");
            ultimoestado=ultimoEstadoAutomata(TH);
            if(E1_usado && !E2_usado){
                if(ultimoestado>0) {escribirAutomata(ultimoestado, ultimoestado+1, "_", TH); ultimoestado++;}
                reescribirAutomata(E1, TH, ultimoestado);
            }else if(E1_usado && E2_usado){
                reescribirAutomata(E1, TH, ultimoestado);
                ultimoestado=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                ultimoestado=ultimoEstadoAutomata(TH);
                reescribirAutomata(E2, TH, ultimoestado);
            }
            
            if(AUX1_usado && !AUX2_usado){
                ultimoestado=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                reescribirAutomata(AUX1, TH, ultimoestado+1);
            }else if(AUX1_usado && AUX2_usado){
                ultimoestado=ultimoEstadoAutomata(TH);
                reescribirAutomata(AUX1, TH, ultimoestado);
                ultimoestado=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                reescribirAutomata(AUX2, TH, ultimoestado+1);
            }
        }else{
            if(simple){
                if(AUX1_usado && !AUX2_usado){
                    if(E1_usado){
                        archivo=E1;
                        ultimoestado=ultimoEstadoAutomata(E1);
                    }else{
                        archivo=E2;
                        ultimoestado=ultimoEstadoAutomata(E2);
                    }
                    escribirAutomata(ultimoestado, ultimoestado+1, "_", archivo);
                    reescribirAutomata(AUX1, archivo, ultimoestado+1);
                }else{
                    if(!E1_usado){
                        archivo=E1; E1_usado=true;
                    }else{
                        archivo=E2; E2_usado=true;
                    }
                    reescribirAutomata(AUX1, archivo, 0);
                    escribirAutomata(1, 2, "_", archivo);
                    reescribirAutomata(AUX2, archivo, 2);
                }
            }else{
                if(E1_usado && !E2_usado){
                    // NO SE SI ESTA BIEN
                    ultimoestado=ultimoEstadoAutomata(TH);
                    reescribirAutomata(E1, TH, ultimoestado);
                }else{
                    // ESTA BIEN
                    // Se escribe primero E1
                    ultimoestado=ultimoEstadoAutomata(TH);
                    reescribirAutomata(E1, TH, ultimoestado);
                    // Se ve el ultimo estado despues de que se escribio E1 y se agrega E2
                    ultimoestado=ultimoEstadoAutomata(TH);
                    escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                    reescribirAutomata(E2, TH, ultimoestado+1);
                }
            }
        }
        try{
            fw = new FileWriter(AUX1);
            fw = new FileWriter(AUX2);
            fw = new FileWriter(AUX3);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        AUX1_usado=false;AUX2_usado=false;
    }
    public void union(boolean simple, boolean fin){
        File archivo1=new File(""), archivo2=new File(""), archivoFinal=new File(""); int ultimoestadoArchivo1=0, ultimoestadoArchivo2=0, primerestadoArchivo1=0,primerestadoArchivo2=0, ultimoestado=0;
        if(fin){
            if(E1_usado && !E2_usado && AUX1_usado && !AUX2_usado){
                System.out.println("1");
                reescribirAutomata(TH, AUX3, 0);
                archivo1=E1;
                archivo2=AUX1;
            }else if(E1_usado && E2_usado){
                System.out.println("2");
                archivo1=E1;
                archivo2=E2;
            }
            if(AUX1_usado && AUX2_usado){
                System.out.println("3");
                archivo1=AUX1;
                archivo2=AUX2;
                if(E1_usado && !E2_usado){
                    ultimoestado=ultimoEstadoAutomata(TH);
                    reescribirAutomata(E1, TH, ultimoestado);
                }
            }
            System.out.println("NADA");
            primerestadoArchivo1=primerEstadoAutomata(archivo1);
            primerestadoArchivo2=primerEstadoAutomata(archivo2);
            ultimoestadoArchivo1=ultimoEstadoAutomata(archivo1);
            ultimoestadoArchivo2=ultimoEstadoAutomata(archivo2);
            // Conecta el primer estado a los primeros estados de Archivo1 y Archivo2
            escribirAutomata(primerestadoArchivo1, primerestadoArchivo1+1, "_", TH);
            escribirAutomata(primerestadoArchivo1, ultimoestadoArchivo1+primerestadoArchivo2+2, "_", TH);
            // Escribe el primer Archivo en TH
            reescribirAutomata(archivo1, TH,primerestadoArchivo1+1);
            // Escribe el segundo Archivo en TH
            ultimoestadoArchivo2=ultimoEstadoAutomata(TH);
            reescribirAutomata(archivo2, TH,ultimoestadoArchivo2+1);
            // Escribe las ultimas conexiones entre los ultimos estados
            ultimoestado=ultimoEstadoAutomata(TH);
            escribirAutomata(ultimoestadoArchivo1+1,ultimoestado+1 , "_", TH);
            escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
        }else{
            if(simple){
                // AUX1 y AUX2
                if(AUX1_usado && !AUX2_usado){
                    if(E1_usado && !E2_usado){
                        System.out.println("E1 usado");
                        reescribirAutomata(E1, AUX3, 0);
                        archivoFinal=E1;
                        archivo1=AUX3;
                        try{
                            fw = new FileWriter(E1);
                        } catch (IOException e) {
                            System.out.println("ERROR");
                        }
                    }else{
                        System.out.println("E2 usado");
                        reescribirAutomata(E2, AUX3, 0);
                        archivoFinal=E2;
                        archivo1=AUX3;
                        try{
                            fw = new FileWriter(E2);
                        } catch (IOException e) {
                            System.out.println("ERROR");
                        }
                    }
                    archivo2=AUX1;
                    primerestadoArchivo1=primerEstadoAutomata(archivo1);
                    primerestadoArchivo2=primerEstadoAutomata(archivo2);
                    ultimoestadoArchivo1=ultimoEstadoAutomata(archivo1);
                    ultimoestadoArchivo2=ultimoEstadoAutomata(archivo2);
                    // Conecta el primer estado a los primeros estados de Archivo1 y Archivo2
                    escribirAutomata(primerestadoArchivo1, primerestadoArchivo1+1, "_", archivoFinal);
                    escribirAutomata(primerestadoArchivo1, ultimoestadoArchivo1+primerestadoArchivo2+2, "_", archivoFinal);
                    // Escribe el primer Archivo en el archivo Final
                    reescribirAutomata(archivo1, archivoFinal, primerestadoArchivo1+1);
                    // Escribe el segundo Archivo en archivo Final
                    ultimoestadoArchivo2=ultimoEstadoAutomata(archivoFinal);
                    reescribirAutomata(archivo2, archivoFinal, ultimoestadoArchivo2+1);
                    // Escribe las ultimas conexiones entre los ultimos estados
                    ultimoestado=ultimoEstadoAutomata(archivoFinal);
                    escribirAutomata(ultimoestadoArchivo1+1,ultimoestado+1 , "_", archivoFinal);
                    escribirAutomata(ultimoestado, ultimoestado+1, "_", archivoFinal);
                }else{
                    if(!E1_usado){
                        System.out.println("E1 usado");
                        archivoFinal=E1;
                        E1_usado=true;
                    }else{
                        System.out.println("E2 usado");
                        archivoFinal=E2;
                        E2_usado=true;
                    }
                    archivo1=AUX1;
                    archivo2=AUX2;
                    primerestadoArchivo1=primerEstadoAutomata(archivo1);
                    primerestadoArchivo2=primerEstadoAutomata(archivo2);
                    ultimoestadoArchivo1=ultimoEstadoAutomata(archivo1);
                    ultimoestadoArchivo2=ultimoEstadoAutomata(archivo2);
                    // Conecta el primer estado a los primeros estados de Archivo1 y Archivo2
                    escribirAutomata(primerestadoArchivo1, primerestadoArchivo1+1, "_", AUX3);
                    escribirAutomata(primerestadoArchivo1, ultimoestadoArchivo1+primerestadoArchivo2+2, "_", AUX3);
                    // Escribe el primer Archivo en el archivo Final
                    reescribirAutomata(archivo1, AUX3, primerestadoArchivo1+1);
                    // Escribe el segundo Archivo en archivo Final
                    ultimoestadoArchivo2=ultimoEstadoAutomata(AUX3);
                    reescribirAutomata(archivo2, AUX3, ultimoestadoArchivo2+1);
                    // Escribe las ultimas conexiones entre los ultimos estados
                    ultimoestado=ultimoEstadoAutomata(AUX3);
                    escribirAutomata(ultimoestadoArchivo1+1,ultimoestado+1 , "_", AUX3);
                    escribirAutomata(ultimoestado, ultimoestado+1, "_", AUX3);
                    // Agregamos en E1 o en E2 segun corresponda
                    ultimoestado=ultimoEstadoAutomata(archivoFinal);
                    reescribirAutomata(AUX3, archivoFinal, ultimoestado);
                }
                
            }else{
                // E1 y E2
                if(E1_usado && !E2_usado){
                    reescribirAutomata(TH, AUX3, 0);
                    archivo1=AUX3;
                    archivo2=E1;
                    System.out.println("E1 usado");
                    try{
                        fw = new FileWriter(TH);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }else{
                    archivo1=E1;
                    archivo2=E2;
                }
                primerestadoArchivo1=primerEstadoAutomata(archivo1);
                primerestadoArchivo2=primerEstadoAutomata(archivo2);
                ultimoestadoArchivo1=ultimoEstadoAutomata(archivo1);
                ultimoestadoArchivo2=ultimoEstadoAutomata(archivo2);
                // Conecta el primer estado a los primeros estados de Archivo1 y Archivo2
                escribirAutomata(primerestadoArchivo1, primerestadoArchivo1+1, "_", TH);
                escribirAutomata(primerestadoArchivo1, ultimoestadoArchivo1+primerestadoArchivo2+2, "_", TH);
                // Escribe el primer Archivo en TH
                reescribirAutomata(archivo1, TH, primerestadoArchivo1+1);
                // Escribe el segundo Archivo en TH
                ultimoestadoArchivo2=ultimoEstadoAutomata(TH);
                reescribirAutomata(archivo2, TH, ultimoestadoArchivo2+1);
                // Escribe las ultimas conexiones entre los ultimos estados
                ultimoestado=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestadoArchivo1+1,ultimoestado+1 , "_", TH);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
            }
        }
        try{
            fw = new FileWriter(AUX1);
            fw = new FileWriter(AUX2);
            fw = new FileWriter(AUX3);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        AUX1_usado=false;AUX2_usado=false;
    }
    public void clausura(boolean simple, boolean fin){
        int ultimoestado=0, primerestadoArchivo=0,ultimoestadoArchivo=0; File archivo=new File("");
        if(fin){
            ultimoestado=ultimoEstadoAutomata(TH);
            if(E1_usado && !E2_usado){
                archivo=E1;
                primerestadoArchivo=primerEstadoAutomata(E1);
                ultimoestadoArchivo=ultimoEstadoAutomata(E1);
            }else if(E1_usado && E2_usado){
                archivo=E2;
                primerestadoArchivo=primerEstadoAutomata(E2);
                ultimoestadoArchivo=ultimoEstadoAutomata(E2);
            }else if(!E1_usado && !E2_usado && AUX1_usado){
                archivo=AUX1;
                primerestadoArchivo=primerEstadoAutomata(AUX1);
                ultimoestadoArchivo=ultimoEstadoAutomata(AUX1);
            }else{
                // SOLO * TH
                archivo=AUX3;
                reescribirAutomata(TH, AUX3, 0);
                primerestadoArchivo=primerEstadoAutomata(AUX3);
                ultimoestadoArchivo=ultimoEstadoAutomata(AUX3);
                try{
                    fw = new FileWriter(TH);
                } catch (IOException e) {
                    System.out.println("ERROR");
                }
            }
            ultimoestado=ultimoEstadoAutomata(TH);
            escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
            escribirAutomata(ultimoestado, ultimoestadoArchivo+2, "_", TH);
            reescribirAutomata(archivo, TH, 1);
            ultimoestado=ultimoEstadoAutomata(TH);
            escribirAutomata(ultimoestado, primerestadoArchivo+1, "_", TH);
            escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
        }else{
            if(simple){
                System.out.println("----------------------------SIMPLE");
                if(AUX1_usado && !AUX2_usado){
                    archivo=AUX1;
                    primerestadoArchivo=primerEstadoAutomata(AUX1);
                    ultimoestadoArchivo=ultimoEstadoAutomata(AUX1);
                    reescribirAutomata(AUX1, AUX3, 0);
                    try{
                        fw = new FileWriter(AUX1);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }else{
                    archivo=AUX2;
                    primerestadoArchivo=primerEstadoAutomata(AUX2);
                    ultimoestadoArchivo=ultimoEstadoAutomata(AUX2);
                    reescribirAutomata(AUX2, AUX3, 0);
                    try{
                        fw = new FileWriter(AUX2);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }
                escribirAutomata(0, 1, "_", archivo);
                escribirAutomata(0, ultimoestadoArchivo+2, "_", archivo);
                reescribirAutomata(AUX3, archivo, 1);
                ultimoestado=ultimoEstadoAutomata(archivo);
                escribirAutomata(ultimoestado, primerestadoArchivo+1, "_", archivo);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", archivo);
                sololeer(archivo);
            }else{
                System.out.println("--------------------------------NO SIMPLE");
                if(E1_usado && !E2_usado){
                    System.out.println("----------------------------------AQUI 1");
                    archivo=E1;
                    primerestadoArchivo=primerEstadoAutomata(E1);
                    ultimoestadoArchivo=ultimoEstadoAutomata(E1);
                    reescribirAutomata(E1, AUX3, 0);
                    try{
                        fw = new FileWriter(E1);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }else{
                    System.out.println("------------------------------AQUI 2");
                    archivo=E2;
                    primerestadoArchivo=primerEstadoAutomata(E2);
                    ultimoestadoArchivo=ultimoEstadoAutomata(E2);
                    reescribirAutomata(E2, AUX3, 0);
                    try{
                        fw = new FileWriter(E2);
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }
                }
                escribirAutomata(0, 1, "_", archivo);
                escribirAutomata(0, ultimoestadoArchivo+2, "_", archivo);
                reescribirAutomata(AUX3, archivo, 1);
                ultimoestado=ultimoEstadoAutomata(archivo);
                escribirAutomata(ultimoestado, primerestadoArchivo+1, "_", archivo);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", archivo);
            }
        }
        try{
            fw = new FileWriter(AUX3);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        AUX3_usado=false;
    }
    public void transicion_basica(String letra){
        // Se genera el automata mas basico de todos
        int estado=0; File archivo;
        if(!AUX1_usado){
            archivo=AUX1;
            AUX1_usado=true;
        }else{
            archivo=AUX2;
            AUX2_usado=true;
        }
        escribirAutomata(estado, estado+1, letra, archivo);
    }
    public void escribirAutomata(int estado1,int estado2, String simbolo, File archivo){
        // Lo usamos para escribir el automata al .txt
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println(estado1+" "+simbolo+" "+estado2);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
    public int ultimoEstadoAutomata(File archivoLeer){
        // Nos retorna el ultimo estado de un automata, esto sirve cuando queremos juntar dos automatas.
        int posicion=0; int estadoFinal=0;String line="";
        try {
            Scanner input = new Scanner(archivoLeer);
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
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
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return estadoFinal;
    }
    public int primerEstadoAutomata(File archivoLeer){
       // Nos retorna el ultimo estado de un automata, esto sirve cuando queremos juntar dos automatas.
       int posicion=0; int estadoInicial=0;String line="";
       try {
           Scanner input = new Scanner(archivoLeer);
           while (input.hasNext()) {
               line = input.next();
               if(posicion==0){
                   // Primer estado de la linea
                   estadoInicial=Integer.parseInt(line);
               }
               posicion++;
           }
           input.close();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return estadoInicial;
    }
    public void sololeer(File archivoLeer){
        // Para DEBUG
        try {
            FileReader fr = new FileReader (archivoLeer);
            BufferedReader br = new BufferedReader(fr);
   
            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                System.out.println("{"+linea+"}");
            }
            br.close();
         }
         catch(Exception e){
            e.printStackTrace();
         }
    }
    public void reescribirAutomata(File archivoLeer, File archivoEscribir, int cambioEstado){
        // Escribe lo que hay en un .txt en otro.
        int posicion=0; int estado1=0,estado2=0; String simbolo="",line="";
        try {
            Scanner input = new Scanner(archivoLeer);
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
                }else if(posicion==2){
                    // Segundo estado
                    estado2=Integer.parseInt(line);
                    escribirAutomata(estado1+cambioEstado, estado2+cambioEstado, simbolo, archivoEscribir);
                    posicion=0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void ordenarER(File TH){
        //Funcion que sirve para reordenar el automata para luego mostrarlo
    }
}
