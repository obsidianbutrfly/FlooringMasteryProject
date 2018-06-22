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

/**
 *
 * @author ashleybesser
 */
public class FlooringDaoStubImpl  {

    Order onlyOrder;
    State onlyState;
    Product onlyProduct;
    String onlyDate;
    int orderNumber = 1000;
    boolean trainingMode;

    List<Order> orderList = new ArrayList<>();
    List<State> stateList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();

    public FlooringDaoStubImpl() {
        onlyDate = "00000000";

        onlyState = new State("State");
        onlyState.setStateName("MN");
        onlyState.setTaxRate(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        stateList.add(onlyState);

        onlyProduct = new Product("TYPE");
        onlyProduct.setProductType("Bamboo");
        onlyProduct.setCostPerSqFoot(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        onlyProduct.setLaborCostPerSqFoot(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        productList.add(onlyProduct);

        onlyOrder = new Order(1001);
        onlyOrder.setCustomerName("Ashley");
        onlyOrder.setState(onlyState);
        onlyOrder.setProduct(onlyProduct);
        onlyOrder.setAreaOfRoom(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setMaterialCostPerSqFoot(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setLaborCostPerSqFoot(new BigDecimal(10).setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setMaterialTotalCost(onlyProduct.getCostPerSqFoot().multiply(onlyOrder.getAreaOfRoom().setScale(2, RoundingMode.HALF_UP)));
        onlyOrder.setLaborTotalCost(onlyProduct.getLaborCostPerSqFoot().multiply(onlyOrder.getAreaOfRoom().setScale(2, RoundingMode.HALF_UP)));
        onlyOrder.setTaxTotal(onlyOrder.getMaterialTotalCost().add(onlyOrder.getLaborTotalCost().multiply(onlyState.getTaxRate().divide(new BigDecimal("100")))));
        onlyOrder.setFinalTotal(onlyOrder.getLaborTotalCost().add(onlyOrder.getMaterialTotalCost().add(onlyOrder.getTaxRate())));
        orderList.add(onlyOrder);
    }


    public boolean initializesystem() throws FlooringPersistenceException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }


    public Order addOrder(Order order, String date) throws FlooringPersistenceException, IOException {
        if (date.equals(onlyDate) && order.getOrderNumer() == onlyOrder.getOrderNumer()) {
            return onlyOrder;
        } else {
            return null;
        }
    }


    public List<Order> getAllOrders(String date) throws FlooringPersistenceException {
        return orderList;
    }


    public Order removeOrder(String date, int orderNumber) throws FlooringPersistenceException, IOException {
        if (date.equals(onlyDate) && orderNumber == onlyOrder.getOrderNumer()) {
            return onlyOrder;
        } else {
            return null;
        }
    }


    public Order editOrder(int orderNumber, Order order) throws FlooringPersistenceException, IOException {
        if (order.equals(onlyOrder.getOrderNumer())) {
            return onlyOrder;
        } else {
            return null;
        }
    }


    public Order getOrder(String date, int orderNumber) throws FlooringPersistenceException {
        if (date.equals(onlyDate)&& orderNumber == onlyOrder.getOrderNumer()){
            return onlyOrder;
        } else {
            return null;
        }
    }


    public List<State> getAllStates() throws FlooringPersistenceException {
        return stateList;
    }


    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return productList;
    }


    public int setNextOrderNumbers(String date) throws FlooringPersistenceException, IOException {
        if (date.equals(onlyDate)) {
            return orderNumber + 1;
        } else {
            return orderNumber;
        }
    }


    public String getDateFromUser(String date) throws FlooringPersistenceException {
        return date;
    }

}
