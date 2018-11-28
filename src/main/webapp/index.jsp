<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  Index page
  <form action="user" name="add_user" method="post">
    <input type="text" name="userName" value="" placeholder="name">
    <input type="text" name="passportKey" value="" placeholder="passport key">
    <input type="submit" value="add user">
  </form>

  <form action="user" name="remove_user" method="get">
    <input type="text" name="userName" value="" placeholder="name">
    <input type="submit" value="remove user">
  </form>

  <form action="quest" name="add_question" method="post">
    <input type="text" name="question" value="" placeholder="question">
    <input type="submit" value="add question">
  </form>

  <form action="quest" name="remove_question" method="get">
    <input type="text" name="question" value="" placeholder="question">
    <input type="submit" value="remove question">
  </form>

  <form action="relat" name="ask_question" method="post">
    <input type="hidden" name="section" value="ASK_QUESTION">
    <input type="text" name="userName" value="" placeholder="user name">
    <input type="text" name="question" value="" placeholder="question">
    <input type="submit" value="ask question">
  </form>

  <form action="relat" name="answer_question" method="post">
    <input type="hidden" name="section" value="ANSWER_QUESTION">
    <input type="text" name="userName" value="" placeholder="user name">
    <input type="text" name="question" value="" placeholder="question">
    <input type="text" name="answer" value="" placeholder="answer">
    <input type="submit" value="answer question">
  </form>

  <form action="relat" name="get_full_data" method="get">
    <input type="submit" value="get full data">
  </form>

  ${data}

  <c:forEach var="token" items="${multipleData}">
    ${token}<br>
  </c:forEach>

  </body>
</html>
