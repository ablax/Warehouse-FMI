package me.ablax.warehouse.models.req;

public class LoginReq {

    private String username;
    private String password;

    public LoginReq() {
    }

    public LoginReq(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
