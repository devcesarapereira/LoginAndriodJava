package com.example.testeloginjava;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testeloginjava.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {


    // objeto para receber a vinculação do binding
    ActivityMainBinding binding;
    // criando o botão google
    //adicionar a dependencia no build.gradel
    GoogleSignInClient googleSignInClient;
    //adicionando firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //arquivo de vienculação com nome da classe de layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //criação de variavél de vinculação
        View view = binding.getRoot();

        setContentView(view);

        //declarando mAuth do firebase
        mAuth = FirebaseAuth.getInstance();

        //mudando o texo do botão do google
        TextView text_botao_logar_google = (TextView) binding.botaoGoogle.getChildAt(0);
        text_botao_logar_google.setText("Fazer login com o Google");
        //login e senha
        binding.botaoEntrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tratamento de erro de TextEdit vazio
                // se o editTextUsuário estiver vazio retorna setError
                if (TextUtils.isEmpty(binding.editTextUsuarioLogin.getText())) {
                    binding.editTextUsuarioLogin.setError("Usuário não pode ser vazio");
                    Toast.makeText(getApplicationContext(),
                            "Por favor preencha o nome de usuário !",
                            Toast.LENGTH_LONG).show();

                    // se o editTextSenha estiver vazio retorna setError
                } else if (TextUtils.isEmpty(binding.editTextSenhaLogin.getText())) {
                    binding.editTextSenhaLogin.setError("Senha não pode estar em branco");
                    Toast.makeText(getApplicationContext(),
                            "Por favor preencha a senha!",
                            Toast.LENGTH_LONG).show();

                } else {
                    loginUsuarioESenha(
                            binding.editTextUsuarioLogin.getText().toString(),
                            binding.editTextSenhaLogin.getText().toString());
                }
            }
        });
    }

    private void abrePrincipal() {
        binding.editTextUsuarioLogin.setText("");
        binding.editTextSenhaLogin.setText("");
        //chamando a activity Principal
        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginUsuarioESenha(String usuario, String senha) {
        mAuth.signInWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "singInWithCustonToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),
                                    "Login realizado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            abrePrincipal();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "singInWithCustonTokenl:failure", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Erro Login não realizado!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try {//verificando se está logado e não deixa voltar para a tela login
            Toast.makeText(
                    getApplicationContext(),
                    "Usuário: " + currentUser.getEmail() + " logado",
                    Toast.LENGTH_SHORT).show();
            abrePrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //reload();
    }
}

