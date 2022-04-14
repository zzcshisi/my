$(function () {
    //初始化省份菜单
    var field = new Array();
    field[1] = "软件开发";
    field[2] = "通信/硬件";
    field[3] = "机械/制造";
    field[4] = "产品/项目/运营";
    field[5] = "金融";
    field[6] = "市场营销";
    field[7] = "咨询/管理";
    field[8] = "人力/财务/行政";
    field[9] = "教育/科研";
    field[10] = "供应链/物流";
    field[11] = "视觉/交互/设计";
    field[12] = "房地产/建筑";
    field[13] = "生物医疗";
    
    for (var i = 1; i < field.length; i++) {
        $("#selectField").append("<option>"+field[i]+"</option>");
    }
    var post = new Array();
    post[1] = new Array("后端开发","前端开发","客户端开发","测试","数据","运维/技术支持","人工智能/算法","研发工程师","技术支持");
    post[2] = new Array("硬件工程师","电子/半导体","通信");
    post[3] = new Array("机械","汽车制造","化工");
    post[4] = new Array("产品","运营","游戏策划","项目","用户研究");
    post[5] = new Array("风控", "证券类", "投融资", "银行");
    post[6] = new Array("市场/营销", "销售/商务", "公关媒介", "客服");
    post[7] = new Array("管理", "管理培训生");
    post[8] = new Array("财务审计", "人力资源", "行政管理", "法务");
    post[9] = new Array("教育产品研发", "教育", "翻译相关", "科研");
    post[10] = new Array("供应链", "物流");
    post[11] = new Array("交互/设计", "动画/特效", "工业类设计");
    post[12] = new Array("房地产", "设计装修与市政建设");
    post[13] = new Array("生物制药");
    
    $("#selectField").on("change",function(){
        $("#selectPost").children("option").detach();
        $("#selectPost").append("<option>请选择岗位</option>");
        var indexField = $("#selectField>option:selected").index();//取得选中的想的数组下标0
        if (indexField > 0) {
            for (var i = 0; i < post[indexField].length; i++) {
                $("#selectPost").append("<option>" + post[indexField][i] + "</option>");
            }
            console.log( $("#selectField>option:selected").val() + $("#selectPost>option:first").val() );
        }else {
            console.log( "请选择领域" );
        }
    });
    $("#selectPost").on("change",function(){
        console.log( $("#selectField>option:selected").val() + $("#selectPost>option:selected").val() );
    });
});
