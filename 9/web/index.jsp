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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>MQv8x</title>
        <link href="css/main.css" rel="stylesheet" type="text/css" />

        <!--[if IE 6]>
	<style type="text/css">
		html { overflow-y: hidden; }
		body { overflow-y: auto; }
		#bg { position:absolute; z-index:-1; }
		#content { position:static; }
	</style>
	<![endif]-->

    </head>

    <body>
        <img src="./images/bgimage2.jpg" alt="background image" id="bg" />
        <div id="container">
            <div id="top"><img alt="logo" src="images/logo.png" height="130px" /></div>

            <div id="content">

                <div id="bigLinks">
                    <a href="./add.jsp" target="_self">L&auml;gg till bild</a>
                    <a href="./edit.jsp">&Auml;ndra bild</a>
                </div>

                <div id="images">
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                    <a href="image.jsp"><img alt="image" src="images/bgimage.jpg" height="150" width="200" /></a>
                </div>

            </div>
            <div class="clear"></div>
        </div>

    </body>
    <%}%>
</html>