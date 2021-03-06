package ru.gc986.weatherforecast

import android.Manifest
import android.graphics.drawable.Animatable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_weather_info.*
import ru.gc986.commontools.Temperature
import ru.gc986.dialogs.Dialogs
import ru.gc986.models.Consts.Companion.TEN_MINUTES
import ru.gc986.models.weather.WeatherFull
import ru.gc986.weatherforecast2.v.adapters.WeatherAdapter
import ru.gc986.weatherforecast2.v.common.*
import ru.gc986.weatherforecast2.vm.main.MainViewModel
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity(), LifecycleObserver {

    var menuUpdate: Animatable? = null
    var userLongitude: Double? = null
    var userLatitude: Double? = null
    private val handlerTimer = HandlerTimer()
    private var currentWeather: WeatherFull? = null
    private lateinit var viewModel: MainViewModel
    private val dateFormat = SimpleDateFormat("ss:mm:HH dd.MM.yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        toSubscribe()
        viewModel.getWeathers()

        menuUpdate?.start()

        lifecycle.addObserver(this)
        performingNecessaryChecks()
    }

    private fun toSubscribe() {
        viewModel.newWeather.observe(this, Observer {
            onNewWeather(it)
        })
        viewModel.weathers.observe(this, Observer {
            showWeathers(ArrayList(it))
        })
        viewModel.inProgress.observe(this, Observer {
            if (it)
                menuUpdate?.start()
            else
                menuUpdate?.stop()
        })
        viewModel.onErrMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun performingNecessaryChecks() {
        CheckedPlayServices(this).toCheckPlayServices({
            checkPermissions {
                getLocation()
                // create timer
                handlerTimer.start(TEN_MINUTES) {
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeTimer() {
        handlerTimer.reset()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopTimer() {
        handlerTimer.stop()
    }

    private fun closeApp() {
        finish()
    }

    private fun checkPermissions(onCompleted: () -> Unit) {
        val checkPermissions = CheckPermissions(this)
        checkPermissions.toCheckPermissions(
                onCompleted,
                { closeApp() },
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        lifecycle.addObserver(checkPermissions)
    }

    private fun getLocation() {
        val lm = LocationManager()
        lm.location.observe(this, Observer {location ->
            userLongitude = location.longitude
            userLatitude = location.latitude
            getCurrentWeather()
        })
        lm.getLocation(this)
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
                    handlerTimer.reset()
                    getCurrentWeather()
                }

                return true
            }
        }
        return false
    }

    private fun getCurrentWeather() {
        if (userLatitude != null && userLongitude != null)
            viewModel.getWeather(userLongitude!!, userLatitude!!)
        else
            showMessage(getString(R.string.coordinates_are_not_defined))
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onNewWeather(weather: WeatherFull) {
        tvLocation.text = weather.weather.name
        tvLalitude.text = weather.weather.coord.lat.toString()
        tvLongitude.text = weather.weather.coord.lon.toString()
        val celsius = Temperature.kelvinsToCelsius(weather.weather.main.temp)
        val celsiusFeelsLike = Temperature.kelvinsToCelsius(weather.weather.main.feels_like)
        val temp = "$celsius (${getString(R.string.feels_like)} $celsiusFeelsLike)"
        tvWinter.text = makeWindText(weather)
        tvTemperature.text = temp
        tvDate.text = dateFormat.format(weather.weather.time)

        Picasso.with(this).cancelRequest(ivIcoWeather)
        Picasso.with(this)
            .load(weather.iconPath)
            .noPlaceholder()
            .error(android.R.drawable.ic_menu_gallery)
            .into(ivIcoWeather)

        incLastWeather.visibility = View.VISIBLE
        if (currentWeather != null && rvList.adapter != null)
            (rvList.adapter as WeatherAdapter).addWeather(currentWeather!!)

        currentWeather = weather
    }

    private fun makeWindText(weather: WeatherFull): String = getString(
        R.string.wind_,
        weather.weather.wind.speed.toString(),
        weather.weather.wind.deg.toString()
    )

    private fun showWeathers(weathers: ArrayList<WeatherFull>) {
        tvPreviousWeathers.visibility = View.VISIBLE
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = WeatherAdapter(this, weathers)
    }

}
