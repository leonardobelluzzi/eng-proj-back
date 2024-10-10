package br.com.fatec.fatec_eng3.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank(message = "O nome de usuário não pode estar em branco")
    private String username;

    @NotBlank(message = "A senha não pode estar em branco")
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}