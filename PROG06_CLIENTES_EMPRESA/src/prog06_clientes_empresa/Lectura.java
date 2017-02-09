/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog06_clientes_empresa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author EstMP
 */
public class Lectura {

    public static String lecturaTeclado() throws Exception {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            return line;
        } catch (IOException e) {
            throw e;
        }
    }
    
     public static int pulsacionTecla () throws Exception {
        try { 
            InputStreamReader inputStreamReader = new InputStreamReader(System.in); 
            BufferedReader reader = new BufferedReader(inputStreamReader); 
            int c = reader.read();            
            return c; 
        } 
        catch (IOException e) { 
            throw e;  
        }
    }     
}
