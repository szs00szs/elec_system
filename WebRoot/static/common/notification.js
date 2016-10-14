$(function(){
	
function getLatestStatus(){
	$.ajax({
		type:'POST',
		url:'admin/notification/getLatestStatus',
		dataType: "json", 
		success:function(data){
			if(data.msg.length != 0) {
				notifyMe(data);
			}
			
		},
		error:function(html){
		}
	});
}

 setInterval(getLatestStatus,600000);// 注意函数名没有引号和括弧！ 
// 使用setInterval("show()",3000);会报“缺少对象” 
});

document.addEventListener('DOMContentLoaded', function () {
	  if (Notification.permission !== "granted")
	    Notification.requestPermission();
});

function notifyMe(data) {
  if (!Notification) {
    alert('Desktop notifications not available in your browser. Try Chromium.'); 
    return;
  }

  if (Notification.permission !== "granted")
    Notification.requestPermission();
  else {
    var notification = new Notification('消息提醒-可可家里后台管理', {
      icon: 'static/img/notify.png',
      body: data.msg,
    });
    
    notification.onclick = function () {
    	if(data.msg.indexOf("车主") > 0) {
    		window.open("admin/driverauth/list", "_blank");      
    	} else if(data.msg.indexOf("分期") >= 0){
    		window.open("admin/padinstallment/list", "_blank");      
    	}  else if(data.msg.indexOf("提油") >= 0){
    		window.open("admin/oilcardexchange/list", "_blank");      
    	}  else if(data.msg.indexOf("提现") >= 0){
    		window.open("admin/withdraw/list", "_blank");      
    	}  else if(data.msg.indexOf("留言") >= 0){
    		window.open("admin/feedback/list", "_blank");      
    	}    
    	notification.close();
    };
  }
}

