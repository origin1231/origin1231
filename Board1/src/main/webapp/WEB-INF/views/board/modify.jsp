<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>

				<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label>
							<input type="text" name="title" class="form-control" value="${boardVO.title}">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3">${boardVO.content}</textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputWriter1">Writer</label>
							<input type="text" name="writer" class="form-control" value="${boardVO.writer}">
						</div>
					</div>
				</form>
					
				<div class="box-footer">
					<button type="submit" class="btn btn-primary">SAVE</button>
					<button type="submit" class="btn btn-warning">CANCEL</button>
				</div>
					
				<script type="text/javascript">
				$(document).ready(function(){
					var formObj = $("form[role='form']");
					console.log(formObj);
			
					$(".btn-primary").on("click",function(){
						formObj.submit();
					});
			
					$(".btn-warning").on("click",function(){
						self.location = "/board/listAll"
					});
				});
			
				</script>	
			</div>
		</div>	<!--/.col (left) -->
	</div>	<!-- /.row -->
</section>	<!-- /.content -->
</div>	<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
</body>
</html>