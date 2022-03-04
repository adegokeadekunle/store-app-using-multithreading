package models;

import java.util.Arrays;

public class Storage {
    private Product [] products = new Product[0];

    public void add(Product product){
        Product [] temp = products.clone();
        products = new Product [products.length + 1];
        System.arraycopy(temp, 0, products, 0, products.length - 1);
        products[products.length- 1 ] = product;
    }

    public boolean contains(String productID){
        for(Product goods : products)
            if(goods.getProductID().equals(productID)) return true;
        return false;
    }

    public Product get(String productID){
        for (Product product : products)
            if(product.getProductID().equals(productID)) return product;
        return null;
    }

    public Product get(int index){
        return products[index];
    }

    public int size(){
        return products.length;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "products=" + Arrays.toString(products) +
                '}';
    }
}
