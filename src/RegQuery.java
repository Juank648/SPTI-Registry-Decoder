import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class RegQuery {

    private static final String REGQUERY_UTIL = "reg query ";
    private static final String REGSTR_TOKEN = "REG_SZ";
    private static final String REGDWORD_TOKEN = "REG_DWORD";
    private static final String REGBINARY_TOKEN = "REG_BINARY";

    private static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL +
            "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
            + "Explorer\\UserAssist\\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\" " + " /S";


    public static List<String> getCurrentUserPersonalFolderPath() {
        try {

            Process process = Runtime.getRuntime().exec(PERSONAL_FOLDER_CMD);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultOfExecution = null;
            List<String> registry= new LinkedList<>();

            String val = "";
            while((resultOfExecution = br.readLine()) != null){

                for(int i = 4; i < resultOfExecution.length();i++){
                    if (resultOfExecution.charAt(i) != ' ') {

                        val += resultOfExecution.charAt(i);
                    } else {

                        registry.add(val);
                        val = "";
                        break;

                    }
                }


            }

            return registry;



        }
        catch (Exception e) {
            return null;
        }

    }




    public static String rotar(String cadenaOriginal, int rotaciones) {
        // En ASCII, la a es 97, b 98, A 65, B 66, etcétera
        final int LONGITUD_ALFABETO = 26, INICIO_MINUSCULAS = 97, INICIO_MAYUSCULAS = 65;
        String cadenaRotada = ""; // La cadena nueva, la que estará rotada
        for (int x = 0; x < cadenaOriginal.length(); x++) {
            char caracterActual = cadenaOriginal.charAt(x);
            // Si no es una letra del alfabeto entonces ponemos el char tal y como está
            // y pasamos a la siguiente iteración
            if (!Character.isLetter(caracterActual)) {
                cadenaRotada += caracterActual;
                continue;
            }

            int codigoAsciiDeCaracterActual = (int) caracterActual;
            boolean esMayuscula = Character.isUpperCase(caracterActual);

            // La posición (1 a 26) que ocupará la letra después de ser rotada
            // El % LONGITUD_ALFABETO se utiliza por si se pasa de 26. Por ejemplo,
            // la "z", al ser rotada una vez da el valor de 27, pero en realidad debería
            // regresar a la letra "a", y con mod hacemos eso ya que 27 % 26 == 1,
            // 28 % 26 == 2, etcétera ;)
            int nuevaPosicionEnAlfabeto = ((codigoAsciiDeCaracterActual
                    - (esMayuscula ? INICIO_MAYUSCULAS : INICIO_MINUSCULAS)) + rotaciones) % LONGITUD_ALFABETO;
            // Arreglar rotaciones negativas
            if (nuevaPosicionEnAlfabeto < 0)
                nuevaPosicionEnAlfabeto += LONGITUD_ALFABETO;
            int nuevaPosicionAscii = (esMayuscula ? INICIO_MAYUSCULAS : INICIO_MINUSCULAS) + nuevaPosicionEnAlfabeto;
            // Convertir el código ASCII numérico a su representación como símbolo o letra y
            // concatenar
            cadenaRotada += Character.toString((char) nuevaPosicionAscii);
        }
        return cadenaRotada;
    }

    public static List<String> Controlador(List<String> listaCifrada) {
        List<String> listaDesCifrada = new LinkedList<>();
        for(int i = 0; i < listaCifrada.size(); i++){
            listaDesCifrada.add(rotar(listaCifrada.get(i), 13));
        }
        return listaDesCifrada;
    }

    public static void main(String s[]) {

        List<String> result = getCurrentUserPersonalFolderPath();
        List<String> listaDesCifrada = Controlador(result);
        for(int i = 0; i < listaDesCifrada.size(); i++){
            System.out.println(listaDesCifrada.get(i));
        }
    }
}