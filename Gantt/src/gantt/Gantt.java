/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

import java.util.Random;

/**
 *
 * @author KevinB
 */
public class Gantt {

    /**
     * @param args the command line arguments
     */
    static String[] nombres = {"Matias Roca", "Julen Miguel", "Iluminada Gracia", "Felisa Montesinos", "Óscar Collado", "Ian Solana", "Serafin Mari", "Encarnacion del Moral", "Sebastiana Lin"};
    
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        Random alea = new Random(System.currentTimeMillis());
        alea.setSeed(System.currentTimeMillis());

        boolean disponible = true;
        int tiempo = 0;
        Cola clientes = new Cola();
        int rafa = alea.nextInt(8)+3;
        int pNombre = alea.nextInt(9);

        clientes.insert(tiempo, rafa, nombres[pNombre]);
        
        while (clientes.longitud()!=0 && tiempo<30){
            alea.setSeed(System.currentTimeMillis());

            Thread.sleep(2000); //Pausara 2 segundos
            clientes.Cabecera.comienzo=tiempo;
            Node personaActual = clientes.Cabecera;
            System.out.println("Cliente que sera atendido:"+personaActual.nombre );
            System.out.println("Numero de rafaga:"+personaActual.rafaga);
            int auxiliar =personaActual.rafaga;
            while (auxiliar>0){
                Thread.sleep(2000); //Pausara 2 segundos
                System.out.println("Estamos en el "+tiempo);
                System.out.println("Se esta atendiendo a "+personaActual.nombre);
                tiempo++;
                auxiliar--;
                int x = alea.nextInt(6);
                if (x==4){
                    System.out.println("///////");
                    System.out.println("Llego un nuevo cliente");
                    int prafa = alea.nextInt(4)+2;
                    int pombre = alea.nextInt(9);
                    System.out.println("Rafagas: "+prafa+" nombre: "+nombres[pombre] );
                    clientes.insert(tiempo-1, prafa, nombres[pombre]);
                    System.out.println("///////////");
                }
                
            }
            System.out.println("------------------------");
            System.out.println("Resumen de: " + personaActual.nombre);
            System.out.println("Llegada en: " + personaActual.llegada);
            System.out.println("Rafaga de: "+ personaActual.rafaga);
            System.out.println("Comenzo a las: " + personaActual.comienzo);
            personaActual.fin=personaActual.rafaga+personaActual.comienzo;
            System.out.println("Tiempo final: "+ personaActual.fin);
            personaActual.retorno=personaActual.fin-personaActual.llegada;
            System.out.println("Tiempo de retorno: "+ personaActual.retorno);
            personaActual.espera= personaActual.retorno-personaActual.rafaga;
            System.out.println("Salio en: " + tiempo);
            System.out.println("------------------------");
            System.out.println("(Pausa incomoda para leer el resumen)");
            Thread.sleep(10000);
            clientes.extraer(1);
            
            
        }
        
        
        
        
    }
    
}
