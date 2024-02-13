package Caissier;

import Main.Parameter;
import Main.PrintUtilities;
import Main.db_connection;
import java.sql.ResultSet;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.time.*;
import java.util.Date;

public class Ventes extends javax.swing.JPanel {

    db_connection db;
    ResultSet rs = null;
    String espace = "   ";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    
    public Ventes(int id) {
        db = new db_connection(new Parameter().db, new Parameter().nom, new Parameter().pass);
        id_caissier = id;
        initComponents();
        TableModel = (DefaultTableModel) table.getModel();
    }

    public void setList(){
        listModel = new DefaultListModel<>();
        String[] colonnes = {"code","nom"};
        rs = db.querySelect(colonnes, "produits");
        try{
            while(rs.next()){
                listModel.addElement(rs.getString("code")+espace+rs.getString("nom"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        jList1.setModel(listModel);
    }
    
    public void RechercheModifie(){
        setList();
        //if(n_p.equals("")) setList();
        DefaultListModel RechercheModel = new DefaultListModel<>();
        ListModel<String> model = jList1.getModel();
        for(int i=0 ; i<model.getSize() ; i++){
            String elm = model.getElementAt(i);
            if(elm.toLowerCase().contains(Recherche.getText().toLowerCase())){
                RechercheModel.addElement(elm);
            }
        }
        jList1.setModel(RechercheModel);
    }
    
    public void setTotal(){
        int total = 0;
        for(int i=0 ; i<table.getRowCount() ; i++){
            total += Integer.parseInt(TableModel.getValueAt(i, 3).toString());
        }
        Total.setText(Integer.toString(total));
    }
    
    public void setMonnaie(){
        int monn = 0;
        monn = Integer.parseInt(Montant.getValue().toString()) - Integer.parseInt(Total.getText());
        if(monn<0){
            Monnaie.setText("");
        }
        else{
            Monnaie.setText(Integer.toString(monn));
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Recherche = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        Prix = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Ajouter = new javax.swing.JButton();
        Modifier = new javax.swing.JButton();
        Supprimer = new javax.swing.JButton();
        Quantite = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        Montant = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        InputPaiement = new javax.swing.JComboBox<>();
        Facture = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Date = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        NFacture = new javax.swing.JLabel();
        Valider = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        Paiement = new javax.swing.JLabel();
        Recu = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        Monnaie = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        NCaissier = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 102));
        setMinimumSize(new java.awt.Dimension(1042, 576));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 0, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Produits");

        Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chercher.png"))); // NOI18N

        jScrollPane1.setViewportView(jList1);
        setList();
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jList1.isSelectionEmpty()){
                    Prix.setText("");
                    Ajouter.setVisible(true);
                    Modifier.setVisible(!true);
                    Supprimer.setVisible(!true);
                    return;
                }
                if (!e.getValueIsAdjusting() && !jList1.isSelectionEmpty()) {
                    String code_nom = jList1.getSelectedValue();
                    String[] c_n = code_nom.split(espace);
                    rs = db.querySelectAll("produits", "code='"+c_n[0]+"'");
                    Ajouter.setVisible(true);
                    Modifier.setVisible(!true);
                    Supprimer.setVisible(!true);
                    try{
                        while(rs.next()){
                            Prix.setText(rs.getString("prix"));
                            Quantite.setModel(new SpinnerNumberModel(1, 1, Integer.parseInt(rs.getString("quantite_dispo")), 1));
                        }
                    }
                    catch(SQLException ex){
                        System.out.println(ex.getMessage());
                        System.exit(0);
                    }
                    for(int i=0;i<table.getRowCount();i++){
                        if(c_n[1].equals(table.getValueAt(i, 0))){
                            Ajouter.setVisible(!true);
                            Modifier.setVisible(true);
                            Supprimer.setVisible(true);
                        }
                    }
                }
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Prix");

        Prix.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Quantité");

        Ajouter.setBackground(new java.awt.Color(0, 153, 0));
        Ajouter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Ajouter.setForeground(new java.awt.Color(255, 255, 255));
        Ajouter.setText("Ajouter");
        Ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterActionPerformed(evt);
            }
        });

