
/**
 * StatHits.java
 * @author Andre Karlsson
 *
 * Uppgift 3.1 "Statisk mängd data" i kursen Internetprogrammering 2
 *
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import mixer.Mixer;

public class StatHits extends HttpServlet {

    private static String htmlTemplate = null;

    @Override
    public void init() {
        if (htmlTemplate == null) {
            htmlTemplate = Mixer.getContent(new File(getServletContext().getRealPath("3_1.html")));
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();



        Mixer m = new Mixer(htmlTemplate);

        m.add("===hits===", getHits());

        out.println(m.getMix());

    }

    private synchronized String getHits() throws IOException {
        String hitsPath = StatHits.class.getResource("hits").getPath(); // hitta sökväg till fil
        BufferedReader hitsReader = new BufferedReader(new FileReader(hitsPath));
        String hits = hitsReader.readLine();

        int hitsInteger = Integer.parseInt(hits); // hämta heltal

        PrintWriter hitsWriter = new PrintWriter(hitsPath);
        hitsWriter.println(++hitsInteger); //skriv nytt heltal

        hitsReader.close();
        hitsWriter.close();


        return hits;
    }
}