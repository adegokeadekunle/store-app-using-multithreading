package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String productID;
    private String productName;
    private String productCategory;
    private double productPrice;
    private int productQuantity;
}
