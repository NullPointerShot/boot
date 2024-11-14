package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CartMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CartDTO;
import com.example.onlinecourseplatform.exception.InsufficientFundsException;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Cart;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.repository.CartItemRepository;
import com.example.onlinecourseplatform.repository.CartRepository;
import com.example.onlinecourseplatform.repository.CourseRepository;
import com.example.onlinecourseplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Course course;
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setBalance(new BigDecimal("100.00")); 

        course = new Course();
        course.setId(UUID.randomUUID());
        course.setPrice(new BigDecimal("50.00")); 

        cart = new Cart();
        cart.setUser(user);
    }

    @Test
    void testAddCourseToCart_UserNotFound() {
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addCourseToCart(userId, courseId));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testAddCourseToCart_CourseNotFound() {
        UUID userId = user.getId();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addCourseToCart(userId, courseId));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testAddCourseToCart_CartCreated() {
        UUID userId = user.getId();
        UUID courseId = course.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        cartService.addCourseToCart(userId, courseId);

        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        assertNotNull(cartCaptor.getValue());
        assertEquals(user, cartCaptor.getValue().getUser());
    }

    @Test
    void testPurchaseAllCoursesInCart_UserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.purchaseAllCoursesInCart(userId));
    }

    @Test
    void testPurchaseAllCoursesInCart_CartNotFound() {
        UUID userId = user.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.purchaseAllCoursesInCart(userId));
    }

    @Test
    void testPurchaseAllCoursesInCart_InsufficientFunds() {
        UUID userId = user.getId();
        CartItem cartItem = CartItem.builder()
                .course(course)
                .quantity(3)
                .build();
        cart.setItems(List.of(cartItem));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        assertThrows(InsufficientFundsException.class, () -> cartService.purchaseAllCoursesInCart(userId));
    }


    @Test
    void testPurchaseAllCoursesInCart_SuccessfulPurchase() {
        
        UUID userId = UUID.randomUUID();
        Course course = new Course(); 
        Cart cart = new Cart(); 

        
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart)); 
        when(cartItemRepository.findByCartAndCourse(cart, course)).thenReturn(Optional.empty());

        
        cartService.addCourseToCart(userId, course.getId());

        
        verify(cartItemRepository).save(any(CartItem.class)); 
    }
}
