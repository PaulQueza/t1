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
                er_desglosar.push(er_array[i]);
            }else if(er_array[i]==')'){
                while (!er_desglosar.peek().equals('(')) {
                    er_desglosado += er_desglosar.pop();
                }
                er_desglosar.pop();
            }else{
                terminar=false;
                while (er_desglosar.size() > 0 && !terminar) {
                    Character PeekCaracter = er_desglosar.peek();
                    Integer PeekPadre = obtenerPadre(PeekCaracter),ActualPadre = obtenerPadre(er_array[i]);
                    if (PeekPadre >= ActualPadre) er_desglosado += er_desglosar.pop();else terminar=true;
                }
                er_desglosar.push(er_array[i]);
            }
        } 
        while (er_desglosar.size() > 0){er_desglosado += er_desglosar.pop();}
        er_desg = er_desglosado;

    }  
    public int obtenerPadre(char c){
        Integer padre_char = padre.get(c);if(padre_char == null){padre_char = 6;}
        return padre_char;
    }
    public String getErDesglosado(){return er_desg;}   
}