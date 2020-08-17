/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mateo
 */
public class GUI implements ActionListener {

    int Quantum = 3;

    JLabel lbTitulo, lbInformacion, lbTiempo, lbRafaga, lbCola;
    JTable tbInfo, tbGant, tbBloqueados, tbColas;
    JButton btIniciar, btAgregar, btBloquear, btDesbloquear;
    DefaultTableModel modelTbInfo, modelTbGant, modelTbBloqueados, modelTbColas;
    JScrollPane spTablaInfo;
    int personaActual;
    int fila = 0;
    boolean clienteInicial = true;
    int posicion = 0;

    int colaActual = 0;
    
    int tiempoEnvejecimiento = 5;

    JTextField tfRafaga, tfCola;

    //Se crea las 3 colas de clientes
    Cola clientes = new Cola();
    Cola clientesPrioridades = new Cola();
    Cola clientesRafaga = new Cola();

    Cola clientesOrganizar = new Cola();
    Cola clientesBloqueados = new Cola();

    //Se inicializa el tiempo en cero
    int tiempo = 0;
    Random aleatorio = new Random(System.currentTimeMillis());

    //Se generan los objetos de las tablas de info. y gantt para el cliente actual
    Object[] dataAuxInfo = new Object[9];
    Object[] dataGantt = new Object[46];
    Object[] dataBloqueados = new Object[7];
    Object[] dataColas = new Object[3];

    String[] nombres = {"Belen Ferreira", "Mia Collado", "Marino Vega", "Yassine Z.", "Sira Osuna", "Itziar Ferrero", "Gerardo Marco", "Marian Ojeda", "Matias Roca", "Julen Miguel", "Iluminada Gracia", "Felisa Montesinos", "Óscar Collado", "Ian Solana", "Serafin Mari", "Encarnacion del M.", "Sebastiana Lin"};

    public JPanel Titulo() {

        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 0, 1900, 50);

        Border borderPanel = new TitledBorder(new EtchedBorder());
        Panel.setBorder(borderPanel);
        Panel.setBackground(new java.awt.Color(204, 166, 166));

        lbTitulo = new JLabel("Proyecto de Multicolas - Round Robin, SRTF & Prioridades", SwingConstants.CENTER);
        lbTitulo.setBounds(0, 0, 1900, 50);
        lbTitulo.setVisible(true);
        lbTitulo.setFont(new java.awt.Font("Cambria", 0, 29));
        Panel.add(lbTitulo);

