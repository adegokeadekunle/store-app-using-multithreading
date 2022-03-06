package models;

import enums.Gender;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Customer extends Person{
    private double wallet = 0.0;
    private boolean checkOut = false;
    private final Map<String, Integer> cart = new HashMap<>();
    private double totalGoodsPrice = 0;

    public Customer(String firstName, String lastName, Gender gender) {
        super(firstName, lastName, gender);
    }
}
