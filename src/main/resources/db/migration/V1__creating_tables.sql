create table abrigos(
    id bigint not null auto_increment,
    nome varchar(100) not null unique,
    telefone varchar(14) not null unique,
    email varchar(100) not null unique,
    primary key(id)
);

create table pets(
    id bigint not null auto_increment,
    tipo varchar(100) not null,
    nome varchar(100) not null,
    raca varchar(100) not null,
    idade int not null,
    cor varchar(100) not null,
    peso decimal(4,2) not null,
    abrigo_id bigint not null,
    adotado boolean not null,
    primary key(id),
    constraint fk_pets_abrigo_id foreign key(abrigo_id) references abrigos(id)
);

create table tutores(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    telefone varchar(14) not null unique,
    email varchar(100) not null unique,
    primary key(id)
);

create table adocoes(
    id bigint not null auto_increment,
    data datetime not null,
    tutor_id bigint not null,
    pet_id bigint not null,
    motivo varchar(255) not null,
    status varchar(100) not null,
    justificativa_status varchar(255),
    primary key(id),
    constraint fk_adocoes_tutor_id foreign key(tutor_id) references tutores(id),
    constraint fk_adocoes_pet_id foreign key(pet_id) references pets(id)
);