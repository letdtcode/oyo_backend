SET @createdBy = 'anonymousUser'
SET @lastModifiedBy = 'anonymousUser'
SET @status = 'ENABLE'
SET @deleted = 0

INSERT INTO accommodation_categories(created_by,created_date,last_modified_by,last_modified_date,deleted,accom_cate_name,description,icon,status) VALUES (@createdBy,CURRENT_TIMESTAMP,@lastModifiedBy,CURRENT_TIMESTAMP,@deleted,'Phòng','Phòng nhỏ căn hộ','https://img.icons8.com/ios/25/room.png','ENABLE')