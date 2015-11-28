CREATE TABLE ng_account (
  id                 BIGINT         NOT NULL auto_increment,
  code_sn            CHAR(20)       NOT NULL,
  entity_status      TINYINT        NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  account_status     TINYINT        NOT NULL,
  cash               DECIMAL(10, 2) NOT NULL,
  extra_info         TEXT,
  frozen_cash        DECIMAL(10, 2) NOT NULL,
  frozen_point       DECIMAL(10, 0) NOT NULL,
  frozen_voucher     DECIMAL(10, 2) NOT NULL,
  info               VARCHAR(255),
  name               VARCHAR(255),
  name_shadow        VARCHAR(255)   NOT NULL,
  point              DECIMAL(10, 0) NOT NULL,
  type               TINYINT        NOT NULL,
  voucher            DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id)
)
CREATE TABLE ng_cash_balance (
  id                    BIGINT         NOT NULL auto_increment,
  code_sn               CHAR(20)       NOT NULL,
  entity_status         TINYINT        NOT NULL,
  last_modified_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  extra_info            MEDIUMTEXT,
  from_account_remained DECIMAL(10, 4) NOT NULL,
  to_account_remained   DECIMAL(10, 4) NOT NULL,
  trade_amount          DECIMAL(10, 4) NOT NULL,
  trade_info            TEXT,
  bound_order           CHAR(20)       NOT NULL,
  from_Account          CHAR(20)       NOT NULL,
  from_user             CHAR(20)       NOT NULL,
  to_account            CHAR(20)       NOT NULL,
  to_user               CHAR(20)       NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_global_dict (
  id                 BIGINT   NOT NULL auto_increment,
  code_sn            CHAR(20) NOT NULL,
  entity_status      TINYINT  NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  big_options        MEDIUMTEXT,
  big_value          MEDIUMTEXT,
  dict_name          VARCHAR(255),
  options            TEXT,
  value              TEXT,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_order (
  id                     BIGINT         NOT NULL auto_increment,
  code_sn                CHAR(20)       NOT NULL,
  entity_status          TINYINT        NOT NULL,
  last_modified_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  cash_amount            DECIMAL(10, 2) NOT NULL,
  cash_pre_lock          DECIMAL(10, 2) NOT NULL,
  created_time           DATETIME       NOT NULL,
  info                   TEXT,
  message                TEXT,
  order_status           TINYINT        NOT NULL,
  paid_time              DATETIME,
  pointAmount            DECIMAL(10, 0) NOT NULL,
  sent_time              DATETIME,
  total_amount           DECIMAL(10, 2) NOT NULL,
  trade_direction        TINYINT        NOT NULL,
  voucher_amount         DECIMAL(10, 2) NOT NULL,
  buyer_code_sn          CHAR(20)       NOT NULL,
  buyer_account_code_sn  CHAR(20)       NOT NULL,
  parent_order_code_sn   CHAR(20),
  seller_code_sn         CHAR(20)       NOT NULL,
  seller_account_code_sn CHAR(20)       NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_order_order_sku (
  order_code_sn      CHAR(20)       NOT NULL,
  cash               DECIMAL(10, 2) NOT NULL,
  entity_status      TINYINT        NOT NULL,
  info               TEXT,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  point              DECIMAL(10, 0) NOT NULL,
  quantity           DECIMAL(10, 3) NOT NULL,
  sku_code_sn        CHAR(20),
  total              DECIMAL(10, 2) NOT NULL,
  voucher            DECIMAL(10, 2) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_point_balance (
  id                    BIGINT         NOT NULL auto_increment,
  code_sn               CHAR(20)       NOT NULL,
  entity_status         TINYINT        NOT NULL,
  last_modified_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  extra_info            MEDIUMTEXT,
  from_account_remained DECIMAL(10, 4) NOT NULL,
  to_account_remained   DECIMAL(10, 4) NOT NULL,
  trade_amount          DECIMAL(10, 4) NOT NULL,
  trade_info            TEXT,
  bound_order           CHAR(20)       NOT NULL,
  from_Account          CHAR(20)       NOT NULL,
  from_user             CHAR(20)       NOT NULL,
  to_account            CHAR(20)       NOT NULL,
  to_user               CHAR(20)       NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_producer_consumer (
  id                 BIGINT   NOT NULL auto_increment,
  code_sn            CHAR(20) NOT NULL,
  entity_status      TINYINT  NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  big_value          TEXT,
  flag               BIT      NOT NULL,
  hash_code          BIGINT   NOT NULL,
  large_value        MEDIUMTEXT,
  middle_value       VARCHAR(4095),
  size_type          TINYINT,
  small_value        VARCHAR(1023),
  target_code_sn     CHAR(20) NOT NULL,
  tiny_value         VARCHAR(255),
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_product (
  id                 BIGINT   NOT NULL auto_increment,
  code_sn            CHAR(20) NOT NULL,
  entity_status      TINYINT  NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  extra_info         TEXT,
  info               TEXT,
  long_name          VARCHAR(1023),
  name               VARCHAR(255),
  creater_code_sn    CHAR(20),
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_product_product_prop (
  product_code_sn    CHAR(20)     NOT NULL,
  default_value      VARCHAR(255) NOT NULL,
  entity_status      TINYINT      NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  prop_name          VARCHAR(255) NOT NULL,
  value_candidates   TEXT,
  PRIMARY KEY (product_code_sn, default_value, entity_status, last_modified_time, prop_name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_residence (
  id                 BIGINT   NOT NULL auto_increment,
  code_sn            CHAR(20) NOT NULL,
  entity_status      TINYINT  NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  address            VARCHAR(255),
  latitude           DECIMAL(12, 8),
  longtitude         DECIMAL(12, 8),
  name               CHAR(64),
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_sku (
  id                     BIGINT         NOT NULL auto_increment,
  code_sn                CHAR(20)       NOT NULL,
  entity_status          TINYINT        NOT NULL,
  last_modified_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  activities_id          BIGINT,
  inventory              DECIMAL(10, 3) NOT NULL,
  limitation             TINYINT        NOT NULL,
  merchandises_id        BIGINT,
  offline_time           DATETIME       NOT NULL,
  online_time            DATETIME       NOT NULL,
  protected_unit_price   DECIMAL(10, 2),
  remained_inventory     DECIMAL(10, 3) NOT NULL,
  trade_direction        TINYINT        NOT NULL,
  unit_price             DECIMAL(10, 2) NOT NULL,
  charge_account_code_sn CHAR(20)       NOT NULL,
  owner_code_sn          CHAR(20)       NOT NULL,
  product_code_sn        CHAR(20)       NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_sku_sku_prop (
  sku_code_sn        CHAR(20)     NOT NULL,
  entity_status      TINYINT      NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  prop_ext_name      VARCHAR(255),
  prop_int_name      VARCHAR(255),
  prop_value         VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_sku_sku_rule (
  sku_code_sn        CHAR(20)     NOT NULL,
  entity_status      TINYINT      NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  rule_ext_name      VARCHAR(255),
  sku_rule_int_name  VARCHAR(255),
  rule_value         VARCHAR(255) NOT NULL,
  value_size         TINYINT      NOT NULL,
  value_type         TINYINT      NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_sn_sequence (
  id                 BIGINT                              NOT NULL auto_increment,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_user (
  id                      BIGINT              NOT NULL auto_increment,
  code_sn                 CHAR(20)            NOT NULL,
  entity_status           TINYINT             NOT NULL,
  last_modified_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  email                   VARCHAR(255),
  gender                  TINYINT   DEFAULT '0',
  legacy_id               BIGINT,
  name                    VARCHAR(255)        NOT NULL,
  password                VARCHAR(255)        NOT NULL,
  password_salt           VARCHAR(255)        NOT NULL,
  phone_number            CHAR(32),
  profile_img_url         VARCHAR(255),
  deprecated_role         TINYINT DEFAULT '0' NOT NULL,
  user_info               VARCHAR(512)        NOT NULL,
  user_status             TINYINT             NOT NULL,
  primary_account_code_sn CHAR(20),
  residence_code_sn_1     CHAR(20),
  residence_code_sn_2     CHAR(20),
  residence_code_sn_3     CHAR(20),
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_user_user_account (
  user_code_sn       CHAR(20) NOT NULL,
  account_code_sn    CHAR(20),
  entity_status      TINYINT  NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_user_user_address (
  user_code_sn       CHAR(20)     NOT NULL,
  default_address    TINYINT      NOT NULL,
  entity_status      TINYINT      NOT NULL,
  last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  user_address_value VARCHAR(512) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CREATE TABLE ng_voucher_balance (
  id                    BIGINT         NOT NULL auto_increment,
  code_sn               CHAR(20)       NOT NULL,
  entity_status         TINYINT        NOT NULL,
  last_modified_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  extra_info            MEDIUMTEXT,
  from_account_remained DECIMAL(10, 4) NOT NULL,
  to_account_remained   DECIMAL(10, 4) NOT NULL,
  trade_amount          DECIMAL(10, 4) NOT NULL,
  trade_info            TEXT,
  bound_order           CHAR(20)       NOT NULL,
  from_Account          CHAR(20)       NOT NULL,
  from_user             CHAR(20)       NOT NULL,
  to_account            CHAR(20)       NOT NULL,
  to_user               CHAR(20)       NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
ALTER TABLE ng_account ADD CONSTRAINT UK_o85rvh1fwfwwo2pq6gm3k3jrs UNIQUE (code_sn)
ALTER TABLE ng_account ADD CONSTRAINT UKtlq2lbogn6h3its0dpj8f0dfw UNIQUE (name, name_shadow)
ALTER TABLE ng_cash_balance ADD CONSTRAINT UK_f10dij6lqjhqi9355s0xnso3 UNIQUE (code_sn)
ALTER TABLE ng_global_dict ADD CONSTRAINT UK_ps9e7em49ng3s43qdcd69kxe7 UNIQUE (code_sn)
ALTER TABLE ng_order ADD CONSTRAINT UK_p05gve4jbmm3p1t0q8xpx8wqj UNIQUE (code_sn)
CREATE INDEX IDXgkk6rs37llrlwu40ekilfj5t7 ON ng_order_order_sku (order_code_sn)
ALTER TABLE ng_order_order_sku ADD CONSTRAINT UKkuamoghkarh1ts70tp1odstah UNIQUE (order_code_sn, sku_code_sn)
ALTER TABLE ng_point_balance ADD CONSTRAINT UK_51nyogffqj5g5obylux2p2des UNIQUE (code_sn)
ALTER TABLE ng_producer_consumer ADD CONSTRAINT UKm7xndj42r6q9erhf9c2b06mwb UNIQUE (target_code_sn, hash_code, flag)
ALTER TABLE ng_producer_consumer ADD CONSTRAINT UK_cceu5ngmfqopkw60f5fwewg0l UNIQUE (code_sn)
ALTER TABLE ng_product ADD CONSTRAINT UK_gkunesuy383u7jslbumbqkl5a UNIQUE (code_sn)
CREATE INDEX IDXcuon7s5yj3433ly44g6h5o3ar ON ng_product_product_prop (product_code_sn)
CREATE INDEX IDXrwy9gk4950s2og6kk2jcew54w ON ng_product_product_prop (prop_name)
ALTER TABLE ng_product_product_prop ADD CONSTRAINT UKuwc7wd5tqj9u30ppjd2tw0he UNIQUE (product_code_sn, prop_name)
ALTER TABLE ng_residence ADD CONSTRAINT UK_ledv08446074q6eeeap9x6ivm UNIQUE (code_sn)
ALTER TABLE ng_sku ADD CONSTRAINT UK_pao7v36h7bkmiis04irh43fgt UNIQUE (code_sn)
CREATE INDEX IDXnbgvgurjt2vduc08ci574hdwc ON ng_sku_sku_prop (sku_code_sn)
CREATE INDEX IDXd90j5smmcy8pty50ti5bohfk7 ON ng_sku_sku_prop (prop_value)
ALTER TABLE ng_sku_sku_prop ADD CONSTRAINT UKck361h21pk59gmr6fe4n0n43o UNIQUE (sku_code_sn, prop_value)
CREATE INDEX IDXto993h8fjrodw812gayadyj6r ON ng_sku_sku_rule (sku_code_sn)
CREATE INDEX IDX5nmoorbtkorpeyletfid30ocq ON ng_sku_sku_rule (rule_value)
ALTER TABLE ng_sku_sku_rule ADD CONSTRAINT UK9syhueyhbst637nujq52qyt5i UNIQUE (sku_code_sn, rule_value)
ALTER TABLE ng_user ADD CONSTRAINT UK_pt1f4e73t3q1ulvyjbfdq79ec UNIQUE (code_sn)
ALTER TABLE ng_user ADD CONSTRAINT UK_574im9ip1tl7saohc5bvu4gg UNIQUE (phone_number)
CREATE INDEX IDX1dnfa6fis9fcovcolbjucrin7 ON ng_user_user_account (user_code_sn)
CREATE INDEX IDXlgv6l96w80u7ika8v3mr0hcth ON ng_user_user_account (account_code_sn)
ALTER TABLE ng_user_user_account ADD CONSTRAINT UKrlqjw5ivtuml9am5pmehfx3pr UNIQUE (user_code_sn, account_code_sn)
CREATE INDEX IDX553in674alysea1rjhaear9n2 ON ng_user_user_address (user_code_sn)
ALTER TABLE ng_user_user_address ADD CONSTRAINT UK553in674alysea1rjhaear9n2 UNIQUE (user_code_sn)
ALTER TABLE ng_voucher_balance ADD CONSTRAINT UK_imeam7m42xxpvtkq4g6p4xy54 UNIQUE (code_sn)
