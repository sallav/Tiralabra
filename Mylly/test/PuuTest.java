/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import tietorakenteet.Lista;
import tietorakenteet.Puu;
import tietorakenteet.SijaintiPuu;
import tietorakenteet.SijaintiPuu2;
import tietorakenteet.Solmu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void getJuuriTest(){
        Assert.assertEquals(4, puu.getJuuri().getAvain());
    }
    
    @Test
    public void lisaaSolmuTest(){
        Assert.assertEquals(6, puu.getKoko());
        Assert.assertFalse(puu.lisaa(8));
        Assert.assertEquals(6, puu.getKoko());
    }
    
    @Test
    public void lisaaSolmuTest2(){
        Assert.assertTrue(puu.lisaa(18));
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
        Assert.assertNotNull(puu.etsi(4));
        Assert.assertEquals(4, puu.poista(4).getAvain());
        Assert.assertNull(puu.etsi(4));
        Assert.assertEquals(4, puu.getKoko());
        System.out.println(puu.getJuuri().getAvain());
        Assert.assertEquals(2, puu.etsi(6).getVanhempi().getAvain());
        Assert.assertEquals(2, puu.etsi(1).getVanhempi().getAvain());
    }
    
    @Test
    public void poistaTest2(){
        Solmu p = puu.poista(2);
        Assert.assertNotNull(p);
        Assert.assertTrue(p.getAvain()==2);
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
    public void poistaJaEsijarjestysTest(){
        SijaintiPuu uusi = new SijaintiPuu();
        for(int i=0; i<24; i++){
            uusi.lisaa(i);
        }
        Lista luvut = uusi.esijarjestys();
        Assert.assertEquals(24, luvut.getKoko());
        System.out.println("koko " + luvut.getKoko());
        for(int i=0; i<24; i++){
            System.out.println(luvut.getSolmu(i).getAvain() + " ");
            Assert.assertNotNull(luvut.getSolmu(i));
            Assert.assertTrue(i==luvut.getSolmu(23-i).getAvain());
            if(i==23)   System.out.println("vika");
        }
        uusi.poista(23);
        luvut = uusi.esijarjestys();
        for(int i=0; i<23; i++){
            Assert.assertTrue(i==luvut.getSolmu(23-i).getAvain());
        }
        uusi.lisaa(23);
        uusi.poista(22);
        luvut = uusi.esijarjestys();
        for(int i=0; i<luvut.getKoko(); i++){
            System.out.println(i + " " + luvut.getSolmu(i).getAvain());
            if(i>1) Assert.assertTrue((22-i)==luvut.getSolmu(i).getAvain());
        }        
        uusi.lisaa(22);
        luvut = uusi.esijarjestys();
        for(int i=0; i<23; i++){
            if(i>21)    Assert.assertTrue(luvut.toString(), i!=luvut.getSolmu(i).getAvain());
        }
        uusi.poista(21);
        luvut = uusi.esijarjestys();
        Assert.assertTrue(luvut.toString(), uusi.etsi(21)==null);
    }
    
    @Test
    public void etsiTest(){
        Assert.assertNull(puu.etsi(10));
        Assert.assertTrue(puu.etsi(1).getAvain()==1);
        Assert.assertNotNull(puu.etsi(6));
        Assert.assertTrue(puu.etsi(6).getAvain()==6);
    }
    
    @Test
    public void teeKopioTest(){
        Puu kopio = puu.teeKopio();
        Lista solmut = puu.esijarjestys();
        for(int i=0; i<puu.getKoko(); i++){
            Assert.assertNotNull(kopio.etsi(solmut.getSolmu(i).getAvain()));
        }
    }
   
}
