/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog06_clientes_empresa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/*EXCEPCIONES*/
import java.io.EOFException;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 *
 * @author EstMP
 */
public class Cliente implements Serializable {

    final static int MIN_LONG_NOMBRE = 3;
    final static int MAX_LONG_NOMBRE = 10;    
    final static String RUTA = "clientes.dat";
    
    public static List<Object> listaObjetos;

    /* Todos los tipos primitivos en Java son serializables por defecto. 
    (Al igual que los arrays y otros muchos tipos estándar). */
    private String nif;
    private String nombre;
    private String tlf;
    private String direccion;
    private double deuda;

    /**
     *
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros
     *
     * @param nif
     * @param nombre
     * @param tlf
     * @param deuda
     * @param direccion
     * @throws java.lang.Exception
     */
    public Cliente(String nif, String nombre, String tlf, String direccion, double deuda) throws Exception {
        this.nif = Herramientas.comprobarDNI(nif);
        this.nombre = nombre;
        this.tlf = tlf;
        this.direccion = direccion;
        this.deuda = deuda;
    }

    /**
     *
     * @return
     */
    public String getNif() {
        return nif;
    }

    /**
     *
     * @param nif
     * @throws java.lang.Exception
     */
    public void setNif(String nif) throws Exception {
        this.nif = Herramientas.comprobarDNI(nif);
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getTlf() {
        return tlf;
    }

    /**
     *
     * @param tlf
     */
    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    /**
     *
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return
     */
    public double getDeuda() {
        return deuda;
    }

    /**
     *
     * @param deuda
     */
    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    /* Método sobreescrito toString() que mostrará los datos del cliente */
    @Override
    public String toString() {
        //return "Cliente{" + "nif=" + nif + ", nombre=" + nombre + ", tlf=" + tlf + ", direccion=" + direccion + ", deuda=" + deuda + '}';
        return String.format("\tNombre: %s%n"
                + "\tDNI: %s%n"
                + "\tTeléfono: %s%n"
                + "\tDirección: %s%n"
                + "\tDeuda: %s%n%n",
                getNombre(), getNif(), getTlf(), getDireccion(), getDeuda());
    }

    /*MÉTODOS DE CLASE ESTÁTICOS*/
    
    /**
     * Devuelve el tamaño de la lista de objetos
     *
     * @return
     */
    public static int getListSize() {
        return listaObjetos.size();
    }

    /**
     * Lee los objetos del archivo .dat y se guardan en una lista de objetos
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.io.IOException
     */
    public static void cargarClientes() throws ClassNotFoundException, IOException {
        listaObjetos = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(RUTA);
            while (true) {
                ois = new ObjectInputStream(fis);
                listaObjetos.add(ois.readObject());
            }
        } catch (EOFException e) {
            //throw new EOFException("Fin del archivo");
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Guardar en el archivo el objeto serializado
     *
     * @param cli
     * @throws java.io.FileNotFoundException
     */
    public static void guardarCliente(Cliente cli) throws FileNotFoundException, IOException {
        /* Guardar en el archivo el objeto serializado 
            Variable booleana a true para añadir al archivo si ya existe */
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(RUTA), true);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(cli);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }                
            } catch (IOException e) {
                throw e;
            }
        }
        
    }

    /**
     * Devuelve una lista de objetos con los clientes encontrados
     *
     * @param cliente
     * @return
     */
    public static List<Object> buscarCliente(String cliente) {
        Cliente cli;
        List<Object> objetosEncontrados;
        objetosEncontrados = new ArrayList<>();

        // Bucle para mostrar todos los objetos de la lista
        for (Object result : listaObjetos) {
            cli = (Cliente) result;
            /* Cada atributo "nombre" del objeto se compara con el texto
            introducido en la búsqueda, todo en mayúsculas */
            if (cli.getNombre().toUpperCase().contains(cliente.toUpperCase())) {
                objetosEncontrados.add(result);
            }
        }
        return objetosEncontrados;
    }

    /**
     *
     * @param obj
     * @throws java.io.IOException
     */
    public static void borrarCliente(Object obj) throws IOException {
        Cliente cli;
        // Borrar objeto de la lista
        listaObjetos.remove(obj);

        // Eliminar archivo viejo
        borrarArchivo();

        // Guardar cada objeto de la lista en archivo nuevo
        for (Object listaObjeto : listaObjetos) {
            cli = (Cliente) listaObjeto;
            guardarCliente(cli);
        }
    }

    /**
     *
     * @throws java.io.IOException
     */
    public static void borrarArchivo() throws IOException  {
        File f = null;
        try {
            f = new File(RUTA);
            Files.delete(f.toPath());        
        } catch (IOException e) {
            if (f.exists()) {
                throw new IOException("Ha ocurrido un error al borrar el archivo " + e.getMessage());
            } else {
                throw new FileNotFoundException("No se encuentra el archivo " + e.getMessage());
            }
        }
    }
}
