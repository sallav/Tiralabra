/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Pelaaja -rajapinta määrittelee pelaajan toiminnallisuudet pelissä
 * @author Salla
 */
public interface Pelaaja {

    /**
     * vari -metodi palauttaa pelaajan pelimerkkien väriä vastaavan luvun
     * @return pelaajan pelimerkkien väri (1=musta, 2=valkoinen)
     */
    public abstract int vari();

    /**
     * siirraLaudalle -metodi siirtää uuden pelimerkin laudalle
     * @param l Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param nappejajalj kuinka monta nappia pelaajalla on vielä siirtämättä laudalle
     * @return sijainti, johon siirto tehtiin (0-23)
     */
    public abstract int siirraLaudalle(Lauta l, int nappejajalj);

    /**
     * siirraLaudalla -metodi siirtää jotain jo laudalla olevista pelimerkeistä
     * @param l Lauta -luokan ilmentymä, jossa peliä pelataan
     * @return sijainti, johon siirto tehtiin (0-23)
     */
    public abstract int siirraLaudalla(Lauta l);

    /**
     * lenna -metodi tekee siirron mihin tahansa tyhjään sijaintiin, kun pelaajalla
     * on jäljellä enää kolme pelimerkkiä.
     * @param l Lauta -luokan ilmentymä, jossa peliä pelataan
     * @return sijainti, johon siirto tehtiin (0-23)
     */
    public abstract int lenna(Lauta l);

    /**
     * laudalla -metodi palauttaa pelaajan pelilaudalla olevien merkkien määrän 
     * @param l Lauta -luokan ilmentymä, jossa peliä pelataan
     * @return kuinka monta merkkiä pelaajalla on laudalla
     */
    public abstract int laudalla(Lauta l);

    /**
     * poistaLaudalta -metodi poistaa jonkun vastapuolen pelimerkeistä
     * @param l Lauta -luokan ilmentymä, jossa peliä pelataan
     * @return sijainti, josta pelimerkki poistettiin
     */
    public abstract int poistaLaudalta(Lauta l);
}
