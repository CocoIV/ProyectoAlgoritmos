package proyectoalgoritmo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase proporciona un calculador de complejidad para procedimientos en lenguaje Java.
 * La complejidad se calcula analizando el contenido del procedimiento en busca de estructuras de control y patrones específicos.
 */
public class ComplexityCalculator {

    private String procedureContent; // Contenido del procedimiento a analizar

    /**
     * Constructor de la clase ComplexityCalculator.
     * @param procedureContent El contenido del procedimiento que se va a analizar.
     */
    public ComplexityCalculator(String procedureContent) {
        this.procedureContent = procedureContent;
    }

    /**
     * Calcula la complejidad del procedimiento basándose en la cantidad de bucles, llamadas recursivas y patrones específicos encontrados.
     * @return La complejidad del procedimiento en notación Big O.
     */
    public String calculateComplexity() {
        // Contadores para bucles for, bucles while y llamadas recursivas
        int forLoops = countOccurrences("for\\s*\\(");
        int whileLoops = countOccurrences("while\\s*\\(");
        int recursiveCalls = countRecursiveCalls();

        // Determina la complejidad basada en los contadores y patrones encontrados
        if (forLoops == 0 && whileLoops == 0 && recursiveCalls == 0) {
            return "O(1)";
        } else if (forLoops == 1 && whileLoops == 0 && recursiveCalls == 0) {
            return "O(n)";
        } else if (forLoops > 1 || whileLoops > 1) {
            return "O(n^" + Math.max(forLoops, whileLoops) + ")";
        } else if (containsQuadraticPattern()) {
            return "O(n^2)";
        } else if (containsLogarithmicPattern()) {
            return "O(log n)";
        } else if (recursiveCalls > 0 && countBinarySplits()) {
            return "O(2^n)";
        } else {
            return "Complejidad no determinada";
        }
    }

    /**
     * Cuenta el numero de ocurrencias de un patron dado en el contenido del procedimiento.
     * @return El número de ocurrencias del patrón.
     */
    private int countOccurrences(String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(procedureContent);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * cuenta el numero de llamadas recursivas en el contenido del procedimiento.
     * @return El numero de llamadas recursivas.
     */
    private int countRecursiveCalls() {
        Pattern pattern = Pattern.compile("(void|\\w+)\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(procedureContent);
        if (matcher.find()) {
            String procedureName = matcher.group(2);
            return countOccurrences(procedureName + "\\s*\\(");
        } else {
            return 0;
        }
    }

    /**
     * verifica si el contenido del procedimiento contiene patrones que indican una complejidad logaritmica.
     * @return true si se encuentra un patrón logarítmico, false de lo contrario.
     */
    private boolean containsLogarithmicPattern() {
        return procedureContent.contains("/ 2") || procedureContent.contains(">> 1");
    }

    /**
     * verifica si el contenido del procedimiento contiene patrones que indican una complejidad cuadratica.
     * @return true si se encuentra un patrnn cuadratico, false de lo contrario.
     */
    private boolean containsQuadraticPattern() {
        return procedureContent.contains("for") && procedureContent.contains("int") &&
               procedureContent.contains("=") && procedureContent.contains(";");
    }

    /**
     * verifica si el contenido del procedimiento contiene patrones que indican una bifurcacion binaria.
     * @return true si se encuentra un patrón de bifurcacion binaria, false de lo contrario.
     */
    private boolean countBinarySplits() {
        Pattern pattern = Pattern.compile("if\\s*\\(.+?\\)\\s*\\{");
        Matcher matcher = pattern.matcher(procedureContent);
        return matcher.find();
    }
}
