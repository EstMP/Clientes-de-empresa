/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog06_clientes_empresa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author EstMP
 */
public class PROG06_CLIENTES_EMPRESA {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        String opcion = "", cliente;
        boolean pase;

        while (!"6".equals(opcion)) {

            pase = false;

            try {
                Cliente.cargarClientes();
                System.out.println("Clientes encontardos: " + Cliente.getListSize());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("MENÚ");
            System.out.println("(1) Añadir cliente");
            System.out.println("(2) Listar cliente");
            System.out.println("(3) Buscar cliente");
            System.out.println("(4) Borrar cliente");
            System.out.println("(5) Borrar fichero de clientes completamente");
            System.out.println("(6) Salir de la aplicación");
            System.out.println("(0) Añadir clientes de prueba");

            System.out.print("Selecciona una opción: ");
            opcion = Lectura.lecturaTeclado();

            switch (opcion) {
                case "0":
                    try {
                        addCliente("Pablo Perez", "54585858G", "987987987", "c/Tal", 100);
                        addCliente("Ruben Villar", "45121458N", "987654654", "C/Asd", 150);
                        addCliente("Zacarías Satrústegui", "52545215D", "987654653", "C/Asds", 1150);
                        addCliente("Pedro Díaz", "65854857J", "987452354", "C/Asdss", 150);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                    break;

                case "1":
                    while (!pase) {
                        System.out.println("\nAÑADIR CLIENTE");
                        pase = addCliente();
                    }
                    break;

                case "2":
                    System.out.println("\nLISTAR CLIENTES");
                    System.out.println("Número de clientes: " + Cliente.getListSize() + "\n");
                    for (Object result : Cliente.listaObjetos) {
                        // Cast de objeto a Cliente
                        Cliente cli = (Cliente) result;
                        System.out.printf("CLIENTE Nº%d%n%s", Cliente.listaObjetos.indexOf(cli), cli);
                    }
                    break;

                case "3":
                    System.out.println("\nBUSCAR CLIENTE");
                    System.out.print("Introduce un nombre: ");
                    cliente = Lectura.lecturaTeclado();
                    buscar(cliente);
                    break;

                case "4":
                    System.out.println("\nBORRAR CLIENTE");
                    System.out.print("Introduce un nombre: ");
                    cliente = Lectura.lecturaTeclado();
                    /* 
                    1º Se buscan clientes que coincidan con la búsqueda
                    2º El dato devuelto es una lista de objetos con las 
                        coincidencias y es almacenado en la variable clientes
                     */
                    List<Object> clientes = buscar(cliente);

                    if (!clientes.isEmpty()) {
                        System.out.print("Introduce el índice a borrar: ");
                        try {
                            int index;
                            /*
                            Con la lista nueva se obtiene el objeto a través del 
                            índice introducido por teclado, que será pasado como 
                            parámetro a la función borrarCliente(Object obj)
                             */
                            index = Integer.parseInt(Lectura.lecturaTeclado());
                            Cliente.borrarCliente(clientes.get(index));
                        } catch (NumberFormatException e) {
                            System.err.println("El índice introducido no es correcto");
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("El índice introducido está fuera del rango");
                        } catch (FileNotFoundException e) {
                            System.err.println(e.getMessage());
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    break;

                case "5":
                    System.out.println("\nBORRAR TODOS LOS CLIENTES");
                    try {
                        Cliente.borrarArchivo();
                        System.out.println("El archivo ha sido borrado");
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case "6":
                    return;

                default:
                    System.err.println("Opción incorrecta");
                    break;
            }

            //System.err.flush();
            System.out.println("Introduce una tecla para continuar");
            Lectura.pulsacionTecla();
        }

    }

    /* Retorna una nueva lista de objetos con los resultados obtenidos.
    Se usa para 'Listar clientes' y 'Borrar cliente' */
    private static List<Object> buscar(String cliente) {
        List<Object> clientes = Cliente.buscarCliente(cliente);
        Cliente cli;

        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes");
        } else {
            for (Object cliente1 : clientes) {
                cli = (Cliente) cliente1;
                System.out.println("\t" + clientes.indexOf(cliente1) + ") " + cli.getNombre());
            }
        }
        return clientes;
    }

    /* Método que engloba la validación de los datos introducidos por teclado y 
    añadir clientes:
        VALIDAR
        CREAR OBJETO
        GUARDAR
     */
    private static boolean addCliente() throws Exception {
        boolean pase = false;
        String nif = null, nombre = "", tlf = "", direccion, stringDeuda = null;
        double deuda = 0;

        while (nif == null) {
            System.out.print("NIF: ");
            nif = Lectura.lecturaTeclado().toUpperCase();
            try {
                nif = Herramientas.comprobarDNI(nif);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return false;
            }
            System.out.println(nif);
        }

        while (nombre.length() < Cliente.MIN_LONG_NOMBRE
                || nombre.length() > Cliente.MAX_LONG_NOMBRE) {

            System.out.println("Nombre (3-10)");
            nombre = Lectura.lecturaTeclado();
        }

        while (tlf.length() != 9) {
            System.out.println("Teléfono (9)");
            tlf = Lectura.lecturaTeclado();

            try {
                Integer.parseInt(tlf);
            } catch (NumberFormatException e) {
                tlf = "";
            }
        }

        // Puede ser nulo
        System.out.println("Dirección");
        direccion = Lectura.lecturaTeclado();

        while (stringDeuda == null) {
            System.out.println("Deuda");
            stringDeuda = Lectura.lecturaTeclado();

            try {
                deuda = Double.parseDouble(stringDeuda);
            } catch (NumberFormatException e) {
                stringDeuda = null;
            }
        }

        // Declarar e instanciar el objeto
        Cliente cli = new Cliente(nif, nombre, tlf, direccion, deuda);
        System.out.println("Un nuevo cliente ha sido creado:\n" + cli);

        try {
            Cliente.guardarCliente(cli);
            System.out.println("El cliente ha sido guardado correctamente");
        } catch (IOException e) {            
            System.err.println(e.getMessage());
        } finally {
            pase = true;
        }

        return pase;
    }

    // Método sobrecargado para añadir clientes de prueba
    private static void addCliente(String nombre, String nif, String tlf,
            String direccion, double deuda) throws IOException, Exception {
        Cliente cli = new Cliente(nif, nombre, tlf, direccion, deuda);
        try {
            Cliente.guardarCliente(cli);
        } catch (IOException e) {
            throw e;
        }

    }

}
