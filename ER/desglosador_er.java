package ER;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class desglosador_er {
    private Map<Character, Integer> padre;public String er_desg;
    public desglosador_er(String er){
        Map<Character, Integer> caracteres_claves = new HashMap<>();        
        // Caracteres claves
        caracteres_claves.put('(', 1);
        caracteres_claves.put('|', 2);
        caracteres_claves.put('.', 3);
        caracteres_claves.put('*', 4);
        padre = Collections.unmodifiableMap(caracteres_claves);
        desglosar(er);                        
    }
    public void desglosar(String er){
        Stack<Character> er_desglosar = new Stack<>();String er_desglosado = ""; Boolean terminar=false;char[] er_array = er.toCharArray();
        for(int i = 0 ; i<er_array.length; i++){
            if(er_array[i]=='('){
                System.out.println("Entro al if er_array[i]=='(' -- ");
                er_desglosar.push(er_array[i]);
                System.out.println("Se agrego un elemento en el stack --> "+er_array[i]);
                System.out.println(" ");
            }else if(er_array[i]==')'){
                System.out.println("Entro al if er_array[i]==')' -- ");
                while (!er_desglosar.peek().equals('(')) {
                    System.out.println("Mientras no se encuentre el caracter '(' entonces va a quitar elementos dentro del stack y agregarlos en el er_desglosado");
                    er_desglosado += er_desglosar.pop();
                    System.out.println("er_desglosado se agregaron caracteres --> "+er_desglosado);
                }
                System.out.println("Elimina todos los elementos del stack");
                er_desglosar.pop();
                System.out.println("valores de el stack --> "+er_desglosar);
                System.out.println(" ");
            }else{
                System.out.println("El caracter puede ser un caracter . | * o cualquier letra");
                terminar=false;
                while (er_desglosar.size() > 0 && !terminar) {
                    System.out.println("Mientras que el tamano del stack sea mayor que 0");
                    Character PeekCaracter = er_desglosar.peek();
                    Integer PeekPadre = obtenerPadre(PeekCaracter);
                    Integer ActualPadre = obtenerPadre(er_array[i]);
                    System.out.println("Caracter: "+PeekCaracter+", PeekPadre: "+PeekPadre+", ActualPadre: "+ActualPadre);
                    if (PeekPadre >= ActualPadre){
                        System.out.println("Si el PeekPadre>=ActualPadre, se quita el elemento del stack y se agrega en el string");
                        er_desglosado += er_desglosar.pop();
                        System.out.println("er_desglosado se agregaron caracteres --> "+er_desglosado);
                    }else{
                        System.out.println("Si el PeekPadre<ActualPadre, Termina la funcion WHILE");
                        terminar=true;
                    } 
                }
                System.out.println("Se agrega un elemento al stack -->"+er_array[i]);
                er_desglosar.push(er_array[i]);
                System.out.println(" ");
                System.out.println("STACK ----> "+er_desglosar);
            }
        }
        System.out.println("TERMINO EL FOR");
        System.out.println("er_desglosado ----> "+er_desglosado);
        System.out.println("STACK ----> "+er_desglosar);
        while (er_desglosar.size() > 0){er_desglosado += er_desglosar.pop();}
        er_desg = er_desglosado;
    }
    public int obtenerPadre(char c){
        Integer padre_char = padre.get(c);if(padre_char == null){padre_char = 6;}
        return padre_char;
    }
    public String getErDesglosado(){return er_desg;}   
}