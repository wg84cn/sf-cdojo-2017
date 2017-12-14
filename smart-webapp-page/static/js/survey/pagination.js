var pagination = $("#pagination");
//设置分页更新 页数组 部分
function pageSeries(data){
    if(data && data.pages >= 0){
        var firstShowAll = 8; //初始显示全部页码数
        var firstShowP = 4; //初始显示...前的页码数 前面显示首先三页
        var firstShowA = 4; //初始显示...后的页码数 后面显示最后三页
        if((firstShowP+firstShowA)>firstShowAll){
            console.log("前后显示页数和不能大于初始显示全部页码数");
            return false;
        }
        var pageNumber = [];
        if(data.pages == 0){
            pageNumber.push(1)
        }
        else if(data.pages <= firstShowAll){//当页码总数小于或等于8时全部显示
            for(var i=1;i<=data.pages;i++){
                pageNumber.push(i)
            }
        }
        //当 当前页为前三页或者是后三页的时候。。。。
        else if((data.pageNum <= 3) || (data.pageNum >= data.pages-2)){//当页码总数大于8时，显示前四页和后四页，中间添加 ... 符号
            for(var i=1;i<=firstShowP;i++){
                pageNumber.push(i)
            }
            pageNumber.push("...");
            for(var i=data.pages-firstShowA+1;i<=data.pages;i++){
                pageNumber.push(i)
            }
        }
        //当 当前页为等于第 4 页
        else if((data.pageNum == 4)){//保留开始前五页  ...  接上最后两页
            for(var i=1;i<=firstShowP+1;i++){
                pageNumber.push(i)
            }
            pageNumber.push("...");
            for(var i=data.pages-firstShowA+2;i<=data.pages;i++){
                pageNumber.push(i)
            }
        }
        //当 当前页为倒数第 4页
        else if((data.pageNum == data.pages-3)){//保留开始前两页  ...  接上最后五页
            for(var i=1;i<=firstShowP-1;i++){
                pageNumber.push(i)
            }
            pageNumber.push("...");
            for(var i=data.pages-firstShowA;i<=data.pages;i++){
                pageNumber.push(i)
            }
        }
        //当 当前页为大于 4 页，或当前页小于倒数第 4页
        else if((data.pageNum > 4) || (data.pageNum < data.pages-3)){//前面后面添加 ...  显示当前页的前后两页
            pageNumber.push(1);
            pageNumber.push("...");
            for(var i=data.pageNum-2;i<=data.pageNum+2;i++){
                pageNumber.push(i)
            }
            pageNumber.push("...");
            pageNumber.push(data.pages);
        }
        $.extend(true,data,{"pageSeries": pageNumber});   //添加页码页数
    }
}
function paginationShow(that,d){
    //d格式：{"pageNum":1,"pageSize":3,"pages":12,"total":36,"list":[]}
    var data = {
        "pageNum":1,
        "pageSize":10,
        "pages":1,
        "total":0,
        "perNum": [100, 50, 20, 10]    //添加每页显示条数选择
    };
    $.extend(true,data,d);
    pageSeries(data);
    that.pageSeries=data.pageSeries;
    setTimeout(function(){
        var showPageItem = ".h-pageNum ul li[data-page="+that.pageNum+"]";
        $(showPageItem).addClass("active").siblings().removeClass("active");
        $(".h-pageNum ul li[data-page='...']").addClass("h-ellipsis");
    },200);
    // $(this.$refs.page[this.pageNum - 1]).addClass("active").siblings().removeClass("active");
    var showPageItem = ".h-pageNum ul li[data-page="+that.pageNum+"]";
    $(showPageItem).addClass("active").siblings().removeClass("active");
    $(".h-pageNum ul li[data-page='...']").addClass("h-ellipsis");
}
//分页页码操作
function paginationClick(getDataFun,getDFData){
    pagination.on("click",".h-eachShow li",function(){
        var pageSize = $(this).attr("data-per-num");
        $(this).parents(".h-eachShow").find("p").html(pageSize);
        $.extend(true,getDFData, {pageIndex:1, pageSize:pageSize});//初始化页码为1，发送指定每页显示数量
        getDataFun(getDFData);
    });
    pagination.on("click",".h-pageNum i",function(){
        var pageIndex = $(this).parents(".h-pageNum").find("li.active").attr("data-page");
        var lastPage = $(".h-pageNum ul li:last-child").attr("data-page");
        var handleIndex = $(this).attr("data-page");
        if(handleIndex == "prev"){                   //上一页
            if(pageIndex == 1){
                layer.msg("当前已经是第一页");
                return false;
            }
            pageIndex--;
        }else if(handleIndex == "next"){             //下一页
            if(pageIndex == lastPage){
                layer.msg("当前已经是最后一页");
                return false;
            }
            pageIndex++;
        }
        $.extend(true,getDFData, {pageIndex:pageIndex});//发送指定每页显示数量
        getDataFun(getDFData);
    });
    pagination.on("click",".h-pageNum li",function(){
        var pageIndex = $(this).attr("data-page");
        if(pageIndex == "..."){return false;}
        $.extend(true,getDFData, {pageIndex:pageIndex});
        getDataFun(getDFData);
    });
}
//todo:分页引用指南
/* 在页面引入 pagination.jsp 此文件为分页模板，以及引入 pagination,js
 * 在页面需要插入页码的地方放置容器 <div class="h-showPage" id="pagination">
 * getDataFun 为获取数据的函数，该函数为 ajax 请求，向后台请求数据，当获取数据成功后需要调用 paginationShow(d) 函数用于显示页码
 * 传入数据 d 的格式为 {"pageNum":1,"pageSize":10,"pages":12,"total":36,}必须要有内部四个字段
 * getDFData 为函数ajax 请求，向后台发送的对应数据
 * getDFData 内数据要有{pageIndex:1, pageSize:1}分别为当前页码，每页显示项目条数，
 * 如果与后台接口不对，需改动该js文件paginationClick(getDataFun,getDFData)内的内容
 * 在文件的最后引用函数 paginationClick(getDataFun,getDFData)
 *
 * */
