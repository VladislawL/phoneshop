package com.es.core.model.phone;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
@PropertySource(value = "classpath:config/application.properties")
public abstract class AbstractDataBaseIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${db.testDataRootPath}")
    private String testDataPath;

    public void fillDataBase(String scriptFile) throws IOException {
        jdbcTemplate.execute(IOUtils.resourceToString(testDataPath + scriptFile, StandardCharsets.UTF_8));
    }

    public void cleanDataBase() throws IOException {
        jdbcTemplate.execute(IOUtils.resourceToString("/db/schema.sql", StandardCharsets.UTF_8));
    }

}
