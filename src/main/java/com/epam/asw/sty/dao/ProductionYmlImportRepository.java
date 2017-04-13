package com.epam.asw.sty.dao;

public class ProductionYmlImportRepository implements YmlImportRepository {

    @Override
    public String getYmlImportData() {
        return "TEST YML" + "\n";
    }
}
