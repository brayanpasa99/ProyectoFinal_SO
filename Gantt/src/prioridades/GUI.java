/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prioridades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mateo
 */
public class GUI implements ActionListener {

    JLabel lbTitulo, lbInformacion, lbTiempo;
    JTable tbInfo, tbGant, tbBloqueados;
    JButton btIniciar, btAgregar, btBloquear, btDesbloquear;
    DefaultTableModel modelTbInfo, modelTbGant, modelTbBloqueados;
    JScrollPane spTablaInfo;
    int personaActual;
    int fila = 0;
    boolean clienteInicial = true;
    
    //Se crea una cola de clientes
    Cola clientes = new Cola();
    Cola clientes2 = new Cola();
    Cola clientesBloqueados = new Cola();
    
    //Se inicializa el tiempo en cero
    int tiempo = 0;
    Random aleatorio = new Random(System.currentTimeMillis());
    
    //Se generan los objetos de las tablas de info. y gantt para el cliente actual
    Object[] dataAuxInfo = new Object[8];
    Object[] dataGantt = new Object[30];
    Object[] dataBloqueados = new Object[7];

    String[] nombres = {"Belen Ferreira","Mia Collado","Marino Vega","Yassine Z.","Sira Osuna","Itziar Ferrero","Gerardo Marco","Marian Ojeda","Matias Roca", "Julen Miguel", "Iluminada Gracia", "Felisa Montesinos", "Óscar Collado", "Ian Solana", "Serafin Mari", "Encarnacion del M.", "Sebastiana Lin"};
       
    public void botonIniciar(){
        if (clientes.longitud() == 0) {
            tiempo++;
            JOptionPane.showMessageDialog(null, "La cola esta vacia");
        } else {

            //Se setea la persona actual como la cabeza de la cola
            Node personaActual = clientes.Cabecera;

            if (clienteInicial == true) {

                dataAuxInfo = new Object[8];
                dataGantt = new Object[30];

                //Se organiza la cola
                organizarCola();
                clientes = clientes2;
                clientes2 = new Cola();
                personaActual = clientes.Cabecera;

                //Se genera una nueva semilla aleatoria
                aleatorio.setSeed(System.currentTimeMillis());

                //Se guarda el tiempo de comienzo en el tiempo actual
                personaActual.comienzo = tiempo;
                clientes.Cabecera.comienzo = tiempo;

                System.out.println("Estoy en el if");
                //Se muestra que cliente será atendido y se suben sus primeros datos a la tabla de informacion
                System.out.println("Cliente que sera atendido:" + personaActual.nombre);
                System.out.println("Comienzo inicial:" + personaActual.comienzo);

                dataAuxInfo[0] = personaActual.prioridad;
                dataAuxInfo[1] = personaActual.nombre;
                dataAuxInfo[2] = personaActual.llegada;
                dataAuxInfo[3] = personaActual.rafaga;
                dataAuxInfo[4] = personaActual.comienzo;
                System.out.println("Numero de rafaga:" + personaActual.rafaga);

                modelTbInfo.addRow(dataAuxInfo);
                modelTbGant.addRow(dataGantt);

                dataGantt[0] = personaActual.nombre;

                clienteInicial = false;
            }

            //Si la cola no esta vacia y el tiempo global no ha excedido 30 unidades
            if (clientes.longitud() != 0 || tiempo <= 30) {

                //Si el proceso actual aun tiene rafaga
                if (tiempo < (personaActual.comienzo + personaActual.rafaga - 1)) {

                    //Se muestra la informacion
                    System.out.println("");
                    System.out.println("Estamos en el tiempo: " + tiempo);
                    System.out.println("Se esta atendiendo a " + personaActual.nombre);

                    //En el tiempo actual se llena  esa unidad en el modelo de gantt
                    dataGantt[tiempo + 1] = "X";

                    modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                    modelTbGant.addRow(dataGantt);

                    //Se aumenta el tiempo
                    tiempo++;

                } else {

                    System.out.println("Entre aqui");

                    //Se muestra la informacion
                    System.out.println("");
                    System.out.println("Estamos en el tiempo: " + tiempo);
                    System.out.println("Se esta atendiendo a " + personaActual.nombre);

                    //En el tiempo actual se llena  esa unidad en el modelo de gantt
                    dataGantt[tiempo + 1] = "X";

                    modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                    modelTbGant.addRow(dataGantt);

                    personaActual.fin = personaActual.comienzo + personaActual.rafaga;
                    personaActual.retorno = personaActual.fin - personaActual.llegada;
                    personaActual.espera = personaActual.retorno - personaActual.rafaga;

                    for (int u = personaActual.llegada + 1; u < personaActual.comienzo + 1; u++) {
                        dataGantt[u] = "∞";
                    }

                    if (personaActual.tiempoBloqueo != 0) {
                        for (int u = personaActual.tiempoBloqueo + 1; u < personaActual.llegada + 1; u++) {
                            dataGantt[u] = "B";
                            personaActual.espera++;
                        }
                    }

                    dataAuxInfo[5] = personaActual.fin;
                    dataAuxInfo[6] = personaActual.retorno;
                    dataAuxInfo[7] = personaActual.espera;

                    modelTbInfo.removeRow(modelTbInfo.getRowCount() - 1);
                    modelTbInfo.addRow(dataAuxInfo);
                    modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                    modelTbGant.addRow(dataGantt);

                    System.out.println("------------------------");
                    System.out.println("Resumen de: " + personaActual.nombre);
                    System.out.println("Llegada en: " + personaActual.llegada);
                    System.out.println("Rafaga de: " + personaActual.rafaga);
                    System.out.println("Comienzo final: " + personaActual.comienzo);
                    personaActual.fin = personaActual.rafaga + personaActual.comienzo;
                    System.out.println("Tiempo final: " + personaActual.fin);
                    personaActual.retorno = personaActual.fin - personaActual.llegada;
                    System.out.println("Tiempo de retorno: " + personaActual.retorno);
                    personaActual.espera = personaActual.retorno - personaActual.rafaga;
                    System.out.println("Salio en: " + tiempo);
                    System.out.println("Fila: " + personaActual.fila);
                    System.out.println("------------------------");
                    System.out.println("(Pausa incomoda para leer el resumen)");
                    clientes.extraer(1);
                    clienteInicial = true;
                    tiempo++;
                }

            } else {

                JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

            }
        }

    }
    
