/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author ashleybesser
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner inputReader = new Scanner(System.in);
    String numInt;

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        double numD = inputReader.nextDouble();
        inputReader.nextLine();
        return numD;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double minMaxD;
        do {
            System.out.println(prompt);
            minMaxD = inputReader.nextDouble();
            inputReader.nextLine();

        } while (minMaxD < min || minMaxD > max);

        return minMaxD;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float numF = inputReader.nextFloat();
        inputReader.nextLine();
        return numF;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float minMaxF;
        do {
            System.out.println(prompt);
            minMaxF = inputReader.nextFloat();
            inputReader.nextLine();
        } while (minMaxF < min || minMaxF > max);
        return minMaxF;
    }

    @Override
    public int readInt(String prompt) {
        boolean goodChoice = false;
        while (!goodChoice) {
            print(prompt);
            numInt = inputReader.nextLine();
            if (numInt.isEmpty()) { //checking to see if there is input
                goodChoice = false;
            } else {
                goodChoice = true;
            }
        }
        return Integer.parseInt(numInt);
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean goodChoice = false;
        boolean inRange = false;
        int intMinMax = 0;
        while (!inRange) {
            inRange = false;
            goodChoice = false;
            intMinMax = readInt(prompt);

            if (intMinMax >= min && intMinMax <= max) {
                inRange = true;
            } else {
                goodChoice = true;
            }
        }
        return intMinMax;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        long wordLong = inputReader.nextLong();
        return wordLong;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long minMaxLong;
        do {
            System.out.println(prompt);
            minMaxLong = inputReader.nextLong();
        } while (minMaxLong < min || minMaxLong > max);
        return minMaxLong;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String giveString = inputReader.nextLine();
        return giveString;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean goodChoice = true;
        BigDecimal bigD = new BigDecimal("0");
        do {
            System.out.println(prompt);
            if (inputReader.hasNextBigDecimal()) {
                bigD = inputReader.nextBigDecimal();
                inputReader.nextLine();
                goodChoice = false;
            } else {
                inputReader.next();
            }
        } while (goodChoice);
        return bigD;
    }

}
