package proyectoalgoritmo;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\jcamp\\Downloads\\codigo.java";

        JavaFileReader fileReader = new JavaFileReader();
        String fileContent = fileReader.readFile(filePath);

        // extraer metodos y añadirlos a la lista circular
        CircularLinkedList methodList = new CircularLinkedList();
        fileReader.extractMethods(fileContent, methodList);

       
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione el método para analizar su complejidad teórica (peor caso):");
            ArrayList<String> methodNames = methodList.getMethodNames();
            for (int i = 0; i < methodNames.size(); i++) {
                System.out.println((i + 1) + ". " + methodNames.get(i));
            }
            System.out.println((methodNames.size() + 1) + ". Salir");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice < 1 || choice > methodNames.size() + 1) {
                System.out.println("Selección inválida. Intente de nuevo.");
                continue;
            }

            if (choice == methodNames.size() + 1) {
                System.out.println("Saliendo...");
                break;
            }

            // obtener el nombre del metodo seleccionado
            String selectedMethod = methodNames.get(choice - 1);
            System.out.println("Selected Method: " + selectedMethod);
            // obtener el contenido del metodo seleccionado
            String methodContent = methodList.getMethodContent(selectedMethod);
            System.out.println("Method Content: " + methodContent);

            // analizar la complejidad del metodo seleccionado
            ComplexityCalculator calculator = new ComplexityCalculator(methodContent);
            String complexity = calculator.calculateComplexity();
            System.out.println("Calculated Complexity: " + complexity);

            // mostrar el resultado del calculo de complejidad
            System.out.println("La complejidad teórica (peor caso) del método " + selectedMethod + " es: " + complexity);
        }
    }
}
