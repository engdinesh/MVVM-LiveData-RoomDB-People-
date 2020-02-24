package com.example.ui.person;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.database.AppDatabase;
import com.example.data.model.PersonModel;
import com.example.data.rest.RepoRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PersonViewModel extends ViewModel {

    private AppDatabase appDatabase ;
    private RepoRepository newsRepository;
    private CompositeDisposable disposable;

    private LiveData<List<PersonModel>> personMutableLiveData;

    public void init(Context context)
    {
        appDatabase = AppDatabase.getInstance(context);

        personMutableLiveData = appDatabase.personDao().loadAllPersons();
    }


   /* public void init(){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = RepoRepository.getInstance();
        disposable= RepoRepository.getDisposable();

        //mutableLiveData = newsRepository.getNews("google-news", "API_KEY");

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


   /* public LiveData<NewsResponse> getNewsRepository() {
        return mutableLiveData;
    }*/

    public LiveData<List<PersonModel>> getPersonData(){

        if (personMutableLiveData==null)
        {
            personMutableLiveData=new MutableLiveData<>();
        }

        return personMutableLiveData;
    }

    public void deleteItem(PersonModel personModel) {
        new deleteAsyncTask(appDatabase).execute(personModel);
    }

    private static class deleteAsyncTask extends AsyncTask<PersonModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final PersonModel... params) {
            db.personDao().delete(params[0]);
            return null;
        }

    }

}
