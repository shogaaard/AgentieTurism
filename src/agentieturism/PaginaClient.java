/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentieturism;

import static agentieturism.ConexiuneBD.getConnection;
import static agentieturism.PrimaPagina.campUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dm181
 */
public class PaginaClient extends javax.swing.JFrame {

    /**
     * Creates new form PaginaClient
     */
    public PaginaClient() {
        initComponents();
        setPanelVisible(pagPrincipalaClient);
        fillSejur();
        fillCircuite();
        fillComboTropical();
        fillComboGermania();
        fillComboFranta();
        fillComboNorvegia();
        fillComboTurcia();
        fillTabelRezervariCircuite();
        fillTabelRezervariSejururi();
        fillComboAn();        
        fillComboDestinatie();
        fillComboDestinatie1();
        fillComboDestinatieBilet();
    
    }
     private void setPanelVisible(JPanel panel){
        ArrayList<JPanel> jpaneluri = new ArrayList<>();
        jpaneluri.add(pagPrincipalaClient);
        jpaneluri.add(vizualizareSejur);
        jpaneluri.add(vizualizareCircuit);
        jpaneluri.add(vizualizareCazare);
        jpaneluri.add(tropicalHotels);
        jpaneluri.add(GermaniaHotels);
        jpaneluri.add(FrantaHotels);
        jpaneluri.add(NorvegiaHotels);
        jpaneluri.add(TurciaHotels);
        jpaneluri.add(cautareSejur);
        jpaneluri.add(cautareCircuit);
        jpaneluri.add(cautareZbor);
        jpaneluri.add(rezervarileMele);
        jpaneluri.add(contulMeu);
        jpaneluri.add(rezervareSejur);
        jpaneluri.add(rezervareCircuit);
        jpaneluri.add(rezervareBilet);
        jpaneluri.add(detaliiZbor);
        pagPrincipalaClient.setVisible(false);
        vizualizareSejur.setVisible(false);
        vizualizareCircuit.setVisible(false);
        vizualizareCazare.setVisible(false);
        tropicalHotels.setVisible(false);
        GermaniaHotels.setVisible(false);
        FrantaHotels.setVisible(false);
        NorvegiaHotels.setVisible(false);
        TurciaHotels.setVisible(false);
        cautareSejur.setVisible(false);
        cautareCircuit.setVisible(false);
        cautareZbor.setVisible(false);
        rezervarileMele.setVisible(false);
        contulMeu.setVisible(false);
        rezervareSejur.setVisible(false);
        rezervareCircuit.setVisible(false);
        rezervareBilet.setVisible(false);
        detaliiZbor.setVisible(false);
         for(JPanel jpanel:jpaneluri){
            if(panel == jpanel)
                panel.setVisible(true);
        }
     }
     private boolean anBisect(int an){
        return ((an%4==0) && (an%100!=0))|| an%400==0;
    }
     private void fillComboAn(){
        for(int i=2020;i>=1990;i--)
            anul.addItem(Integer.toString(i));
        for(int i=1;i<=12;i++)
            luna.addItem(Integer.toString(i));

    }    
    
