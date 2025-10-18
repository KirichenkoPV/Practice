package Practice_18_10_2025;

import Practice_04_10_2025.behs.Utils.DfUtils;
import Practice_18_10_2025.Beh.AcceptMassageBeh;
import Practice_18_10_2025.Beh.SendMassageBeh;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyAgentSender extends Agent {
    @Override
    protected void setup() {
        DfUtils.register(this, "CommunicationRoom");
        double timeSend= Math.random()*20000;
        log.info("Ожидание до отправки сообщения {}", timeSend);
        addBehaviour(new SendMassageBeh(this, (long) timeSend));




    }
}
