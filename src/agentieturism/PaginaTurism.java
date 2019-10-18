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
 * @author Diana
 */
public class PaginaTurism extends javax.swing.JFrame {

    /**
     * Creates new form PaginaTurism
     */
    public PaginaTurism() {
        initComponents();
        setPanelVisible(mainPageTursim);
        fillSejur();
        fillCircuite();
        fillComboTropical();
        fillComboGermania();
        fillComboFranta();
        fillComboNorvegia();
        fillComboTurcia();
        fillOrase();
        fillOraseSejur();        
        fillComboAnPlecare();
        fillComboAnSosire();
        fillCircuiteCazare();
        fillSejururiCazare();
        fillCircuiteRezervare();
        fillComboAnRezervare();
        fillComboOrasPlecareSejur();
        setDateCont();
        
    }
    
    
    private void setPanelVisible(JPanel panel){
        ArrayList<JPanel> jpaneluri = new ArrayList<>();
        jpaneluri.add(mainPageTursim);
        jpaneluri.add(vizualizareSejur);
        jpaneluri.add(vizualizareCircuit);
        jpaneluri.add(vizualizareHotel);
        jpaneluri.add(tropicalHotels);
        jpaneluri.add(GermaniaHotels);
        jpaneluri.add(FrantaHotels);
        jpaneluri.add(NorvegiaHotels);
        jpaneluri.add(TurciaHotels);
        jpaneluri.add(adaugareCircuit);
        jpaneluri.add(adaugareSejur);
        jpaneluri.add(adaugareHotel);
        jpaneluri.add(cautareHotel);
        jpaneluri.add(cautareCircuit);
        jpaneluri.add(cautareSejur);
        jpaneluri.add(contulMeu);
        jpaneluri.add(rezervareCircuit);
        jpaneluri.add(rezervareSejur);
        vizualizareHotel.setVisible(false);
        mainPageTursim.setVisible(false);
        vizualizareSejur.setVisible(false);
        vizualizareCircuit.setVisible(false);
        tropicalHotels.setVisible(false);
        GermaniaHotels.setVisible(false);
        FrantaHotels.setVisible(false);
        NorvegiaHotels.setVisible(false);
        TurciaHotels.setVisible(false); 
        adaugareCircuit.setVisible(false);
        adaugareSejur.setVisible(false);
        adaugareHotel.setVisible(false);
        cautareHotel.setVisible(false);
        cautareCircuit.setVisible(false);
        cautareSejur.setVisible(false);
        contulMeu.setVisible(false);
        rezervareCircuit.setVisible(false);
        rezervareSejur.setVisible(false);
        for(JPanel jpanel:jpaneluri){
            if(panel == jpanel)
                panel.setVisible(true);
        }
    }
    private void fillSejur(){
        DefaultTableModel model = (DefaultTableModel) tabelSejur.getModel();        
      
        try{
            Connection con = getConnection();
            String sql = "SELECT * FROM sejur";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                 model.addRow(new Object[]{""  + rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(5)});
            }           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa sejururile!");
        }
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
    
    private void fillCircuite(){
        try{
            Connection con = getConnection();
            String sql = "SELECT * FROM circuit";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCircuite.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa circuitele!");
        }
    }
       private void fillCircuiteRezervare(){
        try{
            Connection con = getConnection();
            String sql = "SELECT * FROM circuit";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboRezervCircuit.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa circuitele!");
        }
    }
     private void fillCircuiteCazare(){
        try{
            Connection con = getConnection();
            String sql = "SELECT * FROM circuit";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboCircuitCazare.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa circuitele!");
        }
    }
     private void fillSejururiCazare(){
        try{
            Connection con = getConnection();
            String sql = "SELECT * FROM sejur";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboSejurCazare.addItem(rs.getString(3));
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa sejururile!");
        }
    }
    
