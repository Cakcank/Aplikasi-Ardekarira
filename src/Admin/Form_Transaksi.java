/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;
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
public class Form_Transaksi extends javax.swing.JFrame {

    /**
     * Creates new form Form_Transaksi
     */
    public Form_Transaksi() {
        initComponents();
        tampilkanDataTransaksi();
    }
    
    private void tampilkanDataTransaksi() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Transaksi");
        model.addColumn("Nama Pemesan");
        model.addColumn("Metode");
        model.addColumn("Pesanan");
        model.addColumn("Total");
        model.addColumn("Bayar");
        model.addColumn("Kembalian");
        model.addColumn("Tanggal");

        try {
            Connection conn = DatabaseConnection.connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM transaksi ORDER BY ID_Transaksi ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID_Transaksi"),
                    rs.getString("Nama_Pemesan"),
                    rs.getString("Metode"),
                    rs.getString("Pesanan"),
                    rs.getInt("Total"),
                    rs.getInt("Bayar"),
                    rs.getInt("Kembalian"),
                    rs.getTimestamp("Tanggal")
                });
            }

            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data transaksi:\n" + e.getMessage());
        }
    }
    
    private void cariTransaksiByWaktu(String keyword) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Kosongkan isi tabel

        String formatSql;

        // Tentukan format DATE_FORMAT sesuai panjang input keyword
        switch (keyword.length()) {
            case 4:  formatSql = "%Y"; break;                         // Tahun
            case 7:  formatSql = "%Y-%m"; break;                      // Tahun-Bulan
            case 10: formatSql = "%Y-%m-%d"; break;                   // Tahun-Bulan-Tanggal
            case 13: formatSql = "%Y-%m-%d %H"; break;                // Dengan jam
            case 16: formatSql = "%Y-%m-%d %H:%i"; break;             // Dengan menit
            case 19: formatSql = "%Y-%m-%d %H:%i:%s"; break;          // Full datetime
            default:
                JOptionPane.showMessageDialog(this, "❗ Format input tidak valid.\nContoh: 2025, 2025-06, 2025-06-21, 2025-06-21 14");
                tampilkanDataTransaksi(); // tampilkan semua agar tidak kosong
                return;
        }

        String sql = "SELECT * FROM transaksi WHERE DATE_FORMAT(Tanggal, ?) = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, formatSql);
            ps.setString(2, keyword);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("ID_Transaksi"),
                    rs.getString("Nama_Pemesan"),
                    rs.getString("Metode"),
                    rs.getString("Pesanan"),
                    rs.getInt("Total"),
                    rs.getInt("Bayar"),
                    rs.getInt("Kembalian"),
                    rs.getTimestamp("Tanggal")
                });
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "🔎 Data tidak ditemukan untuk: " + keyword);
                tampilkanDataTransaksi(); // tampilkan semua data sebagai fallback
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal mencari data: " + e.getMessage());
            tampilkanDataTransaksi(); // tampilkan semua jika error
        }
    }

    
    private void resetIDTransaksi(Connection conn) throws SQLException {
        Statement st = conn.createStatement();

        st.executeUpdate("SET @num := 0");
        st.executeUpdate("UPDATE transaksi SET ID_Transaksi = @num := @num + 1");
        st.executeUpdate("ALTER TABLE transaksi AUTO_INCREMENT = 1");
    }



    
    private void hitungTotalTransaksiTerpilih() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int[] selectedRows = jTable1.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "❗ Pilih baris yang ingin dihitung totalnya!");
            return;
        }

        StringBuilder hasil = new StringBuilder("🧾 Ringkasan Transaksi:\n\n");
        int totalKeseluruhan = 0;

        for (int row : selectedRows) {
            int id         = (int) model.getValueAt(row, 0);
            String nama    = model.getValueAt(row, 1).toString();
            String metode  = model.getValueAt(row, 2).toString();
            String pesanan = model.getValueAt(row, 3).toString();
            int total      = (int) model.getValueAt(row, 4);
            int bayar      = (int) model.getValueAt(row, 5);
            int kembalian  = (int) model.getValueAt(row, 6);
            String tanggal = model.getValueAt(row, 7).toString();

            hasil.append("ID         : ").append(id).append("\n")
                 .append("Nama       : ").append(nama).append("\n")
                 .append("Metode     : ").append(metode).append("\n")
                 .append("Pesanan    : ").append(pesanan).append("\n")
                 .append("Total      : Rp ").append(total).append("\n")
                 .append("Bayar      : Rp ").append(bayar).append("\n")
                 .append("Kembalian  : Rp ").append(kembalian).append("\n")
                 .append("Tanggal    : ").append(tanggal).append("\n")
                 .append("--------------------------------------------------\n");

            totalKeseluruhan += total;
        }

        hasil.append("\n💰 Total Keseluruhan: Rp ").append(totalKeseluruhan);

        JOptionPane.showMessageDialog(this, hasil.toString(), "Total Transaksi Terpilih", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void hapusTransaksiTerpilih() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int[] selectedRows = jTable1.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "❗ Pilih baris yang ingin dihapus terlebih dahulu!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus " + selectedRows.length + " transaksi?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try (Connection conn = DatabaseConnection.connect()) {
            // Hapus data berdasarkan ID
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int id = (int) model.getValueAt(selectedRows[i], 0);
                PreparedStatement ps = conn.prepareStatement("DELETE FROM transaksi WHERE ID_Transaksi = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                model.removeRow(selectedRows[i]); // update tampilan tabel
            }

            // Reset ID agar kembali urut dari 1
            resetIDTransaksi(conn);

            // Refresh tabel
            tampilkanDataTransaksi();

            JOptionPane.showMessageDialog(this, "✅ Transaksi berhasil dihapus dan ID di-reset ulang!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal menghapus transaksi: " + e.getMessage());
        }
    }





    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("⇦  BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Search : ");

        jButton2.setText("GO ⇨");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Total");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Tampilkan");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Transaksi");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String waktu = jTextField1.getText().trim(); // Format: YYYY-MM-DD
        if (waktu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❗ Masukkan tanggal terlebih dahulu!");
        } else {
            cariTransaksiByWaktu(waktu);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        hapusTransaksiTerpilih();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        hitungTotalTransaksiTerpilih();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        tampilkanDataTransaksi();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Admin.Dashboard_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
