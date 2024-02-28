package Admin;

import Caissier.Historique;
import Main.Parameter;
import Main.db_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class Commande extends javax.swing.JPanel {

    LocalDate currentDate = LocalDate.now();
    db_connection db;
    ResultSet rs = null;
    String espace = "  ";
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat RecFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public Commande() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
        setTable();
    }
    
    public boolean valid(){
        if(produit.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Veuillez entrer le nom du produit que vous voulez commander");
            return false;
        }
        else if(fournisseur.getSelectedItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(this, "Aucun fournisseur disponible , veuillez ajouter un fournisseur dans la gestion des fournisseurs afin de pouvoir l'associé à votre commande");
            return false;
        }
        else if((!(Year.getSelectedItem().toString().equals("----")) && (Month.getSelectedItem().toString().equals("--") || Day.getSelectedItem().toString().equals("--"))) ||
                (!(Month.getSelectedItem().toString().equals("--")) && (Year.getSelectedItem().toString().equals("----") || Day.getSelectedItem().toString().equals("--"))) ||
                (!(Day.getSelectedItem().toString().equals("--")) && (Month.getSelectedItem().toString().equals("--") || Year.getSelectedItem().toString().equals("----")))){
            JOptionPane.showMessageDialog(this, "Date incorrect , veuillez donner une valeur correcte pour l'année, le mois et le jour");
            return false;
        }
        setList();
        ListModel<String> model = jList1.getModel();
        for(int i=0 ; i<model.getSize() ; i++){
            String elm = model.getElementAt(i);
            String[] code_nom = elm.split(espace);
            if(code_nom[1].toLowerCase().equals(produit.getText().toLowerCase())){
                System.out.println(elm);
                produit.setText(elm);
                return true;
            }
            else if(i == model.getSize()-1){
                JOptionPane.showMessageDialog(this, "Le produit que vous avez entré n'existe pas dans le supermarché , veuiller l'ajouter dans la gestion des produits avant de pouvoir le commander");
                return false;
            }
        }
        return true;
    }
    
    public void setTable(){
        String[] colonnes = {"date_commande","nom AS produit","quantite","nom_ou_entreprise AS fournisseur","date_reception"};
        rs = db.querySelect(colonnes, "commandes JOIN produits ON code_produit = produits.code JOIN fournisseurs ON code_fournisseur = fournisseurs.code");
        Commandes.setModel(new ResultSetTableModel(rs));
    }
    
    public void TableModifie(){
        if(Recherche.getText().equals("")){
            setTable();
        }
        else{
            String etat = "nom LIKE '%" + Recherche.getText() +"%' OR produits.code LIKE '%" + Recherche.getText() +"%'" +" OR nom_ou_entreprise LIKE '%" + Recherche.getText() +"%'";
            String[] colonnes = {"date_commande","nom AS produit","quantite","nom_ou_entreprise AS fournisseur","date_reception"};
            rs = db.fcSelectCommand(colonnes, "commandes JOIN produits ON code_produit = produits.code JOIN fournisseurs ON code_fournisseur = fournisseurs.code", etat);
            Commandes.setModel(new ResultSetTableModel(rs));
        }
    }
    
    public void RechercheModifie(){
        setList();
        DefaultListModel RechercheModel = new DefaultListModel<>();
        ListModel<String> model = jList1.getModel();
        for(int i=0 ; i<model.getSize() ; i++){
            String elm = model.getElementAt(i);
            if(elm.toLowerCase().contains(produit.getText().toLowerCase())){
                RechercheModel.addElement(elm);
            }
        }
        jList1.setModel(RechercheModel);
    }
    
    public void setList(){
        listModel = new DefaultListModel<>();
        String[] colonnes = {"code","nom"};
        rs = db.querySelect(colonnes, "produits");
        try{
            while(rs.next()){
                listModel.addElement(rs.getString("code") + espace + rs.getString("nom"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        jList1.setModel(listModel);
    }

    public void setFournisseurs(){
        rs = db.querySelectAll("fournisseurs");
        ComboModel = (DefaultComboBoxModel<String>) fournisseur.getModel();
        ComboModel.removeAllElements();
        try {
            while(rs.next()){
                ComboModel.addElement(rs.getString("nom_ou_entreprise"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Historique.class.getName()).log(Level.SEVERE, null, ex);
        }
        fournisseur.setModel(ComboModel);
    }
    
    public void CreateCommand(){
        produit.setText("");
        quantite.setValue(1);
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Ajouter.setVisible(true);
        Create.setVisible(false);
        PanelDate.setVisible(false);
        today.setVisible(false);
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
        jLabel5 = new javax.swing.JLabel();
        quantite = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        produit = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        Modifier = new javax.swing.JButton();
        Ajouter = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        fournisseur = new javax.swing.JComboBox<>();
        PanelDate = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Year = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        Month = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        Day = new javax.swing.JComboBox<>();
        today = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        Create = new javax.swing.JButton();
        Recherche = new javax.swing.JTextField();
        filtre = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Commandes = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 102));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Commandes.png"))); // NOI18N
        jLabel11.setText("Reapprovisionnement");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 44, 0, 0);
        jPanel1.add(jLabel11, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 102));
        jLabel5.setText("Quantité                  : ");
        jPanel2.add(jLabel5);
        jPanel2.add(quantite);
        quantite.setModel(new SpinnerNumberModel(1, 1, 100000, 1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 226;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 0, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 102));
        jLabel9.setText("Produit                    : ");
        jPanel5.add(jLabel9);

        produit.setForeground(new java.awt.Color(0, 102, 102));
        produit.setText("Rechercher un produit");
        produit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                produitMouseClicked(evt);
            }
        });
        produit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produitActionPerformed(evt);
            }
        });
        jPanel5.add(produit);
        produit.getDocument().addDocumentListener(new DocumentListener() {
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(42, 0, 0, 0);
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        Modifier.setBackground(new java.awt.Color(0, 204, 204));
        Modifier.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });
        jPanel7.add(Modifier);
        Modifier.setVisible(false);

        Ajouter.setBackground(new java.awt.Color(0, 204, 51));
        Ajouter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Ajouter.setForeground(new java.awt.Color(255, 255, 255));
        Ajouter.setText("Commander");
        Ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterActionPerformed(evt);
            }
        });
        jPanel7.add(Ajouter);

        Supprimer.setBackground(new java.awt.Color(255, 0, 0));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });
        jPanel7.add(Supprimer);
        Supprimer.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 141;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 117, 0);
        jPanel1.add(jPanel7, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 102));
        jLabel10.setText("Fournisseur              : ");
        jPanel6.add(jLabel10);

        fournisseur.setForeground(new java.awt.Color(0, 102, 102));
        fournisseur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fournisseur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fournisseurActionPerformed(evt);
            }
        });
        jPanel6.add(fournisseur);
        setFournisseurs();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 216;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 0, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        PanelDate.setBackground(new java.awt.Color(255, 255, 255));
        PanelDate.setLayout(new javax.swing.BoxLayout(PanelDate, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Date de  réception : ");
        PanelDate.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));
        jLabel7.setText("Y : ");
        PanelDate.add(jLabel7);

        Year.setForeground(new java.awt.Color(0, 102, 102));
        Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        PanelDate.add(Year);
        int annee = currentDate.getYear();
        Year.addItem("----");
        for(int i =annee ; i<annee+15 ; i++){
            Year.addItem(Integer.toString(i));
        }

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 102));
        jLabel13.setText("M: ");
        PanelDate.add(jLabel13);

        Month.setForeground(new java.awt.Color(0, 102, 102));
        Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
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
        Month.setSelectedItem(Integer.toString(currentDate.getMonthValue()));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 102));
        jLabel14.setText("D: ");
        PanelDate.add(jLabel14);

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
        Day.setSelectedItem(Integer.toString(currentDate.getDayOfWeek().getValue()));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(42, 0, 0, 0);
        jPanel1.add(PanelDate, gridBagConstraints);
        PanelDate.setVisible(false);

        today.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        today.setForeground(new java.awt.Color(255, 0, 102));
        today.setText("Aujourd'hui");
        today.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todayActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 130, 0, 0);
        jPanel1.add(today, gridBagConstraints);
        today.setVisible(false);

        add(jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 0, 102));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(255, 0, 102));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jList1.setForeground(new java.awt.Color(0, 102, 102));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);
        setList();
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !jList1.isSelectionEmpty()) {
                    String nom = jList1.getSelectedValue();
                    if(!jList1.isSelectionEmpty()){
                        String[] code_nom = nom.split(espace);
                        produit.setText(code_nom[1]);
                    }
                }
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.ipady = 460;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Liste des produits");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 42;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel4.add(jLabel8, gridBagConstraints);

        jPanel3.add(jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 0, 102));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Table des  Commandes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        jPanel8.add(jLabel12, gridBagConstraints);

        Create.setBackground(new java.awt.Color(0, 204, 51));
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        jPanel8.add(Create, gridBagConstraints);
        Create.setVisible(false);

        Recherche.setForeground(new java.awt.Color(0, 102, 102));
        Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 106;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 18, 0, 0);
        jPanel8.add(Recherche, gridBagConstraints);
        Recherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                TableModifie();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                TableModifie();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                TableModifie();
            }
        });

        filtre.setForeground(new java.awt.Color(0, 102, 102));
        filtre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toutes les commandes", "Commandes en cours", "Déjà reçu"}));
        filtre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel8.add(filtre, gridBagConstraints);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chercher.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel8.add(jButton4, gridBagConstraints);

        Commandes.setForeground(new java.awt.Color(0, 102, 102));
        Commandes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date de commande", "Produit", "Quantité", "Fournisseur", "Date de réception"
            }
        ));
        Commandes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CommandesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Commandes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 562;
        gridBagConstraints.ipady = 421;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel8.add(jScrollPane1, gridBagConstraints);

        jPanel3.add(jPanel8);

        add(jPanel3);
    }// </editor-fold>//GEN-END:initComponents

    private void produitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produitActionPerformed
        
    }//GEN-LAST:event_produitActionPerformed

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        if(valid()){
            date = new Date();
            String[] colonnes = {"date_commande","code_produit","quantite","code_fournisseur"};
            String product = produit.getText(),fourn = fournisseur.getSelectedItem().toString(),code="";
            String[] code_nom = product.split(espace) ;
            rs = db.querySelectAll("fournisseurs", "nom_ou_entreprise = '"+fourn+"'");
            try {
                while(rs.next()){
                    code = rs.getString("code");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
            String[] valeurs = {dateFormat.format(date),code_nom[0],quantite.getValue().toString(),code};
            System.out.println(db.queryInsert("commandes", colonnes, valeurs));
            setTable();
        }
    }//GEN-LAST:event_AjouterActionPerformed

    private void DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DayActionPerformed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        if(valid()){
            String[] colonnes = {"code_produit","quantite","code_fournisseur","date_reception"};
            String product = produit.getText(),code="";
            String date = Year.getSelectedItem().toString()+"-"+Month.getSelectedItem().toString()+"-"+Day.getSelectedItem().toString();
            String[] code_nom = product.split(espace) ;
            int i = Commandes.getSelectedRow(),qte_dispo = 0 , qte = Integer.parseInt(quantite.getValue().toString()) , new_qte = 0;
            rs = db.querySelectAll("fournisseurs", "nom_ou_entreprise = '"+fournisseur.getSelectedItem().toString()+"'");
            try {
                while(rs.next()){
                    code = rs.getString("code");
                }
                rs = db.querySelectAll("produits", "code = '" + code_nom[0] +"'");
                while(rs.next()){
                    qte_dispo = rs.getInt("quantite_dispo");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(date.equals("----------")){
                String[] valeurs = {code_nom[0],quantite.getValue().toString(),code,"NULL"};
                System.out.println(db.queryUpdate("commandes", colonnes, valeurs, "date_commande='"+Commandes.getValueAt(i, 0)+"'"));
                JOptionPane.showMessageDialog(this, "Commande modifié avec succès");
                setTable();
            }
            else{
                if(Commandes.getValueAt(i, 4)!=null){
                    String[] valeurs = {code_nom[0],quantite.getValue().toString(),code,date};
                    new_qte = qte_dispo - Integer.parseInt(Commandes.getValueAt(i, 2).toString()) + qte;
                    System.out.println(db.executionUpdate("UPDATE produits SET quantite_dispo = "+Integer.toString(new_qte)+" WHERE code ='" + code_nom[0] +"'"));
                    System.out.println(db.queryUpdate("commandes", colonnes, valeurs, "date_commande='"+Commandes.getValueAt(Commandes.getSelectedRow(), 0)+"'"));
                    JOptionPane.showMessageDialog(this, "Commande valide modifié avec succès");
                    setTable();
                }
                else{
                    String[] valeurs = {code_nom[0],quantite.getValue().toString(),code,date};
                    new_qte = qte_dispo + qte;
                    System.out.println(db.executionUpdate("UPDATE produits SET quantite_dispo = "+Integer.toString(new_qte)+" WHERE code ='" + code_nom[0] +"'"));
                    System.out.println(db.queryUpdate("commandes", colonnes, valeurs, "date_commande='"+Commandes.getValueAt(Commandes.getSelectedRow(), 0)+"'"));
                    JOptionPane.showMessageDialog(this, "Commande validé avec succès");
                    setTable();
                }
                System.out.println(new_qte);
            }
        }
    }//GEN-LAST:event_ModifierActionPerformed

    private void CommandesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CommandesMouseClicked
        int i = Commandes.getSelectedRow();
        PanelDate.setVisible(true);
        if(Commandes.getValueAt(i, 4) != null){
            String[] date_rec = Commandes.getValueAt(i, 4).toString().split("-");
            Year.setSelectedItem(date_rec[0]);Month.setSelectedItem(date_rec[1]);Day.setSelectedItem(date_rec[2]);
            Supprimer.setVisible(false);
        }
        else{
            Supprimer.setVisible(!false);
            Year.setSelectedItem("----");Month.setSelectedItem("--");Day.setSelectedItem("--");
        }
        Modifier.setVisible(!false);
        Ajouter.setVisible(false);
        Create.setVisible(true);
        today.setVisible(true);
        today.setSelected(false);
        produit.setText(Commandes.getValueAt(i, 1).toString());
        quantite.setValue(Commandes.getValueAt(i, 2));
        fournisseur.setSelectedItem(Commandes.getValueAt(i, 3));
    }//GEN-LAST:event_CommandesMouseClicked

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer cette commande ??",
            "Suppression de commande", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            System.out.println(db.queryDelete("commandes", "date_commande='"+Commandes.getValueAt(Commandes.getSelectedRow(), 0)+"'"));
            setTable();
            CreateCommand();
        }
    }//GEN-LAST:event_SupprimerActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        CreateCommand();
    }//GEN-LAST:event_CreateActionPerformed

    private void filtreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtreActionPerformed
        String[] colonnes = {"date_commande","nom AS produit","quantite","nom_ou_entreprise AS fournisseur","date_reception"};
        if(filtre.getSelectedItem().equals("Toutes les commandes")){
            setTable();
        }
        else if(filtre.getSelectedItem().equals("Commandes en cours")){
            String table = "commandes JOIN produits ON code_produit = produits.code JOIN fournisseurs ON code_fournisseur = fournisseurs.code" , etat="";
            etat = "date_reception IS NULL";
            rs = db.fcSelectCommand(colonnes, table, etat);
            Commandes.setModel(new ResultSetTableModel(rs));
        }
        else if(filtre.getSelectedItem().equals("Déjà reçu")){
            String table = "commandes JOIN produits ON code_produit = produits.code JOIN fournisseurs ON code_fournisseur = fournisseurs.code" , etat="";
            etat = "date_reception IS NOT NULL";
            rs = db.fcSelectCommand(colonnes, table, etat);
            Commandes.setModel(new ResultSetTableModel(rs));
        }
    }//GEN-LAST:event_filtreActionPerformed

    private void fournisseurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fournisseurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fournisseurActionPerformed

    private void RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RechercheActionPerformed

    private void produitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produitMouseClicked
        produit.setText("");
    }//GEN-LAST:event_produitMouseClicked

    private void todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todayActionPerformed
        currentDate = LocalDate.now();
        if(today.isSelected()){
            Year.setSelectedItem(Integer.toString(currentDate.getYear()));
            if(currentDate.getMonthValue()<10){
                Month.setSelectedItem("0"+Integer.toString(currentDate.getMonthValue()));
            }
            else{
                Month.setSelectedItem(Integer.toString(currentDate.getMonthValue()));
            }
            if(currentDate.getDayOfMonth()<10){
                Day.setSelectedItem("0"+Integer.toString(currentDate.getDayOfMonth()));
            }
            else{
                Day.setSelectedItem(Integer.toString(currentDate.getDayOfMonth()));
            }
        }
        else{
            Year.setSelectedItem("----");
            Month.setSelectedItem("--");
            Day.setSelectedItem("--");
        }
    }//GEN-LAST:event_todayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JTable Commandes;
    private javax.swing.JButton Create;
    private javax.swing.JComboBox<String> Day;
    private javax.swing.JButton Modifier;
    private javax.swing.JComboBox<String> Month;
    private javax.swing.JPanel PanelDate;
    private javax.swing.JTextField Recherche;
    private javax.swing.JButton Supprimer;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JComboBox<String> filtre;
    private javax.swing.JComboBox<String> fournisseur;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField produit;
    private javax.swing.JSpinner quantite;
    private javax.swing.JCheckBox today;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<String> listModel;
    private DefaultComboBoxModel<String> ComboModel;
    
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
                    if (columnIndex == 0 || columnIndex == 4) { // columnIndexOfDatePeremption représente l'indice de la colonne "date_peremption"
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
