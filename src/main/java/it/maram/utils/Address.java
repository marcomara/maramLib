package it.maram.utils;

import it.maram.logging.InvalidParametersException;
import it.maram.logging.InvalidParametersNumberException;

import javax.print.DocFlavor;

public class Address {
    private final String address, city, state, nation;
    private final int number;
    public Address(String address, int number, String city, String state, String nation) throws InvalidParametersException {
        if(number<0) throw new InvalidParametersException("Number can't be negative");
        this.address=address;
        this.number = number;
        this.city = city;
        this.state = state;
        this.nation = nation;
    }
    public final String getAddress(){
        return this.address + ", " + this.number;
    }
    public final String getAddressExtended(){
        return this.address + ", " + this.number + ", " + this.city + ", " + this.state + ", " + this.nation;
    }
    public final String toString(){
        return this.address + "\t" + this.number + "\t" + this.city + "\t" + this.state + "\t" + this.nation;
    }
    public static Address fromString(String address) throws InvalidParametersNumberException {
        final String[] adds = address.split("\t");
        if(adds.length!=5) throw new InvalidParametersNumberException("Expected 5 parameters, found" + adds.length);
        return new Address(adds[0], Integer.parseInt(adds[1]), adds[2], adds[3], adds[4]);
    }
}
