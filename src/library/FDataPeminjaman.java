/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class FDataPeminjaman extends javax.swing.JFrame {

    /**
     * Creates new form FDataPeminjaman
     */
    public FDataPeminjaman() {
        initComponents();
        load_data();
        IDOtomatis();
        loadComboBoxData();

        bedit.setEnabled(false);
        bdelete.setEnabled(false);
    }
    
    private void loadComboBoxData() {
        Connection kon = koneksi.koneksiDb();
        try {
            // Load Anggota
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_anggota, nama_anggota FROM anggota");
            while (rs.next()) {
                comboNama.addItem(rs.getString("id_anggota") + " - " + rs.getString("nama_anggota"));
            }
            
            // Load User (Petugas)
            rs = st.executeQuery("SELECT id_user, nama_user FROM user");
            while (rs.next()) {
                comboUser.addItem(rs.getString("id_user") + " - " + rs.getString("nama_user"));
            }
            
            // Load Buku
            rs = st.executeQuery("SELECT id_buku, judul_buku FROM buku");
            while (rs.next()) {
                comboBuku.addItem(rs.getString("id_buku") + " - " + rs.getString("judul_buku"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + e.getMessage());
        }
    }
    
    private void load_data() {
        Connection kon = koneksi.koneksiDb();
        Object header[] = {"Id Peminjaman", "Nama Anggota", "Nama Petugas", "Judul Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tabelPeminjaman.setModel(data);

        String sql_data = "SELECT peminjaman.id_peminjaman, anggota.nama_anggota, user.nama_user, buku.judul_buku, peminjaman.tanggal_pinjam, peminjaman.tanggal_kembali, peminjaman.status " +
                        "FROM peminjaman " +
                        "JOIN anggota ON peminjaman.id_anggota = anggota.id_anggota " +
                        "JOIN user ON peminjaman.id_user = user.id_user " +
                        "JOIN buku ON peminjaman.id_buku = buku.id_buku";

        try (Statement st = kon.createStatement(); ResultSet rs = st.executeQuery(sql_data)) {
            while (rs.next()) {
                String d1 = rs.getString("id_peminjaman");
                String d2 = rs.getString("nama_anggota");
                String d3 = rs.getString("nama_user");
                String d4 = rs.getString("judul_buku");
                String d5 = rs.getString("tanggal_pinjam");
                String d6 = rs.getString("tanggal_kembali");
                String d7 = rs.getString("status");

                String d[] = {d1, d2, d3, d4, d5, d6, d7};
                data.addRow(d);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void IDOtomatis() {
        try {
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM peminjaman ORDER BY id_peminjaman DESC";
            ResultSet rs = st.executeQuery(sql_id);
            if (rs.next()) {
                String id_peminjaman = rs.getString("id_peminjaman").substring(1);
                String AN = "" + (Integer.parseInt(id_peminjaman) + 1);
                String No1 = "";
                if (AN.length() == 1) { No1 = "000"; }
                else if (AN.length() == 2) { No1 = "00"; }
                else if (AN.length() == 3) { No1 = "0"; }
                id.setText("P" + No1 + AN);
            } else {
                id.setText("P0001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void input_data() {
        try {
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
        
            String idAnggota = comboNama.getSelectedItem().toString().split(" - ")[0];
            String idUser = comboUser.getSelectedItem().toString().split(" - ")[0];
            String idBuku = comboBuku.getSelectedItem().toString().split(" - ")[0];
        
  
            String sql = "INSERT INTO peminjaman (id_peminjaman, id_anggota, id_user, id_buku, tanggal_pinjam, tanggal_kembali, status) VALUES ('"
                        + id.getText() + "', '" 
                        + idAnggota + "', '"
                        + idUser + "', '"
                        + idBuku + "', '"
                        + pinjam.getText() + "','"
                        + kembali.getText() + "','"
                        + status.getSelectedItem()+ "')";

            // Eksekusi query
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Peminjaman Berhasil Dimasukkan");

            // Tutup koneksi
            st.close();
            kon.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
    try {
        Connection kon = koneksi.koneksiDb();
        Statement st = kon.createStatement();
        
        String idAnggota = comboNama.getSelectedItem().toString().split(" - ")[0];
            String idUser = comboUser.getSelectedItem().toString().split(" - ")[0];
            String idBuku = comboBuku.getSelectedItem().toString().split(" - ")[0];


        String sql_update = "UPDATE peminjaman SET id_anggota='" + idAnggota + 
                                "', id_user='" + idUser + 
                                "', id_buku='" + idBuku + 
                                "', tanggal_pinjam='" + pinjam.getText() + 
                                "', tanggal_kembali='" + kembali.getText() + 
                                "', status='" + status.getSelectedItem() + 
                                "' WHERE id_peminjaman='" + id.getText() + "'";
            
            st.executeUpdate(sql_update);
            JOptionPane.showMessageDialog(null, "Data Peminjaman Berhasil Diperbarui");
            
            st.close();
            kon.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + e.getMessage());
        }
    }
    
    private void delete() {
        try {
            Connection kon = koneksi.koneksiDb();
            Statement st = kon.createStatement();
            String sql_delete = "DELETE FROM peminjaman WHERE id_peminjaman='" + id.getText() + "'";
            st.executeUpdate(sql_delete);
            JOptionPane.showMessageDialog(null, "Data Peminjaman Berhasil Dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clear() {
        id.setText("");
        comboNama.setSelectedIndex(0);
        comboUser.setSelectedIndex(0);
        comboBuku.setSelectedIndex(0);
        pinjam.setText("");
        kembali.setText("");
        status.setSelectedItem("AKTIF");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        keluar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPeminjaman = new javax.swing.JTable();
        binput = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        pinjam = new javax.swing.JTextField();
        kembali = new javax.swing.JTextField();
        comboNama = new javax.swing.JComboBox<>();
        comboUser = new javax.swing.JComboBox<>();
        comboBuku = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        keluar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        keluar.setText("KELUAR");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DAFTAR PEMINJAMAN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Id Peminjaman      :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nama Peminjam   :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nama Petugas      : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Judul Buku             :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tanggal Pinjam      :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Tanggal Kembali    :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Status                      :");

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DI PINJAM", "KEMBALI" }));

        tabelPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPeminjaman);

        binput.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        binput.setText("INPUT");
        binput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binputActionPerformed(evt);
            }
        });

        bedit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bedit.setText("EDIT");
        bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditActionPerformed(evt);
            }
        });

        bdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bdelete.setText("DELETE");
        bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(comboBuku, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(comboUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(1, 1, 1))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(kembali))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboNama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(keluar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(binput)
                        .addGap(105, 105, 105)
                        .addComponent(bedit)
                        .addGap(99, 99, 99)
                        .addComponent(bdelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(keluar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binput)
                    .addComponent(bedit)
                    .addComponent(bdelete))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(comboBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        // TODO add your handling code here:
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Data Peminjaman?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
            new FUtamaMeita2().show();
            this.dispose();
        }
    }//GEN-LAST:event_keluarActionPerformed

    private void binputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binputActionPerformed
        // TODO add your handling code here:
        int simpan=JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar? SIMPAN?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simpan==JOptionPane.YES_OPTION)
        {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }
    }//GEN-LAST:event_binputActionPerformed

    private void tabelPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPeminjamanMouseClicked
        // TODO add your handling code here:
        int bar=tabelPeminjaman.getSelectedRow();
        String a=tabelPeminjaman.getValueAt(bar, 0).toString();
        String b=tabelPeminjaman.getValueAt(bar, 1).toString();
        String c=tabelPeminjaman.getValueAt(bar, 2).toString();
        String d=tabelPeminjaman.getValueAt(bar, 3).toString();
        String e=tabelPeminjaman.getValueAt(bar, 4).toString();
        String f=tabelPeminjaman.getValueAt(bar, 5).toString();
        String g=tabelPeminjaman.getValueAt(bar, 6).toString();
        
        id.setText(a);
        comboNama.setSelectedIndex(0);
        comboUser.setSelectedIndex(0);
        comboBuku.setSelectedIndex(0);
        pinjam.setText(e);
        kembali.setText(f);
        if("AKTIF".equals(g))
        {
            status.setSelectedItem(g);
        }
        else
        {
            status.setSelectedItem(g);
        }
        
        //Set Disable INPUT
        binput.setEnabled(false);
        bedit.setEnabled(true);
        bdelete.setEnabled(true);
    }//GEN-LAST:event_tabelPeminjamanMouseClicked

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        // TODO add your handling code here:
        int update=JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Update? Update?",
                "Update Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(update==JOptionPane.YES_OPTION)
        {
            update();
            clear();
            load_data();
            IDOtomatis();
            //Set Enable INPUT
            binput.setEnabled(true);
            bedit.setEnabled(false);
            bdelete.setEnabled(false);
        }
    }//GEN-LAST:event_beditActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        // TODO add your handling code here:
        int delete=JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Hapus? Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(delete==JOptionPane.YES_OPTION)
        {
            delete();
            clear();
            load_data();
            IDOtomatis();
            //Set Enable INPUT
            binput.setEnabled(true);
            bedit.setEnabled(false);
            bdelete.setEnabled(false);
        }
    }//GEN-LAST:event_bdeleteActionPerformed

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
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bedit;
    private javax.swing.JButton binput;
    private javax.swing.JComboBox<String> comboBuku;
    private javax.swing.JComboBox<String> comboNama;
    private javax.swing.JComboBox<String> comboUser;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keluar;
    private javax.swing.JTextField kembali;
    private javax.swing.JTextField pinjam;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTable tabelPeminjaman;
    // End of variables declaration//GEN-END:variables
}
