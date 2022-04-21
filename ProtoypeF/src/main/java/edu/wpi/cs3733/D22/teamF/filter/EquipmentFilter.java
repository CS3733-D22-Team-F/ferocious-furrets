package edu.wpi.cs3733.D22.teamF.filter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EquipmentFilter implements IFilter{
    private String equipToFilter = "";

    public EquipmentFilter(String filterBy) {
        this.equipToFilter = filterBy;
    }

    @Override
    public ArrayList<Object> apply(ResultSet rSet) {
        return null;
    }
}
