/*********************20161123�������ݿ���******��ʼ*********************/
--������Ϣ
alter table gf_prod_base_info add purchase_CHANNEL VARCHAR2(100);
  comment on column gf_prod_base_info.purchase_CHANNEL
  is '�깺��������||�깺��������';
  
  alter table gf_prod_base_info add purchase_PROV VARCHAR2(500);
  comment on column gf_prod_base_info.purchase_PROV
  is '�깺����ʡ||�깺����ʡ';
  
  alter table gf_prod_base_info add SHARE_CHANNEL VARCHAR2(100);
  comment on column gf_prod_base_info.SHARE_CHANNEL
  is '�Ϲ��ڹ�������||�Ϲ��ڹ�������';
  
  alter table gf_prod_base_info add SHARE_PROV VARCHAR2(500);
  comment on column gf_prod_base_info.SHARE_PROV
  is '�Ϲ��ڹ���ʡ||�Ϲ��ڹ���ʡ';

  alter table web_prod_base_info add purchase_CHANNEL VARCHAR2(100);
  comment on column web_prod_base_info.purchase_CHANNEL
  is '�깺��������||�깺��������';
  
  alter table web_prod_base_info add purchase_PROV VARCHAR2(500);
  comment on column web_prod_base_info.purchase_PROV
  is '�깺����ʡ||�깺����ʡ';  

  alter table web_prod_base_info add SHARE_CHANNEL VARCHAR2(100);
  comment on column web_prod_base_info.SHARE_CHANNEL
  is '�Ϲ��ڹ�������||�Ϲ��ڹ�������';
  
  alter table web_prod_base_info add SHARE_PROV VARCHAR2(500);
  comment on column web_prod_base_info.purchase_PROV
  is '�Ϲ��ڹ���ʡ||�Ϲ��ڹ���ʡ';  

--������Ϣ
insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'w592', '���{0}ʧ�ܣ���������ĿͶ������', '99370000000', 'w592', '���{0}ʧ�ܣ���������ĿͶ������', '20160721', '094940', 'κ��');
--������ʾ

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100504', 'PM-05-04', 'PM-05', 100504, '0', '{text : ''��ϸ'', click : onInfo, iconClass : ''icon_list''}', '��ϸ', '/libs/icons/list.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100503', 'PM-05-03', 'PM-05', 100503, '0', '{text : ''ɾ��'', click : onDelete, iconClass : ''icon_delete''}', 'ɾ��', '/libs/icons/delete.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100502', 'PM-05-02', 'PM-05', 100502, '0', '{text : ''�޸�'', click : onUpdate, iconClass : ''icon_edit''}', '�޸�', '/libs/icons/edit.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100501', 'PM-05-01', 'PM-05', 100501, '0', '{text : ''����'', click : onInsert, iconClass : ''icon_add''}', '����', '/libs/icons/add.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('100505', 'PM-05-05', 'PM-05', 100505, '0', '{text : ''����'', click : onRefresh,iconClass : ''icon_refresh''}', '��Ч', '/libs/icons/refresh.gif', '1', '2', '2', '2', '2');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E90BE2E7DB5645D8A6355987983074BD', 'PM-05', 'PM', 100005, '0', '/publicPromptTable/listUI.htm', '������ʾ����', '/libs/icons/leaf.gif', '1', '1', '20160722', '162007', '������');

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
  is '������ʾ��||������ʾ��';
-- Add comments to the columns 
comment on column public_prompt_table.id
  is 'id||id';
comment on column public_prompt_table.content
  is '����||����';
comment on column public_prompt_table.oper_code
  is '������||������';
comment on column public_prompt_table.oper_time
  is '����ʱ��||����ʱ��';
  comment on column public_prompt_table.oper_date
  is '��������||��������';
comment on column public_prompt_table.use_status
  is '״̬||״̬ 1-���ã�1-ͣ��';
alter table public_prompt_table
  add constraint public_prompt_table primary key (id);
  --��ɫ������Ȩ������
delete from web_menu_info  where menu_no='PM-02-05'

--�걨δͨ���˵�
insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('B67A73D87E0C472EA4450EB691A32897', 'GF-05-08-01', 'GF-05-08', 200581, '0', '{text : ''�ر༭'', click : onEdit, iconClass : ''icon_edit''}', '�ر༭', '/libs/icons/edit.gif', '1', '2', '20160724', '181425', 'κ��');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('6F328ABA5BFA43829F03C7361A8072DA', 'GF-05-08', 'GF-05', 200508, '0', '/webProdRegisterFail/listUI.htm', '��Ʒ�걨δͨ����¼', '/libs/icons/leaf.gif', '1', '1', '20160725', '141006', 'κ��');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('61A8E632A5CC47F69FFAD454C87B9840', 'GF-05-08-02', 'GF-05-08', 200582, '0', '{text : ''ɾ��'', click : onDelete, iconClass : ''icon_delete''}', 'ɾ��', '/libs/icons/delete.gif', '1', '2', '20160724', '181725', 'κ��');

--����ר����Ʒ��������
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('95BA7D534D9744A78B0B9A91E7B705D3', 'GF-06-01-08', 'GF-06-01', 200618, '0', '{text : ''����ר����Ʒ��������'', click : onCopy, iconClass : ''icon_add''}', '����ר����Ʒ��������', '/libs/icons/add.gif', '1', '2', '20160725', '182251', '����˧');

--���δ��������ɾ��
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('D6CED4AA4B97432EB43685EF05B7E749', 'GF-06-06-04', 'GF-06-06', 200674, '0', '{text : ''ɾ��'', click : onDelete, iconClass : ''icon_delete''}', 'ɾ��', '/libs/icons/delete.gif', '1', '2', '20160722', '153415', 'κ��');


--Ԥ��δ�������ɾ������
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('574', 'GF-05-07-04', 'GF-05-07', 200574, '0', '{text : ''ɾ��'', click : onDelete, iconClass : ''icon_delete''}', 'ɾ��', '/libs/icons/delete.gif', '1', '2', '2', '2', '������');


--��ɫ�ڵ㲵�ؿ��Ʊ�
update acticiti_role_config_new set up_task_id = 'usertask3' where task_id = 'usertask8'   and activiti_type = '02'��

--ɾ�����в�Ʒ�����ؿ�������
delete acticiti_role_config_new where task_id = 'usertask1'   and activiti_type = '02' and organ_level='0'��

