package Oyun123;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Oyun.java
interface Oyun {
    void baslat();
    void oyna();
}


abstract class Soru {
    protected String soruMetni;
    protected String dogruCevap;
    protected int puan;

    public Soru(String soruMetni, String dogruCevap, int puan) {
        this.soruMetni = soruMetni;
        this.dogruCevap = dogruCevap.toLowerCase(); // Cevaplarý büyük/küçük harf duyarlýlýðý olmadan kontrol edebilmek için küçük harfe dönüþtürüyoruz.
        this.puan = puan;
    }

    abstract boolean cevapKontrol(String cevap);
}


class SoruCevapOyunu implements Oyun {
    private List<Soru> sorular;
    private int toplamPuan;
    private boolean devamEdiyor;

    public SoruCevapOyunu(List<Soru> sorular) {
        this.sorular = sorular;
        this.toplamPuan = 0;
        this.devamEdiyor = false;
    }

    @Override
    public void baslat() {
        devamEdiyor = true;
        toplamPuan = 0;
        System.out.println("Soru-Cevap Oyunu Baþladý!");
    }

    @Override
    public void oyna() {
        Scanner scanner = new Scanner(System.in);
        for (Soru soru : sorular) {
            System.out.println(soru.soruMetni);
            String cevap = scanner.nextLine().toLowerCase(); // Kullanýcýnýn girdisiyle ayný formatta cevabý küçük harfe dönüþtürüyoruz.
            if (soru.cevapKontrol(cevap)) {
                System.out.println("Tebrikler, doðru cevap! " + soru.puan + " puan kazandýnýz.");
                toplamPuan += soru.puan;
            } else {
                System.out.println("Maalesef, yanlýþ cevap. Doðru cevap: " + soru.dogruCevap + ". " + soru.puan + " puan kaybettiniz.");
            }
        }
        sonlandir();
    }

    private void sonlandir() {
        devamEdiyor = false;
        System.out.println("Oyun bitti. Toplam puanýnýz: " + toplamPuan);
    }
}


class MetinSorusu extends Soru {
    public MetinSorusu(String soruMetni, String dogruCevap, int puan) {
        super(soruMetni, dogruCevap, puan);
    }

    @Override
    boolean cevapKontrol(String cevap) {
        return cevap.equals(dogruCevap); // Küçük/küçük harf duyarlýlýðý olmadan kontrol edilmesini saðlamak için equals metodu kullanýlýyor.
    }
}


class DogruYanlisSorusu extends Soru {
    public DogruYanlisSorusu(String soruMetni, String dogruCevap, int puan) {
        super(soruMetni, dogruCevap, puan);
    }

    @Override
    boolean cevapKontrol(String cevap) {
        return cevap.equals(dogruCevap);
    }
}

// Ana.javan
public class Ana {
    public static void main(String[] args) {
        List<Soru> sorular = new ArrayList<>();
        sorular.add(new MetinSorusu("Java hangi tür bir programlama dilidir?", "nesne tabanlý", 10));
        sorular.add(new DogruYanlisSorusu("netflix java dili ile oluþturulmuþtur. (doðru/yanlýþ)", "doðru", 10));
        sorular.add(new MetinSorusu("Java platformu hangi þirket tarafýndan geliþtirilmiþtir?", "oracle", 10));

        Oyun soruCevapOyunu = new SoruCevapOyunu(sorular);
        soruCevapOyunu.baslat();
        soruCevapOyunu.oyna();
    }
}
