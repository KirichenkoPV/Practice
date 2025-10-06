package Practice_04_10_2025.behs;

import Practice_04_10_2025.behs.Utils.DfUtils;
import Practice_04_10_2025.behs.Utils.JsonUtils;
import jade.core.AID;
import jade.core.behaviours.Behaviour;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class Seller extends Behaviour {
    private MessageTemplate mt;

    @Override
    public void onStart(){
        mt =MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        log.info("I am {} and I am start",myAgent.getLocalName());


    }
    @Override
    public void action(){
        log.info("I am {} and I am action",myAgent.getLocalName());

        List<AID> aids = DfUtils.search(myAgent, "Bayer");
        log.info("Found {} buyers", aids.size());
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        for (AID r: aids){
            msg.addReceiver(r);
        }
        msg.setContent(JsonUtils.toJson("Hello "));
        log.info("We are send hello");
        myAgent.send(msg);
        ACLMessage m = myAgent.receive(mt);
        if (m != null) {
            log.info("Received Ping msg from {} with {}", m.getSender().getLocalName(), m.getContent());
        } else {
            block();
        }
    }
    @Override
    public boolean done(){
        log.info("I am {} and I am stopped", myAgent.getLocalName());
        return false;
    }

}
