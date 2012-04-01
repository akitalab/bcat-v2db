<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jp.bcat.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<jsp:useBean id="book" class="jp.bcat.Book" scope="session" />
<HTML>
 <HEAD><TITLE>図書登録確認</TITLE></HEAD>
<%
    String errorMessage = "";
	String title = request.getParameter("title");
	book.setTitle(title);
	if (title == null || title.length() == 0)
		errorMessage += "<P>タイトルを入力してください。</P>";
	String author = request.getParameter("author");
	book.setAuthor(author);
	if (author == null || author.length() == 0)
		errorMessage += "<P>著者を入力してください。</P>";
	String translator = request.getParameter("translator");
	book.setTranslator(translator);
	String publisher = request.getParameter("publisher");
	book.setPublisher(publisher);
	if (publisher == null || publisher.length() == 0)
		errorMessage += "<P>出版社を入力してください。</P>";
	String publicationDate = request.getParameter("publicationDate");
	book.setPublicationDate(publicationDate);
	if (publicationDate == null || publicationDate.length() == 0)
		errorMessage += "<P>出版年月日を入力してください。</P>";
	String code = request.getParameter("code");
	book.setCode(code);
	String memo = request.getParameter("memo");
	book.setMemo(memo);
	String keyword = request.getParameter("keyword");
	book.setKeyword(keyword);
	String dataCreator = request.getParameter("dataCreator");
	book.setDataCreator(dataCreator);
	if (dataCreator == null || dataCreator.length() == 0)
		errorMessage += "<P>登録者名を入力してください。</P>";
	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(new Date());
	book.setDataCreatedDate(now);
	if (errorMessage.length() == 0) {
%>
 <BODY>
  この内容で登録しますか？
<PRE>
<%
	  new BookWriter(new PrintWriter(out), "+ ").write(book);
%>
</PRE>
  <FORM method='POST' action='add2.jsp'>
   <INPUT type='submit' value='登録'>
  </FORM>
  <A href='./'>メニューに戻る</A>
 </BODY>
<%
	} else {
%>
 <BODY>
  <%= errorMessage %>
  <A href='./'>メニューに戻る</A>
 </BODY>
<%
	}
%>
</HTML>