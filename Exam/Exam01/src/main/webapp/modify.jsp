<%@page import="DBPKG.Util"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="check.js"></script>
<meta charset="UTF-8">
<title>modify</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<section style="position:fixed; top:60px; width:100%; height:100%;
background-color:lightgray;">
<h3 style="text-align: center"> <b>홈쇼핑 회원 정보 수정</b></h3><br>
<!-- form : 표 설정 -->
<form method="post" action="action.jsp" name="frm" style="display:flex; align-items: center;
justify-content: center; text-align:center">
<input type ="hidden" name="mode" value="modify">

<table border="1"> <!--  선 두께-->

<!-- db 연결 -->
<%

request.setCharacterEncoding("UTF-8");

Connection conn = null;
Statement stmt = null;
String mod_custno = request.getParameter("mod_custno"); // 외부 파일 결과 받아올 때
String custname="";
String phone="";
String address="";
Date joindate;
String grade="";
String city="";
String joindateStr="";
try{
	conn=Util.getConnection();
	stmt=conn.createStatement();
	String sql = "select * from member_tbl_02 where custno = " + mod_custno;
												//그 때 그 때 데이터를 받아와야 하므로 sql바깥에 +로 연결
	ResultSet rs=stmt.executeQuery(sql);
    rs.next(); //1개의 결과물 출력
    
	mod_custno=rs.getString("custno");
	custname=rs.getString("custname");
	phone=rs.getString("phone");
	address=rs.getString("address");
	// getString 으로 출력하면 시간까지 나옴!
	joindate = rs.getDate("joindate");
	grade=rs.getString("grade");
	city=rs.getString("city"); 
	
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	joindateStr = transFormat.format(joindate);
}
catch(Exception e){
	e.printStackTrace();
}
%>

<%
System.out.println("mod_custno: " + mod_custno);
%>
<tr>
	<td>회원번호</td>
	<td><input type="text" name="custno" value="<%=mod_custno %>" readonly></td>
</tr>
<tr>
	<td>회원성명</td>
	<td><input type="text" name="custname" value="<%=custname%>"></td>
</tr>
<tr>
	<td>회원전화</td>
	<td><input type="text" name="phone" value="<%=phone%>"></td>
</tr>
<tr>
	<td>회원주소</td>
	<td><input type="text" name="address" value="<%=address%>"></td>
</tr>
<tr>
	<td>가입일자</td>
	<td><input type="text" name="joindate" value="<%=joindateStr%>"></td>
</tr>
<tr>
	<td>고객등급(A:VIP,B:일반,C:직원)</td>
	<td><input type="text" name="grade" value="<%=grade%>"></td>
</tr>
<tr>
	<td>도시코드</td>
	<td><input type="text" name="city" value="<%=city%>"></td>
</tr>
<tr>		<!-- 합칠 열 개수 -->
	<td colspan="2">
	<input type="submit" value="수정" onclick="return modify();">
	<input type="button" value="조회" onclick="return search();">
	</td>
</tr>
</table>
</form>

</section>
<jsp:include page="footer.jsp"/>
</body>
</html>