--���л�����Ʋ���
insert into acticiti_role_config_new (TASK_ID, UP_TASK_ID, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask18', 'startevent1', '1', '2', '11', '02');

--�Ż��걨�Ǽǲ����쵼��ȡ
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_REGISTER_LEADER', '����������', '20070301740', '����������', '���޺�', '����������', 0, '������ʼ����', '1', '1', '20160314', '������');

--��Ʒ״̬�޸�
update GF_DICT set dict_value_in='��Ʒ��ƣ����У�2' WHERE DICT_NO='D_PROD_STATUS' AND DICT_KEY_IN='12';
/*********************20161123�������ݿ���*******����********************/

/*********************20161201�������ݿ���*******��ʼ********************/
--gf_dict
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROMPT_INTERVAL', '������ʾ��ˢ��ʱ��(��)', '300', '��̨��ѯ', '', '', 1, '', '1', '160713', '20160725', '������');

delete gf_dict where dict_no='D_PROP_CODE';

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '1', '˽������', '', '', 1, '˽������', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '2', '�߾�ֵ', '', '', 2, '�߾�ֵ', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '3', '����', '', '', 3, '����', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '4', 'ͬҵ', '', '', 4, 'ͬҵ', '1', '172339', '20160223', '99999901001');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '5', 'С��ҵ', '', '', 5, 'С��ҵ', '1', '172339', '20160223', '99999901001');

--gf_prod_brand_info
alter table gf_prod_brand_info add year    varchar2(4) default '2016' not null
comment on column gf_prod_brand_info.year
  is '���||���';

alter table gf_prod_brand_info drop constraint PK_gf_prod_brand_info;
alter table gf_prod_brand_info
  add constraint PK_gf_prod_brand_info primary key (brand_code,year);
/*********************20161201�������ݿ���*******����********************/

/*********************20161214�������ݿ���*******��ʼ********************/
--����֪ͨ���·���Ʒ�Ƴ�
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('201104', 'GF-11-04', 'GF-11', 201104, '0', '{text : ''��Ʒ�Ƴ�����'', click : onAudit, iconClass : ''icon_list''}', '��Ʒ�Ƴ�����', '/libs/icons/edit.gif', '1', '3', '2', '2', '2');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_STATUS', '��Ʒ����״̬', '28', '������ȡ������', '', '', 28, '', '1', '165522', '20160717', '������');
--�걨�Ǽ�������
update gf_dict set dict_value_in='�����' where dict_no='CURRENCY' and dict_value_in='�����Ԫ';

/*********************20161214�������ݿ���*******����********************/
/*********************20161228�������ݿ���*******��ʼ********************/
--��Ʒ�걨���ڴ�
 alter table web_prod_asset_mutual_info add issue_num number(17);
  comment on column web_prod_asset_mutual_info.issue_num
  is '��ƷƷ�����ڴ�||��ƷƷ�����ڴ�'; 
  alter table gf_prod_asset_mutual_info add issue_num number(17);
  comment on column gf_prod_asset_mutual_info.issue_num
  is '��ƷƷ�����ڴ�||��ƷƷ�����ڴ�'; 
 --����֪ͨ
 alter table GF_SALE_NOTIFY_INFO add  CONTENT_BYTE	CLOB; 
     comment on column GF_SALE_NOTIFY_INFO.CONTENT_BYTE
  is '֪ͨ����||֪ͨ����'; 
 --�����ɱ���
delete from gf_dict  where dict_no='COST_ASSET_TYPE_P';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'ZGJH', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-�ʹܼƻ�', '', '', 2, '���˲�Ʒ�ɱ�������', '1', '114123', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'ZQ', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-ծȯ', '', '', 4, '���˲�Ʒ�ɱ�������', '1', '114208', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'CKL', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-���', '', '', 5, '���˲�Ʒ�ɱ�������', '1', '114227', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'SYPZ', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-����ƾ֤', '', '', 6, '���˲�Ʒ�ɱ�������', '1', '114253', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'OTHER', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-����', '', '', 7, '���˲�Ʒ�ɱ�������', '1', '114309', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_P', '���˱�����Ʒ�ɱ����ʲ�����', 'XTJH', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��������ʽ�-�ɱ�-���мƻ�', '', '', 1, '���˲�Ʒ�ɱ�������', '1', '114027', '20160720', '������');


delete from gf_dict  where dict_no='COST_ASSET_TYPE_O';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'OTHER', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-����', '', '', 7, '���˲�Ʒ�ɱ�������', '1', '114309', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'SYPZ', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-����ƾ֤', '', '', 6, '���˲�Ʒ�ɱ�������', '1', '114253', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'CKL', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-���', '', '', 5, '���˲�Ʒ�ɱ�������', '1', '114227', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'ZQ', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-ծȯ', '', '', 4, '���˲�Ʒ�ɱ�������', '1', '114208', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'ZGJH', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-�ʹܼƻ�', '', '', 2, '���˲�Ʒ�ɱ�������', '1', '114123', '20160720', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('COST_ASSET_TYPE_O', '��λ������Ʒ�ɱ����ʲ�����', 'XTJH', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-��λ����ʽ�-�ɱ�-���мƻ�', '', '', 1, '���˲�Ʒ�ɱ�������', '1', '114027', '20160720', '������');
  
/*********************20161228�������ݿ���*******����********************/

--���
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '���䷽��', '0', '����ʡ����������', '', '', 0, '������ʼ����', '1', '1', '20161021', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '���䷽��', '1', '����ʡ������������', '', '', 1, '������ʼ����', '1', '1', '20161021', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '���䷽��', '2', '������ʡ����������', '', '', 2, '������ʼ����', '1', '1', '20161021', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '���䷽��', '3', '������ʡ����������棬������������', '', '', 3, '������ʼ����', '1', '1', '20161021', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EDU_ASSIGN_PLAN', '���䷽��', '4', '��������棨������ʡ������������������ͬʱ����ʡ��', '', '', 4, '������ʼ����', '1', '1', '20161021', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_UP_LIMIT', '�Ƿ����ϼ�ȡ���', '0', '��', '', '', 2, '������ʼ����', '1', '1', '20161026', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_UP_LIMIT', '�Ƿ����ϼ�ȡ���', '1', '��', '', '', 1, '������ʼ����', '1', '1', '20161026', '�');

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
  is '��Ʋ�Ʒֱ��ϵͳ�����Ϣ||��Ʋ�Ʒֱ��ϵͳ�����Ϣ';
