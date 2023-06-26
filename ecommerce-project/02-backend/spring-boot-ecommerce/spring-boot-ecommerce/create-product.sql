CREATE TABLE ecommercedb.product (
  id BIGINT auto_increment NOT NULL,
  sku varchar(255) DEFAULT NULL null,
  name varchar(255) DEFAULT NULL null,
  description varchar(255) DEFAULT NULL null,
  unit_price decimal(13,2) DEFAULT NULL null,
  image_url varchar(255) DEFAULT NULL null,
  active BIT DEFAULT 1,
  units_in_stock INT(11) DEFAULT NULL null,
  date_created DATETIME(6) DEFAULT NULL null,
  last_updated DATETIME(6) DEFAULT NULL null,
  category_id BIGINT(20) NOT NULL null,
  PRIMARY KEY (id),
  KEY fk_category (category_id),
  CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES product_category (id)
)
ENGINE=InnoDB;