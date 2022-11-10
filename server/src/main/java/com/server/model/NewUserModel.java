package com.server.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserModel {
    @NotNull
    @NotEmpty
    @Size(max=50)
    private String email;

    @NotNull
    @NotEmpty
    @Size(max=20)
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repeatedPassword;
}
