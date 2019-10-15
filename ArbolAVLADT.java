/*
 *Dylan Gabriel Gonzalez Sanchez
 *
 */
package tareaavl;

import java.util.Iterator;

/**
 *
 * 
 */
public interface ArbolAVLADT<T extends Comparable<T>> {

    public boolean isEmpty();

    public int size();

    public boolean contains(T elemento);

    public NodoAVL<T> find(T elemento);

    public void impresionIzquierdaDerecha();

    public void add(T elem);

    public boolean remove(T elem);


}