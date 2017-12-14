var interval;
var rqUrl = "http://10.118.37.36:9090";
// var rqUrl = "http://10.202.43.154:9090";
var vue = new Vue({
    el: '#app',
    data:{
        userName: "",
        password: "",
        duration: "",
        surveyType: 1,
        surveyListByGroup: "",
        surveyList:"",//问卷详情列表

        startTime:"",
        endTIme:"",

        scoreId:"",
        supplierName:"",
        projectName:"",
        relationerName:"",
        relationerEmail:"",
        buyerName:"",
        buyerEmail:"",

        showTbody: false,
        showGeneralPage: true,
        showManagerPage: true,
        showTable : true,
        showDetail : false,

        answerName:"",
        answer6blank:"",
        listData:"",
        pageNum:1,
        pageSize:10,
        pageIndex:1,
        pages:1,
        total:0,
        perNum: [100, 50, 20, 10],    //添加每页显示条数选择

        detailList:""

    },
    methods: {
        getUserName: function (userName) {
            this.userName = userName;
        }
        ,getDuration: function (duration) {
            this.duration = "";
            this.duration = duration;
        }
        ,openLogin: function(userName,password){
            var that = this;
            layer.open({
                type: 1 //Page层类型
                ,area: ['500px', '300px']
                ,title: '用户登录'
                ,shade: 0.6 //遮罩透明度
                ,maxmin: false //允许全屏最小化
                ,anim: 1 //0-6的动画形式，-1不开启
                ,content:
                    '<div class="survey_login" id="survey-login">' +
                    '<p><span class="survey_login_tag">用户名：</span><input type="text" class="survey_login_input" name="userName" placeholder="请输入用户名" maxlength="100" v-model="userName"></p>' +
                    '<p><span class="survey_login_tag">密码：</span><input type="password" class="survey_login_input" name="password" placeholder="请输入密码" maxlength="100" v-model="password"></p>' +
                    '<p class="survey_login_submit"><button type="submit" id="loginSubmit" class="s-btn">登录</button></p>'+
                    '</div>'
                ,success: function(layero, index1){
                    $("#loginSubmit").click(function(){
                        vue.loginF(that,index1);
                    });
                    //键盘Enter事件
                    $('#loginSubmit').parent().siblings("p").find("input").bind('keypress',function(event){
                        if(event.keyCode == "13") {
                            vue.loginF(that,index1);
                        }
                    });
                }
            });
        }
        ,loginF:function(that,index1){
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
            var index = layer.load(1);
            $.ajax({
                type:"get",
                data:{userName: userN, password: passW,surveyType:1},
                url: rqUrl + "/login",
                dataType:"JSON",
                success:function(data){
                    //登录成功
                    if(data && data.code && data.message && data.code == 200 ){
                        layer.close(index1);
                        that.startTime = new Date();

                        //todo:登录成功后将用户名显示和时间
                        vue.getUserName(userN);
                        var reg = /^\d$/,
                            sleep = 1000,
                            sum = 0,
                            duration = "";
                        clearInterval(interval);
                        interval = null;
                        interval = setInterval(function() {
                            sum++;
                            var d = new Date("1111/1/1,0:0:0");
                            d.setSeconds(sum);
                            var h = d.getHours();
                            h = reg.test(h) ? "0" + h + ":" : h + ":";
                            var m = d.getMinutes();
                            m = reg.test(m) ? "0" + m + ":" : m + ":";
                            var s = d.getSeconds();
                            s = reg.test(s) ? "0" + s : s;
                            duration = h + m + s;
                            vue.getDuration(duration);
                        }, sleep);
                        //普通用户
                        if(data.message == 0){
                            that.showGeneralPage = true;
                            that.showManagerPage = false;
                            that.surveyList = data.data;
                            that.surveyList.reduce(function (prev, next) {
                                if(prev.classes !== next.classes) {
                                    next.hiddenClass = '';
                                } else {
                                    next.hiddenClass = 'hidden_title';
                                }
                                return next;
                            });
                            //todo:判断用户之前是否登录过，读取localStorage内容
                            //将数据保存在本地
                            var storage = window.localStorage;
                            var localUser = storage.user;
                            if(localUser){
                                var parseUser = JSON.parse(localUser);
                                if(parseUser.length){
                                    //判断本地localStorage是否已存在该用户数据，有则取出展示，没有则为新用户，全部为空
                                    for(var i=0;i<parseUser.length;i++){
                                        if(parseUser[i].userName == that.userName){
                                            layer.msg("欢迎您回来继续答题");
                                            //取出展示

                                            //6个空格
                                            that.surveyType = parseUser[i].userScore.surveyType;
                                            that.supplierName = parseUser[i].userScore.supplierName;
                                            that.projectName = parseUser[i].userScore.projectName;
                                            that.relationerName = parseUser[i].userScore.relationerName;
                                            that.relationerEmail = parseUser[i].userScore.relationerEmail;
                                            that.buyerName = parseUser[i].userScore.buyerName;
                                            that.buyerEmail = parseUser[i].userScore.buyerEmail;

                                            //问卷答题详情
                                            if(parseUser[i].answer.length){
                                                setTimeout(function(){
                                                    for(var j=0;j<parseUser[i].answer.length;j++){
                                                        for(var k=0;k<that.$refs.surveyItem.length;k++){
                                                            var serialNumber = $(that.$refs.surveyItem[k]).find(".serialNumber").html();
                                                            if(parseUser[i].answer[j].serialNumber == serialNumber){
                                                                var choseItem = $(that.$refs.surveyItem[k]).find(".survey_chose_item");
                                                                for(var m=0;m<choseItem.length;m++){
                                                                    if($(choseItem[m]).data("checked") == parseUser[i].answer[j].choseValue){
                                                                        $(choseItem[m]).attr("checked","checked").siblings(".survey_chose_feedback").val(parseUser[i].answer[j].reply);
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                    }
                                                },500);
                                            }
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }else{
                                layer.msg("登录成功，开始答题咯");
                            }
                        }else{//管理员用户
                            that.showGeneralPage = false;
                            that.showManagerPage = true;
                            that.showTable = true;
                            that.showDetail = false;
                            vue.showListPage(that,data);
                        }
                    }else{
                        layer.msg("登录失败，请重新登录");
                        return false;
                    }
                    layer.close(index);
                },error:function(err){
                    console.log(err);
                },complete:function(){
                    // 如数据加载过快，loading的dom结构未生成，success中关闭不成功，则强制等待300ms后再关闭loading层
                    setTimeout(function(){
                        layer.close(index);
                    },300);
                }
            })
        }
        ,surveySubmit:function(){

            if(this.userName == ""){
                layer.msg("您还没有登陆，请先登陆");
                return false;
            }

            this.endTime = new Date();

            var postData = new FormData();

            postData.append("startTime", this.startTime);
            postData.append("endTime", this.endTime);

            if(this.supplierName == ""){layer.msg("供应商全称为空");return false;}
            if(this.projectName == ""){layer.msg("合作项目名称为空");return false;}
            if(this.relationerName == ""){layer.msg("联系人姓名为空");return false;}
            if(this.relationerEmail == ""){layer.msg("联系人邮箱为空");return false;}
            if(this.buyerName == ""){layer.msg("采购专员姓名为空");return false;}
            if(this.buyerEmail == ""){layer.msg("采购专员邮箱为空");return false;}

            postData.append("userName", this.userName);
            postData.append("surveyType", this.surveyType);
            postData.append("supplierName", this.supplierName);
            postData.append("projectName", this.projectName);
            postData.append("relationerName", this.relationerName);
            postData.append("relationerEmail", this.relationerEmail);
            postData.append("buyerName", this.buyerName);
            postData.append("buyerEmail", this.buyerEmail);

            var answer = [];
            for(var i=0;i<this.$refs.surveyItem.length;i++){
                var answerItem = {};
                var serialNumber1 = $(this.$refs.surveyItem[i]).find(".serialNumber").html();
                if($(this.$refs.surveyItem[i]).find('input[checked="checked"]').length != 1){
                    layer.msg("第"+serialNumber1+"题未作答");
                    return false;
                }else{
                    if($(this.$refs.surveyItem[i]).find('input[checked="checked"]').siblings(".survey_chose_feedback").val().trim() == ""){
                        layer.msg("第"+serialNumber1+"题未填写理由");
                        return false;
                    }else{
                        var choseValue = $(this.$refs.surveyItem[i]).find('input[checked="checked"]').data("checked");
                        var reply = $(this.$refs.surveyItem[i]).find('input[checked="checked"]').siblings(".survey_chose_feedback").val().trim();
                        $.extend(true,answerItem,{
                            serialNumber: serialNumber1,
                            choseValue: choseValue,
                            reply: reply
                        })
                    }
                    answer.push(answerItem);
                }
            }

            postData.append("answerJson", JSON.stringify(answer));

            var files = new FormData();
            for(var i=0;i<this.$refs.mustFiles.length;i++){
                var serialNumber2 = $(this.$refs.mustFiles[i]).parents(".survey_content_item").find(".serialNumber").html();
                if($(this.$refs.mustFiles[i]).siblings('input[type="file"]').val() == ""){
                    layer.msg("第"+serialNumber2+"题未上传证明文件");
                    return false;
                }
            }

            function getFileName(file,name){
                var fName = name || "fileName";
                var index1 = file.lastIndexOf(".");
                var index2 = file.length;
                var suffix = file.substring(index1+1,index2);//后缀名
                var fileName = file.substring(0,index1);
                if(fName == "fileName"){
                    return fileName;
                }else if(fName == "suffix"){
                    return suffix;
                }
            }

            for(var i=0;i<this.$refs.filesList.length;i++){
                var serialNumber3 = $(this.$refs.filesList[i]).parents(".survey_content_item").find(".serialNumber").html();
                var fileValue = this.$refs.filesList[i].files;
                if(fileValue != ""){
                    for(var j=0;j<this.$refs.filesList[i].files.length;j++){
                        postData.append("files", fileValue[j], serialNumber3+"."+getFileName(fileValue[j].name, "suffix"));
                    }
                }
            }
            var index = layer.load(1);
            $.ajax({
                type:"POST",
                data:postData,
                processData: false,   // 告诉jQuery不要去处理发送的数据
                contentType: false,   // 告诉jQuery不要去设置Content-Type请求头});
                url:rqUrl + "/answer/save",
                success:function(data){
                    if(data && data.code && data.code == 200){
                        layer.msg("恭喜！您的问卷已提交成功了");
                    }else{
                        layer.msg("Oops！提交失败了");
                    }
                    layer.close(index);
                },error:function(err){
                    console.log(err);
                },complete:function(){
                    // 如数据加载过快，loading的dom结构未生成，success中关闭不成功，则强制等待300ms后再关闭loading层
                    setTimeout(function(){
                        layer.close(index);
                    },300);
                }
            })
        }
        ,managerSubmit:function(){
            this.endTime = new Date();

            var postData = new FormData();

            if(this.supplierName == ""){layer.msg("供应商全称为空");return false;}
            if(this.projectName == ""){layer.msg("合作项目名称为空");return false;}
            if(this.relationerName == ""){layer.msg("联系人姓名为空");return false;}
            if(this.relationerEmail == ""){layer.msg("联系人邮箱为空");return false;}
            if(this.buyerName == ""){layer.msg("采购专员姓名为空");return false;}
            if(this.buyerEmail == ""){layer.msg("采购专员邮箱为空");return false;}

            postData.append("scoreId", this.scoreId);
            postData.append("supplierName", this.supplierName);
            postData.append("projectName", this.projectName);
            postData.append("relationerName", this.relationerName);
            postData.append("relationerEmail", this.relationerEmail);
            postData.append("buyerName", this.buyerName);
            postData.append("buyerEmail", this.buyerEmail);

            var answer = [];
            for(var i=0;i<this.$refs.answerItem.length;i++){
                var answerItem = {};
                var serialNumber1 = $(this.$refs.answerItem[i]).find(".serialNumber").html();
                // var itemId = $(this.$refs.answerItem[i]).find(".survey_content_itemId").html();
                if($(this.$refs.answerItem[i]).find('input[checked="checked"]').length != 1){
                    layer.msg("第"+serialNumber1+"题未作答");
                    return false;
                }else{
                    if($(this.$refs.answerItem[i]).find('input[checked="checked"]').siblings(".survey_chose_feedback").val().trim() == ""){
                        layer.msg("第"+serialNumber1+"题未填写理由");
                        return false;
                    }else{
                        var itemId = $(this.$refs.answerItem[i]).data("id");
                        var choseValue = $(this.$refs.answerItem[i]).find('input[checked="checked"]').data("checked");
                        var reply = $(this.$refs.answerItem[i]).find('input[checked="checked"]').siblings(".survey_chose_feedback").val().trim();
                        $.extend(true,answerItem,{
                            id: itemId,
                            serialNumber: serialNumber1,
                            choseValue: choseValue,
                            reply: reply
                        })
                    }
                    answer.push(answerItem);
                }
            }
            postData.append("answerJson", JSON.stringify(answer));

            var index = layer.load(1);
            $.ajax({
                type:"POST",
                data:postData,
                processData: false,   // 告诉jQuery不要去处理发送的数据
                contentType: false,   // 告诉jQuery不要去设置Content-Type请求头});
                url:rqUrl + "/answer/save",
                success:function(data){
                    if(data && data.code && data.code == 200){
                        layer.msg("恭喜！您的问卷已提交成功了");
                    }else{
                        layer.msg("Oops！提交失败了");
                    }
                    layer.close(index);
                },error:function(err){
                    console.log(err);
                },complete:function(){
                    // 如数据加载过快，loading的dom结构未生成，success中关闭不成功，则强制等待300ms后再关闭loading层
                    setTimeout(function(){
                        layer.close(index);
                    },300);
                }
            })
        }
        ,surveySave:function(){
            if(this.userName == ""){
                layer.msg("您还没有登陆，请先登陆");
                return false;
            }
            var userScore = {
                userName: this.userName,
                surveyType: this.surveyType,
                supplierName: this.supplierName,
                projectName: this.projectName,
                relationerName: this.relationerName,
                relationerEmail: this.relationerEmail,
                buyerName: this.buyerName,
                buyerEmail: this.buyerEmail
            };
            var answer = [];
            for(var i=0;i<this.$refs.surveyItem.length;i++){
                var answerItem = {};
                var serialNumber = $(this.$refs.surveyItem[i]).find(".serialNumber").html();
                if($(this.$refs.surveyItem[i]).find('input[checked="checked"]').length == 1 ){
                    var choseValue = $(this.$refs.surveyItem[i]).find('input[checked="checked"]').data("checked");
                    var reply = $(this.$refs.surveyItem[i]).find('input[checked="checked"]').siblings(".survey_chose_feedback").val().trim();
                    $.extend(true, answerItem, {
                        serialNumber: serialNumber,
                        choseValue: choseValue,
                        reply: reply
                    });
                    answer.push(answerItem);
                }
            }

            if(!window.localStorage){
                alert("浏览器需要支持localstorage");
            }else{
                var newUserData = {};
                $.extend(true, newUserData, {
                    userName : this.userName,
                    surveyType : this.surveyType,
                    // startTime : this.startTime,
                    // endTime : this.endTime,
                    userScore : userScore,
                    answer : answer
                });
                //将数据保存在本地
                var storage = window.localStorage;
                var localUser = storage.user;
                if(localUser){
                    var parseUser = JSON.parse(localUser);
                    if(parseUser.length){
                        //判断本地localStorage是否已存在该用户数据，有则覆盖，没有则新增
                        for(var i=0;i<parseUser.length;i++){
                            if(parseUser[i].userName == this.userName){
                                $.extend(true,parseUser[i],newUserData);//覆盖
                            }else{
                                parseUser.push(newUserData);//新增
                            }
                        }
                        storage.user = JSON.stringify(parseUser);
                    }
                }else{
                    storage.user = JSON.stringify([newUserData]);
                }

                layer.confirm('保存成功！您是否继续答题？', {
                    btn: ['继续','下次再答'] //按钮
                }, function(){
                    layer.msg('继续加油', {icon: 1});
                }, function(){
                    layer.msg('记得下次再来哦!');
                    setTimeout(function(){
                        window.location.reload();
                    },2000)
                });
            }
        }
        ,changeFiles:function(event){
            var files = event.currentTarget.files;//是一个数组，如果支持多个文件，则需要遍历
            var fileInput = $(event.currentTarget).parents(".survey_content_title_file").find(".file_name");
            fileInput.html("");
            for(var i=0;i<files.length;i++){
                if(files[i].size == 0){
                    layer.msg("文件：" + files[i].name + " 为空文件");
                    $(event.currentTarget).val("");
                    fileInput.html("");
                    return false;
                }
                if(files[i].size > (1 * 1024 * 1024)){
                    layer.msg("文件：" + files[i].name + " 过大");
                    $(event.currentTarget).val("");
                    fileInput.html("");
                    return false;
                }
                fileInput.append(files[i].name+"； ")
            }
        }
        ,showDetailPage:function(scoreId,userName){
            this.showTable = false;
            this.showDetail = true;
            //显示用户名
            this.answerName = userName;
            var that = this;
            //请求题目
            var questionPromise = $.ajax({
                type:"GET",
                data:{surveyType: that.surveyType},
                url:rqUrl + "/question/findBySurveyType"
            });
            questionPromise.then(function(data){
                if(data) {
                    that.detailList = data;
                    that.detailList.reduce(function (prev, next) {
                        if(prev.classes !== next.classes) {
                            next.hiddenClass = '';
                        } else {
                            next.hiddenClass = 'hidden_title';
                        }
                        return next;
                    });
                }else{
                    layer.msg("题目加载失败");
                }
            }).then(function(){
                //请求userScore，填6个表格空
                var userScorePromise = $.ajax({
                    type:"GET",
                    data:{scoreId: scoreId},
                    url:rqUrl + "/userScore/findByScoreId"
                });
                userScorePromise.then(function(data){
                    if(data && data.code && data.code == 200 && data.data){
                        //6个空格
                        that.scoreId = data.data.scoreId;
                        that.supplierName = data.data.supplierName;
                        that.projectName = data.data.projectName;
                        that.relationerName = data.data.relationerName;
                        that.relationerEmail = data.data.relationerEmail;
                        that.buyerName = data.data.buyerName;
                        that.buyerEmail = data.data.buyerEmail;
                    }else{
                        layer.msg("答卷数据加载失败，请重试");
                        return false;
                    }
                },function(err){
                    console.log(err);
                }).then(function(){
                    //请求题目答题详情
                    var answerPromise = $.ajax({
                        type:"GET",
                        data:{scoreId: scoreId},
                        url:rqUrl + "/answer/findAnswerInfo"
                    });
                    answerPromise.then(function(data){
                        if(data && data.code && data.code == 200 && data.data){
                            for(var j=0;j<data.data.length;j++){
                                for(var k=0;k<that.$refs.answerItem.length;k++){
                                    var serialNumber = $(that.$refs.answerItem[k]).find(".serialNumber").html();
                                    if(data.data[j].serialNumber == serialNumber){
                                        var downloadFile = $(that.$refs.answerItem[k]).find(".survey_content_title_file");
                                        $(that.$refs.answerItem[k]).attr("data-id",data.data[j].id);
                                        if(data.data[j].fileLink){
                                            $(downloadFile).show().find("a").attr("href",data.data[j].fileLink)
                                        }
                                        var choseItem = $(that.$refs.answerItem[k]).find(".survey_chose_item");
                                        for(var m=0;m<choseItem.length;m++){
                                            if($(choseItem[m]).data("checked") == data.data[j].choseValue){
                                                $(choseItem[m]).attr("checked","checked")
                                                    .siblings(".survey_chose_feedback").val(data.data[j].reply);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }

                    },function(err){
                        console.log(err);
                    });
                },function(err){
                    console.log(err)
                });
            },function(err){
                console.log(err)
            });
        }
        ,showList:function(){
            this.showTable = true;
            this.showDetail = false;
            var that = this;
            setTimeout(function(){
                var showPageItem = ".h-pageNum ul li[data-page="+that.pageNum+"]";
                $(showPageItem).addClass("active").siblings().removeClass("active");
                $(".h-pageNum ul li[data-page='...']").addClass("h-ellipsis");
            },200);
            // $(this.$refs.page[this.pageNum - 1]).addClass("active").siblings().removeClass("active");
            var showPageItem = ".h-pageNum ul li[data-page="+this.pageNum+"]";
            $(showPageItem).addClass("active").siblings().removeClass("active");
        }
        ,showListPage:function(that,data){
            var pageData = data.data.condition.pageData;
            if(data.data && pageData && pageData.length){
                that.showTbody = true;
                for(var i=0;i<pageData.length;i++){
                    var durationT = ((pageData[i].endTime - pageData[i].startTime)/(1000*60)).toFixed(0);
                    if(durationT == 0){
                        durationT = "小于 1 "
                    }
                    if(pageData[i].status == 1){
                        $.extend(true,pageData[i],{statusDetail:"及格"});
                    }else{
                        $.extend(true,pageData[i],{statusDetail:"不及格"});
                    }
                    $.extend(true,pageData[i],{
                        index: i+1,
                        duration: durationT
                    });
                }
            }else{
                that.showTbody = false;
            }
            that.listData = pageData;
            // 显示页码
            var d = {};
            that.pageSize = data.data.pageSize;
            that.pageNum = data.data.pageIndex;
            that.pages = data.data.totalPage;
            that.total = data.data.totalRecord;
            $.extend( true, d, {
                "pageNum":that.pageNum,
                "pageSize":data.data.pageSize,
                "pages":that.pages,
                "total":that.total
            });
            paginationShow(that,d);
        }
        ,returnTop:function(){
            $("html,body").animate({scrollTop:0}, 500);
        }
        ,pageNumClick:function(pageIndex){
            this.pageIndex = pageIndex;
            vue.pageRq(pageIndex,this.pageSize)
        }
        ,pagePreClick:function(){
            var pageIndex = this.pageIndex;
            if(pageIndex == 1){
                layer.msg("当前页已为首页");
                return false;
            }else{
                pageIndex--;
                this.pageIndex = pageIndex;
            }
            vue.pageRq(pageIndex,this.pageSize)
        }
        ,pageNextClick:function(){
            var pageIndex = this.pageIndex;
            if(pageIndex == this.pages){
                layer.msg("当前页已为最后一页");
                return false;
            }else{
                pageIndex++;
                this.pageIndex = pageIndex;
            }
            vue.pageRq(pageIndex,this.pageSize)
        }
        ,pageSizeClick:function(pageSize){
            this.pageSize = pageSize;
            this.pageIndex = 1;
            vue.pageRq(this.pageIndex,pageSize)
        }
        ,pageRq:function(pageIndex,pageSize){
            var that = this;
            var index = layer.load(1);
            $.ajax({
                type:"get",
                data:{pageIndex: pageIndex, pageSize: pageSize},
                url:rqUrl + "/userScore/findByPage",
                dataType:"JSON",
                success:function(data){
                    if(data && data.code && data.code == 200 ){
                        vue.showListPage(that,data);
                    }else{
                        layer.msg("数据请求失败");
                    }
                    layer.close(index);
                },error:function(err){
                    console.log(err);
                },complete:function(){
                    // 如数据加载过快，loading的dom结构未生成，success中关闭不成功，则强制等待300ms后再关闭loading层
                    setTimeout(function(){
                        layer.close(index);
                    },300);
                }
            })
        }
        ,radioClick:function(event){
            $(event.currentTarget).find(".survey_chose_item").attr("checked","checked");
            $(event.currentTarget).parent().siblings().find(".survey_chose_item").removeAttr("checked");
        }
        ,switchBar:function(event){
            $(event.currentTarget).html();
            var titleItem = "";
            if(this.showGeneralPage){
                titleItem = $(this.$refs.surveyItem);
            }else{
                titleItem = $(this.$refs.answerItem);
            }
            for(var i=0;i<titleItem.find(".survey_content_bar").length;i++){
                if($(event.currentTarget).html() == $(titleItem[i]).find(".survey_content_bar").html()){
                    $(titleItem[i]).find(".survey_content_title").slideToggle();
                    $(titleItem[i]).find(".survey_content_chose").slideToggle();
                }
            }
        }
    }
});

vue.openLogin();