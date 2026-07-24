package controller;

import dao.CleanerDAO;
import dao.IssuanceDAO;
import dao.MaterialDAO;
import dao.SupplierDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"", "/HomeServlet"})
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            MaterialDAO materialDAO = new MaterialDAO();
            CleanerDAO cleanerDAO = new CleanerDAO();
            SupplierDAO supplierDAO = new SupplierDAO();
            IssuanceDAO issuanceDAO = new IssuanceDAO();
            request.setAttribute("totalMaterials", materialDAO.getAllMaterials().size());
            request.setAttribute("totalCleaners", cleanerDAO.getAllCleaners().size());
            request.setAttribute("totalSuppliers", supplierDAO.getAllSuppliers().size());
            request.setAttribute("lowStock", materialDAO.getAllLowStock().size());
            request.setAttribute("recentIssuances", issuanceDAO.getAllIssuances());
        } catch (Exception e) {
            e.printStackTrace();
        }


        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}