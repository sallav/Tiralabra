/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Lauta;
import tietorakenteet.Puu;
import tietorakenteet.SijaintiPuu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author varkoi
 */
public class LautaTest {
    private Lauta pelilauta;
    
    public LautaTest() {
        pelilauta = new Lauta();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        pelilauta.laitaMerkki(2, 0, 1);
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
    public void laitaMerkkiTest() throws Exception{
        pelilauta.laitaMerkki(1, 0, 1);
        int[][] taul = pelilauta.getLauta();
        Assert.assertEquals(1, taul[1][0]);
        Assert.assertEquals(2, pelilauta.montakoNappia(1));
    }
    
    @Test
    public void laitaMerkkiTestVaarallaSyotteella() throws Exception{
        pelilauta.laitaMerkki(1, 1, 1);
        Assert.assertFalse(0==pelilauta.getLauta()[1][1]);
        Assert.assertTrue(1==pelilauta.getLauta()[1][1]);
    }
    
    @Test
    public void poistaTest() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void poistaTestVaarallaSyotteella() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertFalse(1==pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void syoTest() throws Exception{
        pelilauta.syo(2, 0, 1);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(1, pelilauta.syoty(1));
        Assert.assertEquals(0, pelilauta.montakoNappia(1));
    }
    
    @Test 
    public void syoTestVaarallaSyotteella() throws Exception{
        pelilauta.syo(2, 0, 1);
        Assert.assertFalse(1==pelilauta.getLauta()[2][0]);
        Assert.assertFalse(0==pelilauta.syoty(1));
        Assert.assertTrue(0==pelilauta.syoty(2));
    }
    
    @Test
    public void peruSyontiTest() throws Exception{
        pelilauta.syo(2, 0, 1);
        pelilauta.peruSyonti(2, 0, 1);
        Assert.assertEquals(1, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(0, pelilauta.syoty(1));
        Assert.assertEquals(1, pelilauta.montakoNappia(1));
    }
    
    @Test
    public void peruSyontiTestVaarallaSyotteella() throws Exception{
        pelilauta.syo(2, 0, 1);
        pelilauta.peruSyonti(2, 0, 1);
        Assert.assertFalse(0==pelilauta.getLauta()[2][0]);
        Assert.assertFalse(0<pelilauta.syoty(1));
        Assert.assertFalse(0==pelilauta.montakoNappia(1));
    }
    
    @Test
    public void puuSiirtoTest() throws Exception{
        Puu m = pelilauta.getMustat();
        Puu v = pelilauta.getValkoiset();
        Assert.assertEquals(1, m.getKoko());
        pelilauta.puuSiirto(16, m, v, 1, (-1));
        Assert.assertNull(m.getJuuri());
        Assert.assertNull(m.etsi(16));
        Assert.assertEquals(0, m.getKoko());
        Assert.assertNotNull(v.getJuuri());
        Assert.assertNotNull(v.etsi(16));
        Assert.assertEquals(16, v.getJuuri().getAvain());
        Assert.assertEquals(0, pelilauta.montakoNappia(1));
        Assert.assertEquals(1, v.getKoko());
        Assert.assertEquals(0, m.getKoko());
    }
    
    @Test
    public void puuSiirto2Test(){
        Puu tyhjat = pelilauta.getTyhjat();
        Puu mustat = pelilauta.getMustat();
        Assert.assertEquals(23, tyhjat.getKoko());
        Assert.assertEquals(1, mustat.getKoko());
        pelilauta.puuSiirto(16, mustat, tyhjat, 1, (-1));
        Assert.assertNotNull(tyhjat.getJuuri());
        Assert.assertEquals(24, tyhjat.getKoko());
        Assert.assertNotNull(tyhjat.etsi(16));
        Assert.assertTrue(tyhjat.etsi(16).getAvain()==16);
        Assert.assertEquals(0, mustat.getKoko());
        Assert.assertTrue(mustat.etsi(16)==null);
        Assert.assertTrue(mustat.getJuuri()==null);
        pelilauta.puuSiirto(18, tyhjat, mustat, 1, 1);
        pelilauta.puuSiirto(7, tyhjat, mustat, 1, 1);
        Assert.assertEquals(22, tyhjat.getKoko());
        Assert.assertEquals(18, mustat.getJuuri().getAvain());
        Assert.assertEquals(2, mustat.getKoko());
    }
    
    @Test
    public void siirraTest() throws Exception{
        pelilauta.siirra(2, 0, 'y', 1);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[1][0]);
        pelilauta.siirra(1, 0, 'a', 1);
        Assert.assertEquals(0, pelilauta.getLauta()[1][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void siirraTest2() throws Exception{
        pelilauta.siirra(2, 0, 'v', 1);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[2][7]);
        pelilauta.siirra(2, 7, 'o', 1);
        Assert.assertEquals(1, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(0, pelilauta.getLauta()[2][7]);
    }
    
    @Test
    public void siirraTestVaarallaSyotteella() throws Exception{
        pelilauta.siirra(2, 0, 'y', 1);
        Assert.assertFalse(1==pelilauta.getLauta()[2][0]);
        Assert.assertFalse(0==pelilauta.getLauta()[1][0]);
        pelilauta.siirra(1, 0, 'v', 1);
        Assert.assertFalse(0==pelilauta.getLauta()[1][7]);
        Assert.assertFalse(1==pelilauta.getLauta()[1][0]);
    }
    
    @Test
    public void siirtoTest() throws Exception{
        int x = pelilauta.siirto(2, 0, 1, 6, 1);
        Assert.assertEquals(x, 14);
        Assert.assertEquals(1, pelilauta.getLauta()[1][6]);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void siirtoTestVaarallaSyotteella() throws Exception{
        int x = pelilauta.siirto(2, 0, 1, 7, 1);
        Assert.assertFalse(x==16);
        Assert.assertFalse(pelilauta.getLauta()[2][0]==1);
        Assert.assertFalse(pelilauta.getLauta()[1][7]==0);
    }
    
    @Test
    public void merkkiTest() throws Exception{
        int merkki = pelilauta.merkki(2, 0);
        Assert.assertEquals(1, merkki);
        Assert.assertEquals(0, pelilauta.merkki(1, 0));
        pelilauta.laitaMerkki(0, 0, 2);
        Assert.assertEquals(2, pelilauta.merkki(0, 0));
    }
    
    @Test
    public void montakoNappiaTest() throws Exception{
        Assert.assertEquals(1, pelilauta.montakoNappia(1));
        Assert.assertEquals(0, pelilauta.montakoNappia(2));
    }
    
    @Test
    public void montakoNappiaVaarallaSyotteella() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertFalse(1==pelilauta.montakoNappia(1));
    }
    
    @Test
    public void voikoLiikkuaTest() throws Exception{
        Assert.assertTrue(pelilauta.voikoLiikkua(1));
        pelilauta.laitaMerkki(2, 1, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertFalse(pelilauta.voikoLiikkua(1));
    }
    
    @Test
    public void voikoLiikkuaTest2(){
        Assert.assertFalse(pelilauta.voikoLiikkua(2));
    }
    
    @Test
    public void myllyjaTest(){
        Assert.assertFalse(0<pelilauta.myllyja(1));
        Assert.assertFalse(0<pelilauta.myllyja(2));
    }
    
    @Test
    public void myllyjaTest2() throws Exception{
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(1==pelilauta.myllyja(1));
        Assert.assertTrue(0==pelilauta.myllyja(2));
    }
    
    @Test
    public void myllyjaTest3() throws Exception{
        pelilauta.laitaMerkki(0, 7, 2);
        pelilauta.laitaMerkki(1, 7, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertTrue(1==pelilauta.myllyja(2));
        Assert.assertTrue(0==pelilauta.myllyja(1));
    }
    
    @Test
    public void myllyTest() throws Exception{
        pelilauta.laitaMerkki(0, 7, 2);
        pelilauta.laitaMerkki(1, 7, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertTrue(pelilauta.mylly(2, 7));
        Assert.assertTrue(pelilauta.mylly(2, 15));
        Assert.assertTrue(pelilauta.mylly(2, 23));
        Assert.assertFalse(pelilauta.mylly(1, 15));
    }
   
    @Test
    public void myllyTest2() throws Exception{
        Assert.assertFalse(pelilauta.mylly(1, 23));
        Assert.assertFalse(pelilauta.mylly(2, 16));
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(pelilauta.mylly(1, 16));
        Assert.assertTrue(pelilauta.mylly(1, 17));
        Assert.assertTrue(pelilauta.mylly(1, 18));
        Assert.assertFalse(pelilauta.mylly(1, 19));
        Assert.assertFalse(pelilauta.mylly(1, 9));
    }
    
    @Test
    public void myllyKulmassaTest() throws Exception{
        Assert.assertFalse(pelilauta.myllyKulmassa(2, 0, 1));
        Assert.assertFalse(pelilauta.myllyKulmassa(2, 2, 1));
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(pelilauta.myllyKulmassa(2, 0, 1));
        Assert.assertTrue(pelilauta.myllyKulmassa(2, 2, 1));
    }
    
    @Test
    public void myllyKulmassaTest2() throws Exception{
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(1, 1, 1);
        pelilauta.laitaMerkki(0, 1, 1);
        Assert.assertFalse(pelilauta.myllyKulmassa(2, 0, 1));
    }
    
    @Test
    public void keskellaMyllyaTest() throws Exception{
        Assert.assertFalse(pelilauta.keskellaMyllya(2, 1, 1));
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(1, 1, 1);
        pelilauta.laitaMerkki(0, 1, 1);
        Assert.assertTrue(pelilauta.keskellaMyllya(2, 1, 1));
        Assert.assertTrue(pelilauta.keskellaMyllya(1, 1, 1));
        Assert.assertTrue(pelilauta.keskellaMyllya(0, 1, 1));
    }
    
        @Test
    public void keskellaMyllyaTestVaarallaSyotteella() throws Exception{
        Assert.assertFalse(pelilauta.keskellaMyllya(2, 1, 2));
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(1, 1, 1);
        pelilauta.laitaMerkki(0, 1, 1);
        Assert.assertFalse(pelilauta.keskellaMyllya(2, 1, 2));
        Assert.assertFalse(pelilauta.keskellaMyllya(1, 1, 2));
        Assert.assertFalse(pelilauta.keskellaMyllya(0, 1, 3));
    }
    
    @Test
    public void melkeinMyllyjaTest() throws Exception{
        Assert.assertTrue(0==pelilauta.melkeinMyllyja(1).getKoko());
        pelilauta.laitaMerkki(2, 1, 1);
        Assert.assertTrue(1==pelilauta.melkeinMyllyja(1).getKoko());
    }
    
    @Test
    public void melkeinMyllySivullaTest() throws Exception{
        Assert.assertEquals(-1, pelilauta.melkeinMyllySivulla(17, 1));
        pelilauta.laitaMerkki(2, 2, 1);
        Assert.assertEquals(17, pelilauta.melkeinMyllySivulla(17, 1));   
    }
    
    @Test
    public void vasTest(){
        Assert.assertEquals(7, pelilauta.vas(0));
        Assert.assertEquals(0, pelilauta.vas(1));        
    }
    
    @Test
    public void vvasTest(){
        Assert.assertEquals(6, pelilauta.vvas(0));
        Assert.assertEquals(7, pelilauta.vvas(1));        
    }    
    
    @Test
    public void oikTest(){
        Assert.assertEquals(0, pelilauta.oik(7));
        Assert.assertEquals(1, pelilauta.oik(0));        
    }
    
    @Test
    public void ooikTest(){
        Assert.assertEquals(1, pelilauta.ooik(7));
        Assert.assertEquals(0, pelilauta.ooik(6));        
    }
    
    @Test
    public void ylaTest(){
        Assert.assertEquals(0, pelilauta.yla(2));
        Assert.assertEquals(2, pelilauta.yla(1));
        Assert.assertEquals(1, pelilauta.yla(0));
    }
    
    @Test
    public void alaTest(){
        Assert.assertEquals(0, pelilauta.ala(1));
        Assert.assertEquals(1, pelilauta.ala(2));
        Assert.assertEquals(2, pelilauta.ala(0));
    }
}
