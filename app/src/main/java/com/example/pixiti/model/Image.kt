package com.example.pixiti.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("largeImageURL")
    val largeImageURL: String?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("comments")
    val comments: Int?,
    @SerializedName("pageURL")
    val pageURL: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("favorites")
    val favorites: Int?,
    @SerializedName("previewURL")
    val previewURL: String?,
    @SerializedName("imageWidth")
    val imageWidth: Int?,
    @SerializedName("imageHeight")
    val imageHeight: Int?,
    @SerializedName("imageSize")
    val imageSize: Int?,
    @SerializedName("views")
    val views: Int?,
    @SerializedName("downloads")
    val downloads: Int?,
    @SerializedName("userImageURL")
    val userImageURL: String?
) : Parcelable