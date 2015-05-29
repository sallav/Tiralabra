/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Heuristiikka määrittelee heuristiikan, jolla arvioidaan pelitilanteiden edullisuutta
 * pelaajalle.
 * @author Salla
 */
public interface Heuristiikka {
    
    /**
     * tilanneArvio -metodi tekee arvion siitä kuinka edullinen parametrina annetulla
     * laudalla oleva tilanne on pelaajalle, jonka väri on annettu parametrina.
     * @param l Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param vari sen pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return arvio tilanteen edullisuudesta pelaajalle
     */
    public abstract int tilanneArvio(Lauta l, int vari, int pelaamatta);
    
}
