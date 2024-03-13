/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author dangVuDinh
 */
public class MyIP extends IPV4 implements IPInterface{
    private int prefixLength;
    //constructor
    public MyIP() {
    }
    public MyIP( String ip, int prefixLength) {
        super(ip);
        this.prefixLength = prefixLength;
    }
    //get set
    public void setIp(String ip, int preLen) throws Exception {
        String regex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        if(!ip.matches(regex)){
            throw new Exception("IP");
        }
        if(preLen < 0 || preLen > 32){
            throw new Exception("preLen");
        }
        super.setIp(ip);
        this.prefixLength = preLen;
    }
    @Override
    public String getIp() {
        return super.getIp() + "/" + this.prefixLength;
    }
    //method
    
    private String[] calculateWithPreLen() {
        String[] res = new String[4];
        int digitCount;
        int digit_1 = this.prefixLength;
        //part1
        digitCount = 8;
        String part1 = "";
        while(digitCount-- != 0){
            if(digit_1 > 0){
                part1 += "1";
            }else{
                part1 += "0";
            }
            digit_1 --;
        }
        //part1
        digitCount = 8;
        String part2 = "";
        while(digitCount-- != 0){
            if(digit_1 > 0){
                part2 += "1";
            }else{
                part2 += "0";
            }
            digit_1 --;
        }
        //part1
        digitCount = 8;
        String part3 = "";
        while(digitCount-- != 0){
            if(digit_1 > 0){
                part3 += "1";
            }else{
                part3 += "0";
            }
            digit_1 --;
        }
        //part1
        digitCount = 8;
        String part4 = "";
        while(digitCount-- != 0){
            if(digit_1 > 0){
                part4  += "1";
            }else{
                part4  += "0";
            }
            digit_1 --;
        }
        res[0] = part1;
        res[1] = part2;
        res[2] = part3;
        res[3] = part4;
        return res;
    }
    
    @Override
    public String calculateSubnetMask() {
        String res  = "";
        //tính toán cho 4 thành phần trong subnetMask
        String[] temp = this.calculateWithPreLen();
        for(int i=0; i<4; i++){
            res += Integer.parseInt(temp[i], 2);
            if(i<3){
                res += ".";
            }
        }
        return res;
    }

    @Override
    public String calculateNetworkAddress() {
        String res = "";
        String[] preLen = this.calculateWithPreLen();
        //chuyển IP sang nhị phân
        String[] ipStringDec = super.getIp().split("\\b\\.\\b");
        for(int i=0;i<4;i++){
            res += (Integer.parseInt(ipStringDec[i]) & Integer.parseInt(preLen[i], 2));
            if(i<3){
                res+=".";
            }
        }
        return res;
    }
    
    private String decToBin(int a){
        String res = "";
        while(a != 0){
            res += (a%2 + "");
            a/=2;
        }
        while(res.length() < 8){
            res += "0";
        }
        return new StringBuilder(res).reverse().toString();
    }

    @Override
    public String calculateBroadcast() {
        String res = "";
        String nwadr = this.calculateNetworkAddress();
        String[] nwadrArr = nwadr.split("\\.");
        for(int i=0;i<4;i++){
            nwadrArr[i] = this.decToBin(Integer.parseInt(nwadrArr[i]));
        }
        String tempNwadr = String.join("", nwadrArr);
        int hostBit = 32 - this.prefixLength;
        tempNwadr = tempNwadr.substring(0, 32 - hostBit);
        while(hostBit-- != 0){
            tempNwadr+="1";
        }
        String[] brdcstArr = new String[4];
        brdcstArr[0] = tempNwadr.substring(0, 8);
        brdcstArr[1] = tempNwadr.substring(8, 16);
        brdcstArr[2] = tempNwadr.substring(16, 24);
        brdcstArr[3] = tempNwadr.substring(24, 32);
        for(int i=0;i<4;i++){
            res += Integer.parseInt(brdcstArr[i], 2);
            if(i<3){
                res += ".";
            }
        }
        return res;
    }

    @Override
    public String calculateFirstAddress() {
        String nwadr = this.calculateNetworkAddress();
        // Tìm vị trí của dấu "." thứ ba
        int thirdDotIndex = nwadr.indexOf('.', nwadr.indexOf('.', nwadr.indexOf('.') + 1) + 1);
        String lastPart = nwadr.substring(thirdDotIndex+1);
        lastPart = "" + (Integer.parseInt(lastPart) + 1);
        return nwadr.substring(0, thirdDotIndex+1) + lastPart;
    }

    @Override
    public String calculateLastAddress() {
        String bc = this.calculateBroadcast();
        // Tìm vị trí của dấu "." thứ ba
        int thirdDotIndex = bc.indexOf('.', bc.indexOf('.', bc.indexOf('.') + 1) + 1);
        String lastPart = bc.substring(thirdDotIndex+1);
        lastPart = "" + (Integer.parseInt(lastPart) - 1);
        return bc.substring(0, thirdDotIndex+1) + lastPart;
    }
    
    //method file
    public void writeToFile(File a, BufferedWriter bw, String a1, String a2, String a3, String a4, String a5) throws IOException{
        bw.write("IP: " + this.getIp());
        bw.newLine();
        bw.write("Subnet mask: " + a1);
        bw.newLine();
        bw.write("Broadcast: " + a2);
        bw.newLine();
        bw.write("Network address: " + a3);
        bw.newLine();
        bw.write("First address: " + a4);
        bw.newLine();
        bw.write("Last address: " + a5);
        bw.newLine();
        bw.write("__________________________");
        bw.newLine();
        bw.flush();
    }
    
}
