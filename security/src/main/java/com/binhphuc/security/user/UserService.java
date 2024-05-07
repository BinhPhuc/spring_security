package com.binhphuc.security.user;

import com.binhphuc.security.config.JwtService;
import com.binhphuc.security.dtos.LoginDTO;
import com.binhphuc.security.dtos.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public User createUser(RegisterDTO registerDTO) {
        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
        Role role = roleRepository.findById(registerDTO.getRoleId()).orElseThrow(

        );
        user.setRole(role);
        return userRepository.save(user);
    }

    public String login (LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
        if(user.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("cannot login");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                loginDTO.getPassword()
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtService.generateToken(user.get());
    }
}
