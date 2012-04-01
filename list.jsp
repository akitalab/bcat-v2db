<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jp.bcat.*" %>
<%@ page import="java.io.PrintWriter" %>
<%
	BookCatalog catalog = BookCatalog.getInstance();
	Book result[] = catalog.getBooks();
%>
<HTML>
 <HEAD><TITLE>図書一覧</TITLE></HEAD>
 <BODY>
  <P><%= result.length %>項目</P>
<%
	BookWriter w = new BookWriter(new PrintWriter(out), " ");
    for (int i = 0; i < result.length; i++) {
%>
<PRE>[<%= (i+1) %>]:
 図書ID: <%= result[i].getBookId() %>
<%
      w.write(result[i]);
%>
</PRE>
<%
    }
%>
  <A href='./'>メニューに戻る</A>
 </BODY>
</HTML>