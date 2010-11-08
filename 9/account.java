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

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

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
                gotoPage("./index.jsp", request, response);

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
                gotoPage("./login.jsp", request, response);
            }

        }

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
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
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
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
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

    private String md5(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(password.getBytes());
        return new String(Hex.encodeHex(bytes));

    }

    private boolean authenticate(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {

        /* Skapa databaskoppling */
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://atlas.dsv.su.se:3306/db_10645011";
        connection = DriverManager.getConnection(url, "usr_10645011", "645011");
        statement = connection.createStatement();

        /* Hämta resultat från databas */
        resultSet = statement.executeQuery("SELECT * FROM ip2_users WHERE username = '" + username + "' AND password = '" + md5(password) + "'");

        if (resultSet.next()) {
            return true; // Inloggning misslyckades
        }

        resultSet.close();
        statement.close();
        connection.close();

        return false;
    }

    private boolean isRegistered(String username) throws ClassNotFoundException, SQLException {

        /* Skapa databaskoppling */
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://atlas.dsv.su.se:3306/db_10645011";
        connection = DriverManager.getConnection(url, "usr_10645011", "645011");
        statement = connection.createStatement();

        /* Hämta resultat från databas */
        resultSet = statement.executeQuery("SELECT * FROM ip2_users WHERE username = '" + username + "'");

        if (resultSet.next()) {
            return true; // Användarnamn är upptaget
        }

        resultSet.close();
        statement.close();
        connection.close();

        return false; // Användarnamnet är tillgängligt
    }
}
