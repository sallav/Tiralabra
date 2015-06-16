/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.SijaintiPuu;
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
public class SijaintiPuuTest {
    SijaintiPuu puu;
    
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
        puu = new SijaintiPuu(paikat);
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
}
