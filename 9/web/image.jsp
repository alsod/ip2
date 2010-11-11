<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <% String validUser = (String) session.getAttribute("validUser");
                if (validUser != null && !validUser.equals("y")) {
    %>
    <head>
        <meta http-equiv="REFRESH" content="2;url=./login.jsp" />
    </head>
    <body>
        <p>Du är inte inloggad, du skickas nu till inloggningssidan.</p>
    </body>
    <%} else {%>
    <head>
        <title>Bildnamn</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="css/image.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage3.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1>Bildnamn</h1></div>
            <div id="content">
                <img alt="bild" src="images/bgimage.jpg" width = "1024px"/>

                <ul>
                    <li>Namn</li>
                    <li>Beskrivning</li>
                </ul>
            </div>
        </div>
    </body>
    <%}%>
</html>