package com.toko.datatoko.services;

import com.toko.datatoko.bean.PegawaiInfoBean;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.constant.RoleConstant;
import com.toko.datatoko.models.Pegawai;
import com.toko.datatoko.repository.PegawaiRepository;
import com.toko.datatoko.utils.SecurityUtils;

public class PegawaiServices {

    PegawaiRepository pegawaiRepository;

    public PegawaiServices(PegawaiRepository pegawaiRepository) {
        this.pegawaiRepository = pegawaiRepository;
    }

    public String login(String username,String password){
        try{
            Pegawai forCheck = pegawaiRepository.findPegawaiByUsername(username).get(0);
            if(forCheck.getUsername().equals(username)){
                if(SecurityUtils.verifyPassword(password,forCheck.getPassword())){
                    return ErrorConstant.CREDENTIAL_VALID;
                } else{
                    return ErrorConstant.CREDENTIAL_INVALID;
                }
            }
            return ErrorConstant.CREDENTIAL_INVALID;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException){
            return ErrorConstant.CREDENTIAL_INVALID;
        }
    }

    public String createPegawai(String username,String password,String role){
        try{
            Pegawai forCheck = pegawaiRepository.findPegawaiByUsername(username).get(0);
        } catch (IndexOutOfBoundsException ex){
            String hashPassword = SecurityUtils.passwordEncode(password);
            pegawaiRepository.insert(new Pegawai(0,username,hashPassword,role));
            return ErrorConstant.CREATE_ACCOUNT_SUCCESS;
        }
        return ErrorConstant.CREATE_ACCOUNT_FAILED;
    }

    public String updatePassword(String username,String newPassword){
        try{
            Pegawai forCheck = pegawaiRepository.findPegawaiByUsername(username).get(0);
            String newPasswordHashed = SecurityUtils.passwordEncode(newPassword);
            forCheck.setPassword(newPasswordHashed);
            pegawaiRepository.update(forCheck);
            return ErrorConstant.UPDATE_PASSWORD_SUCCESS;
        } catch (Exception ex){
            ex.printStackTrace();
            return ErrorConstant.UPDATE_PASSWORD_FAILED;
        }
    }

    public String updateRole(String username,String newRole){
        if(newRole.equals(RoleConstant.ROLE_MASTER) || newRole.equals(RoleConstant.ROLE_ADMIN)){
            try{
                Pegawai forCheck = pegawaiRepository.findPegawaiByUsername(username).get(0);
                forCheck.setRole(newRole);
                pegawaiRepository.update(forCheck);
                return ErrorConstant.UPDATE_ROLE_SUCCESS;
            } catch (Exception ex){
                ex.printStackTrace();
                return ErrorConstant.UPDATE_ROLE_FAILED;
            }
        } else{
            return ErrorConstant.INVALID_ROLE_ACCOUNT;
        }

    }

    public PegawaiInfoBean getInfoPegawai(String username){
        Pegawai pegawai = pegawaiRepository.findPegawaiByUsername(username).get(0);
        return new PegawaiInfoBean(pegawai.getIdPegawai(),pegawai.getUsername(),pegawai.getRole());
    }

    public String deleteAccount(String username){
        Pegawai validateAccount = null;
        try {
            validateAccount = pegawaiRepository.findPegawaiByUsername(username).get(0);
        } catch (IndexOutOfBoundsException x){
            return ErrorConstant.ACCOUNT_NOT_FOUND;
        }
        try{
            pegawaiRepository.delete(username);
            return ErrorConstant.DELETE_ACCOUNT_SUCCESS;
        } catch (Exception x){
            x.printStackTrace();
            return ErrorConstant.DELETE_ACCOUNT_FAILED;
        }
    }
}
