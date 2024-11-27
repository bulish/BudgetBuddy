package com.example.budgetbuddy.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "places")
data class Place(
    var name: String,
    var address: String,
    var category: PlaceCategory,
    var latitude: Double?,
    var longitude: Double?,
    var userId: String,
    var created: LocalDateTime = LocalDateTime.now(),
    var imageName: String?
): ClusterItem {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    val updatedDateFormatted: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            return created.format(formatter)
        }

    override fun getPosition(): LatLng {
        return LatLng(latitude ?: 0.0, longitude ?: 0.0)
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return address
    }

    override fun getZIndex(): Float? {
        return 0.0f
    }
}
