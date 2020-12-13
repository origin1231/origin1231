<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadAjax</title>
<style type="text/css">
.fileDrop {
	width: 100%;
	height: 200px;
	border: 1px dotted blue;
}

small {
	margin-left: 3px;
	font-weight: bold;
	color: gray;
}
</style>
</head>
<body>
	<h3>Ajax File Upload</h3>
	<div class="fileDrop"></div>
	<div class="uploadedList"></div>
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		$(".fileDrop").on("dragenter dragover", function(event){
			event.preventDefault();
		});

		$(".fileDrop").on("drop", function(event){
			event.preventDefault();

			var files = event.originalEvent.dataTransfer.files;

			var file = files[0];

			console.log(file);

			var formData = new FormData();

			formData.append("file",file);

			$.ajax({
				url:"/uploadAjax",
				data:formData,
				dataType:"text",
				processData:false,
				contentType:false,
				type:"POST",
				success:function(data){

					var str = "";

					if(checkImageType(data)){
						str ="<div><a href=displayFile?fileName="+getImageLink(data)+">"
							+"<img src='displayFile?fileName="+data+"'/>"
							+"</a><small data-src="+data+">X</small></div>";
					}else{
						str ="<div><a href='displayFile?fileName"+data+"'>"
							+ getOriginalName(data)+"</a>"
							+"<small data-src="+data+">X</small></div></div>";
					}
					$(".uploadedList").append(str);
					alert(data);
				}
			});
		});

		$(".uploadedList").on("click","small",function(event){
			var that = $(this);

			$.ajax{(
				url:"deleteFile",
				type:"post",
				data:{fileName:$(this).attr("data-src")},
				dataType:"text",
				success:function(result){
					if(result == 'deleted'){
						that.parent("div").remove();	
					}
				}
			)};
		});

		// 전송받은 파일이 이미지 파일인지 확인 하는 함수
		function checkImageType(fileName){
			// 정규표현식을 이횽해서 파일의 확장자가 존재하는지 검사
			var pattern = /jpg|gif|png|jpeg/i;

			return fileName.match(pattern);
		}

		// 일반 파일의 이름이 길게 출력되는 경우 줄여주는 기능 하는 함수
		function getOriginalName(fileName){
			if(checkImageType(fileName)){
				return;
			}

			var idx = fileName.indexOf("_") + 1;
			return fileName.substr(idx);
		}

		// 이미지 파일 원본 파일 찾는 함수
		function getImageLink(fileName){
			if(!checkImageType(fileName)){
				return;
			}
			var front = fileName.substr(0,12);
			var end = fileName.substr(14);	

			return front + end;
		}
		
	</script>
</body>
</html>