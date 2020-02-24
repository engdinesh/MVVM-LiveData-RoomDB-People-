package com.example.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.R;
import com.example.data.model.LoginUser;
import com.example.data.model.NewsArticle;
import com.example.data.model.NewsResponse;
import com.example.databinding.ActivityLoginBinding;

import java.util.List;
import java.util.Objects;


/**
 * Createdby Dinesh on 24/09/19.
 */

public class LoginActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);


        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {

                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                    binding.etEmail.setError("Enter an E-Mail Address");
                    binding.etEmail.requestFocus();
                }
                else if (!loginUser.isEmailValid()) {
                    binding.etEmail.setError("Enter a Valid E-mail Address");
                    binding.etEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                    binding.etPassword.setError("Enter a Password");
                    binding.etPassword.requestFocus();
                }
                else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.etPassword.setError("Enter at least 6 Digit password");
                    binding.etPassword.requestFocus();
                }
                else {

                   callLogin();
                }
            }
        });


    }

    private void callLogin()
    {
        loginViewModel.init();
        loginViewModel.getNewsRepository().observe(this, new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {

            }
        });
    }


}
