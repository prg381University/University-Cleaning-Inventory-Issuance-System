package controller;

import dao.SupplierDAO;
import model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SupplierServlet")
public class SupplierServlet extends HttpServlet {

    private SupplierDAO supplierDAO = new SupplierDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addSupplier(request, response);
        } else if ("update".equals(action)) {
            updateSupplier(request, response);
        } else {
            response.sendRedirect("SupplierServlet?action=list");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || "list".equals(action)) {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            request.setAttribute("suppliersList", suppliers);
            request.getRequestDispatcher("/WEB-INF/views/suppliers.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            supplierDAO.deleteSupplier(id);
            response.sendRedirect("SupplierServlet?action=list");

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Supplier existingSupplier = supplierDAO.getSupplierById(id);
            request.setAttribute("editSupplier", existingSupplier);
            request.getRequestDispatcher("/WEB-INF/views/editSupplier.jsp").forward(request, response);

        } else if ("search".equals(action)) {
            String query = request.getParameter("query");
            List<Supplier> suppliers = supplierDAO.searchSuppliers(query);
            request.setAttribute("suppliersList", suppliers);
            request.getRequestDispatcher("/WEB-INF/views/suppliers.jsp").forward(request, response);
        }
    }

    private void addSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String supplierName = request.getParameter("supplierName");
        String contactPerson = request.getParameter("contactPerson");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier(0, supplierName, contactPerson, phone, email, address);
        supplierDAO.addSupplier(supplier);

        response.sendRedirect("SupplierServlet?action=list");
    }

    private void updateSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        String supplierName = request.getParameter("supplierName");
        String contactPerson = request.getParameter("contactPerson");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier(supplierId, supplierName, contactPerson, phone, email, address);
        supplierDAO.updateSupplier(supplier);

        response.sendRedirect("SupplierServlet?action=list");
    }
}