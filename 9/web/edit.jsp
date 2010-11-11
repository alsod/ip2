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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>&Auml;ndra bild</title>
        <link href="css/formpage.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <img src="./images/bgimage.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><h1>&Auml;ndra bildinfo</h1></div>
            <div id="content">
                <center><form method="get" action="./login" enctype="multipart/form-data" >

                    <table>

                        <tr>
                            <td><label for="namn">Namn: </label></td>
                            <td><input type="text" size="41" name="namn" id="anvandarnamn" /></td>
                        </tr>

                        <tr>
                            <td><label for="beskrivning">Beskrivning: </label></td>
                            <td><textarea cols="30" rows="5" name="beskrivning" id="beskrivning"></textarea></td>
                        </tr>


                    </table>
                    <br>
                    <input type="submit" name="andra" value="&Auml;ndra">
                    <input type="submit" name="radera" value="Radera">
                </form>
                </center>
            </div>
        </div>
    </body>
    <%}%>
</html>
