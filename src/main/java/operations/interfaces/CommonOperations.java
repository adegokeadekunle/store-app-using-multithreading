package operations.interfaces;

import models.Product;
import models.Store;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface CommonOperations {

    default void createAFileToSaveData(String filePath, String fileContent){
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(fileContent);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default List<Product> viewProductByCategory(Store store, String category){
        List<Product> productArrayList = new ArrayList<>();
            for(int i = 0; i< store.getGoods().size(); i++){
                Product product = store.getGoods().get(i);
                if(product.getProductCategory().equalsIgnoreCase(category))
                    productArrayList.add(product);
            }
            return productArrayList;
    }
}
