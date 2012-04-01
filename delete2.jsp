<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jp.bcat.*" %>
<%
	String id = request.getParameter("id");

	BookCatalog catalog = BookCatalog.getInstance();
	catalog.deleteBook(id);
%>
<HTML>
 <HEAD><TITLE>図書削除結果</TITLE></HEAD>
 <BODY>
  <P>削除しました。</P>
  <A href='./'>メニューに戻る</A>
 </BODY>
</HTML>
