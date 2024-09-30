package org.iesalixar.daw2.nombrealumno.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.nombrealumno.dao.ProvinceDAO;
import org.iesalixar.daw2.nombrealumno.dao.ProvinceDAOImpl;
import org.iesalixar.daw2.nombrealumno.entity.Province;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/provinces")
public class ProvinceServlet extends HttpServlet {

    private ProvinceDAO provinceDAO;

    @Override
    public void init() throws ServletException {
        try{
            provinceDAO = new ProvinceDAOImpl();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar el ProvinceDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "new":
                    break;
                case "edit":
                    break;
                default:
                    listProvinces(request, response);
                    break;
            }
        } catch (SQLException | IOException ex) {
                throw new ServletException(ex);
        }
    }

    private void listProvinces(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Province> listProvinces = provinceDAO.listAllProvinces();
        request.setAttribute("listProvinces", listProvinces);
        request.getRequestDispatcher("province.jsp").forward(request, response); // Redirigir a la página JSP
    }

}
