package com.example.testeloginjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testeloginjava.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MainActivity extends AppCompatActivity {

    // objeto para receber a vinculação do binding
    ActivityMainBinding binding;
    // criando o botão google
    //adicionar a dependencia no build.gradel
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //arquivo de vienculação com nome da classe de layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //criação de variavél de vinculação
        View view = binding.getRoot();

        setContentView(view);

        //mudando o texo do botão do google
        TextView text_botao_logar_google = (TextView) binding.botaoGoogle.getChildAt(0);
        text_botao_logar_google.setText("Fazer login com o Google");
    }
}