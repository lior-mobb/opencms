<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@page import="org.opencms.ade.postupload.*" %><%
CmsPostUploadDialogActionElement dialog = new CmsPostUploadDialogActionElement(pageContext, request, response);
dialog.setUsePropertyConfiguration(true);
dialog.setAddBasicProperties(true);
%><!DOCTYPE html>
<html>
  <head>
  	<%= HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(dialog.exportAll())) %>
  	<title>Properties</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body class="-opencms" style="margin: 0px;">&nbsp;</body>
</html>
