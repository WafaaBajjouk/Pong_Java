CREATE TABLE IF NOT EXISTS Player (
                                      Id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      birthday DATE NULL,
                                      password VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS Game (
                                    Id INT AUTO_INCREMENT PRIMARY KEY,
                                    date_of_game DATE NOT NULL,
                                    score INT NULL,
                                    isSinglePlayerMode TINYINT(1),
                                    win TINYINT(1) NULL,
                                    player_id INT,
                                    FOREIGN KEY (player_id) REFERENCES Player(Id)
);

INSERT INTO Player (name, birthday, password) VALUES ('user', '2000-01-15', 'user');
