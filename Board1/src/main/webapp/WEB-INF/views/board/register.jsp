<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Regist</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARDS</h3>
				</div>

				<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label>
							<input type="text" name="title" class="form-control"
							 placeholder="Enter Title">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3" placeholder="Enter..."></textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputWriter1">Writer</label>
							<input type="text" name="writer" class="form-control"
							 placeholder="Enter Writer">
						</div>
					</div>
					
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>	
				</form>				
			</div>
		</div>	<!--/.col (left) -->
	</div>	<!-- /.row -->
</section>	<!-- /.content -->
</div>	<!-- /.content-wrapper -->
<%@include file="../include/footer.jsp"%>
</body>
</html>