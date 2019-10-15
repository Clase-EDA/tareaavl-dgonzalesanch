/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaavl;

/**
 *
 * @author dgonzalesanch
 */
public class TareaAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArbolAVL<Integer> a = new ArbolAVL<>();
        
        a.add(2);
        a.add(18);
        a.add(4);
        a.add(2000);
        a.add(3);
        a.add(666);
        a.add(5);
        a.add(420);
        a.remove(666);
        a.impresionIzquierdaDerecha();
    }
}
