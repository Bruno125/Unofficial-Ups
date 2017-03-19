package com.brunoaybar.unofficialupc.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class ReserveFilter {
    private String key;
    private boolean custom;
    protected String name;
    protected List<ReserveOption> values = new ArrayList<>();

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReserveOption> getValues() {
        return values;
    }

    public void setValues(List<ReserveOption> values) {
        this.values = values;
    }

    public int getValuesCount(){
        return values != null ? values.size() : 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    static public class ReserveOption{
        private String code;
        private String value;

        public ReserveOption(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
