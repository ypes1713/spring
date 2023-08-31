package main.service;

import java.util.List;
import java.util.Optional;

import main.model.Product;

public interface ProductService {

	public List<Product> getAllProduct();
	
	public void addProduct (Product product);
	
	public void removeProductById(long id);
	
	public Optional<Product> getProductById(long id);
	
//	public Product getProductById(long id);
	
	public List<Product> getAllProductsByCategoryId(int id);
}
