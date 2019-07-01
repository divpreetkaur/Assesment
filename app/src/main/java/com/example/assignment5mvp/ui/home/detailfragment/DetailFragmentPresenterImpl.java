package com.example.assignment5mvp.ui.home.detailfragment;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;
import com.example.assignment5mvp.ui.home.listfragment.ListFragmentInteractor;
import com.example.assignment5mvp.ui.home.listfragment.ListFragmentInteractorImpl;
import com.example.assignment5mvp.ui.home.listfragment.ListFragmentPresenter;
import com.example.assignment5mvp.ui.home.listfragment.ListFragmentView;

import java.util.ArrayList;

public class DetailFragmentPresenterImpl implements DetailFragmentPresenter, OnDataSaveListener {
   private DetailFragmentInteractor detailFragmentInteractor;
   private DetailFragmentView detailFragmentView;
   private OnDataSaveListener onDataSaveListener;
   private Constants constants=new Constants();
    public DetailFragmentPresenterImpl()
    {

    }
    public DetailFragmentPresenterImpl(DetailFragmentView detailFragmentView,OnDataSaveListener onDataSaveListener,DetailFragmentInteractor detailFragmentInteractor)
    {
      this.detailFragmentInteractor=detailFragmentInteractor;
      this.detailFragmentView=detailFragmentView;
      this.onDataSaveListener=onDataSaveListener;
    }

    @Override
    public void validateText(String name,String rollno,String cls,DataBaseHandler dataBaseHandler,String actionType) {
         if(name.length()==0)
         {
            detailFragmentView.onFailure(R.string.enter_name);
         }
         else if(rollno.length()==0)
         {
             detailFragmentView.onFailure(R.string.enter_rollno);
         }
         else if(cls.length()==0)
         {
             detailFragmentView.onFailure(R.string.enter_class);
         }
         else
         {   detailFragmentInteractor.instantiateListener(this,this);
             detailFragmentInteractor.onSuccess(dataBaseHandler,rollno,actionType);
         }
    }

    @Override
    public void onSuccess()
    {

           detailFragmentView.onSuccess();

    }

    @Override
    public void onFailure() {
      detailFragmentView.onFailure(R.string.existing_rollno);
    }

    @Override
    public void performDbOperation(DataBaseHandler dataBaseHandler, String service, Student student, String actionType) {
        if (service.equals(constants.BACKGROUND_TASK)) {
            detailFragmentInteractor.performDbOperation(dataBaseHandler, service, student, actionType);
        }
    }

    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {
        onDataSaveListener.onDataSavedSuccess(isAddStudent,student);
    }

    @Override
    public void onDataSavedError(boolean isAddStudent) {
        onDataSaveListener.onDataSavedError(isAddStudent);

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {

    }

    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {

    }
}
