<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/common/common_edit.jsp"%>
<!--框架必需end-->
<script src="<%=path%>/libs/thirdparty/highcharts/js/highcharts.js"></script>
<script>
    var chart;
    $(document).ready(function () {
    	//数量期限图
        $('#amount').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '${amount.title}'
            },
            tooltip: {
        	    pointFormat: '{series.name} <b>{point.y} 只</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
	                },
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: '数量：',
                data: ${amount.data}
            }]
        });
    	
    	//规模期限图
        $('#scale').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '${scale.title}'
            },
            tooltip: {
        	    pointFormat: '{series.name} <b>{point.y} 亿元</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
	                },
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: '规模：',
                data: ${scale.data}
            }]
        });
    });
</script>
</head>
<body>
	<table style="height: 100%;width: 100%">
		<tr>
			<td height="10px"></td>
		</tr>
		<tr>
			<td width="50%" height="100%">
				<div id="amount" style="min-width: 300px; height: 500px; margin: 0 auto"></div>
			</td>
			<td width="50%" height="100%">
				<div id="scale" style="min-width: 300px; height: 500px; margin: 0 auto"></div>
			</td>
		</tr>
	</table>
</body>
</html>