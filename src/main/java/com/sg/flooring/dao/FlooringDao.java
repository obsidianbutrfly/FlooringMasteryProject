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
import java.util.List;

/**
 *
 * @author ashleybesser
 */
public interface FlooringDao {

    public boolean initializesystem() throws FlooringPersistenceException, IOException;
    
    public Order addOrder(Order order, String date)
            throws FlooringPersistenceException, IOException;

    List<Order> getAllOrders(String date)
            throws FlooringPersistenceException;

    Order removeOrder(String date, int orderNumber)
            throws FlooringPersistenceException, IOException;

    Order editOrder(int orderNumber, Order order, String date)
            throws FlooringPersistenceException, IOException;

    Order getOrder(String date, int orderNumber)
            throws FlooringPersistenceException;

    List<State> getAllStates() throws FlooringPersistenceException;

    List<Product> getAllProducts() throws FlooringPersistenceException;


    public int setNextOrderNumbers(String date) throws FlooringPersistenceException, IOException;


    

}
