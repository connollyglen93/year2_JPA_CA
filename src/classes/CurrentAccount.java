/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.persistence.*;

@Entity 
@DiscriminatorValue("CA")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@SuppressWarnings("SerializableClass")
public class CurrentAccount extends Accounts{
    
    public CurrentAccount(){
        super();
    }
    
    public CurrentAccount(String id, double balance, int pin){
        super(id, balance, pin);
    }

    @Override
    public Withdrawals makeWithdrawal(double amount, String type) {
        Withdrawals w = new Withdrawals(amount, type);
        balance -= w.getWithdrawAmount();
        noOfWithdrawals++;
        super.addWithdrawal(w);
        return w;
    }

    @Override
    public Lodgements makeLodgement(double amount, String type) {
        Lodgements l = new Lodgements(amount, type);
        balance += l.getLodgementAmount();
        noOfLodgements++;
        super.addLodgement(l);
        return l;
    }

}
