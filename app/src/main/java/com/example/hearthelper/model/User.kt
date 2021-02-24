package com.example.hearthelper.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class User(var id: String = "", var age: Double? = null, var anaemia: Long? = null, var creatinine_phosphokinase: Long?= null, var diabetes: Long? = null, var ejection_fraction: Long? = null, var high_blood_pressure: Long? = null, var platelets: Double? = null, var serum_creatinine: Double? = null,var serum_sodium: Long? = null, var sex: Long? = null, var smoking: Long? = null) :
    Parcelable {
    override fun toString(): String {
        return "${this.id}, ${this.age}, ${this.anaemia}, ${this.creatinine_phosphokinase}, ${this.diabetes}, ${this.ejection_fraction}, ${this.high_blood_pressure}, ${this.platelets}, ${this.serum_creatinine}, ${this.serum_sodium}, ${this.sex}, ${this.smoking}"
    }
}

