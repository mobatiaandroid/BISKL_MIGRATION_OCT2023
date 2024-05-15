package com.example.bskl_kotlin.activity.settings.model

import android.os.Parcel
import android.os.Parcelable

data class StudentUserModel(
    val studentname: String?,
    val classAndSection: String?,
    val photo: String?,
    val stud_id: String?,
    val alumi: String?
): Parcelable
{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentname)
        parcel.writeString(classAndSection)
        parcel.writeString(photo)
        parcel.writeString(stud_id)
        parcel.writeString(alumi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentUserModel> {
        override fun createFromParcel(parcel: Parcel): StudentUserModel {
            return StudentUserModel(parcel)
        }

        override fun newArray(size: Int): Array<StudentUserModel?> {
            return arrayOfNulls(size)
        }
    }


}