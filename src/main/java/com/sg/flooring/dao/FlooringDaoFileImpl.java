/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.util.Collections.max;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author ashleybesser
 */
public class FlooringDaoFileImpl implements FlooringDao {

    private String findFile;
    private boolean productionMode = false;
    private int nextOrderNumber = 1000;

    public static final String PRODUCTS_FILE = "products.txt";
    public static final String STATES_FILE = "taxes.txt";
    public static final String CONFIG_FILE = "config.txt";

    public static final String DELIMITER = ",";

    public static final String ORDERS_FILEPATH = "./Orders/";
    public static final String FILE_START = "Orders_";
    public static final String FILE_END = ".txt";

    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();
    private Map<String, State> states = new HashMap<>();

    Scanner inputReader;

    //for trainingmode or production mode if the file says anything other than production it will be in training mode
    private void loadConfigFile() throws FlooringPersistenceException {
        try {
            inputReader = new Scanner(new BufferedReader(new FileReader(CONFIG_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load config file into memory.", e);
        }
        String currentMode = inputReader.nextLine();

        if ("PRODUCTION".equals(currentMode)) {
            productionMode = true;
        }
    }
//    loadAllFiles
    @Override
    public boolean initializesystem() throws FlooringPersistenceException, IOException {
        loadConfigFile();
        loadProducts();
        loadStates();
        createFile();
        
        return productionMode; //giving access to controller, for read/write training options
    }
    @Override
    public Order addOrder(Order order, String date) throws FlooringPersistenceException, IOException {
        createFile();
//        updateFindFile(date);
        orders.clear();
        loadOrder(date);
        Set<Integer> KeySet = orders.keySet();
        if (KeySet.isEmpty()) {
            order.setOrderNumer(nextOrderNumber);
            order = orders.put(order.getOrderNumer(), order);
        } else {
            nextOrderNumber = max(KeySet);
            order.setOrderNumer(nextOrderNumber + 1);
            order = orders.put(order.getOrderNumer(), order);
        }

        writeOrder();
        return order;
    }
    

    @Override
    public List<Order> getAllOrders(String date) throws FlooringPersistenceException {
        orders.clear();
        loadOrder(date);
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order removeOrder(String date, int orderNumber) throws FlooringPersistenceException, IOException {
        getAllOrders(date);
        Order removedOrder = orders.remove(orderNumber);
        writeEditRemoveOrder(date);
        return removedOrder;
    }

    @Override
    public Order editOrder(int orderNumber, Order order, String date) throws FlooringPersistenceException, IOException {
        Order editedOrder = orders.put(orderNumber, order);
        writeEditRemoveOrder(date);
        return editedOrder;
    }

    @Override
    public List<State> getAllStates() throws FlooringPersistenceException {
        loadStates();
        return new ArrayList<>(states.values());
    }

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException {
        loadProducts();
        return new ArrayList<>(products.values());
    }

    @Override
    public int setNextOrderNumbers(String date) throws FlooringPersistenceException, IOException {
        createFile();
        loadOrder(date);
        nextOrderNumber++;
        return nextOrderNumber;
    }

    @Override
    public Order getOrder(String date, int orderNumber) throws FlooringPersistenceException {
        loadOrder(date);
        return orders.get(orderNumber);
    }


    private void updateFindFile(LocalDate date) throws FlooringPersistenceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate ld = LocalDate.now();
        String formatted = ld.format(formatter);
        findFile = ORDERS_FILEPATH + FILE_START + formatted + FILE_END;

    }

    private void createFile() throws IOException, FlooringPersistenceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate ld = LocalDate.now();
        String date = ld.format(formatter);

        File file = new File(ORDERS_FILEPATH + FILE_START + date + FILE_END);

        if (!file.createNewFile()) {
            orders.clear();
            loadOrder(date);
        } else {
            //file didn't exist, should be created
        }

    }
//Products load file

    private void loadProducts() throws FlooringPersistenceException {

        try {
            inputReader = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load products data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        inputReader.nextLine();
        while (inputReader.hasNextLine()) {
            currentLine = inputReader.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Product currentProduct = new Product(currentTokens[0]);
            currentProduct.setCostPerSqFoot(new BigDecimal(currentTokens[1]));
            currentProduct.setLaborCostPerSqFoot(new BigDecimal(currentTokens[2]));

            products.put(currentProduct.getProductType(), currentProduct);
        }
        inputReader.close();
    }

//States/taxes file
    private void loadStates() throws FlooringPersistenceException {
        try {
            inputReader = new Scanner(new BufferedReader(new FileReader(STATES_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load states data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        inputReader.nextLine();
        while (inputReader.hasNextLine()) {
            currentLine = inputReader.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            State currentState = new State(currentTokens[0]);
            currentState.setTaxRate(new BigDecimal(currentTokens[1]));

            states.put(currentState.getStateName(), currentState);
        }
        inputReader.close();
    }

    private String formatDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate ld = LocalDate.now();
        String today = ld.format(formatter);
        return today;
    }

    private void loadOrder(String date) throws FlooringPersistenceException {

        try {
            inputReader = new Scanner(new BufferedReader(new FileReader(ORDERS_FILEPATH + FILE_START + date + FILE_END)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- No Orders for that date.", e);
        }

        String currentLine;
        String[] currentTokens;
//        inputReader.nextLine();
        while (inputReader.hasNextLine()) {
            currentLine = inputReader.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Order currentOrder = new Order(Integer.parseInt(currentTokens[0]));
            currentOrder.setCustomerName(currentTokens[1]);

            State currentState = new State(currentTokens[2]);
            currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
            currentOrder.setState(currentState);

            Product currentProduct = new Product(currentTokens[4]);

            currentOrder.setAreaOfRoom(new BigDecimal(currentTokens[5]));

            currentProduct.setCostPerSqFoot(new BigDecimal(currentTokens[6]));
            currentProduct.setLaborCostPerSqFoot(new BigDecimal(currentTokens[7]));
            currentOrder.setProduct(currentProduct);

            currentOrder.setMaterialTotalCost(new BigDecimal(currentTokens[8]));
            currentOrder.setLaborTotalCost(new BigDecimal(currentTokens[9]));
            currentOrder.setTaxTotal(new BigDecimal(currentTokens[10]));
            currentOrder.setFinalTotal(new BigDecimal(currentTokens[11]));

            orders.put(currentOrder.getOrderNumer(), currentOrder);
        }
        inputReader.close();
    }

    private void writeOrder() throws FlooringPersistenceException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ORDERS_FILEPATH + FILE_START + formatDate() + FILE_END));
        } catch (IOException e) {
            throw new FlooringPersistenceException(
                    "Could not save order data.", e);
        }

        List<Order> orderValues = new ArrayList(orders.values());
        for (Order currentOrder : orderValues) {
            out.println(currentOrder.getOrderNumer() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getStateName() + DELIMITER
                    + currentOrder.getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getAreaOfRoom().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getMaterialCostPerSqFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getLaborCostPerSqFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getMaterialTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getTaxTotal().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getFinalTotal().setScale(2, RoundingMode.HALF_UP));
            out.flush();
        }
        out.close();
    }
    
        private void writeEditRemoveOrder(String date) throws FlooringPersistenceException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ORDERS_FILEPATH + FILE_START + date + FILE_END));
        } catch (IOException e) {
            throw new FlooringPersistenceException(
                    "Could not save order data.", e);
        }

        List<Order> orderValues = new ArrayList(orders.values());
        for (Order currentOrder : orderValues) {
            out.println(currentOrder.getOrderNumer() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getStateName() + DELIMITER
                    + currentOrder.getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getAreaOfRoom().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getMaterialCostPerSqFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getLaborCostPerSqFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getMaterialTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getLaborTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getTaxTotal().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                    + currentOrder.getFinalTotal().setScale(2, RoundingMode.HALF_UP));
            out.flush();
        }
        out.close();
    }

}
