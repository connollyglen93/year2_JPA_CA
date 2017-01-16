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
    @SequenceGenerator(name = "lid_seq", sequenceName = "lodge", initialValue = 1, allocationSize = 1) 
    @SuppressWarnings("SerializableClass")
    public class Lodgements {
        
            @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lid_seq")
            private int lid = 0;
            private double lodgementAmount = 0;
            private String lodgementType;
            
            @ManyToOne()    //signifies relationship with Branch table
            @JoinColumn(name = "id")    //name of column which links tables
            private Accounts acc; //makes an instance of branch which this customer is related to
            
            public Lodgements(){
                lodgementAmount = 0;
                lodgementType = "";
            }

            public Lodgements(double lodgeAmount, String lodgeType){
                this.lodgementAmount = lodgeAmount;
                this.lodgementType = lodgeType;
            }
            
            public Accounts getAcc() {
                return acc;
            }

            public void setAcc(Accounts acc) {
                this.acc = acc;
            }

            public double getLodgementAmount() {
                return lodgementAmount;
            }

            public void setLodgementAmount(double lodgementAmount) {
                this.lodgementAmount = lodgementAmount;
            }

            public String getLodgementType() {
                return lodgementType;
            }

            public void setLodgementType(String lodgementType) {
                this.lodgementType = lodgementType;
            }
            
            
            
            @Override
            public String toString(){
                return "Lodgement ID: " + lid + " Lodgement Amount: €" + lodgementAmount +  " Lodgement Type: " + lodgementType;
            }
    }
