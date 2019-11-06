/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentieturism;

import static agentieturism.ConexiuneBD.getConnection;
import static agentieturism.PrimaPagina.agentieLabel;
import static agentieturism.PrimaPagina.campUser;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dm181
 */
public class PaginaTicketing extends javax.swing.JFrame {

    /**
     * Creates new form PaginaTicketing
     */
    public PaginaTicketing() {
        initComponents();
        setPanelVisible(pagPrincTicket);
        afiseazaZboruri();
        afiseazaFurnizori();
        afiseazaContracte();
        fillFurnizori();
        fillComboAn();
        setDateCont();
        afiseazaBilete();
        fillComboDestinatie();
        fillAngajatContract();
        
    }
    private boolean anBisect(int an){
        return ((an%4==0) && (an%100!=0))|| an%400==0;
    }
    private void setPanelVisible(JPanel panel){
        ArrayList<JPanel> jpaneluri = new ArrayList<>();
        jpaneluri.add(pagPrincTicket);
        jpaneluri.add(vizualizareZbor);
        jpaneluri.add(vizualizareFurnizori);
        jpaneluri.add(vizualizareContracte);
        jpaneluri.add(adaugareContract);
        jpaneluri.add(cautareZboruri);
        jpaneluri.add(contulMeu);
        jpaneluri.add(vizualizareBilete);
        jpaneluri.add(rezervariBilete);
        pagPrincTicket.setVisible(false);
        vizualizareZbor.setVisible(false);
        vizualizareFurnizori.setVisible(false);
        vizualizareContracte.setVisible(false);
        adaugareContract.setVisible(false);
        cautareZboruri.setVisible(false);
        contulMeu.setVisible(false);
        vizualizareBilete.setVisible(false);
        rezervariBilete.setVisible(false);
        for(JPanel i : jpaneluri){
            if(i==panel){
                panel.setVisible(true);
            }
        }
    }
    private void afiseazaZboruri(){
        try{
            DefaultTableModel model = (DefaultTableModel) tabelZboruri.getModel();
            Connection conn = getConnection();
            String sql = "SELECT * FROM zbor";
            String transport = "SELECT den_avion FROM transport, zbor WHERE transport.cod_transport = zbor.cod_transport";
            PreparedStatement stmt1 = conn.prepareStatement(transport);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ResultSet rs1 = stmt1.executeQuery();
            while(rs.next() && rs1.next()){
                model.addRow(new Object[]{""  + rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),  rs.getString(6),rs1.getString(1)});
            }
            
        }catch(SQLException e){
           JOptionPane.showMessageDialog(this,"Nu se pot accesa zborurile!");           
        }
    }
    private void afiseazaFurnizori(){
        try{
            DefaultTableModel model = (DefaultTableModel) tabelFurnizori.getModel();
            Connection conn = getConnection();
            String sql = "SELECT * FROM furnizori";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{""  + rs.getInt(1), rs.getString(2)});
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot accesa furnizorii!");
        }
    }
    private void fillAngajatContract(){
        try{
            Connection con = getConnection();
            String sql = "SELECT nume,prenume FROM angajat WHERE cont=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,campUser.getText());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                numeAngajat.setText(rs.getString(1) + " " + rs.getString(2));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot accesa angajatii!");
        }
    }
    private void afiseazaContracte(){
        try{
            DefaultTableModel model = (DefaultTableModel) tabelContract.getModel();
            Connection con = getConnection();
            String contract = "SELECT nrcontract, data_incheierii FROM contract";
            PreparedStatement stmt1 = con.prepareStatement(contract);
            ResultSet rs1 = stmt1.executeQuery();
            String angajat = "SELECT nume,prenume FROM angajat, contract WHERE angajat.cod_angajat = contract.cod_angajat";
            PreparedStatement stmt2 = con.prepareStatement(angajat);
            ResultSet rs2 = stmt2.executeQuery();
            String furnizor = "SELECT firma FROM furnizori, contract WHERE furnizori.cod_furnizor=contract.cod_furnizor";
            PreparedStatement stmt3 = con.prepareStatement(furnizor);
            ResultSet rs3 = stmt3.executeQuery();
            while(rs1.next() && rs2.next() && rs3.next()){
                model.addRow(new Object[]{""  + rs1.getInt(1), rs2.getString(1)+" "+rs2.getString(2),rs3.getString(1),rs1.getString(2)});
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot accesa contractele!");
        }        
    }
    private void fillFurnizori() {
        try{
            Connection conn = getConnection();
            String sql = "SELECT * FROM furnizori";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String test = rs.getString("Firma");
                furnizori.addItem(test);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot accesa furnizorii!");
        }
    }
    
     private void fillComboAn(){
        for(int i=2019;i>=1990;i--)
            anul.addItem(Integer.toString(i));
        for(int i=1;i<=12;i++)
            luna.addItem(Integer.toString(i));

    } 
     private void setDateCont(){
       try{
           Connection conn = getConnection();
           String getDateAdmin = "SELECT * FROM angajat WHERE cont=?";
           PreparedStatement ps = conn.prepareStatement(getDateAdmin);
           ps.setString(1,campUser.getText());
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               numeCont.setText(rs.getString("Nume"));
               prenumeCont.setText(rs.getString("Prenume"));
               telefonCont.setText(rs.getString("Telefon"));
               emailCont.setText(rs.getString("Email"));
               contCont.setText(rs.getString("Cont"));
               parolaCont.setText(rs.getString("Parola"));
           }
       }catch(SQLException e){
           System.out.println(e.getMessage());
           JOptionPane.showMessageDialog(this,"Nu se poate accesa contul!");
       }
    }
     private void afiseazaBilete(){
         try{
            DefaultTableModel model = (DefaultTableModel) tabelBilete.getModel();
            Connection con = getConnection();
             String sql1 = "SELECT nume,prenume, destinatie, data_plecarii, ora_plecarii,bilet.pret,data_achizitionarii FROM zbor, client, bilet WHERE zbor.cod_zbor=bilet.cod_zbor and client.cod_client=bilet.cod_client";
                PreparedStatement pstm1 = con.prepareStatement(sql1);                
                ResultSet rs1 = pstm1.executeQuery();
                
                while(rs1.next()){
                  model.addRow(new Object[]{""  + rs1.getString(1)+" "+rs1.getString(2), rs1.getString(3),rs1.getString(4),rs1.getInt(5),rs1.getInt(6),rs1.getString(7)});
                  
                }
         }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot afisa biletele!");
            e.printStackTrace();
         }
     }
     private void fillComboDestinatie(){
         try{
             Connection con = getConnection();
             String sql = "SELECT DISTINCT(destinatie) FROM zbor";
             PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();
             while(rs.next()){
                 comboDestinatie.addItem(rs.getString(1));
             }
         } catch (SQLException ex) {
              JOptionPane.showMessageDialog(this,"Nu se pot afisa destinatiile!");
        }
     }
     
     private void fillComboClientiFideli(){
         try{
             Connection con = getConnection();
             String sql = "SELECT nume, prenume FROM client";
             PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();
             while(rs.next()){
                 clientiFideli.addItem(rs.getString(1) + " " + rs.getString(2));
             }             
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot afisa clientii!");           
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

        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        pagPrincTicket = new javax.swing.JPanel();
        titluTicketing = new javax.swing.JLabel();
        agentieTicketing = new javax.swing.JLabel();
        butonIesire = new javax.swing.JButton();
        butonFurnizori = new javax.swing.JButton();
        butonZboruri = new javax.swing.JButton();
        butonContracte = new javax.swing.JButton();
        butonBilete = new javax.swing.JButton();
        butonCont = new javax.swing.JButton();
        logoTicket = new javax.swing.JLabel();
        bgTicketing = new javax.swing.JLabel();
        vizualizareZbor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelZboruri = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        vizualizareFurnizori = new javax.swing.JPanel();
        titluFurnizori = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelFurnizori = new javax.swing.JTable();
        inapoiPagAdmin = new javax.swing.JButton();
        butonAdaugaContract = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        vizualizareContracte = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelContract = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        deschideContract = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        contractScris = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        adaugareContract = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        labelData = new javax.swing.JLabel();
        labelNumeAngajat = new javax.swing.JLabel();
        labelFurnizor = new javax.swing.JLabel();
        numeAngajat = new javax.swing.JTextField();
        butonContractAdauga = new javax.swing.JButton();
        furnizori = new javax.swing.JComboBox<String>();
        jScrollPane5 = new javax.swing.JScrollPane();
        contractA4 = new javax.swing.JTextArea();
        anul = new javax.swing.JComboBox<String>();
        luna = new javax.swing.JComboBox<String>();
        ziua = new javax.swing.JComboBox<String>();
        salvareContract = new javax.swing.JButton();
        butonInapoiContract = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cautareZboruri = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Destinatie = new javax.swing.JRadioButton();
        dataPlecareRadio = new javax.swing.JRadioButton();
        oraPlecareButon = new javax.swing.JRadioButton();
        oraSosireButon = new javax.swing.JRadioButton();
        fieldFiltrare = new javax.swing.JTextField();
        butonCautaFiltrare = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelZboruri1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        contulMeu = new javax.swing.JPanel();
        titluContulMeu = new javax.swing.JLabel();
        imgProfilAng = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        parolaCont = new javax.swing.JTextField();
        numeCont = new javax.swing.JTextField();
        prenumeCont = new javax.swing.JTextField();
        emailCont = new javax.swing.JTextField();
        telefonCont = new javax.swing.JTextField();
        contCont = new javax.swing.JTextField();
        butonInapoiCont = new javax.swing.JButton();
        butonSalveaza = new javax.swing.JButton();
        vizualizareBilete = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelBilete = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        radioClient = new javax.swing.JRadioButton();
        radioDestinatie = new javax.swing.JRadioButton();
        radioPret = new javax.swing.JRadioButton();
        radioData = new javax.swing.JRadioButton();
        fieldFiltrareBilete = new javax.swing.JTextField();
        butonCautaBilet = new javax.swing.JButton();
        butonInapoiClientBilet = new javax.swing.JButton();
        rezervariBilete = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        butonRezervaBilet = new javax.swing.JButton();
        butonInapoiBilete = new javax.swing.JButton();
        clientNou = new javax.swing.JRadioButton();
        clientFidel = new javax.swing.JRadioButton();
        comboDestinatie = new javax.swing.JComboBox<String>();
        comboDataPlecarii = new javax.swing.JComboBox<String>();
        numeClient = new javax.swing.JTextField();
        prenumeClient = new javax.swing.JTextField();
        telefonClient = new javax.swing.JTextField();
        emailClient = new javax.swing.JTextField();
        parolaClient = new javax.swing.JTextField();
        contClient = new javax.swing.JTextField();
        adaugaClientButon = new javax.swing.JButton();
        pretBilet = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        clientiFideli = new javax.swing.JComboBox<String>();
        oraPlecarii = new javax.swing.JComboBox<String>();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        vizMeniuTicketing = new javax.swing.JMenu();
        zboruriItem = new javax.swing.JMenuItem();
        furnizoriVizItem = new javax.swing.JMenuItem();
        contracteVizMeniu = new javax.swing.JMenuItem();
        adaugareMeniuTicketing = new javax.swing.JMenu();
        adaugContractMeniu = new javax.swing.JMenuItem();
        cautareMeniuTicketing = new javax.swing.JMenu();
        zboruriCautMeniu = new javax.swing.JMenuItem();
        bileteCautMeniu = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jMenuItem6.setText("jMenuItem6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 530));

        pagPrincTicket.setLayout(null);

        titluTicketing.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        titluTicketing.setText("PAGINA TICKETING");
        pagPrincTicket.add(titluTicketing);
        titluTicketing.setBounds(280, 40, 170, 20);

        agentieTicketing.setText(agentieLabel.toString());
        agentieTicketing.setBackground(new java.awt.Color(255, 255, 255));
        agentieTicketing.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        pagPrincTicket.add(agentieTicketing);
        agentieTicketing.setBounds(280, 80, 220, 30);

        butonIesire.setBackground(new java.awt.Color(0, 102, 102));
        butonIesire.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonIesire.setForeground(new java.awt.Color(255, 255, 255));
        butonIesire.setText("IESIRE");
        butonIesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonIesireActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonIesire);
        butonIesire.setBounds(540, 400, 120, 40);

        butonFurnizori.setBackground(new java.awt.Color(0, 51, 51));
        butonFurnizori.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonFurnizori.setForeground(new java.awt.Color(255, 255, 255));
        butonFurnizori.setText("FURNIZORI");
        butonFurnizori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonFurnizoriActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonFurnizori);
        butonFurnizori.setBounds(40, 250, 120, 40);

        butonZboruri.setBackground(new java.awt.Color(0, 51, 51));
        butonZboruri.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonZboruri.setForeground(new java.awt.Color(255, 255, 255));
        butonZboruri.setText("ZBORURI");
        butonZboruri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonZboruriActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonZboruri);
        butonZboruri.setBounds(40, 170, 120, 40);

        butonContracte.setBackground(new java.awt.Color(0, 51, 51));
        butonContracte.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonContracte.setForeground(new java.awt.Color(255, 255, 255));
        butonContracte.setText("CONTRACTE");
        butonContracte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonContracteActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonContracte);
        butonContracte.setBounds(220, 250, 120, 40);

        butonBilete.setBackground(new java.awt.Color(0, 51, 51));
        butonBilete.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonBilete.setForeground(new java.awt.Color(255, 255, 255));
        butonBilete.setText("BILETE");
        butonBilete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonBileteActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonBilete);
        butonBilete.setBounds(220, 170, 120, 40);

        butonCont.setBackground(new java.awt.Color(0, 51, 51));
        butonCont.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        butonCont.setForeground(new java.awt.Color(255, 255, 255));
        butonCont.setText("CONTUL MEU");
        butonCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonContActionPerformed(evt);
            }
        });
        pagPrincTicket.add(butonCont);
        butonCont.setBounds(120, 350, 120, 40);

        logoTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/logo.jpg"))); // NOI18N
        pagPrincTicket.add(logoTicket);
        logoTicket.setBounds(0, 0, 100, 100);

        bgTicketing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgTicketing.jpg"))); // NOI18N
        pagPrincTicket.add(bgTicketing);
        bgTicketing.setBounds(0, 0, 700, 480);

        vizualizareZbor.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel1.setText("ZBORURI");
        vizualizareZbor.add(jLabel1);
        jLabel1.setBounds(290, 50, 90, 22);

        tabelZboruri.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tabelZboruri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Destinatie", "Data plecare", "Data sosire", "Ora plecare", "Ora sosire", "Avion"
            }
        ));
        jScrollPane1.setViewportView(tabelZboruri);

        vizualizareZbor.add(jScrollPane1);
        jScrollPane1.setBounds(0, 160, 700, 140);

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Inapoi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        vizualizareZbor.add(jButton1);
        jButton1.setBounds(500, 370, 90, 40);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        vizualizareZbor.add(jLabel2);
        jLabel2.setBounds(0, 0, 700, 480);

        vizualizareFurnizori.setLayout(null);

        titluFurnizori.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        titluFurnizori.setText("FURNIZORI TRANSPORT");
        vizualizareFurnizori.add(titluFurnizori);
        titluFurnizori.setBounds(230, 60, 230, 22);

        tabelFurnizori.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tabelFurnizori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod Furnizor", "Firma"
            }
        ));
        jScrollPane2.setViewportView(tabelFurnizori);

        vizualizareFurnizori.add(jScrollPane2);
        jScrollPane2.setBounds(120, 150, 452, 100);

        inapoiPagAdmin.setBackground(new java.awt.Color(0, 102, 102));
        inapoiPagAdmin.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        inapoiPagAdmin.setForeground(new java.awt.Color(255, 255, 255));
        inapoiPagAdmin.setText("Inapoi");
        inapoiPagAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inapoiPagAdminActionPerformed(evt);
            }
        });
        vizualizareFurnizori.add(inapoiPagAdmin);
        inapoiPagAdmin.setBounds(480, 340, 120, 40);

        butonAdaugaContract.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugaContract.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugaContract.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugaContract.setText("Adăugare Contract");
        butonAdaugaContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugaContractActionPerformed(evt);
            }
        });
        vizualizareFurnizori.add(butonAdaugaContract);
        butonAdaugaContract.setBounds(70, 340, 170, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        vizualizareFurnizori.add(jLabel3);
        jLabel3.setBounds(0, 0, 700, 480);

        vizualizareContracte.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel4.setText("CONTRACTE");
        vizualizareContracte.add(jLabel4);
        jLabel4.setBounds(130, 80, 120, 22);

        tabelContract.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tabelContract.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nr.", "Angajat", "Furnizor", "Data"
            }
        ));
        jScrollPane3.setViewportView(tabelContract);
        if (tabelContract.getColumnModel().getColumnCount() > 0) {
            tabelContract.getColumnModel().getColumn(0).setPreferredWidth(25);
        }

        vizualizareContracte.add(jScrollPane3);
        jScrollPane3.setBounds(0, 140, 380, 90);

        jButton2.setBackground(new java.awt.Color(0, 51, 102));
        jButton2.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Inapoi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        vizualizareContracte.add(jButton2);
        jButton2.setBounds(490, 430, 100, 40);

        deschideContract.setBackground(new java.awt.Color(0, 51, 102));
        deschideContract.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        deschideContract.setForeground(new java.awt.Color(255, 255, 255));
        deschideContract.setText("Deschide");
        deschideContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deschideContractActionPerformed(evt);
            }
        });
        vizualizareContracte.add(deschideContract);
        deschideContract.setBounds(130, 270, 120, 25);

        contractScris.setColumns(20);
        contractScris.setRows(5);
        jScrollPane4.setViewportView(contractScris);

        vizualizareContracte.add(jScrollPane4);
        jScrollPane4.setBounds(380, 50, 310, 360);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgC.png"))); // NOI18N
        vizualizareContracte.add(jLabel8);
        jLabel8.setBounds(0, -10, 710, 510);

        adaugareContract.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel5.setText("CONTRACT NOU");
        adaugareContract.add(jLabel5);
        jLabel5.setBounds(89, 38, 138, 20);

        labelData.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        labelData.setText("Data");
        adaugareContract.add(labelData);
        labelData.setBounds(20, 190, 40, 20);

        labelNumeAngajat.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        labelNumeAngajat.setText("Nume angajat");
        adaugareContract.add(labelNumeAngajat);
        labelNumeAngajat.setBounds(30, 90, 83, 15);

        labelFurnizor.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        labelFurnizor.setText("Furnizor");
        adaugareContract.add(labelFurnizor);
        labelFurnizor.setBounds(30, 140, 51, 15);

        numeAngajat.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        numeAngajat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeAngajatActionPerformed(evt);
            }
        });
        adaugareContract.add(numeAngajat);
        numeAngajat.setBounds(160, 90, 120, 21);

        butonContractAdauga.setBackground(new java.awt.Color(0, 102, 102));
        butonContractAdauga.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonContractAdauga.setForeground(new java.awt.Color(255, 255, 255));
        butonContractAdauga.setText("Adauga");
        butonContractAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonContractAdaugaActionPerformed(evt);
            }
        });
        adaugareContract.add(butonContractAdauga);
        butonContractAdauga.setBounds(200, 250, 92, 42);

        furnizori.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        adaugareContract.add(furnizori);
        furnizori.setBounds(160, 130, 120, 21);

        contractA4.setColumns(20);
        contractA4.setRows(5);
        jScrollPane5.setViewportView(contractA4);

        adaugareContract.add(jScrollPane5);
        jScrollPane5.setBounds(320, 20, 340, 400);

        anul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anulActionPerformed(evt);
            }
        });
        adaugareContract.add(anul);
        anul.setBounds(65, 190, 90, 20);

        luna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunaActionPerformed(evt);
            }
        });
        adaugareContract.add(luna);
        luna.setBounds(165, 190, 70, 20);

        adaugareContract.add(ziua);
        ziua.setBounds(250, 190, 60, 20);

        salvareContract.setBackground(new java.awt.Color(0, 102, 102));
        salvareContract.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        salvareContract.setForeground(new java.awt.Color(255, 255, 255));
        salvareContract.setText("Salveaza");
        salvareContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvareContractActionPerformed(evt);
            }
        });
        adaugareContract.add(salvareContract);
        salvareContract.setBounds(30, 250, 120, 40);

        butonInapoiContract.setBackground(new java.awt.Color(0, 102, 102));
        butonInapoiContract.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        butonInapoiContract.setForeground(new java.awt.Color(255, 255, 255));
        butonInapoiContract.setText("Inapoi");
        butonInapoiContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonInapoiContractActionPerformed(evt);
            }
        });
        adaugareContract.add(butonInapoiContract);
        butonInapoiContract.setBounds(120, 391, 100, 40);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        adaugareContract.add(jLabel6);
        jLabel6.setBounds(0, 0, 700, 480);

        cautareZboruri.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel7.setText("Filtrare zbor dupa");
        cautareZboruri.add(jLabel7);
        jLabel7.setBounds(50, 50, 180, 22);

        Destinatie.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        Destinatie.setText("Destinatie");
        cautareZboruri.add(Destinatie);
        Destinatie.setBounds(50, 90, 130, 25);

        dataPlecareRadio.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        dataPlecareRadio.setText("Data Plecare");
        cautareZboruri.add(dataPlecareRadio);
        dataPlecareRadio.setBounds(190, 90, 140, 25);

        oraPlecareButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraPlecareButon.setText("Ora Plecare");
        cautareZboruri.add(oraPlecareButon);
        oraPlecareButon.setBounds(350, 90, 140, 25);

        oraSosireButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraSosireButon.setText("Ora Sosire");
        oraSosireButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oraSosireButonActionPerformed(evt);
            }
        });
        cautareZboruri.add(oraSosireButon);
        oraSosireButon.setBounds(500, 90, 130, 25);

        fieldFiltrare.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        cautareZboruri.add(fieldFiltrare);
        fieldFiltrare.setBounds(150, 140, 200, 30);

        butonCautaFiltrare.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaFiltrare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaFiltrare.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaFiltrare.setText("Cauta");
        butonCautaFiltrare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaFiltrareActionPerformed(evt);
            }
        });
        cautareZboruri.add(butonCautaFiltrare);
        butonCautaFiltrare.setBounds(430, 140, 80, 25);

        tabelZboruri1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelZboruri1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Destinatie", "Data plecare", "Data sosire", "Ora plecare", "Ora sosire", "Avion"
            }
        ));
        jScrollPane6.setViewportView(tabelZboruri1);

        cautareZboruri.add(jScrollPane6);
        jScrollPane6.setBounds(0, 220, 700, 130);

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Inapoi");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        cautareZboruri.add(jButton5);
        jButton5.setBounds(497, 390, 90, 25);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClouds.jpg"))); // NOI18N
        cautareZboruri.add(jLabel9);
        jLabel9.setBounds(0, 0, 700, 510);

        contulMeu.setMinimumSize(new java.awt.Dimension(700, 480));
        contulMeu.setLayout(null);

        titluContulMeu.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        titluContulMeu.setText("CONTUL MEU");
        contulMeu.add(titluContulMeu);
        titluContulMeu.setBounds(290, 70, 120, 20);

        imgProfilAng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/imgProfil.png"))); // NOI18N
        contulMeu.add(imgProfilAng);
        imgProfilAng.setBounds(30, 20, 116, 120);

        jLabel11.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel11.setText("Nume");
        contulMeu.add(jLabel11);
        jLabel11.setBounds(50, 190, 60, 17);

        jLabel12.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel12.setText("Prenume");
        contulMeu.add(jLabel12);
        jLabel12.setBounds(40, 240, 80, 17);

        jLabel14.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel14.setText("Telefon");
        contulMeu.add(jLabel14);
        jLabel14.setBounds(50, 300, 60, 17);

        jLabel15.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel15.setText("Email");
        contulMeu.add(jLabel15);
        jLabel15.setBounds(360, 190, 70, 17);

        jLabel16.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel16.setText("Cont");
        contulMeu.add(jLabel16);
        jLabel16.setBounds(360, 240, 50, 17);

        jLabel17.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel17.setText("Parola");
        contulMeu.add(jLabel17);
        jLabel17.setBounds(360, 290, 60, 20);

        parolaCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parolaContActionPerformed(evt);
            }
        });
        contulMeu.add(parolaCont);
        parolaCont.setBounds(440, 290, 140, 20);

        numeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeContActionPerformed(evt);
            }
        });
        contulMeu.add(numeCont);
        numeCont.setBounds(130, 190, 140, 20);

        prenumeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenumeContActionPerformed(evt);
            }
        });
        contulMeu.add(prenumeCont);
        prenumeCont.setBounds(130, 240, 140, 20);

        emailCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailContActionPerformed(evt);
            }
        });
        contulMeu.add(emailCont);
        emailCont.setBounds(440, 190, 170, 20);

        telefonCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonContActionPerformed(evt);
            }
        });
        contulMeu.add(telefonCont);
        telefonCont.setBounds(130, 290, 140, 20);

        contCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contContActionPerformed(evt);
            }
        });
        contulMeu.add(contCont);
        contCont.setBounds(440, 240, 140, 20);

        butonInapoiCont.setBackground(new java.awt.Color(0, 102, 102));
        butonInapoiCont.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonInapoiCont.setForeground(new java.awt.Color(255, 255, 255));
        butonInapoiCont.setText("Inapoi");
        butonInapoiCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonInapoiContActionPerformed(evt);
            }
        });
        contulMeu.add(butonInapoiCont);
        butonInapoiCont.setBounds(440, 360, 120, 40);

        butonSalveaza.setBackground(new java.awt.Color(0, 102, 102));
        butonSalveaza.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonSalveaza.setForeground(new java.awt.Color(255, 255, 255));
        butonSalveaza.setText("Salveaza");
        butonSalveaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonSalveazaActionPerformed(evt);
            }
        });
        contulMeu.add(butonSalveaza);
        butonSalveaza.setBounds(130, 360, 120, 40);

        vizualizareBilete.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel10.setText("Vizualizare Clienti si Bilete");
        vizualizareBilete.add(jLabel10);
        jLabel10.setBounds(240, 50, 212, 20);

        tabelBilete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client", "Destinatie", "Data Plecarii", "Ora Plecarii", "Pret", "Data achizitionarii"
            }
        ));
        jScrollPane7.setViewportView(tabelBilete);
        if (tabelBilete.getColumnModel().getColumnCount() > 0) {
            tabelBilete.getColumnModel().getColumn(0).setPreferredWidth(120);
            tabelBilete.getColumnModel().getColumn(3).setPreferredWidth(25);
            tabelBilete.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        vizualizareBilete.add(jScrollPane7);
        jScrollPane7.setBounds(0, 110, 700, 130);

        jLabel13.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel13.setText("Filtrare");
        vizualizareBilete.add(jLabel13);
        jLabel13.setBounds(50, 250, 70, 17);

        radioClient.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioClient.setText("Client");
        vizualizareBilete.add(radioClient);
        radioClient.setBounds(40, 280, 90, 25);

        radioDestinatie.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioDestinatie.setText("Destinatie");
        vizualizareBilete.add(radioDestinatie);
        radioDestinatie.setBounds(180, 280, 120, 25);

        radioPret.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioPret.setText("Pret");
        vizualizareBilete.add(radioPret);
        radioPret.setBounds(340, 280, 90, 25);

        radioData.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioData.setText("Data achizitionarii");
        vizualizareBilete.add(radioData);
        radioData.setBounds(470, 280, 170, 25);

        fieldFiltrareBilete.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        vizualizareBilete.add(fieldFiltrareBilete);
        fieldFiltrareBilete.setBounds(240, 320, 180, 30);

        butonCautaBilet.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaBilet.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaBilet.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaBilet.setText("Cauta");
        butonCautaBilet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaBiletActionPerformed(evt);
            }
        });
        vizualizareBilete.add(butonCautaBilet);
        butonCautaBilet.setBounds(520, 370, 100, 40);

        butonInapoiClientBilet.setBackground(new java.awt.Color(0, 102, 102));
        butonInapoiClientBilet.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonInapoiClientBilet.setForeground(new java.awt.Color(255, 255, 255));
        butonInapoiClientBilet.setText("Inapoi");
        butonInapoiClientBilet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonInapoiClientBiletActionPerformed(evt);
            }
        });
        vizualizareBilete.add(butonInapoiClientBilet);
        butonInapoiClientBilet.setBounds(60, 370, 100, 40);

        rezervariBilete.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel18.setText("Client");
        rezervariBilete.add(jLabel18);
        jLabel18.setBounds(10, 80, 60, 30);

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel19.setText("Zbor");
        rezervariBilete.add(jLabel19);
        jLabel19.setBounds(20, 260, 50, 19);

        butonRezervaBilet.setBackground(new java.awt.Color(0, 102, 102));
        butonRezervaBilet.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonRezervaBilet.setForeground(new java.awt.Color(255, 255, 255));
        butonRezervaBilet.setText("Rezerva");
        butonRezervaBilet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonRezervaBiletActionPerformed(evt);
            }
        });
        rezervariBilete.add(butonRezervaBilet);
        butonRezervaBilet.setBounds(290, 380, 100, 25);

        butonInapoiBilete.setBackground(new java.awt.Color(0, 102, 102));
        butonInapoiBilete.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonInapoiBilete.setForeground(new java.awt.Color(255, 255, 255));
        butonInapoiBilete.setText("Inapoi");
        butonInapoiBilete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonInapoiBileteActionPerformed(evt);
            }
        });
        rezervariBilete.add(butonInapoiBilete);
        butonInapoiBilete.setBounds(570, 410, 100, 40);

        clientNou.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientNou.setText("Nou");
        clientNou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientNouActionPerformed(evt);
            }
        });
        rezervariBilete.add(clientNou);
        clientNou.setBounds(90, 80, 80, 25);

        clientFidel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientFidel.setText("Fidel");
        clientFidel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientFidelActionPerformed(evt);
            }
        });
        rezervariBilete.add(clientFidel);
        clientFidel.setBounds(180, 80, 53, 25);

        comboDestinatie.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        comboDestinatie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDestinatieActionPerformed(evt);
            }
        });
        rezervariBilete.add(comboDestinatie);
        comboDestinatie.setBounds(90, 250, 130, 23);

        comboDataPlecarii.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        comboDataPlecarii.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDataPlecariiActionPerformed(evt);
            }
        });
        rezervariBilete.add(comboDataPlecarii);
        comboDataPlecarii.setBounds(240, 250, 130, 23);

        numeClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        numeClient.setText("Nume");
        rezervariBilete.add(numeClient);
        numeClient.setBounds(90, 135, 140, 30);

        prenumeClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        prenumeClient.setText("Prenume");
        rezervariBilete.add(prenumeClient);
        prenumeClient.setBounds(240, 135, 140, 30);

        telefonClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        telefonClient.setText("Telefon");
        rezervariBilete.add(telefonClient);
        telefonClient.setBounds(390, 135, 150, 30);

        emailClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        emailClient.setText("Email");
        rezervariBilete.add(emailClient);
        emailClient.setBounds(90, 185, 160, 30);

        parolaClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        parolaClient.setText("Parola");
        rezervariBilete.add(parolaClient);
        parolaClient.setBounds(400, 185, 140, 30);

        contClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        contClient.setText("Cont");
        rezervariBilete.add(contClient);
        contClient.setBounds(270, 185, 110, 30);

        adaugaClientButon.setBackground(new java.awt.Color(0, 102, 102));
        adaugaClientButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        adaugaClientButon.setForeground(new java.awt.Color(255, 255, 255));
        adaugaClientButon.setText("Adauga");
        adaugaClientButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugaClientButonActionPerformed(evt);
            }
        });
        rezervariBilete.add(adaugaClientButon);
        adaugaClientButon.setBounds(570, 160, 90, 25);
        rezervariBilete.add(pretBilet);
        pretBilet.setBounds(90, 320, 130, 20);

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel21.setText("Pret");
        rezervariBilete.add(jLabel21);
        jLabel21.setBounds(20, 320, 50, 19);

        clientiFideli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clientiFideliItemStateChanged(evt);
            }
        });
        clientiFideli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientiFideliActionPerformed(evt);
            }
        });
        rezervariBilete.add(clientiFideli);
        clientiFideli.setBounds(270, 80, 140, 20);

        oraPlecarii.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rezervariBilete.add(oraPlecarii);
        oraPlecarii.setBounds(390, 250, 60, 23);

        jLabel20.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel20.setText("BILETE");
        rezervariBilete.add(jLabel20);
        jLabel20.setBounds(290, 30, 70, 22);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        rezervariBilete.add(jLabel22);
        jLabel22.setBounds(0, 0, 700, 480);

        vizMeniuTicketing.setForeground(new java.awt.Color(51, 51, 51));
        vizMeniuTicketing.setText("Vizualizare");
        vizMeniuTicketing.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        zboruriItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        zboruriItem.setText("Zboruri");
        zboruriItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zboruriItemActionPerformed(evt);
            }
        });
        vizMeniuTicketing.add(zboruriItem);

        furnizoriVizItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        furnizoriVizItem.setText("Furnizori");
        furnizoriVizItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                furnizoriVizItemActionPerformed(evt);
            }
        });
        vizMeniuTicketing.add(furnizoriVizItem);

        contracteVizMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        contracteVizMeniu.setText("Contracte");
        contracteVizMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contracteVizMeniuActionPerformed(evt);
            }
        });
        vizMeniuTicketing.add(contracteVizMeniu);

        jMenuBar1.add(vizMeniuTicketing);

        adaugareMeniuTicketing.setForeground(new java.awt.Color(51, 51, 51));
        adaugareMeniuTicketing.setText("Adaugare");
        adaugareMeniuTicketing.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        adaugContractMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        adaugContractMeniu.setText("Contract");
        adaugContractMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugContractMeniuActionPerformed(evt);
            }
        });
        adaugareMeniuTicketing.add(adaugContractMeniu);

        jMenuBar1.add(adaugareMeniuTicketing);

        cautareMeniuTicketing.setForeground(new java.awt.Color(51, 51, 51));
        cautareMeniuTicketing.setText("Cautare");
        cautareMeniuTicketing.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        zboruriCautMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        zboruriCautMeniu.setForeground(new java.awt.Color(51, 51, 51));
        zboruriCautMeniu.setText("Zboruri");
        zboruriCautMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zboruriCautMeniuActionPerformed(evt);
            }
        });
        cautareMeniuTicketing.add(zboruriCautMeniu);

        bileteCautMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bileteCautMeniu.setForeground(new java.awt.Color(51, 51, 51));
        bileteCautMeniu.setText("Bilete");
        bileteCautMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bileteCautMeniuActionPerformed(evt);
            }
        });
        cautareMeniuTicketing.add(bileteCautMeniu);

        jMenuBar1.add(cautareMeniuTicketing);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pagPrincTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareZbor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(vizualizareFurnizori, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareContracte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(adaugareContract, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareZboruri, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contulMeu, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareBilete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rezervariBilete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pagPrincTicket, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareZbor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(vizualizareFurnizori, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareContracte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(adaugareContract, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareZboruri, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contulMeu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareBilete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rezervariBilete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void furnizoriVizItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_furnizoriVizItemActionPerformed
        setPanelVisible(vizualizareFurnizori);
    }//GEN-LAST:event_furnizoriVizItemActionPerformed

    private void butonContracteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonContracteActionPerformed
        setPanelVisible(vizualizareContracte);
    }//GEN-LAST:event_butonContracteActionPerformed

    private void zboruriCautMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zboruriCautMeniuActionPerformed
        setPanelVisible(cautareZboruri);
    }//GEN-LAST:event_zboruriCautMeniuActionPerformed

    private void butonIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonIesireActionPerformed
       JFrame primaPagina = new PrimaPagina();
       primaPagina.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_butonIesireActionPerformed

    private void zboruriItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zboruriItemActionPerformed
        setPanelVisible(vizualizareZbor);
    }//GEN-LAST:event_zboruriItemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void inapoiPagAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiPagAdminActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_inapoiPagAdminActionPerformed

    private void butonAdaugaContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugaContractActionPerformed
        setPanelVisible(adaugareContract);
    }//GEN-LAST:event_butonAdaugaContractActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void contracteVizMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contracteVizMeniuActionPerformed
       setPanelVisible(vizualizareContracte);
    }//GEN-LAST:event_contracteVizMeniuActionPerformed

    private void deschideContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deschideContractActionPerformed
        contractScris.setText("");
        try {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            File f1 = new File(open.getSelectedFile().getPath());
            try (FileReader fr = new FileReader(f1)) {
                BufferedReader br = new BufferedReader(fr);
                String s;
                while ((s = br.readLine()) != null) {
                    contractScris.append(s + "\n");
                }
            }
        } catch (HeadlessException | IOException ae) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa contractele!");
        }

    }//GEN-LAST:event_deschideContractActionPerformed

    private void butonContractAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonContractAdaugaActionPerformed

        String admin = numeAngajat.getText();
        String furnizor = furnizori.getSelectedItem().toString();
        String data = anul.getSelectedItem() + "-" + luna.getSelectedItem() + "-" + ziua.getSelectedItem();
        int nrContract = 0;

        try{
            Connection conn = getConnection();

            String getCodFurnizor =
            "SELECT furnizori.cod_furnizor FROM furnizori WHERE firma=? ";
            
            PreparedStatement ps = conn.prepareStatement(getCodFurnizor);
            ps.setString(1, furnizor);
            ResultSet rs = ps.executeQuery();
            int codf = 0;
            while(rs.next()){
                codf = rs.getInt(1);
            }
            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO contract VALUES (1,?,?,?)");
            ps1.setInt(1, codf);
            ps1.setNull(2, Types.INTEGER);
            ps1.setString(3, data);
            ps1.executeUpdate();
            String getNrContract = "SELECT MAX(nrcontract) FROM contract";
            PreparedStatement ps2 = conn.prepareStatement(getNrContract);
            ResultSet rs1 = ps2.executeQuery();

            while(rs1.next()){
                nrContract = rs1.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate realiza contractul!");           
        }
        contractA4.selectAll();
        contractA4.replaceSelection("");
        contractA4.append("\t\n\tCONTRACT DE COLABORARE\n" + "\nNr:\t" + nrContract + "\n\nIntre SC Agentie Turism SRL cu sediul in "
            + "\nStr. Victoriei, Nr.134 " + "\navand certificat de inmatriculare nr. 19887, " + "\nreprezentata de " +
            admin + " avand functia\n" + "de agent ticketing " + "\nsi\n" + "SC " + furnizor + " SRL cu sediul in Str X, Nr.120 " +
            "\n\nObiectul contractului " + "\n\nArt. 1 Obiectul prezentului contract constă în " + "\nvânzarea de bilete de transport pe ruta " + "\n– x – y – z cu plecare în zilele de"+"\n joi, vineri şi sâmbătă.\n" +
            "\n" +
            "Art. 2 Vânzarea şi/sau rezervarea biletelor" + "\n de transport se face cu respectarea dispoziţiilor" + "\n legale şi a celor din prezentul contract." +
            "\n\nIncheiat astazi:\t" + data);
        contractA4.setFont(new Font("Lucida Bright",Font.PLAIN,12));

    }//GEN-LAST:event_butonContractAdaugaActionPerformed

    private void anulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anulActionPerformed

    }//GEN-LAST:event_anulActionPerformed

    private void lunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunaActionPerformed

        if(luna.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anul.getSelectedItem().toString()))){
            ziua.removeAllItems();
            for(int i=1;i<=29;i++){
                ziua.addItem(Integer.toString(i));
            }
        } else if(luna.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anul.getSelectedItem().toString()))==false){
            ziua.removeAllItems();
            for(int i=1;i<=28;i++){
                ziua.addItem(Integer.toString(i));
            }
        } else if(luna.getSelectedItem().equals("1")|| luna.getSelectedItem().equals("3") || luna.getSelectedItem().equals("5") || luna.getSelectedItem().equals("7") || luna.getSelectedItem().equals("8") || luna.getSelectedItem().equals("10") || luna.getSelectedItem().equals("12")){
            ziua.removeAllItems();
            for(int i=1;i<=31;i++){
                ziua.addItem(Integer.toString(i));
            }
        } else{
            ziua.removeAllItems();
            for(int i=1;i<=30;i++){
                ziua.addItem(Integer.toString(i));
            }
        }
    }//GEN-LAST:event_lunaActionPerformed

    private void salvareContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvareContractActionPerformed
        JFileChooser save = new JFileChooser();
        int option = save.showSaveDialog(this);
        File file = new File(save.getSelectedFile().getPath());
        try {
            String source = contractA4.getText();
            char buffer[] = new char[source.length()];
            source.getChars(0, source.length(), buffer, 0);

            try (FileWriter f1 = new FileWriter(file + ".txt")) {
                for (int i = 0; i < buffer.length; i++) {
                    f1.write(buffer[i]);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate salva!");
        }

    }//GEN-LAST:event_salvareContractActionPerformed

    private void butonInapoiContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiContractActionPerformed
        contractA4.selectAll();
        contractA4.replaceSelection("");
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_butonInapoiContractActionPerformed

    private void numeAngajatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeAngajatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeAngajatActionPerformed

    private void adaugContractMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaugContractMeniuActionPerformed
       setPanelVisible(adaugareContract);
    }//GEN-LAST:event_adaugContractMeniuActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void butonCautaFiltrareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaFiltrareActionPerformed
        String textIntrodus = fieldFiltrare.getText();
        DefaultTableModel model = (DefaultTableModel) tabelZboruri1.getModel();
        try{
            Connection con = getConnection();
            String transport = "SELECT den_avion FROM transport, zbor WHERE transport.cod_transport = zbor.cod_transport";
            PreparedStatement stmt1 = con.prepareStatement(transport);
            ResultSet rs1 = stmt1.executeQuery();
            if(Destinatie.isSelected()==true){
                String sql = "SELECT * FROM zbor WHERE destinatie=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, textIntrodus);
                ResultSet rs2 = pstm.executeQuery();
                while(rs2.next() && rs1.next()){
                    model.addRow(new Object[]{"" + rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                                rs1.getString(1)});
                }
                fieldFiltrare.setText("");
                Destinatie.setSelected(false);
            } else if(dataPlecareRadio.isSelected() == true){
                String sql = "SELECT * FROM zbor WHERE data_plecarii=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, textIntrodus);
                ResultSet rs2 = pstm.executeQuery();
                while(rs2.next() && rs1.next()){
                    model.addRow(new Object[]{"" + rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                                rs1.getString(1)});
                }
                fieldFiltrare.setText("");
                dataPlecareRadio.setSelected(false);
            } else if(oraPlecareButon.isSelected() == true){
                String sql = "SELECT * FROM zbor WHERE ora_plecarii=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setInt(1, Integer.parseInt(textIntrodus));
                ResultSet rs2 = pstm.executeQuery();
                while(rs2.next() && rs1.next()){
                    model.addRow(new Object[]{"" + rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                                rs1.getString(1)});
                }
                fieldFiltrare.setText("");
                oraPlecareButon.setSelected(false);
            } else if(oraSosireButon.isSelected() == true){
                String sql = "SELECT * FROM zbor WHERE ora_sosirii=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setInt(1, Integer.parseInt(textIntrodus));
                ResultSet rs2 = pstm.executeQuery();
                while(rs2.next() && rs1.next()){
                    model.addRow(new Object[]{"" + rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                                rs1.getString(1)});
                }
                fieldFiltrare.setText("");
                oraSosireButon.setSelected(false);
            }
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(this,"Eroare cautare zbor");
        }
    }//GEN-LAST:event_butonCautaFiltrareActionPerformed

    private void butonZboruriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonZboruriActionPerformed
       setPanelVisible(cautareZboruri);
    }//GEN-LAST:event_butonZboruriActionPerformed

    private void parolaContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parolaContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parolaContActionPerformed

    private void numeContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeContActionPerformed

    private void prenumeContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenumeContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenumeContActionPerformed

    private void emailContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailContActionPerformed

    private void telefonContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonContActionPerformed

    private void contContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contContActionPerformed

    private void butonInapoiContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiContActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_butonInapoiContActionPerformed

    private void butonSalveazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonSalveazaActionPerformed
        String nume = numeCont.getText();
        String prenume = prenumeCont.getText();
        String telefon = telefonCont.getText();
        String email = emailCont.getText();
        String cont = contCont.getText();
        String parola = parolaCont.getText();
        try{
            Connection conn = getConnection();
            String updateCont = "UPDATE angajat SET nume=?, prenume=?, telefon=?, email=?, cont=?, parola=? WHERE cont=?";
            PreparedStatement ps = conn.prepareStatement(updateCont);
            ps.setString(1, nume);
            ps.setString(2, prenume);
            ps.setString(3, telefon);
            ps.setString(4, email);
            ps.setString(5, cont);
            ps.setString(6, parola);
            ps.setString(7,campUser.getText());
            ps.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Actualizare esuata!");
        }
    }//GEN-LAST:event_butonSalveazaActionPerformed

    private void butonContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonContActionPerformed
       try{
           Connection conn = getConnection();
           String getDateAdmin = "SELECT * FROM angajat WHERE cont=?";
           PreparedStatement ps = conn.prepareStatement(getDateAdmin);
           ps.setString(1,campUser.getText());
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               numeCont.setText(rs.getString("Nume"));
               prenumeCont.setText(rs.getString("Prenume"));
               telefonCont.setText(rs.getString("Telefon"));
               emailCont.setText(rs.getString("Email"));
               contCont.setText(rs.getString("Cont"));
               parolaCont.setText(rs.getString("Parola"));
           }
       }catch(SQLException e){
           System.out.println(e.getMessage());
           JOptionPane.showMessageDialog(this,"Nu se poate accesa contul!");
       }
        setPanelVisible(contulMeu);
    }//GEN-LAST:event_butonContActionPerformed

    private void butonFurnizoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonFurnizoriActionPerformed
       setPanelVisible(vizualizareFurnizori);
    }//GEN-LAST:event_butonFurnizoriActionPerformed

    private void bileteCautMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bileteCautMeniuActionPerformed
       setPanelVisible(vizualizareBilete);
    }//GEN-LAST:event_bileteCautMeniuActionPerformed

    private void butonCautaBiletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaBiletActionPerformed
        String textIntrodus = fieldFiltrareBilete.getText();
        DefaultTableModel model = (DefaultTableModel) tabelBilete.getModel();
        try{
            Connection con = getConnection();
            if(radioClient.isSelected()==true){
                 String sql1 = "SELECT nume,prenume, destinatie, data_plecarii, ora_plecarii,bilet.pret,data_achizitionarii FROM zbor, client, bilet WHERE zbor.cod_zbor=bilet.cod_zbor AND client.cod_client=bilet.cod_client AND nume=?";
                PreparedStatement pstm1 = con.prepareStatement(sql1);
                pstm1.setString(1, textIntrodus);
                ResultSet rs1 = pstm1.executeQuery();
          
                
                while(rs1.next()){
                  model.addRow(new Object[]{""  + rs1.getString(1)+" "+rs1.getString(2), rs1.getString(3),rs1.getString(4),rs1.getInt(5),rs1.getInt(6),rs1.getString(7)});
                  
                }
                fieldFiltrareBilete.setText("");
                radioClient.setSelected(false);
                        
            } else if(radioDestinatie.isSelected()==true){
                String sql1 = "SELECT nume,prenume, destinatie, data_plecarii, ora_plecarii,bilet.pret,data_achizitionarii FROM zbor, client, bilet WHERE zbor.cod_zbor=bilet.cod_zbor AND client.cod_client=bilet.cod_client AND zbor.destinatie=?";
                PreparedStatement pstm1 = con.prepareStatement(sql1);
                pstm1.setString(1, textIntrodus);
                ResultSet rs1 = pstm1.executeQuery();
          
                
                while(rs1.next()){
                  model.addRow(new Object[]{""  + rs1.getString(1)+" "+rs1.getString(2), rs1.getString(3),rs1.getString(4),rs1.getInt(5),rs1.getInt(6),rs1.getString(7)});
                  
                }
                fieldFiltrareBilete.setText("");
                radioDestinatie.setSelected(false);
            } else if(radioPret.isSelected()==true){
                String sql1 = "SELECT nume,prenume, destinatie, data_plecarii, ora_plecarii,bilet.pret,data_achizitionarii FROM zbor, client, bilet WHERE zbor.cod_zbor=bilet.cod_zbor and client.cod_client=bilet.cod_client and bilet.pret=?";
                PreparedStatement pstm1 = con.prepareStatement(sql1);
                pstm1.setInt(1, Integer.parseInt(textIntrodus));
                ResultSet rs1 = pstm1.executeQuery();
          
                
                while(rs1.next()){
                  model.addRow(new Object[]{""  + rs1.getString(1)+" "+rs1.getString(2), rs1.getString(3),rs1.getString(4),rs1.getInt(5),rs1.getInt(6),rs1.getString(7)});
                  
                }
                fieldFiltrareBilete.setText("");
                radioPret.setSelected(false);
            }else if(radioData.isSelected()==true){
                String sql1 = "SELECT nume,prenume, destinatie, data_plecarii, ora_plecarii,bilet.pret,data_achizitionarii FROM zbor, client, bilet WHERE zbor.cod_zbor=bilet.cod_zbor and client.cod_client=bilet.cod_client and data_achizitionarii=?";
                PreparedStatement pstm1 = con.prepareStatement(sql1);
                pstm1.setString(1, textIntrodus);
                ResultSet rs1 = pstm1.executeQuery();
          
                
                while(rs1.next()){
                  model.addRow(new Object[]{""  + rs1.getString(1)+" "+rs1.getString(2), rs1.getString(3),rs1.getString(4),rs1.getInt(5),rs1.getInt(6),rs1.getString(7)});
                  
                }
                fieldFiltrareBilete.setText("");
                radioData.setSelected(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Cautarea nu a reusit!");                    
        }
    }//GEN-LAST:event_butonCautaBiletActionPerformed

    private void butonBileteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonBileteActionPerformed
        setPanelVisible(rezervariBilete);
    }//GEN-LAST:event_butonBileteActionPerformed

    private void comboDestinatieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDestinatieActionPerformed
        String dest = comboDestinatie.getSelectedItem().toString();
        try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(data_plecarii) FROM zbor WHERE destinatie=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dest);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboDataPlecarii.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(this,"Nu se pot afisa datele de plecare!");
        }
    }//GEN-LAST:event_comboDestinatieActionPerformed

    private void adaugaClientButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaugaClientButonActionPerformed
        String numeCli = numeClient.getText();
        String prenumeCli = prenumeClient.getText();
        String telefon = telefonClient.getText();
        String email = emailClient.getText();
        String cont = contClient.getText();
        String parola = parolaClient.getText();
     
        try{
            Connection con = getConnection();
            String sql = "INSERT INTO client VALUES(null,?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, numeCli);
            pstm.setString(2,prenumeCli);
            pstm.setString(3,telefon);
            pstm.setString(4, email);
            pstm.setString(5,cont);
            pstm.setString(6,parola);
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(this,"Clientul a fost adauagat cu succes!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate adauga clientul!");
        }
        
        numeClient.setText("Nume");
        prenumeClient.setText("Prenume");
        telefonClient.setText("Telefon");
        emailClient.setText("Email");
        contClient.setText("Cont");
        parolaClient.setText("Parola");
    }//GEN-LAST:event_adaugaClientButonActionPerformed

    private void clientFidelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientFidelActionPerformed
        fillComboClientiFideli(); 
        
    }//GEN-LAST:event_clientFidelActionPerformed

    private void clientiFideliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientiFideliActionPerformed
      try{
           Connection con = getConnection();
           String sql = "SELECT * FROM client WHERE nume = (substring_index(?,' ', 1))";
           PreparedStatement pstm = con.prepareStatement(sql);
           pstm.setString(1, clientiFideli.getSelectedItem().toString());
           ResultSet rs = pstm.executeQuery();
           while(rs.next()){
               numeClient.setText(rs.getString(2));
               prenumeClient.setText(rs.getString(3));
               telefonClient.setText(rs.getString(4));
               emailClient.setText(rs.getString(5));
               contClient.setText(rs.getString(6));
               parolaClient.setText(rs.getString(7));
           }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate afisa clientul!");
        }
    }//GEN-LAST:event_clientiFideliActionPerformed

    private void clientiFideliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clientiFideliItemStateChanged
         
    }//GEN-LAST:event_clientiFideliItemStateChanged

    private void butonRezervaBiletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonRezervaBiletActionPerformed
        try{
            Connection con = getConnection();
            String sql1 = "SELECT cod_client FROM client WHERE nume=? AND prenume=?";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1, numeClient.getText());
            pstm1.setString(2,prenumeClient.getText());
            ResultSet rs1 = pstm1.executeQuery();
            String sql2 = "SELECT cod_zbor FROM zbor WHERE destinatie=? AND data_plecarii=? AND ora_plecarii=?";
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1,comboDestinatie.getSelectedItem().toString());
            pstm2.setString(2, comboDataPlecarii.getSelectedItem().toString());
            pstm2.setString(3, oraPlecarii.getSelectedItem().toString());
            ResultSet rs2 = pstm2.executeQuery();
            
            String sql3 = "INSERT INTO bilet VALUES(?,?,?,sysdate())";
            PreparedStatement pstm3 = con.prepareStatement(sql3);
            while(rs1.next() && rs2.next()){
                pstm3.setInt(1, rs2.getInt(1));
                pstm3.setInt(2, rs1.getInt(1));
                pstm3.setInt(3,Integer.parseInt(pretBilet.getText()));
            }
            pstm3.executeUpdate();
            JOptionPane.showMessageDialog(this,"Bilet rezervat!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate rezerva biletul!");
        }
    }//GEN-LAST:event_butonRezervaBiletActionPerformed

    private void comboDataPlecariiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDataPlecariiActionPerformed
         String dest = comboDestinatie.getSelectedItem().toString();
         String data = comboDataPlecarii.getSelectedItem().toString();
        try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(ora_plecarii) FROM zbor WHERE destinatie=? AND data_plecarii=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dest);
            pstm.setString(2, data);
            ResultSet rs = pstm.executeQuery();
            oraPlecarii.removeAllItems();
            while(rs.next()){
                oraPlecarii.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(this,"Nu se pot afisa orele de plecare!");
        }
    }//GEN-LAST:event_comboDataPlecariiActionPerformed

    private void clientNouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientNouActionPerformed
        numeClient.setText("");
        prenumeClient.setText("");
        telefonClient.setText("");
        emailClient.setText("");
        contClient.setText("");
        parolaClient.setText("");       
    }//GEN-LAST:event_clientNouActionPerformed

    private void butonInapoiBileteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiBileteActionPerformed
        setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_butonInapoiBileteActionPerformed

    private void butonInapoiClientBiletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiClientBiletActionPerformed
         setPanelVisible(pagPrincTicket);
    }//GEN-LAST:event_butonInapoiClientBiletActionPerformed

    private void oraSosireButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oraSosireButonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oraSosireButonActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaTicketing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaTicketing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Destinatie;
    private javax.swing.JMenuItem adaugContractMeniu;
    private javax.swing.JButton adaugaClientButon;
    private javax.swing.JPanel adaugareContract;
    private javax.swing.JMenu adaugareMeniuTicketing;
    public javax.swing.JLabel agentieTicketing;
    private javax.swing.JComboBox<String> anul;
    private javax.swing.JLabel bgTicketing;
    private javax.swing.JMenuItem bileteCautMeniu;
    private javax.swing.JButton butonAdaugaContract;
    private javax.swing.JButton butonBilete;
    private javax.swing.JButton butonCautaBilet;
    private javax.swing.JButton butonCautaFiltrare;
    private javax.swing.JButton butonCont;
    private javax.swing.JButton butonContractAdauga;
    private javax.swing.JButton butonContracte;
    private javax.swing.JButton butonFurnizori;
    private javax.swing.JButton butonIesire;
    private javax.swing.JButton butonInapoiBilete;
    private javax.swing.JButton butonInapoiClientBilet;
    private javax.swing.JButton butonInapoiCont;
    private javax.swing.JButton butonInapoiContract;
    private javax.swing.JButton butonRezervaBilet;
    private javax.swing.JButton butonSalveaza;
    private javax.swing.JButton butonZboruri;
    private javax.swing.JMenu cautareMeniuTicketing;
    private javax.swing.JPanel cautareZboruri;
    private javax.swing.JRadioButton clientFidel;
    private javax.swing.JRadioButton clientNou;
    private javax.swing.JComboBox<String> clientiFideli;
    private javax.swing.JComboBox<String> comboDataPlecarii;
    private javax.swing.JComboBox<String> comboDestinatie;
    private javax.swing.JTextField contClient;
    private javax.swing.JTextField contCont;
    private javax.swing.JTextArea contractA4;
    private javax.swing.JTextArea contractScris;
    private javax.swing.JMenuItem contracteVizMeniu;
    private javax.swing.JPanel contulMeu;
    private javax.swing.JRadioButton dataPlecareRadio;
    private javax.swing.JButton deschideContract;
    private javax.swing.JTextField emailClient;
    private javax.swing.JTextField emailCont;
    private javax.swing.JTextField fieldFiltrare;
    private javax.swing.JTextField fieldFiltrareBilete;
    private javax.swing.JComboBox<String> furnizori;
    private javax.swing.JMenuItem furnizoriVizItem;
    private javax.swing.JLabel imgProfilAng;
    private javax.swing.JButton inapoiPagAdmin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelFurnizor;
    private javax.swing.JLabel labelNumeAngajat;
    private javax.swing.JLabel logoTicket;
    private javax.swing.JComboBox<String> luna;
    private javax.swing.JTextField numeAngajat;
    private javax.swing.JTextField numeClient;
    private javax.swing.JTextField numeCont;
    private javax.swing.JRadioButton oraPlecareButon;
    private javax.swing.JComboBox<String> oraPlecarii;
    private javax.swing.JRadioButton oraSosireButon;
    private javax.swing.JPanel pagPrincTicket;
    private javax.swing.JTextField parolaClient;
    private javax.swing.JTextField parolaCont;
    private javax.swing.JTextField prenumeClient;
    private javax.swing.JTextField prenumeCont;
    private javax.swing.JTextField pretBilet;
    private javax.swing.JRadioButton radioClient;
    private javax.swing.JRadioButton radioData;
    private javax.swing.JRadioButton radioDestinatie;
    private javax.swing.JRadioButton radioPret;
    private javax.swing.JPanel rezervariBilete;
    private javax.swing.JButton salvareContract;
    private javax.swing.JTable tabelBilete;
    private javax.swing.JTable tabelContract;
    private javax.swing.JTable tabelFurnizori;
    private javax.swing.JTable tabelZboruri;
    private javax.swing.JTable tabelZboruri1;
    private javax.swing.JTextField telefonClient;
    private javax.swing.JTextField telefonCont;
    private javax.swing.JLabel titluContulMeu;
    private javax.swing.JLabel titluFurnizori;
    private javax.swing.JLabel titluTicketing;
    private javax.swing.JMenu vizMeniuTicketing;
    private javax.swing.JPanel vizualizareBilete;
    private javax.swing.JPanel vizualizareContracte;
    private javax.swing.JPanel vizualizareFurnizori;
    private javax.swing.JPanel vizualizareZbor;
    private javax.swing.JMenuItem zboruriCautMeniu;
    private javax.swing.JMenuItem zboruriItem;
    private javax.swing.JComboBox<String> ziua;
    // End of variables declaration//GEN-END:variables
}
