package com.example.socialmediaintegration

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class google_fragment : Fragment() {
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
        val view =  inflater.inflate(R.layout.fragment_google_fragment, container, false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val gsc = GoogleSignIn.getClient(requireContext(),gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val name = account?.displayName
        val email = account?.email
        val photoUrl = account?.photoUrl


        view.findViewById<TextView>(R.id.g_name).text = name
        view.findViewById<TextView>(R.id.g_email).text = email
        val imageView = view.findViewById<ImageView>(R.id.google_image)

        Glide.with(requireContext())
            .load(photoUrl)
            .placeholder(R.drawable.loading)
            .circleCrop()
            .error(R.drawable.error_image_generic)
            .into(imageView)

        view.findViewById<Button>(R.id.g_signout).setOnClickListener{
            signOutDialogBox(gsc)
        }
        return view
    }

    private fun signOutDialogBox(gsc : GoogleSignInClient) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure?")
        builder.setMessage("Do you want to sign out?")
        builder.setPositiveButton("Yes") { _, _ ->
            signOut(gsc)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun signOut(gsc: GoogleSignInClient) {
        findNavController().navigate(R.id.action_google_fragment_to_homeScreen)
        gsc.signOut()
    }


}