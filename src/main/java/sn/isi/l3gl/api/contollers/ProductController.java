package sn.isi.l3gl.api.contollers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.l3gl.core.models.Product;     
import sn.isi.l3gl.core.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> listProducts() {
        return productService.listProducts();
    }

    @PutMapping("/{id}")
    public Product updateQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return productService.updateQuantity(id, quantity);
    }

    @GetMapping("/low-stock/count")
    public long countLowStockProducts() {
        return productService.countLowStockProducts();
    }
}