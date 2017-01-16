/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author x00131787
 */
    @Entity
    @SequenceGenerator(name = "wid_seq", sequenceName = "withd", initialValue = 1, allocationSize = 1)
    @SuppressWarnings("SerializableClass")
    public class Withdrawals {
            
            @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wid_seq")
            private int wid;
            private double withdrawAmount;
            private String withdrawType;
            
            @ManyToOne()    //signifies relationship with Accounts table
            @JoinColumn(name = "id")    //name of column which links tables
            private Accounts acc; //makes an instance of branch which this customer is related to
            
            public Withdrawals(){
                    withdrawAmount = 0;
                    withdrawType = "";
            }

            public Withdrawals(double withdrawAmount, String withdrawType){
                this.withdrawAmount = withdrawAmount;
                this.withdrawType = withdrawType;
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

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType;
    }
            
            
            
            @Override
            public String toString(){
                return "Withdrawal ID: " + wid + "\nWithdraw Amount: €" + withdrawAmount
                        + "\nWithdraw Type: " + withdrawType;
            }
    }