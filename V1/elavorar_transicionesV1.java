package V1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class elavorar_transicionesV1 {
    String er; 
    boolean E1_usado=false,E2_usado=false; 
    ArrayList<String> automata = new ArrayList<String>();FileWriter fw;
    File E1 = new File("Archivos/E1.txt");
    File E2 = new File("Archivos/E2.txt");
    File TH = new File("Archivos/TH.txt");
    File AUX = new File("Archivos/AYUDA.txt");
    public elavorar_transicionesV1(String er){
        // Abrimos y borramos todo lo que tenga los archivos .txt
        try{
            fw = new FileWriter(E1);
            fw = new FileWriter(E2);
            fw = new FileWriter(TH);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        this.er=er;
        recorrerER();
    }
    public void recorrerER(){
        int inicio,fin; char[] er_recorrer = er.toCharArray();
        System.out.println(er);
        for(int i = 0; i<er_recorrer.length; i++){
            if(er_recorrer[i]=='.'){
                inicio = retroceder(i-1, er_recorrer);
                fin = i-1;
                if(inicio-fin==0){
                    // Hay que concatenar un automata basico.
                    System.out.println("Generar automata basico");
                    transicion_basica(""+er_recorrer[i-1]);
                    if (i+1==er_recorrer.length){ concatenacion(er_recorrer, inicio, fin, true, true); System.out.println("1- UNIR, SIMPLE? true");}
                    else if(E1_usado && E2_usado){ concatenacion(er_recorrer, inicio, fin, true,false); System.out.println("1- UNIR, SIMPLE? false");}
                    else{ concatenacion(er_recorrer, inicio, fin, true, true); System.out.println("1- UNIR, SIMPLE? false");}
                }else if(er_recorrer[i-1]=='.' || er_recorrer[i-1]=='|'){
                    // Si se encuentra un simbolo antes del '.' entonces hay que unir.
                    if(E1_usado && E2_usado){ concatenacion(er_recorrer, inicio, fin, true, false); System.out.println("2- UNIR, SIMPLE? false");}
                    else{ concatenacion(er_recorrer, inicio, fin, true, true); System.out.println("2- UNIR, SIMPLE? true");}
                }else{
                    // Se concatena normal, si se encuentra que E1 esta usado y E2 igual, entonces hay que unir y juntarlo en TH 
                    if (i+1==er_recorrer.length){ concatenacion(er_recorrer, inicio, fin, true, true); System.out.println("3- UNIR, SIMPLE? true");}
                    else if(E1_usado && E2_usado){ concatenacion(er_recorrer, inicio, fin, true,false); System.out.println("3- UNIR, SIMPLE? false");}
                    else{ concatenacion(er_recorrer, inicio, fin, false, false); System.out.println("3- NO UNIR, SIMPLE? false");}
                }
            }else if(er_recorrer[i]=='|'){
                if(er_recorrer[i-1]=='*'){
                    inicio=retroceder(i-3, er_recorrer);
                    fin=i-3;
                    transicion_basica(""+er_recorrer[inicio]);
                    clausura(er_recorrer, inicio+1, fin+1, true);
                    union(er_recorrer, inicio, fin, true, false);
                    System.out.println("COMOOOOOOOOOOOOOOOOOOO? 2");
                }
                inicio = retroceder(i-1, er_recorrer);
                fin = i-1;
                if(inicio-fin==0){
                    // Hay que concatenar un automata basico.
                    transicion_basica(""+er_recorrer[i-1]);
                    union(er_recorrer, inicio, fin, true, true);
                }else if(er_recorrer[i-1]=='.' || er_recorrer[i-1]=='|'){
                    // Si se encuentra un simbolo antes del '.' entonces hay que unir.
                    if(E1_usado && E2_usado) union(er_recorrer, inicio, fin, true, false);
                    else union(er_recorrer, inicio, fin, true, true);
                }else{
                    // Se concatena normal, si se encuentra que E1 esta usado y E2 igual, entonces hay que unir y juntarlo en TH 
                    union(er_recorrer, inicio, fin, false, false);
                }
            }else if(er_recorrer[i]=='*'){
                if(er_recorrer[i-1]=='|' || er_recorrer[i-1]=='.' || er_recorrer[i-1]=='*' ){
                    System.out.println("COMOOOOOOOOOOOOOOOOOOO? 3");
                    clausura(er_recorrer, 0, 0, false);
                }
            }
            // Comprobar DEBUG --
            System.out.println("Caracter Usado -> "+er_recorrer[i]+" | E1 Usado -> "+E1_usado+" | E2 Usado -> "+E2_usado);
            
            System.out.println("---E1---");
            sololeer(E1);
            System.out.println("--------");
            System.out.println("---E2---");
            sololeer(E2);
            System.out.println("--------");
            System.out.println("---TH---");
            sololeer(TH);
            System.out.println("--------");
            
        }
        ordenarER(TH);
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
    public void concatenacion(char[] concatenar, int inicio, int fin, boolean unir, boolean unionsimple){
        int estado=0,ultimoestado=0; File archivo_E = new File("");
        if(unir){
            if(E1_usado && E2_usado){
                ultimoestado=ultimoEstadoAutomata(E1);
                reescribirAutomata(E1, TH, 0);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                reescribirAutomata(E2,TH,ultimoestado+1);
            }else if(E1_usado && !E2_usado && unionsimple){
                ultimoestado=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestado, ultimoestado+1, "_", TH);
                reescribirAutomata(E1, TH, ultimoestado+1);
            }
            try{
                fw = new FileWriter(E1);
                fw = new FileWriter(E2);
            } catch (IOException e) {
                System.out.println("ERROR");
            }
            E1_usado=false;
            E2_usado=false;
        }else{
            // Vemos si E1 y E2 esta usado para agregar el automata a alguno de estos dos .txt
            if(!E1_usado){
                archivo_E=E1;
                E1_usado=true;
            }else{
                archivo_E=E2;
                E2_usado=true;
            }
            // Rellenamos el automata con sus respectivos épsilon y letras para cada transicion entre un estado y otro
            for(int i=inicio; i<=fin;i++){
                if(i==inicio){
                    escribirAutomata(estado, estado+1, ""+concatenar[i], archivo_E);
                    
                }else{
                    escribirAutomata(estado, estado+1, "_", archivo_E);
                    estado++;
                    escribirAutomata(estado, estado+1, ""+concatenar[i], archivo_E);
                }
                estado++;
            } 
        }
    }
    public void union(char[] concatenar, int inicio, int fin, boolean unir, boolean simple){
        int ultimoestadoE1=0, ultimoestadoE2=0, ultimoestadoTH=0;
        if(unir){
            if(E1_usado && E2_usado){
                // Obtenemos los ultimos estados de E1 y E2
                ultimoestadoE1=ultimoEstadoAutomata(E1);
                ultimoestadoE2=ultimoEstadoAutomata(E2);
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                // Conecta el primer estado a los primeros estados de E1 y E2
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoE1+2, "_", TH);
                // Escribimos E1 en TH
                reescribirAutomata(E1, TH, 1); 
                // Escribimos E2 en TH
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                reescribirAutomata(E2, TH, ultimoestadoTH+1);
                // Conecta los ultimos estados de E1 y E2 al estado final
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestadoE1+1, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
            }else if(E1_usado && !E2_usado && simple){
                // Escribimos TH en E2
                reescribirAutomata(TH,E2,0);
                try{
                    fw = new FileWriter(TH);
                } catch (IOException e) {
                    System.out.println("ERROR");
                }
                // DEBUG
                System.out.println("----------------   E1   ----------------");
                sololeer(E1);
                System.out.println("----------------________----------------");
                System.out.println("----------------   E2   ----------------");
                sololeer(E2);
                System.out.println("----------------________----------------");
                // Obtenemos los ultimos estados de E1 y E2
                ultimoestadoE1=ultimoEstadoAutomata(E1);
                ultimoestadoE2=ultimoEstadoAutomata(E2);
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                // Conecta el primer estado a los primeros estados de E1 y E2
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoE2+2, "_", TH);
                // DEBUG
                System.out.println("----------------  TH(1) ----------------");
                sololeer(TH);
                System.out.println("----------------________----------------");
                // Escribimos E2 en TH
                reescribirAutomata(E2, TH, 1);
                ultimoestadoE2=ultimoEstadoAutomata(E2);
                // Escribimos E1 en TH
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                reescribirAutomata(E1, TH, ultimoestadoTH+1);
                // Conecta los ultimos estados de E1 y E2 al estado final
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestadoE2+1, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
                // DEBUG
                System.out.println("----------------  TH(2) ----------------");
                sololeer(TH);
                System.out.println("----------------________----------------");
            }
        }else{
                /*  Si no hay una union simple significa que hay que unir 2 simbolos, para eso creamos 2 transiciones basicas
                 *  Las creamos y luego las unimos
                */
                transicion_basica(""+concatenar[inicio]);
                transicion_basica(""+concatenar[fin]);
                // Obtenemos los ultimos estados de E1 y E2
                ultimoestadoE1=ultimoEstadoAutomata(E1);
                ultimoestadoE2=ultimoEstadoAutomata(E2);
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                // Conecta el primer estado a los primeros estados de E1 y E2
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoE1+2, "_", TH);
                // Escribimos E1 en TH
                reescribirAutomata(E1, TH, 1);
                ultimoestadoE1=ultimoEstadoAutomata(E1); 
                // Escribimos E2 en TH
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                reescribirAutomata(E2, TH, ultimoestadoTH+1);
                // Conecta los ultimos estados de E1 y E2 al estado final
                ultimoestadoTH=ultimoEstadoAutomata(TH);
                escribirAutomata(ultimoestadoE1+1, ultimoestadoTH+1, "_", TH);
                escribirAutomata(ultimoestadoTH, ultimoestadoTH+1, "_", TH);
        }
        E1_usado=false;
        E2_usado=false;
        try{
            fw = new FileWriter(E1);
            fw = new FileWriter(E2);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }
    public void clausura(char[] concatenar, int inicio, int fin, boolean simple){
        int estado=0; File archivo_E = new File("");
        if(simple){
            if(!E1_usado){
                archivo_E=E1;
                E1_usado=true;
            }else{
                archivo_E=E2;
                E2_usado=true;
            }
            escribirAutomata(0, 1, "_", archivo_E);
            escribirAutomata(0, estado+3, "_", archivo_E);
            escribirAutomata(estado+1, estado+2, ""+concatenar[inicio], archivo_E);
            escribirAutomata(estado+2, estado+1, "_", archivo_E);
            escribirAutomata(estado+2, estado+3, "_", archivo_E);
        }else{
            if(E1_usado && !E2_usado){
                // Hay que hacerlo para todo E1 y ponerlo en TH
                escribirAutomata(0, 1, "_", TH);
                escribirAutomata(0, estado+3, "_", TH);
                reescribirAutomata(E1, TH, 1);
                escribirAutomata(estado+2, estado+1, "_", TH);
                escribirAutomata(estado+2, estado+3, "_", TH);
            }else{
                // Hay que hacerlo para todo TH y ponerlo en TH nuevamente
                reescribirAutomata(TH, AUX, 0);
                try{
                    fw = new FileWriter(TH);
                } catch (IOException e) {
                    System.out.println("ERROR");
                }
                escribirAutomata(0, 1, "_", TH);
                escribirAutomata(0, estado+3, "_", TH);
                reescribirAutomata(AUX, TH, 1);
                escribirAutomata(estado+2, estado+1, "_", TH);
                escribirAutomata(estado+2, estado+3, "_", TH);
            }
        }
    }
    public void transicion_basica(String letra){
        // Se genera el automata mas basico de todos
        int estado=0; File archivo_E;
        if(!E1_usado){
            archivo_E=E1;
            E1_usado=true;
        }else{
            archivo_E=E2;
            E2_usado=true;
        }
        escribirAutomata(estado, estado+1, letra, archivo_E);
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
    public void sololeer(File archivoLeer){
        // Para DEBUG
        try {
            FileReader fr = new FileReader (archivoLeer);
            BufferedReader br = new BufferedReader(fr);
   
            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                System.out.println("e "+linea);
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
