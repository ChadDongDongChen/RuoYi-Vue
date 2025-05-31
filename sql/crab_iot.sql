-- ----------------------------
-- 1、螃蟹养殖池信息表
-- ----------------------------
drop table if exists crab_pool;
create table crab_pool (
  pool_id           bigint(20)      not null auto_increment    comment '养殖池ID',
  pool_name         varchar(50)     not null                   comment '养殖池名称',
  pool_area         decimal(10,2)   not null                   comment '养殖池面积(平方米)',
  pool_depth        decimal(5,2)    not null                   comment '养殖池深度(米)',
  pool_type         char(1)         default '0'                comment '养殖池类型（0室内 1室外）',
  status            char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (pool_id)
) engine=innodb auto_increment=100 comment = '螃蟹养殖池信息表';

-- ----------------------------
-- 2、环境监测设备表
-- ----------------------------
drop table if exists crab_device;
create table crab_device (
  device_id         bigint(20)      not null auto_increment    comment '设备ID',
  device_name       varchar(50)     not null                   comment '设备名称',
  device_type       char(1)         not null                   comment '设备类型（1温度 2湿度 3水质 4氧气）',
  pool_id           bigint(20)      not null                   comment '所属养殖池ID',
  device_status     char(1)         default '0'                comment '设备状态（0正常 1故障 2离线）',
  last_online_time  datetime                                   comment '最后在线时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (device_id)
) engine=innodb auto_increment=100 comment = '环境监测设备表';

-- ----------------------------
-- 3、环境数据记录表
-- ----------------------------
drop table if exists crab_environment_data;
create table crab_environment_data (
  data_id           bigint(20)      not null auto_increment    comment '数据ID',
  device_id         bigint(20)      not null                   comment '设备ID',
  pool_id           bigint(20)      not null                   comment '养殖池ID',
  data_type         char(1)         not null                   comment '数据类型（1温度 2湿度 3水质 4氧气）',
  data_value        decimal(10,2)   not null                   comment '数据值',
  collect_time      datetime        not null                   comment '采集时间',
  create_time       datetime                                   comment '创建时间',
  primary key (data_id)
) engine=innodb auto_increment=100 comment = '环境数据记录表';

-- ----------------------------
-- 4、投喂记录表
-- ----------------------------
drop table if exists crab_feeding_record;
create table crab_feeding_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  pool_id           bigint(20)      not null                   comment '养殖池ID',
  feed_type         varchar(50)     not null                   comment '饲料类型',
  feed_amount       decimal(10,2)   not null                   comment '投喂量(kg)',
  feed_time         datetime        not null                   comment '投喂时间',
  operator          varchar(64)     not null                   comment '操作人',
  create_time       datetime                                   comment '创建时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '投喂记录表';

-- ----------------------------
-- 5、螃蟹批次信息表
-- ----------------------------
drop table if exists crab_batch;
create table crab_batch (
  batch_id          bigint(20)      not null auto_increment    comment '批次ID',
  batch_name        varchar(50)     not null                   comment '批次名称',
  pool_id           bigint(20)      not null                   comment '养殖池ID',
  crab_type         varchar(50)     not null                   comment '螃蟹品种',
  initial_count     int(11)         not null                   comment '初始数量',
  initial_weight    decimal(10,2)   not null                   comment '初始平均重量(g)',
  start_date        date            not null                   comment '开始养殖日期',
  expected_date     date                                       comment '预计收获日期',
  status            char(1)         default '0'                comment '状态（0养殖中 1已收获）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (batch_id)
) engine=innodb auto_increment=100 comment = '螃蟹批次信息表';

-- ----------------------------
-- 6、螃蟹生长记录表
-- ----------------------------
drop table if exists crab_growth_record;
create table crab_growth_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  batch_id          bigint(20)      not null                   comment '批次ID',
  sample_count      int(11)         not null                   comment '采样数量',
  avg_weight        decimal(10,2)   not null                   comment '平均重量(g)',
  mortality_rate    decimal(5,2)    not null                   comment '死亡率(%)',
  record_date       date            not null                   comment '记录日期',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '螃蟹生长记录表';

-- ----------------------------
-- 7、异常预警记录表
-- ----------------------------
drop table if exists crab_alert_record;
create table crab_alert_record (
  alert_id          bigint(20)      not null auto_increment    comment '预警ID',
  pool_id           bigint(20)      not null                   comment '养殖池ID',
  device_id         bigint(20)      not null                   comment '设备ID',
  alert_type        char(1)         not null                   comment '预警类型（1温度异常 2水质异常 3设备故障）',
  alert_value       decimal(10,2)   not null                   comment '预警值',
  alert_level       char(1)         default '0'                comment '预警级别（0一般 1重要 2紧急）',
  alert_status      char(1)         default '0'                comment '处理状态（0未处理 1已处理）',
  alert_time        datetime        not null                   comment '预警时间',
  handle_time       datetime                                   comment '处理时间',
  handle_by         varchar(64)                                comment '处理人',
  handle_result     varchar(500)                               comment '处理结果',
  create_time       datetime                                   comment '创建时间',
  primary key (alert_id)
) engine=innodb auto_increment=100 comment = '异常预警记录表';

-- ----------------------------
-- 8、养殖池维护记录表
-- ----------------------------
drop table if exists crab_maintenance_record;
create table crab_maintenance_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  pool_id           bigint(20)      not null                   comment '养殖池ID',
  maintenance_type  varchar(50)     not null                   comment '维护类型',
  maintenance_date  date            not null                   comment '维护日期',
  operator          varchar(64)     not null                   comment '操作人',
  cost              decimal(10,2)                              comment '维护费用',
  create_time       datetime                                   comment '创建时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '养殖池维护记录表';

