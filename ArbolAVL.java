/*
 *Jorge Edgar Rodriguez Ortiz Loyola 
 *181334
 */
package tareaavl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Edgar
 */
public class ArbolAVL<T extends Comparable<T>> implements ArbolAVLADT<T> {

    private NodoAVL<T> raiz;
    private int cont;

    public ArbolAVL() {
        raiz = null;
        cont = 0;
    }

    public NodoAVL<T> getRaiz() {
        return raiz;
    }

    public int getCont() {
        return cont;
    }

    @Override
    public boolean isEmpty() {
        return raiz == null;
    }

    @Override
    public int size() {
        return cont;
    }

    @Override
    public boolean contains(T elemento) {
        return find(elemento) != null;
    }
    
    @Override
    public void add(T elem) {
        if (elem != null) {
            NodoAVL<T> nodo = new NodoAVL(elem);
            cont++;
            if (raiz == null) {
                raiz = nodo;
            } else {
                add(nodo, raiz);
            }
        }
    }

    private void add(NodoAVL<T> nodo, NodoAVL<T> act) {
        if (nodo.compareTo(act) > 0) {
            if (act.getDer() == null) {
                act.setDer(nodo);
                nodo.setPapa(act);
                actualizaFE(act, 1);
                return;
            } else {
                add(nodo, act.getDer());
            }
        } else if (act.getIzq() == null) {
            act.setIzq(nodo);
            nodo.setPapa(act);
            actualizaFE(act, -1);
            return;
        } else {
            add(nodo, act.getIzq());
        }
    }

    private void actualizaFE(NodoAVL<T> act, int cambio) {

        while (act != null && cambio != 0) {

            act.cambiaFE(cambio);
            if (Math.abs(act.getFe()) == 2) {
                act = rotacion(act);
            }

            if (act.getFe() == 0) {
                cambio = 0;
            } else {
                cambio = act.getCambio();
            }

            act = act.getPapa();
        }
    }

    private NodoAVL<T> rotacion(NodoAVL<T> n) {
        NodoAVL<T> beta = null, gamma, A, B, C, D, alfa, papa;

        //izquierda-izquierda
        if (n.getFe() == -2 && (n.getIzq() != null && n.getIzq().getFe() == -1 || n.getIzq() != null && n.getIzq().getFe() == 0)) {
            alfa = n;
            papa = n.getPapa();
            beta = alfa.getIzq();
            gamma = beta.getIzq();
            A = gamma.getIzq();
            B = gamma.getDer();
            C = beta.getDer();
            D = alfa.getDer();
            gamma.cuelga(A);
            gamma.cuelga(B);
            alfa.setDer(null);
            alfa.setIzq(null);
            alfa.cuelga(C);
            alfa.cuelga(D);
            beta.cuelga(alfa);
            beta.cuelga(gamma);
            if (papa != null) {
                papa.cuelga(beta);
            } else {
                beta.setPapa(null);
                raiz = beta;
            }
            alfa.setFe();
            beta.setFe();
            return beta;
        }
        //derecha-derecha
        if (n.getFe() == 2 && (n.getDer() != null && n.getDer().getFe() == 1 || n.getDer() != null && n.getDer().getFe() == 0)) {

            alfa = n;
            papa = n.getPapa();
            beta = alfa.getDer();
            gamma = beta.getDer();
            A = alfa.getIzq();
            B = beta.getIzq();
            C = gamma.getIzq();
            D = gamma.getDer();
            alfa.setDer(null);
            alfa.setIzq(null);
            alfa.cuelga(B);
            beta.cuelga(alfa);
            if (papa != null) {
                papa.cuelga(beta);
            } else {
                beta.setPapa(null);
                raiz = beta;
            }
            alfa.setFe();
            beta.setFe();
            return beta;
        }

        //izquierda-derecha
        if (n.getFe() == -2 && n.getIzq() != null && n.getIzq().getFe() == 1) {
            alfa = n;
            papa = n.getPapa();
            beta = alfa.getIzq();
            gamma = beta.getDer();
            A = beta.getIzq();
            B = gamma.getIzq();
            C = gamma.getDer();
            D = alfa.getDer();

            gamma.cuelga(beta);
            gamma.cuelga(alfa);
            alfa.setDer(null);
            alfa.setIzq(null);
            beta.setDer(null);
            beta.setIzq(null);
            alfa.cuelga(C);
            alfa.cuelga(D);
            beta.cuelga(A);
            beta.cuelga(B);
            if (papa != null) {
                papa.cuelga(gamma);
            } else {
                gamma.setPapa(null);
                raiz = gamma;
            }
            alfa.setFe();
            beta.setFe();
            gamma.setFe();
            return gamma;

        }
        //derecha-izquierda 
        if (n.getFe() == 2 && n.getDer() != null && n.getDer().getFe() == -1) {
            alfa = n;
            papa = n.getPapa();
            beta = alfa.getDer();
            gamma = beta.getIzq();
            A = alfa.getIzq();
            B = gamma.getIzq();
            C = gamma.getDer();
            D = beta.getDer();

            gamma.cuelga(beta);
            gamma.cuelga(alfa);
            alfa.setDer(null);
            alfa.setIzq(null);
            beta.setDer(null);
            beta.setIzq(null);
            alfa.cuelga(A);
            alfa.cuelga(B);
            beta.cuelga(C);
            beta.cuelga(D);
            if (papa != null) {
                papa.cuelga(gamma);
            } else {
                gamma.setPapa(null);
                raiz = gamma;
            }
            alfa.setFe();
            beta.setFe();
            gamma.setFe();
            return gamma;

        }
        return beta;
    }

