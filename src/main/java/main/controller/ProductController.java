package main.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import main.model.Product;
import main.model.ProductDTO;
import main.service.CategoryService;
import main.service.ProductService;

@Controller
public class ProductController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages"; 
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/addProducts")
	public String addProducts(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "products-form";
	}
	
	@PostMapping("/productForm")
	public String productForm(@ModelAttribute("productDTO") ProductDTO productDTO,
							  @RequestParam("productImage")MultipartFile file,
			                  @RequestParam("imgName")String imgName) throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		return "redirect:/products";
	}
	
	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct (@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/products";
	}
	
	@GetMapping("/updateproduct/{id}")
	public String updateProduct (@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		
		return "products-form";
	}
}
