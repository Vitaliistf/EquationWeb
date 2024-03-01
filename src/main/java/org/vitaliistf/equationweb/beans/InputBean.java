package org.vitaliistf.equationweb.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean representing input values for an equation calculation.
 */
@Getter
@Setter
@Named("inputBean")
@RequestScoped
public class InputBean {
    private double start;
    private double end;
    private double step;

    /**
     * Method called when the input form is submitted.
     * This method returns the navigation outcome to redirect to the result page.
     *
     * @return the navigation outcome to redirect to the result page
     */
    public String submit() {
        return "result";
    }
}
