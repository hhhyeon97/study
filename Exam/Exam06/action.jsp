<%@ include file="db.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
    
<%
request.setCharacterEncoding("UTF-8");
String mode = request.getParameter("mode");

String id=request.getParameter("id");
String name=request.getParameter("name");
String credit=request.getParameter("credit");
String lecturer=request.getParameter("lecturer");
String week=request.getParameter("week");
String start_hour=request.getParameter("start_hour");
String end_hour=request.getParameter("end_hour");

// 이번 방법에서는 sql 3개 나옴 
try{
	
	String sql="";
	PreparedStatement pstmt = null;
	
	switch(mode){
	case "insert" :
		sql = "insert into course_tbl values(?,?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		
		// ?에 들어갈 내용 생성 : pstmt.setString(순서,항목);
		pstmt.setString(1,id);
		pstmt.setString(2,name);
		pstmt.setString(3,credit);
		pstmt.setString(4,lecturer);
		pstmt.setString(5,week);
		pstmt.setString(6,start_hour);
		pstmt.setString(7,end_hour);
		
		pstmt.executeUpdate(); // DB 업데이트 
		
		%>
		<jsp:forward page="insert.jsp" />
		<%
		
		break;
	case "modify" :
		sql = "update course_tbl set name=?, credit=?, lecturer=?, week=?, start_hour=?, end_hour=? where id=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, credit);
		pstmt.setString(3, lecturer);
		pstmt.setString(4, week);
		pstmt.setString(5, start_hour);
		pstmt.setString(6, end_hour);
		pstmt.setString(7, id);
		
		pstmt.executeUpdate();
%>
	<jsp:forward page="modify.jsp" />
<%		
		break;

	case "delete" :
		sql = "delete from course_tbl where id=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		
		pstmt.executeUpdate();
		
%>
	<jsp:forward page="list.jsp" />
<%			
		break;
	}
	
	
	
}
catch(Exception e){
	e.printStackTrace();
}



%>