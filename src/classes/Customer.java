/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity     //makes class an entity in JPA
@SuppressWarnings("SerializableClass")
@SequenceGenerator(name = "cust_id_seq", sequenceName = "cust", initialValue = 2, allocationSize = 1)  
public class Customer {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_id_seq")
    @Id private int cust_id;
    private String fName;
    private String lName;
    private String address;
    
    @ManyToOne()    //signifies relationship with Branch table
    @JoinColumn(name = "branch_id")    //name of column which links tables
    private Branch bra; //makes an instance of branch which this customer is related to
    
    @ManyToMany (cascade = CascadeType.ALL) // signifies relationship to Accounts
    @JoinTable(name = "CUSTACCOUNT", 
    joinColumns = @JoinColumn(name="Cust_id"), //name of column in associative table with the primary key of this table
    inverseJoinColumns=@JoinColumn(name="Acc_ID")) //name of column in associative table with the primary key from Accounts table
    
    private List<Accounts> accList = new ArrayList<>(); // list of Accounts objects
    
    
    public Customer(){
        cust_id = 0;
        fName = "";
        lName = "";
        address = "";
    }
    
    public Customer(String fname, String lname, String address){
        this.fName = fname;
        this.lName = lname;
        this.address = address;
    }
    
    public Branch getBranch(){
        return bra;
    }
    
    public void setBranch(Branch b){
        this.bra = b;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.cust_id = id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return cust_id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
    
    public void setAccList(List<Accounts> listAcc){
        accList = listAcc;
    }
    
    public List<Accounts> getAccList(){
        return accList;
    }
    
    public void addAccount(Accounts a) {
        accList.add(a);
        a.getClist().add(this);
    }
    
    /*Removing customer's account (may face issues due to implementation of abstraction)
    */
    
    @Override
    public String toString(){
        return fName + " " + lName + 
                "\nAddress: " + address + "\nBranch Name: " 
                + bra.getBranch_name();
    }
}