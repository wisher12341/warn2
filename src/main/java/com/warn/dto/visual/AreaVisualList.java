package com.warn.dto.visual;

import java.util.List;

public class AreaVisualList {
    private String date;
    private List<AreaVisual> areaVisuals;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AreaVisual> getAreaVisuals() {
        return areaVisuals;
    }

    public void setAreaVisuals(List<AreaVisual> areaVisuals) {
        this.areaVisuals = areaVisuals;
    }
}
