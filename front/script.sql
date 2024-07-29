
-- Création de la table 'User'
CREATE TABLE `user` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`email`)
);

-- Thème
CREATE TABLE `subject` (
  `id` INT AUTO_INCREMENT,
  `title_subject` VARCHAR(40),
  `description` VARCHAR(255),
  PRIMARY KEY (`id`)
);

-- Abonnement
CREATE TABLE `subscription` (
  `user_id` INT NOT NULL,
  `theme_id` INT NOT NULL,
   PRIMARY KEY (`user_id`, `theme_id`)
);
ALTER TABLE `subscription` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `subscription` ADD FOREIGN KEY (`theme_id`) REFERENCES `subject` (`id`);

-- Article
CREATE TABLE `post` (
  `id` INT AUTO_INCREMENT,
  `titre` VARCHAR(255) NOT NULL,
  `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `contenu` TEXT NOT NULL,
  `user_id` INT NOT NULL,
  `theme_id` INT NOT NULL,
   PRIMARY KEY (`id`)
);
ALTER TABLE `post` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `post` ADD FOREIGN KEY (`theme_id`) REFERENCES `subject` (`id`);

-- Commentaires
CREATE TABLE `comment` (
  `id` INT AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `post_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`)
)
ALTER TABLE `comment` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `comment` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

