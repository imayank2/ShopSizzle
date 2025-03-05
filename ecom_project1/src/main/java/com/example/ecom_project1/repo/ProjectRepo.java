package com.example.ecom_project1.repo;


import com.example.ecom_project1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository <Product, Integer>{

}
