package com.evan.parknbark.emailpassword;

import androidx.annotation.NonNull;

import com.evan.parknbark.utilities.BaseActivity;
import com.evan.parknbark.validation.EditTextValidator;
import com.evan.parknbark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.dmoral.toasty.Toasty;

public class ResetPassActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout textInputResetPassEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_email_password);

        textInputResetPassEmail = findViewById(R.id.text_input_email_reset_pass);
        findViewById(R.id.button_send_reset_pass).setOnClickListener(this);
    }

    private void resetPassword(String email){
        if (EditTextValidator.isValidEditText(email, textInputResetPassEmail, getApplicationContext())) {
            showProgressBar();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Success//
                        Toasty.info(ResetPassActivity.this, getString(R.string.reset_pass_success), Toasty.LENGTH_SHORT, true).show();
                        startActivity(new Intent(ResetPassActivity.this, LoginActivity.class));
                    } else
                        showErrorToast();
                    hideProgressBar();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.button_send_reset_pass){
            hideSoftKeyboard();
            String email = textInputResetPassEmail.getEditText().getText().toString().trim();
            resetPassword(email);
        }
    }
}
