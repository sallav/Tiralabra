/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class SolmuTest {
    Solmu vanhempi;
    Solmu lapsi;
    
    public SolmuTest() {
        vanhempi = new Solmu(0);
        lapsi = new Solmu(vanhempi, 16);
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
    public void setAvainTest(){
        lapsi.setAvain(8);
        Assert.assertFalse(16==lapsi.getAvain());
    }
    
    @Test
    public void setArvoTest(){
        lapsi.setArvo(100);
        Assert.assertEquals(100, lapsi.getArvo());
    }
    
    @Test
    public void setVasenTest(){
        vanhempi.setVasen(lapsi);
        Assert.assertTrue(vanhempi.getVasen()==lapsi);
    }
    
    @Test
    public void setOikeaTest(){
        vanhempi.setOikea(lapsi);
        Assert.assertTrue(vanhempi.getOikea()==lapsi);
    }
    
    @Test
    public void setVanhempiTest(){
        Solmu uusi = new Solmu(18);
        uusi.setVanhempi(lapsi);
        Assert.assertTrue(uusi.getVanhempi()==lapsi);
    }
    
    @Test
    public void setSuuntaTest(){
        lapsi.setSuunta('v');
        Assert.assertEquals('v', lapsi.getSuunta());
    }
    
    @Test
    public void getVanhempiTest(){
        Assert.assertTrue(lapsi.getVanhempi()==vanhempi);
    }
    
    @Test
    public void getVasenTest(){
        vanhempi.setVasen(lapsi);
        Assert.assertTrue(lapsi==vanhempi.getVasen());
    }
    
    @Test
    public void getOikeaTest(){
        vanhempi.setOikea(lapsi);
        Assert.assertTrue(lapsi==vanhempi.getOikea());
    }
    
    @Test
    public void getAvainTest(){
        Assert.assertEquals(16, lapsi.getAvain());
    }
    
    @Test
    public void getArvoTest(){
        Assert.assertEquals(0, lapsi.getArvo());
        lapsi.setArvo(10);
        Assert.assertEquals(10, lapsi.getArvo());
    }
    
    @Test
    public void getSuuntaTest(){
        Assert.assertEquals('x', lapsi.getSuunta());
        lapsi.setSuunta('o');
        Assert.assertEquals('o', lapsi.getSuunta());
    }
    
    @Test
    public void compareToTest(){
        Assert.assertTrue(lapsi.compareTo(vanhempi)==0);
        lapsi.setArvo(100);
        vanhempi.setArvo(-20);
        Assert.assertFalse(vanhempi.compareTo(lapsi)==0);
        Assert.assertTrue(lapsi.compareTo(vanhempi)>0);
    }
    
}
