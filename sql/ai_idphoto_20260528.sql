-- AI证件照模块数据库脚本
-- 创建时间: 2026-05-28

-- 证件照规格表
drop table if exists ai_idphoto_spec;
create table ai_idphoto_spec (
  spec_id         bigint(20)      not null auto_increment    comment '规格ID',
  spec_name       varchar(50)     not null default ''        comment '规格名称',
  width_px        int(4)          not null                   comment '宽度(像素)',
  height_px       int(4)          not null                   comment '高度(像素)',
  width_mm        decimal(5,2)    default null               comment '宽度(毫米)',
  height_mm       decimal(5,2)    default null               comment '高度(毫米)',
  price           decimal(8,2)    not null default 0.00      comment '单价(元)',
  sort_order      int(4)          default 0                  comment '显示顺序',
  status          char(1)         default '0'                comment '状态(0正常 1停用)',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (spec_id)
) engine=innodb auto_increment=1 default charset=utf8mb4 comment = 'AI证件照规格表';

-- 证件照订单表
drop table if exists ai_idphoto_order;
create table ai_idphoto_order (
  order_id        bigint(20)      not null auto_increment    comment '订单ID',
  order_no        varchar(64)     not null                   comment '订单号',
  user_id         bigint(20)      not null                   comment '用户ID',
  spec_id         bigint(20)      not null                   comment '规格ID',
  spec_name       varchar(50)     not null default ''        comment '规格名称',
  background      varchar(10)     not null default '蓝底'     comment '背景色',
  beauty          varchar(10)     not null default '自然'     comment '美颜级别',
  suit            varchar(10)     not null default '不开启'   comment '正装类型',
  original_url    varchar(500)    not null default ''        comment '原图URL',
  result_url      varchar(500)    not null default ''        comment '结果图URL',
  amount          decimal(8,2)    not null default 0.00      comment '应付金额',
  status          char(1)         default '0'                comment '状态(0待支付 1已支付 2已取消 3已退款)',
  pay_time        datetime                                   comment '支付时间',
  pay_type        varchar(20)     default ''                 comment '支付方式(wechat)',
  transaction_id  varchar(64)     default ''                 comment '支付流水号',
  expire_time     datetime                                   comment '订单过期时间',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (order_id),
  unique key uk_order_no (order_no),
  key idx_user_id (user_id),
  key idx_status (status)
) engine=innodb auto_increment=1 default charset=utf8mb4 comment = 'AI证件照订单表';

-- 初始规格数据
insert into ai_idphoto_spec values(1, '一寸照',  295, 413, 25, 35,  9.90, 1, '0', 'admin', sysdate(), '', null, '标准一寸证件照');
insert into ai_idphoto_spec values(2, '二寸照',  413, 579, 35, 49,  9.90, 2, '0', 'admin', sysdate(), '', null, '标准二寸证件照');
insert into ai_idphoto_spec values(3, '公务员考试', 480, 640, null, null, 9.90, 3, '0', 'admin', sysdate(), '', null, '公务员报名专用');
insert into ai_idphoto_spec values(4, '教资报名', 240, 320, null, null, 9.90, 4, '0', 'admin', sysdate(), '', null, '教师资格证报名');
insert into ai_idphoto_spec values(5, '求职简历照', 390, 567, null, null, 9.90, 5, '0', 'admin', sysdate(), '', null, '简历专用照片');
