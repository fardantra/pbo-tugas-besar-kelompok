/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.User;


import db.Koneksi;
import model.Package;
import model.User;
import util.SessionManager;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Fardan
 */

public class PilihPaket extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PilihPaket.class.getName());
    private ArrayList<Package> packages = new ArrayList<>();
    
    /**
     * Creates new form Login
     */
    public PilihPaket() {
        initComponents();
        
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, 
                "Silakan login terlebih dahulu!", 
                "Akses Ditolak", 
                JOptionPane.WARNING_MESSAGE);
            new ui.Masuk().setVisible(true);
            this.dispose();
            return;
        }
        
        loadPackages();
        
        User currentUser = SessionManager.getInstance().getCurrentUser();
        System.out.println("Welcome, " + currentUser.getFullName());
    }
    
    private void loadPackages() {
        try {
            Connection con = Koneksi.getConnection();
            String sql = "SELECT * FROM package ORDER BY package_id ASC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            packages.clear();
            
            System.out.println("=== DEBUG: Loading packages ===");
            int count = 0;
            
            while (rs.next()) {
                Package pkg = new Package();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setStudioId(rs.getInt("studio_id"));
                pkg.setName(rs.getString("name"));
                pkg.setPrice(rs.getInt("price"));
                pkg.setMinPerson(rs.getInt("min_person"));
                pkg.setMaxPerson(rs.getInt("max_person"));
                pkg.setDuration(rs.getInt("duration"));
                
                packages.add(pkg);
                
                System.out.println("Package " + (count+1) + ": " + pkg.getName() + 
                                " | Price: " + pkg.getPrice() + 
                                " | Min: " + pkg.getMinPerson() + 
                                " | Max: " + pkg.getMaxPerson());
                count++;
            }
            
            System.out.println("Total packages loaded: " + count);
            
            if (count == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada paket tersedia di database!\nSilakan hubungi admin.", 
                    "Database Kosong", 
                    JOptionPane.WARNING_MESSAGE);
            }
            
            rs.close();
            stmt.close();
            Koneksi.closeConnection(con);
            
            updatePackageDisplay();
            
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("Error loading packages: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data paket!\n" + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updatePackageDisplay() {
        System.out.println("=== DEBUG: Updating UI with " + packages.size() + " packages ===");
        
        try {
            if (packages.size() >= 1) {
                Package pkg1 = packages.get(0);
                jumlahOrangIsiLabel.setText(pkg1.getMinPerson() + "-" + pkg1.getMaxPerson() + " orang");
                durasiSesiIsiLabel.setText(pkg1.getDuration() + " menit");
                
                int price1 = (int) pkg1.getPrice();
                hargaIsiLabel.setText("Rp" + String.format("%,d", price1) + "/orang");
                
                fasilitiasIsi1Label.setText("1 background");
                fasilitasIsi2Label.setText("Foto hasil jadi per orang");
                fasilitasIsi3Label.setText("Editing basic");
                
                System.out.println("Package 1: " + pkg1.getName() + " - Price: " + pkg1.getPrice());
            }
            
            if (packages.size() >= 2) {
                Package pkg2 = packages.get(1);
                jumlahOrangIsiLabel1.setText(pkg2.getMinPerson() + "-" + pkg2.getMaxPerson() + " orang");
                durasiSesiIsiLabel1.setText(pkg2.getDuration() + " menit");
                
                int price2 = (int) pkg2.getPrice();
                hargaIsiLabel1.setText("Rp" + String.format("%,d", price2) + "/orang");
                
                fasilitiasIsi1Label1.setText("1 background");
                fasilitasIsi2Label1.setText("Foto hasil jadi per orang");
                fasilitasIsi3Label1.setText("Editing basic");
                
                System.out.println("Package 2: " + pkg2.getName() + " - Price: " + pkg2.getPrice());
            }
            
            if (packages.size() >= 3) {
                Package pkg3 = packages.get(2);
                jumlahOrangIsiLabel4.setText(pkg3.getMinPerson() + "-" + pkg3.getMaxPerson() + " orang");
                durasiSesiIsiLabel4.setText(pkg3.getDuration() + " menit");
                
                int price3 = (int) pkg3.getPrice();
                hargaIsiLabel4.setText("Rp" + String.format("%,d", price3) + "/orang");
                
                fasilitiasIsi1Label4.setText("2 background");
                fasilitasIsi2Label4.setText("Foto hasil jadi per orang");
                fasilitasIsi3Label4.setText("Editing sepuasnya");
                
                System.out.println("Package 3: " + pkg3.getName() + " - Price: " + pkg3.getPrice());
            }
            
        } catch (Exception e) {
            System.out.println("ERROR in updatePackageDisplay: " + e.getMessage());
            e.printStackTrace();
            
            if (packages.size() >= 1) {
                Package pkg1 = packages.get(0);
                hargaIsiLabel.setText("Rp" + pkg1.getPrice() + "/orang");
            }
            if (packages.size() >= 2) {
                Package pkg2 = packages.get(1);
                hargaIsiLabel1.setText("Rp" + pkg2.getPrice() + "/orang");
            }
            if (packages.size() >= 3) {
                Package pkg3 = packages.get(2);
                hargaIsiLabel4.setText("Rp" + pkg3.getPrice() + "/orang");
            }
        }
        
        System.out.println("=== DEBUG: UI update completed ===");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        paketPanel1 = new javax.swing.JPanel();
        paketButton = new javax.swing.JButton();
        jumlahOrangLabel = new javax.swing.JLabel();
        jumlahOrangIsiLabel = new javax.swing.JLabel();
        durasiSesiLabel = new javax.swing.JLabel();
        durasiSesiIsiLabel = new javax.swing.JLabel();
        hargaLabel = new javax.swing.JLabel();
        hargaIsiLabel = new javax.swing.JLabel();
        cocokUntukLabel = new javax.swing.JLabel();
        cocokUntukIsiLabel1 = new javax.swing.JLabel();
        cocokUntukIsi2Label = new javax.swing.JLabel();
        cocokUntukIsi3Label = new javax.swing.JLabel();
        fasilitasLabel = new javax.swing.JLabel();
        fasilitiasIsi1Label = new javax.swing.JLabel();
        fasilitasIsi2Label = new javax.swing.JLabel();
        fasilitasIsi3Label = new javax.swing.JLabel();
        title1 = new javax.swing.JLabel();
        paketPanel2 = new javax.swing.JPanel();
        paketButton1 = new javax.swing.JButton();
        jumlahOrangLabel1 = new javax.swing.JLabel();
        jumlahOrangIsiLabel1 = new javax.swing.JLabel();
        durasiSesiLabel1 = new javax.swing.JLabel();
        durasiSesiIsiLabel1 = new javax.swing.JLabel();
        hargaLabel1 = new javax.swing.JLabel();
        hargaIsiLabel1 = new javax.swing.JLabel();
        cocokUntukLabel1 = new javax.swing.JLabel();
        cocokUntukIsiLabel2 = new javax.swing.JLabel();
        cocokUntukIsi2Label1 = new javax.swing.JLabel();
        cocokUntukIsi3Label1 = new javax.swing.JLabel();
        fasilitasLabel1 = new javax.swing.JLabel();
        fasilitiasIsi1Label1 = new javax.swing.JLabel();
        fasilitasIsi2Label1 = new javax.swing.JLabel();
        fasilitasIsi3Label1 = new javax.swing.JLabel();
        paketPanel5 = new javax.swing.JPanel();
        paketButton4 = new javax.swing.JButton();
        jumlahOrangLabel4 = new javax.swing.JLabel();
        jumlahOrangIsiLabel4 = new javax.swing.JLabel();
        durasiSesiLabel4 = new javax.swing.JLabel();
        durasiSesiIsiLabel4 = new javax.swing.JLabel();
        hargaLabel4 = new javax.swing.JLabel();
        hargaIsiLabel4 = new javax.swing.JLabel();
        cocokUntukLabel4 = new javax.swing.JLabel();
        cocokUntukIsiLabel3 = new javax.swing.JLabel();
        cocokUntukIsi2Label4 = new javax.swing.JLabel();
        cocokUntukIsi3Label4 = new javax.swing.JLabel();
        fasilitasLabel4 = new javax.swing.JLabel();
        fasilitiasIsi1Label4 = new javax.swing.JLabel();
        fasilitasIsi2Label4 = new javax.swing.JLabel();
        fasilitasIsi3Label4 = new javax.swing.JLabel();
        navbarPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        bodyPanel.setBackground(new java.awt.Color(255, 255, 255));

        title.setFont(new java.awt.Font("Poppins Medium", 0, 24)); // NOI18N
        title.setText("Pilih Paket");

        paketButton.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        paketButton.setText("Paket 1");
        paketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paketButtonActionPerformed(evt);
            }
        });

        jumlahOrangLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jumlahOrangLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangLabel.setText("Jumlah orang:");

        jumlahOrangIsiLabel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jumlahOrangIsiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangIsiLabel.setText("1-10 orang");

        durasiSesiLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        durasiSesiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiLabel.setText("Durasi sesi:");

        durasiSesiIsiLabel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        durasiSesiIsiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiIsiLabel.setText("30 menit");

        hargaLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        hargaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaLabel.setText("Harga:");

        hargaIsiLabel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        hargaIsiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaIsiLabel.setText("Rp20.000/orang");

        cocokUntukLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        cocokUntukLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukLabel.setText("Cocok untuk:");

        cocokUntukIsiLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsiLabel1.setText("Foto personal");

        cocokUntukIsi2Label.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi2Label.setText("Pasangan");

        cocokUntukIsi3Label.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi3Label.setText("Keluarga kecil");

        fasilitasLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        fasilitasLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasLabel.setText("Fasilitas");

        fasilitiasIsi1Label.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitiasIsi1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitiasIsi1Label.setText("1 background");

        fasilitasIsi2Label.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi2Label.setText("Foto hasil jadi per orang");

        fasilitasIsi3Label.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi3Label.setText("Editing basic");

        javax.swing.GroupLayout paketPanel1Layout = new javax.swing.GroupLayout(paketPanel1);
        paketPanel1.setLayout(paketPanel1Layout);
        paketPanel1Layout.setHorizontalGroup(
            paketPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(paketPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jumlahOrangIsiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiIsiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsiLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaIsiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi2Label, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(fasilitiasIsi1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jumlahOrangLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paketButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paketPanel1Layout.setVerticalGroup(
            paketPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jumlahOrangLabel)
                .addGap(0, 0, 0)
                .addComponent(jumlahOrangIsiLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(durasiSesiLabel)
                .addGap(0, 0, 0)
                .addComponent(durasiSesiIsiLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cocokUntukLabel)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsiLabel1)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi2Label)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi3Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hargaLabel)
                .addGap(0, 0, 0)
                .addComponent(hargaIsiLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fasilitasLabel)
                .addGap(0, 0, 0)
                .addComponent(fasilitiasIsi1Label)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi2Label)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi3Label)
                .addGap(18, 18, 18)
                .addComponent(paketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        title1.setFont(new java.awt.Font("Retro Majestic Free", 0, 18)); // NOI18N
        title1.setText("Bonas Studio");

        paketButton1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        paketButton1.setText("Paket 2");
        paketButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paketButton1ActionPerformed(evt);
            }
        });

        jumlahOrangLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jumlahOrangLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangLabel1.setText("Jumlah orang:");

        jumlahOrangIsiLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jumlahOrangIsiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangIsiLabel1.setText("11-30 orang");

        durasiSesiLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        durasiSesiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiLabel1.setText("Durasi sesi:");

        durasiSesiIsiLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        durasiSesiIsiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiIsiLabel1.setText("30 menit");

        hargaLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        hargaLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaLabel1.setText("Harga:");

        hargaIsiLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        hargaIsiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaIsiLabel1.setText("Rp25.000/orang");

        cocokUntukLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        cocokUntukLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukLabel1.setText("Cocok untuk:");

        cocokUntukIsiLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsiLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsiLabel2.setText("Foto personal");

        cocokUntukIsi2Label1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi2Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi2Label1.setText("Pasangan");

        cocokUntukIsi3Label1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi3Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi3Label1.setText("Keluarga kecil");

        fasilitasLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        fasilitasLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasLabel1.setText("Fasilitas");

        fasilitiasIsi1Label1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitiasIsi1Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitiasIsi1Label1.setText("1 background");

        fasilitasIsi2Label1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi2Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi2Label1.setText("Foto hasil jadi per orang");

        fasilitasIsi3Label1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi3Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi3Label1.setText("Editing basic");

        javax.swing.GroupLayout paketPanel2Layout = new javax.swing.GroupLayout(paketPanel2);
        paketPanel2.setLayout(paketPanel2Layout);
        paketPanel2Layout.setHorizontalGroup(
            paketPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(paketPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jumlahOrangIsiLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiIsiLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsiLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi2Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi3Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaIsiLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi3Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi2Label1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(fasilitiasIsi1Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jumlahOrangLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paketButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paketPanel2Layout.setVerticalGroup(
            paketPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jumlahOrangLabel1)
                .addGap(0, 0, 0)
                .addComponent(jumlahOrangIsiLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(durasiSesiLabel1)
                .addGap(0, 0, 0)
                .addComponent(durasiSesiIsiLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cocokUntukLabel1)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsiLabel2)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi2Label1)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi3Label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hargaLabel1)
                .addGap(0, 0, 0)
                .addComponent(hargaIsiLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fasilitasLabel1)
                .addGap(0, 0, 0)
                .addComponent(fasilitiasIsi1Label1)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi2Label1)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi3Label1)
                .addGap(18, 18, 18)
                .addComponent(paketButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paketButton4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        paketButton4.setText("Paket 3");
        paketButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paketButton4ActionPerformed(evt);
            }
        });

        jumlahOrangLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jumlahOrangLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangLabel4.setText("Jumlah orang:");

        jumlahOrangIsiLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jumlahOrangIsiLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jumlahOrangIsiLabel4.setText("31-50 orang");

        durasiSesiLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        durasiSesiLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiLabel4.setText("Durasi sesi:");

        durasiSesiIsiLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        durasiSesiIsiLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durasiSesiIsiLabel4.setText("60 menit");

        hargaLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        hargaLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaLabel4.setText("Harga:");

        hargaIsiLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        hargaIsiLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hargaIsiLabel4.setText("Rp30.000/orang");

        cocokUntukLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        cocokUntukLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukLabel4.setText("Cocok untuk:");

        cocokUntukIsiLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsiLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsiLabel3.setText("Foto personal");

        cocokUntukIsi2Label4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi2Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi2Label4.setText("Pasangan");

        cocokUntukIsi3Label4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cocokUntukIsi3Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cocokUntukIsi3Label4.setText("Keluarga kecil");

        fasilitasLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        fasilitasLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasLabel4.setText("Fasilitas");

        fasilitiasIsi1Label4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitiasIsi1Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitiasIsi1Label4.setText("2 background");

        fasilitasIsi2Label4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi2Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi2Label4.setText("Foto hasil jadi per orang");

        fasilitasIsi3Label4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        fasilitasIsi3Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fasilitasIsi3Label4.setText("Editing sepuasnya");

        javax.swing.GroupLayout paketPanel5Layout = new javax.swing.GroupLayout(paketPanel5);
        paketPanel5.setLayout(paketPanel5Layout);
        paketPanel5Layout.setHorizontalGroup(
            paketPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(paketPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jumlahOrangIsiLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durasiSesiIsiLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsiLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi2Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cocokUntukIsi3Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hargaIsiLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi3Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fasilitasIsi2Label4, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(fasilitiasIsi1Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jumlahOrangLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paketButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paketPanel5Layout.setVerticalGroup(
            paketPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paketPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jumlahOrangLabel4)
                .addGap(0, 0, 0)
                .addComponent(jumlahOrangIsiLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(durasiSesiLabel4)
                .addGap(0, 0, 0)
                .addComponent(durasiSesiIsiLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cocokUntukLabel4)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsiLabel3)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi2Label4)
                .addGap(0, 0, 0)
                .addComponent(cocokUntukIsi3Label4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hargaLabel4)
                .addGap(0, 0, 0)
                .addComponent(hargaIsiLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fasilitasLabel4)
                .addGap(0, 0, 0)
                .addComponent(fasilitiasIsi1Label4)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi2Label4)
                .addGap(0, 0, 0)
                .addComponent(fasilitasIsi3Label4)
                .addGap(18, 18, 18)
                .addComponent(paketButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title1)
                            .addComponent(title))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(paketPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(paketPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(paketPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(title1)
                .addGap(0, 0, 0)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paketPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paketPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paketPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
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

    private void openBuatReservasi(Package selectedPackage) {
        new BuatReservasi(selectedPackage).setVisible(true);
        this.dispose();
    }
    
    private void paketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paketButtonActionPerformed
        if (packages.size() >= 1) {
            Package selectedPackage = packages.get(0);
            openBuatReservasi(selectedPackage);
        } else {
            JOptionPane.showMessageDialog(this, "Paket tidak tersedia!");
        }
    }//GEN-LAST:event_paketButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        new ui.Homepage.HomepageUser().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void paketButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paketButton1ActionPerformed
        if (packages.size() >= 2) {
            Package selectedPackage = packages.get(1);
            openBuatReservasi(selectedPackage);
        } else {
            JOptionPane.showMessageDialog(this, "Paket tidak tersedia!");
        }
    }//GEN-LAST:event_paketButton1ActionPerformed

    private void paketButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paketButton4ActionPerformed
        if (packages.size() >= 3) {
            Package selectedPackage = packages.get(2);
            openBuatReservasi(selectedPackage);
        } else {
            JOptionPane.showMessageDialog(this, "Paket tidak tersedia!");
        }
    }//GEN-LAST:event_paketButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PilihPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PilihPaket().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel cocokUntukIsi1Label2;
    private javax.swing.JLabel cocokUntukIsi1Label3;
    private javax.swing.JLabel cocokUntukIsi2Label;
    private javax.swing.JLabel cocokUntukIsi2Label1;
    private javax.swing.JLabel cocokUntukIsi2Label2;
    private javax.swing.JLabel cocokUntukIsi2Label3;
    private javax.swing.JLabel cocokUntukIsi2Label4;
    private javax.swing.JLabel cocokUntukIsi3Label;
    private javax.swing.JLabel cocokUntukIsi3Label1;
    private javax.swing.JLabel cocokUntukIsi3Label2;
    private javax.swing.JLabel cocokUntukIsi3Label3;
    private javax.swing.JLabel cocokUntukIsi3Label4;
    private javax.swing.JLabel cocokUntukIsiLabel1;
    private javax.swing.JLabel cocokUntukIsiLabel2;
    private javax.swing.JLabel cocokUntukIsiLabel3;
    private javax.swing.JLabel cocokUntukLabel;
    private javax.swing.JLabel cocokUntukLabel1;
    private javax.swing.JLabel cocokUntukLabel2;
    private javax.swing.JLabel cocokUntukLabel3;
    private javax.swing.JLabel cocokUntukLabel4;
    private javax.swing.JLabel durasiSesiIsiLabel;
    private javax.swing.JLabel durasiSesiIsiLabel1;
    private javax.swing.JLabel durasiSesiIsiLabel2;
    private javax.swing.JLabel durasiSesiIsiLabel3;
    private javax.swing.JLabel durasiSesiIsiLabel4;
    private javax.swing.JLabel durasiSesiLabel;
    private javax.swing.JLabel durasiSesiLabel1;
    private javax.swing.JLabel durasiSesiLabel2;
    private javax.swing.JLabel durasiSesiLabel3;
    private javax.swing.JLabel durasiSesiLabel4;
    private javax.swing.JLabel fasilitasIsi2Label;
    private javax.swing.JLabel fasilitasIsi2Label1;
    private javax.swing.JLabel fasilitasIsi2Label2;
    private javax.swing.JLabel fasilitasIsi2Label3;
    private javax.swing.JLabel fasilitasIsi2Label4;
    private javax.swing.JLabel fasilitasIsi3Label;
    private javax.swing.JLabel fasilitasIsi3Label1;
    private javax.swing.JLabel fasilitasIsi3Label2;
    private javax.swing.JLabel fasilitasIsi3Label3;
    private javax.swing.JLabel fasilitasIsi3Label4;
    private javax.swing.JLabel fasilitasLabel;
    private javax.swing.JLabel fasilitasLabel1;
    private javax.swing.JLabel fasilitasLabel2;
    private javax.swing.JLabel fasilitasLabel3;
    private javax.swing.JLabel fasilitasLabel4;
    private javax.swing.JLabel fasilitiasIsi1Label;
    private javax.swing.JLabel fasilitiasIsi1Label1;
    private javax.swing.JLabel fasilitiasIsi1Label2;
    private javax.swing.JLabel fasilitiasIsi1Label3;
    private javax.swing.JLabel fasilitiasIsi1Label4;
    private javax.swing.JLabel hargaIsiLabel;
    private javax.swing.JLabel hargaIsiLabel1;
    private javax.swing.JLabel hargaIsiLabel2;
    private javax.swing.JLabel hargaIsiLabel3;
    private javax.swing.JLabel hargaIsiLabel4;
    private javax.swing.JLabel hargaLabel;
    private javax.swing.JLabel hargaLabel1;
    private javax.swing.JLabel hargaLabel2;
    private javax.swing.JLabel hargaLabel3;
    private javax.swing.JLabel hargaLabel4;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jumlahOrangIsiLabel;
    private javax.swing.JLabel jumlahOrangIsiLabel1;
    private javax.swing.JLabel jumlahOrangIsiLabel2;
    private javax.swing.JLabel jumlahOrangIsiLabel3;
    private javax.swing.JLabel jumlahOrangIsiLabel4;
    private javax.swing.JLabel jumlahOrangLabel;
    private javax.swing.JLabel jumlahOrangLabel1;
    private javax.swing.JLabel jumlahOrangLabel2;
    private javax.swing.JLabel jumlahOrangLabel3;
    private javax.swing.JLabel jumlahOrangLabel4;
    private javax.swing.JPanel navbarPanel;
    private javax.swing.JPanel paket1panel1;
    private javax.swing.JPanel paket1panel2;
    private javax.swing.JPanel paket1panel3;
    private javax.swing.JButton paketButton;
    private javax.swing.JButton paketButton1;
    private javax.swing.JButton paketButton2;
    private javax.swing.JButton paketButton3;
    private javax.swing.JButton paketButton4;
    private javax.swing.JPanel paketPanel1;
    private javax.swing.JPanel paketPanel2;
    private javax.swing.JPanel paketPanel3;
    private javax.swing.JPanel paketPanel4;
    private javax.swing.JPanel paketPanel5;
    private javax.swing.JLabel title;
    private javax.swing.JLabel title1;
    // End of variables declaration//GEN-END:variables
}
