package org.example.duan.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.duan.config.VNPayConfig;
import org.example.duan.entity.*;
import org.example.duan.service.CartService;
import org.example.duan.service.OrderService;
import org.example.duan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Redirect to login page if not logged in
        }

        return "giohang"; // Return the cart page
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("address") String address,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("paymentMethod") int paymentMethod,
                                  HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Redirect to login if not logged in
        }

        if (address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
            model.addAttribute("error", "You must enter both address and phone number.");
            return "redirect:/checkout"; // Redirect back to checkout page to re-enter information
        }

        List<CartEntity> cartItems = cartService.getCartItemsByUsername(loggedInUser.getUsername());
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty. Please add products before checkout.");
            return "redirect:/cart"; // Redirect to cart if empty
        }

        double totalPrice = cartService.getTotalPrice(loggedInUser.getUsername());

        if (paymentMethod == 1) {
            return "redirect:/pay?username=" + loggedInUser.getUsername() + "&totalPrice=" + totalPrice + "&address=" + address + "&phone=" + phone + "&paymentMethod=" + paymentMethod;
        }

        if (paymentMethod == 0) {
            OrderEntity order = new OrderEntity();
            order.setAccount(loggedInUser);
            order.setCreateDate(LocalDateTime.now());
            order.setAddress(address);
            order.setPhone(phone);
            order.setTotal(BigDecimal.valueOf(totalPrice));
            order.setStatus(0);
            order.setPaymentMethod(paymentMethod);

            orderService.saveOrder(order);

            for (CartEntity cartItem : cartItems) {
                OrderDetailsEntity orderDetails = new OrderDetailsEntity();
                ProductEntity product = productService.getProductById(cartItem.getProduct().getId());

                int newQuantity = product.getQuantity() - cartItem.getQuantity();
                if (newQuantity < 0) {
                    model.addAttribute("error", "Product " + product.getName() + " is out of stock.");
                    return "redirect:/cart"; // Redirect to cart if not enough stock
                }
                product.setQuantity(newQuantity);
                productService.updateProduct(product);

                orderDetails.setOrder(order);
                orderDetails.setProduct(product);
                orderDetails.setPrice(BigDecimal.valueOf(product.getPrice()));
                orderDetails.setQuantity(cartItem.getQuantity());
                orderDetails.setColor(cartItem.getColor());
                orderDetails.setSize(cartItem.getSize());

                orderService.saveOrderDetails(orderDetails);
            }

            cartService.clearCart(loggedInUser.getUsername());

            model.addAttribute("notification", "Your order has been successfully placed!");

            return "redirect:/camOn";
        } else {
            model.addAttribute("error", "Invalid payment method.");
            return "redirect:/checkout";
        }
    }

    @GetMapping("/camOn")
    public String showThankYouPage(Model model) {
        model.addAttribute("message", "Thank you for your order!");
        return "camOn"; // Return thank you page
    }
    @GetMapping("/paymentFail")
    public String showPaymentFail(Model model) {
        model.addAttribute("message", "Thanh toán thất bại bạn cút đi ");
        return "paymentFail"; // Return thank you page
    }

    @GetMapping("/vnpay-callback")
    public ResponseEntity<Void> paymentCallback(HttpServletRequest request) {
        String vnp_TxnRef = request.getParameter("vnp_TxnRef"); // Mã giao dịch
        String vnp_Amount = request.getParameter("vnp_Amount"); // Tổng số tiền
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode"); // Trạng thái thanh toán

        HttpSession session = request.getSession();
        String address = (String) session.getAttribute("address");
        String phone = (String) session.getAttribute("phone");
        int paymentMethod = (int) session.getAttribute("paymentMethod");

        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");


        if (loggedInUser == null) {
            return ResponseEntity.status(302).header("Location", "/account/login").build();
        }

        if ("00".equals(vnp_ResponseCode)) { // Thanh toán thành công
            List<CartEntity> cartItems = cartService.getCartItemsByUsername(loggedInUser.getUsername());
            if (cartItems.isEmpty()) {
                return ResponseEntity.status(302).header("Location", "/cart").build();
            }

            double totalPrice = Double.parseDouble(vnp_Amount) / 100; // VNPay gửi số tiền nhân 100

            // Tạo đơn hàng mới
            OrderEntity order = new OrderEntity();
            order.setAccount(loggedInUser);
            order.setCreateDate(LocalDateTime.now());
            order.setAddress(address);
            order.setPhone(phone);
            order.setTotal(BigDecimal.valueOf(totalPrice));
            order.setStatus(0);
            order.setPaymentMethod(paymentMethod);
            orderService.saveOrder(order);

            // Lưu chi tiết đơn hàng
            for (CartEntity cartItem : cartItems) {
                ProductEntity product = productService.getProductById(cartItem.getProduct().getId());
                int newQuantity = product.getQuantity() - cartItem.getQuantity();

                if (newQuantity < 0) {
                    return ResponseEntity.status(302)
                            .header("Location", "/cart")
                            .build(); // Quay lại giỏ hàng nếu hết hàng
                }

                product.setQuantity(newQuantity);
                productService.updateProduct(product);

                OrderDetailsEntity orderDetails = new OrderDetailsEntity();
                orderDetails.setOrder(order);
                orderDetails.setProduct(product);
                orderDetails.setPrice(BigDecimal.valueOf(product.getPrice()));
                orderDetails.setQuantity(cartItem.getQuantity());
                orderDetails.setColor(cartItem.getColor());
                orderDetails.setSize(cartItem.getSize());
                orderService.saveOrderDetails(orderDetails);
            }

            // Xóa giỏ hàng sau khi thanh toán thành công
            cartService.clearCart(loggedInUser.getUsername());

            // Chuyển hướng tới trang cảm ơn
            return ResponseEntity.status(302).header("Location", "/camOn").build();
        } else {
            // Thanh toán thất bại
            return ResponseEntity.status(302).header("Location", "/paymentFail").build();
        }
    }


    @GetMapping("/pay")
    public ResponseEntity<String> getPay(@RequestParam("username") String username,
                                         @RequestParam("totalPrice") double totalPrice, @RequestParam("address") String address,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("paymentMethod") int paymentMethod, HttpServletRequest request)
            throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        session.setAttribute("address", address);
        session.setAttribute("phone", phone);
        session.setAttribute("paymentMethod", paymentMethod);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        int amount = (int) (totalPrice * 100);
        String bankCode = "NCB";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(6);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", paymentUrl.toString())
                .build();
    }
}
