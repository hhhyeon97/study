<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<script type="text/javascript" src="check2.js"></script>

<jsp:include page="header.jsp" />
<section style="top:70px; left:0px; width:100%; height:100%;
position:fixed; background-color:gray; color:black;">
<h2 style="text-align:center;">좌석예약조회</h2>

<form method="post" action="action2.jsp" name="frm2" style="display:flex; align-items: center; justify-content: center;">
<table border="1">


<tr>
	<td>사원번호를 입력하시오.</td>
	<td><input type="text" name="empno"></td>
</tr>

<tr>
	<td colspan="2" style="text-align:center;"> 
		<input type="button" value="좌석예약조회" onclick="search()"> &nbsp;
		<input type="button" value="홈으로" onclick="window.location='index.jsp';">
	</td>
</tr>


</table>
</form>

</section>
<jsp:include page="footer.jsp" />
</body>
</html>