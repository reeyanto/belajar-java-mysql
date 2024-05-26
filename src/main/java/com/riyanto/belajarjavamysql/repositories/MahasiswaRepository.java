package com.riyanto.belajarjavamysql.repositories;

import com.riyanto.belajarjavamysql.models.Mahasiswa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MahasiswaRepository {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public MahasiswaRepository(Connection connection) {
        this.connection = connection;
    }
    
    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                mahasiswaList.add(new Mahasiswa(
                    rs.getString("nim"), 
                    rs.getString("nama"), 
                    rs.getString("prodi"))
                );
            }
            return mahasiswaList;
            
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Mahasiswa getMahasiswa(String nim) {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, nim);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                Mahasiswa mahasiswa = new Mahasiswa(
                    rs.getString("nim"), 
                    rs.getString("nama"), 
                    rs.getString("prodi")
                );
                return mahasiswa;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public boolean insertMahasiswa(Mahasiswa mahasiswa) {
        String sql = "INSERT INTO mahasiswa (nim, nama, prodi) VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, mahasiswa.getNim());
            ps.setString(2, mahasiswa.getNama());
            ps.setString(3, mahasiswa.getProdi());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean updateMahasiswa(Mahasiswa mahasiswa) {
        String sql = "UPDATE mahasiswa SET nama = ?, prodi = ? WHERE nim = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, mahasiswa.getNama());
            ps.setString(2, mahasiswa.getProdi());
            ps.setString(3, mahasiswa.getNim());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteMahasiswa(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, nim);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
