package org.d3ifcool.menkosik.model;

import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;

/**
 * Created by Faizal on 10/28/2018.
 */

public class DataKos implements Serializable {
    private String mNamaPemilik;
    private String mHpPemilik;
    private String mNamaKost;
    private String mAlamatKost;
    private String mKotaKost;
    private String mDaerahKost;
    private String mHargaKost;
    private String mLokasiKost;
    private String mFotoKost;
    private double mLongitude;
    private double mLatitude;
    private String mFasilitas;

    public DataKos() {
    }

    public DataKos(String mNamaPemilik, String mHpPemilik, String mNamaKost, String mAlamatKost, String mKotaKost, String mDaerahKost, String mHargaKost, String mLokasiKost, String mFotoKost) {
        this.mNamaPemilik = mNamaPemilik;
        this.mHpPemilik = mHpPemilik;
        this.mNamaKost = mNamaKost;
        this.mAlamatKost = mAlamatKost;
        this.mKotaKost = mKotaKost;
        this.mDaerahKost = mDaerahKost;
        this.mHargaKost = mHargaKost;
        this.mLokasiKost = mLokasiKost;
        this.mFotoKost = mFotoKost;
    }


    public DataKos(String mNamaPemilik, String mHpPemilik, String mNamaKost, String mAlamatKost, String mKotaKost, String mDaerahKost, String mHargaKost, String mLokasiKost, String mFotoKost, double mLongitude, double mLatitude) {
        this.mNamaPemilik = mNamaPemilik;
        this.mHpPemilik = mHpPemilik;
        this.mNamaKost = mNamaKost;
        this.mAlamatKost = mAlamatKost;
        this.mKotaKost = mKotaKost;
        this.mDaerahKost = mDaerahKost;
        this.mHargaKost = mHargaKost;
        this.mLokasiKost = mLokasiKost;
        this.mFotoKost = mFotoKost;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public DataKos(String mNamaPemilik, String mHpPemilik, String mNamaKost, String mAlamatKost, String mKotaKost, String mDaerahKost, String mHargaKost, String mLokasiKost, String mFotoKost, double mLongitude, double mLatitude, String mFasilitas) {
        this.mNamaPemilik = mNamaPemilik;
        this.mHpPemilik = mHpPemilik;
        this.mNamaKost = mNamaKost;
        this.mAlamatKost = mAlamatKost;
        this.mKotaKost = mKotaKost;
        this.mDaerahKost = mDaerahKost;
        this.mHargaKost = mHargaKost;
        this.mLokasiKost = mLokasiKost;
        this.mFotoKost = mFotoKost;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
        this.mFasilitas = mFasilitas;
    }

    public String getmFasilitas() {
        return mFasilitas;
    }

    public void setmFasilitas(String mFasilitas) {
        this.mFasilitas = mFasilitas;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmNamaPemilik() {
        return mNamaPemilik;
    }

    public void setmNamaPemilik(String mNamaPemilik) {
        this.mNamaPemilik = mNamaPemilik;
    }

    public String getmHpPemilik() {
        return mHpPemilik;
    }

    public void setmHpPemilik(String mHpPemilik) {
        this.mHpPemilik = mHpPemilik;
    }

    public String getmNamaKost() {
        return mNamaKost;
    }

    public void setmNamaKost(String mNamaKost) {
        this.mNamaKost = mNamaKost;
    }

    public String getmAlamatKost() {
        return mAlamatKost;
    }

    public void setmAlamatKost(String mAlamatKost) {
        this.mAlamatKost = mAlamatKost;
    }

    public String getmKotaKost() {
        return mKotaKost;
    }

    public void setmKotaKost(String mKotaKost) {
        this.mKotaKost = mKotaKost;
    }

    public String getmDaerahKost() {
        return mDaerahKost;
    }

    public void setmDaerahKost(String mDaerahKost) {
        this.mDaerahKost = mDaerahKost;
    }

    public String getmHargaKost() {
        return mHargaKost;
    }

    public void setmHargaKost(String mHargaKost) {
        this.mHargaKost = mHargaKost;
    }

    public String getmLokasiKost() {
        return mLokasiKost;
    }

    public void setmLokasiKost(String mLokasiKost) {
        this.mLokasiKost = mLokasiKost;
    }

    public String getmFotoKost() {
        return mFotoKost;
    }

    public void setmFotoKost(String mFotoKost) {
        this.mFotoKost = mFotoKost;
    }
}