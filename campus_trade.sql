-- ============================================
-- 校园二手交易平台 — 数据库初始化脚本
-- ============================================

DROP DATABASE IF EXISTS campus_trade;
CREATE DATABASE campus_trade
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE campus_trade;

-- -------------------------------------------
-- 用户表
-- -------------------------------------------
CREATE TABLE user (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码（加密）',
    phone       VARCHAR(20)  COMMENT '手机号',
    avatar      VARCHAR(200) COMMENT '头像路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -------------------------------------------
-- 商品分类表
-- -------------------------------------------
CREATE TABLE category (
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '分类名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- -------------------------------------------
-- 商品表
-- -------------------------------------------
CREATE TABLE goods (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100)   NOT NULL COMMENT '商品标题',
    price       DECIMAL(10,2)  NOT NULL COMMENT '价格',
    description TEXT           COMMENT '商品描述',
    image       VARCHAR(200)   COMMENT '图片路径',
    status      VARCHAR(20)    DEFAULT '在售' COMMENT '状态：在售/已售出/已下架',
    seller_id   INT            NOT NULL COMMENT '卖家ID',
    category_id INT            NOT NULL COMMENT '分类ID',
    create_time DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    CONSTRAINT fk_goods_seller  FOREIGN KEY (seller_id)   REFERENCES user(id),
    CONSTRAINT fk_goods_category FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- -------------------------------------------
-- 订单表
-- -------------------------------------------
CREATE TABLE `order` (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    goods_id    INT NOT NULL COMMENT '商品ID',
    buyer_id    INT NOT NULL COMMENT '买家ID',
    seller_id   INT NOT NULL COMMENT '卖家ID',
    status      VARCHAR(20) DEFAULT '待确认' COMMENT '状态：待确认/已完成/已取消',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    CONSTRAINT fk_order_goods  FOREIGN KEY (goods_id)  REFERENCES goods(id),
    CONSTRAINT fk_order_buyer  FOREIGN KEY (buyer_id)  REFERENCES user(id),
    CONSTRAINT fk_order_seller FOREIGN KEY (seller_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- -------------------------------------------
-- 初始数据：商品分类
-- -------------------------------------------
INSERT INTO category (name) VALUES
    ('教材'),
    ('电子产品'),
    ('生活用品'),
    ('服装'),
    ('其他');

-- -------------------------------------------
-- 测试数据：用户
-- -------------------------------------------
INSERT INTO user (username, password, phone) VALUES
    ('张三', '123456', '13800000001'),
    ('李四', '123456', '13800000002'),
    ('王五', '123456', '13800000003');

-- -------------------------------------------
-- 测试数据：商品
-- -------------------------------------------
INSERT INTO goods (title, price, description, status, seller_id, category_id) VALUES
    ('数据结构（C语言版）九成新', 25.00, '只用了一学期，基本全新，无笔记', '在售', 1, 1),
    ('高等数学第七版', 15.00, '有少量笔记，不影响使用', '在售', 1, 1),
    ('罗技 K380 蓝牙键盘', 80.00, '买了半年，用的很少，箱说全', '在售', 2, 2),
    ('床上书桌（折叠款）', 30.00, '毕业出清，功能正常', '在售', 2, 3),
    ('冬季棉服（男款 L 码）', 60.00, '只穿了一季，洗干净了', '在售', 3, 4);
