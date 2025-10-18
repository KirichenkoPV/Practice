package Practice_18_10_2025;

import Practice_04_10_2025.behs.Utils.DfUtils;
import Practice_18_10_2025.ParallelWork.MyParallelBeh;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;

public class MyAgentAccept extends Agent {
    @Override
    protected void setup() {
        super.setup();
        DfUtils.register(this, "CommunicationRoom");
        addBehaviour(new MyParallelBeh(this, ParallelBehaviour.WHEN_ANY));
    }
}
