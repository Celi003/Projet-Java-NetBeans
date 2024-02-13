-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le : mar. 13 fév. 2024 à 22:39
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
  `nom` varchar(30) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `en_promotion` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`id`, `nom`, `description`, `en_promotion`) VALUES
(1, 'Test', 'test', 0);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `date_commande` date NOT NULL,
  `code_produit` varchar(15) NOT NULL,
  `quantite` int NOT NULL,
  `code_fournisseur` varchar(15) NOT NULL,
  `date_reception` date DEFAULT NULL,
  KEY `code_fournisseur` (`code_fournisseur`),
  KEY `code_produit` (`code_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `fournisseurs`
--

DROP TABLE IF EXISTS `fournisseurs`;
CREATE TABLE IF NOT EXISTS `fournisseurs` (
  `code` varchar(15) NOT NULL,
  `nom_ou_entreprise` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contact` varchar(8) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `fournisseurs`
--

INSERT INTO `fournisseurs` (`code`, `nom_ou_entreprise`, `contact`) VALUES
('F2', 'Lenovo', '00000000'),
('F8', 'Erevan', '00000000');

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
  `contact` varchar(8) NOT NULL,
  `identifiant` varchar(30) DEFAULT NULL,
  `mot_de_passe` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `prenoms`, `role`, `contact`, `identifiant`, `mot_de_passe`) VALUES
(1, 'ADMIN', 'Admin', 'admin', '0', 'admin', 'admin'),
(2, 'CAISSIER', 'Caissier', 'caissier', '00000000', 'caissier', 'caissier'),
(5, 'ABOBO', 'Thiiery', 'agent de nettoyage', '00586175', NULL, NULL),
(6, 'xx', 'yy', 'agent de parking', '12345678', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `produits`
--

DROP TABLE IF EXISTS `produits`;
CREATE TABLE IF NOT EXISTS `produits` (
  `code` varchar(15) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prix` decimal(10,0) NOT NULL,
  `date_peremption` date NOT NULL,
  `en_promotion` tinyint(1) NOT NULL,
  `quantite_dispo` int NOT NULL,
  `id_categorie` int NOT NULL,
  PRIMARY KEY (`code`),
  KEY `id_categorie` (`id_categorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `produits`
--

INSERT INTO `produits` (`code`, `nom`, `prix`, `date_peremption`, `en_promotion`, `quantite_dispo`, `id_categorie`) VALUES
('x', 'x', '1000', '2024-02-01', 0, 5, 1),
('y', 'y', '1000', '2024-02-14', 0, 10, 1);

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ventes`
--

DROP TABLE IF EXISTS `ventes`;
CREATE TABLE IF NOT EXISTS `ventes` (
  `n_vente` int NOT NULL AUTO_INCREMENT,
  `produits` text NOT NULL,
  `cout` int NOT NULL,
  `montant_recu` int NOT NULL,
  `monnaie` int NOT NULL,
  `moyen_paiement` varchar(15) NOT NULL,
  `date` datetime NOT NULL,
  `id_caissier` int NOT NULL,
  PRIMARY KEY (`n_vente`),
  KEY `id_caissier` (`id_caissier`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ventes`
--

INSERT INTO `ventes` (`n_vente`, `produits`, `cout`, `montant_recu`, `monnaie`, `moyen_paiement`, `date`, `id_caissier`) VALUES
(2, '{x,1,10,10}', 10, 10, 0, 'ESPECE', '2024-02-12 01:39:49', 2);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_2` FOREIGN KEY (`code_fournisseur`) REFERENCES `fournisseurs` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `commandes_ibfk_3` FOREIGN KEY (`code_produit`) REFERENCES `produits` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `produits`
--
ALTER TABLE `produits`
  ADD CONSTRAINT `produits_ibfk_1` FOREIGN KEY (`id_categorie`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `ventes`
--
ALTER TABLE `ventes`
  ADD CONSTRAINT `ventes_ibfk_1` FOREIGN KEY (`id_caissier`) REFERENCES `personnel` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
