-- =============================================
-- RuoYi Template Management System Database
-- CORRECTED VERSION
-- =============================================

-- ----------------------------
-- 1. Price Template Table (价格模板表) - Simple, just name and size
-- ----------------------------
DROP TABLE IF EXISTS `biz_price_template`;
CREATE TABLE `biz_price_template` (
  `price_template_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Price Template ID',
  `price_template_name` VARCHAR(100) NOT NULL COMMENT 'Price Template Name',
  `size` VARCHAR(50) DEFAULT NULL COMMENT 'Size (e.g., S, M, L, XL or dimensions)',
  `status` CHAR(1) DEFAULT '0' COMMENT 'Status (0=Normal, 1=Disabled)',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT 'Delete Flag (0=Exist, 2=Deleted)',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT 'Creator',
  `create_time` DATETIME DEFAULT NULL COMMENT 'Create Time',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT 'Updater',
  `update_time` DATETIME DEFAULT NULL COMMENT 'Update Time',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`price_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='Price Template Table';

-- ----------------------------
-- 2. Template Table (模板表) - Main template
-- ----------------------------
DROP TABLE IF EXISTS `biz_template`;
CREATE TABLE `biz_template` (
  `template_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Template ID',
  `template_name` VARCHAR(200) NOT NULL COMMENT 'Template Name',
  `note` TEXT DEFAULT NULL COMMENT 'Template Note/Description',
  `status` CHAR(1) DEFAULT '0' COMMENT 'Status (0=Normal, 1=Disabled)',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT 'Delete Flag (0=Exist, 2=Deleted)',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT 'Creator',
  `create_time` DATETIME DEFAULT NULL COMMENT 'Create Time',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT 'Updater',
  `update_time` DATETIME DEFAULT NULL COMMENT 'Update Time',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='Template Table';

-- ----------------------------
-- 3. Template Item Table (模板项目表) - Each item has ONE image
-- ----------------------------
DROP TABLE IF EXISTS `biz_template_item`;
CREATE TABLE `biz_template_item` (
  `template_item_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Template Item ID',
  `template_id` BIGINT(20) NOT NULL COMMENT 'Template ID (FK)',
  `price_template_id` BIGINT(20) DEFAULT NULL COMMENT 'Price Template ID (FK, optional)',
  `item_name` VARCHAR(100) DEFAULT NULL COMMENT 'Item Name (fallback if no price template)',
  `image_url` VARCHAR(500) DEFAULT NULL COMMENT 'Image URL (MinIO) - ONE image per item',
  `image_name` VARCHAR(200) DEFAULT NULL COMMENT 'Image Name',
  `image_type` VARCHAR(50) DEFAULT NULL COMMENT 'Image Type (e.g., jpg, png)',
  `image_size` BIGINT(20) DEFAULT NULL COMMENT 'Image Size (bytes)',
  `is_default` CHAR(1) DEFAULT '0' COMMENT 'Is Default Item (0=No, 1=Yes)',
  `sort_order` INT(11) DEFAULT 0 COMMENT 'Sort Order',
  `status` CHAR(1) DEFAULT '0' COMMENT 'Status (0=Normal, 1=Disabled)',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT 'Delete Flag (0=Exist, 2=Deleted)',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT 'Creator',
  `create_time` DATETIME DEFAULT NULL COMMENT 'Create Time',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT 'Updater',
  `update_time` DATETIME DEFAULT NULL COMMENT 'Update Time',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`template_item_id`),
  KEY `idx_template_id` (`template_id`),
  KEY `idx_price_template_id` (`price_template_id`),
  CONSTRAINT `fk_template_item_template` FOREIGN KEY (`template_id`)
    REFERENCES `biz_template` (`template_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_template_item_price` FOREIGN KEY (`price_template_id`)
    REFERENCES `biz_price_template` (`price_template_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='Template Item Table - Each item has ONE image';

-- ----------------------------
-- 4. Menu Configuration for RuoYi (菜单SQL)
-- ----------------------------
-- Parent Menu: Template Management
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('Template Management', 0, 10, 'template', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'documentation', 'admin', NOW(), '', NULL, 'Template Management Menu');

SET @parentId = LAST_INSERT_ID();

-- Sub Menu: All Templates
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('All Templates', @parentId, 1, 'allTemplate', 'api/Template/index', NULL, '', 1, 0, 'C', '0', '0', 'biz:template:list', 'list', 'admin', NOW(), '', NULL, 'All Templates Menu');

SET @templateMenuId = LAST_INSERT_ID();

-- Sub Menu: Price Template
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('Price Template', @parentId, 2, 'priceTemplate', 'api/PriceTemplate/index', NULL, '', 1, 0, 'C', '0', '0', 'biz:priceTemplate:list', 'money', 'admin', NOW(), '', NULL, 'Price Template Menu');

SET @priceTemplateMenuId = LAST_INSERT_ID();

-- Button Permissions for Template
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('Template Query', @templateMenuId, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:template:query', '#', 'admin', NOW(), '', NULL, ''),
('Template Add', @templateMenuId, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:template:add', '#', 'admin', NOW(), '', NULL, ''),
('Template Edit', @templateMenuId, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:template:edit', '#', 'admin', NOW(), '', NULL, ''),
('Template Delete', @templateMenuId, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:template:remove', '#', 'admin', NOW(), '', NULL, ''),
('Template Export', @templateMenuId, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:template:export', '#', 'admin', NOW(), '', NULL, '');

-- Button Permissions for Price Template
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('Price Template Query', @priceTemplateMenuId, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:priceTemplate:query', '#', 'admin', NOW(), '', NULL, ''),
('Price Template Add', @priceTemplateMenuId, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:priceTemplate:add', '#', 'admin', NOW(), '', NULL, ''),
('Price Template Edit', @priceTemplateMenuId, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:priceTemplate:edit', '#', 'admin', NOW(), '', NULL, ''),
('Price Template Delete', @priceTemplateMenuId, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:priceTemplate:remove', '#', 'admin', NOW(), '', NULL, ''),
('Price Template Export', @priceTemplateMenuId, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'biz:priceTemplate:export', '#', 'admin', NOW(), '', NULL, '');

-- =============================================
-- Final Table Summary
-- =============================================
-- | Table Name         | Description                          | Notes                           |
-- |--------------------|--------------------------------------|--------------------------------|
-- | biz_price_template | Price template (name, size only)     | Simple - no images             |
-- | biz_template       | Main template (name, note)           | Parent                         |
-- | biz_template_item  | Template items with ONE image each   | Belongs to template, has image |
-- =============================================

-- Data Flow Example:
-- Template "Wedding Package"
--     └── Template Items (biz_template_item)
--         ├── Item 1: Price Template "Small Size" + Image 1 (is_default=1)
--         ├── Item 2: Price Template "Medium Size" + Image 2 (is_default=0)
--         └── Item 3: Price Template "Large Size" + Image 3 (is_default=0)
