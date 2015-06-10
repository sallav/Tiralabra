/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 *
 * @author Käyttäjä
 */
public class Solmu implements Comparable{
    Solmu vanhempi;
    Solmu vasen;
    Solmu oikea;
    int avain;
    int arvo;
    char suunta;
    
    public Solmu(int avain){
        this.vanhempi = null;
        this.vasen = null;
        this.oikea = null;
        this.avain = avain;
        this.arvo = 0;
        this.suunta = 'x';
    }
    
    public Solmu(Solmu vanhempi, int avain){
        this.vanhempi = vanhempi;
        this.vasen = null;
        this.oikea = null;
        this.avain = avain;
        this.arvo = 0;
        this.suunta = 'x';
    }
    
    public void setAvain(int avain){
        this.avain = avain;
    }
    
    public void setArvo(int arvo){
        this.arvo = arvo;
    }
    
    public void setVasen(Solmu lapsi){
        this.vasen = lapsi;
    }
    
    public void setOikea(Solmu lapsi){
        this.oikea = lapsi;
    }
    
    public void setVanhempi(Solmu vanhempi){
        this.vanhempi = vanhempi;
    }
    
    public void setSuunta(char suunta){
        this.suunta = suunta;
    }
    
    public Solmu getVanhempi(){
        return this.vanhempi;
    }
    
    public Solmu getVasen(){
        return this.vasen;
    }
    
    public Solmu getOikea(){
        return this.oikea;
    }
    
    public int getAvain(){
        return this.avain;
    }
    
    public int getArvo(){
        return this.arvo;
    }
    
    public char getSuunta(){
        return this.suunta;
    }

    @Override
    public int compareTo(Object o){
        Solmu toinen;
        if(o.getClass()==this.getClass()){
            toinen = (Solmu)o;
        }
        else return 0;
        if(this.arvo>toinen.getArvo())    return 1;
        else if(this.arvo<toinen.getArvo()) return -1;
        else    return 0;    
    }
    
}
