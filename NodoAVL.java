package tareaavl;

/*
 *Dylan Gabriel Gonzalez Sanchez 182190 
 *
 */
public class NodoAVL<T extends Comparable<T>> {

    private T element;
    private NodoAVL<T> izq, der, papa;
    private int fe, cant;
    int altura;

    NodoAVL(T elem) {
        element = elem;
        izq = null;
        der = null;
        fe = 0;
    }

    public int getCambio() {
        int res = 0;
        if (papa != null) {
            if (element.compareTo(papa.getElement()) > 0) {
                res = 1;
            } else {
                res = -1;
            }
        }
        return res;
    }

    public int getFe() {
        return fe;
    }

    public void setFe() {
        int altDer = 0, altIzq = 0;
        if (der != null) {
            altDer = der.getAltura() - 1;
        }
        if (izq != null) {
            altIzq = izq.getAltura() - 1;
        }
        this.fe = altDer - altIzq;
    }

    public void cambiaFE(int cambio) {
        fe += cambio;
    }

    public void feS() {
        this.fe++;
    }

    public void feR() {
        this.fe--;
    }

    public T getElement() {
        return element;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public int compareTo(NodoAVL<T> otro) {
        return this.element.compareTo(otro.element);
    }

    @Override
    public String toString() {
        if(element==null)
            return null;
        return element.toString();
    }

    public void cuelga(NodoAVL<T> n) {
        if (n != null) {
            if (n.getElement().compareTo(element) < 0) {
                izq = n;
            } else {
                der = n;
            }
            n.setPapa(this);
        }

    }

    public int getAltura() {
        altura = 0;
        altura(this, 1);
        return altura;
    }

    private void altura(NodoAVL<T> act, int nivel) {
        if (act != null) {
            altura(act.getIzq(), nivel + 1);
            if (nivel > altura) {
                altura = nivel;
            }
            altura(act.getDer(), nivel + 1);
        }
    }

    public int noDescendientes() {
        cant = 0;
        noDescendientes(this);
        return cant;
    }

    private void noDescendientes(NodoAVL<T> act) {
        if (act != null) {
            cant++;
            noDescendientes(act.getIzq());
            noDescendientes(act.getDer());
        }
    }

}