package com.example.ecom_project1.Controller;


import com.example.ecom_project1.Service.ProductService;
import com.example.ecom_project1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductService service;



    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){

        Product product = service.getProductById(id);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{ProductId}/image")
    public ResponseEntity<byte[]> getImageByProductIdd(@PathVariable int ProductId){
        Product product = service.getProductById(ProductId);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product, @RequestPart MultipartFile imageFile){

        Product product1 = null;
        try{
            product1 = service.updateProduct(id, product, imageFile);
        }
        catch (IOException e){
            return new ResponseEntity<>("Product Not Updated", HttpStatus.NOT_FOUND);
        }
        if (product1 != null) {
            return new ResponseEntity<>("Product Updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product Not Updated", HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product Not Deleted", HttpStatus.NOT_FOUND);
    }
}
