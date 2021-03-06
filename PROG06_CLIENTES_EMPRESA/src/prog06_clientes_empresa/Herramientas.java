/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog06_clientes_empresa;

/**
 *
 * @author EstMP
 */
public class Herramientas {

    public static String comprobarDNI(String nif) throws Exception {
        String num;
        char letra;
        int _nif;

        if (nif.length() > 7 && nif.length() < 10) {
            num = nif.substring(0, 8);

            try {
                _nif = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Código no válido");
            }

            switch (nif.length()) {
                case 8:
                    nif = num + validarNIF(_nif);
                    break;
                case 9:
                    letra = nif.charAt(8);

                    if (!Character.isLetter(letra)) {
                        throw new Exception("El último caracter no es una letra");
                    }
                    
                    if (validarNIF(_nif) != letra) {
                        throw new Exception("El control no coincide");
                    }
                    break;
            }
        } else {
            throw new Exception("Código no válido");
        }
        return nif;
    }

    private static char validarNIF(int nif) {
        final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
        char letra;

        int modulo = nif % 23;
        letra = LETRAS.charAt(modulo);
        nif += letra;

        return letra;
    }
}
