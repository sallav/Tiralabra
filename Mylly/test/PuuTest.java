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
        int[] paikat = {4, 2, 8, 1, 9, 6};
        puu = new Puu(paikat);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
        Assert.assertEquals(4, puu.getJuuri());
    }
    
    @Test
    public void lisaaSolmuTest(){
        Assert.assertFalse(puu.lisaaSolmu(8));
    }
    
    @Test
    public void lisaaSolmuTest2(){
        Assert.assertTrue(puu.lisaaSolmu(18));
        Assert.assertEquals(18, puu.etsi(18).getAvain());
    }
    
    @Test
    public void lisaaTest(){
        Solmu uusi = new Solmu(3);
        Assert.assertTrue(puu.lisaa(uusi, puu.etsi(8)));
        Assert.assertEquals(uusi, puu.etsi(3));
    }
    
    @Test
    public void lisaaTest2(){
        Solmu uusi = new Solmu(4);
        Assert.assertFalse(puu.lisaa(uusi, puu.getJuuri()));
    }
    
    @Test
    public void poistaTest(){
        Assert.assertTrue(puu.poista(8).getAvain()==8);
        Assert.assertTrue(puu.etsi(8)==null);
        Assert.assertEquals(3, puu.etsi(9).getVanhempi().getAvain());
        Assert.assertEquals(9, puu.etsi(6).getVanhempi().getAvain());
    }
    
    @Test
    public void vanhemmanLapseksiTest(){
        puu.vanhemmanLapseksi(puu.etsi(6), new Solmu(5), new Solmu(4), true);
        Assert.assertTrue(puu.etsi(5).getVanhempi()==puu.etsi(6));
        Assert.assertTrue(puu.etsi(4).getVanhempi()==puu.etsi(5));
    }
    
    @Test
    public void sopivaVanhempiTest(){
        Assert.assertTrue(puu.sopivaVanhempi(new Solmu(3), puu.getJuuri()).getAvain()==2);
        Assert.assertTrue(puu.sopivaVanhempi(new Solmu(12), puu.getJuuri()).getAvain()==9);
    }
    
    @Test
    public void etsiTest(){
        Assert.assertTrue(puu.etsi(10)==null);
        Assert.assertTrue(puu.etsi(1).getAvain()==1);
        Assert.assertTrue(puu.etsi(6).getAvain()==6);
    }
   
}
