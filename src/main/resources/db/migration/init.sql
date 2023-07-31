create table if not exists product
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)    not null,
    description varchar(255)   not null,
    image_url   varchar(255)   not null,
    name        varchar(255)   not null,
    price       decimal(38, 2) not null,
    stock       int            not null,
    updated_at  datetime(6)    not null
);

create table if not exists user
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)  not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    updated_at datetime(6)  not null,
    username   varchar(255) not null
);

create table if not exists cart
(
    id         bigint auto_increment
        primary key,
    quantity   int    not null,
    product_id bigint not null,
    user_id    bigint not null,
    constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id) references product (id),
    constraint FKl70asp4l4w0jmbm1tqyofho4o
        foreign key (user_id) references user (id)
);

create table if not exists order_info
(
    id           bigint auto_increment
        primary key,
    order_date   datetime(6)    not null,
    order_number varchar(255)   not null,
    total_amount decimal(38, 2) not null,
    user_id      bigint         not null,
    constraint FKtlpgba4g9kxg3t6s6bi5jw4v7
        foreign key (user_id) references user (id)
);

create table if not exists order_product
(
    id         bigint auto_increment
        primary key,
    quantity   int    not null,
    order_id   bigint not null,
    product_id bigint not null,
    constraint FKhnfgqyjx3i80qoymrssls3kno
        foreign key (product_id) references product (id),
    constraint FKllpxfv7t1urk3jk80eoce2vc9
        foreign key (order_id) references order_info (id)
);

create table if not exists user_like
(
    id         bigint auto_increment
        primary key,
    product_id bigint null,
    user_id    bigint null,
    constraint FK9v6k2ud44r6y81rf9v1qej5nd
        foreign key (product_id) references product (id),
    constraint FKjsfb2urrv0shlti7sfy9iktpi
        foreign key (user_id) references user (id)
);
