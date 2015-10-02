package com.maxmouchet;

public class StateTransition {

    private final State state;
    private final Character transition;

    StateTransition(State state, Character transition) {
        this.state = state;
        this.transition = transition;
    }

    @Override
    public String toString() {
        return "<StateTransition> " + state.id + " ~ " + transition.charValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StateTransition) {
            StateTransition st = (StateTransition) obj;
            return this.state.id == st.state.id && this.transition.charValue() == st.transition.charValue();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + state.id;
        hash = hash * 31 + transition.charValue();
        return hash;
    }

}
