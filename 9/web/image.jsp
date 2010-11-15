<%@page import="gallery.GalleryImage"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <%
    ArrayList<GalleryImage> images = (ArrayList<GalleryImage>) session.getAttribute("images");
    int id = Integer.parseInt((String) request.getParameter("id"));
    String validUser = (String) session.getAttribute("validUser");
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
        <title><%out.println(images.get(id).getFilename());%></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="css/image.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage3.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1><%out.println(images.get(id).getFilename());%></h1></div>
            <div id="content">
                <img alt="bild" src="./images/<%out.println(images.get(id).getFilename());%>" width = "1024px"/>

                <ul>
                    <li>Namn: <%out.println(images.get(id).getName());%></li>
                    <li>Beskrivning: <%out.println(images.get(id).getDescription());%></li>
                </ul>
            </div>
        </div>
    </body>
    <%}%>
</html>