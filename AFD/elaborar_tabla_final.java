package AFD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class elaborar_tabla_final {
    ArrayList<String> alfabeto = new ArrayList<String>();
    ArrayList<String> conjuntosNuevos = new ArrayList<String>();
    File TH = new File("./Archivos/TH.txt");
    File TABLA_E = new File("./Archivos/tabla_e.txt");
    File TABLA_TRANSICIONES = new File("./Archivos/tabla_transiciones.txt");
    File TABLA_FINAL = new File("./Archivos/tabla_final.txt");
    File AFD = new File("./Archivos/AFD.txt");
    FileWriter fw; int estadoFinal;

    public elaborar_tabla_final(ArrayList<String> alfabeto, int estadoFinal) {
        this.estadoFinal=estadoFinal;
        this.alfabeto = alfabeto;
        try {
            fw = new FileWriter(TABLA_FINAL);
            fw = new FileWriter(AFD);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        tabla_final();
    }

    public void tabla_final() {
        String encabezado = "";
        // Agregamos el encabesado de la tabla
        for (int h = 0; h < alfabeto.size(); h++) {
            if (h == 0) {
                encabezado = "" + alfabeto.get(h);
            } else {
                encabezado += " " + alfabeto.get(h);
            }
        }
        escribirTablaEncabezado(encabezado, TABLA_FINAL);

        // Hacemos la tabla
        String conjunto = obtenerPrimerEstadoTabla(TABLA_E);
        buscarConjuntos(conjunto, alfabeto, TABLA_TRANSICIONES);
        transformar_tabla_afd(TABLA_FINAL, conjuntosNuevos);

    }

    public String obtenerPrimerEstadoTabla(File archivo) {
        // Encuentra todos los epsilon de un estado
        String primerEstado = "", conjunto1 = "", conjunto2 = "", line = "";
        int posicion = 0;
        try {
            Scanner input = new Scanner(archivo);
            while (input.hasNext()) {
                line = input.next();
                if (posicion == 0) {
                    // Primer conjunto
                    conjunto1 = line;
                } else if (posicion == 1) {
                    // Segundo conjunto
                    conjunto2 = line;
                }
                posicion++;
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (conjunto2.equals("NULL")) {
            primerEstado = conjunto1;
        } else {
            primerEstado = conjunto1 + "," + conjunto2;
        }
        return primerEstado;

    }

    public void buscarConjuntos(String estadosBuscar, ArrayList<String> alfabeto, File archivo) {
        // añadimos el primer estado
        conjuntosNuevos.add(estadosBuscar);
        // Encuentra todos los epsilon de un estado
        String transicionConjunto="";
        int nestado = 0;
        String[] estadosSeparados;
        for (int recorrer = 0; recorrer < conjuntosNuevos.size(); recorrer++) {
            estadosSeparados = conjuntosNuevos.get(recorrer).split(",");
            transicionConjunto=""; 
            for (int i = 0; i < alfabeto.size(); i++) {
                if(transicionConjunto.equals("")){
                    transicionConjunto = conjuntosNuevos.get(recorrer)+" "+buscarTransicionesConjunto(alfabeto.get(i), estadosSeparados,i);
                }else{
                    transicionConjunto +=" "+ buscarTransicionesConjunto(alfabeto.get(i), estadosSeparados,i);
                }
            }
            escribirTablaFinal(nestado, transicionConjunto+" "+revisar_estadoFinal(conjuntosNuevos.get(recorrer)), TABLA_FINAL);
            nestado++;
        }
    }

    public String buscarTransicionesConjunto(String letraAlfabeto, String[] conjunto, int posicionAlfabeto) {
        String transiciones="";
        String conjuntoEncontrado = "";
        for (int i = 0; i < conjunto.length; i++) {
            conjuntoEncontrado = leerTablaTransiciones(letraAlfabeto, conjunto[i],posicionAlfabeto);
            if(!conjuntoEncontrado.equals("NULL")){
                if(transiciones.equals("")){
                    transiciones=conjuntoEncontrado;
                }else{
                    transiciones+=","+conjuntoEncontrado;
                }
            }
        }
        transiciones = buscarTablaEpsilon(transiciones);
        transiciones = eliminarEstadosRepetidos(transiciones);
        conjuntosNuevos.add(transiciones);
        conjuntosNuevos = eliminarConjuntosRepetidos(conjuntosNuevos);
        return transiciones;
    }

    public String leerTablaTransiciones(String letraAlfabeto, String estado, int posicionAlfabeto) {
        String conjuntoEncontrado = "";
        String line;
        try {
            int posicion = 0;
            Scanner input = new Scanner(TABLA_TRANSICIONES);
            // Nos saltamos el encabezado
            input.nextLine();
            // Buscamos el estado en la tabla
            while (input.hasNext()) {
                line = input.next();
                if (line.equals(estado)) {
                    // Si encotramos el estado, vemos las transiciones para la letra en especifico
                    while (posicion <= posicionAlfabeto) {
                        line = input.next();
                        posicion++;
                    }
                    /*
                     * Si hay un NULL quiere decir que no hay transicion, de caso contrario
                     * quiere decir que
                     * si hay y hay que guardarlo
                     */
                    if (line.equals("NULL")) {
                        // No se agrega nada
                    } else {
                        if (conjuntoEncontrado.equals("")) {
                            conjuntoEncontrado = line;
                        } else {
                            conjuntoEncontrado += "," + line;
                        }
                    }
                    posicion = 0;
                    break;
                } else {
                    input.nextLine();
                }
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (conjuntoEncontrado.equals("")) {
            return "NULL";
        } else {
            return conjuntoEncontrado;
        }
    }

    public void escribirTablaFinal(int estado, String conjunto, File archivo) {
        // Lo usamos para escribir la tabla en el txt
        try (FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(estado + " " + conjunto);
        } catch (IOException e) {
            // exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }

    public void escribirTablaEncabezado(String alfabeto, File archivo) {
        // Lo usamos para escribir la tabla en el txt
        try (FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("q e " + alfabeto);
        } catch (IOException e) {
            // exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }

    public String eliminarEstadosRepetidos(String dato) {
        ArrayList<String> listHelp = new ArrayList<String>();
        ArrayList<String> newList = new ArrayList<String>();
        String[] separarEstados = dato.split(",");
        Arrays.sort(separarEstados);
        for (int i = 0; i < separarEstados.length; i++) {
            listHelp.add(separarEstados[i]);
        }
        for (int i = 0; i < listHelp.size(); i++) {
            if (!newList.contains(listHelp.get(i))) {
                newList.add(listHelp.get(i));
            }
        }
        dato = "";
        for (int i = 0; i < newList.size(); i++) {
            if (i == 0) {
                dato = newList.get(i);
            } else {
                dato += "," + newList.get(i);
            }
        }
        return dato;
    }

    public ArrayList<String> eliminarConjuntosRepetidos(ArrayList<String> al) {
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < al.size(); i++) {
            if (!newList.contains(al.get(i))) {
                newList.add(al.get(i));
            }
        }
        return newList;
    }
    public String buscarTablaEpsilon(String conjunto) {
        String line, nEstado = "", conjuntoEstados = "";
        int posicion = 0;
        ArrayList<String> tablaEpsilon = new ArrayList<String>();
        // Carga los datos de la tabla epsilon
        try {
            Scanner input = new Scanner(TABLA_E);
            while (input.hasNext()) {
                line = input.next();
                if (posicion == 0) {
                    // NEstado --> Primera columna de la tabla epsilon
                    nEstado = line;
                    posicion++;
                } else if (posicion == 1) {
                    // Conjunto de estados --> Segunda columna de la tabla epsilon
                    conjuntoEstados = line;
                    if (!conjuntoEstados.equals("NULL")) {
                        tablaEpsilon.add(nEstado + "," + conjuntoEstados);
                    } else {
                        tablaEpsilon.add(nEstado);
                    }
                    posicion = 0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Buscar en la tabla epsilon los conjuntos
        String[] separarEstados = conjunto.split(",");
        String elementoTabla;
        int estadoBuscado;
        ArrayList<String> recorrerEstadosEncontrados = new ArrayList<String>();
        for (int i = 0; i < separarEstados.length; i++) {
            recorrerEstadosEncontrados.add(separarEstados[i]);
        }
        for (int i = 0; i < recorrerEstadosEncontrados.size(); i++) {
            estadoBuscado = Integer.parseInt(recorrerEstadosEncontrados.get(i));
            elementoTabla = tablaEpsilon.get(estadoBuscado);
            if (elementoTabla.equals("" + estadoBuscado)) {
                // No se encontro ningun elemento distinto al que se esta buscando
            } else {
                // Se encontro un elemento distinto al que se esta buscando
                separarEstados = elementoTabla.split(",");
                for (int j = 0; j < separarEstados.length; j++) {
                    if (!separarEstados[j].equals("" + estadoBuscado)) {
                        recorrerEstadosEncontrados.add(separarEstados[j]);
                    }
                }
            }
        }
        conjunto = "";
        for (int i = 0; i < recorrerEstadosEncontrados.size(); i++) {
            if (i == 0) {
                conjunto = recorrerEstadosEncontrados.get(i);
            } else {
                conjunto += "," + recorrerEstadosEncontrados.get(i);
            }
        }
        return conjunto;
    }

    public void transformar_tabla_afd(File archivoLeer, ArrayList<String> estados) {
        int posicion = 0;
        String conjuntoEstado = "", conjuntoComparar = "";
        String line = "";
        // La cantidad de columnas de el txt, la suma del alfabeto mas la columna
        int cantidad_de_columnas = alfabeto.size() + 2;
        try {
            Scanner input = new Scanner(archivoLeer);
            // Nos saltamos el encabezado
            input.nextLine();
            while (input.hasNext()) {
                line = input.next();
                if (posicion == 0) {
                    // Columna q
                    posicion++;
                } else if (posicion == 1) {
                    // Columna estados
                    conjuntoEstado = line;
                    // System.out.println(" ConjuntoEstado ---->"+conjuntoEstado);
                    posicion++;
                } else if (posicion < cantidad_de_columnas) {
                    // Columna de el caracter del alafabeto
                    conjuntoComparar = line;
                    if (!conjuntoComparar.equals("NULL")) {
                        escribir_afd_archivo(AFD, "" + buscar_numero_conjunto(estados, conjuntoEstado),
                                alfabeto.get(posicion - 2), "" + buscar_numero_conjunto(estados, conjuntoComparar));
                    }
                    posicion++;
                }else{
                    posicion = 0;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int buscar_numero_conjunto(ArrayList<String> estados, String estadoBuscar) {
        int estado = -1;
        for (int i = 0; i < estados.size(); i++) {
            if (estadoBuscar.equals(estados.get(i))) {
                return i;
            }
        }
        return estado;
    }
    public String revisar_estadoFinal(String conjunto){
        // Funcion que me sirve para revisar si un conjunto tiene algun estado final
        // Si tiene un estado final, entonces ese estado es final
        String[] separarEstados = conjunto.split(",");
        int estado;
        for(int i =0; i<separarEstados.length; i++){
            estado=Integer.parseInt(separarEstados[i]);
            if(estado==estadoFinal){
                return "FINAL";
            }
        }
        return "NOFINAL";
    }

    public void escribir_afd_archivo(File archivo, String estado1, String caracter, String estado2) {
        try (FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(estado1 + " " + caracter + " " + estado2);
        } catch (IOException e) {
            // exception handling left as an exercise for the reader
            System.out.println("ERROR");
        }
    }
}