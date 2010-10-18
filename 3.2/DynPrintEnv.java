
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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
public class DynPrintEnv extends HttpServlet {

    private static String htmlTemplate = null;

    @Override
    public void init() {
        if (htmlTemplate == null) {
            htmlTemplate = Mixer.getContent(new File(getServletContext().getRealPath("3_2.html")));
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Mixer m = new Mixer(htmlTemplate);

        /* Skriv ut en variabel och dess värde per rad */
        Map<String, String> map = System.getenv();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            m.add("<!--===xxx===-->", "===name===", entry.getKey());
            m.add("<!--===xxx===-->", "===value===", entry.getValue());
        }

        out.println(m.getMix());
        
    }
}
