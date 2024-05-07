package com.binhphuc.security.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginDTO {
    private String email;
    private String password;
}
