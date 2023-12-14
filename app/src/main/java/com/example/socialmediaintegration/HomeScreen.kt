package com.example.socialmediaintegration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@Suppress("DEPRECATION")
class HomeScreen : Fragment() {
    private val signInCode =1000
    private var callbackManager = CallbackManager.Factory.create()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val gsc =GoogleSignIn.getClient(requireContext(),gso)
        view.findViewById<SignInButton>(R.id.googleLogin).setOnClickListener {
            gSignIn(gsc)
        }
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onCancel() {
                    Toast.makeText(requireContext(), "SignIn cancelled", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(requireContext(), "Error Signing in", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(result: LoginResult) {
                    findNavController().navigate(R.id.action_homeScreen_to_facebook_fragment)
                }


            })



        view.findViewById<ConstraintLayout>(R.id.facebookLogin).setOnClickListener {
            fSignIn()
        }
        return view
    }



    private fun fSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile","email"))
    }


    private fun gSignIn(gsc : GoogleSignInClient) {
        val intent = gsc.signInIntent
        startActivityForResult(intent,signInCode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == signInCode){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInRequest(task)
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInRequest(task : Task<GoogleSignInAccount>) {
        try {
            task.getResult(ApiException::class.java)
            findNavController().navigate(R.id.action_homeScreen_to_google_fragment)
        }
        catch(E : ApiException){
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}