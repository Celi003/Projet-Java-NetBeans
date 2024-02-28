-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le : mer. 28 fév. 2024 à 02:54
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''categories'' contient le nom, la description et l''id de promotion de la catégorie, la description peut avoir la valeur NULL, car certaines catégories sont déjà assez précises uniquement avec leurs nom, donc la description est beaucoup plus pour les catégories complexes, quant à l''id_promotion, il peut prendre la valeur null, car d''abord il permet de mettre tout les  produits de la catégorie en question en promotion, et tous les produits ne sont pas tenus d''être en promotion d''où la valeur NULL pour les catégories n''étant pas en promotion.';

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''personnel'' contient le nom, le(s) prénom(s), le rôle("admin" , "caissier" , "aide client" , "agent de nettoyage" , "agent de parking" , "testeur produit" , "agent de sécurité"), le contact(au format internationnal ex:+22940404522), l''identifiant et le mot de passe de chaque employés. L''identifiant et le mot de passe peuvent prendre la valeur NULL car c''est pas tous les employés qui ont de mot d''identifiant et de mot de passe, c''est uniquement les caissiers et l''administrateur.';

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `prenoms`, `role`, `contact`, `identifiant`, `mot_de_passe`) VALUES
(1, 'DEFAULT', 'Default', 'admin', '+22940404522', 'admin', 'admin');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='La table ''ventes'' contient le numéro de vente, le coût, le montant reçu, la monnaie, le moyen de paiement(ESPECE, CARTE BANCAIRE, MOMO), la date et l''id du caissier de l''achat d''un client, tous obligatoires. L''id du caissier est l''id de l''employé ayant vendu les produits au client, la table constitut en queleques sortes l''historique de vente, en liaison avec les tables ''produits'' et ''ventes_produits''.';

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
