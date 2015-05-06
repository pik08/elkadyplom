<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
	<head>
		<!-- botstrap -->
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<!-- koniec bootstrap -->
        <%-- jquery--%>
        <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
        <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <%-- koniec jquery --%>

        <link href="<c:url value="/resources/css/maket.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/js/maket.js" />"></script>

	  <title>My first styled page</title>
	</head>
	<body>
        <jsp:include page="tags/navPanel.jsp" />
        <div type="hidden" style="display: none;"> <!-- work space js -->
            <jsp:include page="tags/record.jsp"/>
        </div>
		<div id="studentsForm">
        </div>
	</body>
</html>