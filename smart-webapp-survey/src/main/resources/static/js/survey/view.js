var interval;
var rqUrl = "";
var vue = new Vue({
    el: '#app',
    data:{
        surveyType: 1,
        surveyListByGroup: "",
        surveyList:"",//问卷详情列表

        scoreId:"",
        supplierName:"",
        projectName:"",
        relationerName:"",
        relationerEmail:"",
        buyerName:"",
        buyerEmail:"",

        detailList:""

    },
    methods: {
        initUser:function(){
            var that = this;
            $.post("/loginUser",function(result){
                if(result.code == 200 && result.data != null){
                    that.userName = result.data.userName;
                    vue.loginF(that);
                }else{
                    layer.msg("检测到用户为登录状态！");
                }
            });
        }
        ,logout:function(){
            $.post("/logout",function(data){
                if(data != null && data.code == 200){
                    window.location.href="/login";
                }else{
                    layer.msg("用户注销失败！");
                }
            });
        }
        ,showDetailPage:function(scoreId){
            this.scoreId = scoreId;
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
            },function(err){
                console.log(err);
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
                    console.log(err)
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
                                    var serialNumber = $(that.$refs.answerItem[k]).data("serialnumber");
                                    if(data.data[j].serialNumber == serialNumber){
                                        $(that.$refs.answerItem[k]).attr("data-id",data.data[j].id);
                                        if(data.data[j].fileLink){
                                            $(that.$refs.answerItem[k]).find(".survey_content_title_file").show().find("a").attr("href",data.data[j].fileLink)
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
                        console.log(err)
                    }).then(function(){
                        $("input").attr("readonly","readonly");
                        $("input[type=radio]").attr("disabled","disabled");
                    },function(err){
                        console.log(err);
                    });

                });

            },function(err){
                console.log(err);
            });

        }
        ,returnTop:function(){
            $("html,body").animate({scrollTop:0}, 500);
        }
        ,returnBottom:function(){
            $("html,body").animate({scrollTop:document.body.scrollHeight}, 500);
        }
        ,switchBar:function(event){
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

$(function(){
    var scoreId = $("#scoreId").html();
    vue.showDetailPage(scoreId);
});
