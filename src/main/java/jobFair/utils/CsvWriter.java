/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import jobFair.model.Spot;
import jobFair.model.Users;
import org.springframework.stereotype.Service;

/**
 *
 * @author justinas
 */

@Service
public class CsvWriter {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    
    public CsvWriter() {
    }
    
   public void getCompanies(List<Users> companies, HttpServletResponse response)
                    throws ServletException, IOException {
		
	response.setContentType("text/csv");
        String reportName =  "Inschrijvingen_Jobbeurs_"+sdf.format(new Date())+".csv";
        response.setHeader("Content-disposition", "attachment; " +
                "filename=\"" + reportName + "\"");

        ArrayList<String> rows = new ArrayList<String>();
        rows.add("Bedrijfsnaam;Naam contactpersoon;E-mailadres contactpersoon;Plaatsnummer;Aantal stoelen;Aantal tafels;Elektriciteit;Opmerkingen\n");

        for (Users user : companies) {
            List<Spot> spots = user.getSpots();
            if (spots!=null && !spots.isEmpty()) {
                Spot spot = spots.get(0);
                rows.add(user.getCompanyName()+";"+user.getContactName()+";"+user.getEmail()+";"
                        + spot.getSpotNo() + ";" + spot.getChairs() + ";" + spot.getTables() + ";"
                        + spot.isElectricity() + ";" + spot.getRemarks() + "\n");
            } else {
                rows.add(user.getCompanyName()+";"+user.getContactName()+";"+user.getEmail()+";;;;;\n");
            }
        }

        try {
            OutputStream outputStream = response.getOutputStream();
            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()){
                String outputString = (String) iter.next();
                outputStream.write(outputString.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
        	throw new ServletException(e.getMessage(), e);
        }
   }
}
