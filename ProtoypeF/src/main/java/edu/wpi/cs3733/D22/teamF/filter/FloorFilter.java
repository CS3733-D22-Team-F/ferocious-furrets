package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.observers.Floor;

import java.sql.ResultSet;
import java.util.ArrayList;

public class FloorFilter implements IFilter{
    Floor floorToFilter;

    public FloorFilter(Floor filterBy)
    {
        this.floorToFilter = filterBy;
    }
    @Override
    public ArrayList<Object> apply(ResultSet rSet) {
        return null;
    }
}
