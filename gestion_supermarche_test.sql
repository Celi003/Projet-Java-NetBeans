-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le : mer. 28 fév. 2024 à 02:47
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_supermarche`
--

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `id_promotion` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categories_ibfk_1` (`id_promotion`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''categories'' contient le nom, la description et l''id de promotion de la catégorie, la description peut avoir la valeur NULL, car certaines catégories sont déjà assez précises uniquement avec leurs nom, donc la description est beaucoup plus pour les catégories complexes, quant à l''id_promotion, il peut prendre la valeur null, car d''abord il permet de mettre tout les  produits de la catégorie en question en promotion, et tous les produits ne sont pas tenus d''être en promotion d''où la valeur NULL pour les catégories n''étant pas en promotion.';

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`id`, `nom`, `description`, `id_promotion`) VALUES
(1, 'Fruits et Légumes', 'Cette catégorie comprend une variété de fruits frais tels que des pommes, des bananes, des oranges, ainsi que des légumes comme des carottes, des tomates , et des épinards.', NULL),
(2, 'Produits laitiers', 'Les produits laitiers incluent le lait, le fromage, le yaourt, le beurre et d\'autres produits dérivés du lait.', NULL),
(3, 'Boulangerie', 'Cette catégorie englobe une sélection de pains frais tels que des baguettes, des pains de mie, des croissants, des pains spéciaux, ainsi que des pâtisseries comme des tartes et des gâteaux.', NULL),
(4, 'Viandes et Poissons', 'Vous y trouverez des viandes fraîches telles que du bœuf, du poulet, du porc, ainsi que des poissons et fruits de mer comme du saumon, des crevettes et des moules.', NULL),
(5, 'Produits surgelés', 'Cette catégorie comprend des aliments congelés tels que des légumes, des fruits, des pizzas, des plats cuisinés, des glaces, etc.', NULL),
(6, 'Produits d\'hygiène et de beauté', 'Vous trouverez ici des articles tels que du shampoing, du savon, du dentifrice, des produits de soins pour le visage, des produits cosmétiques, etc.', NULL),
(7, 'Épicerie', 'Cette catégorie comprend une variété d\'aliments emballés tels que des conserves, des pâtes, du riz, des sauces, des condiments, des snacks, etc.', NULL),
(8, 'Boissons', 'Les boissons incluent l\'eau en bouteille, les jus de fruits, les sodas, le café, le thé, les boissons énergisantes, ainsi que les boissons alcoolisées comme la bière, le vin et les spiritueux.', NULL),
(9, 'Produits d\'entretien ménager', 'Vous y trouverez des produits tels que des détergents, des nettoyants multi-surfaces, des produits pour lave-vaisselle, des produits pour lessive, des sacs poubelle, etc.', NULL),
(10, 'Produits pour animaux', 'Cette catégorie comprend de la nourriture pour chiens, chats, oiseaux, ainsi que des accessoires comme des jouets, des litières, des gamelles, etc.', NULL),
(12, 'Exemple', '', NULL),
(13, 'St Valentin', 'Tous les produits pour la saint Valentin', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `date_commande` datetime NOT NULL,
  `code_produit` varchar(15) NOT NULL,
  `quantite` int NOT NULL,
  `code_fournisseur` varchar(15) NOT NULL,
  `date_reception` date DEFAULT NULL,
  KEY `code_produit` (`code_produit`),
  KEY `code_fournisseur` (`code_fournisseur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''commandes'' contient la date et l''heure de commande, le code du produit commandé, la quantité commandé, le code du fournisseur de la commande et la date de réception de la commande de reapprovisionnement, la date de réception peut prendre la valeur NULL car seuls les produits déjà reçus ont une date de réception , quant aux autres en cours de commandes, leurs date de réception reste NULL jusqu''au jour de la réception.';

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`date_commande`, `code_produit`, `quantite`, `code_fournisseur`, `date_reception`) VALUES
('2024-02-25 23:57:49', 'VAL-003', 200, 'AUD-001', '2024-02-26'),
('2024-02-25 23:59:01', 'VAL-006', 60, 'FER-001', NULL),
('2024-02-25 23:59:24', 'VAL-001', 50, 'COTOR-001', '2024-02-26'),
('2024-02-25 23:59:32', 'VAL-002', 50, 'COTOR-001', NULL),
('2024-02-26 00:00:03', 'VAL-009', 5, 'AUD-001', NULL),
('2024-02-26 00:00:41', 'BOI-001', 200, 'FIF-001', '2024-02-26'),
('2024-02-26 00:01:01', 'BOU-002', 100, 'FOU-003', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseurs`
--

DROP TABLE IF EXISTS `fournisseurs`;
CREATE TABLE IF NOT EXISTS `fournisseurs` (
  `code` varchar(15) NOT NULL,
  `nom_ou_entreprise` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contact` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''fournisseurs'' contient le code du fournisseur définit par le supermarché selon une norme donné, le nom du fournisseur ou de l''entreprise et le contact(au format internationnal ex:+22940404522) du fournisseur.';

--
-- Déchargement des données de la table `fournisseurs`
--

INSERT INTO `fournisseurs` (`code`, `nom_ou_entreprise`, `contact`) VALUES
('AUD-001', 'Audemard', '+3325895647532'),
('COTOR-001', 'Côte Or', '+3369741258852'),
('FER-001', 'Ferrero', '+33258741963'),
('FIF-001', 'Fifa', '+2296354148'),
('FOU-001', 'BOSS Orh', '+22942158695'),
('FOU-002', 'Champion', '+22963584125'),
('FOU-003', 'Migan John', '+22963589452'),
('GAV-001', 'La gamme verte', '+22965478912');

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
CREATE TABLE IF NOT EXISTS `personnel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `prenoms` varchar(60) NOT NULL,
  `role` varchar(30) NOT NULL,
  `contact` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identifiant` varchar(30) DEFAULT NULL,
  `mot_de_passe` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''personnel'' contient le nom, le(s) prénom(s), le rôle("admin" , "caissier" , "aide client" , "agent de nettoyage" , "agent de parking" , "testeur produit" , "agent de sécurité"), le contact(au format internationnal ex:+22940404522), l''identifiant et le mot de passe de chaque employés. L''identifiant et le mot de passe peuvent prendre la valeur NULL car c''est pas tous les employés qui ont de mot d''identifiant et de mot de passe, c''est uniquement les caissiers et l''administrateur.';

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `prenoms`, `role`, `contact`, `identifiant`, `mot_de_passe`) VALUES
(2, 'DUPONT', 'Jean', 'admin', '+22990180172', 'admin2', 'admin2'),
(3, 'Marie', 'Martin', 'caissier', '+33526898401', 'caissier', 'caissier'),
(4, 'Sophie', 'Lefebvre', 'caissier', '+226235268981', 'caissier2', 'caissier2'),
(5, 'Thomas', 'Dubois', 'aide client', '+339874563224', NULL, NULL),
(6, 'Emilie', 'Lambert', 'aide client', '+22987587425', NULL, NULL),
(7, 'Nicolas', 'Rousseau', 'agent de nettoyage', '+22724526850', NULL, NULL),
(8, 'Camille', 'Girard', 'agent de nettoyage', '+22966297718', NULL, NULL),
(9, 'Alexandre', 'Moreau', 'agent de parking', '+22940411822', NULL, NULL),
(10, 'Laura', 'Fournier', 'agent de parking', '+22897225823', NULL, NULL),
(11, 'DRIGBA', 'mogo', 'agent de sécurité', '+22698712325', NULL, NULL),
(12, 'DOUL', 'Golden', 'testeur produit', '+22720322581', NULL, NULL),
(13, 'GBENOU', 'Paul', 'agent de sécurité', '+22998745623', NULL, NULL),
(14, 'Yanvi', 'Tchénin', 'testeur produit', '+22985214765', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `produits`
--

DROP TABLE IF EXISTS `produits`;
CREATE TABLE IF NOT EXISTS `produits` (
  `code` varchar(15) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prix` decimal(10,0) NOT NULL,
  `date_peremption` date DEFAULT NULL,
  `quantite_dispo` int NOT NULL,
  `id_promotion` int DEFAULT NULL,
  `id_categorie` int NOT NULL,
  PRIMARY KEY (`code`),
  KEY `id_categorie` (`id_categorie`),
  KEY `produits_ibfk_2` (`id_promotion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''produits'' contient le code, le nom, le prix, la date de perremption, la quantité disponible, l''id de promotion et l''id de la categorie du produit. L''id de promotion peut prendre la valeur NULL, car seuls les produits en promotion auront une valeur au niveau de l''id_promotion et les produits n''étant pas en promotion NULL, quand à l''id de la catégorie, c''est pour connaitre la catégorie à laquelle appartient chaque produit et est obligatoire.';

--
-- Déchargement des données de la table `produits`
--

INSERT INTO `produits` (`code`, `nom`, `prix`, `date_peremption`, `quantite_dispo`, `id_promotion`, `id_categorie`) VALUES
('ANI-001', 'Croquettes pour chat', '3931', '2025-12-31', 0, NULL, 10),
('ANI-002', 'Os à mâcher pour chien', '1966', '2024-01-30', 35, NULL, 10),
('ANI-003', 'Graines pour oiseaux', '656', '2025-10-31', 30, NULL, 10),
('BOI-001', 'Eau minérale', '656', '2025-12-31', 190, NULL, 8),
('BOI-002', 'Jus d\'orange', '1311', '2024-01-30', 30, NULL, 8),
('BOI-003', 'Café moulu', '3276', '2025-10-31', 20, NULL, 8),
('BOU-001', 'Baguette Tradition', '656', '2024-02-25', 0, NULL, 3),
('BOU-002', 'Croissant au Beurre', '656', '2024-01-26', 35, NULL, 3),
('BOU-003', 'Tarte aux Pommes', '3276', '2024-02-28', 10, NULL, 3),
('ENT-001', 'Détergent liquide', '2620', '2026-12-31', 0, NULL, 9),
('ENT-002', 'Nettoyant multi-surfaces', '1311', '2026-11-30', 50, NULL, 9),
('ENT-003', 'Désodorisant', '1311', '2026-10-31', 60, NULL, 9),
('EPI-001', 'Riz basmati', '1311', '2025-12-31', 0, NULL, 7),
('EPI-002', 'Sauce tomate', '656', '2025-11-30', 40, NULL, 7),
('EPI-003', 'Pâtes penne', '656', '2025-10-31', 50, NULL, 7),
('FRU-001', 'Pommes Gala', '1966', '2024-03-01', 0, NULL, 1),
('FRU-002', 'Bananes Bio', '656', '2024-01-27', 30, NULL, 1),
('FRU-003', 'Tomates Cerises', '1311', '2024-02-28', 20, NULL, 1),
('HYG-001', 'Shampoing hydratant', '3276', '2025-12-31', 0, NULL, 6),
('HYG-002', 'Savon à la lavande', '1311', '2025-10-31', 50, NULL, 6),
('HYG-003', 'Dentifrice blanchissant', '1966', '2025-09-30', 30, NULL, 6),
('LAI-001', 'Lait demi-écrémé', '1311', '2024-03-05', 0, NULL, 2),
('LAI-002', 'Fromage Emmental', '1966', '2024-01-10', 5, NULL, 2),
('LAI-003', 'Yaourt Nature', '656', '2024-02-28', 25, NULL, 2),
('SUR-001', 'Petits pois surgelés', '656', '2024-12-31', 0, NULL, 5),
('SUR-002', 'Pizza quatre fromages', '2620', '2024-01-30', 30, NULL, 5),
('SUR-003', 'Frites croustillantes', '1311', '2024-10-31', 40, NULL, 5),
('VAL-001', 'Chocolat noir', '1650', '2025-04-01', 145, NULL, 13),
('VAL-002', 'Chocolat blanc', '1350', '2025-04-01', 45, NULL, 13),
('VAL-003', 'Rose', '3500', '2024-07-01', 252, NULL, 13),
('VAL-004', 'Robe rouge', '9376', NULL, 29, NULL, 13),
('VAL-005', 'préservatif Durex', '750', '2025-01-01', 150, NULL, 13),
('VAL-006', 'Chocolat Ferero Rocher', '6376', '2026-01-01', 37, NULL, 13),
('VAL-007', 'String', '376', NULL, 20, NULL, 13),
('VAL-008', 'Parfum Channel pour femme', '81000', NULL, 10, NULL, 13),
('VAL-009', 'Montre Audemard Piguet Full VVS', '7500000', NULL, 1, NULL, 13),
('VAL-010', 'Pack Amour parfait', '217500', NULL, 5, NULL, 13),
('VIE-001', 'Steak de bœuf', '5240', '2024-02-27', 0, NULL, 4),
('VIE-002', 'Filet de saumon', '6551', '2024-01-28', 5, NULL, 4),
('VIE-003', 'Cuisse de poulet', '2620', '2024-03-01', 25, NULL, 4);

-- --------------------------------------------------------

--
-- Structure de la table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
CREATE TABLE IF NOT EXISTS `promotions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `reduction` decimal(10,0) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `tous_produits` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''promotions'' contient le nom, le porcentage de réduction, la date de début et la date de fin de le promotion et enfin tous_produits pour mettre tous les produits en promotion, tous obligatoires et consititue en même temps l''historique des promotions dans le supermarché.';

-- --------------------------------------------------------

--
-- Structure de la table `ventes`
--

DROP TABLE IF EXISTS `ventes`;
CREATE TABLE IF NOT EXISTS `ventes` (
  `n_vente` int NOT NULL AUTO_INCREMENT,
  `cout` decimal(10,0) NOT NULL,
  `montant_recu` decimal(10,0) NOT NULL,
  `monnaie` decimal(10,0) NOT NULL,
  `moyen_paiement` varchar(15) NOT NULL,
  `date` datetime NOT NULL,
  `id_caissier` int NOT NULL,
  PRIMARY KEY (`n_vente`),
  KEY `id_caissier` (`id_caissier`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''ventes'' contient le numéro de vente, le coût, le montant reçu, la monnaie, le moyen de paiement(ESPECE, CARTE BANCAIRE, MOMO), la date et l''id du caissier de l''achat d''un client, tous obligatoires. L''id du caissier est l''id de l''employé ayant vendu les produits au client, la table constitut en queleques sortes l''historique de vente, en liaison avec les tables ''produits'' et ''ventes_produits''.';

--
-- Déchargement des données de la table `ventes`
--

INSERT INTO `ventes` (`n_vente`, `cout`, `montant_recu`, `monnaie`, `moyen_paiement`, `date`, `id_caissier`) VALUES
(1, '106504', '110000', '3496', 'ESPECE', '2024-02-26 00:46:12', 3),
(2, '6560', '7000', '440', 'MOMO', '2024-02-26 00:47:44', 3),
(3, '7500000', '7500000', '0', 'CARTE BANCAIRE', '2024-02-26 00:48:53', 3),
(4, '22950', '23000', '50', 'ESPECE', '2024-02-26 01:57:50', 4),
(5, '104840', '104840', '0', 'CARTE BANCAIRE', '2024-02-26 02:00:55', 4);

-- --------------------------------------------------------

--
-- Structure de la table `ventes_produits`
--

DROP TABLE IF EXISTS `ventes_produits`;
CREATE TABLE IF NOT EXISTS `ventes_produits` (
  `n_vente` int NOT NULL,
  `code_produit` varchar(15) NOT NULL,
  `quantite` int NOT NULL,
  `montant` int NOT NULL,
  KEY `n_vente` (`n_vente`),
  KEY `code_produit` (`code_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''ventes_produits'' constitue une liaison entre les tables ''produits'' et ''ventes'' car plusieurs produits peuvent appartenir à une même vente (le cas où le client achète plusieurs produits), ainsi pour chaque produit, on a le code produit, le numéro de vente , la quantité acheté et le montant de la vente du nombre de produit.';

--
-- Déchargement des données de la table `ventes_produits`
--

INSERT INTO `ventes_produits` (`n_vente`, `code_produit`, `quantite`, `montant`) VALUES
(1, 'VAL-001', 5, 8250),
(1, 'VAL-002', 5, 6750),
(1, 'VAL-006', 3, 19128),
(1, 'VAL-003', 18, 63000),
(1, 'VAL-004', 1, 9376),
(2, 'BOI-001', 10, 6560),
(3, 'VAL-009', 1, 7500000),
(4, 'ANI-002', 5, 9830),
(4, 'ANI-003', 20, 13120),
(5, 'FRU-002', 10, 6560),
(5, 'FRU-003', 10, 13110),
(5, 'LAI-002', 10, 19660),
(5, 'VIE-002', 10, 65510);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`id_promotion`) REFERENCES `promotions` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_2` FOREIGN KEY (`code_produit`) REFERENCES `produits` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `commandes_ibfk_3` FOREIGN KEY (`code_fournisseur`) REFERENCES `fournisseurs` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `produits`
--
ALTER TABLE `produits`
  ADD CONSTRAINT `produits_ibfk_1` FOREIGN KEY (`id_categorie`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `produits_ibfk_2` FOREIGN KEY (`id_promotion`) REFERENCES `promotions` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Contraintes pour la table `ventes`
--
ALTER TABLE `ventes`
  ADD CONSTRAINT `ventes_ibfk_1` FOREIGN KEY (`id_caissier`) REFERENCES `personnel` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `ventes_produits`
--
ALTER TABLE `ventes_produits`
  ADD CONSTRAINT `ventes_produits_ibfk_1` FOREIGN KEY (`n_vente`) REFERENCES `ventes` (`n_vente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `ventes_produits_ibfk_2` FOREIGN KEY (`code_produit`) REFERENCES `produits` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
