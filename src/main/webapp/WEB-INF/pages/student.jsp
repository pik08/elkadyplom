<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 3/20/2015
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Status printing</title>
</head>
<body>
    <h2>
        Status of printing your document
    </h2>
    <table>
        <tr>
            <td>Printer: </td>
            <td>${properties.printer}</td>
        </tr>
        <tr>
            <td>Comment: </td>
            <td>${properties.comment}</td>
        </tr>
        <tr>
            <td>Is black: </td>
            <td>${properties.black}</td>
        </tr>
        <tr>
            <td>Is double page: </td>
            <td>${properties.doublePage}</td>
        </tr>
        <tr>
            <td>File name: </td>
            <td>${properties.file.name}</td>
        </tr>
    </table>
</body>
</html>
