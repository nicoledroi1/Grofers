// Presenter CUSTOMER
package com.example.project;

import android.content.Intent;

public class CustomerPresenter implements Contract.Presenter {
    Contract.ModelCustomer model;
    Contract.View view;

    public CustomerPresenter(Contract.View view, Contract.ModelCustomer model){
        this.model = model;
        this.view = view;
    }

    @Override
    public void backButtonClicked() {
        ((LoginCustomerActivity) view).finish();
    }

    @Override
    public void registerButtonClicked() {
        ((LoginCustomerActivity) view).startActivity(new Intent((LoginCustomerActivity) view, RegisterCustomerActivity.class));
    }

    @Override
    public void submitButtonClicked(String email, String password) {
        view.showProgressBar();

        if (email.isEmpty()) {
            view.setEmailEmptyError();
            view.hideProgressBar();
            return;
        } else if (password.isEmpty()) {
            view.setPasswordEmptyError();
            view.hideProgressBar();
            return;
        }

        model.setEmail(email);
        model.setPassword(password);

        model.isLoginSuccessful(new LoginCallBackCustomer() {
            @Override
            public void loginValid() {
                view.loginSuccessfulToast();
                view.hideProgressBar();
                ((LoginCustomerActivity) view).startActivity(new Intent(((LoginCustomerActivity) view), CustomerViewActivity.class));
            }

            @Override
            public void loginInvalid() {
                view.loginFailedToast();
                view.hideProgressBar();
            }

            @Override
            public void loginDataFailed() {
                view.loginUserDataFailedToast();
                view.hideProgressBar();
            }
        });
    }
}
