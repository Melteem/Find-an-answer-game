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
        this.dogruCevap = dogruCevap.toLowerCase(); // Cevaplar� b�y�k/k���k harf duyarl�l��� olmadan kontrol edebilmek i�in k���k harfe d�n��t�r�yoruz.
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
        System.out.println("Soru-Cevap Oyunu Ba�lad�!");
    }

    @Override
    public void oyna() {
        Scanner scanner = new Scanner(System.in);
        for (Soru soru : sorular) {
            System.out.println(soru.soruMetni);
            String cevap = scanner.nextLine().toLowerCase(); // Kullan�c�n�n girdisiyle ayn� formatta cevab� k���k harfe d�n��t�r�yoruz.
            if (soru.cevapKontrol(cevap)) {
                System.out.println("Tebrikler, do�ru cevap! " + soru.puan + " puan kazand�n�z.");
                toplamPuan += soru.puan;
            } else {
                System.out.println("Maalesef, yanl�� cevap. Do�ru cevap: " + soru.dogruCevap + ". " + soru.puan + " puan kaybettiniz.");
            }
        }
        sonlandir();
    }

    private void sonlandir() {
        devamEdiyor = false;
        System.out.println("Oyun bitti. Toplam puan�n�z: " + toplamPuan);
    }
}


class MetinSorusu extends Soru {
    public MetinSorusu(String soruMetni, String dogruCevap, int puan) {
        super(soruMetni, dogruCevap, puan);
    }

    @Override
    boolean cevapKontrol(String cevap) {
        return cevap.equals(dogruCevap); // K���k/k���k harf duyarl�l��� olmadan kontrol edilmesini sa�lamak i�in equals metodu kullan�l�yor.
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
        sorular.add(new MetinSorusu("Java hangi t�r bir programlama dilidir?", "nesne tabanl�", 10));
        sorular.add(new DogruYanlisSorusu("netflix java dili ile olu�turulmu�tur. (do�ru/yanl��)", "do�ru", 10));
        sorular.add(new MetinSorusu("Java platformu hangi �irket taraf�ndan geli�tirilmi�tir?", "oracle", 10));

        Oyun soruCevapOyunu = new SoruCevapOyunu(sorular);
        soruCevapOyunu.baslat();
        soruCevapOyunu.oyna();
    }
}
