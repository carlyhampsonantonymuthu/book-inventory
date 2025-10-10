package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.State;
import com.example.bookinventory.repository.StateRepository;
import com.example.bookinventory.service.StateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State addState(State state) {
        if (stateRepository.existsById(state.getStateCode())) {
            throw new RuntimeException("State already exists with code: " + state.getStateCode());
        }
        return stateRepository.save(state);
    }

    @Override
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public Optional<State> getStateByCode(String stateCode) {
        return stateRepository.findById(stateCode);
    }

    @Override
    public State updateStateName(String stateCode, String stateName) {
        State s = stateRepository.findById(stateCode)
                .orElseThrow(() -> new RuntimeException("State not found with code: " + stateCode));
        s.setStateName(stateName);
        return stateRepository.save(s);
    }
}
