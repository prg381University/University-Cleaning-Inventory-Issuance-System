package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.*;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/AuthServlet");
            return;
        }

        try {
            MaterialDAO materialDAO = new MaterialDAO();
            CleanerDAO cleanerDAO = new CleanerDAO();
            SupplierDAO supplierDAO = new SupplierDAO();
            request.setAttribute("totalMaterials", materialDAO.getAllMaterials().size());
            request.setAttribute("totalCleaners", cleanerDAO.getAllCleaners().size());
            request.setAttribute("totalSuppliers", supplierDAO.getAllSuppliers().size());
            request.setAttribute("lowStock", materialDAO.getAllLowStock().size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(request, response);
    }
}