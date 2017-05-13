/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author justinas
 */
public class StringToCalendar {
    
    private String date;

    public StringToCalendar(String date) {
        this.date = date;
    }

    public StringToCalendar() {
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public Calendar getDate(List<String> errors) {

        if (date == null || date.trim().isEmpty()) {
            errors.add("Geen datum meegegeven.");
            return null;
        }

        if (!date.contains("-")) {
            errors.add("De gegeven datum heeft niet het juiste formaat: \"dag-maand-jaar\"");
            return null;
        }

        String[] elements = date.split("-");
        if (elements.length != 3) {
            errors.add("De gegeven datum heeft niet het juiste formaat: \"dag-maand-jaar\"");
            return null;
        }

        try {
            Calendar deadline = Calendar.getInstance();
            int year = Integer.parseInt(elements[2]);
            int month = Integer.parseInt(elements[1]) - 1;
            int day = Integer.parseInt(elements[0]);
            deadline.set(year, month, day);
            return deadline;
        } catch (NumberFormatException e) {
            errors.add("De gegeven datum bestond niet uit enkel cijfers gescheiden door "
                                + "een '-'. Gelieve een juiste datum mee te geven.");
            return null;
        }
    }
    
    public Date getDateFormat(List<String> errors) {
        Calendar calendar = this.getDate(errors);
        if(errors.size() > 0) {
            return null;
        }
        return (Date) calendar.getTime();
    }
}
