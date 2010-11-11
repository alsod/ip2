
import com.oreilly.servlet.MultipartResponse;
import com.oreilly.servlet.ServletUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andre Karlsson
 *
 * Internetprogrammering 2 uppgift 1.3.3 Serverstyrd omladdning
 */
public class push extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream(); //

        MultipartResponse mres = new MultipartResponse(response);
        
        for (int i = 10; i > 0; i--) {
            out.flush();
            if (i % 3 == 1) {
                mres.startResponse("text/plain");
                out.println("count = " + i); // Skriv ut nedräknare
                mres.endResponse();

            } else if (i % 3 == 0) {
                mres.startResponse("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet NewServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Count " + i + "</h1>");
                out.println("</body>");
                out.println("</html>");   
                mres.endResponse();

            } else {
                mres.startResponse("image/gif");
                try {
                    ServletUtils.returnFile(getServletContext().getRealPath("/") + "test.gif", out);
                } catch (FileNotFoundException e) {
                    throw new ServletException("Could not find file: " + e.getMessage());
                }
                mres.endResponse();
            }


            try {
                Thread.sleep(2000); // Sov en stund
            } catch (InterruptedException ex) {
                Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        mres.finish();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
