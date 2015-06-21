/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Sijaintipuu2 toteuttaa binäärihakupuun, johon voi tallettaa laudalla olevia sijainteja avaiminaan
 * sisältäviä Solmu -luokan ilmentymiä
 * @author Salla
 */
public class SijaintiPuu2 implements Puu{
    Solmu juuri;
    int koko;
    
    /**
     * Luokan konstruktori saa parametrinaan taulukon lukuja ja lisää puuhun solmut, 
     * jotka sisältävät avaiminaan nämä luvut.
     * @param luvut taulukossa avaimet puun solmuihin 
     */
    public SijaintiPuu2(int[] luvut){
        this.koko = 1;
        setJuuri(new Solmu(luvut[0]));
        for(int i=1; i<luvut.length; i++){
            lisaa(luvut[i]);
        }
    }
    
    /**
     * Luokan konstruktori asettaa puun juureksi parametrinaan saamansa Solmu -luokan
     * ilmentymän sekä puun kooksi 1
     * @param juuri puun juurisolmu (Solmu -olio)
     */
    public SijaintiPuu2(Solmu juuri){
        this.koko = 1;
        setJuuri(juuri);
    }
    
    /**
     * Luokan konstruktori asettaa puun juureksi null sekä kooksi 0 
     */
    public SijaintiPuu2(){
        this.koko = 0;
        this.juuri = null;
    }
    
    @Override
    public Solmu getJuuri(){
        return this.juuri;
    }
    
    /**
     * setJuuri -metodi asettaa puun juureksi parametrinaan saadun Solmu -luokan ilmentymän
     * sekä asettaa tämän vanhemmaksi null. Jos parametrina saatu solmu on null asetetaan
     * puun kooksi 0.
     * @param solmu
     */
    public void setJuuri(Solmu solmu){
        this.juuri = solmu;
        if(this.juuri==null) this.koko = 0;
        else this.juuri.setVanhempi(null);
    }
    
    @Override
    public boolean lisaa(int avain){
        Solmu solmu = new Solmu(avain);
        if(this.juuri==null){   //puu on tyhjä
            setJuuri(solmu);
            this.koko++;
        }
        else{
        boolean onnistuu = lisaaJuureen(solmu, avain, this.juuri);
            if(onnistuu)    this.koko++;
            else return false;
        }
        return true;
    }
    
    @Override
    public boolean lisaa(Solmu solmu){
        if(solmu==null) return false;
        if(this.juuri==null){   //puu on tyhjä
            setJuuri(solmu);
            this.koko++;
        }
        else{
        int avain = solmu.getAvain();
        boolean onnistuu = lisaaJuureen(solmu, avain, this.juuri);
            if(onnistuu)    this.koko++;
            else return false;
        }
        return true;
    }
    
    private boolean lisaaJuureen(Solmu solmu, int avain, Solmu juuri){
        if(juuri==this.juuri && this.juuri==null)    setJuuri(solmu);   //jos juuri on null asetetaan uudeksi juureksi
        else{
            if(solmu==null || juuri==null)    return false;     
            Solmu vanhempi = etsiVanhempi(avain, juuri);        //etsitään sopiva vanhempi juuresta alkavasta alipuusta
            int vavain = vanhempi.getAvain();                   
            if(vanhempi==null || vavain==avain)  return false;  //ei lisätä puuhun jos puussa on jo vastaava sijainti
            if(avain<vavain)   vanhempi.setVasen(solmu);        //jos pienempi kuin vanhemman avain lisätään vasemmalle
            else if(avain>vavain)  vanhempi.setOikea(solmu);    //jos suurempi kuin vanhemman avain lisätään oikealle
            solmu.setVanhempi(vanhempi);                        
        }
        return true;
    }
    
    @Override
    public Solmu poista(int avain){
        Solmu p = etsi(avain, this.juuri);
        if(p!=null){
            if(p==this.juuri)   return poistaJuuri();
            else    poisto(p);
        }
        return p;
    }
    
    /**
     * poisto -metodi irrottaa parametrina saamansa solmun puusta ja lisää sen lapset jäljelle
     * jäävään puuhun
     * @param p poistettava Solmu -olio
     */
    public void poisto(Solmu p){           //solmun poisto puusta
        Solmu vanhempi = p.getVanhempi();
        Solmu vas = p.getVasen();
        Solmu oik = p.getOikea();
        irrota(p);                          //irrotetaan puusta
        Solmu uusilapsi = oik;      
        if(vas!=null) uusilapsi = vas;      //lisätään vanhemman lapseksi solmun vasen lapsi tai jos se on null niin oikea
            if(vanhempi==null && uusilapsi==null)   nollaaJuuri();      //jos puu on poiston jälkeen tyhjä
            else{ 
                if(vanhempi==null)    setJuuri(uusilapsi);              //jos poistettiin juuri asetetaan lapsi juureksi
                else if(uusilapsi==null)    nollaaLapsi(vanhempi, p==vanhempi.getVasen());  //jos ei lapsia vanhemman lapseksi null
                else    vanhemmanLapseksi(vanhempi, uusilapsi, p==vanhempi.getVasen());     //asetetaan vanhemmalle lapseksi poistetun solmun lapsi
                this.koko--;    
                if(vas!=null && oik!=null) lisaaAlipuuhun(oik, uusilapsi);        //lisätään mahdollinen oikea lapsi toisesta lapsesta alkavaan alipuuhun 
            }
    }
    
