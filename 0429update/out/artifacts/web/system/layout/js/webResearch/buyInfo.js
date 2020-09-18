function checkIsInv(value){
	if(value == '03'){//03不可以选择
		$("#quotAffirmDate").removeAttr("disabled");
		$("#quotAffirmDate").addClass("date validate[required]");
		$("#quotAffirmDate").render();
		$("#invCycleEndDate").removeAttr("disabled");
		$("#invCycleEndDate").addClass("date validate[required]");
		$("#invCycleEndDate").render();
		$("#firstInvCycleEndDate").removeAttr("disabled");
		$("#firstInvCycleEndDate").addClass("date validate[required]");
		$("#firstInvCycleEndDate").render();
	}else{
		$("#quotAffirmDate").attr("disabled","disabled");
		$("#quotAffirmDate").removeAttr("class");
		$("#quotAffirmDate").render();
		$("#invCycleEndDate").attr("disabled","disabled");
		$("#invCycleEndDate").removeAttr("class");
		$("#invCycleEndDate").render();
		$("#firstInvCycleEndDate").attr("disabled","disabled");
		$("#firstInvCycleEndDate").removeAttr("class");
		$("#firstInvCycleEndDate").render();
	}
}