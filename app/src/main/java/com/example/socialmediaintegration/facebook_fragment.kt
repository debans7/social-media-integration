package com.example.socialmediaintegration

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class facebook_fragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_facebook_fragment, container, false)

        val name = view.findViewById<TextView>(R.id.f_name)
        val email = view.findViewById<TextView>(R.id.f_email)
        val imageView =view.findViewById<ImageView>(R.id.facebook_image)
        val accessToken = AccessToken.getCurrentAccessToken()

        if(accessToken!=null && !accessToken.isExpired){
            val request = GraphRequest.newMeRequest(
                accessToken
            ) { `object`, _ ->
                name.text = `object`?.getString("name")
                if (`object`?.has("email") == true) {
                    email.text = `object`.getString("email")
                    }
                if (`object`?.has("id") == true) {
                    val userId = `object`.getString("id")
                    val profileImageUrl = "https://graph.facebook.com/$userId/picture?type=large"
                    Glide.with(requireContext())
                        .load(profileImageUrl)
                        .placeholder(R.drawable.loading)
                        .circleCrop()
                        .error(R.drawable.error_image_generic)
                        .into(imageView)

                }
            }

            val parameters = Bundle()
            parameters.putString("fields", "id,name,link,email")
            request.parameters = parameters
            request.executeAsync()
        }
        view.findViewById<Button>(R.id.f_signout).setOnClickListener{
            signOutDialogBox()
        }

        return view
    }

    private fun signOutDialogBox() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure?")
        builder.setMessage("Do you want to sign out?")
        builder.setPositiveButton("Yes") { _, _ ->
            signOut()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun signOut() {
        LoginManager.getInstance().logOut()
        findNavController().navigate(R.id.action_facebook_fragment_to_homeScreen)
    }
}