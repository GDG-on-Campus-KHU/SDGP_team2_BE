package com.gdg.coffee.domain.auth.dto.response;

public class GoogleUserInfoDto {
    private String name;
    private String email;

    public GoogleUserInfoDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}