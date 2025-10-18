package Practice_18_10_2025.Beh;

import Practice_04_10_2025.behs.Utils.DfUtils;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AcceptMassageBeh extends Behaviour {
    boolean flag = false;

    @Override
    public void onStart() {
        log.info("{} Я родился", myAgent.getLocalName());
        super.onStart();

    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage response = myAgent.receive(mt);
        if (response != null) {
            String content = response.getContent();
            log.info("Я получил сообщение {}", content);
            flag = true;
        }else{
            block();
        }
    }

    @Override
    public boolean done() {
        return flag;
    }
}