    /**
     * poistaJuuri -metodi poistaa puusta sen juurisolmun sekä asettaa uudeksi juureksi 
     * tämän lapsi solmun ja lisää uuteen puuhun toisen lapsisolmun
     * @return poistettu Solmu -luokan ilmentymä
     */
    public Solmu poistaJuuri(){             
        Solmu p = this.juuri;
        if(p!=null){                        
            Solmu v = this.juuri.getVasen();    
            Solmu o = this.juuri.getOikea();
            irrota(p);
            if(v!=null) this.juuri = v;     //jos on vasen lapsi niin asetetaan se juureksi
            else this.juuri = o;            //muuten oikea
            if(this.juuri!=null) this.juuri.setVanhempi(null);      
            if(v!=null && o!=null) lisaaAlipuuhun(o, this.juuri);   //jos kaksi lasta lisätään toinen puuhun
            this.koko--;
            }
        return p;
    }
    
    private void lisaaAlipuuhun(Solmu solmu, Solmu juuri){
        solmu.setVanhempi(null);
        int avain = solmu.getAvain();
        lisaaJuureen(solmu, avain, juuri);  //lisätään juuresta alkavaan alipuuhun
    }
    
    private void irrota(Solmu p){
        p.setVanhempi(null);
        p.setOikea(null);
        p.setVasen(null);        
    }
    
    private void nollaaLapsi(Solmu vanhempi, boolean vasen){
        if(vasen)   vanhempi.setVasen(null);
        else    vanhempi.setOikea(null);
    }
    
    private void nollaaJuuri(){
        this.juuri = null;
        this.koko = 0;
    }
    
    /**
     * asetetaan parametrina annettu lapsi solmu parametrina annetun vanhempi solmun 
     * lapseksi oikealle tai vasemmalle riippuen parametrista vasemmalle
     * @param vanhempi Solmu -luokan ilmentymä, jonka lapseksi lapsi solmu lisätään
     * @param lapsi Solmu -luokan ilmentymä, joka lisätään vanhemman lapseksi
     * @param vasemmalle lisätäänkö lapsi vanhemman oikealle vai vasemmalle puolelle
     */
    public void vanhemmanLapseksi(Solmu vanhempi, Solmu lapsi, boolean vasemmalle){
        if(vasemmalle)   vanhempi.setVasen(lapsi);
        else    vanhempi.setOikea(lapsi);
        lapsi.setVanhempi(vanhempi);
    }
    
    @Override
    public Solmu etsi(int avain){
        return etsi(avain, this.juuri);
    }
    
    private Solmu etsi(int avain, Solmu juuri){
        if(juuri==null) return null;        //avainta ei löytynyt alipuusta
        int juurenavain = juuri.getAvain();
        if(avain<juurenavain) return etsi(avain, juuri.getVasen());        //etsitään vasemmasta alipuusta
        else if(avain>juurenavain) return etsi(avain, juuri.getOikea());   //etsitään oikeasta alipuusta
        else return juuri;      //jos etsitty avain on juuren avain palautetaan juuri
    }
    
    /**
     * etsiVanhempi -metodi etsii puusta sopivan kohdan parametrina annetulle avaimelle
     * ja palauttaa solmun, jonka lapseksi kyseisen avaimen omistava solmu tulisi lisätä
     * @param avain kokonaisluku avain, jolle etsitään paikkaa puussa
     * @param juuri Solmu -luokan ilmentymä, josta alkavasta alipuusta kohtaa etsitään
     * @return sopiva vanhempi avaimen omistavalle solmulle
     */
    public Solmu etsiVanhempi(int avain, Solmu juuri){
        if(juuri==null) return null;
        int juurenavain = juuri.getAvain();
        if(juurenavain==avain)  return juuri;
        if(avain<juurenavain && juuri.getVasen()==null) return juuri;          //sopiva vanhempi
        else if(avain>juurenavain && juuri.getOikea()==null)   return juuri;   //sopiva vanhempi
        else if(avain<juurenavain)   return etsiVanhempi(avain, juuri.getVasen());  //vasempaan alipuuhun
        else if(avain>juurenavain)  return etsiVanhempi(avain, juuri.getOikea());   //oikeaan alipuuhun
        else return null;   //ei voida lisätä puuhun
    }
    
    @Override
    public int getKoko(){
        return this.koko;
    }
    
    @Override
    public Puu teeKopio(){
        Puu kopio = new SijaintiPuu2();
        Lista solmut = jalkijarjestys();
        ListaSolmu x = solmut.getEka();
        while(x!=null){
            Solmu kopioitava = x.getPuuSolmu();
            Solmu uusi = new Solmu(kopioitava.getAvain());
            kopio.lisaa(uusi);
            x = x.getSeuraava();
            }
        return kopio;
    }
    
    @Override
    public Lista esijarjestys(){
        Lista luvut = new Lista();
        esijarjHelper(luvut, this.juuri);
        return luvut;
    }
    
    /**
     * jalkijarjestys -metodi palauttaa listan, jossa on puun solmut käänteisessä
     * jälkijärjestyksessä
     * @return Lista -luokan olio, joka sisältää puun solmut käänteisessä jälkijärjestyksessä
     */
    public Lista jalkijarjestys(){
        Lista luvut = new Lista();
        jalkijarjHelper(luvut, this.juuri);
        return luvut;
    }
    
    private void esijarjHelper(Lista luvut, Solmu juuri){
        if(juuri!=null){
            luvut.lisaa(juuri);
            esijarjHelper(luvut, juuri.getVasen());
            esijarjHelper(luvut, juuri.getOikea());
        }
    }
    
    private void jalkijarjHelper(Lista luvut, Solmu juuri){
        if(juuri!=null){
            jalkijarjHelper(luvut, juuri.getVasen());
            jalkijarjHelper(luvut, juuri.getOikea());
            luvut.lisaa(juuri);
        }
    }
}
