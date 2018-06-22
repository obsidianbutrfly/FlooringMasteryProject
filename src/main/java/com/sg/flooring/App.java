/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring;

import com.sg.flooring.controller.FlooringController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author ashleybesser
 */


public class App {
    
    public static void main(String[] args) {
//        
//        UserIO myIo = new UserIOConsoleImpl();
//        FlooringView myView = new FlooringView(myIo);
//        FlooringDao myDao = new FlooringDaoFileImpl();
//        FlooringAuditDao myAuditDao = new FlooringAuditDaoFileImpl();
//        FlooringServiceLayerImpl myService = new FlooringServiceLayerImpl(myDao);
//        FlooringController controller = new FlooringController(myService, myView);
//        controller.run();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringController controller = ctx.getBean("controller", FlooringController.class);
        controller.run();
        
    }
    
}
