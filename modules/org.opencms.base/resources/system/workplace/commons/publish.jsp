<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@page import="org.opencms.ade.publish.CmsPublishActionElement"%><%
CmsPublishActionElement publish = new CmsPublishActionElement(pageContext, request, response);
%><!DOCTYPE html>
<html>
  <head>
  	<%= HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(publish.exportAll())) %>
  	<title><%= publish.getTitle() %></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body class="-opencms" style="margin: 0px;">&nbsp;</body>
</html>