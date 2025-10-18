package Practice_18_10_2025.Beh;

import Practice_04_10_2025.behs.Utils.DfUtils;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SendMassageBeh extends WakerBehaviour {
    private List<AID> agents;

    public SendMassageBeh(Agent a, long timeout) {
        super(a, timeout);
    }

    @Override
    public void onStart() {
        log.info("{} Я родился", myAgent.getLocalName());
        super.onStart();

    }
    @Override
    public void onWake() {
        agents = DfUtils.search(myAgent, "CommunicationRoom");
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for(AID aid:agents){
            msg.addReceiver(aid);
        }
        log.info("Я отправил сообщение");
        msg.setContent("Привет");
        myAgent.send(msg);
    }
    @Override
    public int onEnd() {
        log.info("{} Я помер", myAgent.getLocalName());
        return 0;
    }
}
