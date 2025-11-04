import java.io.*;

public class OrderingManagementSystem {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] accounts = new String[5];
        String customerEmail = null, customerPassword = null, customerName = null;
        int accountCount = 0;
        
        String[] menuOptions = { "Sinigang", "Adobo", "Sisig", "Lechon", "Caldereta", "Halo-Halo", "Gulaman" };
        String[] menuCost = { "75", "115", "119", "350", "70", "65", "40" };
        
        //2d Array - [5] is accounts and [99] if the limit of orders you can add to cart
        String[][] customerCart = new String[5][99];
        int customerCartAmount = 0;
                
        String[][] purchasedOrders = new String[5][99];
        
        
        while (true) {
            //Update accountCount always
            int checkAccounts = 0;
            for (String i : accounts) {
                //If i is not null then increment accountCount
                if (i != null) checkAccounts++;
            }
            accountCount = checkAccounts;
            
            //-----------------------------------------
            System.out.print("\n*=*=*=*[Ordering App Name]*=*=*=*" + 
                    "\nCreate Account" + "\nLogin\n" + "-------------------------------------\n"
            );

            String input = null;
            try {
                input = br.readLine();
            } catch (IOException e) {
                System.out.println("Error!");
                continue;
            }
            
            switch (input.toUpperCase().trim()) {
                case "LOGIN":
                    if (accountCount == 0) {
                        System.out.println("\nNo registered account found!");
                        continue;
                    }
                    
                    int loginIndex = login(br, accounts, customerEmail, customerPassword, customerName, accountCount); //<Call Function
                    if (loginIndex != -1) { //<Proceed to login when you sucessfully input correct login details ad function doesn't return (-1) anymore
                        String loggedInEmail = accounts[loginIndex].split(",")[0];
                        orderingInterface(br, accounts, customerEmail, customerPassword, customerName, accountCount, menuOptions, menuCost, customerCart, loggedInEmail, loginIndex, purchasedOrders, customerCartAmount);
                    }
                    break;
                    
                case "CREATE ACCOUNT":
                    if (accountCount >= 5) {
                        System.out.println("\nRegistered Accounts limit reached!");
                        continue;
                    }
                    
                    
                    customerEmail = email(br); //<Call Function
                    if (customerEmail == null) continue;
                    
                    //Prevent registering the same emails
                    for (int i = 0; i < accountCount; i++) {
                        if (accounts[i].startsWith(customerEmail + ",")) {
                            System.out.println("\nThis email has an existing account already!");
                        }
                    }
                    
                    customerPassword = password(br); //<Call Function
                    if (customerPassword == null) continue;
                    customerName = username(br); //<Call Function
                    if (customerName == null) continue;
                    
                    
                    //Register Account
                    accounts[accountCount] = customerEmail + "," + customerPassword + "," + customerName;
                    accountCount++;
                    
                    try {
                        System.out.println("\nRegistration In Process\nPlease Wait...");
                        Thread.sleep(2000);
                        System.out.println("\nRegistration Successful");
                    } catch (InterruptedException e) {
                    }
                    break;
            }
        }
    }
    
    public static String email(BufferedReader br) {
        while (true) {
            System.out.print("\nEnter your Email: ");
            try {
                String enterEmail = br.readLine();
                if (enterEmail == null || enterEmail.trim().isEmpty()) return null;
                if (!enterEmail.endsWith("@gmail.com") && !enterEmail.endsWith("@email.com")) {
                    System.out.println("Invalid Email Address!");
                    continue;
                }
                return enterEmail.trim(); //<Return email value
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    public static String password(BufferedReader br) {
        while (true) {
            System.out.print("\nEnter your Password: ");
            try {
                String enterPassword = br.readLine();
                if (enterPassword == null || enterPassword.isEmpty()) return null;
                
                if (enterPassword.contains(" ")) {
                    System.out.println("Password cannot contain spaces!");
                    continue;
                }
                
                if (enterPassword.length() < 8) {
                    System.out.println("Password must at least have a minimum of 8 characters");
                    continue;
                }
                return enterPassword; //<Return password value
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    public static String username(BufferedReader br) {
        while (true) {
            System.out.print("\nSet a username: ");
            try {
                String enterUsername = br.readLine();
                if (enterUsername == null || enterUsername.isEmpty()) {
                    System.out.println("Username cannot be empty!");
                    continue;
                }
                return enterUsername.trim(); //<Return username value
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    public static int login(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount) {
        while (true) {
            System.out.print("\nEnter your Email: ");
            String enterYourEmail = null;
            try {
                enterYourEmail = br.readLine();
                if (enterYourEmail == null || enterYourEmail.trim().isEmpty()) return -1;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            
            System.out.print("\nEnter your Password: ");
            String enterYourPassword = null;
            try {
                enterYourPassword = br.readLine();
                if (enterYourPassword == null || enterYourPassword.trim().isEmpty()) return -1;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            for (int i = 0; i < accountCount; i++) {
                String[] accountPart = accounts[i].split(",");
                String checkEmail = accountPart[0];
                String checkPassword = accountPart[1];
                String checkCustomerName = accountPart[2];
                
                //If your entered email and password matches current looped index[i] of String accounts then login
                if (enterYourEmail.equals(checkEmail) && enterYourPassword.equals(checkPassword)) {
                    try {
                        System.out.println("\nProcessing\nPlease Wait...");
                        Thread.sleep(2000);
                        System.out.println("\nWelcome! " + checkCustomerName);
                    } catch (InterruptedException e) {
                    }
                    return i; //< i will return current i value and will work later since it will never be (-1)
                } else {
                    try {
                        System.out.println("\nProcessing\nPlease Wait...");
                        Thread.sleep(2000);
                        System.out.println("\nInvalid! Try Again\n");
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }
    
    
    public static void orderingInterface(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount, String[] menuOptions, String[] menuCost, String[][] customerCart, String loggedInEmail, int loginIndex, String[][] purchasedOrders, int customerCartAmount) {
        while (true) {
            try {
                System.out.println("\nType 'ORDERS' to check your orders");
                System.out.println("Type 'PURCHASED' to check your purchased orders");
                System.out.println("=== Menu Options ===\nSelect a food:\n");
                for (int i = 0; i < menuOptions.length; i++) {
                    System.out.println((i + 1) + ". " + menuOptions[i] + "\nPrice: $" + menuCost[i] + "\n");
                }
                    
                String selection = br.readLine();
                if (selection == null || selection.trim().isEmpty()) {
                    while (true) {
                        System.out.println("Are you sure you want to Logout?\nPress Enter to Confirm");
                        String input = br.readLine();
                        if (input == null || input.trim().isEmpty()) break;
                        
                        if (input.toUpperCase().trim().equals("YES")){
                            try {
                                System.out.println("\nLogging Out\nPlease Wait...");
                                Thread.sleep(2000);
                                System.out.println("Logged Out\n");
                            } catch (InterruptedException e) {
                            }
                            return;
                        }
                    }
                }
                
                
                if (selection.toUpperCase().trim().equals("ORDERS")) {
                    orders(br, accounts, customerEmail, customerPassword, customerUsername, accountCount, menuOptions, menuCost, customerCart, loggedInEmail, loginIndex, customerCartAmount, purchasedOrders);
                    continue;
                }
                
                if (selection.toUpperCase().trim().equals("PURCHASED")) {
                    purchasedOrders(br, accounts, customerEmail, customerPassword, customerUsername, accountCount, menuOptions, menuCost, loggedInEmail, loginIndex, purchasedOrders);
                    continue;
                }
                
                //Proceed here if it doesn't match any of the string commands above
                int selectionInt = -1;
                try {
                    selectionInt = Integer.parseInt(selection);
                } catch (NumberFormatException e) {
                    System.out.println("Must be an integer!");
                    continue;
                }
                
                if (selectionInt < 0 || selectionInt > menuOptions.length) {
                    System.out.println("Invalid Selection");
                    continue;
                }
                
                for (int i = 0; i < menuOptions.length; i++) {
                    boolean selectionComplete = false;
                    if (selectionInt == (i + 1)) {
                        while (!selectionComplete) {
                            System.out.println("Press Enter to Cancel\nSelect Quantity");
                            String quantity = br.readLine();
                            if (quantity == null || quantity.trim().isEmpty()) break;
                            
                            int quantityInt = 0;
                            try {
                                quantityInt = Integer.parseInt(quantity);
                            } catch (NumberFormatException e) {
                                System.out.println("Must be an integer!");
                                continue;
                            }
                            
                            int totalCost = Integer.parseInt(menuCost[i]) * quantityInt;
                            
                            System.out.println("\n=== YOU SELECTED ===");
                            System.out.println(menuOptions[i] + " $" + totalCost + "\nQuantity: " + quantityInt + "\n");
                        
                            System.out.println("Press Enter to Cancel\nInput 'YES' to add to your orders");
                            String input = br.readLine();
                            if (input == null || input.trim().isEmpty()) break;
                            
                            if (input.toUpperCase().trim().equals("YES")) {
                                for (int k = 0; k < customerCart[loginIndex].length; k++) {
                                    if (customerCart[loginIndex][k] == null) {
                                        customerCart[loginIndex][k] = menuOptions[i] + "," + menuCost[i] + "," + quantity;
                                        System.out.println("\n=== SUCCESSFULLY ADDED TO YOUR ORDERS ===");
                                        System.out.println(menuOptions[i] + "\nPrice: $" + totalCost + "\nQuantity: " + quantityInt + "\n");
                                        System.out.println("=========================================");
                                        break;
                                    }
                                }
                                
                                while (true) {
                                    System.out.println("\nPress Enter to Exit");
                                    String exit = br.readLine();
                                    if (exit == null || exit.trim().isEmpty()) {
                                        selectionComplete = true;
                                        break;
                                    } else {
                                        System.out.print("Inavlid Input!");
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    public static void orders(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount, String[] menuOptions, String[] menuCost, String[][] customerCart, String loggedInEmail, int loginIndex, int customerCartAmount, String[][] purchasedOrders) {
        while (true) {
            try {
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) continue;
                    String[] accountsPart = accounts[i].split(",");
                    
                    if (accountsPart[0].equals(loginIndex)) {
                        //Shift data from array
                        for (int j = 0; j < customerCart[i].length - 1; j++) {
                            //Replace the current index with the data of index after
                            customerCart[i][j] = customerCart[i][j + 1];
                        }
                        //Set the last slot of array as null always
                        customerCart[i][customerCart[i].length - 1] = null;
                        break;
                    }
                }
                
                
                //Update customerCartAmount
                int cartOrders = 0;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) break;
                    String[] accountPart = accounts[i].split(",");
                
                    //If logged in account matches accounts data then proceed
                    if (accountPart[0].equals(loggedInEmail)) {
                       for (int j = 0; j < customerCart[i].length; j++) {
                            if (customerCart[i][j] != null) cartOrders++;
                        }
                        break;
                    }
                }
                customerCartAmount = cartOrders;
                
                
                System.out.println("\n=== YOUR ORDERS ===");
                System.out.println("Amount: " + customerCartAmount + "/5\n");
                
                //Loop print each product you added
                for (int i = 0; i < customerCart[loginIndex].length; i++) {
                    if (customerCart[loginIndex][i] == null) continue;
                    String[] customerCartParts = customerCart[loginIndex][i].split(",");
                    System.out.println("#" + (i + 1) + customerCartParts[0] + "\nPrice: $" + customerCartParts[1] + "\nQuantity: " + customerCartParts[2] + "\n");
                }
                System.out.println("\nType 'CHECKOUT' to proceed to checkout\nInput a number to Select an Order");
                System.out.print("Input: ");
                String input = br.readLine();
                if (input == null || input.trim().isEmpty()) break;
                
                boolean done = false;
                if (input.toUpperCase().trim().equals("CHECKOUT")) {
                    while (!done) {
                        System.out.print("=== Select ===\n'DINE IN'\n'TAKE OUT'\nInput:");
                        String selection = br.readLine();
                        if (selection == null || selection.trim().isEmpty()) break;

                        if (selection.toUpperCase().trim().equals("DINE IN") || selection.toUpperCase().trim().equals("TAKE OUT")) {
                            while (!done) {
                                System.out.print("\n=== Select Payment Method ===\nType 'CASH' for cash\n Type 'CC' for Credit Card\nInput:");
                                String paymentmethod = br.readLine();
                                if (paymentmethod == null || paymentmethod.trim().isEmpty()) break;
                                        
                                switch (paymentmethod.toUpperCase().trim()) {
                                    case "CASH":
                                    case "CC":
                                        //Order ID Generator
                                        String orderID = null;
                                        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                                            String id = "";
                                            for (int j = 0; j < 8; j++) {
                                            int random = (int)(Math.random() * characters.length());
                                            id += characters.charAt(random);
                                            }
                                        orderID = id;
                                        
                                        //-----------------------------
                                        int total = 0;

                                        System.out.println("\n=== ORDER SUMMARY ===");
                                        for (int i = 0; i < customerCart[loginIndex].length; i++) {
                                            if (customerCart[loginIndex][i] == null) continue;
                                            String[] parts = customerCart[loginIndex][i].split(",");
                                            int price = Integer.parseInt(parts[1]);
                                            total += price;
                                            System.out.println(parts[0] + "\nPrice: $" + parts[1] + "\nQuantity: " + parts[2] + "\n");
                                        }

                                        System.out.println("TOTAL: $" + total);
                                        System.out.print("\nProceed with checkout? (YES to confirm): ");
                                        String confirm = br.readLine();

                                        if (confirm != null && confirm.equalsIgnoreCase("YES")) {
                                            if (paymentmethod.toUpperCase().trim().equals("CC")) {
                                                try {
                                                    System.out.println("\nProcessing Payment\nPlease Wait...");
                                                    Thread.sleep(2000);
                                                    System.out.println("Payment Successful\n");
                                                } catch (InterruptedException e) {
                                                }
                                                System.out.println("PAYMENT SUCCESSFUL!");
                                            }

                                            //Start with orderID and selection type(DINE IN/TAKE OUT)
                                            String orderData = orderID + "," + selection.toUpperCase().trim();  

                                            //Add all items from cart
                                            for (int i = 0; i < customerCart[loginIndex].length; i++) {
                                                if (customerCart[loginIndex][i] == null) continue;
                                                String[] parts = customerCart[loginIndex][i].split(",");
                                                orderData += "," + parts[0] + "," + parts[1] + "," + parts[2];
                                            }

                                            //Append total cost at the end
                                            orderData += "," + total;

                                            //Save order data to purchasedOrders
                                            for (int i = 0; i < purchasedOrders[loginIndex].length; i++) {
                                                if (purchasedOrders[loginIndex][i] == null) {
                                                    purchasedOrders[loginIndex][i] = orderData;
                                                    break;
                                                }
                                            }

                                            //Clear the whole cart
                                            for (int i = 0; i < customerCart[loginIndex].length; i++) {
                                                customerCart[loginIndex][i] = null;
                                            }

                                            System.out.println("\nCheckout Complete!");
                                            done = true;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    continue;
                }
                
                
                int inputInt = -1;
                try {
                    inputInt = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Error");
                }
                
                if (inputInt < 0 || inputInt >= customerCartAmount) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                
                String orderName = null;
                
                boolean completed = false;
                while (!completed) {
                    for (int i = 0; i < customerCart[loginIndex].length; i++) {
                        if (customerCart[loginIndex][i] == null) continue;
                        String[] customerCartParts = customerCart[loginIndex][i].split(",");
                        
                        if (inputInt == i) {
                            System.out.println("YOU SELECTED:");
                            System.out.println("---------------------------------");
                            System.out.println(customerCartParts[0]);
                            System.out.println("Price $:" + customerCartParts[1]);
                            System.out.println("Quantity: " + customerCartParts[2]);
                            System.out.println("---------------------------------");       
                            
                            orderName = customerCartParts[0];
                            break;
                        }
                    }
                    
                    System.out.println("\nType 'DELETE' to remove selected order");
                    System.out.print("Input: ");
                    String input2 = br.readLine();
                    if (input2 == null || input2.trim().isEmpty()) break;
                    
                    switch (input2.toUpperCase().trim()) {
                        case "DELETE":
                            //Delete selected product
                            for (int i = 0; i < accounts.length; i++) {
                                if (accounts[i] == null) continue;
                                String[] accountsPart = accounts[i].split(",");
                                
                                //Ensure it delete inside correct account that matches
                                if (accountsPart[0].equals(loggedInEmail)) {
                                    for (int j = 0; j < customerCart[i].length; j++) {
                                        if (customerCart[i][j] == null) continue;
                                        String[] customerCartParts = customerCart[i][j].split(",");
                                        
                                        //if name of selected product matches ordername then delete product
                                        if (customerCartParts[0].equals(orderName)) {
                                            System.out.println("Order Successfully Deleted!");
                                            customerCart[i][j] = null;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            break;
                    }
                    
                    while (!completed) {
                        System.out.println("Press Enter to Return");
                        String input3 = br.readLine();
                        if (input3 == null || input3.trim().isEmpty()) {
                            completed = true;
                            break;
                        } else {
                            System.out.println("Invalid Input!");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
                                      
    public static void purchasedOrders(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount, String[] menuOptions, String[] menuCost, String loggedInEmail, int loginIndex, String[][] purchasedOrders) {
        while (true) {
            System.out.println("\n------------ RECEIPT ------------");

            for (int i = 0; i < purchasedOrders[loginIndex].length; i++) {
                if (purchasedOrders[loginIndex][i] == null) continue;

                String[] parts = purchasedOrders[loginIndex][i].split(",");

                System.out.println("\n---------------------------------");
                System.out.println("ORDER ID: " + parts[0]);
                
                //Selection (DINE IN / TAKE OUT)
                System.out.println(parts[1] + "\n");

                //Read all orders (name, price, qty)
                //Start at 2 since orderID is (2) and selection is (1)
                for (int j = 2; j < parts.length - 1; j += 3) {
                    //This part is a safety check to prevent error if the loop reaches equal or more than the length
                    if (j + 2 >= parts.length - 1) break;
                    /*
                    Example: orderID(0),selection(1),name(2),price(3),quantity(4)
                    when j finishes looping 2, it becomes:
                    j = 2 and since j += 3 then j is now 5 so:
                    (j + 3 = 5)name, (j + 1 = 3 + 3 = 6)price, (j + 2 = 4 + 3 = 7)quantity
                    so now its 5,6,7 (234567 = name,price,qty,name,price,qty) 
                    
                    total cost wont be read by the loop since its (parts.length - 1)
                    so if lets say you in this example, the length of this order is 6 - 1 so now its 5
                    and since total is 6 then it wont read that part as loop never reachers the value since
                    the safety check prevents reading 5 as j will reach out of bounds from loop
                    ("orderID,selection,name,price,qty,total" = 6 - 1 = 5)
                    */
                    System.out.println(parts[j]); //Item Name Starts at 2
                    System.out.println("Price: $" + parts[j + 1]); //Price Starts at 3
                    System.out.println("Quantity: " + parts[j + 2] + "\n"); //Quantity Starts at 4
                }

                //Total (0, 1, 2, 3, 4, 5 is id,selection,name,price,qty,total)
                //Arrays starts at 0 thats why it reads total since total is 5
                System.out.println("TOTAL: $" + parts[parts.length - 1]);
                System.out.println("---------------------------------\n");
            }

            System.out.println("Press Enter to Return");
            try {
                String input = br.readLine();
                if (input == null || input.trim().isEmpty()) break;
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}