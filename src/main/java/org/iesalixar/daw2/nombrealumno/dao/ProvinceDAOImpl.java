package org.iesalixar.daw2.nombrealumno.dao;

import org.iesalixar.daw2.nombrealumno.entity.Province;
import org.iesalixar.daw2.nombrealumno.entity.Region;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAOImpl implements ProvinceDAO{

    public List<Province> listAllProvinces() throws SQLException {
        List<Province> provinces = new ArrayList<>();
        String query = "select p.id id_province, p.code code_province, p.name name_province, r.id id_region, r.code code_region, r.name name_region from provinces p inner join regions r on p.id_region = r.id";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()){
                int id_province = resultSet.getInt("id_province");
                String code_province = resultSet.getString("code_province");
                String name_province = resultSet.getString("name_province");

                int id_region = resultSet.getInt("id_region");
                String code_region = resultSet.getString("code_region");
                String name_region = resultSet.getString("name_region");

                Region region = new Region(id_region, code_region, name_region);
                Province province = new Province(id_province, code_province, name_province, region);
                provinces.add(province);
            }

        }

        return provinces;
    }

}
