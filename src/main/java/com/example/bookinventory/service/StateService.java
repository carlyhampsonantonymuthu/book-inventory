package com.example.bookinventory.service;

import com.example.bookinventory.entity.State;
import java.util.List;
import java.util.Optional;

public interface StateService {
    State addState(State state);
    List<State> getAllStates();
    Optional<State> getStateByCode(String stateCode);
    State updateStateName(String stateCode, String stateName);
}
