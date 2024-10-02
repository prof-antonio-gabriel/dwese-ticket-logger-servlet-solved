package org.iesalixar.daw2.nombrealumno.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.nombrealumno.dao.ProvinceDAO;
import org.iesalixar.daw2.nombrealumno.dao.ProvinceDAOImpl;
import org.iesalixar.daw2.nombrealumno.dao.RegionDAO;
import org.iesalixar.daw2.nombrealumno.dao.RegionDAOImpl;
import org.iesalixar.daw2.nombrealumno.entity.Province;
import org.iesalixar.daw2.nombrealumno.entity.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/provinces")
public class ProvinceServlet extends HttpServlet {

    private ProvinceDAO provinceDAO;
    private RegionDAO regionDAO;

    @Override
    public void init() throws ServletException {
        try{
            provinceDAO = new ProvinceDAOImpl();
            regionDAO = new RegionDAOImpl();
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
                    showNewForm(request, response);
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertProvince(request, response);  // Insertar nueva región
                    break;
                case "update":
                    break;
                case "delete":
                    break;
                default:
                    listProvinces(request, response);   // Listar todas las regiones
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProvinces(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Province> listProvinces = provinceDAO.listAllProvinces();
        request.setAttribute("listProvinces", listProvinces);
        request.getRequestDispatcher("province.jsp").forward(request, response); // Redirigir a la página JSP
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Region> listRegions = regionDAO.listAllRegions();
        request.setAttribute("listRegions", listRegions);
        request.getRequestDispatcher("province-form.jsp").forward(request, response); // Redirige a la vista para nueva región
    }

    private void insertProvince(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String id_region = request.getParameter("id_region");

        // Validaciones básicas
        if (code.isEmpty() || name.isEmpty() || id_region.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre y la comunidad autonoma no pueden estar vacíos.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Validar si el código ya existe ignorando mayúsculas
        if (provinceDAO.existsProvinceByCode(code)) {
            request.setAttribute("errorMessage", "El código de la provincia ya existe.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        Province province = new Province();
        province.setCode(code);
        province.setName(name);

        Region region = regionDAO.getRegionById(Integer.parseInt(id_region));
        province.setRegion(region);

        try{
            provinceDAO.insertProvince(province);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Código SQL para unique constraint violation
                request.setAttribute("errorMessage", "El código de la provincia debe ser único.");
                request.getRequestDispatcher("province-form.jsp").forward(request, response);
            } else {
                throw e;
            }
        }
        response.sendRedirect("provinces");

    }

}
