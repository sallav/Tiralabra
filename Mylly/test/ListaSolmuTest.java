/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import tietorakenteet.ListaSolmu;
import tietorakenteet.Solmu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Käyttäjä
 */
public class ListaSolmuTest {
    ListaSolmu solmu;
    Solmu puusolmu;
    
    public ListaSolmuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        puusolmu = new Solmu(8);
        solmu = new ListaSolmu(puusolmu);
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
    public void setSeuraavaTest(){
        ListaSolmu seur = new ListaSolmu(new Solmu(19));
        solmu.setSeuraava(seur);
        Assert.assertTrue(seur==solmu.getSeuraava());
    }
    
    @Test
    public void getSeuraavaTest(){
        Assert.assertNull(solmu.getSeuraava());
    }
    
    @Test
    public void getPuuSolmuTest(){
        Assert.assertTrue(puusolmu==solmu.getPuuSolmu());
    }
    
    @Test
    public void getArvoTest(){
        Assert.assertEquals(0, solmu.getArvo());
        solmu.getPuuSolmu().setArvo(10);
        Assert.assertEquals(10, solmu.getArvo());
    }
}
