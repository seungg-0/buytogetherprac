package com.example.buytogetherprac

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        getHashKey()
        checkPermission()

        /*RegisterAvtivity로 화면전환*/
        RegistBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
/*해시키 받아오는 함수
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
*/

    /*
    권한처리 메서드
     */
    fun startProcess(){
        setContentView(R.layout.activity_main)

    }
    val permissions=arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)

    fun checkPermission(){
        var permitted_all=true
        for (permission in permissions){
            val result=ContextCompat.checkSelfPermission(this, permission)
            if (result!=PackageManager.PERMISSION_GRANTED){
                permitted_all=false
                requestPermission()
                break
            }
        }
        if (permitted_all){
            startProcess()
        }

    }
    fun confirmAgain(){
        AlertDialog.Builder(this)
            .setTitle("권한 승인 확인")
            .setMessage("위치 관련 권한을 모두 승인하셔야 앱을 사용할 수 있습니다. 권한 승인을 다시 하시겠습니까?")
            .setPositiveButton("네",{ _, _->requestPermission()})
            .setNegativeButton("아니요",{ _, _->finish()})
            .create()
            .show()
    }
    fun requestPermission(){
        ActivityCompat.requestPermissions(this,permissions,99)
    }

    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<out String>, grantResults:IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            99->{
                var granted_all=true
                for(result in grantResults){
                    if (result!=PackageManager.PERMISSION_GRANTED){
                        granted_all=false
                        break
                    }
                }
                if (granted_all){
                    startProcess()
                }
                else{
                    confirmAgain()
                }
            }
        }
    }

}