     private void fillComboDestinatie(){
         try{
            Connection con = getConnection();
            String sql = "SELECT oras_sosire FROM sejur";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboDestinatia.addItem(rs.getString(1));
            }            
     }  catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa destinatiile!");
        }
     }
     
     private void fillComboDestinatie1(){
         try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(tara) FROM oras";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboDestinatia1.addItem(rs.getString(1));
            }            
     }  catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa destinatiile!");
        }
     }
      private void fillSejur(){
        DefaultTableModel model = (DefaultTableModel) tabelSejur.getModel();        
      
        try{
            Connection con = getConnection();
            String sql = "select s.*, ps.valoare_sejur, a.denumire from sejur s, pret_sejur ps, agentie a\n" +
                           "where s.cod_sejur = ps.sejur_cod_sejur\n" +
                            "and a.cod_agentie = ps.cod_agentie;";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                 model.addRow(new Object[]{""  + rs.getString("oras_plecare"), rs.getString("oras_sosire"), rs.getString("data_plecare"), rs.getString("data_sosire"),  rs.getString("valoare_sejur"), rs.getString("Denumire")});
            }           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa sejururile!");
        }
    }
      private void fillCircuite(){
        try{
            Connection con = getConnection();
            String sql = "select * from circuit";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCircuite.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa circuitele!");
        }
    }
      
       private void fillComboTropical(){
        try{
            Connection con = getConnection();
            String sql = "select  denumire_cazare  from cazare,circuit where cazare.cod_circuit = circuit.cod_circuit"
                    + " and circuit.denumire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "Tropical");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCazareTropical.addItem(rs.getString(1));                 
            }
        }
             catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa cazarile!");
           
        }
        }
       private void fillComboDestinatieBilet(){
            try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(destinatie) FROM zbor";
            PreparedStatement pstm = con.prepareStatement(sql);           
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboDestinatie1.addItem(rs.getString(1));                 
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa destinatiile!");           
        }    
       }
       private void fillComboGermania(){
        try{
            Connection con = getConnection();
            String sql = "select  denumire_cazare  from cazare,sejur where cazare.cod_sejur = sejur.cod_sejur"
                    + " and sejur.oras_sosire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "Berlin");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboGermania.addItem(rs.getString(1));                 
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa cazarile!");
           
        }
    }
    private void fillComboFranta(){
        try{
            Connection con = getConnection();
            String sql = "select  denumire_cazare  from cazare,sejur where cazare.cod_sejur = sejur.cod_sejur"
                    + " and sejur.oras_sosire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "Paris");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCazareFranta.addItem(rs.getString(1));                 
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa cazarile!");
           
        }
    }
    
    private void fillComboNorvegia(){
        try{
            Connection con = getConnection();
            String sql = "select  denumire_cazare  from cazare,circuit where cazare.cod_circuit = circuit.cod_circuit"
                    + " and circuit.denumire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "Scandinavia Dream");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCazareNorvegia.addItem(rs.getString(1));                 
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa cazarile!");
           
        }
    }
      private void fillComboTurcia(){
        try{
            Connection con = getConnection();
            String sql = "select  denumire_cazare  from cazare,sejur where cazare.cod_sejur = sejur.cod_sejur"
                    + " and sejur.oras_sosire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "Ankara");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCazareTurcia.addItem(rs.getString(1));                 
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa cazarile!");
           
        }
    }
      private void fillTabelRezervariCircuite(){
         DefaultTableModel model = (DefaultTableModel) tabelRezervariCircuite.getModel();
          try{
              Connection con = getConnection();
              String sql = "select denumire,valoare_circuit, rezervare_cicuit.data_plecare from circuit, rezervare_cicuit, client, sezon, pret_circuit where circuit.cod_circuit=rezervare_cicuit.circuit_cod_circuit  and rezervare_cicuit.client_cod_client = client.cod_client and pret_circuit.circuit_cod_circuit = circuit.cod_circuit and pret_circuit.sezon_cod_sezon = sezon.cod_sezon and client.cont=? and data_plecare between data_inceput and data_sfarsit";
              PreparedStatement pstm = con.prepareStatement(sql);
              pstm.setString(1, campUser.getText());
              ResultSet rs = pstm.executeQuery();
              while(rs.next()){
                  model.addRow(new Object[]{"" + rs.getString(1), rs.getInt(2), rs.getString(3)});
              }
          } catch (SQLException ex) {
             JOptionPane.showMessageDialog(this,"Nu se pot accesa rezervarile!");
        }
                  
      }
      private void fillTabelRezervariSejururi(){
           DefaultTableModel model = (DefaultTableModel) tabelRezervariSejururi.getModel();
           try{
               Connection con = getConnection();
               String sql = "SELECT oras_plecare, oras_sosire, data_plecare,data_sosire, valoare_sejur FROM sejur, pret_sejur, rezervare_sejur, client, sezon WHERE sejur.cod_sejur = pret_sejur.sejur_cod_sejur AND sejur.cod_sejur = rezervare_sejur.cod_sejur AND client.cod_client = rezervare_sejur.cod_client AND data_plecare between data_inceput and data_sfarsit  and client.cont=? and sezon.cod_sezon = pret_sejur.sezon_cod_sezon;";
               PreparedStatement pstm = con.prepareStatement(sql);
               pstm.setString(1, campUser.getText());
               ResultSet rs = pstm.executeQuery();
               while(rs.next()){
                   model.addRow(new Object[]{"" + rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)});
               }
           } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,"Nu se pot accesa rezervarile!");
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

        rezervareCircuit = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        comboDestinatia1 = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelOferteCircuite = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        rezervareSejur = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        comboDestinatia = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        ziua = new javax.swing.JComboBox<>();
        luna = new javax.swing.JComboBox<>();
        anul = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        butonOferte = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelOferte = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        rezervareBilet = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        comboFurnizori = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        comboDataPlecare = new javax.swing.JComboBox<>();
        comboOraPlecare = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        comboDataSosire = new javax.swing.JComboBox<>();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        comboNrPersBilet = new javax.swing.JComboBox<>();
        jLabel91 = new javax.swing.JLabel();
        comboDestinatie1 = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        pretBilet = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        comboOraSosire = new javax.swing.JComboBox<>();
        jLabel93 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        pagPrincipalaClient = new javax.swing.JPanel();
        labelWelcome = new javax.swing.JLabel();
        butonCautareSejur = new javax.swing.JButton();
        butonCautareCircuit2 = new javax.swing.JButton();
        butonCautareCircuit3 = new javax.swing.JButton();
        butonCautareCircuit4 = new javax.swing.JButton();
        butonCautareCircuit5 = new javax.swing.JButton();
        butonCautareCircuit6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        vizualizareSejur = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSejur = new javax.swing.JTable();
        inapoiSejur = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        vizualizareCircuit = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboCircuite = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelOrase = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        butonCautareCircuit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        vizualizareCazare = new javax.swing.JPanel();
        hotelTropical = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tropicalHotels = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comboCazareTropical = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboNumarStele = new javax.swing.JTextField();
        comboCamere = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        inapoiHoteluri = new javax.swing.JButton();
        GermaniaHotels = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comboGermania = new javax.swing.JComboBox<>();
        nrSteleGermania = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        comboCamereGermania = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        inapoiGermania = new javax.swing.JButton();
        FrantaHotels = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        butonInapoiFranta = new javax.swing.JButton();
        comboCamereFranta = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nrSteleFranta = new javax.swing.JTextField();
        comboCazareFranta = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        NorvegiaHotels = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        comboCamereNorvegia = new javax.swing.JComboBox<>();
        nrSteleNorvegia = new javax.swing.JTextField();
        comboCazareNorvegia = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        TurciaHotels = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        comboCazareTurcia = new javax.swing.JComboBox<>();
        nrSteleTurcia = new javax.swing.JTextField();
        comboCamereTurcia = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        cautareSejur = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        radioOrasPlecare = new javax.swing.JRadioButton();
        radioOrasSosire = new javax.swing.JRadioButton();
        radioDataPlecare = new javax.swing.JRadioButton();
        fieldCautaSejur = new javax.swing.JTextField();
        butonCautaSejur = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelCautaSejur = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        cautareCircuit = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        radioDenumireCircuit = new javax.swing.JRadioButton();
        radioOras = new javax.swing.JRadioButton();
        radioTara = new javax.swing.JRadioButton();
        fieldCircuit = new javax.swing.JTextField();
        butonCautaCircuit = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelCircuit = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cautareZbor = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        Destinatie = new javax.swing.JRadioButton();
        dataPlecareRadio = new javax.swing.JRadioButton();
        oraPlecareButon = new javax.swing.JRadioButton();
        oraSosireButon = new javax.swing.JRadioButton();
        butonCautaFiltrare = new javax.swing.JButton();
        fieldFiltrare = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelZboruri1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        rezervarileMele = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelRezervariCircuite = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelRezervariSejururi = new javax.swing.JTable();
        butonAnulareRezervare = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        contulMeu = new javax.swing.JPanel();
        imgProfilAng = new javax.swing.JLabel();
        titluContulMeu = new javax.swing.JLabel();
        numeCont = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        prenumeCont = new javax.swing.JTextField();
        telefonCont = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        emailCont = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        contCont = new javax.swing.JTextField();
        parolaCont = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        butonInapoiCont = new javax.swing.JButton();
        butonSalveaza = new javax.swing.JButton();
        detaliiZbor = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        firmaZbor = new javax.swing.JTextField();
        nrZbor = new javax.swing.JTextField();
        destinatieZbor = new javax.swing.JTextField();
        transportZbor = new javax.swing.JTextField();
        oraSosireZbor = new javax.swing.JTextField();
        oraPlecareZbor = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel101 = new javax.swing.JLabel();
        pretBiletFinal = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        meniuClient = new javax.swing.JMenuBar();
        VizualizareMeniu = new javax.swing.JMenu();
        sejurVizItem = new javax.swing.JMenuItem();
        circuitVizItem = new javax.swing.JMenuItem();
        rezervariVizItem = new javax.swing.JMenuItem();
        cazareVizItem = new javax.swing.JMenuItem();
        CautareMeniu = new javax.swing.JMenu();
        sejurCautItem = new javax.swing.JMenuItem();
        bileteCautItem = new javax.swing.JMenuItem();
        circuitCautItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        circuitRezervItem = new javax.swing.JMenuItem();
        sejurRezervItem = new javax.swing.JMenuItem();
        bileteRezervItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(710, 550));
        getContentPane().setLayout(null);

        rezervareCircuit.setLayout(null);

        jLabel72.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setText("Oferte Circuite");
        rezervareCircuit.add(jLabel72);
        jLabel72.setBounds(250, 30, 150, 30);

        jLabel74.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setText("Destinatia");
        rezervareCircuit.add(jLabel74);
        jLabel74.setBounds(120, 100, 80, 30);

        comboDestinatia1.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboDestinatia1.setForeground(new java.awt.Color(0, 0, 0));
        comboDestinatia1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDestinatia1ItemStateChanged(evt);
            }
        });
        comboDestinatia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDestinatia1ActionPerformed(evt);
            }
        });
        rezervareCircuit.add(comboDestinatia1);
        comboDestinatia1.setBounds(240, 97, 170, 30);

        jButton11.setBackground(new java.awt.Color(0, 102, 102));
        jButton11.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Rezerva Acum");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        rezervareCircuit.add(jButton11);
        jButton11.setBounds(70, 400, 150, 40);

        jButton12.setBackground(new java.awt.Color(0, 102, 102));
        jButton12.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Inapoi");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        rezervareCircuit.add(jButton12);
        jButton12.setBounds(500, 400, 90, 40);

        tabelOferteCircuite.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Circuit", "Cazare", "Numar stele", "Pret", "Agentie"
            }
        ));
        jScrollPane9.setViewportView(tabelOferteCircuite);

        rezervareCircuit.add(jScrollPane9);
        jScrollPane9.setBounds(0, 190, 700, 170);

        jButton17.setBackground(new java.awt.Color(0, 102, 102));
        jButton17.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Vezi oferte!");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        rezervareCircuit.add(jButton17);
        jButton17.setBounds(490, 90, 130, 30);

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/clientbg.jpg"))); // NOI18N
        rezervareCircuit.add(jLabel59);
        jLabel59.setBounds(0, 0, 730, 520);

        getContentPane().add(rezervareCircuit);
        rezervareCircuit.setBounds(0, 0, 700, 520);

        rezervareSejur.setLayout(null);

        jLabel58.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Oferte Sejururi");
        rezervareSejur.add(jLabel58);
        jLabel58.setBounds(300, 20, 130, 30);

        comboDestinatia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboDestinatia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDestinatiaItemStateChanged(evt);
            }
        });
        comboDestinatia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDestinatiaActionPerformed(evt);
            }
        });
        rezervareSejur.add(comboDestinatia);
        comboDestinatia.setBounds(70, 120, 140, 27);

        jLabel62.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Data Plecare");
        rezervareSejur.add(jLabel62);
        jLabel62.setBounds(350, 70, 100, 30);

        ziua.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        rezervareSejur.add(ziua);
        ziua.setBounds(450, 120, 60, 27);

        luna.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        luna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunaActionPerformed(evt);
            }
        });
        rezervareSejur.add(luna);
        luna.setBounds(360, 120, 70, 27);

        anul.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        anul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anulActionPerformed(evt);
            }
        });
        rezervareSejur.add(anul);
        anul.setBounds(250, 120, 90, 27);

        jLabel65.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Destinatia");
        rezervareSejur.add(jLabel65);
        jLabel65.setBounds(100, 70, 80, 30);

        jButton4.setBackground(new java.awt.Color(0, 102, 102));
        jButton4.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Rezerva Acum");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        rezervareSejur.add(jButton4);
        jButton4.setBounds(70, 380, 150, 40);

        jButton8.setBackground(new java.awt.Color(0, 102, 102));
        jButton8.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Inapoi");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        rezervareSejur.add(jButton8);
        jButton8.setBounds(570, 380, 90, 40);

        butonOferte.setBackground(new java.awt.Color(0, 102, 102));
        butonOferte.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonOferte.setForeground(new java.awt.Color(255, 255, 255));
        butonOferte.setText("Vezi Oferte!");
        butonOferte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonOferteActionPerformed(evt);
            }
        });
        rezervareSejur.add(butonOferte);
        butonOferte.setBounds(550, 110, 120, 40);

        tabelOferte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Oras plecare", "Destinatie", "Plecare", "Sosire", "Cazare", "Pret", "Agentie"
            }
        ));
        jScrollPane8.setViewportView(tabelOferte);

        rezervareSejur.add(jScrollPane8);
        jScrollPane8.setBounds(0, 220, 690, 140);

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/clientbg.jpg"))); // NOI18N
        rezervareSejur.add(jLabel57);
        jLabel57.setBounds(0, 0, 720, 520);

        getContentPane().add(rezervareSejur);
        rezervareSejur.setBounds(0, 0, 700, 520);

        rezervareBilet.setLayout(null);

        jLabel85.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Rezervare Bilet");
        rezervareBilet.add(jLabel85);
        jLabel85.setBounds(250, 10, 150, 30);

        jLabel86.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Destinatia");
        rezervareBilet.add(jLabel86);
        jLabel86.setBounds(70, 70, 80, 30);

        comboFurnizori.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboFurnizori.setForeground(new java.awt.Color(0, 0, 0));
        rezervareBilet.add(comboFurnizori);
        comboFurnizori.setBounds(470, 200, 140, 30);

        jLabel87.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setText("Ora Plecare");
        rezervareBilet.add(jLabel87);
        jLabel87.setBounds(530, 60, 100, 30);

        comboDataPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboDataPlecare.setForeground(new java.awt.Color(0, 0, 0));
        comboDataPlecare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDataPlecareActionPerformed(evt);
            }
        });
        rezervareBilet.add(comboDataPlecare);
        comboDataPlecare.setBounds(250, 110, 180, 30);

        comboOraPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboOraPlecare.setForeground(new java.awt.Color(0, 0, 0));
        rezervareBilet.add(comboOraPlecare);
        comboOraPlecare.setBounds(530, 110, 70, 27);

        jLabel88.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("Pret");
        rezervareBilet.add(jLabel88);
        jLabel88.setBounds(270, 260, 100, 30);

        comboDataSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboDataSosire.setForeground(new java.awt.Color(0, 0, 0));
        comboDataSosire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDataSosireActionPerformed(evt);
            }
        });
        rezervareBilet.add(comboDataSosire);
        comboDataSosire.setBounds(50, 210, 170, 30);

        jLabel89.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("Data Plecare");
        rezervareBilet.add(jLabel89);
        jLabel89.setBounds(260, 70, 100, 30);

        jLabel90.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Nr. Pers");
        rezervareBilet.add(jLabel90);
        jLabel90.setBounds(90, 260, 80, 30);

        comboNrPersBilet.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboNrPersBilet.setForeground(new java.awt.Color(0, 0, 0));
        comboNrPersBilet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4", "3", "2", "1" }));
        rezervareBilet.add(comboNrPersBilet);
        comboNrPersBilet.setBounds(90, 310, 70, 30);

        jLabel91.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("Data Sosire");
        rezervareBilet.add(jLabel91);
        jLabel91.setBounds(70, 170, 100, 30);

        comboDestinatie1.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboDestinatie1.setForeground(new java.awt.Color(0, 0, 0));
        comboDestinatie1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDestinatie1ActionPerformed(evt);
            }
        });
        rezervareBilet.add(comboDestinatie1);
        comboDestinatie1.setBounds(50, 110, 140, 30);

        jLabel92.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 0));
        jLabel92.setText("Furnizor");
        rezervareBilet.add(jLabel92);
        jLabel92.setBounds(470, 160, 100, 30);

        pretBilet.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        pretBilet.setForeground(new java.awt.Color(0, 0, 0));
        pretBilet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pretBiletMouseClicked(evt);
            }
        });
        pretBilet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pretBiletActionPerformed(evt);
            }
        });
        rezervareBilet.add(pretBilet);
        pretBilet.setBounds(250, 310, 130, 30);

        jButton13.setBackground(new java.awt.Color(0, 102, 102));
        jButton13.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Inapoi");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        rezervareBilet.add(jButton13);
        jButton13.setBounds(70, 380, 110, 40);

        jButton14.setBackground(new java.awt.Color(0, 102, 102));
        jButton14.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Urmatorul pas");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        rezervareBilet.add(jButton14);
        jButton14.setBounds(460, 380, 150, 40);

        comboOraSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboOraSosire.setForeground(new java.awt.Color(0, 0, 0));
        rezervareBilet.add(comboOraSosire);
        comboOraSosire.setBounds(310, 207, 70, 30);

        jLabel93.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 0));
        jLabel93.setText("Ora Sosire");
        rezervareBilet.add(jLabel93);
        jLabel93.setBounds(300, 160, 80, 30);

        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/clientbg.jpg"))); // NOI18N
        rezervareBilet.add(jLabel103);
        jLabel103.setBounds(0, 0, 710, 530);

        getContentPane().add(rezervareBilet);
        rezervareBilet.setBounds(0, 0, 700, 520);

        pagPrincipalaClient.setLayout(null);

        labelWelcome.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        labelWelcome.setForeground(new java.awt.Color(0, 0, 0));
        labelWelcome.setText("BUN VENIT!");
        pagPrincipalaClient.add(labelWelcome);
        labelWelcome.setBounds(420, 70, 100, 20);

        butonCautareSejur.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareSejur.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareSejur.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareSejur.setText("Sejur");
        butonCautareSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareSejurActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareSejur);
        butonCautareSejur.setBounds(270, 160, 110, 40);

        butonCautareCircuit2.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareCircuit2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareCircuit2.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit2.setText("Circuit");
        butonCautareCircuit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuit2ActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareCircuit2);
        butonCautareCircuit2.setBounds(420, 160, 110, 40);

        butonCautareCircuit3.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareCircuit3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareCircuit3.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit3.setText("Rezervari");
        butonCautareCircuit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuit3ActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareCircuit3);
        butonCautareCircuit3.setBounds(430, 240, 110, 40);

        butonCautareCircuit4.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareCircuit4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareCircuit4.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit4.setText("Bilete");
        butonCautareCircuit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuit4ActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareCircuit4);
        butonCautareCircuit4.setBounds(580, 160, 110, 40);

        butonCautareCircuit5.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareCircuit5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareCircuit5.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit5.setText("Logout");
        butonCautareCircuit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuit5ActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareCircuit5);
        butonCautareCircuit5.setBounds(560, 430, 110, 40);

        butonCautareCircuit6.setBackground(new java.awt.Color(0, 51, 51));
        butonCautareCircuit6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCautareCircuit6.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit6.setText("Contul meu");
        butonCautareCircuit6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuit6ActionPerformed(evt);
            }
        });
        pagPrincipalaClient.add(butonCautareCircuit6);
        butonCautareCircuit6.setBounds(320, 430, 110, 40);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClient.jpg"))); // NOI18N
        pagPrincipalaClient.add(jLabel4);
        jLabel4.setBounds(0, 0, 700, 520);

        getContentPane().add(pagPrincipalaClient);
        pagPrincipalaClient.setBounds(0, 0, 700, 520);

        vizualizareSejur.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("SEJURURI");
        vizualizareSejur.add(jLabel1);
        jLabel1.setBounds(310, 50, 90, 40);

        tabelSejur.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tabelSejur.setForeground(new java.awt.Color(0, 0, 0));
        tabelSejur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Plecare", "Destinatie", "Data plecare", "Data sosire"
            }
        ));
        jScrollPane1.setViewportView(tabelSejur);

        vizualizareSejur.add(jScrollPane1);
        jScrollPane1.setBounds(0, 160, 700, 120);

        inapoiSejur.setBackground(new java.awt.Color(0, 102, 102));
        inapoiSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        inapoiSejur.setForeground(new java.awt.Color(255, 255, 255));
        inapoiSejur.setText("Inapoi");
        inapoiSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inapoiSejurActionPerformed(evt);
            }
        });
        vizualizareSejur.add(inapoiSejur);
        inapoiSejur.setBounds(540, 400, 100, 40);

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        vizualizareSejur.add(jLabel47);
        jLabel47.setBounds(0, -10, 720, 530);

        getContentPane().add(vizualizareSejur);
        vizualizareSejur.setBounds(0, 0, 700, 520);

        vizualizareCircuit.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("CIRCUITE");
        vizualizareCircuit.add(jLabel3);
        jLabel3.setBounds(300, 30, 90, 40);

        comboCircuite.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        comboCircuite.setForeground(new java.awt.Color(0, 0, 0));
        comboCircuite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCircuiteActionPerformed(evt);
            }
        });
        vizualizareCircuit.add(comboCircuite);
        comboCircuite.setBounds(150, 110, 170, 30);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Denumire");
        vizualizareCircuit.add(jLabel6);
        jLabel6.setBounds(60, 110, 90, 30);

        tabelOrase.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tabelOrase.setForeground(new java.awt.Color(0, 0, 0));
        tabelOrase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Oras", "Tara"
            }
        ));
        jScrollPane2.setViewportView(tabelOrase);

        vizualizareCircuit.add(jScrollPane2);
        jScrollPane2.setBounds(150, 190, 430, 100);

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Inapoi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        vizualizareCircuit.add(jButton1);
        jButton1.setBounds(520, 360, 90, 40);

        butonCautareCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonCautareCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautareCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonCautareCircuit.setText("Cautare");
        butonCautareCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautareCircuitActionPerformed(evt);
            }
        });
        vizualizareCircuit.add(butonCautareCircuit);
        butonCautareCircuit.setBounds(120, 360, 100, 40);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClouds.jpg"))); // NOI18N
        vizualizareCircuit.add(jLabel2);
        jLabel2.setBounds(0, 0, 720, 570);

        getContentPane().add(vizualizareCircuit);
        vizualizareCircuit.setBounds(0, 0, 700, 520);

        vizualizareCazare.setLayout(null);

        hotelTropical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel1.jpg"))); // NOI18N
        vizualizareCazare.add(hotelTropical);
        hotelTropical.setBounds(30, 50, 200, 170);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel6.jpg"))); // NOI18N
        vizualizareCazare.add(jLabel8);
        jLabel8.setBounds(260, 50, 180, 170);

        jRadioButton3.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton3.setText("Germania");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        vizualizareCazare.add(jRadioButton3);
        jRadioButton3.setBounds(290, 230, 130, 28);

        jRadioButton2.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton2.setText("Tropical");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        vizualizareCazare.add(jRadioButton2);
        jRadioButton2.setBounds(80, 230, 100, 28);

        jRadioButton1.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton1.setText("Franta");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        vizualizareCazare.add(jRadioButton1);
        jRadioButton1.setBounds(530, 230, 130, 28);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel2.jpg"))); // NOI18N
        vizualizareCazare.add(jLabel7);
        jLabel7.setBounds(480, 50, 200, 170);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel7.jpg"))); // NOI18N
        vizualizareCazare.add(jLabel10);
        jLabel10.setBounds(430, 260, 240, 180);

        jRadioButton5.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jRadioButton5.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton5.setText("Turcia");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        vizualizareCazare.add(jRadioButton5);
        jRadioButton5.setBounds(500, 450, 130, 28);

        jRadioButton4.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jRadioButton4.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton4.setText("Norvegia");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        vizualizareCazare.add(jRadioButton4);
        jRadioButton4.setBounds(160, 440, 100, 28);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel5.jpg"))); // NOI18N
        vizualizareCazare.add(jLabel9);
        jLabel9.setBounds(80, 260, 296, 170);

        jLabel11.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("HOTELURI");
        vizualizareCazare.add(jLabel11);
        jLabel11.setBounds(290, 20, 100, 22);

        getContentPane().add(vizualizareCazare);
        vizualizareCazare.setBounds(0, 0, 710, 520);

        tropicalHotels.setLayout(null);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical3.png"))); // NOI18N
        tropicalHotels.add(jLabel15);
        jLabel15.setBounds(10, 30, 190, 210);

        jLabel12.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("TROPICAL");
        tropicalHotels.add(jLabel12);
        jLabel12.setBounds(310, 40, 100, 22);

        comboCazareTropical.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboCazareTropical.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareTropical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareTropicalActionPerformed(evt);
            }
        });
        tropicalHotels.add(comboCazareTropical);
        comboCazareTropical.setBounds(300, 110, 180, 27);

        jLabel13.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Cazare");
        tropicalHotels.add(jLabel13);
        jLabel13.setBounds(220, 120, 70, 17);

        jLabel14.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Numar Stele");
        tropicalHotels.add(jLabel14);
        jLabel14.setBounds(210, 170, 110, 17);

        comboNumarStele.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboNumarStele.setForeground(new java.awt.Color(0, 0, 0));
        tropicalHotels.add(comboNumarStele);
        comboNumarStele.setBounds(340, 160, 90, 30);

        comboCamere.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboCamere.setForeground(new java.awt.Color(0, 0, 0));
        tropicalHotels.add(comboCamere);
        comboCamere.setBounds(300, 220, 180, 27);

        jLabel16.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Camere");
        tropicalHotels.add(jLabel16);
        jLabel16.setBounds(220, 220, 70, 17);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical1.jpg"))); // NOI18N
        tropicalHotels.add(jLabel17);
        jLabel17.setBounds(140, 280, 320, 190);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical2.jpg"))); // NOI18N
        tropicalHotels.add(jLabel18);
        jLabel18.setBounds(520, 20, 160, 340);

        inapoiHoteluri.setBackground(new java.awt.Color(0, 102, 102));
        inapoiHoteluri.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        inapoiHoteluri.setForeground(new java.awt.Color(255, 255, 255));
        inapoiHoteluri.setText("Inapoi");
        inapoiHoteluri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inapoiHoteluriActionPerformed(evt);
            }
        });
        tropicalHotels.add(inapoiHoteluri);
        inapoiHoteluri.setBounds(570, 400, 80, 40);

        getContentPane().add(tropicalHotels);
        tropicalHotels.setBounds(0, 0, 700, 520);

        GermaniaHotels.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("GERMANIA");
        GermaniaHotels.add(jLabel19);
        jLabel19.setBounds(180, 40, 110, 20);

        jLabel20.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Cazare");
        GermaniaHotels.add(jLabel20);
        jLabel20.setBounds(70, 100, 70, 20);

        comboGermania.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboGermania.setForeground(new java.awt.Color(0, 0, 0));
        comboGermania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGermaniaActionPerformed(evt);
            }
        });
        GermaniaHotels.add(comboGermania);
        comboGermania.setBounds(160, 100, 150, 30);

        nrSteleGermania.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrSteleGermania.setForeground(new java.awt.Color(0, 0, 0));
        GermaniaHotels.add(nrSteleGermania);
        nrSteleGermania.setBounds(190, 150, 90, 30);

        jLabel21.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Numar Stele");
        GermaniaHotels.add(jLabel21);
        jLabel21.setBounds(60, 150, 110, 20);

        jLabel22.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Camere");
        GermaniaHotels.add(jLabel22);
        jLabel22.setBounds(70, 200, 70, 20);

        comboCamereGermania.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereGermania.setForeground(new java.awt.Color(0, 0, 0));
        GermaniaHotels.add(comboCamereGermania);
        comboCamereGermania.setBounds(160, 200, 150, 30);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/germania1.jpg"))); // NOI18N
        GermaniaHotels.add(jLabel23);
        jLabel23.setBounds(120, 270, 290, 190);

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/germania2.jpg"))); // NOI18N
        GermaniaHotels.add(jLabel24);
        jLabel24.setBounds(490, 70, 160, 260);

        inapoiGermania.setBackground(new java.awt.Color(0, 102, 102));
        inapoiGermania.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        inapoiGermania.setForeground(new java.awt.Color(255, 255, 255));
        inapoiGermania.setText("Inapoi");
        inapoiGermania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inapoiGermaniaActionPerformed(evt);
            }
        });
        GermaniaHotels.add(inapoiGermania);
        inapoiGermania.setBounds(530, 380, 90, 40);

        getContentPane().add(GermaniaHotels);
        GermaniaHotels.setBounds(0, 0, 700, 520);

        FrantaHotels.setLayout(null);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/franta1.jpg"))); // NOI18N
        FrantaHotels.add(jLabel30);
        jLabel30.setBounds(70, 290, 280, 180);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/franta2.jpg"))); // NOI18N
        FrantaHotels.add(jLabel31);
        jLabel31.setBounds(390, 70, 280, 240);

        butonInapoiFranta.setBackground(new java.awt.Color(0, 102, 102));
        butonInapoiFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonInapoiFranta.setForeground(new java.awt.Color(255, 255, 255));
        butonInapoiFranta.setText("Inapoi");
        butonInapoiFranta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonInapoiFrantaActionPerformed(evt);
            }
        });
        FrantaHotels.add(butonInapoiFranta);
        butonInapoiFranta.setBounds(530, 400, 90, 40);

        comboCamereFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereFranta.setForeground(new java.awt.Color(0, 0, 0));
        FrantaHotels.add(comboCamereFranta);
        comboCamereFranta.setBounds(160, 200, 160, 30);

        jLabel29.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Camere");
        FrantaHotels.add(jLabel29);
        jLabel29.setBounds(80, 210, 70, 20);

        jLabel28.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Numar Stele");
        FrantaHotels.add(jLabel28);
        jLabel28.setBounds(70, 160, 110, 20);

        nrSteleFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrSteleFranta.setForeground(new java.awt.Color(0, 0, 0));
        FrantaHotels.add(nrSteleFranta);
        nrSteleFranta.setBounds(200, 150, 80, 30);

        comboCazareFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareFranta.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareFranta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareFrantaActionPerformed(evt);
            }
        });
        FrantaHotels.add(comboCazareFranta);
        comboCazareFranta.setBounds(160, 100, 160, 30);

        jLabel27.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Cazare");
        FrantaHotels.add(jLabel27);
        jLabel27.setBounds(80, 110, 70, 20);

        jLabel26.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("FRANTA");
        FrantaHotels.add(jLabel26);
        jLabel26.setBounds(290, 20, 90, 22);

        getContentPane().add(FrantaHotels);
        FrantaHotels.setBounds(0, 0, 700, 520);

        NorvegiaHotels.setLayout(null);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/norvegia2.jpg"))); // NOI18N
        NorvegiaHotels.add(jLabel38);
        jLabel38.setBounds(70, 260, 284, 180);

        comboCamereNorvegia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereNorvegia.setForeground(new java.awt.Color(0, 0, 0));
        comboCamereNorvegia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCamereNorvegiaActionPerformed(evt);
            }
        });
        NorvegiaHotels.add(comboCamereNorvegia);
        comboCamereNorvegia.setBounds(140, 210, 150, 26);

        nrSteleNorvegia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrSteleNorvegia.setForeground(new java.awt.Color(0, 0, 0));
        NorvegiaHotels.add(nrSteleNorvegia);
        nrSteleNorvegia.setBounds(170, 150, 90, 25);

        comboCazareNorvegia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareNorvegia.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareNorvegia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareNorvegiaActionPerformed(evt);
            }
        });
        NorvegiaHotels.add(comboCazareNorvegia);
        comboCazareNorvegia.setBounds(140, 100, 150, 26);

        jLabel35.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Cazare");
        NorvegiaHotels.add(jLabel35);
        jLabel35.setBounds(40, 110, 70, 17);

        jLabel36.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Numar Stele");
        NorvegiaHotels.add(jLabel36);
        jLabel36.setBounds(40, 160, 100, 17);

        jLabel34.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Camere");
        NorvegiaHotels.add(jLabel34);
        jLabel34.setBounds(40, 210, 100, 17);

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/norvegia1.jpg"))); // NOI18N
        NorvegiaHotels.add(jLabel37);
        jLabel37.setBounds(340, 100, 370, 140);

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Inapoi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        NorvegiaHotels.add(jButton2);
        jButton2.setBounds(490, 330, 90, 40);

        jLabel33.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("NORVEGIA");
        NorvegiaHotels.add(jLabel33);
        jLabel33.setBounds(320, 40, 110, 22);

        getContentPane().add(NorvegiaHotels);
        NorvegiaHotels.setBounds(0, 0, 700, 520);

        TurciaHotels.setLayout(null);

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/turcia2.jpg"))); // NOI18N
        TurciaHotels.add(jLabel44);
        jLabel44.setBounds(50, 270, 380, 170);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/turcia1.jpg"))); // NOI18N
        TurciaHotels.add(jLabel45);
        jLabel45.setBounds(380, 80, 290, 170);

        comboCazareTurcia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareTurcia.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareTurcia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareTurciaActionPerformed(evt);
            }
        });
        TurciaHotels.add(comboCazareTurcia);
        comboCazareTurcia.setBounds(160, 100, 150, 30);

        nrSteleTurcia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrSteleTurcia.setForeground(new java.awt.Color(0, 0, 0));
        TurciaHotels.add(nrSteleTurcia);
        nrSteleTurcia.setBounds(190, 150, 90, 30);

        comboCamereTurcia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereTurcia.setForeground(new java.awt.Color(0, 0, 0));
        TurciaHotels.add(comboCamereTurcia);
        comboCamereTurcia.setBounds(160, 200, 150, 30);

        jLabel42.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Camere");
        TurciaHotels.add(jLabel42);
        jLabel42.setBounds(70, 200, 70, 20);

        jLabel41.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Numar Stele");
        TurciaHotels.add(jLabel41);
        jLabel41.setBounds(60, 150, 110, 20);

        jLabel40.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Cazare");
        TurciaHotels.add(jLabel40);
        jLabel40.setBounds(70, 100, 70, 20);

        jLabel43.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("TURCIA");
        TurciaHotels.add(jLabel43);
        jLabel43.setBounds(320, 30, 80, 40);

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Inapoi");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        TurciaHotels.add(jButton3);
        jButton3.setBounds(510, 340, 90, 40);

        getContentPane().add(TurciaHotels);
        TurciaHotels.setBounds(0, 0, 700, 520);

        cautareSejur.setLayout(null);

        jLabel73.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText("Cauta Sejur");
        cautareSejur.add(jLabel73);
        jLabel73.setBounds(280, 40, 120, 40);

        radioOrasPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioOrasPlecare.setForeground(new java.awt.Color(0, 0, 0));
        radioOrasPlecare.setText("Oras Plecare");
        cautareSejur.add(radioOrasPlecare);
        radioOrasPlecare.setBounds(100, 110, 130, 28);

        radioOrasSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioOrasSosire.setForeground(new java.awt.Color(0, 0, 0));
        radioOrasSosire.setText("Destinatie");
        cautareSejur.add(radioOrasSosire);
        radioOrasSosire.setBounds(300, 110, 110, 28);

        radioDataPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioDataPlecare.setForeground(new java.awt.Color(0, 0, 0));
        radioDataPlecare.setText("Data plecare");
        cautareSejur.add(radioDataPlecare);
        radioDataPlecare.setBounds(480, 110, 130, 28);

        fieldCautaSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        fieldCautaSejur.setForeground(new java.awt.Color(0, 0, 0));
        fieldCautaSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCautaSejurActionPerformed(evt);
            }
        });
        cautareSejur.add(fieldCautaSejur);
        fieldCautaSejur.setBounds(260, 160, 180, 30);

        butonCautaSejur.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaSejur.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaSejur.setText("Cauta");
        butonCautaSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaSejurActionPerformed(evt);
            }
        });
        cautareSejur.add(butonCautaSejur);
        butonCautaSejur.setBounds(300, 210, 100, 40);

        tabelCautaSejur.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelCautaSejur.setForeground(new java.awt.Color(0, 0, 0));
        tabelCautaSejur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Oras plecare", "Destinatie", "Data plecare", "Data sosire"
            }
        ));
        jScrollPane5.setViewportView(tabelCautaSejur);

        cautareSejur.add(jScrollPane5);
        jScrollPane5.setBounds(0, 280, 700, 130);

        jButton10.setBackground(new java.awt.Color(0, 102, 102));
        jButton10.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Inapoi");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        cautareSejur.add(jButton10);
        jButton10.setBounds(540, 430, 100, 40);

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/sejur1.jpg"))); // NOI18N
        cautareSejur.add(jLabel53);
        jLabel53.setBounds(0, 0, 700, 540);

        getContentPane().add(cautareSejur);
        cautareSejur.setBounds(0, 0, 700, 520);

        cautareCircuit.setLayout(null);

        jLabel69.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Cauta Circuit");
        cautareCircuit.add(jLabel69);
        jLabel69.setBounds(280, 20, 120, 40);

        radioDenumireCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioDenumireCircuit.setForeground(new java.awt.Color(0, 0, 0));
        radioDenumireCircuit.setText("Denumire");
        cautareCircuit.add(radioDenumireCircuit);
        radioDenumireCircuit.setBounds(100, 110, 102, 28);

        radioOras.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioOras.setForeground(new java.awt.Color(0, 0, 0));
        radioOras.setText("Oras");
        cautareCircuit.add(radioOras);
        radioOras.setBounds(310, 110, 80, 28);

        radioTara.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioTara.setForeground(new java.awt.Color(0, 0, 0));
        radioTara.setText("Tara");
        cautareCircuit.add(radioTara);
        radioTara.setBounds(480, 110, 130, 28);

        fieldCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        fieldCircuit.setForeground(new java.awt.Color(0, 0, 0));
        fieldCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCircuitActionPerformed(evt);
            }
        });
        cautareCircuit.add(fieldCircuit);
        fieldCircuit.setBounds(260, 160, 180, 30);

        butonCautaCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaCircuit.setText("Cauta");
        butonCautaCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaCircuitActionPerformed(evt);
            }
        });
        cautareCircuit.add(butonCautaCircuit);
        butonCautaCircuit.setBounds(300, 210, 100, 40);

        tabelCircuit.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelCircuit.setForeground(new java.awt.Color(0, 0, 0));
        tabelCircuit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Denumire", "Orase", "Tari"
            }
        ));
        jScrollPane4.setViewportView(tabelCircuit);

        cautareCircuit.add(jScrollPane4);
        jScrollPane4.setBounds(0, 280, 700, 130);

        jButton9.setBackground(new java.awt.Color(0, 102, 102));
        jButton9.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Inapoi");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        cautareCircuit.add(jButton9);
        jButton9.setBounds(540, 430, 100, 40);

        jLabel70.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Filtrare dupa");
        cautareCircuit.add(jLabel70);
        jLabel70.setBounds(30, 60, 120, 40);

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClouds.jpg"))); // NOI18N
        cautareCircuit.add(jLabel54);
        jLabel54.setBounds(0, 0, 700, 520);

        getContentPane().add(cautareCircuit);
        cautareCircuit.setBounds(0, 0, 700, 520);

        cautareZbor.setLayout(null);

        jLabel25.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Filtrare zbor dupa");
        cautareZbor.add(jLabel25);
        jLabel25.setBounds(50, 50, 180, 22);

        Destinatie.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        Destinatie.setForeground(new java.awt.Color(0, 0, 0));
        Destinatie.setText("Destinatie");
        cautareZbor.add(Destinatie);
        Destinatie.setBounds(50, 90, 130, 28);

        dataPlecareRadio.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        dataPlecareRadio.setForeground(new java.awt.Color(0, 0, 0));
        dataPlecareRadio.setText("Data Plecare");
        cautareZbor.add(dataPlecareRadio);
        dataPlecareRadio.setBounds(190, 90, 140, 28);

        oraPlecareButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraPlecareButon.setForeground(new java.awt.Color(0, 0, 0));
        oraPlecareButon.setText("Ora Plecare");
        cautareZbor.add(oraPlecareButon);
        oraPlecareButon.setBounds(350, 90, 140, 28);

        oraSosireButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraSosireButon.setForeground(new java.awt.Color(0, 0, 0));
        oraSosireButon.setText("Ora Sosire");
        cautareZbor.add(oraSosireButon);
        oraSosireButon.setBounds(500, 90, 130, 28);

        butonCautaFiltrare.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaFiltrare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaFiltrare.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaFiltrare.setText("Cauta");
        butonCautaFiltrare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaFiltrareActionPerformed(evt);
            }
        });
        cautareZbor.add(butonCautaFiltrare);
        butonCautaFiltrare.setBounds(430, 140, 80, 33);

        fieldFiltrare.setFont(new java.awt.Font("Lucida Bright", 0, 12)); // NOI18N
        fieldFiltrare.setForeground(new java.awt.Color(0, 0, 0));
        cautareZbor.add(fieldFiltrare);
        fieldFiltrare.setBounds(150, 140, 200, 30);

        tabelZboruri1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelZboruri1.setForeground(new java.awt.Color(0, 0, 0));
        tabelZboruri1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Destinatie", "Data plecare", "Data sosire", "Ora plecare", "Ora sosire", "Avion"
            }
        ));
        jScrollPane6.setViewportView(tabelZboruri1);

        cautareZbor.add(jScrollPane6);
        jScrollPane6.setBounds(0, 220, 700, 130);

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Rezerva Acum!");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        cautareZbor.add(jButton5);
        jButton5.setBounds(250, 380, 160, 50);

        jButton7.setBackground(new java.awt.Color(0, 102, 102));
        jButton7.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Inapoi");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        cautareZbor.add(jButton7);
        jButton7.setBounds(540, 450, 90, 33);

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/clientbg.jpg"))); // NOI18N
        cautareZbor.add(jLabel55);
        jLabel55.setBounds(0, 0, 700, 540);

        getContentPane().add(cautareZbor);
        cautareZbor.setBounds(0, 0, 700, 520);

        rezervarileMele.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Sejururi");
        rezervarileMele.add(jLabel5);
        jLabel5.setBounds(40, 230, 80, 20);

        jLabel32.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Rezervari");
        rezervarileMele.add(jLabel32);
        jLabel32.setBounds(310, 30, 90, 20);

        jLabel39.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Circuite");
        rezervarileMele.add(jLabel39);
        jLabel39.setBounds(40, 60, 80, 20);

        tabelRezervariCircuite.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelRezervariCircuite.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Denumire", "Pret", "Data_plecare"
            }
        ));
        jScrollPane3.setViewportView(tabelRezervariCircuite);

        rezervarileMele.add(jScrollPane3);
        jScrollPane3.setBounds(0, 90, 700, 130);

        tabelRezervariSejururi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelRezervariSejururi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plecare", "Destinatie", "Data plecare", "Data sosire", "Pret"
            }
        ));
        jScrollPane7.setViewportView(tabelRezervariSejururi);

        rezervarileMele.add(jScrollPane7);
        jScrollPane7.setBounds(0, 270, 700, 130);

        butonAnulareRezervare.setBackground(new java.awt.Color(0, 102, 102));
        butonAnulareRezervare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAnulareRezervare.setForeground(new java.awt.Color(255, 255, 255));
        butonAnulareRezervare.setText("Anuleaza rezervare");
        butonAnulareRezervare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAnulareRezervareActionPerformed(evt);
            }
        });
        rezervarileMele.add(butonAnulareRezervare);
        butonAnulareRezervare.setBounds(100, 420, 190, 50);

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Inapoi");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        rezervarileMele.add(jButton6);
        jButton6.setBounds(500, 430, 90, 40);

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClouds.jpg"))); // NOI18N
        rezervarileMele.add(jLabel56);
        jLabel56.setBounds(0, 0, 700, 520);

        getContentPane().add(rezervarileMele);
        rezervarileMele.setBounds(0, 0, 700, 520);

        contulMeu.setLayout(null);

        imgProfilAng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/imgProfil.png"))); // NOI18N
        contulMeu.add(imgProfilAng);
        imgProfilAng.setBounds(30, 20, 116, 120);

        titluContulMeu.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        titluContulMeu.setForeground(new java.awt.Color(0, 0, 0));
        titluContulMeu.setText("CONTUL MEU");
        contulMeu.add(titluContulMeu);
        titluContulMeu.setBounds(290, 70, 120, 20);

        numeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeContActionPerformed(evt);
            }
        });
        contulMeu.add(numeCont);
        numeCont.setBounds(130, 190, 140, 24);

        jLabel46.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Nume");
        contulMeu.add(jLabel46);
        jLabel46.setBounds(50, 190, 60, 17);

        jLabel48.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Prenume");
        contulMeu.add(jLabel48);
        jLabel48.setBounds(40, 240, 80, 17);

        prenumeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenumeContActionPerformed(evt);
            }
        });
        contulMeu.add(prenumeCont);
        prenumeCont.setBounds(130, 240, 140, 24);

        telefonCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonContActionPerformed(evt);
            }
        });
        contulMeu.add(telefonCont);
        telefonCont.setBounds(130, 290, 140, 24);

        jLabel49.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Telefon");
        contulMeu.add(jLabel49);
        jLabel49.setBounds(50, 300, 60, 17);

        emailCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailContActionPerformed(evt);
            }
        });
        contulMeu.add(emailCont);
        emailCont.setBounds(440, 190, 170, 24);

        jLabel50.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Email");
        contulMeu.add(jLabel50);
        jLabel50.setBounds(360, 190, 70, 17);

        jLabel51.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Cont");
        contulMeu.add(jLabel51);
        jLabel51.setBounds(360, 240, 50, 17);

        contCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contContActionPerformed(evt);
            }
        });
        contulMeu.add(contCont);
        contCont.setBounds(440, 240, 140, 24);

        parolaCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parolaContActionPerformed(evt);
            }
        });
        contulMeu.add(parolaCont);
        parolaCont.setBounds(440, 290, 140, 24);

        jLabel52.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Parola");
        contulMeu.add(jLabel52);
        jLabel52.setBounds(360, 290, 60, 20);

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

        getContentPane().add(contulMeu);
        contulMeu.setBounds(0, 0, 700, 520);

        detaliiZbor.setLayout(null);

        jLabel94.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 0));
        jLabel94.setText("Aeronava");
        detaliiZbor.add(jLabel94);
        jLabel94.setBounds(320, 240, 80, 30);

        jLabel95.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setText("Verifica detaliile zborului");
        detaliiZbor.add(jLabel95);
        jLabel95.setBounds(220, 20, 210, 40);

        jLabel96.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 0));
        jLabel96.setText("Destinatie");
        detaliiZbor.add(jLabel96);
        jLabel96.setBounds(30, 120, 90, 30);

        jLabel97.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(0, 0, 0));
        jLabel97.setText("Ora Plecare");
        detaliiZbor.add(jLabel97);
        jLabel97.setBounds(320, 120, 100, 30);

        jLabel98.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 0));
        jLabel98.setText("Ora Sosire");
        detaliiZbor.add(jLabel98);
        jLabel98.setBounds(30, 180, 80, 30);

        jLabel99.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(0, 0, 0));
        jLabel99.setText("Compania aeriana");
        detaliiZbor.add(jLabel99);
        jLabel99.setBounds(300, 180, 140, 30);

        jLabel100.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 0, 0));
        jLabel100.setText("Nr. Zbor");
        detaliiZbor.add(jLabel100);
        jLabel100.setBounds(30, 240, 110, 30);

        firmaZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        firmaZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(firmaZbor);
        firmaZbor.setBounds(440, 180, 130, 30);

        nrZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(nrZbor);
        nrZbor.setBounds(160, 240, 70, 30);

        destinatieZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        destinatieZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(destinatieZbor);
        destinatieZbor.setBounds(130, 120, 130, 30);

        transportZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        transportZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(transportZbor);
        transportZbor.setBounds(440, 240, 130, 30);

        oraSosireZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraSosireZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(oraSosireZbor);
        oraSosireZbor.setBounds(160, 180, 70, 30);

        oraPlecareZbor.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        oraPlecareZbor.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(oraPlecareZbor);
        oraPlecareZbor.setBounds(470, 120, 70, 30);

        jButton15.setBackground(new java.awt.Color(0, 102, 102));
        jButton15.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Rezerva Acum!");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        detaliiZbor.add(jButton15);
        jButton15.setBounds(390, 380, 150, 40);

        jButton16.setBackground(new java.awt.Color(0, 102, 102));
        jButton16.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Inapoi");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        detaliiZbor.add(jButton16);
        jButton16.setBounds(90, 380, 110, 40);

        jLabel101.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(0, 0, 0));
        jLabel101.setText("Pret");
        detaliiZbor.add(jLabel101);
        jLabel101.setBounds(30, 290, 110, 30);

        pretBiletFinal.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        pretBiletFinal.setForeground(new java.awt.Color(0, 0, 0));
        detaliiZbor.add(pretBiletFinal);
        pretBiletFinal.setBounds(160, 290, 70, 30);

        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/clientbg.jpg"))); // NOI18N
        detaliiZbor.add(jLabel102);
        jLabel102.setBounds(0, 0, 700, 520);

        getContentPane().add(detaliiZbor);
        detaliiZbor.setBounds(0, 0, 700, 520);

        VizualizareMeniu.setForeground(new java.awt.Color(51, 51, 51));
        VizualizareMeniu.setText("Vizualizare");
        VizualizareMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        sejurVizItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sejurVizItem.setForeground(new java.awt.Color(51, 51, 51));
        sejurVizItem.setText("Sejur");
        sejurVizItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sejurVizItemActionPerformed(evt);
            }
        });
        VizualizareMeniu.add(sejurVizItem);

        circuitVizItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        circuitVizItem.setForeground(new java.awt.Color(51, 51, 51));
        circuitVizItem.setText("Circuit");
        circuitVizItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circuitVizItemActionPerformed(evt);
            }
        });
        VizualizareMeniu.add(circuitVizItem);

        rezervariVizItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rezervariVizItem.setForeground(new java.awt.Color(51, 51, 51));
        rezervariVizItem.setText("Rezervarile mele");
        rezervariVizItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rezervariVizItemActionPerformed(evt);
            }
        });
        VizualizareMeniu.add(rezervariVizItem);

        cazareVizItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cazareVizItem.setForeground(new java.awt.Color(51, 51, 51));
        cazareVizItem.setText("Cazare");
        cazareVizItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cazareVizItemActionPerformed(evt);
            }
        });
        VizualizareMeniu.add(cazareVizItem);

        meniuClient.add(VizualizareMeniu);

        CautareMeniu.setForeground(new java.awt.Color(51, 51, 51));
        CautareMeniu.setText("Cautare");
        CautareMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        sejurCautItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sejurCautItem.setForeground(new java.awt.Color(51, 51, 51));
        sejurCautItem.setText("Sejur");
        sejurCautItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sejurCautItemActionPerformed(evt);
            }
        });
        CautareMeniu.add(sejurCautItem);

        bileteCautItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bileteCautItem.setForeground(new java.awt.Color(51, 51, 51));
        bileteCautItem.setText("Bilete Avion");
        bileteCautItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bileteCautItemActionPerformed(evt);
            }
        });
        CautareMeniu.add(bileteCautItem);

        circuitCautItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        circuitCautItem.setForeground(new java.awt.Color(51, 51, 51));
        circuitCautItem.setText("Circuit");
        circuitCautItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circuitCautItemActionPerformed(evt);
            }
        });
        CautareMeniu.add(circuitCautItem);

        meniuClient.add(CautareMeniu);

        jMenu1.setForeground(new java.awt.Color(51, 51, 51));
        jMenu1.setText("Rezervare");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        circuitRezervItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        circuitRezervItem.setForeground(new java.awt.Color(51, 51, 51));
        circuitRezervItem.setText("Circuit");
        circuitRezervItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circuitRezervItemActionPerformed(evt);
            }
        });
        jMenu1.add(circuitRezervItem);

        sejurRezervItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sejurRezervItem.setForeground(new java.awt.Color(51, 51, 51));
        sejurRezervItem.setText("Sejur");
        sejurRezervItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sejurRezervItemActionPerformed(evt);
            }
        });
        jMenu1.add(sejurRezervItem);

        bileteRezervItem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bileteRezervItem.setForeground(new java.awt.Color(51, 51, 51));
        bileteRezervItem.setText("Bilete");
        bileteRezervItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bileteRezervItemActionPerformed(evt);
            }
        });
        jMenu1.add(bileteRezervItem);

        meniuClient.add(jMenu1);

        setJMenuBar(meniuClient);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sejurRezervItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sejurRezervItemActionPerformed
       setPanelVisible(rezervareSejur);
    }//GEN-LAST:event_sejurRezervItemActionPerformed

    private void butonCautareCircuit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuit5ActionPerformed
       JFrame primaPagina = new PrimaPagina();
       primaPagina.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_butonCautareCircuit5ActionPerformed

    private void inapoiSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiSejurActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_inapoiSejurActionPerformed

    private void sejurVizItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sejurVizItemActionPerformed
        setPanelVisible(vizualizareSejur);
    }//GEN-LAST:event_sejurVizItemActionPerformed

    private void comboCircuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCircuiteActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelOrase.getModel();
        try{
            Connection con = getConnection();
            String sql = "select c.denumire, a.denumire, pc.valoare_circuit, s.denumire_sezon\n" +
                            "from circuit c, agentie a, pret_circuit pc, sezon s\n" +
                            "where c.cod_circuit = pc.circuit_cod_circuit\n" +
                            "and a.cod_agentie = pc.cod_agentie_circuit\n" +
                            "and pc.sezon_cod_Sezon = s.cod_sezon\n" +
                            "and c.denumire = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboCircuite.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            model.setRowCount(0);            
            while(rs.next()){
               model.addRow(new Object[]{""  + rs.getString("c.denumire"), rs.getInt("valoare_circuit"), rs.getString("denumire_sezon"), rs.getString("a.denumire")});
            }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele!");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_comboCircuiteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void circuitVizItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circuitVizItemActionPerformed
        setPanelVisible(vizualizareCircuit);
    }//GEN-LAST:event_circuitVizItemActionPerformed

    private void cazareVizItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cazareVizItemActionPerformed
        setPanelVisible(vizualizareCazare);
    }//GEN-LAST:event_cazareVizItemActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        setPanelVisible(GermaniaHotels);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        setPanelVisible(tropicalHotels);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        setPanelVisible(FrantaHotels);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        setPanelVisible(TurciaHotels);
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        setPanelVisible(NorvegiaHotels);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void comboCazareTropicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCazareTropicalActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nr_stele FROM cazare WHERE denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboCazareTropical.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboNumarStele.setText(rs.getString(1));
            }
            String altSql = "SELECT tip_camera FROM cazare, camera WHERE cazare.cod_cazare = camera.cod_cazare and denumire_cazare=? ";
            PreparedStatement pstm1 = con.prepareStatement(altSql);
            pstm1.setString(1, comboCazareTropical.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();
            comboCamere.removeAllItems();
            while(rs1.next()){
                comboCamere.addItem(rs1.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot vedea tipurile de camere!");
        }
    }//GEN-LAST:event_comboCazareTropicalActionPerformed

    private void inapoiHoteluriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiHoteluriActionPerformed
        setPanelVisible(vizualizareCazare);
        jRadioButton2.setSelected(false);
    }//GEN-LAST:event_inapoiHoteluriActionPerformed

    private void comboGermaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGermaniaActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nr_stele FROM cazare WHERE denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboGermania.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                nrSteleGermania.setText(rs.getString(1));
            }
            String altSql = "SELECT tip_camera FROM cazare, camera WHERE cazare.cod_cazare = camera.cod_cazare and denumire_cazare=? ";
            PreparedStatement pstm1 = con.prepareStatement(altSql);
            pstm1.setString(1, comboGermania.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();
            comboCamereGermania.removeAllItems();
            while(rs1.next()){
                comboCamereGermania.addItem(rs1.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot vedea tipurile de camere!");
        }
    }//GEN-LAST:event_comboGermaniaActionPerformed

    private void inapoiGermaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiGermaniaActionPerformed
        setPanelVisible(vizualizareCazare);
        jRadioButton3.setSelected(false);
    }//GEN-LAST:event_inapoiGermaniaActionPerformed

    private void butonInapoiFrantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiFrantaActionPerformed
        setPanelVisible(vizualizareCazare);
        jRadioButton1.setSelected(false);
    }//GEN-LAST:event_butonInapoiFrantaActionPerformed

    private void comboCazareFrantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCazareFrantaActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nr_stele FROM cazare WHERE denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboCazareFranta.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                nrSteleFranta.setText(rs.getString(1));
            }
            String altSql = "SELECT tip_camera FROM cazare, camera WHERE cazare.cod_cazare = camera.cod_cazare and denumire_cazare=? ";
            PreparedStatement pstm1 = con.prepareStatement(altSql);
            pstm1.setString(1, comboCazareFranta.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();
            comboCamereFranta.removeAllItems();
            while(rs1.next()){
                comboCamereFranta.addItem(rs1.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot vedea tipurile de camere!");
        }
    }//GEN-LAST:event_comboCazareFrantaActionPerformed

    private void comboCamereNorvegiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCamereNorvegiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCamereNorvegiaActionPerformed

    private void comboCazareNorvegiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCazareNorvegiaActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nr_stele FROM cazare WHERE denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboCazareNorvegia.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                nrSteleNorvegia.setText(rs.getString(1));
            }
            String altSql = "SELECT tip_camera FROM cazare, camera WHERE cazare.cod_cazare = camera.cod_cazare and denumire_cazare=? ";
            PreparedStatement pstm1 = con.prepareStatement(altSql);
            pstm1.setString(1, comboCazareNorvegia.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();
            comboCamereNorvegia.removeAllItems();
            while(rs1.next()){
                comboCamereNorvegia.addItem(rs1.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot vedea tipurile de camere!");
        }
    }//GEN-LAST:event_comboCazareNorvegiaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setPanelVisible(vizualizareCazare);
        jRadioButton4.setSelected(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void comboCazareTurciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCazareTurciaActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nr_stele FROM cazare WHERE denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboCazareTurcia.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                nrSteleTurcia.setText(rs.getString(1));
            }
            String altSql = "SELECT tip_camera FROM cazare, camera WHERE cazare.cod_cazare = camera.cod_cazare and denumire_cazare=? ";
            PreparedStatement pstm1 = con.prepareStatement(altSql);
            pstm1.setString(1, comboCazareTurcia.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();
            comboCamereTurcia.removeAllItems();
            while(rs1.next()){
                comboCamereTurcia.addItem(rs1.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot vedea tipurile de camere!");
        }
    }//GEN-LAST:event_comboCazareTurciaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        setPanelVisible(vizualizareCazare);
        jRadioButton5.setSelected(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void sejurCautItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sejurCautItemActionPerformed
        setPanelVisible(cautareSejur);
    }//GEN-LAST:event_sejurCautItemActionPerformed

    private void fieldCautaSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCautaSejurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCautaSejurActionPerformed

    private void butonCautaSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaSejurActionPerformed
        String text = fieldCautaSejur.getText();
        DefaultTableModel model = (DefaultTableModel) tabelCautaSejur.getModel();
        try{
            Connection con = getConnection();
            if(radioOrasPlecare.isSelected()){
                String sql = "SELECT oras_plecare, oras_sosire, data_plecare, data_sosire FROM sejur where oras_plecare=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)});
                }
                fieldCautaSejur.setText("");
                radioOrasPlecare.setSelected(false);
            }else if(radioOrasSosire.isSelected()){
                String sql = "SELECT oras_plecare, oras_sosire, data_plecare, data_sosire FROM sejur where oras_sosire=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)});
                }
                fieldCautaSejur.setText("");
                radioOrasSosire.setSelected(false);
            }
            else if(radioDataPlecare.isSelected()){
                String sql = "SELECT oras_plecare, oras_sosire, data_plecare, data_sosire FROM sejur where data_plecare=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)});
                }
                fieldCautaSejur.setText("");
                radioDataPlecare.setSelected(false);
            
            }

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se poate realiza cautarea!");
        }
    }//GEN-LAST:event_butonCautaSejurActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void fieldCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCircuitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCircuitActionPerformed

    private void butonCautaCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaCircuitActionPerformed
        String text = fieldCircuit.getText();
        DefaultTableModel model = (DefaultTableModel) tabelCircuit.getModel();
        try{
            Connection con = getConnection();
            if(radioDenumireCircuit.isSelected()){
                String sql = "SELECT denumire, nume_oras, tara from circuit, oras where circuit.cod_circuit=oras.cod_circuit and denumire=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3)});
                }
                fieldCircuit.setText("");
                radioDenumireCircuit.setSelected(false);
            }else if(radioOras.isSelected()){
                String sql = "SELECT denumire, nume_oras, tara from circuit, oras where circuit.cod_circuit=oras.cod_circuit and nume_oras=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3)});
                }
                fieldCircuit.setText("");
                radioOras.setSelected(false);
            }else if(radioTara.isSelected()){
                String sql = "SELECT denumire, nume_oras, tara from circuit, oras where circuit.cod_circuit=oras.cod_circuit and tara=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, text);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3)});
                }
                fieldCircuit.setText("");
                radioTara.setSelected(false);
            }

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se poate realiza cautarea!");
        }
    }//GEN-LAST:event_butonCautaCircuitActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void circuitCautItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circuitCautItemActionPerformed
        setPanelVisible(cautareCircuit);
    }//GEN-LAST:event_circuitCautItemActionPerformed

    private void butonCautaFiltrareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaFiltrareActionPerformed
        String textIntrodus = fieldFiltrare.getText();
        DefaultTableModel model = (DefaultTableModel) tabelZboruri1.getModel();
        try{
            Connection con = getConnection();
            String transport = "select den_avion from transport, zbor where transport.cod_transport = zbor.cod_transport";
            PreparedStatement stmt1 = con.prepareStatement(transport);
            ResultSet rs1 = stmt1.executeQuery();
            if(Destinatie.isSelected()==true){
                String sql = "SELECT * from zbor WHERE destinatie=?";
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try{
            DefaultTableModel model = (DefaultTableModel) tabelZboruri1.getModel();
            Connection con = getConnection();
            String sql = "SELECT cod_zbor FROM zbor WHERE destinatie=? AND  ora_plecarii=? and ora_sosirii=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            int randSelectat = tabelZboruri1.getSelectedRow();
            pstm.setString(1, model.getValueAt(randSelectat, 0).toString());                     
            pstm.setInt(2, Integer.parseInt(model.getValueAt(randSelectat, 3).toString()));
            pstm.setInt(3, Integer.parseInt(model.getValueAt(randSelectat, 4).toString()));
            String sql1 = "SELECT cod_client FROM client WHERE cont=?";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1,campUser.getText());
            ResultSet rs = pstm.executeQuery();
            ResultSet rs1 = pstm1.executeQuery();
             String sql3 = "INSERT INTO bilet values(?,?,1500, sysdate())";
             PreparedStatement pstm2 = con.prepareStatement(sql3);
            while(rs.next() && rs1.next()){
               pstm2.setInt(1, rs.getInt(1));
               pstm2.setInt(2,rs1.getInt(1));
                
            }
            pstm2.executeUpdate();
            JOptionPane.showMessageDialog(this,"Rezervare reusita!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Eroare rezervare");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void bileteRezervItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bileteRezervItemActionPerformed
        setPanelVisible(rezervareBilet);
    }//GEN-LAST:event_bileteRezervItemActionPerformed

    private void rezervariVizItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rezervariVizItemActionPerformed
        setPanelVisible(rezervarileMele);
    }//GEN-LAST:event_rezervariVizItemActionPerformed

    private void bileteCautItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bileteCautItemActionPerformed
        setPanelVisible(cautareZbor);
    }//GEN-LAST:event_bileteCautItemActionPerformed

    private void numeContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeContActionPerformed

    private void prenumeContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenumeContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenumeContActionPerformed

    private void telefonContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonContActionPerformed

    private void emailContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailContActionPerformed

    private void contContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contContActionPerformed

    private void parolaContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parolaContActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parolaContActionPerformed

    private void butonInapoiContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiContActionPerformed
        setPanelVisible(pagPrincipalaClient);
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
            String updateCont = "UPDATE angajat SET nume=?, prenume=?, telefon=?, email=?, cont=?, parola=? WHERE cod_angajat=?";
            PreparedStatement ps = conn.prepareStatement(updateCont);
            ps.setString(1, nume);
            ps.setString(2, prenume);
            ps.setString(3, telefon);
            ps.setString(4, email);
            ps.setString(5, cont);
            ps.setString(6, parola);
            ps.setInt(7,1);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Actualizare reusita!");

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Actualizare esuata!");
        }
    }//GEN-LAST:event_butonSalveazaActionPerformed

    private void butonCautareCircuit6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuit6ActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT nume,prenume,telefon,email,cont,parola FROM client where cont=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, campUser.getText());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                numeCont.setText(rs.getString("nume"));
                prenumeCont.setText(rs.getString("prenume"));
                telefonCont.setText(rs.getString("telefon"));
                emailCont.setText(rs.getString("email"));
                contCont.setText(rs.getString("cont"));
                parolaCont.setText(rs.getString("parola"));
            }
                    
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se pot incarca datele contului!");
        }
        setPanelVisible(contulMeu);
    }//GEN-LAST:event_butonCautareCircuit6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void butonAnulareRezervareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAnulareRezervareActionPerformed
        DefaultTableModel model1 = (DefaultTableModel) tabelRezervariCircuite.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tabelRezervariSejururi.getModel();
        try{
            Connection con = getConnection();
            if(tabelRezervariCircuite.getSelectedRowCount()!=0){
            String sql = "DELETE rezervare_cicuit FROM rezervare_cicuit,client,circuit WHERE rezervare_cicuit.circuit_cod_circuit=circuit.cod_circuit AND rezervare_cicuit.client_cod_client = client.cod_client AND circuit.denumire=? AND client.cont=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,tabelRezervariCircuite.getValueAt(tabelRezervariCircuite.getSelectedRow(),0).toString());            
            pstm.setString(2,campUser.getText());           
            pstm.executeUpdate();            
            JOptionPane.showMessageDialog(this,"Rezervarea a fost anulata!");
            model1.removeRow(tabelRezervariCircuite.getSelectedRow());
            }  if(tabelRezervariSejururi.getSelectedRowCount()!=0){
            String sql1 = "DELETE rezervare_sejur FROM rezervare_sejur,client,sejur WHERE rezervare_sejur.cod_sejur=sejur.cod_sejur AND rezervare_sejur.cod_client= client.cod_client AND sejur.data_plecare=? AND client.cont=?";
            PreparedStatement pstm1= con.prepareStatement(sql1);
            pstm1.setString(1,tabelRezervariSejururi.getValueAt(tabelRezervariSejururi.getSelectedRow(),2).toString());            
            pstm1.setString(2,campUser.getText());           
            pstm1.executeUpdate();
            JOptionPane.showMessageDialog(this,"Rezervarea a fost anulata!");           
            model2.removeRow(tabelRezervariSejururi.getSelectedRow());
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Nu se poate anula rezervarea!");
        }
                
    }//GEN-LAST:event_butonAnulareRezervareActionPerformed

    private void butonCautareCircuit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuit3ActionPerformed
        setPanelVisible(rezervarileMele);
    }//GEN-LAST:event_butonCautareCircuit3ActionPerformed

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

    private void anulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anulActionPerformed

    }//GEN-LAST:event_anulActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            Connection con = getConnection();
            String getClient = "SELECT cod_client FROM client WHERE cont=?";
            PreparedStatement pstm1 = con.prepareStatement(getClient);
            pstm1.setString(1, campUser.getText());
            ResultSet rs1 = pstm1.executeQuery();
            String getSejur = "SELECT cod_sejur FROM sejur WHERE oras_sosire=? AND oras_plecare=?";
            PreparedStatement pstm2 = con.prepareStatement(getSejur);
            pstm2.setString(1,comboDestinatia.getSelectedItem().toString());
            pstm2.setString(2,tabelOferte.getValueAt(tabelOferte.getSelectedRow(),0).toString());            
            ResultSet rs2 = pstm2.executeQuery();
            
            String adaugaRezervare = "INSERT INTO rezervare_sejur VALUES(?,?,NULL)";
            PreparedStatement pstm3 = con.prepareStatement(adaugaRezervare);
            while(rs1.next() && rs2.next()){
                pstm3.setInt(1,rs2.getInt(1));
                pstm3.setInt(2, rs1.getInt(1));
                pstm3.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this,"Rezervare reusita!");
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Rezervare esuata!");  
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void butonCautareSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareSejurActionPerformed
         setPanelVisible(rezervareSejur);
    }//GEN-LAST:event_butonCautareSejurActionPerformed

    private void comboDestinatiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDestinatiaActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(oras_plecare) FROM sejur WHERE oras_sosire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboDestinatia.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
