package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import main.model.Cart;
import main.service.CategoryService;
import main.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin-home";
	}
	
	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("cartCount", Cart.cart.size());
		return "home";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount", Cart.cart.size());
		return "shop";
	}
	
	@GetMapping("/shopCategory/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("cartCount", Cart.cart.size());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";
	}
	
	@GetMapping("/shopViewProduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount", Cart.cart.size());
		return "product-view";
	}
}
