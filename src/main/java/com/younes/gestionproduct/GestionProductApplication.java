package com.younes.gestionproduct;

import com.younes.gestionproduct.Repository.ProductRepository;
import com.younes.gestionproduct.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import com.younes.gestionproduct.entities.Product;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class } )
public class GestionProductApplication {

    public static void main(String[] args) {

        SpringApplication.run(GestionProductApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(ProductRepository productRepository) {
         return args -> {
             productRepository.save(Product.builder().name("Radio").price(10).quantity(5).build());
             productRepository.save(Product.builder().name("TV").price(70).quantity(7).build());
             productRepository.save(Product.builder().name("Camera").price(50).quantity(3).build());
             productRepository.save(Product.builder().name("Computer").price(90).quantity(11).build());
             productRepository.save(Product.builder().name("Smartphone").price(120).quantity(8).build());
             productRepository.save(Product.builder().name("Tablet").price(60).quantity(6).build());
             productRepository.save(Product.builder().name("Headphones").price(25).quantity(15).build());
             productRepository.save(Product.builder().name("Keyboard").price(15).quantity(12).build());
             productRepository.save(Product.builder().name("Mouse").price(10).quantity(18).build());
             productRepository.save(Product.builder().name("Speaker").price(35).quantity(9).build());
         };
    }

}
