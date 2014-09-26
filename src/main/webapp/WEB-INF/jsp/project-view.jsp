<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>View Project ${project.name}</title>
</head>
<body>
<h1>Project ${project.name}</h1>
<table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
	<thead>
		<tr>
			<th width="25px">capture name</th>
			<th width="150px">capture path</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="capture" items="${captureList}">			
			<tr>
				<td>${capture.name}</td>
				<td>${capture.path}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<h1><i>${message}</i><br></h1>
<h1>Add capture</h1>
	<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/project/addCapture/${project.id}.html" >
		Capture to upload: <input type="file" name="file"><br /> 
		<input type="submit" value="Upload"> Upload capture
	</form>
<a href="${pageContext.request.contextPath}/project/list.html">Project list</a>
<br></br>
<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>