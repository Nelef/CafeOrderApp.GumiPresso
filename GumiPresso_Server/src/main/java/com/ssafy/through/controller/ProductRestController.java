package com.ssafy.through.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.through.model.dto.Comment;
import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.service.CommentService;
import com.ssafy.through.model.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/product")
@Api(value = "Product")
public class ProductRestController {
	@Autowired
	private ProductService pService;
	@Autowired
	private CommentService cService;

	@ApiOperation(value = "모든 Product 정보 return: List<Product>", response = User.class)
	@GetMapping("/")
	public ResponseEntity<?> getProductList() {
		List<Product> products = pService.search();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	@ApiOperation(value = "product_id에 해당하는 Product return: Product", response = User.class)
	@GetMapping("/{product_id}")
	public ResponseEntity<?> me(@PathVariable String product_id) {
		Product selectedProduct = pService.select(product_id);		
		if (selectedProduct != null) {
			return new ResponseEntity<Product>(selectedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(selectedProduct, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/select/{product_id}")
	public ResponseEntity<?> getSelectProduct(@PathVariable String product_id){
		Product selectedProduct = pService.select(product_id);
		List<Comment> comment = cService.search(selectedProduct.getId());		
		if (selectedProduct != null) {
			Product newProduct = new Product(selectedProduct.getId(), selectedProduct.getName(),
					selectedProduct.getType(), selectedProduct.getPrice(),
					selectedProduct.getImg(), comment);
			return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
