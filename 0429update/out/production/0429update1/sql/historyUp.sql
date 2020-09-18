/*********************20161123升级数据库变更******开始*********************/
--基本信息
alter table gf_prod_base_info add purchase_CHANNEL VARCHAR2(100);
  comment on column gf_prod_base_info.purchase_CHANNEL
  is '申购共享渠道||申购共享渠道';
  
  alter table gf_prod_base_info add purchase_PROV VARCHAR2(500);
  comment on column gf_prod_base_info.purchase_PROV
  is '申购共享省||申购共享省';
  
  alter table gf_prod_base_info add SHARE_CHANNEL VARCHAR2(100);
  comment on column gf_prod_base_info.SHARE_CHANNEL
  is '认购期共享渠道||认购期共享渠道';
  
  alter table gf_prod_base_info add SHARE_PROV VARCHAR2(500);
  comment on column gf_prod_base_info.SHARE_PROV
  is '认购期共享省||认购期共享省';

  alter table web_prod_base_info add purchase_CHANNEL VARCHAR2(100);
  comment on column web_prod_base_info.purchase_CHANNEL
  is '申购共享渠道||申购共享渠道';
  
  alter table web_prod_base_info add purchase_PROV VARCHAR2(500);
  comment on column web_prod_base_info.purchase_PROV
  is '申购共享省||申购共享省';  

  alter table web_prod_base_info add SHARE_CHANNEL VARCHAR2(100);
  comment on column web_prod_base_info.SHARE_CHANNEL
  is '认购期共享渠道||认购期共享渠道';
  
  alter table web_prod_base_info add SHARE_PROV VARCHAR2(500);
  comment on column web_prod_base_info.purchase_PROV
  is '认购期共享省||认购期共享省';  

--错误信息
insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'w592', '添加{0}失败，不符合项目投资流程', '99370000000', 'w592', '添加{0}失败，不符合项目投资流程', '20160721', '094940', '魏玉航');
--公告提示

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100504', 'PM-05-04', 'PM-05', 100504, '0', '{text : ''详细'', click : onInfo, iconClass : ''icon_list''}', '详细', '/libs/icons/list.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100503', 'PM-05-03', 'PM-05', 100503, '0', '{text : ''删除'', click : onDelete, iconClass : ''icon_delete''}', '删除', '/libs/icons/delete.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100502', 'PM-05-02', 'PM-05', 100502, '0', '{text : ''修改'', click : onUpdate, iconClass : ''icon_edit''}', '修改', '/libs/icons/edit.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100501', 'PM-05-01', 'PM-05', 100501, '0', '{text : ''新增'', click : onInsert, iconClass : ''icon_add''}', '新增', '/libs/icons/add.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100505', 'PM-05-05', 'PM-05', 100505, '0', '{text : ''启用'', click : onRefresh,iconClass : ''icon_refresh''}', '生效', '/libs/icons/refresh.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E90BE2E7DB5645D8A6355987983074BD', 'PM-05', 'PM', 100005, '0', '/publicPromptTable/listUI.htm', '公告提示管理', '/libs/icons/leaf.gif', '1', '1', '20160722', '162007', '谷立羊');

create table public_prompt_table    
{
  id  VARCHAR2(64) not null,
  content VARCHAR2(2000) not null,
  oper_code VARCHAR2(11) not null,
  oper_time varchar2(6) not null,
  oper_date varchar2(8) not null,
  use_status CHAR(1) not null
};
-- Add comments to the table 
comment on table public_prompt_table
  is '公共提示表||公共提示表';
-- Add comments to the columns 
comment on column public_prompt_table.id
  is 'id||id';
comment on column public_prompt_table.content
  is '内容||内容';
comment on column public_prompt_table.oper_code
  is '操作人||操作人';
comment on column public_prompt_table.oper_time
  is '操作时间||操作时间';
  comment on column public_prompt_table.oper_date
  is '操作日期||操作日期';
comment on column public_prompt_table.use_status
  is '状态||状态 1-启用，1-停用';
alter table public_prompt_table
  add constraint public_prompt_table primary key (id);
  --角色管理中权限配置
delete from web_menu_info  where menu_no='PM-02-05'

--申报未通过菜单
insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('B67A73D87E0C472EA4450EB691A32897', 'GF-05-08-01', 'GF-05-08', 200581, '0', '{text : ''重编辑'', click : onEdit, iconClass : ''icon_edit''}', '重编辑', '/libs/icons/edit.gif', '1', '2', '20160724', '181425', '魏玉航');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('6F328ABA5BFA43829F03C7361A8072DA', 'GF-05-08', 'GF-05', 200508, '0', '/webProdRegisterFail/listUI.htm', '产品申报未通过记录', '/libs/icons/leaf.gif', '1', '1', '20160725', '141006', '魏玉航');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('61A8E632A5CC47F69FFAD454C87B9840', 'GF-05-08-02', 'GF-05-08', 200582, '0', '{text : ''删除'', click : onDelete, iconClass : ''icon_delete''}', '删除', '/libs/icons/delete.gif', '1', '2', '20160724', '181725', '魏玉航');

--机构专户产品复制新增
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('95BA7D534D9744A78B0B9A91E7B705D3', 'GF-06-01-08', 'GF-06-01', 200618, '0', '{text : ''机构专户产品复制新增'', click : onCopy, iconClass : ''icon_add''}', '机构专户产品复制新增', '/libs/icons/add.gif', '1', '2', '20160725', '182251', '张兴帅');

--设计未启动流程删除
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('D6CED4AA4B97432EB43685EF05B7E749', 'GF-06-06-04', 'GF-06-06', 200674, '0', '{text : ''删除'', click : onDelete, iconClass : ''icon_delete''}', '删除', '/libs/icons/delete.gif', '1', '2', '20160722', '153415', '魏玉航');


--预研未启动添加删除功能
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('574', 'GF-05-07-04', 'GF-05-07', 200574, '0', '{text : ''删除'', click : onDelete, iconClass : ''icon_delete''}', '删除', '/libs/icons/delete.gif', '1', '2', '2', '2', '谷立羊');


--角色节点驳回控制表
update acticiti_role_config_new set up_task_id = 'usertask3' where task_id = 'usertask8'   and activiti_type = '02'；

--删除总行产品经理驳回控制条件
delete acticiti_role_config_new where task_id = 'usertask1'   and activiti_type = '02' and organ_level='0'；

--分行机构设计驳回
insert into acticiti_role_config_new (TASK_ID, UP_TASK_ID, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask18', 'startevent1', '1', '2', '11', '02');

--优化申报登记部门领导获取
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_REGISTER_LEADER', '部负门责人', '20070301740', '部负门责人', '步艳红', '部负门责人', 0, '参数初始导入', '1', '1', '20160314', '谷立羊');

--产品状态修改
update GF_DICT set dict_value_in='产品设计（分行）2' WHERE DICT_NO='D_PROD_STATUS' AND DICT_KEY_IN='12';
/*********************20161123升级数据库变更*******结束********************/

/*********************20161201升级数据库变更*******开始********************/
--gf_dict
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROMPT_INTERVAL', '公告提示数刷新时间(秒)', '300', '后台查询', '', '', 1, '', '1', '160713', '20160725', '谷立羊');

delete gf_dict where dict_no='D_PROP_CODE';

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '1', '私人银行', '', '', 1, '私人银行', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '2', '高净值', '', '', 2, '高净值', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '3', '机构', '', '', 3, '机构', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '4', '同业', '', '', 4, '同业', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '5', '小企业', '', '', 5, '小企业', '1', '172339', '20160223', '99999901001');

