package com.product.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.test.exception.ResourceNotFoundException;
import com.product.test.model.Product;
import com.product.test.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	// get all employees
	@GetMapping("/products")
	public Page<Product> getAllProducts( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		PageRequest request = PageRequest.of ( page, size );
		Page<Product> result = productRepository.findAll(request);
		return result;
	}		
	
	// create employee rest api
	@PostMapping("/products")
	public Product createProducts(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	// get employee by id rest api
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException ("Employee not exist with id :" + id));
		return ResponseEntity.ok(product);
	}
	
	// update employee rest api
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		product.setCode (productDetails.getCode ());
		product.setName (productDetails.getName ());
		product.setCategory (productDetails.getCategory ());
		product.setBrand (productDetails.getBrand () );
		product.setType ( productDetails.getType ());
		product.setDescription(productDetails.getDescription ());
		Product updatedProduct = productRepository.save(product);
		return ResponseEntity.ok(updatedProduct);
	}
	
	// delete employee rest api
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
