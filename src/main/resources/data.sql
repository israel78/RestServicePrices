
CREATE TABLE USER (
               ID INTEGER DEFAULT NOT NULL AUTO_INCREMENT primary key ,
               NAME VARCHAR(100) NOT NULL,
               PASS VARCHAR(100) NOT NULL
                );

CREATE TABLE PRICES (
               ID INTEGER NOT NULL AUTO_INCREMENT,
               BRAND_ID INTEGER NOT NULL,
               START_DATE TIMESTAMP NOT NULL,
               END_DATE TIMESTAMP NOT NULL,
               PRICE_LIST INTEGER NOT NULL,
               PRODUCT_ID INTEGER NOT NULL,
               PRIORITY INTEGER NOT NULL,
               PRICE DOUBLE NOT NULL,
               CURR VARCHAR(100) NOT NULL
);

INSERT INTO USER (NAME,PASS) VALUES
('antonio','antonio');
INSERT INTO USER (NAME,PASS) VALUES
('pedro','pedro');
INSERT INTO PRICES (BRAND_ID,START_DATE,END_DATE,PRICE_LIST,PRODUCT_ID,PRIORITY,PRICE,CURR) VALUES (
                    1,parsedatetime('2020-06-14 12.00.00', 'yyyy-MM-dd HH.mm.ss'),parsedatetime('2020-12-31 12.00.00', 'yyyy-MM-dd HH.mm.ss'),1,35455,0,35.5,'EUR');
INSERT INTO PRICES (BRAND_ID,START_DATE,END_DATE,PRICE_LIST,PRODUCT_ID,PRIORITY,PRICE,CURR) VALUES (
                    1,parsedatetime('2020-06-14 12.30.00', 'yyyy-MM-dd HH.mm.ss'),parsedatetime('2020-06-14 15.00.00', 'yyyy-MM-dd HH.mm.ss'),2,35455,1,25.45,'EUR');
INSERT INTO PRICES (BRAND_ID,START_DATE,END_DATE,PRICE_LIST,PRODUCT_ID,PRIORITY,PRICE,CURR) VALUES (
                    1,parsedatetime('2020-06-15 12.00.00', 'yyyy-MM-dd HH.mm.ss'),parsedatetime('2020-06-15 12.00.00', 'yyyy-MM-dd HH.mm.ss'),3,35455,1,30.5,'EUR');
INSERT INTO PRICES (BRAND_ID,START_DATE,END_DATE,PRICE_LIST,PRODUCT_ID,PRIORITY,PRICE,CURR) VALUES (
                    1,parsedatetime('2020-06-15 00.00.00', 'yyyy-MM-dd HH.mm.ss'),parsedatetime('2020-12-31 12.00.00', 'yyyy-MM-dd HH.mm.ss'),4,35455,1,38.95,'EUR');

