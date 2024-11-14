package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.UserDTO;
import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import com.example.onlinecourseplatform.service.baseService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UUID, UserDTO> {

    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    
    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> findByName(@PathVariable String name) {
        Optional<UserDTO> userDTO = userService.findByName(name);
        return userDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> findByRole(@PathVariable UserRole role) {
        List<UserDTO> userDTOList = userService.findByRole(role);
        if (userDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTOList);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        UserDTO savedUser = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody UserDTO dto) {
        UserDTO updatedUser = userService.update(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
