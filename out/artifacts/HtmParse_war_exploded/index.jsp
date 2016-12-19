<%--
  User: romariomkk
  Date: 14.12.2016
  Time: 22:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HTML Parser</title>
    <style>
        input[type='text'] {
            font-size: 24px;
        }

        form {
            text-align: center;
        }
    </style>
</head>
<body>
<h3 align="middle">By Roman Leizerovych</h3><br/>
<form name="url" method="post" action="mainServlet">
    <p>URL 1: <input type="text" name="urlN1" size="50" value="https://www.work.ua/"/></p>
    <p>URL 2: <input type="text" name="urlN2" size="50" value="https://github.com/"/></p>
    <p>URL 3: <input type="text" name="urlN3" size="50" value="https://blog.google/"/></p>
    <input type="submit" value="PROCESS"/>
</form>
</body>
</html>
