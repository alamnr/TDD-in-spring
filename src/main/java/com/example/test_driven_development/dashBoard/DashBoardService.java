package com.example.test_driven_development.dashBoard;

import org.springframework.stereotype.Service;

@Service
public class DashBoardService {

    public Integer[] getAnalyticsGraphData(){
        return new Integer[]{1,2,3,4,5,6};
    }

}
