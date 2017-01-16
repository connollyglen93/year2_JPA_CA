/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import javax.persistence.*;
import java.util.Scanner;

@Entity 
@DiscriminatorValue("SA")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@SuppressWarnings("SerializableClass")
public class SavingsAccount extends Accounts{
    private double withdrawPending = 0; //added these to populate savings account table with value to be withdraw once approved
    private String withdrawType = "none";   //
    
    
    public SavingsAccount(){
        super();
        withdrawPending = 0;
    }
    
    public SavingsAccount(String id, double balance, int pin){
        super(id, balance, pin);
        withdrawPending = 0;
    }

    
    
    
    /* public void requestWithdrawal(double amount, String type) { //need to review this method
        Scanner in = new Scanner(System.in);
        System.out.print("Are you sure you would like to make a withdrawal from this savings account?");
        String confirm = in.nextLine();
        if (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y'){
            System.out.println("Request made. You will be able to make a withdrawal from this account in 5 Working days.");
            withdrawRequest = true;  //set time remaining on request using date (current date - setdate). Could possibly have a approve() method in account which resets this timer, signify that the money has been withdrawn and adding a counter of one to noOfWithdrawals in account
            withdrawPending = amount;
            withdrawType = type;
        }
        else {
            System.out.println("Request not completed");
            withdrawRequest = false;
        }
    } */

    @Override
    public Lodgements makeLodgement(double amount, String type) {
        Lodgements l = new Lodgements(amount, type);
        balance += l.getLodgementAmount();
        noOfLodgements++;
        super.addLodgement(l);
        return l;
    }

    @Override
    Withdrawals makeWithdrawal(double amount, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
