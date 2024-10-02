package org.iesalixar.daw2.nombrealumno.dao;

import org.iesalixar.daw2.nombrealumno.entity.Province;

import java.sql.SQLException;
import java.util.List;

public interface ProvinceDAO {

    List<Province> listAllProvinces() throws SQLException;
    void insertProvince(Province province) throws SQLException;
    boolean existsProvinceByCode(String code) throws SQLException;
}