-- Add comments to the columns 
comment on column web_gf_limit_ctl.PROD_CODE
  is '��Ʋ�Ʒ����||��Ʋ�Ʒ�������λ������������ȣ�2�������֣�2������ƷƷ�ƴ��루2��+��Ʒ�ڴ�������4��';
comment on column web_gf_limit_ctl.ORGAN_CODE
  is '��������||��������';
comment on column web_gf_limit_ctl.CHANNEL
  is '����||����';
comment on column web_gf_limit_ctl.BEGIN_DATE
  is '��ʼ����||��ʼ����';
comment on column web_gf_limit_ctl.END_DATE
  is '��������||��������';
comment on column web_gf_limit_ctl.WAIT_SELL_LIMIT
  is '�������||�������';
comment on column web_gf_limit_ctl.CAN_SELL_LIMIT
  is '�������||�������';
comment on column web_gf_limit_ctl.SUM_ORDER_LIMIT
  is '��ԤԼ���||��ԤԼ���';
comment on column web_gf_limit_ctl.CAN_ORDER_LIMIT
  is '��ԤԼ���||��ԤԼ���';
comment on column web_gf_limit_ctl.SUM_BUY_LIMIT
  is '���Ϲ����||���Ϲ����';
comment on column web_gf_limit_ctl.CAN_BUY_LIMIT
  is '���Ϲ����||���Ϲ����';
comment on column web_gf_limit_ctl.SUM_PURCHASE_LIMIT
  is '���깺���||���깺���';
comment on column web_gf_limit_ctl.CAN_PURCHASE_LIMIT
  is '���깺���||���깺���';
comment on column web_gf_limit_ctl.IS_UP_LIMIT
  is '�Ƿ����ϼ�ȡ���||�Ƿ����ϼ�ȡ���:
0-�� 1-�� ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table web_gf_limit_ctl
  add constraint PK_web_gf_limit_ctl primary key (PROD_CODE, ORGAN_CODE, CHANNEL, BEGIN_DATE, END_DATE);

--web_prod_open_duration_info��gf_prod_open_duration_info
  alter table web_prod_open_duration_info add open_flag CHAR(1) default '1' not null;
comment on column web_prod_open_duration_info.open_flag
    is '���ű�־:0-�й��� 1-�޹���||���ű�־:0-�й��� 1-�޹���';
 alter table gf_prod_open_duration_info add open_flag CHAR(1) default '1' not null;
comment on column gf_prod_open_duration_info.open_flag
    is '���ű�־:0-�й��� 1-�޹���||���ű�־:0-�й��� 1-�޹���';

    
--  gf_prod_limit_detail
alter table gf_prod_limit_detail rename column  cust_type char(1) to  channel varchar2(2);
    comment on column gf_prod_limit_detail.channel is '����||����';
alter table gf_prod_limit_detail add trade_date date not null;       
comment on column gf_prod_limit_detail.trade_date
is '��������||��������';   
comment on column gf_prod_limit_detail.LIMIT_DEDUCTION_FLAG
  is '��ȿۼ���־||��ȿۼ���־ 1-ԤԼ,2-�Ϲ�,3-�깺��4ԤԼת�Ϲ�';      


--gf_prod_transf_note
alter table gf_prod_transf_note add  host_note varchar2(128);
 comment  on column gf_prod_transf_note.host_note
 is ' ������Ϣ|| ������Ϣ';  
alter table gf_prod_transf_note add  host_code varchar2(6);
 comment  on column gf_prod_transf_note.host_code
 is ' ����������|| ����������';      
--ta_endday_ctl    
alter table ta_endday_ctl add LOG_FILE	VARCHAR2(256);
comment on column ta_endday_ctl.LOG_FILE
  is '��־�ļ�||��־�ļ�';      
   
    


alter table web_prod_open_duration_rule drop column redeem_amt_handle_flag;
  alter table web_prod_open_duration_rule drop column redeem_quot_affirm_type;  
  alter table web_prod_open_duration_rule drop column purchase_amt_handle_flag;      
  alter table web_prod_open_duration_rule drop column purchase_quot_affirm_type;         

 alter table web_prod_open_duration_rule add amt_handle_flag	char(1);
 comment on column web_prod_open_duration_rule.amt_handle_flag
 is '�ʽ���ʽ||�깺�ۿʽ����ش�ʽ';     
    
alter table web_prod_open_duration_rule add quot_affirm_type	char(1);
comment on column web_prod_open_duration_rule.quot_affirm_type
  is '�ݶ�ȷ�Ϸ�ʽ||�깺����ݶ�ȷ�Ϸ�ʽ : 1-����ȷ��  2-ʵʱȷ��';     

alter table web_prod_open_duration_rule drop column PURCHASE_INTERVAL;
alter table web_prod_open_duration_rule drop column REDEEM_INTERVAL;

alter table gf_prod_open_duration_rule drop column PURCHASE_INTERVAL;
alter table gf_prod_open_duration_rule drop column REDEEM_INTERVAL;

 alter table gf_recv_capital_data_info
  add constraint gf_recv_capital_data_info primary key (capital_type);
   
   alter table gf_sell_trade_list_note add handle_name VARCHAR2(60);
  comment on column gf_sell_trade_list_note.handle_name
  is '����������||����������';
  alter table gf_sell_trade_list_note add handle_cert_type VARCHAR2(2);
  comment on column gf_sell_trade_list_note.handle_cert_type
  is '������֤������||������֤������';
  alter table gf_sell_trade_list_note add handle_cert_code VARCHAR2(20);
  comment on column gf_sell_trade_list_note.handle_cert_code
  is '������֤������||������֤������';
  alter table ta_special_trade_list_note modify re_application_code varchar2(20);
  
   alter table gf_prod_cust_white_list rename column  ACCT_CODE to  customerid;
  comment on column gf_prod_cust_white_list.customerid is '�м�ҵ�����˺�||�м�ҵ�����˺�';
 
drop table gf_limit_ctl;   -------------------------------------------------------------------------------------ִ�е�ʱ����Ҫע�⣬ԭ���Ķ�ȱ�
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
  is '��Ʋ�Ʒֱ��ϵͳ�����Ϣ||��Ʋ�Ʒֱ��ϵͳ�����Ϣ';
-- Add comments to the columns 
comment on column GF_LIMIT_CTL.PROD_CODE
  is '��Ʋ�Ʒ����||��Ʋ�Ʒ�������λ������������ȣ�2�������֣�2������ƷƷ�ƴ��루2��+��Ʒ�ڴ�������4��';
