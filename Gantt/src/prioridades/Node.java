/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prioridades;

import java.util.Random;

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
    public int fila;
    public int rafagaRestante;
    public int prioridad;
    public int tiempoBloqueo = 0;
    public String nombre;
    public Node next;
        

    public Node(int prior, int llegada, int raf, String nom, int fil, int rRestante, int tBloqueo) {
        rafaga = raf;
        next = null;
        nombre = nom;
        fila = fil;
        rafagaRestante = rRestante;
        tiempoBloqueo = tBloqueo;
        this.llegada= llegada;
        prioridad = prior;
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
