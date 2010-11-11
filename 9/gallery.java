
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alsod
 */
public class gallery extends HttpServlet {

    HttpSession session;

    /* Skapa databasvariabler */
    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        /* Uppdatera session attribut */
        session = request.getSession();

        createDBConnection();

        String action = (String) session.getAttribute("action");

        /* Användaren har loggat in, skapa en array med bilder att visa */
        if (action.equals("showimages")) {
            for (GalleryImage image : getImages()) {
                out.println("name: " + image.getName());
            }
            out.println("hej");
            /* TODO: tabort showImages attribut */
            /* TODO: skicka vidare bildarray till gallerisida*/
        }

        /* Förfrågan kommer ifrån lägg till bild formuläret */
        if (action.equals("add")) {
            /* Sökväg till temporär lagringsplats */
            File dir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");

            MultipartRequest mreq = new MultipartRequest(request, dir.getAbsolutePath(), 15000000);
            String contentType = mreq.getContentType("file");
            if (contentType.equals("image/jpeg")) {
                addImage(request, out, mreq);
                gotoPage("./index.jsp", request, response);
            } else {
                session.setAttribute("imageError", "y");
                gotoPage("./add.jsp", request, response);
            }

        }

        closeDBConnection();
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

    private void createDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://atlas.dsv.su.se:3306/db_10645011";
            connection = DriverManager.getConnection(url, "usr_10645011", "645011");
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void closeDBConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addImage(HttpServletRequest request, PrintWriter out, MultipartRequest mreq) {

        try {

            /* Hämta namn och beskrivning */
            String[] name = mreq.getParameterValues("name");
            String[] description = mreq.getParameterValues("description");

            /* Skapa handle för den uppladdade filen */
            File tempfile = mreq.getFile("file");

            /* Skapa en ny fil att kopiera till*/
            File file = new File(getServletContext().getRealPath("/") + "/images/" + tempfile.getName());

            /* Kopiera tempfilen till webplatsen*/
            copyFile(tempfile, file);

            String insertStmt = "INSERT INTO ip2_images (id, name, description, filename, path, maker) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = connection.prepareStatement(insertStmt);
            prepStmt.setTimestamp(1, null);
            prepStmt.setString(2, name[0]);
            prepStmt.setString(3, description[0]);
            prepStmt.setString(4, file.getName());
            prepStmt.setString(5, file.getPath());
            prepStmt.setString(6, (String) session.getAttribute("user"));
            int rs = prepStmt.executeUpdate();

        } catch (IOException ex) {
            Logger.getLogger(gallery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(gallery.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void gotoPage(String page, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);

        if (dispatcher != null) {
            dispatcher.forward(req, res);

        }

    }

    private ArrayList<GalleryImage> getImages() {

        ArrayList<GalleryImage> images = new ArrayList<GalleryImage>();

        try {
            /* Hämta resultat från databas */
            resultSet = statement.executeQuery("SELECT * FROM ip2_images");
            while (resultSet.next()) {
                images.add(new GalleryImage(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("filename"), resultSet.getString("path"), resultSet.getString("maker")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }

        return images;
    }
}
