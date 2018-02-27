var action = contextPath + "/publish-news";
$(function() {

	$("#upload-btn").click(function(){
		$("#upload-img").click();
	});

	$("#upload-img").change(function() {
		var file = $(this).val();
		if (file) {
			upload();
		}
	});
    
    $(".submit").click(function() {
        $.post(action + "/addOrUpdate", $("#baseInfoForm").serialize(), function(data) {
            if (data.success) {
                alert("操作成功");
                location.reload();
            }
        }, "json");
	});
	
});

function upload() {
    $.ajaxFileUpload({
        url: '/upload?dir=image', //提交的路径
        secureuri: false, // 是否启用安全提交，默认为false
        fileElementId: 'upload-img', // file控件id
        dataType: 'json',
        success: function(data, status) //服务器成功响应处理函数
            {
				$("#upload-img").change(function() {
					var file = $(this).val();
                    if (file) {
						upload();
                    }
                });
                $("#img-path").val(data.path);
				alert("上传成功！");
            },
        error: function(data, status, e) //服务器响应失败处理函数
            {
				$("#upload-img").change(function() {
					var file = $(this).val();
                    if (file) {
						upload();
                    }
                });
				alert("上传失败！");
            }
    });
}