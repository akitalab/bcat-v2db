<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jp.bcat.*" %>
<%@ page import="java.io.PrintWriter" %>
<HTML>
 <HEAD><TITLE>図書削除確認</TITLE></HEAD>
<%
	String id = request.getParameter("id");
	BookCatalog catalog = BookCatalog.getInstance();
	Book book = catalog.getBook(id);
	if (book != null) {
%>
 <BODY>
  <P>この図書を削除しますか？</P>
<PRE>
<%
	  new BookWriter(new PrintWriter(out), "- ").write(book);
%>
</PRE>
  <FORM method='GET' action='delete2.jsp'>
   <INPUT type='hidden' name='id' value='<%= id %>'>
   <INPUT type='submit' value='削除'>
  </FORM>
  <A href='./'>メニューに戻る</A>
 </BODY>
<%
	} else {
%>
 <BODY>
  <P>該当する図書はありません。</P>
  <A href='./'>メニューに戻る</A>
 </BODY>
<%
	}
%>
</HTML>