    private void fillComboTropical(){
        try{
            Connection con = getConnection();
            String sql = "SELECT  denumire_cazare FROM cazare,circuit WHERE cazare.cod_circuit = circuit.cod_circuit"
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
    private void fillComboGermania(){
        try{
            Connection con = getConnection();
            String sql = "SELECT  denumire_cazare FROM cazare,sejur WHERE cazare.cod_sejur = sejur.cod_sejur"
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
            String sql = "SELECT  denumire_cazare  FROM cazare,sejur WHERE cazare.cod_sejur = sejur.cod_sejur"
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
            String sql = "SELECT denumire_cazare  FROM cazare,circuit WHERE cazare.cod_circuit = circuit.cod_circuit"
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
    private void fillComboOrasPlecareSejur(){
        try{
            Connection con = getConnection();
            String sql = "SELECT DISTINCT(oras_plecare) FROM sejur";
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while(r.next()){
                orasPlecareSejur.addItem(r.getString(1));                
            }
        }catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele!");
           
        }
    }
    
      private void fillComboTurcia(){
        try{
            Connection con = getConnection();
            String sql = "SELECT  denumire_cazare FROM cazare,sejur WHERE cazare.cod_sejur = sejur.cod_sejur"
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
      
      private void fillOrase(){
          try{
             Connection con = getConnection();
             String sql = "SELECT nume "
                     + "FROM orase";
             PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboOraseCircuit.addItem(rs.getString(1));                 
            }
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele!");
        }
      }
      private void fillOraseSejur(){
          try{
             Connection con = getConnection();
             String sql = "SELECT nume "
                     + "FROM orase";
             PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                comboOrasSosire.addItem(rs.getString(1));                 
            }
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele!");
        }
      }
      private boolean anBisect(int an){
        return ((an%4==0) && (an%100!=0))|| an%400==0;
    }
      private void fillComboAnPlecare(){
        for(int i=2020;i>=1990;i--)
            anPlecare.addItem(Integer.toString(i));
        for(int i=1;i<=12;i++)
            lunaPlecare.addItem(Integer.toString(i));

    }
      private void fillComboAnSosire(){
        for(int i=2020;i>=1990;i--)
            anSosire.addItem(Integer.toString(i));
        for(int i=1;i<=12;i++)
            lunaSosire.addItem(Integer.toString(i));

    }
       private void fillComboAnRezervare(){
        for(int i=2019;i>=2018;i--)
            anData.addItem(Integer.toString(i));
        for(int i=1;i<=12;i++)
            lunaData.addItem(Integer.toString(i));

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
      
       private void fillComboClientiFideli2(){
         try{
             Connection con = getConnection();
             String sql = "SELECT nume, prenume FROM client";
             PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();
             while(rs.next()){
                 clientiFideli2.addItem(rs.getString(1) + " " + rs.getString(2));
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

        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        mainPageTursim = new javax.swing.JPanel();
        progrVoiajLabel = new javax.swing.JLabel();
        butonCircuit = new javax.swing.JButton();
        butonHotel = new javax.swing.JButton();
        butonCont = new javax.swing.JButton();
        iesireButon = new javax.swing.JButton();
        butonSejur2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        vizualizareSejur = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSejur = new javax.swing.JTable();
        inapoiSejur = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        vizualizareCircuit = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboCircuite = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelOrase = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        butonAdaugareCircuit = new javax.swing.JButton();
        butonCautareCircuit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        vizualizareHotel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        hotelTropical = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        radioFranta = new javax.swing.JRadioButton();
        radioTropical = new javax.swing.JRadioButton();
        radioGermania = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        radioNorvegia = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        radioTurcia = new javax.swing.JRadioButton();
        tropicalHotels = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboCazareTropical = new javax.swing.JComboBox<>();
        comboCamere = new javax.swing.JComboBox<>();
        comboNumarStele = new javax.swing.JTextField();
        inapoiHoteluri = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        GermaniaHotels = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        comboGermania = new javax.swing.JComboBox<>();
        nrSteleGermania = new javax.swing.JTextField();
        comboCamereGermania = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        inapoiGermania = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        FrantaHotels = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        comboCazareFranta = new javax.swing.JComboBox<>();
        nrSteleFranta = new javax.swing.JTextField();
        comboCamereFranta = new javax.swing.JComboBox<>();
        butonInapoiFranta = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        NorvegiaHotels = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        comboCazareNorvegia = new javax.swing.JComboBox<>();
        comboCamereNorvegia = new javax.swing.JComboBox<>();
        nrSteleNorvegia = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        TurciaHotels = new javax.swing.JPanel();
        comboCazareTurcia = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        nrSteleTurcia = new javax.swing.JTextField();
        comboCamereTurcia = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        adaugareCircuit = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        denumireTara = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        comboOraseCircuit = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        butonAdaugOras = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        butonAdaugaCircuit = new javax.swing.JButton();
        denumireCircuit = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        adaugareSejur = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        comboOrasPlecare = new javax.swing.JComboBox<>();
        comboOrasSosire = new javax.swing.JComboBox<>();
        ziPlecare = new javax.swing.JComboBox<>();
        anPlecare = new javax.swing.JComboBox<>();
        lunaPlecare = new javax.swing.JComboBox<>();
        ziSosire = new javax.swing.JComboBox<>();
        lunaSosire = new javax.swing.JComboBox<>();
        anSosire = new javax.swing.JComboBox<>();
        butonAdaugaSejur = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        adaugareHotel = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        denumireCazare = new javax.swing.JTextField();
        comboSejurCazare = new javax.swing.JComboBox<>();
        comboTipCazare = new javax.swing.JComboBox<>();
        comboNrStele = new javax.swing.JComboBox<>();
        comboCircuitCazare = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        butonAdaugaCazare = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        cautareHotel = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        fieldCazare = new javax.swing.JTextField();
        radioStele = new javax.swing.JRadioButton();
        radioDenumire = new javax.swing.JRadioButton();
        radioTip = new javax.swing.JRadioButton();
        jLabel67 = new javax.swing.JLabel();
        butonCautaCazare = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelCazare = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        cautareCircuit = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        radioDenumireCircuit = new javax.swing.JRadioButton();
        radioOras = new javax.swing.JRadioButton();
        radioTara = new javax.swing.JRadioButton();
        fieldCircuit = new javax.swing.JTextField();
        butonCautaCircuit = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelCircuit = new javax.swing.JTable();
        jLabel71 = new javax.swing.JLabel();
        cautareSejur = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelCautaSejur = new javax.swing.JTable();
        butonCautaSejur = new javax.swing.JButton();
        fieldCautaSejur = new javax.swing.JTextField();
        radioOrasSosire = new javax.swing.JRadioButton();
        radioDataPlecare = new javax.swing.JRadioButton();
        radioOrasPlecare = new javax.swing.JRadioButton();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        contulMeu = new javax.swing.JPanel();
        titluContulMeu = new javax.swing.JLabel();
        imgProfilAng = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        parolaCont = new javax.swing.JTextField();
        numeCont = new javax.swing.JTextField();
        prenumeCont = new javax.swing.JTextField();
        emailCont = new javax.swing.JTextField();
        telefonCont = new javax.swing.JTextField();
        contCont = new javax.swing.JTextField();
        butonInapoiCont = new javax.swing.JButton();
        butonSalveaza = new javax.swing.JButton();
        rezervareCircuit = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        clientNou = new javax.swing.JRadioButton();
        clientFidel = new javax.swing.JRadioButton();
        clientiFideli = new javax.swing.JComboBox<>();
        prenumeClient = new javax.swing.JTextField();
        numeClient = new javax.swing.JTextField();
        emailClient = new javax.swing.JTextField();
        contClient = new javax.swing.JTextField();
        parolaClient = new javax.swing.JTextField();
        telefonClient = new javax.swing.JTextField();
        adaugaClientButon = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        pretCircuit = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        butonRezervareCircuit = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        ziData = new javax.swing.JComboBox<>();
        comboRezervCircuit = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        comboOrasCircuit = new javax.swing.JComboBox<>();
        anData = new javax.swing.JComboBox<>();
        lunaData = new javax.swing.JComboBox<>();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        rezervareSejur = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        adaugaClientButon1 = new javax.swing.JButton();
        parolaClient1 = new javax.swing.JTextField();
        telefonClient1 = new javax.swing.JTextField();
        prenumeClient1 = new javax.swing.JTextField();
        pretSejur = new javax.swing.JTextField();
        plecareSejur = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        clientFidel1 = new javax.swing.JRadioButton();
        numeClient1 = new javax.swing.JTextField();
        emailClient1 = new javax.swing.JTextField();
        clientNou1 = new javax.swing.JRadioButton();
        jLabel89 = new javax.swing.JLabel();
        clientiFideli2 = new javax.swing.JComboBox<>();
        orasPlecareSejur = new javax.swing.JComboBox<>();
        sosireSejur = new javax.swing.JComboBox<>();
        destinatieSejur = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        contClient1 = new javax.swing.JTextField();
        butonRezervaSejur = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        nrPers = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        vizualizareMeniu = new javax.swing.JMenu();
        vizSejurMeniu = new javax.swing.JMenuItem();
        vizCircuitMeniu = new javax.swing.JMenuItem();
        vizHotelMeniu = new javax.swing.JMenuItem();
        adaugCircuitMeniu = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        adaugSejurMeniu = new javax.swing.JMenuItem();
        adaugHotelMeniu = new javax.swing.JMenuItem();
        cautareMeniu = new javax.swing.JMenu();
        cautHotelMeniu = new javax.swing.JMenuItem();
        cautCircuitMeniu = new javax.swing.JMenuItem();
        cautSejurMeniu = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        jMenuItem5.setText("jMenuItem5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPageTursim.setLayout(null);

        progrVoiajLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        progrVoiajLabel.setForeground(new java.awt.Color(51, 51, 51));
        progrVoiajLabel.setText("Programe de voiaj");
        mainPageTursim.add(progrVoiajLabel);
        progrVoiajLabel.setBounds(140, 230, 120, 30);

        butonCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonCircuit.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonCircuit.setText("Circuit");
        butonCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCircuitActionPerformed(evt);
            }
        });
        mainPageTursim.add(butonCircuit);
        butonCircuit.setBounds(50, 290, 100, 40);

        butonHotel.setBackground(new java.awt.Color(0, 102, 102));
        butonHotel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonHotel.setForeground(new java.awt.Color(255, 255, 255));
        butonHotel.setText("Hoteluri");
        butonHotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonHotelActionPerformed(evt);
            }
        });
        mainPageTursim.add(butonHotel);
        butonHotel.setBounds(410, 290, 100, 40);

        butonCont.setBackground(new java.awt.Color(0, 102, 102));
        butonCont.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonCont.setForeground(new java.awt.Color(255, 255, 255));
        butonCont.setText("Contul meu");
        butonCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonContActionPerformed(evt);
            }
        });
        mainPageTursim.add(butonCont);
        butonCont.setBounds(570, 290, 120, 40);

        iesireButon.setBackground(new java.awt.Color(0, 102, 102));
        iesireButon.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        iesireButon.setForeground(new java.awt.Color(255, 255, 255));
        iesireButon.setText("Iesire");
        iesireButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iesireButonActionPerformed(evt);
            }
        });
        mainPageTursim.add(iesireButon);
        iesireButon.setBounds(310, 390, 100, 40);

        butonSejur2.setBackground(new java.awt.Color(0, 102, 102));
        butonSejur2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        butonSejur2.setForeground(new java.awt.Color(255, 255, 255));
        butonSejur2.setText("Sejur");
        butonSejur2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonSejur2ActionPerformed(evt);
            }
        });
        mainPageTursim.add(butonSejur2);
        butonSejur2.setBounds(230, 290, 100, 40);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgTurism.jpg"))); // NOI18N
        mainPageTursim.add(jLabel2);
        jLabel2.setBounds(0, 0, 720, 480);

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
        jScrollPane1.setBounds(0, 120, 730, 130);

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
        inapoiSejur.setBounds(527, 363, 100, 40);

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        vizualizareSejur.add(jLabel47);
        jLabel47.setBounds(0, 0, 730, 490);

        vizualizareCircuit.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("CIRCUITE");
        vizualizareCircuit.add(jLabel3);
        jLabel3.setBounds(300, 30, 90, 40);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Denumire");
        vizualizareCircuit.add(jLabel4);
        jLabel4.setBounds(60, 110, 90, 30);

        comboCircuite.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        comboCircuite.setForeground(new java.awt.Color(0, 0, 0));
        comboCircuite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCircuiteActionPerformed(evt);
            }
        });
        vizualizareCircuit.add(comboCircuite);
        comboCircuite.setBounds(150, 110, 170, 30);

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
        jScrollPane2.setBounds(150, 190, 430, 110);

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

        butonAdaugareCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugareCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugareCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugareCircuit.setText("Adaugare");
        butonAdaugareCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugareCircuitActionPerformed(evt);
            }
        });
        vizualizareCircuit.add(butonAdaugareCircuit);
        butonAdaugareCircuit.setBounds(90, 360, 100, 40);

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
        butonCautareCircuit.setBounds(300, 360, 100, 40);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgClouds.jpg"))); // NOI18N
        vizualizareCircuit.add(jLabel5);
        jLabel5.setBounds(0, 0, 720, 480);

        vizualizareHotel.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("HOTELURI");
        vizualizareHotel.add(jLabel6);
        jLabel6.setBounds(290, 20, 100, 22);

        hotelTropical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel1.jpg"))); // NOI18N
        vizualizareHotel.add(hotelTropical);
        hotelTropical.setBounds(30, 50, 200, 170);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel2.jpg"))); // NOI18N
        vizualizareHotel.add(jLabel7);
        jLabel7.setBounds(480, 50, 200, 170);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel6.jpg"))); // NOI18N
        vizualizareHotel.add(jLabel8);
        jLabel8.setBounds(260, 50, 180, 170);

        radioFranta.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        radioFranta.setForeground(new java.awt.Color(0, 0, 0));
        radioFranta.setText("Franta");
        radioFranta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFrantaActionPerformed(evt);
            }
        });
        vizualizareHotel.add(radioFranta);
        radioFranta.setBounds(530, 230, 130, 28);

        radioTropical.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        radioTropical.setForeground(new java.awt.Color(0, 0, 0));
        radioTropical.setText("Tropical");
        radioTropical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTropicalActionPerformed(evt);
            }
        });
        vizualizareHotel.add(radioTropical);
        radioTropical.setBounds(80, 230, 100, 28);

        radioGermania.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        radioGermania.setForeground(new java.awt.Color(0, 0, 0));
        radioGermania.setText("Germania");
        radioGermania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioGermaniaActionPerformed(evt);
            }
        });
        vizualizareHotel.add(radioGermania);
        radioGermania.setBounds(290, 230, 130, 28);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel5.jpg"))); // NOI18N
        vizualizareHotel.add(jLabel9);
        jLabel9.setBounds(80, 260, 296, 170);

        radioNorvegia.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        radioNorvegia.setForeground(new java.awt.Color(0, 0, 0));
        radioNorvegia.setText("Norvegia");
        radioNorvegia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNorvegiaActionPerformed(evt);
            }
        });
        vizualizareHotel.add(radioNorvegia);
        radioNorvegia.setBounds(160, 440, 100, 28);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/hotel7.jpg"))); // NOI18N
        vizualizareHotel.add(jLabel10);
        jLabel10.setBounds(430, 260, 240, 180);

        radioTurcia.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        radioTurcia.setForeground(new java.awt.Color(0, 0, 0));
        radioTurcia.setText("Turcia");
        radioTurcia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTurciaActionPerformed(evt);
            }
        });
        vizualizareHotel.add(radioTurcia);
        radioTurcia.setBounds(500, 450, 130, 28);

        tropicalHotels.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("TROPICAL");
        tropicalHotels.add(jLabel11);
        jLabel11.setBounds(310, 40, 100, 22);

        jLabel12.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Cazare");
        tropicalHotels.add(jLabel12);
        jLabel12.setBounds(220, 120, 70, 17);

        jLabel13.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Camere");
        tropicalHotels.add(jLabel13);
        jLabel13.setBounds(220, 220, 70, 17);

        jLabel14.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Numar Stele");
        tropicalHotels.add(jLabel14);
        jLabel14.setBounds(210, 170, 110, 17);

        comboCazareTropical.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboCazareTropical.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareTropical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareTropicalActionPerformed(evt);
            }
        });
        tropicalHotels.add(comboCazareTropical);
        comboCazareTropical.setBounds(300, 110, 180, 27);

        comboCamere.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboCamere.setForeground(new java.awt.Color(0, 0, 0));
        tropicalHotels.add(comboCamere);
        comboCamere.setBounds(300, 220, 180, 27);

        comboNumarStele.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        comboNumarStele.setForeground(new java.awt.Color(0, 0, 0));
        tropicalHotels.add(comboNumarStele);
        comboNumarStele.setBounds(340, 160, 90, 30);

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
        inapoiHoteluri.setBounds(590, 410, 80, 40);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical3.png"))); // NOI18N
        tropicalHotels.add(jLabel15);
        jLabel15.setBounds(10, 30, 190, 210);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical1.jpg"))); // NOI18N
        tropicalHotels.add(jLabel16);
        jLabel16.setBounds(140, 280, 320, 190);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/tropical2.jpg"))); // NOI18N
        tropicalHotels.add(jLabel17);
        jLabel17.setBounds(540, 20, 160, 340);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        tropicalHotels.add(jLabel18);
        jLabel18.setBounds(0, 0, 730, 490);

        GermaniaHotels.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("GERMANIA");
        GermaniaHotels.add(jLabel19);
        jLabel19.setBounds(180, 40, 110, 20);

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

        comboCamereGermania.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereGermania.setForeground(new java.awt.Color(0, 0, 0));
        comboCamereGermania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCamereGermaniaActionPerformed(evt);
            }
        });
        GermaniaHotels.add(comboCamereGermania);
        comboCamereGermania.setBounds(160, 200, 150, 30);

        jLabel20.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Cazare");
        GermaniaHotels.add(jLabel20);
        jLabel20.setBounds(70, 100, 70, 20);

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/germania1.jpg"))); // NOI18N
        GermaniaHotels.add(jLabel23);
        jLabel23.setBounds(120, 270, 290, 190);

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/germania2.jpg"))); // NOI18N
        GermaniaHotels.add(jLabel24);
        jLabel24.setBounds(490, 70, 160, 260);

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        GermaniaHotels.add(jLabel25);
        jLabel25.setBounds(0, 0, 730, 490);

        FrantaHotels.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("FRANTA");
        FrantaHotels.add(jLabel26);
        jLabel26.setBounds(290, 20, 90, 22);

        jLabel27.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Cazare");
        FrantaHotels.add(jLabel27);
        jLabel27.setBounds(80, 110, 70, 20);

        jLabel28.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Numar Stele");
        FrantaHotels.add(jLabel28);
        jLabel28.setBounds(70, 160, 110, 20);

        jLabel29.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Camere");
        FrantaHotels.add(jLabel29);
        jLabel29.setBounds(80, 210, 70, 20);

        comboCazareFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareFranta.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareFranta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareFrantaActionPerformed(evt);
            }
        });
        FrantaHotels.add(comboCazareFranta);
        comboCazareFranta.setBounds(160, 100, 160, 30);

        nrSteleFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        nrSteleFranta.setForeground(new java.awt.Color(0, 0, 0));
        FrantaHotels.add(nrSteleFranta);
        nrSteleFranta.setBounds(200, 150, 80, 30);

        comboCamereFranta.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCamereFranta.setForeground(new java.awt.Color(0, 0, 0));
        FrantaHotels.add(comboCamereFranta);
        comboCamereFranta.setBounds(160, 200, 160, 30);

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

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/franta1.jpg"))); // NOI18N
        FrantaHotels.add(jLabel30);
        jLabel30.setBounds(70, 290, 280, 180);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/franta2.jpg"))); // NOI18N
        FrantaHotels.add(jLabel31);
        jLabel31.setBounds(430, 70, 280, 240);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        FrantaHotels.add(jLabel32);
        jLabel32.setBounds(0, 0, 740, 500);

        NorvegiaHotels.setLayout(null);

        jLabel33.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("NORVEGIA");
        NorvegiaHotels.add(jLabel33);
        jLabel33.setBounds(320, 40, 110, 22);

        jLabel34.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Camere");
        NorvegiaHotels.add(jLabel34);
        jLabel34.setBounds(40, 210, 100, 17);

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

        comboCazareNorvegia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareNorvegia.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareNorvegia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareNorvegiaActionPerformed(evt);
            }
        });
        NorvegiaHotels.add(comboCazareNorvegia);
        comboCazareNorvegia.setBounds(140, 100, 150, 26);

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

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/norvegia1.jpg"))); // NOI18N
        NorvegiaHotels.add(jLabel37);
        jLabel37.setBounds(340, 100, 370, 140);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/norvegia2.jpg"))); // NOI18N
        NorvegiaHotels.add(jLabel38);
        jLabel38.setBounds(70, 260, 284, 180);

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        NorvegiaHotels.add(jLabel39);
        jLabel39.setBounds(0, 0, 730, 490);

        TurciaHotels.setLayout(null);

        comboCazareTurcia.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCazareTurcia.setForeground(new java.awt.Color(0, 0, 0));
        comboCazareTurcia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCazareTurciaActionPerformed(evt);
            }
        });
        TurciaHotels.add(comboCazareTurcia);
        comboCazareTurcia.setBounds(160, 100, 150, 30);

        jLabel40.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Cazare");
        TurciaHotels.add(jLabel40);
        jLabel40.setBounds(70, 100, 70, 20);

        jLabel41.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Numar Stele");
        TurciaHotels.add(jLabel41);
        jLabel41.setBounds(60, 150, 110, 20);

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

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/turcia2.jpg"))); // NOI18N
        TurciaHotels.add(jLabel44);
        jLabel44.setBounds(50, 270, 380, 170);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/turcia1.jpg"))); // NOI18N
        TurciaHotels.add(jLabel45);
        jLabel45.setBounds(380, 80, 290, 170);

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        TurciaHotels.add(jLabel46);
        jLabel46.setBounds(0, 0, 730, 490);

        adaugareCircuit.setLayout(null);

        jLabel48.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Circuite");
        adaugareCircuit.add(jLabel48);
        jLabel48.setBounds(340, 20, 100, 40);

        jLabel49.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Denumire");
        adaugareCircuit.add(jLabel49);
        jLabel49.setBounds(200, 110, 80, 17);

        denumireTara.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        denumireTara.setForeground(new java.awt.Color(0, 0, 0));
        adaugareCircuit.add(denumireTara);
        denumireTara.setBounds(290, 220, 190, 30);

        jLabel50.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Alege orase");
        adaugareCircuit.add(jLabel50);
        jLabel50.setBounds(190, 170, 90, 30);

        comboOraseCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboOraseCircuit.setForeground(new java.awt.Color(0, 0, 0));
        comboOraseCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOraseCircuitActionPerformed(evt);
            }
        });
        adaugareCircuit.add(comboOraseCircuit);
        comboOraseCircuit.setBounds(290, 170, 190, 30);

        jLabel51.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Tara");
        adaugareCircuit.add(jLabel51);
        jLabel51.setBounds(220, 220, 50, 30);

        butonAdaugOras.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugOras.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugOras.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugOras.setText("Adauga Oras");
        butonAdaugOras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugOrasActionPerformed(evt);
            }
        });
        adaugareCircuit.add(butonAdaugOras);
        butonAdaugOras.setBounds(320, 300, 140, 40);

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Inapoi");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        adaugareCircuit.add(jButton5);
        jButton5.setBounds(50, 390, 100, 40);

        butonAdaugaCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugaCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugaCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugaCircuit.setText("Adauga Circuit");
        butonAdaugaCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugaCircuitActionPerformed(evt);
            }
        });
        adaugareCircuit.add(butonAdaugaCircuit);
        butonAdaugaCircuit.setBounds(530, 100, 140, 30);

        denumireCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        denumireCircuit.setForeground(new java.awt.Color(0, 0, 0));
        adaugareCircuit.add(denumireCircuit);
        denumireCircuit.setBounds(290, 100, 190, 30);

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        adaugareCircuit.add(jLabel52);
        jLabel52.setBounds(0, 0, 730, 490);

        adaugareSejur.setLayout(null);

        jLabel53.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Adaugare Sejur");
        adaugareSejur.add(jLabel53);
        jLabel53.setBounds(270, 80, 190, 20);

        jLabel54.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Oras plecare");
        adaugareSejur.add(jLabel54);
        jLabel54.setBounds(190, 150, 90, 17);

        jLabel55.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Oras sosire");
        adaugareSejur.add(jLabel55);
        jLabel55.setBounds(190, 190, 90, 17);

        jLabel56.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Data plecarii");
        adaugareSejur.add(jLabel56);
        jLabel56.setBounds(190, 230, 100, 17);

        jLabel57.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Data sosirii");
        adaugareSejur.add(jLabel57);
        jLabel57.setBounds(190, 270, 80, 17);

        comboOrasPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboOrasPlecare.setForeground(new java.awt.Color(0, 0, 0));
        comboOrasPlecare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bucuresti", "Cluj" }));
        comboOrasPlecare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOrasPlecareActionPerformed(evt);
            }
        });
        adaugareSejur.add(comboOrasPlecare);
        comboOrasPlecare.setBounds(320, 140, 150, 30);

        comboOrasSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboOrasSosire.setForeground(new java.awt.Color(0, 0, 0));
        adaugareSejur.add(comboOrasSosire);
        comboOrasSosire.setBounds(320, 190, 150, 30);

        ziPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        ziPlecare.setForeground(new java.awt.Color(0, 0, 0));
        adaugareSejur.add(ziPlecare);
        ziPlecare.setBounds(500, 230, 80, 30);

        anPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        anPlecare.setForeground(new java.awt.Color(0, 0, 0));
        adaugareSejur.add(anPlecare);
        anPlecare.setBounds(320, 230, 80, 30);

        lunaPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        lunaPlecare.setForeground(new java.awt.Color(0, 0, 0));
        lunaPlecare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunaPlecareActionPerformed(evt);
            }
        });
        adaugareSejur.add(lunaPlecare);
        lunaPlecare.setBounds(410, 230, 70, 30);

        ziSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        ziSosire.setForeground(new java.awt.Color(0, 0, 0));
        adaugareSejur.add(ziSosire);
        ziSosire.setBounds(500, 270, 80, 30);

        lunaSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        lunaSosire.setForeground(new java.awt.Color(0, 0, 0));
        lunaSosire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunaSosireActionPerformed(evt);
            }
        });
        adaugareSejur.add(lunaSosire);
        lunaSosire.setBounds(410, 270, 70, 30);

        anSosire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        anSosire.setForeground(new java.awt.Color(0, 0, 0));
        adaugareSejur.add(anSosire);
        anSosire.setBounds(320, 270, 80, 30);

        butonAdaugaSejur.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugaSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugaSejur.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugaSejur.setText("Adauga");
        butonAdaugaSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugaSejurActionPerformed(evt);
            }
        });
        adaugareSejur.add(butonAdaugaSejur);
        butonAdaugaSejur.setBounds(370, 340, 100, 40);

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Inapoi");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        adaugareSejur.add(jButton6);
        jButton6.setBounds(570, 400, 90, 40);

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/sejur1.jpg"))); // NOI18N
        adaugareSejur.add(jLabel58);
        jLabel58.setBounds(0, 0, 730, 490);

        adaugareHotel.setLayout(null);

        jLabel59.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Adaugare Cazare");
        adaugareHotel.add(jLabel59);
        jLabel59.setBounds(290, 20, 160, 50);

        jLabel60.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Sejur");
        adaugareHotel.add(jLabel60);
        jLabel60.setBounds(230, 300, 80, 30);

        jLabel61.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("Denumire");
        adaugareHotel.add(jLabel61);
        jLabel61.setBounds(210, 100, 80, 30);

        jLabel62.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Tip");
        adaugareHotel.add(jLabel62);
        jLabel62.setBounds(230, 150, 70, 30);

        jLabel63.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Numar Stele");
        adaugareHotel.add(jLabel63);
        jLabel63.setBounds(210, 200, 90, 30);

        jLabel64.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("Circuit");
        adaugareHotel.add(jLabel64);
        jLabel64.setBounds(220, 250, 80, 30);

        denumireCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        denumireCazare.setForeground(new java.awt.Color(0, 0, 0));
        adaugareHotel.add(denumireCazare);
        denumireCazare.setBounds(330, 100, 180, 30);

        comboSejurCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboSejurCazare.setForeground(new java.awt.Color(0, 0, 0));
        adaugareHotel.add(comboSejurCazare);
        comboSejurCazare.setBounds(330, 300, 180, 30);

        comboTipCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboTipCazare.setForeground(new java.awt.Color(0, 0, 0));
        comboTipCazare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hotel", "Pensiune", "Motel", "Vila", "Cabana" }));
        adaugareHotel.add(comboTipCazare);
        comboTipCazare.setBounds(330, 150, 180, 30);

        comboNrStele.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboNrStele.setForeground(new java.awt.Color(0, 0, 0));
        comboNrStele.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "4", "3", "2", "1" }));
        adaugareHotel.add(comboNrStele);
        comboNrStele.setBounds(330, 200, 180, 30);

        comboCircuitCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        comboCircuitCazare.setForeground(new java.awt.Color(0, 0, 0));
        adaugareHotel.add(comboCircuitCazare);
        comboCircuitCazare.setBounds(330, 250, 180, 30);

        jButton4.setBackground(new java.awt.Color(0, 102, 102));
        jButton4.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Inapoi");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        adaugareHotel.add(jButton4);
        jButton4.setBounds(40, 400, 90, 40);

        butonAdaugaCazare.setBackground(new java.awt.Color(0, 102, 102));
        butonAdaugaCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonAdaugaCazare.setForeground(new java.awt.Color(255, 255, 255));
        butonAdaugaCazare.setText("ADAUGA");
        butonAdaugaCazare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAdaugaCazareActionPerformed(evt);
            }
        });
        adaugareHotel.add(butonAdaugaCazare);
        butonAdaugaCazare.setBounds(370, 360, 110, 40);

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        adaugareHotel.add(jLabel65);
        jLabel65.setBounds(0, 0, 730, 490);

        cautareHotel.setLayout(null);

        jLabel66.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("Filtrare dupa");
        cautareHotel.add(jLabel66);
        jLabel66.setBounds(30, 60, 120, 40);

        fieldCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        fieldCazare.setForeground(new java.awt.Color(0, 0, 0));
        fieldCazare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCazareActionPerformed(evt);
            }
        });
        cautareHotel.add(fieldCazare);
        fieldCazare.setBounds(260, 160, 180, 30);

        radioStele.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioStele.setForeground(new java.awt.Color(0, 0, 0));
        radioStele.setText("Numar Stele");
        cautareHotel.add(radioStele);
        radioStele.setBounds(480, 110, 130, 28);

        radioDenumire.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioDenumire.setForeground(new java.awt.Color(0, 0, 0));
        radioDenumire.setText("Denumire");
        cautareHotel.add(radioDenumire);
        radioDenumire.setBounds(100, 110, 102, 28);

        radioTip.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioTip.setForeground(new java.awt.Color(0, 0, 0));
        radioTip.setText("Tip");
        cautareHotel.add(radioTip);
        radioTip.setBounds(310, 110, 80, 28);

        jLabel67.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("Cauta Cazare");
        cautareHotel.add(jLabel67);
        jLabel67.setBounds(280, 20, 120, 40);

        butonCautaCazare.setBackground(new java.awt.Color(0, 102, 102));
        butonCautaCazare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonCautaCazare.setForeground(new java.awt.Color(255, 255, 255));
        butonCautaCazare.setText("Cauta");
        butonCautaCazare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonCautaCazareActionPerformed(evt);
            }
        });
        cautareHotel.add(butonCautaCazare);
        butonCautaCazare.setBounds(300, 210, 100, 40);

        tabelCazare.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelCazare.setForeground(new java.awt.Color(0, 0, 0));
        tabelCazare.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nume", "Tip", "Stele", "Circuit", "Sejur"
            }
        ));
        jScrollPane3.setViewportView(tabelCazare);
        if (tabelCazare.getColumnModel().getColumnCount() > 0) {
            tabelCazare.getColumnModel().getColumn(3).setHeaderValue("Circuit");
            tabelCazare.getColumnModel().getColumn(4).setHeaderValue("Sejur");
        }

        cautareHotel.add(jScrollPane3);
        jScrollPane3.setBounds(0, 280, 730, 130);

        jButton8.setBackground(new java.awt.Color(0, 102, 102));
        jButton8.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Inapoi");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        cautareHotel.add(jButton8);
        jButton8.setBounds(540, 430, 100, 40);

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        cautareHotel.add(jLabel68);
        jLabel68.setBounds(0, 0, 730, 510);

        cautareCircuit.setLayout(null);

        jLabel69.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Cauta Circuit");
        cautareCircuit.add(jLabel69);
        jLabel69.setBounds(280, 20, 120, 40);

        jLabel70.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Filtrare dupa");
        cautareCircuit.add(jLabel70);
        jLabel70.setBounds(30, 60, 120, 40);

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
        jScrollPane4.setBounds(0, 280, 730, 130);

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        cautareCircuit.add(jLabel71);
        jLabel71.setBounds(0, 0, 730, 490);

        cautareSejur.setLayout(null);

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
        jButton10.setBounds(540, 430, 110, 40);

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
        jScrollPane5.setBounds(0, 280, 730, 130);

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

        fieldCautaSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        fieldCautaSejur.setForeground(new java.awt.Color(0, 0, 0));
        fieldCautaSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCautaSejurActionPerformed(evt);
            }
        });
        cautareSejur.add(fieldCautaSejur);
        fieldCautaSejur.setBounds(260, 160, 180, 30);

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

        radioOrasPlecare.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        radioOrasPlecare.setForeground(new java.awt.Color(0, 0, 0));
        radioOrasPlecare.setText("Oras Plecare");
        cautareSejur.add(radioOrasPlecare);
        radioOrasPlecare.setBounds(100, 110, 130, 28);

        jLabel72.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setText("Filtrare dupa");
        cautareSejur.add(jLabel72);
        jLabel72.setBounds(30, 60, 120, 40);

        jLabel73.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText("Cauta Sejur");
        cautareSejur.add(jLabel73);
        jLabel73.setBounds(280, 20, 120, 40);

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        cautareSejur.add(jLabel74);
        jLabel74.setBounds(0, 0, 730, 490);

        contulMeu.setLayout(null);

        titluContulMeu.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        titluContulMeu.setForeground(new java.awt.Color(0, 0, 0));
        titluContulMeu.setText("CONTUL MEU");
        contulMeu.add(titluContulMeu);
        titluContulMeu.setBounds(290, 70, 120, 20);

        imgProfilAng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/imgProfil.png"))); // NOI18N
        contulMeu.add(imgProfilAng);
        imgProfilAng.setBounds(30, 20, 116, 120);

        jLabel75.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Nume");
        contulMeu.add(jLabel75);
        jLabel75.setBounds(50, 190, 60, 17);

        jLabel76.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 0, 0));
        jLabel76.setText("Prenume");
        contulMeu.add(jLabel76);
        jLabel76.setBounds(40, 240, 80, 17);

        jLabel77.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("Telefon");
        contulMeu.add(jLabel77);
        jLabel77.setBounds(50, 300, 60, 17);

        jLabel78.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setText("Email");
        contulMeu.add(jLabel78);
        jLabel78.setBounds(360, 190, 70, 17);

        jLabel79.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setText("Cont");
        contulMeu.add(jLabel79);
        jLabel79.setBounds(360, 240, 50, 17);

        jLabel80.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("Parola");
        contulMeu.add(jLabel80);
        jLabel80.setBounds(360, 290, 60, 20);

        parolaCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parolaContActionPerformed(evt);
            }
        });
        contulMeu.add(parolaCont);
        parolaCont.setBounds(440, 290, 140, 24);

        numeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeContActionPerformed(evt);
            }
        });
        contulMeu.add(numeCont);
        numeCont.setBounds(130, 190, 140, 24);

        prenumeCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenumeContActionPerformed(evt);
            }
        });
        contulMeu.add(prenumeCont);
        prenumeCont.setBounds(130, 240, 140, 24);

        emailCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailContActionPerformed(evt);
            }
        });
        contulMeu.add(emailCont);
        emailCont.setBounds(440, 190, 170, 24);

        telefonCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonContActionPerformed(evt);
            }
        });
        contulMeu.add(telefonCont);
        telefonCont.setBounds(130, 290, 140, 24);

        contCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contContActionPerformed(evt);
            }
        });
        contulMeu.add(contCont);
        contCont.setBounds(440, 240, 140, 24);

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

        rezervareCircuit.setLayout(null);

        jLabel81.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Rezervare Circuit");
        rezervareCircuit.add(jLabel81);
        jLabel81.setBounds(280, 20, 150, 30);

        clientNou.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientNou.setForeground(new java.awt.Color(0, 0, 0));
        clientNou.setText("Nou");
        clientNou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientNouActionPerformed(evt);
            }
        });
        rezervareCircuit.add(clientNou);
        clientNou.setBounds(90, 80, 80, 28);

        clientFidel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientFidel.setForeground(new java.awt.Color(0, 0, 0));
        clientFidel.setText("Fidel");
        clientFidel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientFidelActionPerformed(evt);
            }
        });
        rezervareCircuit.add(clientFidel);
        clientFidel.setBounds(180, 80, 59, 28);

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
        rezervareCircuit.add(clientiFideli);
        clientiFideli.setBounds(270, 80, 140, 26);

        prenumeClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        prenumeClient.setForeground(new java.awt.Color(0, 0, 0));
        prenumeClient.setText("Prenume");
        rezervareCircuit.add(prenumeClient);
        prenumeClient.setBounds(240, 135, 140, 30);

        numeClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        numeClient.setForeground(new java.awt.Color(0, 0, 0));
        numeClient.setText("Nume");
        rezervareCircuit.add(numeClient);
        numeClient.setBounds(90, 135, 140, 30);

        emailClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        emailClient.setForeground(new java.awt.Color(0, 0, 0));
        emailClient.setText("Email");
        rezervareCircuit.add(emailClient);
        emailClient.setBounds(90, 185, 160, 30);

        contClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        contClient.setForeground(new java.awt.Color(0, 0, 0));
        contClient.setText("Cont");
        rezervareCircuit.add(contClient);
        contClient.setBounds(270, 185, 110, 30);

        parolaClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        parolaClient.setForeground(new java.awt.Color(0, 0, 0));
        parolaClient.setText("Parola");
        rezervareCircuit.add(parolaClient);
        parolaClient.setBounds(400, 185, 140, 30);

        telefonClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        telefonClient.setForeground(new java.awt.Color(0, 0, 0));
        telefonClient.setText("Telefon");
        rezervareCircuit.add(telefonClient);
        telefonClient.setBounds(390, 135, 150, 30);

        adaugaClientButon.setBackground(new java.awt.Color(0, 102, 102));
        adaugaClientButon.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        adaugaClientButon.setForeground(new java.awt.Color(255, 255, 255));
        adaugaClientButon.setText("Adauga");
        adaugaClientButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugaClientButonActionPerformed(evt);
            }
        });
        rezervareCircuit.add(adaugaClientButon);
        adaugaClientButon.setBounds(570, 160, 90, 33);

        jLabel82.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setText("Pret");
        rezervareCircuit.add(jLabel82);
        jLabel82.setBounds(420, 320, 50, 19);

        pretCircuit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pretCircuitMouseClicked(evt);
            }
        });
        pretCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pretCircuitActionPerformed(evt);
            }
        });
        rezervareCircuit.add(pretCircuit);
        pretCircuit.setBounds(470, 320, 130, 24);

        jButton7.setBackground(new java.awt.Color(0, 102, 102));
        jButton7.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Inapoi");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        rezervareCircuit.add(jButton7);
        jButton7.setBounds(580, 380, 80, 40);

        butonRezervareCircuit.setBackground(new java.awt.Color(0, 102, 102));
        butonRezervareCircuit.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonRezervareCircuit.setForeground(new java.awt.Color(255, 255, 255));
        butonRezervareCircuit.setText("Rezerva");
        butonRezervareCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonRezervareCircuitActionPerformed(evt);
            }
        });
        rezervareCircuit.add(butonRezervareCircuit);
        butonRezervareCircuit.setBounds(290, 380, 100, 33);

        jLabel83.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setText("Data");
        rezervareCircuit.add(jLabel83);
        jLabel83.setBounds(20, 320, 50, 19);

        ziData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ziDataItemStateChanged(evt);
            }
        });
        ziData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ziDataActionPerformed(evt);
            }
        });
        rezervareCircuit.add(ziData);
        ziData.setBounds(290, 320, 70, 26);

        comboRezervCircuit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRezervCircuitItemStateChanged(evt);
            }
        });
        comboRezervCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRezervCircuitActionPerformed(evt);
            }
        });
        rezervareCircuit.add(comboRezervCircuit);
        comboRezervCircuit.setBounds(90, 260, 140, 26);

        jLabel84.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 0));
        jLabel84.setText("Circuit");
        rezervareCircuit.add(jLabel84);
        jLabel84.setBounds(20, 260, 50, 19);

        jLabel85.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Orase");
        rezervareCircuit.add(jLabel85);
        jLabel85.setBounds(280, 260, 50, 19);

        comboOrasCircuit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboOrasCircuitItemStateChanged(evt);
            }
        });
        comboOrasCircuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOrasCircuitActionPerformed(evt);
            }
        });
        rezervareCircuit.add(comboOrasCircuit);
        comboOrasCircuit.setBounds(340, 260, 140, 26);

        anData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                anDataItemStateChanged(evt);
            }
        });
        anData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anDataActionPerformed(evt);
            }
        });
        rezervareCircuit.add(anData);
        anData.setBounds(80, 320, 70, 26);

        lunaData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                lunaDataItemStateChanged(evt);
            }
        });
        lunaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunaDataActionPerformed(evt);
            }
        });
        rezervareCircuit.add(lunaData);
        lunaData.setBounds(180, 320, 70, 26);

        jLabel86.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Client");
        rezervareCircuit.add(jLabel86);
        jLabel86.setBounds(10, 80, 60, 30);

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        rezervareCircuit.add(jLabel87);
        jLabel87.setBounds(0, 0, 730, 490);

        rezervareSejur.setLayout(null);

        jButton11.setBackground(new java.awt.Color(0, 102, 102));
        jButton11.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Inapoi");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        rezervareSejur.add(jButton11);
        jButton11.setBounds(590, 430, 80, 40);

        adaugaClientButon1.setBackground(new java.awt.Color(0, 102, 102));
        adaugaClientButon1.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        adaugaClientButon1.setForeground(new java.awt.Color(255, 255, 255));
        adaugaClientButon1.setText("Adauga");
        adaugaClientButon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugaClientButon1ActionPerformed(evt);
            }
        });
        rezervareSejur.add(adaugaClientButon1);
        adaugaClientButon1.setBounds(570, 150, 90, 33);

        parolaClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        parolaClient1.setForeground(new java.awt.Color(0, 0, 0));
        parolaClient1.setText("Parola");
        rezervareSejur.add(parolaClient1);
        parolaClient1.setBounds(400, 185, 140, 30);

        telefonClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        telefonClient1.setForeground(new java.awt.Color(0, 0, 0));
        telefonClient1.setText("Telefon");
        rezervareSejur.add(telefonClient1);
        telefonClient1.setBounds(390, 135, 150, 30);

        prenumeClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        prenumeClient1.setForeground(new java.awt.Color(0, 0, 0));
        prenumeClient1.setText("Prenume");
        rezervareSejur.add(prenumeClient1);
        prenumeClient1.setBounds(240, 135, 140, 30);

        pretSejur.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pretSejur.setForeground(new java.awt.Color(0, 0, 0));
        pretSejur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pretSejurMouseClicked(evt);
            }
        });
        pretSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pretSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(pretSejur);
        pretSejur.setBounds(400, 340, 110, 30);

        plecareSejur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                plecareSejurItemStateChanged(evt);
            }
        });
        plecareSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plecareSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(plecareSejur);
        plecareSejur.setBounds(380, 290, 140, 26);

        jLabel88.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("Rezervare Sejur");
        rezervareSejur.add(jLabel88);
        jLabel88.setBounds(280, 20, 150, 30);

        clientFidel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientFidel1.setForeground(new java.awt.Color(0, 0, 0));
        clientFidel1.setText("Fidel");
        clientFidel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientFidel1ActionPerformed(evt);
            }
        });
        rezervareSejur.add(clientFidel1);
        clientFidel1.setBounds(180, 80, 59, 28);

        numeClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        numeClient1.setForeground(new java.awt.Color(0, 0, 0));
        numeClient1.setText("Nume");
        rezervareSejur.add(numeClient1);
        numeClient1.setBounds(90, 135, 140, 30);

        emailClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        emailClient1.setForeground(new java.awt.Color(0, 0, 0));
        emailClient1.setText("Email");
        rezervareSejur.add(emailClient1);
        emailClient1.setBounds(90, 185, 160, 30);

        clientNou1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientNou1.setForeground(new java.awt.Color(0, 0, 0));
        clientNou1.setText("Nou");
        clientNou1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientNou1ActionPerformed(evt);
            }
        });
        rezervareSejur.add(clientNou1);
        clientNou1.setBounds(90, 80, 80, 28);

        jLabel89.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("Nr pers.");
        rezervareSejur.add(jLabel89);
        jLabel89.setBounds(150, 340, 70, 30);

        clientiFideli2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clientiFideli2ItemStateChanged(evt);
            }
        });
        clientiFideli2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientiFideli2ActionPerformed(evt);
            }
        });
        rezervareSejur.add(clientiFideli2);
        clientiFideli2.setBounds(270, 80, 140, 26);

        orasPlecareSejur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                orasPlecareSejurItemStateChanged(evt);
            }
        });
        orasPlecareSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orasPlecareSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(orasPlecareSejur);
        orasPlecareSejur.setBounds(40, 290, 140, 26);

        sosireSejur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sosireSejurItemStateChanged(evt);
            }
        });
        sosireSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sosireSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(sosireSejur);
        sosireSejur.setBounds(540, 290, 140, 26);

        destinatieSejur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                destinatieSejurItemStateChanged(evt);
            }
        });
        destinatieSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinatieSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(destinatieSejur);
        destinatieSejur.setBounds(210, 290, 140, 26);

        jLabel90.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Client");
        rezervareSejur.add(jLabel90);
        jLabel90.setBounds(10, 80, 60, 30);

        jLabel91.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("Plecare");
        rezervareSejur.add(jLabel91);
        jLabel91.setBounds(80, 250, 60, 30);

        jLabel92.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 0));
        jLabel92.setText("Destinatie");
        rezervareSejur.add(jLabel92);
        jLabel92.setBounds(230, 250, 80, 30);

        jLabel93.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 0));
        jLabel93.setText("Data plecare");
        rezervareSejur.add(jLabel93);
        jLabel93.setBounds(400, 250, 90, 30);

        jLabel94.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 0));
        jLabel94.setText("Data sosire");
        rezervareSejur.add(jLabel94);
        jLabel94.setBounds(560, 250, 80, 30);

        contClient1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        contClient1.setForeground(new java.awt.Color(0, 0, 0));
        contClient1.setText("Cont");
        rezervareSejur.add(contClient1);
        contClient1.setBounds(270, 185, 110, 30);

        butonRezervaSejur.setBackground(new java.awt.Color(0, 102, 102));
        butonRezervaSejur.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N
        butonRezervaSejur.setForeground(new java.awt.Color(255, 255, 255));
        butonRezervaSejur.setText("Rezerva");
        butonRezervaSejur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonRezervaSejurActionPerformed(evt);
            }
        });
        rezervareSejur.add(butonRezervaSejur);
        butonRezervaSejur.setBounds(300, 430, 100, 33);

        jLabel95.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setText("Pret");
        rezervareSejur.add(jLabel95);
        jLabel95.setBounds(350, 340, 50, 30);

        nrPers.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        nrPers.setForeground(new java.awt.Color(0, 0, 0));
        rezervareSejur.add(nrPers);
        nrPers.setBounds(220, 340, 70, 30);

        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agentieturism/bgFurnizoriAdmin.jpg"))); // NOI18N
        rezervareSejur.add(jLabel96);
        jLabel96.setBounds(0, 0, 740, 510);

        vizualizareMeniu.setForeground(new java.awt.Color(51, 51, 51));
        vizualizareMeniu.setText("Vizualizare");
        vizualizareMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        vizSejurMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        vizSejurMeniu.setForeground(new java.awt.Color(51, 51, 51));
        vizSejurMeniu.setText("Sejur");
        vizSejurMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vizSejurMeniuActionPerformed(evt);
            }
        });
        vizualizareMeniu.add(vizSejurMeniu);

        vizCircuitMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        vizCircuitMeniu.setForeground(new java.awt.Color(51, 51, 51));
        vizCircuitMeniu.setText("Circuit");
        vizCircuitMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vizCircuitMeniuActionPerformed(evt);
            }
        });
        vizualizareMeniu.add(vizCircuitMeniu);

        vizHotelMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        vizHotelMeniu.setForeground(new java.awt.Color(51, 51, 51));
        vizHotelMeniu.setText("Cazare");
        vizHotelMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vizHotelMeniuActionPerformed(evt);
            }
        });
        vizualizareMeniu.add(vizHotelMeniu);

        jMenuBar1.add(vizualizareMeniu);

        adaugCircuitMeniu.setForeground(new java.awt.Color(51, 51, 51));
        adaugCircuitMeniu.setText("Adaugare");
        adaugCircuitMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jMenuItem6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jMenuItem6.setForeground(new java.awt.Color(51, 51, 51));
        jMenuItem6.setText("Circuit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        adaugCircuitMeniu.add(jMenuItem6);

        adaugSejurMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        adaugSejurMeniu.setForeground(new java.awt.Color(51, 51, 51));
        adaugSejurMeniu.setText("Sejur");
        adaugSejurMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugSejurMeniuActionPerformed(evt);
            }
        });
        adaugCircuitMeniu.add(adaugSejurMeniu);

        adaugHotelMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        adaugHotelMeniu.setForeground(new java.awt.Color(51, 51, 51));
        adaugHotelMeniu.setText("Cazare");
        adaugHotelMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaugHotelMeniuActionPerformed(evt);
            }
        });
        adaugCircuitMeniu.add(adaugHotelMeniu);

        jMenuBar1.add(adaugCircuitMeniu);

        cautareMeniu.setForeground(new java.awt.Color(51, 51, 51));
        cautareMeniu.setText("Cautare");
        cautareMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        cautHotelMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cautHotelMeniu.setForeground(new java.awt.Color(51, 51, 51));
        cautHotelMeniu.setText("Cazare");
        cautHotelMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cautHotelMeniuActionPerformed(evt);
            }
        });
        cautareMeniu.add(cautHotelMeniu);

        cautCircuitMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cautCircuitMeniu.setForeground(new java.awt.Color(51, 51, 51));
        cautCircuitMeniu.setText("Circuit");
        cautCircuitMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cautCircuitMeniuActionPerformed(evt);
            }
        });
        cautareMeniu.add(cautCircuitMeniu);

        cautSejurMeniu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cautSejurMeniu.setForeground(new java.awt.Color(51, 51, 51));
        cautSejurMeniu.setText("Sejur");
        cautSejurMeniu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cautSejurMeniuActionPerformed(evt);
            }
        });
        cautareMeniu.add(cautSejurMeniu);

        jMenuBar1.add(cautareMeniu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPageTursim, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareSejur, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareCircuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(tropicalHotels, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(GermaniaHotels, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FrantaHotels, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(NorvegiaHotels, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(TurciaHotels, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareCircuit, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareSejur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareCircuit, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareSejur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contulMeu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rezervareCircuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rezervareSejur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPageTursim, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareSejur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareCircuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vizualizareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(tropicalHotels, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(GermaniaHotels, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FrantaHotels, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(NorvegiaHotels, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TurciaHotels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareCircuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareSejur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adaugareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareHotel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareCircuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cautareSejur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contulMeu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rezervareCircuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(rezervareSejur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void vizCircuitMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vizCircuitMeniuActionPerformed
        setPanelVisible(vizualizareCircuit);
    }//GEN-LAST:event_vizCircuitMeniuActionPerformed

    private void adaugSejurMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaugSejurMeniuActionPerformed
       setPanelVisible(adaugareSejur);
    }//GEN-LAST:event_adaugSejurMeniuActionPerformed

    private void cautSejurMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cautSejurMeniuActionPerformed
        setPanelVisible(cautareSejur);
    }//GEN-LAST:event_cautSejurMeniuActionPerformed

    private void iesireButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iesireButonActionPerformed
       JFrame primaPagina = new PrimaPagina();
       primaPagina.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_iesireButonActionPerformed

    private void inapoiSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiSejurActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_inapoiSejurActionPerformed

    private void vizSejurMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vizSejurMeniuActionPerformed
        setPanelVisible(vizualizareSejur);
    }//GEN-LAST:event_vizSejurMeniuActionPerformed

    private void comboCircuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCircuiteActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelOrase.getModel();
        try{
            Connection con = getConnection();
            String sql = "SELECT nume_oras, tara FROM oras,circuit WHERE oras.cod_circuit = circuit.cod_circuit and denumire=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboCircuite.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new Object[]{""  + rs.getString(1), rs.getString(2)});

            }
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se pot accesa orasele!");
           
        }
    }//GEN-LAST:event_comboCircuiteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vizHotelMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vizHotelMeniuActionPerformed
        setPanelVisible(vizualizareHotel);
    }//GEN-LAST:event_vizHotelMeniuActionPerformed

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

    private void radioTropicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTropicalActionPerformed
        setPanelVisible(tropicalHotels);
    }//GEN-LAST:event_radioTropicalActionPerformed

    private void inapoiHoteluriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inapoiHoteluriActionPerformed
        radioTropical.setSelected(false);
        setPanelVisible(vizualizareHotel);
        
    }//GEN-LAST:event_inapoiHoteluriActionPerformed

    private void radioGermaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGermaniaActionPerformed
        setPanelVisible(GermaniaHotels);
    }//GEN-LAST:event_radioGermaniaActionPerformed

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
        radioGermania.setSelected(false);
        setPanelVisible(vizualizareHotel);
    }//GEN-LAST:event_inapoiGermaniaActionPerformed

    private void butonInapoiFrantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonInapoiFrantaActionPerformed
        radioFranta.setSelected(false);
        setPanelVisible(vizualizareHotel);
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

    private void radioFrantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFrantaActionPerformed
        setPanelVisible(FrantaHotels);
    }//GEN-LAST:event_radioFrantaActionPerformed

    private void comboCamereNorvegiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCamereNorvegiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCamereNorvegiaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        radioNorvegia.setSelected(false);
        setPanelVisible(vizualizareHotel);
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void radioNorvegiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNorvegiaActionPerformed
       setPanelVisible(NorvegiaHotels);
    }//GEN-LAST:event_radioNorvegiaActionPerformed

    private void radioTurciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTurciaActionPerformed
        setPanelVisible(TurciaHotels);
    }//GEN-LAST:event_radioTurciaActionPerformed

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
        radioTurcia.setSelected(false);
        setPanelVisible(vizualizareHotel);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        setPanelVisible(adaugareCircuit);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void butonAdaugaCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugaCircuitActionPerformed
        try{
            Connection con = getConnection();
            String sql = "INSERT INTO CIRCUIT VALUES(null, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, denumireCircuit.getText());
            pstm.executeUpdate();
             JOptionPane.showMessageDialog(this,"S-a adaugat circuitul, alegeti orasele!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate insera circuitul!");
        }
    }//GEN-LAST:event_butonAdaugaCircuitActionPerformed

    private void comboOraseCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOraseCircuitActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT tara FROM orase WHERE nume=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, comboOraseCircuit.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                denumireTara.setText(rs.getString(1));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate afisa tara!");
        }
        
    }//GEN-LAST:event_comboOraseCircuitActionPerformed

    private void butonAdaugOrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugOrasActionPerformed
        try{
            Connection con = getConnection();
            String sql1 = "INSERT INTO oras VALUES(NULL,?,?,?)";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1, comboOraseCircuit.getSelectedItem().toString());
            pstm1.setString(2, denumireTara.getText());
            String sql2 = "SELECT cod_circuit FROM circuit WHERE denumire=?";
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1, denumireCircuit.getText());
            ResultSet rs = pstm2.executeQuery();
            while(rs.next()){
                pstm1.setInt(3, rs.getInt(1));
            }
            pstm1.executeUpdate();
            JOptionPane.showMessageDialog(this,"Oras inserat!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Nu se poate insera orasul!");
            ex.printStackTrace();
           
        }
    }//GEN-LAST:event_butonAdaugOrasActionPerformed

    private void lunaPlecareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunaPlecareActionPerformed
       if(lunaPlecare.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anPlecare.getSelectedItem().toString()))){
             ziPlecare.removeAllItems();
            for(int i=1;i<=29;i++){               
                ziPlecare.addItem(Integer.toString(i));
            }                     
        } else if(lunaPlecare.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anPlecare.getSelectedItem().toString()))==false){
             ziPlecare.removeAllItems();
            for(int i=1;i<=28;i++){               
                ziPlecare.addItem(Integer.toString(i));
            } 
        } else if(lunaPlecare.getSelectedItem().equals("1")|| lunaPlecare.getSelectedItem().equals("3") || lunaPlecare.getSelectedItem().equals("5") || lunaPlecare.getSelectedItem().equals("7") || lunaPlecare.getSelectedItem().equals("8") || lunaPlecare.getSelectedItem().equals("10") || lunaPlecare.getSelectedItem().equals("12")){
              ziPlecare.removeAllItems();
            for(int i=1;i<=31;i++){                 
                 ziPlecare.addItem(Integer.toString(i));     
            }            
        } else{
             ziPlecare.removeAllItems();
            for(int i=1;i<=30;i++){                 
                ziPlecare.addItem(Integer.toString(i));
            }            
        }
    }//GEN-LAST:event_lunaPlecareActionPerformed

    private void lunaSosireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunaSosireActionPerformed
         if(lunaSosire.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anSosire.getSelectedItem().toString()))){
             ziSosire.removeAllItems();
            for(int i=1;i<=29;i++){               
                ziSosire.addItem(Integer.toString(i));
            }                     
        } else if(lunaSosire.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anSosire.getSelectedItem().toString()))==false){
             ziSosire.removeAllItems();
            for(int i=1;i<=28;i++){               
                ziSosire.addItem(Integer.toString(i));
            } 
        } else if(lunaSosire.getSelectedItem().equals("1")|| lunaSosire.getSelectedItem().equals("3") || lunaSosire.getSelectedItem().equals("5") || lunaSosire.getSelectedItem().equals("7") || lunaSosire.getSelectedItem().equals("8") || lunaSosire.getSelectedItem().equals("10") || lunaSosire.getSelectedItem().equals("12")){
              ziSosire.removeAllItems();
            for(int i=1;i<=31;i++){                 
                 ziSosire.addItem(Integer.toString(i));     
            }            
        } else{
             ziSosire.removeAllItems();
            for(int i=1;i<=30;i++){                 
                ziSosire.addItem(Integer.toString(i));
            }            
        }
    }//GEN-LAST:event_lunaSosireActionPerformed

    private void butonAdaugaSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugaSejurActionPerformed
       String orasPlecare = comboOrasPlecare.getSelectedItem().toString();
       String orasSosire = comboOrasSosire.getSelectedItem().toString();
       String dataPlecarii = anPlecare.getSelectedItem().toString()+"-"+lunaPlecare.getSelectedItem().toString() + "-" + ziPlecare.getSelectedItem().toString();
       String dataSosirii =  anSosire.getSelectedItem().toString()+"-"+lunaSosire.getSelectedItem().toString() + "-" + ziSosire.getSelectedItem().toString();
       try{
           Connection con = getConnection();
           String sql = "INSERT INTO sejur VALUES(null,?,?,?,?)";
           PreparedStatement pstm = con.prepareStatement(sql);
           pstm.setString(1,orasPlecare);
           pstm.setString(2, orasSosire);
           pstm.setString(3, dataPlecarii);
           pstm.setString(4, dataSosirii);
           pstm.executeUpdate();
            JOptionPane.showMessageDialog(this,"Sejur adaugat!");   
          
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
           JOptionPane.showMessageDialog(this,"Nu se poate adauga sejurul!");
        }
    }//GEN-LAST:event_butonAdaugaSejurActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void adaugHotelMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaugHotelMeniuActionPerformed
        setPanelVisible(adaugareHotel);
    }//GEN-LAST:event_adaugHotelMeniuActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void butonAdaugaCazareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugaCazareActionPerformed
        String denCazare = denumireCazare.getText();
        String tip = comboTipCazare.getSelectedItem().toString();
        String nrStele = comboNrStele.getSelectedItem().toString();
        String circuit = comboCircuitCazare.getSelectedItem().toString();
        String sejur = comboSejurCazare.getSelectedItem().toString();
        try{
            Connection con = getConnection();
            String sql1 = "SELECT cod_circuit FROM circuit WHERE denumire=?";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1,circuit);
            ResultSet rs1 = pstm1.executeQuery();
            String sql2 = "SELECT cod_sejur FROM sejur WHERE oras_sosire=?";
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1,sejur);
            ResultSet rs2 = pstm2.executeQuery();
            String insertCazare = "INSERT INTO cazare VALUES(null,?,?,?,?,?)";
            PreparedStatement pstm3 = con.prepareStatement(insertCazare);
            pstm3.setString(1,denCazare);
            pstm3.setString(2,tip);
            pstm3.setInt(3,Integer.parseInt(nrStele));
            while(rs1.next() && rs2.next()){
                pstm3.setInt(4, rs1.getInt(1));
                pstm3.setInt(5, rs2.getInt(1));
            }
             JOptionPane.showMessageDialog(this,"Cazare adaugata!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(this,"Nu se poate adauga cazarea!");
        }
    }//GEN-LAST:event_butonAdaugaCazareActionPerformed

    private void butonHotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonHotelActionPerformed
        setPanelVisible(vizualizareHotel);
    }//GEN-LAST:event_butonHotelActionPerformed

    private void butonCautaCazareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautaCazareActionPerformed
        String text = fieldCazare.getText();
        DefaultTableModel model = (DefaultTableModel) tabelCazare.getModel();
        try{
            Connection con = getConnection();
            if(radioDenumire.isSelected()){
            String sql = "SELECT denumire_cazare, tip, nr_stele, circuit.denumire, sejur.oras_sosire FROM cazare, circuit, sejur WHERE "
                    + "cazare.cod_circuit = circuit.cod_circuit AND sejur.cod_sejur=cazare.cod_sejur and denumire_cazare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, text);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)});
            }
            fieldCazare.setText("");
            radioDenumire.setSelected(false);
        }else if(radioTip.isSelected()){
            String sql = "SELECT denumire_cazare, tip, nr_stele, circuit.denumire, sejur.oras_sosire FROM cazare, circuit, sejur WHERE "
                    + "cazare.cod_circuit = circuit.cod_circuit AND sejur.cod_sejur=cazare.cod_sejur and tip=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, text);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)});
            }
            fieldCazare.setText("");
            radioTip.setSelected(false);
        }else if(radioStele.isSelected()){
            String sql = "SELECT denumire_cazare, tip, nr_stele, circuit.denumire, sejur.oras_sosire FROM cazare, circuit, sejur WHERE "
                    + "cazare.cod_circuit = circuit.cod_circuit AND sejur.cod_sejur=cazare.cod_sejur and nr_stele=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(text));
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)});
            }
             fieldCazare.setText("");
             radioStele.setSelected(false);
        }
           
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(this,"Nu se poate realiza cautarea!");
        }
    }//GEN-LAST:event_butonCautaCazareActionPerformed

    private void fieldCazareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCazareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCazareActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void cautHotelMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cautHotelMeniuActionPerformed
        setPanelVisible(cautareHotel);
    }//GEN-LAST:event_cautHotelMeniuActionPerformed

    private void cautCircuitMeniuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cautCircuitMeniuActionPerformed
        setPanelVisible(cautareCircuit);
    }//GEN-LAST:event_cautCircuitMeniuActionPerformed

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
            System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(this,"Nu se poate realiza cautarea!");
        }
    }//GEN-LAST:event_butonCautaCircuitActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton10ActionPerformed

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
        }else if(radioTara.isSelected()){
            String sql = "SELECT oras_plecare, oras_sosire, data_plecare, data_sosire FROM sejur where data_plecare=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, text);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{"" + rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)});
            }
             fieldCautaSejur.setText("");
             radioTara.setSelected(false);
        }
           
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(this,"Nu se poate realiza cautarea!");
        }
    }//GEN-LAST:event_butonCautaSejurActionPerformed

    private void fieldCautaSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCautaSejurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCautaSejurActionPerformed

    private void butonContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonContActionPerformed
        setPanelVisible(contulMeu);
    }//GEN-LAST:event_butonContActionPerformed

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
        setPanelVisible(mainPageTursim);
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
            JOptionPane.showMessageDialog(this,"Datele au fost actualizate");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Actualizare esuata!");
        }
    }//GEN-LAST:event_butonSalveazaActionPerformed

    private void butonCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCircuitActionPerformed
        setPanelVisible(rezervareCircuit);
    }//GEN-LAST:event_butonCircuitActionPerformed

    private void clientNouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientNouActionPerformed
        numeClient.setText("");
        prenumeClient.setText("");
        telefonClient.setText("");
        emailClient.setText("");
        contClient.setText("");
        parolaClient.setText("");
    }//GEN-LAST:event_clientNouActionPerformed

    private void clientFidelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientFidelActionPerformed
        fillComboClientiFideli();

    }//GEN-LAST:event_clientFidelActionPerformed

    private void clientiFideliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clientiFideliItemStateChanged

    }//GEN-LAST:event_clientiFideliItemStateChanged

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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate adauga clientul!");
        }
       
    }//GEN-LAST:event_adaugaClientButonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void butonRezervareCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonRezervareCircuitActionPerformed
        try{
            Connection con = getConnection();
            String getCodClient = "SELECT cod_client FROM client WHERE nume=? and prenume=?";
            PreparedStatement pstm1 = con.prepareStatement(getCodClient);
            pstm1.setString(1, numeClient.getText());
            pstm1.setString(2,prenumeClient.getText());
            ResultSet rs1 = pstm1.executeQuery();
            
            String getCodCircuit = "SELECT cod_circuit FROM circuit WHERE denumire=?";
            PreparedStatement pstm2 = con.prepareStatement(getCodCircuit);
            pstm2.setString(1, comboRezervCircuit.getSelectedItem().toString());
            ResultSet rs2 = pstm2.executeQuery();
            
            String insertRezervare = "INSERT INTO rezervare_cicuit VALUES(?,?,null,?)";
            PreparedStatement pstm3 = con.prepareStatement(insertRezervare);
            while(rs1.next() && rs2.next()){
                pstm3.setInt(1, rs2.getInt(1));
                pstm3.setInt(2, rs1.getInt(1));
                pstm3.setString(3, anData.getSelectedItem().toString() + "-" + lunaData.getSelectedItem().toString()+ "-" + ziData.getSelectedItem().toString());
            }
            pstm3.executeUpdate();
            JOptionPane.showMessageDialog(this,"Rezervare reusita!");           

            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate rezerva circuitul!");           
        }
    }//GEN-LAST:event_butonRezervareCircuitActionPerformed

    private void ziDataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ziDataItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ziDataItemStateChanged

    private void ziDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ziDataActionPerformed
       
    }//GEN-LAST:event_ziDataActionPerformed

    private void comboRezervCircuitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRezervCircuitItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRezervCircuitItemStateChanged

    private void comboRezervCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRezervCircuitActionPerformed
        try{
            Connection con = getConnection();
            String getOrasByCircuit = "SELECT nume_oras FROM oras,circuit WHERE oras.cod_circuit=circuit.cod_circuit and denumire=? ";
            PreparedStatement pstm = con.prepareStatement(getOrasByCircuit);
            pstm.setString(1, comboRezervCircuit.getSelectedItem().toString());
            ResultSet rs = pstm.executeQuery();
            comboOrasCircuit.removeAllItems();
            while(rs.next()){
                comboOrasCircuit.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Nu se pot afisa orasele!");
        }
    }//GEN-LAST:event_comboRezervCircuitActionPerformed

    private void comboOrasCircuitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboOrasCircuitItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboOrasCircuitItemStateChanged

    private void comboOrasCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOrasCircuitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboOrasCircuitActionPerformed

    private void anDataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_anDataItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_anDataItemStateChanged

    private void anDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_anDataActionPerformed

    private void lunaDataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_lunaDataItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_lunaDataItemStateChanged

    private void lunaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunaDataActionPerformed
       if(lunaData.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anData.getSelectedItem().toString()))){
             ziData.removeAllItems();
            for(int i=1;i<=29;i++){               
                ziData.addItem(Integer.toString(i));
            }                     
        } else if(lunaData.getSelectedItem().equals("2") && anBisect(Integer.parseInt(anData.getSelectedItem().toString()))==false){
             ziData.removeAllItems();
            for(int i=1;i<=28;i++){               
                ziData.addItem(Integer.toString(i));
            } 
        } else if(lunaData.getSelectedItem().equals("1")|| lunaData.getSelectedItem().equals("3") || lunaData.getSelectedItem().equals("5") || lunaData.getSelectedItem().equals("7") || lunaData.getSelectedItem().equals("8") || lunaData.getSelectedItem().equals("10") || lunaData.getSelectedItem().equals("12")){
              ziData.removeAllItems();
            for(int i=1;i<=31;i++){                 
                 ziData.addItem(Integer.toString(i));     
            }            
        } else{
             ziData.removeAllItems();
            for(int i=1;i<=30;i++){                 
                ziData.addItem(Integer.toString(i));
            }            
        }
    }//GEN-LAST:event_lunaDataActionPerformed

    private void pretCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pretCircuitActionPerformed
        String circuit = comboRezervCircuit.getSelectedItem().toString();
        String an = anData.getSelectedItem().toString();
        String luna = lunaData.getSelectedItem().toString();
        String zi = ziData.getSelectedItem().toString();
        String data = an+"-"+luna+"-"+zi;
        try{
            Connection con = getConnection();
            String getPret = "SELECT valoare_circuit FROM circuit, pret_circuit,sezon WHERE circuit.cod_circuit = pret_circuit.cod_circuit AND pret_circuit.cod_sezon = sezon.cod_sezon AND ? between data_inceput and data_sfarsit AND denumire=?";
            PreparedStatement pstm = con.prepareStatement(getPret);
            pstm.setString(1, data);
            pstm.setString(2,circuit);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                pretCircuit.setText(Integer.toString(rs.getInt(1)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
             JOptionPane.showMessageDialog(this, "Nu se poate afisa pretul!");
        }
    }//GEN-LAST:event_pretCircuitActionPerformed

    private void pretCircuitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pretCircuitMouseClicked
        String circuit = comboRezervCircuit.getSelectedItem().toString();
        String an = anData.getSelectedItem().toString();
        String luna = lunaData.getSelectedItem().toString();
        String zi = ziData.getSelectedItem().toString();
        String data = an+"-"+luna+"-"+zi;
        try{
            Connection con = getConnection();
            String getPret = "SELECT valoare_circuit FROM circuit, pret_circuit,sezon WHERE circuit.cod_circuit = pret_circuit.circuit_cod_circuit AND pret_circuit.sezon_cod_sezon = sezon.cod_sezon AND ? between data_inceput and data_sfarsit AND denumire=?";
            PreparedStatement pstm = con.prepareStatement(getPret);
            pstm.setString(1, data);
            pstm.setString(2,circuit);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                pretCircuit.setText(Integer.toString(rs.getInt(1)));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Pret indisponibil!");
           
        }
    }//GEN-LAST:event_pretCircuitMouseClicked

    private void butonSejur2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonSejur2ActionPerformed
        setPanelVisible(rezervareSejur);
    }//GEN-LAST:event_butonSejur2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        setPanelVisible(mainPageTursim);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void adaugaClientButon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaugaClientButon1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adaugaClientButon1ActionPerformed

    private void plecareSejurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_plecareSejurItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_plecareSejurItemStateChanged

    private void plecareSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plecareSejurActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT data_sosire FROM sejur WHERE oras_plecare=? AND oras_sosire=? AND data_plecare=?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1,orasPlecareSejur.getSelectedItem().toString());
            p.setString(2,destinatieSejur.getSelectedItem().toString());
            p.setString(3,plecareSejur.getSelectedItem().toString());
            ResultSet r = p.executeQuery();
            sosireSejur.removeAllItems();
            while(r.next()){
                sosireSejur.addItem(r.getString(1));
            }
            
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Data sosire indisponibila!");
           
        }      
    }//GEN-LAST:event_plecareSejurActionPerformed

    private void clientFidel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientFidel1ActionPerformed
        fillComboClientiFideli2();
    }//GEN-LAST:event_clientFidel1ActionPerformed

    private void clientNou1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientNou1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientNou1ActionPerformed

    private void clientiFideli2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clientiFideli2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_clientiFideli2ItemStateChanged

    private void clientiFideli2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientiFideli2ActionPerformed
         try{
           Connection con = getConnection();
           String sql = "SELECT * FROM client WHERE nume = (substring_index(?,' ', 1))";
           PreparedStatement pstm = con.prepareStatement(sql);
           pstm.setString(1, clientiFideli2.getSelectedItem().toString());
           ResultSet rs = pstm.executeQuery();
           while(rs.next()){
               numeClient1.setText(rs.getString(2));
               prenumeClient1.setText(rs.getString(3));
               telefonClient1.setText(rs.getString(4));
               emailClient1.setText(rs.getString(5));
               contClient1.setText(rs.getString(6));
               parolaClient1.setText(rs.getString(7));
           }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate afisa clientul!");
        }
    }//GEN-LAST:event_clientiFideli2ActionPerformed

    private void orasPlecareSejurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_orasPlecareSejurItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_orasPlecareSejurItemStateChanged

    private void orasPlecareSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orasPlecareSejurActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT oras_sosire FROM sejur WHERE oras_plecare=?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1,orasPlecareSejur.getSelectedItem().toString());
            ResultSet r = p.executeQuery();
            
            while(r.next()){
                destinatieSejur.addItem(r.getString(1));
            }
            
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Destinatii indisponibile!");           
           
        }
    }//GEN-LAST:event_orasPlecareSejurActionPerformed

    private void sosireSejurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sosireSejurItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sosireSejurItemStateChanged

    private void sosireSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sosireSejurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sosireSejurActionPerformed

    private void destinatieSejurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_destinatieSejurItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_destinatieSejurItemStateChanged

    private void destinatieSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinatieSejurActionPerformed
        try{
            Connection con = getConnection();
            String sql = "SELECT data_plecare FROM sejur WHERE oras_plecare=? AND oras_sosire=?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1,orasPlecareSejur.getSelectedItem().toString());
            p.setString(2,destinatieSejur.getSelectedItem().toString());
            ResultSet r = p.executeQuery();
            plecareSejur.setSelectedItem("");
            while(r.next()){
                plecareSejur.addItem(r.getString(1));
            }
            
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Data plecare indisponibila!");         
           
        }
    }//GEN-LAST:event_destinatieSejurActionPerformed

    private void butonRezervaSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonRezervaSejurActionPerformed
        try{
            Connection con = getConnection();
            String getCodClient = "SELECT cod_client FROM client WHERE nume=? and prenume=?";
            PreparedStatement pstm1 = con.prepareStatement(getCodClient);
            pstm1.setString(1, numeClient1.getText());
            pstm1.setString(2,prenumeClient1.getText());
            ResultSet rs1 = pstm1.executeQuery();
            
            String getCodSejur = "SELECT cod_sejur FROM sejur WHERE oras_plecare=? and oras_sosire=? and data_plecare=? and data_sosire=?";
            PreparedStatement pstm2 = con.prepareStatement(getCodSejur);
            pstm2.setString(1, orasPlecareSejur.getSelectedItem().toString());
            pstm2.setString(2, destinatieSejur.getSelectedItem().toString());
            pstm2.setString(3, plecareSejur.getSelectedItem().toString());
            pstm2.setString(4, sosireSejur.getSelectedItem().toString());            
            ResultSet rs2 = pstm2.executeQuery();
            
            String insertRezervare = "INSERT INTO rezervare_sejur VALUES(?,?,null)";
            PreparedStatement pstm3 = con.prepareStatement(insertRezervare);
            while(rs1.next() && rs2.next()){
                
                pstm3.setInt(1, rs2.getInt(1));
                pstm3.setInt(2, rs1.getInt(1));
                
            }
            pstm3.executeUpdate();
            JOptionPane.showMessageDialog(this,"Rezervare reusita!");           

            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Nu se poate rezerva sejurul!");            
        }
    }//GEN-LAST:event_butonRezervaSejurActionPerformed

    private void pretSejurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pretSejurMouseClicked
          try{
            Connection con = getConnection();
            String getPret = "SELECT valoare_sejur FROM sejur, pret_sejur, sezon where sejur.cod_sejur = pret_sejur.sejur_cod_sejur AND "
                    + "pret_sejur.sezon_cod_sezon = sezon.cod_sezon and oras_plecare=? and oras_sosire=? and ? between data_inceput and data_sfarsit";
            PreparedStatement ps = con.prepareStatement(getPret);
            ps.setString(1, orasPlecareSejur.getSelectedItem().toString());
            ps.setString(2, destinatieSejur.getSelectedItem().toString());
            ps.setString(3, plecareSejur.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(nrPers.getText().equals("")){
                    pretSejur.setText(Integer.toString(rs.getInt(1)));
                } else{
                    pretSejur.setText(Integer.toString(rs.getInt(1)*Integer.parseInt(nrPers.getText())));
                }
            }
        }
        catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Pret indisponibil!");
           
        }
    }//GEN-LAST:event_pretSejurMouseClicked

    private void pretSejurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pretSejurActionPerformed
        
       
      
    }//GEN-LAST:event_pretSejurActionPerformed

    private void comboCamereGermaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCamereGermaniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCamereGermaniaActionPerformed

    private void butonAdaugareCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonAdaugareCircuitActionPerformed
        setPanelVisible(adaugareCircuit);
    }//GEN-LAST:event_butonAdaugareCircuitActionPerformed

    private void butonCautareCircuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonCautareCircuitActionPerformed
        setPanelVisible(cautareCircuit);
    }//GEN-LAST:event_butonCautareCircuitActionPerformed

    private void comboOrasPlecareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOrasPlecareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboOrasPlecareActionPerformed

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
            java.util.logging.Logger.getLogger(PaginaTurism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaTurism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaTurism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaTurism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaTurism().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FrantaHotels;
    private javax.swing.JPanel GermaniaHotels;
    private javax.swing.JPanel NorvegiaHotels;
    private javax.swing.JPanel TurciaHotels;
    private javax.swing.JMenu adaugCircuitMeniu;
    private javax.swing.JMenuItem adaugHotelMeniu;
    private javax.swing.JMenuItem adaugSejurMeniu;
    private javax.swing.JButton adaugaClientButon;
    private javax.swing.JButton adaugaClientButon1;
    private javax.swing.JPanel adaugareCircuit;
    private javax.swing.JPanel adaugareHotel;
    private javax.swing.JPanel adaugareSejur;
    private javax.swing.JComboBox<String> anData;
    private javax.swing.JComboBox<String> anPlecare;
    private javax.swing.JComboBox<String> anSosire;
    private javax.swing.JButton butonAdaugOras;
    private javax.swing.JButton butonAdaugaCazare;
    private javax.swing.JButton butonAdaugaCircuit;
    private javax.swing.JButton butonAdaugaSejur;
    private javax.swing.JButton butonAdaugareCircuit;
    private javax.swing.JButton butonCautaCazare;
    private javax.swing.JButton butonCautaCircuit;
    private javax.swing.JButton butonCautaSejur;
    private javax.swing.JButton butonCautareCircuit;
    private javax.swing.JButton butonCircuit;
    private javax.swing.JButton butonCont;
    private javax.swing.JButton butonHotel;
    private javax.swing.JButton butonInapoiCont;
    private javax.swing.JButton butonInapoiFranta;
    private javax.swing.JButton butonRezervaSejur;
    private javax.swing.JButton butonRezervareCircuit;
    private javax.swing.JButton butonSalveaza;
    private javax.swing.JButton butonSejur2;
    private javax.swing.JMenuItem cautCircuitMeniu;
    private javax.swing.JMenuItem cautHotelMeniu;
    private javax.swing.JMenuItem cautSejurMeniu;
    private javax.swing.JPanel cautareCircuit;
    private javax.swing.JPanel cautareHotel;
    private javax.swing.JMenu cautareMeniu;
    private javax.swing.JPanel cautareSejur;
    private javax.swing.JRadioButton clientFidel;
    private javax.swing.JRadioButton clientFidel1;
    private javax.swing.JRadioButton clientNou;
    private javax.swing.JRadioButton clientNou1;
    private javax.swing.JComboBox<String> clientiFideli;
    private javax.swing.JComboBox<String> clientiFideli2;
    private javax.swing.JComboBox<String> comboCamere;
    private javax.swing.JComboBox<String> comboCamereFranta;
    private javax.swing.JComboBox<String> comboCamereGermania;
    private javax.swing.JComboBox<String> comboCamereNorvegia;
    private javax.swing.JComboBox<String> comboCamereTurcia;
    private javax.swing.JComboBox<String> comboCazareFranta;
    private javax.swing.JComboBox<String> comboCazareNorvegia;
    private javax.swing.JComboBox<String> comboCazareTropical;
    private javax.swing.JComboBox<String> comboCazareTurcia;
    private javax.swing.JComboBox<String> comboCircuitCazare;
    private javax.swing.JComboBox<String> comboCircuite;
    private javax.swing.JComboBox<String> comboGermania;
    private javax.swing.JComboBox<String> comboNrStele;
    private javax.swing.JTextField comboNumarStele;
    private javax.swing.JComboBox<String> comboOrasCircuit;
    private javax.swing.JComboBox<String> comboOrasPlecare;
    private javax.swing.JComboBox<String> comboOrasSosire;
    private javax.swing.JComboBox<String> comboOraseCircuit;
    private javax.swing.JComboBox<String> comboRezervCircuit;
    private javax.swing.JComboBox<String> comboSejurCazare;
    private javax.swing.JComboBox<String> comboTipCazare;
    private javax.swing.JTextField contClient;
    private javax.swing.JTextField contClient1;
    private javax.swing.JTextField contCont;
    private javax.swing.JPanel contulMeu;
    private javax.swing.JTextField denumireCazare;
    private javax.swing.JTextField denumireCircuit;
    private javax.swing.JTextField denumireTara;
    private javax.swing.JComboBox<String> destinatieSejur;
    private javax.swing.JTextField emailClient;
    private javax.swing.JTextField emailClient1;
    private javax.swing.JTextField emailCont;
    private javax.swing.JTextField fieldCautaSejur;
    private javax.swing.JTextField fieldCazare;
    private javax.swing.JTextField fieldCircuit;
    private javax.swing.JLabel hotelTropical;
    private javax.swing.JButton iesireButon;
    private javax.swing.JLabel imgProfilAng;
    private javax.swing.JButton inapoiGermania;
    private javax.swing.JButton inapoiHoteluri;
    private javax.swing.JButton inapoiSejur;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
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
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> lunaData;
    private javax.swing.JComboBox<String> lunaPlecare;
    private javax.swing.JComboBox<String> lunaSosire;
    private javax.swing.JPanel mainPageTursim;
    private javax.swing.JTextField nrPers;
    private javax.swing.JTextField nrSteleFranta;
    private javax.swing.JTextField nrSteleGermania;
    private javax.swing.JTextField nrSteleNorvegia;
    private javax.swing.JTextField nrSteleTurcia;
    private javax.swing.JTextField numeClient;
    private javax.swing.JTextField numeClient1;
    private javax.swing.JTextField numeCont;
    private javax.swing.JComboBox<String> orasPlecareSejur;
    private javax.swing.JTextField parolaClient;
    private javax.swing.JTextField parolaClient1;
    private javax.swing.JTextField parolaCont;
    private javax.swing.JComboBox<String> plecareSejur;
    private javax.swing.JTextField prenumeClient;
    private javax.swing.JTextField prenumeClient1;
    private javax.swing.JTextField prenumeCont;
    private javax.swing.JTextField pretCircuit;
    private javax.swing.JTextField pretSejur;
    private javax.swing.JLabel progrVoiajLabel;
    private javax.swing.JRadioButton radioDataPlecare;
    private javax.swing.JRadioButton radioDenumire;
    private javax.swing.JRadioButton radioDenumireCircuit;
    private javax.swing.JRadioButton radioFranta;
    private javax.swing.JRadioButton radioGermania;
    private javax.swing.JRadioButton radioNorvegia;
    private javax.swing.JRadioButton radioOras;
    private javax.swing.JRadioButton radioOrasPlecare;
    private javax.swing.JRadioButton radioOrasSosire;
    private javax.swing.JRadioButton radioStele;
    private javax.swing.JRadioButton radioTara;
    private javax.swing.JRadioButton radioTip;
    private javax.swing.JRadioButton radioTropical;
    private javax.swing.JRadioButton radioTurcia;
    private javax.swing.JPanel rezervareCircuit;
    private javax.swing.JPanel rezervareSejur;
    private javax.swing.JComboBox<String> sosireSejur;
    private javax.swing.JTable tabelCautaSejur;
    private javax.swing.JTable tabelCazare;
    private javax.swing.JTable tabelCircuit;
    private javax.swing.JTable tabelOrase;
    private javax.swing.JTable tabelSejur;
    private javax.swing.JTextField telefonClient;
    private javax.swing.JTextField telefonClient1;
    private javax.swing.JTextField telefonCont;
    private javax.swing.JLabel titluContulMeu;
    private javax.swing.JPanel tropicalHotels;
    private javax.swing.JMenuItem vizCircuitMeniu;
    private javax.swing.JMenuItem vizHotelMeniu;
    private javax.swing.JMenuItem vizSejurMeniu;
    private javax.swing.JPanel vizualizareCircuit;
    private javax.swing.JPanel vizualizareHotel;
    private javax.swing.JMenu vizualizareMeniu;
    private javax.swing.JPanel vizualizareSejur;
    private javax.swing.JComboBox<String> ziData;
    private javax.swing.JComboBox<String> ziPlecare;
    private javax.swing.JComboBox<String> ziSosire;
    // End of variables declaration//GEN-END:variables
}
