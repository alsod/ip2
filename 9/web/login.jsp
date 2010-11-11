<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>MQv8x - logga in</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1>Logga in</h1></div>
            <div id="content">
                <form method="post" action="./account">

                    <table>

                        <tr>
                            <td><label for="username">Anv&auml;ndarnamn: </label></td>
                            <td><input type="text" name="username" id="username" size="15"/></td>
                        </tr>

                        <tr>
                            <td><label for="password">L&ouml;senord: </label></td>
                            <td><input type="password" name="password" id="password" size="15"/></td>
                        </tr>

                        <tr>
                            <td colspan="2"><center><input type="submit" name="action" value="Logga in"></center></td>
                        </tr>
                    </table>                    
                </form>

                
                <p align="center">
                    <%
                                String loginError = (String) session.getAttribute("loginError");
                                String newUser = (String) session.getAttribute("newUser");
                                if (loginError != null && loginError.equals("y")) {
                    %>

                    <font color="red">Felaktig inloggning, f&ouml;rs&ouml;k igen.</font>

                    <% }
                                if (newUser != null && newUser.equals("y")) {
                    %>

                    <font color="red">Du &auml;r nu registrerad. <br> Testa att logga in.</font>

                    <% }
                                session.removeAttribute("loginError");
                                session.removeAttribute("newUser");
                    %>
                </p>
                <p>
                    Inget konto? registrera dig <a href="reg.jsp">här</a>
                </p>
                
            </div>
        </div>
    </body>
</html>