
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PrintEnv.java
 * @author Andre Karlsson
 *
 * Skriver ut environment variablerna definierade i systemet
 * Uppgift 1.1.2 Internetprogrammering 2
 */
public class PrintEnv extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        out.println("Environment variabler:\n");

        /* Skriv ut en variabel och dess värde per rad */
        Map<String, String> map = System.getenv();
        for(Map.Entry<String, String> entry : map.entrySet()){
            out.println(entry.getKey() + " = " + entry.getValue());
        }

        /* Mer info */
        out.println("\n\nMer info:\n");
        out.println("Method: " + request.getMethod());
        out.println("Request URI: " + request.getRequestURI());
        out.println("Protocol: " + request.getProtocol());
        out.println("PathInfo: " + request.getPathInfo());
        out.println("Remote Address: " + request.getRemoteAddr());

    }


}
