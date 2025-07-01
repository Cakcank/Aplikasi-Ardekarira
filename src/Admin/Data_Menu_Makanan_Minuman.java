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
public class Data_Menu_Makanan_Minuman extends javax.swing.JFrame {

    /**
     * Creates new form Data_Menu_Makanan
     */
    public Data_Menu_Makanan_Minuman() {
        initComponents();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable1.getSelectedRow();
            if (row != -1) {
                String selected = jComboBox1.getSelectedItem().toString();

                if (selected.equalsIgnoreCase("Makanan")) {
                    jTextField1.setText(jTable1.getValueAt(row, 1).toString()); // Kode
                    jTextField2.setText(jTable1.getValueAt(row, 2).toString()); // Nama
                    jTextField3.setText(jTable1.getValueAt(row, 3).toString()); // Kategori
                    jTextField4.setText(jTable1.getValueAt(row, 4).toString()); // Harga
                } else {
                    jTextField1.setText(jTable1.getValueAt(row, 1).toString()); // Kode
                    jTextField2.setText(jTable1.getValueAt(row, 2).toString()); // Nama
                    jTextField3.setText(""); // Kosongkan kategori
                    jTextField4.setText(jTable1.getValueAt(row, 3).toString()); // Harga
                }
            }
        }
    });
        sembunyikanFormInput();
        jButton5.setVisible(false); // Sembunyikan tombol Back saat awal
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
    
    private void resetAutoIncrement(String namaTabel) {
        try (Connection conn = DatabaseConnection.connect()) {
            // Buat tabel sementara
            String sqlTemp = "CREATE TEMPORARY TABLE temp_table AS SELECT * FROM " + namaTabel + " ORDER BY ID";
            conn.createStatement().execute(sqlTemp);

            // Hapus isi tabel asli
            String sqlDelete = "DELETE FROM " + namaTabel;
            conn.createStatement().execute(sqlDelete);

            // Reset Auto Increment
            String sqlReset = "ALTER TABLE " + namaTabel + " AUTO_INCREMENT = 1";
            conn.createStatement().execute(sqlReset);

            // Masukkan kembali data dari temp_table ke tabel asli
            if (namaTabel.equalsIgnoreCase("makanan")) {
                String sqlInsert = "INSERT INTO makanan (Kode_Makanan, Nama_Makanan, Kategori, Harga) SELECT Kode_Makanan, Nama_Makanan, Kategori, Harga FROM temp_table";
                conn.createStatement().execute(sqlInsert);
            } else if (namaTabel.equalsIgnoreCase("minuman")) {
                String sqlInsert = "INSERT INTO minuman (Kode_Minuman, Nama_Minuman, Harga) SELECT Kode_Minuman, Nama_Minuman, Harga FROM temp_table";
                conn.createStatement().execute(sqlInsert);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal reset ID: " + e.getMessage());
        }
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
    
    private void sembunyikanFormInput() {
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);

        jTextField1.setVisible(false);
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jTextField4.setVisible(false);

        jButton1.setVisible(false); // Tombol Tambah
        jButton2.setVisible(false); // Tombol Hapus
        jButton3.setVisible(false); // Tombol Edit
    }
    
    private void tampilkanFormInput(String pilihan) {
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel5.setVisible(true);

        jTextField1.setVisible(true);
        jTextField2.setVisible(true);
        jTextField4.setVisible(true);

        jButton1.setVisible(true);
        jButton2.setVisible(true);
        jButton3.setVisible(true);

        if (pilihan.equalsIgnoreCase("Makanan")) {
            jLabel4.setVisible(true);
            jTextField3.setVisible(true);
        } else if (pilihan.equalsIgnoreCase("Minuman")){
            jLabel4.setVisible(false);
            jTextField3.setVisible(false);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Menu                    :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Makanan", "Minuman" }));
        jComboBox1.setSelectedIndex(-1);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Kode  Makanan    :");

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nama  Makanan  : ");

        jLabel4.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Kategori                 :");

        jLabel5.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Harga                    :");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
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

        jButton1.setText("Tambah Data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hapus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Tampilkan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Search                   : ");

        jButton6.setText("Search");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("⇦Dashboard Admin");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Data Menu");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6))
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton2)
                            .addComponent(jButton5)
                            .addComponent(jButton7)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        Object selectedItem = jComboBox1.getSelectedItem();
        
        if (selectedItem == null) return;

        String pilihan = selectedItem.toString();

        if (pilihan.equals("Makanan")) {
            jTable1.setEnabled(true);
            jLabel2.setText("Kode Makanan    :");
            jLabel3.setText("Nama Makanan  :");
            jLabel4.setText("Kategori                :");
            jLabel5.setText("Harga                   :");
            jLabel4.setVisible(true);
            jTextField3.setVisible(true);
            jTextField3.setEnabled(true);
            tampilkanFormInput(pilihan);
            tampilkanData();

            // Tambahkan "Tampilkan Semua Data Menu" jika belum ada
            if (!sudahAdaTampilkanSemua()) {
                jComboBox1.addItem("Tampilkan Semua Data Menu");
            }

        }else if (pilihan.equals("Minuman")) {
            jTable1.setEnabled(true);
            jLabel2.setText("Kode Minuman    :");
            jLabel3.setText("Nama Minuman  :");
            jLabel5.setText("Harga                    :");
            jLabel4.setVisible(false);
            jTextField3.setVisible(false);
            jTextField3.setText("");
            tampilkanFormInput(pilihan);
            tampilkanData();

            // Tambahkan "Tampilkan Semua Data Menu" jika belum ada
            if (!sudahAdaTampilkanSemua()) {
                jComboBox1.addItem("Tampilkan Semua Data Menu");
            }

        } else if (pilihan.equals("Tampilkan Semua Data Menu")) {
            sembunyikanFormInput();
            tampilkanSemuaDataGabungan();

            // Hapus item setelah dipilih
            jComboBox1.removeItem("Tampilkan Semua Data Menu");
            jComboBox1.setSelectedIndex(-1); // reset pilihan
        }
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        // ✅ Tampilkan data sesuai pilihan combo
        tampilkanData();
        jButton5.setVisible(true);

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        tampilkanData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Pilih baris terlebih dahulu sebelum mengedit!");
            return;
        }

        String selected = jComboBox1.getSelectedItem().toString();
        int id = (int) jTable1.getValueAt(selectedRow, 0); // Ambil ID

        String kode = jTextField1.getText().trim();
        String nama = jTextField2.getText().trim();
        String hargaStr = jTextField4.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❗ Kode, Nama, dan Harga tidak boleh kosong.");
            return;
        }

        try {
            int harga = Integer.parseInt(hargaStr);
            Connection conn = DatabaseConnection.connect();

            // Cek duplikat (kode atau nama) tapi ID-nya harus beda
            String sqlCheck = "";
            if (selected.equalsIgnoreCase("Makanan")) {
                sqlCheck = "SELECT * FROM makanan WHERE (Kode_Makanan=? OR Nama_Makanan=?) AND ID<>?";
            } else {
                sqlCheck = "SELECT * FROM minuman WHERE (Kode_Minuman=? OR Nama_Minuman=?) AND ID<>?";
            }

            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
            stmtCheck.setString(1, kode);
            stmtCheck.setString(2, nama);
            stmtCheck.setInt(3, id);
            ResultSet rs = stmtCheck.executeQuery();

            // Update jika tidak duplikat
            if (selected.equalsIgnoreCase("Makanan")) {
                String kategori = jTextField3.getText().trim();
                if (kategori.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❗ Kategori harus diisi.");
                    return;
                }

                String sql = "UPDATE makanan SET Kode_Makanan=?, Nama_Makanan=?, Kategori=?, Harga=? WHERE ID=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, kode);
                stmt.setString(2, nama);
                stmt.setString(3, kategori);
                stmt.setInt(4, harga);
                stmt.setInt(5, id);
                stmt.executeUpdate();

            } else if (selected.equalsIgnoreCase("Minuman")) {
                String sql = "UPDATE minuman SET Kode_Minuman=?, Nama_Minuman=?, Harga=? WHERE ID=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, kode);
                stmt.setString(2, nama);
                stmt.setInt(3, harga);
                stmt.setInt(4, id);
                stmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "✅ Data berhasil diubah.");
            tampilkanData(); // Refresh data sesuai pilihan combo box

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Harga harus berupa angka.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal mengedit data: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
            // Reset pilihan ComboBox
        jComboBox1.setSelectedIndex(-1);

        // Kosongkan semua input
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");

        // Reset label-label
        jLabel2.setText("Kode");
        jLabel3.setText("Nama");
        jLabel4.setText("Kategori");
        jLabel5.setText("Harga");

        // Pastikan field kategori tampil kembali
        jLabel4.setVisible(true);
        jTextField3.setVisible(true);
        jTextField3.setEnabled(true);

        // Tampilkan semua data makanan + minuman
        tampilkanSemuaDataGabungan();
        sembunyikanFormInput();

        // Sembunyikan tombol Back lagi
        jButton5.setVisible(false);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String selected = jComboBox1.getSelectedItem().toString();
        String kode = jTextField1.getText().trim();
        String nama = jTextField2.getText().trim();
        String kategori = jTextField3.getText().trim();
        String hargaStr = jTextField4.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || hargaStr.isEmpty() || (selected.equals("Makanan") && kategori.isEmpty())) {
            JOptionPane.showMessageDialog(this, "❗ Semua field harus diisi.");
            return;
        }

        try {
            int harga = Integer.parseInt(hargaStr);
            Connection conn = DatabaseConnection.connect();

            // Cek duplikat
            String sqlCheck = "";
            if (selected.equals("Makanan")) {
                sqlCheck = "SELECT * FROM makanan WHERE Kode_Makanan=? OR Nama_Makanan=?";
            } else {
                sqlCheck = "SELECT * FROM minuman WHERE Kode_Minuman=? OR Nama_Minuman=?";
            }

            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
            stmtCheck.setString(1, kode);
            stmtCheck.setString(2, nama);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "⚠️ Data dengan kode atau nama yang sama sudah ada.");
                return;
            }

            // Insert data
            String sqlInsert = "";
            if (selected.equals("Makanan")) {
                sqlInsert = "INSERT INTO makanan (Kode_Makanan, Nama_Makanan, Kategori, Harga) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
                stmt.setString(1, kode);
                stmt.setString(2, nama);
                stmt.setString(3, kategori);
                stmt.setInt(4, harga);
                stmt.executeUpdate();
            } else {
                sqlInsert = "INSERT INTO minuman (Kode_Minuman, Nama_Minuman, Harga) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
                stmt.setString(1, kode);
                stmt.setString(2, nama);
                stmt.setInt(3, harga);
                stmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "✅ Data berhasil ditambahkan.");
            tampilkanData();
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Harga harus berupa angka.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal menambahkan data: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Pilih baris terlebih dahulu sebelum menghapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String selected = jComboBox1.getSelectedItem().toString();
        int id = (int) jTable1.getValueAt(selectedRow, 0); // Ambil ID

        try (Connection conn = DatabaseConnection.connect()) {
            if (selected.equalsIgnoreCase("Makanan")) {
                String sql = "DELETE FROM makanan WHERE ID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();

                // 🔁 Reset ID
                resetAutoIncrement("makanan");

            } else if (selected.equalsIgnoreCase("Minuman")) {
                String sql = "DELETE FROM minuman WHERE ID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();

                // 🔁 Reset ID
                resetAutoIncrement("minuman");
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
            }

            JOptionPane.showMessageDialog(this, "✅ Data berhasil dihapus.");
            tampilkanData(); // Refresh tabel

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal menghapus data: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String keyword = jTextField5.getText().trim();
        Object selectedItem = jComboBox1.getSelectedItem();

        if (keyword.isEmpty() || selectedItem == null) {
            JOptionPane.showMessageDialog(this, "❗ Masukkan jenis menu dan kata kunci pencarian terlebih dahulu.");
            return;
        }

        String pilihan = selectedItem.toString();
        DefaultTableModel model = new DefaultTableModel();

        try (Connection conn = Pegawai.DatabaseConnection.connect()) {
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
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        new Admin.Dashboard_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
