<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>任意聊</title>

    <style>
        html {
            background-color: #D9D9D9;
        }
    </style>
</head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>任意聊</title>


<link rel="stylesheet" href="/css/layim.css">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/layui.mobile.css">
<body>


<div class="tab-frame " id="start_tab">
    <div class="top zwim-title" style="background: #36373C">
        <p>任意聊</p>
    </div>
    <div class="zwim-tab-page zwim-list " index=0 style="background: white">
        <li class="data_row" winTitle="官方大哥">
            <div><img src="//tva1.sinaimg.cn/crop.0.0.180.180.180/7fde8b93jw1e8qgp5bmzyj2050050aa8.jpg"></div>
            <span>官方大哥</span>
            <p>我现在有事不在，一会再和您联系。</p><span class="layim-msg-status">new</span>
        </li>
        <#--<li class="data_row">-->
            <#--<div><img src="http://tp2.sinaimg.cn/5488749285/50/5719808192/1"></div>-->
            <#--<span>张三</span>-->
            <#--<p>一起王者。</p><span class="layim-msg-status" style="display: inline">new</span>-->
        <#--</li>-->
    </div>


    <#--面板2-->

    <div class="zwim-tab-page hide" index=1>

        <div class="layui-layim">

            <div class="layim-tab-content layui-show">
                <ul class="layim-list-top">
                    <#--<li ><i class="layui-icon"></i>新的朋友(开发中)<i class="layim-new" id="LAY_layimNewFriend"></i>-->
                    <#--</li>-->
                    <li id=che_dan winTitle="全民尬聊"><i class="layui-icon"></i>全民尬聊<i class="layim-new" id="LAY_layimNewGroup"></i>
                    </li>
                </ul>
                <ul class="layim-list-friend">
                    <li>
                        <h5  lay-type="false"><i class="layui-icon"></i><span>所有在线用户(开发中)</span></h5>
                        <ul class="layui-layim-list">
                            <li  data-type="friend" data-index="0" class="layim-friend100001 ">
                                <div>
                                    <img src="//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                                </div><span>贤心</span>
                                <p>这些都是测试数据，实际使用请严格按照该格式返回</p><span class="layim-msg-status">new</span>
                            </li>
                            <li  data-type="friend" data-index="0" class="layim-friend100001222 ">
                                <div>
                                    <img src="//tva4.sinaimg.cn/crop.0.1.1125.1125.180/475bb144jw8f9nwebnuhkj20v90vbwh9.jpg">
                                </div><span>刘涛tamia</span>
                                <p>如约而至，不负姊妹欢乐颂</p><span class="layim-msg-status">new</span>
                            </li>
                        </ul>
                    </li>


                </ul>
            </div>

        </div>
    </div>
    <#--面板3-->
    <div class="zwim-tab-page hide" index="2">
        <div class="layui-layim">

            <div class="layim-tab-content layui-show">
                <ul class="layim-list-top">
                    <li  lay-filter="find"><i class="layui-icon "></i>全民坦克(构思中)<i class="layim-new layui-show" id="LAY_layimNewfind"></i>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <ul class="zwim-tab" style="overflow-x: hidden;overflow-y: auto;display: flex">
        <li title="消息" index=0 class="zwim-this"><i class="zwim-icon "></i><span>消息</span><i class="layim-new layui-show"
                                                                                              id="LAY_layimNewMsg"></i></li>
        <li title="去聊天" index=1><i class="zwim-icon"></i><span>去聊天</span><i class="layim-new" id="LAY_layimNewList"></i>
        </li>
        <li title="更多" index=2><i class="zwim-icon"></i><span>更多</span><i class="layim-new layui-show"
                                                                           id="LAY_layimNewMore"></i></li
    </ul>
</div>






</body>
</html>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"/></script>
<script src="js/laytpl.js"/></script>
<script src="js/layer/layer.min.js"></script>
<script src="js/zwcommon.js"></script>





<script id="winTempl" type="text/html">
    <div class="tab-frame layui-m-anim-left " id="{{d.newWinId}}">
        <div class="top zwim-title" style="background: #36373C;">
           <p class="back"><i class="zwim-icon layim-chat-back" ></i>{{d.newWinTitle}}</p>

        </div>
        {{d.innerHtml}}
    </div>
