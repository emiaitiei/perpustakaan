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
public class FDataAnggota extends javax.swing.JFrame {

    /**
     * Creates new form FDataAnggota
     */
    public FDataAnggota() 
    {
        initComponents();
        load_data(); //Memanggil menampilkan Data
        IDOtomatis(); //menampilkan id otomatis
        LoadTingkat(); //load combo tingkat
        LoadJurusan(); //load combo jurusan
        LoadKelas(); //load combo kelas
        
        bedit.setEnabled(false);
        bdelete.setEnabled(false);
        
    }
    
    //Load Data Dari Database anggota
    private void load_data()
    {
        Connection kon=koneksi.koneksiDb();
        Object header[]={"Id Anggota", "NIS", "Nama Anggota", "Jenis Kelamin", "Tingkat", "Jurusan", "Kelas", "No HP", "Status"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        TabelAnggota.setModel(data);
        String sql_data="SELECT * FROM anggota";
        try
        {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next())
            {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                String d5=rs.getString(5);
                String d6=rs.getString(6);
                String d7=rs.getString(7);
                String d8=rs.getString(8);
                String d9=rs.getString(9);
                
                String d[]={d1,d2,d3,d4,d5,d6,d7,d8,d9};
                data.addRow(d);
                
            }          
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }  
    
    //ID Anggota Otomatis
    private void IDOtomatis()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_id="SELECT * FROM anggota order by id_anggota desc";
            ResultSet rs=st.executeQuery(sql_id);
            if(rs.next())
            {
                String id_anggota=rs.getString("id_anggota").substring(1);
                String AN=""+(Integer.parseInt(id_anggota)+1);
                String No1="";
                if(AN.length()==1) //jika id_anggota A00001
                {
                    No1="0000";
                }
                else if(AN.length()==2) //jika id_anggota A00010
                {
                    No1="000";
                }
                else if(AN.length()==3) //jika id_anggota A00100
                {
                    No1="00";
                }
                id.setText("A"+No1+AN);
            }
            else
            {
                id.setText("A00001");
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    //Load Combo Tingkat
    private void LoadTingkat()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT * FROM tingkat";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                ktingkat.addItem(rs.getString("id_tingkat"));
            } 
            rs.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Load Nama Tingkat
    private void NamaTingkat()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT tingkat FROM tingkat WHERE id_tingkat='"+ktingkat.getSelectedItem()+"'";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                ntingkat.setText(rs.getString("tingkat"));
            } 
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Load Combo Jurusan
    private void LoadJurusan()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT id_jurusan FROM jurusan";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                kjurusan.addItem(rs.getString("id_jurusan"));
            } 
            rs.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Load Nama Jurusan
    private void NamaJurusan()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT jurusan FROM jurusan WHERE id_jurusan='"+kjurusan.getSelectedItem()+"'";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                njurusan.setText(rs.getString("jurusan"));
            } 
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Load Combo Kelas
    private void LoadKelas()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT id_kelas FROM kelas";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                kkelas.addItem(rs.getString("id_kelas"));
            } 
            rs.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Input Data
    private void input_data()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            //untuk jenis kelamin
            String jk="";
            if(jkl.isSelected())
            {
                jk=jkl.getText();
            }
            else
            {
                jk=jkp.getText();
            }
            
            String sql="INSERT INTO anggota values('"+id.getText()
                    +"','"+nis.getText()
                    +"','"+nama.getText()
                    +"','"+jk
                    +"','"+ktingkat.getSelectedItem()
                    +"','"+kjurusan.getSelectedItem()
                    +"','"+kkelas.getSelectedItem()
                    +"','"+nope.getText()
                    +"','"+status.getSelectedItem()
                    +"')";
            st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Dimasukan");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Reset Data setelah Input/edit/delete
    public void clear()
    {
        nis.setText("");
        nama.setText("");
        nope.setText("");
        jkl.setSelected(rootPaneCheckingEnabled);
        ktingkat.setSelectedItem(1);
        kjurusan.setSelectedItem("AKL");
        kkelas.setSelectedItem(1);
        status.setSelectedItem("AKTIF");
    }
    
    //Edit Data
    private void update()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String jk="";
            if(jkl.isSelected())
            {
                jk=jkl.getText();
            }
            else
            {
                jk=jkp.getText();
            }
            
            String sql_update="UPDATE anggota SET NIS='"+nis.getText()
                    +"',nama_anggota='"+nama.getText()
                    +"',jenis_kelamin='"+jk
                    +"',id_tingkat='"+ktingkat.getSelectedItem()
                    +"',id_jurusan='"+kjurusan.getSelectedItem()
                    +"',id_kelas='"+kkelas.getSelectedItem()
                    +"',nope='"+nope.getText()
                    +"',status='"+status.getSelectedItem()
                    +"'WHERE id_anggota='"+id.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Delete Data
    private void delete()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_delete="DELETE from anggota WHERE "
                    + "id_anggota='"+id.getText()+"'";
            st.executeUpdate(sql_delete);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
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

        jLabel1 = new javax.swing.JLabel();
        bkeluar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        nis = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        jkp = new javax.swing.JRadioButton();
        jkl = new javax.swing.JRadioButton();
        ktingkat = new javax.swing.JComboBox<>();
        kjurusan = new javax.swing.JComboBox<>();
        kkelas = new javax.swing.JComboBox<>();
        nope = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        ntingkat = new javax.swing.JTextField();
        njurusan = new javax.swing.JTextField();
        binput = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelAnggota = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DATA ANGGOTA PERPUSTAKAAN");

        bkeluar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bkeluar.setText("KELUAR");
        bkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkeluarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Id Anggota       :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("NIS                     :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nama Anggota :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Jenis Kelamin    :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tingkat               :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Jurusan              :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Kelas                   :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("No HP                :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Status                :");

        jkp.setText("Perempuan");

        jkl.setText("Laki-Laki");

        ktingkat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ktingkatMouseClicked(evt);
            }
        });
        ktingkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ktingkatActionPerformed(evt);
            }
        });

        kjurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kjurusanActionPerformed(evt);
            }
        });

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AKTIF", "TIDAK AKTIF" }));

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

        TabelAnggota.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelAnggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelAnggota);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kjurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(njurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ktingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(ntingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(nis))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(nama))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jkp, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jkl, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nope, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(binput)
                        .addGap(126, 126, 126)
                        .addComponent(bedit)
                        .addGap(129, 129, 129)
                        .addComponent(bdelete)
                        .addGap(212, 212, 212))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bkeluar)
                .addGap(419, 419, 419)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bkeluar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addGap(32, 32, 32)
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
                            .addComponent(nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jkp)
                            .addComponent(jkl))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ktingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ntingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(kjurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(njurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(kkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(nope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        // TODO add your handling code here:
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Data Anggota?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
            new FUtamaMeita2().show();
            this.dispose();
        }
    }//GEN-LAST:event_bkeluarActionPerformed

    private void ktingkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ktingkatActionPerformed
        // TODO add your handling code here:
        NamaTingkat();
    }//GEN-LAST:event_ktingkatActionPerformed

    private void ktingkatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ktingkatMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ktingkatMouseClicked

    private void kjurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kjurusanActionPerformed
        // TODO add your handling code here:
        NamaJurusan();
    }//GEN-LAST:event_kjurusanActionPerformed

    private void binputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binputActionPerformed
        // TODO add your handling code here:
        //Confirm YESNO
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

    private void TabelAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelAnggotaMouseClicked
        // TODO add your handling code here:
        int bar=TabelAnggota.getSelectedRow();
        String a=TabelAnggota.getValueAt(bar, 0).toString();
        String b=TabelAnggota.getValueAt(bar, 1).toString();
        String c=TabelAnggota.getValueAt(bar, 2).toString();
        String d=TabelAnggota.getValueAt(bar, 3).toString();
        String e=TabelAnggota.getValueAt(bar, 4).toString();
        String f=TabelAnggota.getValueAt(bar, 5).toString();
        String g=TabelAnggota.getValueAt(bar, 6).toString();
        String h=TabelAnggota.getValueAt(bar, 7).toString();
        String i=TabelAnggota.getValueAt(bar, 8).toString();
        
        id.setText(a);
        nis.setText(b);
        nama.setText(c);
        //Jenis Kelamin
        if("Laki-Laki".equals(d))
        {
            jkl.setSelected(true);
        }
        else
        {
            jkp.setSelected(true);
        }
        //Tingkat
        ktingkat.setSelectedItem(e);
        kjurusan.setSelectedItem(f);
        kkelas.setSelectedItem(g);
        nope.setText(h);
        //Status
        if("AKTIF".equals(i))
        {
            status.setSelectedItem(i);
        }
        else
        {
            status.setSelectedItem(i);
        }
        
        //Set Disable INPUT
        binput.setEnabled(false);
        bedit.setEnabled(true);
        bdelete.setEnabled(true);
    }//GEN-LAST:event_TabelAnggotaMouseClicked

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
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelAnggota;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bedit;
    private javax.swing.JButton binput;
    private javax.swing.JButton bkeluar;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton jkl;
    private javax.swing.JRadioButton jkp;
    private javax.swing.JComboBox<String> kjurusan;
    private javax.swing.JComboBox<String> kkelas;
    private javax.swing.JComboBox<String> ktingkat;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nis;
    private javax.swing.JTextField njurusan;
    private javax.swing.JTextField nope;
    private javax.swing.JTextField ntingkat;
    private javax.swing.JComboBox<String> status;
    // End of variables declaration//GEN-END:variables
}
