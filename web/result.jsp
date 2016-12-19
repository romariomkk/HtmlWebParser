<%@ page import="parser.custom_entity.TagValuePair" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: romariomkk
  Date: 19.12.2016
  Time: 4:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results of Parsing</title>
</head>
<body>
<% List<List<TagValuePair>> list = (List<List<TagValuePair>>) request.getAttribute("listOfLists");
    for (int collectionIndex = 0; collectionIndex < list.size(); collectionIndex++) {
        List<TagValuePair> currentCollection = list.get(collectionIndex);
%>
<table align="center" cellpadding="10" border="1">
    <thead>
    <th>TAG</th>
    <th>VALUE</th>
    </thead>
    <tbody>
    <% for (int docIndex = 0; docIndex < currentCollection.size(); docIndex++) {
        TagValuePair pair = currentCollection.get(docIndex);
    %>
    <tr>
        <td><%pair.getTag();%></td>
        <td><%pair.getValue();%></td>
    </tr>
    <%
        }
    %>

    </tbody>
</table>
<%
    }
%>
</body>
</html>
