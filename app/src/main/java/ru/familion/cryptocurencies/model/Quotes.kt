package ru.familion.cryptocurencies.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName;

data class Quotes (

        @SerializedName("USD")
        @Embedded
        var uSD : USD

)