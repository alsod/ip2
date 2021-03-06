
/**
 * Hits.java
 * @author Andre Karlsson
 *
 * Uppgift 1.1.1 "Säker filhantering" i kursen Internetprogrammering 2
 * 
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Hits extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        response.setContentType("text/plain");
        String hits = getHits();
        PrintWriter out = response.getWriter();
        out.print("Besökare: ");
        out.println(hits); // skriv ut träffar

    }

    private synchronized String getHits() throws IOException {
        String hitsPath = Hits.class.getResource("hits").getPath(); // hitta sökväg till fil
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
