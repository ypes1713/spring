package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import main.model.Cart;
import main.model.Product;

import main.service.ProductService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/cart")
	public String getcart(Model model) {
		model.addAttribute("cartCount", Cart.cart.size());
		model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", Cart.cart);
		return "cart";
	}
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		Cart.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";

	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		Cart.cart.remove(index);
		return "redirect:/cart";
	}
}	
