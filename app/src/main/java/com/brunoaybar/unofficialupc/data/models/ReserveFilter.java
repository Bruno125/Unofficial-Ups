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

    public ReserveFilter() {
    }

    public ReserveFilter(String key, String serviceKey, boolean custom, String name, List<ReserveFilterValue> values) {
        this.key = key;
        this.serviceKey = serviceKey;
        this.custom = custom;
        this.name = name;
        this.values = values;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof ReserveFilter){
            ReserveFilter that = (ReserveFilter) obj;
            return this.key.equals(that.key) &&
                    this.serviceKey.equals(that.serviceKey) &&
                    this.name.equals(that.name) &&
                    this.values.equals(that.values) &&
                    this.custom == that.custom;
        }else{
            return false;
        }
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

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (obj instanceof ReserveFilterValue){
                ReserveFilterValue that = (ReserveFilterValue) obj;
                return this.code.equals(that.code) &&
                        this.value.equals(that.value);
            }else{
                return false;
            }
        }
    }

}
