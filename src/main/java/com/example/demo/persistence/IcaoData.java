package com.example.demo.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class IcaoData {

    public IcaoData() {}

    public IcaoData(String icao, String lastUpdate, String name, String url) {
        this.icao = icao;
        this.lastUpdate = lastUpdate;
        this.name = name;
        this.url = url;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("ICAO")
    private String icao;
    @JsonProperty("last_update")
    private String lastUpdate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(this.getClass()))
            return false;
        else {
            return ((IcaoData) o).getIcao().equals(this.getIcao());
        }
    }

}
