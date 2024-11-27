package com.example.budgetbuddy.ui.elements.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.ui.graphics.toArgb
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.PlaceCategory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomMapRenderer(
    val context: Context,
    val googleMap: GoogleMap,
    val clusterManager: ClusterManager<Place>
) : DefaultClusterRenderer<Place>(context, googleMap, clusterManager) {

    override fun shouldRenderAsCluster(cluster: Cluster<Place>): Boolean {
        return cluster.size > 2
    }

    override fun onBeforeClusterItemRendered(item: Place, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        val iconResId = PlaceCategory.fromString(item.category.name).icon
        val bitmap = getBitmapFromVectorDrawable(
            context,
            iconResId,
            PlaceCategory.fromString(item.category.name).itemColor.toArgb() ?: Color.WHITE
        )

        if (bitmap != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        } else {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }

        markerOptions.title(item.name)
    }

    private fun getBitmapFromVectorDrawable(
        context: Context,
        vectorResId: Int,
        backgroundColor: Int = Color.WHITE
    ): Bitmap {
        Log.d("vectorResId", "${vectorResId}")
        val drawable = VectorDrawableCompat.create(context.resources, vectorResId, null)
        val padding = 8F
        val diameter = maxOf(drawable!!.intrinsicWidth, drawable.intrinsicHeight) + (padding * 2)

        val bitmap =
            Bitmap.createBitmap(diameter.toInt(), diameter.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.color = backgroundColor
        paint.isAntiAlias = true

        val center = diameter / 2
        canvas.drawCircle(center, center, diameter / 2, paint)

        val drawableSize = maxOf(drawable.intrinsicWidth, drawable.intrinsicHeight)
        val left = (diameter - drawableSize) / 2
        val top = (diameter - drawableSize) / 2
        val right = left + drawableSize
        val bottom = top + drawableSize

        drawable.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        drawable.draw(canvas)

        return bitmap
    }
}
