/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Puu;
import mylly.Solmu;
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
public class PuuTest {
    Puu puu;
    
    
    public PuuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        int[] paikat = {4, 2, 8, 1, 9, 6};
        puu = new Puu(paikat);
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
    public void getJuuriTest(){
        Assert.assertEquals(4, puu.getJuuri().getAvain());
    }
    
    @Test
    public void lisaaSolmuTest(){
        Assert.assertEquals(6, puu.getKoko());
        Assert.assertFalse(puu.lisaaSolmu(8));
        Assert.assertEquals(6, puu.getKoko());
    }
    
    @Test
    public void lisaaSolmuTest2(){
        Assert.assertTrue(puu.lisaaSolmu(18));
        Assert.assertEquals(18, puu.etsi(18).getAvain());
        Assert.assertEquals(7, puu.getKoko());
    }
    
    @Test
    public void lisaaTest(){
        Solmu uusi = new Solmu(3);
        Assert.assertTrue(puu.lisaa(uusi));
        Assert.assertEquals(uusi.getAvain(), puu.etsi(3).getAvain());
        Assert.assertEquals(7, puu.getKoko());
    }
    
    @Test
    public void lisaaTest2(){
        Solmu uusi = new Solmu(4);
        Assert.assertFalse(puu.lisaa(uusi));
        Assert.assertEquals(6, puu.getKoko());
    }
    
    @Test
    public void poistaTest(){
        Assert.assertTrue(puu.poista(8).getAvain()==8);
        Assert.assertNull(puu.etsi(8));
        Assert.assertEquals(5, puu.getKoko());
        Assert.assertEquals(6, puu.etsi(9).getVanhempi().getAvain());
        Assert.assertEquals(4, puu.etsi(6).getVanhempi().getAvain());
        Assert.assertEquals(4, puu.poista(4).getAvain());
        Assert.assertNull(puu.etsi(4));
        Assert.assertEquals(4, puu.getKoko());
        System.out.println(puu.getJuuri().getAvain());
        Assert.assertEquals(2, puu.etsi(6).getVanhempi().getAvain());
        Assert.assertEquals(2, puu.etsi(1).getVanhempi().getAvain());
    }
    
    @Test
    public void poistaTest2(){
        Assert.assertTrue(puu.poista(2).getAvain()==2);
        Assert.assertNull(puu.etsi(2));
        Assert.assertEquals(5, puu.getKoko());
        Assert.assertNotNull(puu.getJuuri());
        Assert.assertNotNull(puu.getJuuri().getVasen());
        Assert.assertEquals(1, puu.getJuuri().getVasen().getAvain());
        Assert.assertNotNull(puu.etsi(1));
        Assert.assertEquals(4, puu.etsi(1).getVanhempi().getAvain());
        Assert.assertNull(puu.poista(18));
        Assert.assertEquals(5, puu.getKoko());
    }
    
    @Test
    public void vanhemmanLapseksiTest(){
        puu.vanhemmanLapseksi(puu.etsi(9), new Solmu(10), new Solmu(11), false);
        Assert.assertTrue(puu.etsi(11).getVanhempi()==puu.etsi(10));
        Assert.assertTrue(puu.etsi(10).getVanhempi()==puu.etsi(9));
    }
    
    @Test
    public void vanhemmanLapseksiTest2(){
        puu.vanhemmanLapseksi(puu.etsi(1), new Solmu(-2), new Solmu(-1), true);
        Assert.assertTrue(puu.etsi(-1).getVanhempi()==puu.etsi(-2));
        Assert.assertTrue(puu.etsi(-2).getVanhempi()==puu.etsi(1));
    }
    
    @Test
    public void sopivaVanhempiTest(){
        Assert.assertTrue(puu.sopivaVanhempi(new Solmu(3), puu.getJuuri()).getAvain()==2);
        Assert.assertTrue(puu.sopivaVanhempi(new Solmu(12), puu.getJuuri()).getAvain()==9);
    }
    
    @Test
    public void etsiTest(){
        Assert.assertNull(puu.etsi(10));
        Assert.assertTrue(puu.etsi(1).getAvain()==1);
        Assert.assertTrue(puu.etsi(6).getAvain()==6);
    }
   
}
