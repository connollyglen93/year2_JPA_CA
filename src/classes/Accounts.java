/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@SuppressWarnings("SerializableClass")
public abstract class Accounts {
    @Id
    protected String id;
    protected double balance;
    protected int pin = 0;
    protected int noOfLodgements = 0;
    protected int noOfWithdrawals = 0;
    
    @ManyToMany(mappedBy = "accList")   //many to many relationship with customer
    private List<Customer> clist = new ArrayList<>();
    
    @OneToMany(mappedBy = "acc", orphanRemoval=true) //one to many relationship with lodgement
    private List<Lodgements> lodgelist = new ArrayList<>();
        
    @OneToMany(mappedBy = "acc", orphanRemoval=true) //one to many relationship with withdraw
    private List<Withdrawals> withlist = new ArrayList<>();
    
    public Accounts(){
        this.id = "";
        this.balance = 0;
        this.pin = 0;      
    }
    
    public Accounts(String id, double balance, int pin){
        this.id = id;
        this.balance = balance;
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    
    
    
    public double getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setClist(List<Customer> custs){
        clist = custs;
    }
    
    public List<Customer> getClist(){
        return clist;
    }
    
    public void setWithList(List<Withdrawals> withs){
        withlist = withs;
    }
    
    public List<Withdrawals> getWithList(){
        return withlist;
    }
    
    public void setLodgeList(List<Lodgements> lodges){
        lodgelist = lodges;
    }
    
    public List<Lodgements> getLodgeList(){
        return lodgelist;
    }
    
    
    public void addCustomer(Customer c) {
        clist.add(c);
        c.getAccList().add(this);
    }
    
    abstract Withdrawals makeWithdrawal(double amount, String type);
    
    abstract Lodgements makeLodgement(double amount, String type);
    
    public void addLodgement(Lodgements l){
        lodgelist.add(l);
        l.setAcc(this);
    }
    
    public void addWithdrawal(Withdrawals w){
        withlist.add(w);
        w.setAcc(this);
    }    
    
    @Override
    public String toString(){
        return "Account ID: " + id + " Balance: " + balance;
    }
    

    
/*    @Entity
    @SequenceGenerator(name = "wid_seq", initialValue = 1, allocationSize = 1)
    class Withdrawals {
            
            @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wid_seq")
            private int wid;
            private double withdrawAmount;
            private String withdrawType;
            
            @ManyToOne()    //signifies relationship with Accounts table
            @JoinColumn(name = "id")    //name of column which links tables
            private Accounts acc; //makes an instance of branch which this customer is related to
            
            private Withdrawals(){
                    withdrawAmount = 0;
                    withdrawType = "";
            }

            private Withdrawals(double withdrawAmount, String withdrawType){
                this.withdrawAmount = withdrawAmount;
                this.withdrawType = withdrawType;
                balance -= withdrawAmount;
                noOfWithdrawls++;
            }
            
            public int getId() {
                return wid;
            }
            
            public Accounts getAcc() {
                return acc;
            }

            public void setAcc(Accounts acc) {
                this.acc = acc;
            }

            @Override
            public String toString(){
                return "Withdrawal ID: " + wid + "\nWithdraw Amount: " + withdrawAmount
                        + "\nWithdraw Type: " + withdrawType;
            }
    }
    @Entity
    @SequenceGenerator(name = "lid_seq", initialValue = 1, allocationSize = 1)       
    class Lodgements {
        
            @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lid_seq")
            private int lid;
            private double lodgeAmount = 0;
            private String lodgeType;
            
            @ManyToOne()    //signifies relationship with Branch table
            @JoinColumn(name = "id")    //name of column which links tables
            private Accounts acc; //makes an instance of branch which this customer is related to
            
            public Lodgements(){
                lodgeAmount = 0;
                lodgeType = "";
            }

            public Lodgements(double lodgeAmount, String lodgeType){
                this.lodgeAmount = lodgeAmount;
                balance += lodgeAmount;
                this.lodgeType = lodgeType;
                noOfLodgements++;
            }
            
            public Accounts getAcc() {
                return acc;
            }

            public void setAcc(Accounts acc) {
                this.acc = acc;
            }
            
            @Override
            public String toString(){
                return "Lodgement ID: " + lid + " Lodgement Amount: " + " Lodgement Type: " + lodgeType;
            }
    }
        
        public int getNoOfLodgements(){
            return noOfLodgements;
        }
        
        public int getNoOfWithdrawals(){
            return noOfWithdrawls;
        }

*/
}