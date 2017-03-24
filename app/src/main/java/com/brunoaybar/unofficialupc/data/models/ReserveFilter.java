package com.brunoaybar.unofficialupc.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class ReserveFilter {
    private String key;
    @SerializedName("service_key")
    private String serviceKey;
    private boolean custom;
    private int selected;
    protected String name;
    protected List<ReserveFilterValue> values = new ArrayList<>();

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

    public List<ReserveFilterValue> getValues() {
        return values;
    }

    public ReserveFilterValue getSelectedFilterValue(){
        return values.get(selected);
    }

    public void setValues(List<ReserveFilterValue> values) {
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

    public int getSelected() {
        return selected;
    }

    protected void setSelected(int selected) {
        this.selected = selected;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    static public class ReserveFilterValue {
        private String code;
        private String value;

        public ReserveFilterValue(String code, String value) {
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
