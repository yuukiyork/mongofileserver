<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>发布</title>
<%
	//context path
    String contextPath = request.getContextPath();
    request.setAttribute("contextPath",contextPath);
    //js and css version
    String jsVersion = "1.0.1";
    String cssVersion = "1.0.1";
	request.setAttribute("jsVersion", jsVersion);
	request.setAttribute("cssVersion", cssVersion);
    String timeStamp = System.currentTimeMillis() + "";
	request.setAttribute("timeStamp", timeStamp);
%>
<script>
	var contextPath = '${contextPath}';
</script>
    <script type="text/javascript" src="${contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/ajaxfileupload.js"></script>
</head>
<body>
	<form id="baseInfoForm" class="form-horizontal">
		<div class="panel-body">
			<input type="hidden" name="baseNews.id" value="${baseNews.id}">
			<div class="form-group">
				<label class="control-label col-md-1">新闻标题 :</label>
				<div class="col-md-2">
					<input name="baseNews.title" class="form-control" value="${baseNews.title}"/>
				</div>
				<label class="control-label col-md-1">新闻类型 :</label>
				<div class="col-md-1">
					<select id="newsType" name="baseNews.type" class="form-control" data-value="${baseNews.type}">
						<option value ="0">官方通知</option>
						<option value ="1">行业资讯</option>
						<option value ="2">安装知识</option>
						<option value ="3">故障维修</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-1">列表图片 :</label>
				<div class="col-md-2">
					<input id="img-path" name="baseNews.img" value="${baseNews.img}"class="form-control" readonly>
					<input id="upload-img" type="file" name="upload" class="hide" accept="image/jpeg,image/jpg,image/bmp,image/png">
				</div>
				<div class="col-md-1">
					<button id="upload-btn" class="btn btn-primary" type="button">上传</button>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-1">创建时间 :</label>
				<div class="col-md-2">
					<input data-provide="datepicker" data-date-format="yyyy-mm-dd hh:ii:ss"
					name="baseNews.createTime" class="form-control form_datetime" value="<fmt:formatDate value='${baseNews.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
				</div>
			</div>
		</div>
		<button style="margin-left: 50px" class="btn btn-primary submit" type="button">发布</button>
		<a href="/publish-news" class="btn btn-primary">返回</a>
		<script id="editor_id" name="baseNews.content" type="text/plain">${baseNews.content}</script>
		<button class="btn btn-primary submit" type="button">发布</button>
	</form>
	<script type="text/javascript" src="${contextPath}/resources/js/publish.js"></script>
</body>
</html>