        Modifier.setBackground(new java.awt.Color(0, 204, 204));
        Modifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modifier.setForeground(new java.awt.Color(255, 255, 255));
        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });

        Supprimer.setBackground(new java.awt.Color(255, 0, 0));
        Supprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Supprimer.setForeground(new java.awt.Color(255, 255, 255));
        Supprimer.setText("Supprimer");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Montant reçu");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Mode de paiement");

        InputPaiement.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"ESPECE","CARTE BANCAIRE","MOMO"}));
        InputPaiement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputPaiementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton1)
                .addGap(66, 66, 66)
                .addComponent(jLabel2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(Prix, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel3))
                    .addComponent(Quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(Ajouter))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(Modifier))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(Supprimer))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel4))
                    .addComponent(Montant, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel13))
                    .addComponent(InputPaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Prix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(Quantite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(Ajouter)
                        .addGap(18, 18, 18)
                        .addComponent(Modifier)
                        .addGap(18, 18, 18)
                        .addComponent(Supprimer)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(Montant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(InputPaiement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
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
        Ajouter.setVisible(false);
        Modifier.setVisible(false);
        Supprimer.setVisible(false);
        Quantite.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        Montant.setModel(new SpinnerNumberModel(1, 1, 999999999, 1));
        Spinnermodel = Montant.getModel();
        Spinnermodel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Recu.setText(Montant.getValue().toString()+" ");
                setMonnaie();
            }
        });

        add(jPanel1);

        Facture.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 102));
        jLabel5.setText("SuperMarché");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produits", "Quantite", "Prix Unitaire", "Montant"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        Date.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Date.setForeground(new java.awt.Color(255, 0, 102));
        Date.setText("Date");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));
        jLabel7.setText("Facture N° ");
        jPanel3.add(jLabel7);

        NFacture.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        NFacture.setForeground(new java.awt.Color(255, 0, 102));
        NFacture.setText("00000000");
        jPanel3.add(NFacture);

        Valider.setBackground(new java.awt.Color(0, 204, 0));
        Valider.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Valider.setForeground(new java.awt.Color(255, 255, 255));
        Valider.setText("Valider");
        Valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        Paiement.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Paiement.setForeground(new java.awt.Color(255, 0, 102));
        Paiement.setText("ESPECE  :  ");
        jPanel4.add(Paiement);

        Recu.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Recu.setForeground(new java.awt.Color(255, 0, 102));
        Recu.setText("1 ");
        jPanel4.add(Recu);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 102));
        jLabel8.setText("Net à payer : ");
        jPanel5.add(jLabel8);

        Total.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Total.setForeground(new java.awt.Color(255, 0, 102));
        Total.setText("0 ");
        jPanel5.add(Total);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 102));
        jLabel14.setText("Monnaie rendu : ");
        jPanel6.add(jLabel14);

        Monnaie.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Monnaie.setForeground(new java.awt.Color(255, 0, 102));
        Monnaie.setText("0");
        jPanel6.add(Monnaie);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 102));
        jLabel9.setText("Caissier : ");
        jPanel7.add(jLabel9);

        NCaissier.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        NCaissier.setForeground(new java.awt.Color(255, 0, 102));
        NCaissier.setText("azerty");
        jPanel7.add(NCaissier);
        NCaissier.setText(Integer.toString(id_caissier));

        javax.swing.GroupLayout FactureLayout = new javax.swing.GroupLayout(Facture);
        Facture.setLayout(FactureLayout);
        FactureLayout.setHorizontalGroup(
            FactureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(FactureLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(FactureLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(FactureLayout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(Valider))
            .addGroup(FactureLayout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(jLabel5))
            .addGroup(FactureLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(FactureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(FactureLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223)
                        .addComponent(Date))))
        );
        FactureLayout.setVerticalGroup(
            FactureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FactureLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(FactureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Date)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(FactureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(Valider))
        );

        Date.setText(dateFormat.format(date));
        Date.setVisible(false);
        jPanel3.setVisible(false);
        jPanel3.setVisible(false);

        add(Facture);
    }// </editor-fold>//GEN-END:initComponents

    private void RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RechercheActionPerformed

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        rs = db.querySelectAll("produits","code='"+jList1.getSelectedValue().split(espace)[0]+"'");
        try {
            while(rs.next()){
                Object[] ligne = {rs.getString("nom"), Quantite.getValue(), rs.getString("prix"), Integer.parseInt(Quantite.getValue().toString())*Integer.parseInt(rs.getString("prix"))};
                TableModel.addRow(ligne);
            }
            table.setModel(TableModel);
        } catch (SQLException ex) {
            Logger.getLogger(Ventes.class.getName()).log(Level.SEVERE, null, ex);
        }
        jList1.clearSelection();
        setTotal();
        setMonnaie();
        Ajouter.setVisible(false);
    }//GEN-LAST:event_AjouterActionPerformed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        for(int i=0 ; i<table.getRowCount() ; i++){
            if(table.getValueAt(i, 0).equals(jList1.getSelectedValue().split(espace)[1])){
                if(Quantite.getValue().equals(table.getValueAt(i, 1))){
                    JOptionPane.showMessageDialog(this, "La quantité n'a pas été modifié");
                    return;
                }
                TableModel.setValueAt(Quantite.getValue(), i, 1);
                TableModel.setValueAt(Integer.parseInt(Quantite.getValue().toString())*Integer.parseInt(table.getValueAt(i, 2).toString()), i, 3);
            }
        }
        table.setModel(TableModel);
        setTotal();
        setMonnaie();
    }//GEN-LAST:event_ModifierActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        for(int i=0 ; i<table.getRowCount() ; i++){
            if(table.getValueAt(i, 0).equals(jList1.getSelectedValue().split(espace)[1])){
                TableModel.removeRow(i);
            }
        }
        table.setModel(TableModel);
        setTotal();
        setMonnaie();
    }//GEN-LAST:event_SupprimerActionPerformed

    private void InputPaiementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputPaiementActionPerformed
        Paiement.setText(InputPaiement.getSelectedItem().toString()+"  :  ");
    }//GEN-LAST:event_InputPaiementActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        rs = db.querySelectAll("produits", "nom='"+table.getValueAt(table.getSelectedRow(), 0).toString()+"'");
        Quantite.setValue(table.getValueAt(table.getSelectedRow(), 1));
        try {
            while(rs.next()){
                jList1.setSelectedValue(rs.getString("code")+espace+rs.getString("nom"), true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ventes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void ValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderActionPerformed
        int total = Integer.parseInt(Total.getText()) , montant = Integer.parseInt(Montant.getValue().toString());
        date = new Date();
        if(montant < total){
            JOptionPane.showMessageDialog(this, "Le montant est inférieur au coût total", "Vente impossible", JOptionPane.ERROR_MESSAGE);
        }
        else{
            String[] colonnes = {"produits","cout","montant_recu","moyen_paiement","date","id_caissier"};
            String[] col = {"quantite_dispo"};
            int new_qte = 0;
            String produits ="",etat="id_caissier='"+Integer.toString(id_caissier)+"' and date='"+dateFormat.format(date)+"'";
            for(int i=0 ; i<table.getRowCount();i++){
                rs = db.querySelectAll("produits", "nom='"+table.getValueAt(i, 0).toString()+"'");
                try {
                    while(rs.next()){
                        new_qte = Integer.parseInt(rs.getString("quantite_dispo")) - Integer.parseInt(table.getValueAt(i, 1).toString());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Ventes.class.getName()).log(Level.SEVERE, null, ex);
                }
                String[] val = {Integer.toString(new_qte)};
                System.out.println(db.queryUpdate("produits", col, val, "nom='"+table.getValueAt(i, 0).toString()+"'"));
                produits += "{";
                for(int j=0 ; j<table.getColumnCount() ; j++){
                    produits += table.getValueAt(i, j).toString();
                    if(j!=table.getColumnCount()-1){
                        produits += ",";
                    }
                }
                produits += "}";
                if(i!=table.getRowCount()-1){
                    produits += ";";
                }
            }
            String[] valeurs = {produits,Total.getText(),Montant.getValue().toString(),InputPaiement.getSelectedItem().toString(),dateFormat.format(date),Integer.toString(id_caissier)};
            System.out.println(db.queryInsert("ventes", colonnes, valeurs));
            TableModel.setRowCount(0);
            table.setModel(TableModel);
            setTotal();
            setMonnaie();
            Prix.setText("");
            Quantite.setValue(1);
            Montant.setValue(1);
            rs = db.querySelectAll("ventes", etat);
            Date.setVisible(true);
            NFacture.setVisible(true);
            try {
                while(rs.next()){
                    NFacture.setText(rs.getString("id"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Ventes.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintUtilities.printComponent(Facture);
            Date.setVisible(!true);
            NFacture.setVisible(!true);
        }
    }//GEN-LAST:event_ValiderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JLabel Date;
    private javax.swing.JPanel Facture;
    private javax.swing.JComboBox<String> InputPaiement;
    private javax.swing.JButton Modifier;
    private javax.swing.JLabel Monnaie;
    private javax.swing.JSpinner Montant;
    private javax.swing.JLabel NCaissier;
    private javax.swing.JLabel NFacture;
    private javax.swing.JLabel Paiement;
    private javax.swing.JTextField Prix;
    private javax.swing.JSpinner Quantite;
    private javax.swing.JTextField Recherche;
    private javax.swing.JLabel Recu;
    private javax.swing.JButton Supprimer;
    private javax.swing.JLabel Total;
    private javax.swing.JButton Valider;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<String> listModel;
    private DefaultTableModel TableModel;
    private SpinnerModel Spinnermodel;
    public int id_caissier;
}