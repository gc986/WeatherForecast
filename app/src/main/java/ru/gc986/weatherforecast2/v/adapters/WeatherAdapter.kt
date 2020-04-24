package ru.gc986.weatherforecast2.v.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.gc986.commontools.Temperature
import ru.gc986.models.weather.WeatherFull
import ru.gc986.weatherforecast.R
import java.text.SimpleDateFormat


class WeatherAdapter(val context: Context,
    val weathers: ArrayList<WeatherFull>
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dateFormat = SimpleDateFormat("ss:mm:HH dd.MM.yyyy")

    fun addWeather(weatherFull: WeatherFull){
        weathers.add(0, weatherFull)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.view_weather_info, parent, false))

    override fun getItemCount(): Int = weathers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weathers[position]

        holder.tvLocation.text = weather.weather.name
        holder.tvLalitude.text = weather.weather.coord.lat.toString()
        holder.tvLongitude.text = weather.weather.coord.lon.toString()
        val celsius = Temperature.kelvinsToCelsius(weather.weather.main.temp)
        val celsiusFeelsLike = Temperature.kelvinsToCelsius(weather.weather.main.feels_like)
        val temp = "$celsius (${getString(R.string.feels_like)} $celsiusFeelsLike)"
        holder.tvTemperature.text = temp

        Picasso.with(context).cancelRequest(holder.ivIcoWeather)
        Picasso.with(context)
            .load(weather.iconPath)
            .noPlaceholder()
            .error(android.R.drawable.ic_menu_gallery)
            .into(holder.ivIcoWeather)

        holder.tvDate.text = dateFormat.format(weather.weather.time)
    }

    private fun getString(@StringRes resId: Int) = context.getString(resId)

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal val tvDate: TextView = view.findViewById(R.id.tvDate)
        internal val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        internal val tvLalitude: TextView = view.findViewById(R.id.tvLalitude)
        internal val tvLongitude: TextView = view.findViewById(R.id.tvLongitude)
        internal val ivIcoWeather: ImageView = view.findViewById(R.id.ivIcoWeather)
        internal val tvTemperature: TextView = view.findViewById(R.id.tvTemperature)
    }

}