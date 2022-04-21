package edu.wpi.cs3733.D22.teamF.filter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AndFilter implements IFilter{
    private IFilter filter;
    private IFilter filter2;

    public AndFilter(IFilter filter, IFilter filter2 ){
        this.filter = filter;
        this.filter2 = filter2;
    }

    @Override
    public ArrayList<Object> apply(ResultSet rSet) {
        return null;
    }
}
