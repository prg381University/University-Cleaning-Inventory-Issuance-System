package controller;

import model.Cleaner;
import dao.CleanerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CleanerServlet")
public class CleanerServlet extends HttpServlet {

    private CleanerDAO cleanerDAO;

    public void init() {
        cleanerDAO = new CleanerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String fullName = request.getParameter("fullName");
            String department = request.getParameter("department");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            Cleaner cleaner = new Cleaner(fullName, department, phone, email);
            cleanerDAO.addCleaner(cleaner);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String department = request.getParameter("department");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            Cleaner cleaner = new Cleaner(id, fullName, department, phone, email);
            cleanerDAO.updateCleaner(cleaner);
        }

        response.sendRedirect(request.getContextPath() + "/CleanerServlet?action=list");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || "list".equals(action)) {
            List<Cleaner> cleaners = cleanerDAO.getAllCleaners();
            request.setAttribute("cleanersList", cleaners);
            request.getRequestDispatcher("/WEB-INF/views/cleaners.jsp").forward(request, response);

        }  else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/addCleaner.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            cleanerDAO.deleteCleaner(id);
            response.sendRedirect(request.getContextPath() + "/CleanerServlet?action=list");

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Cleaner existingCleaner = cleanerDAO.getCleaner(id);
            request.setAttribute("cleaner", existingCleaner);
            request.getRequestDispatcher("/WEB-INF/views/editCleaner.jsp").forward(request, response);

        } else if ("search".equals(action)) {
            String query = request.getParameter("query");
            List<Cleaner> cleaners = cleanerDAO.searchCleaners(query);
            request.setAttribute("cleanersList", cleaners);
            request.getRequestDispatcher("/WEB-INF/views/cleaners.jsp").forward(request, response);
        }
    }
}