</script>





<script id="quan_min_ga_liao_temp" type="text/html">
    <div style="    position: absolute;top: 0px;right: 46px;color: #fff;font-size: 15px;height: 50px;line-height: 50px;"><span>当前在线人数:<span style="font-size: 20px" id="lineNumPeople"></span>人</span></div>
    <div class="layui-unselect layim-content">
        <div class="layim-chat layim-chat-group">
            <div class="layim-chat-main">
                <ul>


                </ul>
            </div>
            <div class="layim-chat-footer">
                <div class="layim-chat-send">
                    <input type="text" autocomplete="off" class="send_input" maxlength="400">
                    <button class="layim-send layui-disabled sendbtn" >发送</button>
                </div>
                <#--<div class="layim-chat-tool" data-json="%7B%22groupname%22%3A%22%E5%89%8D%E7%AB%AF%E7%BE%A4%22%2C%22id%22%3A%22101%22%2C%22avatar%22%3A%22http%3A%2F%2Ftp2.sinaimg.cn%2F2211874245%2F180%2F40050524279%2F0%22%2C%22name%22%3A%22%E5%89%8D%E7%AB%AF%E7%BE%A4%22%2C%22type%22%3A%22group%22%7D"><span class="layui-icon layim-tool-face" title="选择表情" ></span><span class="layui-icon layim-tool-image" title="上传图片" layim-event="image"><input type="file" name="file" accept="image/*"></span><span class="layui-icon layim-tool-image"-->
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        <#--title="发送文件"  data-type="file"><input type="file" name="file"></span>-->
                <#--</div>-->
            </div>
        </div>
    </div>
</script>

<script id="galiaoRowTemp" type="text/html">
    <li class="layim-chat-system"><span>{{getDateTime(d.quan_min_ga_liao.createTime)}}</span>
    </li>
    <li class="layim-chat-li  {{g_appuser&&d.param.fromUserId==g_appuser.id?'layim-chat-mine':''}} ">
            <div class="layim-chat-user">
            <img src="/img/head/{{d.param.head}}.jpg"><cite>{{d.param.nickName}}</cite>
            </div>
            <div class="layim-chat-text">{{d.quan_min_ga_liao.content}}</div>
    </li>
</script>



<!--界面控制-->
<script>

    var winindex=1;
    //tab按钮单击事件
    $('.zwim-tab li').click(function () {
        var index = $(this).attr('index');
        var currtab = $(this);
        $('.zwim-tab-page').each(function () {
            var page_inde = $(this).attr('index');
            if (index == page_inde) {
                $(this).show();
            } else {
                $(this).hide();
            }
        })


        $('.zwim-tab li').each(function () {
            if ($(this).attr('index') == index) {
                $(this).addClass('zwim-this')
            } else {
                $(this).removeClass('zwim-this')
            }
        })


    })

    //从历史消息进入
    $(document).on('click','.data_row',function () {
        layer.msg('开发中...')
        return
        var winData={}
        createWin('sendMsg','start_tab',$(this).attr('winTitle'),winData);
    })


    $(document).on('click','.back',function () {
        closeWin();
    })

    //一起扯淡
    $(document).on('click','#che_dan',function () {
        var winData={}
        createWin('quan_min_ga_liao_temp','start_tab',$(this).attr('winTitle'),winData,function () {
            //var user=g_appuser||getuserInfo();
            send(encode('get_line_num',{}));
        });
    })

    //创建窗口  newWinTempl:新窗口模板id,closeWinId:关闭窗口id,winData:新窗口相关数据,

    function createWin(newWinTempl,closeWinId,newWinTitle,winData,callBack) {
        winindex++;
        var innerHtml= laytpl($("#"+newWinTempl).html()).render({});
        winData.innerHtml=innerHtml;
        winData.newWinId='new_tab'+winindex;
        winData.closeWinId=closeWinId;
        winData.newWinTitle=newWinTitle;
        var html=   laytpl($("#winTempl").html()).render(winData);
        $('body').append(html)
        $('#'+closeWinId).addClass('layui-m-anim-right').addClass('layui-m-anim-lout')
        if (callBack){
            callBack();
        }
        localStorage.setItem('winData',JSON.stringify(winData));
    }
    
    //关闭窗口
    function closeWin() {
        var winData=localStorage.getItem('winData');
        winData=JSON.parse(winData);
        $('#'+winData.newWinId).addClass('layui-m-anim-rout'); //新窗口id
        $('#'+winData.closeWinId).removeClass('layui-m-anim-lout'); //还原关闭窗口即上一个窗口

        setTimeout(function(){  $('#'+winData.newWinId).remove(); }, 1000);//延迟关闭窗口,为了特效显示平滑处理,

        if (winData.callBack){
            winData.callBack();
        }
    }
