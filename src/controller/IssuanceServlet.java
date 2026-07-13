package controller;

import dao.IssuanceDAO;
import dao.MaterialDAO;
import dao.CleanerDAO;
import model.Issuance;
import model.Material;
import model.Cleaner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/IssuanceServlet")
public class IssuanceServlet extends HttpServlet {
    
    private IssuanceDAO issuanceDAO = new IssuanceDAO();
    private MaterialDAO materialDAO = new MaterialDAO();
    private CleanerDAO cleanerDAO = new CleanerDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            try {
                int materialId = Integer.parseInt(request.getParameter("materialId"));
                int cleanerId = Integer.parseInt(request.getParameter("cleanerId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String notes = request.getParameter("notes");
                
                int issuedBy = 1;
                
                issuanceDAO.issueStock(materialId, cleanerId, quantity, notes, issuedBy);
                
                request.getSession().setAttribute("successMessage", 
                    "Stock issued successfully! " + quantity + " items deducted.");
                
            } catch (SQLException e) {
                request.getSession().setAttribute("errorMessage", 
                    "Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", 
                    "Please enter valid numbers.");
            }
            
            response.sendRedirect(request.getContextPath() + "/IssuanceServlet?action=list");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null || "list".equals(action)) {
            try {
                List<Issuance> issuances = issuanceDAO.getAllIssuances();
                List<Material> materials = materialDAO.getAllMaterials();
                List<Cleaner> cleaners = cleanerDAO.getAllCleaners();
                
                request.setAttribute("issuancesList", issuances);
                request.setAttribute("materialsList", materials);
                request.setAttribute("cleanersList", cleaners);
                
            } catch (Exception e) {  // ← FIXED: catch Exception instead of SQLException
                request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            }
            
            request.getRequestDispatcher("/jsp/issuance.jsp").forward(request, response);
            
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            
            try {
                issuanceDAO.returnStock(id);
                
                request.getSession().setAttribute("successMessage", 
                    "Issuance deleted and stock returned.");
                    
            } catch (SQLException e) {
                request.getSession().setAttribute("errorMessage", 
                    "Error: " + e.getMessage());
            }
            
            response.sendRedirect(request.getContextPath() + "/IssuanceServlet?action=list");
        }
    }
}