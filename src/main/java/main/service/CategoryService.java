package main.service;

import java.util.List;
import java.util.Optional;

import main.model.Category;

public interface CategoryService {
	
	public List<Category> getAllCategory();
	
	public void addCategory(Category category);
	
	public void removeCategoryById(int id);
	
	public Optional<Category> getCategoryById(int id);  

}
