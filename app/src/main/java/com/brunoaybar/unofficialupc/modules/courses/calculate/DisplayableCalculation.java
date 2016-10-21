package com.brunoaybar.unofficialupc.modules.courses.calculate;

/**
 * Displayable class for every calculate assessment row
 */

public class DisplayableCalculation {

    private String name;
    private double weightValue;
    private String weight;
    private String grade;
    private boolean editingEnabled = true;
    private boolean hasError;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeight(double weight) {
        this.weightValue = weight;
        this.weight = weight + "%";
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isEditingEnabled() {
        return editingEnabled;
    }

    public void setEditingEnabled(boolean editingEnabled) {
        this.editingEnabled = editingEnabled;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

}
