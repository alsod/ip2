
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * One.java
 * @author Andre Karlsson
 *
 * Skapar htmlsida som skriver ut kakor
 * Uppgift 4.2 Internetprogrammering 2
 */
public class Two extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Cookie[] cookies = request.getCookies(); // hämta kakor

        out.println("<html>");
        out.println("<head>");
        out.println("<title>2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Här nedanför borde kakorna skrivas ut: <br><br>");

        /*Skriv ut kakor*/
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            String name = c.getName();
            String value = c.getValue();
            
            if (name.equals("name") || name.equals("time")) {
                out.println(name + " = " + value + "<br>");
            }
        }


        out.println("</body>");
        out.println("</html>");

    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
