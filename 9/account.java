/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Hex;


/**
 *
 * @author Andre Karlsson
 */
public class account extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");


        createDBConnection(); // Skapa koppling till databasen

        /* Uppdatera session attribut */
        session = request.getSession();
        session.removeAttribute("loginError");
        session.removeAttribute("usernameError");
        session.removeAttribute("passwordError");
        session.removeAttribute("newUser");
        session.removeAttribute("illegalEntry");

        /* Förfrågan kommer ifrån loginformuläret */
        if (action.equals("Logga in")) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");


            if (authenticate(username, password)) {
                session.setAttribute("user", username);
                session.setAttribute("validUser", "y");
                session.setAttribute("loginError", "n");
                session.setAttribute("showImages", "y");
                gotoPage("/index.jsp", request, response);

            } else {
                session.setAttribute("validUser", "n");
                session.setAttribute("loginError", "y");
                gotoPage("./login.jsp", request, response);
            }

        }

        /* Förfrågan kommer från registreringsformuläret*/
        if (action.equals("Registrera")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");

            if (!username.matches("^[a-z0-9]{3,15}$") || !password.matches("^[a-z0-9]{3,15}$")) {
                session.setAttribute("illegalEntry", "y");
                gotoPage("./reg.jsp", request, response);

            } else if (isRegistered(username)) {
                session.setAttribute("usernameError", "y");
                gotoPage("./reg.jsp", request, response);

            } else if (!password.equals(password2)) {
                session.setAttribute("passwordError", "y");
                gotoPage("./reg.jsp", request, response);

            } else {
                session.setAttribute("newUser", "y");
                registerUser(username, password);
                gotoPage("./login.jsp", request, response);
            }

        }

        closeDBConnection(); // Stäng koppling till databas
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
        try {
            try {
                processRequest(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }


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
        try {
            try {
                processRequest(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";


    }// </editor-fold>

    private void gotoPage(String page, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);

        if (dispatcher != null) {
            dispatcher.forward(req, res);

        }

    }

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
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String md5(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(password.getBytes());
        return new String(Hex.encodeHex(bytes));

    }

    private boolean authenticate(String username, String password) {
        try {
            /* Hämta resultat från databas */
            resultSet = statement.executeQuery("SELECT * FROM ip2_users WHERE username = '" + username + "' AND password = '" + md5(password) + "'");
            if (resultSet.next()) {
                return true; // Inloggning misslyckades
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean isRegistered(String username) {
        try {
            /* Hämta resultat från databas */
            resultSet = statement.executeQuery("SELECT * FROM ip2_users WHERE username = '" + username + "'");
            if (resultSet.next()) {
                return true; // Användarnamn är upptaget
            }
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false; // Användarnamnet är tillgängligt
    }

        private void registerUser(String username, String password) throws NoSuchAlgorithmException {
        try {
            String insertStmt = "INSERT INTO ip2_users (id, username, password) VALUES (?, ?, ?)";
            PreparedStatement prepStmt = connection.prepareStatement(insertStmt);
            prepStmt.setTimestamp(1, null);
            prepStmt.setString(2, username);
            prepStmt.setString(3, md5(password));
            int rs = prepStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
