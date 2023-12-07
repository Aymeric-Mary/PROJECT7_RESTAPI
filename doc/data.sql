CREATE TABLE bid_list
(
    id           tinyint(4)  NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    bid_quantity  DOUBLE,
    creation_date TIMESTAMP,
    revision_date TIMESTAMP,

    PRIMARY KEY (id)
)

CREATE TABLE trade
(
    id      tinyint(4)  NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    buy_quantity  DOUBLE,
    creation_date TIMESTAMP,
    revision_date TIMESTAMP,

    PRIMARY KEY (id)
)

CREATE TABLE curve_point
(
    id           tinyint(4) NOT NULL AUTO_INCREMENT,
    curve_id     tinyint NOT NULL,
    as_of_date   TIMESTAMP,
    term         DOUBLE,
    value        DOUBLE,
    creation_date TIMESTAMP,

    PRIMARY KEY (id)
)

CREATE TABLE rating
(
    id           tinyint(4) NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating  VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  tinyint,

    PRIMARY KEY (id)
)

CREATE TABLE rule_name
(
    id          tinyint(4) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(512),
    sql_str      VARCHAR(125),
    sql_part     VARCHAR(125),

    PRIMARY KEY (id)
)

CREATE TABLE user
(
    id       tinyint(4) NOT NULL AUTO_INCREMENT,
    username VARCHAR(125),
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),

    PRIMARY KEY (id)
)

insert into user(fullname, username, password, role)
values ("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN")
insert into user(fullname, username, password, role)
values ("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER")