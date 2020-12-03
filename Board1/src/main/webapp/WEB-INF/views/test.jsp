<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax Test Page</title>
<style type="text/css">
#modDiv {
	width:300px;
	height:100px;
	background-color: gray;
	position: absolute;
	top:50px;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}

.pagination {
	width: 100%;
}

.pagination li {
	list-style: none;
	float: left;
	padding: 3px;
	border: 1px solid blue;
	margin: 3px;
}

.pagination li a{
	margin: 3px;
	text-decoration: none;
}
</style>
</head>
<body>
<h2>Ajax Test Page</h2>

<div id="modDiv" style="display: none;">
	<div class="modal-title"></div>
	<div>
		<input type="text" id="replytext">
	</div>
	<div>
		<button type="button" id="replyModBtn">Modify</button>
		<button type="button" id="replyDelBtn">DELETE</button>
		<button type="button" id="closeBtn">Close</button>
	</div>
</div>

<div>
	<div>
		REPLYER<input type="text" name="replyer" id="newReplyWriter"> 
	</div>
	<div>
		REPLYER TEXT<input type="text" name="replyertext" id="newReplyText"> 
	</div>
	<button id="replyAddBtn">ADD REPLY</button>
</div>

<ul id="replies"></ul>
<ul class='pagination'></ul>
<!-- jQuery 2.1.4 -->
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
	var bno = 9;

	getPageList(1);
	
	function getAllList(){
	
	
		$.getJSON("/replies/all/"+bno, function(data){
			console.log(data.length);
	
			var str = "";
			
			$(data).each(
					function(){
						str += "<li data-rno='"+this.rno+"' class='replyLi'>" 
							+ this.rno 
							+ ":" 
							+ this.replytext
							+"<button>MOD</button></li>";
			});
			
			$("#replies").html(str);
		});
	}

	$("#closeBtn").on("click", function(){
		$("#modDiv").hide();
		//$("#modDiv").css("display","none");
	});
	
	$("#replyAddBtn").on("click", function(){
		var replyer = $("#newReplyWriter").val();	
		var replytext = $("#newReplyText").val();	

		
		$.ajax({
			type:'post',
			url:'/replies',
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"POST"
			},
			dataType:'text',
			data:JSON.stringify({
				bno:bno,
				replyer:replyer,
				replytext:replytext
			}),
			success:function(result){
				if(result =='SUCCESS') {
 					alert('등록 되었습니다.');
 					getAllList();
				}
			}
		}); 
		/*
		$.post("/replies", {replyer:replyer, replytext:replytext},
			function(result){
				alert(result);
			}
		);
		*/
	});

	$("#replies").on("click",".replyLi button", function() {

		var reply = $(this).parent();

		var rno = reply.attr("data-rno");
		var replytext = reply.text();

		$(".modal-title").html(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow");

		alert(rno + " : " + replytext);
	});

	$("#replyDelBtn").on("click", function(){

		var rno = $(".modal-title").html();
		var	replytext = $("#replytext").val();

		$.ajax({
			type: 'delete',
			url: '/replies/' + rno,
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"DELETE"
			},
			dataType:"text",
			success:function(result){
				console.log("result:"+result);
				if(result == "SUCCESS"){
					alert("삭제 되었습니다.");
					$("#modDiv").hide("slow");
					getAllList();
				}
			}
		});
	});

	$("#replyModBtn").on("click", function(){

		var rno = $(".modal-title").html();
		var	replytext = $("#replytext").val();

		$.ajax({
			type:'put',
			url:'/replies/'+rno,
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"PUT"},
			data:JSON.stringify({ replytext:replytest }),		
			dataType:"text",
			success:function(result){
				console.log("result:"+result);
				if(result == "SUCCESS"){
					alert("수정 되었습니다.");
					$("#modDiv").hide("slow");
					getPageList(replyPage);
				}
			}
		});
				
	});

	function getPageList(page) {
		$.getJSON("/replies/"+bno+"/"+page, function(data){
			console.log(data.list.length);

			var str = "";

			$(data.list).each(function(){
				str += "<li data-rno='"+this.rno+"' class='replyLi'>" 
				+ this.rno 
				+ ":" 
				+ this.replytext
				+"<button>MOD</button></li>";
			});

			$("#replies").html(str);
			printPaging(data.pageMaker);
		});	
	}

	function printPaging(pageMaker) {
		var str = "";

		if(pageMaker.prev){
			str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
		}

		for(var i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){
			var strClass = pageMaker.cri.page == i?'class=active':'';
			str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
		}

		if(pageMaker.next){
			str += "<li><a href='"+(pageMaker.endPage+1)+"'> >> </a></li>";
		}
		$('.pagination').html(str);
	}	

	var replyPage = 1;

	$(".pagination").on("click","li a",function(event){

		event.preventDefault();
		replyPage = $(this).attr("href");
		getPageList(replyPage);
	});
		
	
</script>

</body>
</html>