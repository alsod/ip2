import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Two.java
 * @author Andre Karlsson
 *
 * Tar emot parametrar och skriver ut dessa.
 * Uppgift 4.1.2 Internetprogrammering 2
 */


public class Two extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        Enumeration e = request.getParameterNames(); // ta emot parametrar som värdepar
        out.println("Skriver ut parametrar:");

        /* Skriv ut alla inkommna parametrar */
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String value = request.getParameter(name);
            out.println(name + " = " + value);
        }
    }
}
