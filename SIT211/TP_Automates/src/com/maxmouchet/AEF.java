package com.maxmouchet;

import java.util.HashMap;

public abstract class AEF {

    protected final HashMap<StateTransition, State> nextStates = new HashMap<>();
    protected State[] states = {};

    public void addTransition(State from, State to, Character character) {
        nextStates.put(new StateTransition(from, character), to);
    }

    public void addTransitions(State from, State to, Character[] characters) {
        for (Character character : characters) {
            addTransition(from, to, character);
        }
    }

    public void addSelfTransition(State state, Character character) {
        addTransition(state, state, character);
    }


    public boolean accepte(String input) {
        State state = states[0];

        int i = 0;
        char c;

        while (i != input.length()) {
            c = input.charAt(i++);

            StateTransition st = new StateTransition(state, c);

            if (nextStates.containsKey(st)) {
                state = nextStates.get(st);
                continue;
            } else {
                return false;
            }
        }

        return state.isExit;
    }
}
