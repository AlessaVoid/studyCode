<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>存续期参数</title> 
<script type="text/javascript">
    //数据表格初始化
    var durationGrid;
	function initComplete(){
  	    var year = $("#year").val();
        var month = $("#month").val();
        document.getElementById("year").value = year;
        document.getElementById("month").value = month;
        showCalender();
}
	 function showCalender(){
         var year = document.getElementById("year").value;
         var month = document.getElementById("month").value;
         var url = "<%=request.getContextPath()%>/conference_room_usageGTT.action?usage_date=";
         myCalender(year,month,"cla",url);
     }


function myCalender(year,month,tbId,url){
	//切换日期 删除日历表格中的数据
 	var oTable = document.getElementById(tbId);//取得table
  	var oTBody = oTable.tBodies[1];
 	var row_length = oTBody.rows.length;
  	for(var rows = 0 ; rows < row_length; rows ++){

      	oTBody.deleteRow(0);
  	}
    //切换日期 删除开放日详细表格的数据
 	var oTables = document.getElementById("proTop");//取得table
  	var oTBodys = oTables.tBodies[1];
 	var row_lengths = oTBodys.rows.length;
  	for(var rows = 0 ; rows < row_lengths; rows ++){
      	oTBodys.deleteRow(0);
  	}
  	var crrentDay = new Date();
  	crrentDay.setYear(year);
  	crrentDay.setMonth(month);
  	var maxIndex = new Date(crrentDay.getFullYear(),crrentDay.getMonth(),0).getDate();
  	var firstIndex = new Date(crrentDay.getFullYear(),crrentDay.getMonth()-1,1).getDay();
     var days = new Array(42);
  
  for(var d = 0 ; d < maxIndex ; d ++){
      days[firstIndex+d] = d+1;
  }
  
  for(var d = 0 ; d < 42 ; d ++){
      if(days[d] == null || days[d] == ""){
          days[d] = "&nbsp;";
      }
  }
  var arrProdCode =new Array(42);;
  $.ajax({
  	url:'<%=path%>/gfOpenDurationDate/getProdCode.htm',
  	async:false,
  	type:"post",
  	dataType:'json',
  	data:{"year":year,"month":month},
  	success:function(result){
  		 arrProdCode = result;
  	}
  });
  for(var j=0; j<6 ; j++) { 
     var newTr = document.createElement("TR");
     for(var i=j*7 ; i< (j+1)*7 ;i++) { 
         var newTd = document.createElement("TD");
         if(days[i] == "&nbsp;"){
             newTd.innerHTML = days[i];
         }else{
            //截取串有问题
            var prod = new Array(5);
            var prodIndex ="";
            for (var p = 0; p < arrProdCode.length; p++) {
         	 if(arrProdCode[p].selectDate==days[i]){
         		 prod=arrProdCode[p].prodCode;
         		 var prodShow;
         		 for (var int = 0; int < prod.length; int++) {
         			 if(prod[int]==null){
          				prodShow="--";
          			 }else{
          				prodShow=prod[int];
          			 }
         			 prodIndex +="<br/>"+prodShow;
				}
         	 }
   			
   		   } 
            if(month.length<2){
         	   month ="0"+month;
            }
            var day =days[i];
            if(day.toString().length<2){
         	   day ="0"+days[i];
            }
            newTd.innerHTML ="<a href='javaScript:void(0);' onclick='js_method("+year+month+day+")' id='selectdate'><font size=5>"+days[i]+"</font></a>"+prodIndex;
             
         }
         newTd.setAttribute("width","100px");
         newTd.setAttribute("height","80px");
         newTr.appendChild(newTd);
     }
     oTBody.appendChild(newTr); 
 }
  
 
}

