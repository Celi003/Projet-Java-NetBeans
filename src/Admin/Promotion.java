package Admin;

import Main.Parameter;
import Main.ResultSetTableModel;
import Main.db_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

public class Promotion extends javax.swing.JPanel {

    LocalDate currentDate = LocalDate.now();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    db_connection db;
    ResultSet rs = null;
    
    public Promotion() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
        setTable();
        if(valid()!=0){
            delPromotion();
            setPromotion();
        }
        else{
            delProductPromo();
        }
        setTable();
    }

    public void setTable(){
        rs = db.querySelectAll("promotions");
        promos.setModel(new ResultSetTableModel(rs));
    }
    
    public int valid(){
        rs = db.querySelectAll("promotions");
        String fin="";
        try {
            while(rs.next()){
                fin = rs.getString("date_fin");
                try {
                    Date date_fin = dateFormat.parse(fin);
                    if(date_fin.after(new Date())){
                        return Integer.parseInt(rs.getString("id"));
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void setPromotion(){
        int id_promo = valid();
        float reduction =0,new_price=0;
        if(id_promo!=0){
            rs = db.querySelectAll("promotions", "id='"+Integer.toString(id_promo)+"'");
            try {
                while(rs.next()){
                    reduction = Float.parseFloat(rs.getString("reduction"));
                }
                rs = db.querySelectAll("produits", "en_promotion=1");
                String[] colonnes = {"prix"};
                while(rs.next()){
                    int prix = Integer.parseInt(rs.getString("prix"));
                    new_price = prix - (prix*reduction)/100;
                    String[] valeurs = {Integer.toString(Math.round(new_price))};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs, "code='"+rs.getString("code")+"'"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void delPromotion(){
        int id_promo = valid();
        float reduction =0,new_price=0;
        if(id_promo!=0){
            rs = db.querySelectAll("promotions", "id='"+Integer.toString(id_promo)+"'");
            try {
                while(rs.next()){
                    reduction = Float.parseFloat(rs.getString("reduction"));
                }
                rs = db.querySelectAll("produits", "en_promotion=1");
                String[] colonnes = {"prix"};
                while(rs.next()){
                    int prix = Integer.parseInt(rs.getString("prix"));
                    new_price = prix + (prix*reduction)/100;
                    String[] valeurs = {Integer.toString(Math.round(new_price))};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs, "code='"+rs.getString("code")+"'"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void delProductPromo(){
        float reduction =0,new_price=0;
        if(valid()==0){
            Date lastpromodate=null;
            try {
                lastpromodate = dateFormat.parse("2000-01-01");
            } catch (ParseException ex) {
                Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
            }
            rs = db.querySelectAll("promotions");
            try {
                while(rs.next()){
                    try {
                        if(dateFormat.parse(rs.getString("date_fin")).after(lastpromodate)){
                            lastpromodate = dateFormat.parse(rs.getString("date_fin"));
                            reduction = Float.parseFloat(rs.getString("id"));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                rs = db.querySelectAll("produits", "en_promotion=1");
                String[] colonnes = {"prix","en_promotion"};
                while(rs.next()){
                    int prix = Integer.parseInt(rs.getString("prix"));
                    new_price = prix + (prix*reduction)/100;
                    String[] valeurs = {Integer.toString(Math.round(new_price)),"0"};
                    System.out.println(db.queryUpdate("produits", colonnes, valeurs, "code='"+rs.getString("code")+"'"));
                }
                rs = db.querySelectAll("categories", "en_promotion=1");
                String[] cols = {"en_promotion"},vals = {"0"};
                while(rs.next()){
                    System.out.println(db.queryUpdate("categories", cols, vals, "id='"+rs.getString("id")+"'"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void CreatePromo(){
        Nom.setText("");
        Reduction.setValue(1);
        int annee = currentDate.getYear();
        Year.setSelectedItem(annee+1);
        Month.setSelectedItem(1);
        Day.setSelectedItem(1);
        date_debut.setVisible(false);
        All.setSelected(false);
        Ajouter.setVisible(true);
        Modifier.setVisible(!true);
        Supprimer.setVisible(!true);
        Create.setVisible(!true);
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
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        date_debut = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        Debut = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Reduction = new javax.swing.JSpinner();
        PanelDate = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Year = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        Month = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        Day = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Nom = new javax.swing.JTextField();
        All = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        Modifier = new javax.swing.JButton();
        Ajouter = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        promos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        Create = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 102));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Promotions.png"))); // NOI18N
        jLabel10.setText("Promotions");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        date_debut.setBackground(new java.awt.Color(255, 255, 255));
        date_debut.setLayout(new javax.swing.BoxLayout(date_debut, javax.swing.BoxLayout.LINE_AXIS));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 102));
        jLabel11.setText("Date de début                    : ");
        date_debut.add(jLabel11);

        Debut.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        Debut.setForeground(new java.awt.Color(255, 0, 102));
        Debut.setText("Date de début  ");
        date_debut.add(Debut);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 152;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(33, 6, 0, 6);
        jPanel6.add(date_debut, gridBagConstraints);
        date_debut.setVisible(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 102));
        jLabel9.setText("Pourcentage de réduction : ");
        jPanel3.add(jLabel9);
        jPanel3.add(Reduction);
        Reduction.setModel(new SpinnerNumberModel(1, 1, 95, 0.1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 189;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 6, 0, 6);
        jPanel6.add(jPanel3, gridBagConstraints);

        PanelDate.setBackground(new java.awt.Color(255, 255, 255));
        PanelDate.setLayout(new javax.swing.BoxLayout(PanelDate, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Date de fin  : ");
        PanelDate.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));
        jLabel7.setText("Y : ");
        PanelDate.add(jLabel7);

        Year.setModel(new javax.swing.DefaultComboBoxModel<>(new Integer[] {}));
        PanelDate.add(Year);
        int annee = currentDate.getYear();
        for(int i =annee ; i<annee+15 ; i++){
            Year.addItem(i);
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 102));
        jLabel12.setText("M: ");
        PanelDate.add(jLabel12);

        Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        PanelDate.add(Month);
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

        Day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        PanelDate.add(Day);
        for(int i = 1 ; i<=31 ; i++){
            if(i<10){
                Day.addItem("0"+Integer.toString(i));
            }
            else{
                Day.addItem(Integer.toString(i));
            }
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 6, 0, 6);
        jPanel6.add(PanelDate, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 102));
        jLabel5.setText("          Nom                          : ");
        jPanel2.add(jLabel5);
        jPanel2.add(Nom);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 186;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel6.add(jPanel2, gridBagConstraints);

        All.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        All.setForeground(new java.awt.Color(255, 0, 102));
        All.setText("Mettre tous les produits en promotion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 78, 0, 0);
        jPanel6.add(All, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        Modifier.setBackground(new java.awt.Color(0, 204, 204));
        Modifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });
        jPanel4.add(Modifier);
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
        jPanel4.add(Ajouter);

        Supprimer.setBackground(new java.awt.Color(255, 0, 0));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });
        jPanel4.add(Supprimer);
        Supprimer.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 91, 6, 0);
        jPanel6.add(jPanel4, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        add(jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 0, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        promos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nom", "Réduction", "Date de debut", "Date de fin", "Tous produits"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        promos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                promosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(promos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 672;
        gridBagConstraints.ipady = 350;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel5.add(jScrollPane1, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Table des  Promotions");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 166, 0, 0);
        jPanel5.add(jLabel8, gridBagConstraints);

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
        jPanel5.add(Create, gridBagConstraints);
        Create.setVisible(false);

        add(jPanel5);
    }// </editor-fold>//GEN-END:initComponents

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        if(valid()==0){
            if(Nom.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Veuillez entrer le nom de la promotion");
                return ;
            }
            currentDate = LocalDate.now();
            String date_fin = Year.getSelectedItem().toString()+"-"+Month.getSelectedItem().toString()+"-"+Day.getSelectedItem().toString();
            String[] colonnes = {"nom","reduction","date_debut","date_fin","tous_produits"};
            if(All.isSelected()){
                System.out.println(db.executionUpdate("UPDATE produits SET en_promotion=1"));
                String[] valeurs = {Nom.getText(),Reduction.getValue().toString(),currentDate.toString(),date_fin,"1"};
                System.out.println(db.queryInsert("promotions",colonnes, valeurs));
            }
            else{
                System.out.println(db.executionUpdate("UPDATE produits SET en_promotion=0"));
                String[] valeurs = {Nom.getText(),Reduction.getValue().toString(),currentDate.toString(),date_fin,"0"};
                System.out.println(db.queryInsert("promotions",colonnes, valeurs));
            }
            setTable();
            setPromotion();
            CreatePromo();
        }
        else{
            JOptionPane.showMessageDialog(this, "Une promotion est déjà en cours", "Ajout de promo impossible", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AjouterActionPerformed

    private void promosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_promosMouseClicked
        int i = promos.getSelectedRow();
        String fin = promos.getValueAt(i, 4).toString();
        String[] y_m_d = fin.split("-");
        Year.setSelectedItem(Integer.parseInt(y_m_d[0]));Month.setSelectedItem(Integer.parseInt(y_m_d[1]));Day.setSelectedItem(Integer.parseInt(y_m_d[2]));
        try {
            Date date_fin = dateFormat.parse(fin);
            if(date_fin.before(new Date())){
                Year.setEnabled(false);
                Month.setEnabled(false);
                Day.setEnabled(false);
                Ajouter.setVisible(!true);
                Modifier.setVisible(!true);
                Supprimer.setVisible(!true);
                Create.setVisible(true);
                return;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Nom.setText((String) promos.getValueAt(i, 1));
        Reduction.setValue(Float.parseFloat(promos.getValueAt(i, 2).toString()));
        date_debut.setVisible(true);
        Debut.setText(promos.getValueAt(i, 3).toString()+" ");
        if(promos.getValueAt(i, 5).equals(1)){
            All.setSelected(true);
        }
        else{
            All.setSelected(false);
        }
        Ajouter.setVisible(!true);
        Modifier.setVisible(true);
        Supprimer.setVisible(true);
        Create.setVisible(true);
    }//GEN-LAST:event_promosMouseClicked

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        CreatePromo();
    }//GEN-LAST:event_CreateActionPerformed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        String date_fin = Year.getSelectedItem().toString()+"-"+Month.getSelectedItem().toString()+"-"+Day.getSelectedItem().toString(),tous="";
        String[] colonnes = {"nom","reduction","date_fin","tous_produits"};
        if(All.isSelected()){
            tous="1";
            System.out.println(db.executionUpdate("UPDATE produits SET en_promotion=1"));
        }
        else{
            tous="0";
            System.out.println(db.executionUpdate("UPDATE produits SET en_promotion=1"));
        }
        String[] valeurs = {Nom.getText(),Reduction.getValue().toString(),date_fin,tous};
        delPromotion();
        System.out.println(db.queryUpdate("promotions", colonnes, valeurs, "id='"+promos.getValueAt(promos.getSelectedRow(), 0)+"'"));
        setPromotion();
        setTable();
        CreatePromo();
    }//GEN-LAST:event_ModifierActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer cette promotion ??",
            "Suppression de fournisseur", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            delPromotion();
            System.out.println(db.queryDelete("promotions", "id='"+promos.getValueAt(promos.getSelectedRow(), 0)+"'"));
            setTable();
            CreatePromo();
            delProductPromo();
        }
    }//GEN-LAST:event_SupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JCheckBox All;
    private javax.swing.JButton Create;
    private javax.swing.JComboBox<String> Day;
    private javax.swing.JLabel Debut;
    private javax.swing.JButton Modifier;
    private javax.swing.JComboBox<String> Month;
    private javax.swing.JTextField Nom;
    private javax.swing.JPanel PanelDate;
    private javax.swing.JSpinner Reduction;
    private javax.swing.JButton Supprimer;
    private javax.swing.JComboBox<Integer> Year;
    private javax.swing.JPanel date_debut;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable promos;
    // End of variables declaration//GEN-END:variables
}
