/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mateo
 */
public class Principal {
    //Se define un JFrame global
    public static JFrame ventana;
    
    public static void main(String[] args) {
        
        GUI GU = new GUI();
        
        JPanel PaTitulo = GU.Titulo();
        JPanel PaTbInfo = GU.Tabla();
        JPanel PaGant = GU.Gant();
        JPanel PaBotonera = GU.Botonera();
        JPanel PaInfo = GU.Informacion();
        JPanel PaBloqueados = GU.ColaBloqueos();
        
        ventana = new JFrame("");
        
        ventana.add(PaTitulo);
        ventana.add(PaTbInfo);
        ventana.add(PaGant);
        ventana.add(PaBotonera);
        ventana.add(PaInfo);
        ventana.add(PaBloqueados);
        
        ventana.setSize(1280, 900);
        ventana.setTitle("Algoritmo de Planificacion Round Robin");
        
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        
        ventana.setVisible(true);
        
        
    }
    
    
    
}
