<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>readPage</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ</h3>
				</div>

				<form role="form" action="modifyPage" method="post">
					<input type="hidden" name="bno" value="${boardVO.bno}">
					<input type="hidden" name="page" value="${cri.page}">
					<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
					<input type="hidden" name="searchType" value="${cri.searchType}">
					<input type="hidden" name="keyword" value="${cri.keyword}">
				</form>
				
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label>
							<input type="text" name="title" class="form-control" value="${boardVO.title}"
							 readonly="readonly">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3" readonly="readonly">${boardVO.content}</textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputWriter1">Writer</label>
							<input type="text" name="writer" class="form-control" value="${boardVO.writer}"
							 readonly="readonly">
						</div>
					</div>
					
					<div class="box-footer">
						<button type="submit" class="btn btn-warning modifyBtn">Modify</button>
						<button type="submit" class="btn btn-danger removeBtn">Remove</button>
						<button type="submit" class="btn btn-primary">Go List</button>
					</div>
					
					<script type="text/javascript">
					$(document).ready(function(){
						var formObj = $("form[role='form']");
						console.log(formObj);
				
						$(".btn-warning").on("click",function(){
							formObj.attr("action","/sboard/modify");
							formObj.attr("method","get");
							formObj.submit();
						});
				
						$(".btn-danger").on("click",function(){
							formObj.attr("action","/sboard/remove");
							formObj.submit();
						});
				
						$(".btn-primary").on("click",function(){
							formObj.attr("method","get");
							formObj.attr("action","/sboard/list")
							formObj.submit();
						});
					});
				
					</script>	
			</div>
		</div>	<!--/.col (left) -->
	</div>	<!-- /.row -->
</section>	<!-- /.content -->

<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
  </span>
  <h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{replytext}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>

</div>	<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
</body>
</html>