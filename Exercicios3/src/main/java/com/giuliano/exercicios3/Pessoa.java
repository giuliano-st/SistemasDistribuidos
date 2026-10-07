/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.giuliano.exercicios3;

import java.io.Serializable;
import java.net.InetAddress;

public class Pessoa implements Serializable {

    private String nome;
    private String email;
    private String token;
    private long timestampToken;
    private InetAddress ip;
    private int porta;

    public Pessoa(String nome, String email, InetAddress ip, int porta) {
        this.nome = nome;
        this.email = email;
        this.ip = ip;
        this.porta = porta;
        this.gerarNovoToken();
    }
    
    public void gerarNovoToken() {
        this.token = String.format("%06d", (int) (Math.random() * 1000000));
        this.timestampToken = System.currentTimeMillis();
    }
    
    public boolean tokenExpirado() {
        return (System.currentTimeMillis() - this.timestampToken) > 60000;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestampToken() {
        return timestampToken;
    }

    public void setTimestampToken(long timestampToken) {
        this.timestampToken = timestampToken;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
    
    

    @Override
    public String toString() {
        return "Pessoa{" + "nome=" + nome + ", email=" + email + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        boolean mesmoNome = (this.nome != null && this.nome.equalsIgnoreCase(other.nome));
        boolean mesmoEmail = (this.email != null && this.email.equalsIgnoreCase(other.email));
        return mesmoNome || mesmoEmail;
    }
}