    public void botonAgregar(){
        
        //Llegada de un nuevo cliente de manera aleatoria en cada unidad de tiempo
        //Cabecera del mensaje
        System.out.println("///////");
        System.out.println("Llego un nuevo cliente");

        //Se le asigna una rafaga y un nombre aleatorio
        int nuevoClientRagafa = aleatorio.nextInt(4) + 2;
        int nuevoClientNombre = aleatorio.nextInt(nombres.length);

        //Se muestra la informacion del nuevo cliente
        System.out.println("Nombre del nuevo cliente: " + nombres[nuevoClientNombre]);
        System.out.println("Rafaga del nuevo cliente: " + nuevoClientRagafa);

        //Se inserta el nuevo cliente en la cola
        aleatorio.setSeed(System.currentTimeMillis());
        int prioPrueba = aleatorio.nextInt(4) + 1;
        clientes.insert(prioPrueba, tiempo, nuevoClientRagafa, nombres[nuevoClientNombre], fila, 0, 0);
        fila++;
        System.out.println("///////////");
        System.out.println("");
    }
    
    public void botonBloquear(){
        if (clientes.longitud() == 0) {
            JOptionPane.showMessageDialog(null, "La cola esta vacia");
        } else {

            clientesBloqueados.insert(clientes.Cabecera.prioridad, clientes.Cabecera.llegada, clientes.Cabecera.rafaga, clientes.Cabecera.nombre, clientes.Cabecera.fila, clientes.Cabecera.comienzo + clientes.Cabecera.rafaga - tiempo, tiempo);
            JOptionPane.showMessageDialog(null, "El proceso en ejecucion sera bloqueado");

            Node aux = clientesBloqueados.Cabecera;

            while (aux.fila != clientes.Cabecera.fila) {
                aux = aux.next;
            }

            aux.comienzo = clientes.Cabecera.comienzo;

            clientes.extraer(1);
            fila++;
            clienteInicial = true;

            dataAuxInfo[5] = tiempo;
            dataAuxInfo[6] = tiempo - aux.llegada;
            dataAuxInfo[7] = (tiempo - aux.llegada) - (aux.rafaga - aux.rafagaRestante);

            modelTbInfo.removeRow(modelTbInfo.getRowCount() - 1);
            modelTbInfo.addRow(dataAuxInfo);

            dataBloqueados[0] = aux.prioridad;
            dataBloqueados[1] = aux.nombre;
            dataBloqueados[2] = aux.llegada;
            dataBloqueados[3] = aux.rafaga;
            dataBloqueados[4] = aux.comienzo;

            //Bloqueo y restante
            dataBloqueados[5] = tiempo;
            //aux.rafagaRestante = aux.comienzo + aux.rafaga - tiempo;
            dataBloqueados[6] = aux.rafagaRestante;

            modelTbBloqueados.addRow(dataBloqueados);

        }
    }
    
