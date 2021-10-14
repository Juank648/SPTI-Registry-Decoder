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
        final int LONGITUD_ALFABETO = 26, INICIO_MINUSCULAS = 97, INICIO_MAYUSCULAS = 65;
        String cadenaRoot = ""; 
        for (int x = 0; x < cadenaOriginal.length(); x++) {
            char caracterActual = cadenaOriginal.charAt(x);
            if (!Character.isLetter(caracterActual)) {
                cadenaRoot += caracterActual;
                continue;
            }
            else {
                int codigoAsciiDeCaracterActual = (int) caracterActual;
                boolean esMayuscula = Character.isUpperCase(caracterActual);
                int INICIO;
                if(esMayuscula){INICIO = INICIO_MAYUSCULAS;} else {INICIO = INICIO_MINUSCULAS;}
                int nuevaPosicionEnAlfabeto = ((codigoAsciiDeCaracterActual
                        - INICIO + rotaciones)) % LONGITUD_ALFABETO;
                int nuevaPosicionAscii = INICIO + nuevaPosicionEnAlfabeto;
                cadenaRoot += Character.toString((char) nuevaPosicionAscii);
            }


        }
        return cadenaRoot;
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