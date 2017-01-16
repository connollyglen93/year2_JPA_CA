/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;


import static catest.CAtest.*;
import classes.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author x00131787
 */
public class Menu{
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAtestPU");
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    static EntityManager em = emf.createEntityManager();
    static Scanner in = new Scanner(System.in);
    static Random rn = new Random();
    
    public static void mainMenu1() {
        while (true) {
            int choice = -1;
            System.out.println("Welcome to the Banking system.");
            System.out.println("--------------------------------");
            mainMenuOptions();
            try{
                choice = in.nextInt();
            }catch(InputMismatchException e){
                in.nextLine();
                choice = -1;
            }
            while (choice != 1 && choice != 2 && choice != 3) {
                System.out.println("Invalid Choice.\n");
                mainMenuOptions();
                try{
                    choice = in.nextInt();
                }catch(InputMismatchException e){
                    in.nextLine();
                    choice = -1;
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Please enter your account number: ");
                    String aNumber = in.next();
                    accountLogin(aNumber);
                    break;

                case 2:
                    accountSetUp();
                    break;

                case 3:
                    System.out.println("Thank you for using the banking system.");
                    System.exit(0);
            }
        }
    }
    
    public static void mainMenuOptions() {
        System.out.println("1. Account Login");
        System.out.println("2. Account Creation");
        System.out.println("3. Exit System");
        System.out.print("\tChoice: ");
    }
    
    public static void accountLogin(String accNumber) {
        if (!validID(accNumber)) {
            System.out.println("Account number does not exist.\n");
            mainMenu1();
        } else {
            System.out.print("Enter your pin: ");
            int pin = -1;
            try{
                pin = in.nextInt();
            }catch(InputMismatchException e){
                in.nextLine();
                pin = -1;
            }
            while(pin == -1){
                try{
                    System.out.println("Invalid Entry.");
                    System.out.print("Enter your pin: ");
                    pin = in.nextInt();
                }catch(InputMismatchException e){
                    in.nextLine();
                    pin = -1;
                }
            }
            int counter = 0;
            while (!validPin(accNumber, pin)) {
                if(counter == 2){
                   System.err.println("An automated system has alerted the Gardai Siochana.");
                   System.exit(0);
                }
                System.err.println("Invalid Pin Try Again.");
                if(counter == 1){
                    System.err.println("You have one attempt remaining!");
                }
                System.out.print("Enter your pin: ");
                pin = in.nextInt();
                counter++;

            }
            
            displayMenu2(accNumber);
        }
    }
    
    public static void displayMenu2(String aNumber){
        List<Customer> custs = getAccountHolders(aNumber);
        List<Lodgements> lodges = getLodgements(aNumber);
        List<Withdrawals> with = getWithdrawals(aNumber);        
        for(Customer c: custs){
            System.out.println("Welcome "+ c);
            System.out.println("---------------------------\n");
        }
        while(true){
        accountMenu(aNumber);
        int choice = -1;
        try{
            choice = in.nextInt();
        }catch(InputMismatchException e){
            in.nextLine();
            choice = -1;
        }
        while(choice == -1){
            System.out.println("Invalid entry.");
            System.out.print("\tChoice");
        }
        SavingsAccount sa;
        CurrentAccount ca;
        if(choice == 8 && currentAccountChecker(aNumber)){
                System.err.println("Selection unavailable.");
        }
        
        switch (choice) {
           case 1:
              if(currentAccountChecker(aNumber)){
                   ca = (CurrentAccount) em.find(Accounts.class, aNumber);
                   System.out.println("--------------------------------");
                   System.out.println("Balance: €" + ca.getBalance());// c.ge    RETURNS BALANCE FROM DB
                   System.out.println("--------------------------------");
               }else{
                   sa = (SavingsAccount) em.find(Accounts.class, aNumber);
                   System.out.println("--------------------------------");
                   System.out.println("Balance: €" + sa.getBalance());// c.ge    RETURNS BALANCE FROM DB
                   System.out.println("--------------------------------");
               }
               break;
           case 2:
               em.getTransaction().begin();
               System.out.print("Please enter the amount you would like to lodge: €");
               double lodgeIn = -1;
               try{
                   lodgeIn = in.nextDouble();
               }catch(InputMismatchException e){
                   in.nextLine();
                   lodgeIn = -1;
               }
               while(lodgeIn == -1){
                    try{
                        System.out.println("Invalid Entry.");
                        System.out.print("Please enter the amount you would like to lodge: €");
                        lodgeIn = in.nextDouble();
                    }catch(InputMismatchException e){
                        in.nextLine();
                        lodgeIn = -1;
                    }       
               }
               in.nextLine();
               System.out.print("Please enter the message for this lodgement: ");
               String message = in.nextLine();
               System.out.println("Confirming Lodgement..");
               
               if(currentAccountChecker(aNumber)){
                   ca = (CurrentAccount) em.find(Accounts.class, aNumber);
                   em.persist(ca.makeLodgement(lodgeIn, message));
               }else{
                   sa = (SavingsAccount) em.find(Accounts.class, aNumber);
                   em.persist(sa.makeLodgement(lodgeIn, message));
               }
               em.getTransaction().commit();
               System.out.println("Amount Lodged !");
               break;

           case 3:
                em.getTransaction().begin();
                double withdrawl = -1;
                do{
                System.out.print("Please type the amount of cash you would like to withdraw from your account: €");
                try{
                    withdrawl = in.nextDouble();
                }catch(InputMismatchException e){
                    in.nextLine();
                    withdrawl = -1;
                }
                while(withdrawl == -1){
                    try{
                        System.out.println("Invalid Entry.");
                        System.out.print("Please type the amount of cash you would like to withdraw from your account: €");
                        withdrawl = in.nextDouble();
                    }catch(InputMismatchException e){
                        in.nextLine();
                        withdrawl = -1;
                    }
                }    
                if(currentAccountChecker(aNumber)){
                    ca = (CurrentAccount) em.find(Accounts.class, aNumber);
                    if(withdrawl > ca.getBalance()){
                        System.out.println("Insufficient funds.");
                        withdrawl = 0;
                    }
                }else{
                    sa = (SavingsAccount) em.find(Accounts.class, aNumber);
                    if(withdrawl > sa.getBalance()){
                        System.out.println("Insufficient funds.");
                        withdrawl = 0;
                    }
                }}while(withdrawl == 0);
               
               in.nextLine();
               System.out.println("Are you sure you would like to withdraw €"+withdrawl);
               System.out.print("Choice ('Y' / 'N'): ");
               int exit = 0;      
               do {
                   String confirm = in.nextLine();
                   if (confirm.charAt(0) == 'Y' || confirm.charAt(0) == 'y') {
                       exit++;
                   } else if (confirm.charAt(0) == 'N' || confirm.charAt(0) == 'n') {
                       exit++;
                   } else {
                       System.out.println("Invalid choice please enter (Yes/No)");
                   }
               } while (exit == 0);
               
               System.out.print("Please enter the message for this withdrawal: ");
               String withdrawMessage = in.nextLine();
               if(currentAccountChecker(aNumber)){
                   ca = (CurrentAccount) em.find(Accounts.class, aNumber);
                   em.persist(ca.makeWithdrawal(withdrawl, withdrawMessage));
               }else{
                   System.out.println("Your withdrawl is now pending...");
               }
               em.getTransaction().commit();
               break;
           case 4:
               System.out.println("------------------------");
               with = getWithdrawals(aNumber);
               for(Withdrawals w : with){
                   System.out.println(w + "\n------------------------");
               }
               
               break;
            case 5:
               System.out.println("------------------------");
               lodges = getLodgements(aNumber);
               for(Lodgements l : lodges){
                   System.out.println(l + "\n------------------------");
               }
               
               break;
                case 6:
                    System.out.print("Please enter your current pin: ");
                    int pinIn = -1;
                    try{
                        pinIn = in.nextInt();
                    }catch(InputMismatchException e){
                        in.nextLine();
                        pinIn = -1;
                    }
                    while(pinIn == -1){
                        try{
                            System.out.println("Invalid Entry.");
                            System.out.print("Please enter your current pin: ");
                            pinIn = in.nextInt();
                        }catch(InputMismatchException e){
                            in.nextLine();
                            pinIn = -1;
                        }
                    }
                    if(validPin(aNumber, pinIn)){
                        System.out.print("Please enter your new pin: ");
                        int newPin = -1;
                        try{
                            newPin = in.nextInt();
                        }catch(InputMismatchException e){
                            in.nextLine();
                            newPin = -1;
                        }
                        while(newPin == -1){
                            try{
                                System.out.println("Invalid Entry.");
                                System.out.print("Please enter your new pin: ");
                                newPin = in.nextInt();
                            }catch(InputMismatchException e){
                                in.nextLine();
                                newPin = -1;
                            }
                        }
                        while(Integer.toString(newPin).length() > 4 || Integer.toString(newPin).length() <= 3 ){
                           System.err.println("The Pin you have entered was longer than 4 digits ");
                           System.out.print("Please enter your new pin: ");
                           try{
                                newPin = in.nextInt();
                            }catch(InputMismatchException e){
                                in.nextLine();
                                newPin = -1;
                            }
                            while(newPin == -1){
                                try{
                                    System.out.println("Invalid Entry.");
                                    System.out.print("Please enter your new pin: ");
                                    pinIn = in.nextInt();
                                }catch(InputMismatchException e){
                                    in.nextLine();
                                    pinIn = -1;
                                }
                            }
                        }
                        System.out.println("Please re-enter pin to confirm: ");
                        int confirm = -1;
                        try{
                            confirm = in.nextInt();
                        }catch(InputMismatchException e){
                            in.nextLine();
                            confirm = -1;
                        }
                        
                        while(confirm == -1){
                            try{
                                System.out.println("Invalid Entry.");
                                System.out.println("Please re-enter pin to confirm: ");
                                confirm = in.nextInt();
                            }catch(InputMismatchException e){
                                in.nextLine();
                                confirm = -1;
                            }    
                        }
                            
                        if(newPin != confirm){
                            System.err.println("The pins you have entered did not match.");
                        }else{
                            updatePin(aNumber, newPin);
                            System.out.println("Your Pin has been updated.\n");
                            }
                    }
                    break;
                case 7:
                   mainMenu1();
                case 8:
                    System.out.println("Are you sure you would like to cancel your savings account?");
                    exit = 0;      
                    do {
                        String confirm = in.nextLine();
                        if (confirm.charAt(0) == 'Y' || confirm.charAt(0) == 'y') {
                            removeSavingsAccount(aNumber);
                            exit++;
                        } else if (confirm.charAt(0) == 'N' || confirm.charAt(0) == 'n') {
                            exit++;
                        } else {
                            System.out.println("Invalid choice please enter (Yes/No)");
                        }
                    } while (exit == 0);
                    System.out.println("Succesfully deleted your savings account.");
                    mainMenu1();
               
                default:
                   System.err.println("Invalid Selection!");
               break;    
            }   //end of switch
        }       //end of while
   }
    
    
    public static void accountMenu(String accNumber) {
           System.out.println("Please select one of the following actions.");
           System.out.println("1. Check balance.");
           System.out.println("2. Make a lodgement.");
           System.out.println("3. Make a withdrawal.");
           System.out.println("4. Check withdrawal history.");
           System.out.println("5. Check lodgement history.");
           System.out.println("6. Change Pin.");
           System.out.println("7. Logout.");
           if(!currentAccountChecker(accNumber)){
               System.out.println("ADDITIONAL OPTIONS:");
               System.out.println("8.Delete savings account.");
           }
           System.out.print("\tChoice: ");
       }
    
    public static void accountSetUp() {  
       if(!em.getTransaction().isActive()){
            em.getTransaction().begin();             
       }
       in.nextLine();       //counter bug in first menu choice
       int exit = 0;
       boolean choice = true;
       String fName = null;
       String lName = null;
       String address = null;
       String existing = null;
       boolean isBranch = true;
       List<Customer> clist;
       Customer d1 = new Customer();
       
       System.out.print("Are you an existing account holder? (Y/N): ");
       existing = in.nextLine().toUpperCase();
       while(existing.charAt(0) != 'Y' && existing.charAt(0) != 'N'){
           System.out.print("Please enter (Y/N): ");
           existing = in.nextLine().toUpperCase();
       }
       while(existing.charAt(0) == 'Y' || existing.charAt(0) == 'y'){
            isBranch = false;
            System.out.print("Please enter your account number(or type exit to exit): ");
            String accNo = in.nextLine();
            if(validID(accNo)){
                clist = getAccountHolders(accNo);
                d1 = clist.get(0);
                for(Customer c: clist){
                    System.out.println(c);
                }
                System.out.print("Is this correct?(Y/N): ");
                String ans = in.nextLine();
                if(ans.charAt(0) != 'n' || ans.charAt(0) != 'N'){
                    existing = "n";
                    choice = false;
                }
            }else if(!validID(accNo) && !accNo.equals("exit") && !accNo.equals("EXIT")){
                System.err.println("This is not a valid account number");
            }
            if(accNo.equals("exit") || accNo.equals("EXIT")){
                mainMenu1();
            }
       }    
       while(choice){
                String exitLoop;
                System.out.print("Enter your first name: ");
                fName = in.nextLine();
                System.out.print("Enter your last name: ");
                lName = in.nextLine();
                System.out.print("Enter your address: ");
                address = in.nextLine();
                System.out.println("\nName: " + fName + " " + lName + "\tAddress: " + address);
                System.out.print("If this is correct? ('Y' / 'N'): ");
                exitLoop = in.nextLine().toUpperCase();
                while(exitLoop.charAt(0) != 'Y' && exitLoop.charAt(0) != 'N'){ //check
                    System.out.print("Please enter (Y/N): ");
                    exitLoop = in.nextLine().toUpperCase();
                }
                if(exitLoop.charAt(0) == 'Y'){
                    d1 = new Customer(fName, lName, address);
                    choice = false;
                    em.persist(d1);
                }
        }   
        String accountNo = accGen();
        int pinNo = pinGen();
        
        System.out.print("Would you like to set up a savings account or a current account (S-Savings/C-Current): ");
        String accountType = in.nextLine();    
        do {
           switch (accountType.charAt(0)) {
               case 'C':
               case 'c':
                   CurrentAccount ca1 = new CurrentAccount(accountNo, 0, pinNo);
                   ca1.addCustomer(d1);
                   em.persist(ca1);
                   exit++;
                   break;
               case 'S':
               case 's':
                   SavingsAccount sa1 = new SavingsAccount(accountNo, 0, pinNo);
                   sa1.addCustomer(d1);
                   em.persist(sa1);
                   exit++;
                   break;
               default:
                   System.out.print("Invalid choice please enter (S-Savings/C-Current): ");
                   accountType = in.nextLine();
                   break;
           }
        } while (exit == 0);

        System.out.println("Your account number is :"+accountNo+" \nYour Pin is: "+pinNo+"\nNOTICE: please keep a record of your Account Number and Pin.");
        System.out.println();//blank line for spacing.
        while(isBranch){
            System.out.print("Which branch are you a part of:(1/ 2/ 3): \n1.AIB Tallaght \n2.Permanent TSB Tallaght \n3.BOI Tallaght \n\tChoice: ");
            int branchName = 0;
            try{
                branchName = in.nextInt();
            }catch(InputMismatchException e){
                in.nextLine();
                branchName = 0;
            }
            while(branchName == 0){
                System.out.println("Invalid entry");
                System.out.print("\tChoice: ");
                try{
                    branchName = in.nextInt();
                }catch(InputMismatchException e){
                    in.nextLine();
                    branchName = 0;
                }
            }
        
        switch (branchName) {
               case 1:
                   System.out.println("You have selected AIB TALLAGHT.");
                   Branch b1 = getBranch("AIB_Tal1");
                   b1.addCustomer(d1);
                   em.persist(d1);
                   isBranch = false;
                   break;
               case 2:
                   System.out.println("You have selected Permanent TSB Tallaght.");
                   Branch b2 = getBranch("PTSB_1");
                   b2.addCustomer(d1); 
                   em.persist(d1);
                   isBranch = false;
                   break;

               case 3:
                   System.out.println("You have selected BOI TALLAGHT.");
                   Branch b3 = getBranch("BOI_Tal1");
                   b3.addCustomer(d1); 
                   em.persist(d1);
                   isBranch = false;
                   break;
               default:
                   System.err.println("Incorrect branch entered.");
                   System.out.print("Which branch are you a part of: ");
           } 
        
        }
        em.getTransaction().commit();        
}
}
