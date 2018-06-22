/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

/**
 *
 * @author ashleybesser
 */
public class FlooringDuplicateOrderException extends Exception {

    public FlooringDuplicateOrderException(String message) {
        super(message);
    }

    public FlooringDuplicateOrderException(String message, Throwable cause) {
        super(message, cause);
    }

}