    @Override
    public boolean remove(T elem) {
        NodoAVL<T> borrar = find(raiz, elem);
        if (borrar != null) {
            if (borrar.getDer() == null && borrar.getIzq() == null) {
                if (borrar == raiz) {
                    raiz = null;
                } else {
                    if (borrar.getPapa().getDer() == (borrar)) {
                        borrar.getPapa().setDer(null);
                        actualizaFEborrar(borrar.getPapa(), -1);
                    } else {
                        borrar.getPapa().setIzq(null);
                        actualizaFEborrar(borrar.getPapa(), 1);
                    }
                    borrar.setPapa(null);
                }
            } else {
                if (borrar.getIzq() == null) {
                    if (borrar == raiz) {
                        raiz = borrar.getDer();
                        raiz.setPapa(null);
                    } else {
                        borrar.getPapa().cuelga(borrar.getDer());
                        actualizaFEborrar(borrar.getPapa(), -1);
                        borrar.setPapa(null);
                    }
                } else {
                    if (borrar.getDer() == null) { 
                        if (borrar == raiz) {
                            raiz = borrar.getIzq();
                            raiz.setPapa(null);
                        } else {
                            borrar.getPapa().cuelga(borrar.getIzq());
                            actualizaFEborrar(borrar.getPapa(), 1);
                            borrar.setPapa(null);
                        }
                    } else {
                        NodoAVL<T> suc = borrar.getDer();
                        while (suc.getIzq() != null) {
                            suc = suc.getIzq();
                        }
                        borrar.setElement(suc.getElement());
                        if (suc == borrar.getDer()) {
                            borrar.setDer(suc.getDer());
                            actualizaFEborrar(borrar, -1);
                        } else {
                            suc.getPapa().setIzq(suc.getDer());
                            actualizaFEborrar(suc.getPapa(), 1);
                        }

                    }
                }

            }
            cont--;
            return true;
        } else {
            return false;
        }
    }

    private void actualizaFEborrar(NodoAVL<T> act, int cambio) {

        while (act != null && cambio != 0) {

            if (act.getPapa() == null || act.getFe() == 0) {
                act.cambiaFE(cambio);
                cambio = 0;
            } else {
                act.cambiaFE(cambio);
                if (act.compareTo(act.getPapa()) > 0) {
                    cambio = -1;
                } else {
                    cambio = 1;
                }
            }
            if (Math.abs(act.getFe()) == 2) {
                act = rotacion(act);
            }
            act = act.getPapa();
        }
    }

    @Override
    public NodoAVL<T> find(T elemento) {
        if (elemento != null) {
            return find(raiz, elemento);
        } else {
            return null;
        }

    }

    private NodoAVL<T> find(NodoAVL<T> act, T elem) {
        boolean enc = false;

        while (!enc && act != null) {
            if (elem.compareTo(act.getElement()) < 0) {
                act = act.getIzq();
            } else if (elem.compareTo(act.getElement()) > 0) {
                act = act.getDer();
            } else {
                enc = true;
            }
        }
        return act;

    }
    @Override
    public void impresionIzquierdaDerecha() {
        Queue<NodoAVL<T>> cola = new LinkedList<>();
          NodoAVL<T> act;

        cola.add(raiz);
        while (!(cola.isEmpty())) {
            act = cola.remove();
            System.out.print(act.toString() + "(" + act.getFe() + ")" + "  ");
            if (act.getIzq() != null) {
                cola.add(act.getIzq());
            }
            if (act.getDer() != null) {
                cola.add(act.getDer());
            }
        }
    }
}