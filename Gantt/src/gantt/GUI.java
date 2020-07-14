/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

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
    
    int Quantum = 3;
    
    JLabel lbTitulo, lbInformacion, lbTiempo;
    JTable tbInfo, tbGant, tbBloqueados;
    JButton btIniciar, btAgregar, btBloquear, btDesbloquear;
    DefaultTableModel modelTbInfo, modelTbGant, modelTbBloqueados;
    JScrollPane spTablaInfo;
    int personaActual;
    int fila = 0;
    boolean clienteInicial = true;
    int posicion = 0;
    
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

    public JPanel Titulo() {

        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 0, 1280, 50);

        Border borderPanel = new TitledBorder(new EtchedBorder());
        Panel.setBorder(borderPanel);
        Panel.setBackground(new java.awt.Color(204, 166, 166));

        lbTitulo = new JLabel("Algoritmo de Planificacion Round Robin con Quantum de 3", SwingConstants.CENTER);
        lbTitulo.setBounds(0, 0, 1280, 50);
        lbTitulo.setVisible(true);
        lbTitulo.setFont(new java.awt.Font("Cambria", 0, 29));
        Panel.add(lbTitulo);

        return Panel;
    }

    public JPanel Tabla() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 50, 1280, 300);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        //Panel.setBackground(new java.awt.Color(204, 0, 166));

        modelTbInfo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
        tbInfo = new JTable();
        tbInfo.setModel(modelTbInfo);
        
        modelTbInfo.addColumn("Prioridad");
        modelTbInfo.addColumn("Proceso");
        modelTbInfo.addColumn("T. Llegada");
        modelTbInfo.addColumn("Rafaga");
        modelTbInfo.addColumn("T. Comienzo");
        modelTbInfo.addColumn("T. Final");
        modelTbInfo.addColumn("T. Retorno");
        modelTbInfo.addColumn("T. Espera");
        modelTbInfo.addRow(new Object[]{"Prioridad" ,"Proceso", "T. Llegada", "Rafaga", "T. Comienzo", "T. Final", "T. Retorno", "T. Espera"});

        tbInfo.getTableHeader().setReorderingAllowed(false);
        tbInfo.setBounds(0, 0, 1280, 280);
        tbInfo.setVisible(true);

        tbInfo.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbInfo.setFillsViewportHeight(true);

        Panel.add(tbInfo);

        return Panel;
    }

    public JPanel Gant() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 340, 1280, 280);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(204, 166, 166));

        modelTbGant = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
        tbGant = new JTable();
        tbGant.setModel(modelTbGant);

        Object[] data = new Object[31];
        for (int i = 1; i < 31; i++) {
            modelTbGant.addColumn(i - 1);
            data[i] = i - 1;
        }

        tbGant.getColumnModel().getColumn(0).setPreferredWidth(300);

        modelTbGant.addRow(data);

        tbGant.getTableHeader().setReorderingAllowed(false);
        tbGant.setBounds(0, 10, 1280, 280);
        tbGant.setVisible(true);

        tbGant.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbGant.setFillsViewportHeight(true);

        Panel.add(tbGant);

        return Panel;
    }

    public JPanel Botonera() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 620, 1280, 70);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(204, 166, 0));

        btIniciar = new JButton("Avanzar Unidad de Tiempo");
        btIniciar.setBounds(80, 15, 200, 45);
        btIniciar.setVisible(true);
        btIniciar.addActionListener(this);
        Panel.add(btIniciar);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        btAgregar = new JButton("Agregar proceso");
        btAgregar.setBounds(300, 15, 200, 45);
        btAgregar.setVisible(true);
        btAgregar.addActionListener(this);
        Panel.add(btAgregar);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        btBloquear = new JButton("Bloquear proceso");
        btBloquear.setBounds(520, 15, 200, 45);
        btBloquear.setVisible(true);
        btBloquear.addActionListener(this);
        Panel.add(btBloquear);
        Panel.setBackground(new java.awt.Color(198, 198, 198));
        
        btDesbloquear = new JButton("Desbloquear proceso");
        btDesbloquear.setBounds(740, 15, 200, 45);
        btDesbloquear.setVisible(true);
        btDesbloquear.addActionListener(this);
        Panel.add(btDesbloquear);
        Panel.setBackground(new java.awt.Color(198, 198, 198));
        
        lbTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo), SwingConstants.CENTER);
        lbTiempo.setBounds(960, 15, 200, 45);
        lbTiempo.setVisible(true);
        lbTiempo.setFont(new java.awt.Font("Cambria", 0, 29));
        Panel.add(lbTiempo);

        return Panel;

    }

    public JPanel Informacion() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 800, 1280, 90);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(142, 142, 142));

        lbInformacion = new JLabel();
        lbInformacion.setText("Brayan A. Paredes, Kevin A. Borda, Mateo Yate G. - UDistrital - 2020-1");
        lbInformacion.setFont(new java.awt.Font("Cambria", 0, 20));
        lbInformacion.setForeground(Color.white);
        lbInformacion.setBounds(250, 15, 700, 40);
        lbInformacion.setVisible(true);
        lbInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        //lbAsesora.setIcon(new ImageIcon("./imagenes/BankCashier.png"));
        Panel.add(lbInformacion);

        return Panel;
    }
    
    public JPanel ColaBloqueos(){
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 690, 1280, 120);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(0, 142, 142));
        
        modelTbBloqueados = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
        tbBloqueados = new JTable();
        tbBloqueados.setModel(modelTbBloqueados);
        
        modelTbBloqueados.addColumn("Prioridad");
        modelTbBloqueados.addColumn("Proceso");
        modelTbBloqueados.addColumn("T. Llegada");
        modelTbBloqueados.addColumn("Rafaga");
        modelTbBloqueados.addColumn("T. Comienzo");
        modelTbBloqueados.addColumn("T. Bloqueo");
        modelTbBloqueados.addColumn("Rafaga restante");
        
        modelTbBloqueados.addRow(new Object[]{"Prioridad","Proceso", "T. Llegada", "Rafaga", "T. Comienzo", "T. Bloqueo", "Rafaga restante"});

        tbBloqueados.getTableHeader().setReorderingAllowed(false);
        tbBloqueados.setBounds(0, 0, 1280, 280);
        tbBloqueados.setVisible(true);

        tbBloqueados.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbBloqueados.setFillsViewportHeight(true);

        Panel.add(tbBloqueados);
        
        return Panel;
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
                    
                    /*Organizar la cola*/
                    //Se organiza la cola
                    //organizarCola();
                    //clientes = clientes2;
                    //clientes2 = new Cola();
                    personaActual = clientes.Cabecera;
                    Quantum = 0;

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

                
                if (Quantum < 3) {
                    Quantum++;

                    //Si la cola no esta vacia y el tiempo global no ha excedido 30 unidades
                    if (clientes.longitud() != 0 || tiempo <= 30) {
                        personaActual.rafagaEjecutada ++;
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
                            personaActual.espera = personaActual.retorno - personaActual.rafagaEjecutada;

                            for (int u = personaActual.tfPrecursor + 1; u < personaActual.comienzo + 1; u++) {
                                dataGantt[u] = "∞";
                            }
                            
                            /*for (int u = personaActual.llegada + 1; u < personaActual.comienzo + 1; u++) {
                                dataGantt[u] = "∞";
                            }*/
                            
                            JOptionPane.showMessageDialog(null, personaActual.espera);
                            
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
                            System.out.println("Rafaga Ejecutada: " + personaActual.rafagaEjecutada);
                            System.out.println("------------------------");
                            System.out.println("(Pausa incomoda para leer el resumen)");
                            clientes.extraer(1);
                            clienteInicial = true;
                            tiempo++;
                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Quantum excedido...");
                    Node auxNode = clientes.Cabecera;     
                    clientes.insert(auxNode.prioridad,auxNode.llegada, auxNode.rafaga - 3, auxNode.nombre + " (E)" , auxNode.fila, auxNode.rafagaRestante, auxNode.tiempoBloqueo, auxNode.rafagaEjecutada, tiempo);
                    clientes.extraer(1);
                    clienteInicial = true;
                    
                    dataAuxInfo[5] = tiempo;
                    dataAuxInfo[6] = tiempo - auxNode.llegada;
                    dataAuxInfo[7] = (tiempo - auxNode.llegada) - auxNode.rafagaEjecutada;
                    //dataAuxInfo[7] = (tiempo - auxNode.llegada) - (auxNode.rafaga - auxNode.rafagaRestante);
                    
                    modelTbInfo.removeRow(modelTbInfo.getRowCount() - 1);
                    modelTbInfo.addRow(dataAuxInfo);
                    
                    for (int u = personaActual.tfPrecursor + 1; u < personaActual.comienzo + 1; u++) {
                        dataGantt[u] = "∞";
                    }
                    
                    
                    if (personaActual.tiempoBloqueo != 0) {
                        for (int u = personaActual.tiempoBloqueo + 1; u < personaActual.llegada + 1; u++) {
                            dataGantt[u] = "B";
                            personaActual.espera++;
                        }
                        personaActual.tiempoBloqueo = 0;
                    }


                    modelTbGant.removeRow(modelTbGant.getRowCount() - 1);
                    modelTbGant.addRow(dataGantt);
                    
                    Quantum = 0;
                }

            }

        }

        if (e.getSource() == btAgregar) {

            //Llegada de un nuevo cliente de manera aleatoria en cada unidad de tiempo
            //Cabecera del mensaje
            System.out.println("///////");
            System.out.println("Llego un nuevo cliente");

            //Se le asigna una rafaga y un nombre aleatorio
            int nuevoClientRagafa = aleatorio.nextInt(7) + 6;
            //int nuevoClientNombre = ;
            
            //int nuevoClientNombre = aleatorio.nextInt(nombres.length);

            //Se muestra la informacion del nuevo cliente
            System.out.println("Nombre del nuevo cliente: " + "P" + Integer.toString(posicion));
            System.out.println("Rafaga del nuevo cliente: " + nuevoClientRagafa);

            //Se inserta el nuevo cliente en la cola
            aleatorio.setSeed(System.currentTimeMillis());
            int prioPrueba = aleatorio.nextInt(4)+1;
            clientes.insert(prioPrueba,tiempo, nuevoClientRagafa, "P" + Integer.toString(posicion), fila, 0,0,0,tiempo);
            fila++;
            System.out.println("///////////");
            System.out.println("");
            posicion ++;

        } else if (e.getSource() == btBloquear) {
            if (clientes.longitud() == 0) {
                JOptionPane.showMessageDialog(null, "La cola esta vacia");
            } else {

                clientesBloqueados.insert(clientes.Cabecera.prioridad, clientes.Cabecera.llegada, clientes.Cabecera.rafaga, clientes.Cabecera.nombre, clientes.Cabecera.fila, clientes.Cabecera.comienzo + clientes.Cabecera.rafaga - tiempo, tiempo,0,clientes.Cabecera.tfPrecursor);
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
                clientes.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo, clientesBloqueados.Cabecera.rafagaEjecutada, tiempo);
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
            clientes2.insert(colaOrg.get(k).prioridad, colaOrg.get(k).llegada, colaOrg.get(k).rafaga, colaOrg.get(k).nombre, colaOrg.get(k).fila, colaOrg.get(k).rafagaRestante, colaOrg.get(k).tiempoBloqueo, colaOrg.get(k).rafagaEjecutada, colaOrg.get(k).tfPrecursor);
        }

    }

    

}
