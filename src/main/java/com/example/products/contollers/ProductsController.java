package com.example.products.contollers;


import com.example.products.entities.Product;
import com.example.products.entities.UserInfo;
import com.example.products.repositories.ProductRepository;
import com.example.products.repositories.UserRepository;
import com.example.products.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            UserInfo userInfo = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: "));

            String email = userInfo.getEmail();
            return "admin@admin.com".equals(email);
        }
        return false;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden si l'utilisateur n'est pas admin
        }

        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return ResponseEntity.ok(productRepository.save(product));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden si l'utilisateur n'est pas admin
        }
        return productRepository.findById(id).map(product -> {
            if (updatedProduct.getName() != null) product.setName(updatedProduct.getName());
            if (updatedProduct.getDescription() != null) product.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getPrice() != 0) product.setPrice(updatedProduct.getPrice());
            product.setUpdatedAt(Instant.now());
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden si l'utilisateur n'est pas admin
        }
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
