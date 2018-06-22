/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;


/**
 *
 * @author ashleybesser
 */
public class Order {

    /*
OrderNumber,
CustomerName,
State,
TaxRate,
ProductType,
Area,
CostPerSquareFoot,
LaborCostPerSquareFoot,
MaterialCost,
LaborCost,
Tax,
Total
     */
    private int orderNumer;
    private String customerName;
    private String stateName;
    private BigDecimal taxRate;
    private BigDecimal areaOfRoom;
    private String productType;
    private BigDecimal materialCostPerSqFoot;
    private BigDecimal laborCostPerSqFoot;
    private BigDecimal materialTotalCost;
    private BigDecimal laborTotalCost;
    private BigDecimal taxTotal;
    private BigDecimal finalTotal;
//    private BigDecimal subTotal;
    private State state;
    private Product product;

    public Order(int orderNumer) {
        this.orderNumer = orderNumer;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderNumer() {
        return orderNumer;
    }

    public void setOrderNumer(int orderNumer) {
        this.orderNumer = orderNumer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAreaOfRoom() {
        return areaOfRoom;
    }

    public void setAreaOfRoom(BigDecimal areaOfRoom) {
        this.areaOfRoom = areaOfRoom;
    }

    public BigDecimal getMaterialCostPerSqFoot() {
        return product.getCostPerSqFoot();
    }

    public void setMaterialCostPerSqFoot(BigDecimal materialCostPerSqFoot) {
        this.materialCostPerSqFoot = materialCostPerSqFoot;
    }

    public BigDecimal getLaborCostPerSqFoot() {
        return product.getLaborCostPerSqFoot();
    }

    public void setLaborCostPerSqFoot(BigDecimal laborCostPerSqFoot) {
        this.laborCostPerSqFoot = laborCostPerSqFoot;
    }

    public BigDecimal getMaterialTotalCost() {
        return materialTotalCost;
    }

    public void setMaterialTotalCost(BigDecimal materialTotalCost) {
        this.materialTotalCost = materialTotalCost;
    }


    public String getStateName() {
        return state.getStateName();
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProductType() {
        return product.getProductType();
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getLaborTotalCost() {
        return laborTotalCost;
    }

    public void setLaborTotalCost(BigDecimal laborTotalCost) {
        this.laborTotalCost = laborTotalCost;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

//    public BigDecimal getSubTotal() {
//        return subTotal;
//    }
//
//    public void setSubTotal(BigDecimal subTotal) {
//        this.subTotal = subTotal;
//    }
//

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumer != other.orderNumer) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.areaOfRoom, other.areaOfRoom)) {
            return false;
        }
        if (!Objects.equals(this.materialCostPerSqFoot, other.materialCostPerSqFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqFoot, other.laborCostPerSqFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialTotalCost, other.materialTotalCost)) {
            return false;
        }
        if (!Objects.equals(this.laborTotalCost, other.laborTotalCost)) {
            return false;
        }
        if (!Objects.equals(this.taxTotal, other.taxTotal)) {
            return false;
        }
        if (!Objects.equals(this.finalTotal, other.finalTotal)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    

}
