package com.maxmouchet;

public class AEF2tt extends AEF {

    AEF2tt() {
        states = new State[]{
                new State(0, false),
                new State(1, false),
                new State(2, false),
                new State(3, false),
                new State(4, false),
                new State(5, true)
        };

        addTransition(states[0], states[1], 'a');
        addTransition(states[1], states[2], 'b');
        addTransitions(states[2], states[3], new Character[]{'a', 'b'});
        addSelfTransition(states[3], 'a');
        addTransition(states[3], states[4], 'b');
        addSelfTransition(states[4], 'b');
        addTransition(states[4], states[5], 'a');
        addTransition(states[5], states[4], 'b');
        addTransition(states[5], states[3], 'a');
    }

}
