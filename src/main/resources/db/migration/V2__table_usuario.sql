CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO usuario (nome, email, senha, role)
VALUES ('Eduardo', 'eduardo@email.com', '$2a$10$/y25kCxcbtg6fptSxIn58u83jgvXp/frrI/8wQZpmKQarJ1qUfjhe', 'ADMIN');