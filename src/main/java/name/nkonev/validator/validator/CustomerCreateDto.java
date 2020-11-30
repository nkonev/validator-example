package name.nkonev.validator.validator;

import javax.validation.constraints.NotNull;

public class CustomerCreateDto {
    @NotNull
    private String name;
    @UniqueEmail
    @NotNull
    private String email;

    public CustomerCreateDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