    public void botonDesbloquear(){
        if (clientesBloqueados.longitud() == 0) {
            JOptionPane.showMessageDialog(null, "No hay procesos bloqueados");
        } else {
            clientes.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo);
            clientesBloqueados.extraer(1);

            modelTbBloqueados.removeRow(1);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btIniciar) {
            lbTiempo.setText("Tiempo: " + String.valueOf(tiempo + 1));

            if (clientes.longitud() == 0) {
                tiempo++;
                JOptionPane.showMessageDialog(null, "La cola esta vacia");
            } else {

                //Se setea la persona actual como la cabeza de la cola
                Node personaActual = clientes.Cabecera;

                if (clienteInicial == true) {
                    
                    dataAuxInfo = new Object[8];
                    dataGantt = new Object[30];

                    //Se organiza la cola
                    organizarCola();
                    clientes = clientes2;
                    clientes2 = new Cola();
                    personaActual = clientes.Cabecera;

                    //Se genera una nueva semilla aleatoria
                    aleatorio.setSeed(System.currentTimeMillis());

                    //Se guarda el tiempo de comienzo en el tiempo actual
                    personaActual.comienzo = tiempo;
                    clientes.Cabecera.comienzo = tiempo;

                    System.out.println("Estoy en el if");
                    //Se muestra que cliente será atendido y se suben sus primeros datos a la tabla de informacion
                    System.out.println("Cliente que sera atendido:" + personaActual.nombre);
                    System.out.println("Comienzo inicial:" + personaActual.comienzo);
                    
                    dataAuxInfo[0] = personaActual.prioridad;
                    dataAuxInfo[1] = personaActual.nombre;
                    dataAuxInfo[2] = personaActual.llegada;
                    dataAuxInfo[3] = personaActual.rafaga;
                    dataAuxInfo[4] = personaActual.comienzo;
                    System.out.println("Numero de rafaga:" + personaActual.rafaga);

                    modelTbInfo.addRow(dataAuxInfo);
                    modelTbGant.addRow(dataGantt);

                    dataGantt[0] = personaActual.nombre;

                    clienteInicial = false;
                }

                //Si la cola no esta vacia y el tiempo global no ha excedido 30 unidades
                if (clientes.longitud() != 0 || tiempo <= 30) {

                    //Si el proceso actual aun tiene rafaga
                    if (tiempo < (personaActual.comienzo + personaActual.rafaga - 1)) {

                        //Se muestra la informacion
                        System.out.println("");
                        System.out.println("Estamos en el tiempo: " + tiempo);
                        System.out.println("Se esta atendiendo a " + personaActual.nombre);

                        //En el tiempo actual se llena  esa unidad en el modelo de gantt
                        dataGantt[tiempo + 1] = "X";

                        modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                        modelTbGant.addRow(dataGantt);

                        //Se aumenta el tiempo
                        tiempo++;

                    } else {

                        System.out.println("Entre aqui");

                        //Se muestra la informacion
                        System.out.println("");
                        System.out.println("Estamos en el tiempo: " + tiempo);
                        System.out.println("Se esta atendiendo a " + personaActual.nombre);

                        //En el tiempo actual se llena  esa unidad en el modelo de gantt
                        dataGantt[tiempo + 1] = "X";

                        modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                        modelTbGant.addRow(dataGantt);

                        personaActual.fin = personaActual.comienzo + personaActual.rafaga;
                        personaActual.retorno = personaActual.fin - personaActual.llegada;
                        personaActual.espera = personaActual.retorno - personaActual.rafaga;

                        

                        for (int u = personaActual.llegada + 1; u < personaActual.comienzo + 1; u++) {
                            dataGantt[u] = "∞";
                        }
                        
                        if (personaActual.tiempoBloqueo != 0) {
                            for (int u = personaActual.tiempoBloqueo + 1; u < personaActual.llegada + 1; u++) {
                                dataGantt[u] = "B";
                                personaActual.espera ++;
                            }
                        }
                        
                        dataAuxInfo[5] = personaActual.fin;
                        dataAuxInfo[6] = personaActual.retorno;
                        dataAuxInfo[7] = personaActual.espera;

                        modelTbInfo.removeRow(modelTbInfo.getRowCount() - 1);
                        modelTbInfo.addRow(dataAuxInfo);
                        modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                        modelTbGant.addRow(dataGantt);

                        System.out.println("------------------------");
                        System.out.println("Resumen de: " + personaActual.nombre);
                        System.out.println("Llegada en: " + personaActual.llegada);
                        System.out.println("Rafaga de: " + personaActual.rafaga);
                        System.out.println("Comienzo final: " + personaActual.comienzo);
                        personaActual.fin = personaActual.rafaga + personaActual.comienzo;
                        System.out.println("Tiempo final: " + personaActual.fin);
                        personaActual.retorno = personaActual.fin - personaActual.llegada;
                        System.out.println("Tiempo de retorno: " + personaActual.retorno);
                        personaActual.espera = personaActual.retorno - personaActual.rafaga;
                        System.out.println("Salio en: " + tiempo);
                        System.out.println("Fila: " + personaActual.fila);
                        System.out.println("------------------------");
                        System.out.println("(Pausa incomoda para leer el resumen)");
                        clientes.extraer(1);
                        clienteInicial = true;
                        tiempo++;
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

                }
            }

        }

        if (e.getSource() == btAgregar) {

            //Llegada de un nuevo cliente de manera aleatoria en cada unidad de tiempo
            //Cabecera del mensaje
            System.out.println("///////");
            System.out.println("Llego un nuevo cliente");

            //Se le asigna una rafaga y un nombre aleatorio
            int nuevoClientRagafa = aleatorio.nextInt(4) + 2;
            int nuevoClientNombre = aleatorio.nextInt(nombres.length);

            //Se muestra la informacion del nuevo cliente
            System.out.println("Nombre del nuevo cliente: " + nombres[nuevoClientNombre]);
            System.out.println("Rafaga del nuevo cliente: " + nuevoClientRagafa);

            //Se inserta el nuevo cliente en la cola
            aleatorio.setSeed(System.currentTimeMillis());
            int prioPrueba = aleatorio.nextInt(4)+1;
            clientes.insert(prioPrueba,tiempo, nuevoClientRagafa, nombres[nuevoClientNombre], fila, 0,0);
            fila++;
            System.out.println("///////////");
            System.out.println("");

        } else if (e.getSource() == btBloquear) {
            if (clientes.longitud() == 0) {
                JOptionPane.showMessageDialog(null, "La cola esta vacia");
            } else {

                clientesBloqueados.insert(clientes.Cabecera.prioridad, clientes.Cabecera.llegada, clientes.Cabecera.rafaga, clientes.Cabecera.nombre, clientes.Cabecera.fila, clientes.Cabecera.comienzo + clientes.Cabecera.rafaga - tiempo, tiempo);
                JOptionPane.showMessageDialog(null, "El proceso en ejecucion sera bloqueado");
                
                Node aux = clientesBloqueados.Cabecera;
                
                while(aux.fila != clientes.Cabecera.fila){
                    aux = aux.next;
                }
                
                aux.comienzo = clientes.Cabecera.comienzo;
                
                clientes.extraer(1);
                fila ++;
                clienteInicial = true;
                
                dataAuxInfo[5] = tiempo;
                dataAuxInfo[6] = tiempo - aux.llegada;
                dataAuxInfo[7] = (tiempo - aux.llegada) - (aux.rafaga - aux.rafagaRestante);
                
                modelTbInfo.removeRow(modelTbInfo.getRowCount() - 1);
                modelTbInfo.addRow(dataAuxInfo);
                
                dataBloqueados[0] = aux.prioridad;
                dataBloqueados[1] = aux.nombre;
                dataBloqueados[2] = aux.llegada;
                dataBloqueados[3] = aux.rafaga;
                dataBloqueados[4] = aux.comienzo;
                
                //Bloqueo y restante
                dataBloqueados[5] = tiempo;
                //aux.rafagaRestante = aux.comienzo + aux.rafaga - tiempo;
                dataBloqueados[6] = aux.rafagaRestante;
                
                modelTbBloqueados.addRow(dataBloqueados);
                
            }
        }
        
        if (e.getSource() == btDesbloquear){
            if (clientesBloqueados.longitud() == 0){
                JOptionPane.showMessageDialog(null, "No hay procesos bloqueados");
            } else {
                clientes.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo);
                clientesBloqueados.extraer(1);
                
                modelTbBloqueados.removeRow(1);
            }
        }
    }

    public void organizarCola() {

        ArrayList<Node> colaOrg = clientes.nodosCola();

        for (int i = 1; i < colaOrg.size(); i++) {
            for (int j = 0; j < colaOrg.size() - 1; j++) {
                if (colaOrg.get(j).prioridad > colaOrg.get(j + 1).prioridad) {
                    Node temp = colaOrg.get(j);
                    colaOrg.set(j, colaOrg.get(j+1));
                    colaOrg.set(j+1, temp);
                }
            }
        }

        for (int k = 0; k < colaOrg.size(); k++) {
            clientes2.insert(colaOrg.get(k).prioridad, colaOrg.get(k).llegada, colaOrg.get(k).rafaga, colaOrg.get(k).nombre, colaOrg.get(k).fila, colaOrg.get(k).rafagaRestante, colaOrg.get(k).tiempoBloqueo);
        }

    }

    

}
