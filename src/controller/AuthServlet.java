package controller;

import dao.UserDAO;
import model.User;
import com.bc.cleaning.util.PasswordUtil;
import util.ServletUtil;
import com.bc.cleaning.util.ValidationUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (ServletUtil.getLoggedInUser(request) != null && !"/register".equals(path)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if ("/register".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/register".equals(request.getServletPath())) {
            register(request, response);
        } else {
            login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = ValidationUtil.clean(request.getParameter("username"));
        String password = request.getParameter("password");

        try {
            if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(password)) {
                throw new IllegalArgumentException("Username and password are required.");
            }
            User user = userDAO.findByUsername(username);
            if (user == null || !PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                throw new IllegalArgumentException("Invalid username or password.");
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", user);
            session.setMaxInactiveInterval(30 * 60);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IllegalArgumentException | SQLException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = ValidationUtil.clean(request.getParameter("fullName"));
        String username = ValidationUtil.clean(request.getParameter("username"));
        String email = ValidationUtil.clean(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = ValidationUtil.clean(request.getParameter("role"));

        try {
            if (ValidationUtil.isBlank(fullName) || ValidationUtil.isBlank(username)
                    || ValidationUtil.isBlank(email) || ValidationUtil.isBlank(password)
                    || ValidationUtil.isBlank(confirmPassword) || ValidationUtil.isBlank(role)) {
                throw new IllegalArgumentException("All registration fields are required.");
            }
            if (!ValidationUtil.isValidEmail(email)) {
                throw new IllegalArgumentException("Enter a valid email address.");
            }
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match.");
            }
            if (!PasswordUtil.isStrongPassword(password)) {
                throw new IllegalArgumentException("Password must be at least 8 characters and contain uppercase, lowercase and a number.");
            }
            if (!"STOREKEEPER".equals(role) && !"SUPERVISOR".equals(role)) {
                throw new IllegalArgumentException("Select a valid role.");
            }
            if (userDAO.usernameExists(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (userDAO.emailExists(email)) {
                throw new IllegalArgumentException("Email address already exists.");
            }

            User user = new User();
            user.setFullName(fullName);
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(role);
            user.setPasswordHash(PasswordUtil.hashPassword(password));
            userDAO.create(user);

            ServletUtil.setFlash(request, "success", "Registration successful. Please log in.");
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IllegalArgumentException | SQLException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("fullName", fullName);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("role", role);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
