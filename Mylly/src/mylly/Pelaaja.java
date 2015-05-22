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
public interface Pelaaja {
    public abstract int vari();
    public abstract int siirraLaudalle(Lauta l);
    public abstract int siirraLaudalla(Lauta l);
    public abstract int lenna(Lauta l);
    public abstract int laudalla(Lauta l);
    public abstract int poistaLaudalta(Lauta l);
}
