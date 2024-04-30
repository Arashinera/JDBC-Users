package org.example.app.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ContactCreateView {
    public Map<String, String> getData() {
        System.out.println("\nCREATE FORM");
        Map<String, String> map = new HashMap<>();
        Scanner input = new Scanner(System.in);
        System.out.print("Input user name: ");
        map.put("user_name", input.nextLine().trim());
        System.out.print("Input email in format example@mail.com: ");
        map.put("email", input.nextLine().trim());
        return map;
    }

    public void getOutput(String output) {
        System.out.println(output);
    }
}