-- ----------------------------
-- 9、收获记录表
-- ----------------------------
drop table if exists crab_harvest_record;
create table crab_harvest_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  batch_id          bigint(20)      not null                   comment '批次ID',
  harvest_date      date            not null                   comment '收获日期',
  total_weight      decimal(10,2)   not null                   comment '总重量(kg)',
  avg_weight        decimal(10,2)   not null                   comment '平均重量(g)',
  survival_rate     decimal(5,2)    not null                   comment '存活率(%)',
  operator          varchar(64)     not null                   comment '操作人',
  create_time       datetime                                   comment '创建时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '收获记录表';

-- ----------------------------
-- 10、养殖成本记录表
-- ----------------------------
drop table if exists crab_cost_record;
create table crab_cost_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  batch_id          bigint(20)      not null                   comment '批次ID',
  cost_type         varchar(50)     not null                   comment '成本类型',
  cost_amount       decimal(10,2)   not null                   comment '成本金额',
  cost_date         date            not null                   comment '发生日期',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '养殖成本记录表';

-- ----------------------------
-- 测试数据
-- ----------------------------

-- 养殖池信息
insert into crab_pool values(100, '1号养殖池', 100.00, 1.50, '0', '0', 'admin', sysdate(), '', null, '室内养殖池');
insert into crab_pool values(101, '2号养殖池', 150.00, 1.80, '1', '0', 'admin', sysdate(), '', null, '室外养殖池');
insert into crab_pool values(102, '3号养殖池', 120.00, 1.60, '0', '0', 'admin', sysdate(), '', null, '室内养殖池');

-- 环境监测设备
insert into crab_device values(100, '温度传感器1', '1', 100, '0', sysdate(), 'admin', sysdate(), '', null, '1号池温度传感器');
insert into crab_device values(101, '水质监测器1', '3', 100, '0', sysdate(), 'admin', sysdate(), '', null, '1号池水质监测器');
insert into crab_device values(102, '氧气监测器1', '4', 100, '0', sysdate(), 'admin', sysdate(), '', null, '1号池氧气监测器');
insert into crab_device values(103, '温度传感器2', '1', 101, '0', sysdate(), 'admin', sysdate(), '', null, '2号池温度传感器');
insert into crab_device values(104, '水质监测器2', '3', 101, '0', sysdate(), 'admin', sysdate(), '', null, '2号池水质监测器');

-- 环境数据记录
insert into crab_environment_data values(100, 100, 100, '1', 25.50, sysdate(), sysdate());
insert into crab_environment_data values(101, 101, 100, '3', 7.20, sysdate(), sysdate());
insert into crab_environment_data values(102, 102, 100, '4', 6.80, sysdate(), sysdate());
insert into crab_environment_data values(103, 103, 101, '1', 26.20, sysdate(), sysdate());
insert into crab_environment_data values(104, 104, 101, '3', 7.50, sysdate(), sysdate());

-- 螃蟹批次信息
insert into crab_batch values(100, '2024春季批次1', 100, '大闸蟹', 1000, 50.00, '2024-03-01', '2024-09-01', '0', 'admin', sysdate(), '', null, '春季养殖批次');
insert into crab_batch values(101, '2024春季批次2', 101, '大闸蟹', 1200, 45.00, '2024-03-15', '2024-09-15', '0', 'admin', sysdate(), '', null, '春季养殖批次');

-- 投喂记录
insert into crab_feeding_record values(100, 100, '专用饲料', 5.00, sysdate(), 'admin', sysdate(), '日常投喂');
insert into crab_feeding_record values(101, 100, '小鱼虾', 3.00, sysdate(), 'admin', sysdate(), '补充蛋白质');
insert into crab_feeding_record values(102, 101, '专用饲料', 6.00, sysdate(), 'admin', sysdate(), '日常投喂');

-- 生长记录
insert into crab_growth_record values(100, 100, 50, 75.50, 2.50, sysdate(), 'admin', sysdate(), '月度生长记录');
insert into crab_growth_record values(101, 101, 60, 65.80, 3.00, sysdate(), 'admin', sysdate(), '月度生长记录');

-- 异常预警记录
insert into crab_alert_record values(100, 100, 100, '1', 28.50, '1', '0', sysdate(), null, null, null, sysdate());
insert into crab_alert_record values(101, 101, 104, '3', 8.50, '2', '1', sysdate(), sysdate(), 'admin', '已调整水质', sysdate());

-- 养殖池维护记录
insert into crab_maintenance_record values(100, 100, '水质调节', sysdate(), 'admin', 500.00, sysdate(), '定期水质维护');
insert into crab_maintenance_record values(101, 101, '设备检修', sysdate(), 'admin', 300.00, sysdate(), '设备定期检修');

-- 收获记录
insert into crab_harvest_record values(100, 100, '2023-09-01', 450.00, 150.00, 90.00, 'admin', sysdate(), '秋季收获');
insert into crab_harvest_record values(101, 101, '2023-09-15', 500.00, 145.00, 92.00, 'admin', sysdate(), '秋季收获');

-- 养殖成本记录
insert into crab_cost_record values(100, 100, '饲料成本', 5000.00, '2024-03-01', 'admin', sysdate(), '初始饲料采购');
insert into crab_cost_record values(101, 100, '设备成本', 3000.00, '2024-03-01', 'admin', sysdate(), '监测设备采购');
insert into crab_cost_record values(102, 101, '饲料成本', 6000.00, '2024-03-15', 'admin', sysdate(), '初始饲料采购'); 