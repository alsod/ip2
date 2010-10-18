import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Refresh.java
 * @author Andre Karlsson
 * Uppgift 1.3.1 i kursen Internetprogrammering 2
 * Client pull dvs refresh initierad av klienten
 */
public class Refresh extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    res.setHeader("Refresh", "10");
    out.println(new Date().toString());
  }
}
