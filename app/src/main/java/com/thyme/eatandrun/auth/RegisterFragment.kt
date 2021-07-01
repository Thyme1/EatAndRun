package com.thyme.eatandrun.auth


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.thyme.eatandrun.MainActivity
import com.thyme.todolist.R

class RegisterFragment : Fragment() {

    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var passwordConfView: EditText
    private lateinit var registerButton: Button

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        emailView = view.findViewById(R.id.register_email_view)
        passwordView = view.findViewById(R.id.register_password_view)
        passwordConfView = view.findViewById(R.id.register_passwordconf_view)
        registerButton = view.findViewById(R.id.register_button_view)

        mAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            val email = emailView.text.toString()
            val password = passwordView.text.toString()
            val passwordConfirm = passwordConfView.text.toString()
            activity?.let { activity?.window?.setStatusBarColor(it.getColor(R.color.white)) };


            if (!password.equals(passwordConfirm)) {
                Toast.makeText(activity, "Passwords doesn't match", Toast.LENGTH_LONG).show()
            } else if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                register(email, password)
            }
        }

        return view
    }

    fun register(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Register: success
                    intentToMainActivity()
                } else {
                    // Register: fail
                    Toast.makeText(activity, "Register fail", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun intentToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        // remove initial MainActivity from backstackm
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().startActivity(intent)
    }

}