--gf_prod_brand_info
alter table gf_prod_brand_info add year    varchar2(4) default '2016' not null
comment on column gf_prod_brand_info.year
  is '年份||年份';

alter table gf_prod_brand_info drop constraint PK_gf_prod_brand_info;
alter table gf_prod_brand_info
  add constraint PK_gf_prod_brand_info primary key (brand_code,year);
/*********************20161201升级数据库变更*******结束********************/

/*********************20161214升级数据库变更*******开始********************/
--销售通知待下发产品移除
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('201104', 'GF-11-04', 'GF-11', 201104, '0', '{text : ''产品移除复核'', click : onAudit, iconClass : ''icon_list''}', '产品移除复核', '/libs/icons/edit.gif', '1', '3', '2', '2', '2');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_STATUS', '产品审批状态', '28', '销售已取消发行', '', '', 28, '', '1', '165522', '20160717', '谷立羊');
--申报登记中文名
update gf_dict set dict_value_in='人民币' where dict_no='CURRENCY' and dict_value_in='人民币元';

/*********************20161214升级数据库变更*******结束********************/
/*********************20161228升级数据库变更*******开始********************/
--产品申报总期次
 alter table web_prod_asset_mutual_info add issue_num number(17);
  comment on column web_prod_asset_mutual_info.issue_num
  is '产品品牌总期次||产品品牌总期次'; 
  alter table gf_prod_asset_mutual_info add issue_num number(17);
  comment on column gf_prod_asset_mutual_info.issue_num
  is '产品品牌总期次||产品品牌总期次'; 
 --销售通知
 alter table GF_SALE_NOTIFY_INFO add  CONTENT_BYTE	CLOB; 
     comment on column GF_SALE_NOTIFY_INFO.CONTENT_BYTE
  is '通知内容||通知内容'; 
 --保本成本户
delete from gf_dict  where dict_no='COST_ASSET_TYPE_P';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'ZGJH', '直接指定公允价值计量金融资产-个人理财资金-成本-资管计划', '', '', 2, '个人产品成本户类型', '1', '114123', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'ZQ', '直接指定公允价值计量金融资产-个人理财资金-成本-债券', '', '', 4, '个人产品成本户类型', '1', '114208', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'CKL', '直接指定公允价值计量金融资产-个人理财资金-成本-存款', '', '', 5, '个人产品成本户类型', '1', '114227', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'SYPZ', '直接指定公允价值计量金融资产-个人理财资金-成本-收益凭证', '', '', 6, '个人产品成本户类型', '1', '114253', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'OTHER', '直接指定公允价值计量金融资产-个人理财资金-成本-其他', '', '', 7, '个人产品成本户类型', '1', '114309', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '个人保本产品成本户资产类型', 'XTJH', '直接指定公允价值计量金融资产-个人理财资金-成本-信托计划', '', '', 1, '个人产品成本户类型', '1', '114027', '20160720', '谷立羊');


delete from gf_dict  where dict_no='COST_ASSET_TYPE_O';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'OTHER', '直接指定公允价值计量金融资产-单位理财资金-成本-其他', '', '', 7, '个人产品成本户类型', '1', '114309', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'SYPZ', '直接指定公允价值计量金融资产-单位理财资金-成本-收益凭证', '', '', 6, '个人产品成本户类型', '1', '114253', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'CKL', '直接指定公允价值计量金融资产-单位理财资金-成本-存款', '', '', 5, '个人产品成本户类型', '1', '114227', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'ZQ', '直接指定公允价值计量金融资产-单位理财资金-成本-债券', '', '', 4, '个人产品成本户类型', '1', '114208', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'ZGJH', '直接指定公允价值计量金融资产-单位理财资金-成本-资管计划', '', '', 2, '个人产品成本户类型', '1', '114123', '20160720', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '单位保本产品成本户资产类型', 'XTJH', '直接指定公允价值计量金融资产-单位理财资金-成本-信托计划', '', '', 1, '个人产品成本户类型', '1', '114027', '20160720', '谷立羊');
  
/*********************20161228升级数据库变更*******结束********************/

--额度
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '分配方案', '0', '共享省，共享渠道', '', '', 0, '参数初始导入', '1', '1', '20161021', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '分配方案', '1', '共享省，不共享渠道', '', '', 1, '参数初始导入', '1', '1', '20161021', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '分配方案', '2', '不共享省，共享渠道', '', '', 2, '参数初始导入', '1', '1', '20161021', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '分配方案', '3', '不共享省，不共享柜面，共享其他渠道', '', '', 3, '参数初始导入', '1', '1', '20161021', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '分配方案', '4', '不共享柜面（不共享省），共享其他渠道（同时共享省）', '', '', 4, '参数初始导入', '1', '1', '20161021', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_UP_LIMIT', '是否向上级取额度', '0', '否', '', '', 2, '参数初始导入', '1', '1', '20161026', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_UP_LIMIT', '是否向上级取额度', '1', '是', '', '', 1, '参数初始导入', '1', '1', '20161026', '李博');

alter table gf_endday_trade_note
  add constraint PK_gf_endday_trade_note primary key (application_code);
  
alter table gf_prod_trusteefee_month
  add constraint PK_gf_prod_trusteefee_month primary key (cust_type);
  
alter table gf_prod_trusteefee_daily
  add constraint PK_gf_prod_trusteefee_daily primary key (cust_type);
  
--  web_gf_limit_ctl
drop table web_gf_limit_ctl;
-- Create table
create table web_gf_limit_ctl
(
  PROD_CODE          VARCHAR2(10) not null,
  ORGAN_CODE         VARCHAR2(8) not null,
  CHANNEL            VARCHAR2(2) not null,
  BEGIN_DATE         VARCHAR2(8) not null,
  END_DATE           VARCHAR2(8) not null,
  WAIT_SELL_LIMIT    NUMBER(17,2) default 0.00 not null,
  CAN_SELL_LIMIT     NUMBER(17,2) default 0.00 not null,
  SUM_ORDER_LIMIT    NUMBER(17,2) default 0.00 not null,
  CAN_ORDER_LIMIT    NUMBER(17,2) default 0.00 not null,
  SUM_BUY_LIMIT      NUMBER(17,2) default 0.00 not null,
  CAN_BUY_LIMIT      NUMBER(17,2) default 0.00 not null,
  SUM_PURCHASE_LIMIT NUMBER(17,2) default 0.00 not null,
  CAN_PURCHASE_LIMIT NUMBER(17,2) default 0.00 not null,
  IS_UP_LIMIT        CHAR(1) default '0' not null
);
-- Add comments to the table 
comment on table web_gf_limit_ctl
  is '理财产品直销系统额度信息||理财产品直销系统额度信息';
-- Add comments to the columns 
comment on column web_gf_limit_ctl.PROD_CODE
  is '理财产品编码||理财产品代码规则（位数）：发行年度（2）＋币种（2）＋产品品牌代码（2）+产品期次序数（4）';
