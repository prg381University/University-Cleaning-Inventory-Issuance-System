package controller;

import model.Material;
import dao.MaterialDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/MaterialServlet")
public class MaterialServlet extends HttpServlet {

    private MaterialDAO materialDAO;

    public void init() {
        materialDAO = new MaterialDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int reorderLevel = Integer.parseInt(request.getParameter("reorderLevel"));

            Material newMaterial = new Material(0, name, quantity, reorderLevel);
            materialDAO.addMaterial(newMaterial);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int reorderLevel = Integer.parseInt(request.getParameter("reorderLevel"));

            Material material = new Material(id, name, quantity, reorderLevel);
            materialDAO.updateMaterial(material);
        }

        response.sendRedirect(request.getContextPath() + "/MaterialServlet?action=list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || "list".equals(action)) {
            List<Material> materials = materialDAO.getAllMaterials();
            request.setAttribute("materialsList", materials);
            request.getRequestDispatcher("/jsp/materials.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            materialDAO.deleteMaterial(id);
            response.sendRedirect(request.getContextPath() + "/MaterialServlet?action=list");

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Material existingMaterial = materialDAO.getMaterial(id);
            request.setAttribute("material", existingMaterial);
            request.getRequestDispatcher("/jsp/editMaterial.jsp").forward(request, response);
        }
    }
}