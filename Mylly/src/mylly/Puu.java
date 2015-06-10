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
public class Puu {
    Solmu juuri;
    
    public Puu(int[] luvut){
        this.juuri = new Solmu(luvut[0]);
        for(int i=1; i<luvut.length; i++){
            lisaaSolmu(luvut[i]);
        }
    }
    
    public Puu(Solmu juuri){
        this.juuri = juuri;
    }
    
    public Puu(){
        this.juuri = null;
    }
    
    public Solmu getJuuri(){
        return this.juuri;
    }
    
    public boolean lisaaSolmu(int avain){
        Solmu uusi = new Solmu(avain);
        return lisaa(uusi, this.juuri);
    }
    
    public boolean lisaa(Solmu lisa, Solmu juuri){
        Solmu vanhempi = lehti(lisa, juuri);
        if(lisa.getAvain()>vanhempi.getAvain())         vanhempi.setOikea(lisa);
        else if(lisa.getAvain()<vanhempi.getAvain())    vanhempi.setVasen(lisa);
        else    return false;
        lisa.setVanhempi(vanhempi);
        if(vanhempi==null)  this.juuri = lisa;      //jos puu on tyhjä laitetaan juureksi
        return true;
    }
    
    public Solmu poista(int avain){
        Solmu p = etsi(avain);
        if(p!=null){
            Solmu vas = p.getVasen();
            Solmu oik = p.getOikea();
            Solmu vanh = p.getVanhempi();
            vanh.setVasen(vas);
            lisaa(oik, vas);
        }
        return p;
    }
    
    public Solmu lehti(Solmu uusi, Solmu juuri){
        Solmu v = juuri;
        Solmu p = null;
        while(v != null){
            p = v;
            if(uusi.getAvain()<v.getAvain()) v = v.getVasen();
            else    v = v.getOikea();
        }
        return p;
    }
    
    public Solmu etsi(int avain){
        Solmu x = this.juuri;
        while(x!=null){
            if(avain>x.getAvain())  x = x.getOikea();
            if(avain<x.getAvain())  x = x.getVasen();
            else return x;
        }
        return null;
    }
}