</script>




<#--websocket-->

<script type="text/javascript">

      var socket;
      if (!window.WebSocket) {
          window.WebSocket = window.MozWebSocket;
      }
      if (window.WebSocket) {
          socket = new WebSocket("ws://${wsUrl}:${wsPort}/websocket");
          socket.onmessage = function (event) {
              console.log(event);
              var result= event.data;
              console.log("result:"+result) ;
              receiveProc(event.data)
          };
          socket.onopen = function (event) {
              //$('#responseText').val("打开WebSocket服务正常，浏览器支持WebSocket!");
              console.log('连接正常');
          };
          socket.onclose = function (event) {
              console.log('WebSocket 关闭');
          };
      }
      else {
          alert("抱歉，您的浏览器不支持改功能");
      }

      function send(message) {
          if (!window.WebSocket) {
              return;
          }
          if (socket.readyState == WebSocket.OPEN) {
              socket.send(message);
          }
          else {
              layer.msg("与服务器没有连接成功,请刷新页面重新连接!");
          }
      }

      //序列化参数
      function encode(url,obj) {
          var data={};
          data.url=url;
          data.param=obj;
          return JSON.stringify(data);

      }
      
      //处理业务逻辑
    
        function receiveProc(result) {
            var result = JSON.parse(result);

            //失败直接弹出错误提示
            if (result && result.code == 1) {
                alert(result.msg)
                return;
            }

            var url = result.url;

            switch (url) {
                case "quan_min_ga_liao":
                    var data = result.data;
                    var html = laytpl($("#galiaoRowTemp").html()).render(data);
                    $('.layim-chat-main ul').append(html);
                    $(".layim-chat-main").scrollTop($(".layim-chat-main")[0].scrollHeight)
                    break;
                case "del_connect":
                case "new_connect":
                case "get_line_num":
                    $('#lineNumPeople').text(result.data);
                    break;

            }

        }

</script>





<#--公共业务逻辑-->
<script>

    //全局用户信息
    var g_appuser
    //获取用户信息
    function getuserInfo() {
        var appUser=localStorage.getItem('appUser');
        var uid;
        if (!appUser){
            uid=-999;
        }else {
            uid=JSON.parse(appUser).id;
        }
     

        $.ajax({
            method: "POST",
            url: 'appUser/info',
            dataType: "json",
            data: {id:uid },
            async:false,
            success: function (data, textStatus, jqXHR) {
                if (data.code == 0) {
                    appUser=data.data;
                    localStorage.setItem("appUser",JSON.stringify(appUser));
                } else {
                    layer.msg('获取用户信息失败')
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.msg("出错了！", "", "error");
            }
        });

        g_appuser=appUser;
        return  appUser;


    }
</script>



<#--业务逻辑-->
<script>
    $(document).on('input propertychange','.send_input',function () {
        if ($(this).val()){
            $(this).parent().find('.layim-send').removeClass('layui-disabled')
        }else {
            $(this).parent().find('.layim-send').addClass('layui-disabled')
        }
    })

    $(document).on('click','.send_input',function () {
        if ($(this).val()){
            $(this).parent().find('.layim-send').removeClass('layui-disabled')
        }else {
            $(this).parent().find('.layim-send').addClass('layui-disabled')
        }
    })

    $(document).on('click','.sendbtn',function () {

        //空串直接返回
        if ($(this).hasClass('layui-disabled')) {
            return
        }
        
        var user=g_appuser||getuserInfo();
        //发送,groupId为1是全服
        send(encode('quan_min_ga_liao',{groupId:1,content:$('.layim-chat-send input').val(),head:user.head,nickName:user.nick_name,fromUserId:user.id}));
        $('.layim-chat-send input').val('')
        $(this).addClass('layui-disabled');
        
    })

</script>



