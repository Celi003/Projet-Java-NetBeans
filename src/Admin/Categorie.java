package Admin;

import Main.Parameter;
import Main.ResultSetTableModel;
import Main.db_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Categorie extends javax.swing.JPanel {

    Promotion promo = new Promotion();
    db_connection db;
    ResultSet rs = null,rst = null;
    
    public Categorie() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
        setTable();
        setPromotion();
    }

    public void setInputPromo(){
        if(promo.valid()!=0){
            en_promo.setVisible(true);
        }
        else{
            en_promo.setVisible(false);
        }
    }
    
    public boolean valid(){
        if(Nom.getText().length()<2 ){
            JOptionPane.showMessageDialog(this, "Le nom de la categorie doit contenir au minimum 2 caractères");
            return false;
        }
        if(Nom.getText().length()>=2 ){
            rs = db.querySelectAll("categories", "nom = '"+Nom.getText()+"'");
            try {
                while(rs.next()){
                    JOptionPane.showMessageDialog(this, "Une catégorie possède déjà ce nom, veuillez modifier le nom de la catégorie que vous essayer d'ajouter");
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Produits.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    public void RechercheModifie(){
        if(Recherche.getText().equals("")){
            setTable();
        }
        else{
            String etat = "nom LIKE '%" + Recherche.getText() +"%' OR description LIKE '%" + Recherche.getText() +"%'";
            if(promo.valid()!=0){
                String[] cols = {"categories.id","categories.nom","description","promotions.nom AS Promotion"};
                rs = db.fcSelectCommand(cols, "categories LEFT JOIN promotions ON id_promotion = promotions.id",etat);
                categories.setModel(new ResultSetTableModel(rs));
            }
            else{
                String[] cols = {"id","nom","description"};
                rs = db.fcSelectCommand(cols, "categories",etat);
                categories.setModel(new ResultSetTableModel(rs));
            }
        }
    }
    
    public void CreateCategorie(){
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Ajouter.setVisible(!false);
        Create.setVisible(false);
        Nom.setText("");
        Description.setText("");
        en_promo.setVisible(false);
    }
    
    public void ShowCategorie(){
        Modifier.setVisible(!false);
        Supprimer.setVisible(!false);
        Ajouter.setVisible(false);
        Create.setVisible(!false);
    }
    
    public void setTable(){
        if(promo.valid()!=0){
            String[] cols = {"categories.id","categories.nom","description","promotions.nom AS Promotion"};
            rs = db.querySelect(cols, "categories LEFT JOIN promotions ON id_promotion = promotions.id");
            categories.setModel(new ResultSetTableModel(rs));
        }
        else{
            String[] cols = {"id","nom","description"};
            rs = db.querySelect(cols, "categories");
            categories.setModel(new ResultSetTableModel(rs));
        }
    }
    
    public void setPromotion(){
        rs = db.executionQuery("SELECT id FROM categories");
        try {
            while(rs.next()){
                boolean en_promo = false;
                int id = rs.getInt("id"),id_promo=0;
                rst = db.querySelectAll("produits JOIN categories ON id_categorie = categories.id");
                while(rst.next()){
                    if(rst.getInt("id_categorie") == id){
                        if(rst.getString("id_promotion")!= null){
                            en_promo = true;
                            id_promo = rst.getInt("id_promotion");
                        }
                        else{
                            en_promo = false;
                            break ;
                        }
                    }
                }
                if(en_promo){
                    System.out.println(db.executionUpdate("UPDATE categories SET id_promotion = "+Integer.toString(id_promo)+" WHERE id = "+Integer.toString(id)));
                }
                else{
                    System.out.println(db.executionUpdate("UPDATE categories SET id_promotion = NULL WHERE id = "+Integer.toString(id)));
                }
            }
            setTable();
        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Nom = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Description = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        Modifier = new javax.swing.JButton();
        Ajouter = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();
        en_promo = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        categories = new javax.swing.JTable();
        Create = new javax.swing.JButton();
        Recherche = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(341, 381));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 102));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Catégories.png"))); // NOI18N
        jLabel9.setText("Catégories");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Nom                : ");
        jPanel2.add(jLabel6);

        Nom.setForeground(new java.awt.Color(0, 102, 102));
        jPanel2.add(Nom);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));
        jLabel7.setText("Description     : ");
        jPanel3.add(jLabel7);

        Description.setColumns(20);
        Description.setForeground(new java.awt.Color(0, 102, 102));
        Description.setRows(5);
        jScrollPane2.setViewportView(Description);

        jPanel3.add(jScrollPane2);

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

        Ajouter.setBackground(new java.awt.Color(0, 204, 51));
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

        en_promo.setBackground(new java.awt.Color(255, 255, 255));
        en_promo.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        en_promo.setForeground(new java.awt.Color(255, 0, 102));
        en_promo.setText("Mettre les produits de la catégorie en promotion active");
        en_promo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                en_promoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(en_promo)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(en_promo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        en_promo.setVisible(false);

        add(jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 0, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Table des  Catégories");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 52, 0, 0);
        jPanel5.add(jLabel8, gridBagConstraints);

        categories.setForeground(new java.awt.Color(0, 102, 102));
        categories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nom", "Description", "Promotion"
            }
        ));
        categories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoriesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(categories);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 568;
        gridBagConstraints.ipady = 296;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel5.add(jScrollPane1, gridBagConstraints);

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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        jPanel5.add(Create, gridBagConstraints);
        Create.setVisible(false);

        Recherche.setForeground(new java.awt.Color(0, 102, 102));
        Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 170;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        jPanel5.add(Recherche, gridBagConstraints);
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

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chercher.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel5.add(jButton4, gridBagConstraints);

        add(jPanel5);
    }// </editor-fold>//GEN-END:initComponents

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer cette catégorie ??",
            "Suppression de catégorie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            rs = db.querySelectAll("produits", "id_categorie = '"+categories.getValueAt(categories.getSelectedRow(), 0).toString()+"'");
            try {
                while(rs.next()){
                    JOptionPane.showMessageDialog(this, "Avant de pouvoir supprimer cette catégorie , vous devez modifier la catégorie des produits qui possède déjà cette catégorie car chaque produits doit obligatoirement avoir une carégorie", "Suppression impossible", JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(db.queryDelete("categories", "id='"+categories.getValueAt(categories.getSelectedRow(), 0)+"'"));
            setTable();
            CreateCategorie();
        }
    }//GEN-LAST:event_SupprimerActionPerformed

    private void en_promoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_en_promoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_en_promoActionPerformed

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        if(valid()){
            String[] cols = {"nom","description","id_promotion"};
            if(en_promo.isSelected()){
                String[] vals = {Nom.getText(),Description.getText(),Integer.toString(promo.valid())};
                System.out.println(db.queryInsert("categories", cols, vals));
            }
            else{
                String[] vals = {Nom.getText(),Description.getText(),"NULL"};
                System.out.println(db.queryInsert("categories", cols, vals));
            }
            CreateCategorie();
            setTable();
        }
    }//GEN-LAST:event_AjouterActionPerformed

    private void categoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoriesMouseClicked
        int i = categories.getSelectedRow();
        ShowCategorie();
        setInputPromo();
        Nom.setText(categories.getValueAt(i, 1).toString());
        Description.setText(categories.getValueAt(i, 2).toString());
        if(promo.valid()!=0){
            if(!(categories.getValueAt(i, 3)==null)){
                en_promo.setSelected(true);
            }
            else{
                en_promo.setSelected(false);
            }
        }
    }//GEN-LAST:event_categoriesMouseClicked

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        if(Nom.getText().length()<2 ){
            JOptionPane.showMessageDialog(this, "Le nom de la categorie doit contenir au minimum 2 caractères");
            return ;
        }
        String[] cols = {"nom","description","id_promotion"};
        int id = categories.getSelectedRow(),id_promo = promo.valid();
        if(en_promo.isSelected()){
            promo.delPromotion();
            String[] vals = {Nom.getText(),Description.getText(),Integer.toString(id_promo)};
            System.out.println(db.queryUpdate("categories", cols, vals,"id="+ categories.getValueAt(id, 0).toString()));
            System.out.println(db.executionUpdate("UPDATE produits JOIN categories ON id_categorie = categories.id SET produits.id_promotion = "+Integer.toString(id_promo)+" WHERE id_categorie="+ categories.getValueAt(id, 0).toString()));
            promo.setPromotion();
            setTable();
        }
        else{
            promo.delPromotion();
            String[] vals = {Nom.getText(),Description.getText(),"NULL"};
            System.out.println(db.queryUpdate("categories", cols, vals,"id = '"+ categories.getValueAt(id, 0).toString()+"'"));
            System.out.println(db.executionUpdate("UPDATE produits JOIN categories ON id_categorie = categories.id SET produits.id_promotion = NULL WHERE id_categorie="+ categories.getValueAt(id, 0).toString()));
            promo.setPromotion();
            setTable();
        }
        CreateCategorie();
        setTable();
    }//GEN-LAST:event_ModifierActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        CreateCategorie();
    }//GEN-LAST:event_CreateActionPerformed

    private void RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RechercheActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JButton Create;
    private javax.swing.JTextArea Description;
    private javax.swing.JButton Modifier;
    private javax.swing.JTextField Nom;
    private javax.swing.JTextField Recherche;
    private javax.swing.JButton Supprimer;
    public javax.swing.JTable categories;
    private javax.swing.JCheckBox en_promo;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
