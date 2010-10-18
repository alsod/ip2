import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * GetTest.java
 * @author Andre Karlsson
 *
 * Tar emot parametrar och skriver ut dessa.
 * Uppgift 2.1 Internetprogrammering 2
 */


public class GetTest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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