comment on column web_gf_limit_ctl.ORGAN_CODE
  is '机构代码||机构代码';
comment on column web_gf_limit_ctl.CHANNEL
  is '渠道||渠道';
comment on column web_gf_limit_ctl.BEGIN_DATE
  is '开始日期||开始日期';
comment on column web_gf_limit_ctl.END_DATE
  is '结束日期||结束日期';
comment on column web_gf_limit_ctl.WAIT_SELL_LIMIT
  is '待销额度||待销额度';
comment on column web_gf_limit_ctl.CAN_SELL_LIMIT
  is '在销额度||在销额度';
comment on column web_gf_limit_ctl.SUM_ORDER_LIMIT
  is '总预约额度||总预约额度';
comment on column web_gf_limit_ctl.CAN_ORDER_LIMIT
  is '可预约额度||可预约额度';
comment on column web_gf_limit_ctl.SUM_BUY_LIMIT
  is '总认购额度||总认购额度';
comment on column web_gf_limit_ctl.CAN_BUY_LIMIT
  is '可认购额度||可认购额度';
comment on column web_gf_limit_ctl.SUM_PURCHASE_LIMIT
  is '总申购额度||总申购额度';
comment on column web_gf_limit_ctl.CAN_PURCHASE_LIMIT
  is '可申购额度||可申购额度';
comment on column web_gf_limit_ctl.IS_UP_LIMIT
  is '是否向上级取额度||是否向上级取额度:
0-否 1-是 ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table web_gf_limit_ctl
  add constraint PK_web_gf_limit_ctl primary key (PROD_CODE, ORGAN_CODE, CHANNEL, BEGIN_DATE, END_DATE);

--web_prod_open_duration_info、gf_prod_open_duration_info
  alter table web_prod_open_duration_info add open_flag CHAR(1) default '1' not null;
comment on column web_prod_open_duration_info.open_flag
    is '开放标志:0-有规律 1-无规律||开放标志:0-有规律 1-无规律';
 alter table gf_prod_open_duration_info add open_flag CHAR(1) default '1' not null;
comment on column gf_prod_open_duration_info.open_flag
    is '开放标志:0-有规律 1-无规律||开放标志:0-有规律 1-无规律';

    
--  gf_prod_limit_detail
alter table gf_prod_limit_detail rename column  cust_type char(1) to  channel varchar2(2);
    comment on column gf_prod_limit_detail.channel is '渠道||渠道';
alter table gf_prod_limit_detail add trade_date date not null;       
comment on column gf_prod_limit_detail.trade_date
is '交易日期||交易日期';   
comment on column gf_prod_limit_detail.LIMIT_DEDUCTION_FLAG
  is '额度扣减标志||额度扣减标志 1-预约,2-认购,3-申购，4预约转认购';      


--gf_prod_transf_note
alter table gf_prod_transf_note add  host_note varchar2(128);
 comment  on column gf_prod_transf_note.host_note
 is ' 主机消息|| 主机消息';  
alter table gf_prod_transf_note add  host_code varchar2(6);
 comment  on column gf_prod_transf_note.host_code
 is ' 主机返回码|| 主机返回码';      
--ta_endday_ctl    
alter table ta_endday_ctl add LOG_FILE	VARCHAR2(256);
comment on column ta_endday_ctl.LOG_FILE
  is '日志文件||日志文件';      
   
    


alter table web_prod_open_duration_rule drop column redeem_amt_handle_flag;
  alter table web_prod_open_duration_rule drop column redeem_quot_affirm_type;  
  alter table web_prod_open_duration_rule drop column purchase_amt_handle_flag;      
  alter table web_prod_open_duration_rule drop column purchase_quot_affirm_type;         

 alter table web_prod_open_duration_rule add amt_handle_flag	char(1);
 comment on column web_prod_open_duration_rule.amt_handle_flag
 is '资金处理方式||申购扣款方式，赎回存款方式';     
    
alter table web_prod_open_duration_rule add quot_affirm_type	char(1);
comment on column web_prod_open_duration_rule.quot_affirm_type
  is '份额确认方式||申购区间份额确认方式 : 1-批量确认  2-实时确认';     

alter table web_prod_open_duration_rule drop column PURCHASE_INTERVAL;
alter table web_prod_open_duration_rule drop column REDEEM_INTERVAL;

alter table gf_prod_open_duration_rule drop column PURCHASE_INTERVAL;
alter table gf_prod_open_duration_rule drop column REDEEM_INTERVAL;

 alter table gf_recv_capital_data_info
  add constraint gf_recv_capital_data_info primary key (capital_type);
   
   alter table gf_sell_trade_list_note add handle_name VARCHAR2(60);
  comment on column gf_sell_trade_list_note.handle_name
  is '经办人姓名||经办人姓名';
  alter table gf_sell_trade_list_note add handle_cert_type VARCHAR2(2);
  comment on column gf_sell_trade_list_note.handle_cert_type
  is '经办人证件类型||经办人证件类型';
  alter table gf_sell_trade_list_note add handle_cert_code VARCHAR2(20);
  comment on column gf_sell_trade_list_note.handle_cert_code
  is '经办人证件号码||经办人证件号码';
  alter table ta_special_trade_list_note modify re_application_code varchar2(20);
  
   alter table gf_prod_cust_white_list rename column  ACCT_CODE to  customerid;
  comment on column gf_prod_cust_white_list.customerid is '中间业务交易账号||中间业务交易账号';
 
drop table gf_limit_ctl;   -------------------------------------------------------------------------------------执行的时候需要注意，原来的额度表
-- Create table
create table GF_LIMIT_CTL
(
  PROD_CODE          VARCHAR2(10) not null,
  ORGAN_CODE         VARCHAR2(8) not null,
  CHANNEL            VARCHAR2(2) not null,
  BEGIN_DATE         VARCHAR2(8) not null,
  END_DATE           VARCHAR2(8) not null,
  WAIT_SELL_LIMIT    NUMBER(17,2) default 0.00 not null,
  CAN_SELL_LIMIT     NUMBER(17,2) default 0.00 not null,
  SUM_ORDER_LIMIT    NUMBER(17,2) default 0.00 not null,
  CAN_ORDER_LIMIT    NUMBER(17,2) default 0.00 not null,
  SUM_BUY_LIMIT      NUMBER(17,2) default 0.00 not null,
  CAN_BUY_LIMIT      NUMBER(17,2) default 0.00 not null,
  SUM_PURCHASE_LIMIT NUMBER(17,2) default 0.00 not null,
  CAN_PURCHASE_LIMIT NUMBER(17,2) default 0.00 not null,
  IS_UP_LIMIT        CHAR(1) default '0' not null
);
-- Add comments to the table 
comment on table GF_LIMIT_CTL
  is '理财产品直销系统额度信息||理财产品直销系统额度信息';
-- Add comments to the columns 
comment on column GF_LIMIT_CTL.PROD_CODE
  is '理财产品编码||理财产品代码规则（位数）：发行年度（2）＋币种（2）＋产品品牌代码（2）+产品期次序数（4）';
comment on column GF_LIMIT_CTL.ORGAN_CODE
  is '机构代码||机构代码';
comment on column GF_LIMIT_CTL.CHANNEL
  is '渠道||渠道';
