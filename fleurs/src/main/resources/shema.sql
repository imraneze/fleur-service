DROP TABLE IF EXISTS fleur;

CREATE TABLE fleur (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    couleur     VARCHAR(50)  NOT NULL,
    prix        DOUBLE       NOT NULL,
    saisonnalite VARCHAR(50)
);