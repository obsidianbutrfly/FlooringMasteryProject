/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.State;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ashleybesser
 */
public class FlooringDaoFileImplTest {

    private FlooringDao dao = new FlooringDaoFileImpl();
    String goodDate = "06052018";
    String testerDate = "01012018";
    int orderTestCount;
    int goodOrderNumber = 1001;

    List<Order> orderList = new ArrayList<>();

    public FlooringDaoFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FlooringPersistenceException, IOException {
        orderList = dao.getAllOrders(testerDate);
    }

    @After
    public void tearDown() throws FlooringPersistenceException, IOException{
        orderList = dao.getAllOrders(testerDate);
    }

    /**
     * Test of initializesystem method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testInitializesystem() throws Exception {
        //should be in production mode
        boolean test = dao.initializesystem();
        assertTrue(test);
    }

    /**
     * Test of addOrder method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        orderList = dao.getAllOrders(testerDate);
        int testNumber = 1004;

        State newState = new State("OH");
        newState.setTaxRate(new BigDecimal(6.25).setScale(2, RoundingMode.HALF_UP));

        Product testProd = new Product("Wood");
        testProd.setCostPerSqFoot(new BigDecimal(5.15).setScale(2, RoundingMode.HALF_UP));
        testProd.setLaborCostPerSqFoot(new BigDecimal(4.75).setScale(2, RoundingMode.HALF_UP));

        Order testOrder1 = new Order(testNumber);
        testOrder1.setOrderNumer(testNumber);
        testOrder1.setCustomerName("Ashley");
        testOrder1.setState(newState);
        testOrder1.setStateName(newState.getStateName());
        testOrder1.setTaxRate(newState.getTaxRate());
        testOrder1.setProduct(testProd);
        testOrder1.setProductType(testProd.getProductType());
        testOrder1.setMaterialCostPerSqFoot(testProd.getCostPerSqFoot());
        testOrder1.setLaborCostPerSqFoot(testProd.getLaborCostPerSqFoot());
        testOrder1.setAreaOfRoom(new BigDecimal(100));
        testOrder1.setMaterialTotalCost(new BigDecimal(515).setScale(2, RoundingMode.HALF_UP));
        testOrder1.setLaborTotalCost(new BigDecimal(475).setScale(2, RoundingMode.HALF_UP));
        testOrder1.setTaxTotal(new BigDecimal(61.88).setScale(2, RoundingMode.HALF_UP));
        testOrder1.setFinalTotal(new BigDecimal(1051.88).setScale(2, RoundingMode.HALF_UP));

        dao.addOrder(testOrder1, testerDate);

        Order newOrders = dao.getOrder(testerDate, testNumber);

        assertEquals(testOrder1, newOrders);

    }

    /**
     * Test of getAllOrders method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testGetAllOrders() throws Exception {
//        assertEquals(3, dao.getAllOrders(testerDate).size());
        assertFalse("5".equals(dao.getAllOrders(testerDate).size()));
    }

    /**
     * Test of removeOrder method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        List<Order> orderList2 = new ArrayList<>();
        orderList2 = dao.getAllOrders("01012018");
        Order order2 = orderList2.get(0);
        int testNum = order2.getOrderNumer();

        dao.removeOrder("01012018", testNum);

        assertFalse(orderList.size()>3);

    }

    /**
     * Test of editOrder method, of class FlooringDaoFileImpl.
     */
    @Test   
    public void testEditOrder() throws Exception {
        //test does the order number state the same?
        
        String editDate = "01012018";
        Order preEdit = orderList.get(2);
        int orderNum = preEdit.getOrderNumer();
        String preEditName = dao.getOrder(editDate, orderNum).getCustomerName();
        
        Order postEdit = orderList.get(2);
        postEdit.setCustomerName("Jess");
        String postEditName = orderList.get(2).getCustomerName();
        int postEditNum = orderList.get(2).getOrderNumer();
        
        
        
        dao.editOrder(orderNum, postEdit, editDate);
        assertFalse(preEditName.equalsIgnoreCase(postEditName));
        assertTrue( orderNum == postEditNum);
        
       
    }

    /**
     * Test of getAllStates method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testGetAllStates() throws Exception {

        List<State> states = dao.getAllStates();
        assertNotNull(states.size());
        assertTrue(states.size() > 1);

    }

    /**
     * Test of getAllProducts method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> prods = dao.getAllProducts();

        assertNotNull(prods.size());
        assertTrue(prods.size() > 1);

    }

    /**
     * Test of setNextOrderNumbers method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testSetNextOrderNumbers() throws Exception {
      
        dao.getAllOrders("01012018");
        int orderStart = 1000;
        orderStart++;
        assertTrue(orderStart == 1001);
    }

    /**
     * Test of getOrder method, of class FlooringDaoFileImpl.
     */
    @Test
    public void testGetOrder() throws Exception {
        int orderNumber = 1002;
        orderList = dao.getAllOrders(testerDate);
        Order fromDao = dao.getOrder(testerDate, orderNumber);

        assertNotNull(orderList.size());
        assertEquals(orderNumber , fromDao.getOrderNumer());
        
    }


}
