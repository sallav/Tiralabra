/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Puu -luokka tarjoaa toteutuksen binäärihakupuulle, johon voi tallettaa pelilaudan
 * paikkoja vastaavia sijainteja sisältäviä solmuja
 * @author Salla
 */
public class Puu {
    Solmu juuri;
    
    /**
     * Puu -luokan konstruktori rakentaa binäärihakupuun, joka sisältää solmuissa 
     * avaiminaan parametrina annetun taulukon luvut ja asettaa juureksi taulukon
     * ensimmäisen luvun
     * @param luvut taulukko, joka sisältää puun solmuihin laitettavat luvut
     */
    public Puu(int[] luvut){
        this.juuri = new Solmu(luvut[0]);
        for(int i=1; i<luvut.length; i++){
            lisaaSolmu(luvut[i]);
        }
    }
    
    /**
     * Puu -luokan konstruktori alustaa puun juureksi parametrina saamansa solmun
     * @param juuri Solmu luokan ilmentymä
     */
    public Puu(Solmu juuri){
        this.juuri = juuri;
    }
    
    /**
     * Puu luokan konstruktori asettaa puun juureksi nullin
     */
    public Puu(){
        this.juuri = null;
    }
    
    /**
     * getJuuri -metodi palauttaa puun juuren
     * @return Solmu -luokan olio, joka on puun juurena
     */
    public Solmu getJuuri(){
        return this.juuri;
    }
    
    /**
     * lisaaSolmu -metodi luo ja lisää puuhun uuden Solmu -luokan olion, jonka
     * avaimena on parametrina saatu luku, jos tätä lukua ei ole puussa ennestään
     * ja palauttaa true. Jos parametrina annettu luku löytyy puun solmuista, ei
     * sitä lisätä ja metodi palauttaa false.
     * @param avain solmuun avaimeksi lisättävä kokonaisluku
     * @return true jos solmu lisättiin puuhun, false jos solmua ei lisätty
     */
    public boolean lisaaSolmu(int avain){
        Solmu uusi = new Solmu(avain);
        return lisaa(uusi, this.juuri);
    }
    
    /**
     * Lisää parametrina annetun solmun parametrina annetusta juuresta alkavaan alipuuhun
     * ja palauttaa true jos lisäys onnistui. Jos parametrina annetun solmun avainta vastaava
     * kokonaisluku löytyy jo alipuusta, ei solmua lisätä ja metodi palauttaa false.
     * @param lisa puuhun lisättävä Solmu -luokan olio
     * @param juuri Solmu -luokan olio, josta alkavaan alipuuhun toinen solmu lisätään
     * @return true jos lisäys onnistui, false jos avain löytyy jo puun solmuista ja siten
     * lisäystä ei tehdä
     */
    public boolean lisaa(Solmu lisa, Solmu juuri){
        Solmu vanhempi = sopivaVanhempi(lisa, juuri);
        if(vanhempi==null)  this.juuri = lisa;      //jos puu on tyhjä laitetaan juureksi
        else{
            if(lisa.getAvain()>vanhempi.getAvain())         vanhempi.setOikea(lisa);
            else if(lisa.getAvain()<vanhempi.getAvain())    vanhempi.setVasen(lisa);
            else    return false;
            lisa.setVanhempi(vanhempi);
        }
        return true;
    }
    
    /**
     * poista -metodi poistaa puusta parametrina annetun avaimen sisältävän Solmu -luokan
     * olion ja asettaa sen lapset sen vanhemmasta alkavaan alipuuhun sille puolelle, josta
     * solmu eli lapsi poistettiin. Metodi palauttaa poistetun Solmu -luokan olion.
     * @param avain poistettavan Solmu -luokan olion avain
     * @return  poistettu Solmu -luokan olio
     */
    public Solmu poista(int avain){
        Solmu p = etsi(avain);
        if(p!=null){
            Solmu vas = p.getVasen();
            Solmu oik = p.getOikea();
            Solmu vanh = p.getVanhempi();
            if(vanh!=null && vanh.getVasen()==p) vanhemmanLapseksi(vanh, vas, oik, true);
            else if(vanh!=null) vanhemmanLapseksi(vanh, vas, oik, false);
        }
        return p;
    }
    
    /**
     * vanhemmanLapseksi -metodi asettaa parametrina annetun isov solmun vasemmaksi lapseksi 
     * parametrina annetun vasen solmun, jos parametri vasenlapsi on true ja lisää oikea solmun
     * vasen solmusta alkavaan alipuuhun. Jos parametri vasenlapsi on false metodiasettaa 
     * isov solmun oikeaksi lapseksi parametrina annetun oikea solmun ja lisää vasen solmun 
     * siitä alkavaan alipuuhun. 
     * @param isov solmu josta alkavaan alipuuhun vasen ja oikea lisätään
     * @param vasen Solmu -luokan ilmentymä
     * @param oikea Solmu -luokan ilmentymä
     * @param vasenlapsi true jos poistettu solmu oli isov solmun vasen lapsi, muuten false
     */
    public void vanhemmanLapseksi(Solmu isov, Solmu vasen, Solmu oikea, boolean vasenlapsi){
        if(vasenlapsi){ //jos poistettu lapsi oli vanhempansa vasen lapsi
            isov.setVasen(vasen);   //vasen isovanhemman vasemaksi lapseksi
            lisaa(oikea, vasen);    //lisätään oikea vasemmasta alkavaan alipuuhun
        }else{
            isov.setOikea(oikea);   //oikea isovanhemman oikeaksi lapseksi
            lisaa(vasen, oikea);    //lisätään vasen oikeasta alkavaan alipuuhun
        }
    }
    
    /**
     * sopivaVanhempi -metodi etsii binäärihakupuusta kohdan, johon parametrina annettu 
     * uusi solmu voidaan lisätä
     * @param uusi Solmu -luokan ilmentymä, joka halutaan lisätä puuhun 
     * @param juuri Solmu -luokan ilmentymä, josta alkavaan alipuuhun uusi solmu
     * halutaan lisätä
     * @return Solmu -luokan ilmentymä, jonka lapseksi uuden solmun voi lisätä 
     */
    public Solmu sopivaVanhempi(Solmu uusi, Solmu juuri){
        if(uusi==null)  return null;
        int avain = uusi.getAvain();    //uuden solmun avain
        Solmu v = juuri;
        Solmu p = null;
        while(v != null){       //kunnes saavutetaan sopiva kohta
            p = v;              //seuraavan v:n vanhempi
            if(avain<v.getAvain()) v = v.getVasen();            //vasempaan alipuuhun
            else    v = v.getOikea();                           //oikeaan alipuuhun
        }
        return p;       //palautetaan lehtisolmu
    }
    
    /**
     * etsi -metodi etsii ja palauttaa puusta Solmu -luokan ilmentymän, jonka avain
     * on parametrina annettu kokonaisluku.
     * @param avain etsittävä avain 
     * @return Solmu -luokan ilmentymä, jonka avain on etsittävä avain tai null, jos 
     * avainta ei löydy puusta
     */
    public Solmu etsi(int avain){
        Solmu x = this.juuri;
        while(x!=null){
            if(avain>x.getAvain())  x = x.getOikea();   //etsitään oikeasta alipuusta
            if(avain<x.getAvain())  x = x.getVasen();   //etsitään vasemmasta alipuusta
            else return x;  //jos solmun avain on etsitty avain
        }
        return null;
    }
}
