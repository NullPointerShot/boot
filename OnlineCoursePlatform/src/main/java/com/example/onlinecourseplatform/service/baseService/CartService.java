package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CartMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CartDTO;
import com.example.onlinecourseplatform.exception.InsufficientFundsException;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.*;
import com.example.onlinecourseplatform.repository.*;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CartService extends BaseService<Cart, UUID, CartDTO> {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private CourseRepository courseRepository;
    private CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartMapper cartMapper, UserRepository userRepository, CompanyRepository companyRepository, CourseRepository courseRepository, CartItemRepository cartItemRepository) {
        super(cartRepository, cartMapper);
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.courseRepository = courseRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addCourseToCart(UUID userId, UUID courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .user(user)
                                .build()
                ));

        
        CartItem cartItem = cartItemRepository.findByCartAndCourse(cart, course)
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .cart(cart)
                            .course(course)
                            .quantity(0)
                            .build();
                    return cartItemRepository.save(newItem);
                });

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }




    @Transactional
    public void purchaseAllCoursesInCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getCourse().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (user.getBalance().compareTo(totalAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds to complete purchase.");
        }

        cart.getItems().forEach(cartItem -> {
            Course course = cartItem.getCourse();
            BigDecimal cost = course.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            user.setBalance(user.getBalance().subtract(cost));
            Company company = course.getCompany();
            company.setCapital(company.getCapital().add(cost));
            companyRepository.save(company);
        });

        userRepository.save(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }



}


