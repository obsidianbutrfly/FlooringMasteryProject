/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

import com.sg.flooring.dao.FlooringPersistenceException;
import com.sg.flooring.dto.Order;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ashleybesser
 */
public class FlooringServiceLayerImplTest {

    private FlooringServiceLayerImpl service;

    String goodDate = "06052018";
    int orderNumber = 1001;
    int testOrderNumber = 1;
    List<Order> orderList = new ArrayList<>();

    public FlooringServiceLayerImplTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringServiceLayerImpl.class);
    }

    public String testerDate = "00000000";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FlooringPersistenceException, IOException {
        orderList = service.getAllOrders(goodDate);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        //I only made 1 order so this should only be the size of 1
        assertEquals(3, service.getAllOrders(goodDate).size());
    }

    /**
     * Test of createOrder method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testCreateOrder() throws Exception {
    }

    /**
     * Test of removeOrder method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
    }

    /**
     * Test of getOrder method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testGetOrder() throws Exception {
        assertNotNull(service.getOrder(goodDate, orderNumber));

    }

    /**
     * Test of getAllStates method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testGetAllStates() throws Exception {
        //if stub is correct then there should be stuff in here
        assertNotNull(service.getAllStates());

    }

    /**
     * Test of getAllProducts method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        //if stub is correct there should be at least 1 thing in here
        assertNotNull(service.getAllProducts());
    }

    /**
     * Test of validateDate method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testValidateDate() {
        assertTrue(service.validateDate(goodDate));
        assertFalse(service.validateDate(testerDate));
    }

    /**
     * Test of getNextOrderNumber method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testGetNextOrderNumber() throws Exception {
    }

    /**
     * Test of calculateMaterialTotal method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testCalculateMaterialTotal() throws Exception {
        Order order = orderList.get(0);
        service.calculateMaterialTotal(order);
        assertEquals("3491.25", order.getMaterialTotalCost().setScale(2, RoundingMode.HALF_UP).toString());
        assertFalse("3".equals(order.getMaterialTotalCost().setScale(2, RoundingMode.HALF_UP).toString()));
        
        
    }

    /**
     * Test of calculateLaborTotal method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testCalculateLaborTotal() throws Exception {

        Order order = orderList.get(0);
        service.calculateLaborTotal(order);
        assertEquals("4189.50", order.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP).toString());
        assertFalse("3".equals(order.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP).toString()));
    }

    /**
     * Test of calculateTaxTotal method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testCalculateTaxTotal() throws Exception {
        Order order = orderList.get(0);
        service.calculateTaxTotal(order);
        assertEquals("518.45", order.getTaxTotal().setScale(2, RoundingMode.HALF_UP).toString());
        assertFalse("3".equals(order.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP).toString()));
    }

    /**
     * Test of calculateAllCosts method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testCalculateAllCosts() throws Exception {
        Order order = orderList.get(0);
        service.calculateAllCosts(order);
        assertEquals("8199.20", order.getFinalTotal().setScale(2, RoundingMode.HALF_UP).toString());
        assertFalse("3".equals(order.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP).toString()));
    }


    /**
     * Test of systemEnviornment method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testSystemEnviornment() throws Exception {
    }

}
