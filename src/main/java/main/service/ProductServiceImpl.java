package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.Product;
import main.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public void addProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public void removeProductById(long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Optional<Product> getProductById(long id) {
		return productRepository.findById(id);
	}

	@Override
	public List<Product> getAllProductsByCategoryId(int id) {
		return productRepository.findAllByCategory_Id(id);
	}

}
