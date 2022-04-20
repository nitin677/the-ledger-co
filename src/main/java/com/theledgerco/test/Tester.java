package com.theledgerco.test;

import com.theledgerco.request.LedgerCommandHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {
    public static void main(String args[]) {
        //Expecting the input file path to be provided as an argument while running the tester
        // Eg: "/Users/nitpatel/personal/the-ledger-co/src/main/resources/input2";
        String filePath = args[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String input = null;
            LedgerCommandHandler handler = LedgerCommandHandler.getInstance();
            while ((input = br.readLine()) != null) {
                String response = handler.submitCommand(input);
                System.out.println(input+" : "+response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
