/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Pegawai;
import Admin.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DOSTAKIJO
 */
public class Data_Menu_Makanan_Minuman extends javax.swing.JFrame {

    /**
     * Creates new form Data_Menu_Makanan
     */
    public Data_Menu_Makanan_Minuman() {
        initComponents();
        jTable1.setEnabled(false);


        tampilkanSemuaDataGabungan();
    }
    
    private boolean sudahAdaTampilkanSemua() {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i).equals("Tampilkan Semua Data Menu")) {
                return true;
            }
        }
        return false;
    }

    

    private void tampilkanData() {
    Object selectedItem = jComboBox1.getSelectedItem();
        if (selectedItem == null) {
            return; // ⛔ Jangan lanjut kalau combo belum dipilih
        }

        String selected = selectedItem.toString();
        DefaultTableModel model = new DefaultTableModel();

        try (Connection conn = DatabaseConnection.connect()) {
            if (selected.equalsIgnoreCase("Makanan")) {
                model.setColumnIdentifiers(
                    new Object[]{"ID", "Kode Makanan", "Nama Makanan", "Kategori", "Harga"});
                String sql = "SELECT * FROM makanan ORDER BY ID";
                ResultSet rs = conn.prepareStatement(sql).executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Kode_Makanan"),
                        rs.getString("Nama_Makanan"),
                        rs.getString("Kategori"),
                        rs.getInt("Harga")
                    });
                }
            } else {
                model.setColumnIdentifiers(
                    new Object[]{"ID", "Kode Minuman", "Nama Minuman", "Harga"});
                String sql = "SELECT * FROM minuman ORDER BY ID";
                ResultSet rs = conn.prepareStatement(sql).executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Kode_Minuman"),
                        rs.getString("Nama_Minuman"),
                        rs.getInt("Harga")
                    });
                }
            }

            jTable1.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Gagal menampilkan data: " + e.getMessage());
        }
    }
    
    private void tampilkanSemuaDataGabungan() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Kode", "Nama", "Kategori", "Harga", "Jenis"});

        try (Connection conn = DatabaseConnection.connect()) {

            // Tambah baris header pembatas makanan
            model.addRow(new Object[]{"", "", "--- Daftar Makanan ---", "", "", ""});

            // Tampilkan data makanan
            String sqlMakanan = "SELECT * FROM makanan";
            ResultSet rs1 = conn.prepareStatement(sqlMakanan).executeQuery();
            while (rs1.next()) {
                model.addRow(new Object[]{
                    rs1.getInt("ID"),
                    rs1.getString("Kode_Makanan"),
                    rs1.getString("Nama_Makanan"),
                    rs1.getString("Kategori"),
                    rs1.getInt("Harga"),
                    "Makanan"
                });
            }

            // Tambah baris header pembatas minuman
            model.addRow(new Object[]{"", "", "--- Daftar Minuman ---", "", "", ""});

            // Tampilkan data minuman
            String sqlMinuman = "SELECT * FROM minuman";
            ResultSet rs2 = conn.prepareStatement(sqlMinuman).executeQuery();
            while (rs2.next()) {
                model.addRow(new Object[]{
                    rs2.getInt("ID"),
                    rs2.getString("Kode_Minuman"),
                    rs2.getString("Nama_Minuman"),
                    "-", // Tidak ada kategori untuk minuman
                    rs2.getInt("Harga"),
                    "Minuman"
                });
            }

            jTable1.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal menampilkan data awal: " + e.getMessage());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Menu  :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Makanan", "Minuman" }));
        jComboBox1.setSelectedIndex(-1);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton5.setText("⇦  BACK");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Search :");

        jButton1.setText("GO ⇨");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tampil Data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Data Menu");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2))
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(247, 247, 247))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        Object selectedItem = jComboBox1.getSelectedItem();
        if (selectedItem == null) return;

        String pilihan = selectedItem.toString();

        if (pilihan.equals("Makanan") || pilihan.equals("Minuman")) {
            tampilkanData();

            // Tambahkan "Tampilkan Semua Data Menu" jika belum ada
            if (!sudahAdaTampilkanSemua()) {
                jComboBox1.addItem("Tampilkan Semua Data Menu");
            }

        } else if (pilihan.equals("Tampilkan Semua Data Menu")) {
            tampilkanSemuaDataGabungan();

            // Hapus item setelah dipilih
            jComboBox1.removeItem("Tampilkan Semua Data Menu");
            jComboBox1.setSelectedIndex(-1); // reset pilihan
        }


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new Pegawai.Dashboard_Pegawai().setVisible(true);
        dispose();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String keyword = jTextField1.getText().trim();
        Object selectedItem = jComboBox1.getSelectedItem();

        if (keyword.isEmpty() || selectedItem == null) {
            JOptionPane.showMessageDialog(this, "❗ Masukkan jenis menu dan kata kunci pencarian terlebih dahulu.");
            return;
        }

        String pilihan = selectedItem.toString();
        DefaultTableModel model = new DefaultTableModel();

        try (Connection conn = DatabaseConnection.connect()) {
            if (pilihan.equals("Makanan")) {
                model.setColumnIdentifiers(new Object[]{"ID", "Kode Makanan", "Nama Makanan", "Kategori", "Harga"});
                String sql = "SELECT * FROM makanan WHERE Nama_Makanan LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Kode_Makanan"),
                        rs.getString("Nama_Makanan"),
                        rs.getString("Kategori"),
                        rs.getInt("Harga")
                    });
                }

            } else if (pilihan.equals("Minuman")) {
                model.setColumnIdentifiers(new Object[]{"ID", "Kode Minuman", "Nama Minuman", "Harga"});
                String sql = "SELECT * FROM minuman WHERE Nama_Minuman LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Kode_Minuman"),
                        rs.getString("Nama_Minuman"),
                        rs.getInt("Harga")
                    });
                }

            } else if (pilihan.equals("Tampilkan Semua Data Menu")) {
                model.setColumnIdentifiers(new Object[]{"ID", "Kode", "Nama", "Kategori", "Harga", "Jenis"});

                // Cari makanan
                String sqlM = "SELECT * FROM makanan WHERE Nama_Makanan LIKE ?";
                PreparedStatement psM = conn.prepareStatement(sqlM);
                psM.setString(1, "%" + keyword + "%");
                ResultSet rsM = psM.executeQuery();

                while (rsM.next()) {
                    model.addRow(new Object[]{
                        rsM.getInt("ID"),
                        rsM.getString("Kode_Makanan"),
                        rsM.getString("Nama_Makanan"),
                        rsM.getString("Kategori"),
                        rsM.getInt("Harga"),
                        "Makanan"
                    });
                }

                // Cari minuman
                String sqlMin = "SELECT * FROM minuman WHERE Nama_Minuman LIKE ?";
                PreparedStatement psMin = conn.prepareStatement(sqlMin);
                psMin.setString(1, "%" + keyword + "%");
                ResultSet rsMin = psMin.executeQuery();

                while (rsMin.next()) {
                    model.addRow(new Object[]{
                        rsMin.getInt("ID"),
                        rsMin.getString("Kode_Minuman"),
                        rsMin.getString("Nama_Minuman"),
                        "-", // kategori kosong untuk minuman
                        rsMin.getInt("Harga"),
                        "Minuman"
                    });
                }
            }

            // Tampilkan ke tabel
            jTable1.setModel(model);

            // Cek jika hasil kosong
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "🔎 Tidak ditemukan data untuk: " + keyword);
                tampilkanData();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal mencari data: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        tampilkanData();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Data_Menu_Makanan_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Menu_Makanan_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Menu_Makanan_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Menu_Makanan_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data_Menu_Makanan_Minuman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
