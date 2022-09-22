package AFD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class elavorar_tabla_final {
    ArrayList<String> alfabeto= new ArrayList<String>();
    ArrayList<String> conjuntosNuevos = new ArrayList<String>();
    File TH=new File("./Archivos/TH.txt");
    File TABLA_E = new File("./Archivos/tabla_e.txt");
    File TABLA_TRANSICIONES = new File("./Archivos/tabla_transiciones.txt");
    File TABLA_FINAL=new File("./Archivos/tabla_final.txt"); 
    File AFD = new File("./Archivos/AFD.txt");
    FileWriter fw;

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
        //escribirTablaFinal(0, conjunto, TABLA_FINAL);
        System.out.println("---------------------- SEGUNDO COSO ----------------------");
        System.out.println("");System.out.println("");
        buscarConjuntosTesteo(conjunto, alfabeto, TABLA_TRANSICIONES);
        System.out.println("---------------------- FIN ----------------------");
        transformar_tabla_afd(TABLA_FINAL, conjuntosNuevos);

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
        String line="";int posicion=0; String conjunto="";
        ArrayList<String> conjuntosEncontrados = new ArrayList<String>();
        String[] estadosSeparados = estadosBuscar.split(",");
        for(int i=0; i<estadosSeparados.length; i++){
            // Buscamos las transiciones del alfabeto para cada estado
            for(int j=0; j<alfabeto.size(); j++){
                conjunto="";
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
                                System.out.println("AGREGAR NULL AL ALFABETO ---> "+alfabeto.get(j)+" ????????????");
                            }else{
                                conjunto+=line;
                                System.out.println("Se encontro un conjunto ---> "+line+" para el alfabeto ---> "+alfabeto.get(j));
                                System.out.println("AQUI DEBERIAMOS ESCRIBIR LA WEA DEL --->"+line+" EN LA TABLA KLA ASDJHSALJKDHSAKJDHSAKJDHSAKJDHSAK");

                            }
                            posicion=0;
                            break;
                        }else{
                            input.nextLine();
                        }
                    }
                    input.close();
                    if(!conjunto.equals("")){
                        System.out.println("Se encontraron conjunto/s las cuales fueron agregados en un arraylist para luego escribirlos en el txt");
                        conjuntosEncontrados.add(conjunto);
                    }else{
                        System.out.println("No se encontraron conjuntos por las cuales se agrega un NULL haciendo referencia a que no se encontro nada sadhasjkdhsakudhaskujdaskj");
                        conjuntosEncontrados.add("NULL");
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("CONJUTNOS ENCONTRADOS ----------> "+conjuntosEncontrados);
    }
    public void buscarConjuntosTesteo(String estadosBuscar, ArrayList<String> alfabeto, File archivo){
        // añadimos el primer estadoxs
        conjuntosNuevos.add(estadosBuscar);
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Primer wea de conjuntosNuevos ---->"+conjuntosNuevos);
        // Encuentra todos los epsilon de un estado
        String line="";int posicion=0; String conjunto=""; int nestado=0;
        ArrayList<String> conjuntosEncontrados = new ArrayList<String>();
        String[] estadosSeparados; String conjunto1="", conjunto2="", unirConjuntos="";
        for(int recorrer = 0; recorrer<conjuntosNuevos.size(); recorrer++){
            System.out.println("");
            System.out.println("------------------------------------");
            estadosSeparados = conjuntosNuevos.get(recorrer).split(",");
            System.out.println("Conjunto el cual se va a revisar ------> "+conjuntosNuevos.get(recorrer));
            conjuntosEncontrados = new ArrayList<String>();
            line="";posicion=0;conjunto="";
            for(int i=0; i<alfabeto.size(); i++){
                // Buscamos las transiciones del alfabeto para cada estado
                conjunto="";
                for(int j=0; j<estadosSeparados.length; j++){
                    // Buscamos letra por letra del alfabeto
                    System.out.println("Estado Buscar ---> "+estadosSeparados[j]+", Letra a buscar ---> "+alfabeto.get(i)+", Posicion texto buscar ---> "+i);
                    try {
                        Scanner input = new Scanner(archivo);
                        // Nos saltamos el encabezado
                        input.nextLine();
                        while (input.hasNext()) {
                            // Buscamos el estado en la tabla
                            line = input.next();
                            //System.out.println("Input.line ---> "+line+" == "+estadosSeparados[j]);
                            if(line.equals(estadosSeparados[j])){
                                // Si encotramos el estado, vemos las transiciones para la letra en especifico
                                while(posicion<=i){
                                    line = input.next();
                                    //System.out.println("Avanzando en la poscion del texto... --> "+line);
                                    posicion++;
                                }
                                /* Si hay un NULL quiere decir que no hay transicion, de caso contrario quiere decir que
                                *  si hay y hay que guardarlo  
                                */
                                if(line.equals("NULL")){
                                    //System.out.println("Se encontro un NULL, no se agrega nada");
                                    //System.out.println("AGREGAR NULL AL ALFABETO ---> "+alfabeto.get(i)+" ????????????");
                                }else{
                                    conjunto+=line;
                                    //System.out.println("Se encontro un conjunto ---> "+line+" para el alfabeto ---> "+alfabeto.get(i));
                                    //System.out.println("AQUI DEBERIAMOS ESCRIBIR LA WEA DEL --->"+line+" EN LA TABLA KLA ASDJHSALJKDHSAKJDHSAKJDHSAKJDHSAK");

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
                if(!conjunto.equals("")){
                    System.out.println("Se encontraron conjunto/s las cuales fueron agregados en un arraylist para luego escribirlos en el txt");
                    conjuntosEncontrados.add(conjunto);
                }else{
                    System.out.println("No se encontraron conjuntos por las cuales se agrega un NULL haciendo referencia a que no se encontro nada sadhasjkdhsakudhaskujdaskj");
                    conjuntosEncontrados.add("NULL");
                }
            }

            System.out.println("CONJUTNOS ENCONTRADOS ----------> "+conjuntosEncontrados+" Estados1 --> "+conjuntosNuevos.get(recorrer));
            String lineaIngresar = conjuntosNuevos.get(recorrer); 
            for(int i = 0; i<conjuntosEncontrados.size(); i++ ){
                conjunto1=""; conjunto2=""; unirConjuntos="";
                if(conjuntosEncontrados.get(i).equals("NULL")){
                    System.out.println("No se encontraron conjuntos nuevos ");
                    lineaIngresar+=" "+"NULL";
                }else{
                    // Añadir los conjuntos encontrados a el arreglo de los nuevos conjuntos
                    // HACER FUNCION PARA ELIMINAR LOS ESTADOS REPETIDOS Y LOS CONJUNTOS REPETIDOS //
                    conjunto1 = conjuntosEncontrados.get(i);
                    conjunto2 = buscarElConjunto(conjuntosEncontrados.get(i)); 
                    if(conjunto2.equals("NULL")){
                        unirConjuntos= conjunto1;
                    }else{
                        unirConjuntos = conjunto1+","+conjunto2;
                    }
                    System.out.println("Conjunto encontrado(puede que con estados repetidos) para ingresar al ArrayList ---> "+conjunto2);
                    // Eliminamos los estados repetidos de los conjuntos encontrados (Puede ser que no esten repetidos)
                    unirConjuntos = eliminarEstadosRepetidos(unirConjuntos);
                    System.out.println("Conjunto encontrado(se supone que no tiene que tener estados repetidos) para ingresar al ArrayList ---> "+unirConjuntos);
                    // Luego agregamos el conjunto encontrado al ArrayList
                    conjuntosNuevos.add(unirConjuntos);

                    System.out.println("Se agrega al arrego el conjunto -----------------> "+unirConjuntos+" Al ArrayList conjuntosNuevos --> "+conjuntosNuevos);
                    // Si el conjunto encontrado esta repetido entonces lo eliminamos
                    System.out.println("ArrayList CON CONJUNTOS REPETIDOS -----> "+conjuntosNuevos);
                    conjuntosNuevos = eliminarConjuntosRepetidos(conjuntosNuevos);
                    System.out.println("Se supone que el se eliminan los conjuntos del ArrayList -----> "+conjuntosNuevos);
                    lineaIngresar+=" "+unirConjuntos;
                }
            }
            System.out.println("Linea ingresar a la tabla ------> "+lineaIngresar);
            escribirTablaFinal(nestado, lineaIngresar, TABLA_FINAL);
            nestado++;
            System.out.println("");
            System.out.println("------------------------------------");
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
    public String eliminarEstadosRepetidos(String dato){
        ArrayList<String> listHelp = new ArrayList<String>();
        ArrayList<String> newList = new ArrayList<String>();
        String[] separarEstados = dato.split(",");
        Arrays.sort(separarEstados);
        for(int i = 0; i<separarEstados.length; i++){
            listHelp.add(separarEstados[i]);
        }
        for (int i=0; i<listHelp.size(); i++) {
            if (!newList.contains(listHelp.get(i))) {
                newList.add(listHelp.get(i));
            }
        }
        dato="";
        for(int i = 0; i<newList.size(); i++){
            if(i==0){
                dato=newList.get(i);
            }else{
                dato+=","+newList.get(i);
            }
        }
        System.out.println("Dato sin estados repetidos -----> "+dato);
        return dato;
    }
    public ArrayList<String> eliminarConjuntosRepetidos(ArrayList<String> al){
        ArrayList<String> newList = new ArrayList<String>();
        System.out.println("ArrayList con(puede) los conjuntos repetidos ---> "+al);
        for (int i=0; i<al.size(); i++) {
            if (!newList.contains(al.get(i))) {
                newList.add(al.get(i));
            }
        }
        System.out.println("ArrayList sin los conjuntos repetidos ---> "+al);
        return newList;
    }
    public String buscarElConjunto(String conjunto){
        // Buscamos el conjunto en la tabla de epsilons
        String line="", conjuntoTabla=""; int posicion=0, estadoTabla=0, estadoBuscar=0;
        String[] separarEstados = conjunto.split(","); conjunto="";
        for(int i=0; i<separarEstados.length; i++){
            try {
                Scanner input = new Scanner(TABLA_E);
                while (input.hasNext()) {
                    line = input.next();
                    if(posicion==0){
                        // Estado que estamos buscando
                        estadoBuscar=Integer.parseInt(separarEstados[i]);
                        // Estado de la tabla
                        estadoTabla=Integer.parseInt(line);
                        posicion++;
                    }else if(posicion==1){
                        // Conjunto de la tabla
                        conjuntoTabla=line;
                        if(estadoBuscar==estadoTabla){
                            if(conjunto.equals("")){
                                conjunto=conjuntoTabla;
                            }else{
                                conjunto+=","+conjuntoTabla;
                            }
                           
                        }
                        posicion=0;
                    }
                }
                input.close();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(conjunto.equals("")){
            conjunto="NULL";
        }else{
            conjunto=eliminarEstadosRepetidos(conjunto);
        }
        return conjunto;
    }
    public void transformar_tabla_afd(File archivoLeer, ArrayList<String> estados){
        int posicion=0; String conjuntoEstado="", conjuntoComparar="";String line="";
        // La cantidad de columnas de el txt, la suma del alfabeto mas la columna q(Numero de estado ) y e(Conjunto del estado)
        int cantidad_de_columnas = alfabeto.size()+2;
        try {
            Scanner input = new Scanner(archivoLeer);
            // Nos saltamos el encabezado
            input.nextLine();
            while (input.hasNext()) {
                line = input.next();
                if(posicion==0){
                    // Columna q
                    posicion++;
                }else if(posicion==1){
                    // Columna e
                    conjuntoEstado=line;
                    //System.out.println(" ConjuntoEstado ---->"+conjuntoEstado);
                    posicion++;
                }else if(posicion<=cantidad_de_columnas){
                    // Columna de el caracter del alafabeto
                    conjuntoComparar=line;
                    //System.out.println(" ConjuntoComparar ---->"+conjuntoComparar);
                    if(!conjuntoComparar.equals("NULL")){
                        System.out.println("{q"+buscar_numero_conjunto(estados, conjuntoEstado)+", "+alfabeto.get(posicion-2)+", q"+buscar_numero_conjunto(estados,conjuntoComparar)+"}");
                        escribir_afd_archivo(AFD, ""+buscar_numero_conjunto(estados, conjuntoEstado), alfabeto.get(posicion-2), ""+buscar_numero_conjunto(estados,conjuntoComparar));
                    }
                    posicion++;
                    if(posicion==cantidad_de_columnas){
                        posicion=0;
                    }
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public int buscar_numero_conjunto(ArrayList<String> estados, String estadoBuscar){
        int estado=-1;
        for(int i = 0; i<estados.size(); i++){
            if(estadoBuscar.equals(estados.get(i))){
                return i;
            }
        }
        return estado;
    }
    public void escribir_afd_archivo(File archivo, String estado1, String caracter, String estado2){
        try(FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println(estado1+" "+caracter+" "+estado2);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
}
