package org.example.app.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ContactDeleteView {
    public Map<String, String> getData() {
        System.out.println("\nDELETE FORM");
        Map<String, String> map = new HashMap<>();
        Scanner input = new Scanner(System.in);
        System.out.print("Input id: ");
        map.put("id", input.nextLine().trim());
        return map;
    }

    public void getOutput(String output) {
        System.out.println(output);
    }
}
