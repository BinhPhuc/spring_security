package com.binhphuc.security.auth;

import com.binhphuc.security.dtos.LoginDTO;
import com.binhphuc.security.dtos.RegisterDTO;
import com.binhphuc.security.user.User;
import com.binhphuc.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final UserService userService;
    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        User user = userService.createUser(registerDTO);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/api/v1/skibidi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> skibidi() {
        return ResponseEntity.ok("skibi secured by admin");
    }
}
