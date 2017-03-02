/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tietorakenteet.Lista;
import tietorakenteet.ListaSolmu;
import tietorakenteet.Solmu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Käyttäjä
 */
public class ListaTest {
    Lista lista;
    ListaSolmu solmu;
    Solmu puusolmu;
    
    public ListaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.lista = new Lista();
        this.puusolmu = new Solmu(16);
        this.solmu = new ListaSolmu(puusolmu);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void lisaaTest(){
        lista.lisaa(puusolmu);
        Assert.assertEquals(1, lista.getKoko());
        Assert.assertTrue(solmu.getPuuSolmu()==lista.getVika().getPuuSolmu());
        Assert.assertTrue(lista.getVika()==lista.getEka());
    }
    
    @Test
    public void lisaaTest2(){
        lista.lisaa(new Solmu(9));
        Lista toinen = new Lista(puusolmu);
        lista.lisaa(toinen);
        Assert.assertEquals(2, lista.getKoko());
        Assert.assertEquals(toinen.getVika(), lista.getVika());
        Assert.assertFalse(lista.getEka()==lista.getVika());
    }
    
    @Test
    public void lisaaTest3(){
        Lista lisattava = new Lista(puusolmu);
        lista.lisaa(lisattava);
        Assert.assertEquals(1, lista.getKoko());
        Assert.assertTrue(lista.getEka()==lista.getVika());
        Assert.assertTrue(lisattava.getEka()==lista.getVika());
        Assert.assertTrue(lisattava.getVika()==lista.getEka());
    }
    
    @Test
    public void getEkaTest(){
        lista.lisaa(puusolmu);
        Assert.assertEquals(puusolmu.getAvain(), lista.getEka().getPuuSolmu().getAvain());
    }
    
    @Test
    public void getVikaTest(){
        Assert.assertNull(lista.getVika());
        lista.lisaa(puusolmu);
        Assert.assertTrue(solmu.getPuuSolmu().getAvain()==lista.getVika().getPuuSolmu().getAvain());
        lista.lisaa(new Solmu(14));
        Assert.assertTrue(solmu.getPuuSolmu().getAvain()==lista.getVika().getPuuSolmu().getAvain());
        Assert.assertNull(lista.getVika().getSeuraava());
    }
    
    @Test
    public void getSolmuTest(){
        Assert.assertNull(lista.getSolmu(0));
        lista.lisaa(puusolmu);
        Assert.assertEquals(puusolmu, lista.getSolmu(0));
        Assert.assertNull(lista.getSolmu(1));
        Solmu uusi = new Solmu(2);
        lista.lisaa(uusi);
        Assert.assertEquals(uusi, lista.getSolmu(0));
        Assert.assertEquals(puusolmu, lista.getSolmu(1));
        Assert.assertNull(lista.getSolmu(13));
    }
    
    @Test
    public void getArvoTest(){
        Assert.assertEquals(-1000, lista.getArvo());
        puusolmu.setArvo(100);
        lista.lisaa(puusolmu);
        Assert.assertEquals(100, lista.getArvo());
    }
    
    @Test
    public void getKokoTest(){
        Assert.assertEquals(0, lista.getKoko());
        lista.lisaa(puusolmu);
        Assert.assertEquals(1, lista.getKoko());
        Lista toinen = new Lista(puusolmu);
        toinen.lisaa(puusolmu);
        lista.lisaa(toinen);
        Assert.assertEquals(3, lista.getKoko());
        Assert.assertEquals(2, toinen.getKoko());
    }
}
