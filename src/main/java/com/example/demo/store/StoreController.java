package com.example.demo.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
	
	@Autowired
    ProductEntityCrudRepository productEntityCrudRepository;
    
    @GetMapping(path="/createProduct", produces = "text/html")
    String showProductForm() {
    	String output = "<form action='' method='POST'>";
        output += "Name:  <input name='name' type='text' /><br />";
        output += "Price: <input name='price' type='text' /><br />";
        output += "<input type='submit' />";
        output += "</form>";
        return output;
    }
    
    @PostMapping(path = "/createProduct")
    void createProduct(@ModelAttribute ProductEntity product) {
        if (product.getName().equals("")) {
            throw new RuntimeException("Name required");
        }
        if (product.getPrice() <= 0 || product.getPrice() == null) {
            throw new RuntimeException("Price must be greater than 0");
        }
        productEntityCrudRepository.save(product);
        System.out.println("Product Added Succesfully!");
    }
    

    @GetMapping(path = "/home")
    String home() {

        Iterable<ProductEntity> products = productEntityCrudRepository.findAll();

        String myProducts = "<h2><b>Our Products</b></h2><br />"
        		+ "<table>"
        		+ "  <colgroup>\r\n"
        		+ "    <col span=\"1\" style=\"background-color: cornsilk;\">\r\n"
        		+ "    <col style=\"background-color: bisque;\">\r\n"
        		+ "    <col style=\"background-color: pink;\">\r\n"
        		+ "  </colgroup>"
        		+ "<tr>\r\n"
        		+ "    <th>ID</th>\r\n"
        		+ "    <th>Item Name</th>\r\n"
        		+ "    <th>Price</th>\r\n"
        		+ "  </tr>\r\n";
        for(ProductEntity p: products) {
        	myProducts = myProducts + "<b><tr><td>"+ p.getId() +"</td><td>" + p.getName() +"</td><td>$"+ p.getPrice()+ "</td></tr></b>";
        }
        
        myProducts = myProducts + "</table>";

        return myProducts;
    }

}
