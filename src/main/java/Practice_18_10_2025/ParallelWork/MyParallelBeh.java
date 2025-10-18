package Practice_18_10_2025.ParallelWork;

import Practice_18_10_2025.Beh.AcceptMassageBeh;
import Practice_18_10_2025.Beh.Timerbeh;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyParallelBeh extends ParallelBehaviour {
    public MyParallelBeh(Agent a, int endCondition) {
        super(a, endCondition);
        addSubBehaviour(new AcceptMassageBeh());
        double timeAccept = Math.random()*30000;
        log.info("Ожидание принятия сообщения {}",timeAccept);
        addSubBehaviour(new Timerbeh(a, (long) timeAccept ));
    }
}
