package com.epam.asw.sty.service;
import com.epam.asw.sty.dao.YmlImportRepository;
import com.epam.asw.sty.configuration.yaml.YmlImportSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class YmlImportService {

    @Resource
    private YmlImportSettings ymlImportSettings;

    @Resource
    private YmlImportRepository ymlImportRepository;

    public boolean requiresYml() {
        if (ymlImportSettings.getWow() > 5 && ymlImportSettings.isVery() == true) {
            return true;
        }
        return false;
    }

    public String gettingYml() {
        return ymlImportRepository.getYmlImportData();
    }
}
