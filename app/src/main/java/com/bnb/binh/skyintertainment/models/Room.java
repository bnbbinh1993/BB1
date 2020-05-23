package com.bnb.binh.skyintertainment.models;

import java.util.List;

public class Room {

    private List<WaitingRoom> roomData;

    public Room() {
    }

    public Room(List<WaitingRoom> roomData) {
        this.roomData = roomData;
    }

    public List<WaitingRoom> getRoomData() {
        return roomData;
    }

    public void setRoomData(List<WaitingRoom> roomData) {
        this.roomData = roomData;
    }
}