        return Panel;
    }

    public JPanel Tabla() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 50, 1900, 300);
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
        modelTbInfo.addColumn("Cola");
        modelTbInfo.addRow(new Object[]{"Prioridad", "Proceso", "T. Llegada", "Rafaga", "T. Comienzo", "T. Final", "T. Retorno", "T. Espera", "Cola"});

        tbInfo.getTableHeader().setReorderingAllowed(false);
        tbInfo.setBounds(0, 0, 1900, 280);
        tbInfo.setVisible(true);

        tbInfo.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbInfo.setFillsViewportHeight(true);

        Panel.add(tbInfo);

        return Panel;
    }

    public JPanel Gant() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 340, 1900, 280);
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

        Object[] data = new Object[51];
        for (int i = 1; i < 50; i++) {
            modelTbGant.addColumn(i - 1);
            data[i] = i - 1;
        }

        tbGant.getColumnModel().getColumn(0).setPreferredWidth(300);

        modelTbGant.addRow(data);

        tbGant.getTableHeader().setReorderingAllowed(false);
        tbGant.setBounds(0, 10, 1900, 280);
        tbGant.setVisible(true);

        tbGant.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbGant.setFillsViewportHeight(true);

        Panel.add(tbGant);

        return Panel;
    }

    public JPanel Botonera() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 620, 1900, 70);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(204, 166, 0));

        btIniciar = new JButton("Avanzar Unidad de Tiempo");
        btIniciar.setBounds(80, 15, 200, 45);
        btIniciar.setVisible(true);
        btIniciar.addActionListener(this);
        Panel.add(btIniciar);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        btAgregar = new JButton("Agregar proceso");
        btAgregar.setBounds(450, 15, 200, 45);
        btAgregar.setVisible(true);
        btAgregar.addActionListener(this);
        Panel.add(btAgregar);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        lbCola = new JLabel("Cola: ");
        lbCola.setBounds(745, 17, 40, 15);
        lbCola.setVisible(true);
        Panel.add(lbCola);

        tfCola = new JTextField("");
        tfCola.setBounds(795, 15, 80, 20);
        tfCola.setVisible(true);
        Panel.add(tfCola);

        lbRafaga = new JLabel("Rafaga: ");
        lbRafaga.setBounds(740, 28, 60, 45);
        lbRafaga.setVisible(true);
        Panel.add(lbRafaga);

        tfRafaga = new JTextField("");
        tfRafaga.setBounds(795, 40, 80, 20);
        tfRafaga.setVisible(true);
        Panel.add(tfRafaga);

        btBloquear = new JButton("Bloquear proceso");
        btBloquear.setBounds(1000, 15, 200, 45);
        btBloquear.setVisible(true);
        btBloquear.addActionListener(this);
        Panel.add(btBloquear);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        btDesbloquear = new JButton("Desbloquear proceso");
        btDesbloquear.setBounds(1370, 15, 200, 45);
        btDesbloquear.setVisible(true);
        btDesbloquear.addActionListener(this);
        Panel.add(btDesbloquear);
        Panel.setBackground(new java.awt.Color(198, 198, 198));

        lbTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo), SwingConstants.CENTER);
        lbTiempo.setBounds(1620, 15, 200, 45);
        lbTiempo.setVisible(true);
        lbTiempo.setFont(new java.awt.Font("Cambria", 0, 29));
        Panel.add(lbTiempo);

        return Panel;

    }

    public JPanel Informacion() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 800, 1900, 90);
        Panel.setFont(new java.awt.Font("Cambria", 2, 11));
        Panel.setBackground(new java.awt.Color(142, 142, 142));

        lbInformacion = new JLabel();
        lbInformacion.setText("Brayan A. Paredes, Kevin A. Borda, Mateo Yate G. - UDistrital - 2020-1");
        lbInformacion.setFont(new java.awt.Font("Cambria", 0, 20));
        lbInformacion.setForeground(Color.white);
        lbInformacion.setBounds(650, 15, 700, 40);
        lbInformacion.setVisible(true);
        lbInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        //lbAsesora.setIcon(new ImageIcon("./imagenes/BankCashier.png"));
        Panel.add(lbInformacion);

        return Panel;
    }

    public JPanel ColaBloqueos() {
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 690, 1900, 120);
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

        modelTbBloqueados.addRow(new Object[]{"Prioridad", "Proceso", "T. Llegada", "Rafaga", "T. Comienzo", "T. Bloqueo", "Rafaga restante"});

        tbBloqueados.getTableHeader().setReorderingAllowed(false);
        tbBloqueados.setBounds(0, 0, 1900, 280);
        tbBloqueados.setVisible(true);

        tbBloqueados.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbBloqueados.setFillsViewportHeight(true);

        Panel.add(tbBloqueados);

        return Panel;
    }
    
    public JPanel InformacionColas(){
        
        JPanel Panel =  new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 890, 1900, 120);
        //Panel.setBackground(Color.CYAN);
        
        modelTbColas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
        
        tbColas = new JTable();
        tbColas.setModel(modelTbColas);

        modelTbColas.addColumn("Round Robin (1)");
        modelTbColas.addColumn("Prioridades (2)");
        modelTbColas.addColumn("SRTF (3)");
        //modelTbColas.addRow(new Object[]{"Round Robin (1)", "Prioridades (2)", "SRTF (3)"});

        tbColas.getTableHeader().setReorderingAllowed(false);
        tbColas.setBounds(0, 0, 1900, 120);
        tbColas.setVisible(true);

        tbColas.setPreferredScrollableViewportSize(new Dimension(450, 63));
        tbColas.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane (tbColas);
        scroll.setBounds(0,0,1900,120);
        scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Panel.add(scroll);
        
        //Panel.add(tbColas);
        
        return Panel;
    }
    
    public void pintarColas(){
        
        for (int i=0; i < modelTbColas.getRowCount(); i++){
            modelTbColas.removeRow(i);
        }
        
        for (int i=0; i < 10; i++){
            modelTbColas.addRow(new Object[]{"","",""});
        }
        
        ArrayList colaRoundRobin = clientes.imprimir();
        for (int i = 0; i < colaRoundRobin.size();i++){
            modelTbColas.setValueAt(colaRoundRobin.get(i),i, 0);  
        }
        
        ArrayList colaPrioridades = clientesPrioridades.imprimir();
        for (int i = 0; i < colaPrioridades.size();i++){
            modelTbColas.setValueAt(colaPrioridades.get(i),i, 1);  
        }
        
        ArrayList colaRafagas = clientesRafaga.imprimir();
        for (int i = 0; i < colaRafagas.size();i++){
            modelTbColas.setValueAt(colaRafagas.get(i),i, 2);  
        }
        
    }
    
    public void botonIniciar() {
        lbTiempo.setText("Tiempo: " + String.valueOf(tiempo + 1));

        if (colaActual == 0) {
            if (clientes.longitud() != 0) {
                colaActual = 1;
            } else if (clientesPrioridades.longitud() != 0) {
                colaActual = 2;
            } else if (clientesRafaga.longitud() != 0) {
                colaActual = 3;
            } else {
                JOptionPane.showMessageDialog(null, "Las tres colas están vacias :(");
                tiempo++;
            }

        } //Comprobamos si estamos en la cola ROund Robin
        
        else if (colaActual == 1) {
            //Tiempo de espera cola 2
            if (clientesPrioridades.longitud() != 0) {

                while (tiempo - clientesPrioridades.Cabecera.llegada > tiempoEnvejecimiento) {
                    JOptionPane.showMessageDialog(null, "El proceso " + clientesPrioridades.Cabecera.nombre + " lleva mucho esperando pasara a la cola 1");
                    System.out.println("LLEVA MUCHO ESPERANDO LA CABECERA DE LA SEGUNDA COLA");
                    Node aux = clientesPrioridades.Cabecera;
                    clientes.insert(aux.prioridad, tiempo, aux.rafaga, aux.nombre + "{N}", aux.fila, aux.rafagaRestante,
                             aux.tiempoBloqueo, aux.rafagaEjecutada, tiempo, 1);
                    clientesPrioridades.extraer(1);
                    
                    if (clientesPrioridades.longitud() == 0){
                        break;
                    }
                                        
                }
            }
            
            //Tiempo de espera cola 3
            if (clientesRafaga.longitud() != 0) {

                while (tiempo - clientesRafaga.Cabecera.llegada > tiempoEnvejecimiento) {
                    JOptionPane.showMessageDialog(null, "El proceso " + clientesRafaga.Cabecera.nombre + " lleva mucho esperando pasara a la cola 2");
                    System.out.println("LLEVA MUCHO ESPERANDO LA CABECERA DE LA TERCERA COLA");
                    Node aux = clientesRafaga.Cabecera;
                    clientesPrioridades.insert(aux.prioridad, tiempo, aux.rafaga, aux.nombre + "{N}", aux.fila, aux.rafagaRestante,
                             aux.tiempoBloqueo, aux.rafagaEjecutada, aux.tfPrecursor, 2);
                    
                    organizarColaPrioridades();
                    clientesPrioridades = clientesOrganizar;
                    clientesOrganizar = new Cola();
                    
                    clientesRafaga.extraer(1);
                }
                
            }

            //Se setea la persona actual como la cabeza de la cola
            Node personaActual = clientes.Cabecera;

            if (clienteInicial == true) {

                dataAuxInfo = new Object[9];
                dataGantt = new Object[46];

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

            if (Quantum < 5) {
                Quantum++;

                //Si la cola no esta vacia y el tiempo global no ha excedido 30 unidades
                if (clientes.longitud() != 0 || tiempo <= 46) {
                    personaActual.rafagaEjecutada++;
                    //Si el proceso actual aun tiene rafaga
                    if (tiempo < (personaActual.comienzo + personaActual.rafaga - 1)) {

                        //Se muestra la informacion
                        System.out.println("");
                        System.out.println("Estamos en el tiempo: " + tiempo);
                        System.out.println("Se esta atendiendo a " + personaActual.nombre);

                        //En el tiempo actual se llena  esa unidad en el modelo de gantt
                        dataGantt[tiempo + 1] = "X(1)";

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
                        dataGantt[tiempo + 1] = "X(1)";

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
                        //JOptionPane.showMessageDialog(null, personaActual.espera);

                        if (personaActual.tiempoBloqueo != 0) {
                            for (int u = personaActual.tiempoBloqueo + 1; u < personaActual.llegada + 1; u++) {
                                dataGantt[u] = "B";
                                personaActual.espera++;
                            }
                        }

                        dataAuxInfo[5] = personaActual.fin;
                        dataAuxInfo[6] = personaActual.retorno;
                        dataAuxInfo[7] = personaActual.espera;
                        dataAuxInfo[8] = personaActual.numCola;

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
                        colaActual = 0;
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

                }

            } else {
                JOptionPane.showMessageDialog(null, "Quantum excedido...");
                Node auxNode = clientes.Cabecera;
                clientes.insert(auxNode.prioridad, auxNode.llegada, auxNode.rafaga - 5, auxNode.nombre + " (E)", auxNode.fila, auxNode.rafagaRestante, auxNode.tiempoBloqueo, auxNode.rafagaEjecutada, tiempo, auxNode.numCola);
                clientes.extraer(1);
                clienteInicial = true;

                dataAuxInfo[5] = tiempo;
                dataAuxInfo[6] = tiempo - auxNode.llegada;
                dataAuxInfo[7] = (tiempo - auxNode.llegada) - auxNode.rafagaEjecutada;
                dataAuxInfo[8] = 1;
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

        } //Miramos si la cola actual es nuestra cola de prioridades
        else if (colaActual == 2) {
            
            //Tiempo de espera cola 3
            if (clientesRafaga.longitud() != 0) {

                while (tiempo - clientesRafaga.Cabecera.llegada > tiempoEnvejecimiento) {
                    JOptionPane.showMessageDialog(null, "El proceso " + clientesRafaga.Cabecera.nombre + " lleva mucho esperando pasara a la cola 2");
                    System.out.println("LLEVA MUCHO ESPERANDO LA CABECERA DE LA TERCERA COLA");
                    Node aux = clientesRafaga.Cabecera;
                    clientesPrioridades.insert(aux.prioridad, tiempo, aux.rafaga, aux.nombre + "{N}", aux.fila, aux.rafagaRestante,
                             aux.tiempoBloqueo, aux.rafagaEjecutada, aux.tfPrecursor, 2);
                    clientesRafaga.extraer(1);
                    
                    organizarColaPrioridades();
                    clientesPrioridades = clientesOrganizar;
                    clientesOrganizar = new Cola();
                    
                    if (clientesRafaga.longitud()==0){
                        break;
                    }
                    
                }
                
            } 
            
            lbTiempo.setText("Tiempo: " + String.valueOf(tiempo + 1));

            if (clientesPrioridades.longitud() == 0) {
                tiempo++;
                JOptionPane.showMessageDialog(null, "La cola esta vacia");
            } else {

                //Se setea la persona actual como la cabeza de la cola
                Node personaActual = clientesPrioridades.Cabecera;

                if (clienteInicial == true) {

                    dataAuxInfo = new Object[9];
                    dataGantt = new Object[46];

                    //Se organiza la cola
                    organizarColaPrioridades();
                    clientesPrioridades = clientesOrganizar;
                    clientesOrganizar = new Cola();
                    personaActual = clientesPrioridades.Cabecera;

                    //Se genera una nueva semilla aleatoria
                    aleatorio.setSeed(System.currentTimeMillis());

                    //Se guarda el tiempo de comienzo en el tiempo actual
                    personaActual.comienzo = tiempo;
                    clientesPrioridades.Cabecera.comienzo = tiempo;

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

                //Si la cola no esta vacia y el tiempo global no ha excedido 46 unidades
                if (clientesPrioridades.longitud() != 0 || tiempo <= 46) {

                    //Si el proceso actual aun tiene rafaga
                    if (tiempo < (personaActual.comienzo + personaActual.rafaga - 1)) {

                        //Se muestra la informacion
                        System.out.println("");
                        System.out.println("Estamos en el tiempo: " + tiempo);
                        System.out.println("Se esta atendiendo a " + personaActual.nombre);

                        //En el tiempo actual se llena  esa unidad en el modelo de gantt
                        dataGantt[tiempo + 1] = "X(2)";

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
                        dataGantt[tiempo + 1] = "X(2)";

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
                        dataAuxInfo[8] = 2;

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
                        clientesPrioridades.extraer(1);
                        clienteInicial = true;
                        tiempo++;
                        colaActual = 0;
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

                }
            }

        } //Miramos si la persona actual es de la cola de rafagas
        else if (colaActual == 3) {
            //Se setea la persona actual como la cabeza de la cola
            Node personaActual = clientesRafaga.Cabecera;
            if (clienteInicial == true) {

                dataAuxInfo = new Object[9];
                dataGantt = new Object[46];

                //Se organiza la cola
                organizarColaRaf();
                clientesRafaga = clientesOrganizar;
                clientesOrganizar = new Cola();
                personaActual = clientesRafaga.Cabecera;

                //Se genera una nueva semilla aleatoria
                aleatorio.setSeed(System.currentTimeMillis());

                //Se guarda el tiempo de comienzo en el tiempo actual
                personaActual.comienzo = tiempo;
                clientesRafaga.Cabecera.comienzo = tiempo;

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

            //Si la cola no esta vacia y el tiempo global no ha excedido 46 unidades
            if (clientesRafaga.longitud() != 0 || tiempo <= 46) {

                personaActual.rafagaEjecutada++;
                //Si el proceso actual aun tiene rafaga
                if (tiempo < (personaActual.comienzo + personaActual.rafaga - 1)) {

                    //Se muestra la informacion
                    System.out.println("");
                    System.out.println("Estamos en el tiempo: " + tiempo);
                    System.out.println("Se esta atendiendo a " + personaActual.nombre);

                    //En el tiempo actual se llena  esa unidad en el modelo de gantt
                    dataGantt[tiempo + 1] = "X(3)";

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
                    dataGantt[tiempo + 1] = "X(3)";

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
                    dataAuxInfo[8] = 3;

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
                    clientesRafaga.extraer(1);
                    clienteInicial = true;
                    tiempo++;
                    colaActual = 0;
                }

            } else {

                JOptionPane.showMessageDialog(null, "Tiempo excedido o COLA VACIA");

            }
        }
    }
    
    public void botonAgregar() {
        boolean compro = true;
        int Destino;
        int valor;
        Destino = Integer.parseInt(tfCola.getText());
        valor = Integer.parseInt(tfRafaga.getText());
        //Llegada de un nuevo cliente de manera aleatoria en cada unidad de tiempo
        //Cabecera del mensaje
        System.out.println("///////");
        System.out.println("Llego un nuevo cliente");

        //Se le asigna una rafaga y un nombre aleatorio
        int nuevoClientRagafa = valor;
        //int nuevoClientNombre = ;

        //int nuevoClientNombre = aleatorio.nextInt(nombres.length);
        //Se muestra la informacion del nuevo cliente
        System.out.println("Nombre del nuevo cliente: " + "P" + Integer.toString(posicion));
        System.out.println("Rafaga del nuevo cliente: " + nuevoClientRagafa);

        //Se inserta el nuevo cliente en la cola
        aleatorio.setSeed(System.currentTimeMillis());
        int prioPrueba = aleatorio.nextInt(4) + 1;
        
        if (Destino == 1) {
            clientes.insert(prioPrueba, tiempo, nuevoClientRagafa, "P" + Integer.toString(posicion), fila, 0, 0, 0, tiempo, 1);
            System.out.println("Llego a RR");
        } else if (Destino == 2) {
            clientesPrioridades.insert(prioPrueba, tiempo, nuevoClientRagafa, "P" + Integer.toString(posicion), fila, 0, 0, 0, tiempo, 2);
            System.out.println("Llego a prioridades");
        } else if (Destino == 3) {
            clientesRafaga.insert(prioPrueba, tiempo, nuevoClientRagafa, "P" + Integer.toString(posicion), fila, 0, 0, 0, tiempo, 3);
            System.out.println("LLego a la cola de rafagas");
        } else {
            System.out.println("COLA INVALIDA");
            compro = false;
        }
        if (compro) {
            fila++;
            System.out.println("///////////");
            System.out.println("");
            posicion++;
        }
    }
    
    public void botonBloquear() {
        if (clientes.longitud() == 0 && clientesPrioridades.longitud() == 0 && clientesRafaga.longitud() == 0) {
            JOptionPane.showMessageDialog(null, "La colas estan vacias");
        } else {
            
            Node nodoABloquear = null;
            if (colaActual == 1) {
                nodoABloquear = clientes.Cabecera;
            } else if (colaActual == 2) {
                nodoABloquear = clientesPrioridades.Cabecera;
            } else if (colaActual == 3) {
                nodoABloquear = clientesRafaga.Cabecera;
            } else {
                System.out.println("NO HAY PROCESO EN EJECUCUÓN");
            }

            clientesBloqueados.insert(nodoABloquear.prioridad, nodoABloquear.llegada, nodoABloquear.rafaga, nodoABloquear.nombre, nodoABloquear.fila, nodoABloquear.comienzo + nodoABloquear.rafaga - tiempo, tiempo, 0,
                    nodoABloquear.tfPrecursor, nodoABloquear.numCola);
            JOptionPane.showMessageDialog(null, "El proceso en ejecucion sera bloqueado");

            Node aux = clientesBloqueados.Cabecera;

            while (aux.fila != clientes.Cabecera.fila) {
                aux = aux.next;
            }

            aux.comienzo = clientes.Cabecera.comienzo;
            
            if (colaActual == 1) {
                clientes.extraer(1);
            } else if (colaActual == 2) {
                clientesPrioridades.extraer(1);
            } else if (colaActual == 3) {
                clientesRafaga.extraer(1);
            } else {
                System.out.println("NO HAY PROCESO EN EJECUCUÓN");
            }
            
            fila++;
            clienteInicial = true;

            dataAuxInfo[5] = tiempo;
            dataAuxInfo[6] = tiempo - aux.llegada;
            dataAuxInfo[7] = (tiempo - aux.llegada) - (aux.rafaga - aux.rafagaRestante);
            dataAuxInfo[8] = aux.numCola;

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
            colaActual = 0;

        }
    }
    
    public void botonDesbloquear() {
        if (clientesBloqueados.longitud() == 0) {
            JOptionPane.showMessageDialog(null, "No hay procesos bloqueados");
        } else {
            int colaBase = 0;
            colaBase = clientesBloqueados.Cabecera.numCola;
            if (colaBase == 1) {
                clientes.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo, clientesBloqueados.Cabecera.rafagaEjecutada, tiempo, clientesBloqueados.Cabecera.numCola);
            } else if (colaBase == 2) {
                clientesPrioridades.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo, clientesBloqueados.Cabecera.rafagaEjecutada, tiempo, clientesBloqueados.Cabecera.numCola);
                organizarColaPrioridades();
                clientesPrioridades = clientesOrganizar;
                clientesOrganizar = new Cola();
                
            } else {
                clientesRafaga.insert(clientesBloqueados.Cabecera.prioridad, tiempo, clientesBloqueados.Cabecera.rafagaRestante, clientesBloqueados.Cabecera.nombre + " - (D)", clientesBloqueados.Cabecera.fila, 0, clientesBloqueados.Cabecera.tiempoBloqueo, clientesBloqueados.Cabecera.rafagaEjecutada, tiempo, clientesBloqueados.Cabecera.numCola);
                organizarColaRaf();
                clientesRafaga = clientesOrganizar;
                clientesOrganizar = new Cola();
            }

            clientesBloqueados.extraer(1);

            modelTbBloqueados.removeRow(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (e.getSource() == btIniciar) {
            
            botonIniciar();
            
        }

        if (e.getSource() == btAgregar) {
            
            botonAgregar();

        } else if (e.getSource() == btBloquear) {
            
            botonBloquear();
            
        }

        if (e.getSource() == btDesbloquear) {
            
            botonDesbloquear();
            
        }
        
        pintarColas();

    }

    public void organizarColaPrioridades() {
        
        ArrayList<Node> colaOrg = clientesPrioridades.nodosCola();
        
        int x = 0;
        
        if (colaActual == 2){
            x = 1;
        }

        for (int i = x; i < colaOrg.size(); i++) {
            for (int j = 0; j < colaOrg.size() - 1; j++) {
                if (colaOrg.get(j).prioridad > colaOrg.get(j + 1).prioridad) {
                    Node temp = colaOrg.get(j);
                    colaOrg.set(j, colaOrg.get(j+1));
                    colaOrg.set(j+1, temp);
                }
            }
        }

        for (int k = 0; k < colaOrg.size(); k++) {
            clientesOrganizar.insert(colaOrg.get(k).prioridad, colaOrg.get(k).llegada, colaOrg.get(k).rafaga, colaOrg.get(k).nombre, colaOrg.get(k).fila, colaOrg.get(k).rafagaRestante, colaOrg.get(k).tiempoBloqueo, colaOrg.get(k).rafagaEjecutada, colaOrg.get(k).tfPrecursor, colaOrg.get(k).numCola);
        }

    }
    
    public void organizarColaRaf() {

        ArrayList<Node> colaOrg = clientesRafaga.nodosCola();

        for (int i = 1; i < colaOrg.size(); i++) {
            for (int j = 0; j < colaOrg.size() - 1; j++) {
                if (colaOrg.get(j).rafaga > colaOrg.get(j + 1).rafaga) {
                    Node temp = colaOrg.get(j);
                    colaOrg.set(j, colaOrg.get(j + 1));
                    colaOrg.set(j + 1, temp);
                }
            }
        }

        for (int k = 0; k < colaOrg.size(); k++) {
            clientesOrganizar.insert(colaOrg.get(k).prioridad, colaOrg.get(k).llegada, colaOrg.get(k).rafaga, colaOrg.get(k).nombre, colaOrg.get(k).fila, colaOrg.get(k).rafagaRestante, colaOrg.get(k).tiempoBloqueo, colaOrg.get(k).rafagaEjecutada, colaOrg.get(k).tfPrecursor, colaOrg.get(k).numCola);
        }

    }
    
}


