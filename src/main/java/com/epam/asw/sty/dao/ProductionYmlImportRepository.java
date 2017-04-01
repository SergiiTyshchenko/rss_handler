package com.epam.asw.sty.dao;

public class ProductionYmlImportRepository implements YmlImportRepository {

    @Override
    public String getYmlImportData() {
        StringBuffer ymlImport = new StringBuffer();
        ymlImport.append("TEST YML").append("\n");
        return ymlImport.toString();
    }
}
