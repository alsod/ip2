
import com.oreilly.servlet.MailMessage;
import java.io.*;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * SendMail.java
 * @author Andre Karlsson
 *
 * Tar emot parametrar från formulär samt skapar och skickar epostmeddelande
 * utifrån dessa parametrar
 *
 * Uppgift 6.1.1 Internetprogrammering 2
 */
public class SendMail extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        /* Initialisera serveradress och lösenord */
        String mailhost = "smtp.bredband.net";  // or another mail host
        String password = "1234";
        boolean auth = false;

        /* Skapa meddelandevariabler */
        String from = null;
        String to = null;
        String cc = null;
        String bcc = null;
        String subject = null;
        String message = null;

        MailMessage msg = new MailMessage(mailhost);


        /* Ta emot parametrar från formulär */
        Enumeration params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String name = (String) params.nextElement();
            String value = request.getParameter(name);

            if (name.equals("from")) {
                from = value;
            }

            if (name.equals("to")) {
                to = value;
            }

            if (name.equals("cc")) {
                cc = value;
            }

            if (name.equals("bcc")) {
                bcc = value;
            }

            if (name.equals("subject")) {
                subject = value;
            }

            if (name.equals("message")) {
                message = value;
            }

            if (name.equals("password")) {
                if (password.equals(value)) {
                    auth = true;
                }
            }

        }

        /* Om rätt lösenord, bygg meddelande och skicka. annars skriv ut felmeddelande */
        if (auth) {

            msg.from(from);
            msg.to(to);
            msg.cc(cc);
            msg.bcc(bcc);

            msg.setSubject(subject);

            PrintStream mailOut = msg.getPrintStream();
            mailOut.println(message);
            mailOut.println("\nObservera! Detta meddelande är sänt från ett formulär på Internet och avsändaren kan vara felaktig!");

            msg.sendAndClose();

            /* Visa skickat meddelande */
            out.println("from = " + from);
            out.println("to = " + to);
            out.println("cc = " + cc);
            out.println("bcc = " + bcc);
            out.println("\nsubject = " + subject);
            out.println("message = " + message);

        } else {
            out.println("Fel lösenord. Mailet skickades inte.");
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