//            comboPlecare.removeAllItems();
            while(rs.next() ){
         //       comboPlecare.addItem(rs.getString(1));
            } 
            String sql1 = "SELECT denumire_cazare FROM cazare,sejur WHERE cazare.cod_sejur=sejur.cod_sejur AND oras_sosire=?";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1,comboDestinatia.getSelectedItem().toString());
            ResultSet rs1 = pstm1.executeQuery();          
            while(rs1.next()){
          ///      comboCazare.addItem(rs1.getString(1));
            }       
            
     }  catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele de plecare!");
        }      
        
    }//GEN-LAST:event_comboDestinatiaActionPerformed

    private void comboDestinatiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDestinatiaItemStateChanged
         
    }//GEN-LAST:event_comboDestinatiaItemStateChanged

    private void comboDestinatia1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDestinatia1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDestinatia1ItemStateChanged

    private void comboDestinatia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDestinatia1ActionPerformed
        try{
            Connection con = getConnection();
            String getOrasPlecare = "SELECT DISTINCT(denumire) FROM circuit,oras WHERE circuit.cod_circuit = oras.cod_circuit AND tara=?";
            PreparedStatement pstm = con.prepareStatement(getOrasPlecare);
            pstm.setString(1, comboDestinatia1.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
            //    comboPlecare1.addItem(rs.getString(1));
            }               
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Circuite indisponibile!");
        }
    }//GEN-LAST:event_comboDestinatia1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try{
            Connection con = getConnection();
            String getClient = "SELECT cod_client FROM client WHERE cont=?";
            PreparedStatement pstm1 = con.prepareStatement(getClient);
            pstm1.setString(1, campUser.getText());
            ResultSet rs1 = pstm1.executeQuery();
            String getSejur = "SELECT cod_circuit FROM circuit WHERE denumire=?";
            PreparedStatement pstm2 = con.prepareStatement(getSejur);
            pstm2.setString(1,tabelOferteCircuite.getValueAt(tabelOferteCircuite.getSelectedRow(),0).toString());            
            ResultSet rs2 = pstm2.executeQuery();            
            String adaugaRezervare = "INSERT INTO rezervare_cicuit VALUES(?,?,NULL,sysdate())";
            PreparedStatement pstm3 = con.prepareStatement(adaugaRezervare);
            while(rs1.next() && rs2.next()){
                pstm3.setInt(1,rs2.getInt(1));
                pstm3.setInt(2, rs1.getInt(1));
                pstm3.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this,"Rezervare reusita!");
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Rezervare esuata!");   
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void circuitRezervItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circuitRezervItemActionPerformed
       setPanelVisible(rezervareCircuit);
    }//GEN-LAST:event_circuitRezervItemActionPerformed

    private void butonCautareCircuit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuit2ActionPerformed
       setPanelVisible(rezervareCircuit);
    }//GEN-LAST:event_butonCautareCircuit2ActionPerformed

    private void butonCautareCircuit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuit4ActionPerformed
        setPanelVisible(rezervareBilet);
    }//GEN-LAST:event_butonCautareCircuit4ActionPerformed

    private void comboDataPlecareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDataPlecareActionPerformed
     
    }//GEN-LAST:event_comboDataPlecareActionPerformed

    private void comboDataSosireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDataSosireActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDataSosireActionPerformed

    private void comboDestinatie1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDestinatie1ActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT data_plecarii,ora_plecarii, data_sosirii, ora_sosirii,firma  FROM zbor,furnizori,transport WHERE destinatie=? AND zbor.cod_transport = transport.cod_transport AND transport.cod_furnizor = furnizori.cod_furnizor";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboDestinatie1.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            comboDataPlecare.removeAllItems();
            comboOraPlecare.removeAllItems();
            comboDataSosire.removeAllItems();
            comboOraSosire.removeAllItems();
            comboFurnizori.removeAllItems();          
            while(rs.next()){
                comboDataPlecare.addItem(rs.getString(1));
                comboOraPlecare.addItem(rs.getString(2));
                comboDataSosire.addItem(rs.getString(3));
                comboOraSosire.addItem(rs.getString(4));
                comboFurnizori.addItem(rs.getString(5));
          
            }
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Date zbor indisponibile");  
            
        }
    }//GEN-LAST:event_comboDestinatie1ActionPerformed

    private void pretBiletMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pretBiletMouseClicked
       try{
           Connection con = getConnection();
           String getPretBilet = "SELECT pret FROM zbor WHERE destinatie=? and data_plecarii=? and data_sosirii=? and ora_plecarii=? and ora_sosirii=?";
           PreparedStatement pstm = con.prepareStatement(getPretBilet);
           pstm.setString(1,comboDestinatie1.getSelectedItem().toString());
           pstm.setString(2,comboDataPlecare.getSelectedItem().toString());
           pstm.setString(3, comboDataSosire.getSelectedItem().toString());
           pstm.setString(4, comboOraPlecare.getSelectedItem().toString());
           pstm.setString(5, comboOraSosire.getSelectedItem().toString());
           ResultSet rs = pstm.executeQuery();
           while(rs.next()){
               pretBilet.setText(Integer.toString(rs.getInt(1)*Integer.parseInt(comboNrPersBilet.getSelectedItem().toString())));
           }
       }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Pret indisponibil");  
            
        }
    }//GEN-LAST:event_pretBiletMouseClicked

    private void pretBiletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pretBiletActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pretBiletActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if(pretBilet.getText().equals("")){
             JOptionPane.showMessageDialog(this, "Introduceti toate datele!");  
        }else{
            destinatieZbor.setText(comboDestinatie1.getSelectedItem().toString());
            oraPlecareZbor.setText(comboOraPlecare.getSelectedItem().toString());
            oraSosireZbor.setText(comboOraSosire.getSelectedItem().toString());
            firmaZbor.setText(comboFurnizori.getSelectedItem().toString());
            pretBiletFinal.setText(pretBilet.getText());
           try{
            Connection con = getConnection();
            String sql = "SELECT cod_zbor,den_avion FROM transport,zbor WHERE transport.cod_transport = zbor.cod_transport and destinatie=? and data_plecarii=? and data_sosirii=? and ora_plecarii=? and ora_sosirii=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,comboDestinatie1.getSelectedItem().toString());
            pstm.setString(2,comboDataPlecare.getSelectedItem().toString());
            pstm.setString(3, comboDataSosire.getSelectedItem().toString());
            pstm.setString(4, comboOraPlecare.getSelectedItem().toString());
            pstm.setString(5, comboOraSosire.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                nrZbor.setText(Integer.toString(rs.getInt(1)));
                transportZbor.setText(rs.getString(2));
            }
            setPanelVisible(detaliiZbor);
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Rezervare indisponibila!");  
            
        }
        }   
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        try{
            Connection con = getConnection();
            String getCodClient = "SELECT cod_client FROM client WHERE cont=?";
            PreparedStatement pstm = con.prepareStatement(getCodClient);
            pstm.setString(1, campUser.getText());
            ResultSet rs = pstm.executeQuery();
            
            String sql = "INSERT INTO bilet VALUES(?,?,?,sysdate())";
            PreparedStatement pstm1 = con.prepareStatement(sql);
            pstm1.setInt(1, Integer.parseInt(nrZbor.getText()));
            pstm1.setInt(3, Integer.parseInt(pretBiletFinal.getText()));
            while(rs.next()){
                pstm1.setInt(2, rs.getInt(1));
                pstm1.executeUpdate();
            }
             JOptionPane.showMessageDialog(this, "Rezervare reusita!");  
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Rezervare esuata!");  
            
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        setPanelVisible(rezervareBilet);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void butonCautareCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuitActionPerformed
        setPanelVisible(cautareCircuit);
    }//GEN-LAST:event_butonCautareCircuitActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        setPanelVisible(pagPrincipalaClient);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void butonOferteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonOferteActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelOferte.getModel();        
      
        try{
            Connection con = getConnection();
            String data = anul.getSelectedItem().toString() + "-" +luna.getSelectedItem().toString()+ "-" + ziua.getSelectedItem().toString();
            String sql = "select s.oras_plecare,s.oras_sosire, s.data_plecare, s.data_sosire,\n" +
                            "ps.valoare_sejur,\n" +
                            "a.denumire,\n" +
                            "c.denumire_cazare\n" +
                            "from sejur s, pret_sejur ps, agentie a, cazare c\n" +
                            "where s.cod_sejur=ps.sejur_cod_sejur \n" +
                            "and ps.cod_agentie = a.cod_agentie\n" +
                            "and c.cod_sejur = s.cod_sejur\n" +
                            "and s.oras_sosire = ?\n" +
                            "and s.data_plecare = ?\n" +
                            "group by ps.valoare_sejur";
            
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboDestinatia.getSelectedItem().toString());
            pstm.setString(2,data);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                 model.addRow(new Object[]{""  + rs.getString("oras_plecare"), rs.getString("oras_sosire"), rs.getString("data_plecare"), rs.getString("data_sosire"),  rs.getString("denumire_cazare"), rs.getString("valoare_sejur"), rs.getString("denumire")});
            }           
        } catch (SQLException ex) {            
            JOptionPane.showMessageDialog(this,"Nu se pot accesa sejururile!");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_butonOferteActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelOferteCircuite.getModel();        
      
        try{
            Connection con = getConnection();
            String sql = "select c.denumire, pc.valoare_circuit, a.denumire, ca.denumire_Cazare, ca.nr_Stele\n" +
                            "from circuit c, pret_circuit pc, agentie a, cazare ca, oras o\n" +
                            "where c.cod_circuit = pc.circuit_cod_circuit\n" +
                            "and a.cod_agentie=pc.cod_agentie_circuit\n" +
                            "and c.cod_circuit = ca.cod_circuit\n" +
                            "and o.cod_circuit = c.cod_circuit\n" +
                            "and o.tara=?\n" +
                            "group by pc.valoare_circuit";
            
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboDestinatia1.getSelectedItem().toString());            
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                 model.addRow(new Object[]{""  + rs.getString("c.denumire"), rs.getString("denumire_cazare"), rs.getString("nr_stele"), rs.getString("valoare_circuit"),  rs.getString("a.denumire")});
            }           
        } catch (SQLException ex) {            
            JOptionPane.showMessageDialog(this,"Nu se pot accesa circuitele!");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

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
            java.util.logging.Logger.getLogger(PaginaClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu CautareMeniu;
    private javax.swing.JRadioButton Destinatie;
    private javax.swing.JPanel FrantaHotels;
    private javax.swing.JPanel GermaniaHotels;
    private javax.swing.JPanel NorvegiaHotels;
    private javax.swing.JPanel TurciaHotels;
    private javax.swing.JMenu VizualizareMeniu;
    private javax.swing.JComboBox<String> anul;
    private javax.swing.JMenuItem bileteCautItem;
    private javax.swing.JMenuItem bileteRezervItem;
    private javax.swing.JButton butonAnulareRezervare;
    private javax.swing.JButton butonCautaCircuit;
    private javax.swing.JButton butonCautaFiltrare;
    private javax.swing.JButton butonCautaSejur;
    private javax.swing.JButton butonCautareCircuit;
    private javax.swing.JButton butonCautareCircuit2;
    private javax.swing.JButton butonCautareCircuit3;
    private javax.swing.JButton butonCautareCircuit4;
    private javax.swing.JButton butonCautareCircuit5;
    private javax.swing.JButton butonCautareCircuit6;
    private javax.swing.JButton butonCautareSejur;
    private javax.swing.JButton butonInapoiCont;
    private javax.swing.JButton butonInapoiFranta;
    private javax.swing.JButton butonOferte;
    private javax.swing.JButton butonSalveaza;
    private javax.swing.JPanel cautareCircuit;
    private javax.swing.JPanel cautareSejur;
    private javax.swing.JPanel cautareZbor;
    private javax.swing.JMenuItem cazareVizItem;
    private javax.swing.JMenuItem circuitCautItem;
    private javax.swing.JMenuItem circuitRezervItem;
    private javax.swing.JMenuItem circuitVizItem;
    private javax.swing.JComboBox<String> comboCamere;
    private javax.swing.JComboBox<String> comboCamereFranta;
    private javax.swing.JComboBox<String> comboCamereGermania;
    private javax.swing.JComboBox<String> comboCamereNorvegia;
    private javax.swing.JComboBox<String> comboCamereTurcia;
    private javax.swing.JComboBox<String> comboCazareFranta;
    private javax.swing.JComboBox<String> comboCazareNorvegia;
    private javax.swing.JComboBox<String> comboCazareTropical;
    private javax.swing.JComboBox<String> comboCazareTurcia;
    private javax.swing.JComboBox<String> comboCircuite;
    private javax.swing.JComboBox<String> comboDataPlecare;
    private javax.swing.JComboBox<String> comboDataSosire;
    private javax.swing.JComboBox<String> comboDestinatia;
    private javax.swing.JComboBox<String> comboDestinatia1;
    private javax.swing.JComboBox<String> comboDestinatie1;
    private javax.swing.JComboBox<String> comboFurnizori;
    private javax.swing.JComboBox<String> comboGermania;
    private javax.swing.JComboBox<String> comboNrPersBilet;
    private javax.swing.JTextField comboNumarStele;
    private javax.swing.JComboBox<String> comboOraPlecare;
    private javax.swing.JComboBox<String> comboOraSosire;
    private javax.swing.JTextField contCont;
    private javax.swing.JPanel contulMeu;
    private javax.swing.JRadioButton dataPlecareRadio;
    private javax.swing.JTextField destinatieZbor;
    private javax.swing.JPanel detaliiZbor;
    private javax.swing.JTextField emailCont;
    private javax.swing.JTextField fieldCautaSejur;
    private javax.swing.JTextField fieldCircuit;
    private javax.swing.JTextField fieldFiltrare;
    private javax.swing.JTextField firmaZbor;
    private javax.swing.JLabel hotelTropical;
    private javax.swing.JLabel imgProfilAng;
    private javax.swing.JButton inapoiGermania;
    private javax.swing.JButton inapoiHoteluri;
    private javax.swing.JButton inapoiSejur;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JComboBox<String> luna;
    private javax.swing.JMenuBar meniuClient;
    private javax.swing.JTextField nrSteleFranta;
    private javax.swing.JTextField nrSteleGermania;
    private javax.swing.JTextField nrSteleNorvegia;
    private javax.swing.JTextField nrSteleTurcia;
    private javax.swing.JTextField nrZbor;
    private javax.swing.JTextField numeCont;
    private javax.swing.JRadioButton oraPlecareButon;
    private javax.swing.JTextField oraPlecareZbor;
    private javax.swing.JRadioButton oraSosireButon;
    private javax.swing.JTextField oraSosireZbor;
    private javax.swing.JPanel pagPrincipalaClient;
    private javax.swing.JTextField parolaCont;
    private javax.swing.JTextField prenumeCont;
    private javax.swing.JTextField pretBilet;
    private javax.swing.JTextField pretBiletFinal;
    private javax.swing.JRadioButton radioDataPlecare;
    private javax.swing.JRadioButton radioDenumireCircuit;
    private javax.swing.JRadioButton radioOras;
    private javax.swing.JRadioButton radioOrasPlecare;
    private javax.swing.JRadioButton radioOrasSosire;
    private javax.swing.JRadioButton radioTara;
    private javax.swing.JPanel rezervareBilet;
    private javax.swing.JPanel rezervareCircuit;
    private javax.swing.JPanel rezervareSejur;
    private javax.swing.JMenuItem rezervariVizItem;
    private javax.swing.JPanel rezervarileMele;
    private javax.swing.JMenuItem sejurCautItem;
    private javax.swing.JMenuItem sejurRezervItem;
    private javax.swing.JMenuItem sejurVizItem;
    private javax.swing.JTable tabelCautaSejur;
    private javax.swing.JTable tabelCircuit;
    private javax.swing.JTable tabelOferte;
    private javax.swing.JTable tabelOferteCircuite;
    private javax.swing.JTable tabelOrase;
    private javax.swing.JTable tabelRezervariCircuite;
    private javax.swing.JTable tabelRezervariSejururi;
    private javax.swing.JTable tabelSejur;
    private javax.swing.JTable tabelZboruri1;
    private javax.swing.JTextField telefonCont;
    private javax.swing.JLabel titluContulMeu;
    private javax.swing.JTextField transportZbor;
    private javax.swing.JPanel tropicalHotels;
    private javax.swing.JPanel vizualizareCazare;
    private javax.swing.JPanel vizualizareCircuit;
    private javax.swing.JPanel vizualizareSejur;
    private javax.swing.JComboBox<String> ziua;
    // End of variables declaration//GEN-END:variables
}
