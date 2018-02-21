package com.codingwords.sobrien.gissues;

/**
 * Created by Administrator on 2/21/2018.
 */

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.codingwords.sobrien.gissues.api.GAPIService;
import com.codingwords.sobrien.gissues.model.SearchIssuesModel;
import com.codingwords.sobrien.gissues.repo.IssueRepository;
import com.codingwords.sobrien.gissues.vm.GissuesViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;


public class GissuesUnitTest {

    @Mock //Mock annotation tells Mockito to give a mocked object
    GissuesScreen gissuesScreen;

    @Mock
    GAPIService gapiService;

    //class that is being tested
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    IssueRepository gissuesRepo;
    GissuesViewModel gissuesViewModel;

    final String dummyOwner = "ethereum";
    final String dummyRepo = "solidity";


    @Before
    public void setupGissuesViewModel(){
        //this function will be called before all tests are run
        // call this function to init all objects annotated with @mock
        MockitoAnnotations.initMocks(this);
        gissuesRepo = new IssueRepository();
        gissuesRepo.setGapiService(gapiService);
        gissuesViewModel = new GissuesViewModel(gissuesRepo);
        gissuesViewModel.setGissuesScreen(gissuesScreen);
        SearchIssuesModel sim = new SearchIssuesModel();
        gissuesViewModel.setSearchModel(sim);
        //we create an instance of the class to be tested by passing the mocked objec


    }

    @Test
    public void requestIssuesWithEmptyOwner_showsOwnerError(){
        gissuesViewModel.getSearchModel().setOwner("");
        gissuesViewModel.pullIssues();
        //use mockito to verify that the showOwnerError() method is called in the screen object
        Mockito.verify(gissuesScreen).showOwnerError();
    }

    @Test
    public void requestIssuesWithEmptyRepo_showsRepoError(){
        gissuesViewModel.getSearchModel().setOwner("ethereum");
        gissuesViewModel.getSearchModel().setRepo("");
        gissuesViewModel.pullIssues();
        Mockito.verify(gissuesScreen).showRepoError();
    }

    @Test
    public void requestIssuesWithEmptyList_showsIssuesNotFound(){
        gissuesViewModel.getSearchModel().setOwner("ethereum");
        gissuesViewModel.getSearchModel().setRepo("ethereum");
        gissuesViewModel.pullIssues();
        Mockito.verify(gissuesScreen).showIssuesNotFound();
    }


}