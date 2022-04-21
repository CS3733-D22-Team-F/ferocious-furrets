package edu.wpi.cs3733.D22.teamF.filter;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IFilter {

    public ArrayList<Object> apply(ResultSet rSet);
}
