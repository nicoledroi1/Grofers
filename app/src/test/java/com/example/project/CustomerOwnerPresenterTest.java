package com.example.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Intent;

@RunWith(MockitoJUnitRunner.class)
public class CustomerOwnerPresenterTest {
    private CustomerPresenter customerPresenter;
    private OwnerPresenter ownerPresenter;

    @Mock
    LoginCustomerActivity customerView;

    @Mock
    LoginOwnerActivity ownerView;

    @Mock
    LoginModelCustomer customerModel;

    @Mock
    LoginModelOwner ownerModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerPresenter = new CustomerPresenter(customerView, customerModel);
        ownerPresenter = new OwnerPresenter(ownerView, ownerModel);
    }

    /*** All CustomerPresenter Test Cases ***/

    // Constructor test(s)
    @Test
    public void testCustomerPresenterConstructor() {
        assertEquals(customerPresenter.view, customerView);
        assertEquals(customerPresenter.model, customerModel);
    }

    // backButtonClicked method test(s)
    @Test
    public void testCustomerPresenterBackButtonClicked() {
        customerPresenter.backButtonClicked();
        verify((LoginCustomerActivity) customerView).finish();
    }

    // registerButtonClicked method test(s)
    @Test
    public void testCustomerPresenterRegisterButtonClicked() {
        customerPresenter.registerButtonClicked();

        verify((LoginCustomerActivity) customerView).startActivity(any(Intent.class));
    }

    // submitButtonClicked method test(s)
    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyEmailAndPassword() {
        String email = "";
        String password = "";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setEmailEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyEmail() {
        String email = "";
        String password = "testing";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setEmailEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyPassword() {
        String email = "testing2@gmail.com";
        String password = "";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setPasswordEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedLoginValid() {
        String email = "testing2@gmail.com";
        String password = "testing";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackCustomer callback = (LoginCallBackCustomer) invocation.getArguments()[0];
                callback.loginValid();
                // Verify that once the callback has been called to be loginValid,
                // we have done the correct calls to the view.
                verify(customerView).loginSuccessfulToast();
                verify(customerView).hideProgressBar();
                verify((LoginCustomerActivity) customerView).startActivity(any(Intent.class));
                return null;
            }
        }).when(customerModel).isLoginSuccessful(any(LoginCallBackCustomer.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        customerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(customerModel).setEmail(email);
        verify(customerModel).setPassword(password);
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedInvalidLogin() {
        String email = "asd@gmail.com";
        String password = "wrongPassword";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackCustomer callback = (LoginCallBackCustomer) invocation.getArguments()[0];
                callback.loginInvalid();
                // Verify that once the callback has been called to be invalid,
                // we have done the correct calls to the view.
                verify(customerView).loginFailedToast();
                verify(customerView).hideProgressBar();
                return null;
            }
        }).when(customerModel).isLoginSuccessful(any(LoginCallBackCustomer.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        customerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(customerModel).setEmail(email);
        verify(customerModel).setPassword(password);
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedLoginDataFailed() {
        String email = "d";
        String password = "d";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackCustomer callback = (LoginCallBackCustomer) invocation.getArguments()[0];
                callback.loginDataFailed();
                // Verify that once the callback has been called to be loginDataFailed,
                // we have done the correct calls to the view.
                verify(customerView).loginUserDataFailedToast();
                verify(customerView).hideProgressBar();
                return null;
            }
        }).when(customerModel).isLoginSuccessful(any(LoginCallBackCustomer.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        customerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(customerModel).setEmail(email);
        verify(customerModel).setPassword(password);
    }

    /*** All OwnerPresenter Test Cases ***/

    // Constructor test(s)
    @Test
    public void testOwnerPresenterConstructor() {
        assertEquals(ownerPresenter.view, ownerView);
        assertEquals(ownerPresenter.model, ownerModel);
    }


    // backButtonClicked method test(s)
    @Test
    public void testOwnerPresenterBackButtonClicked() {
        ownerPresenter.backButtonClicked();
        verify((LoginOwnerActivity) ownerView).finish();
    }

    // registerButtonClicked method test(s)
    @Test
    public void testOwnerPresenterRegisterButtonClicked() {
        ownerPresenter.registerButtonClicked();

        verify((LoginOwnerActivity) ownerView).startActivity(any(Intent.class));
    }

    // submitButtonClicked method test(s)
    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyEmailAndPassword() {
        String email = "";
        String password = "";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setEmailEmptyError();
        verify(ownerView).hideProgressBar();
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyEmail() {
        String email = "";
        String password = "testing";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setEmailEmptyError();
        verify(ownerView).hideProgressBar();
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyPassword() {
        String email = "testing2@gmail.com";
        String password = "";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setPasswordEmptyError();
        verify(ownerView).hideProgressBar();
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedLoginValid() {
        String email = "aryan@gmail.com";
        String password = "testing";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackOwner callback = (LoginCallBackOwner) invocation.getArguments()[0];
                callback.loginValid();
                // Verify that once the callback has been called to be loginValid,
                // we have done the correct calls to the view.
                verify(ownerView).loginSuccessfulToast();
                verify(ownerView).hideProgressBar();
                verify((LoginOwnerActivity) ownerView).startActivity(any(Intent.class));
                return null;
            }
        }).when(ownerModel).isLoginSuccessful(any(LoginCallBackOwner.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        ownerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(ownerModel).setEmail(email);
        verify(ownerModel).setPassword(password);
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedInvalidLogin() {
        String email = "asd@gmail.com";
        String password = "wrongPassword";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackOwner callback = (LoginCallBackOwner) invocation.getArguments()[0];
                callback.loginInvalid();
                // Verify that once the callback has been called to be invalid,
                // we have done the correct calls to the view.
                verify(ownerView).loginFailedToast();
                verify(ownerView).hideProgressBar();
                return null;
            }
        }).when(ownerModel).isLoginSuccessful(any(LoginCallBackOwner.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        ownerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(ownerModel).setEmail(email);
        verify(ownerModel).setPassword(password);
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedLoginDataFailed() {
        String email = "d";
        String password = "d";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackOwner callback = (LoginCallBackOwner) invocation.getArguments()[0];
                callback.loginDataFailed();
                // Verify that once the callback has been called to be loginDataFailed,
                // we have done the correct calls to the view.
                verify(ownerView).loginUserDataFailedToast();
                verify(ownerView).hideProgressBar();
                return null;
            }
        }).when(ownerModel).isLoginSuccessful(any(LoginCallBackOwner.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        ownerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(ownerModel).setEmail(email);
        verify(ownerModel).setPassword(password);
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedLoginStoreCreation() {
        String email = "d";
        String password = "d";

        // This test case depends on the Model, so we will assume it calls back on the intended output.
        // We will stub model.isLoginSuccessful so that it calls back on the correct method,
        // i.e. assuming the output from the dependency is always correct.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginCallBackOwner callback = (LoginCallBackOwner) invocation.getArguments()[0];
                callback.loginValidStoreCreation();
                // Verify that once the callback has been called to be loginDataFailed,
                // we have done the correct calls to the view.
                verify(ownerView).loginSuccessfulToast();
                verify(ownerView).hideProgressBar();
                verify((LoginOwnerActivity) ownerView).startActivity(any(Intent.class));
                return null;
            }
        }).when(ownerModel).isLoginSuccessful(any(LoginCallBackOwner.class));

        // After stubbing, we call the submitButtonClicked method to have the presenter do its thing.
        ownerPresenter.submitButtonClicked(email, password);

        // Verify that the miscellaneous calls within submitButtonClicked have run.
        verify(ownerModel).setEmail(email);
        verify(ownerModel).setPassword(password);
    }
}