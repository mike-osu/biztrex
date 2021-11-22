package edu.oregonstate.biztrex

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vmadalin.easypermissions.EasyPermissions
import edu.oregonstate.biztrex.databinding.ActivityPermissionBinding

/**
 * Screen for requesting location permission from user
 */
class PermissionActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPermission.setOnClickListener {
            checkLocationPermission()
        }

        checkLocationPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        binding.btnPermission.visibility = View.INVISIBLE
        proceed()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        binding.btnPermission.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.rationale_location), Toast.LENGTH_LONG).show()
    }

    private fun checkLocationPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *permissions))
            proceed()
        else
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location), 124, *permissions)
    }

    private fun proceed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}