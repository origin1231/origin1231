<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>listPage</title>
</head>
<body>
<%@include file="../include/header.jsp"%>
	
<!-- Main content -->	
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LISTPAGE</h3>
				</div>
				<div class="box-body">
					<select name="searchType">
						<!-- 검색조건 X -->
						<option value="n"
							<c:out value="${cri.searchType == null ? 'selected':''}"/>>---
						</option>
						<!-- 제목 -->
						<option value="t"
							<c:out value="${cri.searchType eq 't' ? 'selected':''}"/>>Title
						</option>
						<!-- 내용 -->
						<option value="c"
							<c:out value="${cri.searchType eq 'c' ? 'selected':''}"/>>Content
						</option>
						<!-- 작성자 -->
						<option value="w"
							<c:out value="${cri.searchType eq 'w' ? 'selected':''}"/>>Writer
						</option>
						<!-- 제목or내용 -->
						<option value="tc"
							<c:out value="${cri.searchType eq 'tc' ? 'selected':''}"/>>Title Or Content
						</option>
						<!-- 내용or작성자 -->
						<option value="cw"
							<c:out value="${cri.searchType eq 'cw' ? 'selected':''}"/>>Content Or Writer
						</option>
						<!-- 제목or내용or작성자 -->
						<option value="tcw"
							<c:out value="${cri.searchType eq 'tcw' ? 'selected':''}"/>>
							Title Or Content Or Wirter
						</option>
					</select>
					<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}">
					<button id='searchBtn'>Search</button>
					<button id='newBtn'>New Board</button>
					<span>총 <c:out value="${pageMaker.totalCount}"/>건</span>
				</div>
				<div class="box">
					<div class="box-header with border">
						<h3 class="box-title">글목록</h3>
					</div>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th style="width: 10px;">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>
					
					<c:forEach items="${list}" var="boardVO">
						<tr>
							<td>${boardVO.bno}</td>
							<td><a href='/sboard/read${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno}'>
								${boardVO.title}
								</a>
							</td>
							<td>${boardVO.writer}</td>
							<td><fmt:formatDate pattern="yyyy-MM-DD HH:mm" value="${boardVO.regdate}"/></td>
							<td><span class="badge bg-red">${boardVO.viewcnt}</span></td>
						</tr>
					</c:forEach>
					</table>
				</div>	<!-- box-body -->
				
				<!-- 하단 패이지번호 -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">
								<li><a href="list${pageMaker.makeSearch(pageMaker.startPage -1)}">&laquo;</a></li>
							</c:if>
							
							<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
								<li <c:out value="${pageMaker.cri.page == idx?'class =active':'' }"/>>
									<a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next && pageMaker.endPage >0}">
								<li><a href="list${pageMaker.makeSearch(pageMaker.endPage +1) }">&raquo;</a>
								</li>
							</c:if>
						</ul>	
					</div>		
				
					<%-- <div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">
								<li><a href="${pageMaker.startPage -1}">&laquo;</a></li>
							</c:if>
							
							<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
								<li <c:out value="${pageMaker.cri.page == idx? 'class = active':'' }"/>>
									<a href="${idx}">${idx}</a>
								</li>
							</c:forEach>
							
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<li>
									<a href="${pageMaker.endPage + 1}">&raquo;</a>
								</li>
							</c:if>
							
						</ul>
					</div> --%>
				</div>	<!-- box-footer -->
			</div>
		</div>	<!-- col (left) -->
	</div>	<!-- row -->
</section>	<!-- content -->
<form id="jobForm">
	<input type="hidden" name="page" value=${pageMaker.cri.page}>
	<input type="hidden" name="perPageNum" value=${pageMaker.cri.perPageNum}>
</form>

<script type="text/javascript">
	var result ='${msg}';
	
	if(result == 'SUCCESS'){
		alert("처리가 완료되었습니다.");
	}


	$(document).ready(function(){
		$('#searchBtn').on("click", function(event){
			self.location = "list"
						  + '${pageMaker.makeQuery(1)}'
						  + "&searchType="
						  + $("select option:selected").val()
						  + "&keyword=" + $('#keywordInput').val();
			});

		$('#newBtn').on("click",function(evt){
			self.location = "register";
			});	
		});
	
	
	/* $(".pagination li").on("click",function(event){

		event.preventDeafault();

		var targetPage = $(this).attr("href");

		var jobForm = $("#jobForm");
		jobForm.find("[name='page']").val(targetPage);
		jobForm.attr("action","/board/listPage").attr("method","get");
		jobForm.submit();
	}); */

</script>

<%@include file="../include/footer.jsp" %>
</body>
</html>