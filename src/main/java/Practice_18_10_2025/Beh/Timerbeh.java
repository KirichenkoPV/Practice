package Practice_18_10_2025.Beh;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timerbeh extends WakerBehaviour {
    public Timerbeh(Agent a, long timeout) {
        super(a, timeout);
    }

    @Override
    protected void onWake() {
        super.onWake();
    }
    @Override
    public int onEnd() {
        log.info("Я не получил сообщение");
        return super.onEnd();
    }
}
