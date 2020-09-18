<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/common/common_edit.jsp"%>
<!--框架必需end-->
<script src="<%=path%>/libs/thirdparty/highcharts/js/highcharts.js"></script>
<script>
$(function () {
	//数量图
    $('#amount').highcharts({
            chart: {
                type: 'column',
				zoomType: 'x'
            },
            title: {
                text: '${amount.title}'
            },
            xAxis: {
                categories: ${amount.month}
            },
            yAxis: {
                min: 0,
                title: {
                    text: '到期产品数量 (只)'
                }
            },
            tooltip: {
                headerFormat: '<span>{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y} 只</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.1,
                    borderWidth: 0
                }
            },
            series: ${amount.amountList}
        });
    
    	//规模图
    $('#scale').highcharts({
        chart: {
            type: 'column',
			 zoomType: 'x'
        },
        title: {
            text: '${scale.title}'
        },
        xAxis: {
            categories: ${scale.month}
        },
        yAxis: {
            min: 0,
            title: {
                text: '到期产品规模 (亿元)'
            }
        },
        tooltip: {
            headerFormat: '<span>{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.2f} 亿元</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.1,
                borderWidth: 0
            }
        },
        series: ${scale.scaleList}
    });
    });
</script>
</head>
<body>
	<table style="height: 100%;width: 100%">
		<tr>
			<td height="20px"></td>
		</tr>
		<tr>
			<td>
				<div id="amount" style="min-width: 310px; height: 500px; margin: 0 auto"></div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="scale" style="min-width: 310px; height: 500px; margin: 0 auto"></div>
			</td>
		</tr>
	</table>
</body>
</html>