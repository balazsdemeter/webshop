package hu.webuni.user.dto;

import lombok.Data;

@Data
public class RegisterDto extends LoginDto {
    private String email;
}