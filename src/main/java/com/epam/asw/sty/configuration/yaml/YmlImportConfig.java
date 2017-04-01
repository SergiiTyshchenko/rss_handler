package com.epam.asw.sty.configuration.yaml;

import com.epam.asw.sty.dao.ProductionYmlImportRepository;
import com.epam.asw.sty.dao.YmlImportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YmlImportConfig {

    @Bean
    public YmlImportRepository ymlImportRepository() {
        return new ProductionYmlImportRepository();
    }
}
