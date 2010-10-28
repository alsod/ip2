
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mixer.Mixer;

/**
 * one.java
 * @author Andre Karlsson
 *
 * Skapar ny sida med länkar, där namn är taget från inkommande parameter
 * Uppgift 4.1.1 Internetprogrammering 2
 */
public class One extends HttpServlet {

    private static String htmlTemplate = null;

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

        Enumeration e = request.getParameterNames(); // ta emot parametrar som värdepar

        String name = (String) e.nextElement();
        String value = request.getParameter(name);

        Mixer m = new Mixer(htmlTemplate);

        m.add("===name===", name);
        m.add("===value===", value);


        out.println(m.getMix());

    }
}
