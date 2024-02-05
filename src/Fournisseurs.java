import java.sql.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Thierry APLOGAN
 */
public class Fournisseurs extends javax.swing.JPanel {
    
    db_connection db;
    ResultSet rs = null;
    
    public Fournisseurs() {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        initComponents();
    }

    public void RechercheModifie(){
        setList();
        String n_p = Recherche.getText();
        //if(n_p.equals("")) setList();
        DefaultListModel RechercheModel = new DefaultListModel<>();
        ListModel<String> model = jList1.getModel();
        for(int i=0 ; i<model.getSize() ; i++){
            String elm = model.getElementAt(i);
            if(elm.toLowerCase().contains(n_p.toLowerCase())){
                RechercheModel.addElement(elm);
            }
        }
        jList1.setModel(RechercheModel);
    }
    
    public void setList(){
        listModel = new DefaultListModel<>();
        rs = db.querySelectAll("fournisseurs");
        try{
            while(rs.next()){
                listModel.addElement(rs.getString("nom_ou_entreprise"));
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
            JOptionPane.showMessageDialog(this, "Le nom du fournisseur ou de l'entreprise doit contenir au minimum 2 caractères");
            return false;
        }
        else if(InputContact.getText().length()>8 ) {
            JOptionPane.showMessageDialog(this, "Le contact est censé s'écrire sans espace et doit contenir au maximum 8 caractères");
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
            InputNom.setVisible(false);
            InputContact.setVisible(false);
            Nom.setVisible(true);
            Contact.setVisible(true);
            Modifier.setText("Modifier");
    }
    
    public void showCreateUser(){
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Creer.setVisible(true);
        Create.setVisible(false);
        InputNom.setVisible(!false);
        InputContact.setVisible(true);
        InputNom.setText("");
        InputContact.setText("");
        Nom.setVisible(!true);
        Contact.setVisible(false);
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

        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        Recherche = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InputNom = new javax.swing.JTextField();
        Nom = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        InputContact = new javax.swing.JTextField();
        Contact = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Create = new javax.swing.JButton();
        Modifier = new javax.swing.JButton();
        Creer = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Nom | Entreprise");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 97, 0, 0);
        add(jLabel9, gridBagConstraints);

        jList1.setModel(new DefaultListModel<>());
        jScrollPane1.setViewportView(jList1);
        setList();
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !jList1.isSelectionEmpty()) {
                    showUser();
                    String nom = jList1.getSelectedValue();
                    String[] colonnes={"nom_ou_entreprise","contact"};
                    rs = db.fcSelectCommand(colonnes, "fournisseurs", "nom_ou_entreprise='"+nom+"'");
                    try{
                        while(rs.next()){
                            Nom.setText(rs.getString("nom"));
                            Contact.setText(rs.getString("contact"));
                        }
                    }
                    catch(SQLException ex){
                        System.out.println(ex.getMessage());
                        System.exit(0);
                    }
                }
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 290;
        gridBagConstraints.ipady = 326;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        add(jScrollPane1, gridBagConstraints);

        Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheActionPerformed(evt);
            }
        });
        Recherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RechercheKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 136;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 54, 0, 0);
        add(Recherche, gridBagConstraints);
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

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Nom | Entreprise:");
        jPanel1.add(jLabel1);
        jPanel1.add(InputNom);

        Nom.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        Nom.setForeground(new java.awt.Color(0, 102, 102));
        Nom.setText("Nom");
        jPanel1.add(Nom);
        Nom.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 148;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(102, 18, 0, 68);
        add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Contact              :");
        jPanel6.add(jLabel7);

        InputContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputContactActionPerformed(evt);
            }
        });
        jPanel6.add(InputContact);

        Contact.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        Contact.setForeground(new java.awt.Color(0, 102, 102));
        Contact.setText("Contact");
        jPanel6.add(Contact);
        Contact.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 128;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(57, 18, 0, 68);
        add(jPanel6, gridBagConstraints);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chercher.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jButton1, gridBagConstraints);

        Create.setBackground(new java.awt.Color(0, 204, 0));
        Create.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Create.setForeground(new java.awt.Color(255, 255, 255));
        Create.setText("Créer");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 95, 6, 0);
        add(Create, gridBagConstraints);
        Create.setVisible(false);

        Modifier.setBackground(new java.awt.Color(0, 204, 204));
        Modifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(97, 18, 0, 0);
        add(Modifier, gridBagConstraints);
        Modifier.setVisible(false);

        Creer.setBackground(new java.awt.Color(0, 204, 0));
        Creer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Creer.setForeground(new java.awt.Color(255, 255, 255));
        Creer.setText("Créer");
        Creer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(97, 66, 0, 0);
        add(Creer, gridBagConstraints);

        Supprimer.setBackground(new java.awt.Color(255, 51, 51));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(97, 1, 0, 0);
        add(Supprimer, gridBagConstraints);
        Supprimer.setVisible(false);
    }// </editor-fold>//GEN-END:initComponents

    private void RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheActionPerformed
        RechercheModifie();
    }//GEN-LAST:event_RechercheActionPerformed

    private void RechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RechercheKeyPressed

    }//GEN-LAST:event_RechercheKeyPressed

    private void InputContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputContactActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        showCreateUser();
        InputNom.setText("");
        InputContact.setText("");
    }//GEN-LAST:event_CreateActionPerformed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        if(Modifier.getText().equals("Modifier")){
            InputNom.setVisible(true);
            InputContact.setVisible(true);
            InputNom.setText(Nom.getText());
            InputContact.setText(Contact.getText());
            Nom.setVisible(false);
            Contact.setVisible(false);
            Modifier.setText("Enregistrer");
        }
        else{
            if(valide()){
                String[] colonnes={"nom|entreprise","contact"};
                String[] valeurs={InputNom.getText(),InputContact.getText()};
                String etat = "nom_ou_entreprise='"+Nom.getText()+"'";
                System.out.println(db.queryUpdate("fournisseurs", colonnes, valeurs, etat));
                setList();
                showUser();
                Nom.setText(InputNom.getText());
                Contact.setText(InputContact.getText());
            }
        }
    }//GEN-LAST:event_ModifierActionPerformed

    private void CreerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerActionPerformed
        if(valide()){
            String[] colonnes={"nom_ou_entreprise","contact"};
            String[] valeurs={InputNom.getText(),InputContact.getText()};
            ListModel<String> model = jList1.getModel();
            for(int i=0 ; i<model.getSize() ; i++){
                String elm = model.getElementAt(i);
                if(elm.equals(InputNom.getText())){
                    JOptionPane.showMessageDialog(this, "Un fournisseur possède déja ce nom , veuillez ajouter une petite modification");
                    return;
                }
            }
            System.out.println(db.queryInsert("fournisseurs", colonnes, valeurs));
            setList();
        }
    }//GEN-LAST:event_CreerActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        String etat = "nom='" + Nom.getText()+"'";
        if(JOptionPane.showConfirmDialog(this, "Etes vous sûr de vouloir supprimer cet utilisateur ??",
            "Suppression de fournisseur", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            System.out.println(db.queryDelete("fournisseurs", etat));
            setList();
            showCreateUser();
        }
    }//GEN-LAST:event_SupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Contact;
    private javax.swing.JButton Create;
    private javax.swing.JButton Creer;
    private javax.swing.JTextField InputContact;
    private javax.swing.JTextField InputNom;
    private javax.swing.JButton Modifier;
    private javax.swing.JLabel Nom;
    private javax.swing.JTextField Recherche;
    private javax.swing.JButton Supprimer;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<String> listModel;
}
