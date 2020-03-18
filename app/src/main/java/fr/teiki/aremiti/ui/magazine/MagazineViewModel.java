package fr.teiki.aremiti.ui.magazine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MagazineViewModel extends ViewModel {

	private MutableLiveData<String> mText;

	public MagazineViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is dashboard fragment");
	}

	public LiveData<String> getText() {
		return mText;
	}
}