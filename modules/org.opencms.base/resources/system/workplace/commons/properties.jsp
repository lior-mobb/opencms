<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@page import="org.opencms.ade.properties.*" %><%@ 
	taglib prefix="cms" uri="http://www.opencms.org/taglib/cms"%><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html>
<html>
	<head>
		<%=HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape((new CmsPropertiesActionElement(pageContext, request, response)).exportAll())) %>
	</head>
	<body class="-opencms">
	</body>
</html>