package com.example.fimeff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fimeff.databinding.ActivityCadastrarUserBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CadastrarUser : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarUserBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnsalvar.setOnClickListener {
            val cadastroUsername = binding.cadastarUserneme.text.toString()
            val cadastroPassword = binding.cadastrarPassword.text.toString()

            if (cadastroUsername.isNotEmpty() && cadastroPassword.isNotEmpty()) {
                signupUser(cadastroUsername, cadastroPassword)
            } else {
                Toast.makeText(this@CadastrarUser, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@CadastrarUser, Login::class.java))
            finish()
        }
    }

    private fun signupUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        val id = databaseReference.push().key
                        val userData = UserData(id, username, password)
                        databaseReference.child(id!!).setValue(userData)
                        Toast.makeText(this@CadastrarUser, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@CadastrarUser, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@CadastrarUser, "Usuário já existe!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@CadastrarUser,
                        "Erro no Firebase: ${databaseError.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
