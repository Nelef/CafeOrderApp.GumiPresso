package com.ssafy.gumipresso.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.tmap.*
import com.ssafy.gumipresso.model.service.LocationService
import com.ssafy.gumipresso.util.DateFormatUtil
import com.ssafy.gumipresso.util.DateFormatUtil.Companion.convertTMapArrivalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory

class GPSViewModel:ViewModel() {
    private val _location: MutableLiveData<Location>? = MutableLiveData<Location>()
    val location: LiveData<Location>?
        get() = _location
    var locationRepository: LocationService? = null

    fun setLocationRepository(context: Context) {
        locationRepository = LocationService.getInstance(context)
    }

    fun setLocationItem(location: Location){
        _location!!.value = location
    }

    fun enableLocationServices(){
        locationRepository?.let {
            it.startService()
        }
    }

    private val _arrivalTime =  MutableLiveData<String>()
    val arrivalTime: LiveData<String>
        get() = _arrivalTime

    fun getArrivalTime(){
        val location = _location!!.value as Location
        viewModelScope.launch(Dispatchers.IO){
            val client = OkHttpClient()

            val format = TMapDTO(
                RoutesInfo(
                    Departure("03", location.latitude.toString(), location.longitude.toString(),"출발지"),
                    Destination("03",36.10830144233874.toString(), 128.41827450414362.toString(),"GumiPresso","1000559885","16"),
                    DateFormatUtil.convertyyyyMMddTHHmmsszz(),"arrival")
            )
            val json = Gson().toJson(format)
            val body = RequestBody.create("application/json".toMediaTypeOrNull(), json)
            val request = Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/prediction?resCoordType=WGS84GEO&reqCoordType=WGS84GEO&sort=index")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("appKey", "l7xx4413ff598ab447188293f216e681c583")
                .build()
            val response = client.newCall(request).execute()
            val responseBody = JSONObject(response.body!!.string())

            val receiveForm = Gson().fromJson(responseBody.toString(),ReceiveForm::class.java)
            _arrivalTime.postValue(convertTMapArrivalTime(receiveForm.features[0].properties.arrivalTime))
        }
    }
}