/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.ui;

import com.sg.flooring.dao.FlooringDao;
import com.sg.flooring.dao.FlooringDaoFileImpl;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.State;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ashleybesser
 */
public class FlooringView {

    Scanner inputReader = new Scanner(System.in);

    FlooringDao dao = new FlooringDaoFileImpl();
    private UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void displayWelcomeBanner() {
        io.print("-*-*- Welcome to the Flooring Application -*-*-");
    }

    public void printMenu() {
        io.print("Main Menu");
        io.print("1. Display Orders.");
        io.print("2. Add an Order.");
        io.print("3. Edit an Order.");
        io.print("4. Remove an Order.");
        io.print("5. Exit program");
    }

    public int getMainMenuSelection() {
        printMenu();
        return io.readInt("Please Select from the above choices", 1, 5);
    }

    public void printEditMenu() {
        io.print("Edit Menu");
        io.print("1. Update Customer Name.");
        io.print("2. Update State.");
        io.print("3. Update Product Type.");
        io.print("4. Update Area of Room.");
        io.print("5. Exit program");
    }

    public int getEditMenuSelection() {
        printEditMenu();
        return io.readInt("Please Select from the Above Choices", 1, 5);
    }

    public void displayAllOrdersBanner() {
        io.print("==== All Orders for Today ====");
    }

    public void displayAddOrderBanner() {
        io.print("==== Add Order Menu ====");
    }

    public void displayAddOrderSuccess() {
        io.print("==== Order Added Successfully ====");
    }

    public void displayRemoveOrderBanner() {
        io.print("==== Remove Order Menu ====");
    }

    public void displayRemoveOrderSuccessBanner() {
        io.print("==== Order Removed Successfully ====");
    }

    public void displayEditOrderBanner() {
        io.print("==== Edit Order Menu ====");
    }

    public void displayEditOrderSuccessBanner() {
        io.print("==== Order Edited Successfully ====");
    }

    public void displayOrderList(List<Order> orderList) { //4 .listing current dvds in library
        for (Order currentOrder : orderList) {
            io.print("Order Number: " + currentOrder.getOrderNumer());
            io.print("Customer Name:" + currentOrder.getCustomerName());
            io.print("State:" + currentOrder.getStateName());
            io.print("Tax Rate:" + currentOrder.getTaxRate());
            io.print("Product Type:" + currentOrder.getProductType());
            io.print("Area of Room:" + currentOrder.getAreaOfRoom());
            io.print("Material Cost Per Sq Foot:" + currentOrder.getMaterialCostPerSqFoot());
            io.print("Labor Cost Per Sq Foot:" + currentOrder.getLaborCostPerSqFoot());
            io.print("Material Cost:" + currentOrder.getMaterialTotalCost());
            io.print("Labor Cost:" + currentOrder.getLaborTotalCost());
            io.print("Tax Total:" + currentOrder.getTaxTotal());
            io.print("Final Total:" + currentOrder.getFinalTotal());
            io.print("");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayExitMessage() {
        io.print("=== Goodbye ===");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }

    public String getDateFromUser() {
        String text = io.readString("Please Enter Order Date MMDDYYY");
        return text;
    }

    public String customerName() {
        return io.readString("Please Enter Customer Name:");

    }

    public State getStateChoice(List<State> states) {
        int list = 1;
        int stateChoice = 0;
        boolean valid = false;
        for (State currentState : states) {
            io.print(list + ": " + currentState.getStateName() + " Tax Rate %: " + currentState.getTaxRate());
            list++;
        }
        while (!valid) {
            try {
                stateChoice = Integer.parseInt(io.readString("Please select from list above: "));
                if (stateChoice > 0 && stateChoice < states.size()+1) {
                    valid = true;
                } else {
                    displayInvalidDataError();
                }
            } catch (NumberFormatException e) {
                displayInvalidDataError();
            }

        }
        return states.get(stateChoice -1);
    }

    public Product getProductChoice(List<Product> products) {
        int list = 1;
        boolean valid = false;
        int productChoice = 0;
        for (Product currentProduct : products) {
            io.print(list + ": " + currentProduct.getProductType() + ":|| Material Cost Per Sq Foot $" + currentProduct.getCostPerSqFoot()
                    + ":|| Labor Cost Per Sq Foot $" + currentProduct.getLaborCostPerSqFoot());
            list++;
        }
        while (!valid) {
            try {
                productChoice = Integer.parseInt(io.readString("Please select from list above: "));
                if (productChoice > 0 && productChoice < products.size()+1) {
                    valid = true;
                } else {
                    displayInvalidDataError();
                }
            } catch (NumberFormatException e) {
                displayInvalidDataError();
            }
        }
        return products.get(productChoice -1);
    }

    public BigDecimal areaOfRoom() {
        return io.readBigDecimal("Enter Area of Room:");
    }

    public String goodOrder() {
        return io.readString("Does this order look correct? Y/N");
    }

    public String anotherOrder() {
        return io.readString("Do you want to add another Order? Y/N");
    }

    public String stupidBlankInput() {
        return io.readString("");
    }

    public void displayOrder(Order currentOrder) {
        io.print("Order Number: " + currentOrder.getOrderNumer());
        io.print("Customer Name:" + currentOrder.getCustomerName());
        io.print("State:" + currentOrder.getStateName());
        io.print("Tax Rate:" + currentOrder.getTaxRate());
        io.print("Product Type:" + currentOrder.getProductType());
        io.print("Area of Room:" + currentOrder.getAreaOfRoom());
        io.print("Material Cost Per Sq Foot:" + currentOrder.getMaterialCostPerSqFoot());
        io.print("Labor Cost Per Sq Foot:" + currentOrder.getLaborCostPerSqFoot());
    }

    public int getOrderNumber() {
        return io.readInt("Which Order Number do you want to update?");
    }

    public String confirmRemove() {
        return io.readString("Are you sure this is the orer you want to remove? Y/N");
    }

    public String removeAnotherOrder() {
        return io.readString("Do you want to remove another Order? Y/N");
    }
    
    public String editAnotherOrder(){
        return io.readString("Do you want to edit another Order? Y/N");
    }

    public void displayOrderNotFoundBanner() {
        io.print("==== Order Not Found ====");
    }

    public void displayUnknownCommandBanner() {
        io.print("=== Unknown Option Selected ===");
    }

    public void displayInvalidDataError() {
        io.print("=== Not a Valid Choice ===");
    }

    public void displayBannerMode(String mode) {
        io.print("");
        io.print(" *~*~*~*~*~ " + mode + " *~*~*~*~*~ ");
    }

    public void displayTrainingModeSuccess() {
        io.print("Success! You did everything correct!");
        io.print("You are in training mode, the file will not update");
    }

    public void displaySelecStateBanner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
