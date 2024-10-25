package br.com.fatec.fatec_eng3.controller.dto;

public class LoginResponseDTO {
  private String token;
    private Long id;
    private String username;
    private Long coins;


    public LoginResponseDTO(String token, Long id, String username, Long coins) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.coins = coins;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }
}
