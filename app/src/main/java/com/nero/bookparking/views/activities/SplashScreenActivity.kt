package com.nero.bookparking.views.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nero.bookparking.MainActivity
import com.nero.bookparking.databinding.ActivitySplashScreenBinding
import com.nero.bookparking.helper.*
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding

    val FINE_LOCATION_RO = 101

    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    val SIGN_IN_CODE = 10
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceHelper.getSharedPreferences(this)

        checkForPermissions(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            "location",
            FINE_LOCATION_RO
        )

        Handler().postDelayed(Runnable {
            when {
                PreferenceHelper.getLoginBooleanFromPreference(USER_PHONE_LOGIN) -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                PreferenceHelper.getLoginBooleanFromPreference(KEY_LOGIN_WITH_OAUTH) -> {
                    initializeSignin()
                }
                else -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }, 3000)
    }

    private fun initializeSignin() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mAuth = FirebaseAuth.getInstance()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val intent: Intent = googleSignInClient.signInIntent
        startActivityForResult(intent, SIGN_IN_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_CODE) {
            val task: Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            if (task!!.isSuccessful) {
                val account = task.getResult(ApiException::class.java)
                PreferenceHelper.writeBooleanToPreference(KEY_LOGIN_WITH_OAUTH, true)
                updatePreference(account!!)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("UserName", account.displayName)
                intent.putExtra("UserEmail", account.email)
                intent.putExtra("UserPhoto", account.photoUrl?.toString())
//                Toast.makeText(this, "Welcome ${account.displayName}", Toast.LENGTH_SHORT)
//                    .show()
                saveUser(account)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Login Error " + task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }
    }

    private fun updatePreference(account: GoogleSignInAccount) {
        PreferenceHelper.writeBooleanToPreference(KEY_USER_LOGGED_IN, true)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_ID, account.id)
        PreferenceHelper.writeStringToPreference(
            KEY_DISPLAY_NAME,
            account.displayName
        )
        PreferenceHelper.writeStringToPreference(
            KEY_USER_GOOGLE_GMAIL,
            account.email
        )
    }

    private fun saveUser(account: GoogleSignInAccount) {

        val database = FirebaseDatabase.getInstance()

        val dbUsers = database.getReference("users").child(account.id!!)

        dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //even if user exit in database the FCM token will be different for each device
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseDatabase.getInstance().getReference("users")
                                .child(account.id!!)
                            val token = Objects.requireNonNull(task.result)
                            user.child("token").setValue(token)
                        } else {
                            Log.d("TAG", "onComplete: " + task.exception!!.message)
                        }
                    }
//                    Toast.makeText(this@SplashScreenActivity, "Welcome back ${account.displayName}", Toast.LENGTH_SHORT)
//                        .show()
                    return
                }
                if (!snapshot.exists()) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val token: String = Objects.requireNonNull<String>(it.result)
                            val username: String = ""
                            val user =
                                UserModel(
                                    account.email!!,
                                    account.displayName,
                                    username,
                                    account.photoUrl?.toString(),
                                    null,
                                    token
                                )

                            dbUsers.setValue(user)
                                .addOnCompleteListener { it_inside ->
                                    if (it_inside.isSuccessful) {
                                        Toast.makeText(
                                            this@SplashScreenActivity,
                                            "token saved",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    //location permission
    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
//                    Toast.makeText(
//                        applicationContext,
//                        "$name permission granted",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.setData(uri)
                startActivity(intent)
            } else {
//                Toast.makeText(applicationContext, "Location permission granted", Toast.LENGTH_SHORT)
//                    .show()
            }
        }
        when (requestCode) {
            FINE_LOCATION_RO -> innerCheck("location")
        }
    }


    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("permission to access your $name is requested to use this app")
            setTitle("permission requested")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(permission), requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()

    }
}