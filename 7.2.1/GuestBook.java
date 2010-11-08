
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mixer.Mixer;

/**
 * DynPrintEnv.java
 * @author Andre Karlsson
 *
 * Skriver ut environment variablerna definierade i systemet som html
 * Uppgift 3.2 Internetprogrammering 2
 */
public class GuestBook extends HttpServlet {

    private static String htmlTemplate = null;
    private Connection conn = null;

    @Override
    public void init() {
        if (htmlTemplate == null) {
            htmlTemplate = Mixer.getContent(new File(getServletContext().getRealPath("2.html")));
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        createDBConnection();

        boolean showGB = true;
        String name = "";
        String email = "";
        String homepage = "";
        String comment = "";



        Enumeration e = request.getParameterNames(); // ta emot parametrar som värdepar

        /* Skriv ut alla inkommna parametrar */
        while (e.hasMoreElements()) {
            String namePar = (String) e.nextElement();
            String valuePar = request.getParameter(namePar);

            /* Om viktiga variabler inte har något värde, avsluta loopen och visa felmeddelande */
            if ((namePar.equals("name") || namePar.equals("email") || namePar.equals("comment")) && valuePar.equals("")) {
                out.println("Du m&aring;ste fylla i namn, epost och meddelande. g&aring; <a href=\"1.html\">tillbaka</a>");
                showGB = false; // visa inte gästboken
                break;
            }

            if (namePar.equals("name")) {
                name = cleanEntry(valuePar);
            }

            if (namePar.equals("email")) {
                email = cleanEntry(valuePar);
            }

            if (namePar.equals("homepage")) {
                homepage = cleanEntry(valuePar);
            }

            if (namePar.equals("comment")) {
                comment = cleanEntry(valuePar);
            }

        }
        try {
            addEntry(name, email, homepage, comment);
        } catch (SQLException ex) {
            Logger.getLogger(GuestBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (showGB) {
            printGB(out);
        }

    }

    private String cleanEntry(String text){
        return text.replaceAll("\\<[^>]*>","");

    }

    /**
     * Lägger till ett gästboksinlägg i databasen
     * @param name
     * @param email
     * @param homepage
     * @param comment
     */
    private void addEntry(String name, String email, String homepage, String comment) throws SQLException {
        Calendar cal = Calendar.getInstance();
        String insertStmt = "INSERT INTO gb (id, name, email, homepage, comment) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = conn.prepareStatement(insertStmt);
        
        prepStmt.setTimestamp(1,new java.sql.Timestamp(cal.getTime().getTime()));
        prepStmt.setString(2, name);
        prepStmt.setString(3, email);
        prepStmt.setString(4, homepage);
        prepStmt.setString(5, comment);
        
        int rs = prepStmt.executeUpdate();

    }

    /**
     * Skapar en koppling till databasen
     */
    private void createDBConnection() {

        try {
            String userName = "alsod";
            String password = "alsod";
            String url = "jdbc:mysql://10.211.55.7:3306/ip2-7_2_1";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);

        } catch (Exception e) {
            System.err.println("cannot connect to database");
        }
    }

    /**
     * Skriver ut inlägg som lagrats i databasen
     * @param out
     */
    private void printGB(PrintWriter out) {

        Statement s;
        ResultSet rs;
        Mixer m = new Mixer(htmlTemplate);

        try {
            s = conn.createStatement();
            s.executeQuery("SELECT * FROM gb");
            rs = s.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                m.add("<!--===xxx===-->", "===name===", rs.getString("name"));
                m.add("<!--===xxx===-->", "===email===", rs.getString("email"));
                m.add("<!--===xxx===-->", "===homepage===", rs.getString("homepage"));
                m.add("<!--===xxx===-->", "===comment===", rs.getString("comment"));

            }
            rs.close();
            s.close();

        } catch (SQLException ex) {
            Logger.getLogger(GuestBook.class.getName()).log(Level.SEVERE, null, ex);
        }



        out.println(m.getMix());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