comment on column GF_LIMIT_CTL.BEGIN_DATE
  is '开始日期||开始日期';
comment on column GF_LIMIT_CTL.END_DATE
  is '结束日期||结束日期';

--错误信息
insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z163', '过户份额不能大于可过户份额！', '99370000000', 'z163', '过户份额不能大于可过户份额！', '20160725', '145706', '张兴帅');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z164', '{0}不存在', '99370000000', 'z164', '{0}不存在', '20160725', '143534', '张兴帅');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z165', '{0}异常', '99370000000', 'z165', '{0}异常', '20160725', '143720', '张兴帅');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z166', '{0}的长度不能超过{1}！', '99370000000', 'z166', '{0}的长度不能超过{1}！', '20160725', '150651', '张兴帅');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z167', '产品编码为{0}的产品不存在!', '99370000000', 'z167', '产品编码为{0}的产品不存在!', '20160725', '150720', '张兴帅');

insert into gf_Err_Info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z168', '提交失败！', '99370000000', 'z168', '提交失败！', '20160820', '180731', '张兴帅');

环境138和153 表gf_prod_auto_open_info增加字段  
counter_open_flag char(1) 柜台自动开市标志
counter_ctl_flag char(1)  柜台自动闭市标志
<<<<<<< .working


表gf_prod_cust_white_list   修改字段，原字段customerid  中间业务账号，修改为 TA_CODE   ta账号

=======
>>>>>>> .merge-right.r12567
--gf_dict
update gf_dict set dict_key_in='143'  where dict_no='D_TRADE_KIND' and dict_value_in='分红'
--gf_pub_acct_std_info
truncate table gf_pub_acct_std_info;
insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('68', '1115605003019613', '人民币理财管理费收入户', '241502004', '人民币理财管理费收入', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('67', '1115601000224188', '表外理财资产损益户', '123456789', '表外理财资产损益', '156', '1');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('66', '1115602000520856', '代理业务负债-理财户', '274006', '代理业务负债-理财', '156', '1');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('25', '1115601000066438', '代理人民币理财计提手续费户', '144501005', '直接指定公允价值计量金融资产-理财投资-手续费', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('53', '1115602000463631', '单位保本理财资金清算户', '241501004', '直接指定公允价值计量金融负债-单位理财资金-待处理款项', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('57', '1115602000464827', '个人保本理财资金清算户', '241502004', '直接指定公允价值计量金融负债-个人理财资金-待处理款项', '156', '0');
--20161130
--额度查询条件  
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_FP', '是否分配', '0', '是', '', '', 1, '参数初始导入', '1', '1', '20161130', '李博');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_FP', '是否分配', '1', '否', '', '', 2, '参数初始导入', '1', '1', '20161130', '李博');
--20161201

--错误码
insert into GF_ERR_INFO (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'w999', '{0}', '99370000000', 'w999', '{0}', '20160822', '161249', '魏玉航');


环境153和138
表 fd_publicpara  
增加字段    highnetworthvaliddate   VARCHAR2(8)
--20161201  GF_PROD_ACCT_INFO
create table GF_PROD_ACCT_INFO
(
  PROD_CODE       VARCHAR2(10) not null,
  ACCT_TYPE       CHAR(2) not null,
  IN_ACCT_CODE    VARCHAR2(30) not null,
  IN_ACCT_NAME    VARCHAR2(100) not null,
  BANK_ITEM_CODE  VARCHAR2(60) not null,
  BANK_ITEM_NAME  VARCHAR2(200) not null,
  CURRENCY        VARCHAR2(3) default '156' not null,
  BREAK_EVEN_TYPE CHAR(1) not null
)
tablespace FUNDSYS_DATA_DBS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
  );
-- Add comments to the table 
comment on table GF_PROD_ACCT_INFO
  is '产品账户信息表||记录保本、非保本理财产品资金清算所使用的各类内部户';
-- Add comments to the columns 
comment on column GF_PROD_ACCT_INFO.PROD_CODE
  is '理财产品编码||理财产品代码规则(位数):发行年度(2)+币种(2)+产品品牌代码(2)+产品期次序数(4)';
comment on column GF_PROD_ACCT_INFO.ACCT_TYPE
  is '账户类型||账户类型';
comment on column GF_PROD_ACCT_INFO.IN_ACCT_CODE
  is '内部户帐号||内部户帐号';
comment on column GF_PROD_ACCT_INFO.IN_ACCT_NAME
  is '内部账户名称||内部账户名称';
comment on column GF_PROD_ACCT_INFO.BANK_ITEM_CODE
  is '明细科目编号||明细科目编号';
comment on column GF_PROD_ACCT_INFO.BANK_ITEM_NAME
  is '明细科目名称||明细科目名称';
comment on column GF_PROD_ACCT_INFO.CURRENCY
  is '币种||币种';
comment on column GF_PROD_ACCT_INFO.BREAK_EVEN_TYPE
  is '产品保本类型||产品保本类型 0-保本 1-不保本';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GF_PROD_ACCT_INFO
  add constraint PK_GF_PROD_ACCT_INFO primary key (PROD_CODE, ACCT_TYPE)
  using index 
  tablespace FUNDSYS_DATA_DBS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
  );




