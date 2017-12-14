$(function(){
	$("#loginSubmit").on("click",function(){
        var userN = $('[name="userName"]').val();
        var passW = $('[name="password"]').val();
        if(userN == ""){
            layer.msg("请输入用户名");
            $('[name="userName"]').focus();
            return false;
        }else if(passW == ""){
            layer.msg("请输入密码");
            $('[name="password"]').focus();
            return false;
        }
		$.post("/login", {userName: userN, password: passW}, function(data){
			if(data.code == 200){
				window.location.href="/";
			}else{
				layer.msg("登录失败，请联系管理员！");
			}
		});
	})
});