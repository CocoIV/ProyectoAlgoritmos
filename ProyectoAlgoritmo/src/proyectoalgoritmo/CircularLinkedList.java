package proyectoalgoritmo;

import java.util.ArrayList;

/**
 * la clase CircularLinkedList es una lista enlazada circular que almacena
 * información sobre los metodos del .java cada nodo de la lista contiene el
 * nombre del metodo, su contenido. en la lista enlazada se podra agregar
 * metodos, buscar los metodos y extraerlos cuando se ocupe
 *
 * @author Diego
 */
public class CircularLinkedList {

    private class Node {

        String methodName;
        String methodContent;
        int recursiveCalls;
        Node next;

        //contructur de la clase nodo
        Node(String methodName, String methodContent, int recursiveCalls) {
            this.methodName = methodName;
            this.methodContent = methodContent;
            this.recursiveCalls = recursiveCalls;
        }
    }

    private Node Cabeza = null;
    private Node Cola = null;

    /**
     * Agrega un nuevo método a la lista enlazada.
     *
     */
    public void add(String methodName, String methodContent, int recursiveCalls) {
        Node newNode = new Node(methodName, methodContent, recursiveCalls);
        if (Cabeza == null) {
            // la lista está vacía el nuevo nodo se convierte en la cabeza y la cola
            Cabeza = newNode;
            Cola = newNode;
            newNode.next = Cabeza;
        } else {
            // Se agrega el nuevo nodo al final de la lista y se actualiza la cola
            Cola.next = newNode;
            Cola = newNode;
            Cola.next = Cabeza;
        }
    }

    /**
     * Obtiene una lista de los nombres de los métodos en la lista enlazada.
     *
     * @return Una lista de los nombres de los métodos.
     */
    public ArrayList<String> getMethodNames() {
        ArrayList<String> methodNames = new ArrayList<>();

        Node current = Cabeza;
        if (Cabeza != null) {
            do {
                methodNames.add(current.methodName);
                current = current.next;
            } while (current != Cabeza);
        }
        return methodNames;
    }

    /**
     * Obtiene el contenido de un metodo especifico
     *
     * @return El contenido del método especificado.
     */
    public String getMethodContent(String methodName) {
        Node current = Cabeza;
        if (Cabeza != null) {
            do {
                if (current.methodName.equals(methodName)) {
                    return current.methodContent;
                }
                current = current.next;
            } while (current != Cabeza);
        }
        return null;
    }
}
