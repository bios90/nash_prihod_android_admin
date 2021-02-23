package com.dimfcompany.nashprihodadmin.base.enums

import com.google.gson.annotations.SerializedName

enum class TypeFile
{
    @SerializedName("file")
    FILE,

    @SerializedName("image")
    IMAGE,

    @SerializedName("video")
    VIDEO;
}