--20161202  ta_in_out_acct_ref
insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('120', '57', '52', '认购款划款', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('122', '57', '52', '申购款划款', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('124', '52', '57', '赎回款（本金）划款', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('151', '52', '57', '到期款（本金）划款', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('120', '53', '49', '认购款划款', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('122', '53', '49', '申购款划款', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('124', '49', '53', '赎回款（本金）划款', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('151', '49', '53', '到期款（本金）划款', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('200', '66', '67', '赎回负收益划款', '02', '0');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('201', '66', '67', '到期负收益划款', '02', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('0', '083000', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('1', '163000', '0');

--gf_dict  开放规则20161202
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '开放类型', '1', '申购', '', '', 1, '', '1', '191924', '20160726', '史巍');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_PHASE_MODEL', '开放期模式', '0', '有规律开放', '', '', 1, '开放期是否按规律开放', '1', '153404', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('HOLIDAY_MAINTENANCE', '节假日维护', '1', '取消', '', '', 2, '节假日是否维护', '1', '150144', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_PHASE_MODEL', '开放期模式', '1', '无规律开放', '', '', 2, '是否按规律开放', '1', '150119', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('HOLIDAY_MAINTENANCE', '节假日维护', '0', '顺延', '', '', 1, '节假日是否维护', '1', '154346', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '开放周期', '1', '周', '', '', 2, '开放周期（日周月季年）', '1', '145640', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '开放周期', '2', '月', '', '', 3, '开放周期（日周月季年）', '1', '145720', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '开放周期', '3', '季', '', '', 4, '开放周期（日周月季年）', '1', '145816', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '开放周期', '4', '年', '', '', 5, '开放周期（日周月季年）', '1', '145910', '20160716', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Quot_Affirm_Type', '申购区间份额确认方式', '1', '批量', '', '', 1, '', '1', '101233', '20160720', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Quot_Affirm_Type', '申购区间份额确认方式', '2', '实时', '', '', 2, '', '1', '101249', '20160720', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Amt_Handle_Flag', '申购区间资金处理方式', '1', '批量', '', '', 1, '', '1', '101322', '20160720', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Amt_Handle_Flag', '申购区间资金处理方式', '2', '实时', '', '', 2, '', '1', '101405', '20160720', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '开放类型', '2', '赎回', '', '', 2, '', '1', '192025', '20160726', '史巍');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '开放类型', '3', '申赎皆可', '', '', 3, '', '1', '192026', '20160726', '史巍');



--D_DEAL_STATUS
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '处理状态', '0', '未处理', '', '', 1, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '处理状态', '1', '划款处理中', '', '', 2, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '处理状态', '2', '处理成功', '', '', 3, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '处理状态', '3', '处理失败', '', '', 4, '参数初始导入', '1', '1', '20160308', '谷立羊');

--TA_hK_NOTIFY_INFO、TA_hK_NOTIFY_INFO  添加字段REUSLT_MSG
alter table TA_hK_NOTIFY_INFO add RESULT_MSG	VARCHAR2(60);
comment on column TA_hK_NOTIFY_INFO.RESULT_MSG
  is '划款明细关联信息||划款明细关联信息';  
  
alter table TA_sK_NOTIFY_INFO add RESULT_MSG	VARCHAR2(60);
comment on column TA_sK_NOTIFY_INFO.RESULT_MSG
  is '划款明细关联信息||划款明细关联信息';  
--gf_cict:D_FPFLAG   
DELETE from gf_dict t where t.dict_no='D_FPFLAG';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '0', '未处理', '', '', 1, '手续费分配时', '1', '132358', '20160820', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '1', '流程审批中', '', '', 2, '手续费分配时', '1', '132420', '20160820', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '2', '等待后台处理', '', '', 3, '手续费分配时', '1', '132443', '20160820', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '3', '已处理', '', '', 4, '手续费分配时', '1', '132456', '20160820', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '4', '处理异常', '', '', 5, '手续费分配时', '1', '132507', '20160820', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '销售费处理状态', '5', '审批驳回', '', '', 4, '手续费分配时', '1', '132456', '20160820', '谷立羊');




-- Create table
create table GF_FEE_DIS_APP
(
  WORKFLOW_CODE      VARCHAR2(15) not null,
  FEE_SERIAL_CODE    VARCHAR2(16) not null,
  APP_STATUS         CHAR(1) not null,
  FEE_SERIAL_DESC    VARCHAR2(200),
  LATEST_MODIFY_DATE VARCHAR2(8) not null,
  LATEST_MODIFY_TIME VARCHAR2(6) not null,
  LATEST_OPER_CODE   VARCHAR2(11) not null,
  FPAMT              NUMBER(17,2) not null,
  BEGIN_DATE         VARCHAR2(8) not null,
  END_DATE           VARCHAR2(8) not null
);
-- Add comments to the table 
comment on table GF_FEE_DIS_APP
  is '手续费审批表';
-- Add comments to the columns 
comment on column GF_FEE_DIS_APP.WORKFLOW_CODE
  is '审批流程代码';
comment on column GF_FEE_DIS_APP.FEE_SERIAL_CODE
  is '手续费分配序列号';
comment on column GF_FEE_DIS_APP.APP_STATUS
  is '审批状态';
comment on column GF_FEE_DIS_APP.FEE_SERIAL_DESC
  is '手续费分配描述';
comment on column GF_FEE_DIS_APP.LATEST_MODIFY_DATE
  is '最后修改日期';
comment on column GF_FEE_DIS_APP.LATEST_MODIFY_TIME
  is '最后修改时间';
comment on column GF_FEE_DIS_APP.LATEST_OPER_CODE
  is '最后操作员';
comment on column GF_FEE_DIS_APP.FPAMT
  is '手续费批次总金额';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GF_FEE_DIS_APP
  add constraint PK_GF_FEE_DIS_APP primary key (WORKFLOW_CODE);
  
  
  
  -- Create table
create table GF_FEE_DIS_BATCH_APP
(
  JTFEECODE       VARCHAR2(24) not null,
  FEE_SERIAL_CODE VARCHAR2(16) not null,
  APP_STATUS      VARCHAR2(2) not null,
  WORKFLOW_CODE   VARCHAR2(15) not null
);
-- Add comments to the columns 
comment on column GF_FEE_DIS_BATCH_APP.JTFEECODE
  is '手续费计提码';
comment on column GF_FEE_DIS_BATCH_APP.FEE_SERIAL_CODE
  is '手续费分配序列号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GF_FEE_DIS_BATCH_APP
  add constraint PK_GF_FEE_DIS_BATCH_APP primary key (JTFEECODE, FEE_SERIAL_CODE, WORKFLOW_CODE);
-- 手续费审批配置
insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask2', '228', '0', '0', '', '04');

insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask1', '238', '0', '0', '', '04');

insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('startevent1', '229', '0', '0', '', '04');
--WEB_GF_LIMIT_CTL
 alter table gf_limit_ctl add  initial_order_limit number(22) ; 
     comment on column gf_limit_ctl.initial_order_limit
  is '初始预约额度||初始预约额度';
  
   alter table gf_limit_ctl add  initial_buy_limit number(22) ; 
     comment on column gf_limit_ctl.initial_buy_limit
  is '初始购买额度||初始购买额度';
  
    alter table WEB_GF_LIMIT_CTL add  initial_order_limit number(22) ; 
     comment on column WEB_GF_LIMIT_CTL.initial_order_limit
  is '初始预约额度||初始预约额度';
  
   alter table WEB_GF_LIMIT_CTL add  initial_buy_limit number(22) ; 
     comment on column WEB_GF_LIMIT_CTL.initial_buy_limit
  is '初始购买额度||初始购买额度';
--
 alter table GF_LIMIT_CTL modify INITIAL_ORDER_LIMIT NUMBER(17,2);
  alter table GF_LIMIT_CTL modify INITIAL_ORDER_LIMIT default 0.00;

   alter table GF_LIMIT_CTL modify INITIAL_BUY_LIMIT NUMBER(17,2);
  alter table GF_LIMIT_CTL modify INITIAL_BUY_LIMIT default 0.00;
  
    alter table WEB_GF_LIMIT_CTL modify INITIAL_ORDER_LIMIT NUMBER(17,2);
  alter table WEB_GF_LIMIT_CTL modify INITIAL_ORDER_LIMIT default 0.00;

   alter table WEB_GF_LIMIT_CTL modify INITIAL_BUY_LIMIT NUMBER(17,2);
  alter table WEB_GF_LIMIT_CTL modify INITIAL_BUY_LIMIT default 0.00;
--20161221
--TA非交易过户，交易类型标识
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_TRANS_TYPE', '交易类型', '0', '法院强制执行', '', '', 123, '', '1', '174207', '20160711', '张兴帅');

insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_TRANS_TYPE', '交易类型', '1', '抵债', '', '', 124, '', '1', '174232', '20160711', '张兴帅');
 

--20161213 gf_cict:D_CHNL
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_CHNL', '销售渠道', '17', 'VTM自助银行', '', '', 11, '参数初始导入', '1', '165628', '20160707', '郝志潮');


--20161221 gf_cict:PROD_STATUSZ
insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_STATUSZ', '理财产品状态', '0', '未成立', '', '', 1, '更改理财产品状态', '1', '172308', '20160711', '郝志潮');

insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_STATUSZ', '理财产品状态', '4', '不成立', '', '', 2, '更改理财产品状态', '1', '172359', '20160711', '郝志潮');
 
 
 --20161222 web_Menu_Info 理财产品状态
insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('20C0BF5E871F433FB8B36AF2171802F4', 'TRADE-12', 'TRADE', 400012, '0', '/webGfProdStatus/listUI.htm', '理财产品状态', '/libs/icons/edit.gif', '1', '1', '20160708', '170450', '郝志潮');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('1E897A524B1B4AF6A670208E96556A85', 'TRADE-12-02', 'TRADE-12', 401201, '0', '{text : ''修改'', click : onUpdate, iconClass : ''icon_edit''}', '修改', '/libs/icons/edit.gif', '1', '2', '20160710', '182851', '郝志潮');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('2F1C9D9D1C5A4213A49E19723550E806', 'TRADE-12-01', 'TRADE-12', 401202, '0', '{text : ''详细信息'', click : onInfo, iconClass : ''icon_list''}', '详细信息', '/libs/icons/list.gif', '1', '2', '20160710', '182906', '郝志潮');



--增加生成下年存续期计划菜单
insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('D56FF92D9A55474BA293991FDE561097', 'TRADE-11', 'TRADE', 400011, '0', '/webProdOpenDurationInfo/listUI.htm', '开放式存续期管理', '/libs/icons/leaf.gif', '1', '1', '20160822', '143651', '魏玉航');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('62180224E81948AC88F8B94D98A23D14', 'TRADE-11-01', 'TRADE-11', 401101, '0', '{text : ''生成'', click : onCreate, iconClass : ''icon_add''}', '生成', '/libs/icons/add.gif', '1', '2', '20160714', '161432', '魏玉航');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('0CF2B37E045444B49FA9D97DD60BE94D', 'TRADE-11-02', 'TRADE-11', 401102, '0', '{text : ''详细信息'', click : onInfo, iconClass : ''icon_list''}', '详细信息', '/libs/icons/list.gif', '1', '2', '20160714', '161721', '魏玉航');

--净值型产品说明书生成
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('B69B6B963B394247B5494DE37DF97C63', 'GF-10-05', 'GF-10', 201005, '0', '{text : ''净值型产品说明书生成'', click : onCreate, iconClass : ''icon_add''}', '净值型产品说明书生成', '/libs/icons/add.gif', '1', '2', '20160713', '183235', 'gly');
--净值最晚延迟日
alter table gf_prod_asset_mutual_info add netvalue_last_day  number(2) default '0';
comment on column gf_prod_asset_mutual_info.netvalue_last_day
  is '净值最晚延迟日||净值最晚延迟日'; 
alter table web_prod_asset_mutual_info add netvalue_last_day  number(2) default '0';
comment on column web_prod_asset_mutual_info.netvalue_last_day
  is '净值最晚延迟日||净值最晚延迟日';
  
  --20161223 添加净值最晚延迟日 下拉菜单
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '1', '1', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '2', '2', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '3', '3', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '4', '4', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '5', '5', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '6', '6', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '7', '7', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '8', '8', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '9', '9', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '10', '10', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '11', '11', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '12', '12', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '13', '13', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '14', '14', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '净值最晚延迟日', '15', '15', '', '', 123456, '', '1', '100745', '20160713', '张兴帅');
--
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('DISTRIBUTE_FLAG', '分配标识', '0', '未分配', '', '', 1, '', '1', '155851', '20160927', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('DISTRIBUTE_FLAG', '分配标识', '1', '已分配', '', '', 2, '', '1', '155903', '20160927', '谷立羊');

--20161228添加外围产品交易码
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702010', '理财产品额度预约（外围）', '', '', 1, '初始参数导入', '1', '174646', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702030', '理财追加投资变更（外围）', '', '', 3, '初始参数导入', '1', '100219', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702000', '理财产品撤单（外围）', '', '', 0, '初始参数导入', '1', '100239', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702020', '理财产品认购（外围）', '', '', 2, '初始参数导入', '1', '100308', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702040', '理财产品提前赎回（外围）', '', '', 4, '初始参数导入', '1', '100322', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702050', '理财产品终止投资（外围）', '', '', 5, '初始参数导入', '1', '100342', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702100', '根据合同号/协议号查询理财交易（外围）', '', '', 12, '初始参数导入', '1', '100447', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702110', '理财产品帐户余额查询（外围）', '', '', 6, '初始参数导入', '1', '100426', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702120', '理财产品交易明细查询（外围）', '', '', 7, '初始参数导入', '1', '100409', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702220', '理财产品品种查询（外围）', '', '', 8, '初始参数导入', '1', '100354', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702310', '理财产品分红方式查询（外围）', '', '', 9, '初始参数导入', '1', '100510', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702330', '理财产品份额查询（外围）', '', '', 10, '初始参数导入', '1', '100527', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702430', '余额理财撤约（外围）', '', '', 17, '初始参数导入', '1', '100558', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702340', '理财产品预约查询（外围）', '', '', 11, '初始参数导入', '1', '100542', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702440', '余额理财签约查询（外围）', '', '', 18, '初始参数导入', '1', '100159', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702420', '余额理财变更（外围）', '', '', 16, '初始参数导入', '1', '095845', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702410', '余额理财签约（外围）', '', '', 15, '初始参数导入', '1', '100023', '20160715', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '渠道交易控制交易码（外围）', '702350', '理财产品认购查询（外围）', '', '', 14, '初始参数导入', '1', '100118', '20160715', '郝志潮');

--20170113 添加 购买期最后一天允许撤单 功能
insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('53F6E1D90D004388A07A5C98FA1C9FCE', 'TRADE-13', 'TRADE', 400013, '0', '/gfProdPurLastCancel/listUI.htm', '购买期最后一天允许撤单', '/libs/icons/leaf.gif', '1', '1', '20160718', '154706', '张兴帅');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E841434F2D294D15953AD44766CF12EA', 'TRADE-13-01', 'TRADE-13', 401301, '0', '{text : ''修改'', click : click : onUpdate, iconClass : ''icon_edit''}', '修改', '/libs/icons/edit.gif', '1', '2', '20160718', '155758', '张兴帅');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('C1B0E52B48384291BA8D91492CBCE18D', 'TRADE-13-02', 'TRADE-13', 401302, '0', '{text : ''详细'', click : click : onInfo, iconClass : ''icon_list''}', '详细', '/libs/icons/list.gif', '1', '2', '20160718', '155920', '张兴帅');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('ECFBB4CCCFA84CDAB1900A16681AF8A9', 'TRADE-13-03', 'TRADE-13', 401303, '0', '{text : ''复核'', click : click : onInfo, iconClass : ''icon_list''}', '复核', '/libs/icons/list.gif', '1', '2', '20160718', '105804', '张兴帅');

--20170117 添加自动刷新 开闭市时间检测
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_REFRESH_INTERVAL', '待办记录数刷新时间(秒)', '30', '自动开闭市', '', '', 3, '', '1', '165530', '20160720', '张兴帅');

--20170118 开闭市时间管理
insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('0', '080000', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('1', '180000', '0');

--20170118 自动开闭市管理
insert into gf_Prod_Auto_Open_Info (PROD_AUTO_OPEN_FLAG, E_CHANNEL_AUTO_OPEN_FLAG, COUNTER_OPEN_FLAG, COUNTER_CTL_FLAG)
values ('1', '1', '1', '1');

--20170324 登记簿查询（当日申购赎回明细，月月升当日存量）
insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '登记簿名称', '06', '月月升当日申赎明细', '', '', 6, '初始参数导入', '1', '103100', '20170416', '郝志潮');

insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '登记簿名称', '07', '月月升当日存量', '', '', 7, '初始参数淡入', '1', '142847', '20170304', '郝志潮');

--20170531畅享1号、机构交易明细
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '登记簿名称', '08', '畅享1号申赎明细查询', '', '', 8, '初始参数淡入', '1', '142847', '20170304', '郝志潮');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('JG_REGISTER', '登记簿名称', '12', '机构交易明细登记簿', '', '', 12, '参数初始导入', '1', '1', '20170522', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_TYPE', '产品类型', '02', '封闭式', '02', '封闭式', 1, '', '1', '104800', '20170522', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_TYPE', '产品类型', '04', '开放式', '04', '开放式', 2, '', '1', '104800', '20170522', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '机构客户类型', '1', '个人', '', '', 0, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '机构客户类型', '2', '机构', '', '', 1, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '机构客户类型', '3', '同业', '', '', 2, '参数初始导入', '1', '1', '20160308', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '机构客户类型', '4', '小企业', '', '', 3, '参数初始导入', '1', '1', '20160308', '谷立羊');

--20170712外币汇率交易
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '036', '澳元', '12', '澳大利亚元(AUD)', 1, '', '1', '015209', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '124', '加元', '10', '加拿大元(CAD)', 2, '', '1', '015748', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '156', '人民币元', '01', '人民币(CNY)', 0, '', '1', '014548', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '978', '欧元', '04', '欧元', 8, '', '1', '015748', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '344', '港元', '02', '港元(HKD)', 4, '', '1', '014354', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '392', '日元', '05', '日元(JPY)', 5, '', '1', '015112', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '826', '英镑', '08', '英镑', 6, '', '1', '015624', '20170712', '谷立羊');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '币种', '840', '美元', '03', '美元', 7, '', '1', '015209', '20170712', '谷立羊');

--20170726测试是否通过
alter table WEB_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.SPPI  is '测试是否通过||测试是否通过 0-否,1-是';

alter table GF_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.SPPI  is '测试是否通过||测试是否通过 0-否,1-是';

--20170726推荐标识
alter table WEB_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.SPPI  is '测试是否通过||测试是否通过 0-否,1-是';

alter table GF_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.SPPI  is '测试是否通过||测试是否通过 0-否,1-是';

--20170815
alter table WEB_PROD_ASSET_MUTUAL_INFO add IS_RECOMMEND CHAR(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.IS_RECOMMEND is '推荐标志||推荐标志 0-否 1-是';

alter table GF_PROD_ASSET_MUTUAL_INFO add IS_RECOMMEND CHAR(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.IS_RECOMMEND is '推荐标志||推荐标志 0-否 1-是';

--20170821 视图建表语句
create or replace view gf_prod_list_view as
select base_.prod_code,
       base_.prod_name,
       base_.prod_name_suffix,
       base_.prod_status,
       register_.prod_period,
       base_.cust_type,
       buy_.raising_begin_date,
       buy_.raising_end_date,
       base_.prod_begin_date,
       base_.prod_end_date,
       base_.prod_duration_days,
       base_.is_auto_balance,
       base_.is_cont_prod,
       base_.is_need_cont_prod,
       base_.prod_oper_code,
       base_.prod_manager_code,
       base_.sell_fee_type,
       base_.lowest_investment,
       base_.corp_lowest_investment,
       buy_.plan_issue_amt,
       base_.prod_profit_rate,
       decode(base_.cust_type,
              '0',
              '',
              decode(base_.prod_oper_model,
                     '02',
                     decode(base_.profit_handle_flag,
                            '1',
                            profit_rate_.profit_rate,
                            ''),
                     '')) as profit_rate,
       decode(base_.cust_type,
              '0',
              '',
              decode(base_.prod_oper_model,
                     '02',
                     decode(base_.sell_fee_type,
                            '1',
                            sell_rate_.sell_rate,
                            ''),
                     '')) as sell_rate,
       area_.province_branch_code,
       channel_.channel_code,
       base_.prod_control_fee_rate,
       base_.prod_recom_rate,
       base_.overfulfil_profit,
       prj_.prj_name,
       prj_.prj_research_staff,
       asset_rel_.asset_code,
       asset_rel_.asset_name,
       base_.currency
  from gf_prod_base_info         base_,
       gf_prod_buy_info          buy_,
       gf_prod_register_info     register_,
       gf_prod_channel           channel_,
       gf_prod_issue_area        area_,
       gf_prod_prj_rel           prj_rel_,
       gf_prj_info               prj_,
       gf_prod_asset_rel         asset_rel_,
       gf_prod_fix_profit_rate   profit_rate_,
       gf_prod_fix_sell_fee_rate sell_rate_
 where base_.prod_code = buy_.prod_code(+)
   and base_.prod_code = register_.prod_code(+)
   and base_.prod_code = register_.prod_code(+)
   and base_.prod_code = channel_.prod_code(+)
   and base_.prod_code = area_.prod_code(+)
   and base_.prod_code = prj_rel_.prod_code(+)
   and base_.prod_code = asset_rel_.prod_code(+)
   and base_.prod_code = profit_rate_.prod_code(+)
   and base_.prod_code = sell_rate_.prod_code(+)
   and prj_rel_.prj_code = prj_.prj_code;

-- web_prod_view
create or replace view web_prod_view as
select base_.prod_code,
       base_.prod_name,
       base_.prod_name_suffix,
       base_.prod_status,
       register_.prod_period,
       base_.cust_type,
       base_.is_start_process,
       buy_.raising_begin_date,
       buy_.raising_end_date,
       base_.prod_begin_date,
       base_.prod_end_date,
       base_.prod_duration_days,
       base_.is_auto_balance,
       base_.is_cont_prod,
       base_.is_need_cont_prod,
       base_.prod_oper_code,
       base_.sell_fee_type,
       base_.lowest_investment,
       base_.corp_lowest_investment,
       buy_.plan_issue_amt,
       base_.prod_profit_rate,
       decode(base_.cust_type,
              '0',
              '',
              decode(base_.prod_oper_model,'02',decode(base_.profit_handle_flag,'1',profit_rate_.profit_rate,''),'')
              ) as profit_rate,
       decode(base_.cust_type,
              '0',
              '',
                decode(base_.prod_oper_model,'02',decode(base_.sell_fee_type,'1',sell_rate_.sell_rate,''),'')
              ) as sell_rate,
       area_.province_branch_code,
       channel_.channel_code,
       base_.prod_control_fee_rate,
       base_.prod_recom_rate,
       base_.overfulfil_profit,
       prj_.prj_name,
       prj_.prj_research_staff,
       asset_rel_.asset_code,
       asset_rel_.asset_name,
       base_.currency
  from web_prod_base_info         base_,
       web_prod_buy_info          buy_,
       web_prod_register_info     register_,
       web_prod_channel           channel_,
       web_prod_issue_area        area_,
       gf_prod_prj_rel            prj_rel_,
       gf_prj_info                prj_,
       gf_prod_asset_rel          asset_rel_,
       web_prod_fix_profit_rate   profit_rate_,
       web_prod_fix_sell_fee_rate sell_rate_
 where base_.prod_code = buy_.prod_code(+)
   and base_.prod_code = register_.prod_code(+)
   and base_.prod_code = register_.prod_code(+)
   and base_.prod_code = channel_.prod_code(+)
   and base_.prod_code = area_.prod_code(+)
   and base_.prod_code = prj_rel_.prod_code(+)
   and base_.prod_code = asset_rel_.prod_code(+)
   and base_.prod_code = profit_rate_.prod_code(+)
   and base_.prod_code = sell_rate_.prod_code(+)
   and prj_rel_.prj_code = prj_.prj_code(+);
   

--20171025 客户行内属性 添加标数
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '客户行内属性', '6', '新客户首次签约专属产品', '', '', 6, '新客户首次签约专属产品', '1', '154336', '20171009', '99999901001');
 
 
--20180408 添加业绩基准菜单 
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('72321417118244A097D79B25F4FCD16B', 'TRADE-15', 'TRADE', 400015, '0', '/gfProdPerformanceInfo/listUI.htm', '业绩比较基准维护', '/libs/icons/leaf.gif', '1', '1', '20180414', '161127', '白慧财');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('CBC8F1E89A42459EB5A9212C7BFEB99D', 'TRADE-15-01', 'TRADE-15', 401501, '0', '{text : ''新增'', click : onInsert, iconClass : ''icon_add''}', '新增', '/libs/icons/add.gif', '1', '2', '20180414', '161459', '白慧财');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('3F8D3198EB4546E3A5D472246C0D33C7', 'TRADE-15-02', 'TRADE-15', 401502, '0', '{text : ''修改'', click : onUpdate, iconClass : ''icon_edit''}', '修改', '/libs/icons/edit.gif', '1', '2', '20180414', '161603', '白慧财');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('AB6E403D85064544A10E23926BECBC53', 'TRADE-15-03', 'TRADE-15', 401503, '0', '{text : ''删除'', click : onDelete, iconClass : ''icon_delete''}', '删除', '/libs/icons/delete.gif', '1', '2', '20180414', '161647', '白慧财');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E98CD450A9504D1D8A446BEDC70B6D34', 'TRADE-15-04', 'TRADE-15', 401504, '0', '{text : ''详细'', click : onInfo, iconClass : ''icon_list''}', '详细', '/libs/icons/list.gif', '1', '2', '20180414', '161739', '白慧财');


--20180408 专属标志
alter table gf_prod_base_info add EXCLUSIVE_FLAG CHAR(1);
comment on column gf_prod_base_info.EXCLUSIVE_FLAG is '专属标志||专属标志：1 代发专属 2 节假日专属';

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EXCLUSIVE_FLAG', '专属标志', '1', '代发专属', '', '', 1, '初始参数导入', '1', '100454', '20180408', '白慧财');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EXCLUSIVE_FLAG', '专属标志', '2', '节假日专属', '', '', 2, '初始参数导入', '1', '100454', '20180408', '白慧财');


--201804017 业绩基准参数
create table web_Prod_Performance_Info
(
  PROD_CODE          VARCHAR2(10) not null,
  PERFORMANCE_DATE   VARCHAR2(8) not null,
  PERFORMANCE_VALUE   NUMBER(17,4) default 0.00 not null,
  IS_PEG CHAR(1) not null,
  PEG_DESCRIPTION    VARCHAR2(200),
  LATEST_MODIFY_DATE VARCHAR2(8) NOT NULL, 
  LATEST_MODIFY_TIME VARCHAR2(6) NOT NULL, 
  LATEST_OPER_CODE VARCHAR2(11) NOT NULL 
);
alter table web_Prod_Performance_Info
  add constraint PK_web_Prod_Performance_Info primary key (PROD_CODE, PERFORMANCE_DATE);
comment on column WEB_PROD_PERFORMANCE_INFO.PROD_CODE
  is '理财产品编码||通过视图查询品牌信息，理财产品编码规则（位数）：发行年度（2）＋币种（2）＋产品品牌代码（2）+产品期次序数（4）';
comment on column WEB_PROD_PERFORMANCE_INFO.PERFORMANCE_DATE
  is '启用日期||启用日期';
comment on column WEB_PROD_PERFORMANCE_INFO.PERFORMANCE_VALUE
  is '业绩比较基准||业绩比较基准';
comment on column WEB_PROD_PERFORMANCE_INFO.IS_PEG
  is '是否挂钩||是否挂钩';
comment on column WEB_PROD_PERFORMANCE_INFO.PEG_DESCRIPTION
  is '挂钩描述||挂钩描述';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_MODIFY_DATE
  is '修改日期||修改日期';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_MODIFY_TIME
  is '修改时间||修改时间';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_OPER_CODE
  is '最后修改操作员||最后修改操作员';  

  create table gf_Prod_Performance_Info
(
  PROD_CODE          VARCHAR2(10) not null,
  PERFORMANCE_DATE   VARCHAR2(8) not null,
  PERFORMANCE_VALUE   NUMBER(17,4) default 0.00 not null,
  IS_PEG CHAR(1) not null,
  PEG_DESCRIPTION    VARCHAR2(200),
  LATEST_MODIFY_DATE VARCHAR2(8) NOT NULL, 
  LATEST_MODIFY_TIME VARCHAR2(6) NOT NULL, 
  LATEST_OPER_CODE VARCHAR2(11) NOT NULL 
);
alter table gf_Prod_Performance_Info
  add constraint PK_gf_Prod_Performance_Info primary key (PROD_CODE, PERFORMANCE_DATE);   
comment on table GF_PROD_PERFORMANCE_INFO
  is '产品业绩比较基准信息表||该表存放净值产品业绩比较基准';
-- Add comments to the columns 
comment on column GF_PROD_PERFORMANCE_INFO.PROD_CODE
  is '理财产品编码||通过视图查询品牌信息，理财产品编码规则（位数）：发行年度（2）＋币种（2）＋产品品牌代码（2）+产品期次序数（4）';
comment on column GF_PROD_PERFORMANCE_INFO.PERFORMANCE_DATE
  is '启用日期||启用日期';
comment on column GF_PROD_PERFORMANCE_INFO.PERFORMANCE_VALUE
  is '业绩比较基准||业绩比较基准';
comment on column GF_PROD_PERFORMANCE_INFO.IS_PEG
  is '是否挂钩||是否挂钩';
comment on column GF_PROD_PERFORMANCE_INFO.PEG_DESCRIPTION
  is '挂钩描述||挂钩描述';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_MODIFY_DATE
  is '修改日期||修改日期';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_MODIFY_TIME
  is '修改时间||修改时间';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_OPER_CODE
  is '最后修改操作员||最后修改操作员';
comment on table WEB_PROD_PERFORMANCE_INFO
  is '产品业绩比较基准信息表||该表存放净值产品业绩比较基准';
  
  

