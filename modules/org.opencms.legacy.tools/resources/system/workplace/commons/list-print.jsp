<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@ page import="org.opencms.workplace.list.*"%><%	

	CmsListPrintDialog wp = new CmsListPrintDialog(pageContext, request, response);        

%><%= HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(wp.generateHtml())) %>