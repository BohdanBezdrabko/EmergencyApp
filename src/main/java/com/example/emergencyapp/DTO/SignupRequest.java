package com.example.emergencyapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest { // виправлено на SignupRequest
    private String username;
    private String email;
    private String password;
}
