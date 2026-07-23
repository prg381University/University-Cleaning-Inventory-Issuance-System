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
    
    // data access objects for handling database operations
    private IssuanceDAO issuanceDAO = new IssuanceDAO();
    private MaterialDAO materialDAO = new MaterialDAO();
    private CleanerDAO cleanerDAO = new CleanerDAO();
    
    // handles post requests - used for adding new issuances
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get the action parameter to know what to do
        String action = request.getParameter("action");
        
        // if the action is "add", we are issuing new stock
        if ("add".equals(action)) {
            try {
                // get all the form data from the request
                int materialId = Integer.parseInt(request.getParameter("materialId"));
                int cleanerId = Integer.parseInt(request.getParameter("cleanerId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String notes = request.getParameter("notes");
                
                // for now we use 1 as the default user id
                // later we will get this from the session when login is implemented
                int issuedBy = 1;
                
                // call the dao method that handles stock deduction and saving
                issuanceDAO.issueStock(materialId, cleanerId, quantity, notes, issuedBy);
                
                // show success message to the user
                request.getSession().setAttribute("successMessage", 
                    "Stock issued successfully! " + quantity + " items deducted.");
                
            } catch (SQLException e) {
                // database error - show the error message
                request.getSession().setAttribute("errorMessage", 
                    "Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                // user entered invalid numbers
                request.getSession().setAttribute("errorMessage", 
                    "Please enter valid numbers.");
            }
            
            // redirect back to the list page so the user sees the updated list
            response.sendRedirect(request.getContextPath() + "/IssuanceServlet?action=list");
        }
    }
    
    // handles get requests - used for viewing, deleting and searching
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get the action parameter to know what to do
        String action = request.getParameter("action");
        
        // if no action or "list", show all issuances
        if (action == null || "list".equals(action)) {
            try {
                // get all the data needed for the page
                List<Issuance> issuances = issuanceDAO.getAllIssuances();
                List<Material> materials = materialDAO.getAllMaterials();
                List<Cleaner> cleaners = cleanerDAO.getAllCleaners();
                
                // store the data in request attributes so the jsp can access them
                request.setAttribute("issuancesList", issuances);
                request.setAttribute("materialsList", materials);
                request.setAttribute("cleanersList", cleaners);
                
            } catch (Exception e) {
                // show error if data loading fails
                request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            }
            
            // forward to the jsp page to display the data
            request.getRequestDispatcher("/WEB-INF/views/issuance.jsp").forward(request, response);
            
        } else if ("delete".equals(action)) {
            // user wants to delete an issuance
            int id = Integer.parseInt(request.getParameter("id"));
            
            try {
                // delete the issuance and automatically return the stock
                issuanceDAO.returnStock(id);
                
                // tell the user it worked
                request.getSession().setAttribute("successMessage", 
                    "Issuance deleted and stock returned.");
                    
            } catch (SQLException e) {
                // show error if deletion fails
                request.getSession().setAttribute("errorMessage", 
                    "Error: " + e.getMessage());
            }
            
            // redirect back to the list page
            response.sendRedirect(request.getContextPath() + "/IssuanceServlet?action=list");
            
        } else if ("search".equals(action)) {
            // user wants to search issuances
            String query = request.getParameter("query");
            
            try {
                // search for issuances matching the query
                List<Issuance> issuances = issuanceDAO.searchIssuances(query);
                List<Material> materials = materialDAO.getAllMaterials();
                List<Cleaner> cleaners = cleanerDAO.getAllCleaners();
                
                // store the data in request attributes
                request.setAttribute("issuancesList", issuances);
                request.setAttribute("materialsList", materials);
                request.setAttribute("cleanersList", cleaners);
                request.setAttribute("searchQuery", query);
                
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error searching: " + e.getMessage());
            }
            
            // forward to the jsp page
            request.getRequestDispatcher("/WEB-INF/views/issuance.jsp").forward(request, response);
        }
    }
}