import enums.Designation;
import enums.Gender;
import enums.Qualification;
import exceptions.NotAuthorizedException;
import exceptions.OutOfStockException;
import exceptions.StockDoesNotExistException;
import models.*;
import operations.implementations.AdministrativeOperationsImpl;
import operations.implementations.CustomerOperationsImpl;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException {
        Store decagonEnt = new Store("Decagon Enterprise");
        Staff mike = new Staff("Mike","Peter", Gender.MALE, Designation.MANAGER);
        Staff mary = new Staff("Mary","Samuel",Gender.FEMALE,Designation.CASHIER);
        Customer mercy = new Customer("Mercy","Maxwell",Gender.FEMALE);
        Applicant sammie = new Applicant("Sammie","Richard",Gender.MALE, Qualification.OND);
        CustomerOperationsImpl customerOperations = new CustomerOperationsImpl();
        AdministrativeOperationsImpl administrativeOperations = new AdministrativeOperationsImpl();
        administrativeOperations.loadProductsFromExcelFile(decagonEnt,mike,"src/main/resources/excel_files/decagon_Ent_products.xlsx");

        System.out.println(Arrays.toString(new Storage[]{decagonEnt.getGoods()}));

        customerOperations.addProductToCart(decagonEnt,mercy,"DAMFS-CER-001",10);

        mercy.setWallet(1000000);
        mercy.getCart().put("Kellogs",10);
        mercy.setCheckOut(true);
        mercy.isCheckOut();
        System.out.println(mercy.getCart());
        System.out.println(mercy.getTotalGoodsPrice());

        // Storage storage = new Storage();
//        Product rice = new Product("","","",32,32);
//        decagonEnt..storage.add(rice);
//        decagonEnt.getGoods().add(rice);
//        System.out.println(decagonEnt.getGoods());;
//
//        mercy.setWallet(100000);
//        mercy.getCart().put("rice",1);
//        mercy.setCheckOut(true);
//        mercy.isCheckOut();
        // mercy.setTotalGoodsPrice(10000);
//        System.out.println(mercy.getTotalGoodsPrice());
  }
}

//    Company company = new Company("Damzxyno");
//    Company newComa = new Company("Wilfred Store");
//
//
//        System.out.println(newComa.getCompanyGoods()[0]);
//                Staff staff = new Staff("","","");
//                company.setCompanyGoods(new Product [9]);
//                company.setCompanyGoods(new String [9]);
//                newComa.getStaffList().add()
//                System.out.println(Arrays.toString(company.getCompanyGoods()));
//                System.out.println(company);
//                }
//
//public void addProduct(Product product){
//        Product [] temp = companyGoods.clone();
//        int size = companyGoods.length;
//        companyGoods = new Product [++size];
//        for (int i = 0; i < size-1; i++){
//        companyGoods[i] = temp[i];
//        }
//        companyGoods[size -1] = product;
//
//        String filePath = "src/main/resources/excel_files/damzxyno_food_store.xlsx";