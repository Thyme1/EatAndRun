package com.thyme.eatandrun.auth


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.thyme.eatandrun.MainActivity
import com.thyme.todolist.R

class LoginFragment : Fragment() {

    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var loginButton: Button
    private lateinit var toRegisterText: TextView

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        emailView = view.findViewById(R.id.login_email_view)
        passwordView = view.findViewById(R.id.login_password_view)
        loginButton = view.findViewById(R.id.login_button_view)
        toRegisterText = view.findViewById(R.id.to_register_text)
        activity?.let { activity?.window?.setStatusBarColor(it.getColor(R.color.white)) };




        mAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = emailView.text.toString()
            val password = passwordView.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            }
        }

        toRegisterText.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
        )

        return view
    }

    fun login(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Login : success
                    intentToMainActivity()
                } else {
                    // Login: fail
                    Toast.makeText(activity, "Email or password incorrect", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    fun intentToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().startActivity(intent)
    }
}
