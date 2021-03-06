$(function () {
    //初始化分类菜单
    var clas = new Array();
    clas[1] = "哲学";
    clas[2] = "经济";
    clas[3] = "法学";
    clas[4] = "教育学";
    clas[5] = "文学";
    clas[6] = "历史学";
    clas[7] = "理学";
    clas[8] = "工学";
    clas[9] = "农学";
    clas[10] = "医学";
    clas[11] = "管理学";
    clas[12] = "艺术";
    clas[13] = "农林牧渔";
    clas[14] = "资源环境与安全";
    clas[15] = "能源动力与材料";
    clas[16] = "土木建筑";
    clas[17] = "水利";
    clas[18] = "装备制造";
    clas[19] = "生物与化工";
    clas[20] = "轻工纺织";
    clas[21] = "食品药品与粮食";
    clas[22] = "交通运输";
    clas[23] = "电子与信息";
    clas[24] = "医药卫生";
    clas[25] = "财经贸易";
    clas[26] = "旅游";
    clas[27] = "文化艺术";
    clas[28] = "新闻传播";
    clas[29] = "教育与体育";
    clas[30] = "公安与司法";
    clas[31] = "公共管理与服务";
    for (var i = 1; i < clas.length; i++) {
        $("#selectClass").append("<option>"+clas[i]+"</option>");
    }
    var spec = new Array();
    spec[1] = new Array("哲学类");
    spec[2] = new Array("经济学类","财政学类","金融学类","经济与贸易类");
    spec[3] = new Array("法学类","政治学类","社会学类","人类学","民族学类","马克思主义理论类","公安学类");
    spec[4] = new Array("教育学类","体育学类");
    spec[5] = new Array("中国语言文学类", "外国语言文学类", "新闻传播学类");
    spec[6] = new Array("历史学类");
    spec[7] = new Array("数学类", "物理学类", "化学类", "天文学类", "地理科学类", "大气科学类", "海洋科学类", "地球物理学类", "地质学类", "生物科学类", "心理学类", "统计学类");
    spec[8] = new Array("力学类", "机械类", "仪器类", "材料类", "能源动力类", "电气类", "电子信息类", "自动化类", "计算机类", "土木类", "水利类", "测绘类", "化工与制药类", "地质类","矿业类","纺织类","轻工类","交通运输类","海洋工程类","航空航天类","兵器类","核工程类","农业工程类","林业工程类","环境科学与工程类","生物医学工程类","食品科学与工程类","建筑类","安全科学与工程类","生物工程类","公安技术类");
    spec[9] = new Array("植物生产类", "自然保护与生态环境类", "动物生产类市", "动物医学类", "林学类", "水产类", "草学类");
    spec[10] = new Array("基础医学类", "临床医学类", "口腔医学类", "公共卫生与预防医学类", "中医学类", "中西医结合类", "药学类", "中药学类", "法医学类", "医学技术类", "护理学类");
    spec[11] = new Array("管理科学与工程类", "商管理类", "农业经济管理类", "公共管理类", "图书情报与档案管理类", "物流管理与工程类", "工业工程类", "电子商务类", "旅游管理类");
    spec[12] = new Array("艺术学理论类", "音乐与舞蹈学类", "戏剧与影视学类", "美术学类", "设计学类");
    spec[13] = new Array("农业类", "林业类", "畜牧业类", "渔业类");
    spec[14] = new Array("资源勘探类", "地质类", "测绘地理信息类", "石油与天然气类", "煤炭类", "气象类", "环境保护类", "安全类");
    spec[15] = new Array("电力技术类", "热能与发电工程类", "新能源发电工程类", "黑色金属材料类", "有色金属材料类", "非金属材料类", "建筑材料类");
    spec[16] = new Array("建筑设计类", "城乡规划与管理类", "土建施工类", "建筑设备类", "建筑工程管理类", "市政工程类", "房地产类");
    spec[17] = new Array("水文水资源类", "水利工程与管理类", "水利水电设备类", "水土保持与水环境类");
    spec[18] = new Array("机械设计制造类", "机电设备类", "自动化类", "轨道装备类", "船舶与海洋工程装备类", "航空装备类", "汽车制造类");
    spec[19] = new Array("生物技术类", "化工技术类");
    spec[20] = new Array("轻化工类", "包装类", "印刷类", "织服纺装类");
    spec[21] = new Array("食品类", "药品与医疗器械类", "粮食类");
    spec[22] = new Array("铁道运输类", "道路运输类", "水上运输类", "航空运输类", "城市轨道交通类", "邮政类");
    spec[23] = new Array("电子信息类", "计算机类", "通信类", "集成电路类");
    spec[24] = new Array("护理类", "药学类", "中医药类", "医学技术类", "康复治疗类", "公共卫生与卫生管理类", "健康管理与促进类", "眼视光类");
    spec[25] = new Array("财政税务类", "金融类", "财务会计类", "经济贸易类", "工商管理类", "电子商务类", "物流类");
    spec[26] = new Array("旅游类", "餐饮类");
    spec[27] = new Array("艺术设计类", "表演艺术类", "文化服务类");
    spec[28] = new Array("新闻出版类", "广播影视类");
    spec[29] = new Array("教育类", "语言类", "体育类");
    spec[30] = new Array("公安技术类", "侦察类", "法律实务类", "法律执行类", "司法技术类","安全防范类");
    spec[31] = new Array("公共事业类", "公共管理类", "公共服务类");
    $("#selectClass").on("change",function(){
        $("#selectSpec").children("option").detach();
        $("#selectSpec").append("<option>请选择专业类</option>");
        var indexClass = $("#selectClass>option:selected").index();//取得选中的想的数组下标0
        if (indexClass > 0) {
            for (var i = 0; i < spec[indexClass].length; i++) {
                $("#selectSpec").append("<option>" + spec[indexClass][i] + "</option>");
            }
            console.log( $("#selectClass>option:selected").val() + $("#selectSpec>option:first").val() );
        }else {
            console.log( "请选择分类" );
        }
    });
    $("#selectSpec").on("change",function(){
        console.log( $("#selectClass>option:selected").val() + $("#selectSpec>option:selected").val() );
    });
});