//生成
function js_method(date){
	//生成数据之前，删除原有对应数据
	//var data = durationGrid.records;
	var $table ="";
	$.post("<%=path%>/gfOpenDurationDate/selectOpenProd.htm",{queryDate:date},function(result){
		var oTable = document.getElementById("proTop");//取得table
	  	var oTBody = oTable.tBodies[1];
	 	var row_length = oTBody.rows.length;
	  	for(var rows = 0 ; rows < row_length; rows ++){
	      	oTBody.deleteRow(0);
	  	}
		for (var j = 0; j < result.length; j++) {
			 var newTr = document.createElement("TR");
		     for(var i=0 ; i< 7 ;i++) { 
		         var newTd = document.createElement("TD");
		         if(i==0){
		        	 newTd.innerHTML =result[j].prodCode;
		         }else if(i==1){
		        	 var para =result[j].paraType;
		        	 if(para==1){
		        		 newTd.innerHTML="申购";
		        	 }else {
		        		 newTd.innerHTML="赎回";
		        	 }
		         }else if(i==2){
		        	 newTd.innerHTML =result[j].beginDate;
		         }else if(i==3){
		        	 newTd.innerHTML =result[j].endDate;
		         }else if(i==4){
		        	 newTd.innerHTML =result[j].limit;
		         }else if(i==5){
		        	 var peruseDate =result[j].peruseDate;
		        	 if(peruseDate=='' || peruseDate ==null){
		        		 newTd.innerHTML ="--";
		        	 }else{
		        		 newTd.innerHTML =peruseDate;
		        	 }
		         }else if(i==6){
		        	 var perValue =result[j].perValue;
		        	 if(perValue=='' || perValue ==null){
		        		 newTd.innerHTML ="--";
		        	 }else{
		        		 newTd.innerHTML =perValue;
		        	 }
		         }
		         newTd.setAttribute("width","100px");
		         newTd.setAttribute("height","10px");
		         newTr.appendChild(newTd);
		     }
		     oTBody.appendChild(newTr);
		}
	});
	
	
}


</script>
	</head>
	<body>
        <form id="form1">
            <table width="960" border="0" cellspacing="0" cellpadding="0"
                style="margin: 0 auto; margin-top: 5px">
                <tr>
                    <td valign="top" style="padding-right: 5px; padding-bottom: 10px">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"
                            style="margin-top: 0px">
                            <tr>
                                <td height="28" class="black12_18"
                                    <a
                                        href="javascript:window.location.href=">
                                        </a> > 开放日提醒查询
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"
                            style="border: 1px solid #e8e8e8; border-top: 0;">
                            <tr>
                                <td
                                    style="">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="right" valign="bottom" style="padding: 6px 10px">
                                                &nbsp;
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 15px; padding-top: 0">
                                    <table width="100%" border="1" cellspacing="0" cellpadding="0"
                                        class="black12_18 tab_line" id="cla">
                                        <tr>
                                            <td colspan="7" align="center" height="50">
                                            	<dic:select  dicType="YEAR" id="year" name="year" dicNo="${currentYear }" onchange="showCalender();"  ></dic:select>
                                                &nbsp;年&nbsp;
                                                <dic:select  dicType="MONTH" id="month"  name="month" dicNo="${currentMonth }" onchange="showCalender();"  ></dic:select>
                                                 &nbsp;月&nbsp;&nbsp;
                                                <!-- <input type="button" onclick="showCalender();" value="查 看" /> -->
                                               <!--  <input type="button" onclick="history.go(-1);" value="返回按会议室查询页面" /> -->
                                            </td>
                                        </tr>
                                        <tr>
                                            <th width="100px" height="60px">
                                                <font color="red" size="5">日</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font size="size="5">一</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font size="size="5">二</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font size="size="5">三</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font size="size="5">四</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font size="size="5">五</font>
                                            </th>
                                            <th width="100px" height="60px">
                                                <font color="red" size="5">六</font>
                                            </th>
                                        </tr>
                                        <TBODY align="center">
                                            
                                        </TBODY>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div id="durationParam">
                <table width="100%" border="1" cellspacing="0" cellpadding="0"
                                        class="black12_18 tab_line" id="proTop">
                                        <tr>
                                            <th width="100px" height="10px">
                                                	产品代码
                                            </th>
                                            <th width="100px" height="10px">
                                               		开放类型
                                            </th>
                                            <th width="100px" height="10px">
                                                	起始日期
                                            </th>
                                            <th width="100px" height="10px">
                                                	终止日期
                                            </th>
                                            <th width="100px" height="10px">
                                               	          开放期额度
                                            </th>
                                            <th width="100px" height="10px">
                                               		业绩启用日期
                                            </th>
                                            <th width="100px" height="10px">
                                               		业绩基准值(%)
                                            </th>
                                        </tr>
                                        <TBODY align="center">
                                            
                                        </TBODY>
                                    </table>
           </div>
        </form>
	</body>
</html>