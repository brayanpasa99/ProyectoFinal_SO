/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

/**
 *
 * @author KevinB
 */
public class Node {

    public int rafaga;
    public int llegada;
    public int comienzo;
    public int fin;
    public int retorno;
    public int espera;
    public String nombre;
    public Node next;

    public Node(int llegada, int raf, String nom) {
        rafaga = raf;
        next = null;
        nombre = nom;
        this.llegada= llegada;
    }

    public void setRafaga(int o) {
        rafaga = o;
    }

    public void setNext(Node n) {
        next = n;
    }

    public Object getInfo() {
        return rafaga;
    }

    public Node getNext() {
        return next;
    }
}
