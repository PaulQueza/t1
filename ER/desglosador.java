package ER;
import java.util.ArrayList;
public class desglosador {
    String er_desg;
    public desglosador(String er){      
        this.er_desg = er;
        desglosar(er);                        
    }
    public void desglosar(String er){
        String er_parseado=""; boolean terminar=false;
        ArrayList<String> guardarDatos = new ArrayList<String>(); 
        char[] er_array = er.toCharArray();
        for(int i = 0 ; i<er_array.length; i++){
            if(er_array[i] == '('){
                guardarDatos.add(""+er_array[i]);
            }else if(er_array[i] == ')'){
                for(int j=guardarDatos.size()-1; j>=0; j--){
                    if(!guardarDatos.get(j).equals("(")){
                        er_parseado += guardarDatos.get(j);
                        guardarDatos.remove(j);
                    } 
                    else break;
                }
                if(guardarDatos.size()-1 < 0){
                    guardarDatos = new ArrayList<String>(); 
                }else{
                    guardarDatos.remove(guardarDatos.size()-1);
                }
                System.out.println(" ");
            }else{
                terminar = false;
                for(int j=guardarDatos.size()-1;( j>=0 && !terminar ); j--){
                    String AnteriorCaracter = guardarDatos.get(guardarDatos.size() - 1);
                    String ActualCaracter = ""+er_array[i];
                    int prioridadActual=-1, prioridadAnterior=-1;
                    // Prioridad del caracter actual del er_parseador
                    if(ActualCaracter.equals("(")){
                        prioridadActual = 10; // Prioridad Alta
                    }
                    else if(ActualCaracter.equals(".")){
                        prioridadActual = 8; // Prioridad Media
                    }
                    else if(ActualCaracter.equals("|")){
                        prioridadActual = 9; // Prioridad Alta
                    }
                    else if(ActualCaracter.equals("*")){
                        prioridadActual = 7; // Prioridad baja
                    }else{
                        prioridadActual = 0; // Prioridad muy baja
                    }

                    // Prioridad del caracter actual del ArrayList guardarDatos
                    if(AnteriorCaracter.equals("(")){
                        prioridadAnterior = 10; // Prioridad Alta
                    }
                    else if(AnteriorCaracter.equals(".")){
                        prioridadAnterior = 8; // Prioridad Media
                    }
                    else if(AnteriorCaracter.equals("|")){
                        prioridadAnterior = 9; // Prioridad Alta
                    }
                    else if(AnteriorCaracter.equals("*")){
                        prioridadAnterior = 7; // Prioridad baja
                    }else{
                        // Caracter no especial
                        prioridadAnterior = 0; // Prioridad muy baja
                    }
                    if (prioridadActual >= prioridadAnterior){
                        er_parseado += guardarDatos.get(j);
                        guardarDatos.remove(j);
                    }else{
                        terminar=true;
                    } 
                }
                guardarDatos.add(""+er_array[i]);
            }
        }
        for(int i=guardarDatos.size()-1; i>=0; i--){
            er_parseado += guardarDatos.get(i);
        }
        er_desg = er_parseado; 
    } 
    public String getErDesglosado(){return er_desg;}
}