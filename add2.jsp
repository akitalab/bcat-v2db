<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jp.bcat.*" %>
<jsp:useBean id="book" class="jp.bcat.Book" scope="session" />
<%
  BookCatalog catalog = BookCatalog.getInstance();
  catalog.addBook(book);
%>
<HTML>
 <HEAD><TITLE>図書登録結果</TITLE></HEAD>
 <BODY>
  <P>登録しました。</P>
  <A href='./'>メニューに戻る</A>
 </BODY>
</HTML>