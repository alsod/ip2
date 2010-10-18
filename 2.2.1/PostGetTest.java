
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * PostGetTest.java
 * @author Andre Karlsson
 *
 * Tar emot parametrar från olika formulärelement och skriver ut dessa.
 * Uppgift 2.2.1 Internetprogrammering 2
 */
public class PostGetTest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        Enumeration e = request.getParameterNames(); // ta emot parametrar som värdepar

        out.println("Skriver ut parametrar:");

        /* Skriv ut alla inkommna parametrar */
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String[] values = request.getParameterValues(name);

            if (values.length == 1) { // om bara ett värde
                String value = values[0];
                if (value.length() == 0) { // inget värde angivet
                    out.println("Inget värde angivet");
                } else { // värde angivet
                    out.println(name + " = " + value);
                }
            } else { // flera värden accosierade med samma parameter
                for (int i = 0; i < values.length; i++) {
                    out.println(name + " = " + values[i]);
                }

            }
        }


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
