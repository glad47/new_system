# RuoYi Template Management System - Backend Code

## Directory Structure

```
ruoyi-api/
├── controller/                          # REST Controllers
│   ├── BizTemplateController.java       # Template CRUD APIs
│   └── BizTemplateItemController.java   # Template Item CRUD APIs
├── domain/                              # Entity Classes
│   ├── BizTemplate.java                 # Main template entity
│   ├── BizTemplateItem.java             # Template item entity
│   ├── BizTemplateItemImage.java        # Template item image entity
│   ├── BizTemplatePrice.java            # Template price (1:1) entity
│   └── BizTemplatePriceItem.java        # Junction table entity
├── mapper/                              # MyBatis Mapper Interfaces
│   ├── BizTemplateMapper.java
│   ├── BizTemplateItemMapper.java
│   ├── BizTemplateItemImageMapper.java
│   ├── BizTemplatePriceMapper.java
│   ├── BizTemplatePriceItemMapper.java
│   └── xml/                             # MyBatis XML Mappers
│       ├── BizTemplateMapper.xml
│       ├── BizTemplateItemMapper.xml
│       ├── BizTemplateItemImageMapper.xml
│       ├── BizTemplatePriceMapper.xml
│       └── BizTemplatePriceItemMapper.xml
└── service/                             # Service Layer
    ├── IBizTemplateService.java
    ├── IBizTemplateItemService.java
    └── impl/
        ├── BizTemplateServiceImpl.java
        └── BizTemplateItemServiceImpl.java
```

## Installation

### 1. Copy files to your RuoYi project

```bash
# Copy to ruoyi-admin module
cp -r controller/* ruoyi-admin/src/main/java/com/ruoyi/api/controller/
cp -r domain/* ruoyi-admin/src/main/java/com/ruoyi/api/domain/
cp -r mapper/*.java ruoyi-admin/src/main/java/com/ruoyi/api/mapper/
cp -r service/* ruoyi-admin/src/main/java/com/ruoyi/api/service/

# Copy XML mappers
cp -r mapper/xml/* ruoyi-admin/src/main/resources/mapper/api/
```

### 2. Run database SQL

Execute the SQL script from the previous conversation to create tables and menus.

### 3. Configure MyBatis mapper scanning

Ensure your MyBatis configuration scans the new mapper location. In `application.yml`:

```yaml
mybatis:
  mapperLocations: classpath*:mapper/**/*Mapper.xml
```

### 4. Add component scanning

Make sure Spring scans the new packages. In your main application or config class:

```java
@MapperScan({"com.ruoyi.*.mapper", "com.ruoyi.api.mapper"})
```

## API Endpoints

### Template Item APIs (`/api/templateItem`)

| Method | Endpoint | Permission | Description |
|--------|----------|------------|-------------|
| GET | `/list` | `biz:templateItem:list` | List template items (paginated) |
| GET | `/listAll` | `biz:templateItem:list` | List all active template items |
| GET | `/{id}` | `biz:templateItem:query` | Get template item with images |
| POST | `/` | `biz:templateItem:add` | Create template item with images |
| PUT | `/` | `biz:templateItem:edit` | Update template item with images |
| DELETE | `/{ids}` | `biz:templateItem:remove` | Delete template items |
| POST | `/image` | `biz:templateItem:edit` | Add image to template item |
| DELETE | `/image/{id}` | `biz:templateItem:edit` | Remove image from template item |
| POST | `/export` | `biz:templateItem:export` | Export template items |

### Template APIs (`/api/template`)

| Method | Endpoint | Permission | Description |
|--------|----------|------------|-------------|
| GET | `/list` | `biz:template:list` | List templates with relations (paginated) |
| GET | `/{id}` | `biz:template:query` | Get template with all relations |
| POST | `/` | `biz:template:add` | Create template (auto-creates price & items) |
| PUT | `/` | `biz:template:edit` | Update template |
| DELETE | `/{ids}` | `biz:template:remove` | Delete templates |
| GET | `/checkName` | `biz:template:query` | Check template name uniqueness |
| POST | `/sync/{id}` | `biz:template:edit` | Sync price items with all template items |
| POST | `/export` | `biz:template:export` | Export templates |

## Request/Response Examples

### Create Template Item

```json
POST /api/templateItem
{
    "itemName": "Small Size",
    "size": "S",
    "status": "0",
    "remark": "Small size template",
    "images": [
        {
            "imageUrl": "http://minio.example.com/bucket/image1.jpg",
            "imageName": "Front View",
            "imageType": "jpg",
            "imageSize": 102400
        },
        {
            "imageUrl": "http://minio.example.com/bucket/image2.jpg",
            "imageName": "Back View",
            "imageType": "jpg",
            "imageSize": 98304
        }
    ]
}
```

### Create Template

```json
POST /api/template
{
    "templateName": "Wedding Package",
    "note": "Complete wedding photography package",
    "status": "0",
    "defaultItemId": 1
}
```

**Note:** When creating a template:
- `defaultItemId` is **required** - must select one template item as default
- The system automatically creates `biz_template_price` record
- The system automatically links **ALL active template items** via `biz_template_price_item`

### Get Template with Relations

```json
GET /api/template/1

Response:
{
    "code": 200,
    "data": {
        "templateId": 1,
        "templateName": "Wedding Package",
        "note": "Complete wedding photography package",
        "status": "0",
        "defaultItemId": 1,
        "templatePrice": {
            "templatePriceId": 1,
            "templateId": 1,
            "defaultItemId": 1,
            "defaultItem": {
                "templateItemId": 1,
                "itemName": "Small Size",
                "size": "S"
            }
        },
        "templateItems": [
            {
                "templateItemId": 1,
                "itemName": "Small Size",
                "size": "S",
                "imageCount": 2
            },
            {
                "templateItemId": 2,
                "itemName": "Medium Size",
                "size": "M",
                "imageCount": 3
            }
        ]
    }
}
```

## Business Logic

### When Creating Template Item
1. Insert into `biz_template_item`
2. Insert images into `biz_template_item_image`
3. **Auto-sync**: Add this item to ALL existing templates' `biz_template_price_item`

### When Creating Template
1. Insert into `biz_template`
2. Create `biz_template_price` with the selected `defaultItemId`
3. Create `biz_template_price_item` for **ALL active template items**

### When Deleting Template Item
1. Check if it's used as `defaultItemId` in any template
2. If used as default → **Block deletion with error**
3. If not used as default → Soft delete the item and its images

## Key Features

✅ **Soft Delete**: All delete operations use `del_flag = '2'` instead of physical delete

✅ **Audit Trail**: `create_by`, `create_time`, `update_by`, `update_time` auto-populated

✅ **Pagination**: All list APIs support RuoYi pagination

✅ **Permissions**: Full permission check with `@PreAuthorize`

✅ **Validation**: Input validation with `@Validated`

✅ **Logging**: Operation logging with `@Log` annotation

✅ **Transaction**: Multi-table operations wrapped in `@Transactional`

## Note on MinIO Images

Images are stored in MinIO and only URLs are saved in database. You need to:
1. Configure MinIO client separately
2. Upload images to MinIO first
3. Pass the MinIO URLs to the API when creating/updating template items
