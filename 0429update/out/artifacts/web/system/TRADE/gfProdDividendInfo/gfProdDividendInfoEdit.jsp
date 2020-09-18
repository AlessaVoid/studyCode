<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">

		function fmoney(obj, bit) {
			//将金额转换至money格式，增加千分符【，】
			temp = obj.value;
			if (obj.value != "" && !isNaN(obj.value)) {
				var money = obj.value;
				money = (zh(money, bit));
				obj.value = money;
			} else if (obj.value == "") {
				obj.value = "";
			} else {
				obj.value = (zh("0", bit));
			}

			if (obj.value == "") {
				obj.value = "0.0000";
			}
		}
		function rmoney(s) {
			//将money格式转换至字符类型,去掉千分符如果值为0或者非法  值为空
			if (s.value != "" && s.value != " " && s.value != NaN) {
				var index = parseFloat(s.value.replace(/[^\d\.-]/g, ""));
				if (!isNaN(index)) {
					s.value = index;
				} else {
					s.value = '0.0000';
				}
				if (s.value == '0' || s.value == '0.0000') {
					s.value = "0";
				}
			}
		}

		function rmoney1(s) {
				//将money格式转换至字符类型,去掉千分符如果值非法 则转换成0
			if (s.value != "" && s.value != " " && s.value != NaN) {
				var index = parseFloat(s.value.replace(/[^\d\.-]/g, ""));
				if (!isNaN(index)) {
					s.value = index;
				} else {
					s.value = '0.0000';
				}
				if (s.value == '0' || s.value == '0.0000') {
					s.value = "";
				}
			}
		}
		function rmoneyStr(s) {
			if (s != "" && s != " " && s != NaN) {
				var index = parseFloat(s.replace(/[^\d\.-]/g, ""));
				if (!isNaN(index)) {
					return index;
				} else {
					return 0;
				}
			}
		}
		function zh(s, n) {
			var f = false;
			if (s < 0) {
				f = true;
			}
			if (f) {
				s = s * -1;
			}
			n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
			t = "";
			for ( var i = 0; i < l.length; i++) {
				t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			if (f) {
				return "-" + (t.split("").reverse().join("") + "." + r);
			} else {
				return (t.split("").reverse().join("") + "." + r);
			}
		}

		function rallmoney() {
			$("input[class*='moneyOne']").each(function() {
				rmoney(this);
			});
		}

		function fallmoney() {
			$("input[class*='moneyOne']").css("text-align", "right");

			$("input[class*='moneyOne']").each(function() {
				var bit = getBit(this);
				fmoney(this,bit);
			});

			$("input[class*='moneyOne']").bind("blur", function() {
				var bit = getBit(this);
				fmoney(this,bit);
			}).bind("click", function() {
		  		rmoney1(this);
		  		var e = event.srcElement; 
		     	var r = e.createTextRange(); 
		     r.moveStart('character',e.value.length); 
		     r.collapse(true); 
		     r.select();
			});
		}

		function getBit(obj){
			var bit = 4;
			var array = $(obj).attr("class").split(" ");
			$.each( array, function(index, value){
				var begin = value.indexOf("-") + 1;
				if(value.indexOf("moneyOne")==0 && begin != 0){
					bit = this.substring(begin,value.length);
					}
				});
			return bit;
		}

		$(function() {
			fallmoney();
		});
		</script>
	</head>
	<body>
		<form id="form1">
		<div class="basicTabModern">
				<div name="修改产品净值" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
						<tr>
							<td align="right">
								产品代码:
							</td>
							<td>
							${entity.prodCode}
								<input type="hidden" name="prodCode" value="${entity.prodCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								除权日:
							</td>
							<td>
								${entity.exDividDate}
								<input type="hidden" name="exDividDate" id="exDividDate" value="${entity.exDividDate}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								权益登记日:
							</td>
							<td>
								${entity.registrationDate}
							 <input type="hidden" name="registrationDate" value="${entity.registrationDate}" /> 
							</td>
						</tr>
						<tr>
							<td align="right">
								每份分红金额（元）:
							</td>
							<td>
								<input type="text" name="dividendPerUnit" value="${entity.dividendPerUnit}" class="validate[required],moneyOne" maxlength="12"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<dic:select dicType="DIVIDEND_STATUS" name = "status" dicNo="${entity.status}" tgClass="validate[required]"></dic:select>
							<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" class="firstButton" />
								<button type="button" class="downButton" /></td>
							</tr>
    				</table>
    			</div>
    			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="理财产品分红参数信息维护" /> 
						<input type="hidden" name="tradeCode" value="TRADE-08-02" />
						<table class="tableStyle" width="97%" mode="list" formMode="line">
							<tr>
								<td width="38%">操作员代码：</td>
								<td>
									${sessionScope.sessionUser.opercode} 
									<input type="hidden" name="appUser" value="${sessionScope.sessionUser.opercode}" />
								</td>
							</tr>
							<tr>
								<td width="38%">操作员姓名：</td>
								<td>${sessionScope.sessionUser.opername} <input
									type="hidden" name="appOperName"
									value="${sessionScope.sessionUser.opername}" /></td>
							</tr>
							<tr>
								<td>所属机构代码：</td>
								<td>${sessionScope.sessionUser.organcode} <input
									type="hidden" name="appOrganCode"
									value="${sessionScope.sessionUser.organcode}" /></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td>${sessionScope.sessionOrgan.organname} <input
									type="hidden" name="appOrganName"
									value="${sessionScope.sessionOrgan.organname}" /></td>
							</tr>
							<tr>
								<td>操作员角色：</td>
								<td>${sessionScope.sessionUserRole} <input type="hidden"
									name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
							</tr>
							<tr>
								<td>复核人员：</td>
								<td><select selWidth="127" 
									id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TRADE-08-05">
								</select> <span class="star">*</span> 
								<input type="hidden" name="repUserName" id="repUserName"/></td>
							</tr>
							
							<tr>
								<td>所属机构代码：</td>
								<td><span id="repUserOrganCode1"></span> <input type="hidden"
									id="repUserOrganCode" name="repUserOrganCode"/></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td><span id="repUserOrganName1"></span> <input type="hidden"
									id="repUserOrganName" name="repUserOrganName"/></td>
							</tr>

							<tr>
								<td>复核人员角色：</td>
								<td><span id="repRoleName1"></span> <input type="hidden"
									id="repRoleName" name="repRoleName"/></td>
							</tr>
							<tr>
								<td>备注说明：</td>
								<td><textarea class="textarea"
										style="width: 80%; height: 80px;" name="appRemark"></textarea>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div align="center">
										<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdDividendInfo/update.htm')" class="saveButton"/>
										<button type="button" onclick="return cancel()" class="cancelButton" />	
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
    		</div>
	   	</form>
	</body>
</html>