
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        beer_style tinyint check (beer_style between 0 and 9),
        price decimal(38,2),
        quantity_on_hand integer,
        created_date datetime(6),
        update_date datetime(6),
        version bigint,
        id varchar(36) not null,
        beer_name varchar(255),
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        created_at datetime(6),
        modified_at datetime(6),
        version bigint,
        id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

