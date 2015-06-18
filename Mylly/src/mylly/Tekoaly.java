/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Tekoaly -luokka toteuttaa tekoälyn Myllyn pelaamista varten, jota AIPelaaja -luokan oliot
 * voivat hyödyntää tedessään siirtoja pelilaudalle.
 * @author Salla
 */
public class Tekoaly {
    int syvyys;
    char[] suunnat = {'y', 'a', 'v', 'o'};
    Heuristiikka heuristiikka;
    
    /**
     * Luokan Tekoaly konstruktori alustaa algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden
     * @param syvyys kuinka pitkälle pelipuula generoidaan
     */
    public Tekoaly(Heuristiikka h, int syvyys){
        this.heuristiikka = h;
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
    /**
     * getSyvyys palauttaa syvyyden johon pelipuuta generoidaan tekoälyssä
     * @return pelipuun generointisyvyys
     */
    public int getSyvyys(){
        return this.syvyys;
    }
    
    /**
     * parasTyhjista -metodi tekee arvion parhaasta sijainnista pelilaudalla, johon 
     * pelaaja voi sijoittaa uuden merkin.  
     * @param lauta Lauta -luokan ilmentymä, johon siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @param pelaamatta kuinka monta nappia pelaajilla on laittamatta laudalle
     * @return Solmu, jonka avaimena on paras sijainti (0-23) johon merkki kannattaa sijoittaa
     */
    public Solmu parasTyhjista(Lauta lauta, int vari, int pelaamatta){      //kutsutaan kun kaikki napit ei vielä laudalla
        Puu tyhjat = lauta.getTyhjat().teeKopio();     //laudalla olevat tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        Lista parhaat = listaaParhaat(1, juuri, lauta, vari, 0, pelaamatta, 0);
        System.out.println(parhaat==null);
        return jokuHyvaSijainti(parhaat);            //palautetaan paikka mihin siirtäminen johtaisi parhaaseen lopputulokseen pelaajan kannalta
    }
    
    /**
     * parasSiirto -metodi tekee arvion siitä, mitä jo laudalla olevista merkeistä
     * pelaajan kannattaa liikuttaa ja mihin suuntaan. Metodi palauttaa String muotoisen 
     * esityksen, jossa ensimmäisenä on muutettavaa sijaintia edustava luku(0-23) sekä
     * välimerkin jälkeen suuntaa edustava kirjain(v=vasempaan, o=oikeaan, a=alas, y=ylös).
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @return Solmu, jonka avain on sijainti, missä olevaa merkkiä kannattaa siirtää(0-23)
     * ja jonka suunnaksi on asetettu suunta, johon merkkiä kannattaa liikuttaa
     */
    public Solmu parasSiirto(Lauta lauta, int vari){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getMustat().teeKopio();
        if(vari==2) merkit = lauta.getValkoiset().teeKopio();
        Solmu juuri = merkit.getJuuri();
        Lista parhaat = listaaParhaat(2, juuri, lauta, vari, 0, 0, 0);
        return jokuHyvaSijainti(parhaat);
    }
    
    /**
     * parasPoisto -metodi tekee arvion siitä mikä vastustajan merkeistä kannattaisi 
     * poistaa tilanteessa, jossa pelaaja on saanut myllyn.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari myllyn saaneen pelaajan väri, eli pelivuorossa olevan pelaajan väri
     * @param pelaamatta kuinka monta pelinappulaa pelaajilla on siirtämättä laudalle
     * @return Solmu, jonka avain on sijainti (0-23) mistä vastustajan pelimerkki kannattaa poistaa
     */
    public Solmu parasPoisto(Lauta lauta, int vari, int pelaamatta, int edel){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getValkoiset().teeKopio();
        if(vari==2) merkit = lauta.getMustat().teeKopio();
        Solmu juuri = merkit.getJuuri();
        Lista parhaat = listaaParhaat(3, juuri, lauta, vari, 0, pelaamatta, edel);
        return jokuHyvaSijainti(parhaat);              //mikä merkki kannattaa poistaa (ts. merkin sijaintipaikka)
    }
    
    public Solmu parasLento(Lauta lauta, int vari){
        Lista merkit = null;
        if(vari==1) merkit = lauta.getMustat().teeKopio().esijarjestys();
        if(vari==2) merkit = lauta.getValkoiset().teeKopio().esijarjestys();
        return listanParas(merkit);
    }
 
    public void listanParas(Lista merkit){
        parasa = -1000;
        ListaSolmu x = merkit.getEka();
        Solmu paras = x.getPuuSolmu();
        while(x!=null){
            paras = poistaJaKokeile(lauta, x.getPuuSolmu(), vari, paras, parasa);
            parasa = paras.getArvo();
            x = x.getSeuraava();
        }
        return paras;
    }
    
    public Solmu poistaJaKokeile(Lauta lauta, Solmu p, int vari, Solmu paras, int parasarvo){
        int sij = p.getAvain();
        try{
            lauta.poista(sij/8, sij%8, vari);
            Solmu kohde = parasTyhjista(lauta, vari, 1);
            lauta.laitaMerkki(sij/8, sij%8, vari);
            p.setKohde(kohde);
            p.setArvo(kohde.getArvo());
            if(kohde.getArvo()>parasarvo)   return p;
        }catch(Exception e){
            return paras;
        }
        return paras;
    }
    
    /**
     * jokuHyvaSijainti -metodi arpoo yhden solmun parametrina annetusta listasta
     * @param sijainnit Lista johon on talletettu parhaat puusolmut
     * @return joku listan solmuista
     */
    public Solmu jokuHyvaSijainti(Lista sijainnit){
        if(sijainnit.getKoko()<2)   return sijainnit.getSolmu(0);
        Random arpa = new Random();
        int monesko = arpa.nextInt(sijainnit.getKoko());
        return sijainnit.getSolmu(monesko);
    }
    
    /**
     * listaaParhaat -metodi tekee listan parhaista sijainneista, joihin 
     * parametrina annetun värisen nappulan voisi sijoittaa. Metodi kokeilee 
     * parametrina annettua toimenpidettä, jokaiseen sijaintiin, joka on parametrina 
     * annetusta juuresta alkavassa puussa. Sijainnit käydään läpi jälkijärjestyksessä.
     * @param toimenpide mikä toimenpide tehdään puusolmuja läpikäytäessa (1=uusi nappi 
     * laudalle, 2=laudalla olevien nappuloiden siirto, 3=vastustajan nappulan poisto)
     * @param juuri sen puun juuri, joka halutaan käydä läpi
     * @param lauta pelilauta, jossa siirtoja tehdään
     * @param vari sen pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka syvälle pelipuussa on edetty (puun taso)
     * @param pelaamatta kuinka monta nappia on vielä siirtämättä laudalle
     * @param edel edellinen siirto (sijainti 0-23)
     * @return lista solmuista, jotka edustavat parhaita sijainteja laudalla
     */
    public Lista listaaParhaat(int toimenpide, Solmu juuri, Lauta lauta, int vari, int k, int pelaamatta, int edel){
        Lista parhaat;
        parhaat = new Lista();
        if(juuri!=null){            //saavutettu lehdet           
            Lista v = listaaParhaat(toimenpide, juuri.getVasen(), lauta, vari, k, pelaamatta, edel);  //vasen puoli puuta
            Lista o = listaaParhaat(toimenpide, juuri.getOikea(), lauta, vari, k, pelaamatta, edel);  //oikea puoli puuta
            parhaat = parempiLista(v, o);   //kumpi listoista on parempi
            int uusiarvo = kokeile(toimenpide, lauta, juuri, vari, k, pelaamatta, true, 0);   //mikä tulos juuren solmulle saadaan
            parhaat = paivitaLista(parhaat, juuri, uusiarvo);   //onko juuri arvoltaan parempi tai yhtä hyvä kuin siihen mennesssä parhaat
        }
        return parhaat;
    }
        
    /**
     * paivitaLista -metodi lisää parametrina annetun solmun parhaiden sijaintien listaan
     * jos solmun arvo on yhtä hyvä kuin listan muiden solmujen arvo. Jos solmun arvo on
     * parempi kuin listan solmujen arvot, palauttaa metodi uuden listan, jonka ainoa solmu
     * on tämä kyseinen solmu, muuten palautetaan parametrina annettu lista muuttumattomana.
     * @param parhaat lista parhaista sijainneista
     * @param uusi puusolmu jota verrataan listan solmuihin
     * @param uusiarvo verrattavan puusolmun sen hetkinen arvo
     * @return lista, jossa on tähän mennessä parhaat sijainnit
     */
    public Lista paivitaLista(Lista parhaat, Solmu uusi, int uusiarvo){
        int listanarvo;
        uusiarvo = uusi.getArvo();
        if(parhaat==null) listanarvo = -1000;    //jos Lista olio on null sen arvo on huonoin mahdollinen
        else listanarvo = parhaat.getArvo();     //muuten listan arvo on listan solmujen arvo
//        if(uusiarvo>listanarvo) return new Lista(uusi); //jos uusi parempi, hylätään vanha lista
        if(uusiarvo==listanarvo)    parhaat.lisaa(uusi);    //jos uusi samanarvoinen, lisätään se listaan
        else if(uusiarvo>listanarvo) parhaat = new Lista(uusi);
        return parhaat;                                     //palautetaan päivitetty lista
    }

    /**
     * parempiLista -metodi vertaa kahta listaa ja palauttaa niistä arvoltaan paremman.
     * Jos listat ovat samanarvoisia, ne yhdistetään ja palautetaan yhdistetty lista.
     * @param vasen toinen verrattava lista
     * @param oikea toinen verrattava lista
     * @return parempi lista tai jos listat ovat samanarvoisia yhdistetty lista
     */
    public Lista parempiLista(Lista vasen, Lista oikea){
        int vasena = -1000;
        int oikeaa = -1000;
        if(vasen==null && oikea==null) return null;
        if(vasen!=null) vasena = vasen.getArvo();    //vasemman listan arvo
        if(oikea!=null) oikeaa = oikea.getArvo();    //oikean listan arvo
        if(vasen==null || oikeaa>vasena)    return oikea;
        else if(vasen!=null && oikea!=null && vasena==oikeaa)  vasen.lisaa(oikea);     //jos saman arvoisia yhdistetään listat
        return vasen;   //palautetaan vasen 
    }
    
    /**
     * kayLapiSijainnit -metodi käy läpi parametrina annetusta juuresta alkavan puun, 
     * johon on talletettu laudalla olevia sijainteja, ja suorittaa niihin parametrina 
     * annetun toimenpiteen. Sijainnit käydään läpi jälkijärjestyksessä ja metodi palauttaa
     * arvona alipuun parhaan solmun arvon. 
     * @param toimenpide 1=nappula laudalle, 2=siirrä sijainnissa olevaa nappulaa, 3=vastustajan
     * nappulan poisto
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param juuri sen puun juuri, joka halutaan käydä läpi
     * @param vari sen pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka syvälle pelipuussa ollaan edetty (puun taso)
     * @param pelaamatta kuinka monta nappulaa on siirtämättä laudalle
     * @param omavuoro onko arvioitavan pelaajan vai vastustajan vuoro
     * @param edel edellinen siirto
     * @return paras mahdollinen arvo, joka saadaan puussa olevista sijainneista
     */
    public int kayLapiSijainnit(int toimenpide, Lauta lauta, Solmu juuri, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        if(juuri!=null){
            int v = kayLapiSijainnit(toimenpide, lauta, juuri.getVasen(), vari, k, pelaamatta, omavuoro, edel);
            int o = kayLapiSijainnit(toimenpide, lauta, juuri.getOikea(), vari, k, pelaamatta, omavuoro, edel);
            int paras = parempiArvo(v, o, omavuoro);
            int uusiarvo = kokeile(toimenpide, lauta, juuri, vari, k, pelaamatta, omavuoro, edel);
            return parempiArvo(paras, uusiarvo, omavuoro);
        }
        else if(omavuoro)    return -1000;
        else    return 1000;
    }
        
    /**
     * parempiArvo -metodi palauttaa kahdesta arvosta paremman suhteutettuna siihen 
     * onko pelaajan oma vuoro vai vastustajan vuoro, ts. paremman arvon, jos on pelaajan
     * oma vuoro ja huonomman arvon, jos on vastapelaajan vuoro
     * @param ensimmainen toinen verrattava arvo
     * @param toinen toinen verrattava arvo
     * @param omavuoro onko pelaajan vai vastapelaajan vuoro
     * @return parempi arvo, jos pelaajan oma vuoro, huonompi arvo, jos vastapelaajan vuoro
     */
    public int parempiArvo(int ensimmainen, int toinen, boolean omavuoro){
        if(omavuoro)    return Math.max(ensimmainen, toinen);
        else    return Math.min(ensimmainen, toinen); 
    }
    
    /**
     * kokeile -metodi suorittaa parametrina annetun toimenpiteen parametrina annetun
     * solmun edustamaan sijaintiin ja palauttaa siitä seuraavan arvioidun tuloksen, 
     * asetettuaan sen ensin solmun arvoksi.
     * @param toimenpide 1=uusi nappula laudalle, 2=sijainnissa olevan napin siirto, 
     * 3=vastustajan napin poisto
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param solmu solmu, jonka avaimena on kokeiltava sijainti
     * @param vari sen pelaajan väri, joka on vuorossa
     * @param k kuinka syvälle pelipuuta on jo gneroitu (puun taso)
     * @param pelaamatta kuinka monta nappulaa on siirtämättä laudalle
     * @param omavuoro onko pelaajan vai vastapelaajan vuoro
     * @param edel edellinen siirto
     * @return tulos joka kokeillusta siirrosta voidaan saavuttaa
     */
    public int kokeile(int toimenpide, Lauta lauta, Solmu solmu, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        int sij = solmu.getAvain();     //sijainti
        int tulos = 0;
        switch(toimenpide){
            case 1: tulos = kokeileSijainti(lauta, sij/8, sij%8, vari, k, omavuoro, pelaamatta);
                    break;
            case 2: tulos = kokeileSiirto(lauta, solmu, vari, k, omavuoro);
                    break;
            case 3: tulos = kokeilePoistaa(lauta, sij/8, sij%8, vari, k, pelaamatta, omavuoro, edel);
                    break;
            case 4: tulos = kokeileLentaa(lauta, sij/8, sij%8, vari, k, omavuoro);
        }
        solmu.setArvo(tulos);   //päivitetään parametrina annetun solmun arvo tuloksen mukaan
        return tulos;           //palautetaan parametrina annettu solmu
    }

    /**
     * vastaSuunta -metodi palauttaa parametrina annettua suuntaa vastakkaisen suunnan 
     * char merkkinä. 
     * @param suunta a=alas, y=ylös, v=vasemmalle, o=oikealle
     * @return suunnan vastakohta ('a'->'y', 'y'->'a', 'o'->'v', 'v'->'o')
     */
    public char vastaSuunta(char suunta){
        switch(suunta){
            case 'a': return 'y';
            case 'y': return 'a';
            case 'v': return 'o';
            case 'o': return 'v';
            default: return 'x';
        }
    }
    
    /**
     * omaSiirtoLaudalle -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee 
     * kaikkia mahdollisia laudalle tehtäviä siirtoja omalla värillä sekä minkälaisia 
     * tilanteita niistä seuraa. Metodi palauttaa arvion siitä, kuinka edullinen sen 
     * hetkinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan olio, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return paras mahdollinen tulos johon pelissä päädytään arvioituna asteikolla (-1)-(+1) 
     * (tai joku muu asteikko...)
     */
    public int siirtoLaudalle(Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, pelaamatta, edel);     //kun haluttu syvyys saavutettu palautetaan arvio tilanteesta        
        if(pelaamatta==0)   return siirtoLaudalla(lauta, vari, k, omavuoro, edel);      //jos kaikki nappulat on jo käytetty kutsutaan toista metodia
//        lauta = new Lauta(lauta.getLauta(), lauta.syoty(1), lauta.syoty(2));
        Puu tyhjat = lauta.getTyhjat().teeKopio();     //tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        return kayLapiSijainnit(1, lauta, juuri, vari, k, pelaamatta, omavuoro, edel); //palautetaan paras mahdollinen lopputulos joka voi syntyä alkuperäisestä tilanteesta
    }
    
    /**
     * omaSiirtoLaudalla -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia
     * mahdollisia laudalla tehtävissä olevia siirtoja omalla värillä sekä minkälaisia
     * tilanteita niistä seuraa, kun kaikki pelinappulat on jo siirretty laudalle. Metodi 
     * palauttaa arvion siitä, kuinka edullinen alkuperäinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari  pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta tilanteesta, johon pelissä päädytään
     */
    public int siirtoLaudalla(Lauta lauta, int vari, int k, boolean omavuoro, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, 0, edel);   //arvio tilanteesta kun haluttu syvyys saavutettu
        Puu merkit = null;
        int toimenpide = 2;     //kokeile siirtoa
//        lauta = new Lauta(lauta.getLauta(), lauta.syoty(1), lauta.syoty(2));
        if(vari==1) merkit = lauta.getMustat().teeKopio();
        else if(vari==2) merkit = lauta.getValkoiset().teeKopio();
        Solmu juuri = merkit.getJuuri();                
        if(merkit.getKoko()<4)  toimenpide = 4; //lennetään
        return kayLapiSijainnit(toimenpide, lauta, juuri, vari, k, 0, omavuoro, edel); 
    }
    
    /**
     * poisto -metodi kokeilee poistaa jokaista parametrina annetun värisen pelaajan
     * vastapuolen nappuloista ja palauttaa parhaan mahdollisen tuloksen tai huonoimman
     * mahdollisen tuloksen riippuen onko pelaajan vai vastapelaajan vuoro.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri, joka on vuorossa
     * @param k kuinka syvälle pelipuuta on jo generoitu (puun taso)
     * @param pelaamatta kuinka monta nappulaa on vielä mahdollisesti siirtämättä laudalle
     * @param omavuoro true jos pelaajan, false jos vastapelaajan vuoro
     * @param edel edellinen siirto
     * @return paras mahdollinen tulos, jos on pelaajan vuoro tai huonoin mahdollinen tulos,
     * jos on vastapelaajan vuoro
     */
    public int poisto(Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        Puu merkit = null;
//        lauta = new Lauta(lauta.getLauta(), lauta.syoty(1), lauta.syoty(2));
        if(vari==1) merkit = lauta.getValkoiset().teeKopio();
        if(vari==2) merkit = lauta.getMustat().teeKopio();
        Solmu juuri = merkit.getJuuri();
        return kayLapiSijainnit(3, lauta, juuri, vari, k, pelaamatta, omavuoro, edel);
    }
    
    /**
     * kokeileSijainti -metodi asettaa parametrina annetun värisen merkin parametrina annettuun 
     * sijaintiin. Jos tästä siirrosta syntyy mylly tekoäly poistaa vastapuolen värisen merkin
     * laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa pelipuun generointia jatketaan 
     * seuraavana vuorossa olevan pelaajan siirrolla. Kun siirtojen tulos on saatu selville
     * poistetaan laudalta metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param j rivi jolla kokeiltava paikka sijaitsee
     * @param i paikka rivillä eli indeksi, johon siirtoa kokeillaan
     * @param vari pelaajan väri, jonka siirtoa kokeillaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro true jos kyseessä on pelaaja, jonka mahdollisuuksia arvioidaan, false,
     * jos kyseessä on vastapuolen siirto
     * @param pelaamatta kuinka monta nappulaa pelaajilla on vielä siirtämättä laudalle
     * @return arvio siitä kuinka hyvä on tilanne, johon siirrolla päädytään
     */
    public int kokeileSijainti(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta){
        int tulos = 1000;
        try{
            if(j==2 && i==5){
                System.out.println("jotain");
            }    
            lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen halutun värinen merkki
                    for(int m=0; m<3; m++){
                        for(int l=0; l<8; l++){
                            System.out.print(lauta.getLauta()[m][l]);
                        }}
                    System.out.println("");
            if(lauta.mylly(vari, (8*j)+i))  tulos = poisto(lauta, vari, k, pelaamatta-1, omavuoro, (j*8)+i); //jos syntyi mylly voidaan poistaa yksi vastapuolen merkeistä
            else tulos = siirtoLaudalle(lauta, 3-vari, k+1, pelaamatta-1, !omavuoro, (j*8)+i);  //jos ei myllyä, on vastapuolen vuoro
            lauta.poista(j, i, vari);                       //perutaan tehty siirto
                    for(int l=0; l<24; l++){
                        System.out.print(lauta.getLauta()[l/8][l%8]);
                    }
                    System.out.println("");
       }catch(Exception e){                    //jos paikka on epäsopiva, esim. varattu, heitetään poikkeus merkkiä laitettaessa
            if(omavuoro)tulos = -1000;            //"tätä paikkaa ei haluta käyttää"
        }
        return tulos;                           //kuinka hyvään lopputulokseen kys. sijainti johtaisi
    }
        
    /**
     * kokeileSiirtoaOma -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa 
     * merkkiä kaikkiin mahdollisiin suuntiin ja palauttaa arvion parhaasta mahdollisesta
     * tilanteesta, johon jollain siirrolla voidaan päästä. Tällä metodilla on tarkoitus 
     * kokeilla siirtää sen pelaajan nappuloita, jonka mahdollisuuksia arvioidaan.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappia halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappulaa kokeillaan siirtää
     * @param vari pelaajan väri, jonka mahdollisuuksia yritetään arvioida
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta lopputuloksesta, johon nappulaa siirrettäessä
     * voidaan päätyä
     */
    public int kokeileSiirto(Lauta lauta, Solmu solmu, int vari, int k, boolean omavuoro){
            int paras = -1000;
            if(!omavuoro)   paras = 1000;
            int sijainti = solmu.getAvain();
                for(int l=0; l<4; l++){ // kokeillaan siirtoa kaikkiin 4 eri suuntaan
                   int suunnantulos = kokeileSuunta(lauta, sijainti/8, sijainti%8, suunnat[l], vari, k, omavuoro);
                    if(parempiKuinEnnen(suunnantulos, paras, omavuoro)){
                        paras = suunnantulos;   //jos tulos parempi kuin aikaisemmista siirroista, laitetaan se muistiin
                        solmu.setArvo(suunnantulos);
                        solmu.setSuunta(suunnat[l]);    //myös suunta muistiin
                    }
                }
            return paras;   //palautetaan paras mahdollinen lopputulos ja suunta mistä se syntyi
        }
        
    /**
     * parempiKuinEnnen -metodi palauttaa true, jos parametrina annettu tulos
     * on parempi tai huonompi kuin parametrina annettu, siihen mennessä paras tulos, 
     * riippuen siitä onko pelaajan vai vastapelaajan vuoro. Muuten metodi palauttaa false.
     * @param tulos tulos, jota verrataan aikaisempiin
     * @param paras tähän mennessä paras tulos
     * @param omavuoro true jos pelaajan oma vuoro, false jos on vastapelaajan vuoro
     * @return true jos on oma vuoro ja tulos on parempi kuin edellinen tai vastapuolen vuoro
     * ja tulos huonompi kuin edellinen, muuten false
     */
    public boolean parempiKuinEnnen(int tulos, int paras, boolean omavuoro){
        if(omavuoro){
            if(tulos>paras) return true;
            else return false;
        }else{
            if(tulos<paras) return true;
            else return false;
        }
    }

    /**
     * kokeileSuunta -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa
     * nappulaa parametrina annettuun suuntaan. Jos siirrosta seuraa mylly tekoäly poistaa 
     * vastapuolen värisen merkin laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa 
     * pelipuun generointia jatketaan seuraavana vuorossa olevan pelaajan siirrolla. Kun 
     * siirrosta seuraava tulos on saatu selville perutaan laudalla metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappia halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappia halutaan siirtää
     * @param suunta suunta johon laudalla olevaa nappia halutaan siirtää
     * @param vari minkä väristä merkkiä kokeillaan siirtää
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
     * @return arvio tilanteesta johon siirrolla päädytään
     */
    public int kokeileSuunta(Lauta lauta, int j, int i, char suunta, int vari, int k, boolean omavuoro){
            int tulos = 1000; 
            try{
                int uusip = lauta.siirra(j, i, suunta);//kokeillaan siirtää merkkiä annettuun suuntaan
                    if(uusip==(j*8)+i)  throw new Exception();  //ei voitu siirtää nappia mihinkään
                    else if(lauta.mylly(vari, uusip))    tulos = poisto(lauta, vari, k, 0, omavuoro, (j*8)+i);    //jos syntyi mylly saadaan poistaa vastapuolen merkki
                    else tulos = siirtoLaudalle(lauta, 3-vari, k+1, 0, !omavuoro, (j*8)+i);  //jos ei myllyä on toisen pelaajan vuoro
                lauta.siirra(uusip/8, uusip%8, vastaSuunta(suunta));     //perutaan tehty siirto
                }catch(Exception e){                //jos sijainti on epäsopiva, esim. varattu
                    if(omavuoro)    tulos = -1000;    //"tätä siirtoa ei haluta tehdä"
                    }
            return tulos;                           //lopputulos joka siirrosta seuraisi
        }
        
    /**
     * kokeilePoistaa -metodi kokeilee poistaa parametrina annetussa sijainnissa olevan merkin
     * ja tekee arvion siitä syntyvästä lopputuloksesta generoimalla pelipuuta eteenpäin.
     * Kun arvio on tehty metodi palauttaa poistetun napin paikalleen.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param j rivi, jolla oleva nappi halutaan poistaa
     * @param i paikka rivillä eli indeksi, josta nappi poistetaan
     * @param vari pelaajan väri, joka saa tehdä poiston
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @param omavuoro true, jos vuorossa on pelaaja jonka mahdollisuuksia arvioidaan, false
     * jos on vastapuolen pelivuoro
     * @return arvio tilanteesta johon poistolla päädytään
     */
    public int kokeilePoistaa(Lauta lauta, int j, int i, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
            int tulos = 1000;
            try{
                lauta.syo(j, i, 3-vari);         //kokeillaan poistaa vastapuolen merkki
                tulos = siirtoLaudalle(lauta, 3-vari, k+1, pelaamatta, !omavuoro, edel);  //toisen pelaajan vuoro
                lauta.peruSyonti(j, i, 3-vari);         //perutaan tehty siirto    
            }catch(Exception e){                        //jos yritetään poistaa jotain muuta kuin pitäisi
                if(omavuoro)tulos = -1000;              //"tätä ei haluta poistaa"
            }
            return tulos;       //lopputulos joka poistosta seuraisi
        }
    
    public int kokeileLentaa(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro){
        int tulos = 1000;
        try{
            lauta.poista(j, i, vari);
            tulos = siirtoLaudalle(lauta, vari, k, 1, omavuoro, (j*8)+i);
            lauta.laitaMerkki(j, i, vari);
        }catch(Exception e){
            if(omavuoro) tulos = -1000;
        }
        return tulos;
    }
    
    /**
     * arvioiTilanne -metodi palauttaa arvion siitä, kuinka hyvä parametrina
     * annetulla laudalla oleva pelitilanne on pelaajan kannalta, jonka väri on
     * annettu parametrina.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param vari sen pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan 
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return kokonaislukumerkkinen arvo, joka kuvastaa sitä kuinka hyvä pelitilanne 
     * on annetun värisen pelaajan kannalta
     */
    public int arvioiTilanne(Lauta lauta, int vari, int pelaamatta, int edel){
        return this.heuristiikka.tilanneArvio(lauta, vari, pelaamatta, edel);
    }
    
}
