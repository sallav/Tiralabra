/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tietorakenteet.SijaintiPuu;
import tietorakenteet.SijaintiPuu2;
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
public class SijaintiPuuTest {
    SijaintiPuu2 puu;
    
    public SijaintiPuuTest() {
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
        puu = new SijaintiPuu2(paikat);
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
    public void vanhemmanLapseksiTest(){
        puu.vanhemmanLapseksi(puu.etsi(9), new Solmu(10), false);
        Assert.assertTrue(puu.etsi(10).getVanhempi()==puu.etsi(9));
        Assert.assertTrue(puu.etsi(9).getOikea()==puu.etsi(10));
    }
    
    @Test
    public void vanhemmanLapseksiTest2(){
        puu.vanhemmanLapseksi(puu.etsi(1), new Solmu(-2), true);
        Assert.assertTrue(puu.etsi(-2).getVanhempi()==puu.etsi(1));
        Assert.assertTrue(puu.etsi(1).getVasen()==puu.etsi(-2));
    }
    
    @Test
    public void sopivaVanhempiTest(){
        Assert.assertTrue(puu.etsiVanhempi(3, puu.getJuuri()).getAvain()==2);
        Assert.assertTrue(puu.etsiVanhempi(12, puu.getJuuri()).getAvain()==9);
    }
    
    @Test
    public void poistaJuuriTest(){
        Solmu vj = puu.getJuuri();
        Solmu vasen = puu.getJuuri().getVasen();
        Solmu oikea = puu.getJuuri().getOikea();
        puu.poistaJuuri();
        Assert.assertEquals(vasen.getAvain(), puu.getJuuri().getAvain());
        Assert.assertNotNull(puu.etsi(oikea.getAvain()));
        Assert.assertEquals(oikea.getAvain(), puu.etsi(oikea.getAvain()).getAvain());
        Assert.assertNull(puu.etsi(vj.getAvain()));
        puu.poistaJuuri();
        puu.poistaJuuri();
        Assert.assertEquals(puu.getJuuri().getAvain(), oikea.getAvain());
    }
    
    @Test
    public void poistoTest(){
        Solmu p = puu.etsi(8);
        Solmu v = p.getVasen();
        Solmu o = p.getOikea();
        puu.poisto(p);
        Assert.assertEquals(puu.getJuuri().getOikea().getAvain(), v.getAvain());
        Assert.assertNotNull(puu.etsi(o.getAvain()));
        Assert.assertNotNull(puu.etsi(v.getAvain()));
        Assert.assertNull(puu.etsi(8));
    }
}