comment on column GF_LIMIT_CTL.ORGAN_CODE
  is '��������||��������';
comment on column GF_LIMIT_CTL.CHANNEL
  is '����||����';
comment on column GF_LIMIT_CTL.BEGIN_DATE
  is '��ʼ����||��ʼ����';
comment on column GF_LIMIT_CTL.END_DATE
  is '��������||��������';

--������Ϣ
insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z163', '�����ݶ�ܴ��ڿɹ����ݶ', '99370000000', 'z163', '�����ݶ�ܴ��ڿɹ����ݶ', '20160725', '145706', '����˧');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z164', '{0}������', '99370000000', 'z164', '{0}������', '20160725', '143534', '����˧');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z165', '{0}�쳣', '99370000000', 'z165', '{0}�쳣', '20160725', '143720', '����˧');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z166', '{0}�ĳ��Ȳ��ܳ���{1}��', '99370000000', 'z166', '{0}�ĳ��Ȳ��ܳ���{1}��', '20160725', '150651', '����˧');

insert into gf_err_info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z167', '��Ʒ����Ϊ{0}�Ĳ�Ʒ������!', '99370000000', 'z167', '��Ʒ����Ϊ{0}�Ĳ�Ʒ������!', '20160725', '150720', '����˧');

insert into gf_Err_Info (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'z168', '�ύʧ�ܣ�', '99370000000', 'z168', '�ύʧ�ܣ�', '20160820', '180731', '����˧');

����138��153 ��gf_prod_auto_open_info�����ֶ�  
counter_open_flag char(1) ��̨�Զ����б�־
counter_ctl_flag char(1)  ��̨�Զ����б�־
<<<<<<< .working


��gf_prod_cust_white_list   �޸��ֶΣ�ԭ�ֶ�customerid  �м�ҵ���˺ţ��޸�Ϊ TA_CODE   ta�˺�

