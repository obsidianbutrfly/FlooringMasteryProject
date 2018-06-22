/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.controller;

import com.sg.flooring.dao.FlooringPersistenceException;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.State;
import com.sg.flooring.service.FlooringDataValidationException;
import com.sg.flooring.service.FlooringDuplicateOrderException;
import com.sg.flooring.service.FlooringServiceLayerImpl;
import com.sg.flooring.ui.FlooringView;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author ashleybesser
 */
public class FlooringController {

    private FlooringServiceLayerImpl service;
    private FlooringView view;
    private boolean productionMode;
    private List<Product> products;
    private List<State> states;

    public FlooringController(FlooringServiceLayerImpl service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            systemEnviorment();
            while (keepGoing) {
                whichMode();
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        createOrders();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                        break;
                }
            }
            exitMessage();
        } catch (FlooringPersistenceException | FlooringDuplicateOrderException | FlooringDataValidationException | IOException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void welcomeMessage() {
        view.displayWelcomeBanner();
    }

    public int getMenuSelection() {
        int result = 0;
        boolean valid = false;
        while (!valid) {
            try {
                result = view.getMainMenuSelection();
                valid = true;
            } catch (NumberFormatException e) {
                view.displayInvalidDataError();
            }
        }
        return result;
    }

    public void displayOrders() throws FlooringPersistenceException {
        /*
        Ask user for date
        Load Orders according to the date they input
         */
        String userDate = view.getDateFromUser();
        List<Order> orders = service.getAllOrders(userDate);
        if (orders.isEmpty() == true) {
            view.displayInvalidDataError();
        } else {
            view.displayOrderList(orders);
        }
        orders.clear();
    }

    public String getDateForOrder() throws FlooringPersistenceException {
        return view.getDateFromUser();
    }

    public void createOrders() throws FlooringPersistenceException, FlooringDuplicateOrderException, FlooringDataValidationException, IOException {

        boolean addMore = true;
        view.displayAddOrderBanner();

        while (addMore) {
            whichMode();
            int orderNumber;
            LocalDate today = LocalDate.now();
            String fileDate = today.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            orderNumber = service.getNextOrderNumber(fileDate);
            String customerName = view.customerName();
            State newState = view.getStateChoice(service.getAllStates());

            BigDecimal taxRate = newState.getTaxRate();
            Product product = view.getProductChoice(service.getAllProducts());
            BigDecimal material = product.getCostPerSqFoot();
            BigDecimal labor = product.getLaborCostPerSqFoot();
            BigDecimal area = areaOfRoom();
            Order currentOrder = new Order(orderNumber);
            currentOrder.setCustomerName(customerName);
            currentOrder.setState(newState);
            currentOrder.setTaxRate(taxRate);
            currentOrder.setProduct(product);
            currentOrder.setMaterialCostPerSqFoot(material);
            currentOrder.setLaborCostPerSqFoot(labor);
            currentOrder.setAreaOfRoom(area);

            //display current order
            view.displayOrder(currentOrder);

            //ask if the order is correct
            String orderCorrect = view.goodOrder();
            //if it is correct then add it to the file
            if (orderCorrect.equalsIgnoreCase("y")) {
                if (productionMode) {
                    service.createOrder(currentOrder, fileDate);
                    view.displayAddOrderSuccess();
                }
            }

            String addAnotherOrder = view.anotherOrder();
            if (addAnotherOrder.equalsIgnoreCase("y")) {
            } else {
                addMore = false;
            }
        }
    }

    public void removeOrder() throws FlooringPersistenceException, IOException {
        view.displayRemoveOrderBanner();
        //I need to pull orders according to the date user asks for
        //Then load the orders according to that date
        //then get the order number they want to remove
        //remove that from the file
        //success
        boolean removeAnother = false;
        boolean validChoice = false;
        int orderNumber = 0;

        String removeDate = view.getDateFromUser();
        List<Order> orderlist = service.getAllOrders(removeDate);
        view.displayOrderList(orderlist);

        while (!validChoice) {
            orderNumber = getOrderNumber();
            validChoice = service.validateOrderChoice(orderNumber, removeDate);

            if (validChoice) {

                while (!removeAnother) {
                    whichMode();
                    String removeConfirm = view.confirmRemove();
                    if (removeConfirm.equalsIgnoreCase("y")) {
                        if (productionMode) {
                            service.removeOrder(removeDate, orderNumber);
                        }
                        view.displayRemoveOrderSuccessBanner();
                    }
                    String removeAnotherOrder = view.removeAnotherOrder();
                    if (removeAnotherOrder.equalsIgnoreCase("y")) {
                    } else {
                        removeAnother = true;
                    }
                }
            }
        }

    }

    private void editOrder() throws FlooringPersistenceException, IOException, FlooringDataValidationException {
        /*
        Ask user for date of orders they want to pull
        Confirm if this is the right file...
        Ask for order number they want to edit
        Display the order they are editing
        switch for the differnt options to edit
        1. Customer name
        2. State
        3. Product Type
        4. Area of Room
        5. Exit
         
        then remove the order
        add the order back
        success 
         */

        boolean orderMatch = true;
        boolean validChoice = false;
        int orderNumber = 0;
        boolean keepEditing = false;
        boolean editAnother = false;
        
    while (!editAnother) {
        String editDate = view.getDateFromUser();
        List<Order> orderlist = service.getAllOrders(editDate);
        view.displayOrderList(orderlist);

            while (!validChoice) {
                orderNumber = getOrderNumber();
                validChoice = service.validateOrderChoice(orderNumber, editDate);
                if (validChoice) {
                    do {
                        whichMode();
                        Order editedOrder = service.getOrder(editDate, orderNumber);
                        view.displayOrder(editedOrder);
//                    if (editedOrder == null) {
//                        view.displayOrderNotFoundBanner();
//                        orderMatch = false;
//                    } else {
//                        orderMatch = true;
                        int multiMenuSelect;

                        while (!keepEditing) {

                            multiMenuSelect = getEditMenuSelection();
                            switch (multiMenuSelect) {
                                case 1:
                                    String customerName = view.customerName();
                                    editedOrder.setCustomerName(customerName);
                                    break;
                                case 2:
                                    State newState = view.getStateChoice(service.getAllStates());
                                    editedOrder.setState(newState);
                                    break;
                                case 3:
                                    Product newProduct = view.getProductChoice(service.getAllProducts());
                                    editedOrder.setProduct(newProduct);
                                    break;
                                case 4:
                                    BigDecimal newArea = view.areaOfRoom();
                                    editedOrder.setAreaOfRoom(newArea);
                                    break;
                                case 5:
                                    exitMessage();
                                    keepEditing = true;
                                    break;
                                default:
                                    unknownCommand();
                            }
                            view.displayOrder(editedOrder);
                        }
                        if (productionMode) {
                            service.editOrder(orderNumber, editedOrder, editDate);
                            view.displayOrder(editedOrder);

                        }
                        view.displayEditOrderSuccessBanner();
                    } while (!orderMatch);

                }
                String editMore = view.editAnotherOrder();
                if (editMore.equalsIgnoreCase("y")) {
                    editAnother = false;
                }
            }
        }
    }
//        }
//    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    public int getEditMenuSelection() {
        int result = 0;
        boolean valid = false;
        while (!valid) {
            try {
                result = view.getEditMenuSelection();
                valid = true;
            } catch (NumberFormatException e) {
                view.displayInvalidDataError();
            }
        }
        return result;
    }

    public int getOrderNumber() {
        int result = 0;
        boolean valid = false;
        while (!valid) {
            try {
                result = view.getOrderNumber();
                valid = true;
            } catch (NumberFormatException e) {
                view.displayInvalidDataError();
            }
        }
        return result;
    }

    private BigDecimal areaOfRoom() {
        BigDecimal area = new BigDecimal(0);
        boolean valid = false;
        while (!valid) {
            try {
                area = view.areaOfRoom();
                valid = true;
            } catch (NumberFormatException e) {
                view.displayInvalidDataError();
            }
        }
        return area;
    }

    public String getDateFromUser() throws FlooringPersistenceException {
        String date = view.getDateFromUser();
        return date;
    }

    public void exitMessage() {
        view.displayExitMessage();
    }

    private void whichMode() {
        if (productionMode) {
            view.displayBannerMode("Production");
        } else {
            view.displayBannerMode("Training");
        }
    }

    private void systemEnviorment() throws FlooringPersistenceException, IOException {
        productionMode = service.systemEnviornment();
        products = service.getAllProducts();
        states = service.getAllStates();
    }

}
