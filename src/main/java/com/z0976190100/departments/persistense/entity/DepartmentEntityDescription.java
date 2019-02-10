package com.z0976190100.departments.persistense.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentEntityDescription implements EntityDescription {

    private final List<String> FIELD_NAMES = new ArrayList<>(Arrays.asList("id", "title"));
    private final String TABLE_NAME = "department";
    private final String UNIQUE_FIELD = "title";

    @Override
    public String getUniqueField() {
        return this.UNIQUE_FIELD;
    }

    @Override
    public List getFieldNames() {
        return this.FIELD_NAMES;
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }
}
