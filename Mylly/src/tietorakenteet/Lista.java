/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Lista -luokka toteuttaa yhteen suuntaan linkitetyn listan, joka koostuu 
 * ListaSolmu olioista
 * @author Salla
 */
public class Lista {
    ListaSolmu eka;
    ListaSolmu vika;
    int koko;
    
    /**
     * Lista -luokan konstruktori asettaa listan ensimmäiseksi ja viimeiseksi
     * olioksi nullin sekä kooksi 0
     */
    public Lista(){
        this.eka = null;
        this.vika = null;
        this.koko = 0;
    }
    
    /**
     * Lista luokan konstruktori asettaa parametrina annetun Solmu -olion sisältävän
     * ListaSolmu -olion listan ensimmäiseksi ja viimeiseksi alkioksi ja asettaa
     * listan kooksi 1
     * @param puusolmu Solmu -luokan ilmentymä, joka lisätään ensimmäiseen alkioon  
     */
    public Lista(Solmu puusolmu){
        this.eka = new ListaSolmu(puusolmu);
        this.vika = this.eka;               //ensimmäinen on myös viimeinen
        this.koko = 1;
    }
    
    /**
     * tyhja -metodi kertoo onko lista tyhjä
     * @return true jos lista on tyhjä, false jos listassa on solmuja
     */
    public boolean tyhja(){
        if(this.vika==null && this.koko==0) return true;
        return false;
    }
    
    /**
     * lisaa -metodi luo uuden ListaSolmu -olion, joka sisältää parametrina annetun
     * Solmu -luokan ilmentymän muuttujanaan sekä lisää tämän listan ensimmäiseksi alkioksi.
     * ListaSolmu -olioon lisätään linkki edelliseen listan ensimmäisenä olleeseen alkioon 
     * tai jos lista on vielä tyhjä, merkitään uusi listasolmu myös listan viimeiseksi alkioksi.
     * @param puusolmu Listan alkioon lisättävä Solmu -luokan olio
     */
    public void lisaa(Solmu puusolmu){
        ListaSolmu uusi;        //uusi listan alkio
        if(eka==null){
            uusi = new ListaSolmu(puusolmu);    
            this.vika = uusi;       //ensimmäinen lisättävä on listan viimeinen alkio
        }else{
            uusi = new ListaSolmu(puusolmu, this.eka);      //edellinen eka solmun seuraajaksi
   //         System.out.println("Lisatty: " + puusolmu.getAvain());
   //         System.out.println(uusi.getAvain()==puusolmu.getAvain());
   //         System.out.println(uusi.getPuuSolmu().getAvain() + ", seur: " + uusi.getSeuraava().getAvain());
        }
        this.eka = uusi;            //lisätty alkio on listan ensimmäinen
        this.koko++;                //listan koko kasvaa yhdellä 
    }
    
    /**
     * lisaa -metodi lisää listaan uuden ListaSolmu -olion, jonka avain on parametrina annettu 
     * kokonaisluku. 
     * @param avain kokonaisluku, joka lisätään listasolmun avaimeksi
     */
    public void lisaa(int avain){
        ListaSolmu uusi;
        if(eka==null){
            uusi = new ListaSolmu(avain);
            this.vika = uusi;
        }else{
            uusi = new ListaSolmu(avain, this.eka);
        }
        this.eka = uusi;
        this.koko++;
    }
    
    /**
     * lisaa -metodi lisää tämän listan loppuun parametrina annetun toisen listan sisältämät 
     * alkiot. Listan viimeiseen alkioon lisätään linkki toisen listan ensimmäiseen alkioon 
     * ja merkataan toisen listan viimeinen alkio tämän listan viimeiseksi alkioksi.
     * @param toinen Lista -luokan ilmentymä, joka lisätään tähän listaan
     */
    public void lisaa(Lista toinen){
            if(this.eka==null)  this.eka = toinen.getEka(); //jos lista johon lisätään on tyhjä
            if(this.vika!=null) this.vika.setSeuraava(toinen.getEka()); //vikan seuraajaksi toisen eka
            this.vika = toinen.getVika();           //uusi vika on toisen vika
            this.koko += toinen.getKoko();          //kokoon lisätään toisen listan koko
    }
    
    /**
     * getEka -metodi palauttaa listan ensimmäisen alkion
     * @return listan ensimmäinen eli viimeksi lisätty ListaSolmu -olio
     */
    public ListaSolmu getEka(){
        return this.eka;
    }
    
    /**
     * getVika -metodi palauttaa listan viimeisen alkion
     * @return listan viimeinen eli ensimmäisenä lisätty ListaSolmu -olio
     */
    public ListaSolmu getVika(){
        return this.vika;
    }
    
    /**
     * getSolmu -metodi palauttaa listasta Solmu -luokan ilmentymän, joka löytyy 
     * ListaSolmu -oliosta, jonka järjestysnumero listassa on parametrina annetun
     * luvun mukainen.
     * @param monesko kuinka monennen alkion sisältö halutaan palauttaa
     * @return Solmu -luokan ilmentymä, joka on haetussa ListaSolmu -oliossa
     */
    public Solmu getSolmu(int monesko){
        ListaSolmu x = this.eka;        //aloitetaan etsiminen listan ensimmäisestä alkiosta
        int i = 0;
        while(x!=null && i<monesko){               
            x = x.getSeuraava();        //siirrytään listassa eteenpäin     
            i++;                  //niin kauan kunnes saavutetaan haluttu järjestysnumero
        }
        if(x==null) return null;
        return x.getPuuSolmu();         //palautetaan listan alkion sisältämä Solmu -olio
    }
    
    /**
     * getArvo -metodi palauttaa listan ensimmäisen alkion sisältämän Solmu -olion arvon
     * @return ensimmäisen alkion sisältämän solmun arvo
     */
    public int getArvo(){
        if(this.vika==null)  return -1000;   //jos lista on tyhjä
        return this.vika.getArvo();
    }
    
    /**
     * getKoko -metodi palauttaa listan sen hetkisen koon
     * @return listan koko
     */
    public int getKoko(){
        return this.koko;
    }
}
