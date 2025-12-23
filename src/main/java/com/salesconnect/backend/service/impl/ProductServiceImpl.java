package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.ProductDTO;
import com.salesconnect.backend.entity.Product;
import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.repository.ProductRepository;
import com.salesconnect.backend.service.ProductService;
import com.salesconnect.backend.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final ProductTransformer productTransformer = new ProductTransformer();

    @Override
    public List<ProductDTO> getAllProducts() {
        return productTransformer.toDTOList(productRepository.findAll());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productTransformer::toDTO).orElse(null);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        if (productRepository.existsByName(productDTO.getName())) {
            throw new RuntimeException("Product with this name already exists.");
        }
        Product product = productTransformer.toEntity(productDTO);
        product.setCompany(user.getCompany());
        return productTransformer.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found.");
        }
        Product product = optionalProduct.get();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return productTransformer.toDTO(productRepository.save(product));
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
