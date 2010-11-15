<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <%
                String validUser = (String) session.getAttribute("validUser");
                if (validUser != null && !validUser.equals("y")) {
    %>
    <head>

    </head>
    <body>
        <p>Du är inte inloggad, du skickas nu till inloggningssidan.</p>
    </body>
    <%} else {%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>L&auml;gg till bild</title>
        <link href="css/formpage.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1>L&auml;gg till bild</h1></div>
            <div id="content">
                <center><form method="post" action="./gallery?action=add" enctype="multipart/form-data" >

                        <table>

                            <tr>
                                <td><label for="name">Namn: </label></td>
                                <td><input type="text" size="41" name="name" id="name" /></td>
                            </tr>

                            <tr>
                                <td><label for="beskrivning">Beskrivning: </label></td>
                                <td><textarea cols="30" rows="5" name="description" id="description"></textarea></td>
                            </tr>

                            <tr>
                                <td><label for="file">Bild: </label></td>
                                <td><input type="file" name="file" size="35"></td>
                            </tr>

                        </table>
                        <br>
                        <input type="submit" name="action" value="Skicka bild" />
                    </form>

                    <%
                                        String imageError = (String) session.getAttribute("imageError");
                                        if (imageError != null && imageError.equals("y")) {
                    %>

                    <font color="red">Bara jpeg-filer &auml;r till&aring;tna</font>

                    <%}%>
                </center>
            </div>
        </div>
    </body>
    <%}%>
</html>
