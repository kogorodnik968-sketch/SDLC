package model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SalaryModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private double salary;
    private double declaredHours;
    private double workedHours;


    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        double old = this.salary;
        this.salary = salary;
        support.firePropertyChange("salary", old, salary);
    }

    public double getDeclaredHours() {
        return declaredHours;
    }

    public void setDeclaredHours(double declaredHours) {
        double old = this.declaredHours;
        this.declaredHours = declaredHours;
        support.firePropertyChange("declaredHours", old, declaredHours);
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        double old = this.workedHours;
        this.workedHours = workedHours;
        support.firePropertyChange("workedHours", old, workedHours);
    }



    public double getHourlyRate() {
        return salary / workedHours;
    }

    public double getBreathCost() {
        return salary / (Constants.BREATHS_PER_HOUR * workedHours);
    }

    public double getHeavyBreathCost() {
        return getBreathCost() * Constants.HEAVY_BREATH_K;
    }

    public double getClickCost() {
        return salary / (Constants.CLICKS_PER_HOUR * workedHours);
    }

    public double getLineCost() {
        return salary / (Constants.LINES_PER_HOUR * workedHours);
    }

    public double getBugCost() {
        return salary / (Constants.BUGS_PER_HOUR * workedHours);
    }

    public void calculate() {
        support.firePropertyChange("calculated", false, true);
    }


}
