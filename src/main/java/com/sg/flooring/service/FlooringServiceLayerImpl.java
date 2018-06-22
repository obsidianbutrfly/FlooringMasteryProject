/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

import com.sg.flooring.dao.FlooringDao;
import com.sg.flooring.dao.FlooringPersistenceException;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.State;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author ashleybesser
 */
public class FlooringServiceLayerImpl {

    private FlooringDao dao; //create, read, write, objects
//    private FlooringAuditDao auditDao;

    public FlooringServiceLayerImpl(FlooringDao dao) {
        this.dao = dao;
    }

    public List<Order> getAllOrders(String date) throws FlooringPersistenceException {
        return dao.getAllOrders(date);
    }

    public void createOrder(Order order, String date) throws FlooringDataValidationException, FlooringPersistenceException, IOException {
        calculateAllCosts(order);
        dao.addOrder(order, date);
    }

    public Order removeOrder(String date, int orderNumber) throws FlooringPersistenceException, IOException {
        Order removedOrder = dao.removeOrder(date, orderNumber);
        return removedOrder;
    }
    
    public Order editOrder(int orderNumber,Order order, String date) throws FlooringPersistenceException, IOException {
        Order editedOrder = dao.editOrder(orderNumber, order, date);
        return editedOrder;
    }

    public Order getOrder(String date, int orderNumber) throws FlooringPersistenceException {
        return dao.getOrder(date, orderNumber);
    }

    public List<State> getAllStates() throws FlooringPersistenceException {
        return dao.getAllStates();
    }

    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return dao.getAllProducts();
    }

    public boolean validateDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate date = LocalDate.now();
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public int getNextOrderNumber(String date) throws FlooringPersistenceException, IOException {
        return dao.setNextOrderNumbers(date);
    }


    public void calculateMaterialTotal(Order order) throws FlooringPersistenceException {
        BigDecimal materialTotalCost = order.getMaterialCostPerSqFoot().multiply(order.getAreaOfRoom());
        order.setMaterialTotalCost(materialTotalCost);
    }

    public void calculateLaborTotal(Order order) throws FlooringPersistenceException {
        BigDecimal laborTotalCost = order.getLaborCostPerSqFoot().multiply(order.getAreaOfRoom());
        order.setLaborTotalCost(laborTotalCost);
    }

    public void calculateTaxTotal(Order order) throws FlooringPersistenceException {
        BigDecimal taxTotal = order.getMaterialTotalCost().add(order.getLaborTotalCost()).multiply(order.getTaxRate()).divide(new BigDecimal("100"));
        order.setTaxTotal(taxTotal);
    }

    public void calculateAllCosts(Order order) throws FlooringPersistenceException {
        calculateMaterialTotal(order);
        calculateLaborTotal(order);
        calculateTaxTotal(order);
        order.setFinalTotal(order.getMaterialTotalCost().add(order.getLaborTotalCost()).add(order.getTaxTotal()));
    }


    public boolean systemEnviornment() throws FlooringPersistenceException, IOException {
        return dao.initializesystem();
    }

    public boolean validateOrderChoice(int orderNumber, String date) throws FlooringPersistenceException {
        List<Order> orders = dao.getAllOrders(date);

        //oull the orders for that date, now I need to check if the first 4 numbers in the file are valid
        for (Order o : orders) {
            if (o.getOrderNumer() == orderNumber) {
                return true;
            }
        }
        return false;
    }
}
