<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>MQv8x - Registrering</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/formpage.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1>Registrera konto</h1></div>
            <div id="content">
                <form method="get" action="./gallery">

                    <center>
                        <table>

                            <tr>
                                <td valign="top"><label for="username">Anv&auml;ndarnamn: </label></td>
                                <td><input type="text" name="username" id="username" /> <div class="smallText"> (a-z, 0-9 och minst 3 tecken)</div></td>
                            </tr>

                            <tr>
                                <td valign="top"><label for="password">L&ouml;senord: </label></td>
                                <td><input type="password" name="password" id="password"><div class="smallText"> (a-z, 0-9 och minst 3 tecken)</div></td>
                            </tr>

                            <tr>
                                <td><label for="password2">L&ouml;senord igen: </label></td>
                                <td><input type="password" name="password2" id="password2"></td>
                            </tr>

                            <tr>
                                <td colspan="2"><center><input type="submit" name="action" value="Registrera"></center></td>
                            </tr>

                        </table>
                    </center>
                </form>

                <p align="center">
                    <%
                                String usernameError = (String) session.getAttribute("usernameError");
                                String passwordError = (String) session.getAttribute("passwordError");
                                String illegalEntry = (String) session.getAttribute("illegalEntry");

                                if (usernameError != null && usernameError.equals("y")) {
                    %>
                    <font color="red">Anv&auml;ndarnamnet &auml;r upptaget, f&ouml;rs&ouml;k igen.</font>

                    <% }
                                if (passwordError != null && passwordError.equals("y")) {
                    %>
                    <font color="red">L&ouml;senorden st&auml;mmer inte, f&ouml;rs&ouml;k igen.</font>
                    <% }
                                if (illegalEntry != null && illegalEntry.equals("y")) {
                    %>
                    <font color="red">Otill&aring;tet anv&auml;ndarnamn eller l&ouml;senord.</font>
                    <% }
                                session.removeAttribute("usernameError");
                                session.removeAttribute("passwordError");
                                session.removeAttribute("illegalEntry");

                    %>
                </p>
            </div>
        </div>
    </body>
</html>