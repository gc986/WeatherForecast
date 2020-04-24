package ru.gc986.weatherforecast

import android.Manifest
import android.graphics.drawable.Animatable
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_weather_info.*
import ru.gc986.commontools.Temperature
import ru.gc986.dialogs.Dialogs
import ru.gc986.models.Consts.Companion.TEN_MINUTES
import ru.gc986.models.weather.Weather
import ru.gc986.weatherforecast2.p.main.MainPresenter
import ru.gc986.weatherforecast2.p.main.MainViewI
import ru.gc986.weatherforecast2.v.common.CheckPermissions
import ru.gc986.weatherforecast2.v.common.CheckedPlayServices
import ru.gc986.weatherforecast2.v.common.HandlerTimer


class MainActivity : MvpAppCompatActivity(), MainViewI {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    var menuUpdate: Animatable? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var userLongitude: Double? = null
    var userLatitude: Double? = null
    val handlerTimer = HandlerTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        CheckedPlayServices(this).toCheckPlayServices({
            checkPermissions {
                getLocation()
                // create timer
                handlerTimer.start(TEN_MINUTES){
                    getCurrentWeather()
                }
            }
        }, { message ->
            Dialogs().showTextDialog(
                context = this,
                title = getString(R.string.app_can_t_continue_working),
                description = message,
                answer = { closeApp() })
        })

    }

    override fun onResume() {
        super.onResume()
        handlerTimer.reset()
    }

    override fun onPause() {
        super.onPause()
        handlerTimer.stop()
    }

    private fun closeApp() {
        finish()
    }

    private fun checkPermissions(onCompleted: () -> Unit) {
        CheckPermissions(this)
            .toCheckPermissions(
                onCompleted,
                { closeApp() },
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                userLongitude = location?.longitude
                userLatitude = location?.latitude
                getCurrentWeather()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update -> {
                val icon = item.icon
                if (icon is Animatable)
                    menuUpdate = icon

                checkPermissions {
                    getCurrentWeather()
                }

                return true
            }
        }
        return false
    }

    private fun getCurrentWeather() {
        if (userLatitude != null && userLongitude != null)
            presenter.getWeather(userLongitude!!, userLatitude!!)
        else
            showMessage(getString(R.string.coordinates_are_not_defined))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun weatherUpdatesInProgress() {
        menuUpdate?.start()
    }

    override fun weatherUpdatesStopProgress() {
        menuUpdate?.stop()
    }

    override fun onNewWeather(weather: Weather, iconPath: String) {
        tvLocation.text = weather.name
        tvLalitude.text = weather.coord.lat.toString()
        tvLongitude.text = weather.coord.lon.toString()
        val celsius = Temperature.kelvinsToCelsius(weather.main.temp)
        val celsiusFeelsLike = Temperature.kelvinsToCelsius(weather.main.feels_like)
        val temp = "$celsius (${getString(R.string.feels_like)} $celsiusFeelsLike)"
        tvTemperature.text = temp

        Picasso.with(this).cancelRequest(ivIcoWeather)
        Picasso.with(this)
            .load(iconPath)
            .noPlaceholder()
            .error(android.R.drawable.ic_menu_gallery)
            .into(ivIcoWeather)

        incLastWeather.visibility = View.VISIBLE
    }

}
