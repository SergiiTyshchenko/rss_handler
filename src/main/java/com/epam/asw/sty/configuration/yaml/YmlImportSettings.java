package com.epam.asw.sty.configuration.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="ymlImport")
public class YmlImportSettings {
    private int wow;
    private String such;
    private boolean very;

    public YmlImportSettings() {}

    public int getWow() {
        return wow;
    }

    public void setWow(int wow) {
        this.wow = wow;
    }

    public String getSuch() {
        return such;
    }

    public void setSuch(String such) {
        this.such = such;
    }

    public boolean isVery() {
        return very;
    }

    public void setVery(boolean very) {
        this.very = very;
    }
}
