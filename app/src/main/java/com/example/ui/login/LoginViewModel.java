package com.example.ui.login;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.model.LoginUser;
import com.example.data.model.NewsResponse;
import com.example.data.rest.RepoRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<NewsResponse> mutableLiveData;
    private RepoRepository newsRepository;
    private CompositeDisposable disposable;
    private MutableLiveData<LoginUser> userMutableLiveData;
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public void onClick(View view) {

         LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());

         userMutableLiveData.setValue(loginUser);

    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

/*    public void init(){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = RepoRepository.getInstance();
        disposable= RepoRepository.getDisposable();
        disposable.add(newsRepository.getNews("google-news", "API_KEY").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<NewsResponse>() {
                    @Override
                    public void onSuccess(NewsResponse value)
                    {
                        mutableLiveData.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }*/

    public LiveData<NewsResponse> getNewsRepository() {

        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;

    }

}