=======
>>>>>>> .merge-right.r12567
--gf_dict
update gf_dict set dict_key_in='143'  where dict_no='D_TRADE_KIND' and dict_value_in='�ֺ�'
--gf_pub_acct_std_info
truncate table gf_pub_acct_std_info;
insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('68', '1115605003019613', '�������ƹ�������뻧', '241502004', '�������ƹ��������', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('67', '1115601000224188', '��������ʲ����滧', '123456789', '��������ʲ�����', '156', '1');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('66', '1115602000520856', '����ҵ��ծ-��ƻ�', '274006', '����ҵ��ծ-���', '156', '1');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('25', '1115601000066438', '�����������Ƽ��������ѻ�', '144501005', 'ֱ��ָ�����ʼ�ֵ���������ʲ�-���Ͷ��-������', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('53', '1115602000463631', '��λ��������ʽ����㻧', '241501004', 'ֱ��ָ�����ʼ�ֵ�������ڸ�ծ-��λ����ʽ�-���������', '156', '0');

insert into gf_pub_acct_std_info (ACCT_TYPE, IN_ACCT_CODE, IN_ACCT_NAME, BANK_ITEM_CODE, BANK_ITEM_NAME, CURRENCY, BREAK_EVEN_TYPE)
values ('57', '1115602000464827', '���˱�������ʽ����㻧', '241502004', 'ֱ��ָ�����ʼ�ֵ�������ڸ�ծ-��������ʽ�-���������', '156', '0');
--20161130
--��Ȳ�ѯ����  
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_FP', '�Ƿ����', '0', '��', '', '', 1, '������ʼ����', '1', '1', '20161130', '�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('IS_FP', '�Ƿ����', '1', '��', '', '', 2, '������ʼ����', '1', '1', '20161130', '�');
--20161201

--������
insert into GF_ERR_INFO (GF_SYS_CODE, GF_RET_CODE, GF_RET_INFO, OTHER_SYS_CODE, OTHER_RET_CODE, OTHER_RET_INFO, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('99370000000', 'w999', '{0}', '99370000000', 'w999', '{0}', '20160822', '161249', 'κ��');


����153��138
�� fd_publicpara  
�����ֶ�    highnetworthvaliddate   VARCHAR2(8)
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
  is '��Ʒ�˻���Ϣ��||��¼�������Ǳ�����Ʋ�Ʒ�ʽ�������ʹ�õĸ����ڲ���';
-- Add comments to the columns 
comment on column GF_PROD_ACCT_INFO.PROD_CODE
  is '��Ʋ�Ʒ����||��Ʋ�Ʒ�������(λ��):�������(2)+����(2)+��ƷƷ�ƴ���(2)+��Ʒ�ڴ�����(4)';
comment on column GF_PROD_ACCT_INFO.ACCT_TYPE
  is '�˻�����||�˻�����';
comment on column GF_PROD_ACCT_INFO.IN_ACCT_CODE
  is '�ڲ����ʺ�||�ڲ����ʺ�';
comment on column GF_PROD_ACCT_INFO.IN_ACCT_NAME
  is '�ڲ��˻�����||�ڲ��˻�����';
comment on column GF_PROD_ACCT_INFO.BANK_ITEM_CODE
  is '��ϸ��Ŀ���||��ϸ��Ŀ���';
comment on column GF_PROD_ACCT_INFO.BANK_ITEM_NAME
  is '��ϸ��Ŀ����||��ϸ��Ŀ����';
comment on column GF_PROD_ACCT_INFO.CURRENCY
  is '����||����';
comment on column GF_PROD_ACCT_INFO.BREAK_EVEN_TYPE
  is '��Ʒ��������||��Ʒ�������� 0-���� 1-������';
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
values ('120', '57', '52', '�Ϲ����', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('122', '57', '52', '�깺���', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('124', '52', '57', '��ؿ���𣩻���', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('151', '52', '57', '���ڿ���𣩻���', '01', '1');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('120', '53', '49', '�Ϲ����', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('122', '53', '49', '�깺���', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('124', '49', '53', '��ؿ���𣩻���', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('151', '49', '53', '���ڿ���𣩻���', '01', '2');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('200', '66', '67', '��ظ����滮��', '02', '0');

insert into ta_in_out_acct_ref (TRADE_KIND_CODE, OUT_ACCT_TYPE, IN_ACCT_TYPE, TRADE_KIND_NAME, PROFIT_TYPE, CUST_TYPE)
values ('201', '66', '67', '���ڸ����滮��', '02', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('0', '083000', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('1', '163000', '0');

--gf_dict  ���Ź���20161202
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '��������', '1', '�깺', '', '', 1, '', '1', '191924', '20160726', 'ʷΡ');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_PHASE_MODEL', '������ģʽ', '0', '�й��ɿ���', '', '', 1, '�������Ƿ񰴹��ɿ���', '1', '153404', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('HOLIDAY_MAINTENANCE', '�ڼ���ά��', '1', 'ȡ��', '', '', 2, '�ڼ����Ƿ�ά��', '1', '150144', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_PHASE_MODEL', '������ģʽ', '1', '�޹��ɿ���', '', '', 2, '�Ƿ񰴹��ɿ���', '1', '150119', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('HOLIDAY_MAINTENANCE', '�ڼ���ά��', '0', '˳��', '', '', 1, '�ڼ����Ƿ�ά��', '1', '154346', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '��������', '1', '��', '', '', 2, '�������ڣ������¼��꣩', '1', '145640', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '��������', '2', '��', '', '', 3, '�������ڣ������¼��꣩', '1', '145720', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '��������', '3', '��', '', '', 4, '�������ڣ������¼��꣩', '1', '145816', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('Open_Cycle', '��������', '4', '��', '', '', 5, '�������ڣ������¼��꣩', '1', '145910', '20160716', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Quot_Affirm_Type', '�깺����ݶ�ȷ�Ϸ�ʽ', '1', '����', '', '', 1, '', '1', '101233', '20160720', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Quot_Affirm_Type', '�깺����ݶ�ȷ�Ϸ�ʽ', '2', 'ʵʱ', '', '', 2, '', '1', '101249', '20160720', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Amt_Handle_Flag', '�깺�����ʽ���ʽ', '1', '����', '', '', 1, '', '1', '101322', '20160720', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('purchase_Amt_Handle_Flag', '�깺�����ʽ���ʽ', '2', 'ʵʱ', '', '', 2, '', '1', '101405', '20160720', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '��������', '2', '���', '', '', 2, '', '1', '192025', '20160726', 'ʷΡ');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('OPEN_TYPE', '��������', '3', '����Կ�', '', '', 3, '', '1', '192026', '20160726', 'ʷΡ');



--D_DEAL_STATUS
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '����״̬', '0', 'δ����', '', '', 1, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '����״̬', '1', '�������', '', '', 2, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '����״̬', '2', '����ɹ�', '', '', 3, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_DEAL_STATUS', '����״̬', '3', '����ʧ��', '', '', 4, '������ʼ����', '1', '1', '20160308', '������');

--TA_hK_NOTIFY_INFO��TA_hK_NOTIFY_INFO  ����ֶ�REUSLT_MSG
alter table TA_hK_NOTIFY_INFO add RESULT_MSG	VARCHAR2(60);
comment on column TA_hK_NOTIFY_INFO.RESULT_MSG
  is '������ϸ������Ϣ||������ϸ������Ϣ';  
  
alter table TA_sK_NOTIFY_INFO add RESULT_MSG	VARCHAR2(60);
comment on column TA_sK_NOTIFY_INFO.RESULT_MSG
  is '������ϸ������Ϣ||������ϸ������Ϣ';  
--gf_cict:D_FPFLAG   
DELETE from gf_dict t where t.dict_no='D_FPFLAG';
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '0', 'δ����', '', '', 1, '�����ѷ���ʱ', '1', '132358', '20160820', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '1', '����������', '', '', 2, '�����ѷ���ʱ', '1', '132420', '20160820', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '2', '�ȴ���̨����', '', '', 3, '�����ѷ���ʱ', '1', '132443', '20160820', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '3', '�Ѵ���', '', '', 4, '�����ѷ���ʱ', '1', '132456', '20160820', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '4', '�����쳣', '', '', 5, '�����ѷ���ʱ', '1', '132507', '20160820', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_FPFLAG', '���۷Ѵ���״̬', '5', '��������', '', '', 4, '�����ѷ���ʱ', '1', '132456', '20160820', '������');




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
  is '������������';
-- Add comments to the columns 
comment on column GF_FEE_DIS_APP.WORKFLOW_CODE
  is '�������̴���';
comment on column GF_FEE_DIS_APP.FEE_SERIAL_CODE
  is '�����ѷ������к�';
comment on column GF_FEE_DIS_APP.APP_STATUS
  is '����״̬';
comment on column GF_FEE_DIS_APP.FEE_SERIAL_DESC
  is '�����ѷ�������';
comment on column GF_FEE_DIS_APP.LATEST_MODIFY_DATE
  is '����޸�����';
comment on column GF_FEE_DIS_APP.LATEST_MODIFY_TIME
  is '����޸�ʱ��';
comment on column GF_FEE_DIS_APP.LATEST_OPER_CODE
  is '������Ա';
comment on column GF_FEE_DIS_APP.FPAMT
  is '�����������ܽ��';
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
  is '�����Ѽ�����';
comment on column GF_FEE_DIS_BATCH_APP.FEE_SERIAL_CODE
  is '�����ѷ������к�';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GF_FEE_DIS_BATCH_APP
  add constraint PK_GF_FEE_DIS_BATCH_APP primary key (JTFEECODE, FEE_SERIAL_CODE, WORKFLOW_CODE);
-- ��������������
insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask2', '228', '0', '0', '', '04');

insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('usertask1', '238', '0', '0', '', '04');

insert into acticiti_role_config (TASK_ID, ROLE_CODE, ORGAN_LEVEL, CUST_TYPE, APP_STATUS, ACTIVITI_TYPE)
values ('startevent1', '229', '0', '0', '', '04');
--WEB_GF_LIMIT_CTL
 alter table gf_limit_ctl add  initial_order_limit number(22) ; 
     comment on column gf_limit_ctl.initial_order_limit
  is '��ʼԤԼ���||��ʼԤԼ���';
  
   alter table gf_limit_ctl add  initial_buy_limit number(22) ; 
     comment on column gf_limit_ctl.initial_buy_limit
  is '��ʼ������||��ʼ������';
  
    alter table WEB_GF_LIMIT_CTL add  initial_order_limit number(22) ; 
     comment on column WEB_GF_LIMIT_CTL.initial_order_limit
  is '��ʼԤԼ���||��ʼԤԼ���';
  
   alter table WEB_GF_LIMIT_CTL add  initial_buy_limit number(22) ; 
     comment on column WEB_GF_LIMIT_CTL.initial_buy_limit
  is '��ʼ������||��ʼ������';
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
--TA�ǽ��׹������������ͱ�ʶ
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_TRANS_TYPE', '��������', '0', '��Ժǿ��ִ��', '', '', 123, '', '1', '174207', '20160711', '����˧');

insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_TRANS_TYPE', '��������', '1', '��ծ', '', '', 124, '', '1', '174232', '20160711', '����˧');
 

--20161213 gf_cict:D_CHNL
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_CHNL', '��������', '17', 'VTM��������', '', '', 11, '������ʼ����', '1', '165628', '20160707', '��־��');


--20161221 gf_cict:PROD_STATUSZ
insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_STATUSZ', '��Ʋ�Ʒ״̬', '0', 'δ����', '', '', 1, '������Ʋ�Ʒ״̬', '1', '172308', '20160711', '��־��');

insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_STATUSZ', '��Ʋ�Ʒ״̬', '4', '������', '', '', 2, '������Ʋ�Ʒ״̬', '1', '172359', '20160711', '��־��');
 
 
 --20161222 web_Menu_Info ��Ʋ�Ʒ״̬
insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('20C0BF5E871F433FB8B36AF2171802F4', 'TRADE-12', 'TRADE', 400012, '0', '/webGfProdStatus/listUI.htm', '��Ʋ�Ʒ״̬', '/libs/icons/edit.gif', '1', '1', '20160708', '170450', '��־��');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('1E897A524B1B4AF6A670208E96556A85', 'TRADE-12-02', 'TRADE-12', 401201, '0', '{text : ''�޸�'', click : onUpdate, iconClass : ''icon_edit''}', '�޸�', '/libs/icons/edit.gif', '1', '2', '20160710', '182851', '��־��');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('2F1C9D9D1C5A4213A49E19723550E806', 'TRADE-12-01', 'TRADE-12', 401202, '0', '{text : ''��ϸ��Ϣ'', click : onInfo, iconClass : ''icon_list''}', '��ϸ��Ϣ', '/libs/icons/list.gif', '1', '2', '20160710', '182906', '��־��');



--����������������ڼƻ��˵�
insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('D56FF92D9A55474BA293991FDE561097', 'TRADE-11', 'TRADE', 400011, '0', '/webProdOpenDurationInfo/listUI.htm', '����ʽ�����ڹ���', '/libs/icons/leaf.gif', '1', '1', '20160822', '143651', 'κ��');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('62180224E81948AC88F8B94D98A23D14', 'TRADE-11-01', 'TRADE-11', 401101, '0', '{text : ''����'', click : onCreate, iconClass : ''icon_add''}', '����', '/libs/icons/add.gif', '1', '2', '20160714', '161432', 'κ��');

insert into WEB_MENU_INFO (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('0CF2B37E045444B49FA9D97DD60BE94D', 'TRADE-11-02', 'TRADE-11', 401102, '0', '{text : ''��ϸ��Ϣ'', click : onInfo, iconClass : ''icon_list''}', '��ϸ��Ϣ', '/libs/icons/list.gif', '1', '2', '20160714', '161721', 'κ��');

--��ֵ�Ͳ�Ʒ˵��������
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('B69B6B963B394247B5494DE37DF97C63', 'GF-10-05', 'GF-10', 201005, '0', '{text : ''��ֵ�Ͳ�Ʒ˵��������'', click : onCreate, iconClass : ''icon_add''}', '��ֵ�Ͳ�Ʒ˵��������', '/libs/icons/add.gif', '1', '2', '20160713', '183235', 'gly');
--��ֵ�����ӳ���
alter table gf_prod_asset_mutual_info add netvalue_last_day  number(2) default '0';
comment on column gf_prod_asset_mutual_info.netvalue_last_day
  is '��ֵ�����ӳ���||��ֵ�����ӳ���'; 
alter table web_prod_asset_mutual_info add netvalue_last_day  number(2) default '0';
comment on column web_prod_asset_mutual_info.netvalue_last_day
  is '��ֵ�����ӳ���||��ֵ�����ӳ���';
  
  --20161223 ��Ӿ�ֵ�����ӳ��� �����˵�
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '1', '1', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '2', '2', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '3', '3', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '4', '4', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '5', '5', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '6', '6', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '7', '7', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '8', '8', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '9', '9', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '10', '10', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '11', '11', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '12', '12', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '13', '13', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '14', '14', '', '', 123456, '', '1', '100745', '20160713', '����˧');
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('LAST_DAY', '��ֵ�����ӳ���', '15', '15', '', '', 123456, '', '1', '100745', '20160713', '����˧');
--
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('DISTRIBUTE_FLAG', '�����ʶ', '0', 'δ����', '', '', 1, '', '1', '155851', '20160927', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('DISTRIBUTE_FLAG', '�����ʶ', '1', '�ѷ���', '', '', 2, '', '1', '155903', '20160927', '������');

--20161228�����Χ��Ʒ������
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702010', '��Ʋ�Ʒ���ԤԼ����Χ��', '', '', 1, '��ʼ��������', '1', '174646', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702030', '���׷��Ͷ�ʱ������Χ��', '', '', 3, '��ʼ��������', '1', '100219', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702000', '��Ʋ�Ʒ��������Χ��', '', '', 0, '��ʼ��������', '1', '100239', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702020', '��Ʋ�Ʒ�Ϲ�����Χ��', '', '', 2, '��ʼ��������', '1', '100308', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702040', '��Ʋ�Ʒ��ǰ��أ���Χ��', '', '', 4, '��ʼ��������', '1', '100322', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702050', '��Ʋ�Ʒ��ֹͶ�ʣ���Χ��', '', '', 5, '��ʼ��������', '1', '100342', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702100', '���ݺ�ͬ��/Э��Ų�ѯ��ƽ��ף���Χ��', '', '', 12, '��ʼ��������', '1', '100447', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702110', '��Ʋ�Ʒ�ʻ�����ѯ����Χ��', '', '', 6, '��ʼ��������', '1', '100426', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702120', '��Ʋ�Ʒ������ϸ��ѯ����Χ��', '', '', 7, '��ʼ��������', '1', '100409', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702220', '��Ʋ�ƷƷ�ֲ�ѯ����Χ��', '', '', 8, '��ʼ��������', '1', '100354', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702310', '��Ʋ�Ʒ�ֺ췽ʽ��ѯ����Χ��', '', '', 9, '��ʼ��������', '1', '100510', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702330', '��Ʋ�Ʒ�ݶ��ѯ����Χ��', '', '', 10, '��ʼ��������', '1', '100527', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702430', '�����Ƴ�Լ����Χ��', '', '', 17, '��ʼ��������', '1', '100558', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702340', '��Ʋ�ƷԤԼ��ѯ����Χ��', '', '', 11, '��ʼ��������', '1', '100542', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702440', '������ǩԼ��ѯ����Χ��', '', '', 18, '��ʼ��������', '1', '100159', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702420', '�����Ʊ������Χ��', '', '', 16, '��ʼ��������', '1', '095845', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702410', '������ǩԼ����Χ��', '', '', 15, '��ʼ��������', '1', '100023', '20160715', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROD_CTL_TRADE_CODE_PRIPHERAL', '�������׿��ƽ����루��Χ��', '702350', '��Ʋ�Ʒ�Ϲ���ѯ����Χ��', '', '', 14, '��ʼ��������', '1', '100118', '20160715', '��־��');

--20170113 ��� ���������һ�������� ����
insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('53F6E1D90D004388A07A5C98FA1C9FCE', 'TRADE-13', 'TRADE', 400013, '0', '/gfProdPurLastCancel/listUI.htm', '���������һ��������', '/libs/icons/leaf.gif', '1', '1', '20160718', '154706', '����˧');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E841434F2D294D15953AD44766CF12EA', 'TRADE-13-01', 'TRADE-13', 401301, '0', '{text : ''�޸�'', click : click : onUpdate, iconClass : ''icon_edit''}', '�޸�', '/libs/icons/edit.gif', '1', '2', '20160718', '155758', '����˧');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('C1B0E52B48384291BA8D91492CBCE18D', 'TRADE-13-02', 'TRADE-13', 401302, '0', '{text : ''��ϸ'', click : click : onInfo, iconClass : ''icon_list''}', '��ϸ', '/libs/icons/list.gif', '1', '2', '20160718', '155920', '����˧');

insert into web_Menu_Info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('ECFBB4CCCFA84CDAB1900A16681AF8A9', 'TRADE-13-03', 'TRADE-13', 401303, '0', '{text : ''����'', click : click : onInfo, iconClass : ''icon_list''}', '����', '/libs/icons/list.gif', '1', '2', '20160718', '105804', '����˧');

--20170117 ����Զ�ˢ�� ������ʱ����
insert into gf_Dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_REFRESH_INTERVAL', '�����¼��ˢ��ʱ��(��)', '30', '�Զ�������', '', '', 3, '', '1', '165530', '20160720', '����˧');

--20170118 ������ʱ�����
insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('0', '080000', '0');

insert into gf_Prod_Auto_Ctl_Time_Info (CTL_TYPE, CTL_TIME, USE_STATUS)
values ('1', '180000', '0');

--20170118 �Զ������й���
insert into gf_Prod_Auto_Open_Info (PROD_AUTO_OPEN_FLAG, E_CHANNEL_AUTO_OPEN_FLAG, COUNTER_OPEN_FLAG, COUNTER_CTL_FLAG)
values ('1', '1', '1', '1');

--20170324 �Ǽǲ���ѯ�������깺�����ϸ�����������մ�����
insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '�Ǽǲ�����', '06', '����������������ϸ', '', '', 6, '��ʼ��������', '1', '103100', '20170416', '��־��');

insert into GF_DICT (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '�Ǽǲ�����', '07', '���������մ���', '', '', 7, '��ʼ��������', '1', '142847', '20170304', '��־��');

--20170531����1�š�����������ϸ
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('BQF_REGISTER', '�Ǽǲ�����', '08', '����1��������ϸ��ѯ', '', '', 8, '��ʼ��������', '1', '142847', '20170304', '��־��');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('JG_REGISTER', '�Ǽǲ�����', '12', '����������ϸ�Ǽǲ�', '', '', 12, '������ʼ����', '1', '1', '20170522', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_TYPE', '��Ʒ����', '02', '���ʽ', '02', '���ʽ', 1, '', '1', '104800', '20170522', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('PROD_TYPE', '��Ʒ����', '04', '����ʽ', '04', '����ʽ', 2, '', '1', '104800', '20170522', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '�����ͻ�����', '1', '����', '', '', 0, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '�����ͻ�����', '2', '����', '', '', 1, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '�����ͻ�����', '3', 'ͬҵ', '', '', 2, '������ʼ����', '1', '1', '20160308', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('CUST_FLAG', '�����ͻ�����', '4', 'С��ҵ', '', '', 3, '������ʼ����', '1', '1', '20160308', '������');

--20170712��һ��ʽ���
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '036', '��Ԫ', '12', '�Ĵ�����Ԫ(AUD)', 1, '', '1', '015209', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '124', '��Ԫ', '10', '���ô�Ԫ(CAD)', 2, '', '1', '015748', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '156', '�����Ԫ', '01', '�����(CNY)', 0, '', '1', '014548', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '978', 'ŷԪ', '04', 'ŷԪ', 8, '', '1', '015748', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '344', '��Ԫ', '02', '��Ԫ(HKD)', 4, '', '1', '014354', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '392', '��Ԫ', '05', '��Ԫ(JPY)', 5, '', '1', '015112', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '826', 'Ӣ��', '08', 'Ӣ��', 6, '', '1', '015624', '20170712', '������');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('TR_CURRENCY', '����', '840', '��Ԫ', '03', '��Ԫ', 7, '', '1', '015209', '20170712', '������');

--20170726�����Ƿ�ͨ��
alter table WEB_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.SPPI  is '�����Ƿ�ͨ��||�����Ƿ�ͨ�� 0-��,1-��';

alter table GF_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.SPPI  is '�����Ƿ�ͨ��||�����Ƿ�ͨ�� 0-��,1-��';

--20170726�Ƽ���ʶ
alter table WEB_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.SPPI  is '�����Ƿ�ͨ��||�����Ƿ�ͨ�� 0-��,1-��';

alter table GF_PROD_ASSET_MUTUAL_INFO add SPPI VARCHAR2(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.SPPI  is '�����Ƿ�ͨ��||�����Ƿ�ͨ�� 0-��,1-��';

--20170815
alter table WEB_PROD_ASSET_MUTUAL_INFO add IS_RECOMMEND CHAR(1) default '0' ;
comment on column WEB_PROD_ASSET_MUTUAL_INFO.IS_RECOMMEND is '�Ƽ���־||�Ƽ���־ 0-�� 1-��';

alter table GF_PROD_ASSET_MUTUAL_INFO add IS_RECOMMEND CHAR(1) default '0' ;
comment on column GF_PROD_ASSET_MUTUAL_INFO.IS_RECOMMEND is '�Ƽ���־||�Ƽ���־ 0-�� 1-��';

--20170821 ��ͼ�������
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
   

--20171025 �ͻ��������� ��ӱ���
insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('D_PROP_CODE', '�ͻ���������', '6', '�¿ͻ��״�ǩԼר����Ʒ', '', '', 6, '�¿ͻ��״�ǩԼר����Ʒ', '1', '154336', '20171009', '99999901001');
 
 
--20180408 ���ҵ����׼�˵� 
insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('72321417118244A097D79B25F4FCD16B', 'TRADE-15', 'TRADE', 400015, '0', '/gfProdPerformanceInfo/listUI.htm', 'ҵ���Ƚϻ�׼ά��', '/libs/icons/leaf.gif', '1', '1', '20180414', '161127', '�׻۲�');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('CBC8F1E89A42459EB5A9212C7BFEB99D', 'TRADE-15-01', 'TRADE-15', 401501, '0', '{text : ''����'', click : onInsert, iconClass : ''icon_add''}', '����', '/libs/icons/add.gif', '1', '2', '20180414', '161459', '�׻۲�');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('3F8D3198EB4546E3A5D472246C0D33C7', 'TRADE-15-02', 'TRADE-15', 401502, '0', '{text : ''�޸�'', click : onUpdate, iconClass : ''icon_edit''}', '�޸�', '/libs/icons/edit.gif', '1', '2', '20180414', '161603', '�׻۲�');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('AB6E403D85064544A10E23926BECBC53', 'TRADE-15-03', 'TRADE-15', 401503, '0', '{text : ''ɾ��'', click : onDelete, iconClass : ''icon_delete''}', 'ɾ��', '/libs/icons/delete.gif', '1', '2', '20180414', '161647', '�׻۲�');

insert into web_menu_info (ID, MENU_NO, UP_MENU_NO, ORDER_NO, IS_PARENT, MENU_URL, MENU_NAME, MENU_ICON, MENU_STATUS, MENU_TYPE, LATEST_MODIFY_DATE, LATEST_MODIFY_TIME, LATEST_OPER_CODE)
values ('E98CD450A9504D1D8A446BEDC70B6D34', 'TRADE-15-04', 'TRADE-15', 401504, '0', '{text : ''��ϸ'', click : onInfo, iconClass : ''icon_list''}', '��ϸ', '/libs/icons/list.gif', '1', '2', '20180414', '161739', '�׻۲�');


--20180408 ר����־
alter table gf_prod_base_info add EXCLUSIVE_FLAG CHAR(1);
comment on column gf_prod_base_info.EXCLUSIVE_FLAG is 'ר����־||ר����־��1 ����ר�� 2 �ڼ���ר��';

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EXCLUSIVE_FLAG', 'ר����־', '1', '����ר��', '', '', 1, '��ʼ��������', '1', '100454', '20180408', '�׻۲�');

insert into gf_dict (DICT_NO, DICT_NAME, DICT_KEY_IN, DICT_VALUE_IN, DICT_KEY_OUT, DICT_VALUE_OUT, DICT_NO_ORDER, DICT_DESC, DICT_STATUS, UPDATE_TIME, UPDATE_DATE, CREATE_OPER)
values ('EXCLUSIVE_FLAG', 'ר����־', '2', '�ڼ���ר��', '', '', 2, '��ʼ��������', '1', '100454', '20180408', '�׻۲�');


--201804017 ҵ����׼����
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
  is '��Ʋ�Ʒ����||ͨ����ͼ��ѯƷ����Ϣ����Ʋ�Ʒ�������λ������������ȣ�2�������֣�2������ƷƷ�ƴ��루2��+��Ʒ�ڴ�������4��';
comment on column WEB_PROD_PERFORMANCE_INFO.PERFORMANCE_DATE
  is '��������||��������';
comment on column WEB_PROD_PERFORMANCE_INFO.PERFORMANCE_VALUE
  is 'ҵ���Ƚϻ�׼||ҵ���Ƚϻ�׼';
comment on column WEB_PROD_PERFORMANCE_INFO.IS_PEG
  is '�Ƿ�ҹ�||�Ƿ�ҹ�';
comment on column WEB_PROD_PERFORMANCE_INFO.PEG_DESCRIPTION
  is '�ҹ�����||�ҹ�����';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_MODIFY_DATE
  is '�޸�����||�޸�����';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_MODIFY_TIME
  is '�޸�ʱ��||�޸�ʱ��';
comment on column WEB_PROD_PERFORMANCE_INFO.LATEST_OPER_CODE
  is '����޸Ĳ���Ա||����޸Ĳ���Ա';  

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
  is '��Ʒҵ���Ƚϻ�׼��Ϣ��||�ñ��ž�ֵ��Ʒҵ���Ƚϻ�׼';
-- Add comments to the columns 
comment on column GF_PROD_PERFORMANCE_INFO.PROD_CODE
  is '��Ʋ�Ʒ����||ͨ����ͼ��ѯƷ����Ϣ����Ʋ�Ʒ�������λ������������ȣ�2�������֣�2������ƷƷ�ƴ��루2��+��Ʒ�ڴ�������4��';
comment on column GF_PROD_PERFORMANCE_INFO.PERFORMANCE_DATE
  is '��������||��������';
comment on column GF_PROD_PERFORMANCE_INFO.PERFORMANCE_VALUE
  is 'ҵ���Ƚϻ�׼||ҵ���Ƚϻ�׼';
comment on column GF_PROD_PERFORMANCE_INFO.IS_PEG
  is '�Ƿ�ҹ�||�Ƿ�ҹ�';
comment on column GF_PROD_PERFORMANCE_INFO.PEG_DESCRIPTION
  is '�ҹ�����||�ҹ�����';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_MODIFY_DATE
  is '�޸�����||�޸�����';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_MODIFY_TIME
  is '�޸�ʱ��||�޸�ʱ��';
comment on column GF_PROD_PERFORMANCE_INFO.LATEST_OPER_CODE
  is '����޸Ĳ���Ա||����޸Ĳ���Ա';
comment on table WEB_PROD_PERFORMANCE_INFO
  is '��Ʒҵ���Ƚϻ�׼��Ϣ��||�ñ��ž�ֵ��Ʒҵ���Ƚϻ�׼';
  
  

