<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>User Management/Add</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/add" method="post">
		<p>First name:</p>
		<input type="text" name="firstName" value=""><br>
		<p>Last name:</p>
		<input type="text" name="lastName" value=""><br>
		<p>Date of birthday:</p>
		<input type="text" name="dateOfBirth" value=""><br> <input
			type="submit" name="okButton" value="Ok"> <input
			type="submit" name="cancelButton" value="Cancel">
	</form>
	<c:if test="${requestScope.error != null}">
		<script>
			alert('${requestScope.error}');
		</script>
	</c:if>
</body>
</html>