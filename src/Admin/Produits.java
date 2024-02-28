package Admin;

import Caissier.Historique;
import Main.Parameter;
//import Main.ResultSetTableModel;
import Main.db_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;

public class Produits extends javax.swing.JPanel {
    
    LocalDate currentDate = LocalDate.now();
    Promotion promo = new Promotion();
    db_connection db;
    ResultSet rs = null , rst = null;
    
    public Produits() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
        setTable();
        setCategorie();
    }
    
    public boolean valid(){
        if(nom.getText().length()<2 ){
            JOptionPane.showMessageDialog(this, "Le nom du produit doit contenir au minimum 2 caractères");
            return false;
        }
        else if(code.getText().length()<5 ){
            JOptionPane.showMessageDialog(this, "Le code du produit doit contenir au minimum 5 caractères");
            return false;
        }
        else if((!(Year.getSelectedItem().toString().equals("----")) && (Month.getSelectedItem().toString().equals("--") || Day.getSelectedItem().toString().equals("--"))) ||
                (!(Month.getSelectedItem().toString().equals("--")) && (Year.getSelectedItem().toString().equals("----") || Day.getSelectedItem().toString().equals("--"))) ||
                (!(Day.getSelectedItem().toString().equals("--")) && (Month.getSelectedItem().toString().equals("--") || Year.getSelectedItem().toString().equals("----")))){
            JOptionPane.showMessageDialog(this, "Date incorrect , veuillez donner une valeur correcte pour l'année, le mois et le jour");
            return false;
        }
        else if(categorie.getSelectedItem().toString().isBlank()){
            JOptionPane.showMessageDialog(this, "Veuillez ajouter une catégorie avant d'ajouter ce produit car chaque produit doit obligatoirement avoir une catégorie");
            return false;
        }
        return true;
    }
    
    public void setInputPromo(){
        if(promo.valid()!=0){
            en_promotion.setVisible(true);
        }
        else{
            en_promotion.setVisible(false);
        }
    }
    
    public void setCategorie(){
        ComboModel = (DefaultComboBoxModel<String>) categorie.getModel();
        FiltreModel = (DefaultComboBoxModel<String>) FiltreCategorie.getModel();
        ComboModel.removeAllElements();
        FiltreModel.removeAllElements();
        FiltreModel.addElement("Toutes les catégories");
        try {
            rs = db.querySelectAll("categories");
            while(rs.next()){
                ComboModel.addElement(rs.getString("nom"));
                FiltreModel.addElement(rs.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Historique.class.getName()).log(Level.SEVERE, null, ex);
        }
        categorie.setModel(ComboModel);
        FiltreCategorie.setModel(FiltreModel);
    }

    public void RechercheModifie(){
        if(Recherche.getText().equals("")){
            Filtrer();
        }
        else{
            String etat = "produits.nom LIKE '%" + Recherche.getText() +"%' OR code LIKE '%" + Recherche.getText() +"%'";
            produits.setModel(new ResultSetTableModel(rs));
            if(promo.valid()!=0){
                String[] colonnes = {"code","produits.nom","prix","date_peremption","promotions.nom AS promotion","quantite_dispo","categories.nom AS catégorie"};
                rs = db.fcSelectCommand(colonnes, "produits LEFT JOIN categories ON id_categorie=categories.id LEFT JOIN promotions ON produits.id_promotion=promotions.id", etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else{
                String[] colonnes = {"code","produits.nom","prix","date_peremption","quantite_dispo","categories.nom AS catégorie"};
                rs = db.fcSelectCommand(colonnes, "produits JOIN categories ON id_categorie=categories.id", etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
        }
    }
    
    public void setTable(){
        if(promo.valid()!=0){
            String[] colonnes = {"code","produits.nom","prix","date_peremption","promotions.nom AS promotion","quantite_dispo","categories.nom AS catégorie"};
            rs = db.querySelect(colonnes, "produits LEFT JOIN categories ON id_categorie=categories.id LEFT JOIN promotions ON produits.id_promotion=promotions.id");
            produits.setModel(new ResultSetTableModel(rs));
        }
        else{
            String[] colonnes = {"code","produits.nom","prix","date_peremption","quantite_dispo","categories.nom AS catégorie"};
            rs = db.querySelect(colonnes, "produits LEFT JOIN categories ON id_categorie=categories.id ");
            produits.setModel(new ResultSetTableModel(rs));
        }
    }
    
    public void CreateProduct(){
        code.setText("");
        nom.setText("");
        prix.setValue(1);
        quantite.setValue(0);
        Year.setSelectedIndex(0);
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Ajouter.setVisible(true);
        Create.setVisible(false);
        en_promotion.setSelected(false);
    }
    
    public void Filtrer(){
        String[] colonnes = null;
        String table="produits LEFT JOIN categories ON id_categorie=categories.id LEFT JOIN promotions ON produits.id_promotion=promotions.id";
        if(promo.valid()!=0){
            String[] cols = {"code","produits.nom","prix","date_peremption","promotions.nom AS promotion","quantite_dispo","categories.nom AS catégorie"};
            colonnes = cols.clone();
        }
        else{
            String[] cols = {"code","produits.nom","prix","date_peremption","quantite_dispo","categories.nom AS catégorie"};
            colonnes = cols.clone();
        }
        if(FiltreCategorie.getSelectedItem()!=null){
            if(filtre.getSelectedItem().equals("Tous les produits") && FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                setTable();
            }
            else if(filtre.getSelectedItem().equals("Tous les produits") && !FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="categories.nom = '"+FiltreCategorie.getSelectedItem().toString()+"'";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits en rupture de stock") && FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="quantite_dispo = 0";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits en rupture de stock") && !FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="quantite_dispo = 0 AND categories.nom = '"+FiltreCategorie.getSelectedItem().toString()+"'";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits périmés") && FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="date_peremption < CURDATE()";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits périmés") && !FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="date_peremption < CURDATE() AND categories.nom = '"+FiltreCategorie.getSelectedItem().toString()+"'";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits en promotion") && FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="produits.id_promotion IS NOT NULL";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
            else if(filtre.getSelectedItem().equals("Produits périmés") && !FiltreCategorie.getSelectedItem().equals("Toutes les catégories")){
                String etat="produits.id_promotion IS NOT NULL AND categories.nom = '"+FiltreCategorie.getSelectedItem().toString()+"'";
                rs = db.fcSelectCommand(colonnes, table, etat);
                produits.setModel(new ResultSetTableModel(rs));
            }
        } 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        prix = new javax.swing.JSpinner();
        PanelDate = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Year = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        Month = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        Day = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        quantite = new javax.swing.JSpinner();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        categorie = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        Modifier = new javax.swing.JButton();
        Ajouter = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();
        en_promotion = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        produits = new javax.swing.JTable();
        Create = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        filtre = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        Recherche = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        FiltreCategorie = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 102));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Produits_1.png"))); // NOI18N
        jLabel11.setText("Produits");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 102));
        jLabel3.setText("Nom du Produit      : ");
        jPanel2.add(jLabel3);

        nom.setForeground(new java.awt.Color(0, 102, 102));
        jPanel2.add(nom);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 102));
        jLabel4.setText("Code Produit           : ");
        jPanel3.add(jLabel4);

        code.setForeground(new java.awt.Color(0, 102, 102));
        jPanel3.add(code);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 102));
        jLabel5.setText("Prix U                        : ");
        jPanel4.add(jLabel5);
        jPanel4.add(prix);
        prix.setModel(new SpinnerNumberModel(1, 1, 1000000000, 500));

        PanelDate.setBackground(new java.awt.Color(255, 255, 255));
        PanelDate.setLayout(new javax.swing.BoxLayout(PanelDate, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Date de Péremption  : ");
        PanelDate.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));
        jLabel7.setText("Y : ");
        PanelDate.add(jLabel7);

        Year.setForeground(new java.awt.Color(0, 102, 102));
        Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        Year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearActionPerformed(evt);
            }
        });
        PanelDate.add(Year);
        int annee = currentDate.getYear();
        Year.addItem("----");
        for(int i =annee ; i<annee+15 ; i++){
            Year.addItem(Integer.toString(i));
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 102));
        jLabel12.setText("M: ");
        PanelDate.add(jLabel12);

        Month.setForeground(new java.awt.Color(0, 102, 102));
        Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        Month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonthActionPerformed(evt);
            }
        });
        PanelDate.add(Month);
        Month.addItem("--");
        for(int i = 1 ; i<=12 ; i++){
            if(i<10){
                Month.addItem("0"+Integer.toString(i));
            }
            else{
                Month.addItem(Integer.toString(i));
            }
        }

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 102));
        jLabel13.setText("D: ");
        PanelDate.add(jLabel13);

        Day.setForeground(new java.awt.Color(0, 102, 102));
        Day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DayActionPerformed(evt);
            }
        });
        PanelDate.add(Day);
        Day.addItem("--");
        for(int i = 1 ; i<=31 ; i++){
            if(i<10){
                Day.addItem("0"+Integer.toString(i));
            }
            else{
                Day.addItem(Integer.toString(i));
            }
        }

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 102));
        jLabel9.setText("Quantité disponnible : ");
        jPanel6.add(jLabel9);
        jPanel6.add(quantite);
        quantite.setModel(new SpinnerNumberModel(1, 0, 1000, 5));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 102));
        jLabel10.setText("Catégorie                   : ");
        jPanel7.add(jLabel10);

        categorie.setForeground(new java.awt.Color(0, 102, 102));
        categorie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        categorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorieActionPerformed(evt);
            }
        });
        jPanel7.add(categorie);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        Modifier.setBackground(new java.awt.Color(0, 255, 255));
        Modifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });
        jPanel8.add(Modifier);
        Modifier.setVisible(false);

        Ajouter.setBackground(new java.awt.Color(0, 204, 0));
        Ajouter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Ajouter.setForeground(new java.awt.Color(255, 255, 255));
        Ajouter.setText("Ajouter");
        Ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterActionPerformed(evt);
            }
        });
        jPanel8.add(Ajouter);

        Supprimer.setBackground(new java.awt.Color(255, 0, 0));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });
        jPanel8.add(Supprimer);
        Supprimer.setVisible(false);

        en_promotion.setBackground(new java.awt.Color(255, 255, 255));
        en_promotion.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        en_promotion.setForeground(new java.awt.Color(255, 0, 102));
        en_promotion.setText("Mettre en promotion active");
        en_promotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                en_promotionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(PanelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(en_promotion))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel11)
                .addGap(67, 67, 67)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(PanelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(en_promotion)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel1);

        jPanel9.setBackground(new java.awt.Color(255, 0, 102));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        produits.setForeground(new java.awt.Color(0, 102, 102));
        produits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "code", "nom", "Prix", "Date de Péremption", "Promotion", "Quantité disponible", "Catégorie"
            }
        ));
        produits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                produitsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(produits);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 693;
        gridBagConstraints.ipady = 499;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel9.add(jScrollPane1, gridBagConstraints);

        Create.setBackground(new java.awt.Color(0, 204, 0));
        Create.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Create.setForeground(new java.awt.Color(255, 255, 255));
        Create.setText("Ajouter");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        jPanel9.add(Create, gridBagConstraints);
        Create.setVisible(false);

        jPanel5.setBackground(new java.awt.Color(255, 0, 102));

        filtre.setForeground(new java.awt.Color(0, 102, 102));
        filtre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les produits", "Produits en rupture de stock", "Produits périmés", "Produits en promotion" }));
        filtre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtreActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Table des Produits ");

        Recherche.setForeground(new java.awt.Color(0, 102, 102));
        Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chercher.png"))); // NOI18N

        FiltreCategorie.setForeground(new java.awt.Color(0, 102, 102));
        FiltreCategorie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        FiltreCategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FiltreCategorieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filtre, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(12, 12, 12)
                .addComponent(FiltreCategorie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filtre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(FiltreCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );

        Recherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                RechercheModifie();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                RechercheModifie();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                RechercheModifie();
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 159;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel9.add(jPanel5, gridBagConstraints);

        add(jPanel9);
    }// </editor-fold>//GEN-END:initComponents

    private void categorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categorieActionPerformed

    private void en_promotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_en_promotionActionPerformed
        float reduction = 0 , price = Float.parseFloat(prix.getValue().toString()) , new_price = 0;
        rs = db.querySelectAll("promotions", "id = " + Integer.toString(promo.valid()));
        try {
            while(rs.next()){
                reduction = rs.getFloat("reduction");
            }
            if(en_promotion.isSelected()){
                new_price = price - (price*reduction)/100;
                prix.setValue(new_price);
            }
            else{
                new_price = price / (1-(reduction/100));
                prix.setValue(new_price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_en_promotionActionPerformed

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        if(valid()){
            if(code.getText().length()>=5 ){
                rs = db.querySelectAll("produits", "code = '"+code.getText()+"'");
                try {
                    while(rs.next()){
                        JOptionPane.showMessageDialog(this, "Un produit possède déjà ce code, c'est à dire que le produit que vous essayez d'ajouter existe déjà dans la base de données");
                        return ;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String[] colonnes = {"code","nom","prix","date_peremption","id_promotion","quantite_dispo","id_categorie"};
            String[] cols = {"code","nom","prix","id_promotion","quantite_dispo","id_categorie"};
            String date = Year.getSelectedItem().toString()+"-"+Month.getSelectedItem().toString()+"-"+Day.getSelectedItem().toString();
            int id_categorie = 0;
            rs = db.querySelectAll("categories");
            try {
                while(rs.next()){
                    if(categorie.getSelectedItem().toString().equals(rs.getString("nom"))){
                        id_categorie = Integer.parseInt(rs.getString("id"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(en_promotion.isSelected()){
                if(date.equals("----------")){
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),Integer.toString(promo.valid()),quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryInsert("produits", cols, valeurs));
                }
                else{
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),date,Integer.toString(promo.valid()),quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryInsert("produits", colonnes, valeurs));
                }
            }
            else{
                if(date.equals("----------")){
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),"NULL",quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryInsert("produits", cols, valeurs));
                }
                else{
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),date,"NULL",quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryInsert("produits", colonnes, valeurs));
                }
            }
            RechercheModifie();
            CreateProduct();
        }
    }//GEN-LAST:event_AjouterActionPerformed

    private void produitsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produitsMouseClicked
        int i = produits.getSelectedRow();
        Object peremption = produits.getValueAt(i, 3);
        if(peremption!=null){
            String[] y_m_d = peremption.toString().split("-");
            Year.setSelectedItem(y_m_d[0]);Month.setSelectedItem(y_m_d[1]);Day.setSelectedItem(y_m_d[2]);
        }
        else{
            Year.setSelectedIndex(0);Month.setSelectedIndex(0);Day.setSelectedIndex(0);
        }
        code.setText(produits.getValueAt(i, 0).toString());
        nom.setText(produits.getValueAt(i, 1).toString());
        prix.setValue(produits.getValueAt(i, 2));
        if(promo.valid()!=0){
            if(!(produits.getValueAt(i, 4)==null)){
                en_promotion.setSelected(true);
            }
            else{
                en_promotion.setSelected(false);
            }
            quantite.setValue(produits.getValueAt(i, 5));
            categorie.setSelectedItem(produits.getValueAt(i, 6));
        }
        else{
            quantite.setValue(produits.getValueAt(i, 4));
            categorie.setSelectedItem(produits.getValueAt(i, 5));
        }
        Modifier.setVisible(true);
        Ajouter.setVisible(false);
        Supprimer.setVisible(true);
        Create.setVisible(true);
        rs = db.querySelectAll("commandes","code_produit='"+produits.getValueAt(i, 0).toString()+"'");
        rst = db.querySelectAll("ventes_produits","code_produit='"+produits.getValueAt(i, 0).toString()+"'");
        try {
            while(rs.next() || rst.next()){
                Supprimer.setText("Vider");
                return;
            }
            Supprimer.setText("Supprimer");
        } catch (SQLException ex) {
            Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_produitsMouseClicked

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        if(valid()){
            if(code.getText().length()>=5 && !code.getText().equals(produits.getValueAt(produits.getSelectedRow(), 0))){
                rs = db.querySelectAll("produits", "code = '"+code.getText()+"'");
                try {
                    while(rs.next()){
                        JOptionPane.showMessageDialog(this, "Un produit possède déjà ce code, c'est à dire que le produit que vous essayez de modifier existe déjà dans la base de données");
                        return ;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String[] colonnes = {"code","nom","prix","date_peremption","id_promotion","quantite_dispo","id_categorie"};
            String date = Year.getSelectedItem().toString()+"-"+Month.getSelectedItem().toString()+"-"+Day.getSelectedItem().toString();
            int id_categorie = 0 , i = produits.getSelectedRow();
            rs = db.querySelectAll("categories");
            try {
                while(rs.next()){
                    if(categorie.getSelectedItem().toString().equals(rs.getString("nom"))){
                        id_categorie = Integer.parseInt(rs.getString("id"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
            }
            //promo.delPromotion();
            if(en_promotion.isSelected()){
                if(date.equals("----------")){
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),"NULL",Integer.toString(promo.valid()),quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs,"code = '"+produits.getValueAt(i, 0)+"'"));
                }
                else{
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),date,Integer.toString(promo.valid()),quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs ,"code = '"+produits.getValueAt(i, 0)+"'"));
                }
            }
            else{
                if(date.equals("----------")){
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),"NULL","NULL",quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs,"code = '"+produits.getValueAt(i, 0)+"'"));
                }
                else{
                    String[] valeurs = {code.getText(),nom.getText(),prix.getValue().toString(),date,"NULL",quantite.getValue().toString(),Integer.toString(id_categorie)};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs ,"code = '"+produits.getValueAt(i, 0)+"'"));
                }
            }
            //promo.setPromotion();
            RechercheModifie();
            CreateProduct();
        }
    }//GEN-LAST:event_ModifierActionPerformed

    private void YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearActionPerformed
        if(Year.getSelectedItem().equals("----")){
            Month.setSelectedItem("--");
            Day.setSelectedItem("--");
        }
    }//GEN-LAST:event_YearActionPerformed

    private void MonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonthActionPerformed
        if(Month.getSelectedItem().equals("--")){
            Year.setSelectedItem("----");
            Day.setSelectedItem("--");
        }
    }//GEN-LAST:event_MonthActionPerformed

    private void DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DayActionPerformed
        if(Day.getSelectedItem().equals("--")){
            Year.setSelectedItem("----");
            Month.setSelectedItem("--");
        }
    }//GEN-LAST:event_DayActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        if(Supprimer.getText().equals("Vider")){
            if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir vider le stock de ce produit ??",
            "Vider le stock", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                System.out.println(db.executionUpdate("UPDATE produits SET quantite_dispo = 0 WHERE code ='"+produits.getValueAt(produits.getSelectedRow(), 0)+"'"));
                RechercheModifie();
                CreateProduct();
            }
        }
        else{
            if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer ce produit ??",
            "Supression de produit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                System.out.println(db.executionUpdate("DELETE FROM produits WHERE code ='"+produits.getValueAt(produits.getSelectedRow(), 0)+"'"));
                RechercheModifie();
                CreateProduct();
            }
        }
    }//GEN-LAST:event_SupprimerActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        CreateProduct();
    }//GEN-LAST:event_CreateActionPerformed

    private void filtreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtreActionPerformed
        Filtrer();
    }//GEN-LAST:event_filtreActionPerformed

    private void RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RechercheActionPerformed

    private void FiltreCategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FiltreCategorieActionPerformed
        Filtrer();
    }//GEN-LAST:event_FiltreCategorieActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JButton Create;
    private javax.swing.JComboBox<String> Day;
    private javax.swing.JComboBox<String> FiltreCategorie;
    private javax.swing.JButton Modifier;
    private javax.swing.JComboBox<String> Month;
    private javax.swing.JPanel PanelDate;
    private javax.swing.JTextField Recherche;
    private javax.swing.JButton Supprimer;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JComboBox<String> categorie;
    private javax.swing.JTextField code;
    private javax.swing.JCheckBox en_promotion;
    private javax.swing.JComboBox<String> filtre;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nom;
    private javax.swing.JSpinner prix;
    private javax.swing.JTable produits;
    private javax.swing.JSpinner quantite;
    // End of variables declaration//GEN-END:variables
    private DefaultComboBoxModel<String> ComboModel;
    private DefaultComboBoxModel<String> FiltreModel;
    public class ResultSetTableModel extends AbstractTableModel {

    private ResultSet rs;

    public ResultSetTableModel(ResultSet rs) {
        this.rs = rs;
        fireTableDataChanged();
    }

    public int getColumnCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                return  rs.getMetaData().getColumnCount();
            }
        } catch (SQLException e) {
            System.out.println("getColumncount  resultset generating error while getting column count");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int getRowCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                rs.last();
                return rs.getRow();
            }
        } catch (SQLException e) {
            System.out.println("getrowcount resultset generating error while getting rows count");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > getRowCount()
                || columnIndex < 0 || columnIndex > getColumnCount()) {
            return null;
        }
        try {
            if (rs == null) {
                return null;
            } else {
                rs.absolute(rowIndex + 1);
                if (columnIndex == 3) { // columnIndexOfDatePeremption représente l'indice de la colonne "date_peremption"
                // Récupérer la valeur de la colonne "date_peremption" en tant que String
                    return rs.getString(columnIndex + 1);
                } else {
                    // Récupérer les autres valeurs normalement
                    return rs.getObject(columnIndex + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("getvalueat resultset generating error while fetching rows");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            if (rs == null) {
                return null;
            } else {
                // Récupérer le nom de la colonne à partir de l'alias si disponible
                String columnName = rs.getMetaData().getColumnLabel(columnIndex + 1);
                if (columnName == null || columnName.isEmpty()) {
                    // Si l'alias n'est pas disponible, utilisez le nom de la colonne de la table
                    columnName = rs.getMetaData().getColumnName(columnIndex + 1);
                }
                return columnName;
            }
        } catch (SQLException e) {
            System.out.println("getColumnname  resultset generating error while fetching column name");
            System.out.println(e.getMessage());
        }
        return super.getColumnName(columnIndex);
    }
}
}
