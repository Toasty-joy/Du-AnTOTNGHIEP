package org.example.duan.service;

import org.example.duan.entity.CartEntity;
import org.example.duan.entity.ProductEntity;
import org.example.duan.repository.CartRepository;
import org.example.duan.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService; // Để lấy giá sản phẩm
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductService productService, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // Tính tổng số lượng sản phẩm trong giỏ hàng
    public int getTotalQuantity(String username) {
        List<CartEntity> cartItems = cartRepository.findByUsername(username);
        int totalQuantity = 0;
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartEntity cartItem : cartItems) {
                totalQuantity += cartItem.getQuantity();
            }
        } else {
            System.out.println("Giỏ hàng trống hoặc không tìm thấy giỏ hàng cho người dùng: " + username);
        }
        System.out.println("Total Quantity: " + totalQuantity);  // Debugging
        return totalQuantity;
    }

    // Lấy danh sách sản phẩm trong giỏ hàng theo username
    public List<CartEntity> getCartItemsByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    // Tính tổng giá trị giỏ hàng
    public double getTotalPrice(String username) {
        List<CartEntity> cartItems = cartRepository.findByUsername(username);
        double totalPrice = 0;
        for (CartEntity cartItem : cartItems) {
            ProductEntity product = productService.getProductById(cartItem.getProductId());
            double productPrice = product.getPrice();
            totalPrice += productPrice * cartItem.getQuantity(); // Tính tổng giá trị giỏ hàng
        }
        return totalPrice;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProductToCart(String username, int productId, int quantity, int colorId, int sizeId) {
        CartEntity existingCart = cartRepository.findByUsernameAndProductIdAndColorIdAndSizeId(username, productId, colorId, sizeId);

        if (existingCart != null) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartRepository.save(existingCart);
        } else {
            // Nếu chưa có sản phẩm trong giỏ, tạo mới
            CartEntity newCartItem = new CartEntity();
            newCartItem.setUsername(username);
            newCartItem.setProductId(productId);
            newCartItem.setQuantity(quantity);
            newCartItem.setColorId(colorId);
            newCartItem.setSizeId(sizeId);
            cartRepository.save(newCartItem);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProductFromCart(String username, int productId, int colorId, int sizeId) {
        CartEntity cartItem = cartRepository.findByUsernameAndProductIdAndColorIdAndSizeId(username, productId, colorId, sizeId);

        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }
    public void updateProductQuantity(String username, int productId, int colorId, int sizeId, int newQuantity) {
        // Lấy sản phẩm từ cơ sở dữ liệu
        ProductEntity product = productRepository.findById(productId).orElse(null);

        // Kiểm tra nếu sản phẩm tồn tại và số lượng mới lớn hơn 0 và không vượt quá số lượng trong kho
        if (product != null && newQuantity <= product.getQuantity() && newQuantity > 0) {
            // Tìm CartEntity theo username, productId, colorId, sizeId
            CartEntity cartItem = cartRepository.findByUsernameAndProductIdAndColorIdAndSizeId(username, productId, colorId, sizeId);

            if (cartItem != null) {
                cartItem.setQuantity(newQuantity);
                cartRepository.save(cartItem); // Lưu thay đổi
            }
        }

        // Nếu số lượng sản phẩm trong kho là 0, xóa sản phẩm khỏi giỏ hàng
        if (product != null && product.getQuantity() == 0) {
            CartEntity cartItem = cartRepository.findByUsernameAndProductIdAndColorIdAndSizeId(username, productId, colorId, sizeId);
            if (cartItem != null) {
                cartRepository.delete(cartItem); // Xóa sản phẩm khỏi giỏ hàng
            }
        }
    }


}
