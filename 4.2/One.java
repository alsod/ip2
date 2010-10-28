
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
 * Skapar htmlsida och två kakor
 * Uppgift 4.2 Internetprogrammering 2
 */
public class One extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        /* kakornas värden */
        String nameValue = "foobar";
        String timeValue = getTime();

        /* skapa kakor */
        Cookie name = new Cookie("name", nameValue);
        Cookie time = new Cookie("time", timeValue);

        /* Sätt livslängd*/
        int age = 10800; // Så länge som kakan ska leva 10800 = 3 timmar
        name.setMaxAge(age);
        name.setMaxAge(age);

        response.addCookie(name);
        response.addCookie(time);

        out.println("<html>");
        out.println("<head>");
        out.println("<title>1</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Nu har två kakor förhoppningsvis skapats. gå vidare <a href=\"./2\">här</a>");
        out.println("</body>");
        out.println("</html>");

    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
