/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author KevinB
 */
public class Cola {

    protected Node Cabecera;
    ArrayList<String> ClientesArr;
    
    public Cola() {
        Cabecera = null;
    }

    public void insert(int llegada, int rafaga, String nom) {

        Node tmp = new Node(llegada, rafaga, nom);

        tmp.setNext(Cabecera);

        Cabecera = tmp;
    }

    public void extraer(int x) {
        while (x > 0) {
            int l = longitud();
            if (l > 1) {
                Node aux = Cabecera;
                while (aux.next.next != null) {
                    aux = aux.next;
                }
                aux.setNext(null);
            } else {
                Cabecera = null;
            }
            x--;
        }

    }

    //Metodo para obtener longitud (6 lineas de código)
    /*Generamos un contador y un nodo temporal que ira recorriendo la cola
          hasta encontrar el final, mientra va recorriendo va sumando al contador
     */
    public int longitud() {
        int cont = 0;
        Node tmp = Cabecera;
        while (tmp != null) {
            cont = cont + 1;
            tmp = tmp.getNext();
        }
        return cont;
    }

    public String imprimir() {
        String clientes = "";
        ClientesArr = new ArrayList();
        if (!isEmpty()) {
            Node tmp = Cabecera;
            while (tmp != null) {
                clientes = clientes + "CLIENTE: " + tmp.nombre + " - N° RECIBOS: " + tmp.getInfo() + "." + "\n";
                ClientesArr.add("CLIENTE: " + tmp.nombre + " - N° RECIBOS: " + tmp.getInfo() + ".");
                tmp = tmp.getNext();
            }
        }
        
        return clientes;
    }

    public boolean isEmpty() {
        if (Cabecera == null) {
            return true;
        } else {
            return false;
        }
    }
}
