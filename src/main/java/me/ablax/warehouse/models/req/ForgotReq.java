package me.ablax.warehouse.models.req;

public class ForgotReq {
    private String email;

    public ForgotReq() {
    }

    public ForgotReq(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ForgotReq{" +
                "email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
