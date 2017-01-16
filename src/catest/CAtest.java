/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catest;

import static Menu.Menu.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import classes.*;
import java.util.List;
import java.util.Random;

/**
 *
 * @author x00131787
 */
public class CAtest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAtestPU");
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    static EntityManager em = emf.createEntityManager();
    
    public static void main(String[] args) {
        

        mainMenu1();        //calls Menu class

        
        
        
        em.close();
        emf.close();
        
        
    }
    
    //changes today
    
    public static void updatePin(String accNo, int pin){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        if(currentAccountChecker(accNo)){
            CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
            ca.setPin(pin);
        }
        else{
            SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
            sa.setPin(pin);
        }
        em.getTransaction().commit();
    }
    
    public static boolean removeSavingsAccount(String accNo){
        boolean found = false;
        
        if(!currentAccountChecker(accNo)){
            SavingsAccount a = (SavingsAccount) em.find(Accounts.class, accNo);

            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(a);
            em.getTransaction().commit();
            found = true;
        }
        return found;
    }
    
            
    //changes today end
            
    public static boolean validID(String accNo){
        boolean success = false;
        SavingsAccount sa = em.find(SavingsAccount.class, accNo);
        CurrentAccount ca = em.find(CurrentAccount.class, accNo);
        if (sa != null) {
            success = true;
            return success;
        }
        else if (ca != null){
            success = true;
            return success;
        }
        else{
            return success;
        }

   }
   
    
    
    public static boolean validPin(String accNo, int pin){
        boolean success = false;
        if(em.find(Accounts.class, accNo) instanceof SavingsAccount){
            SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
            if(sa.getPin() == pin) {
                success = true;
                return success;
            }
            else{
                return success;
            }
        }
        else if(em.find(Accounts.class, accNo) instanceof CurrentAccount){
            CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
            if(ca.getPin() == pin) {
                success = true;
                return success;
            }
            else{
                return success;
            }
        }
        else{
            return success;
        }
   }
     
   
    public static boolean currentAccountChecker(String accNo){
        boolean status = true;
        if(em.find(Accounts.class, accNo) instanceof SavingsAccount){
             status = false;
        }
        return status;
    } 

    public static Accounts getAccount(String accNo){
        if(!currentAccountChecker(accNo)){
             SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
             return sa;  
        }
        else if(currentAccountChecker(accNo)){
             CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
             return ca;
        }
        else{
            System.err.println("That is an invalid account number. Please enter a valid account number");
            return null;
        }       
    } 

     public static List<Withdrawals> getWithdrawals(String accNo){        //does not check for non-existing customers as customer must exist at the point in the program which this method is ran
        List<Withdrawals> withdrawals;
        if(!currentAccountChecker(accNo)){
             SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
             withdrawals = sa.getWithList();  
        }
        else if(currentAccountChecker(accNo)){
             CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
             withdrawals = ca.getWithList();  
        }
        else{
            withdrawals = null;
        }
        return withdrawals;
    }

         public static List<Lodgements> getLodgements(String accNo){        //does not check for non-existing customers as customer must exist at the point in the program which this method is ran
        List<Lodgements> lodgements;
        if(!currentAccountChecker(accNo)){
             SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
             lodgements = sa.getLodgeList();  
        }
        else if(currentAccountChecker(accNo)){
             CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
             lodgements = ca.getLodgeList();  
        }
        else{
            lodgements = null;
        }
        return lodgements;
    }

         
         
    public static List<Customer> getAccountHolders(String accNo){        //does not check for non-existing customers as customer must exist at the point in the program which this method is ran
        List<Customer> customers;
        Customer accHolder;
        if(!currentAccountChecker(accNo)){
             SavingsAccount sa = (SavingsAccount) em.find(Accounts.class, accNo);
             customers = sa.getClist();  
        }
        else if(currentAccountChecker(accNo)){
             CurrentAccount ca = (CurrentAccount) em.find(Accounts.class, accNo);
             customers = ca.getClist();  
        }
        else{
            customers = null;
        }
        return customers;
    }
    /*
    public static Customer getSingleAccountHolder(List<Customer> c){
            return c.get(0);
    }
    */
    public static String accGen(){
        Random rand = new Random();
        int randNum = 0;
        randNum = rand.nextInt((99999999 - 10000001) + 1) + 10000001;
        String numberAsString = Integer.toString(randNum);
        return numberAsString;
    }

    public static int pinGen(){
        Random rand = new Random();
        int randNum = 0;
        randNum = rand.nextInt((9999 - 1000) + 1) + 1000;
        return randNum;
    }

    public static Branch getBranch(String branch_id){
        Branch b = em.find(Branch.class, branch_id);
        return b;
    }

 }