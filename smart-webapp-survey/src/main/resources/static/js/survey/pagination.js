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