-- AI 微信客服：商品、客户、消息、开放 API Key
-- 在业务库执行一次

CREATE TABLE IF NOT EXISTS kf_product (
  product_id      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  product_name    VARCHAR(200) NOT NULL DEFAULT '' COMMENT '名称',
  price           DECIMAL(12,2)         DEFAULT NULL COMMENT '价格',
  intro           VARCHAR(2000)        DEFAULT '' COMMENT '简介',
  keywords        VARCHAR(500)         DEFAULT '' COMMENT '关键词/标签，逗号分隔',
  status          CHAR(1)      NOT NULL DEFAULT '0' COMMENT '0上架 1下架',
  sort_order      INT          NOT NULL DEFAULT 0 COMMENT '排序',
  create_by       VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  create_time     DATETIME     DEFAULT NULL,
  update_by       VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  update_time     DATETIME     DEFAULT NULL,
  remark          VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (product_id),
  KEY idx_kf_product_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服推荐商品';

CREATE TABLE IF NOT EXISTS kf_customer (
  customer_id       BIGINT       NOT NULL AUTO_INCREMENT,
  external_userid   VARCHAR(128) NOT NULL COMMENT '微信客服 external_userid',
  open_kfid         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '客服账号ID',
  nickname          VARCHAR(200) DEFAULT '' COMMENT '昵称(若有)',
  remark            VARCHAR(500) DEFAULT NULL,
  first_contact_time DATETIME    DEFAULT NULL,
  last_active_time   DATETIME    DEFAULT NULL,
  create_time       DATETIME     DEFAULT NULL,
  update_time       DATETIME     DEFAULT NULL,
  PRIMARY KEY (customer_id),
  UNIQUE KEY uk_kf_customer_ext_kf (external_userid, open_kfid(64)),
  KEY idx_kf_customer_last (last_active_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信客服客户';

CREATE TABLE IF NOT EXISTS kf_message (
  message_id       BIGINT       NOT NULL AUTO_INCREMENT,
  msgid            VARCHAR(128) NOT NULL DEFAULT '' COMMENT '企微消息ID',
  external_userid  VARCHAR(128) NOT NULL DEFAULT '',
  open_kfid        VARCHAR(128) NOT NULL DEFAULT '',
  direction        CHAR(1)      NOT NULL COMMENT '1客户->企 2企->客户',
  msgtype          VARCHAR(32)  DEFAULT 'text',
  content          VARCHAR(4000) DEFAULT NULL,
  send_time        BIGINT       DEFAULT NULL COMMENT '企微 send_time 秒',
  create_time      DATETIME     DEFAULT NULL,
  PRIMARY KEY (message_id),
  UNIQUE KEY uk_kf_message_msgid (msgid(64)),
  KEY idx_kf_message_user (external_userid(64), create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服消息流水';

CREATE TABLE IF NOT EXISTS kf_open_api_key (
  key_id       BIGINT       NOT NULL AUTO_INCREMENT,
  api_key      VARCHAR(128) NOT NULL COMMENT '密钥明文或hash前展示',
  api_secret   VARCHAR(128) NOT NULL COMMENT '存储hash或明文(首版简单明文比对)',
  name         VARCHAR(100) DEFAULT '',
  enabled      CHAR(1)      NOT NULL DEFAULT '0' COMMENT '0启用 1停用',
  expire_time  DATETIME     DEFAULT NULL COMMENT '过期时间，空表示不过期',
  create_by    VARCHAR(64)  DEFAULT '',
  create_time  DATETIME     DEFAULT NULL,
  update_by    VARCHAR(64)  DEFAULT '',
  update_time  DATETIME     DEFAULT NULL,
  remark       VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (key_id),
  UNIQUE KEY uk_kf_api_key (api_key(64))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开放API密钥';

-- 微信客服 sync_msg 游标（按 open_kfid 持久化，避免重复拉取异常）
CREATE TABLE IF NOT EXISTS kf_sync_cursor (
  open_kfid    VARCHAR(128) NOT NULL,
  next_cursor  VARCHAR(256) NOT NULL DEFAULT '',
  update_time  DATETIME     DEFAULT NULL,
  PRIMARY KEY (open_kfid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sync_msg 游标';

-- 若依菜单（执行后超级管理员可见；普通角色需在「角色管理」中勾选菜单）
INSERT INTO sys_menu VALUES('2200', 'AI客服', '0', '6', 'kf', null, '', '', 1, 0, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '微信客服与开放接口');
INSERT INTO sys_menu VALUES('2201', '客服商品', '2200', '1', 'product', 'kf/product/index', '', '', 1, 0, 'C', '0', '0', 'kf:product:list', 'shopping', 'admin', sysdate(), '', null, '推荐商品库');
INSERT INTO sys_menu VALUES('2202', '客服客户', '2200', '2', 'customer', 'kf/customer/index', '', '', 1, 0, 'C', '0', '0', 'kf:customer:list', 'peoples', 'admin', sysdate(), '', null, '客户列表');
INSERT INTO sys_menu VALUES('2203', '客服消息', '2200', '3', 'message', 'kf/message/index', '', '', 1, 0, 'C', '0', '0', 'kf:message:list', 'log', 'admin', sysdate(), '', null, '消息流水');
INSERT INTO sys_menu VALUES('2204', '开放API密钥', '2200', '4', 'openkey', 'kf/openkey/index', '', '', 1, 0, 'C', '0', '0', 'kf:openkey:list', 'key', 'admin', sysdate(), '', null, '对外开放凭证');
INSERT INTO sys_menu VALUES('2210', '商品查询', '2201', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:product:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2211', '商品新增', '2201', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:product:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2212', '商品修改', '2201', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:product:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2213', '商品删除', '2201', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:product:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2214', '商品导出', '2201', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:product:export', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2220', '客户查询', '2202', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:customer:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2230', '消息查询', '2203', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:message:list', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2240', '密钥查询', '2204', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:openkey:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2241', '密钥新增', '2204', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:openkey:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2242', '密钥修改', '2204', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:openkey:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2243', '密钥删除', '2204', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'kf:openkey:remove', '#', 'admin', sysdate(), '', null, '');
