
import com.oreilly.servlet.MultipartRequest;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * FileTest.java
 * @author Andre Karlsson
 *
 * Tar emot fil och visar den på det sätt som angavs i uppgiftsbeskrivningen.
 * Uppgift 2.2.2 Internetprogrammering 2
 */
public class FileTest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        /* Sökväg till temporär lagringsplats */
        File dir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");

        /* Spara fil på temporär lagringsplats*/
        MultipartRequest mreq = new MultipartRequest(request, dir.getAbsolutePath(), 500000);

        /* Hämta contenttype */
        String contentType = mreq.getContentType("file");

        /* Skapa handle för den uppladdade filen */
        File tempfile = mreq.getFile("file");

        /* Skapa en ny fil att kopiera till*/
        File file = new File(getServletContext().getRealPath("/") + "/tmp/" + tempfile.getName());

        /* Kopiera tempfilen till webplatsen*/
        try {
            copyFile(tempfile, file);
        } catch (Exception ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (contentType.equals("text/plain")) { // om textfil

            BufferedReader text = new BufferedReader(new FileReader(file));
            String line;

            while ((line = text.readLine()) != null) {
                out.println(line);
            }

        } else if (contentType.equals("image/jpeg") || contentType.equals("image/png")) { // om giltig bildfil

            response.setContentType("text/html");
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                    + "Transitional//EN\">\n"
                    + "<HTML>\n"
                    + "<HEAD><TITLE>"
                    + file.getName()
                    + "</TITLE></HEAD>\n"
                    + "<BODY>\n"
                    + "<IMG SRC=\"tmp/"
                    + file.getName()
                    + "\"\n"
                    + "</BODY></HTML>");
        
        } else { // alla andra filtyper

            out.println(file.getName());
            out.println(contentType);
            out.println(file.length() + " bytes");
        
        }
        
   
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    /**
     * Kopiera en fil från en plats till en annan
     * @param in Fil som ska kopieras
     * @param out Fil till vilken källfilen ska kopieras
     * @throws Exception
     */
    public static void copyFile(File in, File out) throws Exception {
        FileInputStream fin = new FileInputStream(in);
        FileOutputStream fout = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fin.read(buf)) != -1) {
                fout.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
