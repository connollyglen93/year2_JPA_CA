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
@SuppressWarnings("SerialzableClass")
public class Branch {
    
    @Id
    private String branch_id;
    private String branch_name;
    private String branch_address;
    
    @OneToMany(mappedBy = "bra")
    private List<Customer> custList = new ArrayList<>();
    
    public Branch(){
        branch_id = "";
        branch_name = "";
        branch_address = "";
    }
    
    public Branch(String id, String name, String bAddress){
        branch_id = id;
        this.branch_name = name;
        this.branch_address = bAddress;
    }
    
    public void addCustomer(Customer c) {
        this.custList.add(c);       //adds customer object to arraylist cust list which reflects in customer tabler 
        c.setBranch(this);
    }
    
    public String getId() {
        return branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getbAddress() {
        return branch_address;
    }

    public void setId(String id) {
        this.branch_id = id;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public void setbAddress(String bAddress) {
        this.branch_address = bAddress;
    }
    
    
    @Override
    public String toString(){
        return "Branch ID:" + branch_id + "Branch Name: " 
                + branch_name + " Address: " + branch_address;
    }       
}
