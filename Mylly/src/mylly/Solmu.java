/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Solmu -luokka määrittelee binäärihakupuuhun solmun, jonka avaimena on 
 * sijainti pelilaudalla
 * @author Salla
 */
public class Solmu implements Comparable{
    Solmu vanhempi;
    Solmu vasen;
    Solmu oikea;
    int avain;
    int arvo;
    char suunta;
    Solmu kohde;
    
    /**
     * Solmu -luokan konstruktori alustaa avaimen arvoksi annetun parametrin 
     * solmun vanhemmaksi sekä vasemmaksi ja oikeaksi lapseksi asetetaan null
     * @param avain kokonaisluku, joka edustaa sijaintia laudalla
     */
    public Solmu(int avain){
        this.vanhempi = null;
        this.vasen = null;
        this.oikea = null;
        this.avain = avain;
        this.arvo = 0;
        this.suunta = 'x';
        this.kohde = null;
    }
    
    public Solmu(int avain, int arvo){
        this.vanhempi = null;
        this.vasen = null;
        this.oikea = null;
        this.avain = avain;
        this.arvo = arvo;
        this.suunta = 'x';
        this.kohde = null;
    }
    
    /**
     * Solmu -luokan konstruktori alustaa avaimen arvoksi parametrina annetun avaimen
     * sekä solmun vanhemmaksi parametrina annetun toisen Solmu -luokan ilmentymän.
     * Vasen ja oikea lapsi saavat arvoikdeen null.
     * @param vanhempi solmun vanhepi
     * @param avain solmun avain
     */
    public Solmu(Solmu vanhempi, int avain){
        this.vanhempi = vanhempi;       
        this.vasen = null;
        this.oikea = null;
        this.avain = avain;
        this.arvo = 0;
        this.suunta = 'x';
        this.kohde = null;
    }
    
    /**
     * setAvain -metodi asettaa solmun avaimeksi parametrina annetun arvon
     * @param avain kokonaisluku, joka edustaa sijaintia pelilaudalla
     */
    public void setAvain(int avain){
        this.avain = avain;
    }
    
    /**
     * setArvo -metodi asettaa solmulle arvon, joka kuvastaa sijainnin hyvyyttä
     * pelaajan kannalta sillä hetkellä
     * @param arvo kokonaisluku, joka edustaa sijainnin hyvyyttä
     */
    public void setArvo(int arvo){
        this.arvo = arvo;
    }
    
    /**
     * setVasen -metodi asettaa solmun vasemmaksi lapseksi parametrina annetun
     * Solmu -olion.
     * @param lapsi Solmu -luokan ilmentymä, joka asetetaan solmun vasemmaksi lapseksi
     */
    public void setVasen(Solmu lapsi){
        if(lapsi==null) this.vasen = null;
        this.vasen = lapsi;
    }
    
    /**
     * setOikea -metodi asettaa solmun oikeaksi lapseksi parametrina annetun
     * Solmu -olion
     * @param lapsi Solmu -luokan ilmentymä, joka asetetaan solmun oikeaksi lapseksi
     */
    public void setOikea(Solmu lapsi){
        if(lapsi==null) this.oikea = null;
        this.oikea = lapsi;
    }
    
    /**
     * setVanhempi -metodi asettaa solmun vanhemmaksi parametrina annetun Solmu -olion
     * @param vanhempi Solmu -luokan ilmentymä, joka asetetaan solmun vanhemmaksi
     */
    public void setVanhempi(Solmu vanhempi){
        if(vanhempi==null)  this.vanhempi = null;
        else this.vanhempi = vanhempi;
    }
    
    /**
     * setSuunta -metodi asettaa suunnan, johon kyseisen solmun edustamasta sijainnista
     * kannattaisi sillä hetkellä liikkua.
     * @param suunta suunta laudalla johon sijainnista kannattaisi liikkua
     */
    public void setSuunta(char suunta){
        this.suunta = suunta;
    }
    
    public void setKohde(Solmu solmu){
        this.kohde = solmu;
    }
    
    /**
     * getVanhempi -metodi palauttaa solmun vanhemman
     * @return Solmu -luokan ilmentymä, joka on asetettu solmun vanhemmaksi 
     */
    public Solmu getVanhempi(){
        return this.vanhempi;
    }
    
    /**
     * getVasen -metodi palauttaa solmun vasemman lapsen
     * @return Solmu -luokan ilmentymä, joka on asetettu solmun vasemmaksi lapseksi
     */
    public Solmu getVasen(){
        return this.vasen;
    }
    
    /**
     * getOikea -metodi palauttaa solmun oikean lapsen
     * @return Solmu -luokan ilmentymä, joka on asetettu solmun oikeaksi lapseksi
     */
    public Solmu getOikea(){
        return this.oikea;
    }
    
    /**
     * getAvain -metodi palauttaa solmun avaimen, joka kuvastaa sijaintia laudalla
     * @return kokonaisluku, joka kuvaa sijaintia laudalla eli solmun avain
     */
    public int getAvain(){
        return this.avain;
    }
    
    /**
     * getArvo -metodi palauttaa solmulle merkityn arvon, joka kuvastaa sitä kuinka hyvä 
     * sijainti on pelaajan kannalta ts. kannattaako siihen siirtää merkkiä tai kannattaako
     * siinä olevaa merkkiä siirtää.
     * @return arvo joka kuvaa kuinka hyvä sijainti on pelaajan kannalta
     */
    public int getArvo(){
        return this.arvo;
    }
    
    /**
     * getSuunta -metodi palauttaa suunnan, johon siinä mahdollisesti olevaa nappulaa
     * kannattaisi siirtää
     * @return suunta johon sijainnissa olevaa nappulaa kannattaisi siirtää
     */
    public char getSuunta(){
        return this.suunta;
    }
    
    public Solmu getKohde(){
        return this.kohde;
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
