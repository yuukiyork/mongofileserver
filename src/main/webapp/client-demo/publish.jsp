<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<title>发布</title>
</head>
<body>
	<form id="baseInfoForm" class="form-horizontal">
		<div class="panel-body">
			<div class="form-group">
				<label class="control-label col-md-1">图片 :</label>
				<div class="col-md-2">
					<input id="img-path" name="img" value="${img}"class="form-control" readonly>
					<!-- <input id="upload-img" type="file" name="upload" class="hide" accept="image/jpeg,image/jpg,image/bmp,image/png"> -->
					<input id="upload-img" type="file" name="upload" class="hide">
				</div>
				<div class="col-md-1">
					<button id="upload-btn" class="btn btn-primary" type="button">上传</button>
				</div>
			</div>
		</div>
		<script id="editor_id" name="content" type="text/plain">${content}</script>
	</form>
	<script type="text/javascript" src="js-lib/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="js-lib/ajaxfileupload.js"></script>
	<!-- 配置文件 -->
	<script type="text/javascript" src="js-lib/MongoDbUEditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="js-lib/MongoDbUEditor/ueditor.all.js"></script>
	<script type="text/javascript" src="publish.js"></script>
</body>
</html>
