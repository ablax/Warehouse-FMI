package me.ablax.warehouse.utils;

import jakarta.servlet.http.HttpSession;
import me.ablax.warehouse.models.UserDto;

public final class AuthenticationUtils {

    private AuthenticationUtils(){
        //Utils class
    }

    public static boolean isLoggedIn(final HttpSession session) {
        final Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return false;
        }
        if (!(userObj instanceof final UserDto user)) {
            return false;
        }

        return user.getUserId() != null;
    }

    public static void login(final HttpSession httpSession, final UserDto userDto) {
        httpSession.setAttribute("user", userDto);
    }

    public static UserDto getUser(final HttpSession session) {
        final Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return null;
        }
        if (!(userObj instanceof final UserDto user)) {
            return null;
        }

        return user;
    }

}
