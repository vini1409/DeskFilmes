package com.example.fimeff

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fimeff.databinding.ActivityLoginBinding
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeFirebase()

        binding.buttonLogin.setOnClickListener{
            val loginUsername = binding.loginUsername.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            if (isInputValid(loginUsername, loginPassword)){
                loginUser(loginUsername , loginPassword)
            } else {
                showToast("Todos os campos são Obrigatórios!")
            }
        }

        binding.textdeskcadastrar.setOnClickListener{
            navigateToCadastrarUser()
        }
    }

    private fun initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
    }

    private fun isInputValid(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
    }

    private fun loginUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)

                            if (userData != null && userData.password == password) {
                                showToast("Sucesso no login")
                                navigateToMainActivity()
                                finish()
                                return
                            }
                        }
                    }

                    showToast("Falha no Login")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Database Error: ${databaseError.message}")
                }
            })
    }

    private fun navigateToCadastrarUser() {
        startActivity(Intent(this@Login, CadastrarUser::class.java))
        finish()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this@Login, MainActivity::class.java))
        finish()
    }
}
