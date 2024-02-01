# Projet-Java-NetBeans

# Dépendances

- Le fichier 'gestion.sql' contient les informations de création de la base de donnée , aprèsavoire créé la base de données avec le nom 'gestion' dans phpMyAdmin dans votre navigateur , vous allez dans l'éditeur de requête sql pour coller les requêtes du fichier 'gestion.sql' afin que chaque contributeur ait une même structure de base de données;

- Le fichier /src/Parameter.java/ à été ajouté au .gitignore parceque les paramètres diffèrent de chaque ordinateurs et utilisateurs , donc assurez-vous de bien mettre le mot de passe d'accès à votre phpMyAdmin pour la variable pass et de changer le numéro de port dans la variable db s'il n'est pas 3306.

# Déploiement

- Allez dans le repertoire où vous souhaitez cloner le projet , ouvrez un terminal dans ce dossier puis tapez la commande "git clone 'https://github.com/Celi003/Projet-Java-NetBeans.git'" pour cloner le repository;

- Ouvrez NetBeans , allez dans le menu Fichier < Ouvrir fichier ou tapez "Ctrl+Shift+O" , une nouvelle fenêtre de choix de dossier s'ouvrira pour que vous sélectionnez le projet à ouvrir , allez dans le répertoire où vous avez cloné le repository et ouvrez le projet "Système_de_gestion_de_stock" ;

- Ajoutez le pilote "com.mysql.jdbc_5.1.5.jar" au projet en faisantclic droit sur Libraries , Add JAR/Folder , puis sélectionnez le pilote qui se trouve déjà dans le répertoire du projet pour ne pas avoir des problèmes de compatibilité.

# Utilisation/Développeurs

- Nous supposons que chaque contributeur connait la syntaxe en java et quelques éléments de base en GUI/Java avec NetBeans , donc l'accent sera mis sur l'organisation des fichiers dans le projet;

- Le fichier /src/Parameter.java contient les déclarations des éléments nécessaires à l'obtention connexion à la base de données (nom = "root";pass = null;db = "jdbc:mysql://localhost:3306/gestion;) ,ces derniers sont donc utilisés à l'instanciation d'un objet de la classe db_Connection , donc pour obtenir une connexion ,il suffit de créer un objet db_connection avec les parametres de connection(db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass));

- Après la connexion , il suffit de créer les statements et les resultset pour interagir avec la base de données , mais nous avons simplifié cela en ajoutant des méthodes spécifiques a la classe db_Connection pour éviter les répétitions et faciliter la tâche aux développeurs

- Les requêtes:
  . Essayez de lire et comprendre les méthodes de la classe db_Connection afin de vous retrouver , le principe est simple , au lieu de chaque fois créé des statements et des resultsets , on met juste les parametres et la classe se charge d'exécuter la requête et renvoie soit un resultset soit un string en fonction du type de la requête
  Ex: queryInsert(String nomTable, String[] nomColonnes, String[] contenuTableau)
  String[] colonnes={"nom","prenoms","role","identifiant","mot_de_passe"};
  String[] valeurs={"DOE","John","admin","admin","admin"};
  System.out.println(db.queryInsert("utilisateurs", colonnes, valeurs));//Ce qui revient a INSERT INTO utilisateurs("nom","prenoms","role","identifiant","mot_de_passe") VALUES("DOE","John","admin","admin","admin")
  Veuillez adaptez le code en fonction des parametres de votre fonctionnalité et surtout lisez et comprenez les méthodes de la db_Connection.

# Progression

.Création d'interface pour l'application ✅;
.Page login ✅;
.Gestion des utilisateurs ✅;
.Gestion des produits;
.Gestion des catégories de produits(facultatif);
.Gestion des ventes;
.Gestion des commandes;
.Gestion des fournisseurs;
.Système de suivi des ventes(facultatif);
.Gestion des promotions;
