/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.User;

import db.Koneksi;
import model.Package;
import model.User;
import model.Studio;
import util.SessionManager;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Fardan
 */

public class BuatReservasiDetail extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuatReservasiDetail.class.getName());
    
    private Package selectedPackage;
    private java.sql.Date reservationDate;
    private java.sql.Time reservationTime;
    private ArrayList<Studio> availableStudios = new ArrayList<>();

    public int getDuration() {
        return duration;
    }

    private int duration;

    public Date getReservationDate() {
        return reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }

    /**
     * Creates new form Login
     */
    
    public BuatReservasiDetail() {
        initComponents();
    }
    
    public BuatReservasiDetail(Package pkg, java.sql.Date date, java.sql.Time time) {
        this.selectedPackage = pkg;
        this.reservationDate = date;
        this.reservationTime = time;
        this.duration = selectedPackage.getDuration();

        initComponents();
        
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Silakan login terlebih dahulu!");
            new ui.Masuk().setVisible(true);
            this.dispose();
            return;
        }
        
        populateUserData();
        loadPackageAndStudio();
    }

    public ArrayList<Studio> getAvailableStudios() {
        return availableStudios;
    }
    
    private void populateUserData() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        
        namaField.setText(currentUser.getFullName());
        alamatField.setText(currentUser.getAddress());
        emailField.setText(currentUser.getEmail());
        
        namaField.setEditable(false);
        alamatField.setEditable(false);
        emailField.setEditable(false);
    }
    
    public void loadPackageAndStudio() {
        paketComboBox.removeAllItems();
        paketComboBox.addItem("Paket " + selectedPackage.getPackageId());
        paketComboBox.setEnabled(false);
        
        loadAvailableStudios();
        setupJumlahOrangComboBox();
    }

    private void loadAvailableStudios() {
        try {
            Connection con = Koneksi.getConnection();

            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(reservationTime);
            cal.add(java.util.Calendar.MINUTE, duration);
            java.sql.Time endTime = new java.sql.Time(cal.getTimeInMillis());

            System.out.println("DEBUG: Paket " + selectedPackage.getName() +
                    ", Durasi: " + duration + " menit");
            System.out.println("DEBUG: Waktu mulai: " + reservationTime +
                    ", Waktu selesai: " + endTime);

            String sql = "SELECT s.* FROM studio s " +
                    "WHERE s.studio_id = ? " +
                    "AND s.status = TRUE " +
                    "AND NOT EXISTS ( " +
                    "  SELECT 1 FROM reservation r " +
                    "  JOIN package p ON r.package_id = p.package_id " +
                    "  WHERE p.studio_id = s.studio_id " +
                    "  AND r.reservation_date = ? " +
                    "  AND r.reservation_time < ? " +
                    "  AND ADDTIME(r.reservation_time, SEC_TO_TIME(p.duration * 60)) > ? " +
                    "  AND r.status_payment NOT IN ('CANCELLED', 'FAILED', 'REJECTED')" +
                    ")";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, selectedPackage.getStudioId());
            pstmt.setDate(2, reservationDate);
            pstmt.setTime(3, endTime);
            pstmt.setTime(4, reservationTime);

            ResultSet rs = pstmt.executeQuery();

            studioComboBox.removeAllItems();
            availableStudios.clear();

            int studioCount = 0;
            while (rs.next()) {
                Studio studio = new Studio();
                studio.setStudioId(rs.getInt("studio_id"));
                studio.setCapacity(rs.getInt("capacity"));
                studio.setStatus(rs.getBoolean("status"));

                availableStudios.add(studio);
                studioComboBox.addItem("Studio " + studio.getStudioId() +
                        " (" + studio.getCapacity() + " org)");
                studioCount++;
            }

            System.out.println("DEBUG: Found " + studioCount + " available studios");


            rs.close();
            pstmt.close();
            Koneksi.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("Error loading studios: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getReservationConflictInfo(int studioId) {
        try {
            Connection con = Koneksi.getConnection();

            String sql = "SELECT r.reservation_time, p.duration, p.name as package_name " +
                    "FROM reservation r " +
                    "JOIN package p ON r.package_id = p.package_id " +
                    "WHERE p.studio_id = ? " +
                    "AND r.reservation_date = ? " +
                    "AND r.status_payment NOT IN ('CANCELLED', 'FAILED', 'REJECTED') " +
                    "ORDER BY r.reservation_time";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studioId);
            pstmt.setDate(2, reservationDate);

            ResultSet rs = pstmt.executeQuery();

            rs.close();
            pstmt.close();
            Koneksi.closeConnection(con);

            return "Silakan pilih tanggal/waktu lain.";

        } catch (SQLException e) {
            return "Tidak dapat memeriksa jadwal.";
        }
    }
    
    private void setupJumlahOrangComboBox() {
        jumlahComboBox.removeAllItems();
        for (int i = selectedPackage.getMinPerson(); i <= selectedPackage.getMaxPerson(); i++) {
            jumlahComboBox.addItem(String.valueOf(i));
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
        jLabel3 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        judulLabel = new javax.swing.JLabel();
        namaLabel = new javax.swing.JLabel();
        namaPanel = new javax.swing.JPanel();
        namaField = new javax.swing.JTextField();
        alamatLabel = new javax.swing.JLabel();
        alamatPanel = new javax.swing.JPanel();
        alamatField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailPanel = new javax.swing.JPanel();
        emailField = new javax.swing.JTextField();
        paketLabel = new javax.swing.JLabel();
        paketPanel = new javax.swing.JPanel();
        paketComboBox = new javax.swing.JComboBox<>();
        studioLabel = new javax.swing.JLabel();
        studioPanel = new javax.swing.JPanel();
        studioComboBox = new javax.swing.JComboBox<>();
        jumlahLabel = new javax.swing.JLabel();
        jumlahPanel = new javax.swing.JPanel();
        jumlahComboBox = new javax.swing.JComboBox<>();
        jumlahOrangLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        navbarPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        bodyPanel.setBackground(new java.awt.Color(255, 255, 255));

        logoLabel.setFont(new java.awt.Font("Retro Majestic Free", 0, 18)); // NOI18N
        logoLabel.setText("Bonas Studio");

        judulLabel.setFont(new java.awt.Font("Poppins Medium", 0, 24)); // NOI18N
        judulLabel.setText("Buat Reservasi");

        namaLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        namaLabel.setText("Nama Lengkap:");

        namaField.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        namaField.setText("Nama Lengkap");
        namaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout namaPanelLayout = new javax.swing.GroupLayout(namaPanel);
        namaPanel.setLayout(namaPanelLayout);
        namaPanelLayout.setHorizontalGroup(
            namaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, namaPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        namaPanelLayout.setVerticalGroup(
            namaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, namaPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        alamatLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        alamatLabel.setText("Alamat:");

        alamatField.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        alamatField.setText("Alamat");
        alamatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout alamatPanelLayout = new javax.swing.GroupLayout(alamatPanel);
        alamatPanel.setLayout(alamatPanelLayout);
        alamatPanelLayout.setHorizontalGroup(
            alamatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alamatPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(alamatField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        alamatPanelLayout.setVerticalGroup(
            alamatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alamatPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(alamatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        emailLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        emailLabel.setText("Email:");

        emailField.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        emailField.setText("Email");
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout emailPanelLayout = new javax.swing.GroupLayout(emailPanel);
        emailPanel.setLayout(emailPanelLayout);
        emailPanelLayout.setHorizontalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emailPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        emailPanelLayout.setVerticalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emailPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        paketLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        paketLabel.setText("Paket:");

        paketComboBox.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        paketComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

        javax.swing.GroupLayout paketPanelLayout = new javax.swing.GroupLayout(paketPanel);
        paketPanel.setLayout(paketPanelLayout);
        paketPanelLayout.setHorizontalGroup(
            paketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paketPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(paketComboBox, 0, 153, Short.MAX_VALUE)
                .addContainerGap())
        );
        paketPanelLayout.setVerticalGroup(
            paketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(paketComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        studioLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        studioLabel.setText("Studio:");

        studioComboBox.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        studioComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

        javax.swing.GroupLayout studioPanelLayout = new javax.swing.GroupLayout(studioPanel);
        studioPanel.setLayout(studioPanelLayout);
        studioPanelLayout.setHorizontalGroup(
            studioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studioPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(studioComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        studioPanelLayout.setVerticalGroup(
            studioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studioPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(studioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jumlahLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jumlahLabel.setText("Jumlah:");

        jumlahComboBox.setEditable(true);
        jumlahComboBox.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jumlahComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10" }));

        jumlahOrangLabel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jumlahOrangLabel.setText("orang");

        javax.swing.GroupLayout jumlahPanelLayout = new javax.swing.GroupLayout(jumlahPanel);
        jumlahPanel.setLayout(jumlahPanelLayout);
        jumlahPanelLayout.setHorizontalGroup(
            jumlahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jumlahPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jumlahComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jumlahOrangLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jumlahPanelLayout.setVerticalGroup(
            jumlahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jumlahPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jumlahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jumlahComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahOrangLabel))
                .addGap(9, 9, 9))
        );

        okButton.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logoLabel)
                    .addComponent(judulLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaLabel)
                    .addComponent(namaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailLabel)
                            .addComponent(emailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alamatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alamatLabel))
                        .addGap(107, 107, 107)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(studioLabel)
                                .addComponent(paketLabel)
                                .addComponent(jumlahLabel)
                                .addComponent(jumlahPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(studioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(paketPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(209, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(paketLabel)
                        .addGap(3, 3, 3)
                        .addComponent(paketPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(studioLabel)
                        .addGap(3, 3, 3)
                        .addComponent(studioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jumlahLabel)
                        .addGap(3, 3, 3)
                        .addComponent(jumlahPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(logoLabel)
                        .addGap(0, 0, 0)
                        .addComponent(judulLabel)
                        .addGap(80, 80, 80)
                        .addComponent(namaLabel)
                        .addGap(3, 3, 3)
                        .addComponent(namaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(alamatLabel)
                        .addGap(3, 3, 3)
                        .addComponent(alamatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(emailLabel)
                        .addGap(3, 3, 3)
                        .addComponent(emailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        navbarPanel.setBackground(new java.awt.Color(102, 102, 102));

        backButton.setBackground(new java.awt.Color(102, 102, 102));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/back.png"))); // NOI18N
        backButton.setBorder(null);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navbarPanelLayout = new javax.swing.GroupLayout(navbarPanel);
        navbarPanel.setLayout(navbarPanelLayout);
        navbarPanelLayout.setHorizontalGroup(
            navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navbarPanelLayout.setVerticalGroup(
            navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(backButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(navbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(816, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        if (selectedPackage != null) {
            new BuatReservasi(selectedPackage).setVisible(true);
        } else {
            new PilihPaket().setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void namaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaFieldActionPerformed

    private void alamatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatFieldActionPerformed

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

    private boolean isStudioAvailable(int studioId, java.sql.Date date, java.sql.Time time) {
        try {
            Connection con = Koneksi.getConnection();
            
            // Hitung waktu berakhir (durasi paket + waktu mulai)
            int duration = selectedPackage.getDuration();
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.MINUTE, duration);
            java.sql.Time endTime = new java.sql.Time(cal.getTimeInMillis());
            
            // Cek apakah ada reservasi yang bertabrakan
            String sql = "SELECT COUNT(*) FROM reservation r " +
                        "JOIN package p ON r.package_id = p.package_id " +
                        "WHERE p.studio_id = ? " +
                        "AND r.reservation_date = ? " +
                        "AND r.reservation_time < ? " +  // waktu mulai reservasi lain < waktu selesai kita
                        "AND ADDTIME(r.reservation_time, SEC_TO_TIME(p.duration * 60)) > ? " + // waktu selesai reservasi lain > waktu mulai kita
                        "AND r.status_payment != 'CANCELLED'"; // abaikan yang cancelled
            
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studioId);
            pstmt.setDate(2, date);
            pstmt.setTime(3, endTime); // end time kita
            pstmt.setTime(4, time); // start time kita
            
            ResultSet rs = pstmt.executeQuery();
            boolean available = false;
            
            if (rs.next()) {
                int count = rs.getInt(1);
                available = (count == 0);
            }
            
            rs.close();
            pstmt.close();
            Koneksi.closeConnection(con);
            
            return available;
            
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("Error checking studio availability: " + e.getMessage());
            return false; // Jika error, anggap tidak tersedia
        }
    }
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (jumlahComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih jumlah orang terlebih dahulu!");
            return;
        }
        if (studioComboBox.getSelectedItem() == null || availableStudios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada studio tersedia!");
            return;
        }
        try {
            int jumlahOrang = Integer.parseInt(jumlahComboBox.getSelectedItem().toString());
            int studioIndex = studioComboBox.getSelectedIndex();
            Studio selectedStudio = availableStudios.get(studioIndex);
            
            // Hitung total harga
            int totalPrice = (int)(selectedPackage.getPrice() * jumlahOrang);
            
            // Lanjut ke konfirmasi pembayaran
            BuatReservasiBayar bayarForm = new BuatReservasiBayar();
            bayarForm.setReservationData(
                selectedPackage, 
                selectedStudio,
                reservationDate, 
                reservationTime, 
                jumlahOrang, 
                totalPrice
            );
            bayarForm.setVisible(true);
            
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan: " + e.getMessage());
        }
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuatReservasiDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new BuatReservasiDetail().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JPanel alamatPanel;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JPanel emailPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel judulLabel;
    private javax.swing.JComboBox<String> jumlahComboBox;
    private javax.swing.JLabel jumlahLabel;
    private javax.swing.JLabel jumlahOrangLabel;
    private javax.swing.JPanel jumlahPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JTextField namaField;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JPanel namaPanel;
    private javax.swing.JPanel navbarPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox<String> paketComboBox;
    private javax.swing.JLabel paketLabel;
    private javax.swing.JPanel paketPanel;
    private javax.swing.JComboBox<String> studioComboBox;
    private javax.swing.JLabel studioLabel;
    private javax.swing.JPanel studioPanel;
    // End of variables declaration//GEN-END:variables
}
