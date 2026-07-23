package com.bc.cleaning.util;

import com.bc.cleaning.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class ServletUtil {
    private ServletUtil() {
    }

    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object user = session.getAttribute("loggedInUser");
        return user instanceof User ? (User) user : null;
    }

    public static boolean isSupervisor(HttpServletRequest request) {
        User user = getLoggedInUser(request);
        return user != null && user.isSupervisor();
    }

    public static void setFlash(HttpServletRequest request, String type, String message) {
        request.getSession().setAttribute("flashType", type);
        request.getSession().setAttribute("flashMessage", message);
    }
}
