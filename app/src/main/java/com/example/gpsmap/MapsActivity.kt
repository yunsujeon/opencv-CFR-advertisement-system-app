package com.example.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private lateinit var mMap: GoogleMap
    //위치정보를 주기적으로 얻는데 필요한 객체들을 선언
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_maps)
        // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit() //생성한 변수들 초기화
    }

    //위치정보를 얻기 위한 각종 초기화
    private fun locationInit(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()

        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3600000 //한시간단위로 내 위치를 자동으로 갱신한다.
        locationRequest.fastestInterval = 3600000
    }

    /**
    사용가능한 맵을 조작합니다
     지도를 사용할 준비가 되면 이 콜백이 호출됩니다
     여기서 마커나 선, 청취자를 추가하거나 카메라를 이동할 수 있습니다
     호주 시드니 근처에 마커를 추가하고 있습니다
     google play 서비스가 기기에 설치되어 있지 않은 경우 사용자에게
     supportmapfragment안에 google play 서비스를 설치하라는 메시지가 표시됩니다.
     이 메서드는 사용자가 google play 서비스를 설치하고 앱으로 돌아온 후에만
     호출됩니다.
     */
    override fun onMapReady(googleMap: GoogleMap) {



        mMap = googleMap //지도가 준비되면 googlemap 객체를 얻습니다.

        val mju = LatLng(37.221873, 127.186694)
        mMap.addMarker(MarkerOptions().position(mju)
            .title("명지대학교")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet("인구:3천 나이분포:30대 초당가격:10000 "))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju))

        val mju1 = LatLng(37.226972, 127.193256) //명지로 157번길 자취방들
        mMap.addMarker(MarkerOptions().position(mju1)
            .title("명지로 157번길")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju1))

        val mju2 = LatLng(37.226649, 127.197487) //명지로 209번길 자취방들
        mMap.addMarker(MarkerOptions().position(mju2)
            .title("명지로 209번길")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju2))

        val mju3 = LatLng(37.229973, 127.189003) //명지대 내려가는길
        mMap.addMarker(MarkerOptions().position(mju3)
            .title("명지로 55번길")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju3))

        val mju4 = LatLng(37.228749, 127.186385) //명지로 자취방들
        mMap.addMarker(MarkerOptions().position(mju4)
            .title("명지로 자취방1")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju4))

        val mju5 = LatLng(37.231286, 127.187308) //명지로 자취방들
        mMap.addMarker(MarkerOptions().position(mju5)
            .title("명지로 자취방2")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju5))

        val mju6 = LatLng(37.230167, 127.184411) //골드클래스 용인 역북아파트
        mMap.addMarker(MarkerOptions().position(mju6)
            .title("골드클래스 용인 아파트")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju6))

        val mju7 = LatLng(37.231107, 127.182598) //역북지웰푸르지오아파트
        mMap.addMarker(MarkerOptions().position(mju7)
            .title("역북지웰푸르지오 아파트")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju7))

        val mju8 = LatLng(37.233763, 127.185580) //우미린 센트럴파크 아파트
        mMap.addMarker(MarkerOptions().position(mju8)
            .title("우미린 센트럴파크 아파트")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju8))

        val mju9 = LatLng(37.235608, 127.187061) //동원로얄듀크
        mMap.addMarker(MarkerOptions().position(mju9)
            .title("동원로얄듀크 아파트")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju9))

        val mju10 = LatLng(37.233362, 127.187930) //역북 상가
        mMap.addMarker(MarkerOptions().position(mju10)
            .title("역북 상가")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju10))

        val mju11 = LatLng(37.235071, 127.190377) //신성아파트
        mMap.addMarker(MarkerOptions().position(mju11)
            .title("역북마을 신성아파트")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju11))

        val mju12 = LatLng(37.233525, 127.189486) //구 상가
        mMap.addMarker(MarkerOptions().position(mju12)
            .title("구 상가지구")
            .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW))
            .snippet(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mju12))
    }


//focus 를 얻었을 때 불리는게 on Resume
    override fun onResume(){
        super.onResume()
        permissionCheck(cancel = { showPermissionInfoDialog()}, ok={addLocationListener()})
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    inner class MyLocationCallBack: LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?){
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run{
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity","위도:$latitude, 경도:$longitude")
            }
        }
    }

    private fun permissionCheck(cancel:()->Unit,ok:()->Unit){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                cancel()
            }
            else{
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_ACCESS_FINE_LOCATION)
            }
        }
        else{
            ok()
        }
    }

    private fun showPermissionInfoDialog(){
        alert("현재 위치 정보를 얻으려면 위치 권한이 필요합니다", "권한이 필요한 이유"){
            yesButton{
                ActivityCompat.requestPermissions(this@MapsActivity,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton{}
        }.show()
    }

    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<out String>,grantResults:IntArray){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        when(requestCode){
            REQUEST_ACCESS_FINE_LOCATION->{
                if((grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    addLocationListener()
                }
                else{
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    override fun onPause(){
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}