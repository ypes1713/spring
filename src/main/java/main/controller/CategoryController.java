package main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import main.model.Category;
import main.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String getCategory(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/addCategories")
	public String showForm(Model model) {
		model.addAttribute("category", new Category());
		return "categories-form";
	}
	
	@PostMapping("/processForm")
	public String processCategory(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/deleteCategories/{id}")
	public String deleteCategories (@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/categories";
	}
	
	@GetMapping("/updateCategories/{id}")
	public String updateCategories (@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categories-form";
		}else
			return "404";
	}
}
