package com.server.model;
import lombok.Data;


@Data
public class ChangePasswordModel {
    private String email;
    private String oldPassword;
    private String newPassword;
}
