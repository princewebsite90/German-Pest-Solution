package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookingReference: String,
    val serviceId: String,
    val serviceTitle: String,
    val propertyType: String,
    val price: Int,
    val customerName: String,
    val customerPhone: String,
    val serviceAddress: String,
    val preferredDate: String,
    val timeSlot: String,
    val additionalNotes: String,
    val status: String = "Confirmed",
    val createdAt: Long = System.currentTimeMillis()
)
