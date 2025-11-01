import java.io.*;

public class OrderingManagementSystem {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] accounts = new String[5];
        String customerEmail = null, customerPassword = null, customerName = null;
        int accountCount = 0;
        
        String[] menuOptions = { "Sinigang", "Adobo", "Sisig", "Lechon", "Caldereta", "Halo-Halo", "Gulaman" };
        String[] menuCost = { "75", "115", "119", "350", "70", "65", "40" };
        
        //2d Array [5] is accounts and [99] if the limit of orders you can add to cart
        String[][] customerCart = new String[5][99];
        
        
        while (true) {
            //Update accountCount always
            int checkAccounts = 0;
            for (String i : accounts) {
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
                    
                    String loggedIn = login(br, accounts, customerEmail, customerPassword, customerName, accountCount);
                    if (loggedIn != null) {
                        orderingInterface(br, accounts, customerEmail, customerPassword, customerName, accountCount, menuOptions, menuCost, customerCart);
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
                    
                    customerPassword = password(br);
                    if (customerPassword == null) continue;
                    customerName = username(br);
                    if (customerName == null) continue;
                    
                    accounts[accountCount] = customerEmail + "," + customerPassword + "," + customerName;
                    accountCount++;
                    
                    System.out.println("\nSuccessfull Registration!!");
                    break;
            }
        }
    }
    
    //Register customer email
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
                return enterEmail.trim();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    //Register customer password
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
                return enterPassword;
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    //Register customer username
    public static String username(BufferedReader br) {
        while (true) {
            System.out.print("\nSet a username: ");
            try {
                String enterUsername = br.readLine();
                if (enterUsername == null || enterUsername.isEmpty()) {
                    System.out.println("Username cannot be empty!");
                    continue;
                }
                return enterUsername.trim();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    //Login to account
    public static String login(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount) {
        while (true) {
            System.out.print("\nEnter your Email: ");
            String enterYourEmail = null;
            try {
                enterYourEmail = br.readLine();
                if (enterYourEmail == null || enterYourEmail.trim().isEmpty()) return null;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            
            System.out.print("\nEnter your Password: ");
            String enterYourPassword = null;
            try {
                enterYourPassword = br.readLine();
                if (enterYourPassword == null || enterYourPassword.trim().isEmpty()) return null;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            for (int i = 0; i < accountCount; i++) {
                String[] accountPart = accounts[i].split(",");
                String checkEmail = accountPart[0];
                String checkPassword = accountPart[1];
                String checkCustomerName = accountPart[2];
                
                if (enterYourEmail.equals(checkEmail) && enterYourPassword.equals(checkPassword)) {
                    System.out.println("\nWelcome! " + checkCustomerName);
                    return checkEmail;
                } else {
                    System.out.println("\nInvalid! Try Again\n");
                }
            }
        }
    }
    
    
    //Ordering Interface
    public static void orderingInterface(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount, String[] menuOptions, String[] menuCost, String[][] customerCart) {
        while (true) {
            try {
                System.out.println("\nType 'ORDERS' to check your orders");
                System.out.println("=== Menu Options ===\nSelect a food:\n");
                //Loop to print each menuOption strings
                for (int i = 0; i < menuOptions.length; i++) {
                    System.out.println(i + 1 + ". " + menuOptions[i] + "\nPrice: $" + menuCost[i] + "\n");
                }
                    
                String selection = br.readLine();
                if (selection == null || selection.trim().isEmpty()) break;
                
                if (selection.toUpperCase().trim().equals("ORDERS")) {
                    orders(br, accounts, customerEmail, customerPassword, customerUsername, accountCount, menuOptions, menuCost, customerCart);
                    continue;
                }
                
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
                    if (selectionInt == i + 1) {
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
                            
                       
                            System.out.println("\n=== YOU SELECTED ===");
                            System.out.println(menuOptions[i] + " $" + menuCost[i] + "\nQuantity: " + quantityInt + "\n");
                        
                            System.out.println("Press Enter to Cancel\nInput 'YES' to add to your orders");
                            String input = br.readLine();
                            if (input == null || input.trim().isEmpty()) break;
                            
                            if (input.toUpperCase().trim().equals("YES")) {
                                /*
                                String orderID = null;
                                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                                String id = "";
                                for (int j = 0; j < 8; j++) {
                                    int random = (int)(Math.random() * characters.length());
                                    id += characters.charAt(random);
                                }
                                orderID = id;
                                */
                                
                                for (int j = 0; j < accounts.length; j ++) {
                                    if (accounts[j] == null) continue;
                                    String[] accountPart = accounts[j].split(",");
                                    
                                    if (accountPart[0].equals(customerEmail)) {
                                        for (int k = 0; k < customerCart[j].length; k ++) {
                                            if (customerCart[j][k] == null) {
                                                customerCart[j][k] = menuOptions[i] + "," + menuCost[i] + "," + quantity;
                                                System.out.println("\n=== SUCCESSFULLY ADDED TO YOUR ORDERS ===");
                                                System.out.println(menuOptions[i] + "\nPrice: $" + menuCost[i] + "\nQuantity: " + quantityInt + "\n");
                                                System.out.println("\n=========================================");
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
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    //Customer Order Cart
    public static void orders(BufferedReader br, String[] accounts, String customerEmail, String customerPassword, String customerUsername, int accountCount, String[] menuOptions, String[] menuCost, String[][] customerCart) {
        while (true) {
            try {
                /*
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) continue;
                    String[] accountsPart = accounts[i].split(",");
                    
                    if (accountsPart[0].equals(customerEmail)) {
                        for (int j = 0; j < customerCart[i].length; j++) {
                            if (customerCart != null)
                        }
                    }
                }
                */
                System.out.println("\n=== YOUR ORDERS ===");
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) continue;
                    String[] accountsPart = accounts[i].split(",");
                    
                    if (accountsPart[0].equals(customerEmail)) {
                        for (int j = 0; j < accounts.length; j++) {
                            if (customerCart[i][j] == null) continue;
                            String[] customerCartParts = customerCart[i][j].split(",");
                            System.out.println(customerCartParts[0] + "\nPrice: $" + customerCartParts[1] + "\nQuantity: " + customerCartParts[2] + "\n");
                        }
                    }
                }
                String input = br.readLine();
                if (input == null || input.trim().isEmpty()) break;
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}