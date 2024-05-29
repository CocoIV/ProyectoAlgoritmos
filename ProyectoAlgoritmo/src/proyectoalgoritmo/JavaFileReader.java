package proyectoalgoritmo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * la clase JavaFileReader se encarga de leer un archivo Java y extraer los metodos definidos en el..
 * También cuenta el número de llamadas recursivas dentro de cada método y los agrega a una instancia de CircularLinkedList.
 */
public class JavaFileReader {

    /**
     * lee el contenido de un archivo Java.
     * @return El contenido del archivo como una cadena de caracteres.
     */
    public String readFile(String filePath) {
        StringBuilder fileContent = new StringBuilder();
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    /**
     * extrae los metodos definidos en el contenido del archivo Java y cuenta el numero de llamadas recursivas dentro de cada metodo.
     * Agrega los metodos y su informacion relevante a una instancia de CircularLinkedList.
     * @param fileContent El contenido del archivo Java.
     * @param methodList La lista enlazada circular donde se agregarán los métodos extraídos.
     */
    public void extractMethods(String fileContent, CircularLinkedList methodList) {
        // Patron para identificar la declaracion de metodos en el contenido del archivo Java
        Pattern methodPattern = Pattern.compile("(public|private|protected)\\s+\\w+\\s+\\w+\\s*\\([^)]*\\)\\s*\\{.*?\\}", Pattern.DOTALL);
        Matcher methodMatcher = methodPattern.matcher(fileContent);

        while (methodMatcher.find()) {
            String methodDeclaration = methodMatcher.group();
            String[] methodParts = methodDeclaration.split("\\{", 2);
            String methodName = methodParts[0].trim().split("\\s+")[2];
            String methodContent = methodParts[1];
            int recursiveCalls = countRecursiveCalls(methodContent, methodName);
            methodList.add(methodName, methodContent, recursiveCalls);
        }
    }

    /**
     * Cuenta el numero de llamadas recursivas dentro del contenido de un metodo.
     */
    public int countRecursiveCalls(String methodContent, String methodName) {
        int recursiveCalls = 0;
        // Escapamos cualquier carácter especial en el nombre del método
        String escapedMethodName = Pattern.quote(methodName);
        // Construimos la expresión regular utilizando el nombre del método escapado
        String recursiveCallPattern = escapedMethodName + "\\s*\\([^)]*\\)";
        Pattern pattern = Pattern.compile(recursiveCallPattern);
        Matcher matcher = pattern.matcher(methodContent);

        while (matcher.find()) {
            recursiveCalls++;
        }

        return recursiveCalls;
    }

}
