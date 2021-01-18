package com.toko.datatoko;

import com.toko.datatoko.bean.PegawaiInfoBean;
import com.toko.datatoko.config.DatabaseConfig;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.constant.MenuConstant;
import com.toko.datatoko.constant.RoleConstant;
import com.toko.datatoko.models.Produk;
import com.toko.datatoko.models.Transaksi;
import com.toko.datatoko.repository.AccountRepository;
import com.toko.datatoko.repository.PegawaiRepository;
import com.toko.datatoko.repository.ProdukRepository;
import com.toko.datatoko.repository.TransaksiRepository;
import com.toko.datatoko.services.AccountServices;
import com.toko.datatoko.services.PegawaiServices;
import com.toko.datatoko.services.ProdukServices;
import com.toko.datatoko.services.TransaksiServices;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static Connection conn;
    static Statement statement;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    static Scanner sc = new Scanner(System.in);


    public static void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033\143");
        }
        catch (IOException | InterruptedException ex) {

        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        //initialize DB Connection
        Connection connection = DatabaseConfig.getConnection();

        //initialize Repository
        TransaksiRepository transaksiRepository = new TransaksiRepository(connection);
        AccountRepository accountRepository = new AccountRepository(connection);
        PegawaiRepository pegawaiRepository = new PegawaiRepository(connection);
        ProdukRepository produkRepository = new ProdukRepository(connection);

        //initialize services for logic bussiness using dependencies injection
        AccountServices accountServices = new AccountServices(accountRepository);
        PegawaiServices pegawaiServices = new PegawaiServices(pegawaiRepository);
        ProdukServices produkServices = new ProdukServices(produkRepository);
        TransaksiServices transaksiServices = new TransaksiServices(transaksiRepository,produkServices);

        boolean isExit=false;
        int menu = MenuConstant.MAIN_MENU;
        String currentUser = "";
        PegawaiInfoBean pegawaiYangLogin = null;

        while(isExit==false){
            switch (menu){
                case MenuConstant.MAIN_MENU:
                    clearTerminal();
                    System.out.println("#----- DataBase -----#");
                    System.out.println("#1.Login  #");
                    System.out.println("#0.Exit#");
                    int input = Integer.parseInt(sc.nextLine());
                    if(input == 1){
                        menu = MenuConstant.LOGIN_MENU;
                    }
                    else if(input == 0){
                        menu = MenuConstant.EXIT;
                    }
                    break;
                case MenuConstant.EXIT:
                    System.out.println("OK Goodbye :D");
                    isExit=true;
                    break;
                case MenuConstant.LOGIN_MENU:
                    clearTerminal();
                    System.out.println("#----- DataBaseGudang -----#");
                    System.out.println("#  Login           #");
                    System.out.println("#------------------#");
                    System.out.println("Username = ");
                    String loginUsername = sc.nextLine();
                    System.out.println("Pasword = ");
                    String loginPas = sc.nextLine();
                    String responseLogin = pegawaiServices.login(loginUsername,loginPas);
                    if(responseLogin.equals(ErrorConstant.CREDENTIAL_VALID)){
                        currentUser = loginUsername;
                        pegawaiYangLogin = pegawaiServices.getInfoPegawai(loginUsername);
//                        PegawaiInfoBean checkRole = pegawaiServices.getInfoPegawai(loginUsername);
                        if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_ADMIN)){
                            menu = MenuConstant.ACCOUNT_MENU_ADMIN;
                            break;
                        } else if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_MASTER)){
                            menu = MenuConstant.ACCOUNT_MENU_MASTER;
                            break;
                        }
                    } else {
                        System.out.println(responseLogin);
                        break;
                    }

                case MenuConstant.ACCOUNT_MENU_MASTER:
                    clearTerminal();
                    System.out.println("#----- DataBase -----#");
                    System.out.println("#1. Lihat Gudang  #");
                    System.out.println("#2. Lihat transaksi  #");
                    System.out.println("#3. Create Baru  #");
                    System.out.println("#0. Exit#");
                    int menuAccount = Integer.parseInt(sc.nextLine());
                    if(menuAccount==1){
                        menu=MenuConstant.LIHAT_GUDANG_MASTER;
                        break;
                    }
                    else if(menuAccount==2){
                        menu = MenuConstant.LIHAT_TRANSAKSI;
                        break;
                    }
                    else if(menuAccount==3){
                        menu = MenuConstant.ACCOUNT_MENU;
                        break;

                    }
                    else if(menuAccount==0){
                        menu = MenuConstant.EXIT;
                        break;
                    }
                case MenuConstant.ACCOUNT_MENU_ADMIN:
                    System.out.println("#----- DataBase -----#");
                    System.out.println("#1. Lihat Gudang     #");
                    System.out.println("#2. Lihat transaksi  #");
                    System.out.println("#0. Exit ------------#");
                    int menuAccountAdmin = Integer.parseInt(sc.nextLine());
                    if(menuAccountAdmin==1){
                        menu = MenuConstant.LIHAT_GUDANG_ADMIN;
                        break;
                    }
                    else if(menuAccountAdmin==2){
                        menu = MenuConstant.LIHAT_TRANSAKSI;
                        break;
                    }
                    else if(menuAccountAdmin==0){
                        menu = MenuConstant.EXIT;
                        break;
                    }
                case MenuConstant.LIHAT_GUDANG_MASTER:
                    List<Produk> produkListMaster = produkServices.getGudangProduk();
                    System.out.println("#----- DatabaseGudang -----#");
                    produkListMaster.stream().forEach(System.out::println);
                    System.out.println("#1----- Edit -----#");
                    System.out.println("#2 Kembali ke menu--#");
                    System.out.println("#------------------#");
                    int Tombol = Integer.parseInt(sc.nextLine());
                    if(Tombol == 1){
                        menu = MenuConstant.EDIT_GUDANG;
                    } else if(Tombol == 2){
                        menu = MenuConstant.ACCOUNT_MENU_MASTER;
                    }
                    break;
                case MenuConstant.LIHAT_GUDANG_ADMIN:
                    List<Produk> produkList = produkServices.getGudangProduk();
                    System.out.println("#----- DatabaseGudang -----#");
                    produkList.stream().forEach(System.out::println);
                    System.out.println("#1 Kembali ke menu--#");
                    int Gudang= Integer.parseInt(sc.nextLine());
                    if(Gudang == 1){
                        menu = MenuConstant.ACCOUNT_MENU_ADMIN;
                    }
                    break;
                case MenuConstant.LIHAT_TRANSAKSI:
                    List<Transaksi> transaksiList = transaksiServices.showAllTransaksi();
                    System.out.println("#----- DatabaseGudang -----#");
                    transaksiList.stream().forEach(System.out::println);
                    System.out.println("#1 Edit Transaksi--#");
                    System.out.println("#2 Kembali ke menu--#");
                    int Transaksi= Integer.parseInt(sc.nextLine());
                    if(Transaksi == 1){
                        menu = MenuConstant.EDIT_TRANSAKSI;
                    }
                    if(Transaksi == 2){
                        if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_ADMIN)){
                            menu = MenuConstant.ACCOUNT_MENU_ADMIN;
                        } else if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_MASTER)) {
                            menu = MenuConstant.ACCOUNT_MENU_MASTER;
                        }
                        break;
                    }
                    break;
                case MenuConstant.EDIT_TRANSAKSI:
                    System.out.println("#----- DatabaseGudang -----#");
                    System.out.println("#1---- Tambah ----------------#");
                    System.out.println("#2---- Kembali ke Login----------------#");
                    int EditTransaksi= Integer.parseInt(sc.nextLine());
                    if(EditTransaksi == 1){
                        System.out.println("Masukan Amount = ?");
                        int amount = Integer.parseInt(sc.nextLine());
                        System.out.println("Masukan ID Produk = ?");
                        int idProduk = Integer.parseInt(sc.nextLine());
                        String addTransaksiResponse = transaksiServices.createTransaction(BigDecimal.valueOf(amount),idProduk,0);
                        System.out.println(addTransaksiResponse);
                        menu = MenuConstant.LIHAT_TRANSAKSI;
                    }
                    else if(EditTransaksi == 2){
                        menu = MenuConstant.LOGIN_MENU;
                    }
                    break;
                case MenuConstant.ACCOUNT_MENU:
                    System.out.println("#----- Account Menu-----#");
                    System.out.println("#1----- Edit Acount -----#");
                    System.out.println("#2-------Hapus Acounr-#");
                    System.out.println("#3------Tambah Acount--#");
                    System.out.println("#4------Kemablai ke Menu--#");
                    int Acount = Integer.parseInt(sc.nextLine());
                    if(Acount==1){
                        menu = MenuConstant.EDIT_ACCOUNT;
                        break;
                    }
                    else if(Acount==2){
                        menu = MenuConstant.DELETE_ACCOUNT;
                        break;
                    }
                    else if(Acount==3){
                        menu = MenuConstant.TAMBAH_ACCOUNT;
                        break;
                    }
                    else if(Acount==4){
                        menu = MenuConstant.ACCOUNT_MENU_MASTER;
                        break;
                    }
                case MenuConstant.EDIT_ACCOUNT:
                    System.out.println("#----- Account Menu-----#");
                    System.out.println("#1----- Edit Acount -----#");
                    System.out.println("#2-----Kembali ke Account---#");
                    System.out.println("#3-----Kembali ke Menu---#");
                    int Edit = Integer.parseInt(sc.nextLine());
                    if(Edit==1){
                        System.out.println("Masukan username yang mau di update (kosongkan jika mengganti akun diri sendiri)");
                        String usernameusername = sc.nextLine();
                        System.out.println(usernameusername);
                        System.out.println("Masukkan password baru (kosongkan atau abaikan jika tidak mau ganti)");
                        String requestChangePassword = sc.nextLine();
                        System.out.println(requestChangePassword);
                        System.out.println("Account sebagai? (ketik 'admin' atau 'master')");
                        String requestChangeRole = sc.nextLine();
                        System.out.println(requestChangeRole);
                        if(requestChangeRole.equals("admin")){
                            requestChangeRole = RoleConstant.ROLE_ADMIN;
                        } else if(requestChangeRole.equals("master")) {
                            requestChangeRole = RoleConstant.ROLE_MASTER;
                        } else {
                            System.out.println(ErrorConstant.INVALID_ROLE_ACCOUNT);
                            break;
                        }
                        if(usernameusername.isEmpty()){
                            if(!requestChangePassword.isEmpty()){
                                String reponseUpdatePwd = pegawaiServices.updatePassword(currentUser,requestChangePassword);
                                System.out.println(reponseUpdatePwd);
                            }
                            String reponseUpdateRole = pegawaiServices.updateRole(currentUser,requestChangeRole);
                            System.out.println(reponseUpdateRole);
                        } else {
                            if(!requestChangePassword.isEmpty()){
                                String reponseUpdatePwd = pegawaiServices.updatePassword(usernameusername,requestChangePassword);
                                System.out.println(reponseUpdatePwd);
                            }
                            String reponseUpdateRole = pegawaiServices.updateRole(currentUser,requestChangeRole);
                            System.out.println(reponseUpdateRole);
                        }
                        break;
                    }
                    else if(Edit==2){
                        menu = MenuConstant.ACCOUNT_MENU;
                        break;
                    }
                    else if(Edit==3){
                        menu = MenuConstant.ACCOUNT_MENU_MASTER;
                        break;
                    }
                case MenuConstant.TAMBAH_ACCOUNT:
                    System.out.println("#----- Account Menu-----#");
                    System.out.println("#1----- Tambah Accunt -----#");
                    System.out.println("#2-----Kembali ke Account---#");
                    System.out.println("#3-----Kembali ke Menu---#");
                    int Tambah = Integer.parseInt(sc.nextLine());
                    if(Tambah==1){
                        System.out.println("Account sebagai? (ketik 'admin' atau 'master')");
                        String createRole = sc.nextLine();
                        if(createRole.equals("admin")){
                            createRole = RoleConstant.ROLE_ADMIN;
                        } else if(createRole.equals("master")) {
                            createRole = RoleConstant.ROLE_MASTER;
                        } else {
                            System.out.println(ErrorConstant.INVALID_ROLE_ACCOUNT);
                            break;
                        }
                        System.out.println("Username = ?");
                        String createUsername = sc.nextLine();
                        System.out.println("Password = ?");
                        String createPassword = sc.nextLine();
                        String responseFromService = pegawaiServices.createPegawai(createUsername,createPassword,createRole);
                        System.out.println(responseFromService);
                        break;
                    }
                    else if(Tambah==2){
                        menu = MenuConstant.ACCOUNT_MENU;
                        break;
                    }
                    else if(Tambah==3){
                        menu = MenuConstant.ACCOUNT_MENU_MASTER;
                        break;
                    }
                case MenuConstant.DELETE_ACCOUNT:
                    System.out.println("#----- Account Menu-----#");
                    System.out.println("#1----- Delete Account -----#");
                    System.out.println("#2-----Kembali ke Account---#");
                    System.out.println("#3-----Kembali ke Menu---#");
                    int Delete = Integer.parseInt(sc.nextLine());
                    if(Delete==1){
                        System.out.println("Masukan username yang ingin di hapus");
                        String usernameForDelete = sc.nextLine();
                        String responseDelete = pegawaiServices.deleteAccount(usernameForDelete);
                        System.out.println(responseDelete);
                        break;
                    }
                    else if(Delete==2){
                        menu = MenuConstant.ACCOUNT_MENU;
                        break;
                    }
                    else if(Delete==3){
                        menu = MenuConstant.ACCOUNT_MENU_MASTER;
                        break;
                    }
                case MenuConstant.EDIT_GUDANG:
                    System.out.println("----- Edit Gudang -----#");
                    System.out.println("1. Tambah Produk");
                    System.out.println("2. Edit Produk");
                    int inputEditGudang = Integer.parseInt(sc.nextLine());
                    if(inputEditGudang==1){
                        System.out.println("Nama Produk yang ingin ditambahkan");
                        String nameProd = sc.nextLine();
                        System.out.println("Nama Merchant");
                        String inputMechant = sc.nextLine();
                        System.out.println("Jumlah unit");
                        int unitInput = Integer.parseInt(sc.nextLine());
                        String responseAddProduct = produkServices.addProduct(nameProd,"1",inputMechant,unitInput);
                        System.out.println(responseAddProduct);
                        if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_MASTER)){
                            menu = MenuConstant.LIHAT_GUDANG_MASTER;
                        } else if(pegawaiYangLogin.getRole().equals(RoleConstant.ROLE_ADMIN)){
                            menu = MenuConstant.LIHAT_GUDANG_ADMIN;
                        }
                        break;
                    } else if(inputEditGudang==2) {
                        System.out.println("Id produk yang mau di update");
                        int idProdUpdate = Integer.parseInt(sc.nextLine());

                        Produk prodCheck = produkServices.getProdukById(idProdUpdate);

                        System.out.println("Nama produk Baru (Kosongkan jika tidak ingin di update)");
                        String nameProductUpdate = sc.nextLine();
                        System.out.println("Jumlah Unit = ? ");
                        int jumlahUnitUpdate = Integer.parseInt(sc.nextLine());

                        if(nameProductUpdate.isEmpty()){
                            nameProductUpdate = prodCheck.getNamaProduk();
                        }

                        String responseUpdateProduct = produkServices.updateProduct(idProdUpdate,nameProductUpdate,jumlahUnitUpdate);
                        System.out.println(responseUpdateProduct);
                        menu=MenuConstant.LIHAT_GUDANG_MASTER;
                        break;
                    }
                    break;
            }
        }
    }
}
