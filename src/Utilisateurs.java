import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class Utilisateurs extends javax.swing.JFrame {

    db_connection db;
    ResultSet rs = null;
    public Utilisateurs() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
    }

    public void RechercheModifie(){
        String n_p = Recherche.getText();
        if(n_p.equals("")) setList();
        DefaultListModel RechercheModel = new DefaultListModel<>();
        ListModel<String> model = jList1.getModel();
        for(int i=0 ; i<model.getSize() ; i++){
            String elm = model.getElementAt(i);
            if(elm.contains(n_p.toLowerCase())||elm.contains(n_p.toUpperCase())){
                RechercheModel.addElement(elm);
            }
        }
        jList1.setModel(RechercheModel);
    }
    
    public void setList(){
        listModel = new DefaultListModel<>();
        String[] colonnes = {"nom","prenoms"};
        rs = db.querySelect(colonnes, "utilisateurs");
        try{
            while(rs.next()){
                listModel.addElement(rs.getString("nom")+"   "+rs.getString("prenoms"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        jList1.setModel(listModel);
    }
    
    public boolean valide(){
        if(InputNom.getText().length()<2 ){
            JOptionPane.showMessageDialog(this, "Le nom de l'utilisateur doit contenir au minimum 2 caractères");
            return false;
        }
        else if(InputPrenoms.getText().length()<2 ) {
            JOptionPane.showMessageDialog(this, "Le(s) prénom(s) de l'utilisateur doit contenir au minimum 2 caractères");
            return false;
        }
        else if(new String(Password.getPassword()).length()<8 ) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au minimum 8 caractères");
            return false;
        }
        else if(!new String(Password.getPassword()).equals(new String(Confirmation.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Les deux mots de passe ne correspondent pas .");
            return false;
        }
        else if(Identifiant.getText().length()<2 ) {
            JOptionPane.showMessageDialog(this, "L'identifiant de l'utilisateur doit contenir au minimum 2 caractères");
            return false;
        }
        else{
            return true;
        }
    }
    
    public void showUser(){
        Creer.setVisible(false);
            Modifier.setVisible(true);
            Supprimer.setVisible(true);
            Create.setVisible(true);
            jPanel4.setVisible(false);
            jPanel5.setVisible(false);
            jPanel8.setVisible(false);
            InputNom.setVisible(false);
            InputPrenoms.setVisible(false);
            InputRole.setVisible(false);
            Nom.setVisible(true);
            Prenoms.setVisible(true);
            Role.setVisible(true);
            Modifier.setText("Modifier");
    }
    
    public void showCreateUser(){
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Creer.setVisible(true);
        Create.setVisible(false);
        jPanel4.setVisible(!false);
        jPanel5.setVisible(!false);
        jPanel8.setVisible(!false);
        InputNom.setVisible(!false);
        InputPrenoms.setVisible(!false);
        InputRole.setVisible(!false);
        Nom.setVisible(!true);
        Prenoms.setVisible(!true);
        Role.setVisible(!true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Confirmation = new javax.swing.JPasswordField();
        Recherche = new javax.swing.JTextField();
        Supprimer = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Password = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        InputRole = new javax.swing.JComboBox<>();
        Role = new javax.swing.JLabel();
        Modifier = new javax.swing.JButton();
        Creer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        InputPrenoms = new javax.swing.JTextField();
        Prenoms = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InputNom = new javax.swing.JTextField();
        Nom = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        Create = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Identifiant = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setMinimumSize(new java.awt.Dimension(679, 283));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Confirmation :");
        jPanel5.add(jLabel5);
        jPanel5.add(Confirmation);

        Recherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RechercheKeyPressed(evt);
            }
        });

        Supprimer.setBackground(new java.awt.Color(255, 51, 51));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Mot de passe :");
        jPanel4.add(jLabel4);
        jPanel4.add(Password);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Liste des employés");

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Rôle              :");
        jPanel3.add(jLabel3);

        InputRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin" , "employé" }));
        jPanel3.add(InputRole);

        Role.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        Role.setForeground(new java.awt.Color(0, 102, 102));
        Role.setText("Rôle");
        jPanel3.add(Role);
        Role.setVisible(false);

        Modifier.setBackground(new java.awt.Color(0, 204, 204));
        Modifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });

        Creer.setBackground(new java.awt.Color(0, 204, 0));
        Creer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Creer.setForeground(new java.awt.Color(255, 255, 255));
        Creer.setText("Créer");
        Creer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Prénoms       :");
        jPanel2.add(jLabel2);
        jPanel2.add(InputPrenoms);

        Prenoms.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        Prenoms.setForeground(new java.awt.Color(0, 102, 102));
        Prenoms.setText("Prénoms");
        jPanel2.add(Prenoms);
        Prenoms.setVisible(false);

        jButton1.setText("Rechercher");

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Nom             :");
        jPanel1.add(jLabel1);
        jPanel1.add(InputNom);

        Nom.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        Nom.setForeground(new java.awt.Color(0, 102, 102));
        Nom.setText("Nom");
        jPanel1.add(Nom);
        Nom.setVisible(false);

        jList1.setModel(new DefaultListModel<>());
        jScrollPane1.setViewportView(jList1);
        setList();
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !jList1.isSelectionEmpty()) {
                    showUser();
                    String nom_prenom = jList1.getSelectedValue();
                    String[] n_p = nom_prenom.split("   ");
                    String[] colonnes={"nom","prenoms","role"};
                    rs = db.fcSelectCommand(colonnes, "utilisateurs", "nom='"+n_p[0]+"' and prenoms='"+n_p[1]+"'");
                    try{
                        while(rs.next()){
                            Nom.setText(rs.getString("nom"));
                            Prenoms.setText(rs.getString("prenoms"));
                            Role.setText(rs.getString("role"));
                        }
                    }
                    catch(SQLException ex){
                        System.out.println(ex.getMessage());
                        System.exit(0);
                    }
                }
            }
        });

        Create.setBackground(new java.awt.Color(0, 204, 0));
        Create.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Create.setForeground(new java.awt.Color(255, 255, 255));
        Create.setText("Créer");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(Create)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(2, 2, 2)
                .addComponent(Create)
                .addContainerGap())
        );

        Create.setVisible(false);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Identifiant     :");
        jPanel8.add(jLabel6);
        jPanel8.add(Identifiant);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(Modifier)
                                .addGap(59, 59, 59)
                                .addComponent(Creer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                                .addComponent(Supprimer)
                                .addGap(15, 15, 15))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(43, 43, 43))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Modifier)
                    .addComponent(Creer)
                    .addComponent(Supprimer))
                .addGap(31, 31, 31))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        Supprimer.setVisible(false);
        Modifier.setVisible(false);

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Produits");

        jMenuItem1.setText("Table des produits");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Gestion des produits");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Catégories");

        jMenuItem3.setText("Table des catégories");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Ajouter une catégorie");
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Ventes");
        jMenuBar1.add(jMenu7);

        jMenu3.setText("Commandes");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Utilisateurs");

        jMenuItem9.setText("Table des utilisateurs");
        jMenu4.add(jMenuItem9);
        jMenuItem9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TableUtilisateur().setVisible(true);
            }
        });

        jMenuItem8.setText("Gestion des utilisateurs");
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu8.setText("Fournisseurs");
        jMenuBar1.add(jMenu8);

        jMenu6.setText("Promotions");
        jMenuBar1.add(jMenu6);

        jMenu5.setText("Aide");
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CreerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerActionPerformed
        if(valide()){
            String[] colonnes={"nom","prenoms","role","identifiant","mot_de_passe"};
            String[] valeurs={InputNom.getText(),InputPrenoms.getText(),InputRole.getSelectedItem().toString(),Identifiant.getText(),Password.getText()};
            System.out.println(db.queryInsert("utilisateurs", colonnes, valeurs));
            setList();
        }
    }//GEN-LAST:event_CreerActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        showCreateUser();
    }//GEN-LAST:event_CreateActionPerformed

    private void RechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RechercheKeyPressed
        
    }//GEN-LAST:event_RechercheKeyPressed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        if(Modifier.getText().equals("Modifier")){
            InputNom.setVisible(true);
            InputPrenoms.setVisible(true);
            InputRole.setVisible(true);
            jPanel4.setVisible(true);
            jPanel5.setVisible(true);
            jPanel8.setVisible(true);
            InputNom.setText(Nom.getText());
            InputPrenoms.setText(Prenoms.getText());
            InputRole.setSelectedItem(Role.getText());
            Nom.setVisible(false);
            Prenoms.setVisible(false);
            Role.setVisible(false);
            Modifier.setText("Enregistrer");
        }
        else{
            if(valide()){
                String[] colonnes={"nom","prenoms","role","identifiant","mot_de_passe"};
                String[] valeurs={InputNom.getText(),InputPrenoms.getText(),InputRole.getSelectedItem().toString(),Identifiant.getText(),Confirmation.getText()};
                String etat = "nom='"+Nom.getText()+"' and prenoms='"+Prenoms.getText()+"'";
                System.out.println(db.queryUpdate("utilisateurs", colonnes, valeurs, etat));
                showUser();
            }
        }
    }//GEN-LAST:event_ModifierActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        String etat = "nom='" + Nom.getText()+"' and prenoms='" + Prenoms.getText() +"'";
        if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer cet utilisateur ??", 
        "Suppression d'utilisateur", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            System.out.println(db.queryDelete("utilisateurs", etat));
            setList();
            showCreateUser();
        }
    }//GEN-LAST:event_SupprimerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Utilisateurs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Utilisateurs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Utilisateurs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Utilisateurs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Utilisateurs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Confirmation;
    private javax.swing.JButton Create;
    private javax.swing.JButton Creer;
    private javax.swing.JTextField Identifiant;
    private javax.swing.JTextField InputNom;
    private javax.swing.JTextField InputPrenoms;
    private javax.swing.JComboBox<String> InputRole;
    private javax.swing.JButton Modifier;
    private javax.swing.JLabel Nom;
    private javax.swing.JPasswordField Password;
    private javax.swing.JLabel Prenoms;
    private javax.swing.JTextField Recherche;
    private javax.swing.JLabel Role;
    private javax.swing.JButton Supprimer;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<String> listModel;
}
