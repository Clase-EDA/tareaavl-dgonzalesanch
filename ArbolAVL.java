/*
 * 
 *
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
public class ArbolAVL<T extends Comparable <T>>  implements ArbolAVLADT<T> {
    private NodoAVL<T> raiz;
    private int cont;
    
    public ArbolAVL(){
        raiz=null;
        cont=0;
    }
    
    public NodoAVL<T> buscar(T elem){
        NodoAVL<T> actual=raiz;
        while(actual!=null && actual.element.compareTo(elem)!=0){
            if(actual.element.compareTo(elem)>0){
                actual=actual.izq;
            }
            else
                actual=actual.der;
        }
        
        return actual;
    }
    
    public boolean insertar(T elem){
        boolean res=false;
        if(elem!=null && buscar(elem)==null){
            cont++;
            NodoAVL<T> papa=null;
            NodoAVL<T> aux=raiz;
            if(raiz!=null){
                while(aux!=null){
                    papa=aux;
                    if(elem.compareTo(aux.element)>=0)
                        aux=aux.der;
                    else
                        aux=aux.izq;
                }

                aux=new NodoAVL<T>(elem);
                papa.cuelga(aux);

                boolean termine=false;
                while(!termine && aux.getPapa()!=null){
                    if(aux==aux.getPapa().getIzq())
                        aux.getPapa().factor-=1;
                    else
                        aux.getPapa().factor+=1;

                    if(aux.getPapa().factor==0)
                        termine=true;
                    if(Math.abs(aux.getPapa().getFactor())==2){
                        aux=rotacion(aux.getPapa());
                        termine=true;
                    }
                    aux=aux.getPapa();
                }
                res=true;
            }
            else{
                raiz=new NodoAVL<T>(elem);
                raiz.setPapa(null);
                res=true;
            }
        }
        return res;
    }
    
    public boolean elimina(T elem){
        boolean res=true;
        NodoAVL<T> actual= buscar(elem), padre;
        if(actual==null)
            res=false;
        else{
            cont--;
            boolean termine=false;
            if(actual.getDer()==null && actual.getIzq()==null){
                if(actual==raiz){
                    raiz=null;
                    termine=true;
                }
                else
                    if(elem.compareTo(actual.getPapa().getElement())<0){
                        if(actual.papa.factor==0)
                            termine=true;
                        actual.papa.factor+=1;
                        actual.papa.setIzq(null);
                        padre=actual.papa;
                    }
                    else{
                        if(actual.papa.factor==0)
                            termine=true;
                        actual.papa.factor-=1;
                        actual.papa.setDer(null);
                        padre=actual.papa;
                    }
            }
            else{
                if(actual.getIzq()==null){
                    if(actual==raiz){
                        raiz=actual.getDer();
                        raiz.setPapa(null);
                        termine=true;
                    }
                    else
                        actual.getPapa().cuelgaDer(actual.getDer());
                }
                else
                    if(actual.getDer()==null){
                        if(actual==raiz){
                            raiz=actual.getIzq();
                            raiz.setPapa(null);
                        }
                        else
                            actual.getPapa().cuelgaIzq(actual.getIzq());
                    }
                    else{
                        NodoAVL<T> suc= actual.getDer();
                        while(suc.getIzq()!=null)
                            suc=suc.getIzq();
                        actual.setElement(suc.getElement());
                        if(suc==actual.getDer()){
                            actual.cuelgaDer(suc.getDer());
                            if(actual.factor==0)
                                termine=true;
                            actual.factor-=1;
                        }
                        else{
                            if(suc.papa.factor==0)
                                termine=true;
                            suc.getPapa().factor+=1;
                            actual=suc.papa;
                            suc.getPapa().cuelgaIzq(suc.getDer());
                        }
                    }
            }
            if(actual!=null && Math.abs(actual.getFactor())==2){
                    actual=rotacion(actual);
                    termine=true;
            }
            while(!termine && actual.getPapa()!=null){
                if(actual==actual.getPapa().getIzq())
                    actual.getPapa().factor+=1;
                else
                    actual.getPapa().factor-=1;

                if(Math.abs(actual.getPapa().getFactor())==2){
                    actual=rotacion(actual.papa);
                    termine=true;
                }
                if(!termine && Math.abs(actual.getPapa().getFactor())==1){
                    termine=true;
                }
                actual=actual.getPapa();
            }
        }
        return res;
    }
    
    
    
    private NodoAVL<T> rotacion(NodoAVL<T> N){
        NodoAVL<T> res=N;
        
        if(N.getFactor()==-2 && N.izq!=null && N.izq.factor<=0){
            NodoAVL<T> alfa=N;
            NodoAVL<T> papa=N.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=gamma.getIzq();
            NodoAVL<T> B=gamma.getDer();
            NodoAVL<T> C=beta.getDer();
            NodoAVL<T> D=alfa.getDer();
            gamma.cuelgaIzq(A);
            gamma.cuelgaDer(B);
            alfa.cuelgaIzq(C);
            alfa.cuelgaDer(D);
            beta.cuelgaDer(alfa);
            beta.cuelgaIzq(gamma);
            if(papa!=null)
                papa.cuelga(beta);
            else{
                beta.setPapa(null);
                raiz=beta;
            }
            if(beta.factor==-1){
                if(gamma.factor==-1){
                    gamma.setFactor();
                    beta.setFactor();
                    alfa.setFactor();
                }
                else{
                    if(gamma.factor==0){
                        gamma.setFactor();
                        beta.setFactor();
                        alfa.setFactor();
                    }
                    else{
                        gamma.setFactor();
                        beta.setFactor();
                        alfa.setFactor();
                    }  
                }
            }
            else{
                if(gamma.factor==-1){
                    gamma.setFactor();
                    beta.setFactor();
                    alfa.setFactor();
                }
                else{
                    if(gamma.factor==0){
                        gamma.setFactor();
                        beta.setFactor();
                        alfa.setFactor();
                    }
                    else{
                        gamma.setFactor();
                        beta.setFactor();
                        alfa.setFactor();
                    }
                }  
            }
            res=beta;
        }
        else{
            if(N.getFactor()==-2 && N.izq!=null &&  N.izq.factor==1){
                NodoAVL<T> alfa=N;
                NodoAVL<T> papa=N.getPapa();
                NodoAVL<T> beta=alfa.getIzq();
                NodoAVL<T> gamma=beta.getDer();
                NodoAVL<T> A=gamma.getIzq();
                NodoAVL<T> B=gamma.getDer();
                NodoAVL<T> C=beta.getIzq();
                NodoAVL<T> D=alfa.getDer();
                gamma.cuelgaIzq(beta);
                gamma.cuelgaDer(alfa);
                alfa.cuelgaDer(C);
                alfa.cuelgaIzq(B);
                beta.cuelgaDer(A);
                beta.cuelgaIzq(C);
                if(papa!=null)
                    papa.cuelga(gamma);
                else{
                    gamma.setPapa(null);
                    raiz=gamma;
                }
                if(gamma.getFactor()==1){
                    beta.setFactor();
                    alfa.setFactor();
                    gamma.setFactor();
                }
                else{
                    if(gamma.getFactor()==-1){
                        beta.setFactor();
                        alfa.setFactor();
                        gamma.setFactor();
                    }
                    else{
                        beta.setFactor();
                        alfa.setFactor();
                        gamma.setFactor();
                    }
                }
                res=gamma;
            }
            else{
                if(N.getFactor()==2 && N.der!=null &&  N.der.factor>=0){
                    
                    NodoAVL<T> alfa=N;
                    NodoAVL<T> papa=N.getPapa();
                    NodoAVL<T> beta=alfa.getDer();
                    NodoAVL<T> gamma=beta.getDer();
                    NodoAVL<T> A=gamma.getIzq();
                    NodoAVL<T> B=gamma.getDer();
                    NodoAVL<T> C=beta.getIzq();
                    NodoAVL<T> D=alfa.getIzq();
                    gamma.cuelgaIzq(A);
                    gamma.cuelgaDer(B);
                    alfa.cuelgaDer(C);
                    alfa.cuelgaIzq(D);
                    beta.cuelgaIzq(alfa);
                    beta.cuelgaDer(gamma);
                    if(papa!=null)
                        papa.cuelga(beta);
                    else{
                        beta.setPapa(null);
                        raiz=beta;
                    }
                    if(beta.factor==1){
                        if(gamma.factor==1){
                            gamma.setFactor();
                            beta.setFactor();
                            alfa.setFactor();
                        }
                        else{
                            if(gamma.factor==0){
                                gamma.setFactor();
                                beta.setFactor();
                                alfa.setFactor();
                            }
                            else{
                                gamma.setFactor();
                                beta.setFactor();
                                alfa.setFactor();
                            }  
                        }
                    }
                    else{
                        if(gamma.factor==1){
                            gamma.setFactor();
                            beta.setFactor();
                            alfa.setFactor();
                        }
                        else{
                            if(gamma.factor==0){
                                gamma.setFactor();
                                beta.setFactor();
                                alfa.setFactor();
                            }
                            else{
                                gamma.setFactor();
                                beta.setFactor();
                                alfa.setFactor();
                            }
                        }  
                    }
                    res=beta;
                }
                else{
                    NodoAVL<T> alfa=N;
                    NodoAVL<T> papa=N.getPapa();
                    NodoAVL<T> beta=alfa.getDer();
                    NodoAVL<T> gamma=beta.getIzq();
                    NodoAVL<T> A=gamma.getIzq();
                    NodoAVL<T> B=gamma.getDer();
                    NodoAVL<T> D=beta.getDer();
                    NodoAVL<T> C=alfa.getIzq();
                    alfa.cuelgaIzq(C);
                    alfa.cuelgaDer(A);
                    beta.cuelgaDer(D);
                    beta.cuelgaIzq(B);
                    gamma.cuelgaDer(beta);
                    gamma.cuelgaIzq(alfa);
                    
                    if(papa!=null)
                        papa.cuelga(gamma);
                    else{
                        gamma.setPapa(null);
                        raiz=gamma;
                    }
                    
                    if(gamma.getFactor()==1){
                        beta.setFactor();
                        alfa.setFactor();
                        gamma.setFactor();
                    }
                    else{
                        if(gamma.getFactor()==-1){
                            beta.setFactor();
                            alfa.setFactor();
                            gamma.setFactor();
                        }
                        else{
                            beta.setFactor();
                            alfa.setFactor();
                            gamma.setFactor();
                        }
                    }
                    
                    res=gamma;
                    
                }
                    
            }
                
        }
        return res;
    }
    @Override
    public void impresionIzquierdaDerecha() {
        Queue<NodoAVL<T>> cola = new LinkedList<>();
          NodoAVL<T> act;

        cola.add(raiz);
        while (!(cola.isEmpty())) {
            act = cola.remove();
            System.out.print(act.toString() + "(" + act.getFactor() + ")" + "  ");
            if (act.getIzq() != null) {
                cola.add(act.getIzq());
            }
            if (act.getDer() != null) {
                cola.add(act.getDer());
            }
        }
    }
}