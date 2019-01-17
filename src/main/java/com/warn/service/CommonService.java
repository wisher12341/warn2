package com.warn.service;

import com.warn.entity.Room;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public String getPositionInfo(int data, Room room) {
        switch (data) {
            case 1:
                return room.getNumOne();
            case 2:
                return room.getNumTwo();
            case 3:
                return room.getNumThree();
            case 4:
                return room.getNumFour();
            case 5:
                return room.getNumFive();
            case 6:
                return room.getNumSix();
            case 7:
                return room.getNumSeven();
            case 8:
                return room.getNumEight();
            case 9:
                return room.getNumNine();
            case 10:
                return room.getNumTen();
            default:
                return "floor";
        }

    }

}
